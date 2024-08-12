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

import static org.eclipse.n4js.ts.types.util.TypeExtensions.ref;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addTypeMapping;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.collectAllImplicitSuperTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getCancelIndicator;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Contains some helper methods to compute if type A is a subtype of type B. Note that the main logic for subtype
 * computation is contained in file n4js.xsemantics. For structural typing there is a separate helper class called
 * {@link StructuralTypingComputer}.
 */
@Singleton
class SubtypeComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private DeclMergingHelper declMergingHelper;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	Result checkSameDeclaredTypes(RuleEnvironment G, TypeRef left, TypeRef right, Type rightDeclType) {
		Type leftDT = left.getDeclaredType();
		Type rightDT = right.getDeclaredType();
		Preconditions.checkArgument(leftDT == rightDT);

		if (left.isAliasResolved() && right.isAliasResolved()) {
			// shortcut to mitigate recursion, caused by e.g.: type AliasType = Map<string, string | AliasType>;
			Type leftDeclTypeTmp = left.getOriginalAliasTypeRef().getDeclaredType();
			Type rightDeclTypeTmp = right.getOriginalAliasTypeRef().getDeclaredType();
			if (leftDeclTypeTmp == rightDeclTypeTmp) {
				left = left.getOriginalAliasTypeRef();
				right = right.getOriginalAliasTypeRef();
				rightDeclType = rightDeclTypeTmp;
			}
		}

		if (!left.isGeneric()) {
			return Result.success();
		}

		final List<TypeArgument> leftArgs = left.getTypeArgsWithDefaults();
		final List<TypeArgument> rightArgs = right.getTypeArgsWithDefaults();

