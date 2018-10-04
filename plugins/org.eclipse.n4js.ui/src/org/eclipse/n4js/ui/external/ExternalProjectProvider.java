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
import java.util.Set;

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
import org.eclipse.xtext.util.UriUtil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
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
	private final List<java.net.URI> rootLocations = new LinkedList<>();
	private final Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectCache = new HashMap<>();

	private Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMapping;
	private Map<String, N4JSExternalProject> projectNameMapping;
	private Multimap<java.net.URI, N4JSExternalProject> projectsForLocation;

	/**
	 * Creates a new external library workspace instance with the preference store that provides the configured library
	 * location.
	 *
	 * @param preferenceStore
	 *            the preference store to get the registered external library locations.
	 */
	@Inject
	ExternalProjectProvider(ExternalLibraryPreferenceStore preferenceStore) {
		rootLocations.addAll(preferenceStore.getLocations());
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

	List<java.net.URI> getRootLocations() {
		return rootLocations;
	}

	Collection<URI> getAllProjectLocations() {
		return projectUriMapping.keySet();
	}

	@Override
	public void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor) {
		ensureInitialized();

		Set<java.net.URI> oldLocations = new HashSet<>(rootLocations);
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
		rootLocations.clear();
		List<java.net.URI> locationsInShadowOrder = ExternalLibrariesActivator.sortByShadowing(newLocations);
		rootLocations.addAll(locationsInShadowOrder);

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
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> cachedProjects = projectCache;
		Map<String, N4JSExternalProject> projectNameMappingTmp = newHashMap();
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp = newHashMap();
		Multimap<java.net.URI, N4JSExternalProject> projectsForLocationTmp = HashMultimap.create();

		Iterable<java.net.URI> projectRoots = externalLibraryPreferenceStore
				.convertToProjectRootLocations(rootLocations); // TODO: check if this preserves order!
		List<java.net.URI> projectRootsInReversedOrder = Lists.newArrayList(projectRoots);
		Collections.reverse(projectRootsInReversedOrder);

		for (java.net.URI projectRoot : projectRootsInReversedOrder) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);
			if (cachedProjects.containsKey(projectLocation)) {
				Pair<N4JSExternalProject, ProjectDescription> pair = cachedProjects.get(projectLocation);
				if (null != pair) {
					N4JSExternalProject project = pair.getFirst();
					if (!projectNameMappingTmp.containsKey(project.getName())) {
						final String projectName = ProjectDescriptionUtils
								.deriveN4JSProjectNameFromURI(projectLocation);

						projectNameMappingTmp.put(projectName, project);
						projectUriMappingTmp.put(projectLocation, pair);

						for (java.net.URI rootLoc : rootLocations) {
							if (projectRoot.toString().startsWith(rootLoc.toString())) {
								projectsForLocationTmp.put(rootLoc, project);
								break;
							}
						}
					}
				}
			}
		}

		projectsForLocation = Multimaps.unmodifiableMultimap(projectsForLocationTmp);
		projectNameMapping = Collections.unmodifiableMap(projectNameMappingTmp);
		projectUriMapping = Collections.unmodifiableMap(projectUriMappingTmp);
	}

	/**
	 * Like {@link IN4JSCore#findProject(URI)}, but returns the URI of the containing project. This method is
	 * performance critical because it is called often!
	 */
	URI findProjectWith(URI nestedLocation) {
		if (nestedLocation == null || nestedLocation.isEmpty() || !nestedLocation.isFile()) {
			return null;
		}
		ensureInitialized();
		for (java.net.URI locRaw : this.rootLocations) {
			URI loc = URI.createURI(locRaw.toString());
			URI prefix = !loc.hasTrailingPathSeparator() ? loc.appendSegment("") : loc;
			if (UriUtil.isPrefixOf(prefix, nestedLocation)) {
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
				.convertToProjectRootLocations(rootLocations);

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

	Set<URI> computeNecessaryDependencies() {
		Set<URI> necessaryDeps = new HashSet<>();
		LinkedList<URI> allLocs = new LinkedList<>();
		allLocs.addAll(userWorkspace.getAllProjectLocations());
		allLocs.addAll(getAllProjectLocations());

		for (URI loc : allLocs) {
			N4JSEclipseProject prj = model.getN4JSProject(loc);
			if (prj != null && prj.getProjectType() != ProjectType.PLAINJS) {
				ImmutableList<? extends IN4JSEclipseProject> deps = model.getDependenciesAndImplementedApis(prj, false);
				for (IN4JSEclipseProject dep : deps) {
					URI depLocation = dep.getLocation();
					necessaryDeps.add(depLocation);
				}
			}
		}

		necessaryDeps.removeAll(userWorkspace.getAllProjectLocations());
		return necessaryDeps;
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
		return projectsForLocation.get(rootLocation);
	}

	Pair<N4JSExternalProject, ProjectDescription> getProjectWithDescription(URI location) {
		ensureInitialized();
		return projectUriMapping.get(location);
	}

}
