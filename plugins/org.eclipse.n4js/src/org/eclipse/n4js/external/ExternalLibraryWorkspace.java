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
package org.eclipse.n4js.external;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * Representation of a workspace (with possible multiple workspace roots) that is used for storing external library
 * projects.
 */
@ImplementedBy(HlcExternalLibraryWorkspace.class)
public abstract class ExternalLibraryWorkspace extends InternalN4JSWorkspace<FileURI> {

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/** Contains the projects that were built/cleaned and affected. */
	static public class RegisterResult {
		/** All external projects that were built/cleaned */
		final public Set<FileURI> externalProjectsDone;
		/** All workspace projects that were affected */
		final public Set<SafeURI<?>> affectedWorkspaceProjects;
		/** All projects that were wiped from index */
		final public Set<FileURI> wipedProjects;

		RegisterResult() {
			this.externalProjectsDone = freeze(null);
			this.affectedWorkspaceProjects = freeze(null);
			this.wipedProjects = freeze(null);
		}

		/** Constructor */
		public RegisterResult(Collection<FileURI> extPrjsDone, Collection<? extends SafeURI<?>> wsPrjsAffected) {
			this(extPrjsDone, wsPrjsAffected, null);
		}

		/** Constructor */
		public RegisterResult(Collection<FileURI> extPrjsDone, Collection<? extends SafeURI<?>> wsPrjsAffected,
				Collection<FileURI> prjsWiped) {
			this.externalProjectsDone = freeze(extPrjsDone);
			this.affectedWorkspaceProjects = freeze(wsPrjsAffected);
			this.wipedProjects = freeze(prjsWiped);
		}

		static private <E extends SafeURI<?>> Set<E> freeze(Collection<? extends E> prjs) {
			if (prjs == null) {
				return Collections.unmodifiableSet(Collections.emptySet());
			}
			return Collections.unmodifiableSet(new HashSet<>(prjs));
		}
	}

	@Inject
	private UriExtensions uriExtensions;

	@Override
	public FileURI fromURI(URI uri) {
		if (!uri.isFile() || uri.isRelative()) {
			return null;
		}
		return new FileURI(uriExtensions.withEmptyAuthority(uri));
	}

	/**
	 * Registers the new projects and removed the deleted ones based on the project adaption result. The projects will
	 * be built/cleaned based on the differences given in the result.
	 *
	 * @param monitor
	 *            the monitor for the project registration process.
	 * @param toBeUpdated
	 *            the project adaption result to update/delete projects.
	 */
	public abstract RegisterResult registerProjects(IProgressMonitor monitor, Set<FileURI> toBeUpdated);

	/**
	 * Deregisters all given project and cleans/builds affected workspace projects afterwards. This operation also wipes
	 * the Xtext index clean of all given and affected external projects.
	 *
	 * @param monitor
	 *            the monitor for the project registration process.
	 * @param toBeDeleted
	 *            if true, a clean build is triggered on all affected workspace projects.
	 */
	public abstract RegisterResult deregisterProjects(IProgressMonitor monitor, Set<FileURI> toBeDeleted);

	/**
	 * Deregisters all external projects and wipes the Xtext index clean.
	 *
	 * @param monitor
	 *            the monitor for the project registration process.
	 */
	public abstract RegisterResult deregisterAllProjects(IProgressMonitor monitor);

	/**
	 * Schedules a rebuild of the given workspace projects.
	 *
	 * @param monitor
	 *            the monitor for the project registration process.
	 * @param toBeScheduled
	 *            the workspace projects that should be rebuild.
	 */
	public abstract void scheduleWorkspaceProjects(IProgressMonitor monitor, Set<SafeURI<?>> toBeScheduled);

	/**
	 * Returns with all available external projects.
	 *
	 * @return the external projects.
	 */
	public abstract Collection<N4JSExternalProject> getProjects();

	/**
	 * Returns a map with name and version of all available external projects.
	 *
	 * @return map of name and version of the external projects.
	 */
	public abstract Map<N4JSProjectName, VersionNumber> getProjectNameVersionMap();

	/**
	 * Returns with all external projects including those that are dependencies of plain-JS projects.
	 *
	 * @return the external projects that are actually on the HDD.
	 */
	public abstract List<Pair<FileURI, ProjectDescription>> getProjectsIncludingUnnecessary();

