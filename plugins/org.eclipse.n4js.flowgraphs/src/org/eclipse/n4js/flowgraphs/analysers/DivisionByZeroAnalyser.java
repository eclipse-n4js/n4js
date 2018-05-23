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

import org.eclipse.n4js.flowgraphs.dataflow.Assumption;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.NumericLiteral;

import com.google.common.collect.Multimap;

/**
 * This analysis computes all cases where an implicit assumption of a variable being not zero conflicts either with an
 * explicit guard that assures this variable to be zero or with an explicit assignment of zero.
 */
// TODO: not active/tested
public class DivisionByZeroAnalyser extends DataFlowVisitor {

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (isDivisor(effect.symbol, cfe)) {
			IsNotZero symbolNotZero = new IsNotZero(cfe, effect.symbol);
			assume(symbolNotZero);
		}
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

	static private boolean isZeroLiteral(Expression expr) {
		if (expr instanceof NumericLiteral) {
			NumericLiteral numLit = (NumericLiteral) expr;
			BigDecimal litValue = numLit.getValue();
			return litValue.equals(BigDecimal.ZERO);
		}
		return false;
	}

	static class IsNotZero extends Assumption {
		IsNotZero(ControlFlowElement cfe, Symbol symbol) {
			super(cfe, symbol);
		}

		IsNotZero(IsNotZero copy) {
			super(copy);
		}

		@Override
		public Assumption copy() {
			return new IsNotZero(this);
		}

		@Override
		public PartialResult holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				Expression rhs = ae.getRhs();
				if (isZeroLiteral(rhs)) {
					return PartialResult.Passed;
				}
			}
			return PartialResult.Unclear;
		}

		@Override
		public PartialResult holdsOnGuards(Multimap<GuardType, Guard> neverHolding,
				Multimap<GuardType, Guard> alwaysHolding) {

			if (alwaysHolding.containsKey(GuardType.IsZero)) {
				// TODO
				// return new PartialResult.Failed(GuardType.IsZero);
			}
			if (neverHolding.containsKey(GuardType.IsZero)) {
				return PartialResult.Passed;
			}
			return PartialResult.Unclear;
		}
	}
}
