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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.AnnotationDefinition.PROMISIFIABLE;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.errorTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getBuiltInTypeScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableNTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.drop;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.take;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Core logic for handling annotation <code>@Promisifiable</code> and operator <code>@Promisify</code>, i.e.
 * {@link PromisifyExpression}s.
 */
public class PromisifyHelper {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	@SuppressWarnings("javadoc")
	public static enum CheckResult {
		OK, MISSING_CALLBACK, BAD_CALLBACK__MORE_THAN_ONE_ERROR, BAD_CALLBACK__ERROR_NOT_FIRST_ARG
	}

	/**
	 * Checks if the given function or method may be annotated with <code>@Promisifiable</code>.
	 */
	public CheckResult checkPromisifiablePreconditions(FunctionDefinition funDef) {
		TFunction type = (TFunction) funDef.getDefinedType();
		EList<TFormalParameter> fpars = type == null ? null : type.getFpars();
		TFormalParameter lastFpar = fpars == null ? null : last(fpars);
		TypeRef lastFparTypeRef = lastFpar == null ? null : lastFpar.getTypeRef();
		if (!(lastFparTypeRef instanceof FunctionTypeExprOrRef)) {
			// error: last fpar of promisifiable function/method must be a function (i.e. the callback)
			return CheckResult.MISSING_CALLBACK;
		}
		// ok, we have a callback; now check its signature ...
		RuleEnvironment G = newRuleEnvironment(funDef);
		FunctionTypeExprOrRef callbackTypeRef = (FunctionTypeExprOrRef) lastFparTypeRef;
		EList<TFormalParameter> callbackFpars = callbackTypeRef.getFpars();
		List<TFormalParameter> errorFpars = toList(filter(callbackFpars, it -> isErrorOrSubtype(G, it.getTypeRef())));
		if (errorFpars.size() > 1) {
			// error: more than one fpar of type Error or subtype of Error
			return CheckResult.BAD_CALLBACK__MORE_THAN_ONE_ERROR;
		}
		TFormalParameter errorFpar = head(errorFpars);
		if (errorFpar != null && errorFpar != callbackFpars.get(0)) {
			// error: fpar of type Error is not the first fpar
			return CheckResult.BAD_CALLBACK__ERROR_NOT_FIRST_ARG;
		}
		return CheckResult.OK;
	}

	/**
	 * Checks if the given await expression is a case of "auto-promisify", i.e. if it is a short-hand form for
	 *
	 * <pre>
	 * await @Promisify foo()
	 * </pre>
	 *
	 * .
	 */
	public boolean isAutoPromisify(AwaitExpression awaitExpr) {
		return awaitExpr != null
				&& !(awaitExpr.getExpression() instanceof PromisifyExpression) // must not be explicit promisify!
				&& isPromisifiableExpression(awaitExpr.getExpression());
	}

	/**
	 * Tells if the given expression is promisifiable, i.e. if it is an expression that may appear after a
	 * <code>@Promisify</code> operator and thus is a valid expression within a {@link PromisifyExpression}. However,
	 * this method will *not* check if the expression is actually contained in a {@code PromisifyExpression}.
	 */
	public boolean isPromisifiableExpression(Expression expr) {
		if (expr instanceof ParameterizedCallExpression) {
			RuleEnvironment G = newRuleEnvironment(expr);
			TypeRef targetTypeRef = ts.type(G, ((ParameterizedCallExpression) expr).getTarget());
			if (targetTypeRef instanceof FunctionTypeExprOrRef) {
				TFunction fun = ((FunctionTypeExprOrRef) targetTypeRef).getFunctionType();
				return fun != null && PROMISIFIABLE.hasAnnotation(fun);
			}
		}
		return false;
	}

	/**
	 * If the given expression is a promisifiable expression (see {@link #isPromisifiableExpression(Expression)}, this
	 * method will return the return type of the invoked function or method after promisification.
	 */
	public TypeRef extractPromisifiedReturnType(Expression expr) {
		if (isPromisifiableExpression(expr)) {
			RuleEnvironment G = newRuleEnvironment(expr);
			// casts in next line are OK, because of isPromisifiableExpression() returned true
			FunctionTypeExprOrRef targetTypeRef = (FunctionTypeExprOrRef) ts.type(G,
					((ParameterizedCallExpression) expr).getTarget());
			TypeRef promisifiedReturnTypeRef = extractPromisifiedReturnType(G, targetTypeRef);
			if (promisifiedReturnTypeRef != null) {
				return promisifiedReturnTypeRef;
			}
		}
		// in all other cases:
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	/**
	 * Given the type of a promisifiable function (see {@link #checkPromisifiablePreconditions(FunctionDefinition)},
	 * this method will return the function's return type after promisification. If the function is not promisifiable,
	 * this method returns <code>null</code>.
	 */
	public TypeRef extractPromisifiedReturnType(RuleEnvironment G, FunctionTypeExprOrRef targetTypeRef) {
		TFormalParameter callbackFpar = last(targetTypeRef.getFpars()); // last fpar must be the callback
		if (callbackFpar == null) {
			return null;
		}
		TypeRef callbackTypeRef = callbackFpar.getTypeRef();
		if (callbackTypeRef instanceof FunctionTypeExprOrRef) {
			FunctionTypeExprOrRef fteor = (FunctionTypeExprOrRef) callbackTypeRef;
			EList<TFormalParameter> fpars = fteor.getFpars();
			TFormalParameter headFP = head(fpars);
			TypeRef callback1stFparTypeRef = headFP == null ? null : headFP.getTypeRef();
			boolean hasErrorFpar = isErrorOrSubtype(G, callback1stFparTypeRef);
			TypeRef errorTypeRef = (hasErrorFpar) ? callback1stFparTypeRef : undefinedTypeRef(G);
			List<TFormalParameter> successFpars = (hasErrorFpar) ? toList(drop(fpars, 1)) : fpars;

			List<TypeRef> successFparTypeRefs = toList(map(successFpars, fp -> {
				TypeRef tr = fp.getTypeRef();

				return (tr != null) ? tr : TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
			}));

			int len = successFparTypeRefs.size();
			TypeRef successTypeRef = null;
			if (len == 0) {
				successTypeRef = undefinedTypeRef(G);
			} else if (len == 1) {
				successTypeRef = successFparTypeRefs.get(0);
			} else if (len <= BuiltInTypeScope.ITERABLE_N__MAX_LEN) {
				successTypeRef = iterableNTypeRef(G, successFparTypeRefs.size(),
						successFparTypeRefs.toArray(new TypeRef[0]));
			} else {
				List<TypeRef> droppedFPars = toList(
						drop(successFparTypeRefs, BuiltInTypeScope.ITERABLE_N__MAX_LEN - 1));
				TypeRef remaining = tsh.createUnionType(G, droppedFPars.toArray(new TypeRef[0]));

				Iterable<TypeRef> left = take(successFparTypeRefs, BuiltInTypeScope.ITERABLE_N__MAX_LEN - 1);
				List<TypeRef> concatted = toList(Iterables.concat(left, List.of(remaining)));
				successTypeRef = iterableNTypeRef(G, BuiltInTypeScope.ITERABLE_N__MAX_LEN,
						concatted.toArray(new TypeRef[0]));
			}
			return TypeUtils.createPromiseTypeRef(getBuiltInTypeScope(G), successTypeRef, errorTypeRef);
		}
		return null;
	}

	private boolean isErrorOrSubtype(RuleEnvironment G, TypeRef typeRef) {
		return typeRef != null && ts.subtypeSucceeded(G, typeRef, errorTypeRef(G));
	}
}
