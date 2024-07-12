/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.helper;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;

import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.DependencyTraverser;
import org.eclipse.n4js.utils.DependencyTraverser.DependencyProvider;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;

import com.google.common.collect.ImmutableList;

/**
 * A {@link DependencyProvider} implementation for traversing the dependency graph defined by
 * {@link N4JSProjectConfigSnapshot} entities via {@link DependencyTraverser}.
 */
public class SourceContainerAwareDependencyProvider implements DependencyProvider<N4JSProjectConfigSnapshot> {

	private final N4JSWorkspaceConfigSnapshot workspaceConfig;
	private final boolean ignoreExternalPlainJsProjects;

	/**
	 * Creates a new traverser instance with the given root node.
	 *
	 * @param ignoreExternalPlainJsProjects
	 *            Specifies whether dependency edges to external {@link ProjectType#PLAINJS} projects should be excluded
	 *            when traversing the dependency graph.
	 */
	public SourceContainerAwareDependencyProvider(N4JSWorkspaceConfigSnapshot workspaceConfig,
			boolean ignoreExternalPlainJsProjects) {
		this.workspaceConfig = workspaceConfig;
		this.ignoreExternalPlainJsProjects = ignoreExternalPlainJsProjects;
	}

	@Override
	public List<N4JSProjectConfigSnapshot> getDependencies(N4JSProjectConfigSnapshot p) {

		Iterable<N4JSProjectConfigSnapshot> directDepsResolved = filterNull(
				map(p.getDependencies(), it -> workspaceConfig.findProjectByID(it)));

		if (ignoreExternalPlainJsProjects) {
			return ImmutableList.copyOf(filter(directDepsResolved, dep -> !isIgnored(dep)));
		} else {
			// this is used by default
			return ImmutableList.copyOf(directDepsResolved);
		}
	}

	private static boolean isIgnored(N4JSProjectConfigSnapshot project) {
		return project.isExternal() && (project.getType() == ProjectType.VALIDATION
				|| project.getType() == ProjectType.PLAINJS);
	}

}
