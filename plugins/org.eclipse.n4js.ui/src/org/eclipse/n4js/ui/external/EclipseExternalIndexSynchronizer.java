/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.external;

import static org.eclipse.core.runtime.SubMonitor.convert;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.LibraryChange;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link EclipseExternalIndexSynchronizer} must be used to synchronize the Xtext index with all projects that are
 * located in external workspace locations, such as the {@code node_modules} folder for npm projects.
 */
@Singleton
public class EclipseExternalIndexSynchronizer extends ExternalIndexSynchronizer {

	@Inject
	private ExternalLibraryErrorMarkerManager externalErrorMarkerManager;

	@Inject
	private NpmLogger logger;

	@Inject
	private EclipseBasedN4JSWorkspace workspace;

	@Inject
	private ExternalLibraryPreferenceStore libraryPreferenceStore;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * Call this method to synchronize the information in the Xtext index with all external projects in the external
	 * library folders.
	 */
	@Override
	public void synchronizeNpms(IProgressMonitor monitor) {
		synchronizeNpms(monitor, Collections.emptyList());
	}

	/**
	 * Call this method to synchronize the information in the Xtext index with all external projects in the external
	 * library folders.
	 */
	@Override
	public void synchronizeNpms(IProgressMonitor monitor, Collection<LibraryChange> forcedChangeSet) {
		SubMonitor subMonitor = convert(monitor, 12);

		try {
			workspace.getWorkspace().refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1));

			Collection<LibraryChange> oldChangeSet = identifyChangeSet(forcedChangeSet, false);
			RegisterResult cleanResults = cleanChangesIndex(subMonitor.split(1), oldChangeSet);

