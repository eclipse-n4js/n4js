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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.factories.FactoryMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

abstract public class Node implements ControlFlowable {
	final private ControlFlowElement cfeElem;
	final public String name;
	final public List<Node> internalSucc = new LinkedList<>();
	final public int opPos;

	private final int nodePosition = -1;

	final public List<ControlFlowEdge> pred = new LinkedList<>();
	final public List<ControlFlowEdge> succ = new LinkedList<>();

	final public List<DependencyEdge> startEdges = new LinkedList<>();
	final public List<DependencyEdge> endEdges = new LinkedList<>();

	final public List<JumpToken> jumpToken = new ArrayList<>();
	final public List<CatchToken> catchToken = new ArrayList<>();

	public Node(String name, ControlFlowElement cfeElem, Node... internalSuccessors) {
		this(name, cfeElem, -1, internalSuccessors);
	}

	public Node(String name, ControlFlowElement cfeElem, int opPos, Node... internalSuccessors) {
		this.name = name;
		this.opPos = opPos;
		this.internalSucc.addAll(Arrays.asList(internalSuccessors));
		this.cfeElem = cfeElem;
	}

	public int getOperandPosition() {
		return opPos;
	}

	public void addInternalSuccessors(Node node) {
		internalSucc.add(node);
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

	public List<Node> getInternalSuccessors() {
		return internalSucc;
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
		return FactoryMapper.map(cfeElem);
	}

	abstract public ControlFlowElement getDelegatedControlFlowElement();

	abstract protected List<ControlFlowElement> getCFEOrSucceeding();

	abstract protected List<ControlFlowElement> getCFEOrPreceeding();

	public List<ControlFlowElement> getSuccessors() {
		List<ControlFlowElement> succCFEs = new LinkedList<>();
		for (ControlFlowEdge cfEdge : succ) {
			List<ControlFlowElement> cfes = cfEdge.end.getCFEOrSucceeding();
			succCFEs.addAll(cfes);
		}
		return succCFEs;
	}

	public List<ControlFlowElement> getPredecessors() {
		List<ControlFlowElement> succCFEs = new LinkedList<>();
		for (ControlFlowEdge cfEdge : pred) {
			List<ControlFlowElement> cfes = cfEdge.start.getCFEOrPreceeding();
			succCFEs.addAll(cfes);
		}
		return succCFEs;
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
}
