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
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware
import org.eclipse.n4js.utils.DependencyTraverser

/**
 * Class for traversing {@link IN4JSSourceContainerAware source container aware} dependencies and
 * indicating cycles in the dependency graph.
 */
class SoureContainerAwareDependencyTraverser extends DependencyTraverser<IN4JSSourceContainerAware> {

	static val DEPENDENCIES_FUNC = [IN4JSSourceContainerAware p |
		p.allDirectDependencies
	];
	static val DEPENDENCIES_FUNC_IGNORE_EXTERNAL_VALIDATION = [IN4JSSourceContainerAware p |
		ImmutableList.copyOf(p.allDirectDependencies.filter[dep | !isExternalValidation(dep)]);
	] as Function<IN4JSSourceContainerAware, Collection<? extends IN4JSSourceContainerAware>>;

	private boolean ignoreExternalValidationProjects;

	/** Creates a new traverser instance with the given root node. */
	new(IN4JSSourceContainerAware rootNode) {
		this(rootNode, false);
	}

	/** Creates a new traverser instance with the given root node. */
	new(IN4JSSourceContainerAware rootNode, boolean ignoreExternalValidationProjects) {
		super(rootNode, SourceContainerAwareEquivalence.INSTANCE, if (ignoreExternalValidationProjects) DEPENDENCIES_FUNC_IGNORE_EXTERNAL_VALIDATION else DEPENDENCIES_FUNC)
		this.ignoreExternalValidationProjects = ignoreExternalValidationProjects;
	}

	def private static boolean isExternalValidation(IN4JSSourceContainerAware project) {
		return project.external
			&& project instanceof IN4JSProject
			&& (project as IN4JSProject).projectType===ProjectType.VALIDATION;
	}

	/**
	 * {@link Equivalence} implementation for {@link IN4JSSourceContainerAware} instances. Considers only the URI of the
	 * location property.
	 */
	static class SourceContainerAwareEquivalence extends Equivalence<IN4JSSourceContainerAware> {

		private static final SourceContainerAwareEquivalence INSTANCE = new SourceContainerAwareEquivalence();

		override boolean doEquivalent(IN4JSSourceContainerAware a, IN4JSSourceContainerAware b) {
			return if (null === a) b === null else Objects.equal(a.getLocation(), b.getLocation());
		}

		override int doHash(IN4JSSourceContainerAware t) {
			return if (null === t) 0 else Objects.hashCode(t.getLocation());
		}

	}

}
