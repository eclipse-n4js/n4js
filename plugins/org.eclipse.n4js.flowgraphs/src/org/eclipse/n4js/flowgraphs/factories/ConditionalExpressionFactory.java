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
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ConditionalExpression;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s. */
class ConditionalExpressionFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ConditionalExpression condExpr) {
		ComplexNode cNode = new ComplexNode(astpp.container(), condExpr);

		HelperNode entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), condExpr);
		Node conditionNode = new DelegatingNode("condition", astpp.pos(), condExpr, condExpr.getExpression());
		astpp.visitUtil(conditionNode.getDelegatedControlFlowElement());
		Node thenNode = new DelegatingNode("then", astpp.pos(), condExpr, condExpr.getTrueExpression());
		astpp.visitUtil(thenNode.getDelegatedControlFlowElement());
		Node elseNode = new DelegatingNode("else", astpp.pos(), condExpr, condExpr.getFalseExpression());
		astpp.visitUtil(elseNode.getDelegatedControlFlowElement());
		Node exitNode = new RepresentingNode(EXIT_NODE, astpp.pos(), condExpr);

		cNode.addNode(entryNode);
		cNode.addNode(conditionNode);
		cNode.addNode(thenNode);
		cNode.addNode(elseNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(conditionNode);
		nodes.add(thenNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		nodes.clear();
		nodes.add(conditionNode);
		nodes.add(elseNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
