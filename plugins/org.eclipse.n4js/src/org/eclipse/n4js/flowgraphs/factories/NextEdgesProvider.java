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
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 *
 */
public class NextEdgesProvider {

	protected static LinkedList<LinkedList<ControlFlowEdge>> getPaths(LinkedList<ControlFlowEdge> path,
			boolean forwards, LinkedList<ControlFlowEdge> loopEdges, ControlFlowType... cfTypes) {

		LinkedList<LinkedList<ControlFlowEdge>> resultPaths = new LinkedList<>();
		ControlFlowEdge e = path.getLast();
		Node nextNode = getNextNode(e, forwards);
		List<ControlFlowEdge> nextEdges = getNextEdges(nextNode, forwards, loopEdges, cfTypes);

		for (ControlFlowEdge nextEdge : nextEdges) {
			LinkedList<ControlFlowEdge> pathCopy = path;
			if (nextEdges.size() > 1)
				pathCopy = Lists.newLinkedList(pathCopy);
			pathCopy.add(nextEdge);
			resultPaths.add(pathCopy);
		}
		return resultPaths;
	}

	protected static boolean isEndNode(Node node, ControlFlowEdge edge, boolean forwards) {
		return getNextNode(edge, forwards) == node;
	}

	protected static Node getNextNode(ControlFlowEdge edge, boolean forwards) {
		if (forwards) {
			return edge.end;
		} else {
			return edge.start;
		}
	}

	protected static List<ControlFlowEdge> getNextEdges(Node nextNode, boolean forwards,
			LinkedList<ControlFlowEdge> loopEdges, ControlFlowType... cfTypes) {

		List<ControlFlowEdge> nextEdges = null;
		if (forwards) {
			nextEdges = nextNode.getSuccessorEdges();
		} else {
			nextEdges = nextNode.getPredecessorEdges();
		}
		filter(nextEdges, loopEdges, cfTypes);
		return nextEdges;
	}

	protected static void filter(Iterable<ControlFlowEdge> edges, LinkedList<ControlFlowEdge> loopEdges,
			ControlFlowType... cfTypes) {

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
