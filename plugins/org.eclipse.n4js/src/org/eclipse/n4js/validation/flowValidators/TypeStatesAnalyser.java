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
package org.eclipse.n4js.validation.flowValidators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.dataflow.Assumption;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import com.google.common.collect.Multimap;

/**
 * This analysis computes all cases where succeeding and type state relevant method calls on the same receiver instance
 * conflict with each other regarding their state conditions.
 */
// TODO: not active/tested
public class TypeStatesAnalyser extends DataFlowVisitor {
	static final String ANNOTATION_INSTATE = "InState";
	static final String ANNOTATION_PRESTATE = "PreState";
	static final String ANNOTATION_POSTSTATE = "PostState";
	final N4JSTypeSystem ts;

	/** Constructor */
	public TypeStatesAnalyser(N4JSTypeSystem ts) {
		this.ts = ts;
	}

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (effect.type == EffectType.MethodCall) {
			Set<String> preStates = getDeclaredStates(cfe, ANNOTATION_PRESTATE);
			if (!preStates.isEmpty()) {
				IsInPrestate isInPreState = new IsInPrestate(cfe, effect.symbol, preStates);
				assume(isInPreState);
			}
		}
	}

	@Override
	public void visitGuard(Guard guard) {
		if (guard.asserts != GuardAssertion.MayHolds && guard.type == GuardType.InState) {
			// TODO
			// Set<String> inState = getDeclaredStates(cfe, ANNOTATION_INSTATE);
			// if (!inState.isEmpty()) {
			// IsReasonableStateGuard isInPreState = new IsReasonableStateGuard(cfe, effect.symbol, inState);
			// assume(isInPreState);
			// }
		}
	}

	private Set<String> getDeclaredStates(ControlFlowElement container, String stateName) {
		Set<String> states = new HashSet<>();
		if (container instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) container;
			TFunction tFunc = (TFunction) ts.tau(pce);
			for (TAnnotation ann : tFunc.getAnnotations()) {
				if (stateName.equals(ann.getName())) {
					for (TAnnotationArgument arg : ann.getArgs()) {
						states.add(arg.getArgAsString());
					}
				}
			}
		}
		return states;
	}

	class IsInPrestate extends Assumption {
		private final Set<String> preStates;

		IsInPrestate(ControlFlowElement cfe, Symbol symbol, Set<String> preStates) {
			super(cfe, symbol);
			this.preStates = preStates;
		}

		IsInPrestate(IsInPrestate copy) {
			super(copy);
			this.preStates = copy.preStates;
		}

		@Override
		public Assumption copy() {
			return new IsInPrestate(this);
		}

		@Override
		public void mergeClientData(Assumption assumption) {
			IsInPrestate iip = (IsInPrestate) assumption;
			preStates.retainAll(iip.preStates);
		}

		@Override
		public PartialResult holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			if (effect.type == EffectType.MethodCall) {
				Collection<String> postStates = getDeclaredStates(container, ANNOTATION_POSTSTATE);
				if (!postStates.isEmpty()) {
					// deactivate(); // deactivate this assumption since the predecessor was found here
					postStates.removeAll(preStates);
					boolean allPostStatesAreValidPreStates = postStates.isEmpty();
					if (allPostStatesAreValidPreStates) {
						return PartialResult.Passed;
					}
				}
			}
			return PartialResult.Unclear;
		}

		@Override
		public PartialResult holdsOnGuards(Multimap<GuardType, Guard> neverHolding,
				Multimap<GuardType, Guard> alwaysHolding) {

			if (alwaysHolding.containsKey(GuardType.InState)) {
				// TODO: change from GuardType to Guard
				// Guard guard = alwaysHolding.get(GuardType.InState);
				// Collection<String> inStates = getDeclaredStates(guard.condition, ANNOTATION_INSTATE);
				// if (!inStates.isEmpty()) {
				// if (guard.asserts == HoldAssertion.AlwaysHolds) {
				// preStates.addAll(inStates);
				// }
				// if (guard.asserts == HoldAssertion.NeverHolds) {
				// preStates.clear();
				// preStates.addAll(inStates);
				// }
				// }
			}
			return PartialResult.Unclear;
		}
	}

	class IsReasonableStateGuard extends Assumption {
		private final Set<String> inStates;
		private final Set<String> postStates;

		IsReasonableStateGuard(ControlFlowElement cfe, Symbol symbol, Set<String> inStates) {
			this(cfe, symbol, inStates, new HashSet<>());
		}

		IsReasonableStateGuard(ControlFlowElement cfe, Symbol symbol, Set<String> inStates, Set<String> postStates) {
			super(cfe, symbol);
			this.inStates = inStates;
			this.postStates = postStates;
		}

		IsReasonableStateGuard(IsReasonableStateGuard copy) {
			super(copy);
			this.inStates = copy.inStates;
			this.postStates = copy.postStates;
		}

		@Override
		public Assumption copy() {
			return new IsReasonableStateGuard(this);
		}

		@Override
		public void mergeClientData(Assumption assumption) {
			IsReasonableStateGuard irg = (IsReasonableStateGuard) assumption;
			postStates.addAll(irg.postStates);
		}

		@Override
		public PartialResult holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			if (effect.type == EffectType.MethodCall) {
				Collection<String> postStatesOfMethodCall = getDeclaredStates(container, ANNOTATION_POSTSTATE);
				if (!postStates.isEmpty()) {
					postStates.addAll(postStatesOfMethodCall);
					// deactivate();
					return PartialResult.Passed;
				}
			}
			return PartialResult.Unclear;
		}

		@Override
		public PartialResult holdsOnGuards(Multimap<GuardType, Guard> neverHolding,
				Multimap<GuardType, Guard> alwaysHolding) {
			if (alwaysHolding.containsKey(GuardType.InState)) {
				// TODO: change from GuardType to Guard
				// Guard guard = alwaysHolding.get(GuardType.InState);
				// Collection<String> inStatesAfterGuard = getDeclaredStates(guard.condition, ANNOTATION_INSTATE);
				// if (!inStatesAfterGuard.isEmpty()) {
				// postStates.addAll(inStatesAfterGuard);
				// deactivate();
				// }
			}
			return PartialResult.Unclear;
		}
	}

}
