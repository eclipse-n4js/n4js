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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;

import com.google.inject.Inject;

/**
 * Given a workspace root directory, this class finds npm and yarn projects. Plain-JS projects are included only if
 * there is an N4JS project with a dependency to this plain-JS project, except for yarn workspace root projects, which
 * are always included.
 * <p>
 * Projects are collected as follows:
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
 * <li/>Determine whether a found project is a yarn or npm project. In case of a yarn project, add also all referenced
 * projects (referenced in the workspace property).
 * <li/>Do not overwrite already found projects.
 * </ol>
 * </li>
 * <li>Add dependencies:
 * <ol>
 * <li/>Find and add the dependencies of every project.
 * <li/>Do not overwrite already found dependencies.
 * <li/>Projects inside of dependencies are never considered.
 * <li/>TODO GH-1314: Note that projects of different versions are not yet supported. Instead, only the top level npm
 * will be used.
 * <li/>All dependencies of plain-JS projects are omitted, since they are not necessary for building N4JS sources.
 * </ol>
 * </li>
 * <li>Merge projects and dependencies
 * <ol>
 * <li/>All projects are added to the resulting set.
 * <li/>Those dependencies are added to the resulting set that do not clash with already existing projects.
 * </ol>
 * </li>
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
	 * Collects all projects and uses the approach described above with each of the given workspace root folders.
	 * <p>
	 * Note that the dependencies (i.e. projects in {@code node_modules} folders) are listed after all workspace
	 * projects.
	 */
	public LinkedHashSet<Path> collectAllProjectDirs(Path... workspaceRoots) {
		Map<Path, ProjectDescription> pdCache = new HashMap<>();

		Map<String, Path> allProjectDirs = collectAllProjects(workspaceRoots, pdCache);

		Map<String, Path> dependencies = collectNecessaryDependencies(allProjectDirs, pdCache);
		dependencies.forEach(allProjectDirs::putIfAbsent);

		return new LinkedHashSet<>(allProjectDirs.values());
	}

	/** Searches all projects in the given array of workspace directories */
	private Map<String, Path> collectAllProjects(Path[] workspaceRoots, Map<Path, ProjectDescription> pdCache) {
		Map<String, Path> allProjectDirs = new LinkedHashMap<>();
		for (Path wsRoot : workspaceRoots) {

			Path projectRoot = getProjectRootOrUnchanged(wsRoot);

			NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectRoot, pdCache);

			if (nodeModulesFolder == null) {
				// Is neither NPM nor Yarn project
				collectProjects(projectRoot, true, pdCache, allProjectDirs);
			} else {
				if (nodeModulesFolder.isYarnWorkspaceRoot) {
					// Is Yarn project
					// use projects referenced in packages
					Path yarnProjectDir = nodeModulesFolder.workspaceNodeModulesFolder.getParentFile().toPath();
					collectYarnWorkspaceProjects(yarnProjectDir, pdCache, allProjectDirs);
				} else {
					// Is NPM project
					// given directory is a stand-alone npm project
					addIfNotPlainjs(allProjectDirs, projectRoot, pdCache);
				}
			}
		}
		return allProjectDirs;
	}

	private Path getProjectRootOrUnchanged(Path dir) {
		for (Path projectRoot = dir; projectRoot != null; projectRoot = projectRoot.getParent()) {
			Path pckjson = projectRoot.resolve(N4JSGlobals.PACKAGE_JSON);
			if (pckjson.toFile().isFile()) {
				return projectRoot;
			}
		}
		return dir;
	}

	/**
	 * Adds all paths to projects to the given set of {@code projects} that match the workspace property of the given
	 * yarn workspace project.
	 *
	 * @implNote Accessibility may be reduced to private again after GH-1666 was done.
	 */
	public void collectYarnWorkspaceProjects(Path yarnProjectRoot, Map<Path, ProjectDescription> pdCache,
			Map<String, Path> allProjectDirs) {

		// add the yarn workspace root project even if it is a PLAINJS project
		// Rationale:
		// 1) otherwise not possible for later code to distinguish between yarn workspace and side-by-side use cases,
		// 2) having the workspace root project or not makes a huge difference (projects exist inside projects) and it
		// is better to always stick to one situation (otherwise many tests would have to be provided in two variants),
		// 3) the yarn workspace root project has always been included.
		allProjectDirs.putIfAbsent(getProjectNameFromPath(yarnProjectRoot), yarnProjectRoot);

		ProjectDescription projectDescription = getCachedProjectDescription(yarnProjectRoot, pdCache);
		final List<String> workspaces = (projectDescription == null) ? null : projectDescription.getWorkspaces();
		if (workspaces == null) {
			return;
		}

		Map<String, Path> memberProjects = new LinkedHashMap<>();
		for (String workspaceGlob : workspaces) {
			collectGlobMatches(workspaceGlob, yarnProjectRoot, pdCache, memberProjects);
		}
		removeUnnecessaryPlainjsProjects(memberProjects, pdCache);
		memberProjects.forEach(allProjectDirs::putIfAbsent);
	}

	private void collectProjects(Path root, boolean includeSubtree, Map<Path, ProjectDescription> pdCache,
			Map<String, Path> allProjectDirs) {

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
			depth = 3;
		}
		try {
			EnumSet<FileVisitOption> none = EnumSet.noneOf(FileVisitOption.class);
			Files.walkFileTree(root, none, depth, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (root.equals(dir)) {
						return FileVisitResult.CONTINUE;
					}
					if (dir.toFile().getName().startsWith("@")) {
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
							addIfNotPlainjs(allProjectDirs, dir, pdCache);
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
			Map<String, Path> allProjectDirs) {

		int depth = glob.contains("**") ? Integer.MAX_VALUE : glob.split("/").length + 1;

		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + location.resolve(glob));
		try {
			EnumSet<FileVisitOption> none = EnumSet.noneOf(FileVisitOption.class);
			Files.walkFileTree(location, none, depth, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (pathMatcher.matches(dir)) {
						Path dirName = dir.getName(dir.getNameCount() - 1);
						if (dirName.toString().startsWith("@")) {
							collectProjects(dir, false, pdCache, allProjectDirs);
							return FileVisitResult.SKIP_SUBTREE;

						} else {
							File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
							if (pckJson.isFile()) {
								// note: add 'dir' to 'allProjectDirs' even if it is PLAINJS (will be taken care of by
								// #removeUnnecessaryPlainjsProjects() below)
								allProjectDirs.putIfAbsent(getProjectNameFromPath(dir), dir);
								return FileVisitResult.SKIP_SUBTREE;
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

	/**
	 * Removes from <code>projects</code> all "unnecessary" {@link ProjectType#PLAINJS PLAINJS} projects. A PLAINJS
	 * project is "unnecessary" if there does not exist an N4JS project P' in <code>projects</code> that has a
	 * dependency to P.
	 */
	private void removeUnnecessaryPlainjsProjects(Map<String, Path> projects, Map<Path, ProjectDescription> pdCache) {
		Map<String, Path> plainjsProjects = new HashMap<>();
		Set<String> projectsRequiredByAnN4JSProject = new HashSet<>();
		for (Path project : projects.values()) {
			ProjectDescription pd = getCachedProjectDescription(project, pdCache);
			if (pd == null) {
				continue;
			}
			ProjectType type = pd.getProjectType();
			if (type == ProjectType.PLAINJS) {
				plainjsProjects.put(pd.getProjectName(), project);
			} else {
				List<String> deps = pd.getProjectDependencies().stream()
						.map(ProjectReference::getProjectName).collect(Collectors.toList());
				projectsRequiredByAnN4JSProject.addAll(deps);
			}
		}
		plainjsProjects.keySet().removeAll(projectsRequiredByAnN4JSProject);
		projects.keySet().removeAll(plainjsProjects.keySet());
	}

	private void addIfNotPlainjs(Map<String, Path> addHere, Path project, Map<Path, ProjectDescription> pdCache) {
		ProjectDescription pd = getCachedProjectDescription(project, pdCache);
		if (pd != null && pd.getProjectType() != ProjectType.PLAINJS) {
			addHere.putIfAbsent(getProjectNameFromPath(project), project);
		}
	}

	/** @return the potentially cached {@link ProjectDescription} for the project in the given directory */
	private ProjectDescription getCachedProjectDescription(Path path, Map<Path, ProjectDescription> cache) {
		if (!cache.containsKey(path)) {
			FileURI uri = new FileURI(path.toFile());
			ProjectDescription depPD = projectDescriptionLoader.loadProjectDescriptionAtLocation(uri);
			cache.put(path, depPD);
		}

		return cache.get(path);
	}

	/** Searches all dependencies (ie. npm projects) of the given set of projects */
	@SuppressWarnings("unused")
	private Map<String, Path> collectAllDependencies(Map<String, Path> allProjectDirs,
			Map<Path, ProjectDescription> pdCache) {

		Map<String, Path> dependencies = new LinkedHashMap<>();
		List<Path> nodeModulesFolders = nodeModulesDiscoveryHelper.findNodeModulesFolders(allProjectDirs.values(),
				pdCache);

		for (Path nmFolder : new LinkedHashSet<>(nodeModulesFolders)) {
			collectProjects(nmFolder, true, pdCache, dependencies);
		}
		return dependencies;
	}

	/**
	 * This method will <b>not find all</b> dependencies but only those that are actually necessary for compiling all
	 * the projects found in the given set of workspace directories.
	 * <p>
	 * Necessary project dependencies are those that are either N4JS projects or non-N4JS projects that are direct
	 * dependencies of N4JS projects.
	 */
	private Map<String, Path> collectNecessaryDependencies(Map<String, Path> allProjectDirs,
			Map<Path, ProjectDescription> pdCache) {

		Map<String, Path> dependencies = new LinkedHashMap<>();

		for (Path nextProject : allProjectDirs.values()) {
			collectProjectDependencies(nextProject, pdCache, dependencies);
		}

		return dependencies;
	}

	/** Collects all (transitive) dependencies of the given project */
	private void collectProjectDependencies(Path projectDir, Map<Path, ProjectDescription> pdCache,
			Map<String, Path> dependencies) {

		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectDir, pdCache);
		LinkedHashSet<Path> workList = new LinkedHashSet<>();
		workList.add(projectDir);
		while (!workList.isEmpty()) {
			Iterator<Path> iterator = workList.iterator();
			Path nextProject = iterator.next();
			iterator.remove();

			findDependencies(nextProject, nodeModulesFolder, pdCache, workList, dependencies);
		}
	}

	private void findDependencies(Path prjDir, NodeModulesFolder nodeModulesFolder,
			Map<Path, ProjectDescription> pdCache, Set<Path> workList, Map<String, Path> dependencies) {

		Path prjNodeModules = prjDir.resolve(N4JSGlobals.NODE_MODULES);
		ProjectDescription prjDescr = getCachedProjectDescription(prjDir, pdCache);
		if (prjDescr == null) {
			return;
		}

		for (ProjectDependency dependency : prjDescr.getProjectDependencies()) {
			String depName = dependency.getProjectName();

			Path depLocation = findDependencyLocation(nodeModulesFolder, prjNodeModules, depName);
			if (!prjDir.equals(depLocation)) {
				addDependency(depLocation, pdCache, workList, dependencies);
			}
		}
	}

	private void addDependency(Path depLocation, Map<Path, ProjectDescription> pdCache, Set<Path> workList,
			Map<String, Path> dependencies) {

		if (depLocation == null) {
			return;
		}

		String depName = getProjectNameFromPath(depLocation);
		if (dependencies.containsKey(depName)) {
			return;
		}

		Path packageJson = depLocation.resolve(N4JSGlobals.PACKAGE_JSON);
		if (packageJson.toFile().isFile()) {
			dependencies.putIfAbsent(depName, depLocation);

			ProjectDescription depPD = getCachedProjectDescription(depLocation, pdCache);
			if (depPD != null && depPD.isHasN4JSNature()) {
				workList.add(depLocation);
			}
		}
	}

	// Always use dependency of yarn workspace node_modules folder if it is available
	// TODO GH-1314, remove shadow logic here
	private Path findDependencyLocation(NodeModulesFolder nodeModulesFolder, Path prjNodeModules, String depName) {
		if (nodeModulesFolder == null) {
			return prjNodeModules.resolve(depName);
		}
		if (nodeModulesFolder.localNodeModulesFolder != null) {
			File depLocation = new File(nodeModulesFolder.localNodeModulesFolder, depName);
			if (depLocation.isDirectory()) {
				return depLocation.toPath();
			}
		}
		if (nodeModulesFolder.workspaceNodeModulesFolder != null) {
			File depLocation = new File(nodeModulesFolder.workspaceNodeModulesFolder, depName);
			if (depLocation.isDirectory()) {
				return depLocation.toPath();
			}
		}

		return null;
	}

	private String getProjectNameFromPath(Path projectPath) {
		return ProjectDescriptionUtils.deriveN4JSProjectNameFromPath(projectPath);
	}
}
