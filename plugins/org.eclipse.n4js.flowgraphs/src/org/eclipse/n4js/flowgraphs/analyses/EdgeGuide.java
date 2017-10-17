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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.utils.collections.Collections2;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * The {@link EdgeGuide} keeps track of all {@link PathWalkerInternal}s that are currently exploring a path on
 * that edge. In case an {@link EdgeGuide} has no {@link PathWalkerInternal}s, it might be removed. When an edge
 * of an {@link EdgeGuide} has more than one next edge, the {@link EdgeGuide} will split up and all its
 * {@link PathWalkerInternal}s will fork. If there is only one next edge, the current edge of the
 * {@link EdgeGuide} instance will be replaced.
 * <p>
 * The {@link EdgeGuide} keeps track of edges that enter or exit {@link FinallyBlock}s. The reason is, that
 * these e.g. entering edges will determine the correct exiting edges. (Consider that {@link FinallyBlock}s can be
 * entered via a {@link ReturnStatement} and will then exit directly to the next {@link FinallyBlock} or to the end of
 * the method, instead of executing statements that follow the {@link FinallyBlock}.)
 */
public class EdgeGuide {
	final NextEdgesProvider edgeProvider;
	ControlFlowEdge edge;
	final Set<PathWalkerInternal> activePaths = new HashSet<>();
	final Set<JumpToken> finallyBlockContexts = new HashSet<>();

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
		this.edgeProvider = edgeProvider;
		this.edge = edge;
		if (edge.finallyPathContext != null) {
			finallyBlockContexts.add(edge.finallyPathContext);
		}
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Set<PathWalkerInternal> activePaths) {
		this(edgeProvider, edge, activePaths, Sets.newHashSet());
	}

	EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Set<PathWalkerInternal> activePaths,
			Set<JumpToken> finallyBlockContexts) {

		this(edgeProvider, edge);
		this.activePaths.addAll(activePaths);
		this.finallyBlockContexts.addAll(finallyBlockContexts);
	}

	Node getPrevNode() {
		return edgeProvider.getPrevNode(edge);
	}

	Node getNextNode() {
		return edgeProvider.getNextNode(edge);
	}

	List<ControlFlowEdge> getNextEdges() {
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(getNextNode());
		Set<JumpToken> nextJumpContexts = new HashSet<>();
		List<ControlFlowEdge> finallyBlockContextEdges = findFinallyBlockContextEdge(nextEdges, nextJumpContexts);
		finallyBlockContexts.addAll(nextJumpContexts);

		if (!finallyBlockContextEdges.isEmpty()) {
			return finallyBlockContextEdges;
		}

		return nextEdges;
	}

	/**
	 * This method searches all FinallyBlock-entry/exit edges E to chose the correct next following edges. The following
	 * rules are implemented:
	 * <ul>
	 * <li/>If there exists no next edge with a context, then null is returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has no
	 * context that matches with one of the next edges, then all edges without context are returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has a context
	 * that matches with one of the next edges, the matching edge is returned.
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
			JumpToken finallyPathContext = nE.finallyPathContext;
			if (finallyPathContext != null) {
				contextEdges.put(finallyPathContext, nE);
			} else {
				fbContextFreeEdges.add(nE);
			}
		}
	}

	void join(EdgeGuide egi) {
		for (PathWalkerInternal masterPWI : activePaths) {
			PathExplorerInternal explorer = masterPWI.getExplorer();
			Set<PathWalkerInternal> explorersPaths = explorer.getActivePaths();
			PathWalkerInternal forkedPWI = null;
			for (PathWalkerInternal forkdPWI : egi.activePaths) {
				if (explorersPaths.contains(forkdPWI)) {
					forkedPWI = forkdPWI;
				}
			}

			if (forkedPWI != null) {
				forkedPWI.callJoin(masterPWI);
				masterPWI.callJoinedWith(forkedPWI);
				egi.activePaths.remove(forkedPWI);
			}
		}

		activePaths.addAll(egi.activePaths);
	}

	@Override
	public String toString() {
		return this.edge.toString();
	}

}
