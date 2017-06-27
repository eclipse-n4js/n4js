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
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.MultiplicativeOperator
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.AdditiveOperator

class ES_11_BinaryExpressionEsprimaTest extends AbstractParserTest {

	/*
	 * Expression is hung up left due to operator precedence. That is
	 * <pre>x + y * z:
	 *      +
	 *     / \
	 *    x   *
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testAddMul() {
		val program = 'x + y * z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as AdditiveExpression
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as MultiplicativeExpression
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}

	/*
	 * Expression is hung up left due to operator precedence. That is
	 * <pre>x + y / z:
	 *      +
	 *     / \
	 *    x   /
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testAddDiv() {
		val program = 'x + y / z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as AdditiveExpression
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as MultiplicativeExpression
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}

	/*
	 * Expression is hung up left due to operator precedence. That is
	 * <pre>x + y % z:
	 *      +
	 *     / \
	 *    x   %
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testAddMod() {
		val program = 'x + y % z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as AdditiveExpression
		assertEquals(AdditiveOperator.ADD, expression.op)
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as MultiplicativeExpression
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}

	/*
	 * Expression is hung up left due to operator precedence. That is
	 * <pre>x + y % z:
	 *      -
	 *     / \
	 *    x   %
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testSubMod() {
		val program = 'x - y % z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement

		val expression = statement.expression as AdditiveExpression
		assertEquals(AdditiveOperator.SUB, expression.op)
		val leftTop = expression.lhs as IdentifierRef
		assertEquals('x', leftTop.text)

		val rightTop = expression.rhs as MultiplicativeExpression
		val leftBot = rightTop.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = rightTop.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}


	/*
	 * Expression is hung up right. That is
	 * <pre>x * y + z:
	 *      +
	 *     / \
	 *    *   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testTimesAdd() {
		val program = 'x * y + z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AdditiveExpression
		val op = expression.op
		assertEquals(AdditiveOperator.ADD, op)

		val left = expression.lhs as MultiplicativeExpression
		val leftOp = left.op
		assertEquals(MultiplicativeOperator.TIMES, leftOp)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)

		val rightTop = expression.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
	}
}
