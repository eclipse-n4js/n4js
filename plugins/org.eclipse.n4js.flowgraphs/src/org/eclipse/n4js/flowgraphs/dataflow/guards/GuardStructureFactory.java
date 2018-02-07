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
package org.eclipse.n4js.flowgraphs.dataflow.guards;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 * This factory creates {@link GuardStructure}s for given conditions. While doing this, also {@link Guard}s are created,
 * which refer to their guarded {@link Symbol}s.
 */
public class GuardStructureFactory {

	/**
	 * Creates {@link GuardStructure}s in case the given edge is connected to a {@link ControlFlowElement} that can
	 * contain a {@link GuardStructure}.
	 *
	 * @return {@link GuardStructure} or null
	 */
	@SuppressWarnings("incomplete-switch")
	static public GuardStructure create(ControlFlowEdge edge) {
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
				return new GuardStructure(condition, negate);
			}
		}
		return null;
	}

	/** @return the top {@link Expression} of a {@link GuardStructure}, or null. */
	static private Expression getCondition(ControlFlowEdge edge) {
		Expression condition = null;

		Node previousNode = edge.start;
		ControlFlowElement previousCFE = previousNode.getControlFlowElement();
		EObject parent = previousCFE.eContainer();

		if (parent instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) parent;
			condition = ce.getExpression();

		} else if (parent instanceof BinaryLogicalExpression) {
			BinaryLogicalExpression ble = (BinaryLogicalExpression) parent;
			condition = ble.getLhs();

		} else if (parent instanceof IfStatement) {
			IfStatement is = (IfStatement) parent;
			condition = is.getExpression();

		} else if (parent instanceof WhileStatement) {
			WhileStatement ws = (WhileStatement) parent;
			condition = ws.getExpression();

		} else if (parent instanceof DoStatement) {
			DoStatement ws = (DoStatement) parent;
			condition = ws.getExpression();

		} else if (parent instanceof ForStatement) {
			ForStatement ws = (ForStatement) parent;
			condition = ws.getExpression();
		}
		return condition;
	}

}
