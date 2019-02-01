/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.external;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * This provider creates {@link ExternalProject}s.
 */
public class ExternalProjectMappings {
	/** True: Only compile those projects that are referenced by N4JS projects */
	public static boolean REDUCE_REGISTERED_NPMS = true;

	final private EclipseBasedN4JSWorkspace userWorkspace;
	final private ExternalLibraryPreferenceStore preferenceStore;

	/*
	 * The following collections either contain all npms in the external locations or a reduced set of them. The reduced
	 * set does neither contain shadowed nor unnecessary projects. An unnecessary project is a dependency of one (or
	 * more) plain-JS projects.
	 */
	final Map<URI, Pair<N4JSExternalProject, ProjectDescription>> completeCache;
	final List<Pair<URI, ProjectDescription>> completeList;
	final Map<String, List<N4JSExternalProject>> completeProjectNameMapping;
	final Map<URI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMapping;
	final Map<java.net.URI, List<N4JSExternalProject>> reducedProjectsLocationMapping;
	final Set<N4JSExternalProject> reducedSet;
	final boolean initialized;

	ExternalProjectMappings(EclipseBasedN4JSWorkspace userWorkspace,
			ExternalLibraryPreferenceStore preferenceStore,
			Map<URI, Pair<N4JSExternalProject, ProjectDescription>> completeCache) {

		this(userWorkspace, preferenceStore, completeCache, true);
	}

	/**
	 * Creates a new external library workspace instance with the preference store that provides the configured library
	 * location.
	 *
	 * @param preferenceStore
	 *            the preference store to get the registered external library locations.
	 */
	protected ExternalProjectMappings(EclipseBasedN4JSWorkspace userWorkspace,
			ExternalLibraryPreferenceStore preferenceStore,
			Map<URI, Pair<N4JSExternalProject, ProjectDescription>> completeCache, boolean initialized) {

		this.userWorkspace = userWorkspace;
		this.preferenceStore = preferenceStore;

		this.completeCache = completeCache;
		Mappings mappings = computeMappings();
		this.completeList = mappings.completeList;
		this.completeProjectNameMapping = mappings.completeProjectNameMapping;
		this.reducedProjectUriMapping = mappings.reducedProjectUriMapping;
		this.reducedProjectsLocationMapping = mappings.reducedProjectsLocationMapping;
		this.reducedSet = mappings.reducedSet;
		this.initialized = initialized;
	}

	private static class Mappings {
		List<Pair<URI, ProjectDescription>> completeList;
		Map<String, List<N4JSExternalProject>> completeProjectNameMapping;
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMapping;
		Map<java.net.URI, List<N4JSExternalProject>> reducedProjectsLocationMapping;
		Set<N4JSExternalProject> reducedSet;
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that some bundles/classes are not initialized
	 * yet, hence not available when injecting this instance.
	 */
	private Mappings computeMappings() {
		Map<String, List<N4JSExternalProject>> completeProjectNameMappingTmp = newHashMap();
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMappingTmp = newHashMap();
		Map<java.net.URI, List<N4JSExternalProject>> reducedProjectsLocationMappingTmp = newHashMap();
		List<Pair<URI, ProjectDescription>> completeListTmp = new LinkedList<>();
		if (completeCache == null) {
			Mappings mappings = new Mappings();
			mappings.completeList = Collections.emptyList();
			mappings.completeProjectNameMapping = Collections.emptyMap();
			mappings.reducedSet = Collections.emptySet();
			mappings.reducedProjectsLocationMapping = Collections.emptyMap();
			mappings.reducedProjectUriMapping = Collections.emptyMap();
			return mappings;
		}

		// step 1: compute all projects
		List<URI> allPrjLocsReversed = new LinkedList<>(completeCache.keySet());
		// Collections.reverse(allPrjLocsReversed);
		for (URI projectLocation : allPrjLocsReversed) {
			Pair<N4JSExternalProject, ProjectDescription> pair = completeCache.get(projectLocation);
			N4JSExternalProject project = pair.getFirst();
			ProjectDescription prjDescr = pair.getSecond();
			completeListTmp.add(Tuples.pair(projectLocation, prjDescr));

			// shadowing is done here by checking if an npm is already in the mapping
			String projectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(projectLocation);
			if (!completeProjectNameMappingTmp.containsKey(projectName)) {

				completeProjectNameMappingTmp.put(projectName, Lists.newArrayList(project));
				reducedProjectUriMappingTmp.put(projectLocation, pair);

				java.net.URI rootLoc = ExternalLibraryWorkspace
						.getRootLocationForResource(preferenceStore.getLocations(), projectLocation);

				reducedProjectsLocationMappingTmp.putIfAbsent(rootLoc, new LinkedList<>());
				reducedProjectsLocationMappingTmp.get(rootLoc).add(project);
			} else {
				List<N4JSExternalProject> list = completeProjectNameMappingTmp.get(projectName);
				list.add(project);
			}
		}

		// step 2: compute necessary projects
		Set<URI> reducedSetURIs = computeUserWorkspaceDependencies(completeProjectNameMappingTmp,
				reducedProjectsLocationMappingTmp, reducedProjectUriMappingTmp);
		Set<N4JSExternalProject> reducedSetTmps = new HashSet<>();
		for (URI prjLoc : reducedSetURIs) {
			Pair<N4JSExternalProject, ProjectDescription> pair = reducedProjectUriMappingTmp.get(prjLoc);
			if (pair != null) {
				N4JSExternalProject project = pair.getFirst();
				reducedSetTmps.add(project);
			}
		}

		// step 3: reduce to necessary projects
		if (REDUCE_REGISTERED_NPMS) {
			for (java.net.URI nodeModuleLocation : preferenceStore.getNodeModulesLocations()) {
				List<N4JSExternalProject> nodeModuleProjects = reducedProjectsLocationMappingTmp
						.get(nodeModuleLocation);

				if (nodeModuleProjects != null) {
					for (Iterator<N4JSExternalProject> iter = nodeModuleProjects.iterator(); iter.hasNext();) {
						URI location = iter.next().getIProject().getLocation();
						if (!reducedSetURIs.contains(location)) {
							iter.remove();
						}
					}
				}
			}
			reducedProjectUriMappingTmp.keySet().retainAll(reducedSetURIs);
			Preconditions.checkState(reducedSetURIs.size() == reducedProjectUriMappingTmp.size());
		} else {
			for (List<N4JSExternalProject> rlPrjs : reducedProjectsLocationMappingTmp.values()) {
				reducedSetTmps.addAll(rlPrjs);
			}
		}

		// step 4: seal collections
		Mappings mappings = new Mappings();
		mappings.completeList = Collections.unmodifiableList(completeListTmp);
		mappings.completeProjectNameMapping = Collections.unmodifiableMap(completeProjectNameMappingTmp);
		mappings.reducedSet = Collections.unmodifiableSet(reducedSetTmps);
		mappings.reducedProjectsLocationMapping = Collections.unmodifiableMap(reducedProjectsLocationMappingTmp);
		mappings.reducedProjectUriMapping = Collections.unmodifiableMap(reducedProjectUriMappingTmp);
		return mappings;
	}

