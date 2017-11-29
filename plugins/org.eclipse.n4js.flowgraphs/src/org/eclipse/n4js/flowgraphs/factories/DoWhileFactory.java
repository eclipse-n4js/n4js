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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link DoStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class DoWhileFactory {
	static final String CONDITION_NODE_NAME = "condition";

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, DoStatement doStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), doStmt);

		Node entryNode = new HelperNode("entry", astpp.pos(), doStmt);
		Node preBodyNode = new HelperNode("preBody", astpp.pos(), doStmt);
		Node bodyNode = DelegatingNodeFactory.create(astpp, "body", doStmt, doStmt.getStatement());
		Node conditionNode = DelegatingNodeFactory.createOrHelper(astpp, CONDITION_NODE_NAME, doStmt,
				doStmt.getExpression());
		Node exitNode = new HelperNode("exit", astpp.pos(), doStmt);

		cNode.addNode(entryNode);
		cNode.addNode(preBodyNode);
		cNode.addNode(bodyNode);
		cNode.addNode(conditionNode);
		cNode.addNode(exitNode);

		cNode.connectInternalSucc(entryNode, preBodyNode);
		cNode.connectInternalSucc(ControlFlowType.LoopEnter, preBodyNode, bodyNode);
		cNode.connectInternalSucc(bodyNode, conditionNode);
		cNode.connectInternalSucc(ControlFlowType.LoopRepeat, conditionNode, preBodyNode);
		cNode.connectInternalSucc(ControlFlowType.LoopExit, conditionNode, exitNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(doStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		conditionNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
