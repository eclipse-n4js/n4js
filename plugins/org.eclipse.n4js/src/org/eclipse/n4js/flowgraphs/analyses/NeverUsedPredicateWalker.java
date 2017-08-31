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

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Checks if a local variable is never used.
 */
public class NeverUsedPredicateWalker extends GraphWalker {

	NeverUsedPredicateWalker() {
		super(Direction.Forward);
	}

	@Override
	protected void initAll() {
		// nothing to do
	}

	@Override
	protected void init(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminate(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminateAll() {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof IdentifierRef && cfe.eContainer() instanceof VariableDeclaration) {
			super.requestActivation(new NeverUsedActivatedPathPredicate((IdentifierRef) cfe));
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	private class NeverUsedActivatedPathPredicate extends ActivatedPathPredicate {
		final IdentifierRef idRef;

		public NeverUsedActivatedPathPredicate(IdentifierRef idRef) {
			super(PredicateType.ForOnePath);
			this.idRef = idRef;
		}

		@Override
		protected NeverUsedActivePath firstPath() {
			return new NeverUsedActivePath();
		}

		private class NeverUsedActivePath extends ActivePath {

			@Override
			protected void init() {
				// nothing to do
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				if (cfe instanceof IdentifierRef && flowAnalyses.isRead(cfe, idRef)) {
					pass();
					deactivateAll();
				}
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
				// nothing to do
			}

			@Override
			protected NeverUsedActivePath forkPath() {
				return new NeverUsedActivePath();
			}

			@Override
			protected void terminate() {
				fail();
			}

		}

	}

}