	Set<URI> computeUserWorkspaceDependencies(
			Map<String, List<N4JSExternalProject>> completeProjectNameMappingTmp,
			Map<java.net.URI, List<N4JSExternalProject>> reducedProjectsLocationMappingTmp,
			Map<URI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMappingTmp) {

		Set<URI> uwsDeps = new HashSet<>();
		// respect closed workspace projects by omitting them
		Collection<URI> projectsInUserWSopen = new LinkedList<>();
		for (URI projectInUserWS : userWorkspace.getAllProjectLocations()) {
			String locStr = projectInUserWS.toPlatformString(true);
			IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(locStr);
			if (iProject != null && iProject.isAccessible()) {
				projectsInUserWSopen.add(projectInUserWS);
			}
		}

		computeNecessaryDependenciesRek(completeProjectNameMappingTmp, reducedProjectUriMappingTmp,
				projectsInUserWSopen, uwsDeps);
		uwsDeps.removeAll(projectsInUserWSopen);

		Set<String> depNames = new HashSet<>();
		for (URI uwsDep : uwsDeps) {
			String name = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(uwsDep);
			depNames.add(name);
		}

		// add all shipped npms to user necessary dependencies
		for (java.net.URI location : reducedProjectsLocationMappingTmp.keySet()) {
			String locationStr = location.toString();
			if (locationStr.endsWith("/")) {
				locationStr = locationStr.substring(0, locationStr.length() - 1);
			}
			int startLastSegment = locationStr.lastIndexOf("/");
			String locationName = locationStr.substring(startLastSegment + 1);
			if (ExternalLibrariesActivator.SHIPPED_ROOTS_FOLDER_NAMES.contains(locationName)) {
				List<N4JSExternalProject> list = reducedProjectsLocationMappingTmp.get(location);
				for (N4JSExternalProject n4prj : list) {
					IN4JSProject iProject = n4prj.getIProject();
					String projectName = iProject.getProjectName();
					if (!depNames.contains(projectName)) {
						uwsDeps.add(iProject.getLocation());
					}
				}
			}
		}

		return uwsDeps;
	}

	private void computeNecessaryDependenciesRek(Map<String, List<N4JSExternalProject>> projectNameMappingTmp,
			Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp, Collection<URI> locs,
			Set<URI> necessaryDeps) {

		Set<URI> depUris = new HashSet<>();
		for (URI loc : locs) {
			ProjectDescription pd = getProjectDescription(projectUriMappingTmp, loc);
			if (pd != null && pd.getProjectType() != ProjectType.PLAINJS) {
				for (ProjectDependency pDep : pd.getProjectDependencies()) {
					URI depLoc = getProjectLocation(projectNameMappingTmp, pDep);

					if (depLoc != null && !necessaryDeps.contains(depLoc)) {
						depUris.add(depLoc);
						necessaryDeps.add(depLoc);
					}
				}

			}
		}
		if (!depUris.isEmpty()) {
			computeNecessaryDependenciesRek(projectNameMappingTmp, projectUriMappingTmp, depUris, necessaryDeps);
		}
	}

	private URI getProjectLocation(Map<String, List<N4JSExternalProject>> projectNameMappingTmp,
			ProjectDependency pDep) {

		String projectName = pDep.getProjectName();
		URI depLoc = userWorkspace.findProjectForName(projectName);
		if (depLoc != null) {
			// respect closed workspace projects by omitting them
			String locStr = depLoc.toPlatformString(true);
			IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(locStr);
			if (iProject == null || !iProject.isAccessible()) {
				depLoc = null;
			}
		}
		if (depLoc == null) {
			List<N4JSExternalProject> prjsOfName = projectNameMappingTmp.get(projectName);
			N4JSExternalProject project = (prjsOfName == null || prjsOfName.isEmpty()) ? null : prjsOfName.get(0);
			if (project != null) {
				depLoc = project.getIProject().getLocation();
			}
		}
		return depLoc;
	}

	private ProjectDescription getProjectDescription(
			Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp, URI loc) {

		ProjectDescription pd = userWorkspace.getProjectDescription(loc);
		if (pd == null) {
			Pair<N4JSExternalProject, ProjectDescription> pair = projectUriMappingTmp.get(loc);
			if (pair != null) {
				pd = pair.getSecond();
			}
		}
		return pd;
	}

}
