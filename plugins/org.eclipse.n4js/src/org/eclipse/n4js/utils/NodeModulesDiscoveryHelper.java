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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.locations.FileURI;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Helper for finding all node_modules folders for a given set of N4JS projects, including support for yarn workspaces.
 *
 * For more details on yarn workspaces, see: https://yarnpkg.com/lang/en/docs/workspaces/
 */
public class NodeModulesDiscoveryHelper {

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	/** Defines whether a node_modules folder is a child of an NPM project or a YARN workspace project */
	public static class NodeModulesFolder {
		/** Location of a node_modules folder */
		final public File nodeModulesFolder;
		/** True iff the {@link #nodeModulesFolder} is in a yarn workspace */
		final public boolean isYarnWorkspace;

		/** Constructor */
		public NodeModulesFolder(File nodeModulesFolder, boolean isYarnWorkspace) {
			this.nodeModulesFolder = nodeModulesFolder;
			this.isYarnWorkspace = isYarnWorkspace;
		}
	}

	/** @return the node_modules folder of the given project including a flag if this is a yarn workspace. */
	public NodeModulesFolder getNodeModulesFolder(Path projectLocation) {
		Map<File, List<String>> workspacesCache = new HashMap<>();
		File projectLocationAsFile = projectLocation.toFile();

		if (isYarnWorkspaceRoot(projectLocationAsFile, Optional.absent(), workspacesCache)) {
			return new NodeModulesFolder(new File(projectLocationAsFile, N4JSGlobals.NODE_MODULES), true);
		}

		final Optional<File> workspaceRoot = getYarnWorkspaceRoot(projectLocationAsFile, workspacesCache);
		if (workspaceRoot.isPresent()) {
			return new NodeModulesFolder(new File(workspaceRoot.get(), N4JSGlobals.NODE_MODULES), true);
		}

		final Path packgeJsonPath = projectLocation.resolve(N4JSGlobals.PACKAGE_JSON);
		if (packgeJsonPath.toFile().isFile()) {
			final Path nodeModulesPath = projectLocation.resolve(N4JSGlobals.NODE_MODULES);
			return new NodeModulesFolder(nodeModulesPath.toFile(), false);
		}

		return null;
	}

	/**
	 * For the given N4JS projects, this method will search and return all <code>node_modules</code> folders. The
	 * returned list is ordered by priority, such that elements with a higher index have higher priority than those with
	 * a lower index. This is in line with shadowing between <code>node_modules</code> locations in the library manager
	 * (as configured in {@link ExternalLibraryPreferenceStore}), where the contents of locations with a higher index
	 * shadow those of locations with a lower index.
	 *
	 * NOTE: this method is accessing the file system, so it should be deemed expensive!
	 *
	 * @param n4jsProjects
	 *            paths to the root folder of N4JS projects. If a path points to a folder not containing a valid N4JS
	 *            project, the behavior is undefined (will not be checked by this method).
	 */
	public List<Path> findNodeModulesFolders(Collection<Path> n4jsProjects) {
		List<Path> result = new ArrayList<>();

		final Map<File, List<String>> workspacesCache = new HashMap<>();
		final Set<File> matchingWorkspaceRoots = new LinkedHashSet<>();

		for (Path projectFolderPath : n4jsProjects) {
			projectFolderPath = projectFolderPath.toAbsolutePath();
			final File projectFolder = projectFolderPath.toFile();
			if (projectFolder.isDirectory()) {
				final Path nodeModulesPath = projectFolderPath.resolve(N4JSGlobals.NODE_MODULES);
				result.add(nodeModulesPath);

				final Optional<File> workspaceRoot = getYarnWorkspaceRoot(projectFolder, workspacesCache);
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
	 * @param workspacesCache
	 *            a cache used to avoid loading/parsing the same package.json multiple times.
	 */
	private Optional<File> getYarnWorkspaceRoot(File n4jsProjectFolder, Map<File, List<String>> workspacesCache) {
		File candidate = n4jsProjectFolder.getParentFile();
		while (candidate != null) {
			// is candidate the root of a yarn workspace with 'n4jsProjectFolder' as one of the registered projects?
			if (isYarnWorkspaceRoot(candidate, Optional.of(n4jsProjectFolder), workspacesCache)) {
				// yes, so return candidate
				return Optional.of(candidate);
			}
			// if not, continue with parent folder as new candidate
			candidate = candidate.getParentFile();
		}
		return Optional.absent();
	}

	private boolean isYarnWorkspaceRoot(File folder, Optional<File> projectFolder,
			Map<File, List<String>> workspacesCache) {
		if (!folder.isDirectory()) {
			return false;
		}
		// obtain value of property "workspaces" in package.json located in folder 'candidate'
		final List<String> workspaces;
		final List<String> workspacesFromCache = workspacesCache.get(folder);
		if (workspacesFromCache != null) {
			// use the value from the cache
			workspaces = workspacesFromCache;
		} else {
			// load value from package.json
			workspaces = projectDescriptionLoader
					.loadWorkspacesFromProjectDescriptionAtLocation(new FileURI(folder));
			if (workspaces != null) {
				workspacesCache.put(folder, workspaces);
			}
		}
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

	private boolean isPointingTo(File base, String relativePath, File target) {
		String pattern = base.getAbsolutePath() + File.separator + relativePath;
		PathMatcher matcher = WildcardPathFilterHelper.createPathMatcher(pattern);
		return matcher.matches(target.toPath());
	}
}
