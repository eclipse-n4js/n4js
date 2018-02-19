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
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class BinaryLogicalExpressionFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, BinaryLogicalExpression lbExpr) {
		ComplexNode cNode = new ComplexNode(astpp.container(), lbExpr);

		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), lbExpr);
		Node lhsNode = DelegatingNodeFactory.create(astpp, NodeNames.LHS, lbExpr, lbExpr.getLhs());
		Node rhsNode = DelegatingNodeFactory.create(astpp, NodeNames.RHS, lbExpr, lbExpr.getRhs());
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), lbExpr);

		cNode.addNode(entryNode);
		cNode.addNode(lhsNode);
		cNode.addNode(rhsNode);
		cNode.addNode(exitNode);

		ControlFlowType thenCFT = null;
		ControlFlowType elseCFT = null;
		switch (lbExpr.getOp()) {
		case OR:
			thenCFT = ControlFlowType.IfFalse;
			elseCFT = ControlFlowType.IfTrue;
			break;
		case AND:
			thenCFT = ControlFlowType.IfTrue;
			elseCFT = ControlFlowType.IfFalse;
			break;
		}

		cNode.connectInternalSucc(entryNode, lhsNode);
		cNode.connectInternalSucc(thenCFT, lhsNode, rhsNode);
		cNode.connectInternalSucc(rhsNode, exitNode);

		cNode.connectInternalSucc(elseCFT, lhsNode, exitNode); // short-circuit evaluation

		if (lhsNode == null) { // broken AST
			if (rhsNode == null) {
				cNode.connectInternalSucc(entryNode, exitNode);
			} else {
				cNode.connectInternalSucc(entryNode, rhsNode);
			}
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
