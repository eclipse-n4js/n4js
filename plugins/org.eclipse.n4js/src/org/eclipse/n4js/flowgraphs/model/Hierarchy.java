/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.Block;

public class Hierarchy {
	final Map<Node, ComplexNode> nodeToCNode = new HashMap<>();
	final Map<ComplexNode, Block> stmtToBlock = new HashMap<>();
	final Map<Block, ComplexNode> blockToCNode = new HashMap<>();
	final Map<ComplexNode, ComplexNode> elemToStmt = new HashMap<>();

	public void addComplexNode(ComplexNode cn) {
		for (Node n : cn.getNodes()) {
			nodeToCNode.put(n, cn);
		}
	}

	public void addStmtToBlock(ComplexNode childBlockElem, Block parentBlock, ComplexNode parentCNode) {
		stmtToBlock.put(childBlockElem, parentBlock);
		blockToCNode.put(parentBlock, parentCNode);
	}

	public void addElementToStatement(ComplexNode elem, ComplexNode stmt) {
		elemToStmt.put(elem, stmt);
	}

	public ComplexNode getStatementCNode(ComplexNode elem) {
		return elemToStmt.map(elem.getOriginal());
	}

	public Block getParentBlock(ComplexNode cnElem) {
		ComplexNode stmtCN = getStatementCNode(cnElem);
		return stmtToBlock.map(stmtCN);
	}

	public Block getParentBlock(Block block) {
		ComplexNode stmtCN = blockToCNode.map(block);
		return stmtToBlock.map(stmtCN);
	}

	public ComplexNode getParentStmt(ComplexNode cnElem) {
		Block pBlock = getParentBlock(cnElem);
		return blockToCNode.map(pBlock);
	}

	public ComplexNode getParentStmt(Node n) {
		return getParentStmt(getComplexNode(n));
	}

	public ComplexNode getComplexNode(Node n) {
		return nodeToCNode.get(n);
	}

	public int getDepth(ComplexNode cNode) {
		assert (cNode != null);

		ComplexNode parent = getParentStmt(cNode);
		if (parent == null)
			return 0;

		return 1 + getDepth(parent);
	}

	public int getDepth(Node node) {
		ComplexNode cNode = getComplexNode(node);
		return getDepth(cNode);
	}

	public int getDepth(Block block) {
		ComplexNode cNode = blockToCNode.map(block);
		return getDepth(cNode);
	}

	public ComplexNode getCommonParent(ComplexNode cn1p, ComplexNode cn2p) {
		ComplexNode cn1 = cn1p;
		ComplexNode cn2 = cn2p;

		assert cn1 != null;
		assert cn2 != null;
		if (cn1 == cn2)
			return cn1;

		int d1 = getDepth(cn1);
		int d2 = getDepth(cn2);

		while (d1 != d2) {
			if (d1 < d2) {
				cn2 = getParentStmt(cn2);
				if (cn2 == null)
					break;
				d2 = getDepth(cn2);
			}
			if (d1 > d2) {
				cn1 = getParentStmt(cn1);
				if (cn1 == null)
					break;
				d1 = getDepth(cn1);
			}
		}

		assert cn1 != null;
		assert cn2 != null;
		if (cn1 == cn2)
			return cn1;

		while (cn1 != cn2) {
			cn1 = getParentStmt(cn1);
			cn2 = getParentStmt(cn2);

			if (cn1 == null)
				break;
			if (cn2 == null)
				break;
		}

		assert cn1 != null;
		assert cn2 != null;
		return cn1;
	}

