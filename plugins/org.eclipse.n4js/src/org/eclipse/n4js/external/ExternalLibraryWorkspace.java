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
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.utils.URIUtils;

import com.google.inject.ImplementedBy;

/**
 * Representation of a workspace (with possible multiple workspace roots) that is used for storing external library
 * projects.
 */
@ImplementedBy(HlcExternalLibraryWorkspace.class)
public abstract class ExternalLibraryWorkspace extends InternalN4JSWorkspace {

	/** Contains the projects that were built/cleaned and affected. */
	static public class RegisterResult {
		/** All external projects that were built/cleaned */
		final public Set<URI> externalProjectsDone;
		/** All workspace projects that were affected */
		final public Set<URI> affectedWorkspaceProjects;
		/** All projects that were wiped from index */
		final public Set<URI> wipedProjects;

		RegisterResult() {
			this.externalProjectsDone = freeze(null);
			this.affectedWorkspaceProjects = freeze(null);
			this.wipedProjects = freeze(null);
		}

		/** Constructor */
		public RegisterResult(Collection<URI> extPrjsDone, Collection<URI> wsPrjsAffected) {
			this(extPrjsDone, wsPrjsAffected, null);
		}

		/** Constructor */
		public RegisterResult(Collection<URI> extPrjsDone, Collection<URI> wsPrjsAffected, Collection<URI> prjsWiped) {
			this.externalProjectsDone = freeze(extPrjsDone);
			this.affectedWorkspaceProjects = freeze(wsPrjsAffected);
			this.wipedProjects = freeze(prjsWiped);
		}

		/** Constructor */
		public RegisterResult(IProject[] allProjectsToClean, IProject[] wsPrjAffected) {
			this(allProjectsToClean, wsPrjAffected, null);
		}

		/** Constructor */
		public RegisterResult(IProject[] allProjectsToClean, IProject[] wsPrjAffected, Collection<URI> prjsWiped) {
			this.externalProjectsDone = freeze(getURIs(allProjectsToClean));
			this.affectedWorkspaceProjects = freeze(getURIs(wsPrjAffected));
			this.wipedProjects = freeze(prjsWiped);
		}

		static private Set<URI> freeze(Collection<URI> prjs) {
			if (prjs == null) {
				return Collections.unmodifiableSet(Collections.emptySet());
			}
			return Collections.unmodifiableSet(new HashSet<>(prjs));
		}

		static private Collection<URI> getURIs(IProject[] projects) {
			HashSet<URI> uris = new HashSet<>();
			for (IProject project : projects) {
				uris.add(URIUtils.convert(project));
			}
			return uris;
		}
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
	public abstract RegisterResult registerProjects(IProgressMonitor monitor, Set<URI> toBeUpdated);

	/**
	 * Deregisters all given project and cleans/builds affected workspace projects afterwards. This operation also wipes
	 * the Xtext index clean of all given and affected external projects.
	 *
	 * @param monitor
	 *            the monitor for the project registration process.
	 * @param toBeDeleted
	 *            if true, a clean build is triggered on all affected workspace projects.
	 */
	public abstract RegisterResult deregisterProjects(IProgressMonitor monitor, Set<URI> toBeDeleted);

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
	public abstract void scheduleWorkspaceProjects(IProgressMonitor monitor, Set<URI> toBeScheduled);

	/**
	 * Returns with all available external projects.
	 *
	 * @return the external projects.
	 */
	public abstract Collection<N4JSExternalProject> getProjects();

	/**
	 * Returns with all external projects. Does not use cached data.
	 *
	 * @return the external projects that are actually on the HDD.
	 */
	public abstract Collection<N4JSExternalProject> computeProjectsUncached();

	/**
	 * Returns with all existing external projects that are contained in the given external library root location.
	 *
	 * @param rootLocation
	 *            the location of the external library root.
	 * @return an iterable of external projects available from the given external library root location.
	 */
	public abstract Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation);

	/**
	 * Returns with all existing external projects that are contained in the given external library root locations.
	 *
	 * @param rootLocations
	 *            the locations of the external library roots.
	 * @return an iterable of external projects available from the given external library root locations.
	 */
	public abstract Collection<N4JSExternalProject> getProjectsIn(Collection<java.net.URI> rootLocations);

	/**
	 * Returns with all existing external project descriptions that are contained in the given external library root
	 * location.
	 *
	 * @param rootLocation
	 *            the location of the external library root.
	 * @return an iterable of external project descriptions available from the given external library root location.
	 */
	public abstract Collection<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation);

	/**
	 * Returns with the project with the given name. Or {@code null} if the project does not exist.
	 *
	 * @param projectName
	 *            the unique name of the project.
	 * @return the project, or {@code null} if does not exist.
	 */
	public abstract N4JSExternalProject getProject(final String projectName);

	/**
	 * Returns with the project with the given location. Or {@code null} if the project does not exist.
	 *
	 * @param projectLocation
	 *            the location of the project.
	 * @return the project, or {@code null} if does not exist.
	 */
	public abstract N4JSExternalProject getProject(final URI projectLocation);

	/**
	 * Returns with the file given with the file location URI argument. This method returns with {@code null} if the
	 * file cannot be found at the given location.
	 *
	 * @param location
	 *            the location of the file we are looking for.
	 * @return the file at the given location or {@code null} if does not exist.
	 */
	public abstract IResource getResource(URI location);

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

}
