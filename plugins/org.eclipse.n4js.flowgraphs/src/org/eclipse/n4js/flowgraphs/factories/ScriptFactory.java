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

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link Script}s. */
class ScriptFactory {

	static ComplexNode buildComplexNode(Script script) {
		ComplexNode cNode = new ComplexNode(script);

		Node entryNode = new HelperNode(ENTRY_NODE, script);
		Node exitNode = new HelperNode(EXIT_NODE, script);
		List<Node> scriptNodes = new LinkedList<>();

		EList<ScriptElement> scriptElems = script.getScriptElements();
		for (int i = 0; i < scriptElems.size(); i++) {
			ScriptElement scriptElem = getScriptElementAt(script, i);
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

		exitNode.addCatchToken(new CatchToken(ControlFlowType.CatchesAll));

		return cNode;
	}

	private static ScriptElement getScriptElementAt(Script script, int i) {
		EList<ScriptElement> scriptElems = script.getScriptElements();
		ScriptElement scriptElement = scriptElems.get(i);
		if (scriptElement instanceof ExportDeclaration) {
			ExportableElement expElem = ((ExportDeclaration) scriptElement).getExportedElement();
			if (expElem instanceof ExportedVariableStatement) {
				scriptElement = (ExportedVariableStatement) expElem;
			}
		}
		return scriptElement;
	}

	private static boolean isControlFlowStatement(ScriptElement scriptElem) {
		boolean isControlFlowStatement = scriptElem instanceof Statement;
		isControlFlowStatement &= !(scriptElem instanceof FunctionDeclaration);
		return isControlFlowStatement;
	}

}
