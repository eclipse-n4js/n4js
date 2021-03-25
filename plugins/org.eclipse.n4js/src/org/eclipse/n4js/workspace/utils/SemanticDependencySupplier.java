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

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfig;

/**
 * Computes the semantic dependencies of an N4JS project.
 */
public class SemanticDependencySupplier {

	/**
	 * Actually computes the semantic dependencies, see {@link N4JSProjectConfig#computeSemanticDependencies()}.
	 */
	public static List<ProjectDependency> computeSemanticDependencies(
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
}
