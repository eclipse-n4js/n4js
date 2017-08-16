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
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.EdgeUtils;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 *
 */
public class ControlFlowGraphFactory {
	static private Map<ControlFlowElement, ComplexNode> cnMap = new HashMap<>();

	/**
	 *
	 */
	static public FlowGraph build(Script script) {
		init();
		createComplexNodes(script);
		connectComplexNodes();

		FlowGraph cfg = new FlowGraph(cnMap);
		return cfg;
	}

	static private void init() {
		cnMap.clear();
	}

	static private void createComplexNodes(Script script) {
		TreeIterator<EObject> tit = script.eAllContents();
		while (tit.hasNext()) {
			EObject eObj = tit.next();
			if (eObj instanceof ControlFlowElement) {
				ControlFlowElement cfe = (ControlFlowElement) eObj;
				cfe = FactoryMapper.map(cfe);
				if (cfe != null && !cnMap.containsKey(cfe)) {
					ComplexNode cn = FactoryDispatcher.build(cfe);
					cnMap.put(cfe, cn);
				}
			}
		}
	}

	static private void connectComplexNodes() {
		for (ComplexNode cn : cnMap.values()) {
			for (Node mNode : cn.getAllButExitNodes()) {
				connectNode(mNode);
			}
		}
	}

	private static void connectNode(Node mNode) {
		Node internalStartNode = mNode;
		ControlFlowElement subASTElem = mNode.getDelegatedControlFlowElement();
		if (subASTElem != null) {
			if (cnMap.containsKey(subASTElem)) { // can be missing when the AST is incomplete
				ComplexNode subCN = cnMap.get(subASTElem);
				EdgeUtils.addEdgeCF(mNode, subCN.getEntry());
				internalStartNode = subCN.getExit();
			}
		}

		List<Node> internalSuccs = mNode.getInternalSuccessors();
		for (Node internalSucc : internalSuccs) {
			EdgeUtils.addEdgeCF(internalStartNode, internalSucc);
		}
	}

}
