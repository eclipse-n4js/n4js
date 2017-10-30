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
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.Statement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link org.eclipse.n4js.n4JS.Block}s. */
class BlockFactory {

	static ComplexNode buildComplexNode(org.eclipse.n4js.n4JS.Block block) {
		ComplexNode cNode = new ComplexNode(block);

		Node entryNode = new HelperNode(ENTRY_NODE, block);
		Node exitNode = new HelperNode(EXIT_NODE, block);
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

		if (FGUtils.isCFContainer(block)) {
			exitNode.addCatchToken(new CatchToken(ControlFlowType.CatchesAll));
		}

		return cNode;
	}

}
