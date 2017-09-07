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
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;

class DeclarationStatementFactory {

	static ComplexNode buildComplexNode(VariableStatement varDeclStmt) {
		ComplexNode cNode = new ComplexNode(varDeclStmt);

		Node entryNode = new HelperNode("entry", varDeclStmt);
		Node exitNode = new RepresentingNode("exit", varDeclStmt);

		List<Node> varDeclNodes = new LinkedList<>();
		for (int i = 0; i < varDeclStmt.getVarDeclsOrBindings().size(); i++) {
			VariableDeclarationOrBinding varDOB = varDeclStmt.getVarDeclsOrBindings().get(i);
			Node varDeclNode = new DelegatingNode("declaration_" + i, varDeclStmt, varDOB);
			varDeclNodes.add(varDeclNode);
		}

		cNode.addNode(entryNode);
		for (Node varDeclNode : varDeclNodes)
			cNode.addNode(varDeclNode);
		cNode.addNode(exitNode);

		for (Node varDeclNode : varDeclNodes) {
			List<Node> nodes = new LinkedList<>();
			nodes.add(entryNode);
			nodes.add(varDeclNode);
			nodes.add(exitNode);
			cNode.connectInternalSucc(nodes);
		}

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);
		cNode.setRepresentNode(exitNode);

		return cNode;
	}

}
