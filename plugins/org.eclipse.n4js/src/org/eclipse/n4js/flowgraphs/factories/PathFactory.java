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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.NoPath;
import org.eclipse.n4js.flowgraphs.Path;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 *
 */
public class PathFactory extends NextEdgesProvider {

	public static Path buildPath(Node start, Node end) {
		LinkedList<ControlFlowEdge> pathEdges = findPath(start, end, true);
		Path path = null;
		if (pathEdges != null) {
			path = new Path(start, end, pathEdges, true);
		} else {
			path = new NoPath(start, end, true);
		}
		return path;
	}

	static private LinkedList<ControlFlowEdge> findPath(Node startNode, Node endNode, boolean forwards,
			ControlFlowType... cfTypes) {

		if (startNode == endNode) {
			return Lists.newLinkedList();
		}

		LinkedList<ControlFlowEdge> loopEdges = new LinkedList<>();
		LinkedList<LinkedList<ControlFlowEdge>> allPaths = new LinkedList<>();

		List<ControlFlowEdge> nextEdges = getNextEdges(startNode, forwards, loopEdges, cfTypes);
		for (ControlFlowEdge nextEdge : nextEdges) {
			LinkedList<ControlFlowEdge> path = new LinkedList<>();
			path.add(nextEdge);
			if (isEndNode(endNode, nextEdge, forwards)) {
				return path; // direct edge from startNode to endNode due to nextEdge
			}
			allPaths.add(path);
		}

		while (!allPaths.isEmpty()) {
			LinkedList<ControlFlowEdge> firstPath = allPaths.removeFirst();
			LinkedList<LinkedList<ControlFlowEdge>> ch = getPaths(firstPath, forwards, loopEdges, cfTypes);
			for (LinkedList<ControlFlowEdge> chPath : ch) {
				if (isEndNode(endNode, chPath.getLast(), forwards)) {
					return chPath;
				}
			}
			allPaths.addAll(ch);
		}

		return null;
	}

}
