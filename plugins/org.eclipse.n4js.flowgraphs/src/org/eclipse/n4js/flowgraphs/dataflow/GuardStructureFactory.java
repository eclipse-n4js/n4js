/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 *
 */
public class GuardStructureFactory {
	final private GuardFactory guardFactory;

	GuardStructureFactory(SymbolFactory symbolFactory) {
		this.guardFactory = new GuardFactory(symbolFactory);
	}

	@SuppressWarnings("incomplete-switch")
	GuardStructure create(ControlFlowEdge edge) {
		boolean negate = false;
		switch (edge.cfType) {
		case IfFalse:
		case LoopExit:
			negate = true;
			//$FALL-THROUGH$
		case IfTrue:
		case LoopEnter:
		case LoopReenter:
			Expression condition = getCondition(edge);
			if (condition != null) {
				return new GuardStructure(guardFactory, condition, negate);
			}
		}
		return null;
	}

	static private Expression getCondition(ControlFlowEdge edge) {
		Expression condition = null;

		Node previousNode = edge.start;
		ControlFlowElement previousCFE = previousNode.getControlFlowElement();
		EObject parent = previousCFE.eContainer();
		System.out.println(parent.getClass().getSimpleName() + " : " + edge.cfType);
		if (parent instanceof IfStatement) {
			IfStatement is = (IfStatement) parent;
			condition = is.getExpression();
		}
		if (parent instanceof WhileStatement) {
			WhileStatement ws = (WhileStatement) parent;
			condition = ws.getExpression();
		}
		if (parent instanceof DoStatement) {
			DoStatement ws = (DoStatement) parent;
			condition = ws.getExpression();
		}
		if (parent instanceof ForStatement) {
			ForStatement ws = (ForStatement) parent;
			condition = ws.getExpression();
		}
		return condition;
	}

}
