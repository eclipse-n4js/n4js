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

import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal.Quantor;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * Paths begin when a {@link BranchWalkerInternal} gets active or when forked from another path. Paths end when no
 * succeeding nodes are in the control flow graph, or the user calls either {@link #pass()}, {@link #fail()}, or
 * {@link #deactivate()}. Paths follow every edge and fork on every node that has more than one edge. However, each edge
 * of type {@literal ControlFlowType.Repeat} is followed exactly twice.
 */
abstract public class BranchWalkerInternal {
	private GraphExplorerInternal pathExplorer;
	private BranchWalkerInternal pathPredecessor;
	private BranchWalkerInternal pathSuccessor;

	/////////////////////// Abstract Methods ///////////////////////

	/** Called before any other method of this instance is called. */
	protected void initialize() {
		// overwrite me
	}

	/**
	 * Called for each node in the order of nodes on the current path.
	 *
	 * @param node
	 *            node that gets visits
	 */
	protected void visit(Node node) {
		// overwrite me
	}

	/**
	 * Called for each edge in the order of edges on the current path.
	 *
	 * @param start
	 *            node that was visited before
	 * @param end
	 *            node that will be visited next
	 * @param edge
	 *            traversed edge
	 */
	protected void visit(Node start, Node end, ControlFlowEdge edge) {
		// overwrite me
	}

	/**
	 * Forks another path from the current node.<br/>
	 * <i>Take care about forking the current state of this instance!</i>
	 */
	abstract protected BranchWalkerInternal fork();

	/**
	 * Iff {@code true} returned, this {@link BranchWalkerInternal} will be joined. Otherwise joining is omitted and this
	 * {@link BranchWalkerInternal} will traverse the paths in parallel to other {@link BranchWalkerInternal}s.
	 * <p>
	 * <b>Attention:</b> If there are more than 1000 {@link BranchWalkerInternal}, the {@link BranchWalkerInternal} with the
	 * most {@link BranchWalkerInternal}s will be cancelled.
	 */
	protected boolean isJoinable() {
		return true;
	}

	/**
	 * Called when another {@link BranchWalkerInternal} is joined into this {@link BranchWalkerInternal} instance.
	 *
	 * @param joiningPWI
	 *            will receive the method call {@link #joinedWith(BranchWalkerInternal)} and then terminate
	 */
	protected void join(BranchWalkerInternal joiningPWI) {
		// overwrite me
	}

	/**
	 * Called after this {@link BranchWalkerInternal} instance was joined into the {code joinSurvivor}
	 * {@link BranchWalkerInternal} instance.
	 * <p>
	 * After this method succeeded, {@link #terminate()} gets called.
	 *
	 * @param joinSurvivor
	 *            will continue to walk the path.
	 */
	protected void joinedWith(BranchWalkerInternal joinSurvivor) {
		// overwrite me
	}

	/** Called at last. */
	protected void terminate() {
		// overwrite me
	}

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link BranchWalkerInternal#initialize()}.
	 */
	protected void callInitialize(GraphExplorerInternal explorer, BranchWalkerInternal predecessor) {
		this.pathExplorer = explorer;
		this.pathPredecessor = predecessor;
		pathExplorer.allPaths.add(this);
		pathExplorer.activePaths.add(this);
		initialize();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link BranchWalkerInternal#visit(Node)}.
	 */
	final void callVisit(Node node) {
		visit(node);
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link BranchWalkerInternal#visit(Node, Node, ControlFlowEdge)}.
	 */
	final void callVisit(Node start, Node end, ControlFlowEdge edge) {
		visit(start, end, edge);
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #fork()}. */
	final BranchWalkerInternal callFork() {
		BranchWalkerInternal forkedPath = fork();
		forkedPath.callInitialize(pathExplorer, this);
		return forkedPath;
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link BranchWalkerInternal#join(BranchWalkerInternal)}.
	 */
	final void callJoin(BranchWalkerInternal joiningPWI) {
		join(joiningPWI);
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link BranchWalkerInternal#joinedWith(BranchWalkerInternal)}.
	 */
	final void callJoinedWith(BranchWalkerInternal joinSurvivor) {
		pathSuccessor = joinSurvivor;
		joinedWith(joinSurvivor);
	}

	/////////////////////// Service Methods for inherited classes ///////////////////////

	/**
	 * returns the {@link GraphExplorerInternal} of this instance.
	 */
	final public GraphExplorerInternal getExplorer() {
		return pathExplorer;
	}

	/**
	 * returns the {@link BranchWalkerInternal} from which this instance was forked. Is null for the first
	 * {@link BranchWalkerInternal}.
	 */
	final public BranchWalkerInternal getPathPredecessor() {
		return pathPredecessor;
	}

	/**
	 * returns the {@link BranchWalkerInternal} into which this instance was joined. Is set before
	 * {@link #joinedWith(BranchWalkerInternal)} is called.
	 */
	final public BranchWalkerInternal getPathSuccessor() {
		return pathSuccessor;
	}

	/** Sets the verdict of this path to <i>Passed</i>. */
	final public void pass() {
		pathExplorer.passedPaths.add(this);
		if (pathExplorer.quantor == Quantor.AtLeastOnePath) {
			pathExplorer.deactivateAll();
		} else {
			deactivate();
		}
	}

	/** Sets the verdict of this path to <i>Failed</i>. */
	final public void fail() {
		pathExplorer.failedPaths.add(this);
		if (pathExplorer.quantor == Quantor.ForAllPaths) {
			pathExplorer.deactivateAll();
		} else {
			deactivate();
		}
	}

	/** Deactivates this path without setting the verdict. */
	final public void deactivate() {
		pathExplorer.activePaths.remove(this);
		terminate();
		pathExplorer.checkExplorerDeactivation();
	}

	/** @return true, iff this path is active. */
	final public boolean isActive() {
		return pathExplorer.activePaths.contains(this);
	}

}
