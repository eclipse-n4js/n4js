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
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.workspace.locations.FileURI;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Helper for finding all node_modules folders for a given set of N4JS projects, including support for yarn workspaces.
 *
 * For more details on yarn workspaces, see: https://yarnpkg.com/lang/en/docs/workspaces/
 */
public class NodeModulesDiscoveryHelper {

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	/** Holds information for a given project that is either a plain npm or a yarn related project */
	public static class NodeModulesFolder {
		/** <code>true</code> iff the given project is the root project of a yarn workspace */
		final public boolean isYarnWorkspaceRoot;
		/** <code>true</code> iff the given project is contained in a yarn workspace but isn't the root */
		final public boolean isYarnWorkspaceMember;
		/** node_modules folder of the given project or <code>null</code> if it doesn't have one */
		final public File localNodeModulesFolder;
		/** node_modules folder of the related yarn workspace project */
		final public File workspaceNodeModulesFolder;

		/** Constructor */
		public NodeModulesFolder(boolean isYarnWorkspaceRoot, boolean isYarnWorkspaceMember,
				File localNodeModulesFolder, File workspaceNodeModulesFolder) {
			this.isYarnWorkspaceRoot = isYarnWorkspaceRoot;
			this.isYarnWorkspaceMember = isYarnWorkspaceMember;
			this.localNodeModulesFolder = localNodeModulesFolder;
			this.workspaceNodeModulesFolder = workspaceNodeModulesFolder;
		}

		/**
		 * Returns the {@link #localNodeModulesFolder local} and {@link #workspaceNodeModulesFolder workspace
		 * node_modules folder} in the order of priority from high to low, meaning that if a project exists in both
		 * folders, the N4JS implementation should use the project in the folder that comes first in the returned list.
		 * Values of <code>null</code> are omitted, so the returned list may also have a size of 0 or 1 instead of 2.
		 *
		 * @return {@link #localNodeModulesFolder} and {@link #workspaceNodeModulesFolder} iff not null.
		 */
		public List<File> getNodeModulesFoldersInOrderOfPriority() {
			// GH-1314: according to node's module look-up semantics it would be correct to give the local folder
			// priority over the workspace folder; however, because N4JS does not yet support several projects with the
			// same name, we have to give the workspace folder the higher priority
			ArrayList<File> nmfList = Lists.newArrayList(this.workspaceNodeModulesFolder, this.localNodeModulesFolder);
			nmfList.removeAll(Collections.singleton(null));
			return Collections.unmodifiableList(nmfList);
		}

		/** True iff the {@link #workspaceNodeModulesFolder} is not null */
		public boolean isYarnWorkspace() {
			return this.workspaceNodeModulesFolder != null;
		}
	}

	/** @return the node_modules folder of the given project including a flag if this is a yarn workspace. */
	public NodeModulesFolder getNodeModulesFolder(Path projectLocation) {
		Map<Path, ProjectDescription> pdCache = new HashMap<>();
		return getNodeModulesFolder(projectLocation, pdCache);
	}

	/** @return the node_modules folder of the given project including a flag if this is a yarn workspace. */
	public NodeModulesFolder getNodeModulesFolder(Path projectLocation, Map<Path, ProjectDescription> pdCache) {
		final Path packgeJsonPath = projectLocation.resolve(N4JSGlobals.PACKAGE_JSON);
		if (!packgeJsonPath.toFile().isFile()) {
			return null; // given projectLocation must contain a package.json file
		}

		File projectLocationAsFile = projectLocation.toFile();

		if (isYarnWorkspaceRoot(projectLocationAsFile, Optional.absent(), pdCache)) {
			File workspaceNMF = new File(projectLocationAsFile, N4JSGlobals.NODE_MODULES);
			return new NodeModulesFolder(true, false, null, workspaceNMF);
		}

		final Optional<File> workspaceRoot = getYarnWorkspaceRoot(projectLocationAsFile, pdCache);
		if (workspaceRoot.isPresent()) {
			File workspaceNMF = new File(workspaceRoot.get(), N4JSGlobals.NODE_MODULES);
			File localNMF = new File(projectLocationAsFile, N4JSGlobals.NODE_MODULES);
			localNMF = localNMF.exists() ? localNMF : null;
			return new NodeModulesFolder(false, true, localNMF, workspaceNMF);
		}

		final Path nodeModulesPath = projectLocation.resolve(N4JSGlobals.NODE_MODULES);
		return new NodeModulesFolder(false, false, nodeModulesPath.toFile(), null);
	}

	/** Same as {@link #findNodeModulesFolders(Collection, Map)} without cache */
	public List<Path> findNodeModulesFolders(Collection<Path> n4jsProjects) {
		final Map<Path, ProjectDescription> pdCache = new HashMap<>();

		return findNodeModulesFolders(n4jsProjects, pdCache);
	}

