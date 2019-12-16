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

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDescription;
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

	/** */
	@Inject
	protected NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;
	/** */
	@Inject
	protected ProjectDescriptionLoader projectDescriptionLoader;

	/**
	 * Collections all projects and uses the approach described above with each of the given workspace root folders.
	 * <p>
	 * Note that the dependencies (i.e. projects in {@code node_modules} folders) are listed after all workspace
	 * projects.
	 */
	public LinkedHashSet<Path> collectAllProjectDirs(Path... workspaceRoots) {
		Map<Path, ProjectDescription> pdCache = new HashMap<>();

		LinkedHashSet<Path> allProjectDirs = collectAllProjects(workspaceRoots, pdCache);
		LinkedHashSet<Path> dependencies = collectAllDependencies(allProjectDirs, pdCache);
		allProjectDirs.addAll(dependencies);

		return allProjectDirs;
	}

	/** Searches all projects in the given array of workspace directories */
	protected LinkedHashSet<Path> collectAllProjects(Path[] workspaceRoots, Map<Path, ProjectDescription> pdCache) {
		LinkedHashSet<Path> allProjectDirs = new LinkedHashSet<>();
		for (Path wsRoot : workspaceRoots) {
			NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(wsRoot, pdCache);

			if (nodeModulesFolder == null) {
				// Is neither NPM nor Yarn project
				collectProjects(wsRoot, false, pdCache, allProjectDirs);
			} else {
				if (nodeModulesFolder.isYarnWorkspace) {
					// Is Yarn project
					// use projects referenced in packages
					Path yarnProjectDir = nodeModulesFolder.nodeModulesFolder.getParentFile().toPath();
					allProjectDirs.add(yarnProjectDir);
					collectYarnWorkspaceProjects(yarnProjectDir, pdCache, allProjectDirs);
				} else {
					// Is NPM project
					// given directory is a stand-alone npm project
					allProjectDirs.add(wsRoot);
				}
			}
		}
		return allProjectDirs;
	}

	/** Searches all dependencies (ie. npm projects) of the given set of projects */
	protected LinkedHashSet<Path> collectAllDependencies(LinkedHashSet<Path> allProjectDirs,
			Map<Path, ProjectDescription> pdCache) {

		LinkedHashSet<Path> dependencies = new LinkedHashSet<>();

		List<Path> nodeModulesFolders = nodeModulesDiscoveryHelper.findNodeModulesFolders(allProjectDirs, pdCache);

		for (Path nmFolder : new LinkedHashSet<>(nodeModulesFolders)) {
			collectProjects(nmFolder, true, pdCache, dependencies);
		}
		return dependencies;
	}

	private void collectYarnWorkspaceProjects(Path yarnProjectRoot, Map<Path, ProjectDescription> pdCache,
			Set<Path> projects) {

		ProjectDescription projectDescription = getCachedProjectDescription(yarnProjectRoot, pdCache);
		final List<String> workspaces = (projectDescription == null) ? null : projectDescription.getWorkspaces();
		if (workspaces == null) {
			return;
		}

		for (String workspaceGlob : workspaces) {
			collectGlobMatches(workspaceGlob, yarnProjectRoot, pdCache, projects);
		}
	}

	private void collectProjects(Path root, boolean includeSubtree, Map<Path, ProjectDescription> pdCache,
			Set<Path> allProjectDirs) {

		if (!root.toFile().isDirectory()) {
			return;
		}

		FileVisitResult defaultReturn;
		int depth;
		if (includeSubtree) {
			defaultReturn = CONTINUE;
			depth = Integer.MAX_VALUE;
		} else {
			defaultReturn = SKIP_SUBTREE;
			depth = 2;
		}
		try {
			EnumSet<FileVisitOption> none = EnumSet.noneOf(FileVisitOption.class);
			Files.walkFileTree(root, none, depth, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (root.equals(dir)) {
						return FileVisitResult.CONTINUE;
					}

					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
					if (pckJson.isFile()) {
						if (!root.endsWith(N4JSGlobals.NODE_MODULES)
								&& nodeModulesDiscoveryHelper.isYarnWorkspaceRoot(dir.toFile(), pdCache)) {
							collectYarnWorkspaceProjects(dir, pdCache, allProjectDirs);
						} else {
							allProjectDirs.add(dir);
						}
						return FileVisitResult.SKIP_SUBTREE;
					}

					return defaultReturn;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void collectGlobMatches(String glob, Path location, Map<Path, ProjectDescription> pdCache,
			Set<Path> allProjectDirs) {

		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + location.resolve(glob));
		try {
			Files.walkFileTree(location, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}
					if (pathMatcher.matches(dir)) {
						Path dirName = dir.getName(dir.getNameCount() - 1);
						if (dirName.toString().startsWith("@")) {
							collectProjects(dir, false, pdCache, allProjectDirs);
						} else {
							File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
							if (pckJson.isFile()) {
								allProjectDirs.add(dir);
							}
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** @return the potentially cached {@link ProjectDescription} for the project in the given directory */
	protected ProjectDescription getCachedProjectDescription(Path path, Map<Path, ProjectDescription> cache) {
		if (!cache.containsKey(path)) {
			FileURI uri = new FileURI(path.toFile());
			ProjectDescription depPD = projectDescriptionLoader.loadProjectDescriptionAtLocation(uri);
			cache.put(path, depPD);
		}

		return cache.get(path);
	}

}
