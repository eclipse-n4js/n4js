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

import java.util.List;

import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
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
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof IdentifierRef && cfe.eContainer() instanceof VariableDeclaration) {
			super.requestActivation(new NeverUsedExplorer((IdentifierRef) cfe));
		}
	}

	static private class NeverUsedExplorer extends GraphExplorer {
		@SuppressWarnings("unused")
		final IdentifierRef idRef;

		public NeverUsedExplorer(IdentifierRef idRef) {
			super(Quantor.AtLeastOneBranch);
			this.idRef = idRef;
		}

		@Override
		protected NeverUsedWalker firstBranchWalker() {
			return new NeverUsedWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new NeverUsedWalker();
		}

	}

	static private class NeverUsedWalker extends BranchWalker {

		@Override
		protected void visit(ControlFlowElement cfe) {
			// TODO
			// if (cfe instanceof IdentifierRef && flowAnalyses.isRead(cfe, idRef)) {
			// pass();
			// deactivateAll();
			// }
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
