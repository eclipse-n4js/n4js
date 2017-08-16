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
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.CaseClause;

class AbstractCaseClauseFactory {

	static ComplexNode buildComplexNode(AbstractCaseClause stmt) {
		ComplexNode cNode = new ComplexNode(stmt);

		Node entryNode = new HelperNode("entry", stmt);
		Node endNode = new DelegatingNode("exit", stmt);
		Node comGroup = null;

		if (stmt instanceof CaseClause) {
			CaseClause caseClause = (CaseClause) stmt;
			comGroup = new DelegatingNode("condition", caseClause.getExpression());
		}

		cNode.addNode(entryNode);
		cNode.addNode(comGroup);
		cNode.addNode(endNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(comGroup);
		nodes.add(endNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(endNode);

		return cNode;
	}

}
