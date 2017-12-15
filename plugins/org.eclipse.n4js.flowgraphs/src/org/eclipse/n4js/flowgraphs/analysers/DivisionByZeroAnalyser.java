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
package org.eclipse.n4js.flowgraphs.analysers;

import java.math.BigDecimal;

import org.eclipse.n4js.flowgraphs.analyses.Assumption;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.NumericLiteral;

/**
 * This analysis computes all cases where an implicit assumption of a variable being not zero conflicts either with an
 * explicit guard that assures this variable to be zero or with an explicit assignment of zero.
 */
public class DivisionByZeroAnalyser {

	public void visitEffect(EffectType effect, Symbol symbol, ControlFlowElement cfe) {
		if (isDivisor(symbol, cfe)) {
			IsNotZero symbolNotZero = new IsNotZero(symbol);
			assume(symbolNotZero);
		}
	}

	protected void assume(Assumption a) {
	}

	boolean isDivisor(Symbol symbol, ControlFlowElement cfe) {
		if (cfe instanceof MultiplicativeExpression) {
			MultiplicativeExpression me = (MultiplicativeExpression) cfe;
			if (symbol.is(me.getLhs())) {
				return true;
			}
		}
		return false;
	}

	static class IsNotZero extends Assumption {
		IsNotZero(Symbol symbol) {
			super(symbol);
		}

		@Override
		public boolean holdsOnEffect(EffectType effect, Symbol alias, ControlFlowElement cfe) {
			if (effect == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				Expression rhs = ae.getRhs();
				return isZeroLiteral(rhs);
			}
			return true;
		}

		@Override
		public boolean holdsOnGuard(EffectType effect, Symbol alias, ControlFlowElement cfe, boolean must,
				boolean inverse) {
			EqualityOperator equalityOperator = getEqualityOperator(cfe);
			if (!inverse && equalityOperator == EqualityOperator.EQ) {
				EqualityExpression eqExpr = (EqualityExpression) cfe;
				Expression otherExpr = alias.is(eqExpr.getLhs()) ? eqExpr.getRhs() : eqExpr.getLhs();
				return isZeroLiteral(otherExpr);
			}
			if (inverse && equalityOperator == EqualityOperator.NEQ) {
				EqualityExpression eqExpr = (EqualityExpression) cfe;
				Expression otherExpr = alias.is(eqExpr.getLhs()) ? eqExpr.getRhs() : eqExpr.getLhs();
				return isZeroLiteral(otherExpr);
			}
			return true;
		}

		private boolean isZeroLiteral(Expression expr) {
			if (expr instanceof NumericLiteral) {
				NumericLiteral numLit = (NumericLiteral) expr;
				BigDecimal litValue = numLit.getValue();
				return litValue.equals(0);
			}
			return false;
		}

		private EqualityOperator getEqualityOperator(ControlFlowElement readOperation) {
			if (readOperation instanceof EqualityExpression) {
				EqualityExpression eqExpr = (EqualityExpression) readOperation;
				return eqExpr.getOp();
			}
			return null;
		}
	}
}
