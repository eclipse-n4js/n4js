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

import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.WhileStatement;

class WhileFactory {

	static final String CONDITION_NODE_NAME = "condition";

	static ComplexNode buildComplexNode(WhileStatement whileStmt) {
		ComplexNode cNode = new ComplexNode(whileStmt);

		Node entryNode = new HelperNode("entry", whileStmt);
		Node exitNode = new HelperNode("exit", whileStmt);
		Node conditionNode = new DelegatingNode(CONDITION_NODE_NAME, whileStmt, whileStmt.getExpression());
		Node bodyNode = null;

		if (whileStmt.getStatement() != null) {
			bodyNode = new DelegatingNode("body", whileStmt, whileStmt.getStatement());
		}

		cNode.addNode(entryNode);
		cNode.addNode(conditionNode);
		cNode.addNode(bodyNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(conditionNode);
		nodes.add(bodyNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(conditionNode, exitNode);

		LinkedList<Node> parts = ListUtils.filterNulls(bodyNode, conditionNode);
		cNode.connectInternalSuccLC(parts.getFirst(), conditionNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		String label = ASTUtils.getLabel(whileStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, label));
		conditionNode.addCatchToken(new CatchToken(ControlFlowType.Continue, label));
		cNode.setLoopContainer(true);

		return cNode;
	}

}
