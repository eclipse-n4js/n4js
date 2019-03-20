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

import static org.eclipse.n4js.ts.types.util.Variance.INV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.CharDiscreteDomain;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Range;

/**
 * An inference context maintains a set of inference variables together with a set of {@link TypeConstraint constraints}
 * on these inference variables, and provides functionality to compute a solution for this constraint system, i.e. an
 * instantiation for each inference variable to a {@link TypeUtils#isProper(TypeArgument) proper} type that satisfies
 * all given constraints.
 *
 * <h1>Terminology</h1>
 *
 * Some terms and definitions:
 * <ul>
 * <li><b>inference variables</b> are meta-variables for types, i.e. they represent the unknown types searched for while
 * solving a constraint system. Inference variables may appear in type constraints in order to define relations between
 * those inference variables.
 * <li><b>proper types</b> are types that are not inference variables and do not mention any inference variables (see
 * {@link TypeUtils#isProper(TypeArgument)} for details).
 * </ul>
 *
 * <h1>Usage</h1>
 *
 * Client code will create an instance of this class and then
 * <ol>
 * <li>register inference variables (either by registering existing type variables as inference variables via the
 * constructor OR by obtaining newly created inference variables using {@link #newInferenceVariable()}),
 * <li>add {@link TypeConstraint type constraints} using {@link #addConstraint(TypeConstraint)},
 * <li>invoke {@link #solve()} to compute and obtain a solution.
 * </ol>
 * The first two steps can happen in any order, but no more type variables or constraints can be added after calling
 * {@link #solve()}.
 *
 * <h1>Implementation</h1>
 *
 * The constraint solving algorithm used here is largely modeled after the one defined in The Java Language
 * Specification 8, Chapter 18 but was adjusted in a number of ways, esp. by removing functionality not required for
 * N4JS (e.g. primitive types, method overloading) and adding support for specific N4JS language features (e.g. union
 * types, structural typing).
 * <p>
 * All processing is structured into the following 3 phases:
 * <ol>
 * <li><b>Reduction</b>: arbitrary, high-level {@link TypeConstraint constraints} provided by client code are broken
 * down into simpler {@link TypeBound bounds} of a unified form.
 * <li><b>Incorporation</b>: throughout processing, a set of type bounds is maintained in field {@link #currentBounds};
 * when adding a new bound B to the set of current bounds B<sub>1</sub>...B<sub>n</sub>, we not merely add B but also
 * investigate each pair (B, B<sub>i</sub>) with 1&le;i&le;n and, in each case, add 0..* constraints or bounds to
 * reflect all indirect relationships we can deduce from B and B<sub>i</sub>. These additional constraints / bounds are
 * in turn reduced / incorporated.
 * <li><b>Resolution</b>: for each inference variable in field {@link #inferenceVariables} we try to find an
 * instantiation; this is done in the order defined by the dependencies between inference variables. Each instantiation
 * is immediately added as a new constraint which is then reduced as in step 1 (which will trigger incorporation to make
 * all indirect consequences of this instantiation for other, dependent inference variables explicit in the form of
 * additional bounds).
 * </ol>
 * These phases are not strictly consecutive steps but can happen iteratively. For example, during resolution another
 * round of reduction might be triggered.
 * <p>
 * Reduction logic is factored out to class {@link Reducer}, incorporation logic is mostly contained in class
 * {@link BoundSet}, whereas resolution logic can be found in this class.
 *
 * <h1>Debugging</h1>
 *
 * In order to get detailed debugging information, turn on logging by setting field {@link #DEBUG} to <code>true</code>.
 */
public final class InferenceContext {

	static final boolean DEBUG = false;

	private final N4JSTypeSystem ts;
	private final TypeSystemHelper tsh;
	private final OperationCanceledManager operationCanceledManager; // may be null
	private final CancelIndicator cancelIndicator; // may be null
	private final RuleEnvironment G;

	/**
	 * An order-preserving set of inference variables. A type variable is treated as an inference variable if it is
	 * contained in this set, and otherwise as an ordinary type.
	 */
	private final Set<InferenceVariable> inferenceVariables = new LinkedHashSet<>();

	/** List of constraints supplied by client. */
	private final List<TypeConstraint> constraints = new ArrayList<>();

	/** Collaborator reducing higher-level constraints into simpler bounds. */
	/* package */ final Reducer reducer;

