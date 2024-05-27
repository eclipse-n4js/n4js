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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.setThisBinding;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Contains helper methods used by the rules of the 'expectedTypeIn' judgment. Main reason for factoring out this code
 * is the same logic is used by several rules.
 */
@Singleton
class ExpectedTypeComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;

	/**
	 * Returns the expected type of an expression which be used to compute the return value of its containing function.
	 * In the standard case, the expression should be an expression in a return statement and the expected type will be
	 * the return type of its containing function. However, in case of a single-expression arrow function, the given
	 * expression need not be the child of a return statement.
	 * <p>
	 * This method will not check that the given expression is actually an expression that will, at runtime, provide the
	 * return value of a function. This has to be ensured by the client code.
	 *
	 * @return the expected type or <code>null</code> if there is no type expectation or some error occurred (e.g.
	 *         broken AST).
	 */
	TypeRef getExpectedTypeOfReturnValueExpression(RuleEnvironment G, Expression returnValueExpr) {
		FunctionOrFieldAccessor fofa = EcoreUtil2.getContainerOfType(
				returnValueExpr == null ? null : returnValueExpr.eContainer(), FunctionOrFieldAccessor.class);
		RuleEnvironment G2 = wrap(G);
		TypeRef myThisTypeRef = tsh.getThisTypeAtLocation(G, returnValueExpr);
		setThisBinding(G2, myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		return getExpectedTypeOfFunctionOrFieldAccessor(G2, fofa); // null means: no type expectation
	}

	TypeRef getExpectedTypeOfFunctionOrFieldAccessor(RuleEnvironment G, FunctionOrFieldAccessor fofa) {
		RuleEnvironment G2 = (G == null) ? RuleEnvironmentExtensions.newRuleEnvironment(fofa) : G;

		if (fofa instanceof FunctionDefinition) {
			FunctionDefinition fdef = (FunctionDefinition) fofa;
			if (fofa.isAsync() && !fdef.isGenerator()) {
				return getExpectedTypeOfReturnValueExpressionForAsyncFunction(G2, fdef);

			} else if (fdef.isGenerator()) {
				return getExpectedTypeOfReturnValueExpressionForGeneratorFunction(G2, fdef);

			} else {
				// this is the normal case
				TypeRef fType = ts.type(G2, fdef);
				if (fType instanceof FunctionTypeExprOrRef) {
					return ts.substTypeVariables(G2, ((FunctionTypeExprOrRef) fType).getReturnTypeRef());
				}
			}

		} else {
			// funDef === null, so maybe we are in a getter:
			if (fofa instanceof GetterDeclaration) {
				TGetter definedGetter = ((GetterDeclaration) fofa).getDefinedGetter();
				if (definedGetter != null) {
					return definedGetter.getTypeRef();
				}
			}
		}
		return null; // null means: no type expectation
	}

	private TypeRef getExpectedTypeOfReturnValueExpressionForAsyncFunction(RuleEnvironment G,
			FunctionDefinition funDef) {
		// we have an async function:
		// in case it does not already have a return type of Promise, N4JSFunctionDefinitionTypesBuilder sets
		// funDef.definedType.returnTypeRef to Promise<R,?>, where R can be based on funDef.returnTypeRef
		Type tFun = funDef.getDefinedType();
		if (tFun instanceof TFunction) {
			TypeRef actualReturnTypeRef = getAndResolveOuterReturnType(G, (TFunction) tFun);
			if (TypeUtils.isPromise(actualReturnTypeRef, getPredefinedTypes(G).builtInTypeScope)) {
				TypeArgument firstTypeArg = actualReturnTypeRef.getDeclaredTypeArgs().get(0);
				if (firstTypeArg != null)
					return ts.upperBound(G, firstTypeArg); // take upper bound to get rid of Wildcard, etc.
			}
		}

		if (funDef.getDeclaredReturnTypeRef() != null) {
			return funDef.getDeclaredReturnTypeRef();
		}

		return null;
	}

	private TypeRef getExpectedTypeOfReturnValueExpressionForGeneratorFunction(RuleEnvironment G,
			FunctionDefinition funDef) {
		// we have a generator function:
		// in case it does not already have a return type of Generator, N4JSFunctionDefinitionTypesBuilder
		// sets funDef.definedType.returnTypeRef to Generator<TYield,TResult,TNext>, where TYield can be based on
		// funDef.returnTypeRef
		TFunction tFun = funDef.getDefinedFunction();
		if (tFun != null) {
			TypeRef actualReturnTypeRef = getAndResolveOuterReturnType(G, tFun);
			if (TypeUtils.isGeneratorOrAsyncGenerator(actualReturnTypeRef, getPredefinedTypes(G).builtInTypeScope)) {
				return tsh.getGeneratorTReturn(G, actualReturnTypeRef);
			}
		}

		if (funDef.getDeclaredReturnTypeRef() != null) {
			return funDef.getDeclaredReturnTypeRef();
		}

		return null;
	}

	/**
	 * Returns the expected type of the yield value. It is retrieved from the type TYield of the actual function return
	 * type (with regard to {@code Generator<TYield,TReturn,TNext>}). In case the yield expression is recursive
	 * (features a star), the expected type must conform to {@code [Async]Generator<? extends TYield,?,? super TNext>}.
	 */
	TypeRef getExpectedTypeOfYieldValueExpression(RuleEnvironment G, YieldExpression yieldExpr,
			TypeRef exprTypeRefRaw) {
		Expression expression = yieldExpr.getExpression();
		FunctionDefinition funDef = EcoreUtil2.getContainerOfType(expression == null ? null : expression.eContainer(),
				FunctionDefinition.class);
		RuleEnvironment G2 = wrap(G);
		TypeRef myThisTypeRef = tsh.getThisTypeAtLocation(G, expression);
		setThisBinding(G2, myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		if (funDef == null || !funDef.isGenerator()) {
			return null; // yield only occurs in generator functions
		}

		TFunction tFun = funDef.getDefinedFunction();
		if (tFun != null) {
			TypeRef actualReturnTypeRef = getAndResolveOuterReturnType(G, tFun);
			BuiltInTypeScope scope = getPredefinedTypes(G).builtInTypeScope;
			if (TypeUtils.isGeneratorOrAsyncGenerator(actualReturnTypeRef, scope)) {
				TypeRef yieldTypeRef = tsh.getGeneratorTYield(G, actualReturnTypeRef);
				TypeRef yieldTypeRefCopy = TypeUtils.copyWithProxies(yieldTypeRef);
				if (yieldExpr.isMany()) {
					TypeRef exprTypeRef = ts.upperBoundWithReopenAndResolveTypeVars(G, exprTypeRefRaw);
					if (TypeUtils.isGeneratorOrAsyncGenerator(exprTypeRef, scope)) {
						TypeRef nextTypeRef = tsh.getGeneratorTNext(G, actualReturnTypeRef);
						TypeRef nextTypeRefCopy = TypeUtils.copyWithProxies(nextTypeRef);
						Wildcard superNext = TypeUtils.createWildcardSuper(nextTypeRefCopy);
						Wildcard extendsYield = TypeUtils.createWildcardExtends(yieldTypeRefCopy);
						Wildcard tReturn = TypeUtils.createWildcard(); // the return type does not matter since its use
																		// is optional
						TypeRef result = TypeUtils.createGeneratorTypeRef(scope, false, extendsYield, tReturn,
								superNext);
						if (funDef.isAsync()) {
							// yield* in async generators supports both async and non-async generators as argument
							result = TypeUtils.createNonSimplifiedUnionType(
									TypeUtils.createGeneratorTypeRef(scope, true, extendsYield, tReturn, superNext),
									result);
						}
						return result;
					} else {
						TypeRef iterableTypeRef = iterableTypeRef(G, yieldTypeRefCopy);
						return iterableTypeRef;
					}
				} else {
					return yieldTypeRefCopy;
				}
			}
		}

		return null; // null means: no type expectation
	}

	private TypeRef getAndResolveOuterReturnType(RuleEnvironment G, TFunction tFun) {
		TypeRef actualReturnTypeRef = tFun.getReturnTypeRef();
		if (actualReturnTypeRef != null) {
			return ts.upperBoundWithReopenAndResolveTypeVars(G, actualReturnTypeRef);
		}
		return null;
	}
}
