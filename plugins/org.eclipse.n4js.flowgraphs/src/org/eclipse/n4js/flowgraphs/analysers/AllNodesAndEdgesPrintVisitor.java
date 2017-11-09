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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
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
	final List<ControlFlowElement> allLiveNodes = new LinkedList<>();
	final List<ControlFlowElement> allDeadNodes = new LinkedList<>();

	/**
	 * Constructor.
	 *
	 * @param container
	 *            if not null, only graph elements within (transitive) are found, otherwise all elements of the script
	 */
	public AllNodesAndEdgesPrintVisitor(ControlFlowElement container) {
		super(container, Mode.Forward);
	}

	@Override
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		requestActivation(new AllNodesAndEdgesExplorer());
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		allNodes.add(cfe);
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
		protected void visit(FlowEdge edge) {
			if (isLiveCode()) {
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
