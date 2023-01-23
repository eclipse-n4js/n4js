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

import static com.google.common.base.Preconditions.checkState;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.flowgraphs.factories.ListUtils;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * {@link ComplexNode}s are used only to create the internal graph. They represent a complete
 * {@link ControlFlowElement}. {@link ComplexNode} hold information about the control flow that a
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

	private Node entry, exit, jump;
	private RepresentingNode represent;

	/** Constructor */
	public ComplexNode(ControlFlowElement container, ControlFlowElement astElement) {
		this.container = CFEMapper.map(container);
		this.astElement = CFEMapper.map(astElement);
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

		Node nNext = iter.next();
		while (iter.hasNext()) {
			checkState(nodeMap.values().contains(nNext), "FlowGraph malformed: Node not child of complex node");

			Node nLast = nNext;
			nNext = iter.next();
			nLast.addInternalSuccessor(nNext, cfType);
			nNext.addInternalPredecessor(nLast, cfType);
		}

		checkState(nodeMap.values().contains(nNext), "FlowGraph malformed: Node not child of complex node");
	}

	/** Adds a node to this {@link ComplexNode}. */
	public void addNode(Node node) {
		if (node == null) {
			return;
		}
		if (node instanceof RepresentingNode) {
			checkState(represent == null, "FlowGraph malformed: Only one RepresentingNode can be added");

			represent = (RepresentingNode) node;
		}
		nodeMap.put(node.name, node);
	}

	/** Removes a node from this instance */
	public void removeNode(Node node) {
		removeNodeChecks(node);
		nodeMap.remove(node.name);
	}

	/** Checks invoked before a node from this instance is removed */
	public void removeNodeChecks(Node node) {
		checkState(entry != node, "FlowGraph malformed: Node not child of complex node");
		checkState(exit != node, "FlowGraph malformed: Node not child of complex node");
		checkState(represent != node, "FlowGraph malformed: Node not child of complex node");
	}

	/** Sets the entry node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setEntryNode(Node entryNode) {
		checkState(nodeMap.values().contains(entryNode), "FlowGraph malformed: Node not child of complex node");

		this.entry = entryNode;
	}

	/** Sets the exit node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setExitNode(Node exitNode) {
		checkState(nodeMap.values().contains(exitNode), "FlowGraph malformed: Node not child of complex node");

		this.exit = exitNode;
	}

	/** Sets the jump node of this {@link ComplexNode}. Must have been added to this {@link ComplexNode} before. */
	public void setJumpNode(Node jumpNode) {
		checkState(nodeMap.values().contains(jumpNode), "FlowGraph malformed: Node not child of complex node");
		checkState(!jumpNode.jumpToken.isEmpty(), "Jump nodes must provide jump tokens");

		this.jump = jumpNode;
	}

	/** @return the control flow container. See {@link FGUtils#isCFContainer(EObject)}. */
	public ControlFlowElement getControlFlowContainer() {
		return container;
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		return astElement;
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

	/** @return a node with a {@link JumpToken} or null */
	public Node getJump() {
		return jump;
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

	@Override
	public String toString() {
		return "CN[" + FGUtils.getClassName(astElement) + "]";
	}
}
