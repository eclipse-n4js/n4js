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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.EdgeUtils;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.Script;

/**
 * Factory to build the internal control flow graphs.
 */
public class ControlFlowGraphFactory {
	/** Prints out the {@link ControlFlowEdge}s of the internal graph */
	private final static boolean PRINT_EDGE_DETAILS = false;

	/** Builds and returns a control flow graph from a given {@link Script}. */
	static public FlowGraph build(Script script) {
		Map<ControlFlowElement, List<ControlFlowElement>> cfContainers = new HashMap<>();
		Set<Block> cfCatchBlocks = new HashSet<>();
		Map<ControlFlowElement, ComplexNode> cnMap = new HashMap<>();

		createComplexNodes(script, cfContainers, cfCatchBlocks, cnMap);
		ComplexNodeMapper cnMapper = new ComplexNodeMapper(cnMap);

		connectComplexNodes(cnMapper);
		createJumpEdges(cnMapper);

		FlowGraph cfg = new FlowGraph(script, cfContainers, cfCatchBlocks, cnMap);

		if (PRINT_EDGE_DETAILS)
			printAllEdgeDetails(cnMapper);

		return cfg;
	}

	/** Creates {@link ComplexNode}s for every {@link ControlFlowElement}. */
	static private void createComplexNodes(Script script,
			Map<ControlFlowElement, List<ControlFlowElement>> cfContainers,
			Set<Block> cfCatchBlocks, Map<ControlFlowElement, ComplexNode> cnMap) {

		ComplexNode cn = CFEFactoryDispatcher.build(script);
		cnMap.put(script, cn);

		TreeIterator<EObject> tit = script.eAllContents();
		while (tit.hasNext()) {
			EObject eObj = tit.next();
			if (eObj instanceof ControlFlowElement) {
				ControlFlowElement cfe = (ControlFlowElement) eObj;
				cfe = CFEMapper.map(cfe);

				if (cfe != null && !cnMap.containsKey(cfe)) {
					addToContainer(cfContainers, cfe);

					Block cfCatchBlock = FGUtils.getCatchBlock(cfe);
					if (cfCatchBlock != null) {
						cfCatchBlocks.add(cfCatchBlock);
					}
					cn = CFEFactoryDispatcher.build(cfe);
					if (cn != null) {
						cnMap.put(cfe, cn);
					}
				}
			}
		}
	}

	private static void addToContainer(Map<ControlFlowElement, List<ControlFlowElement>> cfContainers,
			ControlFlowElement cfe) {

		ControlFlowElement cfContainer = FGUtils.getCFContainer(cfe);
		if (!cfContainers.containsKey(cfContainer)) {
			cfContainers.put(cfContainer, new LinkedList<ControlFlowElement>());
		}
		List<ControlFlowElement> containerList = cfContainers.get(cfContainer);
		containerList.add(cfe);
	}

	static private void connectComplexNodes(ComplexNodeMapper cnMapper) {
		for (ComplexNode cn : cnMapper.getAll()) {
			List<Node> removeNodes = new LinkedList<>();
			for (Node mNode : cn.getNodes()) {
				if (mNode != cn.getExit()) {
					connectNode(cnMapper, mNode);
				}
			}
			for (Node mNode : cn.getNodes()) {
				if (isRemovableNode(mNode)) {
					removeNodes.add(mNode);
				}
			}
			for (Node removeNode : removeNodes) {
				removeNode(cn, removeNode);
			}
		}
	}

	/**
	 * Connects all nodes based on
	 * <ul>
	 * <li/>the delegating nodes, and
	 * <li/>the internal successor information of each node.
	 * </ul>
	 */
	static private void connectNode(ComplexNodeMapper cnMapper, Node mNode) {
		Node internalStartNode = mNode;
		ControlFlowElement subASTElem = mNode.getDelegatedControlFlowElement();
		if (subASTElem != null) {
			ComplexNode subCN = cnMapper.get(subASTElem);
			if (subCN != null) { // can be null in case of malformed AST
				EdgeUtils.connectCF(mNode, subCN.getEntry());
				internalStartNode = subCN.getExit();
			}
		}

		Set<Node> internalSuccs = mNode.getInternalSuccessors();
		for (Node internalSucc : internalSuccs) {
			ControlFlowType cfType = mNode.getInternalSuccessorControlFlowType(internalSucc);
			EdgeUtils.connectCF(internalStartNode, internalSucc, cfType);
		}
	}

	private static boolean isRemovableNode(Node mNode) {
		boolean remDel = true;
		remDel = remDel && mNode instanceof DelegatingNode;
		remDel = remDel && !(mNode instanceof RepresentingNode);
		remDel = remDel && mNode.jumpToken.isEmpty();
		remDel = remDel && mNode.catchToken.isEmpty();
		remDel = remDel && mNode.getInternalPredecessors().size() == 1;
		remDel = remDel && mNode.pred.size() == 1;
		remDel = remDel && mNode.succ.size() == 1;
		remDel = remDel && mNode.pred.first().cfType == ControlFlowType.Successor;
		remDel = remDel && mNode.succ.first().cfType == ControlFlowType.Successor;
		return remDel;
	}

