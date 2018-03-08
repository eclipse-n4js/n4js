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
import java.util.Map;
import java.util.StringJoiner;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.libraries.TargetPlatformModel;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Allows to access data in the manifests of the provided {@link IN4JSProject}s. Unlike {@link N4JSModel} will return
 * data that describes unknown or missing projects. Unlike {@link ManifestValuesParsingUtil} it allows to read manifest
 * of known projects, and not arbitrary {@code .n4mf} file.
 */
class ProjectDependenciesHelper {
	private static final Logger LOGGER = Logger.getLogger(ProjectDependenciesHelper.class);

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
		DependenciesCollectingUtil.updateMissingDependneciesMap(versionedPackages, getAvailableProjectsDescriptions());
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

	private Iterable<ProjectDescription> getAvailableProjectsDescriptions() {
		return IterableExtensions.map(core.findAllProjects(), this::getProjectDescription);
	}

	private ProjectDescription getProjectDescription(IN4JSProject project) {
		return getProjectDescription(project.getLocation());
	}

	/** Mimic hidden {@link N4JSModel#getProjectDescription(URI)}. */
	@SuppressWarnings("javadoc")
	private ProjectDescription getProjectDescription(URI location) {
		ProjectDescription description = workspace.getProjectDescription(location);
		if (null == description)
			description = externalLibraryWorkspace.getProjectDescription(location);

		return description;
	}

}
