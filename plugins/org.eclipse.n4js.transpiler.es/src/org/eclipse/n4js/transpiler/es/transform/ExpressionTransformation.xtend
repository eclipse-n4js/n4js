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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.PromisifyHelper

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Some expressions need special handling, this is done in this transformation.
 * <p>
 * Dependencies:
 * <ul>
 * <li>ExcludesBefore: AsyncAwaitTransformation<br>
 *     otherwise the 'await' expressions have already be converted to 'yield', but we need to find them in order to
 *     support auto-promisify after 'await'; see method {@link #transformExpression(AwaitExpression)}
 * </ul>
 */
@ExcludesBefore(AsyncAwaitTransformation)
class ExpressionTransformation extends Transformation {

	@Inject private PromisifyHelper promisifyHelper;


	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, Expression, true).forEach[transformExpression];
	}

	def private dispatch void transformExpression(Expression relExpr) {
		// default case -> do nothing
	}

	def private dispatch void transformExpression(CastExpression castExpr) {
		replace(castExpr, castExpr.expression); // simply remove the cast
	}

	/**
	 * <b>IMPORTANT:</b>
	 * here we only do some special handling for the auto-promisify case within an AwaitExpression; the main
	 * handling of the AwaitExpression itself is done in a later transformation {@code BlockTransformation.transformBlockAsync(Block)}!!!
	 * <p>
	 * Changes
	 * <pre>await cls.meth(a, b)</pre>
	 * to
	 * <pre>await $n4promisifyMethod(cls, 'meth', [a, b])</pre>
	 * OR
	 * <pre>await fun(a, b)</pre>
	 * to
	 * <pre>await $n4promisifyFunction(fun, [a, b])</pre>
	 * assuming that method 'meth' and function 'fun' are annotated with <code>@Promisifiable</code>.
	 */
	def private dispatch void transformExpression(AwaitExpression awaitExpr) {
		val awaitExprOrig = state.tracer.getOriginalASTNodeOfSameType(awaitExpr, false);
		if(promisifyHelper.isAutoPromisify(awaitExprOrig)) {
			val callExpr = awaitExpr.expression as ParameterizedCallExpression; // cast is safe because isPromisifiableExpression() returned true
			val replacement = promisify(callExpr);
			replace(callExpr, replacement); // note: leaving awaitExpr in the IM; only replacing its contained expression!!
		}
	}

	/**
	 * Changes
	 * <pre>@Promisify cls.meth(a, b)</pre>
	 * to
	 * <pre>$n4promisifyMethod(cls, 'meth', [a, b])</pre>
	 * OR
	 * <pre>@Promisify fun(a, b)</pre>
	 * to
	 * <pre>$n4promisifyFunction(fun, [a, b])</pre>
	 */
	def private dispatch void transformExpression(PromisifyExpression promiExpr) {
		val callExpr = promiExpr.expression as ParameterizedCallExpression; // cast is safe because of validations
		val replacement = promisify(callExpr);
		replace(promiExpr, replacement);
	}

	def private ParameterizedCallExpression promisify(ParameterizedCallExpression callExpr) {
		val target = callExpr.target;
		val targetSTE = switch(target) {
			ParameterizedPropertyAccessExpression_IM: target.property_IM
			IdentifierRef_IM: target.id_IM
		};
		if(targetSTE instanceof SymbolTableEntryOriginal) {
			val originalTarget = targetSTE.originalTarget;
			if(originalTarget instanceof TFunction) { // could be a method
				val originalTargetTypeRef = TypeUtils.createTypeRef(originalTarget) as FunctionTypeRef;
				val returnTypeRef = promisifyHelper.extractPromisifiedReturnType(state.G, originalTargetTypeRef);
				val hasErrorValue = !TypeUtils.isUndefined(returnTypeRef.typeArgs.drop(1).head); // isUndefined() is null-safe
				val hasMoreThan1SuccessValue = state.G.isIterableN(returnTypeRef.typeArgs.head); // isIterableN() is null-safe
				if(target instanceof ParameterizedPropertyAccessExpression_IM && targetSTE.originalTarget instanceof TMethod) {
					// we have a method invocation, so we need to preserve the 'this' argument:

					// @Promisify cls.meth(a, b)
					// -->
					// $n4promisifyMethod(cls, 'meth', [a, b])
					return _CallExpr(
						_IdentRef(steFor_$n4promisifyMethod()),
						(target as ParameterizedPropertyAccessExpression_IM).target, // here we take the "cls" part of "cls.meth" as first argument
						_StringLiteralForSTE(targetSTE),
						_ArrLit(callExpr.arguments.map[_ArrayElement(spread, expression)]), // reuse arguments while preserving spread
						_BooleanLiteral(hasMoreThan1SuccessValue),
						_BooleanLiteral(!hasErrorValue)
					);
				} else {
					// in all other cases, we do not preserve the 'this' argument:

					// @Promisify fun(a, b)
					// -->
					// $n4promisifyFunction(fun, [a, b])
					return _CallExpr(
						_IdentRef(steFor_$n4promisifyFunction()),
						callExpr.target, // reuse target as first argument
						_ArrLit(callExpr.arguments.map[_ArrayElement(spread,expression)]), // reuse arguments while preserving spread
						_BooleanLiteral(hasMoreThan1SuccessValue),
						_BooleanLiteral(!hasErrorValue)
					);
				}
			}
		}
		// if anything goes awry, we just return callExpr as replacement, which means we simply remove the @Promisify
		return callExpr;
	}
}
