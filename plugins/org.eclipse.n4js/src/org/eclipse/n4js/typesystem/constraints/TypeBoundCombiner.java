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
package org.eclipse.n4js.typesystem.constraints;

import static org.eclipse.n4js.ts.types.util.Variance.CO;
import static org.eclipse.n4js.ts.types.util.Variance.CONTRA;
import static org.eclipse.n4js.ts.types.util.Variance.INV;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

/**  */
/* package */ final class TypeBoundCombiner {
	private final RuleEnvironment G;
	private final N4JSTypeSystem ts;

	private final HashMap<EObject, Set<TypeVariable>> cachedReferencedTypeVars = new HashMap<>();

	/**
	 * Creates an instance.
	 */
	TypeBoundCombiner(RuleEnvironment G, N4JSTypeSystem ts) {
		this.G = G;
		this.ts = ts;
	}

	/**
	 * @see #combine(TypeBound, TypeBound, BoundSet)
	 */
	TypeConstraint combine(TypeBound boundI, TypeBound boundJ) {
		return combine(boundI, boundJ, null);
	}

	/**
	 * In terms of JLS8, this method embodies the implication rules listed in Sec. 18.3.1 (the other implication rules
	 * in JLS8 take as input capture conversion constraints).
	 */
	TypeConstraint combine(TypeBound boundI, TypeBound boundJ, BoundSet bset) {
		switch (boundI.variance) {
		case INV:
			switch (boundJ.variance) {
			case INV:
				return combineInvInv(boundI, boundJ, bset);
			case CO:
			case CONTRA:
				return combineInvVar(boundI, boundJ, bset);
			}
			break;
		case CO:
			switch (boundJ.variance) {
			case INV:
				return combineInvVar(boundJ, boundI, bset); // note: reversed arguments!
			case CONTRA:
				return combineContraCo(boundJ, boundI); // note: reversed arguments!
			case CO:
				return combineBothCoOrBothContra(boundI, boundJ);
			}
			break;
		case CONTRA:
			switch (boundJ.variance) {
			case INV:
				return combineInvVar(boundJ, boundI, bset); // note: reversed arguments!
			case CO:
				return combineContraCo(boundI, boundJ);
			case CONTRA:
				return combineBothCoOrBothContra(boundI, boundJ);
			}
		}
		throw new IllegalStateException("unreachable");
	}

	/**
	 * Case: both bounds are equalities.
	 */
	private TypeConstraint combineInvInv(TypeBound boundS, TypeBound boundT, BoundSet bset) {
		if (boundS.left == boundT.left) {
			// `α = S` and `α = T` implies `S = T`
			return new TypeConstraint(boundS.right, boundT.right, INV);
		}
		// inference variables are different
		// -> try to substitute a proper RHS in the RHS of the other bound, to make it a proper type itself
		TypeConstraint newConstraint = combineInvInvWithProperType(boundS, boundT, bset);
		if (newConstraint != null) {
			return newConstraint;
		}
		newConstraint = combineInvInvWithProperType(boundT, boundS, bset);
		if (newConstraint != null) {
			return newConstraint;
		}
		return null;
	}

	/**
	 * Given two type bounds `α = U` and `β = T` with α &ne; β, will return a new constraint `β = T[α:=U]` if
	 * <ul>
	 * <li>U is proper, and
	 * <li>T mentions α.
	 * </ul>
	 * Otherwise, <code>null</code> is returned.
	 */
	private TypeConstraint combineInvInvWithProperType(TypeBound boundWithProperRHS, TypeBound boundOther,
			BoundSet bset) {
		final InferenceVariable alpha = boundWithProperRHS.left;
		final TypeRef U = boundWithProperRHS.right;
		final TypeRef T = boundOther.right;
		if (TypeUtils.isProper(U) && getCachedReferencedTypeVars(T).contains(alpha)) {
			final InferenceVariable beta = boundOther.left;
			final TypeRef T_subst = substituteInferenceVariable(T, alpha, U); // returns T[α:=U]
			if (bset != null) {
				bset.removeBound(boundOther); // performance tweak: avoid unnecessary growth of bounds
			}
			return new TypeConstraint(typeRef(beta), T_subst, INV);
		}
		return null;
	}

	/**
	 * Case: first bound is an equality, while the second isn't: `α = S` and `β Φ T` with Φ either {@code <:} or
	 * {@code :>}.
	 */
	private TypeConstraint combineInvVar(TypeBound boundS, TypeBound boundT, BoundSet bset) {
		final InferenceVariable alpha = boundS.left;
		final InferenceVariable beta = boundT.left;
		final TypeRef S = boundS.right;
		final TypeRef T = boundT.right;
		final Variance Phi = boundT.variance;
		if (alpha == beta) {
			// (1) `α = S` and `α Φ T` implies `S Φ T`
			return new TypeConstraint(S, T, Phi);
		}
		// both bounds have different inference variables, i.e. α != β
		if (alpha == T.getDeclaredType()) {
			// (2) `α = S` and `β Φ α` implies `β Φ S`
			return new TypeConstraint(typeRef(beta), S, Phi);
		}
		if (TypeUtils.isInferenceVariable(S)) {
			// first bound is of the form `α = γ` (with γ being another inference variable)
			final InferenceVariable gamma = (InferenceVariable) S.getDeclaredType();
			if (gamma == beta) {
				// (3) `α = β` and `β Φ T` implies `α Φ T`
				return new TypeConstraint(typeRef(alpha), T, Phi);
			}
			if (gamma == T.getDeclaredType()) {
				// (4) `α = γ` and `β Φ γ` implies `β Φ α`
				return new TypeConstraint(typeRef(beta), typeRef(alpha), Phi);
			}
		}
		// so, S is not an inference variable
		if (TypeUtils.isProper(S) && getCachedReferencedTypeVars(T).contains(alpha)) {
			// (5) `α = S` (where S is proper) and `β Φ T` implies `β Φ T[α:=U]`
			final TypeRef T_subst = substituteInferenceVariable(T, alpha, S); // returns T[α:=U]
			if (bset != null) {
				bset.removeBound(boundT); // performance tweak: avoid unnecessary growth of bounds
			}
			return new TypeConstraint(typeRef(beta), T_subst, Phi);
		}
		return null;
	}

	private Set<TypeVariable> getCachedReferencedTypeVars(EObject obj) {
		Set<TypeVariable> result = cachedReferencedTypeVars.get(obj);
		if (result == null) {
			result = TypeUtils.getReferencedTypeVars(obj);
			cachedReferencedTypeVars.put(obj, result);
		}
		return result;
	}

	/**
	 * Case: `α :> S` and `β <: T`.
	 */
	private TypeConstraint combineContraCo(TypeBound boundS, TypeBound boundT) {
		final InferenceVariable alpha = boundS.left;
		final InferenceVariable beta = boundT.left;
		final TypeRef S = boundS.right;
		final TypeRef T = boundT.right;
		if (alpha == beta) {
			// transitivity, using LHS as bridge:
			// α :> S and α <: T implies S <: T
			return new TypeConstraint(S, T, CO);
		}
		// so, α and β are different
		if (TypeUtils.isInferenceVariable(S)) {
			final InferenceVariable gamma = (InferenceVariable) S.getDeclaredType();
			if (gamma == T.getDeclaredType()) {
				// transitivity, using RHS as bridge:
				// α :> γ and β <: γ implies α :> β
				return new TypeConstraint(typeRef(alpha), typeRef(beta), CONTRA);
			}
		}
		return null;
	}

	/**
	 * Case: inequalities of same direction, i.e.
	 * <ul>
	 * <li>`α <: S` and `β <: T` or
	 * <li>`α :> S` and `β :> T`.
	 * </ul>
	 */
	private TypeConstraint combineBothCoOrBothContra(TypeBound boundS, TypeBound boundT) {
		final InferenceVariable alpha = boundS.left;
		final InferenceVariable beta = boundT.left;
		final TypeRef S = boundS.right;
		final TypeRef T = boundT.right;
		if (alpha == T.getDeclaredType()) {
			// α <: S and β <: α implies β <: S
			// α :> S and β :> α implies β :> S
			return new TypeConstraint(typeRef(beta), S, boundS.variance);
		}
		if (S.getDeclaredType() == beta) {
			// α <: β and β <: T implies α <: T
			// α :> β and β :> T implies α :> T
			return new TypeConstraint(typeRef(alpha), T, boundS.variance);
		}
		return null;
	}

	private static TypeRef typeRef(InferenceVariable infVar) {
		return TypeUtils.createTypeRef(infVar, new TypeArgument[0]);
	}

	/**
	 * Returns a copy of {@code typeRef} in which {@code infVar} is substituted by {@code typeArg} or {@code typeRef}
	 * itself if no change has occurred.
	 *
	 * @return typeRef[infVar:=typeArg]
	 */
	private TypeRef substituteInferenceVariable(TypeRef typeRef, InferenceVariable infVar, TypeArgument typeArg) {
		final RuleEnvironment Gtemp = RuleEnvironmentExtensions.wrap(this.G);
		RuleEnvironmentExtensions.addTypeMapping(Gtemp, infVar, typeArg);
		final TypeRef result = this.ts.substTypeVariables(Gtemp, typeRef);
		// note: infVar may still occur in result, if infVar->typeArg is not a valid type mapping!
		// assert !(TypeVarUtils.occursIn(infVar, result));
		return result;
	}
}
