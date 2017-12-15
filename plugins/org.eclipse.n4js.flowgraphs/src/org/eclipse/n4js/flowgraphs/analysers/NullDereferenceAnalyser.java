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

import org.eclipse.n4js.flowgraphs.analyses.Assumption;
import org.eclipse.n4js.flowgraphs.analyses.AssumptionWithContext;
import org.eclipse.n4js.flowgraphs.analyses.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

/**
 * This analysis computes all cases where an implicit assumption of a variable being not null conflicts either with an
 * explicit guard that assures this variable to be null, or with an explicit assignment of null.
 */
public class NullDereferenceAnalyser extends DataFlowVisitor {

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (isDereference(cfe)) {
			IsNotNull symbolNotNull = new IsNotNull(effect.symbol);
			assume(symbolNotNull);
		}
	}

	@Override
	public void visitGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		if (must && isDereference(cfe)) {
			IsReasonableNullGuard isReasonableNullGuard = new IsReasonableNullGuard(effect.symbol);
			assume(isReasonableNullGuard);
		}
	}

	boolean isDereference(ControlFlowElement cfe) {
		boolean isDereference = false;
		isDereference |= cfe instanceof FieldAccessor;
		isDereference |= cfe instanceof ParameterizedPropertyAccessExpression;
		return isDereference;
	}

	static private EqualityOperator getNullCheckOperator(Symbol alias, ControlFlowElement readOperation) {
		if (readOperation instanceof EqualityExpression) {
			EqualityExpression eqExpr = (EqualityExpression) readOperation;
			EqualityOperator equalityOperator = eqExpr.getOp();
			if (equalityOperator == EqualityOperator.EQ || equalityOperator == EqualityOperator.NEQ) {
				Expression otherExpr = alias.is(eqExpr.getLhs()) ? eqExpr.getRhs() : eqExpr.getLhs();
				if (otherExpr instanceof NullLiteral) {
					return equalityOperator;
				}
			}
		}
		return null;
	}

	static class IsNotNull extends Assumption {
		IsNotNull(Symbol symbol) {
			super(symbol);
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				if (ae.getRhs() instanceof NullLiteral) {
					return false;
				}
			}
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean holdsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
			if (must) {
				EqualityOperator equalityOperator = getNullCheckOperator(effect.symbol, cfe);
				if (equalityOperator != null) {
					if (!inverse && equalityOperator == EqualityOperator.EQ) {
						return false;
					}
					if (inverse && equalityOperator == EqualityOperator.NEQ) {
						return false;
					}
				}
			}
			return true;
		}
	}

	static class IsReasonableNullGuard extends AssumptionWithContext {
		private boolean alwaysNullBefore = false;
		private boolean alwaysNotNullBefore = false;

		IsReasonableNullGuard(Symbol symbol) {
			this(symbol, false, false);
		}

		IsReasonableNullGuard(Symbol symbol, boolean alwaysNullBefore, boolean alwaysNotNullBefore) {
			super(symbol);
			this.alwaysNullBefore = alwaysNullBefore;
			this.alwaysNotNullBefore = alwaysNotNullBefore;
		}

		@Override
		public Assumption copy() {
			return new IsReasonableNullGuard(symbol, alwaysNullBefore, alwaysNotNullBefore);
		}

		@Override
		public void mergeWith(Assumption assumption) {
			IsReasonableNullGuard irng = (IsReasonableNullGuard) assumption;
			alwaysNullBefore |= irng.alwaysNullBefore;
			alwaysNotNullBefore |= irng.alwaysNotNullBefore;
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				if (ae.getRhs() instanceof NullLiteral) {
					alwaysNullBefore = true;
				} else {
					alwaysNotNullBefore = true;
				}
				deactivate();
			}
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean holdsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
			if (must) {
				EqualityOperator equalityOperator = getNullCheckOperator(effect.symbol, cfe);
				if (equalityOperator != null) {
					if (inverse == (equalityOperator == EqualityOperator.NEQ)) {
						alwaysNullBefore = true;
					} else {
						alwaysNotNullBefore = true;
					}
					deactivate();
				}
			}
			return true;
		}

		@Override
		public boolean holdsAfterall() {
			boolean isReasonableNullGuard = true;
			isReasonableNullGuard &= !alwaysNullBefore;
			isReasonableNullGuard &= !alwaysNotNullBefore;
			return isReasonableNullGuard;
		}
	}
}
