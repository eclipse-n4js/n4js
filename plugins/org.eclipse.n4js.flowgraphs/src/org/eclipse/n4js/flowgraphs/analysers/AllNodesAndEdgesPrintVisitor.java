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
package org.eclipse.n4js.flowgraphs.analysers;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analysis.BranchWalker;
import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 * Finds all elements of a control flow graph of a given {@link Script}: Nodes (i.e. {@link ControlFlowElement}s) and
 * {@link ControlFlowEdge}s. If a {@link ControlFlowElement} called 'container' passed in the constructor is not null,
 * only those elements in this container are found.
 */
public class AllNodesAndEdgesPrintVisitor extends GraphVisitor {
	final Set<FlowEdge> allEdges = new HashSet<>();
	final List<ControlFlowElement> allNodes = new LinkedList<>();
	final Set<ControlFlowElement> allDeadNodesGV = new HashSet<>();
	final Set<ControlFlowElement> allDeadNodesBW = new HashSet<>();

	/**
	 * Constructor.
	 *
	 * @param container
	 *            if not null, only graph elements within (transitive) are found, otherwise all elements of the script
	 */
	public AllNodesAndEdgesPrintVisitor(ControlFlowElement container) {
		super(container, TraverseDirection.Forward);
	}

	@Override
	protected void initializeMode(TraverseDirection curMode, ControlFlowElement curContainer) {
		requestActivation(new AllNodesAndEdgesExplorer());
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		allNodes.add(cfe);
		if (isDeadCodeNode()) {
			allDeadNodesGV.add(cfe);
		}
	}

	@Override
	protected void terminateMode(TraverseDirection curMode, ControlFlowElement curContainer) {
		checkState(allDeadNodesGV.size() == allDeadNodesBW.size());
		checkState(allDeadNodesGV.containsAll(allDeadNodesBW));

		allDeadNodesGV.clear();
		allDeadNodesBW.clear();
	}

	class AllNodesAndEdgesExplorer extends GraphExplorer {

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new AllNodesAndEdgesBranchWalker();
		}

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new AllNodesAndEdgesBranchWalker();
		}

	}

	class AllNodesAndEdgesBranchWalker extends BranchWalker {

		@Override
		protected BranchWalker forkPath() {
			return new AllNodesAndEdgesBranchWalker();
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			if (isDeadCodeNode()) {
				allDeadNodesBW.add(cfe);
			}
		}

		@Override
		protected void visit(FlowEdge edge) {
			if (!edge.isDead()) {
				allEdges.add(edge);
			}
		}

	}

	/** @return all found {@link ControlFlowElement}s as Strings */
	public List<String> getAllNodeStrings() {
		List<String> nodeStrings = new LinkedList<>();
		for (ControlFlowElement node : allNodes) {
			nodeStrings.add(FGUtils.getSourceText(node));
		}
		return nodeStrings;
	}

	/** @return all found {@link ControlFlowEdge}s as Strings */
	public List<String> getAllEdgeStrings() {
		List<String> edgeStrings = new LinkedList<>();
		for (FlowEdge edge : allEdges) {
			edgeStrings.add(edge.toString());
		}
		return edgeStrings;
	}

}
