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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * A bound set:
 * <ul>
 * <li>manages a growing set of bounds, classified by the their {@link TypeVariable} on left-hand side,
 * <li>supports {@link #incorporate() incorporation}, the process that analyzes pairs of existing bounds to derive a
 * third one (implied by the former) which becomes part of the bound set,
 * <li>once a bound has been added, it's never removed.
 * </ul>
 * <p>
 * A bound-set participates as collaborator supporting {@link InferenceContext inference contexts}.
 */
/* package */ final class BoundSet {

	private static final boolean DEBUG = InferenceContext.DEBUG;

	private final InferenceContext ic;

	private final RuleEnvironment G;
	private final N4JSTypeSystem ts;

	/** Bounds within this bound set, stored per inference variable. */
	private final SetMultimap<InferenceVariable, TypeBound> boundsPerInfVar = LinkedHashMultimap.create();
	/** Instantiations among the bounds of this set, i.e. bounds of the form `α = P` with P being a proper type. */
	private final Map<InferenceVariable, TypeRef> instantiations = new LinkedHashMap<>();

	// used to keep track of what bounds have already been incorporated
	private final Set<TypeBound> incorporatedBounds = new HashSet<>();

	/**
	 * Flag to escalate a contradiction (once set, it stays set). Checked frequently via {@link #hasBoundFALSE()} to
	 * avoid hopeless work.
	 */
	private boolean haveBoundFALSE;

	/**
	 * Flag to indicate at least one {@link TypeUtils#isRawTypeRef(TypeRef) raw type reference} has been added to the
	 * bound set. Used for optimization purposes.
	 */
	private boolean haveRawTypeRef;

	/**
	 * Creates an instance.
	 */
	public BoundSet(InferenceContext ic, RuleEnvironment G, N4JSTypeSystem ts) {
		this.ic = ic;
		this.G = G;
		this.ts = ts;
	}

	/**
	 * Once a contradiction has been detected, constraint solving can quit early: no solution exists anyway.
	 */
	public boolean hasBoundFALSE() {
		return haveBoundFALSE;
	}

	/**
	 * Returns the number of type bounds in this bound set.
	 */
	public int size() {
		return boundsPerInfVar.size();
	}

	/**
	 * Returns all bounds in this bound set.
	 */
	public TypeBound[] getAllBounds() {
		final Collection<TypeBound> allBounds = boundsPerInfVar.values();
		return allBounds.toArray(new TypeBound[allBounds.size()]);
	}

	/**
	 * Returns all type bounds from this bounds set having the given inference variable on their LHS.
	 */
	public Set<TypeBound> getBounds(InferenceVariable infVar) {
		return boundsPerInfVar.get(infVar); // note: returns empty set, not null if no bounds for infVar found
	}

	/**
	 * Does the argument appear on the left-hand side of one ore more bounds?
	 */
	public boolean isBounded(InferenceVariable infVar) {
		return boundsPerInfVar.containsKey(infVar);
	}

	/**
	 * Same as {@link #isBounded(InferenceVariable)}, but inverted. Provided for readability reasons.
	 */
	public boolean isUnbounded(InferenceVariable infVar) {
		return !boundsPerInfVar.containsKey(infVar);
	}

	/**
	 * Adds special type bounds TRUE or FALSE. A bound TRUE will be ignored and a bound FALSE will immediately render
	 * the containing constraint system unsolvable, short-circuiting all further reduction, incorporation or resolution
	 * work.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	public boolean addBound(boolean b) {
		if (DEBUG) {
			log(">>> ADD bound: " + Boolean.toString(b).toUpperCase());
		}
		if (b == false && !haveBoundFALSE) {
			haveBoundFALSE = true;
			return true;
		}
		return false; // when adding bound TRUE always return false here (new round of incorporation not required)
	}

	/**
	 * Adds a type bound to this set. Does not itself trigger incorporation; this must be done by caller if(!) this
	 * method returns true.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	public boolean addBound(TypeBound bound) {
		if (bound.isTrivial()) {
			// more than a performance tweak: adding a trivial bound (e.g., `α <: α`) would cause isBounded(α)==true
			return false;
		}
		final boolean wasAdded = internal_addBound(bound);
		if (DEBUG) {
			final String suffix = wasAdded ? "  (ADDED)" : "  (not added; already present)";
			log(">>> ADD bound: " + bound + suffix);
		}
		return wasAdded;
	}

	private boolean internal_addBound(TypeBound bound) {
		final boolean wasAdded = boundsPerInfVar.put(bound.left, bound);
		if (wasAdded) {
			if (TypeUtils.isRawTypeRef(bound.right)) {
				haveRawTypeRef = true;
			}
			if (bound.variance == INV) {
				if (TypeUtils.isProper(bound.right) && !TypeUtils.isRawTypeRef(bound.right)) {
					instantiations.put(bound.left, bound.right);
				}
			}
		}
		return wasAdded;
	}

	/**
	 * Removes the given type bound, identified by object identity.
	 * <p>
	 * For internal use only! Bounds cannot really be removed from a {@code BoundSet}; this is only provided for
	 * performance reasons to allow removal of type bounds that do no longer have any effect. Use with care.
	 */
	private void removeBound(TypeBound bound) {
		boundsPerInfVar.remove(bound.left, bound);
		incorporatedBounds.remove(bound);
	}

	/**
	 * Returns all instantiations of inference variables among the type bounds of the receiving bound set.
	 */
	public Map<InferenceVariable, TypeRef> getInstantiations() {
		return ImmutableMap.copyOf(instantiations);
	}

	/**
	 * Return all lower bounds of the given inference variable, i.e. all type references TR appearing as RHS of bounds
	 * of the form `infVar :> TR` or `infVar = TR`.
	 */
	public TypeRef[] collectLowerBounds(InferenceVariable infVar, boolean onlyProper, boolean resolveRawTypes) {
		return collectBounds(infVar, onlyProper, resolveRawTypes,
				b -> (b.variance == CONTRA || b.variance == INV) && !(b.variance == CONTRA && b.right.isBottomType()));
	}

	/**
	 * Return all upper bounds of the given inference variable, i.e. all type references TR appearing as RHS of bounds
	 * of the form `infVar <: TR` or `infVar = TR`.
	 */
	public TypeRef[] collectUpperBounds(InferenceVariable infVar, boolean onlyProper, boolean resolveRawTypes) {
		return collectBounds(infVar, onlyProper, resolveRawTypes,
				b -> (b.variance == CO || b.variance == INV) && !(b.variance == CO && b.right.isTopType()));
	}

	private TypeRef[] collectBounds(InferenceVariable infVar, boolean onlyProper, boolean resolveRawTypes,
			Predicate<? super TypeBound> predicate) {
		final Set<TypeBound> bounds = resolveRawTypes ? resolveRawTypes(getBounds(infVar)) : getBounds(infVar);
		Stream<TypeRef> result = bounds.stream()
				.filter(predicate)
				.map(b -> b.right);
		if (onlyProper) {
			result = result.filter(t -> TypeUtils.isProper(t));
		}
		return result.toArray(TypeRef[]::new);
	}

	/**
	 * Handle raw types in given type bounds.
	 * <p>
	 * Even though raw type are not supported in N4JS, they can occur during type inference due to ClassifierTypeRef.
	 * The basic idea of this method is that a raw type is removed if at least one non-raw type for the same declared
	 * type exists: if we have A and A&lt;string> as upper bounds, we can remove A because it does not add any
	 * information.
	 * <p>
	 * TODO revise handling of raw types (inefficient implementation, does not handle all cases (e.g. nested raw types))
	 */
	private Set<TypeBound> resolveRawTypes(Set<TypeBound> typeBounds) {
		if (!haveRawTypeRef) // this is the 98% case, so we optimize for that
			return typeBounds;
		if (typeBounds.isEmpty())
			return typeBounds;
		final List<TypeBound> result = new ArrayList<>(typeBounds);
		final Set<Type> genTypesWithNonRawTypeRefs = new HashSet<>();
		final Set<TypeBound> boundsWithRawTypeRef = new HashSet<>();
		// collect
		for (TypeBound tb : result) {
			if (tb.right instanceof ParameterizedTypeRef) {
				final ParameterizedTypeRef ptr = (ParameterizedTypeRef) tb.right;
				final Type declType = ptr.getDeclaredType();
				if (declType != null && declType.isGeneric()) {
					final boolean isRaw = TypeUtils.isRawTypeRef(ptr);
					if (isRaw) {
						boundsWithRawTypeRef.add(tb);
					} else {
						genTypesWithNonRawTypeRefs.add(declType);
					}
				}
			}
		}
		// remove all raw type bounds for which at least 1 other non-raw typeRef exists
		for (TypeBound currTB : boundsWithRawTypeRef) {
			if (genTypesWithNonRawTypeRefs.contains(currTB.right.getDeclaredType())) {
				result.remove(currTB);
			}
		}
		// remaining raw types must be sanitized
		final int len = result.size();
		for (int i = 0; i < len; i++) {
			result.set(i, result.get(i).sanitizeRawTypeRef());
		}
		return new LinkedHashSet<>(result);
	}

	/**
	 * Tells if an instantiation was recorded via method {@link #addBound(TypeBound)} for the given inference variable.
	 */
	public boolean isInstantiated(InferenceVariable infVar) {
		return infVar != null && instantiations.containsKey(infVar);
	}

	/**
	 * Tells if the first argument is constrained by the second (irrespective of whether that second argument is
	 * instantiated).
	 */
	public boolean dependsOnResolutionOf(InferenceVariable infVar, InferenceVariable candidate) {
		for (TypeBound currBound : getBounds(infVar)) {
			if (TypeUtils.isOrContainsRefToTypeVar(currBound.right, candidate)) {
				return true;
			}
		}
		return false;
	}

	public void dumpInstantiations() {
		for (Entry<InferenceVariable, TypeRef> e : instantiations.entrySet()) {
			log(String.valueOf(e.getKey().getTypeAsString()) + " -> " + e.getValue().getTypeRefAsString());
		}
	}

	// ###############################################################################################################
	// INCORPORATION

	/**
	 * This method inspects bounds pairwise, recording that so as to avoid inspecting the same pair in follow-up rounds
	 * of incorporation. This "inspection" may result in a new constraint, computed by
	 * {@link #combine(TypeBound, TypeBound)}, which is then reduced.
	 */
	public void incorporate() {
		boolean updated;
		do {
			updated = false;
			final TypeBound[] bounds = getAllBounds();
			final int len = bounds.length;
			if (len < 2) {
				return;
			}
			for (int i = 0; i < len; ++i) {
				final TypeBound boundI = bounds[i];
				final boolean isIncorporatedI = incorporatedBounds.contains(boundI);
				for (int j = i + 1; j < len; ++j) {
					final TypeBound boundJ = bounds[j];
					final boolean isIncorporatedJ = incorporatedBounds.contains(boundJ);
					final boolean bothAlreadyIncorporated = (isIncorporatedI && isIncorporatedJ);
					if (!bothAlreadyIncorporated) {
						if (DEBUG) {
							log("--- incorporating:  " + boundI + "  |  " + boundJ);
						}
						final TypeConstraint newConstraint = combine(boundI, boundJ);
						if (newConstraint != null) {
							// this is where incorporation triggers reduction (of the new constraint)
							// reduction may in turn trigger incorporation (provided it adds bounds)
							updated |= ic.reducer.reduce(newConstraint);
						}
						if (ic.isDoomed()) {
							return;
						}
					}
				}
				if (!isIncorporatedI) {
					incorporatedBounds.add(boundI);
				}
			}
		} while (updated);
	}

	/**
	 * In terms of JLS8, this method embodies the implication rules listed in Sec. 18.3.1 (the other implication rules
	 * in JLS8 take as input capture conversion constraints).
	 */
	private TypeConstraint combine(TypeBound boundI, TypeBound boundJ) {
		switch (boundI.variance) {
		case INV:
			switch (boundJ.variance) {
			case INV:
				return combineInvInv(boundI, boundJ);
			case CO:
			case CONTRA:
				return combineInvVar(boundI, boundJ);
			}
			break;
		case CO:
			switch (boundJ.variance) {
			case INV:
				return combineInvVar(boundJ, boundI); // note: reversed arguments!
			case CONTRA:
				return combineContraCo(boundJ, boundI); // note: reversed arguments!
			case CO:
				return combineBothCoOrBothContra(boundI, boundJ);
			}
			break;
		case CONTRA:
			switch (boundJ.variance) {
			case INV:
				return combineInvVar(boundJ, boundI); // note: reversed arguments!
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
	private TypeConstraint combineInvInv(TypeBound boundS, TypeBound boundT) {
		if (boundS.left == boundT.left) {
			// `α = S` and `α = T` implies `S = T`
			return new TypeConstraint(boundS.right, boundT.right, INV);
		}
		// inference variables are different
		// -> try to substitute a proper RHS in the RHS of the other bound, to make it a proper type itself
		TypeConstraint newConstraint = combineInvInvWithProperType(boundS, boundT);
		if (newConstraint != null) {
			return newConstraint;
		}
		newConstraint = combineInvInvWithProperType(boundT, boundS);
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
	private TypeConstraint combineInvInvWithProperType(TypeBound boundWithProperRHS, TypeBound boundOther) {
		final InferenceVariable alpha = boundWithProperRHS.left;
		final TypeRef U = boundWithProperRHS.right;
		final TypeRef T = boundOther.right;
		if (TypeUtils.isProper(U) && TypeUtils.getReferencedTypeVars(T).contains(alpha)) {
			final InferenceVariable beta = boundOther.left;
			final TypeRef T_subst = substituteInferenceVariable(T, alpha, U); // returns T[α:=U]
			removeBound(boundOther); // performance tweak: avoid unnecessary growth of bounds
			return new TypeConstraint(typeRef(beta), T_subst, INV);
		}
		return null;
	}

	/**
	 * Case: first bound is an equality, while the second isn't: `α = S` and `β Φ T` with Φ either {@code <:} or
	 * {@code :>}.
	 */
	private TypeConstraint combineInvVar(TypeBound boundS, TypeBound boundT) {
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
		if (TypeUtils.isProper(S) && TypeUtils.getReferencedTypeVars(T).contains(alpha)) {
			// (5) `α = S` (where S is proper) and `β Φ T` implies `β Φ T[α:=U]`
			final TypeRef T_subst = substituteInferenceVariable(T, alpha, S); // returns T[α:=U]
			removeBound(boundT); // performance tweak: avoid unnecessary growth of bounds
			return new TypeConstraint(typeRef(beta), T_subst, Phi);
		}
		return null;
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
		final TypeRef result = (TypeRef) this.ts.substTypeVariables(Gtemp, typeRef).getValue();
		// note: infVar may still occur in result, if infVar->typeArg is not a valid type mapping!
		// assert !(TypeVarUtils.occursIn(infVar, result));
		return result;
	}

	private void log(final String message) {
		ic.log(message);
	}
}
