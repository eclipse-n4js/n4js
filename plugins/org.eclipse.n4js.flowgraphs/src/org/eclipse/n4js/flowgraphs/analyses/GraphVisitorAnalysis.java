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
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;

/**
 * Contains algorithms that start {@link GraphVisitorInternal}s using the {@link GraphVisitorGuideInternal}.
 */
public class GraphVisitorAnalysis {
	static private final DataCollector dcForwardAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Forward", "Flow Graphs", "Perform Analyses");
	static private final DataCollector dcBackwardAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Backward", "Flow Graphs", "Perform Analyses");
	static private final DataCollector dcCatchBlocksAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("CatchBlocks", "Flow Graphs", "Perform Analyses");
	static private final DataCollector dcIslandsAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Islands", "Flow Graphs", "Perform Analyses");

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
		for (ControlFlowElement container : cfg.getAllContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);

			traverseForwards(guide, allNodes, cnContainer);
			traverseBackwards(guide, allNodes, cnContainer);
		}

		traverseCatchBlocks(guide, allNodes);
		traverseIslands(guide, allNodes);

		guide.terminate();
	}

	private void traverseForwards(GraphVisitorGuideInternal guide, Set<Node> allNodes, ComplexNode cnContainer) {
		Measurement msmnt = dcForwardAnalyses.getMeasurement("Forward_" + cfg.getScriptName());
		Set<Node> visitedNodes = guide.walkthroughForward(cnContainer);
		msmnt.end();
		allNodes.removeAll(visitedNodes);
	}

	private void traverseBackwards(GraphVisitorGuideInternal guide, Set<Node> allNodes, ComplexNode cnContainer) {
		Measurement msmnt = dcBackwardAnalyses.getMeasurement("Forward_" + cfg.getScriptName());
		Set<Node> visitedNodes = guide.walkthroughBackward(cnContainer);
		msmnt.end();
		allNodes.removeAll(visitedNodes);
	}

	private void traverseCatchBlocks(GraphVisitorGuideInternal guide, Set<Node> allNodes) {
		Measurement msmnt = dcCatchBlocksAnalyses.getMeasurement("CatchBlocks_" + cfg.getScriptName());
		Set<Node> visitedNodes;
		for (ControlFlowElement catchBlock : cfg.getCatchBlocks()) {
			ComplexNode cnCatchBlock = cfg.getComplexNode(catchBlock);
			visitedNodes = guide.walkthroughCatchBlocks(cnCatchBlock);
			allNodes.removeAll(visitedNodes);
		}
		msmnt.end();
	}

	private void traverseIslands(GraphVisitorGuideInternal guide, Set<Node> allNodes) {
		Measurement msmnt = dcIslandsAnalyses.getMeasurement("Islands_" + cfg.getScriptName());
		Set<Node> visitedNodes;
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
		msmnt.end();
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
