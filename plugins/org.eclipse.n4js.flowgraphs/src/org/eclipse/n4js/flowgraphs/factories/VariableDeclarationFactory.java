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
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link ConditionalExpression}s. */
class VariableDeclarationFactory {

	static ComplexNode buildComplexNode(VariableDeclaration vd) {
		ComplexNode cNode = new ComplexNode(vd);

		HelperNode entryNode = new HelperNode(ENTRY_NODE, vd);
		Node exitNode = new RepresentingNode(EXIT_NODE, vd);
		Node expressionNode = null;

		if (vd.getExpression() != null) {
			expressionNode = new DelegatingNode("expression", vd, vd.getExpression());
		}

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
			cNode.connectInternalSucc(entryNode, exitNode);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
