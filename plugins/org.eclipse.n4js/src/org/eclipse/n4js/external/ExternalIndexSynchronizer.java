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

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryWorkspace.RegisterResult;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.packagejson.PackageJsonResourceDescriptionExtension;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.validation.helper.FolderContainmentHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * The {@link ExternalIndexSynchronizer} must be used to synchronize the Xtext index with all projects that are located
 * in external workspace locations, such as the {@code node_modules} folder for npm projects.
 */
@ImplementedBy(HlcExternalIndexSynchronizer.class)
public abstract class ExternalIndexSynchronizer {

	/**
	 * Used for finding locations and accessing the Xtext index, also used by subclasses.
	 */
	@Inject
	protected IN4JSCore core;

	/**
	 * Find external workspace location and synchronize external projects, also used by subclasses.
	 */
	@Inject
	protected ExternalLibraryWorkspace externalLibraryWorkspace;

	/** shadowing helper */
	@Inject
	protected ShadowingInfoHelper shadowingInfoHelper;

	/** containment helper */
	@Inject
	protected FolderContainmentHelper containmentHelper;

	/** preference store */
	@Inject
	protected ExternalLibraryPreferenceStore libraryPreferenceStore;

	/** Npm logger */
	@Inject
	protected NpmLogger logger;

	/**
	 * Call this method to synchronize the information in the Xtext index with all external projects in the external
	 * library folders.
	 */
	abstract public void synchronizeNpms(IProgressMonitor monitor);

	/**
	 * see {@link #synchronizeNpms(IProgressMonitor)}
	 * <p>
	 * All affected projects are rebuild for the given set of changes.
	 */
	abstract public void synchronizeNpms(IProgressMonitor monitor, Collection<LibraryChange> forcedChangeSet);

	/**
	 * Call this method to re-index all external libraries. This means: All external libraries are cleaned and re-build.
	 */
	abstract public void reindexAllExternalProjects(IProgressMonitor monitor);

	/** Enum to configure the method {@link ExternalIndexSynchronizer#findNpmsInFolder(ProjectStateOperation)} */
	public enum ProjectStateOperation {
		/** uses the current state */
		NONE,
		/** computes the state from disk but leaves the cache untouched */
		PEEK,
		/** computes the state from disk and updates the cache */
		UPDATE
	}

	/**
	 * Note: Expensive method
	 * <p>
	 * Returns a map that maps the names of projects as they can be found in the {@code node_modules} folder to their
	 * locations and versions.
	 *
	 * @param operation
	 *            configuration. see {@link ProjectStateOperation}
	 */
	final public Map<String, Pair<URI, String>> findNpmsInFolder(ProjectStateOperation operation) {
		Map<String, Pair<URI, String>> npmsFolder = new HashMap<>();

		List<org.eclipse.xtext.util.Pair<URI, ProjectDescription>> prjs = Collections.emptyList();
		switch (operation) {
		case NONE:
			prjs = externalLibraryWorkspace.getProjectsIncludingUnnecessary();
			break;
		case PEEK:
			prjs = externalLibraryWorkspace.computeProjectsIncludingUnnecessary();
			break;
		case UPDATE:
			externalLibraryWorkspace.updateState();
			prjs = externalLibraryWorkspace.getProjectsIncludingUnnecessary();
			break;
		}

		for (org.eclipse.xtext.util.Pair<URI, ProjectDescription> pair : prjs) {

			URI location = pair.getFirst();
			IN4JSProject project = core.findProject(location).orNull();

			if (project == null || !shadowingInfoHelper.isShadowedProject(project)) {
				ProjectDescription projectDescription = pair.getSecond();
				VersionNumber version = projectDescription.getProjectVersion();
				String name = projectDescription.getProjectName();

				if (version != null) {
					npmsFolder.putIfAbsent(name, Pair.of(location, version.toString()));
				}
			}
		}

		return npmsFolder;
	}

	/**
	 * Returns a map that maps the names of projects as they can be found in the index to their locations and versions.
	 */
	public Map<String, Pair<URI, String>> findNpmsInIndex() {
		// keep map of all NPMs that were discovered in the index
		Map<String, Pair<URI, String>> discoveredNpmsInIndex = new HashMap<>();

		final ResourceSet resourceSet = core.createResourceSet(Optional.absent());
		final IResourceDescriptions index = core.getXtextIndex(resourceSet);

		for (IResourceDescription resourceDescription : index.getAllResourceDescriptions()) {
			boolean isExternal = resourceDescription.getURI().isFile();
			if (isExternal) {
				addToIndex(discoveredNpmsInIndex, resourceDescription);
			}
		}

		return discoveredNpmsInIndex;
	}

