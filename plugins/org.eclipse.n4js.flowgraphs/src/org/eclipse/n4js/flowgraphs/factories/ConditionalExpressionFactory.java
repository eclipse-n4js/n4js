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
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ConditionalExpression;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class ConditionalExpressionFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ConditionalExpression condExpr) {
		ComplexNode cNode = new ComplexNode(astpp.container(), condExpr);

		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), condExpr);
		Node conditionNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.CONDITION, condExpr,
				condExpr.getExpression());
		Node thenNode = DelegatingNodeFactory.create(astpp, NodeNames.THEN, condExpr, condExpr.getTrueExpression());
		Node elseNode = DelegatingNodeFactory.create(astpp, NodeNames.ELSE, condExpr, condExpr.getFalseExpression());
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), condExpr);

		cNode.addNode(entryNode);
		cNode.addNode(conditionNode);
		cNode.addNode(thenNode);
		cNode.addNode(elseNode);
		cNode.addNode(exitNode);

		cNode.connectInternalSucc(entryNode, conditionNode);
		cNode.connectInternalSucc(ControlFlowType.IfTrue, conditionNode, thenNode);
		cNode.connectInternalSucc(thenNode, exitNode);

		cNode.connectInternalSucc(ControlFlowType.IfFalse, conditionNode, elseNode);
		cNode.connectInternalSucc(elseNode, exitNode);

		if (thenNode == null && elseNode == null) {
			// broken AST
			cNode.connectInternalSucc(conditionNode, exitNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
