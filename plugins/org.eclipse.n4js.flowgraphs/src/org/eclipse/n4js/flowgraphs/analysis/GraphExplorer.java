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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.List;

/**
 * see {@link GraphVisitorInternal}
 */
abstract public class GraphExplorer extends GraphExplorerInternal {

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal()}
	 */
	protected GraphExplorer() {
		super();
	}

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal(Quantor)}
	 */
	protected GraphExplorer(Quantor quantor) {
		super(quantor);
	}

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal(Quantor, boolean)}
	 */
	protected GraphExplorer(Quantor quantor, boolean passAsDefault) {
		super(quantor, passAsDefault);
	}

	/** Joins two branches and returns a new one. */
	abstract protected BranchWalker joinBranches(List<BranchWalker> branchWalkers);

	/** Joins two branches and returns a new one. */
	@Override
	final protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
		@SuppressWarnings("unchecked")
		List<BranchWalker> bWalkers = (List<BranchWalker>) (List<?>) branchWalkers;
		BranchWalker joinedBranch = joinBranches(bWalkers);
		return joinedBranch;
	}

}
