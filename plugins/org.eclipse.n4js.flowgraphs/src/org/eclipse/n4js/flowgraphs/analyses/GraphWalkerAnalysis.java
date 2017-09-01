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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Contains algorithms that start {@link GraphWalkerInternal}s using the {@link GraphWalkerGuideInternal}.
 */
public class GraphWalkerAnalysis {
	final FlowGraph cfg;

	/** Constructor */
	public GraphWalkerAnalysis(FlowGraph cfg) {
		this.cfg = cfg;
	}

	/** see {@link N4JSFlowAnalyses#performAnalyzes(GraphWalkerInternal...)} */
	public void analyseScript(N4JSFlowAnalyses flowAnalyses, Collection<GraphWalkerInternal> graphWalkers) {
		GraphWalkerGuideInternal guide = new GraphWalkerGuideInternal(flowAnalyses, graphWalkers);
		Set<Node> allNodes = new HashSet<>();
		for (ComplexNode cn : cfg.getAllComplexNodes()) {
			if (!cn.isControlElement()) {
				allNodes.addAll(cn.getNodes());
			}
		}

		Set<Node> visitedNodes;
		for (ControlFlowElement container : cfg.getContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);
			visitedNodes = guide.walkthroughForward(cnContainer);
			allNodes.removeAll(visitedNodes);
			visitedNodes = guide.walkthroughBackward(cnContainer);
			allNodes.removeAll(visitedNodes);
		}

		while (!allNodes.isEmpty()) {
			Node unvisitedNode = allNodes.iterator().next();
			ComplexNode cnUnvisited = cfg.getComplexNode(unvisitedNode.getControlFlowElement());
			if (cnUnvisited.isControlElement()) {
				allNodes.remove(unvisitedNode);
			} else {
				visitedNodes = guide.walkthroughIsland(cnUnvisited);
				allNodes.removeAll(visitedNodes);
			}
		}
	}

}
