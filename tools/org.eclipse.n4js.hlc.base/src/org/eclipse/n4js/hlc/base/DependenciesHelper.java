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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.URIUtils;

import com.google.inject.Inject;

/**
 * Headless version of the org.eclipse.n4js.ui.handler.ProjectDependenciesHelper
 */
class DependenciesHelper {

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
	Map<String, String> discoverMissingDependencies(Iterable<? extends IN4JSProject> allProjects) {

		final Iterable<ProjectDescription> allProjectDescriptions = getAvailableProjectDescriptions(
				allProjects);
		final Map<String, String> dependencies = new HashMap<>();
		DependenciesCollectingUtil.updateMissingDependenciesMap(dependencies, allProjectDescriptions);

		return dependencies;
	}

	private Iterable<ProjectDescription> getAvailableProjectDescriptions(Iterable<? extends IN4JSProject> allProjects) {
		List<ProjectDescription> descriptions = new ArrayList<>();
		StreamSupport.stream(allProjects.spliterator(), false)
				.map(p -> p.getLocationPath().toFile())
				.forEach(root -> {
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
