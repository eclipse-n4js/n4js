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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/** see {@link BranchWalkerInternal} */
abstract public class BranchWalker extends BranchWalkerInternal {
	ControlFlowElement pLastCFE;
	Set<ControlFlowType> pEdgeTypes = new HashSet<>();

	@Override
	final protected void visit(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfe = node.getRepresentedControlFlowElement();

			if (pLastCFE == null) {
				for (BranchWalker predBW : getPredecessors()) {
					Set<ControlFlowType> pEdgeTypesCopy = predBW.pEdgeTypes;
					pEdgeTypesCopy.addAll(pEdgeTypes);
					FlowEdge edge = new FlowEdge(predBW.pLastCFE, cfe, pEdgeTypesCopy);
					visit(edge);
				}
			} else {
				FlowEdge edge = new FlowEdge(pLastCFE, cfe, pEdgeTypes);
				visit(edge);
				pEdgeTypes.clear();
			}

			visit(cfe);
			pLastCFE = cfe;
		}
	}

	@Override
	final protected void visit(Node start, Node end, List<ControlFlowEdge> edges) {
		if (edges.size() == 1) {
			pEdgeTypes.add(edges.get(0).cfType);
		}
	}

	/**
	 * Called for each node in direction of a path.
	 *
	 * @param cfe
	 *            {@link ControlFlowElement} that is visited
	 */
	protected void visit(ControlFlowElement cfe) {
		// overwrite me
	}

	/**
	 * Called for each edge in direction of a path. The edge direction is aligned to the traversing direction, i.e. the
	 * start node was traversed before the end node of the edge.
	 *
	 * @param edge
	 *            {@link FlowEdge} that is visited
	 */
	protected void visit(FlowEdge edge) {
		// overwrite me
	}

	/** see {@link BranchWalkerInternal#fork()} */
	abstract protected BranchWalker forkPath();

	@Override
	final protected BranchWalker fork() {
		BranchWalker ap2 = forkPath();
		ap2.pLastCFE = pLastCFE;
		ap2.pEdgeTypes.addAll(pEdgeTypes);
		return ap2;
	}

	/**
	 * returns a list of {@link BranchWalkerInternal}s which proceed this instance.
	 */
	@SuppressWarnings("unchecked")
	final public List<BranchWalker> getPredecessors() {
		return (List<BranchWalker>) (List<?>) getPathPredecessors();
	}

	/**
	 * returns a list of {@link BranchWalkerInternal}s which succeed this instance.
	 */
	@SuppressWarnings("unchecked")
	final public List<BranchWalker> getSuccessors() {
		return (List<BranchWalker>) (List<?>) getPathSuccessors();
	}

}
