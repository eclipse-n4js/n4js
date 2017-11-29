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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * Provides the next {@link ControlFlowEdge} or {@link Node} with regard to a specific traverse direction. Also provides
 * the starting or ending node of a given {@link ComplexNode} with regard to a specific traverse direction. The
 * directions are implemented in {@link Forward} and {@link Backward}.
 * <p>
 * <b>Attention:</b> {@link ControlFlowEdge}s of type {@literal ControlFlowType.Repeat} are followed at most twice.
 */
abstract class NextEdgesProvider {
	private final Map<ControlFlowEdge, Integer> loopEnterEdges = new HashMap<>();

	/** Traverses edges from start to end */
	static class Forward extends NextEdgesProvider {
		Forward() {
		}

		Forward(Map<ControlFlowEdge, Integer> repeatEdges) {
			super.loopEnterEdges.putAll(repeatEdges);
		}

		@Override
		protected boolean isForward() {
			return true;
		}

		@Override
		protected Node getPrevNode(ControlFlowEdge edge) {
			return edge.start;
		}

		@Override
		protected Node getNextNode(ControlFlowEdge edge) {
			return edge.end;
		}

		@Override
		protected Node getStartNode(ComplexNode cn) {
			return cn.getEntry();
		}

		@Override
		protected Node getEndNode(ComplexNode cn) {
			return cn.getExit();
		}

		@Override
		protected Collection<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			Collection<ControlFlowEdge> nextEdges = nextNode.getSuccessorEdges();
			return nextEdges;
		}

		@Override
		protected Collection<ControlFlowEdge> getPlainPrevEdges(Node nextNode) {
			Collection<ControlFlowEdge> nextEdges = nextNode.getPredecessorEdges();
			return nextEdges;
		}

		@Override
		protected Forward copy() {
			return new Forward(new HashMap<>(super.loopEnterEdges));
		}
	}

	/** Traverses edges from end to start */
	static public class Backward extends NextEdgesProvider {
		Backward() {
		}

		Backward(Map<ControlFlowEdge, Integer> repeatEdges) {
			super.loopEnterEdges.putAll(repeatEdges);
		}

		@Override
		protected boolean isForward() {
			return false;
		}

		@Override
		protected Node getPrevNode(ControlFlowEdge edge) {
			return edge.end;
		}

		@Override
		protected Node getNextNode(ControlFlowEdge edge) {
			return edge.start;
		}

		@Override
		protected Node getStartNode(ComplexNode cn) {
			return cn.getExit();
		}

		@Override
		protected Node getEndNode(ComplexNode cn) {
			return cn.getEntry();
		}

		@Override
		protected Collection<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			Collection<ControlFlowEdge> nextEdges = nextNode.getPredecessorEdges();
			return nextEdges;
		}

		@Override
		protected Collection<ControlFlowEdge> getPlainPrevEdges(Node nextNode) {
			Collection<ControlFlowEdge> nextEdges = nextNode.getSuccessorEdges();
			return nextEdges;
		}

		@Override
		protected Backward copy() {
			return new Backward(new HashMap<>(super.loopEnterEdges));
		}
	}

	/** @return true iff this edge provider traverses in forward direction */
	abstract boolean isForward();

	/** @return the next node with regard to the traverse direction */
	abstract protected Node getNextNode(ControlFlowEdge edge);

	/** @return the previous node with regard to the traverse direction */
	abstract protected Node getPrevNode(ControlFlowEdge edge);

	/** @return a copy of the {@code this} instance */
	abstract protected NextEdgesProvider copy();

	/**
	 * @return the start node with regard to the traverse direction so that the internal nodes of {@code cn} will be
	 *         traversed first.
	 */
	abstract protected Node getStartNode(ComplexNode cn);

	/**
	 * @return the end node with regard to the traverse direction so that the internal nodes of {@code cn} will be
	 *         traversed first.
	 */
	abstract protected Node getEndNode(ComplexNode cn);

	/** @return the all unfiltered previous edges with regard to the traverse direction */
	abstract protected Collection<ControlFlowEdge> getPlainPrevEdges(Node nextNode);

	/** @return the all unfiltered next edges with regard to the traverse direction */
	abstract protected Collection<ControlFlowEdge> getPlainNextEdges(Node nextNode);

	/** Resets the counter of traversed {@literal ControlFlowType.Repeat} edges. */
	protected void reset() {
		loopEnterEdges.clear();
	}

	protected void join(NextEdgesProvider edgesProvider) {
		for (Map.Entry<ControlFlowEdge, Integer> repeatCounter : edgesProvider.loopEnterEdges.entrySet()) {
			ControlFlowEdge edge = repeatCounter.getKey();
			Integer otherCount = repeatCounter.getValue();
			int myCount = getOccurences(edge);
			int newCount = Math.max(myCount, otherCount);
			loopEnterEdges.put(edge, newCount);
		}
	}

	/**
	 * Edges of type {@literal ControlFlowType.Repeat} are followed at most twice.
	 *
	 * @param nextNode
	 *            start location
	 * @return all following edges of the given node.
	 */
	protected List<ControlFlowEdge> getNextEdges(Node nextNode, ControlFlowType... flowTypes) {
		Iterable<ControlFlowEdge> nextEdges = getPlainNextEdges(nextNode);
		List<ControlFlowEdge> filteredEdges = filter(nextEdges, flowTypes);
		return filteredEdges;
	}

	/**
	 * Filters out all {@literal ControlFlowType.Repeat} edges iff they were traversed twice already, and filters out
	 * all edges whose {@link ControlFlowType} is not in the given set {@code cfTypes} iff {@code cfTypes} is neither
	 * null nor empty.
	 */
	protected List<ControlFlowEdge> filter(Iterable<ControlFlowEdge> edges, ControlFlowType... flowTypes) {
		List<ControlFlowEdge> filteredEdges = new LinkedList<>(); // copy of the original pred/succ list of Node
		for (ControlFlowEdge cfEdge : edges) {
			boolean copyEdge = true;
			int maxOccurences = getMaxOccurences(cfEdge.cfType);
			if (maxOccurences > 0) {
				copyEdge = getOccurences(cfEdge) < maxOccurences;
				incrOccurence(cfEdge);
			}
			if (copyEdge && cfEdge.cfType.isInOrEmpty(flowTypes)) {
				filteredEdges.add(cfEdge);
			}
		}
		return filteredEdges;
	}

	private int getMaxOccurences(ControlFlowType cfType) {
		switch (cfType) {
		case LoopEnter:
			return 2;
		case LoopReenter:
			return 1;
		default:
			return -1;
		}
	}

	private int getOccurences(ControlFlowEdge edge) {
		Integer count = loopEnterEdges.getOrDefault(edge, 0);
		return count;
	}

	private void incrOccurence(ControlFlowEdge edge) {
		int count = getOccurences(edge) + 1;
		loopEnterEdges.put(edge, count);
	}
}
