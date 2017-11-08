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
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.Statement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link AbstractCaseClause}s. */
class AbstractCaseClauseFactory {

	static ComplexNode buildComplexNode(AbstractCaseClause abstrCaseClause) {
		int intPos = 0;
		ComplexNode cNode = new ComplexNode(abstrCaseClause);

		Node entryNode = new HelperNode(ENTRY_NODE, intPos++, abstrCaseClause);
		List<Node> stmtNodes = new LinkedList<>();
		Node caseConditionNode = null;

		if (abstrCaseClause instanceof CaseClause) {
			CaseClause caseClause = (CaseClause) abstrCaseClause;
			caseConditionNode = new DelegatingNode("condition", intPos++, caseClause, caseClause.getExpression());
		}

		EList<Statement> stmts = abstrCaseClause.getStatements();
		for (int i = 0; i < stmts.size(); i++) {
			Statement stmt = stmts.get(i);
			Node blockNode = new DelegatingNode("stmt_" + i, intPos++, abstrCaseClause, stmt);
			stmtNodes.add(blockNode);
		}
		Node exitNode = new HelperNode(EXIT_NODE, intPos++, abstrCaseClause);

		cNode.addNode(entryNode);
		cNode.addNode(caseConditionNode);
		for (Node blockNode : stmtNodes)
			cNode.addNode(blockNode);
		cNode.addNode(exitNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(caseConditionNode);
		nodes.addAll(stmtNodes);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		return cNode;
	}

}
