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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.internal.ExternalProjectCacheLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.n4js.validation.helper.FolderContainmentHelper;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.UriUtil;

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

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private FolderContainmentHelper containmentHelper;

	private final Collection<ExternalLocationsUpdatedListener> locListeners = new LinkedList<>();
	private final List<java.net.URI> rootLocations = new LinkedList<>();
	private final Map<URI, Pair<N4JSExternalProject, ProjectDescription>> projectCache = new HashMap<>();
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

		for (Pair<N4JSExternalProject, ProjectDescription> pair : collectProjects()) {
			URI locationURI = pair.getFirst().getIProject().getLocation();
			projectCache.put(locationURI, pair);
		}

		updateMappings();
	}

	/** @return all projects. Does not affect cached projects. */
	private List<Pair<N4JSExternalProject, ProjectDescription>> collectProjects() {
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
					}
				}
			}
		}

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

	Collection<N4JSExternalProject> computeProjectsUncached() {
		List<N4JSExternalProject> projects = new LinkedList<>();
		for (Pair<N4JSExternalProject, ProjectDescription> pair : collectProjects()) {
			projects.add(pair.getFirst());
		}
		return projects;
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
			// TODO maybe we can be stricter here (directly contained || directly contained in scope)
			if (containmentHelper.isContained(projectLocation, rootUri)) {
				Pair<N4JSExternalProject, ProjectDescription> pair = entry.getValue();
				if (null != pair && null != pair.getFirst()) {
					N4JSExternalProject project = pair.getFirst();
					ProjectDescription projectDescription = pair.getSecond();
					projectsMapping.put(projectDescription.getProjectName(), project);
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
			// TODO maybe we can be stricter here (directly contained || directly contained in scope)
			if (containmentHelper.isContained(projectLocation, rootUri)) {
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
