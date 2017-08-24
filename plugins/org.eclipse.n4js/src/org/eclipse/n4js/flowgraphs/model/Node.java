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

abstract public class Node implements ControlFlowable {
	final private ControlFlowElement cfeElem;
	final public String name;
	final public Map<Node, SuccessorEdgeDescription> internalSucc = new HashMap<>();

	final public List<ControlFlowEdge> pred = new LinkedList<>();
	final public List<ControlFlowEdge> succ = new LinkedList<>();

	final public List<DependencyEdge> startEdges = new LinkedList<>();
	final public List<DependencyEdge> endEdges = new LinkedList<>();

	final public List<JumpToken> jumpToken = new ArrayList<>();
	final public List<CatchToken> catchToken = new ArrayList<>();

	public Node(String name, ControlFlowElement cfeElem) {
		this.name = name;
		this.cfeElem = cfeElem;
	}

	public void addInternalSuccessors(Node node) {
		addInternalSuccessors(node, ControlFlowType.Successor);
	}

	public void addInternalSuccessors(Node node, ControlFlowType cfType) {
		SuccessorEdgeDescription sed = new SuccessorEdgeDescription(node, cfType);
		internalSucc.put(sed.endNode, sed);
	}

	public void addSuccessor(ControlFlowEdge cfEdge) {
		succ.add(cfEdge);
	}

	public void addPredecessor(ControlFlowEdge cfEdge) {
		pred.add(cfEdge);
	}

	public void addOutgoingDependency(DependencyEdge depEdge) {
		startEdges.add(depEdge);
	}

	public void addIncomingDependency(DependencyEdge depEdge) {
		endEdges.add(depEdge);
	}

	public Set<Node> getInternalSuccessors() {
		return internalSucc.keySet();
	}

	public ControlFlowType getInternalSuccessorControlFlowType(Node endNode) {
		if (internalSucc.containsKey(endNode)) {
			SuccessorEdgeDescription sed = internalSucc.get(endNode);
			return sed.cfType;
		}
		return null;
	}

	public List<ControlFlowEdge> getSuccessorEdges() {
		return succ;
	}

	public List<ControlFlowEdge> getPredecessorEdges() {
		return pred;
	}

	public String getName() {
		return toString();
	}

	public void addJumpToken(JumpToken jt) {
		jumpToken.add(jt);
	}

	public void addCatchToken(CatchToken ct) {
		catchToken.add(ct);
	}

	public boolean isJump() {
		return !jumpToken.isEmpty();
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		if (cfeElem == null)
			return null; // can be missing when the AST is incomplete
		return CFEMapper.map(cfeElem);
	}

	abstract public ControlFlowElement getDelegatedControlFlowElement();

	abstract public ControlFlowElement getRepresentedControlFlowElement();

	abstract protected List<RepresentingNode> getRepresentingOrSucceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges);

	abstract protected List<RepresentingNode> getRepresentingOrPreceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges);

	public List<RepresentingNode> getSuccessors(ControlFlowType... followEdges) {
		return getSuccessors(new LinkedList<>(), followEdges);
	}

	protected List<RepresentingNode> getSuccessors(List<ControlFlowEdge> loopEdges, ControlFlowType... followEdges) {
		List<RepresentingNode> succCFEs = new LinkedList<>();
		for (ControlFlowEdge cfEdge : succ) {
			if (cfEdge.cfType.isInOrEmpty(followEdges) && !loopEdges.contains(cfEdge)) {
				loopEdges.add(cfEdge);
				List<RepresentingNode> cfes = cfEdge.end.getRepresentingOrSucceeding(loopEdges, followEdges);
				succCFEs.addAll(cfes);
			}
		}
		return succCFEs;
	}

	public List<RepresentingNode> getPredecessors(ControlFlowType... followEdges) {
		return getPredecessors(new LinkedList<>(), followEdges);
	}

	protected List<RepresentingNode> getPredecessors(List<ControlFlowEdge> loopEdges, ControlFlowType... followEdges) {
		List<RepresentingNode> predCFEs = new LinkedList<>();
		for (ControlFlowEdge cfEdge : pred) {
			if (cfEdge.cfType.isInOrEmpty(followEdges) && !loopEdges.contains(cfEdge)) {
				loopEdges.add(cfEdge);
				List<RepresentingNode> cfes = cfEdge.start.getRepresentingOrPreceeding(loopEdges, followEdges);
				predCFEs.addAll(cfes);
			}
		}
		return predCFEs;
	}

	public boolean isTransitiveSuccessor(ControlFlowElement cfeTo, List<ControlFlowEdge> loopEdges) {
		if (getRepresentedControlFlowElement() == cfeTo) {
			return true;
		}

		List<RepresentingNode> succNodes = getSuccessors(loopEdges);
		for (Node succNode : succNodes) {
			if (succNode.isTransitiveSuccessor(cfeTo, loopEdges)) {
				return true;
			}
		}
		return false;
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
		return name;
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
