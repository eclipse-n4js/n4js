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
import org.eclipse.n4js.flowgraphs.model.JumpType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.DoStatement;

class DoWhileFactory {

	static ComplexNode buildComplexNode(DoStatement doStmt) {
		ComplexNode cNode = new ComplexNode(doStmt);

		Node entryNode = new HelperNode("entry", doStmt);
		Node exitNode = new DelegatingNode("exit", doStmt);
		Node conditionNode = new DelegatingNode("condition", doStmt.getExpression());
		Node bodyNode = new DelegatingNode("body", doStmt.getStatement());

		cNode.addNode(entryNode);
		cNode.addNode(bodyNode);
		cNode.addNode(conditionNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(bodyNode);
		nodes.add(conditionNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.connectInternalSuccLC(conditionNode, bodyNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		String label = ASTUtils.getLabel(doStmt);
		exitNode.addCatchToken(new CatchToken(JumpType.Break, label));
		conditionNode.addCatchToken(new CatchToken(JumpType.Continue, label));

		cNode.setLoopContainer(true);
		return cNode;
	}

}
