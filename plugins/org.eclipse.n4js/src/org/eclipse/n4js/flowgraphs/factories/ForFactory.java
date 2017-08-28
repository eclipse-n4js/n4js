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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;

class ForFactory {

	static final String LOOPCATCH_NODE_NAME = "loopCatch";

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
		Node hasNextNode = new HelperNode(LOOPCATCH_NODE_NAME, forStmt);
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
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(ControlFlowType.Repeat, hasNextNode, nextNode);
		cNode.connectInternalSucc(nextNode, bodyNode);
		cNode.connectInternalSucc(ControlFlowType.Loop, bodyNode, hasNextNode);

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

		List<Node> initNodes = new LinkedList<>();
		Node conditionNode = null;
		Node bodyNode = null;
		Node loopCatchNode = new HelperNode(LOOPCATCH_NODE_NAME, forStmt);
		Node updatesNode = null;
		Node entryNode = new HelperNode("entry", forStmt);
		Node exitNode = new HelperNode("exit", forStmt);

		if (forStmt.getVarDeclsOrBindings() != null) {
			int i = 0;
			for (VariableDeclarationOrBinding vdob : forStmt.getVarDeclsOrBindings()) {
				for (VariableDeclaration varDecl : vdob.getVariableDeclarations()) {
					Node initNode = new DelegatingNode("init_" + i, forStmt, varDecl);
					initNodes.add(initNode);
					i++;
				}
			}
		}
		if (forStmt.getInitExpr() != null) {
			Node initNode = new DelegatingNode("inits", forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		if (forStmt.getExpression() != null) {
			conditionNode = new DelegatingNode("condition", forStmt, forStmt.getExpression());
		}
		if (forStmt.getStatement() != null) {
			bodyNode = new DelegatingNode("body", forStmt, forStmt.getStatement());
		}
		if (forStmt.getUpdateExpr() != null) {
			updatesNode = new DelegatingNode("updates", forStmt, forStmt.getUpdateExpr());
		}

		cNode.addNode(entryNode);
		cNode.addNode(exitNode);
		for (Node initNode : initNodes)
			cNode.addNode(initNode);
		cNode.addNode(conditionNode);
		cNode.addNode(bodyNode);
		cNode.addNode(loopCatchNode);
		cNode.addNode(updatesNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(initNodes);
		nodes.add(conditionNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		if (conditionNode != null) {
			cNode.connectInternalSucc(ControlFlowType.Repeat, conditionNode, bodyNode);
			cNode.connectInternalSucc(bodyNode, loopCatchNode, updatesNode, conditionNode);

		} else {
			cNode.connectInternalSucc(bodyNode, loopCatchNode, updatesNode);

			LinkedList<Node> loopCycle = ListUtils.filterNulls(bodyNode, loopCatchNode, updatesNode);
			Node loopSrc = loopCycle.getLast();
			Node loopTgt = loopCycle.getFirst();
			cNode.connectInternalSucc(ControlFlowType.Repeat, loopSrc, loopTgt);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		cNode.setLoopContainer(true);

		String label = ASTUtils.getLabel(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, label));
		loopCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, label));

		return cNode;
	}

}
