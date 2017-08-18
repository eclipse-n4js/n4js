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
package org.eclipse.n4js.flowgraphs.factories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.EdgeUtils;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 * Factory to build control flow graphs.
 */
public class ControlFlowGraphFactory {
	private final static boolean PRINT_EDGE_DETAILS = false;

	/**
	 * Builds and returns a control flow graph from a given {@link Script}.
	 */
	static public FlowGraph build(Script script) {
		Map<ControlFlowElement, ComplexNode> cnMap = new HashMap<>();
		createComplexNodes(cnMap, script);
		connectComplexNodes(cnMap);

		FlowGraph cfg = new FlowGraph(cnMap);
		return cfg;
	}

	static private void createComplexNodes(Map<ControlFlowElement, ComplexNode> cnMap, Script script) {
		TreeIterator<EObject> tit = script.eAllContents();
		while (tit.hasNext()) {
			EObject eObj = tit.next();
			if (eObj instanceof ControlFlowElement) {
				ControlFlowElement cfe = (ControlFlowElement) eObj;
				cfe = CFEMapper.map(cfe);
				if (cfe != null && !cnMap.containsKey(cfe)) {
					ComplexNode cn = FactoryDispatcher.build(cfe);
					cnMap.put(cfe, cn);
				}
			}
		}
	}

	static private void connectComplexNodes(Map<ControlFlowElement, ComplexNode> cnMap) {
		for (ComplexNode cn : cnMap.values()) {
			for (Node mNode : cn.getAllButExitNodes()) {
				connectNode(cnMap, mNode);
			}
		}
	}

	static private void connectNode(Map<ControlFlowElement, ComplexNode> cnMap, Node mNode) {
		Node internalStartNode = mNode;
		ControlFlowElement subASTElem = mNode.getDelegatedControlFlowElement();
		if (subASTElem != null) {
			if (cnMap.containsKey(subASTElem)) { // can be missing when the AST is incomplete
				ComplexNode subCN = cnMap.get(subASTElem);
				ControlFlowEdge e = EdgeUtils.addEdgeCF(mNode, subCN.getEntry());
				internalStartNode = subCN.getExit();
				if (PRINT_EDGE_DETAILS)
					printEdgeDetails(cnMap, e);
			}
		}

		List<Node> internalSuccs = mNode.getInternalSuccessors();
		for (Node internalSucc : internalSuccs) {
			ControlFlowEdge e = EdgeUtils.addEdgeCF(internalStartNode, internalSucc);
			if (PRINT_EDGE_DETAILS)
				printEdgeDetails(cnMap, e);
		}
	}

	/** Used for debugging purposes */
	private static void printEdgeDetails(Map<ControlFlowElement, ComplexNode> cnMap, ControlFlowEdge e) {
		ControlFlowElement sCFE = e.start.getControlFlowElement();
		ControlFlowElement eCFE = e.end.getControlFlowElement();
		sCFE = cnMap.get(sCFE).getControlFlowElement();
		eCFE = cnMap.get(eCFE).getControlFlowElement();

		String edgeStr = FGUtils.getClassName(sCFE) + ":" + e.start.name + ":" + FGUtils.getTextLabel(sCFE);
		edgeStr += " --> " + FGUtils.getClassName(eCFE) + ":" + e.end.name + ":" + FGUtils.getTextLabel(eCFE);
		System.out.println(edgeStr);
	}

}
