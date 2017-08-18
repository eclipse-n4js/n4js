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
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;

class VariableDeclaratorFactory {

	static ComplexNode buildComplexNode(VariableDeclaration varDecl) {
		ComplexNode cNode = new ComplexNode(varDecl);

		Node entryNode = new HelperNode("entry", varDecl);
		Node exitNode = new RepresentingNode("exit", varDecl);
		Node initNode = null;

		if (varDecl.getExpression() != null) {
			initNode = new DelegatingNode("initializer", varDecl, varDecl.getExpression());
		}

		cNode.addNode(entryNode);
		cNode.addNode(initNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(initNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		// entryNode.setDeclaratedTokens(varDecl); // TODO: Declare-Effect

		return cNode;
	}

}