	public Block getCommonBlock(ComplexNode cn1p, ComplexNode cn2p) {
		Block b1 = getParentBlock(cn1p);
		Block b2 = getParentBlock(cn2p);

		assert b1 != null;
		assert b2 != null;
		if (b1 == b2)
			return b1;

		int d1 = getDepth(b1);
		int d2 = getDepth(b2);

		while (d1 != d2) {
			if (d1 < d2) {
				b2 = getParentBlock(b2);
				if (b2 == null)
					break;
				d2 = getDepth(b2);
			}
			if (d1 > d2) {
				b1 = getParentBlock(b1);
				if (b1 == null)
					break;
				d1 = getDepth(b1);
			}
		}

		assert b1 != null;
		assert b2 != null;
		if (b1 == b2)
			return b1;

		while (b1 != b2) {
			b1 = getParentBlock(b1);
			b2 = getParentBlock(b2);

			if (b1 == null)
				break;
			if (b2 == null)
				break;
		}

		assert b1 != null;
		assert b2 != null;
		return b1;
	}

	public ComplexNode getCommonParent(Node n1, Node n2) {
		ComplexNode cn1 = getComplexNode(n1);
		ComplexNode cn2 = getComplexNode(n2);
		return getCommonParent(cn1, cn2);
	}

	public ComplexNode getPredecessor(ComplexNode cNode) {
		Node predNode = cNode.getEntry();
		boolean startOrEndNode;
		do {
			predNode = getPredecessorNode(predNode);
			if (predNode == null)
				return null;

			startOrEndNode = false;
			startOrEndNode |= predNode instanceof StartNode;
			startOrEndNode |= predNode instanceof EndNode;
		} while (!startOrEndNode);

		ComplexNode predCNode = getComplexNode(predNode);
		return predCNode;
	}

	private Node getPredecessorNode(Node node) {
		Node predecessor = null;

		/** get simple predecessor */
		for (ControlFlowEdge predEdge : node.pred) {
			boolean isSimplePred = true;
			isSimplePred &= !predEdge.isJump;
			isSimplePred &= !predEdge.isLoop;
			if (isSimplePred) {
				predecessor = predEdge.start;
			}
		}

		/** get break predecessor */
		if (predecessor == null) {
			for (ControlFlowEdge predEdge : node.pred) {
				boolean isBreakPred = true;
				isBreakPred &= !predEdge.isLoop;
				if (isBreakPred) {
					predecessor = predEdge.start;
				}
			}
		}

		return predecessor;
	}

	public boolean isPartOfStatement(ComplexNode stmtCNode, ComplexNode partCNode) {
		ComplexNode cNode = partCNode;
		Node predNode;

		do {
			Node startNode = cNode.getEntry();
			predNode = getPredecessorNode(startNode);
			if (predNode == null)
				return false;

			cNode = getComplexNode(predNode);

		} while (predNode instanceof EndNode);

		return stmtCNode == cNode;
	}

	public Node getFirstNode(Node node1, Node node2) {
		ComplexNode commonParent = getCommonParent(node1, node2);

		ComplexNode cn1 = getComplexNode(node1);
		ComplexNode cn2 = getComplexNode(node2);
		List<ComplexNode> path1 = new LinkedList<>();
		List<ComplexNode> path2 = new LinkedList<>();

		boolean foundPaths = true;
		foundPaths &= setPath(cn1, commonParent, path1);
		foundPaths &= setPath(cn2, commonParent, path2);
		if (!foundPaths)
			return null;

		Iterator<ComplexNode> it1 = path1.iterator();
		Iterator<ComplexNode> it2 = path2.iterator();
		do {
			cn1 = it1.next();
			cn2 = it2.next();

			if (!it1.hasNext())
				break;
			if (!it2.hasNext())
				break;
		} while (cn1 == cn2);

		assert cn1 != null;
		assert cn2 != null;

		if (cn1 == cn2) {
			if (!it1.hasNext())
				return node1;
			if (!it2.hasNext())
				return node2;
		}

		int d1 = getDepth(cn1);
		int d2 = getDepth(cn2);
		if (d1 == d2)
			return null;
		if (d1 < d2)
			return node2;
		if (d1 > d2)
			return node1;

		return null;
	}

	private boolean setPath(ComplexNode cNode, ComplexNode root, List<ComplexNode> path) {
		path.add(0, cNode);
		if (cNode == root)
			return true;

		ComplexNode pred = getPredecessor(cNode);
		if (pred == null)
			return false;

		return setPath(pred, root, path);
	}
}
