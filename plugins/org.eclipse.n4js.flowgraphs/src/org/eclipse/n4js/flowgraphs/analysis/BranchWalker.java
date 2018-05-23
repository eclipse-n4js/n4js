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
	private Node lastRN;
	Set<ControlFlowType> pEdgeTypes = new HashSet<>();

	@Override
	final protected void visit(Node node) {
		if (node instanceof RepresentingNode) {
			lastRN = node;
			ControlFlowElement cfe = node.getRepresentedControlFlowElement();
			visit(cfe);
			return;
		}
		if (this.getContainer() == node.getControlFlowElement()) {
			ControlFlowElement cfe = node.getControlFlowElement();
			if (lastRN == null) {
				enterContainer(cfe);
			} else {
				exitContainer(cfe);
			}
			lastRN = node;
		}
	}

	@Override
	final protected void visit(Node start, Node end, ControlFlowEdge edge) {
		pEdgeTypes.add(edge.cfType);

		if (end instanceof RepresentingNode || this.getContainer() == end.getControlFlowElement()) {
			ControlFlowElement endCFE = end.getRepresentedControlFlowElement();
			endCFE = endCFE == null ? end.getControlFlowElement() : endCFE;

			if (lastRN != null) {
				ControlFlowElement startCFE = lastRN.getRepresentedControlFlowElement();
				startCFE = startCFE == null ? lastRN.getControlFlowElement() : startCFE;
				FlowEdge flowEdge = new FlowEdge(startCFE, endCFE, pEdgeTypes);
				visit(flowEdge);
				pEdgeTypes.clear();

			} else {
				HashSet<EdgeInfo> edgeInfos = new HashSet<>();
				addPredecedingRepNodes(this, edgeInfos, new EdgeInfo(this));
				for (EdgeInfo edgeInfo : edgeInfos) {
					ControlFlowElement startCFE = edgeInfo.startNode.getRepresentedControlFlowElement();
					startCFE = startCFE == null ? edgeInfo.startNode.getControlFlowElement() : startCFE;
					FlowEdge flowEdge = new FlowEdge(startCFE, endCFE, edgeInfo.pEdgeTypes);
					edgeInfo.edgeOwner.visit(flowEdge);
				}
				pEdgeTypes.clear();
			}
		}
	}

	static class EdgeInfo {
		Node startNode = null;
		boolean isDead = false;
		Set<ControlFlowType> pEdgeTypes = new HashSet<>();
		BranchWalker edgeOwner = null;

		EdgeInfo(BranchWalker bw) {
			update(bw);
		}

		EdgeInfo(EdgeInfo edgeInfo) {
			this.startNode = edgeInfo.startNode;
			this.isDead = edgeInfo.isDead;
			this.edgeOwner = edgeInfo.edgeOwner;
			this.pEdgeTypes.addAll(edgeInfo.pEdgeTypes);
		}

		EdgeInfo(EdgeInfo edgeInfo, RepresentingNode startNode) {
			this.startNode = startNode;
			this.isDead = edgeInfo.isDead;
			this.pEdgeTypes.addAll(edgeInfo.pEdgeTypes);
		}

		void update(BranchWalker bw) {
			pEdgeTypes.addAll(bw.pEdgeTypes);
			if (bw.pEdgeTypes.contains(ControlFlowType.DeadCode)) {
				isDead = true;
				if (edgeOwner == null) {
					edgeOwner = bw;
				}
			}
		}
	}

	static void addPredecedingRepNodes(BranchWalker bw, HashSet<EdgeInfo> edgeInfos, EdgeInfo incompleteInfo) {
		if (bw.lastRN != null) {
			incompleteInfo.startNode = bw.lastRN;
			if (incompleteInfo.edgeOwner == null) {
				incompleteInfo.edgeOwner = bw;
			}
			edgeInfos.add(incompleteInfo);
		} else {
			for (BranchWalkerInternal predBWI : bw.getPathPredecessors()) {
				BranchWalker predBW = (BranchWalker) predBWI;
				EdgeInfo incompleteInfoCpy = new EdgeInfo(incompleteInfo);
				boolean omitUpdate = !predBW.isDeadCodeBranch() && bw.isDeadCodeBranch();
				if (!omitUpdate) {
					incompleteInfoCpy.update(predBW);
				}
				addPredecedingRepNodes(predBW, edgeInfos, incompleteInfoCpy);
			}
		}
	}

	/**
	 * Called after {@link #initialize()} and before {@link #visit(ControlFlowElement)}.
	 *
	 * @param cfContainer
	 *            the control flow container that is entered
	 */
	protected void enterContainer(ControlFlowElement cfContainer) {
		// overwrite me
	}

	/**
	 * Called after {@link #visit(ControlFlowElement)} and before {@link #terminate()}.
	 *
	 * @param cfContainer
	 *            the control flow container that is exited
	 */
	protected void exitContainer(ControlFlowElement cfContainer) {
		// overwrite me
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
		BranchWalker forkedPath = forkPath();
		if (forkedPath != null) {
			forkedPath.lastRN = lastRN;
			forkedPath.pEdgeTypes.addAll(pEdgeTypes);
		}
		return forkedPath;
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
