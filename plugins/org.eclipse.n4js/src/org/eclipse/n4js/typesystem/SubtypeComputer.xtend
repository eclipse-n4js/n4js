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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import com.google.inject.Singleton
import it.xsemantics.runtime.RuleEnvironment
import java.util.List
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.xtext.service.OperationCanceledManager

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Contains some helper methods to compute if type A is a subtype of type B.
 * Note that the main logic for subtype computation is contained in file
 * n4js.xsemantics. For structural typing there is a separate helper class
 * called {@link StructuralTypingComputer}.
 */
@Singleton
class SubtypeComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Returns true iff function/method 'left' is a subtype of function/method 'right'.
	 */
	def boolean isSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		val leftTypeVars = left.typeVars;
		val rightTypeVars = right.typeVars;

		if (leftTypeVars.empty && rightTypeVars.empty) {

			// both non-generic
			return primIsSubtypeFunction(G, left, right);

		} else if (!leftTypeVars.empty && rightTypeVars.empty) {

			// left is generic, right is non-generic
			// (i.e. cases like: {function<T>(T):T} <: {function(string):string})
			// rationale: if there exists a valid binding of left's type variables
			// so that bound(left) <: right, then left <: right

			val infCtx = new InferenceContext(ts, tsh, operationCanceledManager, G.cancelIndicator, G); // start with no inference variables
			val left_withInfVars = infCtx.newInferenceVariablesFor(left); // create an inference variable for each type param in left
			// assuming 'left' was {function<T>(T):T}, then left_withInfVars is now: {function(α):α} (non-generic!)
			infCtx.addConstraint(left_withInfVars, right, Variance.CO);
			val solution = infCtx.solve; // will give us something like α->string
			if (solution !== null) {
				val G_solution = G.newRuleEnvironment;
				solution.entrySet.forEach[G_solution.addTypeMapping(key,value)];
				val leftSubst = ts.substTypeVariablesInTypeRef(G_solution, left_withInfVars);
				if (leftSubst instanceof FunctionTypeExprOrRef) {
					return primIsSubtypeFunction(G, leftSubst, right);
				}
			}
			return false;

		} else {

			// at least one is generic
			// require same number of type parameters (an thus both have to be generic)
			if (leftTypeVars.size !== rightTypeVars.size)
				return false;

			//
			// STEP #1: apply ordinary subtype rules for functions
			//
			/*
			 * Here we substitute the type variables in the right function with matching (as in: same index)
			 * type variables of the left function. That is, the following substitution is applied before the
			 * function types are compared (here illustrated with a method override scenario):
			 *
			 * class Super {
			 *   <T> T m(T t); // {function(T):T} *1
			 * }
			 * class Sub extends Super {
			 *   <U> U m(object o); // {function(object):U} *2
			 * }
			 *
			 * The signature *1 from Super is converted to a signature {function(U):U} which is then compared
			 * to the subtype's method *2 {function(object):U}.
			 *
			 * The last step is to check the bounds of the type variables themselves.
			 */
			val G2 = G.wrap
			for (i : 0 ..< leftTypeVars.size) {
				G2.addTypeMapping(rightTypeVars.get(i), TypeUtils.createTypeRef(leftTypeVars.get(i)))
			}
			val TypeRef rightSubst = ts.substTypeVariablesInTypeRef(G2, right);
			if (!(rightSubst instanceof FunctionTypeExprOrRef &&
				primIsSubtypeFunction(G, left, rightSubst as FunctionTypeExprOrRef)))
				return false;

			//
			// STEP #2: check type variable bounds
			//
			// the following is required in a method override scenario where 'left' is the overriding
			// and 'right' the overridden method
			if (left.declaredType?.eContainer instanceof TClassifier)
				addSubstitutions(G2, TypeUtils.createTypeRef(left.declaredType.eContainer as TClassifier));

			return isMatchingTypeVariableBounds(G2, leftTypeVars, rightTypeVars);
		}
	}

	/**
	 * Contains the core logic for subtype relation of functions/methods but <em>without</em>
	 * taking into account type variables of generic functions/methods. Generic functions are handled
	 * in method {@link #isSubtypeFunction(RuleEnvironment,FunctionTypeExprOrRef,FunctionTypeExprOrRef)}.
	 */
	private def boolean primIsSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {

		// return type
		val leftReturnTypeRef = left.returnTypeRef;
		val rightReturnTypeRef = right.returnTypeRef;
		if (rightReturnTypeRef !== null) {

			// f():void <: f():void      --> true
			// f():B    <: f():void      --> true
			// f():B?   <: f():void      --> true
			// f():void <: f():A         --> false, except A==undefined
			// f():B    <: f():A         --> B <: A
			// f():B?   <: f():A         --> false (!)
			// f():void <: f():A?        --> true (!)
			// f():B    <: f():A?        --> B <: A
			// f():B?   <: f():A?        --> B <: A

			// note these special cases, that follow from the above rules:
			// f():void <: f():undefined --> true
			// f():B    <: f():undefined --> false (!)
			// f():B?   <: f():undefined --> false (!)
			// f():undefined <: f():void --> true
			// f():undefined <: f():A    --> true
			// f():undefined <: f():A?   --> true

			if (rightReturnTypeRef.declaredType !== G.voidType) {
				val rightFunType = right.declaredType;
				val boolean isRightReturnOptional = if (rightFunType instanceof TFunction)
						rightFunType.returnValueOptional
					else
						right.isReturnValueOptional;

				if (leftReturnTypeRef.declaredType !== G.voidType) {
					// both are non-void
					if (left.isReturnValueOptional && !isRightReturnOptional) {
						return false;
					} else if (!isSubtype(G, leftReturnTypeRef, rightReturnTypeRef)) {
						return false;
					}
				} else {
					// left is void, right is non-void
					if (!isRightReturnOptional && !ts.equaltypeSucceeded(G, rightReturnTypeRef, G.undefinedTypeRef)) {
						return false;
					}
				}
			}
		}

		// formal parameters
		val k = left.fpars.size;
		val n = right.fpars.size;
		if (k <= n) {
			if (k > 0) {
				var i = 0;
				while (i < k) {
					val R = right.fpars.get(i);
					val L = left.fpars.get(i);

					if ((R.variadic || R.optional) && !(L.optional || L.variadic)) {
						return false;
					}

					if (!isSubtype(G, R.typeRef, L.typeRef))
						return false;
					i = i + 1;
				}
				val L = left.fpars.get(k - 1);
				if (L.variadic) {
					while (i < n) {
						val R = right.fpars.get(i);
						if (!isSubtype(G, R.typeRef, L.typeRef))
							return false;
						i = i + 1;
					}
				}
			}
		} else { // k>n

			// {function(A, A...)} <: {function(A)}
			var i = 0;
			while (i < n) {
				val R = right.fpars.get(i);
				val L = left.fpars.get(i);

				if ((R.variadic || R.optional) && !(L.optional || L.variadic)) {
					return false;
				}

				if (!isSubtype(G, R.typeRef, L.typeRef))
					return false;
				i = i + 1;
			}
			val TFormalParameter R = if (n > 0) {
					right.fpars.get(n - 1)
				} else {
					// if right hand side has no parameters at all, e.g. {function(A?)} <: {function()}
					null
				};
			while (i < k) {
				val L = left.fpars.get(i);
				if (! (L.optional || L.variadic)) {
					return false;
				}
				if (R !== null && R.variadic) {
					if (!isSubtype(G, R.typeRef, L.typeRef))
						return false;
				}
				i = i + 1;
			}
		}

		// declaredThisType
		// contra-variant behavior:
		val rThis = right.declaredThisType
		val lThis = left.declaredThisType
		if (rThis !== null) {
			return lThis === null || isSubtype(G, rThis, lThis);
		} else {

			// Should fail:
			if (lThis !== null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks bounds of type variables in left and right.
	 * Upper bound on left side must be a super type of upper bound on right side.
	 */
	private def boolean isMatchingTypeVariableBounds(RuleEnvironment G, List<TypeVariable> left,
		List<TypeVariable> right) {

		// check type variable bounds
		for (var i = 0; i < right.size; i++) {
			val leftTypeVar = left.get(i)
			val rightTypeVar = right.get(i)
			val leftDeclUB = leftTypeVar.declaredUpperBound;
			val rightDeclUB = rightTypeVar.declaredUpperBound;
			val leftUpperBound = if (leftDeclUB===null) {
					N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G)
				} else {
					leftDeclUB
				};
			val rightUpperBound = if (rightDeclUB===null) {
					N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G)
				} else {
					rightDeclUB
				};
			val rightUpperBoundSubst = ts.substTypeVariablesInTypeRef(G, rightUpperBound);

			// leftUpperBound must be a super(!) type of rightUpperBound,
			// i.e. rightUpperBound <: leftUpperBound
			if (!G.isSubtype(rightUpperBoundSubst, leftUpperBound)) {
				return false
			}
		}
		return true
	}


	/**
	 * Subtype check for {@link ComposedTypeRef}s. Will throw an {@link IllegalArgumentException} if neither
	 * {@code left} nor {@code right} is a {@code ComposedTypeRef}.
	 */
	def public boolean isSubtypeComposedTypeRef(RuleEnvironment G, TypeRef left, TypeRef right) { // TODO better TypeArgument instead of TypeRef?
		// general rule:
		// splitting an intersection on LHS and splitting a union on RHS is illegal

		val leftIsUnion = left instanceof UnionTypeExpression;
		val leftIsIntersection = left instanceof IntersectionTypeExpression;
		val leftIsComposed = leftIsUnion || leftIsIntersection;
		val rightIsUnion = right instanceof UnionTypeExpression;
		val rightIsIntersection = right instanceof IntersectionTypeExpression;
		val rightIsComposed = rightIsUnion || rightIsIntersection;

		val result = if(leftIsComposed && rightIsComposed) {
			// both sides are a ComposedTypeRef
			if(leftIsUnion && rightIsUnion) {
				// must not split union on RHS
				checkUnionOther(G, left as UnionTypeExpression, right)
			} else if(leftIsUnion && rightIsIntersection) {
				// doesn't matter which side we split (could split union on LHS as well as intersection on RHS)
				checkOtherIntersection(G, left, right as IntersectionTypeExpression)
			} else if(leftIsIntersection && rightIsUnion) {
				// TRICKY CASE: we must neither split the intersection on the LHS nor the union on the RHS
				// -> try to apply law of distributivity to change either ...
				//    a) the LHS from an intersection to a union, or
				//    b) the RHS from a union to an intersection.
				val leftMultiplied = convertToOpposingKind(G, left as IntersectionTypeExpression);
				if(leftMultiplied instanceof UnionTypeExpression) {
					// success: multiplication turned the intersection on LHS into a union!
					checkUnionOther(G, leftMultiplied, right)
				} else {
					val rightMultiplied = convertToOpposingKind(G, right as UnionTypeExpression);
					if(rightMultiplied instanceof IntersectionTypeExpression) {
						// success: multiplication turned the union on RHS into an intersection!
						checkOtherIntersection(G, left, rightMultiplied)
					} else {
						// multiplication does not change anything -> fall back to old behavior
						checkIntersectionOther(G, left as IntersectionTypeExpression, right)
						|| checkOtherUnion(G, left, right as UnionTypeExpression)
					}
				}
			} else if(leftIsIntersection && rightIsIntersection) {
				// must not split intersection on LHS
				checkOtherIntersection(G, left, right as IntersectionTypeExpression)
			} else {
				throw new AssertionError("cannot happen")
			}
		} else if(leftIsComposed !== rightIsComposed) {
			// one side is a ComposedTypeRef, the other isn't
			if(leftIsUnion) {
				checkUnionOther(G, left as UnionTypeExpression, right)
			} else if(rightIsUnion) {
				checkOtherUnion(G, left, right as UnionTypeExpression)
			} else if(leftIsIntersection) {
				checkIntersectionOther(G, left as IntersectionTypeExpression, right)
			} else if(rightIsIntersection) {
				checkOtherIntersection(G, left, right as IntersectionTypeExpression)
			} else {
				throw new AssertionError("cannot happen")
			}
		} else {
			// neither side is a ComposedTypeRef
			throw new IllegalArgumentException();
		};

		return result;
	}

	def private boolean checkUnionOther(RuleEnvironment G, UnionTypeExpression left, TypeRef right) {
		return left.typeRefs.forall[T|
			isSubtype(G, T, right)
		];
	}

	def private boolean checkOtherUnion(RuleEnvironment G, TypeRef left, UnionTypeExpression right) {
		return right.typeRefs.exists[T|
			isSubtype(G, left, T)
		];
	}

	def private boolean checkIntersectionOther(RuleEnvironment G, IntersectionTypeExpression left, TypeRef right) {
		return left.typeRefs.exists[T|
			isSubtype(G, T, right)
		];
	}

	def private boolean checkOtherIntersection(RuleEnvironment G, TypeRef left, IntersectionTypeExpression right) {
		return right.typeRefs.forall[T|
			isSubtype(G, left, T)
		];
	}

	/**
	 * Given any composed type reference, this method will try to convert it into a new composed type reference of
	 * opposing kind, i.e. converting a union into an intersection and vice versa.
	 * <p>
	 * For example, given an intersection {@code T1 & T2 & ... & Tn}, this method will first simplify* the intersection
	 * and will then search for a {@code Ti} with {@code 0 < i <= n} of the form {@code Ti1 | Ti2 | ... | Tim}. Then it
	 * will apply commutativity and associativity to obtain
	 * <pre>
	 * (Ti1 | Ti2 | ... | Tim) & (T1 & ... & Ti-1 & Ti+1 & ... & Tn)
	 * </pre>
	 * and further apply distributivity to obtain
	 * <pre>
	 * (Ti1 & (T1 & ... & Ti-1 & Ti+1 & ... & Tn)) | ... | (Tim & (T1 & ... & Ti-1 & Ti+1 & ... & Tn))
	 * </pre>
	 * If no {@code Ti} of the above form can be found, the given composed type reference is returned unchanged, i.e.
	 * the conversion from intersection to union failed.
	 * <p>
	 * (* simplification is done via {@link SimplifyComputer#simplify(RuleEnvironment,ComposedTypeRef)})
	 */
	def private TypeRef convertToOpposingKind(RuleEnvironment G, ComposedTypeRef composedTypeRef) {
		val isIntersection = composedTypeRef instanceof IntersectionTypeExpression;
		val isUnion = !isIntersection;

		// simplify to remove nested composed type refs of the same kind
		val composedTypeRefSimple = simplify(G, composedTypeRef);
		if((isIntersection && !(composedTypeRefSimple instanceof IntersectionTypeExpression))
			|| (isUnion && !(composedTypeRefSimple instanceof UnionTypeExpression))) {
			// the composed type reference changed its kind due to simplification
			// -> just return it without change
			return composedTypeRefSimple;
		}
		val composedTypeRefSimpleCasted = composedTypeRefSimple as ComposedTypeRef;

		// prepare the source type references (unfortunately we have to copy, here)
		// TODO consider refactoring to avoid copying (but this will make code less readable)
		val sourceTypeRefs = TypeUtils.copyAll(composedTypeRefSimpleCasted.typeRefs).toList;

		// find first member type that is a union (if isIntersection) / is an intersection (if isUnion)
		val toBeMultiplied = sourceTypeRefs.findFirst[
			(isIntersection && it instanceof UnionTypeExpression)
			|| (isUnion && it instanceof IntersectionTypeExpression)
		] as ComposedTypeRef;
		if(toBeMultiplied===null) {
			// not found, cannot apply distributivity
			return composedTypeRef;
		}

		// remove 'toBeMultiplied' from typeRefs
		sourceTypeRefs.remove(toBeMultiplied);

		// multiply
		val resultTypeRefs = newArrayList;
		for(TypeRef currTypeRef : toBeMultiplied.typeRefs) {
			val multipliedTypeRef = if(isIntersection) {
				TypeUtils.createNonSimplifiedIntersectionType(#[currTypeRef] + TypeUtils.copyAll(sourceTypeRefs))
			} else { // isUnion
				TypeUtils.createNonSimplifiedUnionType(#[currTypeRef] + TypeUtils.copyAll(sourceTypeRefs))
			};
			resultTypeRefs += multipliedTypeRef;
		}

		return if(isIntersection) {
			TypeUtils.createNonSimplifiedUnionType(resultTypeRefs)
		} else { // isUnion
			TypeUtils.createNonSimplifiedIntersectionType(resultTypeRefs)
		};
	}


	/** Delegate back to the main subtype judgment. */
	private def boolean isSubtype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		ts.subtype(G, left, right).value ?: false
	}
}
