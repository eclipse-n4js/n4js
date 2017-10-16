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

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Contains algorithms that start {@link GraphVisitorInternal}s using the {@link GraphVisitorGuideInternal}.
 */
public class GraphVisitorAnalysis {
	final FlowGraph cfg;

	/** Constructor */
	public GraphVisitorAnalysis(FlowGraph cfg) {
		this.cfg = cfg;
	}

	/** see {@link N4JSFlowAnalyzer#accept(GraphVisitor...)} */
	public void analyseScript(N4JSFlowAnalyzer flowAnalyzer, Collection<? extends GraphVisitorInternal> graphWalkers) {
		GraphVisitorGuideInternal guide = new GraphVisitorGuideInternal(flowAnalyzer, graphWalkers);
		guide.init();

		Set<Node> allNodes = getAllNonControlNodes();
		Set<Node> visitedNodes;
		for (ControlFlowElement container : cfg.getAllContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);
			visitedNodes = guide.walkthroughForward(cnContainer);
			allNodes.removeAll(visitedNodes);
			visitedNodes = guide.walkthroughBackward(cnContainer);
			allNodes.removeAll(visitedNodes);
		}

		for (ControlFlowElement catchBlock : cfg.getCatchBlocks()) {
			ComplexNode cnCatchBlock = cfg.getComplexNode(catchBlock);
			visitedNodes = guide.walkthroughCatchBlocks(cnCatchBlock);
			allNodes.removeAll(visitedNodes);
		}

		while (!allNodes.isEmpty()) {
			Node unvisitedNode = allNodes.iterator().next();
			ComplexNode cnUnvisited = cfg.getComplexNode(unvisitedNode.getControlFlowElement());
			if (cnUnvisited.isControlStatement()) {
				allNodes.remove(unvisitedNode);
			} else {

				visitedNodes = guide.walkthroughIsland(cnUnvisited);
				int unvisitedCount = allNodes.size();
				allNodes.removeAll(visitedNodes);
				if (allNodes.size() == unvisitedCount) {
					printErrMalformedGraph(cnUnvisited);
					break;
				}
			}
		}

		guide.terminate();
	}

	private void printErrMalformedGraph(ComplexNode cnUnvisited) {
		ControlFlowElement cfe = cnUnvisited.getControlFlowElement();
		String astNodeStr = FGUtils.getSourceText(cfe) + " (" + FGUtils.getClassName(cfe) + ")";
		System.err.println("Malformed control flow graph: Could not visit AST node: " + astNodeStr);
	}

	/** @return all nodes of the CFG, except for nodes of ControlElements */
	private Set<Node> getAllNonControlNodes() {
		Set<Node> allNodes = new HashSet<>();
		for (ComplexNode cn : cfg.getAllComplexNodes()) {
			if (!cn.isControlStatement()) { // make sure that no control element is part of the CFG
				allNodes.addAll(cn.getNodes());
			}
		}
		return allNodes;
	}

}
