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
package org.eclipse.n4js.smith.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.model.ControlFlowType;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.graph.graph.CFEdge;
import org.eclipse.n4js.smith.graph.graph.Edge;
import org.eclipse.n4js.smith.graph.graph.GraphProvider;
import org.eclipse.n4js.smith.graph.graph.Node;
import org.eclipse.xtext.EcoreUtil2;

/**
 * The graph provider creates {@link Node}s and {@link CFEdge}s for a given {@link Script}. Moreover, it provides some
 * calls to the {@link N4JSFlowAnalyses} API by delegating to it.
 */
public class CFGraphProvider implements GraphProvider<Object, ControlFlowElement> {
	N4JSFlowAnalyses flowAnalyses = new N4JSFlowAnalyses();
	Map<ControlFlowElement, Node> nodeMap = new HashMap<>();
	Map<ControlFlowElement, List<Edge>> edgesMap = new HashMap<>();

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
		return edgesMap.get(cfe);
	}

	/**
	 * see {@link N4JSFlowAnalyses#isTransitiveSuccessor(ControlFlowElement, ControlFlowElement)}
	 */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return flowAnalyses.isTransitiveSuccessor(cfeFrom, cfeTo);
	}

	/**
	 * see {@link N4JSFlowAnalyses#getCommonPredecessor(ControlFlowElement, ControlFlowElement)}
	 */
	public ControlFlowElement getCommonPredecessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		return flowAnalyses.getCommonPredecessor(cfe1, cfe2);
	}

	/**
	 * see {@link N4JSFlowAnalyses#getPathIdentifier(ControlFlowElement, ControlFlowElement)}
	 */
	public String getPathIdentifier(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return flowAnalyses.getPathIdentifier(cfeFrom, cfeTo);
	}

	/** Triggers a control flow analyses and initialized the two maps {@link #nodeMap} and {@link #edgesMap}. */
	private void init(Object input) {
		nodeMap.clear();
		performFlowAnalyses(input);

		Collection<ControlFlowElement> allCFEs = flowAnalyses.getAllElements();
		for (ControlFlowElement cfe : allCFEs) {
			String label = FGUtils.getTextLabel(cfe);
			Node node = new Node(cfe, label, cfe.getClass().getSimpleName());
			nodeMap.put(cfe, node);
		}
		edgesMap.clear();
		for (ControlFlowElement cfe : allCFEs) {
			List<Edge> edges = new LinkedList<>();
			Collection<ControlFlowElement> succs = flowAnalyses.getSuccessors(cfe);
			Node sNode = nodeMap.get(cfe);

			for (ControlFlowElement succ : succs) {
				Node eNode = nodeMap.get(succ);
				TreeSet<ControlFlowType> cfTypes = flowAnalyses.getControlFlowTypeToSuccessors(cfe, succ);
				Edge edge = new CFEdge("CF", sNode, eNode, cfTypes);
				edges.add(edge);
			}
			edgesMap.put(cfe, edges);
		}
	}

	/** Finds a script for the given input and then triggers a control flow analyses. */
	private void performFlowAnalyses(Object input) {
		Script script = findScript(input);
		Objects.nonNull(script);
		flowAnalyses.perform(script);
	}

	/**
	 * Searches the script node in the given input.
	 */
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

}
