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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Contains algorithms to reason about successor and predecessor relationships between two {@link ControlFlowElement}s.
 */
public class SuccessorPredecessorAnalysis {
	private final FlowGraph cfg;

	/** Constructor */
	public SuccessorPredecessorAnalysis(FlowGraph cfg) {
		this.cfg = cfg;
	}

	/** see {@link N4JSFlowAnalyser#getPredecessors(ControlFlowElement)} */
	public Set<ControlFlowElement> getPredecessors(ControlFlowElement cfe) {
		NextEdgesProvider nextEdgesProvider = new NextEdgesProvider.Backward();
		Node nextNode = getNextNode(cfe, false, nextEdgesProvider);
		Set<ControlFlowElement> predecessors = getNextCFEs(nextEdgesProvider, cfe, nextNode);
		return predecessors;
	}

	/** see {@link N4JSFlowAnalyser#getPredecessorsSkipInternal(ControlFlowElement)} */
	public Set<ControlFlowElement> getPredecessorsSkipInternal(ControlFlowElement cfe) {
		NextEdgesProvider nextEdgesProvider = new NextEdgesProvider.Backward();
		Node nextNode = getNextNode(cfe, true, nextEdgesProvider);
		Set<ControlFlowElement> predecessors = getNextCFEs(nextEdgesProvider, cfe, nextNode);
		return predecessors;
	}

	/** see {@link N4JSFlowAnalyser#getSuccessors(ControlFlowElement)} */
	public Set<ControlFlowElement> getSuccessors(ControlFlowElement cfe) {
		NextEdgesProvider nextEdgesProvider = new NextEdgesProvider.Forward();
		Node nextNode = getNextNode(cfe, false, nextEdgesProvider);
		Set<ControlFlowElement> successors = getNextCFEs(nextEdgesProvider, cfe, nextNode);
		return successors;
	}

	/** see {@link N4JSFlowAnalyser#getSuccessorsSkipInternal(ControlFlowElement)} */
	public Set<ControlFlowElement> getSuccessorsSkipInternal(ControlFlowElement cfe) {
		NextEdgesProvider nextEdgesProvider = new NextEdgesProvider.Forward();
		Node nextNode = getNextNode(cfe, true, nextEdgesProvider);
		Set<ControlFlowElement> successors = getNextCFEs(nextEdgesProvider, cfe, nextNode);
		return successors;
	}

	/**
	 * @return the next node which is either the start of the {@link ControlFlowElement}, or its end iff
	 *         {@code skipInternal} is true.
	 */
	private Node getNextNode(ControlFlowElement cfe, boolean skipInternal, NextEdgesProvider nextEdgesProvider) {
		ComplexNode cn = cfg.getComplexNode(cfe);
		if (skipInternal && FGUtils.isControlStatement(cfe)) {
			return nextEdgesProvider.getEndNode(cn);
		} else {
			if (cn.hasRepresentingNode()) {
				return cn.getRepresent();
			} else {
				return nextEdgesProvider.getStartNode(cn);
			}
		}
	}

	/** @return the set of all next {@link ControlFlowElement} */
	private Set<ControlFlowElement> getNextCFEs(NextEdgesProvider nextEdgesProvider, ControlFlowElement cfe,
			Node nextNode) {

		Objects.requireNonNull(cfe);
		Set<ControlFlowElement> nexts = new HashSet<>();

		LinkedList<ControlFlowEdge> allEdges = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = nextEdgesProvider.getNextEdges(nextNode, ControlFlowType.NonDeadTypes);
		allEdges.addAll(nextEdges);

		while (!allEdges.isEmpty()) {
			ControlFlowEdge nextEdge = allEdges.removeFirst();
			nextNode = nextEdgesProvider.getNextNode(nextEdge);
			if (nextNode instanceof RepresentingNode) {
				ControlFlowElement succ = nextNode.getRepresentedControlFlowElement();
				nexts.add(succ);
			} else {
				nextEdges = nextEdgesProvider.getNextEdges(nextNode, ControlFlowType.NonDeadTypes);
				allEdges.addAll(nextEdges);
			}
		}

		return nexts;
	}

	/** @return true iff cfe2 is a direct successor of cfe1 */
	public boolean isSuccessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		Set<ControlFlowElement> succs = getSuccessors(cfe1);
		return succs.contains(cfe2);
	}

	/** @return true iff cfe2 is a direct predecessor of cfe1 */
	public boolean isPredecessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		Set<ControlFlowElement> preds = getPredecessors(cfe1);
		return preds.contains(cfe2);
	}

}
