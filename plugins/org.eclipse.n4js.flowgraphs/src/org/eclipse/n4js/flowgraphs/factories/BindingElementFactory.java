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
import org.eclipse.n4js.n4JS.BindingElement;

class BindingElementFactory {

	static ComplexNode buildComplexNode(BindingElement be) {
		ComplexNode cNode = new ComplexNode(be);

		Node entryNode = new HelperNode("entry", be);
		Node exitNode = new HelperNode("exit", be);
		Node declNode = null;
		Node initNode = null;
		Node nestedPatternNode = null;

		if (be.getNestedPattern() != null) {
			nestedPatternNode = new DelegatingNode("nestedPattern", be, be.getNestedPattern());
		}
		if (be.getVarDecl() != null) {
			declNode = new DelegatingNode("declaration", be, be.getVarDecl());
		}
		if (be.getExpression() != null) {
			initNode = new DelegatingNode("initializer", be, be.getExpression());
		}

		cNode.addNode(entryNode);
		cNode.addNode(nestedPatternNode);
		cNode.addNode(declNode);
		cNode.addNode(initNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(nestedPatternNode);
		nodes.add(declNode);
		nodes.add(initNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		// entryNode.setDeclaratedTokens(varDecl); // TODO: Declare-Effect

		return cNode;
	}

}
