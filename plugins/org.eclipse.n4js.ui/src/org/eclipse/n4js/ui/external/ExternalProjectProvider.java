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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableCollection;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.ExternalProjectCacheLoader;
import org.eclipse.n4js.ui.internal.N4JSEclipseModel;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This provider creates {@link ExternalProject}s.
 */
@Singleton
public class ExternalProjectProvider implements StoreUpdatedListener {

	@Inject
	private ExternalProjectCacheLoader cacheLoader;

	@Inject
	private ProjectStateChangeListener projectStateChangeListener;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private EclipseBasedN4JSWorkspace userWorkspace;

	@Inject
	private N4JSEclipseModel model;

	private final Collection<ExternalLocationsUpdatedListener> locListeners = new LinkedList<>();
	private final Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectCache = new HashMap<>();

	private NavigableMap<String, java.net.URI> rootLocations;
	private Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMapping;
	private Map<String, N4JSExternalProject> projectNameMapping;
	private Map<java.net.URI, List<N4JSExternalProject>> projectsForLocation;
	private Set<URI> necessaryDependencies;

	/**
	 * Creates a new external library workspace instance with the preference store that provides the configured library
	 * location.
	 *
	 * @param preferenceStore
	 *            the preference store to get the registered external library locations.
	 */
	@Inject
	ExternalProjectProvider(ExternalLibraryPreferenceStore preferenceStore) {
		setRootLocations(preferenceStore.getLocations());
		preferenceStore.addListener(this);
	}

	/**
	 * Initializes the backing cache with the cache loader and registers a {@link ProjectStateChangeListener} into the
	 * workspace.
	 */
	@Inject
	void init() {
		if (Platform.isRunning()) {
			getWorkspace().addResourceChangeListener(projectStateChangeListener);
		}
	}

	/** Adds the given listener. Listener gets called after locations of external workspaces changed. */
	public void addExternalLocationsUpdatedListener(ExternalLocationsUpdatedListener listener) {
		locListeners.add(listener);
	}

	Collection<java.net.URI> getRootLocations() {
		return rootLocations.values();
	}

	Collection<URI> getAllProjectLocations() {
		return projectUriMapping.keySet();
	}