			Collection<LibraryChange> newChangesSet = identifyChangeSet(forcedChangeSet, true);
			buildChangesIndex(subMonitor.split(9), newChangesSet, cleanResults);

		} catch (Exception e) {
			checkAndClearIndex(subMonitor.split(1));
		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Call this method when the content of the {@code node_modules} folder changed. It must be called before
	 * {@link ExternalLibraryWorkspace#updateState()} adapted these changes. Otherwise the clean operation is not
	 * possible anymore, since the projects to clean will be removed from the {@link ExternalLibraryWorkspace} instance.
	 */
	private RegisterResult cleanChangesIndex(IProgressMonitor monitor, Collection<LibraryChange> changeSet) {
		try {
			monitor.setTaskName("Cleaning new projects...");
			Set<URI> toBeRemovedProjects = getToBeRemovedProjects(changeSet);
			RegisterResult cleanResults = externalLibraryWorkspace.deregisterProjects(monitor, toBeRemovedProjects);
			printRegisterResults(cleanResults, "cleaned");

			return cleanResults;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Call this method after the {@link ExternalLibraryWorkspace#updateState()} adapted the changes of the
	 * {@code node_modules} folder. Due to this adaption, new folders are already represented as external projects and
	 * can be build and added to the index.
	 */
	private void buildChangesIndex(IProgressMonitor monitor, Collection<LibraryChange> changeSet,
			RegisterResult cleanResults) {

		SubMonitor subMonitor = convert(monitor, 10);
		try {

			Set<URI> toBeUpdated = getToBeBuildProjects(changeSet);
			for (URI cleanedPrjLoc : cleanResults.externalProjectsDone) {
				ExternalProject project = externalLibraryWorkspace.getProject(cleanedPrjLoc);
				if (project != null) {
					toBeUpdated.add(cleanedPrjLoc);
				}
			}

			subMonitor.setTaskName("Building new projects...");
			RegisterResult buildResult = externalLibraryWorkspace.registerProjects(subMonitor.split(9), toBeUpdated);
			printRegisterResults(buildResult, "built");

			Set<URI> toBeScheduled = new HashSet<>();
			toBeScheduled.addAll(cleanResults.affectedWorkspaceProjects);
			toBeScheduled.addAll(buildResult.affectedWorkspaceProjects);
			externalLibraryWorkspace.scheduleWorkspaceProjects(subMonitor.split(1), toBeScheduled);

		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Call this method to re-index all external libraries. This means: All external libraries are cleaned and re-build.
	 */
	@Override
	public void reindexAllExternalProjects(IProgressMonitor monitor) {
		try {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 11);

			monitor.setTaskName("Cleaning all projects...");
			externalLibraryWorkspace.deregisterAllProjects(subMonitor.split(1));
			externalErrorMarkerManager.clearAllMarkers();

			synchronizeNpms(subMonitor.split(10));

		} finally {
			monitor.done();
		}
	}

	private Set<URI> getToBeRemovedProjects(Collection<LibraryChange> changeSet) {
		Set<URI> toBeDeleted = new HashSet<>();
		for (LibraryChange change : changeSet) {

			switch (change.type) {
			case Added:
				N4JSExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The added project might shadow an existing project.
					// Hence, the existing project must be cleaned.
					toBeDeleted.add(project.getIProject().getLocation());
				}
				break;

			case Updated:
			case Removed:
				project = externalLibraryWorkspace.getProject(change.location);
				if (project != null) {
					toBeDeleted.add(change.location);
				}
				break;

			case Install:
			case Uninstall:
				// nothing to do
			}
		}
		return toBeDeleted;
	}

	private Set<URI> getToBeBuildProjects(Collection<LibraryChange> changeSet) {
		Set<URI> toBeUpdated = new HashSet<>();
		for (LibraryChange change : changeSet) {

			switch (change.type) {
			case Added:
			case Updated: {
				ExternalProject project = externalLibraryWorkspace.getProject(change.location);
				if (project != null) {
					// Usually, updated and added projects must be in the workspace already,
					// since the {@link ExternalLibraryWorkspace#updateState()} was called.
					toBeUpdated.add(change.location);
				} else {
					String msg = "ERROR: The project '" + change.name + "' was " + change.type;
					msg += " but could not be found at " + change.location + ".\n";
					msg += "       Hence, the project is not available in the workspace.";
					logger.logInfo(msg);
				}
				break;
			}
			case Removed: {
				ExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The removed project shadowed an existing project.
					// Hence, the shadowed project must be build.
					toBeUpdated.add(URIUtils.convert(project));
				}
				break;
			}
			case Install:
			case Uninstall:
				// nothing to do
			}
		}
		return toBeUpdated;
	}

	private void printRegisterResults(RegisterResult rr, String jobName) {
		if (!rr.externalProjectsDone.isEmpty()) {
			SortedSet<String> prjNames = getProjectNamesFromLocations(rr.externalProjectsDone);
			logger.logInfo("External libraries " + jobName + ": " + String.join(", ", prjNames));
		}

		if (!rr.wipedProjects.isEmpty()) {
			SortedSet<String> prjNames = new TreeSet<>();
			for (URI location : rr.wipedProjects) {
				String projectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
				prjNames.add(projectName);
			}
			logger.logInfo("Projects deregistered: " + String.join(", ", prjNames));
		}

		if (!rr.affectedWorkspaceProjects.isEmpty()) {
			SortedSet<String> prjNames = getProjectNamesFromLocations(rr.affectedWorkspaceProjects);
			logger.logInfo("Workspace projects affected: " + String.join(", ", prjNames));
		}
	}

	private SortedSet<String> getProjectNamesFromLocations(Collection<URI> projectLocations) {
		SortedSet<String> prjNames = new TreeSet<>();
		for (URI location : projectLocations) {
			IN4JSProject p = core.findProject(location).orNull();
			prjNames.add(p.getProjectName());
		}
		return prjNames;
	}

	/** Sets error markers to every N4JS project iff the folder node_modules and the N4JS index are out of sync. */
	public void checkAndClearIndex(IProgressMonitor monitor) {
		Collection<LibraryChange> changeSet = identifyChangeSet(Collections.emptyList(), true);
		cleanRemovedProjectsFromIndex(monitor, changeSet);
	}

	private void cleanRemovedProjectsFromIndex(IProgressMonitor monitor, Collection<LibraryChange> changeSet) {
		monitor.setTaskName("Deregister removed projects...");
		Set<URI> cleanProjects = new HashSet<>();
		for (LibraryChange libChange : changeSet) {
			if (libChange.type == LibraryChangeType.Removed) {
				cleanProjects.add(libChange.location);
			}
		}

		RegisterResult cleanResult = externalLibraryWorkspace.deregisterProjects(monitor, cleanProjects);
		printRegisterResults(cleanResult, "deregistered");
	}

	/** Triggers synchronization of the stored node_modules folders with the ones that actually exist. */
	@Override
	public void synchronizeNodeModulesFolders() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<Path> projectRoots = new LinkedList<>();
		for (IProject project : projects) {
			if (project.isAccessible()) {
				Path path = project.getLocation().toFile().toPath();
				projectRoots.add(path);
			}
		}

		libraryPreferenceStore.resetDefaults();
		Collection<Path> locations = nodeModulesDiscoveryHelper.findNodeModulesFolders(projectRoots);
		for (Path location : locations) {
			libraryPreferenceStore.add(location.toUri());
		}
		libraryPreferenceStore.save(new NullProgressMonitor());
	}

}
