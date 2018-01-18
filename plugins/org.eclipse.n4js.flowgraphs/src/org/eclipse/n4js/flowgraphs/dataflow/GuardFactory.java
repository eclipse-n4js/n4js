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

import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;

/**
 *
 */
public class GuardFactory {
	final private SymbolFactory symbolFactory;

	GuardFactory(SymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}

	Guard create(Expression expr, boolean negateTree) {
		if (expr instanceof EqualityExpression) {
			EqualityExpression eqe = (EqualityExpression) expr;
			EqualityOperator operation = eqe.getOp();
			boolean sameEqual = operation == EqualityOperator.EQ || operation == EqualityOperator.SAME;
			boolean sameEqualNot = operation == EqualityOperator.NEQ || operation == EqualityOperator.NSAME;
			if (sameEqual || sameEqualNot) {
				return createGuardForEquality(negateTree, eqe, sameEqualNot);
			}

		} else if (expr instanceof RelationalExpression) {

		} else if (expr instanceof ParameterizedCallExpression) {

		} else {
			// in case of:
			// val a = f();
			// if (a) {...}
		}
		return null;
	}

	private Guard createGuardForEquality(boolean negateTree, EqualityExpression eqe, boolean sameEqualNot) {
		Expression lhs = eqe.getLhs();
		Expression rhs = eqe.getRhs();
		Symbol ls = symbolFactory.create(lhs);
		Symbol rs = symbolFactory.create(rhs);
		boolean lNUZ = ls != null && (ls.isNullLiteral() || ls.isUndefinedLiteral() || ls.isZeroLiteral());
		boolean rNUZ = rs != null && (rs.isNullLiteral() || rs.isUndefinedLiteral() || rs.isZeroLiteral());

		// var == null|undefined|0
		if (ls != null && rs != null) {
			if (lNUZ && !rNUZ) {
				return createGuardForNUZ(eqe, negateTree, sameEqualNot, ls, rs);
			}
			if (!lNUZ && rNUZ) {
				return createGuardForNUZ(eqe, negateTree, sameEqualNot, rs, ls);
			}
		}

		UnaryOperator luo = lhs instanceof UnaryExpression ? ((UnaryExpression) lhs).getOp() : null;
		UnaryOperator ruo = rhs instanceof UnaryExpression ? ((UnaryExpression) rhs).getOp() : null;

		// typeof foo != 'undefined'
		if (luo == UnaryOperator.TYPEOF) {
			return createGuardForTypeof((UnaryExpression) lhs, negateTree, sameEqualNot, rhs);
		}
		if (ruo == UnaryOperator.TYPEOF) {
			return createGuardForTypeof((UnaryExpression) rhs, negateTree, sameEqualNot, lhs);
		}

		// name == void 0
		if (luo == UnaryOperator.VOID && rs != null) {
			return createGuardForVoid((UnaryExpression) lhs, negateTree, sameEqualNot, rs);
		}
		if (ruo == UnaryOperator.VOID && ls != null) {
			return createGuardForVoid((UnaryExpression) rhs, negateTree, sameEqualNot, ls);
		}

		// "v" in window
		// not supported

		return null;
	}

	private Guard createGuardForNUZ(Expression eqe, boolean negateTree, boolean negateEqe, Symbol nuz, Symbol symbol) {
		GuardType type = null;
		if (nuz.isNullLiteral()) {
			type = GuardType.IsNull;
		}
		if (nuz.isUndefinedLiteral()) {
			type = GuardType.IsUndefined;
		}
		if (nuz.isZeroLiteral()) {
			type = GuardType.IsZero;
		}
		GuardAssertion asserts = GuardAssertion.get(eqe, negateTree, negateEqe);
		Guard guard = new Guard(eqe, type, symbol, asserts);
		return guard;
	}

	private Guard createGuardForTypeof(UnaryExpression ue, boolean negateTree, boolean negateEqe, Expression rhs) {
		if (rhs instanceof StringLiteral) {
			StringLiteral rhsStringLit = (StringLiteral) rhs;
			String lit = rhsStringLit.getValue();
			if (!"undefined".equals(lit)) {
				return null;
			}
		}
		Expression typeofExpr = ue.getExpression();
		Symbol typeofSymbol = symbolFactory.create(typeofExpr);
		if (typeofSymbol == null) {
			return null;
		}
		GuardAssertion asserts = GuardAssertion.get(ue, negateTree, negateEqe);
		Guard guard = new Guard(ue, GuardType.IsUndefined, typeofSymbol, asserts);
		return guard;
	}

	private Guard createGuardForVoid(UnaryExpression ue, boolean negateTree, boolean negateEqe, Symbol symbol) {
		Expression voidExpr = ue.getExpression();
		if (voidExpr instanceof NumericLiteral) {
			NumericLiteral nl = (NumericLiteral) voidExpr;
			String numVal = nl.getValueAsString();
			if (!"0".equals(numVal)) {
				return null;
			}
		}
		GuardAssertion asserts = GuardAssertion.get(ue, negateTree, negateEqe);
		Guard guard = new Guard(ue, GuardType.IsUndefined, symbol, asserts);
		return guard;
	}

}
