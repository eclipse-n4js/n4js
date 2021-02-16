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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

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
	/** The control flow position of this node in context of the AST */
	final public int astPosition;

	/** Maps from a predecessor node to an {@link EdgeDescription} */
	final public Map<Node, EdgeDescription> internalPred = new HashMap<>();
	/** Maps from a successor node to an {@link EdgeDescription} */
	final public Map<Node, EdgeDescription> internalSucc = new HashMap<>();
	/** List of all preceding {@link ControlFlowEdge}s */
	final public TreeSet<ControlFlowEdge> pred = new TreeSet<>();
	/** List of all succeeding {@link ControlFlowEdge}s */
	final public TreeSet<ControlFlowEdge> succ = new TreeSet<>();
	/** List of all {@link JumpToken}s of this node */
	final public Set<JumpToken> jumpToken = new HashSet<>();
	/** List of all {@link CatchToken}s of this node */
	final public List<CatchToken> catchToken = new ArrayList<>();
	/** List of all {@link EffectInfo}s of this node */
	final public List<EffectInfo> effectInfos = new ArrayList<>();

	/** Set during graph traversal. */
	private Reachability reachability = Reachability.Unknown;
	/** Set to true during graph traversal. */
	private boolean isVisited = false;

	private enum Reachability {
		Unknown, Reachable, Unreachable
	}

	/**
	 * Constructor.<br/>
	 * Creates a node with the given name and {@link ControlFlowElement}.
	 */
	public Node(String name, int astPosition, ControlFlowElement cfElem) {
		this.name = name;
		this.astPosition = astPosition;
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
		checkState(node != this, "Self loops are not allowed");

		EdgeDescription sed = new EdgeDescription(node, cfType);
		internalSucc.put(sed.node, sed);
	}

	/** Removes an internal successor */
	public void removeInternalSuccessor(Node node) {
		internalSucc.remove(node);
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addSuccessor(ControlFlowEdge cfEdge) {
		boolean addSucceeded = succ.add(cfEdge);
		checkState(addSucceeded, "Adding an edge should always be successful");
	}

	/** Only called from {@link EdgeUtils}. Adds a successor edge. */
	void addPredecessor(ControlFlowEdge cfEdge) {
		boolean addSucceeded = pred.add(cfEdge);
		checkState(addSucceeded, "Adding an edge should always be successful");
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
	public Set<ControlFlowEdge> getSuccessorEdges() {
		return succ;
	}

	/** @return set of all predecessor edges. */
	public Set<ControlFlowEdge> getPredecessorEdges() {
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

	/** Adds {@link EffectInfo} to this node */
	public void addEffectInfo(EffectInfo ei) {
		if (ei != null) {
			effectInfos.add(ei);
		}
	}

	/** @return true, iff this node has at least one jump token. */
	public boolean isJump() {
		return !jumpToken.isEmpty();
	}

	/** Sets the reachability of this node to unreachable. Sets this node to be visited. */
	public void setUnreachable() {
		reachability = Reachability.Unreachable;
		isVisited = true;
	}

	/** Sets the reachability of this node to reachable. Sets this node to be visited. */
	public void setReachable() {
		reachability = Reachability.Reachable;
		isVisited = true;
	}

	/** @return true iff this node is not reachable */
	public boolean isVisited() {
		return isVisited;
	}

	/** @return true iff this node is not reachable */
	public boolean isUnreachable() {
		return reachability == Reachability.Unreachable;
	}

	/** @return true iff this node is not reachable */
	public boolean isReachable() {
		return reachability == Reachability.Reachable;
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

	/** @return a String that contains the node name and the {@link ControlFlowElement} */
	public String getExtendedString() {
		String s = "";
		s += "[" + FGUtils.getSourceText(getControlFlowElement()) + "]";
		s += "(" + getName() + ")";
		return s;
	}

	private class EdgeDescription {
		final ControlFlowType cfType;
		final Node node;

		EdgeDescription(Node node, ControlFlowType cfType) {
			this.node = node;
			this.cfType = cfType;
		}
	}

}
