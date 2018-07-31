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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.collect.ImmutableList;

/**
 * Supplier for the ordered list of dependencies of a given {@link IN4JSProject}.
 *
 * In contrast to {@link IN4JSProject#getDependencies()}, this supplier applies an ordering to the list of dependencies,
 * in such that it is assured that type definitions shadow their implementation projects.
 */
public class TypeDefinitionsAwareDependenciesSupplier {
	/**
	 * Returns an iterable of dependencies of the given {@code project}.
	 *
	 * Re-arranges the positions of type definition projects in such, that they always occur right in front of the
	 * corresponding implementation project. As a consequence, the contents of the type definition source containers
	 * always has precedence over implementation project's source containers, enabling shadowing on the module-level.
	 */
	public Iterable<IN4JSProject> get(IN4JSProject project) {
		final ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		final Map<String, IN4JSProject> typeDefinitionsById = new HashMap<>();
		// runtime dependencies (non-type dependencies)
		final List<IN4JSProject> runtimeDependencies = new LinkedList<>();

		// keep track of unused type definition projects (this set also assures that
		// conflicts do not remove type definition dependencies entirely)
		final Set<IN4JSProject> unusedTypeDefinitionProjects = new HashSet<>();

		// separate type definition projects from runtime projects
		for (IN4JSProject dependency : dependencies) {
			if (dependency.getProjectType() == ProjectType.DEFINITION) {
				final String definesPackage = dependency.getDefinesPackage();
				if (definesPackage != null) {
					typeDefinitionsById.put(dependency.getDefinesPackage(), dependency);
				}
				unusedTypeDefinitionProjects.add(dependency);
			} else {
				runtimeDependencies.add(dependency);
			}
		}

		final List<IN4JSProject> orderedDependencies = new LinkedList<>();

		for (IN4JSProject dependency : runtimeDependencies) {
			final String projectId = dependency.getProjectId();
			final IN4JSProject typeDefinitionProject = typeDefinitionsById.get(projectId);

			// first list type definition dependency
			if (typeDefinitionProject != null) {
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
			throw new IllegalStateException("Failed to compute dependency order for project " + project.getLocation()
					+ ": Ordered list of dependencies does not match original dependencies list in length.\n"
					+ "Length " + orderedDependencies.size() + ": " + orderedDependencies.toString() + " vs. "
					+ "Length " + dependencies.size() + ": " + dependencies.toString());
		}

		return orderedDependencies;
	}
}
