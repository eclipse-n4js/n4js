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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.EdgeUtils;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
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
		TreeSet<ControlFlowElement> cfContainers = new TreeSet<>(new CFEComparator());
		TreeSet<Block> cfCatchBlocks = new TreeSet<>(new CFEComparator());
		Map<ControlFlowElement, ComplexNode> cnMap = new HashMap<>();

		createComplexNodes(script, cfContainers, cfCatchBlocks, cnMap);
		ComplexNodeMapper cnMapper = new CNMapper(cnMap);

		connectComplexNodes(cnMapper);
		createJumpEdges(cnMapper);

		FlowGraph cfg = new FlowGraph(cfContainers, cfCatchBlocks, cnMap);
		return cfg;
	}

	/** Creates {@link ComplexNode}s for every {@link ControlFlowElement}. */
	static private void createComplexNodes(Script script, TreeSet<ControlFlowElement> cfContainers,
			TreeSet<Block> cfCatchBlocks, Map<ControlFlowElement, ComplexNode> cnMap) {

		ComplexNode cn = CFEFactoryDispatcher.build(script);
		cnMap.put(script, cn);

		TreeIterator<EObject> tit = script.eAllContents();
		while (tit.hasNext()) {
			EObject eObj = tit.next();
			if (eObj instanceof ControlFlowElement) {
				ControlFlowElement cfe = (ControlFlowElement) eObj;
				cfe = CFEMapper.map(cfe);

				if (cfe != null && !cnMap.containsKey(cfe)) {
					ControlFlowElement cfContainer = FGUtils.getCFContainer(cfe);
					cfContainers.add(cfContainer);
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

	static private void connectComplexNodes(ComplexNodeMapper cnMapper) {
		for (ComplexNode cn : cnMapper.getAll()) {
			for (Node mNode : cn.getAllButExitNodes()) {
				connectNode(cnMapper, mNode);
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
				ControlFlowEdge e = EdgeUtils.connectCF(mNode, subCN.getEntry());
				internalStartNode = subCN.getExit();
				if (PRINT_EDGE_DETAILS)
					printEdgeDetails(e);
			}
		}

		Set<Node> internalSuccs = mNode.getInternalSuccessors();
		for (Node internalSucc : internalSuccs) {
			ControlFlowType cfType = mNode.getInternalSuccessorControlFlowType(internalSucc);
			ControlFlowEdge e = EdgeUtils.connectCF(internalStartNode, internalSucc, cfType);
			if (PRINT_EDGE_DETAILS)
				printEdgeDetails(e);
		}
	}

	/**
	 * This methods searches for {@link ComplexNode}s that cause jumps. The outgoing edge is then replaced by a new jump
	 * edge that targets the catch node.
	 */
	private static void createJumpEdges(ComplexNodeMapper cnMapper) {
		for (ComplexNode cn : cnMapper.getAll()) {
			Node jumpNode = cn.getExit();
			for (JumpToken jumpToken : jumpNode.jumpToken) {
				EdgeUtils.removeAllCF(jumpNode.getSuccessorEdges());
				connectToJumpTarget(cnMapper, jumpNode, jumpToken);
			}
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

	private static final class CFEComparator implements Comparator<ControlFlowElement> {
		@Override
		public int compare(ControlFlowElement cfe1, ControlFlowElement cfe2) {
			return cfe1.hashCode() - cfe2.hashCode();
		}
	}

	private static class CNMapper implements ComplexNodeMapper {
		final private Map<ControlFlowElement, ComplexNode> cnMap;

		CNMapper(Map<ControlFlowElement, ComplexNode> cnMap) {
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
