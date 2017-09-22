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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 * Provides the next {@link ControlFlowEdge} or {@link Node} with regard to a specific traverse direction. Also provides
 * the starting or ending node of a given {@link ComplexNode} with regard to a specific traverse direction. The
 * directions are implemented in {@link Forward} and {@link Backward}.
 * <p>
 * <b>Attention:</b> {@link ControlFlowEdge}s of type {@literal ControlFlowType.Repeat} are followed at most twice.
 */
abstract class NextEdgesProvider {
	private final Map<ControlFlowEdge, Integer> repeatEdges = new HashMap<>();

	/** Traverses edges from start to end */
	static class Forward extends NextEdgesProvider {
		Forward() {
		}

		Forward(Map<ControlFlowEdge, Integer> repeatEdges) {
			super.repeatEdges.putAll(repeatEdges);
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
		protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getSuccessorEdges();
			return nextEdges;
		}

		@Override
		protected Forward copy() {
			return new Forward(new HashMap<>(super.repeatEdges));
		}
	}

	/** Traverses edges from end to start */
	static public class Backward extends NextEdgesProvider {
		Backward() {
		}

		Backward(Map<ControlFlowEdge, Integer> repeatEdges) {
			super.repeatEdges.putAll(repeatEdges);
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
		protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getPredecessorEdges();
			return nextEdges;
		}

		@Override
		protected Backward copy() {
			return new Backward(new HashMap<>(super.repeatEdges));
		}
	}

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

	/** @return the all unfiltered next edges with regard to the traverse direction */
	abstract protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode);

	/** Resets the counter of traversed {@literal ControlFlowType.Repeat} edges. */
	protected void reset() {
		repeatEdges.clear();
	}

	/**
	 * Edges of type {@literal ControlFlowType.Repeat} are followed at most twice.
	 *
	 * @param nextNode
	 *            start location
	 * @param cfTypes
	 *            Resulting edges have one of the given {@link ControlFlowType}. Iff null/empty, all edges are part of
	 *            the result.
	 * @return all following edges of the given node.
	 */
	protected List<ControlFlowEdge> getNextEdges(Node nextNode, ControlFlowType... cfTypes) {
		List<ControlFlowEdge> nextEdges = getPlainNextEdges(nextNode);
		nextEdges = filter(nextEdges, cfTypes);
		return nextEdges;
	}

	/**
	 * Filters out all {@literal ControlFlowType.Repeat} edges iff they were traversed twice already, and filters out
	 * all edges whose {@link ControlFlowType} is not in the given set {@code cfTypes} iff {@code cfTypes} is neither
	 * null nor empty.
	 */
	protected List<ControlFlowEdge> filter(Iterable<ControlFlowEdge> edges, ControlFlowType... cfTypes) {
		List<ControlFlowEdge> filteredEdges = Lists.newLinkedList(edges);
		for (Iterator<ControlFlowEdge> edgeIt = filteredEdges.iterator(); edgeIt.hasNext();) {
			ControlFlowEdge edge = edgeIt.next();

			boolean removeEdge = false;
			removeEdge |= !edge.cfType.isInOrEmpty(cfTypes);
			removeEdge |= getOccurences(edge) >= 2;
			if (removeEdge) {
				edgeIt.remove();
			}
			if (edge.isRepeat()) {
				incrOccurence(edge);
			}
		}
		return filteredEdges;
	}

	private int getOccurences(ControlFlowEdge edge) {
		Integer count = repeatEdges.getOrDefault(edge, 0);
		return count;
	}

	private void incrOccurence(ControlFlowEdge edge) {
		int count = getOccurences(edge) + 1;
		repeatEdges.put(edge, count);
	}
}
