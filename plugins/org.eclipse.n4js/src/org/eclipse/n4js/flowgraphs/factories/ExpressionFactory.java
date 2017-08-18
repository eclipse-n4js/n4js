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

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;

class ExpressionFactory {

	static ComplexNode buildComplexNode(Expression expr) {
		if (expr instanceof ConditionalExpression) {
			return buildComplexNode((ConditionalExpression) expr);
		}

		ComplexNode cNode = new ComplexNode(expr);

		HelperNode entryNode = new HelperNode("entry", expr);
		Node exitNode = new RepresentingNode("exit", expr);
		List<Node> argumentNodes = new LinkedList<>();

		List<Expression> args = ControlFlowChildren.get(expr);
		for (int i = 0; i < args.size(); i++) {
			Expression arg = args.get(i);
			Node argNode = new DelegatingNode("arg_" + i, expr, arg);
			argumentNodes.add(argNode);
		}

		cNode.addNode(entryNode);
		for (Node arg : argumentNodes)
			cNode.addNode(arg);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(argumentNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

	static ComplexNode buildComplexNode(ConditionalExpression condExpr) {
		ComplexNode cNode = new ComplexNode(condExpr);

		HelperNode entryNode = new HelperNode("entry", condExpr);
		Node exitNode = new RepresentingNode("exit", condExpr);
		Node conditionNode = new DelegatingNode("condition", condExpr, condExpr.getExpression());
		Node thenNode = new DelegatingNode("then", condExpr, condExpr.getTrueExpression());
		Node elseNode = new DelegatingNode("else", condExpr, condExpr.getFalseExpression());

		cNode.addNode(entryNode);
		cNode.addNode(conditionNode);
		cNode.addNode(thenNode);
		cNode.addNode(elseNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(conditionNode);
		nodes.add(thenNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		nodes.clear();
		nodes.add(conditionNode);
		nodes.add(elseNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
