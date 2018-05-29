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
package org.eclipse.n4js.hlc.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.generator.headless.HeadlessHelper;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Headless version of the org.eclipse.n4js.ui.handler.ProjectDependenciesHelper
 */
class DependenciesHelper {

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private FileBasedWorkspace n4jsFileBasedWorkspace;

	@Inject
	private HeadlessHelper headlessHelper;

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

	/**
	 * Discovers projects in the provided locations and projects containing provided files, then returns missing
	 * dependencies of those projects.
	 *
	 * Simplified version of the
	 * {@code org.eclipse.n4js.generator.headless.N4HeadlessCompiler.collectAndRegisterProjects(List<File>,
	 * List<File>, List<File>)}
	 *
	 * This implementations analyzes all available projects, without checking which were directly requested by the user.
	 */
	Map<String, String> discoverMissingDependencies(String projectLocations, List<File> srcFiles) {

		List<File> allProjectsRoots = new ArrayList<>();

		List<File> locations = new ArrayList<>();
		locations.addAll(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider));
		if (projectLocations != null) {
			locations.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));
		}
		// Discover projects in search paths.
		List<File> discoveredProjectLocations = headlessHelper.collectAllProjectPaths(locations);

		// Discover projects for single source files.
		List<File> singleSourceProjectLocations = new ArrayList<>();
		try {
			singleSourceProjectLocations
					.addAll(headlessHelper.findProjectsForSingleFiles(srcFiles, n4jsFileBasedWorkspace));
		} catch (N4JSCompileException e) {
			System.err.println(Throwables.getStackTraceAsString(e));
		}

		allProjectsRoots.addAll(discoveredProjectLocations);
		allProjectsRoots.addAll(singleSourceProjectLocations);

		Map<String, String> dependencies = new HashMap<>();

		DependenciesCollectingUtil.updateMissingDependenciesMap(dependencies,
				getAvailableProjectDescriptions(allProjectsRoots));

		return dependencies;
	}

	private Iterable<ProjectDescription> getAvailableProjectDescriptions(List<File> allProjectsRoots) {
		List<ProjectDescription> descriptions = new ArrayList<>();
		allProjectsRoots.forEach(root -> {
			final ProjectDescription projectDescription = projectDescriptionHelper
					.loadProjectDescriptionAtLocation(URIUtils.toFileUri(root.toURI()));

			if (projectDescription == null) {
				System.out.println("Cannot load project description for project at " + root.getPath()
						+ ". Make sure the project contains a valid package.json file.");
				return;
			}
			descriptions.add(projectDescription);

		});
		return descriptions;
	}

}
