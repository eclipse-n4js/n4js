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

import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.AdditiveOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.StringLiteral

class ES_11_06_AdditiveOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testAdd() {
		val program = 'x + y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AdditiveExpression
		val op = expression.op
		assertEquals(AdditiveOperator.ADD, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testSub() {
		val program = 'x - y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AdditiveExpression
		val op = expression.op
		assertEquals(AdditiveOperator.SUB, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testAddString() {
		val program = '"use strict" + y'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AdditiveExpression
		val op = expression.op
		assertEquals(AdditiveOperator.ADD, op)

		val left = expression.lhs as StringLiteral
		assertEquals('"use strict"', left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	/*
	 * Expression is hung up right. That is
	 * <pre>x + y + z:
	 *      +
	 *     / \
	 *    +   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	def void testAddAdd() {
		val program = 'x + y + z'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AdditiveExpression
		val op = expression.op
		val rightTop = expression.rhs as IdentifierRef
		assertEquals('z', rightTop.text)
		assertEquals(AdditiveOperator.ADD, op)

		val left = expression.lhs as AdditiveExpression
		val leftOp = left.op
		assertEquals(AdditiveOperator.ADD, leftOp)
		val leftBot = left.lhs as IdentifierRef
		assertEquals('x', leftBot.text)
		val rightBot = left.rhs as IdentifierRef
		assertEquals('y', rightBot.text)
	}

}
