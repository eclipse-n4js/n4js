/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform

import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.Optional

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Transform async functions to an ES2015 equivalent (still requires generator functions and 'yield').
 * <p>
 * For example, changes the following code
 * <pre>
 * async function funDef(p1, p2) {
 *     await foo();
 * }
 * </pre>
 * to
 * <pre>
 * function funDef(p1, p2) {
 *     return $spawn(function *() {
 *         yield foo();
 *     }.apply(this, arguments));
 * };
 * </pre>
 */
@Optional(AsyncAwait)
class AsyncAwaitTransformation extends Transformation {


	override assertPreConditions() {
		// true
	}
	override assertPostConditions() {
		assertFalse("there should not be any async functions left in the intermediate model",
			collectNodes(state.im, FunctionOrFieldAccessor, true).exists[isAsync]);
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, FunctionDefinition, true).filter[isAsync].forEach[transformAsyncFunction];
	}

	private def void transformAsyncFunction(FunctionDefinition funDef) {
		if(!funDef.isAsync) {
			throw new IllegalArgumentException("given function definition must be asynchronous");
		}

		funDef.declaredAsync = false;

		// (1) remove 'await' from all return expressions (i.e. change "return await expr;" to "return expr;")
		val body = funDef.body;
		val returnStmnts = body.allReturnStatements.toList
		returnStmnts.forEach[returnStmnt|
			val expr = returnStmnt.expression;
			if(expr instanceof AwaitExpression) {
				// case 3: remove await
				val innerExpr = expr.expression;
				expr.expression = null;
				replace(expr,innerExpr);
			}
		];

		// (2) wrap existing code in funDef within a call to $spawn
		// $spawn(function *() {
		//     <original statements from funDef>
		// }.apply(this, arguments));
		val invoke$spawn = _CallExpr(
		_IdentRef(steFor_$spawn),
			_CallExpr(
				_PropertyAccessExpr(
					_FunExpr(false)=>[innerFun|
						innerFun.generator = true;
						innerFun.body.statements += body.statements // move(!) statements of funDef to innerFun
					],
					getSymbolTableEntryForMember(state.G.functionType, "apply", false, false, true)
				),
				_ThisLiteral,
				_IdentRef(steFor_arguments())
			) // end call function*()
		); // end call $spawn
		if(!body.statements.empty) {
			throw new IllegalStateException();
		}
		body.statements += _ReturnStmnt(invoke$spawn);

		// (3) replace all "await <expression>" by "yield <expression>"
		collectNodes(body, AwaitExpression, true).forEach[
			replace(it, _YieldExpr(it.expression));
		];
	}
}
