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
package org.eclipse.n4js.utils

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.typesystem.utils.RuleEnvironment

import static org.eclipse.n4js.AnnotationDefinition.*

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Core logic for handling annotation <code>@Promisifiable</code> and operator <code>@Promisify</code>, i.e.
 * {@link PromisifyExpression}s.
 */
class PromisifyHelper {

	@Inject private N4JSTypeSystem ts;
	@Inject private TypeSystemHelper tsh;

	public static enum CheckResult {
		OK,
		MISSING_CALLBACK,
		BAD_CALLBACK__MORE_THAN_ONE_ERROR,
		BAD_CALLBACK__ERROR_NOT_FIRST_ARG
	}


	/**
	 * Checks if the given function or method may be annotated with <code>@Promisifiable</code>.
	 */
	def public CheckResult checkPromisifiablePreconditions(FunctionDefinition funDef) {
		val lastFpar = (funDef.definedType as TFunction)?.fpars?.last;
		val lastFparTypeRef = lastFpar?.typeRef;
		if(!(lastFparTypeRef instanceof FunctionTypeExprOrRef)) {
			// error: last fpar of promisifiable function/method must be a function (i.e. the callback)
			return CheckResult.MISSING_CALLBACK;
		}
		// ok, we have a callback; now check its signature ...
		val G = funDef.newRuleEnvironment;
		val callbackTypeRef = lastFparTypeRef as FunctionTypeExprOrRef;
		val callbackFpars = callbackTypeRef.fpars;
		val errorFpars = callbackFpars.filter[isErrorOrSubtype(G,it.typeRef)].toList;
		if(errorFpars.size>1) {
			// error: more than one fpar of type Error or subtype of Error
			return CheckResult.BAD_CALLBACK__MORE_THAN_ONE_ERROR;
		}
		val errorFpar = errorFpars.head;
		if(errorFpar!==null && errorFpar!==callbackFpars.get(0)) {
			// error: fpar of type Error is not the first fpar
			return CheckResult.BAD_CALLBACK__ERROR_NOT_FIRST_ARG;
		}
		return CheckResult.OK;
	}


	/**
	 * Checks if the given await expression is a case of "auto-promisify", i.e. if it is a short-hand form for
	 * <pre>await @Promisify foo()</pre>.
	 */
	def public boolean isAutoPromisify(AwaitExpression awaitExpr) {
		return awaitExpr!==null
			&& !(awaitExpr.expression instanceof PromisifyExpression) // must not be explicit promisify!
			&& isPromisifiableExpression(awaitExpr.expression);
	}


	/**
	 * Tells if the given expression is promisifiable, i.e. if it is an expression that may appear after a
	 * <code>@Promisify</code> operator and thus is a valid expression within a {@link PromisifyExpression}.
	 * However, this method will *not* check if the expression is actually contained in a {@code PromisifyExpression}.
	 */
	def public boolean isPromisifiableExpression(Expression expr) {
		if(expr instanceof ParameterizedCallExpression) {
			val G = expr.newRuleEnvironment;
			val targetTypeRef = ts.type(G, expr.target).value;
			if(targetTypeRef instanceof FunctionTypeExprOrRef) {
				val fun = targetTypeRef.functionType;
				return fun!==null && PROMISIFIABLE.hasAnnotation(fun);
			}
		}
		return false;
	}


	/**
	 * If the given expression is a promisifiable expression (see {@link #isPromisifiableExpression(Expression)}, this
	 * method will return the return type of the invoked function or method after promisification.
	 */
	def public TypeRef extractPromisifiedReturnType(Expression expr) {
		if(isPromisifiableExpression(expr)) {
			val G = expr.newRuleEnvironment;
			// casts in next line are OK, because of isPromisifiableExpression() returned true
			val targetTypeRef = ts.type(G, (expr as ParameterizedCallExpression).target).value as FunctionTypeExprOrRef;
			val promisifiedReturnTypeRef = extractPromisifiedReturnType(G, targetTypeRef);
			if(promisifiedReturnTypeRef!==null) {
				return promisifiedReturnTypeRef;
			}
		}
		// in all other cases:
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef
	}

	/**
	 * Given the type of a promisifiable function (see {@link #checkPromisifiablePreconditions(FunctionDefinition)},
	 * this method will return the function's return type after promisification. If the function is not promisifiable,
	 * this method returns <code>null</code>.
	 */
	def public TypeRef extractPromisifiedReturnType(RuleEnvironment G, FunctionTypeExprOrRef targetTypeRef) {
		val callbackFpar = targetTypeRef.fpars.last; // last fpar must be the callback
		if(callbackFpar!==null) {
			val callbackTypeRef = callbackFpar.typeRef;
			if(callbackTypeRef instanceof FunctionTypeExprOrRef) {
				val callback1stFparTypeRef = callbackTypeRef.fpars.head?.typeRef;
				val hasErrorFpar = isErrorOrSubtype(G, callback1stFparTypeRef);
				val errorTypeRef = if(hasErrorFpar) {
					callback1stFparTypeRef
				} else {
					G.undefinedTypeRef
				};
				val successFpars = if(hasErrorFpar) {
					callbackTypeRef.fpars.drop(1)
				} else {
					callbackTypeRef.fpars
				};
				val successFparTypeRefs = successFpars.map[typeRef].map[
					if(it!==null) it else TypeRefsFactory.eINSTANCE.createUnknownTypeRef
				].toList;
				val len = successFparTypeRefs.size;
				val successTypeRef = if(len===0) {
					G.undefinedTypeRef
				} else if(len===1) {
					successFparTypeRefs.get(0)
				} else if(len<=BuiltInTypeScope.ITERABLE_N__MAX_LEN) {
					G.iterableNTypeRef(successFparTypeRefs.size, successFparTypeRefs)
				} else {
					val remaining = tsh.createUnionType(G, successFparTypeRefs.drop(BuiltInTypeScope.ITERABLE_N__MAX_LEN-1));
					G.iterableNTypeRef(
						BuiltInTypeScope.ITERABLE_N__MAX_LEN,
						successFparTypeRefs.take(BuiltInTypeScope.ITERABLE_N__MAX_LEN-1) + #[remaining])
				};
				return TypeUtils.createPromiseTypeRef(G.builtInTypeScope, successTypeRef, errorTypeRef);
			}
		}
		return null;
	}

	def private boolean isErrorOrSubtype(RuleEnvironment G, TypeRef typeRef) {
		return typeRef!==null && ts.subtypeSucceeded(G, typeRef, G.errorTypeRef);
	}
}
