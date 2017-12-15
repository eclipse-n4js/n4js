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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analyses.AssumptionWithContext;
import org.eclipse.n4js.flowgraphs.analyses.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

/**
 * This analysis computes all cases where succeeding and type state relevant method calls on the same receiver instance
 * conflict with each other regarding their state conditions.
 */
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
				IsInPrestate isInPreState = new IsInPrestate(effect.symbol, preStates);
				assume(isInPreState);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void visitGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		if (must && effect.type == EffectType.MethodCall) {
			Set<String> inState = getDeclaredStates(cfe, ANNOTATION_INSTATE);
			if (!inState.isEmpty()) {
				IsReasonableStateGuard isInPreState = new IsReasonableStateGuard(effect.symbol, inState);
				assume(isInPreState);
			}
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

	class IsInPrestate extends AssumptionWithContext {
		private final Set<String> preStates;

		IsInPrestate(Symbol symbol, Set<String> preStates) {
			super(symbol);
			this.preStates = preStates;
		}

		@Override
		public AssumptionWithContext copy() {
			return new IsInPrestate(symbol, preStates);
		}

		@Override
		public void mergeWith(AssumptionWithContext assumption) {
			IsInPrestate iip = (IsInPrestate) assumption;
			preStates.retainAll(iip.preStates);
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			if (effect.type == EffectType.MethodCall) {
				Collection<String> postStates = getDeclaredStates(container, ANNOTATION_POSTSTATE);
				if (!postStates.isEmpty()) {
					deactivate(); // deactivate this assumption since the predecessor was found here
					postStates.removeAll(preStates);
					boolean allPostStatesAreValidPreStates = postStates.isEmpty();
					return allPostStatesAreValidPreStates;
				}
			}
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean holdsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {

			if (must && effect.type == EffectType.MethodCall) {
				Collection<String> inStates = getDeclaredStates(cfe, ANNOTATION_INSTATE);
				if (!inStates.isEmpty()) {
					if (inverse) {
						preStates.addAll(inStates);
					} else {
						preStates.clear();
						preStates.addAll(inStates);
					}
				}
			}
			return true;
		}
	}

	class IsReasonableStateGuard extends AssumptionWithContext {
		private final Set<String> inStates;
		private final Set<String> postStates;

		IsReasonableStateGuard(Symbol symbol, Set<String> inStates) {
			this(symbol, inStates, new HashSet<>());
		}

		IsReasonableStateGuard(Symbol symbol, Set<String> inStates, Set<String> postStates) {
			super(symbol);
			this.inStates = inStates;
			this.postStates = postStates;
		}

		@Override
		public AssumptionWithContext copy() {
			return new IsReasonableStateGuard(symbol, inStates, postStates);
		}

		@Override
		public void mergeWith(AssumptionWithContext assumption) {
			IsReasonableStateGuard irg = (IsReasonableStateGuard) assumption;
			postStates.addAll(irg.postStates);
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			if (effect.type == EffectType.MethodCall) {
				Collection<String> postStatesOfMethodCall = getDeclaredStates(container, ANNOTATION_POSTSTATE);
				if (!postStates.isEmpty()) {
					postStates.addAll(postStatesOfMethodCall);
					deactivate();
				}
			}
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean holdsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {

			if (must && !inverse && effect.type == EffectType.MethodCall) {
				Collection<String> inStatesAfterGuard = getDeclaredStates(cfe, ANNOTATION_INSTATE);
				if (!inStatesAfterGuard.isEmpty()) {
					postStates.addAll(inStatesAfterGuard);
					deactivate();
				}
			}
			return true;
		}

		@Override
		public boolean holdsAfterall() {
			Set<String> notInStates = new HashSet<>(postStates);
			notInStates.removeAll(inStates);
			Set<String> intersection = new HashSet<>(postStates);
			intersection.retainAll(inStates);

			boolean isReasonableGuard = true;
			isReasonableGuard &= !intersection.isEmpty(); // not reachable patch
			isReasonableGuard &= !notInStates.isEmpty(); // obsolete check
			return isReasonableGuard;
		}
	}

}
