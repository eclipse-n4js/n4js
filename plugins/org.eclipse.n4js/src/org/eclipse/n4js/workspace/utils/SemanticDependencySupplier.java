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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
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
		LinkedList<ProjectDependency> moveToTop = new LinkedList<>();

		EList<ProjectDependency> pDeps = ECollections.newBasicEList(dependencies);
		boolean sawDefinitionsOnly = true;
		for (ProjectDependency dependency : pDeps) {
			N4JSProjectName dependencyName = dependency.getN4JSProjectName();
			existingDependencies.add(dependencyName.getRawName());
			N4JSProjectName definitionProjectName = definitionProjects.getDefinitionProject(dependencyName);
			if (definitionProjectName != null) {
				implicitDependencies.add(definitionProjectName.getRawName());
			}

			boolean isDefinitionProject = definitionProjects.isDefinitionProject(dependencyName);
			sawDefinitionsOnly &= isDefinitionProject;
			if (isDefinitionProject) {
				moveToTop.addFirst(dependency); // add at index 0 to keep order. note below move(0, ...);
			}
		}
		implicitDependencies.removeAll(existingDependencies);

		if (implicitDependencies.isEmpty() && (sawDefinitionsOnly || moveToTop.isEmpty())) {
			return dependencies;
		}

		for (String implicitDependencyString : implicitDependencies) {
			ProjectDependency implicitDependency = new ProjectDependency(
					implicitDependencyString,
					DependencyType.IMPLICIT,
					"",
					SemverUtils.createEmptyVersionRequirement());
			pDeps.add(0, implicitDependency);
		}
		for (ProjectDependency moveToTopDep : moveToTop) {
			pDeps.move(0, moveToTopDep);
		}
		return pDeps;
	}

	public Map<String, String> getQualifiedNames(N4JSWorkspaceConfig workspace, Path workspaceLocation,
			Path projectLocation, List<ProjectDependency> dependencies) {

		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectLocation);
		Map<String, String> packageName2projectIds = new HashMap<>();
		for (ProjectDependency dep : dependencies) {
			String depName = dep.getProjectName();
			Set<N4JSProjectConfig> candidatesByPackageName = workspace.findProjectsByPackageName(depName);
			String qualifiedName = getQualifiedName(workspaceLocation, projectLocation, candidatesByPackageName,
					nodeModulesFolder, depName);

			qualifiedName = qualifiedName == null ? depName : qualifiedName;
			packageName2projectIds.put(depName, qualifiedName);

		}
		return packageName2projectIds;
	}

	private String getQualifiedName(Path workspaceLocation, Path projectLocation,
			Set<N4JSProjectConfig> candidatesByPackageName, NodeModulesFolder nodeModulesFolder, String depName) {

		Path pathToDep = getPathToDependency(workspaceLocation, projectLocation, candidatesByPackageName,
				nodeModulesFolder, depName);
		if (pathToDep == null) {
			return null;
		}
		Path workspaceParentLocation = workspaceLocation.getParent();
		Path relDepPath = workspaceParentLocation.relativize(pathToDep);
		return relDepPath.toString();
	}

	private Path getPathToDependency(Path workspaceLocation, Path projectLocation,
			Set<N4JSProjectConfig> candidatesByPackageName, NodeModulesFolder nodeModulesFolder, String depName) {

		if (nodeModulesFolder == null) {
			return projectLocation.resolve(N4JSGlobals.NODE_MODULES).resolve(depName);

		} else {
			// First check if it is a packages project
			for (N4JSProjectConfig candidate : candidatesByPackageName) {
				if (!candidate.isInNodeModulesFolder()) {
					if (Objects.equals(workspaceLocation, candidate.getRelatedWorkspacePath())) {
						// inside the same yarn workspace
						return candidate.getPathAsFileURI().toPath();
					}
				}
			}

			// Second check if it is a dependency in the node_modules folder
			Set<Path> pathsByPackageName = candidatesByPackageName.stream()
					.map(p -> p.getPathAsFileURI().toPath())
					.collect(Collectors.toSet());
			for (File nodeModulesDir : nodeModulesFolder.getNodeModulesFoldersInOrderOfPriority()) {
				Path absDepPath = nodeModulesDir.toPath().resolve(depName);
				if (pathsByPackageName.contains(absDepPath)) {
					// dependency in a node_modules folder
					return absDepPath;
				}
			}
		}
		return null;
	}

	public static Path resolveSymbolicLinkOrDefault(Path path) {
		Path resolvedPath = resolveSymbolicLink(path);
		if (resolvedPath == null) {
			return path;
		} else {
			return resolvedPath;
		}
	}

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
				e.printStackTrace(); // FIXME: handle this properly
			}
		}
		return null;
	}

	public static String convertProjectIdToName(String projectIdStr) {
		Path projectIdPath = Path.of(projectIdStr);
		int nameCount = projectIdPath.getNameCount();
		if (nameCount == 0) {
			return null;
		}
		if (nameCount == 1) {
			return projectIdStr;
		}
		if (projectIdPath.getName(nameCount - 2).startsWith("@")) {
			return projectIdPath.subpath(nameCount - 2, nameCount - 1).toString();
		}
		return projectIdPath.getFileName().toString();
	}
}
