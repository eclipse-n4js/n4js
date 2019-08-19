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
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getThisType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
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

	private final class SubstTypeVariablesSwitch extends TypeRefsSwitch<TypeArgument> {

		private final RuleEnvironment G;
		private final boolean captureContainedWildcards;
		private final boolean captureUponSubstitution;

		public SubstTypeVariablesSwitch(RuleEnvironment G, boolean captureContainedWildcards,
				boolean captureUponSubstitution) {
			this.G = G;
			this.captureContainedWildcards = captureContainedWildcards;
			this.captureUponSubstitution = captureUponSubstitution;
		}

		// the following 3 methods are provided to increase readability of recursive invocations of #doSwitch()

		@SuppressWarnings("unused")
		private Wildcard substTypeVariables(RuleEnvironment G2, Wildcard wildcard,
				boolean captureContainedWildcardsNEW) {
			return (Wildcard) substTypeVariables(G2, (TypeArgument) wildcard, captureContainedWildcardsNEW);
		}

		private TypeRef substTypeVariables(RuleEnvironment G2, TypeRef typeRef,
				boolean captureContainedWildcardsNEW) {
			return (TypeRef) substTypeVariables(G2, (TypeArgument) typeRef, captureContainedWildcardsNEW);
		}

		private TypeArgument substTypeVariables(RuleEnvironment G2, TypeArgument typeArg,
				boolean captureContainedWildcardsNEW) {
			if (G2 == this.G && captureContainedWildcardsNEW == this.captureContainedWildcards) {
				return doSwitch(typeArg);
			} else {
				return apply(G2, typeArg, captureContainedWildcardsNEW, this.captureUponSubstitution);
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
			TypeRef ub = wildcard.getDeclaredUpperBound();
			if (ub != null) {
				ub = substTypeVariables(G, ub, captureContainedWildcards);
			}
			TypeRef lb = wildcard.getDeclaredLowerBound();
			if (lb != null) {
				lb = substTypeVariables(G, lb, captureContainedWildcards);
			}
			if (ub != wildcard.getDeclaredUpperBound() || lb != wildcard.getDeclaredLowerBound()) {
				Wildcard cpy = TypeUtils.copy(wildcard);
				cpy.setDeclaredUpperBound(TypeUtils.copyIfContained(ub));
				cpy.setDeclaredLowerBound(TypeUtils.copyIfContained(lb));
				wildcard = cpy;
			}
			// step #2: capture the wildcard (if requested)
			if (captureContainedWildcards) {
				return TypeUtils.captureWildcard(null, wildcard);
			}
			return wildcard;
		}

		@Override
		public TypeArgument caseThisTypeRef(ThisTypeRef thisTypeRef) {
			final TypeRef boundRefFromEnvUncasted = getThisType(G);
			if (boundRefFromEnvUncasted instanceof BoundThisTypeRef) {
				final BoundThisTypeRef boundRefFromEnv = (BoundThisTypeRef) boundRefFromEnvUncasted;
				final BoundThisTypeRef boundRef = TypeUtils
						.createBoundThisTypeRef(boundRefFromEnv.getActualThisTypeRef());
				// must take use-site typing-strategy from 'thisTypeRef', not the one stored in the environment:
				boundRef.setTypingStrategy(thisTypeRef.getTypingStrategy());
				// must take use-site type modifiers (e.g. optional):
				TypeUtils.copyTypeModifiers(boundRef, thisTypeRef);
				return boundRef;
			}
			return thisTypeRef;
		}

		@Override
		public TypeArgument caseThisTypeRefStructural(ThisTypeRefStructural thisTypeRef) {
			final TypeRef boundRefFromEnvUncasted = getThisType(G);
			if (boundRefFromEnvUncasted instanceof BoundThisTypeRef) {
				final BoundThisTypeRef boundRefFromEnv = (BoundThisTypeRef) boundRefFromEnvUncasted;
				final BoundThisTypeRef boundRef = TypeUtils
						.createBoundThisTypeRefStructural(boundRefFromEnv.getActualThisTypeRef(), thisTypeRef);
				// must take use-site type modifiers (e.g. optional):
				TypeUtils.copyTypeModifiers(boundRef, thisTypeRef);
				return boundRef;
			}
			return thisTypeRef;
		}

		/*
		 * This is needed to remove the ambiguity: FunctionTypeRef is both a FunctionTypeExprOrRef and a
		 * ParameterizedTypeRef, thus the dispatcher needs the exact type. (same pattern as rules
		 * upperBoundFunctionTypeRef, lowerBoundFunctionTypeRef)
		 */
		@Override
		public TypeArgument caseFunctionTypeRef(FunctionTypeRef typeRef) {
			return caseFunctionTypeExprOrRef(typeRef);
		}

		@Override
		public TypeArgument caseFunctionTypeExprOrRef(FunctionTypeExprOrRef typeRef) {
			return typeSystemHelper.createSubstitutionOfFunctionTypeExprOrRef(G, typeRef,
					captureContainedWildcards, captureUponSubstitution);
		}

		@Override
		public TypeArgument caseComposedTypeRef(ComposedTypeRef typeRef) {
			boolean haveReplacement = false;
			final ArrayList<TypeRef> substTypeRefs = CollectionLiterals.newArrayList();
			for (TypeRef currTypeRef : typeRef.getTypeRefs()) {
				TypeRef substTypeRef = substTypeVariables(G, currTypeRef, captureContainedWildcards);
				substTypeRefs.add(substTypeRef);
				haveReplacement |= substTypeRef != currTypeRef;
			}
			if (haveReplacement) {
				ComposedTypeRef result = TypeUtils.copy(typeRef);
				result.getTypeRefs().clear();
				result.getTypeRefs().addAll(TypeUtils.copyAll(substTypeRefs));
				return result;
			}
			return typeRef;
		}

		@Override
		public TypeArgument caseTypeTypeRef(TypeTypeRef typeRef) {
			TypeArgument typeArg = typeRef.getTypeArg();
			if (typeArg != null) {
				// substitute in type argument
				TypeArgument tResult = substTypeVariables(G, typeArg, captureContainedWildcards);
				if (tResult != typeArg) {
					// changed
					TypeTypeRef result = TypeUtils.copy(typeRef);
					result.setTypeArg(tResult);
					return result;
				}
			}
			// nothing changed
			return typeRef;
		}

		@Override
		public TypeArgument caseParameterizedTypeRef(ParameterizedTypeRef typeRef) {
			TypeRef result;

			// (1) start with unchanged typeRef as result (will be copied and changed below if needed)
			result = typeRef;

			// (2) substitute type variables in declared type
			Type typeRefDeclType = typeRef.getDeclaredType();
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

			// (3) substitute type variables in type arguments
			if (typeRefDeclType != null && typeRefDeclType.isGeneric()) {
				final int len = typeRef.getTypeArgs().size();

				// (a) without changing 'result', perform substitution on all type arguments and remember if anyone has
				// changed
				boolean haveSubstitution = false;
				final TypeArgument[] argsChanged = new TypeArgument[len];
				for (int i = 0; i < len; i++) {
					final TypeArgument arg = typeRef.getTypeArgs().get(i);
					TypeArgument argSubst = substTypeVariables(G, arg, false);
					if (argSubst != arg) {
						// n.b.: will only add to argsChanged if changed! (otherwise argsChanged[i] will remain null)
						argsChanged[i] = argSubst;
						haveSubstitution = true;
					}
				}

				// (b) update 'result' with changed type arguments iff(!) one or more have changed
				if (haveSubstitution) {
					if (result == typeRef) {
						result = TypeUtils.copy(typeRef);
					}
					for (int i = 0; i < len; i++) {
						final TypeArgument argCh = argsChanged[i];
						if (argCh != null) {
							result.getTypeArgs().set(i, argCh);
						}
					}
				}
			}

			// (4) substitution in structural members
			if (result instanceof StructuralTypeRef) {
				result = typeSystemHelper.substTypeVariablesInStructuralMembers(G, (StructuralTypeRef) result);
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
					replacement = TypeUtils.captureWildcard(typeVar, (Wildcard) replacementArg);
				} else {
					// special case: capturing of wildcards is suppressed
					// -> we cannot just keep the wildcard, because Wildcard does not inherit from TypeRef but the
					// value returned from this method is intended as a replacement of type variable 'typeVar' and must
					// therefore be usable in all locations where a type variable, i.e. a TypeRef, may appear.
					// As a solution, we convert to an ExistentialTypeRef in this case, too, and mark the
					// ExistentialTypeRef as "reopened":
					final ExistentialTypeRef capture = TypeUtils.captureWildcard(typeVar, (Wildcard) replacementArg);
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
	}
}
