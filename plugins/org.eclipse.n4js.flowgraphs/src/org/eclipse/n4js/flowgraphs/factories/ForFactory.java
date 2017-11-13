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

import static org.eclipse.n4js.flowgraphs.factories.StandardCFEFactory.ENTRY_NODE;
import static org.eclipse.n4js.flowgraphs.factories.StandardCFEFactory.EXIT_NODE;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link ForStatement}s. */
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
		int intPos = 0;
		ComplexNode cNode = new ComplexNode(forStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, intPos++, forStmt);
		List<Node> declNodes = new LinkedList<>();
		List<Node> initNodes = new LinkedList<>();
		if (forStmt.getVarDeclsOrBindings() != null) {
			int i = 0;
			for (VariableDeclarationOrBinding vdob : forStmt.getVarDeclsOrBindings()) {
				for (VariableDeclaration varDecl : vdob.getVariableDeclarations()) {
					Node initNode = new DelegatingNode("decl_" + i, intPos++, forStmt, varDecl);
					declNodes.add(initNode);
					i++;
				}
			}
		}
		if (forStmt.getInitExpr() != null) {
			Node initNode = new DelegatingNode("inits", intPos++, forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		Node expressionNode = new DelegatingNode("expression", intPos++, forStmt, forStmt.getExpression());
		Node getObjectKeysNode = null;
		if (forInSemantics)
			getObjectKeysNode = new HelperNode("getObjectKeys", intPos++, forStmt);
		Node getIteratorNode = new HelperNode("getIterator", intPos++, forStmt);
		Node hasNextNode = new HelperNode(LOOPCATCH_NODE_NAME, intPos++, forStmt);
		Node nextNode = new HelperNode("next", intPos++, forStmt);
		Node bodyNode = null;
		if (forStmt.getStatement() != null)
			bodyNode = new DelegatingNode("body", intPos++, forStmt, forStmt.getStatement());
		Node exitNode = new HelperNode(EXIT_NODE, intPos++, forStmt);

		cNode.addNode(entryNode);
		for (Node declNode : declNodes)
			cNode.addNode(declNode);
		for (Node initNode : initNodes)
			cNode.addNode(initNode);
		cNode.addNode(expressionNode);
		cNode.addNode(getObjectKeysNode);
		cNode.addNode(getIteratorNode);
		cNode.addNode(hasNextNode);
		cNode.addNode(nextNode);
		cNode.addNode(bodyNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(declNodes);
		nodes.addAll(initNodes);
		nodes.add(expressionNode);
		nodes.add(getObjectKeysNode);
		nodes.add(getIteratorNode);
		nodes.add(hasNextNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(ControlFlowType.Exit, hasNextNode, exitNode);
		cNode.connectInternalSucc(ControlFlowType.Repeat, hasNextNode, nextNode);
		cNode.connectInternalSucc(nextNode, bodyNode, hasNextNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		hasNextNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

	private static ComplexNode buildForPlain(ForStatement forStmt) {
		int intPos = 0;
		ComplexNode cNode = new ComplexNode(forStmt);

		List<Node> initNodes = new LinkedList<>();
		Node entryNode = new HelperNode(ENTRY_NODE, intPos++, forStmt);
		Node conditionNode = null;
		Node bodyNode = null;
		Node updatesNode = null;

		if (forStmt.getVarDeclsOrBindings() != null) {
			int i = 0;
			for (VariableDeclarationOrBinding vdob : forStmt.getVarDeclsOrBindings()) {
				for (VariableDeclaration varDecl : vdob.getVariableDeclarations()) {
					Node initNode = new DelegatingNode("init_" + i, intPos++, forStmt, varDecl);
					initNodes.add(initNode);
					i++;
				}
			}
		}
		if (forStmt.getInitExpr() != null) {
			Node initNode = new DelegatingNode("inits", intPos++, forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		if (forStmt.getExpression() != null) {
			conditionNode = new DelegatingNode("condition", intPos++, forStmt, forStmt.getExpression());
		}
		if (forStmt.getStatement() != null) {
			bodyNode = new DelegatingNode("body", intPos++, forStmt, forStmt.getStatement());
		}
		Node loopCatchNode = new HelperNode(LOOPCATCH_NODE_NAME, intPos++, forStmt);
		if (forStmt.getUpdateExpr() != null) {
			updatesNode = new DelegatingNode("updates", intPos++, forStmt, forStmt.getUpdateExpr());
		}
		Node exitNode = new HelperNode(EXIT_NODE, intPos++, forStmt);

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
		cNode.connectInternalSucc(nodes);

		if (conditionNode != null) {
			cNode.connectInternalSucc(ControlFlowType.Repeat, conditionNode, bodyNode);
			cNode.connectInternalSucc(ControlFlowType.Exit, conditionNode, exitNode);
			cNode.connectInternalSucc(bodyNode, loopCatchNode, updatesNode, conditionNode);

		} else {
			nodes.clear();
			nodes.add(entryNode);
			nodes.addAll(initNodes);
			Node beforeBodyNode = ListUtils.filterNulls(nodes).getLast();
			cNode.connectInternalSucc(beforeBodyNode, bodyNode, loopCatchNode, updatesNode);

			LinkedList<Node> loopCycle = ListUtils.filterNulls(bodyNode, loopCatchNode, updatesNode);
			Node loopSrc = loopCycle.getLast();
			Node loopTgt = loopCycle.getFirst();
			cNode.connectInternalSucc(ControlFlowType.Repeat, loopSrc, loopTgt);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		loopCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
