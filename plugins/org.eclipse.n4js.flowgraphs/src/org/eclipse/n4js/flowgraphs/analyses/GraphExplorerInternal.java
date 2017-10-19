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
 * preconditions. It follows all paths beginning from the location of activation. Its initial path is forked using the
 * method {@link #firstPathWalker()}. Subsequent paths are forked from the initial paths. The
 * {@link GraphExplorerInternal} is deactivated in case it has no active paths anymore. The final state of a
 * {@link GraphExplorerInternal} can be either <i>Passed</i> or <i>Failed</i>.
 * <p/>
 * The life cycle of a {@link GraphExplorerInternal}:
 * <ol>
 * <li/>Instantiation
 * <li/>Request for activation
 * <li/>Activation
 * <li/>Call to {@link GraphExplorerInternal#firstPathWalker()}
 * <li/>De-Activation when all its {@link BranchWalkerInternal}s are inactive
 * <li/>Evaluation by user by calling e.g. {@link GraphExplorerInternal#isPassed()}
 * </ol>
 */
abstract public class GraphExplorerInternal {
	final Set<BranchWalkerInternal> activePaths = new HashSet<>();
	final List<BranchWalkerInternal> passedPaths = new LinkedList<>();
	final List<BranchWalkerInternal> failedPaths = new LinkedList<>();
	final List<BranchWalkerInternal> allPaths = new LinkedList<>();
	/** Quantor, specified in constructor */
	protected final Quantor quantor;
	/** Default verdict, specified in constructor */
	protected final boolean passAsDefault;
	/** Parent {@link GraphVisitorInternal}. Always set once. */
	private GraphVisitorInternal parentGraphVisitor;

	private State state;

	/** The {@link Quantor} defines under which condition a {@link GraphExplorerInternal} fails or passes. */
	public enum Quantor {
		/** No specific condition. */
		None,
		/** The {@link GraphExplorerInternal} passes iff all paths pass. */
		ForAllPaths,
		/** The {@link GraphExplorerInternal} passes if at least one path passes. */
		AtLeastOnePath
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
	 * called on any of its active paths.
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

	/** Spawns the first path. Called right after this {@link GraphExplorerInternal} gets activated. */
	abstract protected BranchWalkerInternal firstPathWalker();

	/////////////////////// Methods called from {@link GraphVisitorInternal} ///////////////////////

	/** Only called from {@link GraphVisitorInternal}. Delegates to {@link #firstPathWalker()}. */
	final BranchWalkerInternal callFirstPathWalker(GraphVisitorInternal parentGraphVisitorInternal) {
		parentGraphVisitor = parentGraphVisitorInternal;
		BranchWalkerInternal activePath = firstPathWalker();
		activePath.callInitialize(this, null);
		return activePath;
	}

	final void checkExplorerDeactivation() {
		if (activePaths.isEmpty()) {
			boolean somePassed = !passedPaths.isEmpty();
			boolean someFailed = !failedPaths.isEmpty();

			switch (quantor) {
			case AtLeastOnePath:
			case ForAllPaths:
				boolean explorerPassed = false;
				explorerPassed |= somePassed && quantor == Quantor.AtLeastOnePath;
				explorerPassed |= somePassed && !someFailed && quantor == Quantor.ForAllPaths;
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

			parentGraphVisitor.deactivatePathExplorer(this);
		}
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

	/** Deactivates all active paths and hence this {@link GraphExplorerInternal}. */
	final public void deactivateAll() {
		while (!activePaths.isEmpty()) {
			BranchWalkerInternal aPath = activePaths.iterator().next();
			aPath.deactivate();
		}
		checkExplorerDeactivation();
	}

	/** @return all paths no matter if they are active or not, or passed or failed. */
	final public List<BranchWalkerInternal> getAllPaths() {
		return allPaths;
	}

	/** @return all paths no matter if they are active or not, or passed or failed. */
	final public Set<BranchWalkerInternal> getActivePaths() {
		return activePaths;
	}

}
