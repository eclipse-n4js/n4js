/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.ui.graph.CFEdge;
import org.eclipse.n4js.smith.ui.graph.Edge;
import org.eclipse.n4js.smith.ui.graph.GraphProvider;
import org.eclipse.n4js.smith.ui.graph.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.EcoreUtil2;

/**
 * The graph provider creates {@link Node}s and {@link CFEdge}s for a given {@link Script}. Moreover, it provides some
 * calls to the {@link N4JSFlowAnalyzer} API by delegating to it.
 */
public class CFGraphProvider implements GraphProvider<Object, ControlFlowElement> {
	N4JSFlowAnalyzer flowAnalyzer = new N4JSFlowAnalyzer();
	Map<ControlFlowElement, Node> nodeMap = new HashMap<>();
	Map<ControlFlowElement, List<Edge>> edgesMap = new HashMap<>();
	final NodesEdgesCollector nodesEdgesCollector = new NodesEdgesCollector();

	@Override
	public Collection<ControlFlowElement> getElements(Object input) {
		init(input);

		return nodeMap.keySet();
	}

	@Override
	public Node getNode(ControlFlowElement element) {
		return nodeMap.get(element);
	}

	@Override
	public List<Edge> getConnectedEdges(Node node, List<Node> allNodes) {
		ControlFlowElement cfe = (ControlFlowElement) node.getElement();
		List<Edge> succs = edgesMap.get(cfe);
		if (succs == null) {
			return Collections.emptyList();
		}
		return succs;
	}

	/** @return a reference to {@link N4JSFlowAnalyzer} of the current {@link Script} */
	public N4JSFlowAnalyzer getFlowAnalyses() {
		return flowAnalyzer;
	}

	/** Triggers a control flow analyses and initialized the two maps {@link #nodeMap} and {@link #edgesMap}. */
	private void init(Object input) {
		performFlowAnalyses(input);
		flowAnalyzer.accept(nodesEdgesCollector);
	}

	/** Finds a script for the given input and then triggers a control flow analyses. */
	private void performFlowAnalyses(Object input) {
		Script script = findScript(input);
		Objects.nonNull(script);
		flowAnalyzer.createGraphs(script);
	}

	/** Searches the script node in the given input. */
	private Script findScript(Object input) {
		if (input instanceof ResourceSet) {
			ResourceSet rs = (ResourceSet) input;
			if (!rs.getResources().isEmpty()) {
				Resource res = rs.getResources().get(0);
				EList<EObject> contents = res.getContents();
				if (!contents.isEmpty()) {
					Script script = EcoreUtil2.getContainerOfType(contents.get(0), Script.class);
					return script;
				}
			}
		}
		return null;
	}

	private class NodesEdgesCollector extends GraphVisitor {

		NodesEdgesCollector() {
			super(Mode.Forward);
		}

		@Override
		protected void initialize() {
			nodeMap.clear();
			edgesMap.clear();
		}

		@Override
		protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
			requestActivation(new EdgesExplorer());
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			addNode(cfe, isDeadCodeNode());
		}

		private void addNode(ControlFlowElement cfe, boolean isDeadCode) {
			if (!nodeMap.containsKey(cfe)) {
				String label = FGUtils.getSourceText(cfe);
				String description = cfe.getClass().getSimpleName();
				Node node;
				if (isDeadCode) {
					Display displ = Display.getCurrent();
					Color grey = displ.getSystemColor(SWT.COLOR_GRAY);
					node = new Node(cfe, label, description, grey);
				} else {
					node = new Node(cfe, label, description);
				}
				nodeMap.put(cfe, node);
			}
		}

		class EdgesExplorer extends GraphExplorer {

			@Override
			protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
				return new EdgesBranchWalker();
			}

			@Override
			protected BranchWalkerInternal firstBranchWalker() {
				return new EdgesBranchWalker();
			}

		}

		class EdgesBranchWalker extends BranchWalker {

			@Override
			protected BranchWalker forkPath() {
				return new EdgesBranchWalker();
			}

			@Override
			protected void visit(FlowEdge edge) {
				addNode(edge.start, isDeadCodeNode());
				addNode(edge.end, isDeadCodeNode());
				Node sNode = nodeMap.get(edge.start);
				Node eNode = nodeMap.get(edge.end);
				CFEdge cfEdge = new CFEdge("CF", sNode, eNode, edge.cfTypes, isDeadCodeNode());

				if (!edgesMap.containsKey(edge.start)) {
					edgesMap.put(edge.start, new LinkedList<>());
				}
				List<Edge> cfEdges = edgesMap.get(edge.start);
				cfEdges.add(cfEdge);

				removeDuplicatedDeadEdge(eNode, cfEdge, cfEdges);
			}

			private void removeDuplicatedDeadEdge(Node eNode, CFEdge cfEdge, List<Edge> cfEdges) {
				CFEdge removeEdge = null;
				for (Edge e : cfEdges) {
					if (cfEdge != e && e.getEndNodes().get(0) == eNode) {
						removeEdge = (CFEdge) e;
						break;
					}
				}
				if (removeEdge != null) {
					if (removeEdge.isDead) {
						cfEdges.remove(removeEdge);
					} else {
						cfEdges.remove(cfEdge);
					}
				}
			}

		}
	}
}
