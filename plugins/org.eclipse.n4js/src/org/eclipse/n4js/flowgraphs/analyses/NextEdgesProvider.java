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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 *
 */
abstract class NextEdgesProvider {
	private final Set<ControlFlowEdge> loopEdges = new HashSet<>();

	static class Forward extends NextEdgesProvider {
		@Override
		protected Node getStartNode(ComplexNode cn) {
			return cn.getEntry();
		}

		@Override
		protected Node getNextNode(ControlFlowEdge edge) {
			return edge.end;
		}

		@Override
		protected List<ControlFlowEdge> getNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getSuccessorEdges();
			return nextEdges;
		}
	}

	static class Backward extends NextEdgesProvider {
		@Override
		protected Node getStartNode(ComplexNode cn) {
			return cn.getExit();
		}

		@Override
		protected Node getNextNode(ControlFlowEdge edge) {
			return edge.start;
		}

		@Override
		protected List<ControlFlowEdge> getNextEdges(Node nextNode) {
			List<ControlFlowEdge> nextEdges = nextNode.getPredecessorEdges();
			return nextEdges;
		}
	}

	protected void reset() {
		loopEdges.clear();
	}

	abstract protected Node getStartNode(ComplexNode cn);

	abstract protected Node getNextNode(ControlFlowEdge edge);

	abstract protected List<ControlFlowEdge> getNextEdges(Node nextNode);

	protected List<ControlFlowEdge> getNextEdges(Node nextNode, ControlFlowType... cfTypes) {
		List<ControlFlowEdge> nextEdges = getNextEdges(nextNode);
		filter(nextEdges, cfTypes);
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

	protected void filter(Iterable<ControlFlowEdge> edges, ControlFlowType... cfTypes) {
		for (Iterator<ControlFlowEdge> edgeIt = edges.iterator(); edgeIt.hasNext();) {
			ControlFlowEdge edge = edgeIt.next();

			boolean removeEdge = false;
			removeEdge |= !edge.cfType.isInOrEmpty(cfTypes);
			removeEdge |= loopEdges.contains(edge);
			if (removeEdge) {
				edgeIt.remove();
			}
			if (edge.isLoopCarried()) {
				loopEdges.add(edge);
			}
		}
	}

}
