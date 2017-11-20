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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 *
 */
abstract public class FastFlowVisitor extends GraphVisitor {
	static int branCount = 0;
	final CheckVariablePathExplorer cvpe;

	public abstract static class ActivationLocation {
		private ControlFlowElement activationLocation;

		abstract public Object getKey();

		public ControlFlowElement getLocation() {
			return activationLocation;
		}
	}

	abstract protected void visitNext(FastFlowBranch currentBranch, ControlFlowElement cfe);

	/** Constructor */
	public FastFlowVisitor() {
		super(Mode.Backward);
		cvpe = new CheckVariablePathExplorer();
	}

	@Override
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		super.requestActivation(cvpe);
	}

	@Override
	protected void terminate() {
		// System.out.println("branches: " + branCount);
	}

	class CheckVariablePathExplorer extends GraphExplorer {
		final Map<VariableDeclaration, List<IdentifierRef>> checkLists = new HashMap<>();

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new FastFlowBranch();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			FastFlowBranch joinedWalker = new FastFlowBranch();
			for (BranchWalker bW : branchWalkers) {
				FastFlowBranch cvbw = (FastFlowBranch) bW;
				joinedWalker.actLocs.putAll(cvbw.actLocs);
			}
			return joinedWalker;
		}
	}

	protected class FastFlowBranch extends BranchWalker {
		final Map<Object, ActivationLocation> actLocs = new HashMap<>();

		FastFlowBranch() {
			branCount++;
		}

		@Override
		protected FastFlowBranch forkPath() {
			FastFlowBranch newBranch = new FastFlowBranch();
			newBranch.actLocs.putAll(actLocs);
			return newBranch;
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			visitNext(this, cfe);
		}

		public void activate(ActivationLocation activationLocation) {
			actLocs.put(activationLocation.getKey(), activationLocation);
		}

		public ActivationLocation getActivationLocation(Object key) {
			return actLocs.get(key);
		}

		public Collection<ActivationLocation> getAllActivationLocations() {
			return actLocs.values();
		}
	}

}
