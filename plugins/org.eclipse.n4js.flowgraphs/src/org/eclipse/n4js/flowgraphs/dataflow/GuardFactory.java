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
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;

/**
 * Creates {@link Guard}s from given {@link Expression}s that are used as conditions.
 */
public class GuardFactory {
	final private SymbolFactory symbolFactory;

	/** Constructor */
	GuardFactory(SymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}

	/** @return a {@link Guard} from a given {@link Expression} that is used as condition. */
	Guard create(EObject topContainer, Expression expr, boolean negateTree) {
		if (expr instanceof EqualityExpression) {
			EqualityExpression eqe = (EqualityExpression) expr;
			EqualityOperator operation = eqe.getOp();
			boolean sameEqual = operation == EqualityOperator.EQ || operation == EqualityOperator.SAME;
			boolean sameEqualNot = operation == EqualityOperator.NEQ || operation == EqualityOperator.NSAME;
			if (sameEqual || sameEqualNot) {
				return createGuardForEquality(topContainer, negateTree, eqe, sameEqualNot);
			}

		} else if (expr instanceof RelationalExpression) {
			RelationalExpression re = (RelationalExpression) expr;
			if (re.getOp() == RelationalOperator.INSTANCEOF) {
				return createGuardForInstanceof(topContainer, negateTree, re);
			}

		} else if (expr instanceof ParameterizedCallExpression) {
			// TODO: implement GuardType.InState here

		} else {
			// in case of:
			// val a = f();
			// if (a) {...}

			Symbol symbol = symbolFactory.create(expr);
			if (symbol != null && symbol.isVariableSymbol()) {
				return createGuardForTruthy(topContainer, expr, negateTree, symbol);
			}
		}

		// "v" in window
		// not supported

		return null;
	}

	private Guard createGuardForInstanceof(EObject topContainer, boolean negateTree, RelationalExpression re) {
		Expression lhs = re.getLhs();
		Expression rhs = re.getRhs();
		Symbol symbol = symbolFactory.create(lhs);
		Expression context = rhs;
		if (symbol == null) {
			symbol = symbolFactory.create(rhs);
			context = lhs;
		}
		if (symbol == null) {
			return null;
		}
		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, re, negateTree, false);
		Guard guard = new Guard(re, GuardType.InstanceOf, asserts, symbol, context);
		return guard;
	}

	private Guard createGuardForTruthy(EObject topContainer, Expression expr, boolean negateTree, Symbol symbol) {
		EObject parent = expr.eContainer();
		boolean isTruthy = false;
		isTruthy |= parent instanceof Statement;
		isTruthy |= parent instanceof ParenExpression;
		isTruthy |= parent instanceof ConditionalExpression;
		isTruthy |= parent instanceof BinaryLogicalExpression;
		isTruthy |= parent instanceof UnaryExpression && ((UnaryExpression) parent).getOp() == UnaryOperator.NOT;
		if (isTruthy) {
			GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, expr, negateTree, false);
			Guard guard = new Guard(expr, GuardType.IsTruthy, asserts, symbol);
			return guard;
		}
		return null;
	}

	private Guard createGuardForEquality(EObject topContainer, boolean negateTree, EqualityExpression eqe,
			boolean sameEqualNot) {

		Expression lhs = eqe.getLhs();
		Expression rhs = eqe.getRhs();
		Symbol ls = symbolFactory.create(lhs);
		Symbol rs = symbolFactory.create(rhs);
		boolean lNUZ = ls != null && (ls.isNullLiteral() || ls.isUndefinedLiteral() || ls.isZeroLiteral());
		boolean rNUZ = rs != null && (rs.isNullLiteral() || rs.isUndefinedLiteral() || rs.isZeroLiteral());

		// var == null|undefined|0
		if (ls != null && rs != null) {
			if (lNUZ && !rNUZ) {
				return createGuardForNUZ(topContainer, eqe, negateTree, sameEqualNot, ls, rs);
			}
			if (!lNUZ && rNUZ) {
				return createGuardForNUZ(topContainer, eqe, negateTree, sameEqualNot, rs, ls);
			}
		}

		UnaryOperator luo = lhs instanceof UnaryExpression ? ((UnaryExpression) lhs).getOp() : null;
		UnaryOperator ruo = rhs instanceof UnaryExpression ? ((UnaryExpression) rhs).getOp() : null;

		// typeof foo != 'undefined'
		if (luo == UnaryOperator.TYPEOF) {
			return createGuardForTypeof(topContainer, (UnaryExpression) lhs, negateTree, sameEqualNot, rhs);
		}
		if (ruo == UnaryOperator.TYPEOF) {
			return createGuardForTypeof(topContainer, (UnaryExpression) rhs, negateTree, sameEqualNot, lhs);
		}

		// name == void 0
		if (luo == UnaryOperator.VOID && rs != null) {
			return createGuardForVoid(topContainer, (UnaryExpression) lhs, negateTree, sameEqualNot, rs);
		}
		if (ruo == UnaryOperator.VOID && ls != null) {
			return createGuardForVoid(topContainer, (UnaryExpression) rhs, negateTree, sameEqualNot, ls);
		}

		return null;
	}

	private Guard createGuardForNUZ(EObject topContainer, Expression eqe, boolean negateTree, boolean negateEqe,
			Symbol nuz, Symbol symbol) {

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
		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, eqe, negateTree, negateEqe);
		Guard guard = new Guard(eqe, type, asserts, symbol);
		return guard;
	}

	private Guard createGuardForTypeof(EObject topContainer, UnaryExpression ue, boolean negateTree,
			boolean negateEqe, Expression rhs) {

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
		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, ue.eContainer(), negateTree, negateEqe);
		Guard guard = new Guard(ue, GuardType.IsUndefined, asserts, typeofSymbol);
		return guard;
	}

	private Guard createGuardForVoid(EObject topContainer, UnaryExpression ue, boolean negateTree, boolean negateEqe,
			Symbol symbol) {

		Expression voidExpr = ue.getExpression();
		if (voidExpr instanceof NumericLiteral) {
			NumericLiteral nl = (NumericLiteral) voidExpr;
			String numVal = nl.getValueAsString();
			if (!"0".equals(numVal)) {
				return null;
			}
		}
		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, ue.eContainer(), negateTree, negateEqe);
		Guard guard = new Guard(ue, GuardType.IsUndefined, asserts, symbol);
		return guard;
	}

}
