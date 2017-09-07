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

/** Represents a complete {@link ControlFlowElement}. */
public class ComplexNode implements ControlFlowable {
	final private ControlFlowElement container;
	final private ControlFlowElement astElement;
	final private Map<String, Node> nodeMap = new HashMap<>();

	private Node entry, exit, represent;

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

		Iterator<Node> it = nodes.iterator();
		if (!it.hasNext()) {
			return;
		}

		Node n1 = it.next();
		while (it.hasNext()) {
			if (!nodeMap.values().contains(n1)) {
				throw new IllegalArgumentException("Node not child of complex node");
			}

			Node n2 = n1;
			n1 = it.next();
			n2.addInternalSuccessors(n1, cfType);
		}

		if (!nodeMap.values().contains(n1)) {
			throw new IllegalArgumentException("Node not child of complex node");
		}
	}

	/** Adds a node to this {@link ComplexNode}. */
	public void addNode(Node node) {
		if (node == null)
			return;
		nodeMap.put(node.name, node);
	}

	/** Sets the entry node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setEntryNode(Node entryNode) {
		if (!nodeMap.values().contains(entryNode))
			throw new IllegalArgumentException("Node not child of complex node");
		this.entry = entryNode;
	}

	/** Sets the exit node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setExitNode(Node exitNode) {
		if (!nodeMap.values().contains(exitNode))
			throw new IllegalArgumentException("Node not child of complex node");
		this.exit = exitNode;
	}

	/**
	 * Sets the representing node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before.
	 */
	public void setRepresentNode(Node representNode) {
		if (!nodeMap.values().contains(representNode))
			throw new IllegalArgumentException("Node not child of complex node");
		if (isControlElement())
			throw new IllegalArgumentException("Control elements do not have representing nodes");
		this.represent = representNode;
	}

	/** @returns the control flow container. See {@link FGUtils#isCFContainer(ControlFlowElement)}. */
	public ControlFlowElement getControlFlowContainer() {
		return CFEMapper.map(container);
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		return CFEMapper.map(astElement);
	}

	/** @returns all nodes */
	public Collection<Node> getNodes() {
		return nodeMap.values();
	}

	/** @returns a node for a given name */
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

	/** @returns {@link RepresentingNode} of the {@link ControlFlowElement} represented by this {@link ComplexNode}. */
	public Node getRepresent() {
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
	 * {@link FGUtils#isControlElement(ControlFlowElement)}
	 */
	public boolean isControlElement() {
		ControlFlowElement cfe = getControlFlowElement();
		return FGUtils.isControlElement(cfe);
	}

	/** @returns all {@link DependencyEdge}s that start or end at one of the nodes of this {@link ComplexNode}. */
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
