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
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;

class DeclarationStatementFactory {

	static ComplexNode buildComplexNode(VariableStatement varDeclStmt) {
		ComplexNode cNode = new ComplexNode(varDeclStmt);

		Node entryNode = new Node("entry", varDeclStmt);
		Node exitNode = new Node("exit", varDeclStmt);

		List<Node> varDeclNodes = new LinkedList<>();
		for (VariableDeclaration varDecl2 : varDeclStmt.getVarDecl()) {
			Node varDeclNode = new Node("declaration_" + varDeclStmt.getVarDecl().indexOf(varDecl2), varDecl2);
			varDeclNodes.add(varDeclNode);
		}

		cNode.addNode(entryNode);
		for (Node varDeclNode : varDeclNodes)
			cNode.addNode(varDeclNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(varDeclNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
