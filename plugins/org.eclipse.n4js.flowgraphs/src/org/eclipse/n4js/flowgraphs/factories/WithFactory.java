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

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.WithStatement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link WithStatement}s. */
class WithFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, WithStatement withStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), withStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), withStmt);
		Node expressionNode = DelNodeFactory.create(astpp, "expression", withStmt, withStmt.getExpression());
		Node statementNode = DelNodeFactory.create(astpp, "statement", withStmt, withStmt.getStatement());
		Node exitNode = new HelperNode(EXIT_NODE, astpp.pos(), withStmt);

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
