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
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware
import org.eclipse.n4js.utils.DependencyTraverser.DependencyVisitor

/**
 * Class for traversing {@link IN4JSSourceContainerAware source container aware} dependencies and
 * finding cycles in the dependency graph.
 */
class SourceContainerAwareDependencyVisitor implements DependencyVisitor<IN4JSSourceContainerAware> {

	private final boolean ignoreExternalValidationProjects;
	
	/** 
	 * Creates a new traverser instance with the given root node.
	 * 
	 * @param rootNode 
	 * 				The root node to start the traversal from.
	 * @param ignoreExternalValidationProjects 
	 * 				Specifies whether dependency edges to external {@link ProjectType#VALIDATION} projects should
	 * 				be excluded when traversing the dependency graph.
	 * @param ignoreCycles
	 * 				Specifies whether the traverser should terminate early when dependency cycles are 
	 * 				detected, or whether it should continue.
	 */
	public new(boolean ignoreExternalValidationProjects) {
		this.ignoreExternalValidationProjects = ignoreExternalValidationProjects;
	}
	
	override accept(IN4JSSourceContainerAware p) {
		if (ignoreExternalValidationProjects) {
			// this is used if external projects of project type VALIDATION are requested to be ignored
			return ImmutableList.copyOf(p.allDirectDependencies.filter[dep|!isExternalValidation(dep)]);
		} else {
			// this is used by default
			return p.allDirectDependencies;
		}
	}
	
	private static def boolean isExternalValidation(IN4JSSourceContainerAware project) {
		return project.external
			&& project instanceof IN4JSProject
			&& (project as IN4JSProject).projectType===ProjectType.VALIDATION;
	}

}
