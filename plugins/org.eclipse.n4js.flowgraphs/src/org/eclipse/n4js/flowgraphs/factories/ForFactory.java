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
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link ForStatement}s. */
class ForFactory {

	static final String LOOPCATCH_NODE_NAME = "loopCatch";

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ForStatement forStmt) {
		if (forStmt.isForIn())
			return buildForInOf(astpp, forStmt, true);
		if (forStmt.isForOf())
			return buildForInOf(astpp, forStmt, false);
		if (forStmt.isForPlain())
			return buildForPlain(astpp, forStmt);

		return null;
	}

	private static ComplexNode buildForInOf(ReentrantASTIterator astpp, ForStatement forStmt, boolean forInSemantics) {
		ComplexNode cNode = new ComplexNode(astpp.container(), forStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), forStmt);
		List<Node> declNodes = new LinkedList<>();
		List<Node> initNodes = new LinkedList<>();
		if (forStmt.getVarDeclsOrBindings() != null) {
			int i = 0;
			for (VariableDeclarationOrBinding vdob : forStmt.getVarDeclsOrBindings()) {
				Node initNode = DelegatingNodeFactory.create(astpp, "decl_" + i, forStmt, vdob);
				declNodes.add(initNode);
				i++;
			}
		}
		if (forStmt.getInitExpr() != null) {
			Node initNode = DelegatingNodeFactory.create(astpp, "inits", forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		Node expressionNode = DelegatingNodeFactory.create(astpp, "expression", forStmt, forStmt.getExpression());
		Node getObjectKeysNode = null;
		if (forInSemantics) {
			getObjectKeysNode = new HelperNode("getObjectKeys", astpp.pos(), forStmt);
		}
		Node getIteratorNode = new HelperNode("getIterator", astpp.pos(), forStmt);
		Node hasNextNode = new HelperNode(LOOPCATCH_NODE_NAME, astpp.pos(), forStmt);
		Node nextNode = new HelperNode("next", astpp.pos(), forStmt);
		Node bodyNode = null;
		if (forStmt.getStatement() != null) {
			bodyNode = DelegatingNodeFactory.create(astpp, "body", forStmt, forStmt.getStatement());
		}
		Node exitNode = new HelperNode(EXIT_NODE, astpp.pos(), forStmt);

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

	private static ComplexNode buildForPlain(ReentrantASTIterator astpp, ForStatement forStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), forStmt);

		List<Node> initNodes = new LinkedList<>();
		Node entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), forStmt);
		Node conditionNode = null;
		Node bodyNode = null;
		Node updatesNode = null;

		if (forStmt.getVarDeclsOrBindings() != null) {
			int i = 0;
			for (VariableDeclarationOrBinding vdob : forStmt.getVarDeclsOrBindings()) {
				Node initNode = DelegatingNodeFactory.create(astpp, "init_" + i, forStmt, vdob);
				initNodes.add(initNode);
				i++;
			}
		}
		if (forStmt.getInitExpr() != null) {
			Node initNode = DelegatingNodeFactory.create(astpp, "inits", forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		if (forStmt.getExpression() != null) {
			conditionNode = DelegatingNodeFactory.create(astpp, "condition", forStmt, forStmt.getExpression());
		}
		bodyNode = DelegatingNodeFactory.createOrHelper(astpp, "body", forStmt, forStmt.getStatement());
		Node loopCatchNode = new HelperNode(LOOPCATCH_NODE_NAME, astpp.pos(), forStmt);
		if (forStmt.getUpdateExpr() != null) {
			updatesNode = DelegatingNodeFactory.create(astpp, "updates", forStmt, forStmt.getUpdateExpr());
		}
		Node exitNode = new HelperNode(EXIT_NODE, astpp.pos(), forStmt);

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
			if (loopSrc != loopTgt) {
				cNode.connectInternalSucc(ControlFlowType.Repeat, loopSrc, loopTgt);
			}
			cNode.connectInternalSucc(ControlFlowType.DeadCode, loopSrc, exitNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		loopCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
