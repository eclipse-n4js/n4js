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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.Iterables;

/**
 * Allows to access data in the manifests of the provided {@link IN4JSProject}s. Unlike {@link N4JSModel} will return
 * data that describes unknown or missing projects. of known projects, and not arbitrary {@code .n4mf} file.
 */
public class ProjectDependenciesHelper {
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
	 *
	 * Will take into account only projects in workspace, not external projects (neither NPM packages nor shipped code).
	 */
	public Map<String, String> calculateDependenciesToInstall() {
		Map<String, String> versionedPackages = new HashMap<>();
		DependenciesCollectingUtil.updateMissingDependenciesMap(versionedPackages,
				getAvailableProjectsDescriptions(false));
		if (LOGGER.isDebugEnabled()) {
			StringJoiner messages = new StringJoiner(System.lineSeparator());
			messages.add("dependencies to install: ");
			versionedPackages.forEach((id, v) -> messages.add(" - " + id + v));
			LOGGER.debug(messages);
		}
		return versionedPackages;
	}

	/**
	 * Returns available project descriptions either in the workspace (if 'external' is <code>false</code>) or in the
	 * library manager (if 'external' is <code>true</code>).
	 */
	public Iterable<ProjectDescription> getAvailableProjectsDescriptions(boolean external) {
		return IterableExtensions.map(
				Iterables.filter(core.findAllProjects(), p -> p.isExternal() == external),
				this::getProjectDescription);
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
