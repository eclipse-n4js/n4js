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
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.Direction;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal;
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
public class GraphVisitorGuideInternal {
	private final N4JSFlowAnalyses flowAnalyses;
	private final Collection<GraphVisitorInternal> walkers;
	private final Set<Node> walkerVisitedNodes = new HashSet<>();
	private final Set<ControlFlowEdge> walkerVisitedEdges = new HashSet<>();

	public GraphVisitorGuideInternal(N4JSFlowAnalyses flowAnalyses, Collection<GraphVisitorInternal> walkers) {
		this.flowAnalyses = flowAnalyses;
		this.walkers = walkers;
	}

	public void init() {
		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyses);
			walker.callInitAll();
		}
	}

	public void terminate() {
		for (GraphVisitorInternal walker : walkers) {
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

		for (GraphVisitorInternal walker : walkers) {
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

		for (GraphVisitorInternal walker : walkers) {
			walker.callTerminate();
		}
		return allVisitedNodes;
	}

	private Set<Node> walkthrough(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<ControlFlowEdge> allVisitedEdges = new HashSet<>();
		LinkedList<EdgeGuide> currEdgeGuides = new LinkedList<>();
		List<EdgeGuide> nextEGs = getFirstDecoratedEdges(cn, edgeProvider);
		currEdgeGuides.addAll(nextEGs);

		Node lastVisitNode = null;

		for (EdgeGuide currEdgeGuide : currEdgeGuides) {
			Node visitNode = currEdgeGuide.getPrevNode();
			lastVisitNode = visitNode(lastVisitNode, currEdgeGuide, visitNode);
		}

		while (!currEdgeGuides.isEmpty()) {
			EdgeGuide currEdgeGuide = currEdgeGuides.removeFirst();
			boolean alreadyVisitedAndObsolete = allVisitedEdges.contains(currEdgeGuide.edge);
			alreadyVisitedAndObsolete &= currEdgeGuide.activePaths.isEmpty();
			if (alreadyVisitedAndObsolete) {
				continue;
			}

			Node visitNode = currEdgeGuide.getNextNode();
			lastVisitNode = visitNode(lastVisitNode, currEdgeGuide, visitNode);

			allVisitedEdges.add(currEdgeGuide.edge);
			nextEGs = getNextEdgeGuides(currEdgeGuide);
			currEdgeGuides.addAll(0, nextEGs); // adding to the front: deep search / to the back: breadth search
		}

		Set<Node> allVisitedNodes = new HashSet<>();
		for (ControlFlowEdge edge : allVisitedEdges) {
			allVisitedNodes.add(edge.start);
			allVisitedNodes.add(edge.end);
		}
		return allVisitedNodes;
	}

	private Node visitNode(Node lastVisitNode, EdgeGuide currEdgeGuide, Node visitNode) {
		if (lastVisitNode != null) {
			callVisit(CallVisit.OnEdge, lastVisitNode, currEdgeGuide, visitNode);
		}

		callVisit(CallVisit.OnNode, lastVisitNode, currEdgeGuide, visitNode);
		return visitNode;
	}

	enum CallVisit {
		OnNode, OnEdge
	}

	private void callVisit(CallVisit callVisit, Node lastVisitNode, EdgeGuide currEdgeGuide, Node visitNode) {
		for (GraphVisitorInternal walker : walkers) {
			switch (callVisit) {
			case OnNode:
				if (!walkerVisitedNodes.contains(visitNode)) {
					walker.callVisit(visitNode);
				}
				walkerVisitedNodes.add(visitNode);
				break;
			case OnEdge:
				if (!walkerVisitedEdges.contains(currEdgeGuide.edge)) {
					walker.callVisit(lastVisitNode, visitNode, currEdgeGuide.edge);
				}
				walkerVisitedEdges.add(currEdgeGuide.edge);
				break;
			}
			List<PathWalkerInternal> activatedPaths = walker.activate();
			currEdgeGuide.activePaths.addAll(activatedPaths);
		}
		for (Iterator<PathWalkerInternal> actPathIt = currEdgeGuide.activePaths.iterator(); actPathIt.hasNext();) {
			PathWalkerInternal activePath = actPathIt.next();
			switch (callVisit) {
			case OnNode:
				activePath.callVisit(visitNode);
				break;
			case OnEdge:
				activePath.callVisit(lastVisitNode, visitNode, currEdgeGuide.edge);
				break;
			}
			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

	private List<EdgeGuide> getFirstDecoratedEdges(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<PathWalkerInternal> activatedPaths = new HashSet<>();
		for (GraphVisitorInternal walker : walkers) {
			activatedPaths.addAll(walker.activate());
		}

		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		List<EdgeGuide> nextEGs = new LinkedList<>();
		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, activatedPaths);
			nextEGs.add(eg);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<PathWalkerInternal> forkedPaths = new HashSet<>();
			for (PathWalkerInternal aPath : activatedPaths) {
				PathWalkerInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}
			EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, forkedPaths);
			nextEGs.add(eg);
		}
		return nextEGs;
	}

	private List<EdgeGuide> getNextEdgeGuides(EdgeGuide currEG) {
		List<EdgeGuide> nextEGs = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = currEG.getNextEdges();
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			currEG.edge = nextEdge;
			nextEGs.add(currEG);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<PathWalkerInternal> forkedPaths = new HashSet<>();
			for (PathWalkerInternal aPath : currEG.activePaths) {
				PathWalkerInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}

			NextEdgesProvider epCopy = currEG.edgeProvider.copy();
			Set<JumpToken> fbContexts = currEG.finallyBlockContexts;
			EdgeGuide edgeGuide = new EdgeGuide(epCopy, nextEdge, forkedPaths, fbContexts);
			nextEGs.add(edgeGuide);
		}

		if (nextEGs.isEmpty()) {
			for (PathWalkerInternal aPath : currEG.activePaths) {
				aPath.deactivate();
			}
		}

		return nextEGs;
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

	private class EdgeGuide {
		final NextEdgesProvider edgeProvider;
		ControlFlowEdge edge;
		final Set<PathWalkerInternal> activePaths = new HashSet<>();
		final Set<JumpToken> finallyBlockContexts = new HashSet<>();

		public EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
			this.edgeProvider = edgeProvider;
			this.edge = edge;
			if (edge.finallyPathContext != null) {
				finallyBlockContexts.add(edge.finallyPathContext);
			}
		}

		public EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge, Set<PathWalkerInternal> activePaths) {
			this(edgeProvider, edge, activePaths, Sets.newHashSet());
		}

		public EdgeGuide(NextEdgesProvider edgeProvider, ControlFlowEdge edge,
				Set<PathWalkerInternal> activePaths, Set<JumpToken> finallyBlockContexts) {

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
		 * This method searches all FinallyBlock-entry/exit edges E to chose the correct next following edges. The
		 * following rules are implemented:
		 * <ul>
		 * <li/>If there exists no next edge with a context, then null is returned.
		 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has no context
		 * that matches with one of the next edges, then all edges without context are returned.
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
