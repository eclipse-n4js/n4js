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
import org.eclipse.n4js.n4JS.IfStatement;

class IfFactory {

	static ComplexNode buildComplexNode(IfStatement ifStmt) {
		ComplexNode cNode = new ComplexNode(ifStmt);

		Node entryNode = new HelperNode("entry", ifStmt);
		Node exitNode = new HelperNode("exit", ifStmt);
		Node conditionNode = new DelegatingNode("condition", ifStmt.getExpression());
		Node thenNode = null;
		Node elseNode = null;

		if (ifStmt.getIfStmt() != null)
			thenNode = new DelegatingNode("then", ifStmt.getIfStmt());
		if (ifStmt.getElseStmt() != null)
			elseNode = new DelegatingNode("else", ifStmt.getElseStmt());

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
