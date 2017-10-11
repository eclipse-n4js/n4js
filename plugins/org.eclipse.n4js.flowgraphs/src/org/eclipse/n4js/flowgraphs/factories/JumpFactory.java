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
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
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
 * <p>
 * Jump edges start at and end at (start --> end):<br/>
 * <ul>
 * <li/>BreakStatement.exitNode --> (LoopStatement | SwitchStatement).exitNode
 * <li/>ContinueStatement.exitNode --> LoopStatement.conditionNode or ForStatement.updateNode
 * <li/>ReturnStatement.exitNode --> FunctionBlock.exitNode
 * <li/>ThrowStatement.exitNode --> FunctionBlock.exitNode
 * </ul>
 * <p>
 * Jumps that happen due to Break- or ContinueStatements can refer to a label. This must be respected when computing the
 * end node of a jump edge. See {@link ControlFlowGraphFactory} for more details.
 */
class JumpFactory {

	static ComplexNode buildComplexNode(BreakStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Break, stmt.getLabel());
		return buildComplexNode(stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ContinueStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Continue, stmt.getLabel());
		return buildComplexNode(stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ReturnStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Return);
		return buildComplexNode(stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(ThrowStatement stmt) {
		JumpToken jumptoken = new JumpToken(ControlFlowType.Throw);
		return buildComplexNode(stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(Statement stmt, Expression expr, JumpToken jumptoken) {
		ComplexNode cNode = new ComplexNode(stmt);

		Node entryNode = new HelperNode(ENTRY_NODE, stmt);
		Node exitNode = new RepresentingNode(EXIT_NODE, stmt);
		Node expression = null;

		cNode.addNode(entryNode);

		if (expr != null) {
			expression = new DelegatingNode("expression", stmt, expr);
			cNode.addNode(expression);
		}
		cNode.addNode(exitNode);

		List<Node> cfs = new LinkedList<>();
		cfs.add(entryNode);
		cfs.add(expression);
		cfs.add(exitNode);
		cNode.connectInternalSucc(cfs);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		exitNode.addJumpToken(jumptoken);

		return cNode;
	}

}
