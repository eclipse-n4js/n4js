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
package org.eclipse.n4js.flowgraphs.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Lists;

/**
 *
 */
public class FlowGraph {
	final private Map<ControlFlowElement, ComplexNode> cnMap;
	final private Map<String, ControlFlowEdge> cfEdgeMap = new HashMap<>();

	/**
	 *
	 */
	public FlowGraph(Map<ControlFlowElement, ComplexNode> cnMap) {
		this.cnMap = cnMap;
		init();
	}

	private void init() {
		for (ComplexNode cn : getAllComplexNodes()) {
			for (Node node : cn.getNodes()) {
				for (ControlFlowEdge cfEdge : node.pred) {
					cfEdgeMap.put(cfEdge.toString(), cfEdge);
				}
				for (ControlFlowEdge cfEdge : node.succ) {
					cfEdgeMap.put(cfEdge.toString(), cfEdge);
				}
			}
		}
	}

	public Collection<ComplexNode> getAllComplexNodes() {
		return cnMap.values();
	}

	public Collection<ControlFlowEdge> getAllControlFlowEdges() {
		return cfEdgeMap.values();
	}

	public ComplexNode getComplexNode(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		if (!cnMap.containsKey(cfe))
			return null;
		return cnMap.get(cfe);
	}

	public ControlFlowType getControlFlowTypeToSuccessor(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		ComplexNode cnStart = getComplexNode(cfe);
		ComplexNode cnEnd = getComplexNode(cfeSucc);
		Node nStart = cnStart.getExit();
		Node nEnd = cnEnd.getEntry();

		List<ControlFlowEdge> path = findPath(nStart, nEnd);
		if (path != null) {
			List<ControlFlowType> nonSuccessorTypes = new LinkedList<>();
			for (ControlFlowEdge cfEdge : path) {
				if (cfEdge.cfType != ControlFlowType.Successor) {
					nonSuccessorTypes.add(cfEdge.cfType);
				}
			}
			if (nonSuccessorTypes.size() > 1) {
				throw new IllegalArgumentException("Given ControlFlowElements must be direct successors");
			}
			if (nonSuccessorTypes.size() == 1) {
				return nonSuccessorTypes.get(0);
			}
			return ControlFlowType.Successor;
		}

		throw new IllegalArgumentException("No path found between given ControlFlowElements");
	}

	private LinkedList<ControlFlowEdge> findPath(Node startNode, Node endNode) {
		LinkedList<LinkedList<ControlFlowEdge>> allPaths = new LinkedList<>();
		for (ControlFlowEdge e : startNode.getSuccessorEdges()) {
			LinkedList<ControlFlowEdge> path = new LinkedList<>();
			allPaths.add(path);
			path.add(e);
		}

		while (!allPaths.isEmpty()) {
			LinkedList<ControlFlowEdge> firstPath = allPaths.removeFirst();
			LinkedList<LinkedList<ControlFlowEdge>> ch = getPaths(firstPath);
			for (LinkedList<ControlFlowEdge> chPath : ch) {
				if (chPath.getLast().end == endNode) {
					return chPath;
				}
			}
			allPaths.addAll(ch);
		}
		return null;
	}

	private LinkedList<LinkedList<ControlFlowEdge>> getPaths(LinkedList<ControlFlowEdge> path) {
		LinkedList<LinkedList<ControlFlowEdge>> resultPaths = new LinkedList<>();
		boolean isFirstPath = true;
		ControlFlowEdge e = path.getLast();
		for (ControlFlowEdge succEdge : e.end.getSuccessorEdges()) {
			LinkedList<ControlFlowEdge> pathCopy = path;
			if (!isFirstPath)
				pathCopy = Lists.newLinkedList(pathCopy);
			pathCopy.add(succEdge);
			resultPaths.add(path);
			isFirstPath = false;
		}
		return resultPaths;
	}
}
