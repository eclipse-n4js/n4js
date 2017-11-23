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

/**
 * Walks in {@literal Mode#Backward} and {@literal Mode#Forward} direction through the code.
 */
public class DummyForwardBackwardVisitor extends GraphVisitor {

	/**
	 * Constructor.
	 */
	public DummyForwardBackwardVisitor() {
		super(Mode.Forward, Mode.Backward);
	}

	@Override
	protected void initializeMode(Mode curDirection, ControlFlowElement curContainer) {
		super.requestActivation(new DummyExplorer());
	}

	static class DummyExplorer extends GraphExplorer {
		@Override
		protected DummyWalker firstBranchWalker() {
			return new DummyWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new DummyWalker();
		}
	}

	static class DummyWalker extends BranchWalker {
		@Override
		protected DummyWalker forkPath() {
			return new DummyWalker();
		}
	}

}
