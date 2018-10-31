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
import org.eclipse.n4js.postprocessing.TypeProcessor;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Main entry point into the N4JS type system. This class is only a facade. In most cases it simply forwards to
 * {@link InternalTypeSystemNEW}, i.e. the type system generated from {@code n4js.xsemantics}. The main exception is
 * {@link #type(RuleEnvironment, TypableElement)}, which hides the Xsemantics type judgment behind some special handling
 * and caching.
 * <p>
 * In addition, this class contains some type-system-related convenience methods.
 */
@Singleton
public class N4JSTypeSystem {

	@Inject
	private InternalTypeSystemNEW ts_internal;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private TypeProcessor typeProcessor;

	// ###############################################################################################################
	// LOW-LEVEL METHODS

	/** Returns the type of the given element wrapped in a {@link Result}. Never returns <code>null</code>. */
	public Result<TypeRef> type(RuleEnvironment G, TypableElement element) {
		return typeProcessor.getType(G, element);
	}

	/**
	 * Returns the expected type for the given expression wrapped in a {@link Result}. Never returns <code>null</code>
	 * <b>but even in the success case the returned result may have a {@link Result#getValue() success value} of
	 * <code>null</code>!</b>
	 * <p>
	 * When invoking {@link Result#getValue() #getValue()} on the returned result, you will get one of the following:
	 * <ul>
	 * <li>some type other than the top type if there is an actual type expectation for the given expression,
	 * <li>the top type (i.e. any) if the expression may evaluate to a value of any type but has to evaluate to a proper
	 * value (i.e. void is disallowed in this case!),
	 * <li><code>null</code> if there is not expectation at all, i.e. even void is allowed.
	 * </ul>
	 */
	public Result<TypeRef> expectedTypeIn(RuleEnvironment G, EObject container, Expression expression) {
		return ts_internal.expectedType(G, container, expression);
	}

	/** Tells if {@code left} is a subtype of {@code right}. Never returns <code>null</code>. */
	public Result<Boolean> subtype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts_internal.subtype(G, left, right);
	}

	/** Tells if {@code left} is a subtype of {@code right}. */
	public boolean subtypeSucceeded(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts_internal.subtype(G, left, right).isSuccess();
	}

	/** Tells if {@code left} is a super type of {@code right}. Never returns <code>null</code>. */
	public Result<Boolean> supertype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts_internal.supertype(G, left, right);
	}

	/** Tells if {@code left} is equal to {@code right}. Never returns <code>null</code>. */
	public Result<Boolean> equaltype(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts_internal.equaltype(G, left, right);
	}

	/** Tells if {@code left} is equal to {@code right}. */
	public boolean equaltypeSucceeded(RuleEnvironment G, TypeArgument left, TypeArgument right) {
		return ts_internal.equaltype(G, left, right).isSuccess();
	}

	/** Returns the upper bound of the given type wrapped in a {@link Result}. Never returns <code>null</code>. */
	public Result<TypeRef> upperBound(RuleEnvironment G, TypeArgument typeArgument) {
		return Result.success(ts_internal.upperBound(G, typeArgument));
	}

	/** Returns the lower bound of the given type wrapped in a {@link Result}. Never returns <code>null</code>. */
	public Result<TypeRef> lowerBound(RuleEnvironment G, TypeArgument typeArgument) {
		return Result.success(ts_internal.lowerBound(G, typeArgument));
	}

	/**
	 * Same as {@link #substTypeVariables(RuleEnvironment, TypeArgument)}, but makes explicit the rule that you get a
	 * {@link TypeRef} back if you put in a {@code TypeRef}.
	 */
	public TypeRef substTypeVariablesInTypeRef(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) substTypeVariables(G, typeRef).getValue();
	}

	/**
	 * Substitutes type variables, i.e. replaces type variables with actual values taken from the rule environment.
	 * Never returns <code>null</code>.
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
	 * FunctionTypeExpression back).
	 */
	public Result<TypeArgument> substTypeVariables(RuleEnvironment G, TypeArgument typeArgument) {
		return ts_internal.substTypeVariables(G, typeArgument);
	}

	/** Returns the this type at the given location wrapped in a {@link Result}. Never returns <code>null</code>. */
	public Result<TypeRef> thisTypeRef(RuleEnvironment G, EObject location) {
		return ts_internal.thisTypeRef(G, location);
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
		return type(G, element).getValue();
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
		final Result<TypeRef> result = type(G, element);
		final TypeRef value = result.getValue();
		if (value != null) {
			final TypeRef substValue = substTypeVariablesInTypeRef(G, value);
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
