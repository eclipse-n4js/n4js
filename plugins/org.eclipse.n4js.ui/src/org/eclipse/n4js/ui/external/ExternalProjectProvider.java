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
import static com.google.common.collect.Maps.newTreeMap;
import static java.util.Collections.unmodifiableCollection;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.ExternalProjectLocationsProvider;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.ui.internal.ExternalProjectCacheLoader;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.util.Pair;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
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

	private final Collection<ExternalLocationsUpdatedListener> locListeners = new LinkedList<>();
	private final List<java.net.URI> rootLocations = new LinkedList<>();
	private LoadingCache<URI, Optional<Pair<N4JSExternalProject, ProjectDescription>>> projectCache;
	private Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMapping;
	private Map<String, N4JSExternalProject> projectNameMapping;

	/**
	 * Creates a new external library workspace instance with the preference store that provides the configured library
	 * location.
	 *
	 * @param preferenceStore
	 *            the preference store to get the registered external library locations.
	 */
	@Inject
	ExternalProjectProvider(ExternalLibraryPreferenceStore preferenceStore) {
		rootLocations.addAll(sortByShadowing(preferenceStore.getLocations()));
		preferenceStore.addListener(this);
	}

	/**
	 * Initializes the backing cache with the cache loader and registers a {@link ProjectStateChangeListener} into the
	 * workspace.
	 */
	@Inject
	void init() {
		projectCache = CacheBuilder.newBuilder().build(cacheLoader);
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
		List<java.net.URI> locationsInShadowOrder = sortByShadowing(newLocations);
		rootLocations.addAll(locationsInShadowOrder);

		updateCache();
	}

	private List<java.net.URI> sortByShadowing(Collection<java.net.URI> locations) {
		Map<String, java.net.URI> knownLocations = new HashMap<>();
		List<java.net.URI> unknownLocations = new LinkedList<>();

		for (java.net.URI location : locations) {
			String locStr = location.toString();
			locStr = locStr.endsWith("/") ? locStr.substring(0, locStr.length() - 1) : locStr;

			boolean locationFound = false;
			for (String knownLocation : ExternalLibrariesActivator.CATEGORY_SHADOWING_ORDER) {
				if (locStr.endsWith(knownLocation)) {
					knownLocations.put(knownLocation, location);
					locationFound = true;
				}
			}

			if (!locationFound) {
				unknownLocations.add(location);
			}
		}

		List<java.net.URI> sortedLocations = new LinkedList<>();
		for (String knownLocation : ExternalLibrariesActivator.CATEGORY_SHADOWING_ORDER) {
			java.net.URI location = knownLocations.get(knownLocation);
			if (location != null) {
				sortedLocations.add(location);
			}
		}
		sortedLocations.addAll(unknownLocations);

		return sortedLocations;
	}

	void updateCache() {
		projectCache.invalidateAll();
		Iterable<java.net.URI> projectRoots = ExternalProjectLocationsProvider.INSTANCE
				.convertToProjectRootLocations(rootLocations);

		for (java.net.URI projectRoot : projectRoots) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);
			Pair<N4JSExternalProject, ProjectDescription> pair;
			try {
				pair = projectCache.get(projectLocation).orNull();
				if (null == pair) { // Removed trash.
					projectCache.invalidate(projectLocation);
				}
			} catch (ExecutionException e) {
				// ignore
			}
		}

		updateMappings();
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that N4MF is not initialized yet, hence not
	 * available when injecting this instance.
	 */
	private void updateMappings() {
		Map<URI, Optional<Pair<N4JSExternalProject, ProjectDescription>>> cachedProjects = projectCache.asMap();
		Map<String, N4JSExternalProject> projectNameMappingTmp = newHashMap();
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectUriMappingTmp = newHashMap();

		Iterable<java.net.URI> projectRoots = ExternalProjectLocationsProvider.INSTANCE
				.convertToProjectRootLocations(rootLocations); // TODO: check if this preserves order!
		List<java.net.URI> projectRootsInReversedOrder = Lists.newArrayList(projectRoots);
		Collections.reverse(projectRootsInReversedOrder);

		for (java.net.URI projectRoot : projectRootsInReversedOrder) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);
			if (cachedProjects.containsKey(projectLocation)) {
				Pair<N4JSExternalProject, ProjectDescription> pair = cachedProjects.get(projectLocation).orNull();
				if (null != pair) {
					N4JSExternalProject project = pair.getFirst();
					if (!projectNameMappingTmp.containsKey(project.getName())) {
						projectNameMappingTmp.put(project.getName(), project);
						projectUriMappingTmp.put(projectLocation, pair);
					}
				}
			}
		}

		projectNameMapping = Collections.unmodifiableMap(projectNameMappingTmp);
		projectUriMapping = Collections.unmodifiableMap(projectUriMappingTmp);
	}

	Collection<URI> getProjectURIs() {
		ensureInitialized();
		return projectUriMapping.keySet();
	}

	Collection<N4JSExternalProject> getProjects() {
		ensureInitialized();
		Map<String, N4JSExternalProject> projects = getProjectMapping();
		return unmodifiableCollection(projects.values());
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

	Collection<N4JSExternalProject> getProjectsIn(Iterable<java.net.URI> pRootLocations) {
		Map<String, N4JSExternalProject> projectsMapping = newTreeMap();
		for (java.net.URI rootLocation : pRootLocations) {
			Iterable<N4JSExternalProject> projects = getProjectsIn(rootLocation);
			for (N4JSExternalProject project : projects) {
				projectsMapping.put(project.getName(), project);
			}
		}
		return unmodifiableCollection(projectsMapping.values());
	}

	Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation) {
		ensureInitialized();
		File rootFolder = new File(rootLocation);

		Map<String, N4JSExternalProject> projectsMapping = newTreeMap();
		URI rootUri = URI.createFileURI(rootFolder.getAbsolutePath());

		for (Entry<URI, Pair<N4JSExternalProject, ProjectDescription>> entry : projectUriMapping.entrySet()) {
			URI projectLocation = entry.getKey();
			if (rootUri.equals(projectLocation.trimSegments(1))) {
				Pair<N4JSExternalProject, ProjectDescription> pair = entry.getValue();
				if (null != pair && null != pair.getFirst()) {
					N4JSExternalProject project = pair.getFirst();
					projectsMapping.put(project.getName(), project);
				}
			}
		}

		return unmodifiableCollection(projectsMapping.values());
	}

	Collection<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation) {
		ensureInitialized();
		File rootFolder = new File(rootLocation);

		Set<ProjectDescription> projectsMapping = new HashSet<>();
		URI rootUri = URI.createFileURI(rootFolder.getAbsolutePath());

		for (Entry<URI, Pair<N4JSExternalProject, ProjectDescription>> entry : projectUriMapping.entrySet()) {
			URI projectLocation = entry.getKey();
			if (rootUri.equals(projectLocation.trimSegments(1))) {
				Pair<N4JSExternalProject, ProjectDescription> pair = entry.getValue();
				if (null != pair && null != pair.getFirst()) {
					ProjectDescription description = pair.getSecond();
					if (description != null) {
						projectsMapping.add(description);
					}
				}
			}
		}

		return unmodifiableCollection(projectsMapping);
	}

	Pair<N4JSExternalProject, ProjectDescription> getProjectWithDescription(URI location) {
		ensureInitialized();
		return projectUriMapping.get(location);
	}

}
