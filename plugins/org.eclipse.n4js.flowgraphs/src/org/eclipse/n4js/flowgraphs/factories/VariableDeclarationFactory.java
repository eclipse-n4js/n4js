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
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class VariableDeclarationFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, VariableDeclaration vd) {
		ComplexNode cNode = new ComplexNode(astpp.container(), vd);

		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), vd);
		Node expressionNode = null;

		if (vd.getExpression() != null) {
			expressionNode = DelegatingNodeFactory.create(astpp, NodeNames.EXPRESSION, vd, vd.getExpression());
		}
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), vd);

		cNode.addNode(entryNode);
		cNode.addNode(expressionNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(expressionNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		if (vd.eContainer() instanceof BindingElement) {
			// In this case, the expression is the default value
			// TODO: improve this: find out if this is true or false
			cNode.connectInternalSucc(entryNode, exitNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