		final int leftArgsCount = leftArgs.size();
		final int rightArgsCount = rightArgs.size();
		if (leftArgsCount > 0 && leftArgsCount <= rightArgsCount) { // ignore raw types
			final int len = Math.min(Math.min(leftArgsCount, rightArgsCount), rightDeclType.getTypeVars().size());
			for (int i = 0; i < len; i++) {
				final TypeArgument leftArg = leftArgs.get(i);
				final TypeArgument rightArg = rightArgs.get(i);
				final Variance variance = rightDeclType.getVarianceOfTypeVar(i);

				final Result currResult = tsh.checkTypeArgumentCompatibility(G, leftArg, rightArg,
						Optional.of(variance), false);
				if (currResult.isFailure()) {
					return currResult;
				}
			}
			return Result.success();
		}
		return Result.success(); // always true for raw types
	}

	Result checkDeclaredSubtypes(RuleEnvironment G, ParameterizedTypeRef left, TypeRef right, Type rightDeclType) {
		Type leftDT = left.getDeclaredType();

		List<ParameterizedTypeRef> allSuperTypeRefs = leftDT instanceof ContainerType<?>
				? AllSuperTypeRefsCollector.collect(left, declMergingHelper)
				: CollectionLiterals.newArrayList();
		Iterable<ParameterizedTypeRef> superTypeRefs = IterableExtensions.operator_plus(allSuperTypeRefs,
				collectAllImplicitSuperTypes(G, left));

		// Note: rightDeclType might appear in superTypes several times in case of multiple implementation
		// of the same interface, which is allowed in case of definition-site co-/contravariance.
		// To support such cases without duplicating any logic, we will use judgment 'substTypeVariables' below.
		if (Iterables.any(superTypeRefs, str -> str.getDeclaredType() == rightDeclType)) {
			// at this point we have 1..* type references in superTypeRefs with a declared type of rightDeclType
			// (more than one possible in case of multiple implementation of the same interface, which is legal
			// in case of definition-site co-/contravariance)
			// (a) these type references may contain unbound type variables from lower level of the inheritance
			// hierarchy and (b) in case of more than 1 type reference we have to combine them into a single
			// type reference
			// --> use type variable substitution on a synthetic type reference with a declared type of
			// rightDeclType to solve all those cases without duplicating any logic:
			final RuleEnvironment localG_left = wrap(G);
			tsh.addSubstitutions(localG_left, left);
			final TypeArgument[] syntheticTypeArgs = rightDeclType.getTypeVars().stream()
					.map(tv -> ref(tv))
					.toArray(l -> new TypeArgument[l]);
			final TypeRef syntheticTypeRef = ref(rightDeclType, syntheticTypeArgs);
			final TypeRef effectiveSuperTypeRef = ts.substTypeVariables(localG_left, syntheticTypeRef);
			return ts.subtype(G, effectiveSuperTypeRef, right);
		}
		return null;
	}

	/**
	 * Returns true iff function/method 'left' is a subtype of function/method 'right'.
	 */
	boolean isSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		EList<TypeVariable> leftTypeVars = left.getTypeVars();
		EList<TypeVariable> rightTypeVars = right.getTypeVars();

		if (leftTypeVars.isEmpty() && rightTypeVars.isEmpty()) {

			// both non-generic
			return primIsSubtypeFunction(G, left, right);

		} else if (!leftTypeVars.isEmpty() && rightTypeVars.isEmpty()) {

			// left is generic, right is non-generic
			// (i.e. cases like: {function<T>(T):T} <: {function(string):string})
			// rationale: if there exists a valid binding of left's type variables
			// so that bound(left) <: right, then left <: right

			InferenceContext infCtx = new InferenceContext(ts, tsh, declMergingHelper, operationCanceledManager,
					getCancelIndicator(G), G); // start with no inference variables
			// create an inference for each type in left
			FunctionTypeExprOrRef left_withInfVars = infCtx.newInferenceVariablesFor(left);
			// assuming 'left' was {function<T>(T):T}, then left_withInfVars is now: {function(α):α} (non-generic!)
			infCtx.addConstraint(left_withInfVars, right, Variance.CO);
			Map<InferenceVariable, TypeRef> solution = infCtx.solve(); // will give us something like α->string
			if (solution != null) {
				RuleEnvironment G_solution = newRuleEnvironment(G);
				for (Entry<InferenceVariable, TypeRef> entry : solution.entrySet()) {
					addTypeMapping(G_solution, entry.getKey(), entry.getValue());
				}
				TypeRef leftSubst = ts.substTypeVariables(G_solution, left_withInfVars);
				if (leftSubst instanceof FunctionTypeExprOrRef) {
					return primIsSubtypeFunction(G, (FunctionTypeExprOrRef) leftSubst, right);
				}
			}
			return false;

		} else {

			// at least one is generic
			// require same number of type parameters (an thus both have to be generic)
			if (leftTypeVars.size() != rightTypeVars.size())
				return false;

			//
			// STEP #1: apply ordinary subtype rules for functions
			//
			/*
			 * Here we substitute the type variables in the right function with matching (as in: same index) type
			 * variables of the left function. That is, the following substitution is applied before the function types
			 * are compared (here illustrated with a method override scenario):
			 *
			 * class Super { <T> T m(T t); // {function(T):T} *1 } class Sub extends Super { <U> U m(object o); //
			 * {function(object):U} *2 }
			 *
			 * The signature *1 from Super is converted to a signature {function(U):U} which is then compared to the
			 * subtype's method *2 {function(object):U}.
			 *
			 * The last step is to check the bounds of the type variables themselves.
			 */
			RuleEnvironment G2 = wrap(G);
			for (int i = 0; i < leftTypeVars.size(); i++) {
				addTypeMapping(G2, rightTypeVars.get(i), TypeUtils.createTypeRef(leftTypeVars.get(i)));
			}
			TypeRef rightSubst = ts.substTypeVariables(G2, right);
			if (!(rightSubst instanceof FunctionTypeExprOrRef &&
					primIsSubtypeFunction(G, left, (FunctionTypeExprOrRef) rightSubst)))
				return false;

			//
			// STEP #2: check type variable bounds
			//
			// the following is required in a method override scenario where 'left' is the overriding
			// and 'right' the overridden method
			if (left.getDeclaredType() != null && left.getDeclaredType().eContainer() instanceof TClassifier) {
				tsh.addSubstitutions(G2, TypeUtils.createTypeRef((TClassifier) left.getDeclaredType().eContainer()));
			}

			return isMatchingTypeVariableBounds(G2, leftTypeVars, rightTypeVars);
		}
	}

	/**
	 * Contains the core logic for subtype relation of functions/methods but <em>without</em> taking into account type
	 * variables of generic functions/methods. Generic functions are handled in method
	 * {@link #isSubtypeFunction(RuleEnvironment,FunctionTypeExprOrRef,FunctionTypeExprOrRef)}.
	 */
	private boolean primIsSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {

		// return type
		TypeRef leftReturnTypeRef = left.getReturnTypeRef();
		TypeRef rightReturnTypeRef = right.getReturnTypeRef();
		if (rightReturnTypeRef != null) {

			// f():void <: f():void --> true
			// f():B <: f():void --> true
			// f():B? <: f():void --> true
			// f():void <: f():A --> false, except A==undefined
			// f():B <: f():A --> B <: A
			// f():B? <: f():A --> false (!)
			// f():void <: f():A? --> true (!)
			// f():B <: f():A? --> B <: A
			// f():B? <: f():A? --> B <: A

			// note these special cases, that follow from the above rules:
			// f():void <: f():undefined --> true
			// f():B <: f():undefined --> false (!)
			// f():B? <: f():undefined --> false (!)
			// f():undefined <: f():void --> true
			// f():undefined <: f():A --> true
			// f():undefined <: f():A? --> true

			if (rightReturnTypeRef.getDeclaredType() != voidType(G)) {
				Type rightFunType = right.getDeclaredType();
				boolean isRightReturnOptional = (rightFunType instanceof TFunction)
						? ((TFunction) rightFunType).isReturnValueOptional()
						: right.isReturnValueOptional();

				if (leftReturnTypeRef != null && leftReturnTypeRef.getDeclaredType() != voidType(G)) {
					// both are non-void
					if (left.isReturnValueOptional() && !isRightReturnOptional) {
						return false;
					} else if (!checkTypeArgumentCompatibility(G, leftReturnTypeRef, rightReturnTypeRef, Variance.CO)) {
						return false;
					}
				} else {
					// left is void, right is non-void
					if (!isRightReturnOptional && !ts.equaltypeSucceeded(G, rightReturnTypeRef, undefinedTypeRef(G))) {
						return false;
					}
				}
			}
		}

		// formal parameters
		int k = left.getFpars().size();
		int n = right.getFpars().size();
		if (k <= n) {
			if (k > 0) {
				var i = 0;
				while (i < k) {
					TFormalParameter R = right.getFpars().get(i);
					TFormalParameter L = left.getFpars().get(i);

					if ((R.isVariadic() || R.isOptional()) && !(L.isOptional() || L.isVariadic())) {
						return false;
					}

					if (!checkTypeArgumentCompatibility(G, L.getTypeRef(), R.getTypeRef(), Variance.CONTRA))
						return false;
					i = i + 1;
				}
				TFormalParameter L = left.getFpars().get(k - 1);
				if (L.isVariadic()) {
					while (i < n) {
						TFormalParameter R = right.getFpars().get(i);
						if (!checkTypeArgumentCompatibility(G, L.getTypeRef(), R.getTypeRef(), Variance.CONTRA))
							return false;
						i = i + 1;
					}
				}
			}
		} else { // k>n

			// {function(A, A...)} <: {function(A)}
			int i = 0;
			while (i < n) {
				TFormalParameter R = right.getFpars().get(i);
				TFormalParameter L = left.getFpars().get(i);

				if ((R.isVariadic() || R.isOptional()) && !(L.isOptional() || L.isVariadic())) {
					return false;
				}

				if (!checkTypeArgumentCompatibility(G, L.getTypeRef(), R.getTypeRef(), Variance.CONTRA))
					return false;
				i = i + 1;
			}
			TFormalParameter R = (n > 0)
					? right.getFpars().get(n - 1)
					:
					// if right hand side has no parameters at all, e.g. {function(A?)} <: {function()}
					null;
			while (i < k) {
				TFormalParameter L = left.getFpars().get(i);
				if (!(L.isOptional() || L.isVariadic())) {
					return false;
				}
				if (R != null && R.isVariadic()) {
					if (!checkTypeArgumentCompatibility(G, L.getTypeRef(), R.getTypeRef(), Variance.CONTRA))
						return false;
				}
				i = i + 1;
			}
		}

		// declaredThisType
		// contra-variant behavior:
		TypeRef rThis = right.getDeclaredThisType();
		TypeRef lThis = left.getDeclaredThisType();
		if (rThis != null) {
			return lThis == null || checkTypeArgumentCompatibility(G, lThis, rThis, Variance.CONTRA);
		} else {

			// Should fail:
			if (lThis != null) {
				return false;
			}
		}

		return true;
	}

	private boolean checkTypeArgumentCompatibility(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg,
			Variance variance) {
		Result result = tsh.checkTypeArgumentCompatibility(G, leftArg, rightArg, Optional.of(variance), false);
		return result.isSuccess();
	}

	/**
	 * Checks bounds of type variables in left and right. Upper bound on left side must be a super type of upper bound
	 * on right side.
	 */
	private boolean isMatchingTypeVariableBounds(RuleEnvironment G, List<TypeVariable> left,
			List<TypeVariable> right) {

		// check type variable bounds
		for (var i = 0; i < right.size(); i++) {
			TypeVariable leftTypeVar = left.get(i);
			TypeVariable rightTypeVar = right.get(i);
			TypeRef leftDeclUB = leftTypeVar.getDeclaredUpperBound();
			TypeRef rightDeclUB = rightTypeVar.getDeclaredUpperBound();
			TypeRef leftUpperBound = (leftDeclUB == null)
					? N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G)
					: leftDeclUB;
			TypeRef rightUpperBound = (rightDeclUB == null)
					? N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G)
					: rightDeclUB;
			TypeRef rightUpperBoundSubst = ts.substTypeVariables(G, rightUpperBound);

			// leftUpperBound must be a super(!) type of rightUpperBound,
			// i.e. rightUpperBound <: leftUpperBound
			if (!isSubtype(G, rightUpperBoundSubst, leftUpperBound)) {
				return false;
			}
		}
		return true;
	}

	private boolean isSubtype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts.subtype(G, left, right).isSuccess();
	}
}
