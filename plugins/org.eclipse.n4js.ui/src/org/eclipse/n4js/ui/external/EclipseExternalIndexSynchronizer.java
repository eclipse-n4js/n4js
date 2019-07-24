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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.LibraryChange;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.ResourceUIValidatorExtension;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link EclipseExternalIndexSynchronizer} must be used to synchronize the Xtext index with all projects that are
 * located in external workspace locations, such as the {@code node_modules} folder for npm projects.
 */
@Singleton
public class EclipseExternalIndexSynchronizer extends ExternalIndexSynchronizer {

	@Inject
	private ResourceUIValidatorExtension validatorExtension;

	@Inject
	private EclipseBasedN4JSWorkspace workspace;

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

			Collection<LibraryChange> changesSet = identifyChangeSet(forcedChangeSet, ProjectStateOperation.UPDATE);
			RegisterResult cleanResults = cleanChangesIndex(subMonitor.split(1), changesSet);
			buildChangesIndex(subMonitor.split(9), changesSet, cleanResults);

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
			Set<FileURI> toBeRemovedProjects = getToBeRemovedProjects(changeSet);
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

			Set<FileURI> toBeUpdated = getToBeBuildProjects(changeSet);
			for (FileURI cleanedPrjLoc : cleanResults.externalProjectsDone) {
				ExternalProject project = externalLibraryWorkspace.getProject(cleanedPrjLoc);
				if (project != null) {
					toBeUpdated.add(cleanedPrjLoc);
				}
			}

			subMonitor.setTaskName("Building new projects...");
			RegisterResult buildResult = externalLibraryWorkspace.registerProjects(subMonitor.split(9), toBeUpdated);
			printRegisterResults(buildResult, "built");

			Set<SafeURI<?>> toBeScheduled = new HashSet<>();
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
			validatorExtension.clearAllMarkersOfAllExternalProjects();

			synchronizeNpms(subMonitor.split(10));

		} finally {
			monitor.done();
		}
	}

	private Set<FileURI> getToBeRemovedProjects(Collection<LibraryChange> changeSet) {
		Set<FileURI> toBeDeleted = new HashSet<>();
		for (LibraryChange change : changeSet) {

			switch (change.type) {
			case Added:
				N4JSExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The added project might shadow an existing project.
					// Hence, the existing project must be cleaned.
					toBeDeleted.add(project.getSafeLocation());
				}
				break;

			case Updated:
			case Removed:
				project = externalLibraryWorkspace.getProject(change.location);
				toBeDeleted.add(change.location);
				break;

			case Install:
			case Uninstall:
				// nothing to do
			}
		}
		return toBeDeleted;
	}

	private Set<FileURI> getToBeBuildProjects(Collection<LibraryChange> changeSet) {
		Set<FileURI> toBeUpdated = new HashSet<>();
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
				N4JSExternalProject project = externalLibraryWorkspace.getProject(change.name);
				if (project != null) {
					// The removed project shadowed an existing project.
					// Hence, the shadowed project must be build.
					toBeUpdated.add(project.getSafeLocation());
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

}
