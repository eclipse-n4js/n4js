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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.TryStatement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link TryStatement}s. */
class TryFactory {

	static final String CATCH_NODE_NAME = "catch";
	static final String FINALLY_NODE_NAME = "finally";

	static ComplexNode buildComplexNode(TryStatement tryStmt) {
		ComplexNode cNode = new ComplexNode(tryStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, tryStmt);
		Node exitNode = new HelperNode(EXIT_NODE, tryStmt);
		Node tryNode = null;
		Node catchNode = null;
		Node finallyNode = null;

		if (tryStmt.getBlock() != null) {
			tryNode = new DelegatingNode("try", tryStmt, tryStmt.getBlock());
		}

		if (tryStmt.getCatch() != null) {
			CatchBlock catchClause = tryStmt.getCatch();
			CatchToken ct = new CatchToken(ControlFlowType.Throw);
			catchNode = new DelegatingNode(CATCH_NODE_NAME, tryStmt, catchClause.getBlock());
			catchNode.addCatchToken(ct);
		}

		if (tryStmt.getFinally() != null) {
			CatchToken ct = new CatchToken(ControlFlowType.CatchesAll);
			finallyNode = new DelegatingNode(FINALLY_NODE_NAME, tryStmt, tryStmt.getFinally().getBlock());
			finallyNode.addCatchToken(ct);
		}

		cNode.addNode(entryNode);
		cNode.addNode(tryNode);
		cNode.addNode(catchNode);
		cNode.addNode(finallyNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(tryNode);
		nodes.add(finallyNode);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		LinkedList<Node> parts = ListUtils.filterNulls(finallyNode, exitNode);
		Node tgtFrgmt = parts.getFirst();
		cNode.connectInternalSucc(catchNode, tgtFrgmt);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
