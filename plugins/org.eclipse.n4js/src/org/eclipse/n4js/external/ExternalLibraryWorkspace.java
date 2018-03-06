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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IBuildConfiguration;
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
			this.externalProjectsCleaned = Collections.unmodifiableSet(Collections.emptySet());
			this.externalProjectsBuilt = Collections.unmodifiableSet(Collections.emptySet());
			this.workspaceProjectsScheduled = Collections.unmodifiableSet(Collections.emptySet());
		}

		/** Constructor */
		public RegisterResult(Set<? extends IProject> extPrjsCleaned,
				Set<? extends IProject> extPrjsBuilt,
				Set<? extends IProject> wsPrjsScheduled) {

			this.externalProjectsCleaned = Collections.unmodifiableSet(extPrjsCleaned);
			this.externalProjectsBuilt = Collections.unmodifiableSet(extPrjsBuilt);
			this.workspaceProjectsScheduled = Collections.unmodifiableSet(wsPrjsScheduled);
		}

		/** Constructor */
		public RegisterResult(Iterable<IBuildConfiguration> extPrjsCleaned,
				Iterable<IBuildConfiguration> extPrjsBuilt,
				Set<? extends IProject> wsPrjsScheduled) {

			this(toProjectSet(extPrjsCleaned), toProjectSet(extPrjsBuilt), wsPrjsScheduled);
		}

		static private Set<? extends IProject> toProjectSet(Iterable<IBuildConfiguration> bConfigs) {
			Set<IProject> prjSet = new HashSet<>();
			if (bConfigs == null) {
				return prjSet;
			}
			for (IBuildConfiguration bConfig : bConfigs) {
				prjSet.add(bConfig.getProject());
			}
			return Collections.unmodifiableSet(prjSet);
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
	public abstract Iterable<ExternalProject> getProjects();

	/**
	 * Returns with all existing external projects that are contained in the given external library root location.
	 *
	 * @param rootLocation
	 *            the location of the external library root.
	 * @return an iterable of external projects available from the given external library root location.
	 */
	public abstract Iterable<ExternalProject> getProjects(java.net.URI rootLocation);

	/**
	 * Returns with all existing external project descriptions that are contained in the given external library root
	 * location.
	 *
	 * @param rootLocation
	 *            the location of the external library root.
	 * @return an iterable of external project descriptions available from the given external library root location.
	 */
	public abstract Iterable<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation);

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
