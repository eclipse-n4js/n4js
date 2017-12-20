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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.analyses.Assumption;
import org.eclipse.n4js.flowgraphs.analyses.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.factories.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * This analysis computes all cases where an implicit assumption of a variable being not null conflicts either with an
 * explicit guard that assures this variable to be null, or with an explicit assignment of null.
 */
public class NullDereferenceAnalyser extends DataFlowVisitor {

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		Expression dereferencer = getDereferencer(cfe);
		if (dereferencer != null) {
			Symbol tgtSymbol = SymbolFactory.create(dereferencer);
			IsNotNull symbolNotNull = new IsNotNull(dereferencer, tgtSymbol);
			assume(symbolNotNull);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void visitGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		Expression dereferencer = getDereferencer(cfe);
		if (must && dereferencer != null) {
			Symbol tgtSymbol = SymbolFactory.create(dereferencer);
			IsReasonableNullGuard isReasonableNullGuard = new IsReasonableNullGuard(dereferencer, tgtSymbol);
			assume(isReasonableNullGuard);
		}
	}

	private Expression getDereferencer(ControlFlowElement cfe) {
		Expression dereferencer = null;
		if (cfe instanceof FieldAccessor) {
			FieldAccessor fa = (FieldAccessor) cfe;
			// fa.get
		}
		if (cfe instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) cfe;
			Expression target = ppae.getTarget();
			dereferencer = target;
		}
		return dereferencer;
	}

	/** @return a list of all AST locations where a null pointer dereference can happen */
	public List<NullDereferenceResult> getNullDereferences() {
		List<NullDereferenceResult> nullDerefs = new LinkedList<>();
		for (Assumption ass : failedAssumptions) {
			IsNotNull inn = (IsNotNull) ass;
			ControlFlowElement astLocation = inn.creationSite;
			NullDereferenceResult ndr = new NullDereferenceResult(astLocation, inn);
			nullDerefs.add(ndr);
		}
		return nullDerefs;
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

	/** Result of a null or undefined dereference */
	static public class NullDereferenceResult {
		/** AST location */
		public final ControlFlowElement cfe;
		/** {@link Symbol} that was checked for null or undefined */
		public final Symbol checkedSymbol;
		/** Aliased {@link Symbol} that failed a check */
		public final Symbol causingSymbol;
		/** Undefined or Null {@link Symbol} that was assigned to {@link #causingSymbol} */
		public final Symbol nullOrUndefinedSymbol;

		NullDereferenceResult(ControlFlowElement cfe, IsNotNull inn) {
			this.cfe = cfe;
			this.checkedSymbol = inn.symbol;
			this.causingSymbol = inn.failedSymbol;
			this.nullOrUndefinedSymbol = inn.nullOrUndefinedSymbol;
		}
	}

	static class IsNotNull extends Assumption {
		Symbol nullOrUndefinedSymbol;

		IsNotNull(ControlFlowElement cfe, Symbol symbol) {
			super(cfe, symbol);
		}

		IsNotNull(IsNotNull copy) {
			super(copy);
		}

		@Override
		public Assumption copy() {
			return new IsNotNull(this);
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write) {
				Expression value = null;
				if (cfe instanceof AssignmentExpression) {
					AssignmentExpression ae = (AssignmentExpression) cfe;
					value = ae.getRhs();
				}
				if (cfe instanceof VariableDeclaration) {
					VariableDeclaration vd = (VariableDeclaration) cfe;
					Expression initExpr = vd.getExpression();
					if (initExpr == null) {
						value = N4JSFactory.eINSTANCE.createNullLiteral();
					} else {
						value = initExpr;
					}
				}

				nullOrUndefinedSymbol = SymbolFactory.create(value);
				if (nullOrUndefinedSymbol != null) {
					if (nullOrUndefinedSymbol.isNullLiteral()) {
						return false;
					} else if (nullOrUndefinedSymbol.isUndefinedLiteral()) {
						return false;
					}
				} else {
					if (value != null) {
						deactivateAlias(effect.symbol);
					}
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

	static class IsReasonableNullGuard extends Assumption {
		private boolean alwaysNullBefore = false;
		private boolean alwaysNotNullBefore = false;

		IsReasonableNullGuard(ControlFlowElement cfe, Symbol symbol) {
			this(cfe, symbol, false, false);
		}

		IsReasonableNullGuard(ControlFlowElement cfe, Symbol symbol, boolean alwaysNullBefore,
				boolean alwaysNotNullBefore) {

			super(cfe, symbol);
			this.alwaysNullBefore = alwaysNullBefore;
			this.alwaysNotNullBefore = alwaysNotNullBefore;
		}

		IsReasonableNullGuard(IsReasonableNullGuard copy) {
			super(copy);
			this.alwaysNullBefore = copy.alwaysNullBefore;
			this.alwaysNotNullBefore = copy.alwaysNotNullBefore;
		}

		@Override
		public Assumption copy() {
			return new IsReasonableNullGuard(this);
		}

		@Override
		public void mergeWith(Assumption assumption) {
			super.mergeWith(assumption);
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
