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

class ES_11_05_MultiplicativeOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testTimes() {
		val program = 'x * y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as MultiplicativeExpression
		val op = expression.op
		assertEquals(MultiplicativeOperator.TIMES, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testDiv() {
		val program = 'x / y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as MultiplicativeExpression
		val op = expression.op
		assertEquals(MultiplicativeOperator.DIV, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testMod() {
		val program = 'x % y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as MultiplicativeExpression
		val op = expression.op
		assertEquals(MultiplicativeOperator.MOD, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	/*
	 * Expression is hung up right. That is
	 * <pre>x + y + z:
	 *      *
	 *     / \
	 *    *   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testTimesTimes() {
		val program = 'x * y * z'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as MultiplicativeExpression
		val op = expression.op
		val rightTop = expression.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
		assertEquals(MultiplicativeOperator.TIMES, op)

		val left = expression.lhs as MultiplicativeExpression
		val leftOp = left.op
		assertEquals(MultiplicativeOperator.TIMES, leftOp)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)
	}

}
