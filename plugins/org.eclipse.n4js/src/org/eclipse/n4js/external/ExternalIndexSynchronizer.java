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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
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
		if (nodeModulesFolder.exists() && nodeModulesFolder.isDirectory()) {
			for (File npmLibrary : nodeModulesFolder.listFiles()) {
				if (npmLibrary.isDirectory()) {
					String npmName = npmLibrary.getName();
					File manifest = npmLibrary.toPath().resolve(IN4JSProject.N4MF_MANIFEST).toFile();
					String version = getVersionFromManifest(manifest);
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

	private String getVersionFromManifest(File manifest) {
		ProjectDescription pDescr = getProjectDescription(manifest);
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

	private ProjectDescription getProjectDescription(File manifest) {
		if (!manifest.exists() || !manifest.isFile()) {
			return null;
		}

		ResourceSet resourceSet = core.createResourceSet(Optional.absent());
		String pathStr = manifest.getPath();
		URI manifestURI = URI.createFileURI(pathStr);
		Resource resource = resourceSet.getResource(manifestURI, true);
		if (resource != null) {
			List<EObject> contents = resource.getContents();
			if (contents.isEmpty() || !(contents.get(0) instanceof ProjectDescription)) {
				return null;
			}
			ProjectDescription pDescr = (ProjectDescription) contents.get(0);
			return pDescr;
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
		isProjectDescriptionFile &= resLocation.endsWith(IN4JSProject.N4MF_MANIFEST);
		isProjectDescriptionFile &= resLocation.substring(resLocation.length()).split(File.separator).length == 1;

		if (isProjectDescriptionFile) {
			Iterable<IEObjectDescription> pds = res.getExportedObjectsByType(N4mfPackage.Literals.PROJECT_DESCRIPTION);

			IEObjectDescription pDescription = pds.iterator().next();
			String nameFromManifest = pDescription.getUserData(N4MFResourceDescriptionStrategy.PROJECT_ID_KEY);
			Preconditions.checkState(name.equals(nameFromManifest));

			version = pDescription.getUserData(N4MFResourceDescriptionStrategy.PROJECT_VERSION_KEY);
		}

		if (!npmsIndex.containsKey(name) || isProjectDescriptionFile) {
			npmsIndex.put(name, Pair.of(location, version));
		}
	}

}
