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
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS__IMPLICIT_UPPER_BOUND_OF_WILDCARD;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addInconsistentSubstitutions;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getThisType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.utils.TypeUtils;
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
			wildcard = internalSubstTypeVariablesInWildcard(wildcard);
			// step #2: capture the wildcard (if requested)
			if (captureContainedWildcards) {
				return TypeUtils.captureWildcard(null, wildcard); // FIXME null?
			}
			return wildcard;
		}

		@Override
		public TypeArgument caseExistentialTypeRef(ExistentialTypeRef existentialTypeRef) {
			// step #1: substitute type variables in corresponding wildcard
			final Wildcard w = existentialTypeRef.getWildcard();
			final Wildcard wSubst = internalSubstTypeVariablesInWildcard(w);
			if (wSubst != w) {
				final ExistentialTypeRef etfCpy = TypeUtils.copy(existentialTypeRef);
				etfCpy.setWildcard(TypeUtils.copyIfContained(wSubst));
				existentialTypeRef = etfCpy;
			}
			// step #2: capture a reopened ExistentialTypeRef (if requested)
			if (captureContainedWildcards && existentialTypeRef.isReopened()) {
				existentialTypeRef = TypeUtils.captureWildcard(null, existentialTypeRef.getWildcard()); // FIXME null?
			}
			return existentialTypeRef;
		}

		private Wildcard internalSubstTypeVariablesInWildcard(Wildcard wildcard) {
			final RuleEnvironment G2;
			final TypeRef ub;
			if (wildcard.isImplicitUpperBoundInEffect()) {
				final Pair<String, TypeArgument> guardKey = Pair.of(
						GUARD_SUBST_TYPE_VARS__IMPLICIT_UPPER_BOUND_OF_WILDCARD, wildcard);
				final boolean isGuarded = G.get(guardKey) != null;
				if (!isGuarded) {
					// first time here for 'wildcard'
					ub = wildcard.getDeclaredOrImplicitUpperBound();
					G2 = wrap(G);
					G2.put(guardKey, Boolean.TRUE);
				} else {
					// returned here for the same wildcard!
					ub = null;
					G2 = G;
				}
			} else {
				ub = wildcard.getDeclaredOrImplicitUpperBound();
				G2 = G;
			}
			final TypeRef lb = wildcard.getDeclaredLowerBound();
			final TypeRef ubSubst = ub != null ? substTypeVariables(G2, ub, false) : null;
			final TypeRef lbSubst = lb != null ? substTypeVariables(G2, lb, false) : null;
			if (ubSubst != ub || lbSubst != lb) {
				final Wildcard cpy = TypeUtils.copy(wildcard);
				cpy.setDeclaredUpperBound(TypeUtils.copyIfContained(ubSubst));
				cpy.setDeclaredLowerBound(TypeUtils.copyIfContained(lbSubst));
				wildcard = cpy;
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

		// required due to multiple inheritance, to ensure FunctionTypeRef is handled like a FunctionTypeExpression,
		// not as a ParameterizedTypeRef
		@Override
		public TypeArgument caseFunctionTypeRef(FunctionTypeRef typeRef) {
			return caseFunctionTypeExprOrRef(typeRef);
		}

		@Override
		public TypeArgument caseFunctionTypeExprOrRef(FunctionTypeExprOrRef F) {
			final FunctionTypeExpression result = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();

			// let posterity know that the newly created FunctionTypeExpression
			// represents the binding of another FunctionTypeExprOrRef
			result.setBinding(true);

			// if the original 'typeRef' was a FunctionTypeRef, then retain the
			// pointer to its declared type in the copied FunctionTypeExpression
			// (see API doc of FunctionTypeExpression#declaredType for more info)
			result.setDeclaredType(F.getFunctionType());

			for (TypeVariable currTV : F.getTypeVars()) {
				if (G.get(currTV) == null) {
					// unbound type variable -> add to 'unboundTypeVars'
					result.getUnboundTypeVars().add(currTV);
					// substitution on upper bounds (if required)
					performSubstitutionOnUpperBounds(F, currTV, result);
				}
			}

			// substitution on this type
			if (F.getDeclaredThisType() != null) {
				final TypeRef resultDeclaredThisType = substTypeVariables(G, F.getDeclaredThisType(), false);
				result.setDeclaredThisType(TypeUtils.copy(resultDeclaredThisType));
			}

			// substitution on return type
			if (F.getReturnTypeRef() != null) {
				final TypeRef resultReturnTypeRef = substTypeVariables(G, F.getReturnTypeRef(), false);
				result.setReturnTypeRef(TypeUtils.copyIfContained(resultReturnTypeRef));
			}

			result.setReturnValueMarkedOptional(F.isReturnValueOptional());

			// substitution on parameter types
			for (TFormalParameter fpar : F.getFpars()) {
				if (fpar != null) {
					final TFormalParameter newPar = TypesFactory.eINSTANCE.createTFormalParameter();
					newPar.setName(fpar.getName());
					newPar.setVariadic(fpar.isVariadic());
					// astInitializer is not copied since it's part of the AST
					newPar.setHasInitializerAssignment(fpar.isHasInitializerAssignment());

					if (fpar.getTypeRef() != null) {
						final TypeRef resultParTypeRef = substTypeVariables(G, fpar.getTypeRef(), false);
						newPar.setTypeRef(TypeUtils.copyIfContained(resultParTypeRef));
					}

					result.getFpars().add(newPar);
				} else {
					result.getFpars().add(null);
				}
			}

			TypeUtils.copyTypeModifiers(result, F);

			return result;
		}

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
		private void performSubstitutionOnUpperBounds(FunctionTypeExprOrRef F, TypeVariable currTV,
				FunctionTypeExpression result) {

			final TypeRef currTV_declUB = currTV.getDeclaredUpperBound();
			if (currTV_declUB != null) {
				final TypeRef oldUB = F.getTypeVarUpperBound(currTV);
				final TypeRef newUB = substTypeVariables(G, oldUB, false);
				final boolean unchanged = (newUB == currTV_declUB); // note: identity compare is what we want
				if (!unchanged) {
					final int idx = result.getUnboundTypeVars().indexOf(currTV);
					while (result.getUnboundTypeVarsUpperBounds().size() < idx) {
						result.getUnboundTypeVarsUpperBounds().add(null); // add 'null' as padding entry
					}
					final TypeRef ubSubst = TypeUtils.copyIfContained(newUB);
					result.getUnboundTypeVarsUpperBounds().add(ubSubst);
				} else {
					// upper bound after substitution is identical to the one stored in type variable; no need to copy
					// it over to 'result' because operation FunctionTypeExpression#getTypeVarUpperBounds() will use
					// currTV's original upper bound if 'unboundTypeVarsUpperBounds' doesn't contain an upper bound for
					// currTV
				}
			}
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

			// (3) substitute type variables in type arguments
			if (typeRefDeclType != null && typeRefDeclType.isGeneric()) {
				final List<TypeVariable> typeParams = typeRefDeclType.getTypeVars();
				final List<TypeArgument> typeArgs = typeRef.getTypeArgs();

				final int lenParams = typeParams.size();
				final int lenArgs = typeArgs.size();

				// (a) without changing 'result', perform substitution on all type arguments and remember if anyone has
				// changed
				final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
				final TypeArgument[] argsChanged = new TypeArgument[lenArgs];
				boolean haveSubstitution = false;
				for (int i = 0; i < lenArgs; i++) {
					final TypeArgument arg = typeArgs.get(i);
					final boolean captureContainedWildcardsNEW = arg instanceof Wildcard
							? captureContainedWildcards
							: false;
					TypeArgument argSubst = substTypeVariables(G2, arg, captureContainedWildcardsNEW);
					if (argSubst != arg) {
						// n.b.: will only add to argsChanged if changed! (otherwise argsChanged[i] will remain null)
						argsChanged[i] = argSubst;
						haveSubstitution = true;
					}
					if (i < lenParams) {
						final TypeVariable param = typeParams.get(i);
						RuleEnvironmentExtensions.addTypeMapping(G2, param, argSubst);
					}
				}

				// (b) update 'result' with changed type arguments iff(!) one or more have changed
				if (haveSubstitution) {
					if (result == typeRef) {
						result = TypeUtils.copy(typeRef);
					}
					for (int i = 0; i < lenArgs; i++) {
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
