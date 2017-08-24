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
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ComplexNodeProvider;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.EdgeUtils;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
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
		CNProvider cnProvider = createComplexNodes(script);
		connectComplexNodes(cnProvider);
		createJumpEdges(cnProvider);

		FlowGraph cfg = new FlowGraph(cnProvider.getMap());
		return cfg;
	}

	static private CNProvider createComplexNodes(Script script) {
		Map<ControlFlowElement, ComplexNode> cnMap = new HashMap<>();
		TreeIterator<EObject> tit = script.eAllContents();
		while (tit.hasNext()) {
			EObject eObj = tit.next();
			if (eObj instanceof ControlFlowElement) {
				ControlFlowElement cfe = (ControlFlowElement) eObj;
				cfe = CFEMapper.map(cfe);
				if (cfe != null && !cnMap.containsKey(cfe)) {
					ComplexNode cn = CFEFactory.build(cfe);
					cnMap.put(cfe, cn);
				}
			}
		}
		CNProvider cnProvider = new CNProvider(cnMap);
		return cnProvider;
	}

	static private void connectComplexNodes(CNProvider cnProvider) {
		for (ComplexNode cn : cnProvider.getAll()) {
			for (Node mNode : cn.getAllButExitNodes()) {
				connectNode(cnProvider, mNode);
			}
		}
	}

	static private void connectNode(CNProvider cnProvider, Node mNode) {
		Node internalStartNode = mNode;
		ControlFlowElement subASTElem = mNode.getDelegatedControlFlowElement();
		if (subASTElem != null) {
			ComplexNode subCN = cnProvider.get(subASTElem);
			if (subCN != null) { // can be missing when the AST is incomplete
				ControlFlowEdge e = EdgeUtils.addEdgeCF(mNode, subCN.getEntry());
				internalStartNode = subCN.getExit();
				if (PRINT_EDGE_DETAILS)
					printEdgeDetails(e);
			}
		}

		Set<Node> internalSuccs = mNode.getInternalSuccessors();
		for (Node internalSucc : internalSuccs) {
			ControlFlowType cfType = mNode.getInternalSuccessorControlFlowType(internalSucc);
			ControlFlowEdge e = EdgeUtils.addEdgeCF(internalStartNode, internalSucc, cfType);
			if (PRINT_EDGE_DETAILS)
				printEdgeDetails(e);
		}
	}

	/**
	 * This methods searches for {@link ComplexNode}s that cause jumps. The outgoing edge is then replaced by a new jump
	 * edge that targets the catch node.
	 */
	private static void createJumpEdges(CNProvider cnProvider) {
		for (ComplexNode cn : cnProvider.getAll()) {
			Node cnJumpNode = cn.getExit();
			for (JumpToken jumpToken : cnJumpNode.jumpToken) {
				EdgeUtils.removeAll(cnJumpNode.getSuccessorEdges());
				Node catchNode = null;
				catchNode = CatchNodeFinder.find(jumpToken, cnJumpNode, cnProvider);
				if (catchNode == null) {
					String jumpTokenStr = getJumpTokenDetailString(jumpToken, cnJumpNode);
					System.err.println("Could not find catching node for jump token '" + jumpTokenStr + "'");
				} else {
					EdgeUtils.addEdgeCF(cnJumpNode, catchNode, jumpToken.cfType);
				}
			}
		}
	}

	private static class CNProvider implements ComplexNodeProvider {
		final private Map<ControlFlowElement, ComplexNode> cnMap;

		CNProvider(Map<ControlFlowElement, ComplexNode> cnMap) {
			this.cnMap = cnMap;
		}

		@Override
		public ComplexNode get(ControlFlowElement cfe) {
			return cnMap.get(CFEMapper.map(cfe));
		}

		@Override
		public Iterable<ComplexNode> getAll() {
			return cnMap.values();
		}

		Map<ControlFlowElement, ComplexNode> getMap() {
			return cnMap;
		}
	}

	/** Prints detailed information of jump nodes */
	private static String getJumpTokenDetailString(JumpToken jumpToken, Node jumpNode) {
		String jNode = ASTUtils.getNodeDetailString(jumpNode);
		String jmpStr = jNode + " >> " + jumpToken.toString();
		return jmpStr;
	}

	/** Prints detailed information of control flow edges. Used for debugging purposes */
	private static void printEdgeDetails(ControlFlowEdge e) {
		String sNode = ASTUtils.getNodeDetailString(e.start);
		String eNode = ASTUtils.getNodeDetailString(e.end);
		String edgeStr = sNode + ":" + e.toString() + ":" + eNode;
		System.out.println(edgeStr);
	}

}
