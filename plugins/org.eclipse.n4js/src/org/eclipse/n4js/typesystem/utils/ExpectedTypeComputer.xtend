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
package org.eclipse.n4js.typesystem.utils

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.xtext.EcoreUtil2

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Contains helper methods used by the rules of the 'expectedTypeIn' judgment.
 * Main reason for factoring out this code is the same logic is used by several rules.
 */
@Singleton
package class ExpectedTypeComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * Returns the expected type of an expression which be used to compute the return value of its containing function.
	 * In the standard case, the expression should be an expression in a return statement and the expected type will
	 * be the return type of its containing function. However, in case of a single-expression arrow function, the
	 * given expression need not be the child of a return statement.
	 * <p>
	 * This method will not check that the given expression is actually an expression that will, at runtime, provide
	 * the return value of a function. This has to be ensured by the client code.
	 *
	 * @return the expected type or <code>null</code> if there is no type expectation or some error occurred (e.g. broken AST).
	 */
	def TypeRef getExpectedTypeOfReturnValueExpression(RuleEnvironment G, Expression returnValueExpr) {
		val fofa = EcoreUtil2.getContainerOfType(returnValueExpr?.eContainer, FunctionOrFieldAccessor);
		val G2 = G.wrap;
		val myThisTypeRef = tsh.getThisTypeAtLocation(G, returnValueExpr);
		G2.addThisType(myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		return getExpectedTypeOfFunctionOrFieldAccessor(G2, fofa); // null means: no type expectation
	}

	def TypeRef getExpectedTypeOfFunctionOrFieldAccessor(RuleEnvironment G, FunctionOrFieldAccessor fofa) {
		val G2 = if (G === null) RuleEnvironmentExtensions.newRuleEnvironment(fofa) else G;

		if (fofa instanceof FunctionDefinition) {
			if (fofa.isAsync) {
		        return getExpectedTypeOfReturnValueExpressionForAsyncFunction(G2, fofa);

		    } else if (fofa.isGenerator()) {
		        return getExpectedTypeOfReturnValueExpressionForGeneratorFunction(G2, fofa);

		    } else {
		        // this is the normal case
				val fType = ts.type(G2, fofa);
				if (fType instanceof FunctionTypeExprOrRef) {
					return ts.substTypeVariables(G2, fType.returnTypeRef);
				}
		    }

		} else {
			// funDef === null, so maybe we are in a getter:
			if (fofa instanceof GetterDeclaration) {
				return fofa?.definedGetter?.declaredTypeRef;
			}
		}
		return null; // null means: no type expectation
	}

	private def TypeRef getExpectedTypeOfReturnValueExpressionForAsyncFunction(RuleEnvironment G, FunctionDefinition funDef) {
		// we have an async function:
		// in case it does not already have a return type of Promise, N4JSFunctionDefinitionTypesBuilder sets
		// funDef.definedType.returnTypeRef to Promise<R,?>, where R can be based on funDef.returnTypeRef
		val tFun = funDef.definedType;
		if (tFun instanceof TFunction) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			if (TypeUtils.isPromise(actualReturnTypeRef, G.getPredefinedTypes().builtInTypeScope)) {
				val firstTypeArg = actualReturnTypeRef.typeArgs.head;
				if (firstTypeArg !== null)
					return ts.upperBound(G, firstTypeArg).value; // take upper bound to get rid of Wildcard, etc.
			}
		}

		if (funDef.returnTypeRef !== null) {
			return funDef.returnTypeRef;
		}

		return null;
	}

	private def TypeRef getExpectedTypeOfReturnValueExpressionForGeneratorFunction(RuleEnvironment G, FunctionDefinition funDef) {
		// we have a generator function:
		// in case it does not already have a return type of Generator, N4JSFunctionDefinitionTypesBuilder
		// sets funDef.definedType.returnTypeRef to Generator<TYield,TResult,TNext>, where TYield can be based on funDef.returnTypeRef
		val tFun = funDef.definedFunction;
		if (tFun !== null) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			if (TypeUtils.isGenerator(actualReturnTypeRef, G.getPredefinedTypes().builtInTypeScope)) {
				return tsh.getGeneratorTReturn(G, actualReturnTypeRef);
			}
		}

		if (funDef.returnTypeRef !== null) {
			return funDef.returnTypeRef;
		}

		return null;
	}

	/**
	 * Returns the expected type of the yield value. It is retrieved from the type TYield of the actual function return type
	 * (with regard to {@code Generator<TYield,TReturn,TNext>}). In case the yield expression is recursive (features a star),
	 * the expected type must conform to {@code Generator<? extends TYield,any,? super TNext>}.
	 */
	def TypeRef getExpectedTypeOfYieldValueExpression(RuleEnvironment G, YieldExpression yieldExpr, TypeRef exprTypeRef) {
		val expression = yieldExpr.expression;
		val funDef = EcoreUtil2.getContainerOfType(expression?.eContainer, FunctionDefinition);
		val G2 = G.wrap;
		val myThisTypeRef = tsh.getThisTypeAtLocation(G, expression);
		G2.addThisType(myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		if (funDef === null || !funDef.isGenerator)
			return null; // yields only occur in generator functions

		val tFun = funDef.definedFunction;
		if (tFun !== null) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			val scope = G.getPredefinedTypes().builtInTypeScope;
			if (TypeUtils.isGenerator(actualReturnTypeRef, scope)) {
				val yieldTypeRef = tsh.getGeneratorTYield(G, actualReturnTypeRef);
				val yieldTypeRefCopy = TypeUtils.copyWithProxies(yieldTypeRef);
				if (yieldExpr.isMany()) {
					val isGenerator = TypeUtils.isGenerator(exprTypeRef, scope);
					if (isGenerator) {
						val nextTypeRef = tsh.getGeneratorTNext(G, actualReturnTypeRef);
						val nextTypeRefCopy = TypeUtils.copyWithProxies(nextTypeRef);
						val superNext = TypeUtils.createWildcardSuper(nextTypeRefCopy);
						val extendsYield = TypeUtils.createWildcardExtends(yieldTypeRefCopy);
						val tReturn = scope.getAnyTypeRef(); // the return type does not matter since its use is optional
						val recursiveGeneratorSuperType = TypeUtils.createGeneratorTypeRef(scope, extendsYield, tReturn, superNext);
						return recursiveGeneratorSuperType;
					} else {
						val iterableTypeRef = G.iterableTypeRef(yieldTypeRefCopy)
						return iterableTypeRef;
					}
				} else {
				    return yieldTypeRefCopy;
				}
			}
		}

		return null; // null means: no type expectation
	}

}
