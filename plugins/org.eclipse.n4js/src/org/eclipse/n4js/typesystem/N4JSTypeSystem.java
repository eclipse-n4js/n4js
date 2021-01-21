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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.postprocessing.TypeProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.EcoreUtil2;

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

	/**
	 * Tells if {@code left} is a subtype of {@code right}. Never returns <code>null</code>.
	 * <p>
	 * If a type argument representing a range of types (e.g. a wildcard) appears on one or both sides, this method will
	 * check whether the subtype relation holds for *all* types represented by the range. For example, given classes A,
	 * B, and C with C &lt;: B &lt;: A, the following applies:
	 *
	 * <pre>
	 * C !&lt;: ? extends B
	 * C &lt;: ? super B
	 * A !&lt;: ? super B
	 * ? extends C &lt;: ? super A
	 * ? extends A !&lt;: ? super C
	 * </pre>
	 *
	 * In particular, this method never performs bounds checks, i.e. it never checks whether one type argument lies
	 * within the range of types represented by another type argument; use method
	 * {@link TypeSystemHelper#checkTypeArgumentCompatibility(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)}
	 * for such bounds checks.
	 */
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
	 * Returns the upper bound of type arguments that represent a range of types; other types are returned unchanged.
	 * Never returns <code>null</code>.
	 * <p>
	 * In particular, this method returns the declared or implicit upper bound of
	 * <ul>
	 * <li>{@link Wildcard}s and
	 * <li>reopened(!) {@link ExistentialTypeRef}s.
	 * </ul>
	 * It does not return the upper bound of
	 * <ul>
	 * <li>closed (i.e. non-reopened) {@code ExistentialTypeRef}s,
	 * <li>{@link BoundThisTypeRef}s, and
	 * <li>{@link TypeVariable}s.
	 * </ul>
	 * Use methods {@link #upperBoundWithReopen(RuleEnvironment, TypeArgument) #upperBoundWithReopen()} or
	 * {@link #upperBoundWithReopenAndResolve(RuleEnvironment, TypeArgument, boolean) #upperBoundWithReopenAndResolve()}
	 * for this purpose.
	 */
	public TypeRef upperBound(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyUpperBound(G, typeArgument, false, false);
	}

	/**
	 * Same as {@link #upperBound(RuleEnvironment, TypeArgument)}, but also returns the upper bound for closed (i.e.
	 * non-reopened) {@code ExistentialTypeRef}s and {@link BoundThisTypeRef}s (but *not* for {@link TypeVariable}s).
	 * <p>
	 * Note that exceptions, i.e. closed {@link ExistentialTypeRef}s that must *not* be turned into their upper bound,
	 * can be defined via {@link RuleEnvironmentExtensions#addFixedCapture(RuleEnvironment, ExistentialTypeRef)}.
	 */
	public TypeRef upperBoundWithReopen(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyUpperBound(G, typeArgument, true, false);
	}

	/**
	 * Same as {@link #upperBoundWithReopen(RuleEnvironment, TypeArgument)}, but also returns the upper bound for
	 * {@link TypeVariable}s.
	 * <p>
	 * NOTE: unlike {@link Wildcard}s, {@link ExistentialTypeRef}s, and {@link BoundThisTypeRef}s, type variables are
	 * only affected on top-level, i.e. not if they are nested inside a {@link ParameterizedTypeRef} (as type argument)
	 * or inside a {@link FunctionTypeExprOrRef} (as parameter or return type).
	 */
	public TypeRef upperBoundWithReopenAndResolve(RuleEnvironment G, TypeArgument typeArgument,
			boolean resolveTypeVariables) {
		return boundJudgment.applyUpperBound(G, typeArgument, true, resolveTypeVariables);
	}

	/**
	 * Returns the lower bound of type arguments that represent a range of types; other types are returned unchanged.
	 * Never returns <code>null</code>.
	 * <p>
	 * In particular, this method returns the lower bound of
	 * <ul>
	 * <li>{@link Wildcard}s and
	 * <li>reopened(!) {@link ExistentialTypeRef}s.
	 * </ul>
	 * It does *not* return the lower bound of
	 * <ul>
	 * <li>closed (i.e. non-reopened) {@code ExistentialTypeRef}s,
	 * <li>{@link BoundThisTypeRef}s, and
	 * <li>{@link TypeVariable}s.
	 * </ul>
	 * Use methods {@link #lowerBoundWithReopen(RuleEnvironment, TypeArgument) #lowerBoundWithReopen()} or
	 * {@link #lowerBoundWithReopenAndResolve(RuleEnvironment, TypeArgument, boolean) #lowerBoundWithReopenAndResolve()}
	 * for this purpose.
	 */
	public TypeRef lowerBound(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyLowerBound(G, typeArgument, false, false);
	}

	/**
	 * Same as {@link #lowerBound(RuleEnvironment, TypeArgument)}, but also returns the lower bound for closed (i.e.
	 * non-reopened) {@code ExistentialTypeRef}s and {@link BoundThisTypeRef}s (but *not* for {@link TypeVariable}s).
	 * <p>
	 * Note that exceptions, i.e. closed {@link ExistentialTypeRef}s that must *not* be turned into their lower bound,
	 * can be defined via {@link RuleEnvironmentExtensions#addFixedCapture(RuleEnvironment, ExistentialTypeRef)}.
	 */
	public TypeRef lowerBoundWithReopen(RuleEnvironment G, TypeArgument typeArgument) {
		return boundJudgment.applyLowerBound(G, typeArgument, true, false);
	}

	/**
	 * Same as {@link #lowerBoundWithReopen(RuleEnvironment, TypeArgument)}, but also returns the lower bound for
	 * {@link TypeVariable}s.
	 * <p>
	 * NOTE: unlike {@link Wildcard}s, {@link ExistentialTypeRef}s, and {@link BoundThisTypeRef}s, type variables are
	 * only affected on top-level, i.e. not if they are nested inside a {@link ParameterizedTypeRef} (as type argument)
	 * or inside a {@link FunctionTypeExprOrRef} (as parameter or return type).
	 */
	public TypeRef lowerBoundWithReopenAndResolve(RuleEnvironment G, TypeArgument typeArgument,
			boolean resolveTypeVariables) {
		return boundJudgment.applyLowerBound(G, typeArgument, true, resolveTypeVariables);
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
	 * {@link TypeRef} back if you pass in a {@code TypeRef}.
	 */
	public TypeRef substTypeVariables(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariables(G, (TypeArgument) typeRef);
	}

	/**
	 * Substitutes type variables, i.e. replaces type variables with actual type arguments taken from the
	 * {@link RuleEnvironmentExtensions#addTypeMapping(RuleEnvironment, TypeVariable, TypeArgument)
	 * type-variable-to-type-argument mappings} in the given rule environment. Never returns <code>null</code>, EXCEPT
	 * if <code>null</code> is passed in as type argument.
	 * <p>
	 * The given {@code typeArgument} will never be changed, instead a copy will be created to reflect the substitution.
	 * If nothing was substituted (i.e. given type argument does not contain any type variable or only type variables
	 * without a binding in the given rule environment), then no copy will be created and {@code typeArgument} will be
	 * returned. Therefore, client code can do an identity check on the return value to find out if a substitution was
	 * performed.
	 * <p>
	 * INVARIANT: if you pass in a {@link TypeRef}, you'll get a {@code TypeRef} back (only other case: pass in a
	 * {@link Wildcard} and you'll get a {@code Wildcard} back). But this is not true for subclasses of {@code TypeRef},
	 * e.g. pass in a {@link FunctionTypeRef} and you might get a {@link FunctionTypeExpression} back.
	 * <p>
	 * NOTE: in some cases, this method converts nested {@code Wildcard}s to an {@link ExistentialTypeRef} with
	 * {@code reopened == true}, because due to substitution {@code Wildcard}s may end up in positions where only
	 * {@link TypeRef}s may appear; however, this is a pure implementation detail and does not have anything to do with
	 * capturing of wildcards and should not have any semantical effect.
	 */
	public TypeArgument substTypeVariables(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, false, false);
	}

	/**
	 * Same as {@link #substTypeVariablesWithFullCapture(RuleEnvironment, TypeArgument)}, but makes explicit the rule
	 * that you get a {@link TypeRef} back if you pass in a {@code TypeRef}.
	 */
	public TypeRef substTypeVariablesWithFullCapture(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariablesWithFullCapture(G, (TypeArgument) typeRef);
	}

	/**
	 * Same as {@link #substTypeVariables(RuleEnvironment, TypeArgument)}, but also captures wildcards, i.e. converts
	 * {@link Wildcard}s into closed, i.e. non-reopened, {@link ExistentialTypeRef}s.
	 * <p>
	 * Note that in some cases partial capturing is required instead; see
	 * {@link #substTypeVariablesWithPartialCapture(RuleEnvironment, TypeArgument)} for details.
	 */
	public TypeArgument substTypeVariablesWithFullCapture(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, true, true);
	}

	/**
	 * Same as {@link #substTypeVariablesWithPartialCapture(RuleEnvironment, TypeArgument)}, but makes explicit the rule
	 * that you get a {@link TypeRef} back if you pass in a {@code TypeRef}.
	 */
	public TypeRef substTypeVariablesWithPartialCapture(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariablesWithPartialCapture(G, (TypeArgument) typeRef);
	}

	/**
	 * Same as {@link #substTypeVariablesWithFullCapture(RuleEnvironment, TypeArgument)}, but only captures some
	 * wildcards:
	 * <ul>
	 * <li>wildcards already contained in the given {@code typeArgument} will *not* be captured,
	 * <li>wildcards being substituted into the given {@code typeArgument} as a replacement for a type variable
	 * contained in the given {@code typeArgument} will be captured.
	 * </ul>
	 * To illustrate the motivation for the above distinction, consider the following example:
	 *
	 * <pre>
	 * class G&lt;T> {
	 *     public field1: Array&lt;?>;
	 *     public field2: Array&lt;T>;
	 * }
	 * let g: G&lt;?> = ...
	 * // XPECT noerrors -->
	 * g.field1 = g.field1;
	 * // XPECT errors --> "Array<capture#1 of ?> is not a subtype of Array<capture#2 of ?>." at "g.field2"
	 * g.field2 = g.field2;
	 * </pre>
	 *
	 * On the right-hand side of the two assignments, both contained and substituted wildcards have to be captured,
	 * whereas on the left-hand side only substituted but not contained wildcards must be captured.
	 */
	public TypeArgument substTypeVariablesWithPartialCapture(RuleEnvironment G, TypeArgument typeArgument) {
		return substTypeVariablesJudgment.apply(G, typeArgument, false, true);
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
				RuleEnvironmentExtensions.setThisBinding(G, contextThisBinding);
			}
		}
		return G;
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
