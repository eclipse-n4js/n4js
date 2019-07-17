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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.external.ExternalLibraryHelper;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.ExternalProjectLoader;
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
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/* Internal type is used to avoid cyclic dependencies */
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
		preferenceStore.addListener(this);
	}

	Collection<FileURI> getAllProjectLocations() {
		return mappings.reducedProjectUriMapping.keySet();
	}

	@Override
	public void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor) {
		Set<SafeURI<?>> oldLocations = new HashSet<>(getRootLocationsInReversedShadowingOrder());
		Set<SafeURI<?>> newLocations = new HashSet<>(store.getLocations());

		Set<SafeURI<?>> removedLocations = new HashSet<>(oldLocations);
		removedLocations.removeAll(newLocations);

		Set<SafeURI<?>> addedLocations = new HashSet<>(newLocations);
		addedLocations.removeAll(oldLocations);
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

	synchronized private void updateCacheInternal() {
		if (semaphore.tryAcquire()) {
			try {
				Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> completeCache = computeProjectsUncached();
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

	Set<FileURI> getAllRootLocations() {
		Set<FileURI> allRootLocs = new HashSet<>();
		allRootLocs.addAll(externalLibraryPreferenceStore.getLocations());
		allRootLocs.addAll(mappings.reducedProjectsLocationMapping.keySet());
		return allRootLocs;
	}

	Collection<N4JSExternalProject> getProjects() {
		ensureInitialized();
		return mappings.reducedSet;
	}

	List<Pair<FileURI, ProjectDescription>> computeProjectsIncludingUnnecessary() {
		Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> completeCache = computeProjectsUncached();
		ExternalProjectMappings mappingsTmp = new ExternalProjectMappings(userWorkspace, externalLibraryPreferenceStore,
				completeCache);

		return mappingsTmp.completeList;
	}

	List<Pair<FileURI, ProjectDescription>> getProjectsIncludingUnnecessary() {
		return mappings.completeList;
	}

	private Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> computeProjectsUncached() {
		Map<FileURI, Pair<N4JSExternalProject, ProjectDescription>> projects = new LinkedHashMap<>();
		Iterable<FileURI> projectRoots = externalLibraryPreferenceStore
				.convertToProjectRootLocations(getRootLocationsInReversedShadowingOrder());

		for (FileURI projectLocation : projectRoots) {
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

	N4JSExternalProject getProject(N4JSProjectName projectName) {
		ensureInitialized();
		List<N4JSExternalProject> prjsOfName = mappings.completeProjectNameMapping.get(projectName);
		N4JSExternalProject activePrj = (prjsOfName == null || prjsOfName.isEmpty()) ? null : prjsOfName.get(0);
		return activePrj;
	}

	List<N4JSExternalProject> getProjectsForName(N4JSProjectName projectName) {
		ensureInitialized();
		List<N4JSExternalProject> prjList = mappings.completeProjectNameMapping.getOrDefault(projectName,
				Collections.emptyList());
		return Collections.unmodifiableList(prjList);
	}

	N4JSExternalProject getProject(SafeURI<?> projectLocation) {
		ensureInitialized();
		Pair<N4JSExternalProject, ProjectDescription> pair = mappings.completeCache.get(projectLocation);
		if (pair != null) {
			return pair.getFirst();
		}
		return null;
	}

	Pair<N4JSExternalProject, ProjectDescription> getProjectWithDescription(SafeURI<?> location) {
		ensureInitialized();
		return mappings.completeCache.get(location);
	}

	Collection<N4JSExternalProject> getProjectsIn(FileURI rootLocation) {
		return mappings.reducedProjectsLocationMapping.getOrDefault(rootLocation, Collections.emptyList());
	}

	Collection<FileURI> getRootLocationsInReversedShadowingOrder() {
		Collection<FileURI> locations = externalLibraryPreferenceStore.getLocations();
		List<FileURI> locationsInShadowOrder = ExternalLibraryHelper.sortByShadowing(locations);
		Collections.reverse(locationsInShadowOrder);
		return locationsInShadowOrder;
	}
}
