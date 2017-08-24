/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Collects all reachable nodes.
 */
public class DeadCodePredicateWalker extends GraphWalker {

	@Override
	protected boolean isBackwards() {
		return false;
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (flowAnalyses.isTop(cfe)) {
			super.activate(new DeadCodeActivatedPathPredicate());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {
		// nothing
	}

	private class DeadCodeActivatedPathPredicate extends ActivatedPathPredicate {
		Set<ControlFlowElement> allCFEs = new HashSet<>();

		@Override
		protected void init() {

		}

		@Override
		protected DeadCodeActivePath first() {
			return new DeadCodeActivePath();
		}

		@Override
		protected void terminate() {

		}

		private class DeadCodeActivePath extends ActivePath {

			@Override
			protected void init() {

			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				allCFEs.add(cfe);
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

			}

			@Override
			protected DeadCodeActivePath fork() {
				return null;
			}

			@Override
			protected void terminate() {

			}

		}
	}

}
