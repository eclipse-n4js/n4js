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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.flowgraphs.factories.ListUtils;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Represents a complete {@link ControlFlowElement}. {@link ComplexNode} represent the control flow that a
 * {@link ControlFlowElement} introduces. This is done using several {@link Node}s inside one {@link ComplexNode}. They
 * are connected to each other using {@link ControlFlowEdge}s. When two {@link ComplexNode}s are concatenated, the exit
 * {@link Node} of the first {@link ComplexNode} will be connected to the entry {@link Node} of the second
 * {@link ComplexNode}. In addition, nesting of {@link ComplexNode}s is supported using {@link DelegatingNode}s. They
 * are connected to {@link Node}s of other {@link ComplexNode}s.
 */
public class ComplexNode implements ControlFlowable {
	final private ControlFlowElement container;
	final private ControlFlowElement astElement;
	final private Map<String, Node> nodeMap = new HashMap<>();

	private Node entry, exit;
	private RepresentingNode represent;

	/** Constructor */
	public ComplexNode(ControlFlowElement astElement) {
		this.container = FGUtils.getCFContainer(astElement);
		this.astElement = astElement;
	}

	/**
	 * Adds the given nodes to the set of internal successors, in the order from left to right with the type
	 * {@literal ControlFlowType.Successor} Nulls are omitted. Only nodes that have been added to this
	 * {@link ComplexNode} are allowed.
	 */
	public void connectInternalSucc(Node... nodes) {
		connectInternalSucc(ControlFlowType.Successor, Arrays.asList(nodes));
	}

	/**
	 * Adds the given nodes to the set of internal successors, in the order from left to right with the type
	 * {@literal ControlFlowType.Successor} Nulls are omitted. Only nodes that have been added to this
	 * {@link ComplexNode} are allowed.
	 */
	public void connectInternalSucc(List<Node> nodes) {
		connectInternalSucc(ControlFlowType.Successor, nodes);
	}

	/**
	 * Adds the given nodes to the set of internal successors, in the order from left to right with the given
	 * {@link ControlFlowType}. Nulls are omitted. Only nodes that have been added to this {@link ComplexNode} are
	 * allowed.
	 */
	public void connectInternalSucc(ControlFlowType cfType, Node... nodes) {
		connectInternalSucc(cfType, Arrays.asList(nodes));
	}

	/**
	 * Adds the given nodes to the set of internal successors, in the order from left to right with the given
	 * {@link ControlFlowType}. Nulls are omitted. Only nodes that have been added to this {@link ComplexNode} are
	 * allowed.
	 */
	public void connectInternalSucc(ControlFlowType cfType, List<Node> nodes) {
		nodes = ListUtils.filterNulls(nodes);

		Iterator<Node> iter = nodes.iterator();
		if (!iter.hasNext()) {
			return;
		}

		Node n1 = iter.next();
		while (iter.hasNext()) {
			assert nodeMap.values().contains(n1) : "FlowGraph malformed: Node not child of complex node";

			Node n2 = n1;
			n1 = iter.next();
			n2.addInternalSuccessors(n1, cfType);
		}

		assert nodeMap.values().contains(n1) : "FlowGraph malformed: Node not child of complex node";
	}

	/** Adds a node to this {@link ComplexNode}. */
	public void addNode(Node node) {
		if (node == null) {
			return;
		}
		if (node instanceof RepresentingNode) {
			assert represent == null : "FlowGraph malformed: Only one RepresentingNode can be added";
			represent = (RepresentingNode) node;
		}
		nodeMap.put(node.name, node);
	}

	/** Sets the entry node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setEntryNode(Node entryNode) {
		assert nodeMap.values().contains(entryNode) : "FlowGraph malformed: Node not child of complex node";
		this.entry = entryNode;
	}

	/** Sets the exit node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setExitNode(Node exitNode) {
		assert nodeMap.values().contains(exitNode) : "FlowGraph malformed: Node not child of complex node";
		this.exit = exitNode;
	}

	/** @return the control flow container. See {@link FGUtils#isCFContainer(ControlFlowElement)}. */
	public ControlFlowElement getControlFlowContainer() {
		return CFEMapper.map(container);
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		return CFEMapper.map(astElement);
	}

	/** @return all nodes */
	public Collection<Node> getNodes() {
		return nodeMap.values();
	}

	/** @return a node for a given name */
	public Node getNode(String name) {
		return nodeMap.get(name);
	}

	@Override
	public Node getEntry() {
		return entry;
	}

	@Override
	public Node getExit() {
		return exit;
	}

	/** @return true iff the a representing node is available */
	public boolean hasRepresentingNode() {
		return represent != null;
	}

	/** @return {@link RepresentingNode} of the {@link ControlFlowElement} represented by this {@link ComplexNode}. */
	public RepresentingNode getRepresent() {
		return represent;
	}

	/**
	 * @return list of nodes not containing the exit node.
	 */
	public List<Node> getAllButExitNodes() {
		List<Node> mNodes = new LinkedList<>();
		mNodes.addAll(nodeMap.values());
		mNodes.remove(exit);
		return mNodes;
	}

	/**
	 * Returns true iff the represented {@link ControlFlowElement} is a control element. See
	 * {@link FGUtils#isControlStatement(ControlFlowElement)}
	 */
	public boolean isControlStatement() {
		ControlFlowElement cfe = getControlFlowElement();
		return FGUtils.isControlStatement(cfe);
	}

	/** @return all {@link DependencyEdge}s that start or end at one of the nodes of this {@link ComplexNode}. */
	@Deprecated
	public Set<DependencyEdge> getDependencyEdges() {
		Set<DependencyEdge> edges = new HashSet<>();
		for (Node n : getNodes()) {
			for (DependencyEdge e : n.startEdges)
				edges.add(e);
			for (DependencyEdge e : n.endEdges)
				edges.add(e);
		}
		return edges;
	}

	@Override
	public String toString() {
		return "CN[" + FGUtils.getClassName(astElement) + "]";
	}
}
