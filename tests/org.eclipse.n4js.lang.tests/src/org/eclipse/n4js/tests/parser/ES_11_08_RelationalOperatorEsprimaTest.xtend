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
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef

class ES_11_08_RelationalOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testLessThan() {
		val program = 'x < y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.LT, op)

		val left = relational.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = relational.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testGreaterThan() {
		val program = 'x > y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.GT, op)

		val left = relational.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = relational.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testLessThanOrEqual() {
		val program = 'x <= y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.LTE, op)

		val left = relational.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = relational.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testGreaterThanOrEqual() {
		val program = 'x >= y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.GTE, op)
	}

	@Test
	def void testIn() {
		val program = 'x in y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.IN, op)

		val left = relational.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = relational.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testInstanceOf() {
		val program = 'x instanceof y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		assertEquals(RelationalOperator.INSTANCEOF, op)

		val left = relational.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = relational.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	/*
	 * Expression is hung up right. That is
	 * <pre>x < y < z:
	 *      <
	 *     / \
	 *    <   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testLessThanLessThan() {
		val program = 'x < y < z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val relational = statement.expression as RelationalExpression
		val op = relational.op
		val rightTop = relational.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
		assertEquals(RelationalOperator.LT, op)

		val left = relational.lhs as RelationalExpression
		val leftOp = left.op
		assertEquals(RelationalOperator.LT, leftOp)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)
	}
}
