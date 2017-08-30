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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerGuideInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class FlowGraph {
	final private TreeSet<ControlFlowElement> cfContainers;
	final private Map<ControlFlowElement, ComplexNode> cnMap;
	final private Map<String, ControlFlowEdge> cfEdgeMap = new HashMap<>();

	/**
	 *
	 */
	public FlowGraph(TreeSet<ControlFlowElement> cfContainers, Map<ControlFlowElement, ComplexNode> cnMap) {
		this.cfContainers = cfContainers;
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

	public void analyze(Collection<GraphWalkerInternal> graphWalkers) {
		GraphWalkerGuideInternal guide = new GraphWalkerGuideInternal(graphWalkers);
		Set<Node> allNodes = new HashSet<>();
		for (ComplexNode cn : cnMap.values()) {
			if (!cn.isControlElement()) {
				allNodes.addAll(cn.getNodes());
			}
		}

		Set<Node> visitedNodes;
		for (ControlFlowElement container : cfContainers) {
			ComplexNode cnContainer = cnMap.get(container);
			visitedNodes = guide.walkthroughForward(cnContainer);
			allNodes.removeAll(visitedNodes);
			visitedNodes = guide.walkthroughBackward(cnContainer);
			allNodes.removeAll(visitedNodes);
		}

		while (!allNodes.isEmpty()) {
			Node unvisitedNode = allNodes.iterator().next();
			ComplexNode cnUnvisited = cnMap.get(unvisitedNode.getControlFlowElement());
			if (cnUnvisited.isControlElement()) {
				allNodes.remove(unvisitedNode);
			} else {
				visitedNodes = guide.walkthroughIsland(cnUnvisited);
				allNodes.removeAll(visitedNodes);
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

}
