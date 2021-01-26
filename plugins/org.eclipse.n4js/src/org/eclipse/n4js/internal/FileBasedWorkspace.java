/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.DependencyType;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectDescriptionFactory;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@SuppressWarnings("javadoc")
@Singleton
public class FileBasedWorkspace extends InternalN4JSWorkspace<FileURI> {

	/** container-prefix for file-based projects */
	public final static String N4FBPRJ = "n4fbprj:";

	private final Map<FileURI, ProjectDescription> projectDescriptions = Maps.newLinkedHashMap();
	private final Map<N4JSProjectName, FileURI> nameToLocation = Maps.newConcurrentMap();
	private final BiMap<N4JSProjectName, N4JSProjectName> definitionProjects = HashBiMap.create();
	private final Multimap<N4JSProjectName, N4JSProjectName> reversedDependencies = HashMultimap.create();

	private final ProjectDescriptionLoader projectDescriptionLoader;

	private final UriExtensions uriExtensions;

	@Inject
	public FileBasedWorkspace(ProjectDescriptionLoader projectDescriptionLoader, UriExtensions uriExtensions) {
		this.projectDescriptionLoader = projectDescriptionLoader;
		this.uriExtensions = uriExtensions;
	}

	@Override
	public FileURI fromURI(URI unsafe) {
		return safeFromURI(unsafe);
	}

	private FileURI safeFromURI(URI uri) {
		return new FileURI(uriExtensions.withEmptyAuthority(uri));
	}

	/**
	 *
	 * @param pLocation
	 *            project directory containing package.json directly
	 */
	synchronized public void registerProject(FileURI pLocation) {
		if (isRegistered(pLocation)) {
			return;
		}
		ProjectDescription pDescription = projectDescriptionLoader.loadProjectDescriptionAtLocation(pLocation);
		if (pDescription == null) {
			return;
		}
		registerProject(pLocation, pDescription);
	}

	synchronized public void registerProject(FileURI pLocation, ProjectDescription pDescription) {
		if (isRegistered(pLocation)) {
			return;
		}
		if (pDescription == null) {
			return;
		}

		N4JSProjectName projectName = pLocation.getProjectName();
		projectDescriptions.put(pLocation, pDescription);
		nameToLocation.putIfAbsent(projectName, pLocation);

		if (!pDescription.isHasN4JSNature()) {
			return;
		}

		String definesPackageString = projectDescriptions.get(pLocation).getDefinesPackage();
		if (!Strings.isNullOrEmpty(definesPackageString)) {
			definitionProjects.putIfAbsent(projectName, new N4JSProjectName(definesPackageString));
		}

		for (ProjectDependency dependency : pDescription.getProjectDependencies()) {
			N4JSProjectName dependencyName = new N4JSProjectName(dependency.getProjectName());
			reversedDependencies.put(dependencyName, new N4JSProjectName(pDescription.getProjectName()));
		}

		addImplicitTypeDefinitionDependencies(pDescription);

		if (!Strings.isNullOrEmpty(definesPackageString)) {
			N4JSProjectName definesPackageName = new N4JSProjectName(definesPackageString);
			for (N4JSProjectName dependingProjectName : reversedDependencies.get(definesPackageName)) {
				FileURI dependingProjectLocation = nameToLocation.get(dependingProjectName);
				if (dependingProjectLocation != null) {
					addImplicitTypeDefinitionDependencies(projectDescriptions.get(dependingProjectLocation));
				}
			}
		}
	}

	synchronized private void addImplicitTypeDefinitionDependencies(ProjectDescription pDescr) {
		Set<String> implicitDependencies = new LinkedHashSet<>();
		Set<String> existingDependencies = new LinkedHashSet<>();
		List<ProjectDependency> moveToTop = new ArrayList<>();

		N4JSProjectName pName = new N4JSProjectName(pDescr.getProjectName());
		boolean sawDefinitionsOnly = true;
		for (ProjectDependency dependency : pDescr.getProjectDependencies()) {
			N4JSProjectName dependencyName = new N4JSProjectName(dependency.getProjectName());
			existingDependencies.add(dependency.getProjectName());
			if (definitionProjects.inverse().containsKey(dependencyName)) {
				N4JSProjectName definitionProjectName = definitionProjects.inverse().get(dependencyName);
				implicitDependencies.add(definitionProjectName.getRawName());
			}

			sawDefinitionsOnly &= dependencyName.isScopeN4jsd();
			if (!sawDefinitionsOnly && dependencyName.isScopeN4jsd()) {
				moveToTop.add(0, dependency); // add at index 0 to keep order. note below move(0, ...);
			}
		}
		implicitDependencies.removeAll(existingDependencies);
		for (String implicitDependencyString : implicitDependencies) {
			ProjectDependency implicitDependency = ProjectDescriptionFactory.eINSTANCE.createProjectDependency();
			implicitDependency.setProjectName(implicitDependencyString);
			implicitDependency.setType(DependencyType.IMPLICIT);
			implicitDependency.setVersionRequirementString("");
			implicitDependency.setVersionRequirement(SemverUtils.createEmptyVersionRequirement());
			pDescr.getProjectDependencies().add(0, implicitDependency);
			reversedDependencies.put(new N4JSProjectName(implicitDependencyString), pName);
		}
		for (ProjectDependency moveToTopDep : moveToTop) {
			pDescr.getProjectDependencies().move(0, moveToTopDep);
		}
	}