	/** Collaborator that takes care of adding and incorporating bounds. */
	/* package */ final BoundSet currentBounds;

	/** Actions registered by client code that will be executed after this constraint system has been solved. */
	private final List<Consumer<Optional<Map<InferenceVariable, TypeRef>>>> onSolvedActions = new ArrayList<>();

	/** Tells if {@link #solve()} was invoked. */
	private boolean isSolved = false;

	/** The solution as returned by {@link #solve()}, or <code>null</code> if unsolvable. */
	private Map<InferenceVariable, TypeRef> solution = null;

	/**
	 * Creates a new, empty inference context for the given inference variables. The cancellation manager and indicator
	 * may be <code>null</code>.
	 *
	 * @param G
	 *            a rule environment used for subtype checking, etc. during constraint resolution. This rule environment
	 *            will not be changed by the inference context AND it is expected to <b>not</b> be changed by anyone
	 *            else during the life-time of the inference context.
	 * @param inferenceVariables
	 *            the meta variables to be inferred.
	 */
	public InferenceContext(N4JSTypeSystem ts, TypeSystemHelper tsh, OperationCanceledManager operationCanceledManager,
			CancelIndicator cancelIndicator, RuleEnvironment G, InferenceVariable... inferenceVariables) {
		Objects.requireNonNull(ts);
		Objects.requireNonNull(tsh);
		Objects.requireNonNull(G);
		this.ts = ts;
		this.tsh = tsh;
		this.operationCanceledManager = operationCanceledManager;
		this.cancelIndicator = cancelIndicator;
		this.G = G;
		addInferenceVariables(false, inferenceVariables);
		this.reducer = new Reducer(this, G, ts, tsh);
		this.currentBounds = new BoundSet(this, G, ts);
	}

	/**
	 * Register the given action to be executed after this constraint system has been solved. In the success case, the
	 * solution is passed to the given action; in the failure case the argument will be {@link Optional#absent()}. The
	 * InferenceContext will guarantee that each such action is executed only once.
	 */
	public void onSolved(Consumer<Optional<Map<InferenceVariable, TypeRef>>> action) {
		onSolvedActions.add(action);
	}

	/**
	 * Add bound <code>FALSE</code>, thus making the inference context {@link InferenceContext#isDoomed() doomed}.
	 */
	public boolean giveUp() {
		return currentBounds.addBound(false);
	}

	/**
	 * Once a contradiction has been detected, constraint solving can quit early: no solution exists anyway.
	 * <p>
	 * Note that this method is not guaranteed to always return true if the constraint system is unsolvable; it just
	 * reports about easy to detect special cases (i.e. after the type bound FALSE has been added to
	 * {@link #currentBounds}) to allow for performance tweaks.
	 * <p>
	 * Since this method is invoked at places that are also suitable for cancellation checking, this method also takes
	 * care to that, throwing an {@link OperationCanceledException} when appropriate.
	 */
	public boolean isDoomed() {
		if (operationCanceledManager != null && cancelIndicator != null) {
			operationCanceledManager.checkCanceled(cancelIndicator);
		}
		return currentBounds.hasBoundFALSE();
	}

	/**
	 * Returns the inference variables of the receiving context.
	 */
	public Set<InferenceVariable> getInferenceVariables() {
		return inferenceVariables;
	}

	@SuppressWarnings("hiding")
	private void addInferenceVariables(boolean internal, InferenceVariable... inferenceVariables) {
		if (inferenceVariables == null || inferenceVariables.length == 0)
			return;
		if (isSolved && !internal) {
			// note: only if !internal (i.e. adding internal inference variables while solution in progress is allowed)
			throw new IllegalStateException("may not add inference variables after #solve() has been invoked");
		}
		this.inferenceVariables.addAll(Arrays.asList(inferenceVariables));
	}

	/**
	 * Introduce a newly generated inference variable to the constraint system of the receiving inference context.
	 */
	public InferenceVariable newInferenceVariable() {
		return newInferenceVariable(false);
	}

	private InferenceVariable newInferenceVariable(boolean internal) {
		final InferenceVariable iv = TypesFactory.eINSTANCE.createInferenceVariable();
		final String name = internal ? "_" + unusedNameGeneratorInternal.next() : unusedNameGenerator.next();
		iv.setName(name);
		addInferenceVariables(internal, iv);
		return iv;
	}

