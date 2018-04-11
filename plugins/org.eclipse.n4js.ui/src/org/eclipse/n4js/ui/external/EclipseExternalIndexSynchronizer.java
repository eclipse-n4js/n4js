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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.external.LibraryChange;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link EclipseExternalIndexSynchronizer} must be used to synchronize the Xtext index with all projects that are
 * located in external workspace locations, such as the {@code node_modules} folder for npm projects.
 */
@Singleton
public class EclipseExternalIndexSynchronizer extends ExternalIndexSynchronizer {

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private NpmLogger logger;

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
		SubMonitor subMonitor = convert(monitor, 1);

		try {
			Collection<LibraryChange> changeSet = identifyChangeSet(forcedChangeSet);
			RegisterResult cleanResults = cleanOutdatedIndex(subMonitor, changeSet);

			// Updates available projects in: IN4JSCore, ExternalLibraryWorkspace, N4JSModel, ExternalProjectProvider
			externalLibraryWorkspace.updateState();

			synchronizeIndex(subMonitor, changeSet, cleanResults);

			// clean error markers 'out-of-sync'
			setOutOfSyncMarkers(false);

		} catch (Throwable t) {
			checkAndSetOutOfSyncMarkers();
			throw t;
		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Call this method when the content of the {@code node_modules} folder changed. It must be called before
	 * {@link ExternalLibraryWorkspace#updateState()} adapted these changes. Otherwise the clean operation is not
	 * possible anymore, since the projects to clean will be removed from the {@link ExternalLibraryWorkspace} instance.
	 */
	private RegisterResult cleanOutdatedIndex(IProgressMonitor monitor, Collection<LibraryChange> changeSet) {
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
	private void synchronizeIndex(IProgressMonitor monitor, Collection<LibraryChange> changeSet,
			RegisterResult cleanResults) {
		try {

			Set<URI> toBeUpdated = getToBeBuildProjects(changeSet);
			for (URI cleanedPrjLoc : cleanResults.externalProjectsDone) {
				ExternalProject project = externalLibraryWorkspace.getProject(cleanedPrjLoc);
				if (project != null) {
					toBeUpdated.add(cleanedPrjLoc);
				}
			}

			monitor.setTaskName("Building new projects...");
			RegisterResult buildResult = externalLibraryWorkspace.registerProjects(monitor, toBeUpdated);
			printRegisterResults(buildResult, "built");

			Set<URI> toBeScheduled = new HashSet<>();
			toBeScheduled.addAll(cleanResults.affectedWorkspaceProjects);
			toBeScheduled.addAll(buildResult.affectedWorkspaceProjects);
			externalLibraryWorkspace.scheduleWorkspaceProjects(monitor, toBeScheduled);

		} finally {
			monitor.done();
		}
	}

	/**
	 * Call this method to re-index all external libraries. This means: All external libraries are cleaned and re-build.
	 */
	@Override
	public void reindexAllExternalProjects(IProgressMonitor monitor) {
		try {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 2);

			monitor.setTaskName("Cleaning all projects...");
			RegisterResult cleanResult = externalLibraryWorkspace.deregisterAllProjects(monitor);
			printRegisterResults(cleanResult, "clean");
			subMonitor.worked(1);

			// Updates available projects in: IN4JSCore, ExternalLibraryWorkspace, N4JSModel, ExternalProjectProvider
			externalLibraryWorkspace.updateState();

			Set<URI> toBeReindexed = new HashSet<>();
			for (N4JSExternalProject n4ExtProject : externalLibraryWorkspace.getProjects()) {
				URI uri = URIUtils.toFileUri(n4ExtProject.getLocationURI());
				toBeReindexed.add(uri);
			}

			monitor.setTaskName("Building new projects...");
			RegisterResult buildResult = externalLibraryWorkspace.registerProjects(monitor, toBeReindexed);
			printRegisterResults(buildResult, "built");
			subMonitor.worked(1);

			Set<URI> toBeScheduled = new HashSet<>();
			toBeScheduled.addAll(cleanResult.affectedWorkspaceProjects);
			toBeScheduled.addAll(buildResult.affectedWorkspaceProjects);
			externalLibraryWorkspace.scheduleWorkspaceProjects(monitor, toBeScheduled);

			// clean error markers 'out-of-sync'
			setOutOfSyncMarkers(false);

		} catch (Throwable t) {
			checkAndSetOutOfSyncMarkers();
			throw t;
		} finally {
			monitor.done();
		}
	}

	private Set<URI> getToBeRemovedProjects(Collection<LibraryChange> changeSet) {
		Set<URI> toBeDeleted = new HashSet<>();
		for (LibraryChange change : changeSet) {

			switch (change.type) {
			case Added:
				ExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The added project will shadow an existing project.
					// Hence, the existing project must be cleaned.
					toBeDeleted.add(change.location);
				}
				break;

			case Updated:
			case Removed:
				toBeDeleted.add(change.location);
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
			case Updated:
				toBeUpdated.add(change.location);
				break;

			case Removed:
				ExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The removed project shadowed an existing project.
					// Hence, the shadowed project must be build.
					toBeUpdated.add(change.location);
				}
				break;

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

		if (!rr.affectedWorkspaceProjects.isEmpty()) {
			SortedSet<String> prjNames = getProjectNamesFromLocations(rr.affectedWorkspaceProjects);
			logger.logInfo("Workspace projects affected: " + String.join(", ", prjNames));
		}
	}

	private SortedSet<String> getProjectNamesFromLocations(Collection<URI> projectLocations) {
		SortedSet<String> prjNames = new TreeSet<>();
		for (URI location : projectLocations) {
			IN4JSProject p = core.findProject(location).orNull();
			prjNames.add(p.getProjectId());
		}
		return prjNames;
	}

	/** Sets error markers to every N4JS project iff the folder node_modules and the N4JS index are out of sync. */
	public void checkAndSetOutOfSyncMarkers() {
		boolean inSync = isProjectsSynchronized();
		setOutOfSyncMarkers(!inSync);
	}

	private void setOutOfSyncMarkers(boolean setMarkers) {
		for (IN4JSProject prj : core.findAllProjects()) {
			if (!prj.isExternal() && prj.exists() && prj instanceof N4JSEclipseProject) {
				N4JSEclipseProject n4EclPrj = (N4JSEclipseProject) prj;
				IProject iProject = n4EclPrj.getProject();
				IResource markerResource = iProject;

				// delete markers
				try {
					markerResource.deleteMarkers(null, true, 0);
					for (IMarker marker : markerResource.findMarkers(null, true, 0)) {
						String issueCode = marker.getAttribute(Issue.CODE_KEY, "");
						if (issueCode.equals(IssueCodes.NODE_MODULES_OUT_OF_SYNC)) {
							marker.delete();
						}
					}
				} catch (CoreException e1) {
					// ignore
				}

				// add markers
				if (setMarkers && !iProject.isHidden() && iProject.isAccessible()) {
					try {
						addMarker(markerResource);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Adds an error marker to a given {@link IResource}, in this case to a {@link IProject}.
	 * <p>
	 * Inspired by {@link MarkerCreator#createMarker(Issue, IResource, String)}
	 */
	private void addMarker(IResource resource) throws CoreException {
		IMarker marker = resource.createMarker("org.eclipse.n4js.n4mf.ui.n4mf.check.normal");

		marker.setAttribute(IMarker.LOCATION, "N4JS Index");
		marker.setAttribute(Issue.CODE_KEY, IssueCodes.NODE_MODULES_OUT_OF_SYNC);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.MESSAGE, "node_modules folder and N4JS index are out of sync");
		marker.setAttribute(Issue.URI_KEY, resource.getLocation().toString());
		marker.setAttribute("FIXABLE_KEY", true);

	}
}
