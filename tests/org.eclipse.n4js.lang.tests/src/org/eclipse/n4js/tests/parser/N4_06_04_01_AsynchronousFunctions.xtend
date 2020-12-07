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
package org.eclipse.n4js.tests.parser

import com.google.common.collect.Iterables
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.junit.Ignore
import org.junit.Test

/**
 * Parser tests for asynchronous functions-
 */
class N4_06_04_01_AsynchronousFunctions extends AbstractParserTest {

	@Test
	def void test_NonAsyncArrowExpression() {
		val script = '''
			() => f()
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		val expr = (script.scriptElements.get(0) as ExpressionStatement).expression;
		assertInstanceOf(expr, ArrowFunction)
		assertFalse("Function must not be async", (expr as ArrowFunction).async);
	}

	@Test
	def void test_AsyncArrowExpression() {
		val script = '''
			async () => f()
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		val expr = (script.scriptElements.get(0) as ExpressionStatement).expression;
		assertInstanceOf(expr, ArrowFunction)
		assertTrue("Function must not be async", (expr as ArrowFunction).async);
	}

	/**
	 * async is recognized as identifier, thus the whole snippet looks like a method call.. which causes a problem at '=>'
	 */
	@Test
	def void test_NonAsyncArrowExpressionWithIdentifierRef() {
		val script = '''
			async
			() => f()
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}



	@Test
	def void test_AsyncFunctionDeclaration() {
		val script = '''
			async function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, FunctionDeclaration)
		assertTrue("Function must be async", (script.scriptElements.get(0) as FunctionDeclaration).async)
	}

	@Test
	def void test_AsyncFunctionDeclarationWithAnnotation() {
		val script = '''
			@Someanno async function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, FunctionDeclaration)
		assertTrue("Function must be async", (script.scriptElements.get(0) as FunctionDeclaration).async)
	}

	@Test
	def void test_AsyncMethodDeclarations() {
		val script = '''
			class C {
				async foo() {}
				bar() {}
				@Final async foo1() {}
				@Final bar1() {}
			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, N4ClassDeclaration)
		val classDecl = script.scriptElements.get(0) as N4ClassDeclaration
		assertInstances(classDecl.ownedMembers, N4MethodDeclaration, N4MethodDeclaration, N4MethodDeclaration, N4MethodDeclaration)

	}

	@Test @Ignore // invalid test expectation
	def void test_AsyncMethodDeclarationErrorDueToTrailingEOL() {
		val script = '''
			class C {
				async
				foo() {}

			}
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void test_NonAsyncFunctionDeclaration() {
		val script = '''
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, FunctionDeclaration)
		assertFalse("Function must not be async", (script.scriptElements.get(0) as FunctionDeclaration).async)
	}

	@Test
	def void test_Async_WithLineBreak() {
		val script = '''
			async
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, IdentifierRef)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}
	@Test
	def void test_Async_WithLineBreaky() {
		val script = '''
			asyncx
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, IdentifierRef)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}

	@Test
	def void test_IdentifierAsync() {
		val script = '''
			async

		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, IdentifierRef)
	}

	@Test
	def void test_IdentifierAsync_AndOthers() {
		val script = '''
			async
			someVar
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, ExpressionStatement)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, IdentifierRef)
		assertInstanceOf((script.scriptElements.get(1) as ExpressionStatement).expression, IdentifierRef)
	}



	@Test
	def void test_IdentifierOnlyOthers() {
		val script = '''
			asyncOther
			someVar
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, ExpressionStatement)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, IdentifierRef)
		assertInstanceOf((script.scriptElements.get(1) as ExpressionStatement).expression, IdentifierRef)
	}

	@Test
	def void test_AsyncFctExpr() {
		val script = '''
			(async function foo(): void {})
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertFExprAsync(script.scriptElements.get(0), true, false);
	}

	@Test
	def void test_AsyncGenExpr1() {
		val script = '''
			(async function* foo(): void {})
		'''.parseESSuccessfully
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertFExprAsync(script.scriptElements.get(0), true, true);
	}

	@Test
	def void test_AsyncGenExpr2() {
		val script = '''
			(async function*(): void {})
		'''.parseESSuccessfully
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertFExprAsync(script.scriptElements.get(0), true, true);
	}

	@Test
	def void test_AsyncGenExpr3() {
		val script = '''
			(async function* <T> (syncIterable: Iterable<T>): T {})
		'''.parseESSuccessfully
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		val fe = assertFExprAsync(script.scriptElements.get(0), true, true);
		assertEquals(1, fe.typeVars.size);
		assertEquals("T", fe.typeVars.head.name);
	}

	@Test
	def void test_FctExpr() {
		val script = '''
			(function foo(): void {})
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertFExprAsync(script.scriptElements.get(0), false, false);
	}

	@Test
	def void test_AsyncFctExpr_WithLineBreak() {
		val script = '''
			async
			(function foo(): void {})
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, ParameterizedCallExpression)
	}

	@Test
	def void test_AsyncFctExpr_WithLineBreak1() {
		val script = '''
			asyncCall
			(function foo(): void {})
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement)
		assertInstanceOf((script.scriptElements.get(0) as ExpressionStatement).expression, ParameterizedCallExpression)
	}

	private def FunctionExpression assertFExprAsync(ScriptElement stmt, boolean expectedAsync, boolean expectedGenerator) {
		assertInstanceOf(stmt, ExpressionStatement)
		val expr = (stmt as ExpressionStatement).expression;
		assertInstanceOf(expr, ParenExpression);
		val fexpr = (expr as ParenExpression).expression;
		assertInstanceOf(fexpr, FunctionExpression);
		assertEquals(expectedAsync, (fexpr as FunctionExpression).async)
		assertEquals(expectedGenerator, (fexpr as FunctionExpression).generator)
		return fexpr as FunctionExpression;
	}

	@Test
	def void test_ExpressionWithAsync_WithLineBreak() {
		val script = '''
			someVar + async
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}

	@Test
	def void test_ExpressionWithAsyncAndVar_WithLineBreak() {
		val script = '''
			async + someVar
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}

	@Test
	def void test_SomeVarName_WithLineBreak() {
		val script = '''
			somveVar
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}

	@Test
	def void test_Async_WithSemi() {
		val script = '''
			async;
			function foo(): void {}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, ExpressionStatement, FunctionDeclaration)
		assertFalse("Function must not be async", (script.scriptElements.get(1) as FunctionDeclaration).async)
	}

	@Test
	def void test_VariableNamedAsync() {
		val script = '''
			var async = 1
			async
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertInstances(script.scriptElements, VariableStatement, ExpressionStatement)
		assertTrue(script.scriptElements.get(1).class.simpleName,
			(script.scriptElements.get(1) as ExpressionStatement).expression instanceof IdentifierRef)

	}

	def private void assertInstanceOf(Object obj, Class<?> type) {
		assertTrue(
			"Expected type " + type.simpleName + " but was " + if (obj === null) "null" else obj.class.simpleName,
			type.isInstance(obj)
		);
	}

	def private void assertInstances(Iterable<? extends Object> objs, Class<?>... types) {
		var i = 0;
		for (obj : objs) {
			if (i >= types.length) {
				fail("Expected " + types.length + " elements, got " + Iterables.size(objs));
			}
			assertTrue("Expected type " + types.get(i).simpleName + " but was " + obj.class.simpleName,
				types.get(i).isInstance(obj));
			i++;
		}
		if (i < types.length) {
			fail("Expected " + types.length + " elements, got " + Iterables.size(objs));
		}

	}

}
