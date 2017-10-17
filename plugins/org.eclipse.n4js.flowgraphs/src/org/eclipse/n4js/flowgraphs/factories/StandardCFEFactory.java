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
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.xtext.xbase.lib.Pair;

/** Used for all non-statements. Children nodes are retrieved from {@link CFEChildren#get(ControlFlowElement)}. */
class StandardCFEFactory {
	static final String ENTRY_NODE = "entry";
	static final String EXIT_NODE = "exit";
	static final String ENTRY_EXIT_NODE = "entryExit";

	static ComplexNode buildComplexNode(ControlFlowElement cfe) {
		return buildComplexNode(cfe, true);
	}

	static ComplexNode buildComplexNodeHidden(ControlFlowElement cfe) {
		return buildComplexNode(cfe, false);
	}

	private static ComplexNode buildComplexNode(ControlFlowElement cfe, boolean isRepresenting) {
		ComplexNode cNode = new ComplexNode(cfe);

		List<Node> argumentNodes = new LinkedList<>();

		List<Pair<String, ControlFlowElement>> args = CFEChildren.get(cfe);
		for (Pair<String, ControlFlowElement> entry : args) {
			String nodeName = entry.getKey();
			ControlFlowElement child = entry.getValue();
			Node argNode = new DelegatingNode(nodeName, cfe, child);
			argumentNodes.add(argNode);
		}

		HelperNode entryNode = null;
		String exitNodeName = ENTRY_EXIT_NODE;
		// if (!argumentNodes.isEmpty()) {
		entryNode = new HelperNode(ENTRY_NODE, cfe);
		exitNodeName = EXIT_NODE;
		// }
		Node exitNode = (isRepresenting) ? new RepresentingNode(exitNodeName, cfe) : new HelperNode(exitNodeName, cfe);

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
			// cNode.setEntryNode(exitNode);
		} else {
		}
		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
