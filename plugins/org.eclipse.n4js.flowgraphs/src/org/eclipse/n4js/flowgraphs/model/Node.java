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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.ComparisonChain;

/**
 * Typically, several {@link Node}s are used to represent a {@link ControlFlowElement} within a {@link ComplexNode}.
 */
abstract public class Node implements ControlFlowable {
	static private int ID_COUNTER = 0;
	/** The node id */
	final public int id = ID_COUNTER++;
	/** The {@link ControlFlowElement} this node refers to */
	final private ControlFlowElement cfElem;
	/** Name of the node */
	final public String name;
	/** The control flow position of this node in context of its {@link ComplexNode} */
	final public int internalPosition;

	/** Maps from a predecessor node to an {@link EdgeDescription} */
	final public Map<Node, EdgeDescription> internalPred = new HashMap<>();
	/** Maps from a successor node to an {@link EdgeDescription} */
	final public Map<Node, EdgeDescription> internalSucc = new HashMap<>();
	/** List of all preceding {@link ControlFlowEdge}s */
	final public List<ControlFlowEdge> pred = new LinkedList<>();
	/** List of all succeeding {@link ControlFlowEdge}s */
	final public List<ControlFlowEdge> succ = new LinkedList<>();
	/** List of all {@link DependencyEdge}s starting at this node */
	final public List<DependencyEdge> startEdges = new LinkedList<>();
	/** List of all {@link DependencyEdge}s ending at this node */
	final public List<DependencyEdge> endEdges = new LinkedList<>();
	/** List of all {@link JumpToken}s of this node */
	final public List<JumpToken> jumpToken = new ArrayList<>();
	/** List of all {@link CatchToken}s of this node */
	final public List<CatchToken> catchToken = new ArrayList<>();

	/**
	 * Constructor.<br/>
	 * Creates a node with the given name and {@link ControlFlowElement}.
	 */
	public Node(String name, int internalPosition, ControlFlowElement cfElem) {
		this.name = name;
		this.internalPosition = internalPosition;
		this.cfElem = cfElem;
	}

	/** Returns the {@link ControlFlowElement} this node is delegating to. */
	abstract public ControlFlowElement getDelegatedControlFlowElement();

	/** Returns the {@link ControlFlowElement} this node is representing. */
	abstract public ControlFlowElement getRepresentedControlFlowElement();

	/**
	 * Adds an internal predecessor with edge type {@literal ControlFlowType.Successor} to this node. It used when the
	 * control flow graph is created.
	 */
	public void addInternalPrecessor(Node node) {
		addInternalPredecessor(node, ControlFlowType.Successor);
	}

	/**
	 * Adds an internal successor with edge type {@literal ControlFlowType.Successor} to this node. It used when the
	 * control flow graph is created.
	 */
	public void addInternalSuccessor(Node node) {
		addInternalSuccessor(node, ControlFlowType.Successor);
	}

	/**
	 * Adds an internal predecessor with the given edge type to this node. It used when the control flow graph is
	 * created.
	 */
	public void addInternalPredecessor(Node node, ControlFlowType cfType) {
		EdgeDescription sed = new EdgeDescription(node, cfType);
		internalPred.put(sed.node, sed);
	}

	/** Removes an internal predecessor */
	public void removeInternalPredecessor(Node node) {
		internalPred.remove(node);
	}

	/**
	 * Adds an internal successor with the given edge type to this node. It used when the control flow graph is created.
	 */
	public void addInternalSuccessor(Node node, ControlFlowType cfType) {
		EdgeDescription sed = new EdgeDescription(node, cfType);
		internalSucc.put(sed.node, sed);
	}

	/** Removes an internal successor */
	public void removeInternalSuccessor(Node node) {
		internalSucc.remove(node);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addSuccessor(ControlFlowEdge cfEdge) {
		succ.add(cfEdge);
		Collections.sort(succ, Node::compareNodes);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addPredecessor(ControlFlowEdge cfEdge) {
		pred.add(cfEdge);
		Collections.sort(pred, Node::compareNodes);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addOutgoingDependency(DependencyEdge depEdge) {
		startEdges.add(depEdge);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addIncomingDependency(DependencyEdge depEdge) {
		endEdges.add(depEdge);
	}

	/** @return set of all internal predecessors. */
	public Set<Node> getInternalPredecessors() {
		return internalPred.keySet();
	}

	/** @return set of all internal successors. */
	public Set<Node> getInternalSuccessors() {
		return internalSucc.keySet();
	}

	/** @return the {@link ControlFlowType} of a given internal successor. */
	public ControlFlowType getInternalSuccessorControlFlowType(Node endNode) {
		if (internalSucc.containsKey(endNode)) {
			EdgeDescription sed = internalSucc.get(endNode);
			return sed.cfType;
		}
		return null;
	}

	/** @return set of all successor edges. */
	public List<ControlFlowEdge> getSuccessorEdges() {
		return succ;
	}

	/** @return set of all predecessor edges. */
	public List<ControlFlowEdge> getPredecessorEdges() {
		return pred;
	}

	/** @return the given name. */
	public String getName() {
		return name;
	}

	/** Adds a {@link JumpToken} to this node. */
	public void addJumpToken(JumpToken jt) {
		jumpToken.add(jt);
	}

	/** Adds a {@link CatchToken} to this node. */
	public void addCatchToken(CatchToken ct) {
		catchToken.add(ct);
	}

	/** @return true, iff this node has at least one jump token. */
	public boolean isJump() {
		return !jumpToken.isEmpty();
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		return CFEMapper.map(cfElem);
	}

	@Override
	public Node getEntry() {
		return this;
	}

	@Override
	public Node getExit() {
		return this;
	}

	@Override
	public String toString() {
		return getName();
	}

	private class EdgeDescription {
		final ControlFlowType cfType;
		final Node node;

		EdgeDescription(Node node, ControlFlowType cfType) {
			this.node = node;
			this.cfType = cfType;
		}
	}

	private static int compareNodes(ControlFlowEdge e1, ControlFlowEdge e2) {
		int result = ComparisonChain.start()
				.compare(e1.start.id, e2.start.id)
				.compare(e1.end.id, e2.end.id)
				.compare(e1.cfType, e2.cfType)
				.compare(e1.start.cfElem.toString(), e2.start.cfElem.toString())
				.result();
		assert result != 0;
		return result;
	}
}
