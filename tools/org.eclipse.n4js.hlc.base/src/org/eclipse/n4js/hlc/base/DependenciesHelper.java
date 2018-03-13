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
import org.eclipse.n4js.n4mf.utils.parsing.ProjectDescriptionProviderUtil;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;

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
		locations.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));
		// Discover projects in search paths.
		List<File> discoveredProjectLocations = HeadlessHelper.collectAllProjectPaths(locations);

		// Discover projects for single source files.
		List<File> singleSourceProjectLocations = new ArrayList<>();
		try {
			singleSourceProjectLocations
					.addAll(HeadlessHelper.findProjectsForSingleFiles(srcFiles, n4jsFileBasedWorkspace));
		} catch (N4JSCompileException e) {
			System.err.println(Throwables.getStackTraceAsString(e));
		}

		allProjectsRoots.addAll(discoveredProjectLocations);
		allProjectsRoots.addAll(singleSourceProjectLocations);

		Map<String, String> dependencies = new HashMap<>();

		DependenciesCollectingUtil.updateMissingDependneciesMap(dependencies,
				getAvailableProjectsDescriptions(allProjectsRoots));

		return dependencies;
	}

	private Iterable<ProjectDescription> getAvailableProjectsDescriptions(List<File> allProjectsRoots) {
		List<ProjectDescription> descriptions = new ArrayList<>();
		allProjectsRoots.forEach(root -> {
			File manifest = new File(root, IN4JSProject.N4MF_MANIFEST);
			if (!manifest.isFile()) {
				System.out.println("Cannot read manifest at " + root);
				return;
			}
			ProjectDescription projectDescription = ProjectDescriptionProviderUtil.getFromFile(manifest);
			descriptions.add(projectDescription);

		});
		return descriptions;
	}

}