	/**
	 * Expensive. Computes available external projects.
	 *
	 * @return the external projects.
	 */
	public abstract List<Pair<FileURI, ProjectDescription>> computeProjectsIncludingUnnecessary();

	/**
	 * Returns with all external projects that have the given name. The returned list is sorted according to the
	 * shadowing priority.
	 *
	 * @return the external projects that have the given name.
	 */
	public abstract List<N4JSExternalProject> getProjectsForName(N4JSProjectName projectName);

	/**
	 * Returns with all existing external projects that are contained in the given external library root location.
	 *
	 * @param rootLocation
	 *            the location of the external library root.
	 * @return an iterable of external projects available from the given external library root location.
	 */
	public abstract Collection<N4JSExternalProject> getProjectsIn(FileURI rootLocation);

	/**
	 * Returns with all existing external projects that are contained in the given external library root locations.
	 *
	 * @param rootLocations
	 *            the locations of the external library roots.
	 * @return an iterable of external projects available from the given external library root locations.
	 */
	public abstract Collection<N4JSExternalProject> getProjectsIn(Collection<FileURI> rootLocations);

	/**
	 * Returns with the project with the given name. Or {@code null} if the project does not exist.
	 *
	 * @param projectName
	 *            the unique name of the project.
	 * @return the project, or {@code null} if does not exist.
	 */
	public abstract N4JSExternalProject getProject(N4JSProjectName projectName);

	/**
	 * Returns with the project with the given location. Or {@code null} if the project does not exist.
	 * <p>
	 * Will also work for <i>unnecessary</i> projects.
	 *
	 * @param location
	 *            the location of the project.
	 * @return the project, or {@code null} if does not exist.
	 */
	public abstract N4JSExternalProject getProject(FileURI location);

	@Override
	public FileURI getProjectLocation(N4JSProjectName name) {
		return getProject(name).getSafeLocation();
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This method will remove/add available projects of {@link IN4JSCore}. It should only be invoked through
	 * {@link HlcExternalIndexSynchronizer#synchronizeNpms(IProgressMonitor)}.
	 * <p>
	 * This cannot be done in construction time, because it might happen that some bundles/classes are not initialized
	 * yet, hence not available when injecting this instance.
	 */
	public abstract void updateState();

	/**
	 * Like {@link #getRootLocationForResource(FileURI)} but in case it finds nothing, a root location is computed based
	 * on the given URI and the built-in shipped code locations.
	 * <p>
	 * Method can be used for instance to find root locations of projects which are closed or removed from workspace.
	 *
	 * @return a root location for a given resource or project
	 */
	public FileURI getRootLocationForResourceOrInfer(FileURI nestedLocation) {
		HashSet<FileURI> allRootLocations = new LinkedHashSet<>();
		allRootLocations.addAll(externalLibraryPreferenceStore.getLocations());

		FileURI rootLocation = getRootLocationForResource(allRootLocations, nestedLocation);
		if (rootLocation == null) {
			do {
				String name = nestedLocation.getName();
				if (N4JSGlobals.NODE_MODULES.equals(name)) {
					return nestedLocation;
				}
				nestedLocation = nestedLocation.getParent();
			} while (nestedLocation != null);
			return null;
		}

		return rootLocation;
	}

	/** @return a root location for a given resource or project */
	public FileURI getRootLocationForResource(FileURI nestedLocation) {
		return getRootLocationForResource(externalLibraryPreferenceStore.getLocations(), nestedLocation);
	}

	/** @return the matching root location for a set of root locations and a resource or project */
	static final public FileURI getRootLocationForResource(Collection<FileURI> rootLocations,
			SafeURI<?> nestedLocation) {

		if (nestedLocation == null || nestedLocation.isEmpty() || !nestedLocation.exists()) {
			return null;
		}

		TreeMap<String, FileURI> rootLocationMap = new TreeMap<>();
		for (FileURI loc : rootLocations) {
			String locStr = loc.toString();
			rootLocationMap.put(locStr, loc);
		}

		String nestedLocStr = nestedLocation.toString();
		String rootLocStr = rootLocationMap.floorKey(nestedLocStr);
		if (rootLocStr != null && nestedLocStr.startsWith(rootLocStr)) {
			return rootLocationMap.get(rootLocStr);
		}
		return null;
	}

	/** @return true iff the project at the given location is a necessary (see ExternalProjectMappings) project. */
	public abstract boolean isNecessary(SafeURI<?> location);

}
