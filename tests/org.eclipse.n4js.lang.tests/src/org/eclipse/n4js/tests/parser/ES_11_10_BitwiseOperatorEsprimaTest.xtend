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
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.junit.Test

class ES_11_10_BitwiseOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testAnd() {
		val program = 'x & y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryBitwiseExpression
		assertNotNull(expression)

		assertEquals(BinaryBitwiseOperator.AND, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testOr() {
		val program = 'x | y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryBitwiseExpression
		assertNotNull(expression)

		assertEquals(BinaryBitwiseOperator.OR, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testXOR() {
		val program = 'x ^ y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryBitwiseExpression
		assertNotNull(expression)

		assertEquals(BinaryBitwiseOperator.XOR, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testXORWithoutSpace() {
		val program = 'x^y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as BinaryBitwiseExpression
		assertNotNull(expression)

		assertEquals(BinaryBitwiseOperator.XOR, expression.op)
		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

}
