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
package org.eclipse.n4js.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;

import com.google.inject.Inject;

/**
 * Given a workspace root directory, this class finds npm and yarn projects as follows:
 * <ol>
 * <li>Find projects:
 * <ol>
 * <li/>Find a package.json in the workspace root directory or above (called root package.json file).
 * <li/>Iff a root package.json was found and this project is a yarn workspace project, find all referenced
 * {@code packages} projects.
 * <li/>Iff a root package.json was found and this project is not yarn workspace project, return this single project.
 * <li/>Iff no root package.json could be found, find all package json in sub-directories (called project package.json
 * files). Skip directories called node_modules.
 * </ol>
 * </li>
 * <li>Sort projects by name (ascending)
 * <li>Add projects:
 * <ol>
 * <li/>Add a found project to the result set of all projects
 * <li/>Determine if a found project is a yarn or npm project. In case of a yarn project, add also all referenced
 * projects (referenced in the workspace property).
 * <li/>Do not overwrite already found projects.
 * </ol>
 * </li>
 * <li>Add dependencies:
 * <ol>
 * <li/>Find the node_modules folder of a project and add all direct dependencies to the set of dependencies.
 * <li/>Do not overwrite already found dependencies.
 * <li/>Projects inside of dependencies are never considered.
 * </ol>
 * </li>
 * <li>Merge projects and dependencies
 * <ol>
 * <li/>All projects are added to the resulting set.
 * <li/>Those dependencies are added to the resulting set that do not clash with already existing projects.
 * </ol>
 * </li>
 * <li/>Emits warnings for all projects that were not found in the given root directory or one of its sub-directories.
 * </ol>
 */
public class ProjectDiscoveryHelper {

	@Inject
	NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;
	@Inject
	ProjectDescriptionLoader projectDescriptionLoader;

	/**
	 * Collections all projects and uses the approach described above with each of the given workspace root folders.
	 * <p>
	 * Note that the dependencies (i.e. projects in {@code node_modules} folders) are listed after all workspace
	 * projects.
	 */
	public LinkedHashSet<Path> collectAllProjectDirs(Path... workspaceRoots) {
		LinkedHashSet<Path> allProjectDirs = new LinkedHashSet<>();

		for (Path workspaceRoot : workspaceRoots) {
			NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(workspaceRoot);

			if (nodeModulesFolder == null) {
				// Is neither NPM nor Yarn project
				LinkedHashSet<Path> standAloneProjects = collectProjects(workspaceRoot);
				allProjectDirs.addAll(standAloneProjects);
			} else {
				if (nodeModulesFolder.isYarnWorkspace) {
					// Is Yarn project
					// use projects referenced in packages
					Path yarnProjectDir = nodeModulesFolder.nodeModulesFolder.getParentFile().toPath();
					allProjectDirs.addAll(collectYarnWorkspaceProjects(yarnProjectDir));
				} else {
					// Is NPM project
					// given directory is a stand-alone npm project
					allProjectDirs.add(workspaceRoot);
				}
			}
		}

		List<Path> nodeModulesFolders = nodeModulesDiscoveryHelper.findNodeModulesFolders(allProjectDirs);
		for (Path nmFolder : nodeModulesFolders) {
			LinkedHashSet<Path> dependencies = collectProjects(nmFolder);
			allProjectDirs.addAll(dependencies);
		}

		return allProjectDirs;
	}

	private LinkedHashSet<Path> collectYarnWorkspaceProjects(Path yarnProjectRoot) {
		LinkedHashSet<Path> allProjectDirs = new LinkedHashSet<>();
		FileURI uri = new FileURI(yarnProjectRoot.toFile());
		List<String> workspaces = projectDescriptionLoader.loadWorkspacesFromProjectDescriptionAtLocation(uri);

		for (String workspace : workspaces) {
			Path workspacePath = yarnProjectRoot.resolve(workspace);
			collectProjects(workspacePath);
		}

		return allProjectDirs;
	}

	private LinkedHashSet<Path> collectProjects(Path root) {
		LinkedHashSet<Path> allProjectDirs = new LinkedHashSet<>();

		try {
			Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
					if (pckJson.isFile()) {
						if (nodeModulesDiscoveryHelper.isYarnWorkspaceRoot(dir.toFile())) {
							allProjectDirs.addAll(collectYarnWorkspaceProjects(dir));
						} else {
							allProjectDirs.add(dir);
						}
						return FileVisitResult.SKIP_SUBTREE;
					}

					return super.preVisitDirectory(dir, attrs);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return allProjectDirs;
	}

}
