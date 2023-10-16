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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * Parser tests for asynchronous functions-
 */
public class N4_06_04_01_AsynchronousFunctions extends AbstractParserTest {

	@Test
	public void test_NonAsyncArrowExpression() throws Exception {
		Script script = parseHelper.parse("""
					() => f()
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		Expression expr = ((ExpressionStatement) script.getScriptElements().get(0)).getExpression();
		assertInstanceOf(expr, ArrowFunction.class);
		assertFalse("Function must not be async", ((ArrowFunction) expr).isAsync());
	}

	@Test
	public void test_AsyncArrowExpression() throws Exception {
		Script script = parseHelper.parse("""
					async () => f()
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		Expression expr = ((ExpressionStatement) script.getScriptElements().get(0)).getExpression();
		assertInstanceOf(expr, ArrowFunction.class);
		assertTrue("Function must not be async", ((ArrowFunction) expr).isAsync());
	}

	/**
	 * async is recognized as identifier, thus the whole snippet looks like a method call.. which causes a problem at
	 * '=>'
	 */
	@Test
	public void test_NonAsyncArrowExpressionWithIdentifierRef() throws Exception {
		Script script = parseHelper.parse("""
					async
					() => f()
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void test_AsyncFunctionDeclaration() throws Exception {
		Script script = parseHelper.parse("""
					async function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), FunctionDeclaration.class);
		assertTrue("Function must be async", ((FunctionDeclaration) script.getScriptElements().get(0)).isAsync());
	}

