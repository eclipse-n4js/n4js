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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Sets;

/**
 * see {@link GraphVisitorInternal}
 */
abstract public class GraphVisitor extends GraphVisitorInternal {
	final private Set<ControlFlowEdge> visitedEdgesInternal = new HashSet<>();
	final private Set<FlowEdge> visitedEdges = new HashSet<>();

	/** see {@link GraphVisitorInternal#GraphVisitorInternal(Direction...)} */
	protected GraphVisitor(Direction... directions) {
		this(null, directions);
	}

	/** see {@link GraphVisitorInternal#GraphVisitorInternal(ControlFlowElement, Direction...)} */
	public GraphVisitor(ControlFlowElement container, Direction... directions) {
		super(container, directions);
	}

	@Override
	final protected void visit(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfe = node.getRepresentedControlFlowElement();
			visit(cfe);
		}
	}

	@Override
	final protected void visit(Node lastNode, Node currentNode, ControlFlowEdge edge) {
		visitedEdgesInternal.add(edge);
		Set<FlowEdge> newConnections = getNewConnections(edge);
		for (FlowEdge dEdge : newConnections) {
			if (!visitedEdges.contains(dEdge)) {
				visitedEdges.add(dEdge);
				ControlFlowElement startCFE = getStartCFE(dEdge);
				ControlFlowElement endCFE = getEndCFE(dEdge);
				visit(startCFE, endCFE, dEdge);
			}
		}
	}

	/**
	 * A half edge consists only of one end of the edge and some (maybe all) of its control flow types. Two
	 * {@link HalfFlowEdge}s can be merged to a single {@link FlowEdge}.
	 */
	private static class HalfFlowEdge {
		final ControlFlowElement node;
		final Set<ControlFlowType> cfTypes;

		HalfFlowEdge(ControlFlowElement node, Set<ControlFlowType> cfTypes) {
			this.node = node;
			this.cfTypes = cfTypes;
		}
	}

	/**
	 * From a given edge, all preceding {@link RepresentingNode}s Prn and all succeeding {@link RepresentingNode}s Srn
	 * are searched. For every such node, an {@link HalfFlowEdge}s are created, called Phfe and Shfe, that store the end
	 * nodes and all {@link ControlFlowType} which occurred on the way to their end node. Then, all Phfe are merged with
	 * all Shfe to create {@link FlowEdge}s FE. These flow edges are then visited, if not already done so. Note, that
	 * while searching for Prn and Srn, only already found {@link ControlFlowEdge}s are followed.
	 */
	private Set<FlowEdge> getNewConnections(ControlFlowEdge edge) {
		Set<HalfFlowEdge> startHalfs = new HashSet<>();
		Set<HalfFlowEdge> endHalfs = new HashSet<>();
		Set<FlowEdge> directNeighbours = new HashSet<>();

		NextEdgesProvider forwardEP = new NextEdgesProvider.Forward();
		endHalfs = findDirectNeighbours(edge, forwardEP, new HashSet<>());

		NextEdgesProvider backwardEP = new NextEdgesProvider.Backward();
		startHalfs = findDirectNeighbours(edge, backwardEP, new HashSet<>());

		for (HalfFlowEdge startHalf : startHalfs) {
			for (HalfFlowEdge endHalf : endHalfs) {
				Set<ControlFlowType> cfTypes = new HashSet<>();
				cfTypes.addAll(startHalf.cfTypes);
				cfTypes.addAll(endHalf.cfTypes);
				FlowEdge flowEdge = new FlowEdge(startHalf.node, endHalf.node, cfTypes);
				directNeighbours.add(flowEdge);
			}
		}

		return directNeighbours;
	}

	private Set<HalfFlowEdge> findDirectNeighbours(ControlFlowEdge edge, NextEdgesProvider edgeProvider,
			Set<ControlFlowType> cfTypes) {

		cfTypes.add(edge.cfType);
		Node nextNode = edgeProvider.getNextNode(edge);
		if (nextNode instanceof RepresentingNode) {
			ControlFlowElement endCFE = nextNode.getRepresentedControlFlowElement();
			HalfFlowEdge flowEdge = new HalfFlowEdge(endCFE, cfTypes);
			return Sets.newHashSet(flowEdge);
		}

		Set<HalfFlowEdge> directNeighbours = new HashSet<>();
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(nextNode);
		for (ControlFlowEdge nextEdge : nextEdges) {
			if (visitedEdgesInternal.contains(nextEdge)) {
				HashSet<ControlFlowType> cftCopy = Sets.newHashSet(cfTypes);
				Set<HalfFlowEdge> someDirectNeighbours = findDirectNeighbours(nextEdge, edgeProvider, cftCopy);
				directNeighbours.addAll(someDirectNeighbours);
			}
		}

		return directNeighbours;
	}

	private ControlFlowElement getStartCFE(FlowEdge dEdge) {
		switch (getCurrentDirection()) {
		case Forward:
		case Islands:
			return dEdge.start;
		default:
			return dEdge.end;
		}
	}

	private ControlFlowElement getEndCFE(FlowEdge dEdge) {
		switch (getCurrentDirection()) {
		case Forward:
		case Islands:
			return dEdge.end;
		default:
			return dEdge.start;
		}
	}

	@Override
	abstract protected void initAll();

	@Override
	final protected void initInternal(Direction curDirection, ControlFlowElement curContainer) {
		visitedEdgesInternal.clear();
		visitedEdges.clear();
		init(curDirection, curContainer);
	}

	/**
	 * Called after {@link #initAll()} and before any visit-method is called.
	 *
	 * @param curDirection
	 *            direction of succeeding calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	abstract protected void init(Direction curDirection, ControlFlowElement curContainer);

	/** Analog to {@link GraphVisitorInternal#visit(Node)} */
	abstract protected void visit(ControlFlowElement cfe);

	/** Analog to {@link GraphVisitorInternal#visit(Node, Node, ControlFlowEdge)} */
	abstract protected void visit(ControlFlowElement lastCFE, ControlFlowElement currentCFE, FlowEdge edge);

	@Override
	abstract protected void terminate(Direction curDirection, ControlFlowElement curContainer);

	@Override
	abstract protected void terminateAll();

	/** see {@link GraphVisitorInternal.PathExplorerInternal} */
	abstract public class PathExplorer extends PathExplorerInternal {

		/**
		 * see {@link GraphVisitorInternal.PathExplorerInternal#PathExplorerInternal(PredicateType)}
		 */
		protected PathExplorer(PredicateType predicateType) {
			super(predicateType);
		}

		/**
		 * see {@link GraphVisitorInternal.PathExplorerInternal#PathExplorerInternal(PredicateType, boolean)}
		 */
		protected PathExplorer(PredicateType predicateType, boolean passAsDefault) {
			super(predicateType, passAsDefault);
		}

		@Override
		abstract protected PathWalker firstPath();

		/** see {@link GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal} */
		abstract public class PathWalker extends PathWalkerInternal {
			ControlFlowElement pLastCFE;
			Set<ControlFlowType> pEdgeTypes = new HashSet<>();

			@Override
			final protected void visit(Node node) {
				if (node instanceof RepresentingNode) {
					ControlFlowElement cfe = node.getRepresentedControlFlowElement();
					if (pLastCFE != null) {
						FlowEdge edge = new FlowEdge(pLastCFE, cfe, pEdgeTypes);
						visit(edge);
						pEdgeTypes.clear();
					}
					visit(cfe);
					pLastCFE = cfe;
				}
			}

			@Override
			final protected void visit(Node start, Node end, ControlFlowEdge edge) {
				pEdgeTypes.add(edge.cfType);
			}

			@Override
			abstract protected void init();

			/** Called for each node in direction of a path. */
			abstract protected void visit(ControlFlowElement cfe);

			/**
			 * Called for each edge in direction of a path. The edge direction is aligned to the traversing direction,
			 * i.e. the start node was traversed before the end node of the edge.
			 */
			abstract protected void visit(FlowEdge edge);

			/** see {@link GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal#fork()} */
			abstract protected PathWalker forkPath();

			@Override
			final protected PathWalker fork() {
				PathWalker ap2 = forkPath();
				ap2.pLastCFE = pLastCFE;
				ap2.pEdgeTypes.addAll(pEdgeTypes);
				return ap2;
			}

			@Override
			abstract protected void terminate();

		}
	}
}
