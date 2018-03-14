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
package org.eclipse.n4js.projectModel.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
import org.eclipse.n4js.n4mf.TestedProjects;

/**
 * Utility for collecting project dependencies. Focused on reading {@link ProjectDescription} only, and does not check
 * data against workspace or library manager.
 */
public class DependenciesCollectingUtil {

	/**
	 * Updates provided mapping of {@code id->version} with information computed from the provided project descriptions.
	 * Returned map will not contain entries where the key matches {@code ProjectDescription#getProjectId() id} of the
	 * processed descriptions.
	 *
	 * Note that {@code ids} of the returned dependencies are not validated.
	 *
	 * Note that in case of dependency being defined in multiple places of the dependency graph only one mapping will be
	 * present. In case of different versions simple resolution is performed, first found with non empty version is
	 * used.
	 */
	public static void updateMissingDependneciesMap(Map<String, String> versionedPackages,
			Iterable<ProjectDescription> projectDescriptions) {
		final Set<String> availableProjectsIds = new HashSet<>();
		projectDescriptions.forEach(pd -> {
			// in case we get non N4JS projects, user docs projects that are not N4JS projects, or something created by
			// the plugins e.g RemoteSystemsTempFiles (see https://stackoverflow.com/q/3627463/52564 )
			if (pd != null) {
				availableProjectsIds.add(pd.getProjectId());
				updateFromProjectDescription(versionedPackages, pd);
			}
		});
		availableProjectsIds.forEach(versionedPackages::remove);
	}

	/** Add to the provided map all possible dependencies based on the {@link ProjectDescription} */
	private static void updateFromProjectDescription(Map<String, String> dependencies,
			ProjectDescription projectDescription) {
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
					.forEach(info -> dependencies.merge(info.name, info.version, DependencyInfo::resolve));
		}
	}

	/** get id-version information about dependencies */
	private static Stream<DependencyInfo> getVersionedDependencies(ProjectDescription description) {
		ProjectDependencies projectDependencies = description.getProjectDependencies();
		if (projectDependencies != null)
			return projectDependencies.getProjectDependencies().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedRequiredRuntimeLibraries(ProjectDescription description) {
		RequiredRuntimeLibraries runtimeLibraries = description.getRequiredRuntimeLibraries();
		if (runtimeLibraries != null)
			return runtimeLibraries.getRequiredRuntimeLibraries().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedProvidedRuntimeLibraries(ProjectDescription description) {
		ProvidedRuntimeLibraries projectDependencies = description.getProvidedRuntimeLibraries();
		if (projectDependencies != null)
			return projectDependencies.getProvidedRuntimeLibraries().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedExtendedRuntimeEnvironment(ProjectDescription description) {
		ExtendedRuntimeEnvironment projectDependencies = description.getExtendedRuntimeEnvironment();
		if (projectDependencies != null)
			return Stream.of(projectDependencies.getExtendedRuntimeEnvironment()).map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedTestedProjects(ProjectDescription description) {
		TestedProjects projectDependencies = description.getTestedProjects();
		if (projectDependencies != null)
			return projectDependencies.getTestedProjects().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedImplementedProjects(ProjectDescription description) {
		ImplementedProjects projectDependencies = description.getImplementedProjects();
		if (projectDependencies != null)
			return projectDependencies.getImplementedProjects().stream().map(DependencyInfo::create);
		return Stream.empty();
	}

}
