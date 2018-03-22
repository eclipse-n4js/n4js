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
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.external.NodeModulesFolderListener.LibraryChange;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.builder.impl.ToBeBuilt;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * The {@link NodeModulesIndexSynchronizer} must be called in the following way:
 * <ol>
 * <li>Call {@link #cleanOutdatedIndex(IProgressMonitor, List)} as soon as a file change is detected in the folder
 * {@code node_modules} but before the {@link ExternalLibraryWorkspace#updateState()} is called.
 * <li>Call {@link #synchronizeIndex(IProgressMonitor, List)} after {@link ExternalLibraryWorkspace#updateState()} was
 * called.
 * </ol>
 */
@SuppressWarnings("restriction")
public class NodeModulesIndexSynchronizer {

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
	public void cleanOutdatedIndex(IProgressMonitor monitor, List<LibraryChange> changeSet) {
		if (!Platform.isRunning()) {
			return;
		}

		try {
			MultiStatus status = statusHelper.createMultiStatus("Status of synchronizing multiple npm dependencies.");

			monitor.setTaskName("Cleaning new projects...");
			Set<URI> toBeDeleted = getToBeDeleted(changeSet);
			cleanBuildDependencies(monitor, status, toBeDeleted, new HashSet<>());

		} finally {
			monitor.done();
		}
	}

	/**
	 * Call this method after the {@link ExternalLibraryWorkspace#updateState()} adapted the changes of the
	 * {@code node_modules} folder. Due to this adaption, new folders are already represented as external projects and
	 * can be build and added to the index.
	 */
	public void synchronizeIndex(IProgressMonitor monitor, List<LibraryChange> changeSet) {
		if (!Platform.isRunning()) {
			return;
		}

		try {
			MultiStatus status = statusHelper.createMultiStatus("Status of synchronizing multiple npm dependencies.");
			// adaptNPMPackages(monitor, status, changeSet);

			monitor.setTaskName("Building new projects...");
			Set<URI> toBeUpdated = getToBeBuild(changeSet);
			cleanBuildDependencies(monitor, status, new HashSet<>(), toBeUpdated);

		} finally {
			monitor.done();
		}
	}

	private Set<URI> getToBeDeleted(List<LibraryChange> changeSet) {
		Set<URI> toBeDeleted = new HashSet<>();
		for (LibraryChange change : changeSet) {
			ExternalProject project = externalLibraryWorkspace.getProject(change.name);
			URI locationURI = project == null ? null : URIUtils.convert(project.getLocationURI());

			switch (change.type) {
			case Added:
				if (project != null) {
					// The added project will shadow an existing project.
					// Hence, the existing project must be cleaned.
					toBeDeleted.add(locationURI);
				}
				break;
			case Updated:
			case Removed:
				Preconditions.checkState(locationURI != null);
				toBeDeleted.add(locationURI);
				break;
			}
		}
		return toBeDeleted;
	}

	private Set<URI> getToBeBuild(List<LibraryChange> changeSet) {
		Set<URI> toBeUpdated = new HashSet<>();
		for (LibraryChange change : changeSet) {
			ExternalProject project = externalLibraryWorkspace.getProject(change.name);
			URI locationURI = project == null ? null : URIUtils.convert(project.getLocationURI());

			switch (change.type) {
			case Added:
			case Updated:
				Preconditions.checkState(locationURI != null);
				toBeUpdated.add(locationURI);
				break;
			case Removed:
				if (project != null) {
					// The removed project shadowed an existing project.
					// Hence, the shadowed project must be build.
					toBeUpdated.add(locationURI);
				}
				break;
			}
		}
		return toBeUpdated;
	}

	private void cleanBuildDependencies(IProgressMonitor monitor, MultiStatus status, Set<URI> toBeDeleted,
			Set<URI> toBeUpdated) {

		if (!Platform.isRunning()) {
			return;
		}

		ToBeBuilt tbb = new ToBeBuilt();
		tbb.getToBeDeleted().addAll(toBeDeleted);
		tbb.getToBeUpdated().addAll(toBeUpdated);

		RegisterResult rr = externalLibraryWorkspace.registerProjects(tbb, monitor, false);
		printRegisterResults(rr);

		if (!status.isOK()) {
			logger.logInfo("There were errors during installation, see logs for details.");
		}
	}

	private void printRegisterResults(RegisterResult rr) {
		if (!rr.externalProjectsCleaned.isEmpty()) {
			SortedSet<String> prjNames = getProjectNames(rr.externalProjectsCleaned);
			logger.logInfo("External libraries cleaned: " + String.join(", ", prjNames));
		}

		if (!rr.externalProjectsBuilt.isEmpty()) {
			SortedSet<String> prjNames = getProjectNames(rr.externalProjectsBuilt);
			logger.logInfo("External libraries built: " + String.join(", ", prjNames));
		}

		if (!rr.workspaceProjectsScheduled.isEmpty()) {
			SortedSet<String> prjNames = getProjectNames(rr.workspaceProjectsScheduled);
			logger.logInfo("Workspace projects scheduled: " + String.join(", ", prjNames));
		}
	}

	private SortedSet<String> getProjectNames(Collection<? extends IProject> projects) {
		SortedSet<String> prjNames = new TreeSet<>();
		prjNames.addAll(projects.stream().map(p -> p.getName()).collect(Collectors.toSet()));
		return prjNames;
	}

}
