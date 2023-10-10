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
package org.eclipse.n4js.transpiler.es.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findLast;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormalParameterTrafoTest extends AbstractTranspilerTest {

	@Test
	public void testReplaceVariadic_in_topLevelFunction() {

		TranspilerState state = transform(createTranspilerState("""

				function F (a, b : any=undefined, ...c ) {
					a = 3;
					c[1] = 5;
					c.length
					b = 4;
				}

				"""));

		// """
		// F = function F(a,b){
		// var $capturedArgs0 = arguments;
		// var c = Array.prototype.slice.call(arguments, 2);
		// a = 3;s
		// c[1] = 5;
		// c.length
		// b = 4;
		// }
		// """

		FunctionDeclaration F_AST = findFirstInAST(state, FunctionDeclaration.class);
		FunctionDeclaration F_IM = findFirstInIM(state, FunctionDeclaration.class,
				e -> Objects.equals(e.getName(), "F"));

		// precondition:
		assertEquals("Should have 3 parameters", 3, F_AST.getFpars().size());

		FormalParameter fparC_AST = F_AST.getFpars().get(2);
		assertEquals("should be c", "c", fparC_AST.getName());
		assertTrue("should be variadic", fparC_AST.isVariadic());

		/// checking transformed result,
		// A) function Expression
		assertEquals("Should have 2 parameters", 2, F_IM.getFpars().size());
		VariableDeclaration varC_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(F_IM.getBody(), 0))
				.getVarDecl().get(0);
		assertEquals("c", "c", varC_decl_IM.getName());
		ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varC_decl_IM.getExpression();

		/// B) call to ....slice()
		assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
		IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
		assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

		IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
		assertEquals("should be 2", 2L, arg1.getValue().longValue());
	}

	@Test
	public void testReplaceVariadic_in_Method() {

		TranspilerState state = transform(createTranspilerState("""

				class A {
					methodF (a, b : any=undefined, ...c ) {
						a = 3;
						c[1] = 5;
						c.length
						b = 4;
					}
				}

				"""));

		// """
		// F = function F(a,b){var c = Array.prototype.slice.call(arguments, 2);
		// a = 3;s
		// c[1] = 5;
		// c.length
		// b = 4;
		// }
		// """

		MethodDeclaration F_AST = findFirstInAST(state, MethodDeclaration.class);
		MethodDeclaration F_IM = findFirstInIM(state, MethodDeclaration.class,
				e -> Objects.equals(e.getName(), "methodF"));

		// precondition:
		assertEquals("Should have 3 parameters", 3, F_AST.getFpars().size());

		FormalParameter fparC_AST = F_AST.getFpars().get(2);
		assertEquals("should be c", "c", fparC_AST.getName());
		assertTrue("should be variadic", fparC_AST.isVariadic());

		/// checking transformed result,
		// A) function Expression
		assertEquals("Should have 2 parameters", 2, F_IM.getFpars().size());
		VariableDeclaration varC_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(F_IM.getBody(), 0))
				.getVarDecl().get(0);
		assertEquals("c", "c", varC_decl_IM.getName());
		ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varC_decl_IM.getExpression();

		/// B) call to ....slice()
		assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
		IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
		assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

		IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
		assertEquals("should be 2", 2L, arg1.getValue().longValue());

	}

	@Test
	public void testVargsInFunctionExpressionInsideOfMethod() {

		TranspilerState state = transform(createTranspilerState("""
				class A {
					methodF (a, b : any=undefined, ...c ) {

						var g = function( a2, b2, c2, ...d2 ) {
							console.log("Length of d2 is",d2.length)	;
						}

						g(a,b,a,c);
					}
				}
				"""));

		// """
		// methodF: { value: function
		// methodF___n4(a,b){var c = Array.prototype.slice.call(arguments, 2);
		//
		// var g = function(a2,b2,c2){var d2 = Array.prototype.slice.call(arguments, 3);
		// console.log("Length of d2 is",d2.length) ;
		// };
		//
		// g(a,b,a,c);
		// }}

		// """

		MethodDeclaration F_AST = findFirstInAST(state, MethodDeclaration.class);
		MethodDeclaration F_IM = findFirstInIM(state, MethodDeclaration.class,
				e -> Objects.equals(e.getName(), "methodF"));

		// precondition:
		assertEquals("Should have 3 parameters", 3, F_AST.getFpars().size());

		FormalParameter fparC_AST = F_AST.getFpars().get(2);
		assertEquals("should be c", "c", fparC_AST.getName());
		assertTrue("should be variadic", fparC_AST.isVariadic());

		List<FunctionExpression> allFE_IM = EcoreUtil2.eAllOfType(F_IM.getBody(), FunctionExpression.class);
		assertEquals("exactly one FunctionExpression in body of methodF() expected", 1, allFE_IM.size());
		FunctionExpression gFE_IM = allFE_IM.get(0);
		assertEquals("FE must be assigned to variable 'g'", "g", ((VariableDeclaration) gFE_IM.eContainer()).getName());

		/// checking transformed result, methodF
		{
			// A) function Expression for methodF
			assertEquals("Should have 2 parameters", 2, F_IM.getFpars().size());
			VariableDeclaration varC_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(F_IM.getBody(), 0))
					.getVarDecl().get(0);
			assertEquals("c", "c", varC_decl_IM.getName());
			ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varC_decl_IM.getExpression();

			/// B) call to ....slice() for c
			assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
			IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
			assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

			IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
			assertEquals("should be 2", 2L, arg1.getValue().longValue());

			// C) checking call of g(a,b,a,c) in body of methodF():
			ExpressionStatement paramCall_of_g_IM_ExprStmt = (ExpressionStatement) findLast(
					F_IM.getBody().getStatements(), stmt -> stmt instanceof ExpressionStatement);
			ParameterizedCallExpression paramCall_of_g_IM = (ParameterizedCallExpression) paramCall_of_g_IM_ExprStmt
					.getExpression();

			List<Expression> g_Args_IM = toList(map(paramCall_of_g_IM.getArguments(), arg -> arg.getExpression()));

			List<String> abac = List.of("a", "b", "a", "c");
			for (var i = 0; i < abac.size(); i++) {
				String arg = abac.get(i);
				assertEquals("i=" + i, arg, ((IdentifierRef_IM) g_Args_IM.get(i)).getId_IM().getName());
			}
		}
		/// checking transformed result, g()
		{
			// A) function Expression for FE g()
			assertEquals("Should have 3 parameters", 3, gFE_IM.getFpars().size());
			VariableDeclaration varD2_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(gFE_IM.getBody(),
					0))
							.getVarDecl().get(0);

			assertEquals("d2", "d2", varD2_decl_IM.getName());
			ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varD2_decl_IM.getExpression();

			/// B) call to ....slice() for d2
			assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
			IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
			assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

			IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
			assertEquals("argument-offset of d2 is 3", 3L, arg1.getValue().longValue());
		}

	}

	@Test
	public void testVargs_InFunctionExpression_InsideAFunctionExpression_InsideOfMethod() {

		TranspilerState state = transform(createTranspilerState("""
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
				"""));

		// """
		// methodF: { value: function
		// methodF___n4(a,b){var c = Array.prototype.slice.call(arguments, 2);
		//
		// var g = function(a2,b2,c2){var d2 = Array.prototype.slice.call(arguments, 3);
		// console.log("Length of d2 is",d2.length) ;
		//
		//
		// (function(){var z1 = Array.prototype.slice.call(arguments, 0);
		// return 5;
		// })(c);
		// };
		// ...
		// """

		MethodDeclaration F_AST = findFirstInAST(state, MethodDeclaration.class);
		MethodDeclaration F_IM = findFirstInIM(state, MethodDeclaration.class,
				e -> Objects.equals(e.getName(), "methodF"));

		// precondition:
		assertEquals("Should have 3 parameters", 3, F_AST.getFpars().size());

		FormalParameter fparC_AST = F_AST.getFpars().get(2);
		assertEquals("should be c", "c", fparC_AST.getName());
		assertTrue("should be variadic", fparC_AST.isVariadic());

		List<FunctionExpression> allFE_IM = EcoreUtil2.eAllOfType(F_IM.getBody(), FunctionExpression.class);
		assertEquals("exactly one FunctionExpression in body of methodF() expected", 2, allFE_IM.size());
		FunctionExpression gFE_IM = allFE_IM.get(0);
		assertEquals("FE must be assigned to variable 'g'", "g", ((VariableDeclaration) gFE_IM.eContainer()).getName());

		FunctionExpression anonFE_IM = allFE_IM.get(1);
		assertEquals("anonymous FunctionExpression should not have a name", null, anonFE_IM.getName());

		/// checking transformed result, methodF
		{
			// A) function Expression for methodF
			assertEquals("Should have 2 parameters", 2, F_IM.getFpars().size());
			VariableDeclaration varC_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(F_IM.getBody(), 0))
					.getVarDecl().get(0);
			assertEquals("c", "c", varC_decl_IM.getName());
			ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varC_decl_IM.getExpression();

			/// B) call to ....slice() for c
			assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
			IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
			assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

			IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
			assertEquals("should be 2", 2L, arg1.getValue().longValue());

			// C) checking call of g(a,b,a,c) in body of methodF():
			ExpressionStatement paramCall_of_g_IM_ExprStmt = (ExpressionStatement) findLast(
					F_IM.getBody().getStatements(), stmt -> stmt instanceof ExpressionStatement);
			ParameterizedCallExpression paramCall_of_g_IM = (ParameterizedCallExpression) paramCall_of_g_IM_ExprStmt
					.getExpression();

			List<Expression> g_Args_IM = toList(map(paramCall_of_g_IM.getArguments(), arg -> arg.getExpression()));

			List<String> abac = List.of("a", "b", "a", "c");
			for (int i = 0; i < abac.size(); i++) {
				String arg = abac.get(i);
				assertEquals("i=" + i, arg, ((IdentifierRef_IM) g_Args_IM.get(i)).getId_IM().getName());
			}
		}
		/// checking transformed result, g()
		{
			// A) function Expression for FE g()
			assertEquals("Should have 3 parameters", 3, gFE_IM.getFpars().size());
			VariableDeclaration varD2_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(gFE_IM.getBody(),
					0)).getVarDecl().get(0);
			assertEquals("d2", "d2", varD2_decl_IM.getName());
			ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varD2_decl_IM.getExpression();

			/// B) call to ....slice() for d2
			assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
			IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
			assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

			IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
			assertEquals("argument-offset of d2 is 3", 3L, arg1.getValue().longValue());
		}
		/// checking transformed result, ( function(...){})()
		{
			// A) function Expression for FE g()
			assertEquals("Should have 0 parameters", 0, anonFE_IM.getFpars().size());
			VariableDeclaration varZ1_decl_IM = ((VariableStatement) statementAt_skipArgsCapture_get(
					anonFE_IM.getBody(), 0)).getVarDecl().get(0);
			assertEquals("z1", "z1", varZ1_decl_IM.getName());
			ParameterizedCallExpression pc_IM = (ParameterizedCallExpression) varZ1_decl_IM.getExpression();

			/// B) call to ....slice() for d2
			assertEquals("should have two parameter", 2, pc_IM.getArguments().size());
			IdentifierRef_IM arg0 = (IdentifierRef_IM) pc_IM.getArguments().get(0).getExpression();
			assertEquals("First parameters must be the arguments-variable", "arguments", arg0.getId_IM().getName());

			IntLiteral arg1 = (IntLiteral) pc_IM.getArguments().get(1).getExpression();
			assertEquals("argument-offset of z1 is 0", 0L, arg1.getValue().longValue());
		}

	}

}
