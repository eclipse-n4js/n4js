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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 *
 */
abstract class NextEdgesProvider {
	private final Map<ControlFlowEdge, Integer> repeatEdges = new HashMap<>();

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
		protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getSuccessorEdges();
			return nextEdges;
		}

		@Override
		protected Forward copy() {
			return new Forward(new HashMap<>(super.repeatEdges));
		}
	}

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
		protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getPredecessorEdges();
			return nextEdges;
		}

		@Override
		protected Backward copy() {
			return new Backward(new HashMap<>(super.repeatEdges));
		}
	}

	abstract protected Node getNextNode(ControlFlowEdge edge);

	abstract protected Node getPrevNode(ControlFlowEdge edge);

	abstract protected NextEdgesProvider copy();

	abstract protected Node getStartNode(ComplexNode cn);

	abstract protected List<ControlFlowEdge> getPlainNextEdges(Node nextNode);

	protected void reset() {
		repeatEdges.clear();
	}

	protected List<ControlFlowEdge> getNextEdges(Node nextNode, ControlFlowType... cfTypes) {
		List<ControlFlowEdge> nextEdges = getPlainNextEdges(nextNode);
		nextEdges = filter(nextEdges, cfTypes);
		return nextEdges;
	}

	protected LinkedList<LinkedList<ControlFlowEdge>> getPaths(LinkedList<ControlFlowEdge> path,
			ControlFlowType... cfTypes) {

		LinkedList<LinkedList<ControlFlowEdge>> resultPaths = new LinkedList<>();
		ControlFlowEdge e = path.getLast();
		Node nextNode = getNextNode(e);
		List<ControlFlowEdge> nextEdges = getNextEdges(nextNode, cfTypes);

		for (ControlFlowEdge nextEdge : nextEdges) {
			LinkedList<ControlFlowEdge> pathCopy = path;
			if (nextEdges.size() > 1)
				pathCopy = Lists.newLinkedList(pathCopy);
			pathCopy.add(nextEdge);
			resultPaths.add(pathCopy);
		}
		return resultPaths;
	}

	protected boolean isEndNode(Node node, ControlFlowEdge edge) {
		return getNextNode(edge) == node;
	}

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
		if (!repeatEdges.containsKey(edge)) {
			return 0;
		} else {
			Integer count = repeatEdges.get(edge);
			return count;
		}
	}

	private void incrOccurence(ControlFlowEdge edge) {
		if (!repeatEdges.containsKey(edge)) {
			repeatEdges.put(edge, 0);
		}
		Integer count = repeatEdges.get(edge);
		count++;
		repeatEdges.put(edge, count);
	}
}
