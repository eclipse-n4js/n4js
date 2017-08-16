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
import org.eclipse.n4js.n4JS.Expression;

class ExpressionFactory {

	static ComplexNode buildComplexNode(Expression expr) {
		ComplexNode cNode = new ComplexNode(expr);

		HelperNode entryNode = new HelperNode("entry", expr);
		Node exitNode = new RepresentingNode("exit", expr);
		List<Node> argumentNodes = new LinkedList<>();

		List<Expression> args = ControlFlowChildren.get(expr);
		for (Expression arg : args) {
			Node argNode = new DelegatingNode("arg_" + args.indexOf(arg), arg);
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

}
