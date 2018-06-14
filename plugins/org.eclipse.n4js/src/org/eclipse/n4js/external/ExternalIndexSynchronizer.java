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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.packagejson.PackageJsonResourceDescriptionExtension;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
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

	@Inject
	private IN4JSCore core;

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

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

	/**
	 * This method compares the external projects located in all external locations to the projects available through
	 * {@link ExternalLibraryWorkspace} or {@link IN4JSCore}.
	 * <p>
	 * Note that this method will iterate through the complete Xtext index.
	 *
	 * @return true iff exactly those external projects in external locations are available through
	 *         {@link ExternalLibraryWorkspace} or {@link IN4JSCore}.
	 */
	final public boolean isProjectsSynchronized() {
		Collection<LibraryChange> changeSet = identifyChangeSet(Collections.emptyList());
		return changeSet.isEmpty();
	}

	/**
	 * @return a map that maps projectIDs to their locations and versions
	 */
	final public Map<String, Pair<URI, String>> findNpmsInFolder() {
		Map<String, Pair<URI, String>> npmsFolder = new HashMap<>();

		java.net.URI nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation();
		File nodeModulesFolder = new File(nodeModulesLocation.getPath());
		if (nodeModulesFolder.isDirectory()) {
			for (File npmLibrary : nodeModulesFolder.listFiles()) {
				if (ExternalLibraryUtils.isExternalProjectDirectory(npmLibrary)) {
					String npmName = npmLibrary.getName();
					String version = getVersionFromPackageJSON(npmLibrary);
					if (version != null) {
						String path = npmLibrary.getAbsolutePath();
						URI location = URI.createFileURI(path);
						npmsFolder.put(npmName, Pair.of(location, version));
					}
				}
			}
		}

		return npmsFolder;
	}

	private String getVersionFromPackageJSON(File packageJSON) {
		URI uri = URI.createFileURI(packageJSON.getAbsolutePath());
		ProjectDescription pDescr = projectDescriptionHelper.loadProjectDescriptionAtLocation(uri);
		if (pDescr != null) {
			DeclaredVersion pV = pDescr.getProjectVersion();
			String version = pV.getMajor() + "." + pV.getMinor() + "." + pV.getMicro();
			if (pV.getQualifier() != null) {
				version += ":" + pV.getQualifier();
			}
			return version;
		}

		return null;
	}

	/** @return a set of all changes between the Xtext index and the external projects in all external locations */
	final protected Collection<LibraryChange> identifyChangeSet(Collection<LibraryChange> forcedChangeSet) {
		Collection<LibraryChange> changes = new LinkedHashSet<>(forcedChangeSet);

		Map<String, Pair<URI, String>> npmsOfIndex = findNpmsInIndex();
		Map<String, Pair<URI, String>> npmsOfFolder = findNpmsInFolder();

		Set<String> differences = new HashSet<>();
		differences.addAll(npmsOfIndex.keySet());
		differences.addAll(npmsOfFolder.keySet());
		SetView<String> intersection = Sets.intersection(npmsOfIndex.keySet(), npmsOfFolder.keySet());
		differences.removeAll(intersection);

		for (String diff : differences) {
			LibraryChangeType changeType = null;
			String name = diff;
			URI location = null;
			String version = null;

			if (npmsOfFolder.containsKey(diff)) {
				// new in folder, not in index
				changeType = LibraryChangeType.Added;
				location = npmsOfFolder.get(name).getKey();
				version = npmsOfFolder.get(name).getValue();
			}
			if (npmsOfIndex.containsKey(diff)) {
				// removed in folder, still in index
				changeType = LibraryChangeType.Removed;
				location = npmsOfIndex.get(name).getKey();
				version = npmsOfIndex.get(name).getValue();
			}
			changes.add(new LibraryChange(changeType, location, name, version));
		}

		for (String name : npmsOfIndex.keySet()) {
			if (npmsOfFolder.containsKey(name)) {
				String versionIndex = npmsOfIndex.get(name).getValue();
				String versionFolder = npmsOfFolder.get(name).getValue();
				URI locationIndex = npmsOfIndex.get(name).getKey();
				URI locationFolder = npmsOfFolder.get(name).getKey();

				Preconditions.checkState(locationFolder.equals(locationIndex));

				if (versionIndex != null && !versionIndex.equals(versionFolder)) {
					changes.add(new LibraryChange(LibraryChangeType.Updated, locationFolder, name, versionFolder));
				}
			}
		}

		return changes;
	}

	private Map<String, Pair<URI, String>> findNpmsInIndex() {
		Map<String, Pair<URI, String>> npmsIndex = new HashMap<>();

		String nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation().toString();
		ResourceSet resourceSet = core.createResourceSet(Optional.absent());
		IResourceDescriptions index = core.getXtextIndex(resourceSet);

		for (IResourceDescription res : index.getAllResourceDescriptions()) {
			String resLocation = res.getURI().toString();
			boolean isNPM = resLocation.startsWith(nodeModulesLocation);

			if (isNPM) {
				addToIndex(npmsIndex, nodeModulesLocation, res, resLocation);
			}
		}

		return npmsIndex;
	}

	private void addToIndex(Map<String, Pair<URI, String>> npmsIndex,
			String nodeModulesLocation, IResourceDescription res, String resLocation) {

		String version = "";
		int endOfProjectFolder = resLocation.indexOf(File.separator, nodeModulesLocation.length() + 1);
		String locationString = resLocation.substring(0, endOfProjectFolder);
		URI location = URI.createURI(locationString);
		String name = locationString.substring(nodeModulesLocation.length());

		boolean isProjectDescriptionFile = true;
		isProjectDescriptionFile &= resLocation.endsWith(IN4JSProject.PACKAGE_JSON);
		isProjectDescriptionFile &= resLocation.substring(nodeModulesLocation.length())
				.split(File.separator).length == 2;

		if (isProjectDescriptionFile) {
			Iterable<IEObjectDescription> pds = res.getExportedObjectsByType(JSONPackage.eINSTANCE.getJSONDocument());
			IEObjectDescription pDescription = pds.iterator().next();
			String nameFromPackageJSON = pDescription
					.getUserData(PackageJsonResourceDescriptionExtension.PROJECT_ID_KEY);
			if (!name.equals(nameFromPackageJSON)) {
				throw new IllegalStateException(
						"name mismatch: name=" + name + "; nameFromPackageJSON=" + nameFromPackageJSON);
			}

			version = pDescription.getUserData(PackageJsonResourceDescriptionExtension.PROJECT_VERSION_KEY);
		}

		if (!npmsIndex.containsKey(name) || isProjectDescriptionFile) {
			npmsIndex.put(name, Pair.of(location, version));
		}
	}

}
