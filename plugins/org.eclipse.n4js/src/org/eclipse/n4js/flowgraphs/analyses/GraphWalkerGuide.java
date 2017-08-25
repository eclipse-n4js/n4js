/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker.ActivatedPathPredicate.ActivePath;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker.Direction;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class GraphWalkerGuide {
	final Collection<GraphWalker> walkers;

	public GraphWalkerGuide(Collection<GraphWalker> walkers) {
		this.walkers = walkers;
	}

	public Set<ControlFlowElement> walkthroughForward(ComplexNode cn) {
		return walkthrough(cn, Direction.Forward);
	}

	public Set<ControlFlowElement> walkthroughBackward(ComplexNode cn) {
		return walkthrough(cn, Direction.Backward);
	}

	public Set<ControlFlowElement> walkthroughIsland(ComplexNode cn) {
		return walkthrough(cn, Direction.Islands);
	}

	private Set<ControlFlowElement> walkthrough(ComplexNode cn, Direction direction) {
		for (GraphWalker walker : walkers) {
			walker.setCurrentDirection(direction);
			walker.init();
		}

		Set<ControlFlowElement> allVisitedCFEs = new HashSet<>();
		List<NextEdgesProvider> edgeProviders = getEdgeProviders(direction);
		for (NextEdgesProvider edgeProvider : edgeProviders) {
			Set<ControlFlowElement> visitedCFEs = walkthrough(cn, edgeProvider);
			allVisitedCFEs.addAll(visitedCFEs);
		}

		for (GraphWalker walker : walkers) {
			walker.terminate();
		}
		return allVisitedCFEs;
	}

	private Set<ControlFlowElement> walkthrough(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<ControlFlowElement> allVisitedCFEs = new HashSet<>();
		LinkedList<DecoratedEdge> currDEdges = new LinkedList<>();
		List<DecoratedEdge> nextDEdges = getFirstDecoratedEdges(cn, edgeProvider);
		currDEdges.addAll(nextDEdges);

		ControlFlowElement lastVisitCFE = null;
		Set<ControlFlowType> edgeTypesFromLastVisitCFE = new HashSet<>();

		while (!currDEdges.isEmpty()) {
			DecoratedEdge currDEdge = currDEdges.removeFirst();

			Node nextNode = edgeProvider.getNextNode(currDEdge.edge);
			if (nextNode instanceof RepresentingNode) {
				lastVisitCFE = visitRepresentingNode(lastVisitCFE, edgeTypesFromLastVisitCFE, currDEdge, nextNode);
				allVisitedCFEs.add(lastVisitCFE);
			}

			nextDEdges = getNextDecoratedEdges(edgeProvider, currDEdge, nextNode);
			currDEdges.addAll(nextDEdges);
		}

		return allVisitedCFEs;
	}

	private ControlFlowElement visitRepresentingNode(ControlFlowElement lastVisitCFE,
			Set<ControlFlowType> edgeTypesFromLastVisitCFE, DecoratedEdge currDEdge, Node nextNode) {

		ControlFlowElement visitCFE = nextNode.getRepresentedControlFlowElement();
		if (lastVisitCFE != null) {
			callVisit(CallVisit.OnEdge, lastVisitCFE, edgeTypesFromLastVisitCFE, currDEdge, visitCFE);
			edgeTypesFromLastVisitCFE.clear();
		}

		edgeTypesFromLastVisitCFE.add(currDEdge.edge.cfType);
		lastVisitCFE = visitCFE;

		callVisit(CallVisit.OnNode, lastVisitCFE, edgeTypesFromLastVisitCFE, currDEdge, visitCFE);
		return lastVisitCFE;
	}

	enum CallVisit {
		OnNode, OnEdge
	}

	private void callVisit(CallVisit callVisit, ControlFlowElement lastVisitCFE,
			Set<ControlFlowType> edgeTypesFromLastVisitCFE,
			DecoratedEdge currDEdge, ControlFlowElement visitCFE) {

		for (Iterator<ActivePath> actPathIt = currDEdge.activePaths.iterator(); actPathIt.hasNext();) {
			ActivePath activePath = actPathIt.next();
			switch (callVisit) {
			case OnNode:
				activePath.callVisit(lastVisitCFE);
				break;
			case OnEdge:
				activePath.callVisit(lastVisitCFE, visitCFE, edgeTypesFromLastVisitCFE);
				break;
			}
			if (!activePath.isActive()) {
				actPathIt.remove();
			}
		}
		for (GraphWalker walker : walkers) {
			switch (callVisit) {
			case OnNode:
				walker.callVisit(visitCFE);
				break;
			case OnEdge:
				walker.callVisit(lastVisitCFE, visitCFE, edgeTypesFromLastVisitCFE);
				break;
			}
			List<ActivePath> activatedPaths = walker.activate();
			currDEdge.activePaths.addAll(activatedPaths);
		}
	}

	private List<DecoratedEdge> getFirstDecoratedEdges(ComplexNode cn, NextEdgesProvider edgeProvider) {
		List<DecoratedEdge> nextDEdges = new LinkedList<>();
		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		for (ControlFlowEdge nextEdge : nextEdges) {
			DecoratedEdge eF = new DecoratedEdge(nextEdge);
			nextDEdges.add(eF);
		}
		return nextDEdges;
	}

	private List<DecoratedEdge> getNextDecoratedEdges(NextEdgesProvider edgeProvider, DecoratedEdge currDEdge,
			Node nextNode) {

		List<DecoratedEdge> nextDEdges = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(nextNode);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			currDEdge.edge = nextEdge;
			nextDEdges.add(currDEdge);
		}

		for (ControlFlowEdge nextEdge = nextEdgeIt.next(); nextEdgeIt.hasNext(); nextEdge = nextEdgeIt.next()) {
			Set<ActivePath> forkedPaths = new HashSet<>();
			for (ActivePath aPath : currDEdge.activePaths) {
				ActivePath forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}

			DecoratedEdge dEdge = new DecoratedEdge(nextEdge, forkedPaths);
			nextDEdges.add(dEdge);
		}

		if (nextDEdges.isEmpty()) {
			for (ActivePath aPath : currDEdge.activePaths) {
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
		}
		return edgeProviders;
	}

	private class DecoratedEdge {
		ControlFlowEdge edge;
		final Set<ActivePath> activePaths = new HashSet<>();

		public DecoratedEdge(ControlFlowEdge edge) {
			this.edge = edge;
		}

		public DecoratedEdge(ControlFlowEdge edge, Set<ActivePath> activePaths) {
			this(edge);
			activePaths.addAll(activePaths);
		}

	}
}
