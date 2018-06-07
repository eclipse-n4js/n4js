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

import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.MultiplicativeOperator
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.junit.Test

class ES_12_09_ReturnStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testReturn_SimpleASI() {
		val script = '(function(){ return })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement
		assertNull(returnStmt.expression)
	}

	@Test
	def void testReturn_Simple() {
		val script = '(function(){ return; })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement
		assertNull(returnStmt.expression)
	}

	@Test
	def void testReturn_WithArgument() {
		val script = '(function(){ return x; })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement
		val identifier = returnStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)
	}


	@Test
	def void testReturn_WithArgumentASI() {
		val script = '(function(){ return x })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement
		val identifier = returnStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)
	}

	@Test
	def void testReturn_WithArgumentASI2() {
		val script = '(function(){ return \n x*y })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(2, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement
		assertNull(returnStmt.expression)
	}

	@Test
	def void testReturn_WithArgumentMult() {
		val script = '(function(){ return x * y })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement

		val expression = returnStmt.expression as MultiplicativeExpression
		val op = expression.op
		assertEquals(MultiplicativeOperator.TIMES, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testReturn_WithArgumentMultASI() {
		val script = '(function(){ return x\n * y })'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		val parenExpr = statement.expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		val block = funcExpr.body
		assertEquals(1, block.statements.size)
		val returnStmt = block.statements.get(0) as ReturnStatement

		val expression = returnStmt.expression as MultiplicativeExpression
		val op = expression.op
		assertEquals(MultiplicativeOperator.TIMES, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

}
