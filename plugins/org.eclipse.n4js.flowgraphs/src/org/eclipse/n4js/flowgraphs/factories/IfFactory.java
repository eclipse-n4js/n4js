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
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.IfStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link IfStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class IfFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, IfStatement ifStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), ifStmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), ifStmt);
		Node conditionNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.CONDITION, ifStmt,
				ifStmt.getExpression());
		Node thenNode = DelegatingNodeFactory.create(astpp, NodeNames.THEN, ifStmt, ifStmt.getIfStmt());
		Node elseNode = DelegatingNodeFactory.create(astpp, NodeNames.ELSE, ifStmt, ifStmt.getElseStmt());
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), ifStmt);

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

		if (ifStmt.getElseStmt() == null) {
			cNode.connectInternalSucc(conditionNode, exitNode);
		} else {
			cNode.connectInternalSucc(conditionNode, elseNode, exitNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
