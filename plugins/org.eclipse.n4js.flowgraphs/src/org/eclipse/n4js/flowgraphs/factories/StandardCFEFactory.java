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
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class StandardCFEFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ControlFlowElement cfe) {
		return buildComplexNode(astpp, cfe, true);
	}

	static ComplexNode buildComplexNodeHidden(ReentrantASTIterator astpp, ControlFlowElement cfe) {
		return buildComplexNode(astpp, cfe, false);
	}

	private static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ControlFlowElement cfe,
			boolean isRepresenting) {

		ComplexNode cNode = new ComplexNode(astpp.container(), cfe);

		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), cfe);

		List<Node> argumentNodes = new LinkedList<>();
		List<Node> args = CFEChildren.get(astpp, cfe);
		for (Node argNode : args) {
			argumentNodes.add(argNode);
		}

		Node exitNode;
		String extName;
		if (argumentNodes.isEmpty()) {
			entryNode = null;
			extName = NodeNames.ENTRY_EXIT;
		} else {
			extName = NodeNames.EXIT;
		}
		if (isRepresenting) {
			exitNode = new RepresentingNode(extName, astpp.pos(), cfe);
		} else {
			exitNode = new HelperNode(extName, astpp.pos(), cfe);
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
