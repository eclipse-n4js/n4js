/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * An {@link GraphExplorerInternal} is created and spawned from a {@link GraphVisitorInternal} on specific
 * preconditions. It follows all branches beginning from the location of activation. Its initial branch is forked using
 * the method {@link #firstBranchWalker()}. Subsequent branches are forked from the initial branches. The
 * {@link GraphExplorerInternal} is deactivated in case it has no active branches anymore. The final state of a
 * {@link GraphExplorerInternal} can be either <i>Passed</i> or <i>Failed</i>.
 * <p/>
 * The life cycle of a {@link GraphExplorerInternal}:
 * <ol>
 * <li/>Instantiation
 * <li/>Request for activation
 * <li/>Activation
 * <li/>Call to {@link GraphExplorerInternal#firstBranchWalker()}
 * <li/>De-Activation when all its {@link BranchWalkerInternal}s are inactive
 * <li/>Evaluation by user by calling e.g. {@link GraphExplorerInternal#isPassed()}
 * </ol>
 */
abstract public class GraphExplorerInternal {
	final Set<BranchWalkerInternal> activeBranches = new HashSet<>();
	final List<BranchWalkerInternal> passedBranches = new LinkedList<>();
	final List<BranchWalkerInternal> failedBranchs = new LinkedList<>();
	final List<BranchWalkerInternal> allBranches = new LinkedList<>();
	/** Quantor, specified in constructor */
	protected final Quantor quantor;
	/** Default verdict, specified in constructor */
	protected final boolean passAsDefault;
	/** First branch created by {@link #firstBranchWalker()} */
	private BranchWalkerInternal firstBranch;
	/** Parent {@link GraphVisitorInternal}. Always set once. */
	private GraphVisitorInternal parentGraphVisitor;
	/** Current state of this {@link GraphExplorerInternal} */
	private State state;
	/** Branch counter */
	private int branchCounter = 0;

	/** The {@link Quantor} defines under which condition a {@link GraphExplorerInternal} fails or passes. */
	public enum Quantor {
		/** No specific condition. */
		None,
		/** The {@link GraphExplorerInternal} passes iff all branches pass. */
		ForAllBranches,
		/** The {@link GraphExplorerInternal} passes if at least one branch passes. */
		AtLeastOneBranch
	}

	/** The {@link State} defines the current state of a {@link GraphExplorerInternal}. */
	public enum State {
		/** The {@link GraphExplorerInternal} is active. */
		Active,
		/** The {@link GraphExplorerInternal} terminated with no specific verdict. */
		Terminated,
		/** The {@link GraphExplorerInternal} terminated with the verdict <i>passed</i>. */
		Passed,
		/** The {@link GraphExplorerInternal} terminated with the verdict <i>failed</i>. */
		Failed
	}

	/**
	 * Constructor
	 * <p>
	 * The {@link GraphExplorerInternal} without support for the verdicts pass or fail.
	 *
	 */
	protected GraphExplorerInternal() {
		this(Quantor.None, false);
	}

	/**
	 * Constructor
	 * <p>
	 * The {@link GraphExplorerInternal} will pass as default in case {@link BranchWalkerInternal#fail()} is never
	 * called on any of its active branches.
	 *
	 * @param quantor
	 *            defines fail/pass condition
	 */
	protected GraphExplorerInternal(Quantor quantor) {
		this(quantor, true);
	}

	/**
	 * Constructor
	 *
	 * @param quantor
	 *            defines fail/pass condition
	 * @param passAsDefault
	 *            iff true, the {@link GraphExplorerInternal} will pass as default
	 */
	protected GraphExplorerInternal(Quantor quantor, boolean passAsDefault) {
		this.quantor = quantor;
		this.passAsDefault = passAsDefault;
		this.state = State.Active;
	}

	/////////////////////// Abstract Methods ///////////////////////

	/** Spawns the first branch. Called right after this {@link GraphExplorerInternal} gets activated. */
	abstract protected BranchWalkerInternal firstBranchWalker();

	/** Joins two branches and returns a new one. */
	abstract protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers);

	/////////////////////// Methods called from {@link GraphVisitorInternal} ///////////////////////

	/** Only called from {@link GraphVisitorInternal}. Delegates to {@link #firstBranchWalker()}. */
	final BranchWalkerInternal callFirstBranchWalker(GraphVisitorInternal parentGraphVisitorInternal) {
		parentGraphVisitor = parentGraphVisitorInternal;
		firstBranch = firstBranchWalker();
		firstBranch.callInitialize(this);
		return firstBranch;
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #joinBranchWalkers(List)}.
	 */
	final BranchWalkerInternal callJoinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
		if (branchWalkers.size() == 1) {
			return branchWalkers.get(0);
		}

		BranchWalkerInternal activeBranch = joinBranchWalkers(branchWalkers);
		for (BranchWalkerInternal bW : branchWalkers) {
			activeBranches.remove(bW);
			bW.deactivate();
		}
		activeBranch.callInitialize(this, branchWalkers);
		return activeBranch;
	}

	/** Only called from {@link BranchWalkerInternal}. Performs checked deactivation and state updates. */
	final void checkExplorerDeactivation() {
		if (activeBranches.isEmpty()) {
			boolean somePassed = !passedBranches.isEmpty();
			boolean someFailed = !failedBranchs.isEmpty();

			switch (quantor) {
			case AtLeastOneBranch:
			case ForAllBranches:
				boolean explorerPassed = false;
				explorerPassed |= somePassed && quantor == Quantor.AtLeastOneBranch;
				explorerPassed |= somePassed && !someFailed && quantor == Quantor.ForAllBranches;
				explorerPassed |= !somePassed && !someFailed && passAsDefault;
				if (explorerPassed) {
					state = State.Passed;
				} else {
					state = State.Failed;
				}
				break;

			case None:
				state = State.Terminated;
				break;
			}

			parentGraphVisitor.deactivateGraphExplorer(this);
		}
	}

	/** Only called from {@link BranchWalkerInternal}. Returns and increments the current branch count. */
	final int getAndIncrementBranchCounter() {
		return branchCounter++;
	}

	/////////////////////// Service Methods for inherited classes ///////////////////////

	/** @return true, iff the {@link GraphExplorerInternal} is terminated and has verdict <i>Passed</i> */
	final public boolean isPassed() {
		return state == State.Passed;
	}

	/** @return true, iff the {@link GraphExplorerInternal} is terminated and has verdict <i>Failed</i> */
	final public boolean isFailed() {
		return state == State.Failed;
	}

	/** Deactivates all active branches and hence this {@link GraphExplorerInternal}. */
	final public void deactivateAll() {
		while (!activeBranches.isEmpty()) {
			BranchWalkerInternal aBranch = activeBranches.iterator().next();
			aBranch.deactivate();
		}
		checkExplorerDeactivation();
	}

	/** @return all branches no matter if they are active or not, or passed or failed. */
	final public List<BranchWalkerInternal> getAllBranches() {
		return allBranches;
	}

	/** @return all branches no matter if they are active or not, or passed or failed. */
	final public Set<BranchWalkerInternal> getActiveBranches() {
		return activeBranches;
	}

	/** @return the first {@link BranchWalkerInternal} of this {@link GraphExplorerInternal} instance. */
	final public BranchWalkerInternal getFirstBranch() {
		return firstBranch;
	}

}
