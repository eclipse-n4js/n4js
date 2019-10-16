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
import org.eclipse.n4js.n4JS.CoalesceExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class CoalesceExpressionFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, CoalesceExpression coalesceExpr) {
		ComplexNode cNode = new ComplexNode(astpp.container(), coalesceExpr);

		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), coalesceExpr);
		Node expressionNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.EXPRESSION, coalesceExpr,
				coalesceExpr.getExpression());
		HelperNode forkNode = new HelperNode(NodeNames.NULLISH_COALESCING_FORK, astpp.pos(), coalesceExpr);
		Node defaultValueNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.DEFAULT, coalesceExpr,
				coalesceExpr.getDefaultExpression());
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), coalesceExpr);

		cNode.addNode(entryNode);
		cNode.addNode(expressionNode);
		cNode.addNode(forkNode);
		cNode.addNode(defaultValueNode);
		cNode.addNode(exitNode);

		cNode.connectInternalSucc(entryNode, expressionNode, forkNode);
		cNode.connectInternalSucc(forkNode, exitNode);

		cNode.connectInternalSucc(ControlFlowType.IfNullish, forkNode, defaultValueNode);
		cNode.connectInternalSucc(defaultValueNode, exitNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
