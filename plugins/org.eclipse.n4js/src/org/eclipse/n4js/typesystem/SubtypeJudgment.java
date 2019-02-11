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

import static org.eclipse.n4js.ts.utils.TypeExtensions.ref;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addThisType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.collectAllImplicitSuperTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getReplacement;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.intType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isExistentialTypeToBeReopened;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isFunction;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isObject;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4EnumType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4StringBasedEnumType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.AllSuperTypeRefsCollector;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.StructuralTypingResult;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Iterables;

/* package */ final class SubtypeJudgment extends AbstractJudgment {

	/** See {@link N4JSTypeSystem#subtype(RuleEnvironment, TypeArgument, TypeArgument)}. */
	public Result apply(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		Result result = doApply(G, left, right);
		if (result.isFailure()) {
			// set default failure message
			final String leftMsg = left != null ? left.getTypeRefAsString() : "<null>";
			final String rightMsg = right != null ? right.getTypeRefAsString() : "<null>";
			return result.setDefaultFailureMessage(leftMsg + " is not a subtype of " + rightMsg);
		}
		return result;
	}

	private Result doApply(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg) {

		// get rid of wildcards by taking their upper/lower bound
		final TypeRef left = leftArg instanceof Wildcard ? ts.upperBound(G, leftArg) : (TypeRef) leftArg;
		final TypeRef right = rightArg instanceof Wildcard ? ts.lowerBound(G, rightArg) : (TypeRef) rightArg;
		if (left == null || right == null) {
			return failure();
		}

		// ComposedTypeRef
		if (left instanceof UnionTypeExpression) {
			return applyUnion_Left(G, (UnionTypeExpression) left, right);
		}
		if (right instanceof IntersectionTypeExpression) {
			return applyIntersection_Right(G, left, (IntersectionTypeExpression) right);
		}
		if (left instanceof IntersectionTypeExpression) {
			return applyIntersection_Left(G, (IntersectionTypeExpression) left, right);
		}
		if (right instanceof UnionTypeExpression) {
			return applyUnion_Right(G, left, (UnionTypeExpression) right);
		}

		// internal top/bottom type 'UnkownTypeRef'
		if (left instanceof UnknownTypeRef || right instanceof UnknownTypeRef) {
			return success();
		}

		// void is unrelated to all other types (not even sub-/super-type of top/bottom type)
		final boolean leftIsVoid = TypeUtils.isVoid(left);
		final boolean rightIsVoid = TypeUtils.isVoid(right);
		if (leftIsVoid || rightIsVoid) {
			return resultFromBoolean(leftIsVoid && rightIsVoid);
		}

		// top and bottom types, including the pseudo-bottom type 'null'
		// (NOTE: doing this up-front here instead of in method #applyParameterizedTypeRef()
		// simplifies the handling of other special cases)
		if (left.isBottomType() || right.isTopType()) {
			return success();
		}
		if (TypeUtils.isNull(left) && !TypeUtils.isUndefined(right)) {
			return success();
		}

		// ExistentialTypeRef
		if (left instanceof ExistentialTypeRef) {
			return applyExistentialTypeRef_Left(G, (ExistentialTypeRef) left, right);
		}
		if (right instanceof ExistentialTypeRef) {
			return applyExistentialTypeRef_Right(G, left, (ExistentialTypeRef) right);
		}

		if (left instanceof BoundThisTypeRef) {
			if (right instanceof BoundThisTypeRef) {
				return applyBoundThisTypeRef_Both(G, (BoundThisTypeRef) left, (BoundThisTypeRef) right);
			}
			return applyBoundThisTypeRef_Left(G, (BoundThisTypeRef) left, right);
		}
		if (right instanceof BoundThisTypeRef) {
			return applyBoundThisTypeRef_Right(G, left, (BoundThisTypeRef) right);
		}

		// TypeTypeRef
		if (left instanceof TypeTypeRef) {
			if (right instanceof TypeTypeRef) {
				return applyTypeTypeRef(G, (TypeTypeRef) left, (TypeTypeRef) right);
			} else {
				return resultFromBoolean(
						isObject(G, right)
								|| (isFunction(G, right) && ((TypeTypeRef) left).isConstructorRef()));
			}
		}

		// FunctionTypeExprOrRef
		if (left instanceof FunctionTypeExprOrRef) {
			if (right instanceof FunctionTypeExprOrRef) {
				return applyFunctionTypeExprOrRef(G, (FunctionTypeExprOrRef) left, (FunctionTypeExprOrRef) right);
			} else if (right instanceof ParameterizedTypeRef && right.getDeclaredType() instanceof TFunction) {
				// special case:
				// FunctionTypeExprOrRef on left and a ParameterizedTypeRef pointing to a TFunction on right;
				// normally ParameterizedTypeRef is not allowed to point to a TFunction (an instance of FunctionTypeRef
				// is to be used instead); however, the parser cannot distinguish these cases and will create such an
				// invalid ParameterizedTypeRef whenever the name of a declared function is used in the source code as a
				// reference. To avoid confusing error messages, we provide subtyping support for this case.
				final FunctionTypeRef rightFixed = (FunctionTypeRef) TypeUtils.createTypeRef(right.getDeclaredType());
				TypeUtils.copyTypeModifiers(rightFixed, right);
				return applyFunctionTypeExprOrRef(G, (FunctionTypeExprOrRef) left, rightFixed);
			} else {
				return resultFromBoolean(
						isObject(G, right)
								|| isFunction(G, right));
			}
		}

		// ParameterizedTypeRef
		if (left instanceof ParameterizedTypeRef && right instanceof ParameterizedTypeRef) {
			return applyParameterizedTypeRef(G, (ParameterizedTypeRef) left, (ParameterizedTypeRef) right);
		}

		return failure();
	}

	private Result applyUnion_Left(RuleEnvironment G,
			UnionTypeExpression U, TypeRef S) {
		return requireAllSuccess(
				U.getTypeRefs().stream().map(
						T -> ts.subtype(G, T, S)))
								.trimCauses(); // TODO legacy behavior; could improve error messages here! (same below)
	}

	private Result applyUnion_Right(RuleEnvironment G,
			TypeRef S, UnionTypeExpression U) {
		return requireExistsSuccess(
				U.getTypeRefs().stream().map(
						T -> ts.subtype(G, S, T)))
								.trimCauses(); // legacy behavior; could improve error messages here!
	}

	private Result applyIntersection_Left(RuleEnvironment G,
			IntersectionTypeExpression I, TypeRef S) {
		return requireExistsSuccess(
				I.getTypeRefs().stream().map(
						T -> ts.subtype(G, T, S)))
								.trimCauses(); // legacy behavior; could improve error messages here!
	}

	private Result applyIntersection_Right(RuleEnvironment G,
			TypeRef S, IntersectionTypeExpression I) {
		return requireAllSuccess(
				I.getTypeRefs().stream().map(
						T -> ts.subtype(G, S, T)))
								.trimCauses(); // legacy behavior; could improve error messages here!
	}

	private Result applyParameterizedTypeRef(RuleEnvironment G,
			ParameterizedTypeRef leftOriginal, ParameterizedTypeRef rightOriginal) {
		final TypeRef left = getReplacement(G, leftOriginal);
		final TypeRef right = getReplacement(G, rightOriginal);
		final Type leftDeclType = left.getDeclaredType();
		final Type rightDeclType = right.getDeclaredType();
		if (leftDeclType == null || rightDeclType == null) {
			return success();
		}
		if (leftDeclType.eIsProxy() || rightDeclType.eIsProxy()) {
			return success();
		}
		if ((leftDeclType == intType(G) && rightDeclType == numberType(G))
				|| (leftDeclType == numberType(G) && rightDeclType == intType(G))) {
			return success(); // int <: number AND number <: int (for now, int and number are synonymous)
		}
		if (leftDeclType instanceof TEnum
				&& (rightDeclType == n4EnumType(G)
						|| rightDeclType == objectType(G))) {
			return resultFromBoolean(!AnnotationDefinition.STRING_BASED.hasAnnotation(leftDeclType));
		}
		if (leftDeclType instanceof TEnum
				&& (rightDeclType == n4StringBasedEnumType(G)
						|| rightDeclType == stringType(G)
						|| rightDeclType == stringObjectType(G))) {
			return resultFromBoolean(AnnotationDefinition.STRING_BASED.hasAnnotation(leftDeclType));
		}
		if (leftDeclType == n4StringBasedEnumType(G)
				&& (rightDeclType == stringType(G) || rightDeclType == stringObjectType(G))) {
			return success();
		}
		if (leftDeclType instanceof PrimitiveType
				&& ((PrimitiveType) leftDeclType).getAssignmentCompatible() == rightDeclType) {
			return success();
		}
		if (rightDeclType instanceof PrimitiveType
				&& leftDeclType == ((PrimitiveType) rightDeclType).getAssignmentCompatible()) {
			return success();
		}
		if (leftDeclType instanceof TInterface
				&& !(rightDeclType instanceof TInterface)
				&& right.getTypingStrategy() == TypingStrategy.NOMINAL
				&& !(rightDeclType == n4ObjectType(G)
						|| rightDeclType == objectType(G)
						|| rightDeclType == anyType(G))) {
			return failure();
		}
		// ##########
		boolean structuralTyping = false;
		if (right.isUseSiteStructuralTyping()) { // e.g. foo(~A right){..} foo(left) --> left ~<: right
			final StructuralTypingResult result = typeSystemHelper.isStructuralSubtype(G, left, right);
			if (!result.isValue()) {
				return failure(result.message);
			}
			structuralTyping = true;
		} else if (right.isDefSiteStructuralTyping()) {
			// avoid recursion
			final Pair<String, Pair<TypeRef, TypeRef>> guardKey = Pair.of(
					GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT, Pair.of(left, right));
			final Boolean guard = (Boolean) G.get(guardKey);
			if (guard == null || !guard) {
				final StructuralTypingResult result = typeSystemHelper.isStructuralSubtype(G, left, right);
				if (!result.isValue()) {
					final RuleEnvironment G2 = wrap(G);
					G2.put(guardKey, Boolean.TRUE);
					if (result.isN4ObjectOnLeftWithDefSite()
							// check if left is declared/nominal subtype of right
							&& ts.subtype(G2, left, right).isSuccess()) {
						structuralTyping = true;
					} else {
						return failure(result.message);
					}
				}
				structuralTyping = result.isValue();
			}
		}
		if (structuralTyping) {
			return success();
		}
		// ##########
		// do nominal, right is not a structural type

		// enforce that ~T !<: S except for S = Object and T <: Object and for primitive types on the left
		if ((left.isUseSiteStructuralTyping() || left.isDefSiteStructuralTyping())
				&& !(rightDeclType == objectType(G) && leftDeclType instanceof TClassifier)
				&& !(leftDeclType instanceof PrimitiveType)) {
			return failure("Structural type " + left.getTypeRefAsString()
					+ " is not a subtype of non-structural type " + right.getTypeRefAsString());
		}
		// nominal typing (the default behavior)
		if (leftDeclType instanceof TypeVariable || rightDeclType instanceof TypeVariable) {
			// we have a type variable on one or both sides
			if (leftDeclType == rightDeclType) {
				return success();
			} else if (leftDeclType instanceof TypeVariable) {
				// a case like:
				// (T extends B) <: A (with T being an unbound type variable and A,B two classes with B <: A)
				final TypeRef declUB = ((TypeVariable) leftDeclType).getDeclaredUpperBound();
				final TypeRef ub = declUB != null ? declUB : N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G);
				return requireAllSuccess(
						ts.subtype(G, ub, right));
			} else {
				// all other cases are always false, for example:
				// B <: (T extends A) is always false, even if B <: A
				// (T extends A) <: (S extends B) is always false, even if A === B (note the difference to existential
				// types)
				return failure();
			}
		} else if (leftDeclType == rightDeclType) {
			final List<TypeArgument> leftArgs = left.getTypeArgs();
			final List<TypeArgument> rightArgs = right.getTypeArgs();
			final int leftArgsCount = leftArgs.size();
			final int rightArgsCount = rightArgs.size();
			if (leftArgsCount > 0 && leftArgsCount <= rightArgsCount) { // ignore raw types
				final int len = Math.min(Math.min(leftArgsCount, rightArgsCount), rightDeclType.getTypeVars().size());
				for (int i = 0; i < len; i++) {
					final TypeArgument leftArg = left.getTypeArgs().get(i);
					final TypeArgument rightArg = right.getTypeArgs().get(i);
					final Variance variance = rightDeclType.getVarianceOfTypeVar(i);

					TypeRef leftArgUpper = ts.upperBound(G, leftArg);
					TypeRef leftArgLower = ts.lowerBound(G, leftArg);
					TypeRef rightArgUpper = ts.upperBound(G, rightArg);
					TypeRef rightArgLower = ts.lowerBound(G, rightArg);

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
					final RuleEnvironment G2;
					if (rightArg instanceof Wildcard && ((Wildcard) rightArg).isImplicitUpperBoundInEffect()) {
						// we're dealing with implicit upper bounds -> need to guard against infinite loop
						final Pair<String, TypeArgument> guardKey = Pair.of(
								GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS, rightArg);
						final boolean isGuarded = G.get(guardKey) != null;
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

// @formatter:off
// TODO IDE-1653 reconsider this logic
// seems bogus because it allows G<?> <: G<E> (with E being a closed existential, i.e. ExistentialTypeRef)
// why not just require type equality, i.e. L<:R && R<:L (without taking upper/lower bounds) and let the
// subtype rules for WildCards / ExistentialTypeRefs do the heavy lifting?
// @formatter:on
					Result tempResult = success();
					// require leftArg <: rightArg, except we have contravariance
					if (variance != Variance.CONTRA) {
						tempResult = ts.subtype(G2, leftArgUpper, rightArgUpper);
					}
					// require rightArg <: leftArg, except we have covariance
					if (variance != Variance.CO) {
						if (tempResult.isSuccess()) {
							tempResult = ts.subtype(G2, rightArgLower, leftArgLower);
						}
					}

					if (tempResult.isFailure()) {
						if (tempResult.isOrIsCausedByPriority()) {
							// fail with a custom message including the nested custom failure message:
							return failure(left.getTypeRefAsString()
									+ " is not a subtype of " + right.getTypeRefAsString()
									+ " due to incompatible type arguments: "
									+ tempResult.getPriorityFailureMessage());
						} else {
							// fail with our default message:
							return failure();
						}
					}
				}
				return success();
			} else {
				return success(); // always true for raw types
			}
		} else {
			final List<ParameterizedTypeRef> allSuperTypeRefs = leftDeclType instanceof ContainerType<?>
					? AllSuperTypeRefsCollector.collect((ContainerType<?>) leftDeclType)
					: CollectionLiterals.newArrayList();
			final Iterable<ParameterizedTypeRef> superTypeRefs = IterableExtensions.operator_plus(allSuperTypeRefs,
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
				typeSystemHelper.addSubstitutions(localG_left, left);
				final TypeArgument[] syntheticTypeArgs = rightDeclType.getTypeVars().stream()
						.map(tv -> ref(tv))
						.toArray(l -> new TypeArgument[l]);
				final TypeRef syntheticTypeRef = ref(rightDeclType, syntheticTypeArgs);
				final TypeRef effectiveSuperTypeRef = ts.substTypeVariables(localG_left, syntheticTypeRef);
				return requireAllSuccess(
						ts.subtype(G, effectiveSuperTypeRef, right));
			} else {
				return failure();
			}
		}
	}

	private Result applyFunctionTypeExprOrRef(RuleEnvironment G,
			FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		return resultFromBoolean(typeSystemHelper.isSubtypeFunction(G, left, right));
	}

	private Result applyTypeTypeRef(RuleEnvironment G,
			TypeTypeRef left, TypeTypeRef right) {
		final TypeArgument leftTypeArg = left.getTypeArg();
		final TypeArgument rightTypeArg = right.getTypeArg();
		final boolean leftIsCtorRef = left.isConstructorRef();
		final boolean rightIsCtorRef = right.isConstructorRef();
		final boolean rightHasTypeRef = rightTypeArg instanceof TypeRef;

		if (!leftIsCtorRef && rightIsCtorRef) {
			// case: type{} <: constructor{}
			return failure();

		} else if (rightHasTypeRef && !rightIsCtorRef) {
			// case: type|constructor{} <: type{} AND right doesn't contain wildcard

			// check type arguments
			return requireAllSuccess(
					ts.subtype(G, leftTypeArg, rightTypeArg));

		} else if (rightHasTypeRef && rightIsCtorRef) {
			// constructor{} <: constructor{} AND right doesn't contain wildcard

			final Type left_staticType = typeSystemHelper.getStaticType(G, left);
			final Type right_staticType = typeSystemHelper.getStaticType(G, right);
			if (left_staticType == null || left_staticType.eIsProxy()
					|| right_staticType == null || right_staticType.eIsProxy()) {
				return failure();
			}

			final boolean leftHasCovariantConstructor = left_staticType instanceof TClassifier
					&& N4JSLanguageUtils.hasCovariantConstructor((TClassifier) left_staticType);

			// left must not contain a wildcard, (closed) existential type, this type
			// (except we have a @CovariantConstructor)
			if (!leftHasCovariantConstructor) {
				final boolean hasDisallowedArg = leftTypeArg instanceof Wildcard
						|| leftTypeArg instanceof ExistentialTypeRef
						|| leftTypeArg instanceof ThisTypeRef;
				if (hasDisallowedArg) {
					return failure();
				}
			}

			// check type arguments
			final Result result = ts.subtype(G, leftTypeArg, rightTypeArg);
			if (!result.isSuccess()) {
				return failure(result);
			}

			// check constructors
			if (left_staticType instanceof TypeVariable || right_staticType instanceof TypeVariable) {
				// special case: we have a type variable on one or both sides
				// left <: right if and only if the type variables are identical
				return resultFromBoolean(
						left_staticType == right_staticType);
				// (for a type variable itself we cannot get the constructor, we could only get the constructor
				// of the upper/lower bound; however, in contrast to other type rules, we cannot say
				// constructor{upperBound(T)} <: constructor{X} implies constructor{T} <: constructor{X}
				// because the subtype relation for ConstructorTypeRefs is not transitive; so we need
				// the identical type - in this case: the identical type variable - on both sides)
			} else {
				final TMethod leftCtor = containerTypesHelper.fromContext(getContextResource(G))
						.findConstructor((ContainerType<?>) left_staticType);
				final TMethod rightCtor = containerTypesHelper.fromContext(getContextResource(G))
						.findConstructor((ContainerType<?>) right_staticType);
				if (leftCtor == null || rightCtor == null) {
					return failure();
				}

				// we need the type of the two constructors (i.e. their signature)
				// DO NOT USE "TypeUtils.createTypeRef(leftCtor)", because this would by-pass the handling
				// of forward references during AST traversal! Instead, obtain the type via the type judgment:
				final TypeRef leftCtorRef = ts.type(G, leftCtor);
				final TypeRef rightCtorRef = ts.type(G, rightCtor);

				// support for type variables and [~]~this as type of fpars in constructors
				final RuleEnvironment G_left = wrap(G);
				final RuleEnvironment G_right = wrap(G);
				typeSystemHelper.addSubstitutions(G_left, ref(left_staticType));
				addThisType(G_left, ref(left_staticType));
				typeSystemHelper.addSubstitutions(G_right, ref(right_staticType));
				addThisType(G_right, ref(right_staticType));
				final TypeRef leftCtorRefSubst = ts.substTypeVariables(G_left, leftCtorRef);
				final TypeRef rightCtorRefSubst = ts.substTypeVariables(G_right, rightCtorRef);

				return requireAllSuccess(
						ts.subtype(G, leftCtorRefSubst, rightCtorRefSubst));
			}

		} else {
			// any combination except type{} <: constructor{} AND right contains wildcard

			final TypeRef upperBoundLeft = ts.upperBound(G, leftTypeArg);
			final TypeRef lowerBoundLeft = ts.lowerBound(G, leftTypeArg);
			final TypeRef upperBoundRight = ts.upperBound(G, rightTypeArg);
			final TypeRef lowerBoundRight = ts.lowerBound(G, rightTypeArg);

			return requireAllSuccess(
					ts.subtype(G, upperBoundLeft, upperBoundRight),
					ts.subtype(G, lowerBoundRight, lowerBoundLeft));
		}
	}

	private Result applyExistentialTypeRef_Left(RuleEnvironment G,
			ExistentialTypeRef existentialTypeRef, TypeArgument right) {
		if (isExistentialTypeToBeReopened(G, existentialTypeRef)) {
			// special case: open existential
			// --> we may pick any valid type we want for 'existentialTypeRef'
			// --> only check that 'left' lies within the bounds

			// obtain wildcard from which existentialTypeRef was created
			final Wildcard wildThing = existentialTypeRef.getWildcard();
			// check upper and lower bounds
			final TypeRef upperBound = ts.upperBound(G, wildThing);
			final TypeRef lowerBound = ts.lowerBound(G, wildThing);
			return requireAllSuccess(
					ts.subtype(G, right, upperBound),
					ts.subtype(G, lowerBound, right));
		} else {
			// standard case: closed existential
			// --> the type has been picked but we don't know it
			// --> all we know is the picked type lies within the bounds
			// --> subtype check succeeds if and only if: P<:'right' for *ALL* types P that may have been picked for the
			// existential
			if (existentialTypeRef == right) {
				return success(); // performance tweak
			}
			final TypeRef upperExt = ts.upperBound(G, existentialTypeRef);
			return requireAllSuccess(
					ts.subtype(G, upperExt, right));
		}
	}

	private Result applyExistentialTypeRef_Right(RuleEnvironment G,
			TypeArgument left, ExistentialTypeRef existentialTypeRef) {
		if (isExistentialTypeToBeReopened(G, existentialTypeRef)) {
			// special case: open existential
			// --> we may pick any valid type we want for 'existentialTypeRef'
			// --> only check that 'left' lies within the bounds

			// obtain wildcard from which existentialTypeRef was created
			final Wildcard wildThing = existentialTypeRef.getWildcard();
			// check upper and lower bounds
			final TypeRef upperBound = ts.upperBound(G, wildThing);
			final TypeRef lowerBound = ts.lowerBound(G, wildThing);
			return requireAllSuccess(
					ts.subtype(G, left, upperBound),
					ts.subtype(G, lowerBound, left));
		} else {
			// standard case: closed existential
			// --> the type has been picked but we don't know it
			// --> all we know is the picked type lies within the bounds
			// --> subtype check succeeds if and only if: 'left'<:P for *ALL* types P that may have been picked for the
			// existential
			if (left == existentialTypeRef) {
				return success(); // performance tweak
			}
			final TypeRef lowerExt = ts.lowerBound(G, existentialTypeRef);
			return requireAllSuccess(
					ts.subtype(G, left, lowerExt));
		}
	}

	private Result applyBoundThisTypeRef_Both(RuleEnvironment G,
			BoundThisTypeRef left, BoundThisTypeRef right) {
		// also see subtypeParameterizedTypeRef, simplified here as less cases are possible
		if (right.isUseSiteStructuralTyping()) {
			// e.g. foo(~A right){..} foo(left) --> left ~<: right
			final StructuralTypingResult result = typeSystemHelper.isStructuralSubtype(G, left, right);
			if (!result.isValue()) {
				return failure(result.message);
			}
			return success();
			// def site structural typing not possible in N4Objects constructor
		} else {
			if (left.isUseSiteStructuralTyping() != right.isUseSiteStructuralTyping()) {
				return failure();
			}
			return requireAllSuccess(
					ts.subtype(G, left.getActualThisTypeRef(), right.getActualThisTypeRef()));
		}
	}

	private Result applyBoundThisTypeRef_Left(RuleEnvironment G,
			BoundThisTypeRef boundThisTypeRef, TypeArgument right) {
		if (boundThisTypeRef == right) {
			return success();
		}
		final TypeRef upperExt = ts.upperBound(G, boundThisTypeRef);
		return requireAllSuccess(
				ts.subtype(G, upperExt, right));
	}

	private Result applyBoundThisTypeRef_Right(RuleEnvironment G,
			TypeArgument left, BoundThisTypeRef boundThisTypeRef) {
		if (left == boundThisTypeRef) {
			return success();
		}
		final Type leftType = left.getDeclaredType();
		if (leftType == undefinedType(G) || leftType == nullType(G)) {
			return success();
		}
		// or {
		// val leftBound = left as BoundThisTypeRef
		// G|- leftBound.actualThisTypeRef <: boundThisTypeRef.actualThisTypeRef
		// G|- boundThisTypeRef.actualThisTypeRef <: boundThisTypeRef.actualThisTypeRef
		// } or
		if (boundThisTypeRef.isUseSiteStructuralTyping()
				|| (null != boundThisTypeRef.getActualThisTypeRef()
						&& null != boundThisTypeRef.getActualThisTypeRef().getDeclaredType()
						&& boundThisTypeRef.getActualThisTypeRef().getDeclaredType().isFinal())) {
			// special cases: in the following cases, a subtype check X <: this[C] boils down to X <: C
			// (1) if the rhs is structurally typed
			// (NOTE: structurally typed 'this' on rhs will only occur when type checking NewExpression
			// arguments when referring to a constructor using [~]~this as parameter type.)
			// (2) if the actual type on rhs is final (i.e. if C in above example is final)
			// -->
			// instead of copying all the logic from rule subtypeParameterizedTypeRef,
			// we simply create a corresponding ParameterizedTypeRef for the bound this type,
			// i.e. we treat X <: [~]~this{C} like X <: [~]~C (in case 1)
			// and X <: this{C} like X <: C (in case 2 with C being final)
			final ParameterizedTypeRef resolvedTypeRef = TypeUtils.createResolvedThisTypeRef(boundThisTypeRef);
			return ts.subtype(G, left, resolvedTypeRef);
			// return requireAllSuccess(
			// ts.subtype(G, left, resolvedTypeRef));
		} else {
			return failure();
		}
	}

	// ################################################################################
	// utility methods:

	private Result resultFromBoolean(boolean result) {
		return result ? success() : failure();
	}

	private Result requireAllSuccess(Result... results) {
		if (results == null || results.length == 0) {
			throw new IllegalArgumentException("no results given");
		}
		return requireAllSuccess(Stream.of(results));
	}

	private Result requireAllSuccess(Stream<Result> results) {
		Iterator<Result> iter = results.iterator();
		while (iter.hasNext()) {
			final Result result = iter.next();
			if (!result.isSuccess()) {
				return failure(result);
			}
		}
		return success();
	}

	private Result requireExistsSuccess(Stream<Result> results) {
		Iterator<Result> iter = results.iterator();
		Result firstFailure = null;
		while (iter.hasNext()) {
			final Result result = iter.next();
			if (result.isSuccess()) {
				return success();
			}
			if (firstFailure == null) {
				firstFailure = result;
			}
		}
		return firstFailure != null ? failure(firstFailure) : failure();
	}

	private Result success() {
		return Result.success();
	}

	private Result failure(Result... results) {
		return failure((String) null, results);
	}

	private Result failure(String failureMessage, Result... results) {
		final Result firstFailure = Stream.of(results)
				.filter(Result::isFailure)
				.findFirst().orElse(null);
		return Result.failure(failureMessage, failureMessage != null, firstFailure);
	}
}
