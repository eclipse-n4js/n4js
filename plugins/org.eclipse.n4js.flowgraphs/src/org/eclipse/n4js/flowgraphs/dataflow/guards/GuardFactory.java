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

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.FlowAssertionFactory;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * Creates {@link Guard}s from given {@link Expression}s that are used as conditions.
 */
public class GuardFactory {

	/** @return a {@link Guard} from a given {@link Expression} that is used as condition. */
	static Guard create(EObject topContainer, Expression expr, boolean negateTree) {
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
			return createGuardForTruthy(topContainer, expr, negateTree);
		}

		// "v" in window
		// not supported

		return null;
	}

	static private Guard createGuardForInstanceof(EObject topContainer, boolean negateTree, RelationalExpression re) {
		Expression lhs = re.getLhs();
		Expression rhs = re.getRhs();
		if (SymbolFactory.canCreate(lhs)) {
			GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, re, negateTree, false);
			Guard guard = createInstanceofGuard(re, asserts, lhs, rhs);
			return guard;
		}
		return null;
	}

	static private Guard createGuardForTruthy(EObject topContainer, Expression expr, boolean negateTree) {
		if (SymbolFactory.canCreate(expr)) {
			EObject parent = expr.eContainer();
			boolean isTruthy = false;
			isTruthy |= parent instanceof Statement;
			isTruthy |= parent instanceof ParenExpression;
			isTruthy |= parent instanceof ConditionalExpression;
			isTruthy |= parent instanceof BinaryLogicalExpression;
			isTruthy |= parent instanceof UnaryExpression && ((UnaryExpression) parent).getOp() == UnaryOperator.NOT;
			if (isTruthy) {
				GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, expr, negateTree, false);
				Guard guard = createIsTruthyGuard(expr, asserts);
				return guard;
			}
		}
		return null;
	}

	static private Guard createGuardForEquality(EObject topContainer, boolean negateTree, EqualityExpression eqe,
			boolean sameEqualNot) {

		Expression lhs = eqe.getLhs();
		Expression rhs = eqe.getRhs();

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
		if (luo == UnaryOperator.VOID && SymbolFactory.canCreate(rhs)) {
			return createGuardForVoid(topContainer, (UnaryExpression) lhs, negateTree, sameEqualNot, rhs);
		}
		if (ruo == UnaryOperator.VOID && SymbolFactory.canCreate(lhs)) {
			return createGuardForVoid(topContainer, (UnaryExpression) rhs, negateTree, sameEqualNot, lhs);
		}

		// v == null|undefined|0
		if (SymbolFactory.canCreate(lhs) && SymbolFactory.canCreate(rhs)) {
			GuardType guardType = getGuardType(lhs);
			Expression symbolExpr = rhs;
			if (guardType == null) {
				guardType = getGuardType(rhs);
				symbolExpr = lhs;
			}
			if (guardType != null) {
				return createGuardForNUZ(topContainer, eqe, negateTree, sameEqualNot, guardType, symbolExpr);
			}
		}

		return null;
	}

	static private Guard createGuardForNUZ(EObject topContainer, Expression eqe, boolean negateTree, boolean negateEqe,
			GuardType guardType, Expression symbolExpr) {

		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, eqe, negateTree, negateEqe);

		switch (guardType) {
		case IsNull:
			return createIsNullGuard(eqe, asserts, symbolExpr);
		case IsUndefined:
			return createIsUndefinedGuard(eqe, asserts, symbolExpr);
		case IsZero:
			return createIsZeroGuard(eqe, asserts, symbolExpr);
		default:
			break;
		}
		return null;
	}

	static private GuardType getGuardType(Expression nuz) {
		if (nuz instanceof NullLiteral) {
			return GuardType.IsNull;
		}
		if (nuz instanceof IdentifierRef) {
			IdentifiableElement id = ((IdentifierRef) nuz).getId();
			if (id != null && "undefined".equals(id.getName())) {
				return GuardType.IsUndefined;
			}
		}
		if (nuz instanceof IntLiteral && new BigDecimal(0).equals(((NumericLiteral) nuz).getValue())) {
			return GuardType.IsZero;
		}
		return null;
	}

	static private Guard createGuardForTypeof(EObject topContainer, UnaryExpression ue, boolean negateTree,
			boolean negateEqe, Expression rhs) {

		if (rhs instanceof StringLiteral) {
			StringLiteral rhsStringLit = (StringLiteral) rhs;
			String lit = rhsStringLit.getValue();
			if (!"undefined".equals(lit)) {
				return null;
			}
		}
		Expression typeofExpr = ue.getExpression();
		if (SymbolFactory.canCreate(typeofExpr)) {
			GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, ue.eContainer(), negateTree,
					negateEqe);
			Guard guard = createIsUndefinedGuard(ue, asserts, typeofExpr);
			return guard;
		}
		return null;
	}

	static private Guard createGuardForVoid(EObject topContainer, UnaryExpression ue, boolean negateTree,
			boolean negateEqe,
			Expression symbolExpr) {

		Expression voidExpr = ue.getExpression();
		if (voidExpr instanceof NumericLiteral) {
			NumericLiteral nl = (NumericLiteral) voidExpr;
			String numVal = nl.getValueAsString();
			if (!"0".equals(numVal)) {
				return null;
			}
		}
		GuardAssertion asserts = FlowAssertionFactory.getGuard(topContainer, ue.eContainer(), negateTree, negateEqe);
		Guard guard = createIsUndefinedGuard(ue, asserts, symbolExpr);
		return guard;
	}

	static private Guard createIsNullGuard(Expression expr, GuardAssertion asserts, Expression symoblExpr) {
		return new Guard(expr, GuardType.IsNull, asserts, symoblExpr);
	}

	static private Guard createIsTruthyGuard(Expression expr, GuardAssertion asserts) {
		return new Guard(expr, GuardType.IsTruthy, asserts, expr);
	}

	static private Guard createIsUndefinedGuard(Expression expr, GuardAssertion asserts, Expression symoblExpr) {
		return new Guard(expr, GuardType.IsUndefined, asserts, symoblExpr);
	}

	static private Guard createIsZeroGuard(Expression expr, GuardAssertion asserts, Expression symoblExpr) {
		return new Guard(expr, GuardType.IsZero, asserts, symoblExpr);
	}

	static private Guard createInstanceofGuard(Expression expr, GuardAssertion asserts, Expression symoblExpr,
			Expression typeIdentifier) {

		return new InstanceofGuard(expr, asserts, symoblExpr, typeIdentifier);
	}
}
