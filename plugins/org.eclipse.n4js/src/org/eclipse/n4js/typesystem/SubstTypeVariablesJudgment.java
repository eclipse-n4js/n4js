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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addInconsistentSubstitutions;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.List;

import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.NestedTypeRefsSwitch;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

/* package */ final class SubstTypeVariablesJudgment extends AbstractJudgment {

	/**
	 * See {@link N4JSTypeSystem#substTypeVariables(RuleEnvironment, TypeArgument)}.
	 * <p>
	 * Never returns <code>null</code>, EXCEPT if <code>null</code> is passed in as type argument.
	 */
	public TypeArgument apply(RuleEnvironment G, TypeArgument typeArg, boolean captureContainedWildcards,
			boolean captureUponSubstitution) {
		if (typeArg == null) {
			return null;
		}
		final SubstTypeVariablesSwitch theSwitch = new SubstTypeVariablesSwitch(G, captureContainedWildcards,
				captureUponSubstitution);
		final TypeArgument result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg.getTypeRefAsString();
			throw new IllegalArgumentException(
					"null return value in substTypeVariables judgment for type argument: " + stringRep);
		}
		return result;
	}

	private final class SubstTypeVariablesSwitch extends NestedTypeRefsSwitch {

		private final boolean captureContainedWildcards;
		private final boolean captureUponSubstitution;

		public SubstTypeVariablesSwitch(RuleEnvironment G, boolean captureContainedWildcards,
				boolean captureUponSubstitution) {
			super(G);
			this.captureContainedWildcards = captureContainedWildcards;
			this.captureUponSubstitution = captureUponSubstitution;
		}

		@Override
		protected SubstTypeVariablesSwitch derive(RuleEnvironment G_NEW) {
			return new SubstTypeVariablesSwitch(G_NEW, captureContainedWildcards, captureUponSubstitution);
		}

		// the following 3 methods are provided to increase readability of recursive invocations of #doSwitch()

		@SuppressWarnings("unused")
		private Wildcard substTypeVariables(RuleEnvironment G_NEW, Wildcard wildcard,
				boolean captureContainedWildcardsNEW) {
			return (Wildcard) substTypeVariables(G_NEW, (TypeArgument) wildcard, captureContainedWildcardsNEW);
		}

		private TypeRef substTypeVariables(RuleEnvironment G_NEW, TypeRef typeRef,
				boolean captureContainedWildcardsNEW) {
			return (TypeRef) substTypeVariables(G_NEW, (TypeArgument) typeRef, captureContainedWildcardsNEW);
		}

		private TypeArgument substTypeVariables(RuleEnvironment G_NEW, TypeArgument typeArg,
				boolean captureContainedWildcardsNEW) {
			if (typeArg == null) {
				return null;
			}
			if (G_NEW == this.G && captureContainedWildcardsNEW == this.captureContainedWildcards) {
				return doSwitch(typeArg);
			} else {
				SubstTypeVariablesSwitch nestedSwitch = new SubstTypeVariablesSwitch(
						G_NEW, captureContainedWildcardsNEW, this.captureUponSubstitution);
				return nestedSwitch.doSwitch(typeArg);
			}
		}

		/** Base case. */
		@Override
		public TypeArgument caseTypeArgument(TypeArgument typeArg) {
			return typeArg;
		}

		@Override
		public TypeArgument caseWildcard(Wildcard wildcard) {
			// step #1: substitute type variables in upper and lower bound of given wildcard
			wildcard = (Wildcard) super.caseWildcard(wildcard);
			// step #2: capture the wildcard (if requested)
			if (captureContainedWildcards) {
				return TypeUtils.captureWildcard(wildcard);
			}
			return wildcard;
		}

		@Override
		protected TypeRef caseWildcard_modifyUpperBound(RuleEnvironment G2, TypeRef ub) {
			return substTypeVariables(G2, ub, false);
		}

		@Override
		protected TypeRef caseWildcard_modifyLowerBound(RuleEnvironment G2, TypeRef lb) {
			return substTypeVariables(G2, lb, false);
		}

		@Override
		public TypeRef caseExistentialTypeRef(ExistentialTypeRef existentialTypeRef) {
			// step #1: substitute type variables in corresponding wildcard
			existentialTypeRef = (ExistentialTypeRef) super.caseExistentialTypeRef(existentialTypeRef);
			// step #2: capture a reopened ExistentialTypeRef (if requested)
			if (captureContainedWildcards && existentialTypeRef.isReopened()) {
				existentialTypeRef = TypeUtils.captureWildcard(existentialTypeRef.getWildcard());
			}
			return existentialTypeRef;
		}

		@Override
		protected boolean caseThisTypeRef_shouldBind(ThisTypeRef thisTypeRef) {
			return true;
		}

		@Override
		protected boolean caseBoundThisTypeRef_shouldRebind(BoundThisTypeRef boundThisTypeRef) {
			return true;
		}

		@Override
		protected boolean caseFunctionTypeExprOrRef_isTypeParameterRetained(FunctionTypeExprOrRef F, TypeVariable tv) {
			// type parameters are retained iff they are unbound,
			// i.e. if G does not contain a type variable mapping for them:
			return G.get(tv) == null;
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyTypeParameterUpperBound(TypeRef ub) {
			return substTypeVariables(G, ub, false);
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyDeclaredThisType(TypeRef declThisType) {
			return substTypeVariables(G, declThisType, false);
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyReturnType(TypeRef returnTypeRef) {
			return substTypeVariables(G, returnTypeRef, false);
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyParameterType(TypeRef fparTypeRef) {
			return substTypeVariables(G, fparTypeRef, false);
		}

		// FIXME move to super class
		/**
		 * Performing substitution on the upper bound of an unbound(!) type variable is non-trivial, because we aren't
		 * allowed to copy the type variable and change its upper bound (short version: a type variable is a type and
		 * therefore needs to be contained in a Resource; but our new FunctionTypeExpression 'result' is a TypeRef which
		 * may not be contained in any Resource).
		 * <p>
		 * If type variable substitution on <code>currTV</code>'s upper bound leads to a change of that upper bound (and
		 * only then!), the modified upper bound will be stored in property 'unboundTypeVarsUpperBounds' of
		 * <code>result</code>.
		 * <p>
		 * This has to be carefully aligned with {@link FunctionTypeExpression#getUnboundTypeVarsUpperBounds()} and
		 * {@link FunctionTypeExpression#getTypeVarUpperBound(TypeVariable)}.
		 */

		@Override
		protected TypeRef caseComposedTypeRef_modifyMemberType(TypeRef memberTypeRef) {
			return substTypeVariables(G, memberTypeRef, captureContainedWildcards);
		}

		@Override
		protected TypeArgument caseTypeTypeRef_modifyTypeArg(TypeArgument typeArg) {
			return substTypeVariables(G, typeArg, captureContainedWildcards);
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_modifyDeclaredType(ParameterizedTypeRef typeRef) {
			TypeRef result;

			// (1) start with unchanged typeRef as result (will be copied and changed below if needed)
			result = typeRef;

			// (2) substitute type variables in declared type
			final Type typeRefDeclType = typeRef.getDeclaredType();
			if (typeRefDeclType instanceof TypeVariable) {
				final TypeVariable typeVar = (TypeVariable) typeRefDeclType;

				final Object replacementFromEnvUntyped = G.get(typeVar);
				if (replacementFromEnvUntyped instanceof TypeArgument) {
					// we have a single substitution!
					final TypeArgument replacementFromEnv = (TypeArgument) replacementFromEnvUntyped;
					final TypeArgument replacement = TypeUtils.mergeTypeModifiers(replacementFromEnv, typeRef);
					final TypeRef replacementPrepared = prepareTypeVariableReplacement(typeVar, replacement);
					result = replacementPrepared;
				} else if (replacementFromEnvUntyped instanceof List<?>) {
					// we have multiple substitutions!
					// TODO GHOLD-43 consider resolving the redundancy with handling of recursive mappings in
					// GenericsComputer#addSubstitutions()
					@SuppressWarnings("unchecked")
					final List<TypeArgument> l_raw = (List<TypeArgument>) replacementFromEnvUntyped;
					final List<TypeRef> l = CollectionLiterals.newArrayList();
					for (int i = 0; i < l_raw.size(); i++) {
						final TypeArgument replacement = l_raw.get(i);
						final TypeRef replacementPrepared = prepareTypeVariableReplacement(typeVar, replacement);
						l.add(replacementPrepared);
					}
					if (typeVar.isDeclaredCovariant()) {
						result = typeSystemHelper.createIntersectionType(G, l.toArray(new TypeRef[l.size()]));
					} else if (typeVar.isDeclaredContravariant()) {
						result = typeSystemHelper.createUnionType(G, l.toArray(new TypeRef[l.size()]));
					} else {
						addInconsistentSubstitutions(G, typeVar, l); // will have no effect unless recording was turned
						// on by a validation (see method RuleEnvironmentExtensions#recordInconsistentSubstitutions())
						result = unknown();
					}
					TypeUtils.copyTypeModifiers(result, typeRef);
				} else {
					// we have no substitutions at all!
					// -> no need to change anything here
				}
			}

			return result;
		}

		private TypeRef prepareTypeVariableReplacement(TypeVariable typeVar, TypeArgument replacementArg) {

			// capture wildcards
			TypeRef replacement;
			if (replacementArg instanceof Wildcard) {
				if (captureUponSubstitution) {
					// standard case: capturing is not suppressed
					// -> simply capture the wildcard and proceed as normal
					replacement = TypeUtils.captureWildcard((Wildcard) replacementArg);
				} else {
					// special case: capturing of wildcards is suppressed
					// -> we cannot just keep the wildcard, because Wildcard does not inherit from TypeRef but the
					// value returned from this method is intended as a replacement of type variable 'typeVar' and must
					// therefore be usable in all locations where a type variable, i.e. a TypeRef, may appear.
					// As a solution, we convert to an ExistentialTypeRef in this case, too, and mark the
					// ExistentialTypeRef as "reopened":
					final ExistentialTypeRef capture = TypeUtils.captureWildcard((Wildcard) replacementArg);
					capture.setReopened(true);
					replacement = capture;
				}
			} else {
				replacement = (TypeRef) replacementArg;
			}

			// perform recursive substitution on the replacement (if required)
			final Type replacementDeclType = replacement.getDeclaredType();
			if (typeVar != replacementDeclType
					&& (TypeUtils.isOrContainsRefToTypeVar(replacement)
							|| (replacementDeclType != null && replacementDeclType.isGeneric()))) {
				final Pair<String, TypeRef> guardKey = Pair.of(GUARD_SUBST_TYPE_VARS, replacement);
				if (G.get(guardKey) == null) {
					final RuleEnvironment G2 = wrap(G);
					G2.put(guardKey, Boolean.TRUE);
					replacement = substTypeVariables(G2, replacement, captureContainedWildcards);
				}
			}

			// Always copy the replacement!
			// Rationale:
			// 1) 'replacement' was taken from the type mappings in the rule environment (by our caller) and we don't
			// want those to be reused outside the rule environment.
			// 2) using TypeUtils#copyIfContained() would fail here, because 'replacement' is "contained" in the rule
			// environment via a POJO object reference, but not in the sense of EMF containment.
			// 3) the above call to #substTypeVariables() might have returned the replacement unchanged, so we can't be
			// sure copying has already taken place.
			// 4) the copying performed in some cases by our caller on the value returned by this method will only use
			// #copyIfContained() (e.g. in utility methods for creating union/intersection types), so we can't be sure
			// copying will take place later.
			replacement = TypeUtils.copy(replacement);

			return replacement;
		}

		@Override
		protected TypeArgument caseParameterizedTypeRef_modifyTypeArgument(RuleEnvironment G2, TypeVariable typeParam,
				TypeArgument typeArg) {
			final boolean captureContainedWildcardsNEW = typeArg instanceof Wildcard
					? captureContainedWildcards
					: false;
			TypeArgument argSubst = substTypeVariables(G2, typeArg, captureContainedWildcardsNEW);
			if (typeParam != null) {
				RuleEnvironmentExtensions.addTypeMapping(G2, typeParam, argSubst);
			}
			return argSubst;
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_modifyStructuralMembers(StructuralTypeRef typeRef,
				boolean alreadyCopied) {
			return typeSystemHelper.substTypeVariablesInStructuralMembers(G, typeRef);
		}
	}
}
