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
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware
import org.eclipse.n4js.utils.DependencyTraverser

/**
 * Class for traversing {@link IN4JSSourceContainerAware source container aware} dependencies and
 * indicating cycles in the dependency graph.
 */
class SoureContainerAwareDependencyTraverser extends DependencyTraverser<IN4JSSourceContainerAware> {

	static val DEPENDENCIES_FUNC = [IN4JSSourceContainerAware it | allDirectDependencies];

	/** Creates a new traverser instance with the given root node. */
	new(IN4JSSourceContainerAware rootNode) {
		super(rootNode, SourceContainerAwareEquivalence.INSTANCE, DEPENDENCIES_FUNC)
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
