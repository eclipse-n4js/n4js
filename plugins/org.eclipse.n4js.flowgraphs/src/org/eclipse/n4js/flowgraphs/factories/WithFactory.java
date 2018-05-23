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
import org.eclipse.n4js.n4JS.WithStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link WithStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class WithFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, WithStatement withStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), withStmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), withStmt);
		Node expressionNode = DelegatingNodeFactory.create(astpp, NodeNames.EXPRESSION, withStmt,
				withStmt.getExpression());
		Node statementNode = DelegatingNodeFactory.create(astpp, NodeNames.STATEMENT, withStmt,
				withStmt.getStatement());
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), withStmt);

		cNode.addNode(entryNode);
		cNode.addNode(expressionNode);
		cNode.addNode(statementNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(expressionNode);
		nodes.add(statementNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
