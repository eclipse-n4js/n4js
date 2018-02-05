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

import java.util.LinkedList;
import java.util.List;

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
	private int branchNumber = -1;
	private GraphExplorerInternal pathExplorer;
	private final LinkedList<BranchWalkerInternal> pathPredecessors = new LinkedList<>();
	private final LinkedList<BranchWalkerInternal> pathSuccessors = new LinkedList<>();
	private boolean isDeadCodeBranch = false;
	private boolean isDeadCodeNode = false;

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
	 * @param lastVisitNodes
	 *            nodes that were visited before
	 * @param end
	 *            node that will be visited next
	 * @param edge
	 *            traversed edge
	 */
	protected void visit(Node lastVisitNodes, Node end, ControlFlowEdge edge) {
		// overwrite me
	}

	/**
	 * Forks another path from the current node.<br/>
	 * <i>Take care about forking the current state of this instance!</i>
	 */
	abstract protected BranchWalkerInternal fork();

	/** Called at last. */
	protected void terminate() {
		// overwrite me
	}

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link BranchWalkerInternal#initialize()}.
	 */
	final void callInitialize(GraphExplorerInternal explorer, BranchWalkerInternal predecessor) {
		if (predecessor != null) {
			predecessor.pathSuccessors.add(this);
			this.pathPredecessors.add(predecessor);
		}
		initializeRest(explorer);
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link BranchWalkerInternal#initialize()}.
	 */
	final void callInitialize(GraphExplorerInternal explorer, List<BranchWalkerInternal> predecessors) {
		for (BranchWalkerInternal pred : predecessors) {
			pred.pathSuccessors.add(this);
			this.pathPredecessors.add(pred);
		}
		initializeRest(explorer);
	}

	private void initializeRest(GraphExplorerInternal explorer) {
		assert (this.branchNumber == -1) : "Cannot initialize twice";

		this.branchNumber = explorer.getAndIncrementBranchCounter();
		this.pathExplorer = explorer;
		pathExplorer.allBranches.add(this);
		pathExplorer.activeBranches.add(this);
		initialize();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link BranchWalkerInternal#visit(Node)}.
	 */
	final void callVisit(Node node) {
		isDeadCodeNode = node.isUnreachable();
		isDeadCodeBranch |= isDeadCodeNode;
		visit(node);
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link BranchWalkerInternal#visit(Node, Node, ControlFlowEdge)}.
	 */
	final void callVisit(Node lastVisitNode, Node end, ControlFlowEdge edge) {
		isDeadCodeNode = end.isUnreachable();
		isDeadCodeBranch |= isDeadCodeNode;
		visit(lastVisitNode, end, edge);
	}

	final void setDeadCode(boolean isDead) {
		this.isDeadCodeBranch = isDead;
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #fork()}. */
	final BranchWalkerInternal callFork() {
		BranchWalkerInternal forkedPath = fork();
		forkedPath.callInitialize(pathExplorer, this);
		return forkedPath;
	}

	/**
	 * @return a list of {@link BranchWalkerInternal}s which proceed this instance.
	 */
	final List<BranchWalkerInternal> getPathPredecessors() {
		return pathPredecessors;
	}

	/**
	 * @return a list of {@link BranchWalkerInternal}s which succeed this instance.
	 */
	final List<BranchWalkerInternal> getPathSuccessors() {
		return pathSuccessors;
	}

	/////////////////////// Service Methods for inherited classes ///////////////////////

	/** @return the {@link GraphExplorerInternal} of this instance. */
	final public GraphExplorerInternal getExplorer() {
		return pathExplorer;
	}

	/** @return the number of this {@link BranchWalkerInternal}. */
	final public int getNumber() {
		return branchNumber;
	}

	/** Sets the verdict of this path to <i>Passed</i>. */
	final public void pass() {
		pathExplorer.passedBranches.add(this);
		if (pathExplorer.quantor == Quantor.AtLeastOneBranch) {
			pathExplorer.deactivateAll();
		} else {
			deactivate();
		}
	}

	/** Sets the verdict of this path to <i>Failed</i>. */
	final public void fail() {
		pathExplorer.failedBranchs.add(this);
		if (pathExplorer.quantor == Quantor.ForAllBranches) {
			pathExplorer.deactivateAll();
		} else {
			deactivate();
		}
	}

	/** Deactivates this path without setting the verdict. */
	final public void deactivate() {
		pathExplorer.activeBranches.remove(this);
		terminate();
		pathExplorer.checkExplorerDeactivation();
	}

	/** @return true, iff this path is active. */
	final public boolean isActive() {
		return pathExplorer.activeBranches.contains(this);
	}

	/** @return true iff the last visited node was dead. */
	final public boolean isDeadCodeNode() {
		return isDeadCodeNode;
	}

	/** @return true iff this branch contains dead nodes. */
	final public boolean isDeadCodeBranch() {
		return isDeadCodeBranch;
	}

	/** @return true iff this branch is dead and its predecessor is alive. */
	final public boolean isFirstDead() {
		boolean isFirstDead = isDeadCodeBranch();
		isFirstDead &= !pathPredecessors.isEmpty() && !pathPredecessors.getFirst().isDeadCodeBranch();
		return isFirstDead;
	}

	@Override
	public String toString() {
		return (isDeadCodeBranch ? "b" : "B") + branchNumber;
	}

}
