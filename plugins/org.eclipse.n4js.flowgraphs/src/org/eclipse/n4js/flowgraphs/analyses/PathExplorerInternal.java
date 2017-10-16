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
 * An {@link PathExplorerInternal} is created and spawned from a {@link GraphVisitorInternal} on specific preconditions.
 * It follows all paths beginning from the location of activation. Its initial path is forked using the method
 * {@link #firstPathWalker()}. Subsequent paths are forked from the initial paths. The {@link PathExplorerInternal} is
 * deactivated in case it has no active paths anymore. The final state of a {@link PathExplorerInternal} can be either
 * <i>Passed</i> or <i>Failed</i>.
 * <p/>
 * The life cycle of a {@link PathExplorerInternal}:
 * <ol>
 * <li/>Instantiation
 * <li/>Request for activation
 * <li/>Activation
 * <li/>Call to {@link PathExplorerInternal#firstPathWalker()}
 * <li/>De-Activation when all its {@link PathWalkerInternal}s are inactive
 * <li/>Evaluation by user by calling e.g. {@link PathExplorerInternal#isPassed()}
 * </ol>
 */
abstract public class PathExplorerInternal {
	final Set<PathWalkerInternal> activePaths = new HashSet<>();
	final List<PathWalkerInternal> passedPaths = new LinkedList<>();
	final List<PathWalkerInternal> failedPaths = new LinkedList<>();
	final List<PathWalkerInternal> allPaths = new LinkedList<>();
	/** Quantor, specified in constructor */
	protected final Quantor quantor;
	/** Default verdict, specified in constructor */
	protected final boolean passAsDefault;
	/** Parent {@link GraphVisitorInternal}. Always set once. */
	private GraphVisitorInternal parentGraphVisitor;

	private State state;

	/** The {@link Quantor} defines under which condition a {@link PathExplorerInternal} fails or passes. */
	public enum Quantor {
		/** No specific condition. */
		None,
		/** The {@link PathExplorerInternal} passes iff all paths pass. */
		ForAllPaths,
		/** The {@link PathExplorerInternal} passes if at least one path passes. */
		AtLeastOnePath
	}

	/** The {@link State} defines the current state of a {@link PathExplorerInternal}. */
	public enum State {
		/** The {@link PathExplorerInternal} is active. */
		Active,
		/** The {@link PathExplorerInternal} terminated with no specific verdict. */
		Terminated,
		/** The {@link PathExplorerInternal} terminated with the verdict <i>passed</i>. */
		Passed,
		/** The {@link PathExplorerInternal} terminated with the verdict <i>failed</i>. */
		Failed
	}

	/**
	 * Constructor
	 * <p>
	 * The {@link PathExplorerInternal} without support for the verdicts pass or fail.
	 *
	 */
	protected PathExplorerInternal() {
		this(Quantor.None, false);
	}

	/**
	 * Constructor
	 * <p>
	 * The {@link PathExplorerInternal} will pass as default in case {@link PathWalkerInternal#fail()} is never called
	 * on any of its active paths.
	 *
	 * @param quantor
	 *            defines fail/pass condition
	 */
	protected PathExplorerInternal(Quantor quantor) {
		this(quantor, true);
	}

	/**
	 * Constructor
	 *
	 * @param quantor
	 *            defines fail/pass condition
	 * @param passAsDefault
	 *            iff true, the {@link PathExplorerInternal} will pass as default
	 */
	protected PathExplorerInternal(Quantor quantor, boolean passAsDefault) {
		this.quantor = quantor;
		this.passAsDefault = passAsDefault;
		this.state = State.Active;
	}

	/////////////////////// Abstract Methods ///////////////////////

	/** Spawns the first path. Called right after this {@link PathExplorerInternal} gets activated. */
	abstract protected PathWalkerInternal firstPathWalker();

	/////////////////////// Methods called from {@link GraphVisitorInternal} ///////////////////////

	/** Only called from {@link GraphVisitorInternal}. Delegates to {@link #firstPathWalker()}. */
	final PathWalkerInternal callFirstPathWalker(GraphVisitorInternal parentGraphVisitorInternal) {
		parentGraphVisitor = parentGraphVisitorInternal;
		PathWalkerInternal activePath = firstPathWalker();
		activePaths.add(activePath);
		allPaths.add(activePath);
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

	/** @return true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Passed</i> */
	final public boolean isPassed() {
		return state == State.Passed;
	}

	/** @return true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Failed</i> */
	final public boolean isFailed() {
		return state == State.Failed;
	}

	/** Deactivates all active paths and hence this {@link PathExplorerInternal}. */
	final public void deactivateAll() {
		while (!activePaths.isEmpty()) {
			PathWalkerInternal aPath = activePaths.iterator().next();
			aPath.deactivate();
		}
		checkExplorerDeactivation();
	}

	/** @return all paths no matter if they are active or not, or passed or failed. */
	final public List<PathWalkerInternal> getAllPaths() {
		return allPaths;
	}

	/** @return all paths no matter if they are active or not, or passed or failed. */
	final public Set<PathWalkerInternal> getActivePaths() {
		return activePaths;
	}

}