	@Override
	synchronized public FileURI getProjectLocation(N4JSProjectName name) {
		return nameToLocation.get(name);
	}

	/** Remove all entries from this workspace. */
	synchronized public void clear() {
		projectDescriptions.clear();
		nameToLocation.clear();
		definitionProjects.clear();
		reversedDependencies.clear();
	}

	/** @return true iff the project at the given location was registered before */
	synchronized public boolean isRegistered(FileURI location) {
		return projectDescriptions.containsKey(location);
	}

	/** Deregisters the project at the given location */
	synchronized public void deregister(FileURI location) {
		ProjectDescription pDescr = projectDescriptions.remove(location);
		String prjNameString = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
		N4JSProjectName prjName = new N4JSProjectName(prjNameString);
		nameToLocation.remove(prjName);
		definitionProjects.remove(prjName);
		for (ProjectDependency dependency : pDescr.getProjectDependencies()) {
			N4JSProjectName dependencyProjectName = new N4JSProjectName(dependency.getProjectName());
			reversedDependencies.remove(dependencyProjectName, prjName);
		}
	}

	/** Deregisters all projects */
	public void deregisterAll() {
		clear();
	}

	@Override
	synchronized public FileURI findProjectWith(FileURI nestedLocation) {
		FileURI key = nestedLocation.trimFragment();

		// determine longest registered project location, that is a prefix of 'key'
		do {
			ProjectDescription match = this.projectDescriptions.get(key);
			if (match != null) {
				return key;
			}
			key = key.getParent();
		} while (key != null);

		return null;
	}

	@Override
	synchronized public ProjectDescription getProjectDescription(FileURI location) {
		// URI location = URIUtils.normalize(unsafeLocation);
		ProjectDescription pDescr = projectDescriptions.get(location);
		if (pDescr == null) {
			return null;
		}

		return pDescr;
	}

	@Override
	public void invalidateProject(FileURI location) {
		if (isRegistered(location)) {
			deregister(location);
			registerProject(location);
		}
	}

	public Iterator<FileURI> getAllProjectLocationsIterator() {
		return getAllProjectLocations().iterator();
	}

	@Override
	synchronized public List<FileURI> getAllProjectLocations() {
		return new ArrayList<>(projectDescriptions.keySet());
	}

	@Override
	synchronized public FileURI getLocation(ProjectReference projectReference) {
		String projectName = projectReference.getProjectName();
		for (FileURI siblingProject : projectDescriptions.keySet()) {
			String candidateProjectName = siblingProject.getProjectName().getRawName();
			if (candidateProjectName.equals(projectName)) {
				ProjectDescription lazyHandle = projectDescriptions.get(siblingProject);
				if (lazyHandle != null) {
					return siblingProject;
				}
			}
		}
		return null;
	}

	@Override
	public Iterator<? extends FileURI> getFolderIterator(FileURI folderLocation) {
		return Iterators.filter(folderLocation.getAllChildren(), loc -> !loc.isDirectory());
	}

	@Override
	public FileURI findArtifactInFolder(FileURI folder, String relativePath) {
		FileURI result = folder.appendPath(relativePath);
		if (result != null && result.exists()) {
			return result;
		}
		return null;
	}

	/**
	 * Convert container-handle to URI
	 *
	 * @see #handleFrom(URI)
	 * @param handle
	 *            "n4fbprj:"-prefixed uri
	 * @return the uri-part of the handle as uri
	 */
	public static URI uriFrom(String handle) {
		if (handle.startsWith(N4FBPRJ)) {
			return URI.createURI(handle.substring(N4FBPRJ.length()));
		}
		return null;
	}

	/**
	 * Create container-handle form uri.
	 *
	 * @see #uriFrom(String)
	 * @param uri
	 *            URI denoting a project
	 * @return string-representation of uri prefixed with "n4fbprj:"
	 */
	public static String handleFrom(URI uri) {
		return N4FBPRJ + uri.toString();
	}

}