	private static void removeNode(ComplexNode cn, Node mNode) {
		ControlFlowEdge e1 = mNode.pred.first();
		ControlFlowEdge e2 = mNode.succ.first();
		Node pred = e1.start;
		Node succ = e2.end;

		EdgeUtils.removeCF(e1);
		EdgeUtils.removeCF(e2);
		cn.removeNodeChecks(mNode);
		cn.removeNode(mNode);

		for (Node intPred : mNode.getInternalPredecessors()) {
			intPred.removeInternalSuccessor(mNode);
		}
		for (Node intSucc : mNode.getInternalSuccessors()) {
			intSucc.removeInternalPredecessor(mNode);
		}
		mNode.getInternalPredecessors().clear();
		mNode.getInternalSuccessors().clear();

		EdgeUtils.connectCF(pred, succ);
	}

	/**
	 * This methods searches for {@link ComplexNode}s that cause jumps. The outgoing edge is then replaced by a new jump
	 * edge that targets the catch node.
	 */
	private static void createJumpEdges(ComplexNodeMapper cnMapper) {
		for (ComplexNode cn : cnMapper.getAll()) {
			Node jumpNode = cn.getExit();
			for (JumpToken jumpToken : jumpNode.jumpToken) {
				replaceWithDeadCodeEdges(jumpNode.getSuccessorEdges());
				connectToJumpTarget(cnMapper, jumpNode, jumpToken);
			}
		}
	}

	private static void replaceWithDeadCodeEdges(Set<ControlFlowEdge> replacedEdges) {
		for (ControlFlowEdge rEdge : replacedEdges) {
			assert rEdge.finallyPathContext == null;
			EdgeUtils.removeCF(rEdge);
			EdgeUtils.connectCF(rEdge.start, rEdge.end, ControlFlowType.DeadCode);
		}
	}

	private static void connectToJumpTarget(ComplexNodeMapper cnMapper, Node jumpNode, JumpToken jumpToken) {
		Node catchNode = null;
		catchNode = CatchNodeFinder.find(jumpToken, jumpNode, cnMapper);
		if (catchNode == null) {
			String jumpTokenStr = getJumpTokenDetailString(jumpToken, jumpNode);
			System.err.println("Could not find catching node for jump token '" + jumpTokenStr + "'");
			return;
		}

		FinallyBlock enteringFinallyBlock = getEnteringFinallyBlock(catchNode);
		boolean isExitingFinallyBlock = isExitingFinallyBlock(cnMapper, jumpNode);
		if (enteringFinallyBlock != null || isExitingFinallyBlock) {
			EdgeUtils.connectCF(jumpNode, catchNode, jumpToken);
		} else {
			EdgeUtils.connectCF(jumpNode, catchNode, jumpToken.cfType);
		}

		if (enteringFinallyBlock != null) {
			// Iff finally block was entered abruptly, jump on from exit of finally block
			Block block = enteringFinallyBlock.getBlock();
			ComplexNode cnBlock = cnMapper.get(block);
			Node exitFinallyBlock = cnBlock.getExit();
			connectToJumpTarget(cnMapper, exitFinallyBlock, jumpToken);
		}
	}

	private static FinallyBlock getEnteringFinallyBlock(Node catchNode) {
		if (catchNode.name.equals(TryFactory.FINALLY_NODE_NAME)) {
			ControlFlowElement cfe = catchNode.getDelegatedControlFlowElement();
			EObject cfeContainer = cfe.eContainer();
			return (FinallyBlock) cfeContainer;
		}
		return null;
	}

	private static boolean isExitingFinallyBlock(ComplexNodeMapper cnMapper, Node node) {
		ControlFlowElement cfe = node.getControlFlowElement();
		ComplexNode cn = cnMapper.get(cfe);
		boolean isExitingFinallyBlock = true;
		isExitingFinallyBlock &= cfe instanceof Block;
		isExitingFinallyBlock &= cfe.eContainer() instanceof FinallyBlock;
		isExitingFinallyBlock &= cn.getExit() == node;
		return isExitingFinallyBlock;
	}

	/** Prints detailed information of jump nodes */
	private static String getJumpTokenDetailString(JumpToken jumpToken, Node jumpNode) {
		String jNode = ASTUtils.getNodeDetailString(jumpNode);
		String jmpStr = jNode + " >> " + jumpToken.toString();
		return jmpStr;
	}

	/** Prints detailed information of all control flow edges. Used for debugging purposes */
	private static void printAllEdgeDetails(ComplexNodeMapper cnMapper) { // TODO move this to a PrintUtils class
		System.out.println("\nAll edges:");
		Set<ControlFlowEdge> allEdges = new HashSet<>();
		for (ComplexNode cn : cnMapper.getAll()) {
			for (Node n : cn.getNodes()) {
				allEdges.addAll(n.pred);
				allEdges.addAll(n.succ);
			}
		}
		for (ControlFlowEdge edge : allEdges) {
			System.out.println(edge);
		}
	}

}
