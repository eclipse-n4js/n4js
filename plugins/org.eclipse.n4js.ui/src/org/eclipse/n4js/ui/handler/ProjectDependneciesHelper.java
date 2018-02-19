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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.libraries.TargetPlatformModel;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
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
class ProjectDependneciesHelper {
	private static final Logger LOGGER = Logger.getLogger(ProjectDependneciesHelper.class);

	@Inject
	private EclipseBasedN4JSWorkspace workspace;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private IN4JSCore core;

	/**
	 * Creates map of dependency-version calculated based on the selected target platform file, workspace projects and
	 * shipped external libraries. Note that list of dependencies is combined, but in case of conflicting versions info,
	 * workspace based data wins, e.g.
	 *
	 * <pre>
	 *  <ul>
	 *   <li> platform files requests "express" but no project depends on "express" then express is installed (in latest version)</li>
	 *   <li> platform files requests "express@2.0.0" but workspace project depends on "express@1.0.0"  then express is installed in version "2.0.0"</li>
	 *  <ul>
	 * </pre>
	 */
	public Map<String, String> calculateDependenciesToInstall(File selectedN4TP) {
		Map<String, String> versionedPackages = populateFromPlatformFile(selectedN4TP);
		updateMissingDependneciesMap(versionedPackages);
		if (LOGGER.isDebugEnabled()) {
			StringJoiner messages = new StringJoiner(System.lineSeparator());
			messages.add("dependencies to install: ");
			versionedPackages.forEach((id, v) -> messages.add(" - " + id + v));
			LOGGER.debug(messages);
		}
		return versionedPackages;
	}

	/** @return dependencies to install based on the platform file. */
	private Map<String, String> populateFromPlatformFile(File n4tp) {
		if (n4tp != null) {
			try {
				final java.net.URI platformFileLocation = n4tp.toURI();
				Map<String, String> n4tpPackages;
				n4tpPackages = TargetPlatformModel
						.npmVersionedPackageNamesFrom(platformFileLocation);
				return n4tpPackages;
			} catch (IOException e) {
				LOGGER.error("Cannot read platform file", e);
			}
		}
		return new HashMap<>();
	}

	/**
	 * Calculates missing dependencies based on current workspace and library manager state.
	 *
	 * Names of the dependencies are not validated. Also there is no version resolution, last one wins.
	 */
	private void updateMissingDependneciesMap(Map<String, String> versionedPackages) {
		final Set<String> availableProjectsIds = new HashSet<>();
		core.findAllProjects().forEach(p -> {
			availableProjectsIds.add(p.getProjectId());
			updateFromProject(versionedPackages, p);
		});

		availableProjectsIds.forEach(versionedPackages::remove);
	}

	/**
	 * Updates provided mapping of {@code id->version} with information computed from the provided project.
	 *
	 * Note that {@code ids} of the returned dependencies are not validated.
	 *
	 * Note that in case of dependency being defined in multiple places of the dependency graph only one mapping will be
	 * present. In case of different versions simple resolution is performed, first found with non empty version is
	 * used.
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
					.forEach(info -> dependencies.merge(info.id, info.version, DependencyInfo::resolve));
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
