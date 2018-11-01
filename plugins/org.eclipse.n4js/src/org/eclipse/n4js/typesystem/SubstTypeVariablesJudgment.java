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
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
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
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Substitutes type variables (that is, replaces type variables with actual type arguments taken from the rule
 * environment).
 * <p>
 * The given typeArg will never be changed, instead a copy will be created to reflect the substitution. If nothing was
 * substituted (i.e. given typeArg does not contain any type variable or only type variables without a binding in the
 * rule environment), then no copy will be created and typeArg will be returned. Therefore, client code can do an
 * identity check on the return value to find out if a substitution was performed.<br>
 * TODO currently unnecessary copies are created in some cases; clean-up code to make last statement valid!
 * <p>
 * Invariant: if you put in a TypeRef, you'll get a TypeRef back (only other case: put in a Wildcard and you'll get a
 * Wildcard). But this is not true for subclasses of TypeRef, e.g. put in a FunctionTypeRef and you might get a
 * FunctionTypeExpression back).
 */
/* package */ final class SubstTypeVariablesJudgment extends AbstractJudgment {

	/**
	 * Never returns <code>null</code>, EXCEPT if <code>null</code> is passed in as type argument.
	 */
	public TypeArgument apply(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg == null) {
			return null;
		}
		final SubstTypeVariablesSwitch theSwitch = new SubstTypeVariablesSwitch(G);
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

		public SubstTypeVariablesSwitch(RuleEnvironment G) {
			this.G = G;
		}

		// the following 3 methods are provided to increase readability of recursive invocations of #doSwitch()

		@SuppressWarnings("unused")
		private Wildcard substTypeVariables(RuleEnvironment G2, Wildcard wildcard) {
			return (Wildcard) substTypeVariables(G2, (TypeArgument) wildcard);
		}

		private TypeRef substTypeVariables(RuleEnvironment G2, TypeRef typeRef) {
			return (TypeRef) substTypeVariables(G2, (TypeArgument) typeRef);
		}

		private TypeArgument substTypeVariables(RuleEnvironment G2, TypeArgument typeArg) {
			if (G2 == this.G) {
				return doSwitch(typeArg);
			} else {
				return apply(G2, typeArg);
			}
		}

		/** Base case. */
		@Override
		public TypeArgument caseTypeArgument(TypeArgument typeArg) {
			return typeArg;
		}

		@Override
		public TypeArgument caseWildcard(Wildcard wildcard) {
			TypeRef ub = wildcard.getDeclaredUpperBound();
			if (ub != null) {
				ub = substTypeVariables(G, ub);
			}
			TypeRef lb = wildcard.getDeclaredLowerBound();
			if (lb != null) {
				lb = substTypeVariables(G, lb);
			}
			if (ub != wildcard.getDeclaredUpperBound() || lb != wildcard.getDeclaredLowerBound()) {
				Wildcard cpy = TypeUtils.copy(wildcard);
				cpy.setDeclaredUpperBound(TypeUtils.copyIfContained(ub));
				cpy.setDeclaredLowerBound(TypeUtils.copyIfContained(lb));
				return cpy;
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
			return caseFunctionTypeExprOrRef(typeRef); // FIXME delegation!!!! still needed?
		}

		@Override
		public TypeArgument caseFunctionTypeExprOrRef(FunctionTypeExprOrRef typeRef) {
			return typeSystemHelper.createSubstitutionOfFunctionTypeExprOrRef(G, typeRef);
		}

		@Override
		public TypeArgument caseComposedTypeRef(ComposedTypeRef typeRef) {
			boolean haveReplacement = false;
			final ArrayList<TypeRef> substTypeRefs = CollectionLiterals.newArrayList();
			for (TypeRef currTypeRef : typeRef.getTypeRefs()) {
				TypeRef substTypeRef = substTypeVariables(G, currTypeRef);
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
			// Substitute in Parameterized Typeref.
			TypeArgument tResult = substTypeVariables(G, typeRef.getTypeArg());
			if (tResult != typeRef.getTypeArg()) {
				// changed
				TypeTypeRef result = TypeUtils.copyIfContained(typeRef);
				result.setTypeArg(TypeUtils.copyIfContained(tResult));
				return result;
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
			// TODO GHOLD-43 clean up the following (simplify + resolve redundancy with handling of recursive mappings
			// in GenericsComputer#addSubstitutions())
			Type typeRefDeclType = typeRef.getDeclaredType();
			if (typeRefDeclType instanceof TypeVariable) {
				final TypeVariable typeVar = (TypeVariable) typeRefDeclType;

				final Object tempFromEnvUntyped = G.get(typeVar);
				if (tempFromEnvUntyped instanceof TypeRef) {
					// we have a single substitution!
					final TypeRef tempFromEnv = (TypeRef) tempFromEnvUntyped;
					final TypeRef temp = TypeUtils.mergeTypeModifiers(tempFromEnv, typeRef);

					final Type tempDeclaredType = temp.getDeclaredType();
					final Pair<String, TypeRef> guardKey = Pair.of(GUARD_SUBST_TYPE_VARS, temp);
					if (typeVar != tempDeclaredType
							&& (TypeUtils.isOrContainsRefToTypeVar(temp)
									|| (tempDeclaredType != null && tempDeclaredType.isGeneric()))
							&& G.get(guardKey) == null) {
						final RuleEnvironment G2 = wrap(G);
						G2.put(guardKey, Boolean.TRUE);
						result = substTypeVariables(G2, temp);
						result = TypeUtils.copy(result); // always copy! (the subst-judgment might return 'temp'
						// unchanged; see next comment why we have to copy 'temp')
					} else {
						result = TypeUtils.copy(temp); // always copy! ('temp' is lying in the rule env, don't wanna
						// change content of rule env!)
						// (note: TypeUtils#copyIfContained() would fail in previous lines, because 'temp' will not be
						// "contained" in the sense of EMF containment)
					}
				} else if (tempFromEnvUntyped instanceof List<?>) {
					// we have multiple substitutions!
					@SuppressWarnings("unchecked")
					final List<TypeRef> l_raw = (List<TypeRef>) tempFromEnvUntyped;
					final List<TypeRef> l = CollectionLiterals.newArrayList();
					for (int i = 0; i < l_raw.size(); i++) {
						final TypeRef temp = l_raw.get(i);
						final Type tempDeclaredType = temp.getDeclaredType();
						final Pair<String, TypeRef> guardKey = Pair.of(GUARD_SUBST_TYPE_VARS, temp);
						if (typeVar != tempDeclaredType
								&& (TypeUtils.isOrContainsRefToTypeVar(temp)
										|| (tempDeclaredType != null && tempDeclaredType.isGeneric()))
								&& G.get(guardKey) == null) {
							final RuleEnvironment G2 = wrap(G);
							G2.put(guardKey, Boolean.TRUE);
							TypeRef tempResult = substTypeVariables(G2, temp);
							tempResult = TypeUtils.copy(tempResult); // always copy! (methods #createUnionType() and
							// #createIntersectionType() below will do a #cloneIfContained() which fails here, see
							// above)
							l.add(tempResult);
						} else {
							l.add(TypeUtils.copy(temp)); // always copy! (methods #createUnion/IntersectionType() below
							// will do a #cloneIfContained() which fails here, see above)
						}
					}
					if (typeVar.isDeclaredCovariant()) {
						result = typeSystemHelper.createIntersectionType(G, l.toArray(new TypeRef[l.size()]));
					} else if (typeVar.isDeclaredContravariant()) {
						result = typeSystemHelper.createUnionType(G, l.toArray(new TypeRef[l.size()]));
					} else {
						addInconsistentSubstitutions(G, typeVar, l); // will have no effect unless recording was turned
						// on by a validation (see method RuleEnvironmentExtensions#recordInconsistentSubstitutions())
						result = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
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
					TypeArgument argSubst = substTypeVariables(G, arg);
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
	}
}
