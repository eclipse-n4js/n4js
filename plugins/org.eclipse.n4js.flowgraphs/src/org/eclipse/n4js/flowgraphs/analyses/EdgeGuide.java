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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.utils.collections.Collections2;

import com.google.common.collect.Lists;
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
	final Set<JumpToken> finallyBlockContexts = new HashSet<>();
	private ControlFlowEdge edge;
	private boolean isDeadCode;

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
		this(edgeProvider, edge, Sets.newHashSet());
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths) {
		this(edgeProvider, edge, activePaths, Sets.newHashSet(), false);
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths,
			Set<JumpToken> finallyBlockContexts) {
		this(edgeProvider, edge, activePaths, finallyBlockContexts, false);
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Collection<BranchWalkerInternal> activePaths,
			Set<JumpToken> finallyBlockContexts, boolean isDeadCode) {

		this.isDeadCode = isDeadCode;
		this.edgeProvider = edgeProvider;
		this.edge = edge;
		this.branchWalkers = activePaths;
		if (edge.finallyPathContext != null) {
			finallyBlockContexts.add(edge.finallyPathContext);
		}
		this.finallyBlockContexts.addAll(finallyBlockContexts);
		syncDeadCodeProperty();
	}

	Node getPrevNode() {
		return edgeProvider.getPrevNode(edge);
	}

	Node getNextNode() {
		return edgeProvider.getNextNode(edge);
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
				nextEGs.add(eg);
			}
		}

		return nextEGs;
	}

	List<EdgeGuide> getNextEdgeGuides() {
		List<EdgeGuide> nextEGs = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = getNextEdges();
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
				syncDeadCodeProperty();
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
				Set<JumpToken> fbContexts = finallyBlockContexts;
				EdgeGuide edgeGuide = new EdgeGuide(epCopy, nextEdge, forkedPaths, fbContexts, isDeadCode());
				nextEGs.add(edgeGuide);
			}
		}

		return nextEGs;
	}

	private List<ControlFlowEdge> getNextEdges() {
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(getNextNode());
		Set<JumpToken> nextJumpContexts = new HashSet<>();
		List<ControlFlowEdge> finallyBlockContextEdges = findFinallyBlockContextEdge(nextEdges, nextJumpContexts);
		finallyBlockContexts.addAll(nextJumpContexts);

		if (!finallyBlockContextEdges.isEmpty()) {
			return finallyBlockContextEdges;
		}

		return nextEdges;
	}

	private void syncDeadCodeProperty() {
		if (edgeProvider.isForward()) {
			isDeadCode = isDeadCode || edge.cfType == ControlFlowType.DeadCode;
			getNextNode().isDeadCode = isDeadCode;
		} else {
			isDeadCode = getNextNode().isDeadCode;
		}
		if (isDeadCode) {
			for (BranchWalkerInternal bw : branchWalkers) {
				bw.setDeadCode();
			}
		}
	}

	private boolean isDeadAliveChange(ControlFlowEdge cfEdge) {
		return cfEdge.cfType == ControlFlowType.DeadCode;
	}

	/**
	 * This method searches all FinallyBlock-entry/exit edges E to chose the correct next following edges. The following
	 * rules are implemented:
	 * <ul>
	 * <li/>If there exists no next edge with a context, then null is returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has no context that
	 * matches with one of the next edges, then all edges without context are returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has a context that
	 * matches with one of the next edges, the matching edge is returned.
	 * </ul>
	 */
	private List<ControlFlowEdge> findFinallyBlockContextEdge(List<ControlFlowEdge> nextEdges,
			Set<JumpToken> nextJumpContexts) {

		LinkedList<ControlFlowEdge> fbContextFreeEdges = new LinkedList<>();
		Map<JumpToken, ControlFlowEdge> contextEdges = new HashMap<>();
		mapFinallyBlockContextEdges(nextEdges, fbContextFreeEdges, contextEdges);
		if (contextEdges.isEmpty()) {
			return Lists.newLinkedList();
		}

		ControlFlowEdge matchedFBContextEdge = null;
		Map.Entry<JumpToken, ControlFlowEdge> otherEdgePair = null;
		for (Map.Entry<JumpToken, ControlFlowEdge> ctxEdgePair : contextEdges.entrySet()) {
			JumpToken fbContext = ctxEdgePair.getKey();
			otherEdgePair = ctxEdgePair;
			if (finallyBlockContexts.contains(fbContext)) {
				matchedFBContextEdge = ctxEdgePair.getValue();
			}
		}

		if (matchedFBContextEdge != null) {
			return Collections2.newLinkedList(matchedFBContextEdge);

		} else if (!fbContextFreeEdges.isEmpty()) {
			return fbContextFreeEdges;

		} else if (otherEdgePair != null) {
			nextJumpContexts.add(otherEdgePair.getKey());
			return Collections2.newLinkedList(otherEdgePair.getValue());
		}
		return Lists.newLinkedList();
	}

	private void mapFinallyBlockContextEdges(List<ControlFlowEdge> nextEdges, List<ControlFlowEdge> fbContextFreeEdges,
			Map<JumpToken, ControlFlowEdge> contextEdges) {

		for (ControlFlowEdge nE : nextEdges) {
			if (nE.cfType.isInOrEmpty(ControlFlowType.NonDeadTypes)) {
				JumpToken finallyPathContext = nE.finallyPathContext;
				if (finallyPathContext != null) {
					contextEdges.put(finallyPathContext, nE);
				} else {
					fbContextFreeEdges.add(nE);
				}
			}
		}
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
		return isDeadCode;
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
