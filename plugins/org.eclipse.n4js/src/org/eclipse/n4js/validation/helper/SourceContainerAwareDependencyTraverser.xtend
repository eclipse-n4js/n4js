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
import com.google.common.base.Function
import com.google.common.base.Objects
import com.google.common.collect.ImmutableList
import java.util.Collection
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.utils.DependencyTraverser

/**
 * Class for traversing {@link IN4JSProject source container aware} dependencies and
 * indicating cycles in the dependency graph.
 */
class SourceContainerAwareDependencyTraverser extends DependencyTraverser<IN4JSProject> {

	// this is used by default:
	static val DEPENDENCIES_FUNC = [ IN4JSProject p |
		p.allDirectDependencies
	];
	// this is used if external projects of project type VALIDATION are requested to be ignored:
	static val DEPENDENCIES_FUNC_IGNORE_EXTERNAL_VALIDATION = [ IN4JSProject p |
		ImmutableList.copyOf(p.allDirectDependencies.filter[dep|!isExternalValidation(dep)]);
	] as Function<IN4JSProject, Collection<? extends IN4JSProject>>;

	/** Creates a new traverser instance with the given root node. */
	new(IN4JSProject rootNode) {
		this(rootNode, false);
	}

	/** Creates a new traverser instance with the given root node. */
	new(IN4JSProject rootNode, boolean ignoreExternalValidationProjects) {
		super(
			rootNode,
			SourceContainerAwareEquivalence.INSTANCE,
			if (ignoreExternalValidationProjects)
				DEPENDENCIES_FUNC_IGNORE_EXTERNAL_VALIDATION
			else
				DEPENDENCIES_FUNC
		)
	}

	def private static boolean isExternalValidation(IN4JSProject project) {
		return project.external
			&& project.projectType===ProjectType.VALIDATION;
	}

	/**
	 * {@link Equivalence} implementation for {@link IN4JSProject} instances. Considers only the URI of the
	 * location property.
	 */
	static class SourceContainerAwareEquivalence extends Equivalence<IN4JSProject> {

		private static final SourceContainerAwareEquivalence INSTANCE = new SourceContainerAwareEquivalence();

		override boolean doEquivalent(IN4JSProject a, IN4JSProject b) {
			return if (null === a) b === null else Objects.equal(a.getLocation(), b.getLocation());
		}

		override int doHash(IN4JSProject t) {
			return if (null === t) 0 else Objects.hashCode(t.getLocation());
		}

	}

}
