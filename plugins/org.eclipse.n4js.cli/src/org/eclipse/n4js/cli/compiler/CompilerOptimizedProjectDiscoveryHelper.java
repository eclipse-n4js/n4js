/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.compiler;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;

/**
 * This {@link ProjectDiscoveryHelper} will <b>not find all</b> dependencies but only those that are actually necessary
 * for compiling all the projects found in the given set of workspace directories.
 * <p>
 * Necessary project dependencies are those that are either N4JS projects or non-N4JS projects that are direct
 * dependencies of N4JS projects.
 */
public class CompilerOptimizedProjectDiscoveryHelper extends ProjectDiscoveryHelper {

	@Override
	protected LinkedHashSet<Path> collectAllDependencies(LinkedHashSet<Path> allProjectDirs,
			Map<Path, ProjectDescription> pdCache) {

		LinkedHashSet<Path> dependencies = new LinkedHashSet<>();

		for (Path nextProject : allProjectDirs) {
			collectProjectDependencies(nextProject, pdCache, dependencies);
		}

		return dependencies;
	}

	/** Collects all (transitive) dependencies of the given project */
	private void collectProjectDependencies(Path projectDir, Map<Path, ProjectDescription> pdCache,
			LinkedHashSet<Path> dependencies) {

		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectDir, pdCache);
		Path yarnNodeModules = (nodeModulesFolder != null && nodeModulesFolder.isYarnWorkspace)
				? nodeModulesFolder.nodeModulesFolder.toPath()
				: null;

		LinkedHashSet<Path> workList = new LinkedHashSet<>();
		workList.add(projectDir);
		while (!workList.isEmpty()) {
			Iterator<Path> iterator = workList.iterator();
			Path nextProject = iterator.next();
			iterator.remove();

			findDependencies(nextProject, yarnNodeModules, pdCache, workList, dependencies);
		}
	}

	private void findDependencies(Path prjDir, Path yarnNodeModules, Map<Path, ProjectDescription> pdCache,
			Set<Path> workList, Set<Path> dependencies) {

		Path prjNodeModules = prjDir.resolve(N4JSGlobals.NODE_MODULES);

		ProjectDescription prjDescr = getCachedProjectDescription(prjDir, pdCache);
		if (prjDescr == null) {
			return;
		}

		for (ProjectDependency dependency : prjDescr.getProjectDependencies()) {
			String depName = dependency.getProjectName();

			// Always use dependency of yarn workspace node_modules folder if it is available
			// TODO GH-1314, remove shadow logic here
			Path depLocation = (yarnNodeModules == null)
					? prjNodeModules.resolve(depName)
					: yarnNodeModules.resolve(depName);

			if (!prjDir.equals(depLocation)) {
				addDependency(depLocation, pdCache, workList, dependencies);
			}
		}

	}

	private void addDependency(Path depLocation, Map<Path, ProjectDescription> pdCache, Set<Path> workList,
			Set<Path> dependencies) {

		if (dependencies.contains(depLocation)) {
			return;
		}

		Path packageJson = depLocation.resolve(N4JSGlobals.PACKAGE_JSON);
		if (packageJson.toFile().isFile()) {
			dependencies.add(depLocation);

			ProjectDescription depPD = getCachedProjectDescription(depLocation, pdCache);
			if (depPD != null && depPD.isHasN4JSNature()) {
				workList.add(depLocation);
			}
		}
	}
}
