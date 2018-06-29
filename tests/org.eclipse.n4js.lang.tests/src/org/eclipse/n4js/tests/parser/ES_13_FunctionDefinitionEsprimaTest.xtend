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

import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.junit.Test
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.VariableStatement

class ES_13_FunctionDefinitionEsprimaTest extends AbstractParserTest {

	@Test
	def void testFunctionDefinition() {
		val script = 'function hello() { sayHi(); }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionEval() {
		val script = 'function eval() { }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("eval", funcDecl.name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionArguments() {
		val script = 'function arguments() { }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("arguments", funcDecl.name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionWithFpars() {
		val script = 'function test(t, t) { }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("test", funcDecl.name)

		assertEquals(2, funcDecl.fpars.size())
		assertEquals("t", funcDecl.fpars.get(0).name)
		assertEquals("t", funcDecl.fpars.get(1).name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionExpressionWithFpars() {
		val script = '(function test(t, t) { })'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val parenExpr = (script.scriptElements.head as ExpressionStatement).expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		assertEquals("test", funcExpr.name)

		assertEquals(2, funcExpr.fpars.size())
		assertEquals("t", funcExpr.fpars.get(0).name)
		assertEquals("t", funcExpr.fpars.get(1).name)

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionEvalWithInner() {
		val script = 'function eval() { function inner() { "use strict" } }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("eval", funcDecl.name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val innerFunc = block.statements.head as FunctionDeclaration
		assertEquals("inner", innerFunc.name)
		val blockInner = innerFunc.body
		assertEquals(1, blockInner.statements.size())
		assertEquals('"use strict"', ((blockInner.statements.head as ExpressionStatement).expression as StringLiteral).text)
	}

	@Test
	def void testFunctionDefinition1() {
		val script = 'function hello(a) { sayHi(); }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		assertEquals(1, funcDecl.fpars.size())
		assertEquals("a", funcDecl.fpars.get(0).name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinition2() {
		val script = 'function hello(a, b) { sayHi(); }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		assertEquals(2, funcDecl.fpars.size())
		assertEquals("a", funcDecl.fpars.get(0).name)
		assertEquals("b", funcDecl.fpars.get(1).name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionVar() {
		val script = 'var hi = function() { sayHi() };'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionVar2() {
		val script = 'var hi = function eval() { };'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
		assertEquals("eval", funcExpr.name)
	}

	@Test
	def void testFunctionDefinitionVar3() {
		val script = 'var hi = function arguments() { };'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
		assertEquals("arguments", funcExpr.name)
	}


}
