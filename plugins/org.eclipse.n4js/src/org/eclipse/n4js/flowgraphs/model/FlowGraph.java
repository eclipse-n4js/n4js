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
import java.util.TreeSet;

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
		return getControlFlowTypeToSuccessors(cfe, cfeSucc).first();
	}

	public TreeSet<ControlFlowType> getControlFlowTypeToSuccessors(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		ComplexNode cnStart = getComplexNode(cfe);
		ComplexNode cnEnd = getComplexNode(cfeSucc);
		Node nStart = cnStart.getRepresent();
		Node nEnd = cnEnd.getRepresent();

		List<ControlFlowEdge> path = findPath(nStart, nEnd);
		if (path != null && !path.isEmpty()) {
			TreeSet<ControlFlowType> nonSuccessorTypes = new TreeSet<>();
			for (ControlFlowEdge cfEdge : path) {
				nonSuccessorTypes.add(cfEdge.cfType);
			}
			if (!nonSuccessorTypes.isEmpty()) {
				return nonSuccessorTypes;
			}
		}

		throw new IllegalArgumentException("No path found between given ControlFlowElements");
	}

	private LinkedList<ControlFlowEdge> findPath(Node startNode, Node endNode) {
		LinkedList<LinkedList<ControlFlowEdge>> allPaths = new LinkedList<>();
		for (ControlFlowEdge dSucc : startNode.getSuccessorEdges()) {
			LinkedList<ControlFlowEdge> path = new LinkedList<>();
			path.add(dSucc);
			if (dSucc.end == endNode) {
				return path; // direct edge from startNode to endNode due to dSucc
			}
			allPaths.add(path);
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
		ControlFlowEdge e = path.getLast();
		List<ControlFlowEdge> successorEdges = e.end.getSuccessorEdges();
		for (ControlFlowEdge succEdge : successorEdges) {
			LinkedList<ControlFlowEdge> pathCopy = path;
			if (successorEdges.size() > 1)
				pathCopy = Lists.newLinkedList(pathCopy);
			pathCopy.add(succEdge);
			resultPaths.add(path);
		}
		return resultPaths;
	}
}
