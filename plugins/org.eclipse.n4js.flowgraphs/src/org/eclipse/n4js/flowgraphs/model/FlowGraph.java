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

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 * Stores information about all control flow graphs of one {@link Script}.
 */
public class FlowGraph {
	final private TreeSet<ControlFlowElement> cfContainers;
	final private TreeSet<Block> cfCatchBlocks;
	final private Map<ControlFlowElement, ComplexNode> cnMap;
	final private Map<String, ControlFlowEdge> cfEdgeMap = new HashMap<>();

	/** Constructor. */
	public FlowGraph(TreeSet<ControlFlowElement> cfContainers, TreeSet<Block> cfCatchBlocks,
			Map<ControlFlowElement, ComplexNode> cnMap) {

		this.cfContainers = cfContainers;
		this.cfCatchBlocks = cfCatchBlocks;
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

	/** @return all {@link ComplexNode}s of the script. */
	public Collection<ComplexNode> getAllComplexNodes() {
		return cnMap.values();
	}

	/** @return all {@link ControlFlowEdge}s of the script. */
	public Collection<ControlFlowEdge> getAllControlFlowEdges() {
		return cfEdgeMap.values();
	}

	/** @return the {@link ComplexNode} for the given {@link ControlFlowElement} cfe. */
	public ComplexNode getComplexNode(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		if (!cnMap.containsKey(cfe))
			return null;
		return cnMap.get(cfe);
	}

	/** see {@link N4JSFlowAnalyzer#getContainer(ControlFlowElement)} */
	public ControlFlowElement getContainer(ControlFlowElement cfe) {
		ComplexNode cn = getComplexNode(cfe);
		return cn.getControlFlowContainer();
	}

	/** see {@link N4JSFlowAnalyzer#getAllContainers()} */
	public TreeSet<ControlFlowElement> getAllContainers() {
		return cfContainers;
	}

	/** @return all {@link Block}s whose containers are of type {@link CatchBlock} */
	public TreeSet<Block> getCatchBlocks() {
		return cfCatchBlocks;
	}

	/** see {@link N4JSFlowAnalyzer#getCatchBlocksOfContainer(ControlFlowElement)} */
	public List<Block> getCatchBlocksOfContainer(ControlFlowElement container) {
		List<Block> catchBlockOfContainer = new LinkedList<>();
		for (Block catchBlock : cfCatchBlocks) {
			ControlFlowElement cbContainer = getContainer(catchBlock);
			if (cbContainer == container) {
				catchBlockOfContainer.add(catchBlock);
			}
		}
		return catchBlockOfContainer;
	}

}
