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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ReturnStatement;

import com.google.common.collect.Sets;

/**
 * The {@link EdgeGuide} keeps track of all {@link BranchWalkerInternal}s that are currently exploring a path on that
 * edge. In case an {@link EdgeGuide} has no {@link BranchWalkerInternal}s, it might be removed. When an edge of an
 * {@link EdgeGuide} has more than one next edge, the {@link EdgeGuide} will split up and all its
 * {@link BranchWalkerInternal}s will fork. If there is only one next edge, the current edge of the {@link EdgeGuide}
 * instance will be replaced.
 * <p>
 * The {@link EdgeGuide} keeps track of edges that enter or exit {@link FinallyBlock}s. The reason is, that these e.g.
 * entering edges will determine the correct exiting edges. (Consider that {@link FinallyBlock}s can be entered via a
 * {@link ReturnStatement} and will then exit directly to the next {@link FinallyBlock} or to the end of the method,
 * instead of executing statements that follow the {@link FinallyBlock}.)
 */
public class EdgeGuide {
	final NextEdgesProvider edgeProvider;
	final Collection<BranchWalkerInternal> branchWalkers;
	final FinallyFlowContext finallyContext;
	final DeadFlowContext deadContext;
	private ControlFlowEdge edge;

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
		this(edgeProvider, edge, Sets.newHashSet());
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths) {
		this(edgeProvider, edge, activePaths, null, null);
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths,
			FinallyFlowContext flowContext) {
		this(edgeProvider, edge, activePaths, flowContext, null);
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths,
			FinallyFlowContext flowContext, DeadFlowContext deadContext) {

		this.edgeProvider = edgeProvider;
		this.edge = edge;
		this.branchWalkers = activePaths;
		this.finallyContext = new FinallyFlowContext(flowContext, edge);
		this.deadContext = DeadFlowContext.create(deadContext, edgeProvider, edge);
		setBranchWalkersReachability();
	}

	static List<EdgeGuide> getFirstEdgeGuides(ComplexNode cn, NextEdgesProvider edgeProvider,
			Collection<BranchWalkerInternal> activatedPaths) {

		List<EdgeGuide> nextEGs = new LinkedList<>();
		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		if (nextEdges.size() == 1) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, activatedPaths);
			eg.deadContext.update(eg.getPrevNode());
			nextEGs.add(eg);
		}

		if (nextEdges.size() > 1) {
			while (nextEdgeIt.hasNext()) {
				ControlFlowEdge nextEdge = nextEdgeIt.next();
				Collection<BranchWalkerInternal> forkedPaths = new HashSet<>();
				for (BranchWalkerInternal aPath : activatedPaths) {
					BranchWalkerInternal forkedPath = aPath.callFork();
					forkedPaths.add(forkedPath);
				}
				EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, forkedPaths);
				eg.deadContext.update(eg.getPrevNode());
				nextEGs.add(eg);
			}
		}

		return nextEGs;
	}

	Node getPrevNode() {
		return edgeProvider.getPrevNode(edge);
	}

	Node getNextNode() {
		return edgeProvider.getNextNode(edge);
	}

	List<EdgeGuide> getNextEdgeGuides() {
		List<EdgeGuide> nextEGs = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(getNextNode());
		nextEdges = finallyContext.filterEdges(nextEdges);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();
		boolean deadAliveChange = false;

		if (nextEdges.size() == 0) {
			for (BranchWalkerInternal aPath : getBranchIterable()) {
				aPath.deactivate();
			}
		}

		if (nextEdges.size() == 1) {
			deadAliveChange = isDeadAliveChange(nextEdges.get(0));
			if (!deadAliveChange) {
				ControlFlowEdge nextEdge = nextEdgeIt.next();
				edge = nextEdge;
				finallyContext.update(edgeProvider, edge);
				setBranchWalkersReachability();
				nextEGs.add(this);
			}
		}

		if (deadAliveChange || nextEdges.size() > 1) {
			while (nextEdgeIt.hasNext()) {
				ControlFlowEdge nextEdge = nextEdgeIt.next();

				Collection<BranchWalkerInternal> forkedPaths = new HashSet<>();
				for (BranchWalkerInternal aPath : getBranchIterable()) {
					BranchWalkerInternal forkedPath = aPath.callFork();
					aPath.deactivate();
					forkedPaths.add(forkedPath);
				}

				NextEdgesProvider epCopy = edgeProvider.copy();
				EdgeGuide edgeGuide = new EdgeGuide(epCopy, nextEdge, forkedPaths, finallyContext, deadContext);
				nextEGs.add(edgeGuide);
			}
		}

		return nextEGs;
	}

	private void setBranchWalkersReachability() {
		deadContext.update(edgeProvider, edge);
	}

	private boolean isDeadAliveChange(ControlFlowEdge cfEdge) {
		return cfEdge.cfType == ControlFlowType.DeadCode;
	}

	void addActiveBranches(Collection<BranchWalkerInternal> activatedPaths) {
		branchWalkers.addAll(activatedPaths);
	}

	Iterable<BranchWalkerInternal> getBranchIterable() {
		return branchWalkers;
	}

	ControlFlowEdge getEdge() {
		return edge;
	}

	/** @return true iff this edge guide refers to dead code */
	boolean isDeadCode() {
		return deadContext.isDead();
	}

	@Override
	public String toString() {
		return edge.toString();
	}

	boolean isEmpty() {
		return branchWalkers.isEmpty();
	}

	/** @return true iff this {@link EdgeGuide} was merged from two or more {@link EdgeGuide}s */
	boolean isMerged() {
		return false;
	}

}
