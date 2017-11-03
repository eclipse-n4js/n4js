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
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.Mode;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
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
 * a {@link GraphExplorerInternal}, all paths starting from the current {@link Node} are explored. For this mechanism,
 * the {@link EdgeGuide} class is used, which stores information about all paths that are currently explored. The path
 * exploration is done in parallel for every {@link GraphExplorerInternal} of every {@link GraphVisitorInternal}.
 */
public class GraphVisitorGuideInternal {
	private final N4JSFlowAnalyzer flowAnalyzer;
	private final Collection<? extends GraphVisitorInternal> walkers;
	private final Set<Node> walkerVisitedNodes = new HashSet<>();
	private final EdgeGuideWorklist guideWorklist = new EdgeGuideWorklist();

	/** Constructor */
	GraphVisitorGuideInternal(N4JSFlowAnalyzer flowAnalyzer,
			Collection<? extends GraphVisitorInternal> walkers) {
		this.flowAnalyzer = flowAnalyzer;
		this.walkers = walkers;
	}

	/** Call before any of the {@code walkthrough} methods is called. */
	void init() {
		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyzer);
			walker.callInitialize();
		}
	}

	/** Call after all of the {@code walkthrough} methods have been called. */
	void terminate() {
		for (GraphVisitorInternal walker : walkers) {
			walker.setFlowAnalyses(flowAnalyzer);
			walker.callTerminate();
		}
	}

	/** Traverses the control flow graph in {@literal Mode.Forward} */
	Set<Node> walkthroughForward(ComplexNode cn) {
		return walkthrough(cn, Mode.Forward);
	}

	/** Traverses the control flow graph in {@literal Mode.Backward} */
	Set<Node> walkthroughBackward(ComplexNode cn) {
		return walkthrough(cn, Mode.Backward);
	}

	/** Traverses the control flow graph in {@literal Mode.CatchBlocks} */
	Set<Node> walkthroughCatchBlocks(ComplexNode cn) {
		return walkthrough(cn, Mode.CatchBlocks);
	}

	/** Traverses the control flow graph in {@literal Mode.Islands} */
	Set<Node> walkthroughIsland(ComplexNode cn) {
		return walkthrough(cn, Mode.Islands);
	}

	private Set<Node> walkthrough(ComplexNode cn, Mode mode) {
		walkerVisitedNodes.clear();

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
		Set<BranchWalkerInternal> activatedPaths = initVisit();
		guideWorklist.initialize(cn, edgeProvider, activatedPaths);

		Node lastVisitNode = null;

		for (EdgeGuide currEdgeGuide : guideWorklist.getCurrentEdgeGuides()) {
			Node visitNode = currEdgeGuide.getPrevNode();
			visitNode(lastVisitNode, currEdgeGuide, visitNode);
			lastVisitNode = visitNode;
		}

		while (guideWorklist.hasNext()) {
			flowAnalyzer.checkCancelled();

			EdgeGuide currEdgeGuide = guideWorklist.next();

			Node visitNode = currEdgeGuide.getNextNode();
			visitNode(lastVisitNode, currEdgeGuide, visitNode);
			lastVisitNode = visitNode;

			mergeEdgeGuides();
		}

		Set<Node> allVisitedNodes = guideWorklist.getAllVisitedNodes(cn, edgeProvider);
		return allVisitedNodes;
	}

	private Set<BranchWalkerInternal> initVisit() {
		Set<BranchWalkerInternal> activatedPaths = new HashSet<>();
		for (GraphVisitorInternal walker : walkers) {
			activatedPaths.addAll(walker.activateRequestedExplorers());
		}
		return activatedPaths;
	}

	private void mergeEdgeGuides() {
		LinkedList<EdgeGuide> joinGuideGroup = guideWorklist.getJoinGroups();
		if (!joinGuideGroup.isEmpty()) {
			EdgeGuide firstEG = joinGuideGroup.getFirst();
			Node endNode = firstEG.getNextNode(); // end node is the same of all EGs in the list
			for (EdgeGuide eg : joinGuideGroup) {
				Node startNode = eg.getPrevNode();
				// Before merging the join group, the edges are visited.
				callVisitOnEdge(startNode, eg, endNode);
			}
			EdgeGuide mergedEG = guideWorklist.mergeJoinGroup(joinGuideGroup);
			callVisitOnNode(mergedEG, endNode);
		}
	}

	private Node visitNode(Node lastVisitNode, EdgeGuide currEdgeGuide, Node visitNode) {
		if (lastVisitNode != null) {
			callVisitOnEdge(lastVisitNode, currEdgeGuide, visitNode);
		}

		callVisitOnNode(currEdgeGuide, visitNode);
		return visitNode;
	}

	/** This method must be kept in sync with {@link #callVisitOnEdge(Node, EdgeGuide, Node)} */
	private void callVisitOnNode(EdgeGuide currEdgeGuide, Node visitNode) {
		if (!walkerVisitedNodes.contains(visitNode)) {
			for (GraphVisitorInternal walker : walkers) {
				walker.callVisit(visitNode);
			}
		}
		walkerVisitedNodes.add(visitNode);

		for (GraphVisitorInternal walker : walkers) {
			List<BranchWalkerInternal> activatedPaths = walker.activateRequestedExplorers();
			currEdgeGuide.addActiveBranches(activatedPaths);
		}

		for (Iterator<Map.Entry<GraphExplorerInternal, BranchWalkerInternal>> actPathIt = currEdgeGuide
				.getEWIterator(); actPathIt.hasNext();) {

			BranchWalkerInternal activePath = actPathIt.next().getValue();
			activePath.callVisit(visitNode);

			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

	/** This method must be kept in sync with {@link #callVisitOnNode(EdgeGuide, Node)} */
	private void callVisitOnEdge(Node lastVisitNode, EdgeGuide currEdgeGuide, Node visitNode) {
		ControlFlowEdge egEdge = currEdgeGuide.getEdge();
		ControlFlowEdge visitedEdge = currEdgeGuide.getEdge();

		if (!guideWorklist.edgeVisited(egEdge)) {
			for (GraphVisitorInternal walker : walkers) {
				walker.callVisit(lastVisitNode, visitNode, visitedEdge);
			}
		}

		for (GraphVisitorInternal walker : walkers) {
			List<BranchWalkerInternal> activatedPaths = walker.activateRequestedExplorers();
			currEdgeGuide.addActiveBranches(activatedPaths);
		}

		for (Iterator<Map.Entry<GraphExplorerInternal, BranchWalkerInternal>> actPathIt = currEdgeGuide
				.getEWIterator(); actPathIt.hasNext();) {

			BranchWalkerInternal activePath = actPathIt.next().getValue();
			activePath.callVisit(lastVisitNode, visitNode, visitedEdge);

			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
	}

}
