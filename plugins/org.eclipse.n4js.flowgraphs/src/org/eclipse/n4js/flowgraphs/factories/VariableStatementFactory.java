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
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link VariableStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class VariableStatementFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, VariableStatement varDeclStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), varDeclStmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), varDeclStmt);
		List<Node> varDeclNodes = new LinkedList<>();
		for (int n = 0; n < varDeclStmt.getVarDeclsOrBindings().size(); n++) {
			VariableDeclarationOrBinding varDOB = varDeclStmt.getVarDeclsOrBindings().get(n);
			Node varDeclNode = DelegatingNodeFactory.create(astpp, "declaration_" + n, varDeclStmt, varDOB);
			varDeclNodes.add(varDeclNode);
		}
		Node exitNode = new RepresentingNode(NodeNames.EXIT, astpp.pos(), varDeclStmt);

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
