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
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;

class BindingPatternFactory {

	static ComplexNode buildComplexNode(ArrayBindingPattern abp) {
		ComplexNode cNode = new ComplexNode(abp);

		Node entryNode = new HelperNode("entry", abp);
		Node exitNode = new HelperNode("exit", abp);
		List<Node> declNodes = new LinkedList<>();
		Node initNode = null;

		for (int i = 0; i < abp.getElements().size(); i++) {
			BindingElement be = abp.getElements().get(i);
			DelegatingNode declNode = new DelegatingNode("elem_" + i, abp, be);
			declNodes.add(declNode);
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

	static ComplexNode buildComplexNode(ObjectBindingPattern obp) {
		ComplexNode cNode = new ComplexNode(obp);

		Node entryNode = new HelperNode("entry", obp);
		Node exitNode = new HelperNode("exit", obp);
		List<Node> propertyValueNodes = new LinkedList<>();
		Node initNode = null;

		for (int i = 0; i < obp.getProperties().size(); i++) {
			BindingProperty bp = obp.getProperties().get(i);
			BindingElement be = bp.getValue();
			if (be != null) {
				DelegatingNode declNode = new DelegatingNode("init_" + i, obp, be);
				propertyValueNodes.add(declNode);
			}
		}

		cNode.addNode(entryNode);
		for (Node declNode : propertyValueNodes)
			cNode.addNode(declNode);
		cNode.addNode(initNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(propertyValueNodes);
		nodes.add(initNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		// entryNode.setDeclaratedTokens(varDecl); // TODO: Declare-Effect

		return cNode;
	}

}
