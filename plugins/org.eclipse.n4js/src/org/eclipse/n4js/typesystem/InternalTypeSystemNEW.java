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
package org.eclipse.n4js.typesystem;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

import com.google.inject.Inject;

/*
 * TODO continue with:
 * judgment subtype (mostly done and working; 3 test failures remain, see open Xpect test files)
 * pending judgments: (1) type (2) expectedTypeIn
 *
 * TODO challenges:
 * + several main arguments (that require switching) -->  obsolete, because only subtype needed this and this is not using a switch
 * - several switches
 * - helper arguments (should be easy: simply add more fields than just G to inner switch class)
 */

public class InternalTypeSystemNEW {

	@Inject
	private N4JSTypeSystem tsOLD;

	@Inject
	private TypeJudgment typeJudgment;
	@Inject
	private ExpectedTypeJudgment expectedTypeJudgment;
	@Inject
	private SubtypeJudgment subtypeJudgment;
	@Inject
	private BoundJudgment boundJudgment;
	@Inject
	private SubstTypeVariablesJudgment substTypeVariablesJudgment;
	@Inject
	private ThisTypeJudgment thisTypeJudgment;

	public Result<TypeRef> type(RuleEnvironment G, TypableElement element) {
		return tsOLD.type(G, element);
	}

	/**
	 * <b>!!! This method must never be invoked, except from {@code AbstractProcessor#askXsemanticsForType()} !!!</b>
	 * <p>
	 * This method may be called to actually use the 'type' judgment in Xsemantics. It is used by {@link ASTProcessor}
	 * while traversing the entire AST (during post-processing) to obtain the type of nodes that have not yet been
	 * processed.
	 */
	public Result<TypeRef> use_type_judgment_from_PostProcessors(RuleEnvironment G,
			TypableElement element) {
		return typeJudgment.apply(G, element);
	}

	public Result<TypeRef> expectedType(RuleEnvironment G, EObject container, Expression expression) {
		return expectedTypeJudgment.apply(G, container, expression);
	}

	public Result<Boolean> subtype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return subtypeJudgment.apply(G, left, right);
	}

	public Result<Boolean> supertype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		if (subtype(G, right, left).isSuccess()) {
			return Result.success();
		} else {
			return Result.failure(
					left.getTypeRefAsString() + " is not a super type of " + right.getTypeRefAsString(), false, null);
		}
	}

	public Result<Boolean> equaltype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		if (subtype(G, left, right).isSuccess() && subtype(G, right, left).isSuccess()) {
			return Result.success();
		} else {
			return Result.failure(
					left.getTypeRefAsString() + " is not equal to " + right.getTypeRefAsString(), false, null);
		}
	}

	// public TypeRef expectedTypeIn(RuleEnvironment G, EObject container, Expression expression) {
	// throw new UnsupportedOperationException();
	// }

	public TypeRef upperBound(RuleEnvironment G, TypeArgument typeArg) {
		return boundJudgment.applyUpperBound(G, typeArg).getValue();
	}

	public TypeRef lowerBound(RuleEnvironment G, TypeArgument typeArg) {
		return boundJudgment.applyLowerBound(G, typeArg).getValue();
	}

	public Result<TypeArgument> substTypeVariables(RuleEnvironment G, TypeArgument typeArg) {
		return substTypeVariablesJudgment.apply(G, typeArg);
	}

	public Result<Wildcard> substTypeVariables(RuleEnvironment G, Wildcard wildcard) {
		// FIXME ugly cast!!!!
		return (Result<Wildcard>) ((Result<?>) substTypeVariablesJudgment.apply(G, wildcard));
	}

	public Result<TypeRef> substTypeVariables(RuleEnvironment G, TypeRef typeRef) {
		// FIXME ugly cast!!!!
		return (Result<TypeRef>) ((Result<?>) substTypeVariablesJudgment.apply(G, typeRef));
	}

	public Result<TypeRef> thisTypeRef(RuleEnvironment G, EObject location) {
		return thisTypeJudgment.apply(G, location);
	}
}