	/**
	 * Same as {@link #newInferenceVariable()}, but creates <code>n</code> inference variables in one step.
	 */
	public InferenceVariable[] newInferenceVariables(int n) {
		return newInferenceVariables(n, false);
	}

	private InferenceVariable[] newInferenceVariables(int n, boolean internal) {
		final InferenceVariable[] result = new InferenceVariable[n];
		for (int i = 0; i < n; i++)
			result[i] = newInferenceVariable(internal);
		return result;
	}

	/**
	 * Introduces newly generated inference variables for each type parameter of the given function type (if generic)
	 * and returns a non-generic function type in which all type variables owned by the given function type are replaced
	 * by those inference variables; simply returns the given function type unchanged if it already is non-generic.
	 * Returns given function type unchanged in case of error (so returned function type may actually be generic, but
	 * only in error cases).
	 * <p>
	 * Example: given a function type such as
	 *
	 * <pre>
	 * {function&lt;T,S>(G&lt;T,string>):S}
	 * </pre>
	 *
	 * this method will add two new type variables α', β' and return
	 *
	 * <pre>
	 * {function(G&lt;α',string>):β'}
	 * </pre>
	 *
	 * Note that the returned function type is non-generic.
	 */
	public FunctionTypeExprOrRef newInferenceVariablesFor(FunctionTypeExprOrRef funTypeRef) {
		if (!funTypeRef.isGeneric())
			return funTypeRef;
		final List<TypeVariable> typeParams = funTypeRef.getTypeVars(); // NOTE: typeParam may contain null entries!
		final InferenceVariable[] newInfVars = newInferenceVariables(typeParams.size(), true);
		final List<? extends TypeRef> newInfVarsRefs = Stream.of(newInfVars).map(TypeUtils::createTypeRef)
				.collect(Collectors.toList());
		final RuleEnvironment G_params2infVars = RuleEnvironmentExtensions.newRuleEnvironment(G); // new, empty RE
		RuleEnvironmentExtensions.addTypeMappings(G_params2infVars, typeParams, newInfVarsRefs);
		final TypeArgument left_withInfVars = ts.substTypeVariables(G_params2infVars, funTypeRef);
		if (left_withInfVars instanceof FunctionTypeExprOrRef)
			return (FunctionTypeExprOrRef) left_withInfVars;
		// in case of substitution error: return original funTypeRef
		return funTypeRef;
	}

	private final UnusedNameGenerator unusedNameGenerator = new UnusedNameGenerator();
	private final UnusedNameGenerator unusedNameGeneratorInternal = new UnusedNameGenerator();

	private static final class UnusedNameGenerator {
		private Iterator<Character> unusedNames = null;
		private int unusedNamesOverflows = 0;

		public String next() {
			if (unusedNames == null) {
				unusedNames = ContiguousSet.create(Range.closed('\u03B1', '\u03B9'), CharDiscreteDomain.chars())
						.iterator();
			}

			final String next;
			if (unusedNamesOverflows == 0) {
				next = unusedNames.next().toString();
			} else {
				final StringBuffer sb = new StringBuffer();
				sb.append(unusedNames.next().toString());
				for (int i = 0; i < unusedNamesOverflows; i++)
					sb.append('\'');
				next = sb.toString();
			}

			if (!unusedNames.hasNext()) {
				unusedNames = null;
				unusedNamesOverflows++;
			}
			return next;
		}
	}

	/**
	 * Add a type constraint to this inference context. When done adding constraints, call {@link #solve()}.
	 */
	public void addConstraint(TypeArgument left, TypeArgument right, Variance variance) {
		addConstraint(new TypeConstraint(left, right, variance));
	}

	/**
	 * Add a type constraint to this inference context. When done adding constraints, call {@link #solve()}.
	 */
	public void addConstraint(TypeConstraint constraint) {
		if (isSolved) {
			throw new IllegalStateException("may not add constraints after #solve() has been invoked");
		}
		constraints.add(constraint);
	}

