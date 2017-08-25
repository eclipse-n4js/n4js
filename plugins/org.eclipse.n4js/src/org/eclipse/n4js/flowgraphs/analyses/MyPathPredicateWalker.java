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

import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class MyPathPredicateWalker extends GraphWalker {

	MyPathPredicateWalker() {
		super(Direction.Forward);
	}

	@Override
	protected void init() {
	}

	@Override
	protected void terminate() {
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
	}

	class MyActivatedPathPredicate extends ActivatedPathPredicate {

		MyActivatedPathPredicate() {
			super(PredicateType.ForAllPaths);
		}

		@Override
		protected MyActivePath first() {
			return new MyActivePath();
		}

		class MyActivePath extends ActivePath {

			@Override
			protected void init() {
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
			}

			@Override
			protected MyActivePath fork() {
				return null;
			}

			@Override
			protected void terminate() {
			}

		}
	}

}