	/** @return true iff the given project (i.e. its package.json) is contained in the index */
	public boolean isInIndex(N4JSExternalProject project) {
		return isInIndex(project.getIProject().getProjectDescriptionLocation().orNull());
	}

	/** @return true iff the given project (i.e. its package.json) is contained in the index */
	public boolean isInIndex(URI projectLocation) {
		final ResourceSet resourceSet = core.createResourceSet(Optional.absent());
		final IResourceDescriptions index = core.getXtextIndex(resourceSet);
		IResourceDescription resourceDescription = index.getResourceDescription(projectLocation);
		return resourceDescription != null;
	}

	/** Sets error markers to every N4JS project iff the folder node_modules and the N4JS index are out of sync. */
	public void checkAndClearIndex(IProgressMonitor monitor) {
		Collection<LibraryChange> changeSet = identifyChangeSet(Collections.emptyList(), ProjectStateOperation.UPDATE);
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

	/**
	 * Note: Expensive method: works on the disk directly (not on the cache of external library WS)
	 *
	 * @return a set of all changes between the Xtext index and the external projects in all external locations
	 */
	final protected Collection<LibraryChange> identifyChangeSet(Collection<LibraryChange> forcedChangeSet,
			ProjectStateOperation operation) {

		int synchronizeStatusCode = libraryPreferenceStore.synchronizeNodeModulesFolders().getCode();
		if (synchronizeStatusCode == ExternalLibraryPreferenceStore.STATUS_CODE_SAVED_CHANGES) {
			operation = ProjectStateOperation.NONE;
		}

		Collection<LibraryChange> changes = new LinkedHashSet<>(forcedChangeSet);

		Map<String, Pair<URI, String>> npmsOfIndex = findNpmsInIndex();
		Map<String, Pair<URI, String>> npmsOfFolder = findNpmsInFolder(operation);

		Set<String> differences = new HashSet<>();
		differences.addAll(npmsOfIndex.keySet());
		differences.addAll(npmsOfFolder.keySet());
		SetView<String> intersection = Sets.intersection(npmsOfIndex.keySet(), npmsOfFolder.keySet());
		differences.removeAll(intersection);

		for (String diff : differences) {
			String name = diff;
			LibraryChange change = null;

			if (npmsOfFolder.containsKey(diff)) {
				// new in folder, not in index
				URI location = npmsOfFolder.get(name).getKey();
				if (externalLibraryWorkspace.isNecessary(location)) {
					String version = npmsOfFolder.get(name).getValue();
					change = new LibraryChange(LibraryChangeType.Added, location, name, version);
				}
			}
			if (npmsOfIndex.containsKey(diff)) {
				// removed in folder, still in index
				URI location = npmsOfIndex.get(name).getKey();
				String version = npmsOfIndex.get(name).getValue();
				change = new LibraryChange(LibraryChangeType.Removed, location, name, version);
			}
			if (change != null) {
				changes.add(change);
			}
		}

		for (String name : intersection) {
			String versionIndex = npmsOfIndex.get(name).getValue();
			String versionFolder = npmsOfFolder.get(name).getValue();
			URI locationIndex = npmsOfIndex.get(name).getKey();
			URI locationFolder = npmsOfFolder.get(name).getKey();

			boolean shadowingChanged = !locationFolder.equals(locationIndex);
			boolean versionsChanged = versionIndex != null && !versionIndex.equals(versionFolder);
			if (shadowingChanged || versionsChanged) {
				changes.add(new LibraryChange(LibraryChangeType.Removed, locationIndex, name, versionIndex));
				changes.add(new LibraryChange(LibraryChangeType.Added, locationFolder, name, versionFolder));
			}
		}

		return changes;
	}

	private void addToIndex(Map<String, Pair<URI, String>> npmsIndex, IResourceDescription resourceDescription) {
		URI nestedLocation = resourceDescription.getURI();
		java.net.URI rootLocationJNU = externalLibraryWorkspace.getRootLocationForResourceOrInfer(nestedLocation);
		if (rootLocationJNU == null) {
			logger.logInfo("Could not find location for: " + nestedLocation.toString()
					+ ".\n Please rebuild external libraries!");
			return;
		}
		URI rootLocation = URI.createURI(rootLocationJNU.toString());
		String name = getPackageName(nestedLocation, URI.createURI(rootLocation.toString()));
		URI packageLocation = createProjectLocation(rootLocation, name);
		String version = getVersion(resourceDescription, nestedLocation, name, packageLocation);

		if (!npmsIndex.containsKey(name) || version != null) {
			npmsIndex.put(name, Pair.of(packageLocation, version));
		}
	}

	private String getVersion(IResourceDescription resourceDescription, URI nestedLocation, String name,
			URI packageLocation) {

		if (!isProjectDescriptionFile(nestedLocation, packageLocation)) {
			return null;
		}

		Iterable<IEObjectDescription> pds = resourceDescription
				.getExportedObjectsByType(JSONPackage.eINSTANCE.getJSONDocument());
		Iterator<IEObjectDescription> pdsIter = pds.iterator();

		if (pdsIter.hasNext()) {
			IEObjectDescription pDescription = pdsIter.next();
			String nameFromPackageJSON = PackageJsonResourceDescriptionExtension.getProjectName(pDescription);
			if (nameFromPackageJSON == null || name.equals(nameFromPackageJSON)) {
				// consistency check
				String version = pDescription.getUserData(PackageJsonResourceDescriptionExtension.PROJECT_VERSION_KEY);
				return version;
			}
		}
		return null;
	}

	/**
	 * Returns {@code true} iff the given {@code resourceLocation} is considered the project description file
	 * (package.json) of the package that is located at the given {@code packageLocation}.
	 *
	 * More specifically, this method checks the name of the specified resource and its direct containment in the
	 * package location.
	 */
	private boolean isProjectDescriptionFile(URI resourceLocation, final URI packageLocation) {
		boolean isProjectDescriptionFile = true;
		// URI points to file named 'package.json'
		isProjectDescriptionFile &= resourceLocation.lastSegment().equals(N4JSGlobals.PACKAGE_JSON);
		// URI points to 'package.json' directly contained in the project directory
		isProjectDescriptionFile &= resourceLocation.equals(packageLocation.appendSegment(N4JSGlobals.PACKAGE_JSON));
		return isProjectDescriptionFile;
	}

	/**
	 * Infers the name of the package that contains the given nested location, relative to the given
	 * {@code workspaceLocation}
	 */
	private String getPackageName(URI nestedLocation, URI workspaceLocation) {
		if (!containmentHelper.isContained(nestedLocation, workspaceLocation)) {
			throw new IllegalArgumentException("Cannot determine package name of " + nestedLocation
					+ ": The nested location is not contained in the given workspace location " + workspaceLocation);
		}

		// make sure workspace location has trailing path separator (for correct resolution)
		if (!workspaceLocation.hasTrailingPathSeparator()) {
			workspaceLocation = workspaceLocation.appendSegment("");
		}
		final URI relativeLocation = nestedLocation.deresolve(workspaceLocation);

		if (relativeLocation.segmentCount() == 0) {
			throw new IllegalArgumentException("Malformed package location " + nestedLocation
					+ ": Expected at least one segment in addition to workspace location " + workspaceLocation + ".");
		}

		if (relativeLocation.segment(0).startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX)) {
			if (relativeLocation.segmentCount() < 2) {
				throw new IllegalArgumentException("Malformed package location: " + nestedLocation);
			}

			return relativeLocation.segment(0) // scope segment
					+ File.separator + relativeLocation.segment(1); // package name
		} else {
			return relativeLocation.segment(0); // package name
		}

	}

	/**
	 * Creates a URI that points to the location of a project with the given {@code projectName} in the given
	 * {@code workspaceLocation}.
	 */
	private URI createProjectLocation(URI workspaceLocation, String projectName) {
		if (workspaceLocation.hasTrailingPathSeparator()) {
			// remove last segment
			workspaceLocation = workspaceLocation.trimSegments(1);
		}
		return workspaceLocation.appendSegments(URI.createURI(projectName).segments());
	}

	/** Prints the given results to the npm logger */
	protected void printRegisterResults(RegisterResult rr, String jobName) {
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
}