	/**
	 * Solves the constraints of the receiving inference context.
	 * <p>
	 * This method returns
	 * <ul>
	 * <li>null, if no solution was found
	 * <li>otherwise, a map from each inference variable returned from {@link #getInferenceVariables()} to its
	 * instantiation.
	 * </ul>
	 * At this time, no partial solutions are returned in case of unsolvable constraint systems.
	 */
	public Map<InferenceVariable, TypeRef> solve() {
		if (isSolved) {
			return solution;
		}
		isSolved = true;

		if (DEBUG) {
			log("====== ====== ====== ====== ====== ====== ====== ====== ====== ====== ====== ======");
			log("solving the following constraint set:");
			constraints.stream().forEachOrdered(c -> log(c.toString()));
			log("inference variables: "
					+ inferenceVariables.stream().map(iv -> str(iv)).collect(Collectors.joining(", ")));
		}

		// ---------------------------------------------------------------------------
		// REDUCTION
		// new constraints added during reduction or incorporation will be reduced immediately (see #addConstraint()
		// above) so no need for recursion, here

		if (DEBUG) {
			log("****** Reduction");
		}
		for (final TypeConstraint constraint : constraints) {
			reducer.reduce(constraint);
			if (isDoomed()) {
				break;
			}
		}
		// clearing the list of constraints is ok given their information has been transferred to the bound set,
		// to which bounds are added but never removed.
		constraints.clear();

		// ---------------------------------------------------------------------------
		// INCORPORATION

		if (DEBUG) {
			log("****** Incorporation");
		}
		if (!isDoomed()) {
			currentBounds.incorporate();
		}

		// ---------------------------------------------------------------------------
		// RESOLUTION

		if (DEBUG) {
			log("****** Resolution");
		}
		final boolean success = !isDoomed() ? resolve() : false;
		solution = success ? currentBounds.getInstantiations() : null;
		if (DEBUG) {
			if (!success) {
				log("NO SOLUTION FOUND");
			} else {
				log("SOLUTION:");
				currentBounds.dumpInstantiations();
			}
		}

		// perform onSolvedActions
		for (Consumer<Optional<Map<InferenceVariable, TypeRef>>> action : onSolvedActions) {
			action.accept(Optional.fromNullable(solution));
		}

		return solution;
	}

	// ###############################################################################################################
	// RESOLUTION

