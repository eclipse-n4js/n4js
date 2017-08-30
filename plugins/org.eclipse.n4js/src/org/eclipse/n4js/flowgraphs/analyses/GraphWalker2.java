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
 *
 */
@SuppressWarnings("javadoc")
abstract public class GraphWalker2 extends GraphWalkerInternal {
	final private Set<ControlFlowEdge> visitedEdgesInternal = new HashSet<>();
	final private Set<FlowEdge> visitedEdges = new HashSet<>();

	/** Default direction is {@literal Direction.Forward} */
	protected GraphWalker2(Direction... directions) {
		this(null, directions);
	}

	public GraphWalker2(ControlFlowElement container, Direction... directions) {
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
	final protected void visit(ControlFlowEdge edge) {
		visitedEdgesInternal.add(edge);
		Set<FlowEdge> newConnections = getNewConnections(edge);
		for (FlowEdge dEdge : newConnections) {
			if (!visitedEdges.contains(dEdge)) {
				visitedEdges.add(dEdge);
				visit(dEdge);
			}
		}
	}

	private static class HalfFlowEdge {
		final ControlFlowElement node;
		final Set<ControlFlowType> cfTypes;

		HalfFlowEdge(ControlFlowElement node, Set<ControlFlowType> cfTypes) {
			this.node = node;
			this.cfTypes = cfTypes;
		}
	}

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

	@Override
	final protected void init() {
		visitedEdgesInternal.clear();
		init2();
	}

	abstract protected void init2();

	@Override
	abstract protected void init(Direction direction);

	abstract protected void visit(ControlFlowElement cfe);

	abstract protected void visit(FlowEdge edge);

	@Override
	abstract protected void terminate(Direction direction);

	@Override
	abstract protected void terminate();

	abstract public class ActivatedPathPredicate2 extends ActivatedPathPredicateInternal {

		protected ActivatedPathPredicate2(PredicateType predicateType) {
			super(predicateType);
		}

		@Override
		abstract protected ActivePath2 first();

		abstract public class ActivePath2 extends ActivePathInternal {
			ControlFlowElement pLastCFE;
			Set<ControlFlowType> pEdgeTypes = new HashSet<>();

			@Override
			final protected void visit(Node node) {
				if (node instanceof RepresentingNode) {
					ControlFlowElement cfe = node.getRepresentedControlFlowElement();
					if (pLastCFE != null) {
						visit(pLastCFE, cfe, pEdgeTypes);
						pEdgeTypes.clear();
					}
					visit(cfe);
					pLastCFE = cfe;
				}
			}

			@Override
			final protected void visit(ControlFlowEdge edge) {
				pEdgeTypes.add(edge.cfType);
			}

			@Override
			abstract protected void init();

			abstract protected void visit(ControlFlowElement cfe);

			abstract protected void visit(ControlFlowElement start, ControlFlowElement end,
					Set<ControlFlowType> cfTypes);

			abstract protected ActivePath2 fork2();

			@Override
			final protected ActivePath2 fork() {
				ActivePath2 ap2 = fork2();
				ap2.pLastCFE = pLastCFE;
				ap2.pEdgeTypes.addAll(pEdgeTypes);
				return ap2;
			}

			@Override
			abstract protected void terminate();

		}
	}
}
