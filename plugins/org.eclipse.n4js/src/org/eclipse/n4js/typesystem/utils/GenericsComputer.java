/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_CHECK_TYPE_ARGUMENT_COMPATIBILITY;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addTypeMappings;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.hasSubstitutionFor;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.GenericType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.TypeExtensions;
import org.eclipse.n4js.ts.types.util.TypeModelUtils;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Type System Helper Strategy for managing type variable mappings in RuleEnvironments of XSemantics. Note that
 * low-level method for adding type mappings to a rule environment are contained in {@link RuleEnvironmentExtensions}.
 */
class GenericsComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeCompareHelper typeCompareHelper;

	@Inject
	private SimplifyComputer simplifyComputer;

	@Inject
	private DeclMergingHelper declMergingHelper;

	/** Convenience for {@link #addSubstitutions(RuleEnvironment, TypeRef, RecursionGuard)} */
	void addSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		addSubstitutions(G, typeRef, null);
	}

	/**
	 * Given a type reference to a generic type G where type variables are already bound, e.g., G&lt;A,B>, this method
	 * adds to the given rule environment the mappings for the type variables of the declared type G using the type
	 * arguments of the passed type reference. Such mappings will be used later to bind type variables.
	 * <p>
	 * For instance, given a type reference G&lt;A,B> to a class
	 *
	 * <pre>
	 * interface I&lt;V> {
	 * 	// ...
	 * }
	 *
	 * class G&lt;T, U> implements I&lt;C> {
	 * 	// ...
	 * }
	 * </pre>
	 *
	 * this function will add the mappings T -> A, U -> B, V -> C.
	 */
	void addSubstitutions(RuleEnvironment G, TypeRef typeRef, RecursionGuard<TypeAlias> guard) {
		Type declType = typeRef.getDeclaredType();
		if (typeRef instanceof ExistentialTypeRef) {
			Wildcard wildcard = ((ExistentialTypeRef) typeRef).getWildcard();
			if (wildcard != null) {
				TypeRef wildcardUB = ts.upperBound(G, wildcard);
				addSubstitutions(G, wildcardUB, guard);
			}
		} else if (typeRef instanceof BoundThisTypeRef) {
			ParameterizedTypeRef actualThisTypeRef = ((BoundThisTypeRef) typeRef).getActualThisTypeRef();
			if (actualThisTypeRef != null) {
				addSubstitutions(G, actualThisTypeRef, guard);
			}
		} else if (typeRef instanceof ComposedTypeRef) {
			for (TypeRef tRef : ((ComposedTypeRef) typeRef).getTypeRefs()) {
				if (tRef != null) {
					addSubstitutions(G, tRef, guard);
				}
			}
		} else if (declType instanceof TypeAlias) {
			primAddSubstitutions(G, typeRef);
			TypeRef actualTypeRef = ((TypeAlias) declType).getTypeRef();
			if (actualTypeRef != null && actualTypeRef.getDeclaredType() != declType) {
				RecursionGuard<TypeAlias> guardOrNew = (guard == null) ? new RecursionGuard<>() : guard;
				if (guardOrNew.tryNext((TypeAlias) declType)) {
					addSubstitutions(G, actualTypeRef, guardOrNew);
				}
			}
		} else if (declType instanceof TypeVariable) {
			TypeRef currBound = ((TypeVariable) declType).getDeclaredUpperBound();
			if (currBound != null) {
				addSubstitutions(G, currBound, guard);
			}
		} else if (declType instanceof TClassifier) {
			// note: despite its name, collectSuperTypeRefs will also return typeRef itself!!
			for (TypeRef currBaseType : collectSuperTypeRefs(typeRef)) {
				primAddSubstitutions(G, currBaseType);
			}
		} else {
			primAddSubstitutions(G, typeRef);
		}
	}

	/**
	 * Adds substitutions for an individual type reference (i.e. without taking into account inheritance hierarchies,
	 * bounds, aliases, etc.).
	 */
	private void primAddSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof ParameterizedTypeRef) {
			List<TypeArgument> typeRefTypeArgs = typeRef.getTypeArgsWithDefaults();
			if (!typeRefTypeArgs.isEmpty()) {
				Type declType = ((ParameterizedTypeRef) typeRef).getDeclaredType();
				if (declType instanceof GenericType) {
					Iterator<TypeVariable> varIter = ((GenericType) declType).getTypeVars().iterator();
					for (TypeArgument typeArg : typeRefTypeArgs) {
						if (varIter.hasNext()) {
							TypeVariable typeVar = varIter.next();
							addSubstitution(G, typeVar, typeArg);
						}
					}
				}
			}
			if (typeRef instanceof StructuralTypeRef) {
				restorePostponedSubstitutionsFrom(G, (StructuralTypeRef) typeRef);
			}
		}
	}

	/**
	 * Adds an individual substitution. TODO proper handling of type variables with multiple solutions
	 * (union/intersection on TypeRef level, not on variable level! or: disallow entirely)
	 */
	private void addSubstitution(RuleEnvironment G, TypeVariable typeVar, TypeArgument typeArg) {

		// resolve typeArg
		Object actualTypeArg = typeArg;
		// simplify to avoid recursion, see test GH-2344_Recursion_Bug_Type_Judgement.n4js.xt
		actualTypeArg = (actualTypeArg instanceof ComposedTypeRef)
				? simplifyComputer.simplify(G, (ComposedTypeRef) actualTypeArg, true)
				: actualTypeArg;

		while (hasSubstitutionFor(G, actualTypeArg)) {
			// otherwise #hasSubstitutionFor() would not have returned true
			TypeRef actualTypeArgCasted = (TypeRef) actualTypeArg;
			Object fromEnv = G.getEnvironment().get(actualTypeArgCasted.getDeclaredType());
			actualTypeArg = (fromEnv instanceof TypeRef)
					? TypeUtils.mergeTypeModifiers((TypeRef) fromEnv, actualTypeArgCasted, false)
					: fromEnv;
		}

		// combine actualTypeArg with current substitution target (if required)
		Object currSubstitute = G.get(typeVar);

		// ignore reflexive
		if (currSubstitute == typeVar) {
			currSubstitute = null;
		}

		G.put(typeVar, mergeTypeArgs(G, currSubstitute, actualTypeArg));
	}

	private Object mergeTypeArgs(RuleEnvironment G, Object... typeArgs) {
		List<Object> result = new ArrayList<>();

		for (Object currTypeArg : typeArgs) {
			Collection<?> l = (currTypeArg instanceof Collection<?>)
					? (Collection<?>) currTypeArg
					: currTypeArg == null
							? Collections.emptyList()
							: List.of(currTypeArg);

			for (Object currO : l) {
				if (currO != null && !typeArgAwareContains(G, result, currO)) {
					result.add(currO);
				}
			}
		}

		if (result.size() >= 2) {
			return result;
		}
		if (result.size() == 1) {
			return result.get(0);
		}

		return null;
	}

	private boolean typeArgAwareContains(RuleEnvironment G, Collection<?> l, Object o) {
		if (o instanceof TypeArgument) {
			TypeArgument oSubst = ts.substTypeVariables(G, (TypeArgument) o);
			for (Object currO : l) {
				if (currO instanceof TypeArgument) {
					TypeArgument currOSubst = ts.substTypeVariables(G, (TypeArgument) currO);
					if (typeCompareHelper.compare(currOSubst, oSubst) == 0) {
						return true;
					}
				}
			}
			return false;
		}

		return l.contains(o);
	}

	// note: this method is redundant with AllSuperTypeRefsCollector, but we cannot use it
	// here, because in case of diamond hierarchies (i.e. one type appears more than once)
	// we rely on (1) typeRefs appearing more than once in the returned list and (2) a
	// particular order (this situation seems not ideal and should maybe be refactored)
	private List<ParameterizedTypeRef> collectSuperTypeRefs(TypeRef typeRef) {
		List<TypeRef> result = new ArrayList<>();
		primCollectSuperTypeRefs(typeRef, result, new RecursionGuard<TypeRef>());
		List<ParameterizedTypeRef> result2 = new ArrayList<>(
				AllSuperTypeRefsCollector.collect((ParameterizedTypeRef) typeRef, declMergingHelper));
		result2.add(0, (ParameterizedTypeRef) typeRef);
		return result2;
	}

	private void primCollectSuperTypeRefs(TypeRef typeRef, List<TypeRef> addHere, RecursionGuard<TypeRef> guard) {
		if (guard.tryNext(typeRef)) {
			addHere.add(typeRef);
			for (TypeRef currSuperTypeRef : getAllSuperTypes(typeRef.getDeclaredType())) {
				primCollectSuperTypeRefs(currSuperTypeRef, addHere, guard);
			}
			guard.done(typeRef);
		}
	}

	private List<ParameterizedTypeRef> getAllSuperTypes(Type type) {
		if (type instanceof TClass) {
			TClass tclass = (TClass) type;
			List<ParameterizedTypeRef> result = new ArrayList<>();
			if (tclass.getSuperClassRef() != null) {
				result.add(tclass.getSuperClassRef());
			}
			result.addAll(tclass.getImplementedInterfaceRefs());
			return result;
		}
		if (type instanceof TInterface) {
			return ((TInterface) type).getSuperInterfaceRefs();
		}
		return Collections.emptyList();
	}

	/**
	 * Add type variable substitutions for the given property access expression. If the property expression is
	 * parameterized, the explicitly provided type arguments will be used, otherwise this method will do nothing.
	 */
	void addSubstitutions(RuleEnvironment G, ParameterizedPropertyAccessExpression paExpr) {
		if (paExpr.isParameterized()) { // Note: Default type args not checked
			IdentifiableElement prop = paExpr.getProperty();
			if (prop instanceof Type) {
				List<TypeRef> typeArgs = toList(map(paExpr.getTypeArgs(), ta -> ta.getTypeRef()));
				addTypeMappings(G, ((Type) prop).getTypeVars(), typeArgs);
			}
		}
	}

	/**
	 * Add type variable substitutions for the given call expression. If the call expression is parameterized, the
	 * explicitly provided type arguments will be used, otherwise this method tries to infer type arguments from types
	 * of function/method arguments and the expected return type.
	 *
	 * @param targetTypeRef
	 *            the inferred type of <code>callExpr.target</code> or <code>null</code> to let this method do the
	 *            inference; only purpose of this argument is to avoid an unnecessary 2nd type inference if caller has
	 *            already performed this.
	 */
	void addSubstitutions(RuleEnvironment G, ParameterizedCallExpression callExpr, FunctionTypeExprOrRef targetTypeRef,
			boolean defaultsTypeArgsToAny) {
		addSubstitutions(G, callExpr, true, targetTypeRef, defaultsTypeArgsToAny);
	}

	void addSubstitutions(RuleEnvironment G, NewExpression newExpr, TMethod constructSignature) {
		addSubstitutions(G, newExpr, false, TypeExtensions.ref(constructSignature), false);
	}

	private void addSubstitutions(RuleEnvironment G, ParameterizedAccess paramAccessExpr, boolean isCallExpr,
			FunctionTypeExprOrRef targetTypeRef, boolean defaultsTypeArgsToAny) {
		// restore type mappings from postponed substitutions
		// TODO what if the structural type ref is contained in another typeRef (e.g. in a ComposedTypeRef)???
		// TODO is there a better place to do this???
		if (targetTypeRef.getReturnTypeRef() instanceof StructuralTypeRef) {
			restorePostponedSubstitutionsFrom(G, (StructuralTypeRef) targetTypeRef.getReturnTypeRef());
		}
		for (TFormalParameter currFpar : targetTypeRef.getFpars()) {
			if (currFpar.getTypeRef() instanceof StructuralTypeRef) {
				restorePostponedSubstitutionsFrom(G, (StructuralTypeRef) currFpar.getTypeRef());
			}
		}

		if (targetTypeRef.isGeneric()) {
			List<? extends TypeArgument> typeArgs = null;

			if (!N4JSLanguageUtils.isPoly(targetTypeRef, paramAccessExpr)) {
				Function<TypeRef, TypeRef> mapDefaultTypeArg;
				if (defaultsTypeArgsToAny) {
					mapDefaultTypeArg = (TypeRef typeRef) -> (typeRef == null) ? null : anyTypeRef(G);
				} else {
					mapDefaultTypeArg = Function.identity();
				}
				typeArgs = TypeModelUtils.getTypeArgsWithDefaults(targetTypeRef,
						toList(map(paramAccessExpr.getTypeArgs(), ta -> ta.getTypeRef())), mapDefaultTypeArg);
			} else if (isCallExpr) {
				typeArgs = ASTMetaInfoUtils.getInferredTypeArgs((ParameterizedCallExpression) paramAccessExpr);
			}
			if (typeArgs == null) {
				Collections.emptyList();
			}
			addTypeMappings(G, targetTypeRef.getTypeVars(), typeArgs);
		}
	}

	/**
	 * Helper method for Xsemantics substTypeVariables judgment. Will either directly substitute in a copy of 'typeRef'
	 * or postpone substitution. The given 'typeRef' is never changed.
	 */
	TypeRef substTypeVariablesInStructuralMembers(RuleEnvironment G, StructuralTypeRef typeRef) {
		if (typeRef.getStructuralMembers().isEmpty() && typeRef.getPostponedSubstitutions().isEmpty()) {
			// nothing to store/postpone -> return unchanged
			return (TypeRef) typeRef;
		}

		StructuralTypeRef result = TypeUtils.copy(typeRef);
		if (result.getGenStructuralMembers().size() == result.getStructuralMembers().size()) {
			// CASE 1: all structural members contained in the type reference
			// -> directly substitute within the members in 'result'
			// (rationale: we just had to create a copy of all members anyway; no point in postponing substitutions)

			for (TStructMember member : result.getGenStructuralMembers()) {
				// create list up-front to not confuse tree iterator when replacing nodes!
				List<TypeArgument> l = new ArrayList<>();
				TreeIterator<EObject> iter = member.eAllContents();
				while (iter.hasNext()) {
					EObject obj = iter.next();
					if (obj instanceof TypeArgument) {
						l.add((TypeArgument) obj);
						iter.prune();
					}
				}

				for (TypeArgument ta : l) {

					TypeArgument taSubst = ts.substTypeVariables(G, ta);
					if (taSubst != null && taSubst != ta) {
						EcoreUtil.replace(ta, taSubst);
					}
				}
			}

		} else {
			// CASE 2: the structural members reside in the AST or in a TStructuralType in the TModule
			// -> postpone substitutions
			// (rationale: we cannot change the AST nor the TModule and we want to avoid copying the members to
			// genStructuralMembers of 'result' for performance reasons (memory))

			storePostponedSubstitutionsIn(G, result);
		}
		return (TypeRef) result;
	}

	/**
	 * For all type variables used in the structural members of 'typeRef', this method stores the type variable mappings
	 * defined in G to the given 'typeRef' (stored in property 'postponedSubstitutions'). This allows to defer actual
	 * substitution in the structural members until a later point in time.
	 * <p>
	 * For more details, see the API documentation for property 'postponedSubstitutions' of {@link StructuralTypeRef}.
	 *
	 * @see #restorePostponedSubstitutionsFrom(RuleEnvironment,StructuralTypeRef)
	 */
	void storePostponedSubstitutionsIn(RuleEnvironment G, StructuralTypeRef typeRef) {
		// collect all type variables used in the structural members of typeRef
		Set<TypeVariable> typeVarsInMembers = TypeUtils.getTypeVarsInStructMembers(typeRef);
		// remove all type variables for which a postponed substitution exists (i.e. don't overwrite existing mappings!)
		// (rationale: we have to treat 'typeRef' as if the postponed substitutions were already applied; but then
		// those type variables would no longer be in the structural members of typeRef (they would have been replaced))
		// Example: 'typeRef' is ~Object with { T1 prop1; T2 prop2; } [[T1->A]] and 'G' contains T1->X, T2->X
		typeVarsInMembers.removeIf(currVar -> typeRef.hasPostponedSubstitutionFor(currVar));
		// add new mappings for these type variables
		List<TypeVariableMapping> bindings = new ArrayList<>();
		for (TypeVariable currVar : typeVarsInMembers) {
			Object currArg = G.get(currVar);
			if (currArg instanceof TypeArgument) {
				TypeArgument currArgCpy = TypeUtils.copy((TypeArgument) currArg); // always copy!
				// important: store substitutions in argument!
				if (currArgCpy instanceof StructuralTypeRef) {
					storePostponedSubstitutionsIn(G, (StructuralTypeRef) currArgCpy);
				}
				// create & add binding
				bindings.add(TypeUtils.createTypeVariableMapping(currVar, currArgCpy));
			}
		}
		typeRef.getPostponedSubstitutions().addAll(bindings);
		// apply substitutions to target values of mappings
		// Esp. important if 'typeRef' already contained postponed substitutions when this method was invoked.
		// Example: 'typeRef' is ~Object with { T1 prop1; T2 prop2; } [[T1->A, T2->α]] and 'G' contains α->B
		for (TypeVariableMapping tvmapping : typeRef.getPostponedSubstitutions()) {
			TypeArgument typeArgSubst = ts.substTypeVariables(G, tvmapping.getTypeArg());
			if (typeArgSubst != null && typeArgSubst != tvmapping.getTypeArg()) {
				tvmapping.setTypeArg(typeArgSubst);
			}
		}
	}

	/**
	 * Restores type variable mappings from the given structural type reference 'typeRef' to rule environment 'G'.
	 * Assumes that method {@link #storePostponedSubstitutionsIn(RuleEnvironment,StructuralTypeRef)} has been invoked on
	 * the given 'typeRef' before.
	 */
	void restorePostponedSubstitutionsFrom(RuleEnvironment G, StructuralTypeRef typeRef) {
		for (TypeVariableMapping currMapping : typeRef.getPostponedSubstitutions()) {
			G.put(currMapping.getTypeVar(), TypeUtils.copy(currMapping.getTypeArg()));
		}
	}

	/**
	 * Checks compatibility of the given type arguments, e.g. as required as part of a subtype check such as
	 * {@code G<T> <: G<S>}. In case of wildcards and open existential types, a bounds check will be performed.
	 *
	 * @param leftArg
	 *            left type argument to check.
	 * @param rightArg
	 *            right type argument to check.
	 * @param varianceOpt
	 *            the variance. Usually this is the definition-site variance of the corresponding type parameter, but
	 *            can be some special value in certain contexts (e.g. in {@link TypeTypeRef}s). If absent, invariance
	 *            will be assumed.
	 * @param useFancyErrMsg
	 *            if <code>true</code>, will use methods
	 *            {@link N4JSTypeSystem#equaltype(RuleEnvironment, TypeArgument, TypeArgument)
	 *            N4JSTypeSystem#equaltype()} and
	 *            {@link N4JSTypeSystem#supertype(RuleEnvironment, TypeArgument, TypeArgument) #supertype()} instead of
	 *            plain subtype checks. Will only affect error messages.
	 * @return a result of the appropriate subtype check(s).
	 */
	public Result checkTypeArgumentCompatibility(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg,
			Optional<Variance> varianceOpt, boolean useFancyErrMsg) {

		Variance variance = varianceOpt.or(Variance.INV);

		// !!! keep the following aligned with below method #reduceTypeArgumentCompatibilityCheck() !!!

		TypeRef leftArgUpper = ts.upperBound(G, leftArg);
		TypeRef leftArgLower = ts.lowerBound(G, leftArg);
		TypeRef rightArgUpper = ts.upperBound(G, rightArg);
		TypeRef rightArgLower = ts.lowerBound(G, rightArg);

		// minor tweak to slightly beautify error messages
		// (i.e. having "not equals to" instead of "not a subtype" in a random direction)
		if (useFancyErrMsg
				&& variance == Variance.INV
				&& leftArgUpper == leftArg && leftArgLower == leftArg
				&& rightArgUpper == rightArg && rightArgLower == rightArg) {
			return ts.equaltype(G, leftArg, rightArg);
		}

		// guard against infinite recursion due to recursive implicit upper bounds, such as in
		//
		// class A<T extends A<?>> {}
		//
		// and
		//
		// class X<T extends B<?>> {}
		// class Y<T extends X<?>> {}
		// class B<T extends Y<?>> {}
		//
		RuleEnvironment G2;
		if (rightArg instanceof Wildcard && ((Wildcard) rightArg).isImplicitUpperBoundInEffect()) {
			// we're dealing with implicit upper bounds -> need to guard against infinite loop
			Pair<String, TypeArgument> guardKey = Pair.of(GUARD_CHECK_TYPE_ARGUMENT_COMPATIBILITY, rightArg);
			boolean isGuarded = G.get(guardKey) != null;
			if (!isGuarded) {
				// first time here for wildcard 'rightArg'
				// -> continue as usual but add guard to rule environment
				G2 = wrap(G);
				G2.put(guardKey, Boolean.TRUE);
			} else {
				// returned here for the same wildcard!
				// -> ignore implicit upper bound on right-hand side to break infinite loop
				rightArgUpper = topTypeRef(G);
				G2 = G; // won't add another guard, so no need to wrap G
			}
		} else {
			// not dealing with implicit upper bounds -> just continue as usual without guarding
			G2 = G;
		}

		// require leftArgUpper <: rightArgUpper, except we have contravariance
		if (variance != Variance.CONTRA) {
			Result result = ts.subtype(G2, leftArgUpper, rightArgUpper);
			if (result.isFailure()) {
				return result;
			}
		}
		// require rightArgLower <: leftArgLower, except we have covariance
		if (variance != Variance.CO) {
			Result result = (useFancyErrMsg)
					? ts.supertype(G2, leftArgLower, rightArgLower)
					: ts.subtype(G2, rightArgLower, leftArgLower);
			if (result.isFailure()) {
				return result;
			}
		}
		return Result.success();
	}

	/**
	 * Same as {@link #checkTypeArgumentCompatibility(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)},
	 * but instead of actually performing the compatibility check, 0..2 {@link TypeConstraint}s are returned that
	 * represent the compatibility check.
	 */
	List<TypeConstraint> reduceTypeArgumentCompatibilityCheck(RuleEnvironment G, TypeArgument leftArg,
			TypeArgument rightArg, Optional<Variance> varianceOpt, boolean useFancyConstraints) {

		Variance variance = varianceOpt.or(Variance.INV);

		// !!! keep the following aligned with above method #checkTypeArgumentCompatibility() !!!

		TypeRef leftArgUpper = ts.upperBound(G, leftArg);
		TypeRef leftArgLower = ts.lowerBound(G, leftArg);
		TypeRef rightArgUpper = ts.upperBound(G, rightArg);
		TypeRef rightArgLower = ts.lowerBound(G, rightArg);

		List<TypeConstraint> result = new ArrayList<>(2);

		// require leftArgUpper <: rightArgUpper, except we have contravariance
		if (variance == Variance.INV) {
			if (leftArgUpper == leftArgLower && rightArgLower == rightArgUpper) {
				// having a single constraint ⟨ α = X ⟩ instead of two constraints ⟨ α :> X ⟩, ⟨ α <: X ⟩ helps the
				// solver to avoid large unions in which one element is the super type of all others, in certain typical
				// cases involving array/object literals)
				result.add(new TypeConstraint(leftArgUpper, rightArgUpper, Variance.INV));
			} else {
				result.add(new TypeConstraint(leftArgUpper, rightArgUpper, Variance.CO));
				result.add(new TypeConstraint(rightArgLower, leftArgLower, Variance.CO));
			}
		} else if (variance == Variance.CO) {
			result.add(new TypeConstraint(leftArgUpper, rightArgUpper, Variance.CO));
		} else if (variance == Variance.CONTRA) {
			// require rightArgLower <: leftArgLower, except we have covariance
			result.add(new TypeConstraint(rightArgLower, leftArgLower, Variance.CO));
		}

		return result;
	}

	// NOTE: this method is only used by JoinComputer; since JoinComputer and MeetComputer have not been
	// used and therefore not been updated for a long time, probably also this method is out-dated!
	TypeRef bindTypeVariables(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof FunctionTypeRef) {
			return typeRef;
		}
		if (typeRef instanceof ParameterizedTypeRef) {
			if (typeRef.getDeclaredType() instanceof TypeVariable) {
				TypeRef boundType = (TypeRef) G.get(typeRef.getDeclaredType());
				return boundType;
			} else if (typeRef.getDeclaredType().isGeneric()) {
				TypeRef ptr = TypeUtils.copy(typeRef);

				int i = 0;
				while (i < typeRef.getDeclaredType().getTypeVars().size()) {
					TypeVariable typeVar = typeRef.getDeclaredType().getTypeVars().get(i);
					TypeRef boundType = (TypeRef) G.get(typeVar);
					if (boundType != null) {
						while (ptr.getDeclaredTypeArgs().size() <= i) {
							ptr.getDeclaredTypeArgs().add(null);
						}
						ptr.getDeclaredTypeArgs().set(i, TypeUtils.copy(boundType));
					}
					i = i + 1;
				}
				return ptr;
			} else {
				return typeRef;
			}
		}
		return typeRef;
	}
}
