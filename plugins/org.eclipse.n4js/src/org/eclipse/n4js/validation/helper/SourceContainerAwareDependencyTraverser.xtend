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

import com.google.common.base.Equivalence
import com.google.common.base.Objects
import com.google.common.collect.ImmutableList
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware
import org.eclipse.n4js.utils.DependencyTraverser
import org.eclipse.n4js.utils.DependencyTraverser.DependencyVisitor

/**
 * Class for traversing {@link IN4JSSourceContainerAware source container aware} dependencies and
 * finding cycles in the dependency graph.
 */
class SourceContainerAwareDependencyTraverser extends DependencyTraverser<IN4JSSourceContainerAware> {

	// this is used by default:
	private static val DEPENDENCIES_VISITOR = new DependencyVisitor<IN4JSSourceContainerAware> () {
		override visit(IN4JSSourceContainerAware p) {
			return p.allDirectDependencies;
		}
	} 
	
	// this is used if external projects of project type VALIDATION are requested to be ignored:
	private static val DEPENDENCIES_VISITOR_IGNORE_EXTERNAL_VALIDATION = new DependencyVisitor<IN4JSSourceContainerAware> () {
		override visit(IN4JSSourceContainerAware p) {
			return ImmutableList.copyOf(p.allDirectDependencies.filter[dep|!isExternalValidation(dep)]);
		}
	}

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
	public new(IN4JSSourceContainerAware rootNode, boolean ignoreExternalValidationProjects, boolean ignoreCycles) {
		super(
			rootNode,
			SourceContainerAwareEquivalence.INSTANCE,
			(if (ignoreExternalValidationProjects) DEPENDENCIES_VISITOR_IGNORE_EXTERNAL_VALIDATION else DEPENDENCIES_VISITOR),
			ignoreCycles
		)
	}

	private static def boolean isExternalValidation(IN4JSSourceContainerAware project) {
		return project.external
			&& project instanceof IN4JSProject
			&& (project as IN4JSProject).projectType===ProjectType.VALIDATION;
	}

	/**
	 * {@link Equivalence} implementation for {@link IN4JSSourceContainerAware} instances. Considers only the URI of the
	 * location property.
	 */
	private static class SourceContainerAwareEquivalence extends Equivalence<IN4JSSourceContainerAware> {

		private static final SourceContainerAwareEquivalence INSTANCE = new SourceContainerAwareEquivalence();

		override boolean doEquivalent(IN4JSSourceContainerAware a, IN4JSSourceContainerAware b) {
			return if (null === a) b === null else Objects.equal(a.getLocation(), b.getLocation());
		}

		override int doHash(IN4JSSourceContainerAware t) {
			return if (null === t) 0 else Objects.hashCode(t.getLocation());
		}

	}

}
