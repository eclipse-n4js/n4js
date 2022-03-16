/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.workspace.N4JSProjectConfig;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfig;

import com.google.inject.Inject;

/**
 * Computes the semantic dependencies of an N4JS project.
 */
public class SemanticDependencySupplier {

	/** */
	@Inject
	protected NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * Actually computes the semantic dependencies, see {@link N4JSProjectConfig#getSemanticDependencies()}.
	 */
	public List<ProjectDependency> computeSemanticDependencies(
			DefinitionProjectMap definitionProjects, List<ProjectDependency> dependencies) {

		Set<String> implicitDependencies = new LinkedHashSet<>();
		Set<String> existingDependencies = new LinkedHashSet<>();
		List<ProjectDependency> moveToTop = new ArrayList<>();
		List<ProjectDependency> keepAtPosition = new ArrayList<>();

		boolean sawDefinitionsOnly = true;
		for (ProjectDependency dependency : dependencies) {
			N4JSPackageName dependencyName = dependency.getN4JSProjectName();
			existingDependencies.add(dependencyName.getRawName());
			Collection<N4JSPackageName> defPrjDeps = definitionProjects.getDefinitionProjects(dependencyName);
			for (N4JSPackageName prjName : defPrjDeps) {
				implicitDependencies.add(prjName.getRawName());
			}

			boolean isDefinitionProject = definitionProjects.isDefinitionProject(dependencyName);
			sawDefinitionsOnly &= isDefinitionProject;
			if (isDefinitionProject) {
				moveToTop.add(dependency);
			} else {
				keepAtPosition.add(dependency);
			}
		}
		implicitDependencies.removeAll(existingDependencies);

		if (implicitDependencies.isEmpty() && (sawDefinitionsOnly || moveToTop.isEmpty())) {
			return dependencies;
		}

		List<ProjectDependency> result = new ArrayList<>(
				implicitDependencies.size() + moveToTop.size() + keepAtPosition.size());

		for (String implicitDependencyString : implicitDependencies) {
			ProjectDependency implicitDependency = new ProjectDependency(
					implicitDependencyString,
					DependencyType.IMPLICIT,
					"",
					SemverUtils.createEmptyVersionRequirement());
			moveToTop.add(implicitDependency);
		}

		// move @n4jsd definitions before @types definitions
		List<ProjectDependency> typesDefDeps = new ArrayList<>();
		for (Iterator<ProjectDependency> iter = moveToTop.iterator(); iter.hasNext();) {
			ProjectDependency topDep = iter.next();
			if (Objects.equals(N4JSGlobals.TYPES_SCOPE, topDep.getN4JSProjectName().getScopeName())) {
				iter.remove();
				typesDefDeps.add(topDep);
			}
		}

		result.addAll(moveToTop);
		result.addAll(typesDefDeps);
		result.addAll(keepAtPosition);
		return result;
	}

	/**
	 * Computes a map from package names to project ids. The context of the given project is taken into account, i.e. in
	 * case there exist multiple projects in the workspace with the same package name, the id of that project is mapped
	 * that would bind to the given project as a dependency.
	 */
	public Map<String, String> computePackageName2ProjectIdMap(N4JSWorkspaceConfig workspace,
			ProjectDescription projectDescription, Path relatedRootLocation, Iterable<String> packageNames) {

		Path projectLocation = projectDescription.getLocation().toPath();
		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectLocation);
		Map<String, String> packageName2projectIds = new HashMap<>();
		for (String packageName : packageNames) {
			Set<N4JSProjectConfig> candidates = workspace.findProjectsByPackageName(packageName);
			String qualifiedName = getQualifiedName(relatedRootLocation, projectLocation, candidates,
					nodeModulesFolder, packageName);

			qualifiedName = qualifiedName == null ? packageName : qualifiedName;
			packageName2projectIds.put(packageName, qualifiedName);

		}
		return packageName2projectIds;
	}