	@Override
	public void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor) {
		ensureInitialized();

		Set<java.net.URI> oldLocations = new HashSet<>(getRootLocations());
		Set<java.net.URI> newLocations = new HashSet<>(store.getLocations());

		Set<java.net.URI> removedLocations = new HashSet<>(oldLocations);
		removedLocations.removeAll(newLocations);

		Set<java.net.URI> addedLocations = new HashSet<>(newLocations);
		addedLocations.removeAll(oldLocations);

		for (ExternalLocationsUpdatedListener locListener : locListeners) {
			locListener.beforeLocationsUpdated(removedLocations, monitor);
		}

		updateCache(newLocations);

		for (ExternalLocationsUpdatedListener locListener : locListeners) {
			locListener.afterLocationsUpdated(addedLocations, monitor);
		}
	}

	void ensureInitialized() {
		if (null == projectNameMapping) {
			synchronized (this) {
				if (null == projectNameMapping) {
					updateCache();
				}
			}
		}

		checkNotNull(projectNameMapping, "Eclipse based external library workspace is not initialized yet.");
	}

	private Map<String, N4JSExternalProject> getProjectMapping() {
		ensureInitialized();
		return projectNameMapping;
	}

	private void updateCache(Set<java.net.URI> newLocations) {
		List<java.net.URI> locationsInShadowOrder = ExternalLibrariesActivator.sortByShadowing(newLocations);
		setRootLocations(locationsInShadowOrder);

		updateCache();
	}

	void updateCache() {
		projectCache.clear();

		for (Pair<N4JSExternalProject, ProjectDescription> pair : computeProjectsUncached()) {
			URI locationURI = pair.getFirst().getIProject().getLocation();
			projectCache.put(locationURI, pair);
		}

		updateMappings();
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that some bundles/classes are not initialized
	 * yet, hence not available when injecting this instance.
	 */
	private void updateMappings() {
		// necessaryDependencies = Collections.unmodifiableSet(computeNecessaryDependencies());

		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> cachedProjects = projectCache;
		Map<String, N4JSExternalProject> projectNameMappingTmp = newHashMap();
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp = newHashMap();
		Map<java.net.URI, List<N4JSExternalProject>> projectsForLocationTmp = newHashMap();

		Iterable<java.net.URI> projectRoots = externalLibraryPreferenceStore
				.convertToProjectRootLocations(getRootLocations());

		for (java.net.URI projectRoot : projectRoots) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);
			if (cachedProjects.containsKey(projectLocation)) {
				Pair<N4JSExternalProject, ProjectDescription> pair = cachedProjects.get(projectLocation);
				if (null != pair) {
					N4JSExternalProject project = pair.getFirst();

					// shadowing is done here by checking if an npm is already in the mapping
					if (!projectNameMappingTmp.containsKey(project.getName())) {
						final String projectName = ProjectDescriptionUtils
								.deriveN4JSProjectNameFromURI(projectLocation);

						projectNameMappingTmp.put(projectName, project);
						projectUriMappingTmp.put(projectLocation, pair);

						java.net.URI rootLoc = getRootLocationForResource(projectLocation);
						projectsForLocationTmp.putIfAbsent(rootLoc, new LinkedList<>());
						projectsForLocationTmp.get(rootLoc).add(project);
					}
				}
			}
		}

		projectsForLocation = Collections.unmodifiableMap(projectsForLocationTmp);
		projectNameMapping = Collections.unmodifiableMap(projectNameMappingTmp);
		projectUriMapping = Collections.unmodifiableMap(projectUriMappingTmp);
	}

	Set<URI> computeNecessaryDependencies() {
		Set<URI> necessaryDeps = new HashSet<>();
		computeNecessaryDependenciesRek(projectCache.keySet(), necessaryDeps);
		necessaryDeps.removeAll(userWorkspace.getAllProjectLocations());
		return necessaryDeps;
	}

	private void computeNecessaryDependenciesRek(Collection<URI> locs, Set<URI> necessaryDeps) {
		for (URI loc : locs) {
			N4JSEclipseProject prj = model.getN4JSProject(loc);
			if (prj != null && prj.getProjectType() != ProjectType.PLAINJS) {
				ImmutableList<? extends IN4JSEclipseProject> deps = model.getDependenciesAndImplementedApis(prj, false);

				List<URI> depUris = new LinkedList<>();
				for (IN4JSEclipseProject dep : deps) {
					URI depLocation = dep.getLocation();
					if (!necessaryDeps.contains(depLocation)) {
						depUris.add(depLocation);
						necessaryDeps.add(depLocation);
					}
				}
				computeNecessaryDependenciesRek(depUris, necessaryDeps);
			}
		}
	}

	Set<URI> getNecessaryDependencies() {
		ensureInitialized();
		return necessaryDependencies;
	}

	java.net.URI getRootLocationForResource(URI nestedLocation) {
		if (nestedLocation == null || nestedLocation.isEmpty() || !nestedLocation.isFile()) {
			return null;
		}

		String nestedLocStr = nestedLocation.toString();
		String rootLocStr = rootLocations.floorKey(nestedLocStr);
		if (rootLocStr != null) {
			return rootLocations.get(rootLocStr);
		}
		return null;
	}

	/**
	 * Like {@link IN4JSCore#findProject(URI)}, but returns the URI of the containing project. This method is
	 * performance critical because it is called often!
	 */
	URI findProjectWith(URI nestedLocation) {
		ensureInitialized();
		java.net.URI rootLoc = getRootLocationForResource(nestedLocation);
		String rootLocStr = rootLoc.toString();

		if (rootLocStr != null) {
			URI loc = URI.createURI(rootLocStr);
			URI prefix = !loc.hasTrailingPathSeparator() ? loc.appendSegment("") : loc;
			int oldSegmentCount = nestedLocation.segmentCount();
			int newSegmentCount = prefix.segmentCount()
					- 1 // -1 because of the trailing empty segment
					+ 1; // +1 to include the project folder
			if (newSegmentCount - 1 >= oldSegmentCount) {
				return null; // can happen if the URI of an external library location is passed in
			}
			String projectNameCandidate = nestedLocation.segment(newSegmentCount - 1);
			if (projectNameCandidate.startsWith("@")) {
				// last segment is a folder representing an npm scope, not a project folder
				// --> add 1 to include the actual project folder
				++newSegmentCount;
			}
			URI uriCandidate = nestedLocation.trimSegments(oldSegmentCount - newSegmentCount)
					.trimFragment();
			if (projectUriMapping.containsKey(uriCandidate)) {
				return uriCandidate;
			}
		}

		return null;
	}

	Collection<N4JSExternalProject> getProjects() {
		ensureInitialized();
		Map<String, N4JSExternalProject> projects = getProjectMapping();
		return unmodifiableCollection(projects.values());
	}

	List<Pair<N4JSExternalProject, ProjectDescription>> computeProjectsUncached() {
		List<Pair<N4JSExternalProject, ProjectDescription>> projectPairs = new LinkedList<>();
		Iterable<java.net.URI> projectRoots = externalLibraryPreferenceStore
				.convertToProjectRootLocations(getRootLocations());

		for (java.net.URI projectRoot : projectRoots) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);

			try {
				Pair<N4JSExternalProject, ProjectDescription> pair;
				pair = cacheLoader.load(projectLocation).orNull();
				if (null != pair) {
					projectPairs.add(pair);
				}
			} catch (Exception e) {
				// ignore
			}
		}

		return projectPairs;
	}

	N4JSExternalProject getProject(String projectName) {
		ensureInitialized();
		return projectNameMapping.get(projectName);
	}

	N4JSExternalProject getProject(URI projectLocation) {
		ensureInitialized();
		Pair<N4JSExternalProject, ProjectDescription> pair = projectUriMapping.get(projectLocation);
		if (pair != null) {
			return pair.getFirst();
		}
		return null;
	}

	Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation) {
		return projectsForLocation.getOrDefault(rootLocation, Collections.emptyList());
	}

	Pair<N4JSExternalProject, ProjectDescription> getProjectWithDescription(URI location) {
		ensureInitialized();
		return projectUriMapping.get(location);
	}

	private void setRootLocations(Collection<java.net.URI> newRootLocations) {
		rootLocations = new TreeMap<>();
		for (java.net.URI loc : newRootLocations) {
			String locStr = loc.toString();
			rootLocations.put(locStr, loc);
		}
		rootLocations = Collections.unmodifiableNavigableMap(rootLocations);
	}
}
