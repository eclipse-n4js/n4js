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

import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.MethodDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import static extension org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FormalParameterTrafoTest extends AbstractTranspilerTest {



	@Test
	def void testReplaceVariadic_in_topLevelFunction() {

		val state = '''

			function F (a, b : any=undefined, ...c ) {
				a = 3;
				c[1] = 5;
				c.length
				b = 4;
			}

		'''.createTranspilerState.transform;


//		'''
//			F = function F(a,b){
// 				var $capturedArgs0 = arguments;
// 				var c = Array.prototype.slice.call(arguments, 2);
//				a = 3;s
//				c[1] = 5;
//				c.length
//				b = 4;
//			}
//		'''

		val F_AST = state.findFirstInAST(FunctionDeclaration);
		val F_IM = state.findFirstInIM(FunctionDeclaration,[name=="F"]);

		// precondition:
		"Should have 3 paramerters".assertEquals(3, F_AST.fpars.size)

		val fparC_AST = F_AST.fpars.get(2);
		"should be c".assertEquals("c", fparC_AST.name)
		"should be variadic".assertTrue(fparC_AST.isVariadic)



		/// checking transformed result,
		// A) function Expression
		"Should have 2 paramerters".assertEquals(2, F_IM.fpars.size)
		val varC_decl_IM = (F_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
		"c".assertEquals("c",varC_decl_IM.name)
		val pc_IM = varC_decl_IM.expression as ParameterizedCallExpression;

		/// B) call to ....slice()
		"should have two parameter".assertEquals(2,pc_IM.arguments.size)
		val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
		"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

		val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
		"should be 2".assertEquals(2L,arg1.value.longValue())

	}


	@Test
	def void testReplaceVariadic_in_Method() {

		val state = '''

			class A {
				methodF (a, b : any=undefined, ...c ) {
					a = 3;
					c[1] = 5;
					c.length
					b = 4;
				}
			}

		'''.createTranspilerState.transform;


//		'''
//			F = function F(a,b){var c = Array.prototype.slice.call(arguments, 2);
//				a = 3;s
//				c[1] = 5;
//				c.length
//				b = 4;
//			}
//		'''

		val F_AST = state.findFirstInAST(MethodDeclaration);
		val F_IM = state.findFirstInIM(MethodDeclaration,[name=="methodF"]);

		// precondition:
		"Should have 3 paramerters".assertEquals(3, F_AST.fpars.size)

		val fparC_AST = F_AST.fpars.get(2);
		"should be c".assertEquals("c", fparC_AST.name)
		"should be variadic".assertTrue(fparC_AST.isVariadic)



		/// checking transformed result,
		// A) function Expression
		"Should have 2 paramerters".assertEquals(2, F_IM.fpars.size)
		val varC_decl_IM = (F_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
		"c".assertEquals("c",varC_decl_IM.name)
		val pc_IM = varC_decl_IM.expression as ParameterizedCallExpression;

		/// B) call to ....slice()
		"should have two parameter".assertEquals(2,pc_IM.arguments.size)
		val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
		"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

		val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
		"should be 2".assertEquals(2L,arg1.value.longValue())

	}

	@Test
	def void testVargsInFunctionExpressionInsideOfMethod() {

		val state = '''
			class A {
				methodF (a, b : any=undefined, ...c ) {

					var g = function( a2, b2, c2, ...d2 ) {
						console.log("Length of d2 is",d2.length)	;
					}

					g(a,b,a,c);
				}
			}
		'''.createTranspilerState.transform;


//		'''
//       methodF: { value: function
//        	methodF___n4(a,b){var c = Array.prototype.slice.call(arguments, 2);
//
//        		var  g = function(a2,b2,c2){var d2 = Array.prototype.slice.call(arguments, 3);
//        			console.log("Length of d2 is",d2.length)	;
//        };
//
//        		g(a,b,a,c);
//        }}

//		'''

		val F_AST = state.findFirstInAST(MethodDeclaration);
		val F_IM = state.findFirstInIM(MethodDeclaration,[name=="methodF"]);

		// precondition:
		"Should have 3 paramerters".assertEquals(3, F_AST.fpars.size)

		val fparC_AST = F_AST.fpars.get(2);
		"should be c".assertEquals("c", fparC_AST.name)
		"should be variadic".assertTrue(fparC_AST.isVariadic)

		val allFE_IM = EcoreUtil2.eAllOfType(F_IM.body, FunctionExpression)
		"exactly one FunctionExpression in body of methodF() expected".assertEquals(1,allFE_IM.size)
		val gFE_IM = allFE_IM.get(0)
		"FE must be assigned to variable 'g'".assertEquals("g", (gFE_IM.eContainer as VariableDeclaration).name)



		/// checking transformed result, methodF
		{
			// A) function Expression for methodF
			"Should have 2 paramerters".assertEquals(2, F_IM.fpars.size)
			val varC_decl_IM = (F_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
			"c".assertEquals("c",varC_decl_IM.name)
			val pc_IM = varC_decl_IM.expression as ParameterizedCallExpression;

			/// B) call to ....slice() for c
			"should have two parameter".assertEquals(2,pc_IM.arguments.size)
			val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
			"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

			val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
			"should be 2".assertEquals(2L,arg1.value.longValue())

			// C) checking call of g(a,b,a,c) in body of methodF():
		 	val paramCall_of_g_IM = (F_IM.body?.statements.findLast[ it instanceof ExpressionStatement] as ExpressionStatement).expression as ParameterizedCallExpression;
		 	val g_Args_IM = paramCall_of_g_IM.arguments.map[expression]
		 	#['a','b','a','c'].forEach[arg,i| ("i="+i).assertEquals(arg,(g_Args_IM.get(i) as IdentifierRef_IM).id_IM.name )]
		}
		/// checking transformed result, g()
		{
			// A) function Expression for FE g()
			"Should have 3 paramerters".assertEquals(3, gFE_IM.fpars.size)
			val varD2_decl_IM = (gFE_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
			"d2".assertEquals("d2",varD2_decl_IM.name)
			val pc_IM = varD2_decl_IM.expression as ParameterizedCallExpression;

			/// B) call to ....slice() for d2
			"should have two parameter".assertEquals(2,pc_IM.arguments.size)
			val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
			"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

			val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
			"argument-offset of d2 is 3".assertEquals(3L,arg1.value.longValue())
		}

	}

	@Test
	def void testVargs_InFunctionExpression_InsideAFunctionExpression_InsideOfMethod() {

		val state = '''
			class A {
				methodF (a, b : any=undefined, ...c ) {

					var g = function( a2, b2, c2, ...d2 ) {
						console.log("Length of d2 is",d2.length)	;

						(function   (...z1) {
							return 5;
						})(c);

					}

					g(a,b,a,c);
				}
			}
		'''.createTranspilerState.transform;


//		'''
//        methodF: { value: function
// 	       methodF___n4(a,b){var c = Array.prototype.slice.call(arguments, 2);
//
//        		var  g = function(a2,b2,c2){var d2 = Array.prototype.slice.call(arguments, 3);
//        			console.log("Length of d2 is",d2.length)	;
//
//
//        			(function(){var z1 = Array.prototype.slice.call(arguments, 0);
//        				return 5;
//       			 })(c);
//       		 };
// ...
//		'''

		val F_AST = state.findFirstInAST(MethodDeclaration);
		val F_IM = state.findFirstInIM(MethodDeclaration,[name=="methodF"]);

		// precondition:
		"Should have 3 paramerters".assertEquals(3, F_AST.fpars.size)

		val fparC_AST = F_AST.fpars.get(2);
		"should be c".assertEquals("c", fparC_AST.name)
		"should be variadic".assertTrue(fparC_AST.isVariadic)

		val allFE_IM = EcoreUtil2.eAllOfType(F_IM.body, FunctionExpression)
		"exactly one FunctionExpression in body of methodF() expected".assertEquals(2,allFE_IM.size)
		val gFE_IM = allFE_IM.get(0)
		"FE must be assigned to variable 'g'".assertEquals("g", (gFE_IM.eContainer as VariableDeclaration).name)

		val anonFE_IM = allFE_IM.get(1)
		"anonymous FunctionExpression should not have a name".assertEquals(null, anonFE_IM.name );


		/// checking transformed result, methodF
		{
			// A) function Expression for methodF
			"Should have 2 paramerters".assertEquals(2, F_IM.fpars.size)
			val varC_decl_IM = (F_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
			"c".assertEquals("c",varC_decl_IM.name)
			val pc_IM = varC_decl_IM.expression as ParameterizedCallExpression;

			/// B) call to ....slice() for c
			"should have two parameter".assertEquals(2,pc_IM.arguments.size)
			val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
			"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

			val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
			"should be 2".assertEquals(2L,arg1.value.longValue())

			// C) checking call of g(a,b,a,c) in body of methodF():
		 	val paramCall_of_g_IM = (F_IM.body?.statements.findLast[ it instanceof ExpressionStatement] as ExpressionStatement).expression as ParameterizedCallExpression;
		 	val g_Args_IM = paramCall_of_g_IM.arguments.map[expression]
		 	#['a','b','a','c'].forEach[arg,i| ("i="+i).assertEquals(arg,(g_Args_IM.get(i) as IdentifierRef_IM).id_IM.name )]
		}
		/// checking transformed result, g()
		{
			// A) function Expression for FE g()
			"Should have 3 paramerters".assertEquals(3, gFE_IM.fpars.size)
			val varD2_decl_IM = (gFE_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
			"d2".assertEquals("d2",varD2_decl_IM.name)
			val pc_IM = varD2_decl_IM.expression as ParameterizedCallExpression;

			/// B) call to ....slice() for d2
			"should have two parameter".assertEquals(2,pc_IM.arguments.size)
			val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
			"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

			val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
			"argument-offset of d2 is 3".assertEquals(3L,arg1.value.longValue())
		}
		/// checking transformed result, ( function(...){})()
		{
			// A) function Expression for FE g()
			"Should have 0 paramerters".assertEquals(0, anonFE_IM.fpars.size)
			val varZ1_decl_IM = (anonFE_IM.body?.statementAt_skipArgsCapture_get(0) as VariableStatement).varDecl.get(0)
			"z1".assertEquals("z1",varZ1_decl_IM.name)
			val pc_IM = varZ1_decl_IM.expression as ParameterizedCallExpression;

			/// B) call to ....slice() for d2
			"should have two parameter".assertEquals(2,pc_IM.arguments.size)
			val arg0 = pc_IM.arguments.get(0).expression as IdentifierRef_IM;
			"First parameters must be the arguments-variable".assertEquals("arguments",arg0.id_IM.name)

			val arg1 = pc_IM.arguments.get(1).expression as IntLiteral;
			"argument-offset of z1 is 0".assertEquals(0L,arg1.value.longValue())
		}

	}

}