	private String getQualifiedName(Path relatedRootLocation, Path projectLocation,
			Set<N4JSProjectConfig> candidates, NodeModulesFolder nodeModulesFolder, String depName) {

		Path pathToDep = getPathToDependency(relatedRootLocation, projectLocation, candidates,
				nodeModulesFolder, depName);
		if (pathToDep == null) {
			return null;
		}
		Path workspaceParentLocation = relatedRootLocation.getParent();
		Path relDepPath = workspaceParentLocation.relativize(pathToDep);
		return relDepPath.toString();
	}

	private Path getPathToDependency(Path relatedRootLocation, Path projectLocation,
			Set<N4JSProjectConfig> candidates, NodeModulesFolder nodeModulesFolder, String depName) {

		if (nodeModulesFolder == null) {
			return projectLocation.resolve(N4JSGlobals.NODE_MODULES).resolve(depName);

		} else {

			Set<Path> candidatePaths = new HashSet<>();

			// First check if it is a packages project
			for (N4JSProjectConfig candidate : candidates) {
				if (!candidate.isInNodeModulesFolder()) {
					if (Objects.equals(relatedRootLocation, candidate.getRelatedRootLocation())) {
						// inside the same yarn workspace
						return candidate.getPathAsFileURI().toPath();
					}
				}
				candidatePaths.add(candidate.getPathAsFileURI().toPath());
			}

			// Second check if it is a dependency in the node_modules folder
			for (File nodeModulesDir : nodeModulesFolder.getNodeModulesFoldersInOrderOfPriority()) {
				Path absDepPath = nodeModulesDir.toPath().resolve(depName);
				if (candidatePaths.contains(absDepPath)) {
					// dependency in a node_modules folder
					return absDepPath;
				}
			}
		}
		return null;
	}

	/** @return the resolved path or the path itself */
	public static Path resolveSymbolicLinkOrDefault(Path path) {
		Path resolvedPath = resolveSymbolicLink(path);
		if (resolvedPath == null) {
			return path;
		} else {
			return resolvedPath;
		}
	}

	/** @return true iff the given path is a symbolic link or iff its parent is a scope folder and a symbolic link */
	public static boolean isSymbolicLink(Path path) {
		if (Files.isSymbolicLink(path)) {
			return true;
		}
		if (isSymbolicLinkParent(path)) {
			return true;
		}
		return false;
	}

	private static boolean isSymbolicLinkParent(Path path) {
		Path parent = path.getParent();
		if (parent != null && parent.getFileName().toString().startsWith("@") && Files.isSymbolicLink(parent)) {
			return true;
		}
		return false;
	}

	/** Resolves the given path in case it is a symbolic link. Otherwise returns null. */
	public static Path resolveSymbolicLink(Path path) {
		if (isSymbolicLinkParent(path)) {
			Path parent = path.getParent();
			Path slTargetOfParent = resolveSymbolicLink(parent);
			return slTargetOfParent.resolve(path.getName(path.getNameCount() - 1));
		}
		if (Files.isSymbolicLink(path)) {
			try {
				Path slTarget = Files.readSymbolicLink(path);
				if (slTarget.isAbsolute()) {
					return slTarget;
				} else {
					return path.getParent().resolve(slTarget).normalize();
				}
			} catch (IOException e) {
				return null;
			}
		}
		return null;

	}

	/**
	 * Converts a project id to a package name. Since project ids are made of relative paths, be sure that this path
	 * contains the package name. This can be assumed to be always true if the given project id is from a project inside
	 * a {@code node_modules} folder.
	 */
	public static String convertProjectIdToPackageName(String projectId) {
		Path projectIdPath = Path.of(projectId);
		int nameCount = projectIdPath.getNameCount();
		if (nameCount == 0) {
			return null;
		}
		if (nameCount == 1) {
			return projectId;
		}
		if (projectIdPath.getName(nameCount - 2).startsWith("@")) {
			return projectIdPath.subpath(nameCount - 2, nameCount - 1).toString();
		}
		return projectIdPath.getFileName().toString();
	}
}
