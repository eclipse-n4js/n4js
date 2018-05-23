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
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link ForStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class ForFactory {

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

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), forStmt);
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
			Node initNode = DelegatingNodeFactory.create(astpp, NodeNames.INITS, forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		Node expressionNode = DelegatingNodeFactory.create(astpp, NodeNames.EXPRESSION, forStmt,
				forStmt.getExpression());
		Node getObjectKeysNode = null;
		if (forInSemantics) {
			getObjectKeysNode = new HelperNode(NodeNames.GET_OBJECT_KEYS, astpp.pos(), forStmt);
		}
		Node getIteratorNode = new HelperNode(NodeNames.GET_ITERATOR, astpp.pos(), forStmt);
		Node hasNextNode = new HelperNode(NodeNames.HAS_NEXT, astpp.pos(), forStmt);
		Node nextNode = new HelperNode(NodeNames.NEXT, astpp.pos(), forStmt);
		Node bodyNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.BODY, forStmt, forStmt.getStatement());
		Node continueCatchNode = new HelperNode(NodeNames.CONTINUE_CATCH, astpp.pos(), forStmt);
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), forStmt);

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
		cNode.addNode(continueCatchNode);
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
		cNode.connectInternalSucc(ControlFlowType.LoopExit, hasNextNode, exitNode);
		cNode.connectInternalSucc(ControlFlowType.LoopEnter, hasNextNode, nextNode);
		cNode.connectInternalSucc(nextNode, bodyNode, continueCatchNode);
		cNode.connectInternalSucc(ControlFlowType.LoopRepeat, continueCatchNode, hasNextNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		continueCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

	private static ComplexNode buildForPlain(ReentrantASTIterator astpp, ForStatement forStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), forStmt);

		List<Node> initNodes = new LinkedList<>();
		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), forStmt);
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
			Node initNode = DelegatingNodeFactory.create(astpp, NodeNames.INITS, forStmt, forStmt.getInitExpr());
			initNodes.add(initNode);
		}
		if (forStmt.getExpression() != null) {
			conditionNode = DelegatingNodeFactory.create(astpp, NodeNames.CONDITION, forStmt, forStmt.getExpression());
		}
		Node conditionForkNode = new HelperNode(NodeNames.CONDITION_FORK, astpp.pos(), forStmt);
		bodyNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.BODY, forStmt, forStmt.getStatement());
		Node continueCatchNode = new HelperNode(NodeNames.CONTINUE_CATCH, astpp.pos(), forStmt);
		if (forStmt.getUpdateExpr() != null) {
			updatesNode = DelegatingNodeFactory.create(astpp, NodeNames.UPDATES, forStmt, forStmt.getUpdateExpr());
		}
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), forStmt);

		cNode.addNode(entryNode);
		cNode.addNode(exitNode);
		for (Node initNode : initNodes)
			cNode.addNode(initNode);
		cNode.addNode(conditionNode);
		cNode.addNode(conditionForkNode);
		cNode.addNode(bodyNode);
		cNode.addNode(continueCatchNode);
		cNode.addNode(updatesNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(initNodes);
		nodes.add(conditionNode);
		nodes.add(conditionForkNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(ControlFlowType.LoopEnter, conditionForkNode, bodyNode);

		if (conditionNode != null) {
			cNode.connectInternalSucc(ControlFlowType.LoopExit, conditionForkNode, exitNode);
			cNode.connectInternalSucc(bodyNode, continueCatchNode, updatesNode);
			Node beforeConditionNode = ListUtils.filterNulls(bodyNode, continueCatchNode, updatesNode).getLast();
			cNode.connectInternalSucc(ControlFlowType.LoopRepeat, beforeConditionNode, conditionNode);

		} else {
			cNode.connectInternalSucc(bodyNode, continueCatchNode, updatesNode);

			LinkedList<Node> loopCycle = ListUtils.filterNulls(continueCatchNode, updatesNode);
			Node loopSrc = loopCycle.getLast();
			cNode.connectInternalSucc(ControlFlowType.LoopInfinite, loopSrc, conditionForkNode);
			cNode.connectInternalSucc(ControlFlowType.DeadCode, loopSrc, exitNode);
		}

		// catch for short-circuits
		bodyNode.addCatchToken(new CatchToken(ControlFlowType.IfTrue, ControlFlowType.LoopEnter));
		exitNode.addCatchToken(new CatchToken(ControlFlowType.IfFalse, ControlFlowType.LoopExit));

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(forStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		continueCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
