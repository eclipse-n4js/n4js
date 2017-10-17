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
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.DefaultClause;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;

/** Creates instances of {@link ComplexNode}s for AST elements of type {@link SwitchStatement}s. */
class SwitchFactory {

	static ComplexNode buildComplexNode(SwitchStatement switchStmt) {
		ComplexNode cNode = new ComplexNode(switchStmt);

		Node entryNode = new HelperNode(ENTRY_NODE, switchStmt);
		Node exitNode = new HelperNode(EXIT_NODE, switchStmt);
		Node pivotNode = new DelegatingNode("pivot", switchStmt, switchStmt.getExpression());

		cNode.addNode(entryNode);
		cNode.addNode(pivotNode);

		List<Node> caseNodes = new LinkedList<>();
		// Assumption: clauses are ordered analog to the source code
		List<AbstractCaseClause> caseClauses = switchStmt.getCases();
		for (int i = 0; i < caseClauses.size(); i++) {
			AbstractCaseClause cc = caseClauses.get(i);
			Node caseNode = null;
			if (cc instanceof CaseClause) {
				caseNode = new DelegatingNode("case_" + i, switchStmt, cc);
			}
			if (cc instanceof DefaultClause) {
				caseNode = new DelegatingNode("default", switchStmt, cc);
			}
			caseNodes.add(caseNode);
			cNode.addNode(caseNode);
		}
		cNode.addNode(exitNode);

		List<Node> cfs = new LinkedList<>();
		cfs.add(entryNode);
		cfs.add(pivotNode);
		cNode.connectInternalSucc(cfs);

		for (Node cnf : caseNodes) {
			cNode.connectInternalSucc(pivotNode, cnf);
		}

		cfs.clear();
		cfs.addAll(caseNodes);
		cfs.add(exitNode);
		cNode.connectInternalSucc(cfs); // See {@link JumpFactory} how {@link BreakStatements} modify the control flow

		if (switchStmt.getDefaultClause() == null)
			cNode.connectInternalSucc(pivotNode, exitNode);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		LabelledStatement lblStmt = ASTUtils.getLabelledStatement(switchStmt);
		exitNode.addCatchToken(new CatchToken(ControlFlowType.Break, lblStmt));

		return cNode;
	}

}
