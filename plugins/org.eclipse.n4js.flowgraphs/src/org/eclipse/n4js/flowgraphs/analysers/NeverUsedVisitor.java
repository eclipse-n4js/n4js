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
package org.eclipse.n4js.flowgraphs.analysers;

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorer;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Checks if a local variable is never used.
 */
public class NeverUsedVisitor extends GraphVisitor {
	// TODO GH-235

	NeverUsedVisitor() {
		super(Mode.Forward);
	}

	@Override
	protected void initialize() {
		// nothing to do
	}

	@Override
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminateMode(Mode curMode, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminate() {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof IdentifierRef && cfe.eContainer() instanceof VariableDeclaration) {
			super.requestActivation(new NeverUsedExplorer((IdentifierRef) cfe));
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	private class NeverUsedExplorer extends PathExplorer {
		@SuppressWarnings("unused")
		final IdentifierRef idRef;

		public NeverUsedExplorer(IdentifierRef idRef) {
			super(Quantor.AtLeastOnePath);
			this.idRef = idRef;
		}

		@Override
		protected NeverUsedWalker firstPathWalker() {
			return new NeverUsedWalker();
		}

		private class NeverUsedWalker extends PathWalker {

			@Override
			protected void initialize() {
				// nothing to do
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				// TODO
				// if (cfe instanceof IdentifierRef && flowAnalyses.isRead(cfe, idRef)) {
				// pass();
				// deactivateAll();
				// }
			}

			@Override
			protected void visit(FlowEdge edge) {
				// nothing to do
			}

			@Override
			protected NeverUsedWalker forkPath() {
				return new NeverUsedWalker();
			}

			@Override
			protected void terminate() {
				fail();
			}

		}

	}

}
