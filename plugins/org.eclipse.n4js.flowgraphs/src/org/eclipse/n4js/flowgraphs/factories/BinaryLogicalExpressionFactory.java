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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.WhileStatement;

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
		Node lhsNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.LHS, lbExpr, lbExpr.getLhs());
		Node scJumpNode = new HelperNode(NodeNames.SHORT_CIRCUIT_JUMP, astpp.pos(), lbExpr);
		Node rhsNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.RHS, lbExpr, lbExpr.getRhs());
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), lbExpr);

		cNode.addNode(entryNode);
		cNode.addNode(lhsNode);
		cNode.addNode(scJumpNode);
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

		cNode.connectInternalSucc(entryNode, lhsNode, scJumpNode);
		cNode.connectInternalSucc(thenCFT, scJumpNode, rhsNode);
		cNode.connectInternalSucc(rhsNode, exitNode);

		scJumpNode.addJumpToken(new JumpToken(elseCFT)); // short-circuit evaluation
		cNode.setJumpNode(scJumpNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		rhsNode.addCatchToken(new CatchToken(thenCFT));

		boolean isCatchingLhs = isTopJumpCatcher(lbExpr);
		if (isCatchingLhs) {
			exitNode.addCatchToken(new CatchToken(elseCFT));
			exitNode.addCatchToken(new CatchToken(thenCFT));
		} else {
			// TODO: minor improvement: add the following jump node
			// exitNode.addJumpToken(new JumpToken(thenCFT)); // short-circuit evaluation
			// cNode.setJumpNode(scJumpNode);
		}

		return cNode;
	}

	/**
	 * Short circuit evaluation of a {@link BinaryLogicalExpression} <i>ble</i> causes jumps that start at a lhs
	 * condition and jump e.g. directly into the then or else block. However, sometimes this jump does not target such a
	 * then or else block, for instance when <i>ble</i> is not used as a condition. In this case, the <i>ble</i> itself
	 * or one of its parents will catch the short circuit jump.
	 * <p>
	 * This method returns true if the given {@link BinaryLogicalExpression} is catching a jump that is caused by a
	 * short circuit evaluation. It returns false, if there is a parent element that will catch the jump instead.
	 */
	static private boolean isTopJumpCatcher(BinaryLogicalExpression ble) {
		EObject child, parent = ble;
		do { // skip parentheses
			child = parent;
			parent = parent.eContainer();
		} while (parent instanceof ParenExpression);

		if (parent instanceof BinaryLogicalExpression) {
			return false;
		}
		if (parent instanceof ConditionalExpression) {
			ConditionalExpression isParent = (ConditionalExpression) parent;
			return isParent.getExpression() != child;
		}
		if (parent instanceof Statement) {
			if (parent instanceof IfStatement) {
				IfStatement isParent = (IfStatement) parent;
				return isParent.getExpression() != child;
			}
			if (parent instanceof ForStatement) {
				ForStatement isParent = (ForStatement) parent;
				return isParent.getExpression() != child || !isParent.isForPlain();
			}
			if (parent instanceof WhileStatement) {
				WhileStatement isParent = (WhileStatement) parent;
				return isParent.getExpression() != child;
			}
			if (parent instanceof DoStatement) {
				DoStatement isParent = (DoStatement) parent;
				return isParent.getExpression() != child;
			}
		}

		return true;
	}

}
