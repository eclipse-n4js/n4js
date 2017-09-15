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

class VariableDeclarationFactory {

	static ComplexNode buildComplexNode(VariableDeclaration varDOB) {
		ComplexNode cNode = new ComplexNode(varDOB);

		Node entryNode = new HelperNode("entry", varDOB);
		Node exitNode = new RepresentingNode("exit", varDOB);
		List<Node> declNodes = new LinkedList<>();
		Node initNode = null;

		if (varDOB.getExpression() != null) {
			initNode = new DelegatingNode("initializer", varDOB, varDOB.getExpression());
		}

		cNode.addNode(entryNode);
		for (Node declNode : declNodes)
			cNode.addNode(declNode);
		cNode.addNode(initNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(declNodes);
		nodes.add(initNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		// entryNode.setDeclaratedTokens(varDecl); // TODO: Declare-Effect

		return cNode;
	}

}
