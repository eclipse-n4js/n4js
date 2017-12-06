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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 * Jumps are control flow edges that start at a {@link ControlFlowElement} and end at another
 * {@link ControlFlowElement}. The loop control flow edge in loop statements, such as in {@link WhileStatement}s, is not
 * considered as a jump.
 * <p/>
 * Jump edges start at and end at (start --> end):<br/>
 * <ul>
 * <li/>BreakStatement.exitNode --> (LoopStatement | SwitchStatement).exitNode
 * <li/>ContinueStatement.exitNode --> LoopStatement.conditionNode or ForStatement.updateNode
 * <li/>ReturnStatement.exitNode --> FunctionBlock.exitNode
 * <li/>ThrowStatement.exitNode --> FunctionBlock.exitNode
 * </ul>
 * <p/>
 * Jumps that happen due to Break- or ContinueStatements can refer to a label. This must be respected when computing the
 * end node of a jump edge. See {@link ControlFlowGraphFactory} for more details.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class JumpFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, BreakStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Break, stmt.getLabel());
		return buildComplexNode(astpp, stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ContinueStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Continue, stmt.getLabel());
		return buildComplexNode(astpp, stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ReturnStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Return);
		return buildComplexNode(astpp, stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ThrowStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Throw);
		return buildComplexNode(astpp, stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, Statement stmt, Expression expr,
			JumpToken jumptoken) {
		ComplexNode cNode = new ComplexNode(astpp.container(), stmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), stmt);
		cNode.addNode(entryNode);

		Node expression = null;
		if (expr != null) {
			expression = DelegatingNodeFactory.create(astpp, NodeNames.EXPRESSION, stmt, expr);
			cNode.addNode(expression);
		}
		Node jumpNode = new RepresentingNode(NodeNames.JUMP, astpp.pos(), stmt);
		cNode.addNode(jumpNode);
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), stmt);
		cNode.addNode(exitNode);

		List<Node> cfs = new LinkedList<>();
		cfs.add(entryNode);
		cfs.add(expression);
		cNode.connectInternalSucc(entryNode, expression);

		Node beforeDeadNode = ListUtils.filterNulls(entryNode, expression).getLast();
		cNode.connectInternalSucc(beforeDeadNode, jumpNode);
		cNode.connectInternalSucc(ControlFlowType.DeadCode, jumpNode, exitNode);

		jumpNode.addJumpToken(jumptoken);
		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);
		cNode.setJumpNode(jumpNode);

		return cNode;
	}

}
