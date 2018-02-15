/**
* Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.handler;

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
import org.eclipse.n4js.n4mf.TestedProject;
import org.eclipse.n4js.n4mf.TestedProjects;
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;

/**
 * Allows to access data in the manifests of the provided {@link IN4JSProject}s. Unlike {@link N4JSModel} will return
 * data that describes unknown or missing projects. Unlike {@link ManifestValuesParsingUtil} it allows to read manifest
 * of known projects, and not arbitrary {@code .n4mf} file.
 */
class DependneciesHelper {
	@Inject
	private EclipseBasedN4JSWorkspace workspace;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private IN4JSCore core;

	private static class DependencyInfo {
		/**
		 * version representation for projects with no declared versions, mimics behavior of
		 * {@link org.eclipse.n4js.external.version.VersionConstraintFormatUtil#npmFormat}
		 */
		private static String NO_VERSION = "";
		private final String id;
		private final String version;

		private DependencyInfo(String id, String version) {
			this.id = id;
			this.version = version;
		}

		public String getID() {
			return this.id;
		}

		public String getVersion() {
			return this.version;
		}

		/** Resolve conflict between two versions. Simple strategy - returns second if it is not empty. */
		public static String resolve(String version1, String version2) {
			return NO_VERSION.equals(version2) ? version1 : version2;
		}

		public static DependencyInfo create(ProjectReference projectReference) {
			return new DependencyInfo(toID(projectReference), toVersion(projectReference));
		}

		private static String toID(ProjectReference projectReference) {
			return projectReference.getProject().getProjectId();
		}

		private static String toVersion(ProjectReference projectReference) {
			String version = NO_VERSION;
			if (projectReference instanceof ProjectDependency)
				version = npmFormat(((ProjectDependency) projectReference).getVersionConstraint());
			else if (projectReference instanceof TestedProject)
				version = npmFormat(((TestedProject) projectReference).getVersionConstraint());

			return version;
		}

	}

	/**
	 * Calculates missing dependencies based on current workspace and library manager state.
	 *
	 * Names of the dependencies are not validated. Also there is no version resolution, last one wins.
	 *
	 * @return map of {@code id->version} dependency infos
	 */
	public Map<String, String> calculateMissingDependnecies() {
		final Set<String> availableProjectsIds = new HashSet<>();
		final Map<String, String> versionedDependnecies = new HashMap<>();
		core.findAllProjects().forEach(p -> {
			availableProjectsIds.add(p.getProjectId());
			updateFromProject(versionedDependnecies, p);
		});

		availableProjectsIds.forEach(versionedDependnecies::remove);
		return versionedDependnecies;
	}

	/**
	 * Updates provided mapping of {@code id->version} with information computed from the provided project.
	 *
	 * Note that {@code ids} of the returned dependencies are not validated.
	 *
	 * Note that in case of dependency being defined in multiple places of the dependency graph only one mapping will be
	 * present. In case of different versions simple resolution is performed, last found with non empty version is used
	 * (where "latest" is not ).
	 */
	private void updateFromProject(Map<String, String> dependencies, IN4JSProject project) {
		ProjectDescription projectDescription = getProjectDescription(project.getLocation());
		if (projectDescription != null) {
			Stream.of(getVersionedDependencies(projectDescription),
					// TODO GH-613, user projects can be misconfigured
					getVersionedRequiredRuntimeLibraries(projectDescription),
					getVersionedProvidedRuntimeLibraries(projectDescription),
					getVersionedExtendedRuntimeEnvironment(projectDescription),
					getVersionedTestedProjects(projectDescription),
					getVersionedImplementedProjects(projectDescription))
					.reduce(Stream::concat)
					.orElseGet(Stream::empty)
					.forEach(info -> dependencies.merge(info.getID(), info.getVersion(), DependencyInfo::resolve));
		}
	}

	/** get id-version information about dependencies */
	private Stream<DependencyInfo> getVersionedDependencies(ProjectDescription description) {
		ProjectDependencies projectDependencies = description.getProjectDependencies();
		if (projectDependencies != null)
			return projectDependencies.getProjectDependencies().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private Stream<DependencyInfo> getVersionedRequiredRuntimeLibraries(ProjectDescription description) {
		RequiredRuntimeLibraries runtimeLibraries = description.getRequiredRuntimeLibraries();
		if (runtimeLibraries != null)
			return runtimeLibraries.getRequiredRuntimeLibraries().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private Stream<DependencyInfo> getVersionedProvidedRuntimeLibraries(ProjectDescription description) {
		ProvidedRuntimeLibraries projectDependencies = description.getProvidedRuntimeLibraries();
		if (projectDependencies != null)
			return projectDependencies.getProvidedRuntimeLibraries().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private Stream<DependencyInfo> getVersionedExtendedRuntimeEnvironment(ProjectDescription description) {
		ExtendedRuntimeEnvironment projectDependencies = description.getExtendedRuntimeEnvironment();
		if (projectDependencies != null)
			return Stream.of(projectDependencies.getExtendedRuntimeEnvironment()).map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private Stream<DependencyInfo> getVersionedTestedProjects(ProjectDescription description) {
		TestedProjects projectDependencies = description.getTestedProjects();
		if (projectDependencies != null)
			return projectDependencies.getTestedProjects().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private Stream<DependencyInfo> getVersionedImplementedProjects(ProjectDescription description) {
		ImplementedProjects projectDependencies = description.getImplementedProjects();
		if (projectDependencies != null)
			return projectDependencies.getImplementedProjects().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** Mimic hidden {@link N4JSModel#getProjectDescription(URI)}. */
	@SuppressWarnings("javadoc")
	private ProjectDescription getProjectDescription(URI location) {
		ProjectDescription description = workspace.getProjectDescription(location);
		if (null == description) {
			description = externalLibraryWorkspace.getProjectDescription(location);
		}
		return description;
	}

}
