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

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.ActivatedPathPredicateInternal.ActivePathInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.Direction;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.utils.collections.Collections2;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 */
@SuppressWarnings("javadoc")
public class GraphWalkerGuideInternal {
	private final N4JSFlowAnalyses flowAnalyses;
	private final Collection<GraphWalkerInternal> walkers;
	private final Set<Node> walkerVisitedNodes = new HashSet<>();
	private final Set<ControlFlowEdge> walkerVisitedEdges = new HashSet<>();

	public GraphWalkerGuideInternal(N4JSFlowAnalyses flowAnalyses, Collection<GraphWalkerInternal> walkers) {
		this.flowAnalyses = flowAnalyses;
		this.walkers = walkers;
	}

	public void init() {
		for (GraphWalkerInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyses);
			walker.callInitAll();
		}
	}

	public void terminate() {
		for (GraphWalkerInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyses);
			walker.callTerminateAll();
		}
	}

	public Set<Node> walkthroughForward(ComplexNode cn) {
		return walkthrough(cn, Direction.Forward);
	}

	public Set<Node> walkthroughBackward(ComplexNode cn) {
		return walkthrough(cn, Direction.Backward);
	}

	public Set<Node> walkthroughCatchBlocks(ComplexNode cn) {
		return walkthrough(cn, Direction.CatchBlocks);
	}

	public Set<Node> walkthroughIsland(ComplexNode cn) {
		return walkthrough(cn, Direction.Islands);
	}

	private Set<Node> walkthrough(ComplexNode cn, Direction direction) {
		walkerVisitedNodes.clear();
		walkerVisitedEdges.clear();

		for (GraphWalkerInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyses);
			walker.setContainerAndDirection(cn.getControlFlowContainer(), direction);
			walker.callInitInternal();
		}

		Set<Node> allVisitedNodes = new HashSet<>();
		List<NextEdgesProvider> edgeProviders = getEdgeProviders(direction);
		for (NextEdgesProvider edgeProvider : edgeProviders) {
			Set<Node> visitedNodes = walkthrough(cn, edgeProvider);
			allVisitedNodes.addAll(visitedNodes);
		}

		for (GraphWalkerInternal walker : walkers) {
			walker.callTerminate();
		}
		return allVisitedNodes;
	}

	private Set<Node> walkthrough(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<Node> allVisitedNodes = new HashSet<>();
		LinkedList<DecoratedEdgeInternal> currDEdges = new LinkedList<>();
		List<DecoratedEdgeInternal> nextDEdges = getFirstDecoratedEdges(cn, edgeProvider);
		currDEdges.addAll(nextDEdges);

		Node lastVisitNode = null;

		for (DecoratedEdgeInternal currDEdge : currDEdges) {
			Node visitNode = currDEdge.getPrevNode();
			lastVisitNode = visitNode(lastVisitNode, currDEdge, visitNode);
			allVisitedNodes.add(lastVisitNode);
		}

		while (!currDEdges.isEmpty()) {
			DecoratedEdgeInternal currDEdge = currDEdges.removeFirst();

			Node visitNode = currDEdge.getNextNode();
			lastVisitNode = visitNode(lastVisitNode, currDEdge, visitNode);
			allVisitedNodes.add(lastVisitNode);

			nextDEdges = getNextDecoratedEdges(currDEdge);
			currDEdges.addAll(0, nextDEdges); // adding to the front: deep search / to the back: breadth search
		}

		return allVisitedNodes;
	}

	private Node visitNode(Node lastVisitNode, DecoratedEdgeInternal currDEdge, Node visitNode) {
		if (lastVisitNode != null) {
			callVisit(CallVisit.OnEdge, lastVisitNode, currDEdge, visitNode);
		}

		callVisit(CallVisit.OnNode, lastVisitNode, currDEdge, visitNode);
		return visitNode;
	}

	enum CallVisit {
		OnNode, OnEdge
	}

	private void callVisit(CallVisit callVisit, Node lastVisitNode, DecoratedEdgeInternal currDEdge, Node visitNode) {
		for (GraphWalkerInternal walker : walkers) {
			switch (callVisit) {
			case OnNode:
				if (!walkerVisitedNodes.contains(visitNode)) {
					walker.callVisit(visitNode);
				}
				walkerVisitedNodes.add(visitNode);
				break;
			case OnEdge:
				if (!walkerVisitedEdges.contains(currDEdge.edge)) {
					walker.callVisit(lastVisitNode, visitNode, currDEdge.edge);
				}
				walkerVisitedEdges.add(currDEdge.edge);
				break;
			}
			List<ActivePathInternal> activatedPaths = walker.activate();
			currDEdge.activePaths.addAll(activatedPaths);
		}
		for (Iterator<ActivePathInternal> actPathIt = currDEdge.activePaths.iterator(); actPathIt.hasNext();) {
			ActivePathInternal activePath = actPathIt.next();
			switch (callVisit) {
			case OnNode:
				activePath.callVisit(visitNode);
				break;
			case OnEdge:
				activePath.callVisit(lastVisitNode, visitNode, currDEdge.edge);
				break;
			}
			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

	private List<DecoratedEdgeInternal> getFirstDecoratedEdges(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<ActivePathInternal> activatedPaths = new HashSet<>();
		for (GraphWalkerInternal walker : walkers) {
			activatedPaths.addAll(walker.activate());
		}

		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		List<DecoratedEdgeInternal> nextDEdges = new LinkedList<>();
		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			DecoratedEdgeInternal eF = new DecoratedEdgeInternal(edgeProvider.copy(), nextEdge, activatedPaths);
			nextDEdges.add(eF);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<ActivePathInternal> forkedPaths = new HashSet<>();
			for (ActivePathInternal aPath : activatedPaths) {
				ActivePathInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}
			DecoratedEdgeInternal eF = new DecoratedEdgeInternal(edgeProvider.copy(), nextEdge, forkedPaths);
			nextDEdges.add(eF);
		}
		return nextDEdges;
	}

	private List<DecoratedEdgeInternal> getNextDecoratedEdges(DecoratedEdgeInternal currDEdge) {
		List<DecoratedEdgeInternal> nextDEdges = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = currDEdge.getNextEdges();
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			currDEdge.edge = nextEdge;
			nextDEdges.add(currDEdge);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<ActivePathInternal> forkedPaths = new HashSet<>();
			for (ActivePathInternal aPath : currDEdge.activePaths) {
				ActivePathInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}

			NextEdgesProvider epCopy = currDEdge.edgeProvider.copy();
			Set<JumpToken> fbContexts = currDEdge.finallyBlockContexts;
			DecoratedEdgeInternal dEdge = new DecoratedEdgeInternal(epCopy, nextEdge, forkedPaths, fbContexts);
			nextDEdges.add(dEdge);
		}

		if (nextDEdges.isEmpty()) {
			for (ActivePathInternal aPath : currDEdge.activePaths) {
				aPath.deactivate();
			}
		}

		return nextDEdges;
	}

	private List<NextEdgesProvider> getEdgeProviders(Direction direction) {
		List<NextEdgesProvider> edgeProviders = new LinkedList<>();
		switch (direction) {
		case Forward:
			edgeProviders.add(new NextEdgesProvider.Forward());
			break;
		case Backward:
			edgeProviders.add(new NextEdgesProvider.Backward());
			break;
		case Islands:
			edgeProviders.add(new NextEdgesProvider.Forward());
			edgeProviders.add(new NextEdgesProvider.Backward());
			break;
		case CatchBlocks:
			edgeProviders.add(new NextEdgesProvider.Forward());
			break;
		}
		return edgeProviders;
	}

	private class DecoratedEdgeInternal {
		final NextEdgesProvider edgeProvider;
		ControlFlowEdge edge;
		final Set<ActivePathInternal> activePaths = new HashSet<>();
		final Set<JumpToken> finallyBlockContexts = new HashSet<>();

		public DecoratedEdgeInternal(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
			this.edgeProvider = edgeProvider;
			this.edge = edge;
			if (edge.finallyPathContext != null) {
				finallyBlockContexts.add(edge.finallyPathContext);
			}
		}

		public DecoratedEdgeInternal(NextEdgesProvider edgeProvider, ControlFlowEdge edge,
				Set<ActivePathInternal> activePaths) {
			this(edgeProvider, edge, activePaths, Sets.newHashSet());
		}

		public DecoratedEdgeInternal(NextEdgesProvider edgeProvider, ControlFlowEdge edge,
				Set<ActivePathInternal> activePaths, Set<JumpToken> finallyBlockContexts) {

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
		 * This method searches all FinallyBlock-entry/exit edges E to chose the correct next following edge. The
		 * following rules are implemented:
		 * <ul>
		 * <li/>If there exists no next edge with a context, then null is returned.
		 * <li/>If there exists a next edge with a context, and the current {@link DecoratedEdgeInternal} instance has
		 * no context that matches with one of the next edges, then an edge without context is returned. In these cases,
		 * there should be only one next edge.
		 * <li/>If there exists a next edge with a context, and the current {@link DecoratedEdgeInternal} instance has a
		 * context that matches with one of the next edges, the matching edge is returned.
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

		private void mapFinallyBlockContextEdges(List<ControlFlowEdge> nextEdges,
				List<ControlFlowEdge> fbContextFreeEdges, Map<JumpToken, ControlFlowEdge> contextEdges) {

			for (ControlFlowEdge nE : nextEdges) {
				JumpToken finallyPathContext = nE.finallyPathContext;
				if (finallyPathContext != null) {
					contextEdges.put(finallyPathContext, nE);
				} else {
					fbContextFreeEdges.add(nE);
				}
			}
		}

	}
}
