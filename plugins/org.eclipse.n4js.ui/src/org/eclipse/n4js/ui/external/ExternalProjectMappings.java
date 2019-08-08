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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
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
	final Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> completeCache;
	final List<Pair<FileURI, ProjectDescription>> completeList;
	final Map<N4JSProjectName, List<N4JSExternalProject>> completeProjectNameMapping;
	final Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMapping;
	final Map<FileURI, List<N4JSExternalProject>> reducedProjectsLocationMapping;
	final Set<N4JSExternalProject> reducedSet;
	final boolean initialized;

	ExternalProjectMappings(EclipseBasedN4JSWorkspace userWorkspace,
			ExternalLibraryPreferenceStore preferenceStore,
			Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> completeCache) {

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
			Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> completeCache, boolean initialized) {

		this.userWorkspace = userWorkspace;
		this.preferenceStore = preferenceStore;

		this.completeCache = completeCache != null ? new LinkedHashMap<>(completeCache) : Collections.emptyMap();
		removeDuplicatesFromCompleteCache();

		Mappings mappings = computeMappings();
		this.completeList = mappings.completeList;
		this.completeProjectNameMapping = mappings.completeProjectNameMapping;
		this.reducedProjectUriMapping = mappings.reducedProjectUriMapping;
		this.reducedProjectsLocationMapping = mappings.reducedProjectsLocationMapping;
		this.reducedSet = mappings.reducedSet;
		this.initialized = initialized;
	}

	/**
	 * Removes "duplicate projects" from field {@link #completeCache}.
	 *
	 * A "duplicate project" in this sense is an external project that has the identical location on disk as a workspace
	 * project. Duplicates will occur if a symbolic link in a node_modules folder points to the same location on disk as
	 * a project imported into the Eclipse workspace. This typically occurs in the global node_modules folder of a yarn
	 * workspace, where symbolic links point to the packages contained in the workspace; if and only if these packages
	 * are imported into Eclipse, then this method will remove them from field {@link #completeCache}.
	 */
	private void removeDuplicatesFromCompleteCache() {
		if (completeCache.isEmpty()) {
			return;
		}

		// prepare list of locations of all projects in workspace
		// (note: also include locations of close projects!)
		Set<PlatformResourceURI> locationsOfWorkspaceProjects = new HashSet<>(userWorkspace.getAllProjectLocations());
		Map<N4JSProjectName, FileURI> workspaceAsFileURIs = new HashMap<>();
		for (PlatformResourceURI wsProject : locationsOfWorkspaceProjects) {
			workspaceAsFileURIs.put(wsProject.getProjectName(), wsProject.toFileURI());
		}
		List<FileURI> allPrjLocs = new LinkedList<>(completeCache.keySet());
		for (FileURI projectLocation : allPrjLocs) {
			FileURI fromWorkspace = workspaceAsFileURIs.get(projectLocation.getProjectName());
			if (fromWorkspace != null) {
				if (projectLocation.equals(fromWorkspace)) {
					completeCache.remove(projectLocation);
				} else {
					try {
						FileURI canonicalLocation = projectLocation.resolveSymLinks();
						if (canonicalLocation.equals(fromWorkspace)) {
							completeCache.remove(projectLocation);
						}
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}
	}

	private static class Mappings {
		List<Pair<FileURI, ProjectDescription>> completeList;
		Map<N4JSProjectName, List<N4JSExternalProject>> completeProjectNameMapping;
		Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMapping;
		Map<FileURI, List<N4JSExternalProject>> reducedProjectsLocationMapping;
		Set<N4JSExternalProject> reducedSet;
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that some bundles/classes are not initialized
	 * yet, hence not available when injecting this instance.
	 */
	private Mappings computeMappings() {
		Map<N4JSProjectName, List<N4JSExternalProject>> completeProjectNameMappingTmp = newHashMap();
		Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMappingTmp = newHashMap();
		Map<FileURI, List<N4JSExternalProject>> reducedProjectsLocationMappingTmp = newHashMap();
		List<Pair<FileURI, ProjectDescription>> completeListTmp = new LinkedList<>();
		if (completeCache.isEmpty()) {
			Mappings mappings = new Mappings();
			mappings.completeList = Collections.emptyList();
			mappings.completeProjectNameMapping = Collections.emptyMap();
			mappings.reducedSet = Collections.emptySet();
			mappings.reducedProjectsLocationMapping = Collections.emptyMap();
			mappings.reducedProjectUriMapping = Collections.emptyMap();
			return mappings;
		}

		// step 1: compute all projects
		List<FileURI> allPrjLocsReversed = new LinkedList<>(completeCache.keySet());
		// Collections.reverse(allPrjLocsReversed);
		for (FileURI projectLocation : allPrjLocsReversed) {
			Pair<N4JSExternalProject, ProjectDescription> pair = completeCache.get(projectLocation);
			N4JSExternalProject project = pair.getFirst();
			ProjectDescription prjDescr = pair.getSecond();
			completeListTmp.add(Tuples.pair(projectLocation, prjDescr));

			// shadowing is done here by checking if an npm is already in the mapping
			N4JSProjectName projectName = projectLocation.getProjectName();
			if (!completeProjectNameMappingTmp.containsKey(projectName)) {

				completeProjectNameMappingTmp.put(projectName, Lists.newArrayList(project));
				reducedProjectUriMappingTmp.put(projectLocation, pair);

				FileURI rootLoc = ExternalLibraryWorkspace
						.getRootLocationForResource(preferenceStore.getLocations(), projectLocation);

				reducedProjectsLocationMappingTmp.putIfAbsent(rootLoc, new LinkedList<>());
				reducedProjectsLocationMappingTmp.get(rootLoc).add(project);
			} else {
				List<N4JSExternalProject> list = completeProjectNameMappingTmp.get(projectName);
				list.add(project);
			}
		}

		// step 2: compute necessary projects
		Set<SafeURI<?>> reducedSetURIs = computeUserWorkspaceDependencies(completeProjectNameMappingTmp,
				reducedProjectUriMappingTmp);
		Set<N4JSExternalProject> reducedSetTmps = new HashSet<>();
		for (SafeURI<?> prjLoc : reducedSetURIs) {
			Pair<N4JSExternalProject, ProjectDescription> pair = reducedProjectUriMappingTmp.get(prjLoc);
			if (pair != null) {
				N4JSExternalProject project = pair.getFirst();
				reducedSetTmps.add(project);
			}
		}

		// step 3: reduce to necessary projects
		if (REDUCE_REGISTERED_NPMS) {
			for (FileURI nodeModuleLocation : preferenceStore.getNodeModulesLocations()) {
				List<N4JSExternalProject> nodeModuleProjects = reducedProjectsLocationMappingTmp
						.get(nodeModuleLocation);

				if (nodeModuleProjects != null) {
					for (Iterator<N4JSExternalProject> iter = nodeModuleProjects.iterator(); iter.hasNext();) {
						FileURI location = iter.next().getSafeLocation();
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

	Set<SafeURI<?>> computeUserWorkspaceDependencies(
			Map<N4JSProjectName, List<N4JSExternalProject>> completeProjectNameMappingTmp,
			Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> reducedProjectUriMappingTmp) {

		Set<SafeURI<?>> uwsDeps = new HashSet<>();
		// respect closed workspace projects by omitting them
		Collection<SafeURI<?>> projectsInUserWSopen = new LinkedList<>();
		for (PlatformResourceURI projectInUserWS : userWorkspace.getAllProjectLocations()) {
			projectsInUserWSopen.add(projectInUserWS);
		}

		computeNecessaryDependenciesRek(completeProjectNameMappingTmp, reducedProjectUriMappingTmp,
				projectsInUserWSopen, uwsDeps);
		uwsDeps.removeAll(projectsInUserWSopen);

		return uwsDeps;
	}

	private void computeNecessaryDependenciesRek(Map<N4JSProjectName, List<N4JSExternalProject>> projectNameMappingTmp,
			Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp,
			Collection<SafeURI<?>> locs,
			Set<SafeURI<?>> necessaryDeps) {

		Set<SafeURI<?>> depUris = new HashSet<>();
		for (SafeURI<?> loc : locs) {
			ProjectDescription pd = getProjectDescription(projectUriMappingTmp, loc);
			if (pd != null && pd.getProjectType() != ProjectType.PLAINJS) {
				for (ProjectDependency pDep : pd.getProjectDependencies()) {
					SafeURI<?> depLoc = getProjectLocation(projectNameMappingTmp, pDep);

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

	private SafeURI<?> getProjectLocation(Map<N4JSProjectName, List<N4JSExternalProject>> projectNameMappingTmp,
			ProjectDependency pDep) {

		N4JSProjectName projectName = new N4JSProjectName(pDep.getProjectName());
		SafeURI<?> depLoc = userWorkspace.getProjectLocation(projectName);
		if (depLoc == null) {
			List<N4JSExternalProject> prjsOfName = projectNameMappingTmp.get(projectName);
			N4JSExternalProject project = (prjsOfName == null || prjsOfName.isEmpty()) ? null : prjsOfName.get(0);
			if (project != null) {
				depLoc = project.getSafeLocation();
			}
		}
		return depLoc;
	}

	private ProjectDescription getProjectDescription(
			Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp,
			SafeURI<?> loc) {

		ProjectDescription pd = loc instanceof PlatformResourceURI
				? userWorkspace.getProjectDescription((PlatformResourceURI) loc)
				: null;
		if (pd == null) {
			Pair<N4JSExternalProject, ProjectDescription> pair = projectUriMappingTmp.get(loc);
			if (pair != null) {
				pd = pair.getSecond();
			}
		}
		return pd;
	}

}