	/**
	 * For the given N4JS projects, this method will search and return all <code>node_modules</code> folders. The
	 * returned list is ordered by priority, such that elements with a higher index have higher priority than those with
	 * a lower index.
	 *
	 * NOTE: this method is accessing the file system, so it should be deemed expensive!
	 *
	 * @param n4jsProjects
	 *            paths to the root folder of N4JS projects. If a path points to a folder not containing a valid N4JS
	 *            project, the behavior is undefined (will not be checked by this method).
	 */
	public List<Path> findNodeModulesFolders(Collection<Path> n4jsProjects, Map<Path, ProjectDescription> pdCache) {
		List<Path> result = new ArrayList<>();

		final Set<File> matchingWorkspaceRoots = new LinkedHashSet<>();

		for (Path projectFolderPath : n4jsProjects) {
			projectFolderPath = projectFolderPath.toAbsolutePath();
			final File projectFolder = projectFolderPath.toFile();
			if (projectFolder.isDirectory()) {
				final Path nodeModulesPath = projectFolderPath.resolve(N4JSGlobals.NODE_MODULES);
				result.add(nodeModulesPath);

				final Optional<File> workspaceRoot = getYarnWorkspaceRoot(projectFolder, pdCache);
				if (workspaceRoot.isPresent()) {
					matchingWorkspaceRoots.add(workspaceRoot.get());
				}
			}
		}

		// ORDER IMPORTANT: global node_modules folders from yarn workspaces should be added at the end!
		// Rationale: in case of multiple versions of the same npm package, only one can be "active" in the library
		// manager (with the current implementation of the library manager) that will be seen by all projects.
		// Having all projects see the version installed in the global node_modules folder is less harmful than
		// having all projects see the version installed in a local node_modules folder. Thus the global
		// node_modules folder should have priority and should therefore come last in list 'result'.
		// (NOTE: both "solutions" are actually broken, but this is a limitation of the library manager.)
		for (File workspaceRoot : matchingWorkspaceRoots) {
			final Path globalNodeModulesFolderPath = workspaceRoot.toPath().resolve(N4JSGlobals.NODE_MODULES);
			result.add(globalNodeModulesFolderPath);
		}

		return result;
	}

	/**
	 * If the given N4JS project, denoted by its root folder, is a member in a yarn workspace, this method will return
	 * the "workspace root" of the containing yarn workspace. Otherwise, {@link Optional#absent() absent} is returned.
	 *
	 * NOTE: this method is accessing the file system, so it should be deemed expensive!
	 *
	 * @param n4jsProjectFolder
	 *            root folder of an N4JS project that may or may not be a member of a yarn workspace.
	 * @param pdCache
	 *            a cache used to avoid loading/parsing the same package.json multiple times.
	 */
	private Optional<File> getYarnWorkspaceRoot(File n4jsProjectFolder, Map<Path, ProjectDescription> pdCache) {
		File candidate = n4jsProjectFolder.getParentFile();
		while (candidate != null) {
			// is candidate the root of a yarn workspace with 'n4jsProjectFolder' as one of the registered projects?
			if (isYarnWorkspaceRoot(candidate, Optional.of(n4jsProjectFolder), pdCache)) {
				// yes, so return candidate
				return Optional.of(candidate);
			}
			// if not, continue with parent folder as new candidate
			candidate = candidate.getParentFile();
		}
		return Optional.absent();
	}

	/** Same as {@link #isYarnWorkspaceRoot(File, Optional, Map)} without project folder and cache */
	public boolean isYarnWorkspaceRoot(File folder) {
		return isYarnWorkspaceRoot(folder, new HashMap<>());
	}

	/** Same as {@link #isYarnWorkspaceRoot(File, Optional, Map)} without project folder */
	public boolean isYarnWorkspaceRoot(File folder, Map<Path, ProjectDescription> pdCache) {
		return isYarnWorkspaceRoot(folder, Optional.absent(), pdCache);
	}

	/**
	 * Note that this method is expensive, since it reads a package.json file.
	 *
	 * @return true iff the given folder contains a package.json file and this file has a {@code workspaces} property.
	 */
	public boolean isYarnWorkspaceRoot(File folder, Optional<File> projectFolder,
			Map<Path, ProjectDescription> pdCache) {

		if (!folder.isDirectory()) {
			return false;
		}

		final List<String> workspaces = getWorkspacesOfYarnWorkspaceProject(folder, pdCache);
		if (workspaces == null) {
			return false;
		}

		// check if one of the values in property "workspaces" points to 'projectFolder'
		if (projectFolder.isPresent()) {
			for (String relativePath : workspaces) {
				if (isPointingTo(folder, relativePath, projectFolder.get())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	private List<String> getWorkspacesOfYarnWorkspaceProject(File yarnProjectFolder,
			Map<Path, ProjectDescription> pdCache) {

		// obtain value of property "workspaces" in package.json located in folder 'candidate'
		final ProjectDescription prjDescr = pdCache.computeIfAbsent(yarnProjectFolder.toPath(),
				// load value from package.json
				p -> {
					ProjectDescription pd = projectDescriptionLoader
							.loadProjectDescriptionAtLocation(new FileURI(yarnProjectFolder));
					return pd;
				});
		final List<String> workspaces = (prjDescr != null && prjDescr.isYarnWorkspaceRoot())
				? prjDescr.getWorkspaces()
				: null;

		return workspaces;
	}

	private boolean isPointingTo(File base, String relativePath, File target) {
		String pattern = base.getAbsolutePath() + File.separator + relativePath;
		PathMatcher matcher = ModuleFilterUtils.createPathMatcher(pattern);
		return matcher.matches(target.toPath());
	}

}