	/**
	 * Performs resolution of all inference variables of the receiving inference context. This might trigger further
	 * reduction and incorporation steps.
	 * <p>
	 * If a solution is found, <code>true</code> is returned and {@link #currentBounds} will contain instantiations for
	 * all inference variables. Otherwise, <code>false</code> is returned and {@link #currentBounds} will be in an
	 * undefined state.
	 */
	private boolean resolve() {
		Set<InferenceVariable> currVariableSet;
		while ((currVariableSet = getSmallestVariableSet(inferenceVariables)) != null) {
			for (InferenceVariable currVariable : currVariableSet) {
				if (DEBUG) {
					log("======");
					log("trying to resolve inference variable: " + str(currVariable));
					log("based on this bound set:");
					Arrays.stream(currentBounds.getAllBounds()).forEachOrdered(b -> log("    " + b.toString()));
				}
				final TypeRef instantiation = chooseInstantiation(currVariable);
				if (instantiation == null) {
					if (DEBUG) {
						log("NO INSTANTIATION found for inference variable: " + str(currVariable));
					}
					return false;
				} else {
					if (DEBUG) {
						log("choosing instantiation " + str(currVariable) + " := " + str(instantiation));
					}
					instantiate(currVariable, instantiation);
				}
			}
			currentBounds.incorporate();
			if (isDoomed()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add bound {@code variable = proper} and perform reduction (which might trigger incorporation).
	 */
	private void instantiate(InferenceVariable infVar, TypeRef proper) {
		assert !(currentBounds.isInstantiated(infVar)) : "attempt to re-instantiate var " + str(infVar);
		assert TypeUtils.isProper(proper);
		// add bound `infVar = proper`
		reducer.reduce(TypeUtils.createTypeRef(infVar), proper, INV);
		// check if 'proper' was accepted by BoundSet 'currentBounds' as an instantiation/solution of 'infVar'
		if (!currentBounds.isInstantiated(infVar)) {
			// No! This should never happen except if 'proper' is not actually a proper type (and the above assertions
			// are turned off) OR if 'proper' is a raw type, which is invalid but may occur when dealing with corrupted
			// data (broken AST, etc.).
			// Since 'proper' was intended as an instantiation/solution when this method is called, not having it
			// accepted is a problem and we should give up on this constraint system:
			giveUp();
		}
	}

	/**
	 * Returns a valid solution for the given inference variable <b>without</b> taking into account any dependencies it
	 * might have on other inference variables, i.e. this method will consider only {@link TypeBound}s where the LHS is
	 * the given inference variable and the RHS is a proper type.
	 * <p>
	 * Background: this method is safe to use even for an inference variable α with a dependency on another inference
	 * variable β iff β has already been instantiated and its instantiation has been properly reduced and incorporated,
	 * because during reduction/incorporation a type bound α &lt;: P or α >: P or α = P (with P being a proper type)
	 * will have been created that reflects the impact of the instantiation of β on α (without mentioning β).
	 */
	private TypeRef chooseInstantiation(InferenceVariable infVar) {
		final TypeRef[] lowerBounds = currentBounds.collectLowerBounds(infVar, true, true);
		final boolean lowerAreUninteresting = lowerBounds.length > 0 && !containsInterestingLowerBound(lowerBounds);
		final TypeRef[] upperBoundsPreview = lowerAreUninteresting
				? currentBounds.collectUpperBounds(infVar, true, true)
				: null;
		final boolean preferUpperOverLower = lowerAreUninteresting && upperBoundsPreview != null
				&& upperBoundsPreview.length > 0;
		if (lowerBounds.length > 0 && !preferUpperOverLower) {
			// TODO IDE-1653 reconsider:
			// take upper bound of all lower bounds
			// (if we have a type bound `α :> ? extends A` this will give us A as a lower bound for α)
			for (int i = 0; i < lowerBounds.length; i++) {
				lowerBounds[i] = ts.upperBound(G, lowerBounds[i]);
			}
			final TypeRef result = tsh.createUnionType(G, lowerBounds);
			assert TypeUtils.isProper(result) : "not a proper LUB: " + str(result);
			return result;
		} else {
			final TypeRef[] upperBounds = currentBounds.collectUpperBounds(infVar, true, true);
			if (upperBounds.length > 0) {
				// TODO IDE-1653 should we here take lower bound of all upperBounds? (for consistency with above)
				final TypeRef result = tsh.createIntersectionType(G, upperBounds);
				assert TypeUtils.isProper(result) : "not a proper GLB: " + str(result);
				return result;
			} else {
				// neither lower nor upper bounds found -> typeVar is unconstrained
				return RuleEnvironmentExtensions.anyTypeRef(G);
			}
		}
	}

	private boolean containsInterestingLowerBound(TypeRef[] typeRefs) {
		final Type undefinedType = RuleEnvironmentExtensions.undefinedType(G);
		final Type nullType = RuleEnvironmentExtensions.nullType(G);
		for (int i = 0; i < typeRefs.length; i++) {
			final TypeRef curr = typeRefs[i];
			if (curr instanceof ParameterizedTypeRef) {
				// for ParameterizedTypeRefs, it depends on the declared type:
				final Type currDeclType = curr.getDeclaredType();
				if (currDeclType != null && currDeclType != undefinedType && currDeclType != nullType) {
					return true; // wow, that bound is interesting!
				}
			} else {
				// all non-ParameterizedTypeRefs are interesting, except UnknownTypeRef:
				if (!(curr instanceof UnknownTypeRef)) {
					return true; // wow, that bound looks intriguing!
				}
			}
		}
		return false; // no interesting bounds encountered
	}

	/**
	 * Given a set <code>infVars</code> of inference variables of the receiving inference context, this method returns
	 * the smallest, non-empty set S such that
	 * <ul>
	 * <li>all α &isin; S are uninstantiated inference variables of the receiving inference context,
	 * <li>one of the elements in S is also in <code>infVars</code>: &exist; α &isin; S: α &isin; <code>infVars</code>,
	 * <li>if an α &isin; S depends on the resolution of another variable β, then either β is instantiated or β &isin;
	 * S,</li>
	 * <li>there exists no non-empty proper subset of S with this property.</li>
	 * </ul>
	 * Returns <code>null</code> if no such set exists, e.g. because <code>infVars</code> was empty or did not contain
	 * any uninstantiated inference variables.
	 * <p>
	 * The returned set, if non-null, is guaranteed to be non-empty.
	 */
	private Set<InferenceVariable> getSmallestVariableSet(Set<InferenceVariable> infVars) {
		Set<InferenceVariable> result = null;
		Set<InferenceVariable> deferred = null;
		int min = Integer.MAX_VALUE;
		for (InferenceVariable currentVariable : infVars) {
			if (!currentBounds.isInstantiated(currentVariable)) {

				// Defer an unbounded currentVariable IFF all other iv that depend on currentVariable have
				// proper bounds AND only proper bounds.
				//
				// For example:
				// α :> β
				// α :> A
				// Because α depends on β, we would first instantiate β (to any) and then choose α=intersection{A,any}.
				// With the following code, we defer processing of β until α has been instantiated to A.
				if (currentBounds.isUnbounded(currentVariable)) {
					final boolean gotAtLeastOneDependantIV = infVars.stream()
							.anyMatch(iv -> currentBounds.dependsOnResolutionOf(iv, currentVariable));
					if (gotAtLeastOneDependantIV) {
						final boolean defer = infVars.stream()
								.filter(iv -> currentBounds.dependsOnResolutionOf(iv, currentVariable))
								.allMatch(iv -> {
									final List<TypeBound> bs = currentBounds.getBounds(iv).stream()
											.filter(b -> !(b.left == iv
													&& b.right.getDeclaredType() == currentVariable))
											.collect(Collectors.toList());
									return !bs.isEmpty()
											&& bs.stream().allMatch(b -> TypeUtils.isProper(b.right));
								});
						if (defer) {
							if (deferred == null) {
								deferred = new HashSet<>();
							}
							deferred.add(currentVariable);
							continue;
						}
					}
				}

				final Set<InferenceVariable> set = new LinkedHashSet<>();
				if (addDependencies(currentVariable, min, set)) {
					final int curr = set.size();
					if (curr == 1) {
						return set; // 'set' contains only currentVariable -> no need to remove deferred variables
					}
					if (curr < min) {
						result = set;
						min = curr;
					}
				}
			}
		}
		if ((result == null) || result.isEmpty()) {
			return null;
		}
		// deferred variables may have been added via #addDependencies() above, so remove them
		if (deferred != null) {
			// note: because deferred variables can only end up in 'result' via #addDependencies(), we can safely
			// remove all deferred variables and be sure that 'result' won't be empty afterwards
			result.removeAll(deferred);
		}
		return result;
	}

	/**
	 * <b>IMPORTANT:</b> Given inference variable <code>infVar</code> must be uninstantiated; this will *not* be checked
	 * (again) by this method!
	 * <p>
	 * For the given, uninstantiated inference variable <code>infVar</code>, this method will add to the given
	 * answer-set <code>addHere</code> the transitive closure of all uninstantiated inference variables of the receiving
	 * inference context on which <code>infVar</code> depends directly or indirectly.
	 * <p>
	 * In addition, a <code>limit</code> for the answer-set's size is supplied, which means the collection of
	 * dependencies will be interrupted immediately when <code>addHere</code>'s size reaches the given limit.
	 * <p>
	 * Returns <code>true</code> on success or <code>false</code> if collection of dependencies was interrupted due to
	 * reaching the <code>limit</code>. In the latter case, the answer-set will be in an invalid state (contains a
	 * random subset of the transitive closure of dependencies) and should be discarded.
	 */
	private boolean addDependencies(InferenceVariable infVar, int limit, Set<InferenceVariable> addHere) {
		if (addHere.size() >= limit) {
			// the candidate answer-set is already not better than best-yet answer-set (i.e. larger or same size)
			return false;
		}
		// add infVar
		if (!addHere.add(infVar)) {
			// candidate variable was already visited for this answer-set; we assume also its dependencies have already
			// been added -> so nothing else to be done here
			return true;
		}
		// for all (uninstantiated) variables on which the current one depends, recurse
		for (InferenceVariable candidate : inferenceVariables) {
			if (candidate != infVar) {
				if (!(currentBounds.isInstantiated(candidate))) {
					if (currentBounds.dependsOnResolutionOf(infVar, candidate)
							&& !addDependencies(candidate, limit, addHere)) {
						// one uninstantiated variable too many (that currentVariable depends on) causes the current
						// answer-set to be discarded
						return false;
					}
				}
			}
		}
		return true;
	}

	private static String str(TypeVariable v) {
		return v.getTypeAsString();
	}

	private static String str(TypeArgument targ) {
		return targ.getTypeRefAsString();
	}

	void log(final String message) {
		System.out.println("[" + Integer.toHexString(System.identityHashCode(this)) + "] " + message);
	}

	@Override
	public String toString() {
		return constraints.toString();
	}
}
