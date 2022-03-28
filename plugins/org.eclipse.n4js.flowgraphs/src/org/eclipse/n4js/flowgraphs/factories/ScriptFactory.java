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
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link Script}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class ScriptFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, Script script) {
		ComplexNode cNode = new ComplexNode(astpp.container(), script);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), script);
		List<Node> scriptNodes = new LinkedList<>();

		EList<ScriptElement> scriptElems = script.getScriptElements();
		for (int n = 0; n < scriptElems.size(); n++) {
			ScriptElement scriptElem = getScriptElementAt(script, n);
			if (isControlFlowStatement(scriptElem)) {
				Node blockNode = DelegatingNodeFactory.create(astpp, "stmt_" + n, script, (Statement) scriptElem);
				scriptNodes.add(blockNode);
			}
		}

		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), script);

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
			if (expElem instanceof VariableStatement) {
				scriptElement = expElem;
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
