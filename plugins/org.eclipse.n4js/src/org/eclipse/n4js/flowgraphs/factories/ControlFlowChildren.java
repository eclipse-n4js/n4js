/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PrimaryExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.YieldExpression;

final class ControlFlowChildren extends Dispatcher {

	public static List<Expression> get(Expression expr) {
		try {
			return dispatch("_get", expr);
		} catch (NoDispatchMethodFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	static List<Expression> _get(AdditiveExpression ae) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ae.getLhs());
		cfc.add(ae.getRhs());
		return cfc;
	}

	static List<Expression> _get(AssignmentExpression ae) {
		List<Expression> cfc = new LinkedList<>();
		Expression lhs = ae.getLhs();
		cfc.addAll(getCFChildren(lhs));
		cfc.add(ae.getRhs());
		return cfc;
	}

	static List<Expression> _get(AwaitExpression ae) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ae.getExpression());
		return cfc;
	}

	static List<Expression> _get(BinaryBitwiseExpression bbe) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(bbe.getLhs());
		cfc.add(bbe.getRhs());
		return cfc;
	}

	static List<Expression> _get(BinaryLogicalExpression ble) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ble.getLhs());
		cfc.add(ble.getRhs());
		return cfc;
	}

	static List<Expression> _get(CastExpression ce) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ce.getExpression());
		return cfc;
	}

	static List<Expression> _get(CommaExpression ce) {
		List<Expression> cfc = new LinkedList<>();
		cfc.addAll(ce.getExprs());
		return cfc;
	}

	static List<Expression> _get(ConditionalExpression ce) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ce.getExpression());
		cfc.add(ce.getTrueExpression());
		cfc.add(ce.getFalseExpression());
		return cfc;
	}

	static List<Expression> _get(EqualityExpression ee) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ee.getLhs());
		cfc.add(ee.getRhs());
		return cfc;
	}

	static List<Expression> _get(IndexedAccessExpression iae) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(iae.getTarget());
		cfc.add(iae.getIndex());
		return cfc;
	}

	static List<Expression> _get(NewExpression ne) {
		List<Expression> cfc = new LinkedList<>();
		for (Argument arg : ne.getArguments())
			cfc.add(arg.getExpression());
		return cfc;
	}

	static List<Expression> _get(@SuppressWarnings("unused") NewTarget nt) {
		return Collections.emptyList();
	}

	static List<Expression> _get(ParameterizedCallExpression pce) {
		List<Expression> cfc = new LinkedList<>();
		if (pce.getReceiver() != null)
			cfc.add(pce.getReceiver());
		cfc.add(pce.getTarget());
		for (Argument arg : pce.getArguments())
			cfc.add(arg.getExpression());
		return cfc;
	}

	static List<Expression> _get(ParameterizedPropertyAccessExpression ppae) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ppae.getTarget());
		return cfc;
	}

	static List<Expression> _get(PostfixExpression pe) {
		List<Expression> cfc = new LinkedList<>();
		Expression expr = pe.getExpression();
		cfc.addAll(getCFChildren(expr));
		return cfc;
	}

	static List<Expression> _get(@SuppressWarnings("unused") PrimaryExpression pe) {
		return Collections.emptyList();
	}

	static List<Expression> _get(PromisifyExpression pe) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(pe.getExpression());
		return cfc;
	}

	// ReferencingElementExpression_IM

	static List<Expression> _get(RelationalExpression re) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(re.getLhs());
		cfc.add(re.getRhs());
		return cfc;
	}

	static List<Expression> _get(ShiftExpression se) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(se.getLhs());
		cfc.add(se.getRhs());
		return cfc;
	}

	static List<Expression> _get(@SuppressWarnings("unused") TaggedTemplateString tts) {
		return Collections.emptyList();
	}

	static List<Expression> _get(UnaryExpression ue) {
		List<Expression> cfc = new LinkedList<>();
		cfc.add(ue.getExpression());
		return cfc;
	}

	static List<Expression> _get(YieldExpression ye) {
		List<Expression> cfc = new LinkedList<>();
		if (ye.getExpression() != null)
			cfc.add(ye.getExpression());
		return cfc;
	}

	private static List<Expression> getCFChildren(Expression accessExpr) {
		List<Expression> cfChildren = new LinkedList<>();
		if (accessExpr instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) accessExpr;
			cfChildren.add(ppae.getTarget());

		} else if (accessExpr instanceof IndexedAccessExpression) {
			IndexedAccessExpression iae = (IndexedAccessExpression) accessExpr;
			cfChildren.add(iae.getTarget());
			cfChildren.add(iae.getIndex());

		} else {
			cfChildren.add(accessExpr);
		}
		return cfChildren;
	}
}
