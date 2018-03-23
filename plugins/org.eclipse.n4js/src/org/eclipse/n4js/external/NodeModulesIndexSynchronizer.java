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
package org.eclipse.n4js.external;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.external.NodeModulesFolderListener.LibraryChange;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.resources.ExternalProject;

import com.google.inject.Inject;

/**
 * The {@link NodeModulesIndexSynchronizer} must be called in the following way:
 * <ol>
 * <li>Call {@link #cleanOutdatedIndex(IProgressMonitor, MultiStatus, List)} as soon as a file change is detected in the
 * folder {@code node_modules} but before the {@link ExternalLibraryWorkspace#updateState()} is called.
 * <li>Call {@link #synchronizeIndex(IProgressMonitor, MultiStatus, List, boolean)} after
 * {@link ExternalLibraryWorkspace#updateState()} was called.
 * </ol>
 */
public class NodeModulesIndexSynchronizer {
	private RegisterResult cleanResults;

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private NpmLogger logger;

	/**
	 * Call this method when the content of the {@code node_modules} folder changed. It must be called before
	 * {@link ExternalLibraryWorkspace#updateState()} adapted these changes. Otherwise the clean operation is not
	 * possible anymore, since the projects to clean will be removed from the {@link ExternalLibraryWorkspace} instance.
	 */
	public void cleanOutdatedIndex(IProgressMonitor monitor, MultiStatus status, List<LibraryChange> changeSet) {
		if (!Platform.isRunning()) {
			return;
		}

		try {
			MultiStatus status2 = statusHelper.createMultiStatus("Status of synchronizing multiple npm dependencies.");

			monitor.setTaskName("Cleaning new projects...");
			Set<URI> toBeRemovedProjects = getToBeRemovedProjects(changeSet);
			cleanResults = externalLibraryWorkspace.deregisterProjects(monitor, toBeRemovedProjects);
			printRegisterResults(cleanResults, "cleaned");

			if (!status2.isOK()) {
				logger.logInfo("There were errors during installation, see logs for details.");
			}

			status.merge(status2);

		} finally {
			monitor.done();
		}
	}

	/**
	 * Call this method after the {@link ExternalLibraryWorkspace#updateState()} adapted the changes of the
	 * {@code node_modules} folder. Due to this adaption, new folders are already represented as external projects and
	 * can be build and added to the index.
	 */
	public void synchronizeIndex(IProgressMonitor monitor, MultiStatus status, List<LibraryChange> changeSet,
			boolean triggerCleanbuild) {

		if (!Platform.isRunning()) {
			return;
		}

		try {
			MultiStatus status2 = statusHelper.createMultiStatus("Status of synchronizing multiple npm dependencies.");

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

			if (!status2.isOK()) {
				logger.logInfo("There were errors during installation, see logs for details.");
			}

			Set<URI> toBeScheduled = new HashSet<>();
			toBeScheduled.addAll(cleanResults.affectedWorkspaceProjects);
			toBeScheduled.addAll(buildResult.affectedWorkspaceProjects);
			if (triggerCleanbuild) {
				externalLibraryWorkspace.scheduleWorkspaceProjects(monitor, toBeScheduled);
			} else {
				// clean only
			}

			status.merge(status2);

		} finally {
			monitor.done();
		}
	}

	private Set<URI> getToBeRemovedProjects(List<LibraryChange> changeSet) {
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
			}
		}
		return toBeDeleted;
	}

	private Set<URI> getToBeBuildProjects(List<LibraryChange> changeSet) {
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

}
