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
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.TryStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link TryStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class TryFactory {

	static final String CATCH_NODE_NAME = "catch";
	static final String FINALLY_NODE_NAME = "finally";

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, TryStatement tryStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), tryStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, astpp.pos(), tryStmt);
		Node tryNode = null;
		Node catchNode = null;
		Node finallyNode = null;

		if (tryStmt.getBlock() != null) {
			tryNode = DelegatingNodeFactory.create(astpp, "try", tryStmt, tryStmt.getBlock());
		}

		if (tryStmt.getCatch() != null && tryStmt.getCatch().getBlock() != null) {
			CatchBlock catchClause = tryStmt.getCatch();
			CatchToken ct = new CatchToken(ControlFlowType.Throw);
			catchNode = DelegatingNodeFactory.create(astpp, CATCH_NODE_NAME, tryStmt, catchClause.getBlock());
			catchNode.addCatchToken(ct);
		}

		if (tryStmt.getFinally() != null && tryStmt.getFinally().getBlock() != null) {
			FinallyBlock finallyElem = tryStmt.getFinally();
			CatchToken ct = new CatchToken(ControlFlowType.CatchesAll);
			finallyNode = DelegatingNodeFactory.create(astpp, FINALLY_NODE_NAME, tryStmt, finallyElem.getBlock());
			finallyNode.addCatchToken(ct);
		}

		Node exitNode = new HelperNode(EXIT_NODE, astpp.pos(), tryStmt);

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

		cNode.connectInternalSucc(entryNode, catchNode); // TODO: Consider to use a special edge type 'unsound'
		LinkedList<Node> parts = ListUtils.filterNulls(finallyNode, exitNode);
		Node tgtFrgmt = parts.getFirst();
		cNode.connectInternalSucc(tryNode, catchNode); // TODO: Consider to use a special edge type 'unsound'
		cNode.connectInternalSucc(catchNode, tgtFrgmt);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
