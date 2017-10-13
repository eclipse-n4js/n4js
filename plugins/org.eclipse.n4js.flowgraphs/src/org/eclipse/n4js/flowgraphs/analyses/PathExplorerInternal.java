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

import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

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
	private final Set<PathWalkerInternal> activePaths = new HashSet<>();
	private final List<PathWalkerInternal> passedPaths = new LinkedList<>();
	private final List<PathWalkerInternal> failedPaths = new LinkedList<>();
	private final List<PathWalkerInternal> allPaths = new LinkedList<>();
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
		activePath.initialize();
		return activePath;
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

	/** @return all paths no matter if they are active or not, or passed or failed. */
	final public List<PathWalkerInternal> getAllPaths() {
		return allPaths;
	}

	/** Deactivates all active paths and hence this {@link PathExplorerInternal}. */
	final protected void deactivateAll() {
		while (!activePaths.isEmpty()) {
			PathWalkerInternal aPath = activePaths.iterator().next();
			aPath.deactivate();
		}
		checkExplorerDeactivation();
	}

	private void checkExplorerDeactivation() {
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

	/**
	 * Paths begin when a {@link PathExplorerInternal} gets active or when forked from another path. Paths end when no
	 * succeeding nodes are in the control flow graph, or the user calls either {@link #pass()}, {@link #fail()}, or
	 * {@link #deactivate()}. Paths follow every edge and fork on every node that has more than one edge. However, each
	 * edge of type {@literal ControlFlowType.Repeat} is followed exactly twice.
	 */
	abstract public class PathWalkerInternal {

		/////////////////////// Abstract Methods ///////////////////////

		/** Called before any other method of this instance is called. */
		abstract protected void initialize();

		/** Called for each node in the order of nodes on the current path. */
		abstract protected void visit(Node node);

		/**
		 * Called for each edge in the order of edges on the current path.
		 *
		 * @param edge
		 *            traversed edge
		 */
		abstract protected void visit(Node start, Node end, ControlFlowEdge edge);

		/**
		 * Forks another path from the current node.<br/>
		 * <i>Take care about forking the current state of this instance!</i>
		 */
		abstract protected PathWalkerInternal fork();

		/** Called at last. */
		abstract protected void terminate();

		/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

		/**
		 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link PathWalkerInternal#visit(Node)}.
		 */
		final void callVisit(Node node) {
			visit(node);
		}

		/**
		 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
		 * {@link PathWalkerInternal#visit(Node, Node, ControlFlowEdge)}.
		 */
		final void callVisit(Node start, Node end, ControlFlowEdge edge) {
			visit(start, end, edge);
		}

		/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #fork()}. */
		final PathWalkerInternal callFork() {
			PathWalkerInternal forkedPath = fork();
			allPaths.add(forkedPath);
			activePaths.add(forkedPath);
			forkedPath.initialize();
			return forkedPath;
		}

		/////////////////////// Service Methods for inherited classes ///////////////////////

		/** Sets the verdict of this path to <i>Passed</i>. */
		final public void pass() {
			passedPaths.add(this);
			if (quantor == Quantor.AtLeastOnePath) {
				deactivateAll();
			} else {
				deactivate();
			}
		}

		/** Sets the verdict of this path to <i>Failed</i>. */
		final public void fail() {
			failedPaths.add(this);
			if (quantor == Quantor.ForAllPaths) {
				deactivateAll();
			} else {
				deactivate();
			}
		}

		/** Deactivates this path without setting the verdict. */
		final public void deactivate() {
			activePaths.remove(this);
			terminate();
			checkExplorerDeactivation();
		}

		/** @return true, iff this path is active. */
		final public boolean isActive() {
			return activePaths.contains(this);
		}

	}

}
