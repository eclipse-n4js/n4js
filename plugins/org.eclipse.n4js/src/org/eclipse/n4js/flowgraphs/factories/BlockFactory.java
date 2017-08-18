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
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.JumpType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.Statement;

class BlockFactory {

	static ComplexNode buildComplexNode(org.eclipse.n4js.n4JS.Block block) {
		ComplexNode cNode = new ComplexNode(block);

		Node entryNode = new HelperNode("entry", block);
		Node exitNode = new HelperNode("exit", block);
		List<Node> blockNodes = new LinkedList<>();

		EList<Statement> stmts = block.getStatements();
		for (int i = 0; i < stmts.size(); i++) {
			Statement stmt = stmts.get(i);
			Node blockNode = new DelegatingNode("stmt_" + i, block, stmt);
			blockNodes.add(blockNode);
		}

		cNode.addNode(entryNode);
		for (Node blockNode : blockNodes)
			cNode.addNode(blockNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.addAll(blockNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		if (block.eContainer() instanceof GenericDeclaration) {
			exitNode.addCatchToken(new CatchToken(JumpType.Return));
			exitNode.addCatchToken(new CatchToken(JumpType.CatchesRuntimeExceptions));
		}

		return cNode;
	}

}
