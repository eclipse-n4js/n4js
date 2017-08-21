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
package org.eclipse.n4js.flowgraphs.factories;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ForStatement;

class ForFactory {

	static final String CONDITION_NODE_NAME = "condition";

	static ComplexNode buildComplexNode(ForStatement forStmt) {
		if (forStmt.isForIn())
			return buildForInOf(forStmt, true);
		if (forStmt.isForOf())
			return buildForInOf(forStmt, false);
		if (forStmt.isForPlain())
			return buildForPlain(forStmt);

		return null;
	}

	private static ComplexNode buildForInOf(ForStatement forStmt, boolean forInSemantics) {
		ComplexNode cNode = new ComplexNode(forStmt);

		Node entryNode = new HelperNode("entry", forStmt);
		Node exitNode = new HelperNode("exit", forStmt);
		Node expressionNode = new DelegatingNode("expression", forStmt, forStmt.getExpression());
		Node getObjectKeysNode = null;
		if (forInSemantics)
			getObjectKeysNode = new HelperNode("getObjectKeys", forStmt);
		Node getIteratorNode = new HelperNode("getIterator", forStmt);
		Node hasNextNode = new HelperNode(CONDITION_NODE_NAME, forStmt);
		Node nextNode = new HelperNode("next", forStmt);
		Node bodyNode = null;
		if (forStmt.getStatement() != null)
			bodyNode = new DelegatingNode("body", forStmt, forStmt.getStatement());

		cNode.addNode(entryNode);
		cNode.addNode(expressionNode);
		cNode.addNode(getObjectKeysNode);
		cNode.addNode(getIteratorNode);
		cNode.addNode(hasNextNode);
		cNode.addNode(nextNode);
		cNode.addNode(bodyNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(expressionNode);
		nodes.add(getObjectKeysNode);
		nodes.add(getIteratorNode);
		nodes.add(hasNextNode);
		nodes.add(nextNode);
		nodes.add(bodyNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(nextNode, exitNode);

		if (bodyNode != null) {
			cNode.connectInternalSuccLC(bodyNode, nextNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		String label = ASTUtils.getLabel(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, label));
		hasNextNode.addCatchToken(new CatchToken(ControlFlowType.Continue, label));

		cNode.setLoopContainer(true);
		return cNode;
	}

	private static ComplexNode buildForPlain(ForStatement forStmt) {
		ComplexNode cNode = new ComplexNode(forStmt);

		Node initsNode = null;
		Node prebodyNode = new HelperNode("prebody", forStmt);
		Node bodyNode = null;
		Node conditionNode = null;
		Node updatesNode = null;

		Node entryNode = new HelperNode("entry", forStmt);
		Node exitNode = new HelperNode("exit", forStmt);
		if (forStmt.getInitExpr() != null) {
			initsNode = new DelegatingNode("inits", forStmt, forStmt.getInitExpr());
		}
		if (forStmt.getExpression() != null) {
			conditionNode = new DelegatingNode(CONDITION_NODE_NAME, forStmt, forStmt.getExpression());
		}
		if (forStmt.getStatement() != null) {
			bodyNode = new DelegatingNode("body", forStmt, forStmt.getStatement());
		}
		if (forStmt.getUpdateExpr() != null) {
			updatesNode = new DelegatingNode("updates", forStmt, forStmt.getUpdateExpr());
		}

		cNode.addNode(entryNode);
		cNode.addNode(exitNode);
		cNode.addNode(initsNode);
		cNode.addNode(conditionNode);
		cNode.addNode(prebodyNode);
		cNode.addNode(bodyNode);
		cNode.addNode(updatesNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(initsNode);
		nodes.add(conditionNode);
		nodes.add(prebodyNode);
		nodes.add(bodyNode);
		nodes.add(updatesNode);
		cNode.connectInternalSucc(nodes);

		if (conditionNode != null)
			cNode.connectInternalSucc(conditionNode, exitNode);

		LinkedList<Node> loopCycle = ListUtils.filterNulls(conditionNode, prebodyNode, bodyNode, updatesNode);
		// LinkedList<Node> parts = ListUtils.filterNulls(updatesNode, bodyNode, conditionNode);
		if (!loopCycle.isEmpty()) {
			Node loopSrc = loopCycle.getLast();
			Node loopTgt = loopCycle.getFirst();
			if (loopSrc != null && loopTgt != null) {
				cNode.connectInternalSuccLC(loopSrc, loopTgt);
			}
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		cNode.setLoopContainer(true);

		String label = ASTUtils.getLabel(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, label));

		// parts = ListUtils.filterNulls(conditionNode, prebodyNode, bodyNode, updatesNode);
		if (!loopCycle.isEmpty()) {
			Node contNode = loopCycle.getFirst();
			contNode.addCatchToken(new CatchToken(ControlFlowType.Continue, label));
		}

		return cNode;
	}

}
