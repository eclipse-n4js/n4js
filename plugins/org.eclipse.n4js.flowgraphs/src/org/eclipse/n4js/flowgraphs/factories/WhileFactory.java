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
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link WhileStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class WhileFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, WhileStatement whileStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), whileStmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), whileStmt);
		Node conditionNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.CONDITION, whileStmt,
				whileStmt.getExpression());
		Node conditionForkNode = new HelperNode(NodeNames.CONDITION_FORK, astpp.pos(), whileStmt);
		Node bodyNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.BODY, whileStmt,
				whileStmt.getStatement());
		Node continueCatchNode = new HelperNode(NodeNames.CONTINUE_CATCH, astpp.pos(), whileStmt);
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), whileStmt);

		cNode.addNode(entryNode);
		cNode.addNode(conditionNode);
		cNode.addNode(conditionForkNode);
		cNode.addNode(bodyNode);
		cNode.addNode(continueCatchNode);
		cNode.addNode(exitNode);

		cNode.connectInternalSucc(entryNode, conditionNode, conditionForkNode);
		cNode.connectInternalSucc(ControlFlowType.LoopEnter, conditionForkNode, bodyNode);
		cNode.connectInternalSucc(ControlFlowType.LoopExit, conditionForkNode, exitNode);
		cNode.connectInternalSucc(bodyNode, continueCatchNode);
		cNode.connectInternalSucc(ControlFlowType.LoopRepeat, continueCatchNode, conditionNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		// catch for short-circuits
		bodyNode.addCatchToken(new CatchToken(ControlFlowType.IfTrue, ControlFlowType.LoopEnter));
		exitNode.addCatchToken(new CatchToken(ControlFlowType.IfFalse, ControlFlowType.LoopExit));

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(whileStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));
		continueCatchNode.addCatchToken(new CatchToken(ControlFlowType.Continue, lblStmt));

		return cNode;
	}

}
