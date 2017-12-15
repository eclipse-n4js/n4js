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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.DefaultClause;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;

/**
 * Creates instances of {@link ComplexNode}s for AST elements of type {@link SwitchStatement}s.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class SwitchFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, SwitchStatement switchStmt) {
		ComplexNode cNode = new ComplexNode(astpp.container(), switchStmt);

		Node entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), switchStmt);
		Node pivotNode = DelegatingNodeFactory.createOrHelper(astpp, NodeNames.PIVOT, switchStmt,
				switchStmt.getExpression());

		cNode.addNode(entryNode);
		cNode.addNode(pivotNode);

		List<Node> caseNodes = new LinkedList<>();
		// Assumption: clauses are ordered analog to the source code
		List<AbstractCaseClause> caseClauses = switchStmt.getCases();
		for (int n = 0; n < caseClauses.size(); n++) {
			AbstractCaseClause cc = caseClauses.get(n);
			Node caseNode = null;
			if (cc instanceof CaseClause) {
				caseNode = DelegatingNodeFactory.create(astpp, "case_" + n, switchStmt, cc);
			}
			if (cc instanceof DefaultClause) {
				caseNode = DelegatingNodeFactory.create(astpp, NodeNames.DEFAULT, switchStmt, cc);
			}
			caseNodes.add(caseNode);
			cNode.addNode(caseNode);
		}
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), switchStmt);
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
