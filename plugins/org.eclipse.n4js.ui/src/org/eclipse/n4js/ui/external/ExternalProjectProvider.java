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

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.ExternalProjectLoader;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This provider creates {@link ExternalProject}s.
 */
@Singleton
public class ExternalProjectProvider implements StoreUpdatedListener {

	@Inject
	private ExternalProjectLoader cacheLoader;

	@Inject
	private ProjectStateChangeListener projectStateChangeListener;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private EclipseBasedN4JSWorkspace userWorkspace;

	@Inject
	private NpmLogger npmLogger;

	static private class UninitializedMappings extends ExternalProjectMappings {
		public UninitializedMappings() {
			super(null, null, null, false);
		}
	}

	private final Semaphore semaphore = new Semaphore(1);
	private final Collection<ExternalLocationsUpdatedListener> locListeners = new LinkedList<>();
	private final LinkedHashMap<String, java.net.URI> rootLocations = new LinkedHashMap<>();
	private ExternalProjectMappings mappings = new UninitializedMappings();

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

	Collection<java.net.URI> getRootLocationsInReversedShadowingOrder() {
		return rootLocations.values();
	}

	Collection<URI> getAllProjectLocations() {
		return mappings.reducedProjectUriMapping.keySet();
	}

	@Override
	public void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor) {
		Set<java.net.URI> oldLocations = new HashSet<>(getRootLocationsInReversedShadowingOrder());
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

	void updateCache() {
		updateCacheInternal();
	}

	private void ensureInitialized() {
		if (!mappings.initialized) {
			synchronized (this) {
				if (!mappings.initialized) {
					updateCacheInternal();
				}
			}
		}
	}

	private void updateCache(Set<java.net.URI> newLocations) {
		setRootLocations(newLocations);
		updateCacheInternal();
	}

	synchronized private void updateCacheInternal() {
		if (semaphore.tryAcquire()) {
			try {
				Map<URI, Pair<N4JSExternalProject, ProjectDescription>> completeCache = computeProjectsUncached();
				mappings = new ExternalProjectMappings(userWorkspace, externalLibraryPreferenceStore, completeCache);
				npmLogger.logInfo("external locations updated");

			} finally {
				semaphore.release();
			}
		}
	}

	Set<N4JSExternalProject> getNecessaryDependencies() {
		ensureInitialized();
		return mappings.reducedSet;
	}

	Set<java.net.URI> getAllRootLocations() {
		Set<java.net.URI> allRootLocs = new HashSet<>();
		allRootLocs.addAll(externalLibraryPreferenceStore.getLocations());
		allRootLocs.addAll(mappings.reducedProjectsLocationMapping.keySet());
		return allRootLocs;
	}

	Collection<N4JSExternalProject> getProjects() {
		ensureInitialized();
		return mappings.reducedSet;
	}

	List<Pair<URI, ProjectDescription>> computeProjectsIncludingUnnecessary() {
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> completeCache = computeProjectsUncached();
		ExternalProjectMappings mappingsTmp = new ExternalProjectMappings(userWorkspace, externalLibraryPreferenceStore,
				completeCache);

		return mappingsTmp.completeList;
	}

	List<Pair<URI, ProjectDescription>> getProjectsIncludingUnnecessary() {
		return mappings.completeList;
	}

	private Map<URI, Pair<N4JSExternalProject, ProjectDescription>> computeProjectsUncached() {
		Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projects = new LinkedHashMap<>();
		Iterable<java.net.URI> projectRoots = externalLibraryPreferenceStore
				.convertToProjectRootLocations(getRootLocationsInReversedShadowingOrder());

		for (java.net.URI projectRoot : projectRoots) {
			URI projectLocation = URIUtils.toFileUri(projectRoot);

			try {
				Pair<N4JSExternalProject, ProjectDescription> pair;
				pair = cacheLoader.load(projectLocation);
				if (null != pair) {
					projects.put(projectLocation, pair);
				}
			} catch (Exception e) {
				// ignore
			}
		}

		return projects;
	}

	N4JSExternalProject getProject(String projectName) {
		ensureInitialized();
		List<N4JSExternalProject> prjsOfName = mappings.completeProjectNameMapping.get(projectName);
		N4JSExternalProject activePrj = (prjsOfName == null || prjsOfName.isEmpty()) ? null : prjsOfName.get(0);
		return activePrj;
	}

	List<N4JSExternalProject> getProjectsForName(String projectName) {
		ensureInitialized();
		List<N4JSExternalProject> prjList = mappings.completeProjectNameMapping.getOrDefault(projectName,
				Collections.emptyList());
		return Collections.unmodifiableList(prjList);
	}

	N4JSExternalProject getProject(URI projectLocation) {
		ensureInitialized();
		Pair<N4JSExternalProject, ProjectDescription> pair = mappings.completeCache.get(projectLocation);
		if (pair != null) {
			return pair.getFirst();
		}
		return null;
	}

	Pair<N4JSExternalProject, ProjectDescription> getProjectWithDescription(URI location) {
		ensureInitialized();
		return mappings.completeCache.get(location);
	}

	Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation) {
		return mappings.reducedProjectsLocationMapping.getOrDefault(rootLocation, Collections.emptyList());
	}

	private void setRootLocations(Collection<java.net.URI> newRootLocations) {
		List<java.net.URI> locationsInShadowOrder = ExternalLibrariesActivator.sortByShadowing(newRootLocations);
		Collections.reverse(locationsInShadowOrder); // reverse order for ExternalProjectMapper
		rootLocations.clear();
		for (java.net.URI loc : locationsInShadowOrder) {
			String locStr = loc.toString();
			rootLocations.put(locStr, loc);
		}
	}
}
