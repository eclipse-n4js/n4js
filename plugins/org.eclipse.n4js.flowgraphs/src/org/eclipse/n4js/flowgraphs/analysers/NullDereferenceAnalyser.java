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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.Assumption;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.HoldAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

/**
 * This analysis computes all cases where an implicit assumption of a variable being not null conflicts either with an
 * explicit guard that assures this variable to be null, or with an explicit assignment of null.
 */
public class NullDereferenceAnalyser extends DataFlowVisitor {

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (isDereferencing(cfe)) {
			Symbol tgtSymbol = getSymbolFactory().create(cfe);
			if (tgtSymbol != null) {
				IsNotNull symbolNotNull = new IsNotNull(cfe, tgtSymbol);
				assume(symbolNotNull);
			}
		}
	}

	@Override
	public void visitGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		if (must && isDereferencing(cfe)) {
			Symbol tgtSymbol = getSymbolFactory().create(cfe);
			IsReasonableNullGuard isReasonableNullGuard = new IsReasonableNullGuard(cfe, tgtSymbol);
			assume(isReasonableNullGuard);
		}
	}

	private boolean isDereferencing(ControlFlowElement cfe) {
		EObject parent = cfe.eContainer();
		if (parent instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) parent;
			Expression target = ppae.getTarget();
			return cfe == target;
		}
		return false;
	}

	/** @return a list of all AST locations where a null pointer dereference can happen */
	public Iterable<NullDereferenceResult> getNullDereferences() {
		Set<NullDereferenceResult> nullDerefs = new HashSet<>();
		for (Assumption ass : failedAssumptions) {
			IsNotNull inn = (IsNotNull) ass;
			ControlFlowElement astLocation = inn.creationSite;
			NullDereferenceResult ndr = new NullDereferenceResult(astLocation, inn);
			nullDerefs.add(ndr);
		}
		return nullDerefs;
	}

	class IsNotNull extends Assumption {
		List<Symbol> nullOrUndefinedSymbols = new LinkedList<>();

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
		public HoldAssertion holdsOnDataflow(Symbol lhs, Symbol rSymbol, Expression rValue) {
			if (rSymbol != null) {
				if (rSymbol.isNullLiteral() || rSymbol.isUndefinedLiteral()) {
					nullOrUndefinedSymbols.add(rSymbol);
					return HoldAssertion.NeverHolds;
				}
			} else if (rValue != null) {
				return HoldAssertion.AlwaysHolds;
			}
			return HoldAssertion.MayHold;
		}

		@Override
		public HoldAssertion holdsOnGuard(Guard guard) {
			if (guard.type.IsNullOrUndefined() && guard.asserts == HoldAssertion.AlwaysHolds) {
				return HoldAssertion.NeverHolds;
			}
			if (guard.type == GuardType.IsTruthy) {
				return guard.asserts;
			}

			return HoldAssertion.MayHold;
		}
	}

	// TODO: revisit and specify Null|Undefined|Truthy better
	static class IsReasonableNullGuard extends Assumption {
		private boolean alwaysNullBefore = false;
		private boolean neverNullBefore = false;

		IsReasonableNullGuard(ControlFlowElement cfe, Symbol symbol) {
			this(cfe, symbol, false, false);
		}

		IsReasonableNullGuard(ControlFlowElement cfe, Symbol symbol, boolean alwaysNullBefore,
				boolean alwaysNotNullBefore) {

			super(cfe, symbol);
			this.alwaysNullBefore = alwaysNullBefore;
			this.neverNullBefore = alwaysNotNullBefore;
		}

		IsReasonableNullGuard(IsReasonableNullGuard copy) {
			super(copy);
			this.alwaysNullBefore = copy.alwaysNullBefore;
			this.neverNullBefore = copy.neverNullBefore;
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
			neverNullBefore |= irng.neverNullBefore;
		}

		@Override
		public HoldAssertion holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				if (ae.getRhs() instanceof NullLiteral) {
					alwaysNullBefore = true;
				} else {
					neverNullBefore = true;
				}
				return HoldAssertion.AlwaysHolds;
			}
			return HoldAssertion.MayHold;
		}

		@Override
		public HoldAssertion holdsOnGuard(Guard guard) {
			if (guard.type == GuardType.IsTruthy) {
				if (guard.asserts == HoldAssertion.AlwaysHolds) {
					neverNullBefore = true;
					this.aliasPassed(guard.symbol);

				} else if (guard.asserts == HoldAssertion.NeverHolds) {
					return guard.asserts;
				}
			}

			return HoldAssertion.MayHold;
		}

		@Override
		public boolean holdsAfterall() {
			boolean isReasonableNullGuard = true;
			isReasonableNullGuard &= !alwaysNullBefore;
			isReasonableNullGuard &= !neverNullBefore;
			return isReasonableNullGuard;
		}
	}
}
