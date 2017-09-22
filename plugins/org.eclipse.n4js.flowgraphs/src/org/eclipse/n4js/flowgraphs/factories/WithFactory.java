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
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.WithStatement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link WithStatement}s. */
class WithFactory {

	static ComplexNode buildComplexNode(WithStatement withStmt) {
		ComplexNode cNode = new ComplexNode(withStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, withStmt);
		Node expressionNode = new DelegatingNode("expression", withStmt, withStmt.getExpression());
		Node statementNode = new DelegatingNode("statement", withStmt, withStmt.getStatement());
		Node exitNode = new HelperNode(EXIT_NODE, withStmt);

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
