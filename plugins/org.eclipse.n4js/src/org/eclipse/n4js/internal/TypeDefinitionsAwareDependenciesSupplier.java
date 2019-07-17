/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.collect.ImmutableList;

/**
 * Supplier for the ordered list of dependencies of a given {@link IN4JSProject} such that it is assured that type
 * definitions shadow their implementation projects..
 *
 * In contrast to {@link IN4JSProject#getDependencies()}, this supplier applies an explicit ordering that is of
 * significance when using the list of dependencies for constructing scopes.
 */
public class TypeDefinitionsAwareDependenciesSupplier {

	/**
	 * Returns an iterable of dependencies of the given {@code project}.
	 *
	 * Re-arranges the positions of type definition projects in such that they always occur right in front of the
	 * corresponding implementation project. As a consequence, the contents of the type definition source containers
	 * always has precedence over implementation project's source containers, enabling shadowing on the module-level.
	 *
	 * @throws IllegalStateException
	 *             This method will always return with a list of all dependencies declared by the given {@code project}.
	 *             In case the computation encounters a problem that results in the loss of a dependency, this method
	 *             will throw an {@link IllegalStateException}.
	 */
	static public Iterable<IN4JSProject> get(IN4JSProject project) {
		final ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		// keep ordered list of type definition projects per project ID of the definesPackage property
		final Map<N4JSProjectName, List<IN4JSProject>> typeDefinitionsById = new HashMap<>();
		// runtime dependencies (non-type dependencies)
		final List<IN4JSProject> runtimeDependencies = new LinkedList<>();

		// keep track of unused type definition projects (this set also assures that
		// conflicts do not remove type definition dependencies entirely)
		final Set<IN4JSProject> unusedTypeDefinitionProjects = new HashSet<>();

		// separate type definition projects from runtime projects
		for (IN4JSProject dependency : dependencies) {
			if (dependency.getProjectType() == ProjectType.DEFINITION) {
				final N4JSProjectName definesPackage = dependency.getDefinesPackageName();
				if (definesPackage != null) {
					// get existing or create new list of type definition projects
					List<IN4JSProject> typeDefinitionsProjects = typeDefinitionsById.getOrDefault(definesPackage,
							new ArrayList<>());
					typeDefinitionsProjects.add(dependency);
					typeDefinitionsById.put(definesPackage, typeDefinitionsProjects);
				}
				unusedTypeDefinitionProjects.add(dependency);
			} else {
				runtimeDependencies.add(dependency);
			}
		}

		final List<IN4JSProject> orderedDependencies = new LinkedList<>();

		// construct ordered list of dependencies
		for (IN4JSProject dependency : runtimeDependencies) {
			final N4JSProjectName projectName = dependency.getProjectName();
			final Collection<IN4JSProject> typeDefinitionProjects = typeDefinitionsById.getOrDefault(projectName,
					Collections.emptyList());

			// first list all type definition dependencies
			for (IN4JSProject typeDefinitionProject : typeDefinitionProjects) {
				// only add type definition, if it has not been added further up already
				if (unusedTypeDefinitionProjects.contains(typeDefinitionProject)) {
					orderedDependencies.add(typeDefinitionProject);
					// mark type definition as used
					unusedTypeDefinitionProjects.remove(typeDefinitionProject);
				}
			}
			// then list runtime dependency
			orderedDependencies.add(dependency);
		}

		// add all remaining unused type definition dependencies
		orderedDependencies.addAll(unusedTypeDefinitionProjects);

		// make lost dependencies very explicit
		if (orderedDependencies.size() != dependencies.size()) {
			throw new IllegalStateException(
					"Failed to compute dependency order for project " + project.getLocation()
							+ ": Ordered list of dependencies does not match original dependencies list in length.\n"
							+ "Length " + orderedDependencies.size() + ": " + orderedDependencies.toString() + " vs. "
							+ "Length " + dependencies.size() + ": " + dependencies.toString());
		}

		return orderedDependencies;
	}
}
