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

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.JumpType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.ThrowStatement;

class JumpFactory {

	static ComplexNode buildComplexNode(BreakStatement stmt) {
		JumpToken jumptoken = new JumpToken(JumpType.Break, stmt.getLabel());
		return buildComplexNode(stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ContinueStatement stmt) {
		JumpToken jumptoken = new JumpToken(JumpType.Continue, stmt.getLabel());
		return buildComplexNode(stmt, null, jumptoken);
	}

	static ComplexNode buildComplexNode(ReturnStatement stmt) {
		JumpToken jumptoken = new JumpToken(JumpType.Return);
		return buildComplexNode(stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(ThrowStatement stmt) {
		JumpToken jumptoken = new JumpToken(JumpType.Throw, stmt.getExpression());
		return buildComplexNode(stmt, stmt.getExpression(), jumptoken);
	}

	static ComplexNode buildComplexNode(Statement stmt, Expression expr, JumpToken jumptoken) {
		ComplexNode cNode = new ComplexNode(stmt);

		Node entryNode = new HelperNode("entry", stmt);
		Node endNode = new RepresentingNode("exit", stmt);
		Node expression = null;

		cNode.addNode(entryNode);

		if (expr != null) {
			expression = new DelegatingNode("expression", stmt, expr);
			cNode.addNode(expression);
		}
		cNode.addNode(endNode);

		List<Node> cfs = new LinkedList<>();
		cfs.add(entryNode);
		cfs.add(expression);
		cfs.add(endNode);
		cNode.connectInternalSucc(cfs);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(endNode);

		endNode.addJumpToken(jumptoken);

		return cNode;
	}

}
