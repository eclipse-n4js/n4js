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
package org.eclipse.n4js.transpiler.es.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


import static extension org.junit.Assert.*
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.ExpressionStatement
import java.math.BigDecimal
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.junit.Ignore

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GHOLD_93_AsyncFunctionTest extends AbstractTranspilerTest {

	@Inject extension ParseHelper<Script>

	@Test @Ignore
	def void testWrapImplicitReturn() {

		val state = '''
			()=>5
		'''.createTranspilerState().transform;

//		val generated = '''
//			System.register([], function($n4Export) {
//				return {
//					setters: [],
//					execute: function() {
//						(function() {
//							5;
//						}).bind(this);
//					}
//				};
//			});
//			//# sourceMappingURL=X.map
//		'''


		val F_AST = state.findFirstInAST(ArrowFunction);
		val allFEs = EcoreUtil2.eAllOfType( state.im, FunctionExpression).toList;


		// precondition:
		"Should have 0 parameters".assertEquals(0, F_AST.fpars.size)
		"Should have 1 statement".assertEquals(1, F_AST.body.statements.size)
		val exprStmt = F_AST.body.statements.get(0) as ExpressionStatement;
		val intLit = exprStmt.expression as IntLiteral;
		"statement should be int (5)".assertEquals( BigDecimal.valueOf(5L), intLit.value );

		// After Transpilations:

		"Exactly 3+1 FunctionExpressions expected.".assertEquals(3+1,allFEs.size); // +1 for CJS-wrapper IDE-2050
		val F_IM = allFEs.get(2+1);

		"Should have 0 parameters".assertEquals(0, F_IM.fpars.size)
		"Should have 1 statement".assertEquals(1, F_IM.body.statements.size)
		val retStmt_IM = F_IM.body.statements.get(0) as ReturnStatement;
		val intLit_IM = retStmt_IM.expression as IntLiteral;
		"statement should be int (5)".assertEquals( BigDecimal.valueOf(5L), intLit_IM.value );


		// .System x2 , .exports, .register , .bind expected  :
		val allCallExpressions = EcoreUtil2.eAllOfType( state.im, ParameterizedCallExpression).toList;
		"expect 5 call-expressions, second should be '.bind()' ".assertEquals(5, allCallExpressions.size)

		val bindCall_IM = allCallExpressions.get(2);
		val methodRef_IM = (bindCall_IM.target as ParameterizedPropertyAccessExpression_IM);
		assertEquals("bind", methodRef_IM.rewiredTarget.name )

	}

	@Test
	def void test_Compile() throws Throwable{

		val script = '''
			async function foo(arg) {
			    await Promise.resolve(1);
			}
		''';

		val moduleWrapped = '''
			'use strict';
			System.register([], function($n4Export) {
				var foo;
				foo = function foo(arg) {
					return $spawn(function*() {
						(yield Promise.resolve(1));
««« GHOLD-93:
«««	The following 2 lines
«««						(yield undefined);
«««						return;
««« are completely removed !!!;  not even replaced by 1 line:
«««						(return undefined);
					}.apply(this, arguments));
				};
				return {
					setters: [],
					execute: function() {}
				};
			});
		'''.cjsPatched;

	 	// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript;

   		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet);
		scriptNode.resolveLazyRefs;

		assertCompileResult(scriptNode,moduleWrapped);
	}

}
