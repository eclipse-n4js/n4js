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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.workspace.N4JSProjectConfig;

import com.google.inject.Inject;

/**
 * Computes the semantic dependencies of an N4JS project.
 */
public class SemanticDependencySupplier {

	/** */
	@Inject
	protected NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * Actually computes the semantic dependencies, see {@link N4JSProjectConfig#computeSemanticDependencies()}.
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

	/**
	 */
	public List<ProjectDependency> changeToQualifiedNames(N4JSProjectConfig containingProject,
			List<ProjectDependency> dependencies) {

		Path workspaceParentLocation = containingProject.getRelatedWorkspacePath().getParent();
		Path relProjectLocation = containingProject.getPathInRelatedWorkspace();
		Path projectLocation = containingProject.getPathAsFileURI().toPath();
		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectLocation);
		List<ProjectDependency> result = new ArrayList<>();
		for (ProjectDependency dep : dependencies) {
			String depName = dep.getProjectName();
			String qualifiedName = getQualifiedName(workspaceParentLocation, relProjectLocation, nodeModulesFolder,
					depName);
			qualifiedName = qualifiedName == null ? depName : qualifiedName;
			ProjectDependency newDep = new ProjectDependency(qualifiedName, dep.getType(),
					dep.getVersionRequirementString(), dep.getVersionRequirement());
			result.add(newDep);
		}

		return result;
	}

	private String getQualifiedName(Path workspaceLocation, Path relProjectLocation,
			NodeModulesFolder nodeModulesFolder, String depName) {

		Path relDepPath = null;
		if (nodeModulesFolder == null) {
			relDepPath = relProjectLocation.resolve(N4JSGlobals.NODE_MODULES).resolve(depName);

		} else {
			for (File nodeModulesDir : nodeModulesFolder.getNodeModulesFoldersInOrderOfPriority()) {
				Path absDepPath = nodeModulesDir.toPath().resolve(depName);
				if (absDepPath.resolve(N4JSGlobals.PACKAGE_JSON).toFile().isFile()) {
					absDepPath = resolveSymbolicLinkOrDefault(absDepPath);
					relDepPath = workspaceLocation.relativize(absDepPath);
					break;
				}
			}
		}
		if (relDepPath != null) {
			return relDepPath.toString();
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

	public static Path resolveSymbolicLink(Path path) {
		Path parent = path.getParent();
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
		} else if (parent != null && parent.getFileName().toString().startsWith("@") && Files.isSymbolicLink(parent)) {
			Path slTargetOfParent = resolveSymbolicLink(parent);
			return slTargetOfParent.resolve(path.getName(path.getNameCount() - 1));
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
