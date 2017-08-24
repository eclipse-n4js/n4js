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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker.ActivatedPathPredicate;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker.ActivatedPathPredicate.ActivePath;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class MyPathPredicateWalker extends GraphWalker {
	@Override
	protected boolean isBackwards() {
		return false;
	}

	@Override
	protected void visit(ControlFlowElement cfe) {

	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

	}

	class MyActivatedPathPredicate extends ActivatedPathPredicate {

		@Override
		protected void init() {

		}

		@Override
		protected MyActivePath first() {
			return new MyActivePath();
		}

		@Override
		protected void terminate() {

		}

		class MyActivePath extends ActivePath {

			@Override
			protected void init() {

			}

			@Override
			protected void visit(ControlFlowElement cfe) {

			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

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
