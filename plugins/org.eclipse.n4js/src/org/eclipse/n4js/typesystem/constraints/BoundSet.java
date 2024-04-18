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
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

import com.google.common.base.Stopwatch;
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
	private final TypeBoundCombiner tbc;

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
		this.tbc = new TypeBoundCombiner(G, ts);
	}

	private BoundSet(BoundSet other) {
		this(other.ic, other.G, other.ts);
		this.boundsPerInfVar.putAll(other.boundsPerInfVar);
		this.instantiations.putAll(other.instantiations);
		this.incorporatedBounds.addAll(other.incorporatedBounds);
		this.haveBoundFALSE = other.haveBoundFALSE;
		this.haveRawTypeRef = other.haveRawTypeRef;
	}

	/**
	 * Creates a copy of this bound set's current state.
	 */
	public BoundSet copy() {
		return new BoundSet(this);
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
	void removeBound(TypeBound bound) {
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
		incorporateInstantiations(new ArrayList<>(boundsPerInfVar.values()));

		for (InferenceVariable infVar : new ArrayList<>(boundsPerInfVar.keySet())) {
			ArrayList<TypeBound> bounds = new ArrayList<>(boundsPerInfVar.get(infVar));
			incorporateBounds(bounds, false);
		}
		incorporateFixpoint();
	}

	public void incorporateFixpoint() {
		boolean updated;
		do {
			// PERFORMANCE: incorporate instantiations at once first reduces computation
			incorporateInstantiations(new ArrayList<>(boundsPerInfVar.values()));
			updated = incorporateBounds(new ArrayList<>(boundsPerInfVar.values()), true);
		} while (updated);
	}

	public int counter = 0;
	public Stopwatch sw = Stopwatch.createUnstarted();

	/**
	 * All instantiations are incorporated at once instead of one at a time. This reduces calls to
	 * {@link Reducer#reduce(Iterable)} drastically.
	 */
	private void incorporateInstantiations(ArrayList<TypeBound> bounds) {
		final int len = bounds.size();
		if (len < 2) {
			return;
		}

		for (int j = 0; j < len; ++j) {
			final TypeBound boundJ = bounds.get(j);

			List<InferenceVariable> mutualInfVars = new ArrayList<>();
			for (InferenceVariable refInfVar : boundJ.referencedInfVars) {
				if (instantiations.containsKey(refInfVar)) {
					mutualInfVars.add(refInfVar);
				}
			}

			if (!mutualInfVars.isEmpty()) {
				TypeRef rightJ = boundJ.right;
				for (InferenceVariable mutVar : mutualInfVars) {
					final TypeRef boundIRight = instantiations.get(mutVar);
					// (5) `α = S` (where S is proper) and `β Φ T` implies `β Φ T[α:=U]`
					rightJ = substituteInferenceVariable(rightJ, mutVar, boundIRight);
				}

				removeBound(boundJ); // performance tweak: avoid unnecessary growth of bounds
				ic.reducer.reduce(new TypeConstraint(typeRef(boundJ.left), rightJ, boundJ.variance));
			}
		}
	}

	private boolean incorporateBounds(ArrayList<TypeBound> bounds, boolean addToIncorporated) {
		boolean updated = false;
		final int len = bounds.size();
		if (len < 2) {
			return updated;
		}

		int knownUntilIdx = 0;
		while (knownUntilIdx < bounds.size() && incorporatedBounds.contains(bounds.get(knownUntilIdx))) {
			knownUntilIdx++;
		}

		for (int i = 0; i < len; ++i) {
			final TypeBound boundI = bounds.get(i);
			final boolean isIncorporatedI = incorporatedBounds.contains(boundI);
			for (int j = Math.max(knownUntilIdx, i + 1); j < len; ++j) {
				counter++;
				final TypeBound boundJ = bounds.get(j);
				final boolean isIncorporatedJ = incorporatedBounds.contains(boundJ);
				final boolean bothAlreadyIncorporated = (isIncorporatedI && isIncorporatedJ);
				if (!bothAlreadyIncorporated) {
					if (DEBUG) {
						log("--- incorporating:  " + boundI + "  |  " + boundJ);
					}
					sw.start();
					final TypeConstraint newConstraint = combine(boundI, boundJ);
					sw.stop();
					if (newConstraint != null) {
						// this is where incorporation triggers reduction (of the new constraint)
						// reduction may in turn trigger incorporation (provided it adds bounds)
						updated |= ic.reducer.reduce(newConstraint);
					}
					if (ic.isDoomed()) {
						return false;
					}
				}
			}
			if (addToIncorporated && !isIncorporatedI) {
				incorporatedBounds.add(boundI);
			}
		}

		return updated;
	}

	private TypeConstraint combine(TypeBound boundI, TypeBound boundJ) {
		return tbc.combine(boundI, boundJ, this);
	}

	/**
	 * Side effect free, i.e. no bounds will be removed from this {@link #BoundSet} (as an optimization) when calling
	 * this method.
	 */
	List<TypeConstraint> combineAll(TypeBound newBound) {
		List<TypeConstraint> result = new ArrayList<>();
		Set<TypeBound> bounds = ic.currentBounds.getBounds(newBound.left);
		for (TypeBound tBound : bounds) {
			TypeConstraint newConstraint = tbc.combine(newBound, tBound, null);
			if (newConstraint != null) {
				result.add(newConstraint);
			}
		}
		return result;
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

	private void log(final String message) {
		ic.log(message);
	}
}
