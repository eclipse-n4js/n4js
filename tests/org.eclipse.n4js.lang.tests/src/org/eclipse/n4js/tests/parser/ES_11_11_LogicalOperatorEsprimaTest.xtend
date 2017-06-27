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

import org.eclipse.n4js.n4JS.BinaryBitwiseExpression
import org.eclipse.n4js.n4JS.BinaryBitwiseOperator
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.BinaryLogicalOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.junit.Test

class ES_11_11_LogicalOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testOr() {
		val program = 'x || y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryLogicalExpression
		assertNotNull(expression)

		assertEquals(BinaryLogicalOperator.OR, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testAnd() {
		val program = 'x && y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryLogicalExpression
		assertNotNull(expression)

		assertEquals(BinaryLogicalOperator.AND, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	/*
	 * Expression is hung up right. That is
	 * <pre>x || y || z:
	 *      ||
	 *     / \
	 *    ||   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testOrOr() {
		val program = 'x || y || z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.OR, expression.op)

		val left = expression.lhs as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.OR, expression.op)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)

		val rightTop = expression.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
	}

	/*
	 * Expression is hung up right. That is
	 * <pre>x && y && z:
	 *      &&
	 *     / \
	 *    &&   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testAndAnd() {
		val program = 'x && y && z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.AND, expression.op)

		val left = expression.lhs as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.AND, left.op)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)

		val rightTop = expression.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
	}

	/*
	 * Expression is hung up left. That is
	 * <pre>x || y && z:
	 *      ||
	 *     / \
	 *    x   &&
	 *        / \
	 *       y   z
	 * </pre>
	 */
	@Test
	def void testOrAnd() {
		val program = 'x || y && z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.OR, expression.op)
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.AND, rightTop.op)
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}

	/*
	 * Expression is hung up left. That is
	 * <pre>x || y ^ z:
	 *      ||
	 *     / \
	 *    x   ^
	 *       / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testOrXor() {
		val program = 'x || y ^ z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as BinaryLogicalExpression
		assertEquals(BinaryLogicalOperator.OR, expression.op)
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as BinaryBitwiseExpression
		assertEquals(BinaryBitwiseOperator.XOR, rightTop.op)
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}

}
