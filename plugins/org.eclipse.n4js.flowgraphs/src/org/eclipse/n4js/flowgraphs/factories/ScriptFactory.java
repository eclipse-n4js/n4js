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

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;

class ScriptFactory {

	static ComplexNode buildComplexNode(Script script) {
		ComplexNode cNode = new ComplexNode(script);

		Node entryNode = new HelperNode("entry", script);
		Node exitNode = new HelperNode("exit", script);
		List<Node> scriptNodes = new LinkedList<>();

		EList<ScriptElement> scriptElems = script.getScriptElements();
		for (int i = 0; i < scriptElems.size(); i++) {
			ScriptElement scriptElem = scriptElems.get(i);
			if (isControlFlowStatement(scriptElem)) {
				Node blockNode = new DelegatingNode("stmt_" + i, script, (Statement) scriptElem);
				scriptNodes.add(blockNode);
			}
		}

		cNode.addNode(entryNode);
		for (Node scriptNode : scriptNodes)
			cNode.addNode(scriptNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(scriptNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

	private static boolean isControlFlowStatement(ScriptElement scriptElem) {
		boolean isControlFlowStatement = scriptElem instanceof Statement;
		isControlFlowStatement &= !(scriptElem instanceof FunctionDeclaration);
		return isControlFlowStatement;
	}

}
