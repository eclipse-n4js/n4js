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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.Path;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerGuide;
import org.eclipse.n4js.flowgraphs.analyses.PathFactory;
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

	public void analyze(Collection<GraphWalker> graphWalkers) {
		GraphWalkerGuide guide = new GraphWalkerGuide(graphWalkers);
		Set<ControlFlowElement> allCFEs = new HashSet<>(cnMap.keySet());
		Set<ControlFlowElement> visitedCFEs;
		for (ControlFlowElement container : cfContainers) {
			ComplexNode cnContainer = cnMap.get(container);
			visitedCFEs = guide.walkthroughForward(cnContainer);
			allCFEs.removeAll(visitedCFEs);
			visitedCFEs = guide.walkthroughBackward(cnContainer);
			allCFEs.removeAll(visitedCFEs);
		}

		while (!allCFEs.isEmpty()) {
			ControlFlowElement unvisitedCFE = allCFEs.iterator().next();
			ComplexNode cnUnvisited = cnMap.get(unvisitedCFE);

			visitedCFEs = guide.walkthroughIsland(cnUnvisited);
			allCFEs.removeAll(visitedCFEs);
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
		Path path = getPath(cfe, cfeSucc);
		if (path.isConnecting()) {
			return path.getControlFlowTypes();
		} else {
			throw new IllegalArgumentException("No path found between given ControlFlowElements");
		}
	}

	/**
	 * @return the path from cfe to cfeSucc
	 */
	public Path getPath(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		ComplexNode cnStart = getComplexNode(cfe);
		ComplexNode cnEnd = getComplexNode(cfeSucc);
		Node nStart = cnStart.getRepresent();
		Node nEnd = cnEnd.getRepresent();
		Path path = PathFactory.buildPath(nStart, nEnd);
		return path;
	}

}
