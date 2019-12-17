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
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.Assumption;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Multimap;

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
	public void visitGuard(Guard guard) {
		if (guard.asserts != GuardAssertion.MayHolds && guard.type == GuardType.IsNull) {
			IsReasonableNullGuard isReasonableNullGuard = new IsReasonableNullGuard(guard);
			assume(isReasonableNullGuard);
		}
	}

	private boolean isDereferencing(ControlFlowElement cfe) {
		EObject parent = cfe.eContainer();
		if (parent instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) parent;
			if (!ppae.isOptionalChaining()) {
				Expression target = ppae.getTarget();
				return cfe == target;
			}
		}
		return false;
	}

	/** @return a list of all AST locations where a null pointer dereference can happen */
	public Iterable<NullDereferenceResult> getNullDereferences() {
		Set<NullDereferenceResult> nullDerefs = new HashSet<>();
		for (Assumption ass : failedAssumptions.values()) {
			IsNotNull inn = (IsNotNull) ass;
			ControlFlowElement astLocation = inn.creationSite;
			NullDereferenceResult ndr = new NullDereferenceResult(astLocation, inn);
			nullDerefs.add(ndr);
		}
		return nullDerefs;
	}

	class IsNotNull extends Assumption {

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
		public PartialResult holdsOnDataflow(Symbol lhs, Symbol rSymbol, Expression rValue) {
			if (rSymbol != null) {
				if (rSymbol.isNullLiteral() && !guardsThatNeverHold.containsKey(GuardType.IsNull)) {
					return new NullDereferenceFailed(GuardType.IsNull, lhs);
				}
				if (rSymbol.isUndefinedLiteral() && !guardsThatNeverHold.containsKey(GuardType.IsUndefined)) {
					return new NullDereferenceFailed(GuardType.IsUndefined, lhs);
				}
			} else if (rValue != null) {
				ExpressionWithTarget exprWithTarget = EcoreUtil2.getContainerOfType(rValue, ExpressionWithTarget.class);
				if (exprWithTarget != null && exprWithTarget.isOptionalChaining()) {
					return new NullDereferenceFailed(PartialResult.Type.MayFailed, GuardType.IsUndefined, lhs);
				} else {
					return PartialResult.Passed;
				}
			}
			return PartialResult.Unclear;
		}

		@Override
		public PartialResult holdsOnGuards(Multimap<GuardType, Guard> neverHolding,
				Multimap<GuardType, Guard> alwaysHolding) {

			if (alwaysHolding.containsKey(GuardType.IsTruthy)) {
				return PartialResult.Passed;
			}
			if (neverHolding.containsKey(GuardType.IsNull) && neverHolding.containsKey(GuardType.IsUndefined)) {
				return PartialResult.Passed;
			}
			if (alwaysHolding.containsKey(GuardType.IsNull)) {
				return new NullDereferenceFailed(GuardType.IsNull);
			}
			if (alwaysHolding.containsKey(GuardType.IsUndefined)) {
				return new NullDereferenceFailed(GuardType.IsUndefined);
			}

			return PartialResult.Unclear;
		}
	}

	// TODO: not active/tested
	// TODO: revisit and specify Null|Undefined|Truthy better
	static class IsReasonableNullGuard extends Assumption {
		private boolean alwaysNullBefore = false;
		private boolean neverNullBefore = false;

		IsReasonableNullGuard(Guard guard) {
			this(guard.condition, guard.getSymbol(), false, false);
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
		public void mergeClientData(Assumption assumption) {
			IsReasonableNullGuard irng = (IsReasonableNullGuard) assumption;
			alwaysNullBefore |= irng.alwaysNullBefore;
			neverNullBefore |= irng.neverNullBefore;
		}

		@Override
		public PartialResult holdsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
			if (effect.type == EffectType.Write && cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				if (ae.getRhs() instanceof NullLiteral) {
					alwaysNullBefore = true;
				} else {
					neverNullBefore = true;
				}
				return PartialResult.Passed;
			}
			return PartialResult.Unclear;
		}

		@Override
		public PartialResult holdsOnGuards(Multimap<GuardType, Guard> guardThatNeverHold,
				Multimap<GuardType, Guard> guardThatAlwaysHold) {
			// TODO
			return PartialResult.Unclear;
		}

	}
}
