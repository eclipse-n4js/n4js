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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.Mode;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * This class executes a control flow analyzes in a specific {@link Mode} that are defined as
 * {@link GraphVisitorInternal}s. The execution is triggered from {@link GraphVisitorAnalysis}.
 * <p>
 * Use the API of this class in the following order: Initialize first, then use any of the walkthrough methods in any
 * order, and call {@link #terminate()} at last.
 * <p>
 * For every {@link Mode}, all reachable {@link Node}s and {@link ControlFlowEdge}s are visited in an arbitrary (but
 * loosely control flow related) order. In case one of the given {@link GraphVisitorInternal}s requests an activation of
 * a {@link PathExplorerInternal}, all paths starting from the current {@link Node} are explored. For this mechanism,
 * the {@link EdgeGuideInternal} class is used, which stores information about all paths that are currently explored.
 * The path exploration is done in parallel for every {@link PathExplorerInternal} of every
 * {@link GraphVisitorInternal}.
 */
public class GraphVisitorGuideInternal {
	private final N4JSFlowAnalyzer flowAnalyzer;
	private final Collection<? extends GraphVisitorInternal> walkers;
	private final Set<Node> walkerVisitedNodes = new HashSet<>();
	private final Set<ControlFlowEdge> walkerVisitedEdges = new HashSet<>();

	/** Constructor */
	public GraphVisitorGuideInternal(N4JSFlowAnalyzer flowAnalyzer,
			Collection<? extends GraphVisitorInternal> walkers) {
		this.flowAnalyzer = flowAnalyzer;
		this.walkers = walkers;
	}

	/** Call before any of the {@code walkthrough} methods is called. */
	public void init() {
		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyzer);
			walker.callInitialize();
		}
	}

	/** Call after all of the {@code walkthrough} methods have been called. */
	public void terminate() {
		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyzer);
			walker.callTerminate();
		}
	}

	/** Traverses the control flow graph in {@literal Mode.Forward} */
	public Set<Node> walkthroughForward(ComplexNode cn) {
		return walkthrough(cn, Mode.Forward);
	}

	/** Traverses the control flow graph in {@literal Mode.Backward} */
	public Set<Node> walkthroughBackward(ComplexNode cn) {
		return walkthrough(cn, Mode.Backward);
	}

	/** Traverses the control flow graph in {@literal Mode.CatchBlocks} */
	public Set<Node> walkthroughCatchBlocks(ComplexNode cn) {
		return walkthrough(cn, Mode.CatchBlocks);
	}

	/** Traverses the control flow graph in {@literal Mode.Islands} */
	public Set<Node> walkthroughIsland(ComplexNode cn) {
		return walkthrough(cn, Mode.Islands);
	}

	private Set<Node> walkthrough(ComplexNode cn, Mode mode) {
		walkerVisitedNodes.clear();
		walkerVisitedEdges.clear();

		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyzer);
			walker.setContainerAndMode(cn.getControlFlowContainer(), mode);
			walker.callInitializeModeInternal();
		}

		Set<Node> allVisitedNodes = new HashSet<>();
		List<NextEdgesProvider> edgeProviders = getEdgeProviders(mode);
		for (NextEdgesProvider edgeProvider : edgeProviders) {
			Set<Node> visitedNodes = walkthrough(cn, edgeProvider);
			allVisitedNodes.addAll(visitedNodes);
		}

		for (GraphVisitorInternal walker : walkers) {
			walker.callTerminateMode();
		}
		return allVisitedNodes;
	}

	private List<NextEdgesProvider> getEdgeProviders(Mode mode) {
		List<NextEdgesProvider> edgeProviders = new LinkedList<>();
		switch (mode) {
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

	private Set<Node> walkthrough(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<ControlFlowEdge> allVisitedEdges = new HashSet<>();
		LinkedList<EdgeGuideInternal> currEdgeGuides = new LinkedList<>();
		List<EdgeGuideInternal> nextEGs = getFirstEdgeGuides(cn, edgeProvider);
		currEdgeGuides.addAll(nextEGs);

		Node lastVisitNode = null;

		for (EdgeGuideInternal currEdgeGuide : currEdgeGuides) {
			Node visitNode = currEdgeGuide.getPrevNode();
			lastVisitNode = visitNode(lastVisitNode, currEdgeGuide, visitNode);
		}

		while (!currEdgeGuides.isEmpty()) {
			flowAnalyzer.checkCancelled();

			EdgeGuideInternal currEdgeGuide = currEdgeGuides.removeFirst();
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

	private Node visitNode(Node lastVisitNode, EdgeGuideInternal currEdgeGuide, Node visitNode) {
		if (lastVisitNode != null) {
			callVisitOnEdge(lastVisitNode, currEdgeGuide, visitNode);
		}

		callVisitOnNode(currEdgeGuide, visitNode);
		return visitNode;
	}

	/** This method must be kept in sync with {@link #callVisitOnEdge(Node, EdgeGuideInternal, Node)} */
	private void callVisitOnNode(EdgeGuideInternal currEdgeGuide, Node visitNode) {
		if (!walkerVisitedNodes.contains(visitNode)) {
			for (GraphVisitorInternal walker : walkers) {
				walker.callVisit(visitNode);
			}
		}
		walkerVisitedNodes.add(visitNode);

		for (GraphVisitorInternal walker : walkers) {
			List<PathWalkerInternal> activatedPaths = walker.activateRequestedPathExplorers();
			currEdgeGuide.activePaths.addAll(activatedPaths);
		}

		for (Iterator<PathWalkerInternal> actPathIt = currEdgeGuide.activePaths.iterator(); actPathIt.hasNext();) {
			PathWalkerInternal activePath = actPathIt.next();

			activePath.callVisit(visitNode);

			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

	/** This method must be kept in sync with {@link #callVisitOnNode(EdgeGuideInternal, Node)} */
	private void callVisitOnEdge(Node lastVisitNode, EdgeGuideInternal currEdgeGuide, Node visitNode) {
		if (!walkerVisitedEdges.contains(currEdgeGuide.edge)) {
			for (GraphVisitorInternal walker : walkers) {
				walker.callVisit(lastVisitNode, visitNode, currEdgeGuide.edge);
			}
		}
		walkerVisitedEdges.add(currEdgeGuide.edge);

		for (GraphVisitorInternal walker : walkers) {
			List<PathWalkerInternal> activatedPaths = walker.activateRequestedPathExplorers();
			currEdgeGuide.activePaths.addAll(activatedPaths);
		}

		for (Iterator<PathWalkerInternal> actPathIt = currEdgeGuide.activePaths.iterator(); actPathIt.hasNext();) {
			PathWalkerInternal activePath = actPathIt.next();

			activePath.callVisit(lastVisitNode, visitNode, currEdgeGuide.edge);

			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

	/**
	 * Computes the initial set of {@link EdgeGuideInternal}s based on the start {@link ControlFlowEdge}s of the given
	 * {@link ComplexNode}.
	 */
	private List<EdgeGuideInternal> getFirstEdgeGuides(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<PathWalkerInternal> activatedPaths = new HashSet<>();
		for (GraphVisitorInternal walker : walkers) {
			activatedPaths.addAll(walker.activateRequestedPathExplorers());
		}

		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		List<EdgeGuideInternal> nextEGs = new LinkedList<>();
		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			EdgeGuideInternal eg = new EdgeGuideInternal(edgeProvider.copy(), nextEdge, activatedPaths);
			nextEGs.add(eg);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<PathWalkerInternal> forkedPaths = new HashSet<>();
			for (PathWalkerInternal aPath : activatedPaths) {
				PathWalkerInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}
			EdgeGuideInternal eg = new EdgeGuideInternal(edgeProvider.copy(), nextEdge, forkedPaths);
			nextEGs.add(eg);
		}
		return nextEGs;
	}

	/**
	 * Computes the next {@link EdgeGuideInternal}s based on the next {@link ControlFlowEdge}s. For memory performance
	 * reasons, the current {@link EdgeGuideInternal} is reused and its edge is replaced by the next edge.
	 */
	private List<EdgeGuideInternal> getNextEdgeGuides(EdgeGuideInternal currEG) {
		List<EdgeGuideInternal> nextEGs = new LinkedList<>();
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
			EdgeGuideInternal edgeGuide = new EdgeGuideInternal(epCopy, nextEdge, forkedPaths, fbContexts);
			nextEGs.add(edgeGuide);
		}

		if (nextEGs.isEmpty()) {
			for (PathWalkerInternal aPath : currEG.activePaths) {
				aPath.deactivate();
			}
		}

		return nextEGs;
	}

}
