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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier;

import com.google.common.base.Preconditions;
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
	 * Notes:
	 * <ul>
	 * <li/>Projects and dependencies are sorted to avoid indeterminism from file system.
	 * <li/>Dependencies (i.e. projects in {@code node_modules} folders) are listed after all workspace projects.
	 * <li/>Projects in packages folders have a symlink in the node_modules folder (created by yarn). The symlink is not
	 * used in favor for the path in the packages folder because we want to support workspaces that have not yet
	 * initialized by yarn.
	 * </ul>
	 * <p>
	 */
	public Map<Path, ProjectDescription> collectAllProjectDirs(Collection<Path> workspaceRoots) {
		Map<Path, ProjectDescription> pdCache = new HashMap<>();
		Set<Path> projects = collectAllProjects(workspaceRoots, pdCache);
		Set<Path> dependencies = collectNecessaryDependencies(projects, pdCache);

		List<Path> sortedProjects = new ArrayList<>(projects);
		Collections.sort(sortedProjects);

		List<Path> sortedDependecies = new ArrayList<>();
		sortedDependecies.addAll(dependencies);
		Collections.sort(sortedDependecies);

		sortedProjects.addAll(sortedDependecies);

		Map<Path, ProjectDescription> sortedProjectMap = new LinkedHashMap<>();
		for (Path projectLocation : sortedProjects) {
			ProjectDescription pd = getProjectDescription(projectLocation, pdCache);
			Preconditions.checkNotNull(pd);
			sortedProjectMap.put(projectLocation, pd);
		}

		return sortedProjectMap;
	}

	/** Searches all projects in the given array of workspace directories */
	private Set<Path> collectAllProjects(Collection<Path> workspaceRoots, Map<Path, ProjectDescription> pdCache) {
		Set<Path> allProjectDirs = new LinkedHashSet<>();
		for (Path wsRoot : workspaceRoots) {
			collectAllProjects(wsRoot, allProjectDirs, pdCache);
		}
		return allProjectDirs;
	}

	private void collectAllProjects(Path wsRoot, Set<Path> allProjectDirs, Map<Path, ProjectDescription> pdCache) {

		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(wsRoot, pdCache);

		if (nodeModulesFolder == null) {
			// Assume side-by-side case (neither NPM nor Yarn project)
			collectProjects(wsRoot, null, true, pdCache, allProjectDirs);

			if (allProjectDirs.isEmpty()) {
				// no project found => not side-by-side case: Search in parent directories
				Path projectRoot = getProjectRootOrUnchanged(wsRoot);
				nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectRoot, pdCache);
			} else {
				// check if the side-by-side case is actually a yarn case
				Path oneProject = allProjectDirs.iterator().next();
				NodeModulesFolder yarnProject = nodeModulesDiscoveryHelper.getNodeModulesFolder(oneProject, pdCache);
				if (yarnProject.isYarnWorkspaceMember) {
					Path yarnProjectDir = yarnProject.workspaceNodeModulesFolder.getParentFile().toPath();
					collectYarnWorkspaceProjects(yarnProjectDir, pdCache, allProjectDirs);
				}
			}
		}

		if (nodeModulesFolder != null) {
			if (nodeModulesFolder.isYarnWorkspaceRoot || nodeModulesFolder.isYarnWorkspaceMember) {
				// Is Yarn project
				// use projects referenced in packages
				Path yarnProjectDir = nodeModulesFolder.workspaceNodeModulesFolder.getParentFile().toPath();
				collectYarnWorkspaceProjects(yarnProjectDir, pdCache, allProjectDirs);
			} else {
				// Is a stand-alone npm project
				addIfNotPlainjs(wsRoot, wsRoot, pdCache, allProjectDirs);
			}
		}
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
	 */
	private void collectYarnWorkspaceProjects(Path yarnProjectRoot, Map<Path, ProjectDescription> pdCache,
			Set<Path> allProjectDirs) {

		ProjectDescription projectDescription = getOrCreateProjectDescription(yarnProjectRoot, null, pdCache);
		if (projectDescription == null) {
			return;
		}
		List<String> workspaces = projectDescription.getWorkspaces();
		if (workspaces == null) {
			return;
		}

		// add the yarn workspace root project even if it is a PLAINJS project
		// Rationale:
		// 1) otherwise not possible for later code to distinguish between yarn workspace and side-by-side use cases,
		// 2) having the workspace root project or not makes a huge difference (projects exist inside projects) and it
		// is better to always stick to one situation (otherwise many tests would have to be provided in two variants),
		// 3) the yarn workspace root project has always been included.
		allProjectDirs.add(yarnProjectRoot);

		Set<Path> memberProjects = new LinkedHashSet<>();
		for (String workspaceGlob : workspaces) {
			collectGlobMatches(workspaceGlob, yarnProjectRoot, pdCache, memberProjects);
		}
		removeUnnecessaryPlainjsProjects(memberProjects, pdCache);
		for (Path member : memberProjects) {
			allProjectDirs.add(member);
		}
	}

	private void collectProjects(Path root, Path relatedRoot, boolean includeSubtree,
			Map<Path, ProjectDescription> pdCache, Set<Path> allProjectDirs) {

		if (!root.toFile().isDirectory()) {
			return;
		}

		final FileVisitResult defaultReturn;
		int depth;
		if (includeSubtree) {
			defaultReturn = CONTINUE;
			depth = Integer.MAX_VALUE;
		} else {
			defaultReturn = SKIP_SUBTREE;
			depth = 3;
		}
		try {
			EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
			Files.walkFileTree(root, options, depth, new SimpleFileVisitor<Path>() {
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
							addIfNotPlainjs(dir, relatedRoot, pdCache, allProjectDirs);
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

	private void collectGlobMatches(String glob, Path yarnRoot, Map<Path, ProjectDescription> pdCache,
			Set<Path> allProjectDirs) {

		int depth = glob.contains("**") ? Integer.MAX_VALUE : glob.split("/").length + 1;

		@SuppressWarnings("resource")
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + yarnRoot.resolve(glob));
		try {
			EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
			Files.walkFileTree(yarnRoot, options, depth, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (pathMatcher.matches(dir)) {
						Path dirName = dir.getName(dir.getNameCount() - 1);
						if (dirName.toString().startsWith("@")) {
							// note: project names must not start with '@' (unless it is a parent folder)
							collectProjects(dir, yarnRoot, false, pdCache, allProjectDirs);
							return FileVisitResult.SKIP_SUBTREE;

						} else {
							File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
							if (pckJson.isFile()) {
								ProjectDescription projectDescription = getOrCreateProjectDescription(dir, yarnRoot,
										pdCache);
								// note: add 'dir' to 'allProjectDirs' even if it is PLAINJS (will be taken care of by
								// #removeUnnecessaryPlainjsProjects() below)
								if (projectDescription != null) {
									allProjectDirs.add(dir);
								}
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
	 * project P is "unnecessary" if there does not exist an N4JS project P' in <code>projects</code> that has a
	 * dependency to P.
	 */
	private void removeUnnecessaryPlainjsProjects(Set<Path> projects, Map<Path, ProjectDescription> pdCache) {
		Map<String, Path> plainjsProjects = new HashMap<>();
		Set<String> projectsRequiredByAnN4JSProject = new HashSet<>();
		for (Path project : projects) {
			ProjectDescription pd = getProjectDescription(project, pdCache); // if it is null it was not added before
			if (pd == null) {
				continue;
			}
			ProjectType type = pd.getType();
			if (type == ProjectType.PLAINJS) {
				// note: in case a name occurs twice yarn would throw an error
				plainjsProjects.put(pd.getPackageName(), project);
			} else {
				List<String> deps = pd.getProjectDependencies().stream()
						.map(ProjectReference::getPackageName).collect(Collectors.toList());
				projectsRequiredByAnN4JSProject.addAll(deps);
			}
		}
		plainjsProjects.keySet().removeAll(projectsRequiredByAnN4JSProject);
		for (String plainJsName : plainjsProjects.keySet()) {
			projects.remove(plainjsProjects.get(plainJsName));
		}
	}

	private void addIfNotPlainjs(Path project, Path relatedRoot, Map<Path, ProjectDescription> pdCache,
			Set<Path> allProjectDirs) {

		ProjectDescription pd = getOrCreateProjectDescription(project, relatedRoot, pdCache);
		if (pd != null && pd.getType() != ProjectType.PLAINJS) {
			allProjectDirs.add(project);
		}
	}

	/**
	 * @return the potentially cached {@link ProjectDescription} for the project in the given directory or creates it.
	 *         In case creation fails, null is returned.
	 */
	private ProjectDescription getOrCreateProjectDescription(Path path, Path relatedRoot,
			Map<Path, ProjectDescription> cache) {

		if (!cache.containsKey(path) || cache.get(path).getRelatedRootLocation() == null) {
			FileURI location = new FileURI(path.toFile());
			FileURI relatedRootLocation = relatedRoot == null ? null : new FileURI(relatedRoot.toFile());
			ProjectDescription pd = projectDescriptionLoader
					.loadProjectDescriptionAtLocation(location, relatedRootLocation);
			cache.put(path, pd);
		}
		ProjectDescription pd = cache.get(path);
		if (pd != null && relatedRoot != null && pd.getRelatedRootLocation() != null
				&& !Objects.equals(relatedRoot, pd.getRelatedRootLocation().toPath())) {

			// recompute project description in case the nodeModulesDiscoveryHelper added it
			ProjectDescriptionBuilder pdb = pd.change();
			pd = pdb.setRelatedRootLocation(new FileURI(relatedRoot.toFile()))
					.setId(pdb.computeQualifiedName())
					.build();
			cache.put(path, pd);
		}
		return pd;
	}

	/** @return the {@link ProjectDescription} for the project in the given directory or null. */
	private ProjectDescription getProjectDescription(Path path, Map<Path, ProjectDescription> cache) {
		return cache.get(path);
	}

	/**
	 * This method will <b>not find all</b> dependencies but only those that are actually necessary for compiling all
	 * the projects found in the given set of workspace directories.
	 * <p>
	 * Necessary project dependencies are those that are either N4JS projects or non-N4JS projects that are direct
	 * dependencies of N4JS projects.
	 */
	private Set<Path> collectNecessaryDependencies(Set<Path> allProjectDirs, Map<Path, ProjectDescription> pdCache) {
		Set<Path> dependencies = new LinkedHashSet<>();

		for (Path nextProject : allProjectDirs) {
			collectProjectDependencies(allProjectDirs, nextProject, pdCache, dependencies);
		}

		return dependencies;
	}

	/** Collects all (transitive) dependencies of the given project */
	private void collectProjectDependencies(Set<Path> allProjectDirs, Path projectDir,
			Map<Path, ProjectDescription> pdCache, Set<Path> dependencies) {

		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectDir, pdCache);
		LinkedHashSet<Path> workList = new LinkedHashSet<>();
		workList.add(projectDir);
		while (!workList.isEmpty()) {
			Iterator<Path> iterator = workList.iterator();
			Path nextProject = iterator.next();
			iterator.remove();

			findDependencies(allProjectDirs, nextProject, nodeModulesFolder, pdCache, workList, dependencies);
		}
	}

	private void findDependencies(Set<Path> allProjectDirs, Path prjDir, NodeModulesFolder nodeModulesFolder,
			Map<Path, ProjectDescription> pdCache, Set<Path> workList, Set<Path> dependencies) {

		Path relatedRoot = nodeModulesFolder.getRelatedRoot();
		Path prjNodeModules = prjDir.resolve(N4JSGlobals.NODE_MODULES);
		ProjectDescription prjDescr = getOrCreateProjectDescription(prjDir, relatedRoot, pdCache);
		if (prjDescr == null) {
			return;
		}

		for (ProjectDependency dependency : prjDescr.getProjectDependencies()) {
			String depName = dependency.getPackageName();

			Path depLocation = findDependencyLocation(nodeModulesFolder, prjNodeModules, depName);
			if (isNewDependency(allProjectDirs, prjDir, pdCache, depLocation, relatedRoot)) {
				addDependency(depLocation, relatedRoot, pdCache, workList, dependencies);
			}
		}
	}

	private boolean isNewDependency(Set<Path> allProjectDirs, Path prjDir,
			Map<Path, ProjectDescription> pdCache, Path depLocation, Path relatedRoot) {

		if (depLocation == null) {
			return false;
		}
		// check reference to yarn packages project
		if (SemanticDependencySupplier.isSymbolicLink(depLocation)) {
			Path linkTarget = SemanticDependencySupplier.resolveSymbolicLink(depLocation);
			if (linkTarget != null) {
				ProjectDescription prjDescrLinked = getOrCreateProjectDescription(linkTarget, relatedRoot, pdCache);
				if (prjDescrLinked != null && allProjectDirs.contains(prjDescrLinked.getLocation().toPath())) {
					return false;
				}
			}
		}

		// check self-reference
		return !prjDir.equals(depLocation);
	}

	private void addDependency(Path depLocation, Path relatedRoot, Map<Path, ProjectDescription> pdCache,
			Set<Path> workList, Set<Path> dependencies) {

		if (depLocation == null) {
			return;
		}

		Path packageJson = depLocation.resolve(N4JSGlobals.PACKAGE_JSON);
		if (packageJson.toFile().isFile()) {

			ProjectDescription depPD = getOrCreateProjectDescription(depLocation, relatedRoot, pdCache);
			if (depPD != null) {
				if (dependencies.contains(depLocation)) {
					return;
				}

				dependencies.add(depLocation);

				if (depPD.hasN4JSNature()) {
					workList.add(depLocation);
				}
			}
		}
	}

	// Note: in node_modules folders the project name always equals its parent folder(s)
	private Path findDependencyLocation(NodeModulesFolder nodeModulesFolder, Path prjNodeModules, String depName) {
		if (nodeModulesFolder == null) {
			return prjNodeModules.resolve(depName); // can this happen?
		}
		for (File currNMF : nodeModulesFolder.getNodeModulesFoldersInOrderOfPriority()) {
			File depLocation = new File(currNMF, depName);
			if (depLocation.isDirectory()) {
				return depLocation.toPath();
			}
		}
		return null;
	}

}
