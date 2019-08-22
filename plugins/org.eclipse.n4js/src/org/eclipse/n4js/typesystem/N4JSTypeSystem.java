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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.postprocessing.TypeProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Main entry point into the N4JS type system. This class is only a facade. In most cases it directly forwards to one of
 * the {@code -Judgment} classes, that implement the major operations of the type system. The main exception is
 * {@link #type(RuleEnvironment, TypableElement)}, which hides the type judgment implemented in {@link TypeJudgment}
 * behind some special handling and caching.
 * <p>
 * In addition, this class contains some type-system-related convenience methods.
 */
@Singleton
public class N4JSTypeSystem {

	@Inject
	private TypeJudgment typeJudgment;
	@Inject
	private ExpectedTypeJudgment expectedTypeJudgment;
	@Inject
	private SubtypeJudgment subtypeJudgment;
	@Inject
	private BoundJudgment boundJudgment;
	@Inject
	private SubstTypeVariablesJudgment substTypeVariablesJudgment;

	@Inject
	private TypeProcessor typeProcessor;
	@Inject
	private TypeSystemHelper tsh;

	// ###############################################################################################################
	// LOW-LEVEL METHODS

	/**
	 * Returns the type of the given element. In case the type cannot be inferred, either a default (usually the
	 * {@link RuleEnvironmentExtensions#topType(RuleEnvironment) top type}) or the special type reference
	 * {@link UnknownTypeRef} is returned. Never returns <code>null</code>.
	 *
	 * <h2>IMPLEMENTATION NOTE:</h2>
	 *
	 * <em>All invocations of judgment 'type' - from outside the type system or from within the {@code -Judgment}
	 * classes - are now delegated to {@link TypeProcessor#getType(RuleEnvironment, TypableElement)}.</em>
	 * <p>
	 * {@link TypeProcessor} will simply read the type from the cache (if containing resource is fully processed) or
	 * initiate the post-processing of the entire resource. Actual use of the 'type' judgment, i.e. invocation of
	 * {@link TypeJudgment#apply(RuleEnvironment, TypableElement)} will only be done by the {@link TypeProcessor}s
	 * during post-processing via {@link #use_type_judgment_from_PostProcessors(RuleEnvironment, TypableElement)}. Once
	 * post-processing of an {@link N4JSResource} has finished, the {@link TypeProcessor} will make sure judgment 'type'
	 * will never be invoked again for that resource!
	 */
	public TypeRef type(RuleEnvironment G, TypableElement element) {
		return typeProcessor.getType(G, element);
	}

	/**
	 * <b>!!! This method must never be invoked, except from {@code AbstractProcessor#askXsemanticsForType()} !!!</b>
	 * <p>
	 * This method may be called to actually invoke judgment 'type' as implemented in {@link TypeJudgment}. It is used
	 * by {@link ASTProcessor} while traversing the entire AST (during post-processing) to obtain the type of nodes that
	 * have not yet been processed.
	 */
	public TypeRef use_type_judgment_from_PostProcessors(RuleEnvironment G, TypableElement element) {
		return typeJudgment.apply(G, element);
	}

	/**
	 * Returns the expected type for the given expression. May return <code>null</code>!
	 * <p>
	 * The return value is one of the following:
	 * <ul>
	 * <li>an {@link UnknownTypeRef}: computation of type expectation failed. The client should fail safe, i.e. it
	 * should not perform type checking for the given expression and otherwise ignore this failure.
	 * <li><code>null</code>: no type expectation at all. The client should not perform type checking for the given
	 * expression.
	 * <li>{@link RuleEnvironmentExtensions#topTypeRef(RuleEnvironment) top type}: no specific type expectation, but a
	 * valid value is required. The client should perform type checking for the given expression against the top type.
	 * <li>any other type reference: an actual, specific type expectation exists. The client is expected to check the
	 * given expression's actual type against the returned expected type.
	 * </ul>
	 * The difference between return values <code>null</code> and top type is that in the latter case 'void' is not
	 * accepted. Between return values {@code UnknownTypeRef} and <code>null</code> there is no real difference as far
	 * as the client is concerned; they are distinguished mainly for clarity and readability of the code in this class.
	 */
	public TypeRef expectedType(RuleEnvironment G, EObject container, Expression expression) {
		return expectedTypeJudgment.apply(G, container, expression);
	}

	/** Tells if {@code left} is a subtype of {@code right}. Never returns <code>null</code>. */
	public Result subtype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return subtypeJudgment.apply(G, left, right);
	}

	/** Tells if {@code left} is a subtype of {@code right}. */
	public boolean subtypeSucceeded(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return subtype(G, left, right).isSuccess();
	}

	/** Tells if {@code left} is a super type of {@code right}. Never returns <code>null</code>. */
	public Result supertype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		if (subtype(G, right, left).isSuccess()) {
			return Result.success();
		} else {
			return Result.failure(
					left.getTypeRefAsString() + " is not a super type of " + right.getTypeRefAsString(), false, null);
		}
	}

	/** Tells if {@code left} is equal to {@code right}. Never returns <code>null</code>. */
	public Result equaltype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		if (subtype(G, left, right).isSuccess() && subtype(G, right, left).isSuccess()) {
			return Result.success();
		} else {
			return Result.failure(
					left.getTypeRefAsString() + " is not equal to " + right.getTypeRefAsString(), false, null);
		}
	}

	/** Tells if {@code left} is equal to {@code right}. */
	public boolean equaltypeSucceeded(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return equaltype(G, left, right).isSuccess();
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
	 *            the variance. Usually this is the definition-site variance of the type corresponding parameter, but
	 *            can be some special value in certain contexts (e.g. in {@link TypeTypeRef}s). If absent, invariance
	 *            will be assumed.
	 * @param useFancyErrMsg
	 *            if <code>true</code>, will use methods {@link #equaltype(RuleEnvironment, TypeArgument, TypeArgument)
	 *            #equaltype()} and {@link #supertype(RuleEnvironment, TypeArgument, TypeArgument) #supertype()} instead
	 *            of plain subtype checks. Will only affect error messages.
	 * @return a result of the appropriate subtype check(s).
	 */
	public Result checkTypeArgumentCompatibility(RuleEnvironment G,
			TypeArgument leftArg, TypeArgument rightArg, Optional<Variance> varianceOpt, boolean useFancyErrMsg) {

		final Variance variance = varianceOpt.or(Variance.INV);

		// !!! keep the following aligned with below method #reduceTypeArgumentCompatibilityCheck() !!!

		// FIXME delete the following
		// final boolean leftArgIsOpen = leftArg instanceof Wildcard
		// || (leftArg instanceof ExistentialTypeRef && ((ExistentialTypeRef) leftArg).isReopened());
		// final boolean rightArgIsOpen = rightArg instanceof Wildcard
		// || (rightArg instanceof ExistentialTypeRef && ((ExistentialTypeRef) rightArg).isReopened());
		//
		// TypeRef leftArgUpper = leftArgIsOpen ? upperBound(G, leftArg) : (TypeRef) leftArg;
		// TypeRef leftArgLower = leftArgIsOpen ? lowerBound(G, leftArg) : (TypeRef) leftArg;
		// TypeRef rightArgUpper = rightArgIsOpen ? upperBound(G, rightArg) : (TypeRef) rightArg;
		// TypeRef rightArgLower = rightArgIsOpen ? lowerBound(G, rightArg) : (TypeRef) rightArg;

		TypeRef leftArgUpper = upperBoundHesitant(G, leftArg);
		TypeRef leftArgLower = lowerBoundHesitant(G, leftArg);
		TypeRef rightArgUpper = upperBoundHesitant(G, rightArg);
		TypeRef rightArgLower = lowerBoundHesitant(G, rightArg);

		// minor tweak to slightly improve error messages
		// (i.e. having "not equals to" instead of "not a subtype" in a random direction)
		if (useFancyErrMsg
				&& variance == Variance.INV
				&& leftArgUpper == leftArg && leftArgLower == leftArg
				&& rightArgUpper == rightArg && rightArgLower == rightArg) {
			return equaltype(G, leftArg, rightArg);
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

		// require leftArgUpper <: rightArgUpper, except we have contravariance
		if (variance != Variance.CONTRA) {
			Result result = subtype(G2, leftArgUpper, rightArgUpper);
			if (result.isFailure()) {
				return result;
			}
		}
		// require rightArgLower <: leftArgLower, except we have covariance
		if (variance != Variance.CO) {
			Result result = useFancyErrMsg
					? supertype(G2, leftArgLower, rightArgLower)
					: subtype(G2, rightArgLower, leftArgLower);
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
	public List<TypeConstraint> reduceTypeArgumentCompatibilityCheck(RuleEnvironment G,
			TypeArgument leftArg, TypeArgument rightArg, Optional<Variance> varianceOpt) {

		final Variance variance = varianceOpt.or(Variance.INV);

		// !!! keep the following aligned with above method #checkTypeArgumentCompatibility() !!!

		final TypeRef leftArgUpper = upperBoundHesitant(G, leftArg);
		final TypeRef leftArgLower = lowerBoundHesitant(G, leftArg);
		final TypeRef rightArgUpper = upperBoundHesitant(G, rightArg);
		final TypeRef rightArgLower = lowerBoundHesitant(G, rightArg);

		final List<TypeConstraint> result = new ArrayList<>(2);

		// require leftArgUpper <: rightArgUpper, except we have contravariance
		if (variance != Variance.CONTRA) {
			result.add(new TypeConstraint(leftArgUpper, rightArgUpper, Variance.CO));
		}
		// require rightArgLower <: leftArgLower, except we have covariance
		if (variance != Variance.CO) {
			result.add(new TypeConstraint(rightArgLower, leftArgLower, Variance.CO));
		}

		return result;
	}

	/** Returns the upper bound of the given type. Never returns <code>null</code>. */
	public TypeRef upperBound(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyUpperBound(G, typeArgument, false);
	}

	public TypeRef upperBoundHesitant(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyUpperBound(G, typeArgument, false);
	}

	public TypeRef upperBoundWithForce(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyUpperBound(G, typeArgument, true);
	}

	/** Returns the lower bound of the given type. Never returns <code>null</code>. */
	public TypeRef lowerBound(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyLowerBound(G, typeArgument, false);
	}

	public TypeRef lowerBoundHesitant(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyLowerBound(G, typeArgument, false);
	}

	public TypeRef lowerBoundWithForce(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyLowerBound(G, typeArgument, true);
	}

	/**
	 * Same as {@link #substTypeVariables(RuleEnvironment, TypeArgument)}, but makes explicit the rule that you get a
	 * {@link Wildcard} back if you put in a {@code Wildcard}.
	 */
	public Wildcard substTypeVariables(RuleEnvironment G, Wildcard wildcard) {
		return (Wildcard) substTypeVariables(G, (TypeArgument) wildcard);
	}

	/**
	 * Same as {@link #substTypeVariables(RuleEnvironment, TypeArgument)}, but makes explicit the rule that you get a
	 * {@link TypeRef} back if you put in a {@code TypeRef}.
	 */
	public TypeRef substTypeVariables(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariables(G, (TypeArgument) typeRef);
	}

	/**
	 * Substitutes type variables, i.e. replaces type variables with actual values taken from the
	 * type-variable-to-type-argument mappings in the given rule environment. Never returns <code>null</code>, EXCEPT if
	 * <code>null</code> is passed in as type argument.
	 * <p>
	 * The given {@code typeArgument} will never be changed, instead a copy will be created to reflect the substitution.
	 * If nothing was substituted (i.e. given type argument does not contain any type variable or only type variables
	 * without a binding in the given rule environment), then no copy will be created and {@code typeArgument} will be
	 * returned. Therefore, client code can do an identity check on the return value to find out if a substitution was
	 * performed.
	 * <p>
	 * TODO currently unnecessary copies are created in a few cases; clean-up code to make last statement valid!
	 * <p>
	 * Invariant: if you put in a TypeRef, you'll get a TypeRef back (only other case: put in a Wildcard and you'll get
	 * a Wildcard). But this is not true for subclasses of TypeRef, e.g. put in a FunctionTypeRef and you might get a
	 * FunctionTypeExpression back.
	 */
	public TypeArgument substTypeVariables(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, false, false);
	}

	public TypeRef substTypeVariablesWithCapture(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariablesWithCapture(G, (TypeArgument) typeRef);
	}

	public TypeArgument substTypeVariablesWithCapture(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, true, true);
	}

	public TypeRef substTypeVariablesWithoutCapture(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariablesWithoutCapture(G, (TypeArgument) typeRef);
	}

	public TypeArgument substTypeVariablesWithoutCapture(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, false, false);
	}

	public TypeRef substTypeVariables(RuleEnvironment G, TypeRef typeRef,
			boolean captureContainedWildcards, boolean captureUponSubstitution) {
		return (TypeRef) substTypeVariables(G, (TypeArgument) typeRef, captureContainedWildcards,
				captureUponSubstitution);
	}

	public TypeArgument substTypeVariables(RuleEnvironment G, TypeArgument typeArgument,
			boolean captureContainedWildcards, boolean captureUponSubstitution) {
		return substTypeVariablesJudgment.apply(G, typeArgument, captureContainedWildcards, captureUponSubstitution);
	}

	public TypeRef reopenExistentialTypes(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) reopenExistentialTypes(G, (TypeArgument) typeRef);
	}

	// FIXME integrate this into substTypeVariables judgment!
	public TypeArgument reopenExistentialTypes(RuleEnvironment G, TypeArgument typeArgument) {
		boolean isOrContainsClosedExistential = //
				(typeArgument instanceof ExistentialTypeRef && !((ExistentialTypeRef) typeArgument).isReopened())
						|| StreamSupport
								.stream(Spliterators.spliteratorUnknownSize(typeArgument.eAllContents(), 0), false)
								.anyMatch(eobj -> eobj instanceof ExistentialTypeRef
										&& !((ExistentialTypeRef) eobj).isReopened());
		if (isOrContainsClosedExistential) {
			TypeArgument cpy = TypeUtils.copy(typeArgument);
			if (cpy instanceof ExistentialTypeRef) {
				((ExistentialTypeRef) cpy).setReopened(true);
			}
			StreamSupport.stream(Spliterators.spliteratorUnknownSize(cpy.eAllContents(), 0), false)
					.filter(eobj -> eobj instanceof ExistentialTypeRef)
					.forEach(etr -> ((ExistentialTypeRef) etr).setReopened(true));
			return cpy;
		}
		return typeArgument;
	}

	// ###############################################################################################################
	// CONVENIENCE METHODS

	/**
	 * Convenience method. Infers type of <code>element</code> using a newly created, empty rule environment. The given
	 * element must be contained in a ResourceSet (required for creating a rule environment).
	 * <p>
	 * <b>IMPORTANT:</b>
	 * <ol>
	 * <li>this method does not handle generics and the binding of type parameters/arguments; if you need this behavior,
	 * use method {@link #tau(TypableElement,TypeRef)} instead!
	 * <li>this method must not be used in code that got a rule environment passed in, esp. in the type system!
	 * (creating a new, empty rule environment would, for example, compromise all recursion guards)
	 * </ol>
	 *
	 * @return type of given element or <code>null</code> in case of error.
	 */
	public TypeRef tau(TypableElement element) {
		final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(element);
		return type(G, element);
	}

	/**
	 * This is the generics-aware version of method {@link #tau(TypableElement)}; if you do not care about generics and
	 * the binding of type variables, use plain {@link #tau(TypableElement)} instead (it is less expensive).
	 * <p>
	 * Parameter <code>context</code> defines if and how type variables are bound. Consider the following example:
	 *
	 * <pre>
	 * class A&lt;T> {
	 * 	T f;
	 * }
	 *
	 * class B&lt;S> extends A&lt;S> {
	 * }
	 *
	 * class C extends B&lt;string> {
	 * }
	 * </pre>
	 *
	 * If you call this method passing in the TField instance representing field 'f' as <code>element</code>, the type
	 * inferred by this method will depend on parameter <code>context</code> as follows:
	 * <ul>
	 * <li>passing in A as the context will return type variable T as type of field 'f'.
	 * <li>passing in B as the context will return type variable B as type of field 'f' (note: here type variable T was
	 * bound but type variable S remained unbound).
	 * <li>passing in C as the context will return built-in type 'string' as type of field 'f' (now both type variables
	 * S and T have been bound).
	 * </ul>
	 * Furthermore, if the {@link TypeRef} passed in as context is a {@code ParameterizedTypeRef}, it may define
	 * additional type variable bindings through its type arguments (c.f. {@link ParameterizedTypeRef}).
	 */
	public TypeRef tau(TypableElement element, TypeRef context) {
		final RuleEnvironment G = createRuleEnvironmentForContext(context,
				element != null ? element.eResource() : null);
		return tau(element, G);
	}

	/**
	 * Convenience method. Infers the context of <code>element</code> using the {@link org.eclipse.xtext.EcoreUtil2}
	 * class.<br/>
	 * <br/>
	 * WARNING: does not handle all cases yet!<br/>
	 * TODO refactor uses of #tau() methods to have general approach of deriving rule env from AST node
	 */
	public TypeRef tau(TypableElement element, EObject astNodeForContext) {
		TypeDefiningElement tde = EcoreUtil2.getContainerOfType(astNodeForContext, TypeDefiningElement.class);
		TypeRef context = null;
		if (tde != null) {
			context = TypeUtils.createTypeRef(tde.getDefinedType());
		}
		return tau(element, context);
	}

	/**
	 * Same as {@link #type(RuleEnvironment, TypableElement)}, but performs a type variable substitution on the result
	 * based on the type variable bindings defined in the given rule environment, and returns a {@link TypeRef} (or
	 * <code>null</code> in case of an error) instead of a {@link Result}.
	 */
	public TypeRef tau(TypableElement element, RuleEnvironment G) {
		final TypeRef value = type(G, element);
		if (value != null) {
			final TypeRef substValue = substTypeVariables(G, value);
			return substValue;
		}
		return null;
	}

	/**
	 * Same as {@link #createRuleEnvironmentForContext(TypeRef, TypeRef, Resource)}, but uses the same context for type
	 * variable bindings and this binding.
	 */
	public RuleEnvironment createRuleEnvironmentForContext(TypeRef context, Resource resource) {
		return createRuleEnvironmentForContext(context, context, resource);
	}

	/**
	 * Creates a rule environment for the given context, i.e. it will be populated with type variable substitutions and
	 * a this-binding (if applicable). For details about the context argument see {@link #tau(TypableElement,TypeRef)}.
	 * <p>
	 * Returns an empty rule environment without any bindings if the two contexts are <code>null</code>. For consistency
	 * with {@link RuleEnvironmentExtensions#newRuleEnvironment(EObject)} this will throw an exception if resourceSet is
	 * <code>null</code>.
	 * <p>
	 * Client could should usually use the same context for type variable bindings and the this binding an should thus
	 * prefer method {@link #createRuleEnvironmentForContext(TypeRef, Resource)}.
	 *
	 * @param contextTypeVariableBindings
	 *            the context used to derive type variable bindings. See {@link #tau(TypableElement,TypeRef)} for an
	 *            example. May be <code>null</code>.
	 * @param contextThisBinding
	 *            the context used to derive a this binding. May be <code>null</code>.
	 * @param resource
	 *            containing resource. <b>Must not be <code>null</code></b>.
	 */
	public RuleEnvironment createRuleEnvironmentForContext(TypeRef contextTypeVariableBindings,
			TypeRef contextThisBinding, Resource resource) {
		final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(resource);
		if (contextTypeVariableBindings != null) {
			tsh.addSubstitutions(G, contextTypeVariableBindings);
		}
		if (contextThisBinding instanceof ParameterizedTypeRef) {
			if (contextThisBinding.getDeclaredType() instanceof TClassifier) {
				RuleEnvironmentExtensions.addThisType(G, contextThisBinding);
			}
		}
		return G;
	}

	/**
	 * Get rid of types such as wildcards, ExistentialTypeRef, bound this types, and type variables by replacing them
	 * with their upper bound.
	 */
	public TypeRef resolveType(RuleEnvironment G, TypeArgument typeArgument) {
		return tsh.resolveType(G, typeArgument);
	}

	/**
	 * Convenience method to create a simplified union type. Returns <code>null</code> if
	 * <ul>
	 * <li><code>typeRefs</code> is empty, or
	 * <li><code>typeRefs</code>'s size is greater 1 and <code>resource</code> is <code>null</code>.
	 * </ul>
	 *
	 * @see TypeUtils#createNonSimplifiedUnionType(Iterable)
	 */
	public TypeRef createSimplifiedUnion(List<TypeRef> typeRefs, Resource resource) {
		if (typeRefs.size() > 1 && resource != null) {
			return tsh.createUnionType(
					RuleEnvironmentExtensions.newRuleEnvironment(resource),
					typeRefs.toArray(new TypeRef[typeRefs.size()]));
		} else if (typeRefs.size() == 1) {
			return TypeUtils.copyIfContained(typeRefs.get(0));
		} else {
			return null;
		}
	}

	/**
	 * Convenience method to create a simplified intersection type. Returns <code>null</code> if
	 * <ul>
	 * <li><code>typeRefs</code> is empty, or
	 * <li><code>typeRefs</code>'s size is greater 1 and <code>resource</code> is <code>null</code>.
	 * </ul>
	 *
	 * @see TypeUtils#createNonSimplifiedIntersectionType(Iterable)
	 */
	public TypeRef createSimplifiedIntersection(List<TypeRef> typeRefs, Resource resource) {
		if (typeRefs.size() > 1 && resource != null) {
			return tsh.createIntersectionType(
					RuleEnvironmentExtensions.newRuleEnvironment(resource),
					typeRefs.toArray(new TypeRef[typeRefs.size()]));
		} else if (typeRefs.size() == 1) {
			return TypeUtils.copyIfContained(typeRefs.get(0));
		} else {
			return null;
		}
	}
}
