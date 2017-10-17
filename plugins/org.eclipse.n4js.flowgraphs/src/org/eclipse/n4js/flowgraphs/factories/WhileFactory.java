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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link WhileStatement}s. */
class WhileFactory {

	static final String CONDITION_NODE_NAME = "condition";

	static ComplexNode buildComplexNode(WhileStatement whileStmt) {
		ComplexNode cNode = new ComplexNode(whileStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, whileStmt);
		Node exitNode = new HelperNode(EXIT_NODE, whileStmt);
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
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(ControlFlowType.Repeat, conditionNode, bodyNode);
		cNode.connectInternalSucc(bodyNode, conditionNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(whileStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		conditionNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