	@Test
	public void test_AsyncFunctionDeclarationWithAnnotation() throws Exception {
		Script script = parseHelper.parse("""
					@Someanno async function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), FunctionDeclaration.class);
		assertTrue("Function must be async", ((FunctionDeclaration) script.getScriptElements().get(0)).isAsync());
	}

	@Test
	public void test_AsyncMethodDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					class C {
						async foo() {}
						bar() {}
						@Final async foo1() {}
						@Final bar1() {}
					}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), N4ClassDeclaration.class);
		N4ClassDeclaration classDecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertInstances(classDecl.getOwnedMembers(), N4MethodDeclaration.class, N4MethodDeclaration.class,
				N4MethodDeclaration.class, N4MethodDeclaration.class);

	}

	@Test
	@Ignore // invalid test expectation
	public void test_AsyncMethodDeclarationErrorDueToTrailingEOL() throws Exception {
		Script script = parseHelper.parse("""
					class C {
						async
						foo() {}

					}
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void test_NonAsyncFunctionDeclaration() throws Exception {
		Script script = parseHelper.parse("""
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), FunctionDeclaration.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(0)).isAsync());
	}

	@Test
	public void test_Async_WithLineBreak() throws Exception {
		Script script = parseHelper.parse("""
					async
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				IdentifierRef.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_Async_WithLineBreaky() throws Exception {
		Script script = parseHelper.parse("""
					asyncx
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				IdentifierRef.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_IdentifierAsync() throws Exception {
		Script script = parseHelper.parse("""
					async

				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				IdentifierRef.class);
	}

	@Test
	public void test_IdentifierAsync_AndOthers() throws Exception {
		Script script = parseHelper.parse("""
					async
					someVar
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, ExpressionStatement.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				IdentifierRef.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(1)).getExpression(),
				IdentifierRef.class);
	}

	@Test
	public void test_IdentifierOnlyOthers() throws Exception {
		Script script = parseHelper.parse("""
					asyncOther
					someVar
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, ExpressionStatement.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				IdentifierRef.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(1)).getExpression(),
				IdentifierRef.class);
	}

	@Test
	public void test_AsyncFctExpr() throws Exception {
		Script script = parseHelper.parse("""
					(async function foo(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertFExprAsync(script.getScriptElements().get(0), true, false);
	}

	@Test
	public void test_AsyncGenExpr1() throws Exception {
		Script script = parseESSuccessfully("""
					(async function* foo(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertFExprAsync(script.getScriptElements().get(0), true, true);
	}

	@Test
	public void test_AsyncGenExpr2() throws Exception {
		Script script = parseESSuccessfully("""
					(async function*(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertFExprAsync(script.getScriptElements().get(0), true, true);
	}

	@Test
	public void test_AsyncGenExpr3() throws Exception {
		Script script = parseESSuccessfully("""
					(async function* <T> (syncIterable: Iterable<T>): T {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		FunctionExpression fe = assertFExprAsync(script.getScriptElements().get(0), true, true);
		assertEquals(1, fe.getTypeVars().size());
		assertEquals("T", fe.getTypeVars().get(0).getName());
	}

	@Test
	public void test_FctExpr() throws Exception {
		Script script = parseHelper.parse("""
					(function foo(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertFExprAsync(script.getScriptElements().get(0), false, false);
	}

	@Test
	public void test_AsyncFctExpr_WithLineBreak() throws Exception {
		Script script = parseHelper.parse("""
					async
					(function foo(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				ParameterizedCallExpression.class);
	}

	@Test
	public void test_AsyncFctExpr_WithLineBreak1() throws Exception {
		Script script = parseHelper.parse("""
					asyncCall
					(function foo(): void {})
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class);
		assertInstanceOf(((ExpressionStatement) script.getScriptElements().get(0)).getExpression(),
				ParameterizedCallExpression.class);
	}

	private FunctionExpression assertFExprAsync(ScriptElement stmt, boolean expectedAsync, boolean expectedGenerator)
			throws Exception {
		assertInstanceOf(stmt, ExpressionStatement.class);
		Expression expr = ((ExpressionStatement) stmt).getExpression();
		assertInstanceOf(expr, ParenExpression.class);
		Expression fexpr = ((ParenExpression) expr).getExpression();
		assertInstanceOf(fexpr, FunctionExpression.class);
		assertEquals(expectedAsync, ((FunctionExpression) fexpr).isAsync());
		assertEquals(expectedGenerator, ((FunctionExpression) fexpr).isGenerator());
		return (FunctionExpression) fexpr;
	}

	@Test
	public void test_ExpressionWithAsync_WithLineBreak() throws Exception {
		Script script = parseHelper.parse("""
					someVar + async
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_ExpressionWithAsyncAndVar_WithLineBreak() throws Exception {
		Script script = parseHelper.parse("""
					async + someVar
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_SomeVarName_WithLineBreak() throws Exception {
		Script script = parseHelper.parse("""
					somveVar
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_Async_WithSemi() throws Exception {
		Script script = parseHelper.parse("""
					async;
					function foo(): void {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), ExpressionStatement.class, FunctionDeclaration.class);
		assertFalse("Function must not be async", ((FunctionDeclaration) script.getScriptElements().get(1)).isAsync());
	}

	@Test
	public void test_VariableNamedAsync() throws Exception {
		Script script = parseHelper.parse("""
					var async = 1
					async
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertInstances(script.getScriptElements(), VariableStatement.class, ExpressionStatement.class);
		assertTrue(script.getScriptElements().get(1).getClass().getSimpleName(),
				((ExpressionStatement) script.getScriptElements().get(1)).getExpression() instanceof IdentifierRef);

	}

	private void assertInstanceOf(Object obj, Class<?> type) throws Exception {
		assertTrue(
				"Expected type " + type.getSimpleName() + " but was "
						+ (obj == null ? "null" : obj.getClass().getSimpleName()),
				type.isInstance(obj));
	}

	private void assertInstances(Iterable<? extends Object> objs, Class<?>... types) throws Exception {
		var i = 0;
		for (Object obj : objs) {
			if (i >= types.length) {
				fail("Expected " + types.length + " elements, got " + Iterables.size(objs));
			}
			assertTrue("Expected type " + types[i].getSimpleName() + " but was " + obj.getClass().getSimpleName(),
					types[i].isInstance(obj));
			i++;
		}
		if (i < types.length) {
			fail("Expected " + types.length + " elements, got " + Iterables.size(objs));
		}

	}

}
