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
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Used for all non-statements. Children nodes are retrieved from
 * {@link CFEChildren#get(ReentrantASTIterator, ControlFlowElement)}.
 */
class StandardCFEFactory {
	static final String ENTRY_NODE = "entry";
	static final String EXIT_NODE = "exit";
	static final String ENTRY_EXIT_NODE = "entryExit";

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ControlFlowElement cfe) {
		return buildComplexNode(astpp, cfe, true);
	}

	static ComplexNode buildComplexNodeHidden(ReentrantASTIterator astpp, ControlFlowElement cfe) {
		return buildComplexNode(astpp, cfe, false);
	}

	private static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ControlFlowElement cfe,
			boolean isRepresenting) {
		ComplexNode cNode = new ComplexNode(astpp.container(), cfe);
		HelperNode entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), cfe);

		List<Node> argumentNodes = new LinkedList<>();
		List<Node> args = CFEChildren.get(astpp, cfe);
		for (Node argNode : args) {
			argumentNodes.add(argNode);
		}

		Node exitNode;
		String extName;
		int extID;
		if (argumentNodes.isEmpty()) {
			entryNode = null;
			extName = ENTRY_EXIT_NODE;
			extID = astpp.pos();
		} else {
			extName = EXIT_NODE;
			extID = argumentNodes.get(argumentNodes.size() - 1).id + 1;
		}
		if (isRepresenting) {
			exitNode = new RepresentingNode(extName, extID, cfe);
		} else {
			exitNode = new HelperNode(extName, extID, cfe);
		}

		cNode.addNode(entryNode);
		for (Node arg : argumentNodes)
			cNode.addNode(arg);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(argumentNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		if (argumentNodes.isEmpty()) {
			cNode.setEntryNode(exitNode);
		} else {
			cNode.setEntryNode(entryNode);
		}
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
