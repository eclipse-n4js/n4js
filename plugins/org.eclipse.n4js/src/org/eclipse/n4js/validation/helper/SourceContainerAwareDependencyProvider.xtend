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
package org.eclipse.n4js.validation.helper

import com.google.common.collect.ImmutableList
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.utils.DependencyTraverser
import org.eclipse.n4js.utils.DependencyTraverser.DependencyProvider

/**
 * A {@link DependencyProvider} implementation for traversing the dependency 
 * graph defined by {@link N4JSProjectConfigSnapshot} entities via {@link DependencyTraverser}. 
 */
class SourceContainerAwareDependencyProvider implements DependencyProvider<N4JSProjectConfigSnapshot> {

	private final N4JSWorkspaceConfigSnapshot workspaceConfig;
	private final boolean ignoreExternalPlainJsProjects;

	/** 
	 * Creates a new traverser instance with the given root node.
	 * 
	 * @param rootNode 
	 * 				The root node to start the traversal from.
	 * @param ignoreExternalPlainJsProjects 
	 * 				Specifies whether dependency edges to external {@link ProjectType#PLAINJS} projects should
	 * 				be excluded when traversing the dependency graph.
	 * @param ignoreCycles
	 * 				Specifies whether the traverser should terminate early when dependency cycles are 
	 * 				detected, or whether it should continue.
	 */
	public new(N4JSWorkspaceConfigSnapshot workspaceConfig, boolean ignoreExternalPlainJsProjects) {
		this.workspaceConfig = workspaceConfig;
		this.ignoreExternalPlainJsProjects = ignoreExternalPlainJsProjects;
	}

	override getDependencies(N4JSProjectConfigSnapshot p) {
		val directDepsResolved = p.dependencies.map[workspaceConfig.findProjectByName(it)].filterNull;
		if (ignoreExternalPlainJsProjects) {
			return ImmutableList.copyOf(directDepsResolved.filter[dep|!isIgnored(dep)]);
		} else {
			// this is used by default
			return ImmutableList.copyOf(directDepsResolved);
		}
	}

	private static def boolean isIgnored(N4JSProjectConfigSnapshot project) {
		return project.external && 
			(project.type===ProjectType.VALIDATION || 
				project.type===ProjectType.PLAINJS);
	}

}
