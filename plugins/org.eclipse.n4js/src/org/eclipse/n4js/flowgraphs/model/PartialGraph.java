/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains not: GNodes and Symbols. The csp-Vars 'gn.asx' and 's.exs' need information about the whole constraint
 * problem; a partial problem does not suffice.
 *
 */
public class PartialGraph {
	final public Map<ComplexNode, ComplexNode> cnodeMapping = new HashMap<>();
	final public Map<Fragment, Fragment> fragmentMapping = new HashMap<>();
	final public Map<Node, Node> nodeMapping = new HashMap<>();
	final public Map<ControlFlowEdge, ControlFlowEdge> cfEdgeMapping = new HashMap<>();

	final public List<ComplexNode> cnodes = new LinkedList<>();
	final public List<Fragment> fragments = new LinkedList<>();
	final public List<Node> nodes = new LinkedList<>();
	final public List<DependencyEdge> depEdges = new LinkedList<>();
	final public EffectMap effectMap = new EffectMap();
	public Node startNode;
	public Node endNode;

	final public Map<Node, ComplexNode> nodeToCNode = new HashMap<>();
	final public Map<Node, Fragment> nodeToFrgmt = new HashMap<>();
	final public Map<Fragment, ComplexNode> frgmtToCNode = new HashMap<>();

	final public BlockAbstraction blockAbstraction;

	public PartialGraph(BlockAbstraction blockAbstraction) {
		this.blockAbstraction = blockAbstraction;
	}

	public void init() {

		// create ordered lists
		fragments.clear();
		nodes.clear();
		depEdges.clear();
		for (ComplexNode cn : cnodes) {
			fragments.addAll(cn.getFragments());
		}
		for (Fragment f : fragments) {
			nodes.addAll(f.getNodes());
		}
		for (Node n : nodes) {
			depEdges.addAll(n.startEdges);
		}

		// create maps
		nodeToCNode.clear();
		frgmtToCNode.clear();
		nodeToFrgmt.clear();
		for (ComplexNode cn : cnodes) {
			for (Node n : cn.getNodes())
				nodeToCNode.put(n, cn);
		}
		for (ComplexNode cn : cnodes) {
			for (Fragment f : cn.getFragments())
				frgmtToCNode.put(f, cn);
		}
		for (Fragment f : fragments) {
			for (Node n : f.getNodes())
				nodeToFrgmt.put(n, f);
		}
	}

}
