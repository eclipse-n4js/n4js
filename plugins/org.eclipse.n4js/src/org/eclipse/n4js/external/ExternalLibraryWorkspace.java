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
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.utils.resources.ExternalProject;

import com.google.inject.ImplementedBy;

/**
 * Representation of a workspace (with possible multiple workspace roots) that is used for storing external library
 * projects.
 */
@ImplementedBy(NoopExternalLibraryWorkspace.class)
public abstract class ExternalLibraryWorkspace extends InternalN4JSWorkspace {

	/** Contains the projects that were built and scheduled. */
	static public class RegisterResult {
		/** All external projects that were cleaned */
		final public Set<? extends IProject> externalProjectsCleaned;
		/** All external projects that were built */
		final public Set<? extends IProject> externalProjectsBuilt;
		/** All workspace projects that were scheduled */
		final public Set<? extends IProject> workspaceProjectsScheduled;

		RegisterResult() {
			this.externalProjectsCleaned = freeze(null);
			this.externalProjectsBuilt = freeze(null);
			this.workspaceProjectsScheduled = freeze(null);
		}

		/** Constructor */
		public RegisterResult(Collection<? extends IProject> extPrjsCleaned,
				Collection<? extends IProject> extPrjsBuilt,
				Collection<? extends IProject> wsPrjsScheduled) {

			this.externalProjectsCleaned = freeze(extPrjsCleaned);
			this.externalProjectsBuilt = freeze(extPrjsBuilt);
			this.workspaceProjectsScheduled = freeze(wsPrjsScheduled);
		}

		static private <P extends IProject> Set<P> freeze(Collection<P> prjs) {
			if (prjs == null) {
				return Collections.unmodifiableSet(Collections.emptySet());
			}
			@SuppressWarnings("unused")
			HashSet<P> hashSet = new HashSet<P>(prjs);
			return Collections.unmodifiableSet(hashSet);
		}
	}

	/**
	 * Registers the new projects and removed the deleted ones based on the project adaption result. The projects will
	 * be built/cleaned based on the differences given in the result.
	 *
	 * @param result
	 *            the project adaption result to update/delete projects.
	 * @param monitor
	 *            the monitor for the project registration process.
	 * @param triggerCleanbuild
	 *            if true, a clean build is triggered on all affected workspace projects.
	 */
	public abstract RegisterResult registerProjects(NpmProjectAdaptionResult result, IProgressMonitor monitor,
			boolean triggerCleanbuild);

	/**
	 * Returns with all available external projects.
	 *
	 * @return the external projects.
	 */
	public abstract Collection<N4JSExternalProject> getProjects();

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
	public abstract ExternalProject getProject(final String projectName);

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
	 *
	 * <p>
	 * This cannot be done in construction time, because it might happen that N4MF is not initialized yet, hence not
	 * available when injecting this instance.
	 */
	public abstract void updateState();

}
