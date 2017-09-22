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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Typically, several {@link Node}s are used to represent a {@link ControlFlowElement} within a
 * {@link ComplexNode}. 
 */
abstract public class Node implements ControlFlowable {
	final private ControlFlowElement cfElem;
	/** Name of the node */
	final public String name;

	/** Maps from a successor node to an {@link SuccessorEdgeDescription} */
	final public Map<Node, SuccessorEdgeDescription> internalSucc = new HashMap<>();
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
	public Node(String name, ControlFlowElement cfElem) {
		this.name = name;
		this.cfElem = cfElem;
	}

	/** Returns the {@link ControlFlowElement} this node is delegating to. */
	abstract public ControlFlowElement getDelegatedControlFlowElement();

	/** Returns the {@link ControlFlowElement} this node is representing. */
	abstract public ControlFlowElement getRepresentedControlFlowElement();

	/**
	 * Adds an internal successor with edge type {@literal ControlFlowType.Successor} to this node. It used when the
	 * control flow graph is created.
	 */
	public void addInternalSuccessors(Node node) {
		addInternalSuccessors(node, ControlFlowType.Successor);
	}

	/**
	 * Adds an internal successor with the given edge type to this node. It used when the control flow graph is created.
	 */
	public void addInternalSuccessors(Node node, ControlFlowType cfType) {
		SuccessorEdgeDescription sed = new SuccessorEdgeDescription(node, cfType);
		internalSucc.put(sed.endNode, sed);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addSuccessor(ControlFlowEdge cfEdge) {
		succ.add(cfEdge);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addPredecessor(ControlFlowEdge cfEdge) {
		pred.add(cfEdge);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addOutgoingDependency(DependencyEdge depEdge) {
		startEdges.add(depEdge);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addIncomingDependency(DependencyEdge depEdge) {
		endEdges.add(depEdge);
	}

	/** @return set of all internal successors. */
	public Set<Node> getInternalSuccessors() {
		return internalSucc.keySet();
	}

	/** @return the {@link ControlFlowType} of a given internal successor. */
	public ControlFlowType getInternalSuccessorControlFlowType(Node endNode) {
		if (internalSucc.containsKey(endNode)) {
			SuccessorEdgeDescription sed = internalSucc.get(endNode);
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

	private class SuccessorEdgeDescription {
		final ControlFlowType cfType;
		final Node endNode;

		SuccessorEdgeDescription(Node endNode, ControlFlowType cfType) {
			this.endNode = endNode;
			this.cfType = cfType;
		}
	}

}
