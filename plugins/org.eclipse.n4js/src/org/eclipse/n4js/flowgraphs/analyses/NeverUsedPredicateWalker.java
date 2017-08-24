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
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 *
 */
public class NeverUsedPredicateWalker extends GraphWalker {
	@Override
	protected boolean isBackwards() {
		return false;
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof IdentifierRef && cfe.eContainer() instanceof VariableDeclaration) {
			super.activate(new NeverUsedActivatedPathPredicate((IdentifierRef) cfe));
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

	}

	private class NeverUsedActivatedPathPredicate extends ActivatedPathPredicate {
		final IdentifierRef idRef;
		private final boolean usedSomewhere;

		public NeverUsedActivatedPathPredicate(IdentifierRef idRef) {
			this.idRef = idRef;
			usedSomewhere = false;
		}

		@Override
		protected void init() {
		}

		@Override
		protected NeverUsedActivePath first() {
			return new NeverUsedActivePath();
		}

		@Override
		protected void terminate() {

		}

		private class NeverUsedActivePath extends ActivePath {

			@Override
			protected void init() {

			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				if (cfe instanceof IdentifierRef && flowAnalyses.isRead(cfe)) {
					usedSomewhere = true;
					deactivateAll();
				}
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

			}

			@Override
			protected NeverUsedActivePath fork() {
				return null;
			}

			@Override
			protected void terminate() {

			}

		}

	}

}
