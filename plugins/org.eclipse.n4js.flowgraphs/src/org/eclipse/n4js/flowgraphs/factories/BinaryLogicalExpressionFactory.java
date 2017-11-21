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

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s. */
class BinaryLogicalExpressionFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, BinaryLogicalExpression lbExpr) {
		ComplexNode cNode = new ComplexNode(astpp.container(), lbExpr);

		HelperNode entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), lbExpr);
		Node lhsNode = DelNodeFactory.create(astpp, "lhs", lbExpr, lbExpr.getLhs());
		Node rhsNode = DelNodeFactory.create(astpp, "rhs", lbExpr, lbExpr.getRhs());
		Node exitNode = new RepresentingNode(EXIT_NODE, astpp.pos(), lbExpr);

		cNode.addNode(entryNode);
		cNode.addNode(lhsNode);
		cNode.addNode(rhsNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(lhsNode);
		nodes.add(rhsNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);
		cNode.connectInternalSucc(lhsNode, exitNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
