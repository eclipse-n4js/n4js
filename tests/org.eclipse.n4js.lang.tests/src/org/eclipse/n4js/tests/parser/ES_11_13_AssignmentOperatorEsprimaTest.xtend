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

import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.AssignmentOperator

class ES_11_13_AssignmentOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testAssignment_01() {
		val program = 'x = 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testAssignment_02() {
		val program = 'eval = 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('eval', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testAssignment_03() {
		val program = 'arguments = 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('arguments', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testMultAssignment() {
		val program = 'x *= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.MUL_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testDivAssignment() {
		val program = 'x /= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.DIV_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testModAssignment() {
		val program = 'x %= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.MOD_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testAddAssignment() {
		val program = 'x += 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.ADD_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testSubAssignment() {
		val program = 'x -= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.SUB_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testLeftShiftAssignment() {
		val program = 'x <<= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.SHL_ASSIGN, op)
	}

	@Test
	def void testRightShiftAssignment() {
		val program = 'x >>= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.SHR_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testUnsignedRightShiftAssignment() {
		val program = 'x >>>= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.USHR_ASSIGN, op)
	}

	@Test
	def void testAndAssignment() {
		val program = 'x &= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.AND_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testXorAssignment() {
		val program = 'x ^= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.XOR_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	@Test
	def void testOrAssignment() {
		val program = 'x |= 42'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		assertEquals(AssignmentOperator.OR_ASSIGN, op)

		val left = expression.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = expression.rhs as NumericLiteral
		assertEquals('42', right.text)
	}

	/*
	 * Assignment is hung up left. That is
	 * <pre>x = y = z:
	 *      =
	 *     / \
	 *    x   =
	 *       / \
	 *      y   z
	 * </pre>
	 */
	@Test
	def void testAssignmentAssignment() {
		val program = 'x = y = z'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val expression = statement.expression as AssignmentExpression
		val op = expression.op
		val rightTop = expression.lhs as IdentifierRef
		assertEquals('x', rightTop.text)
		assertEquals(AssignmentOperator.ASSIGN, op)

		val right = expression.rhs as AssignmentExpression
		val rightOp = right.op
		assertEquals(AssignmentOperator.ASSIGN, rightOp)
		val leftBot = right.lhs as IdentifierRef
		assertEquals('y', leftBot.text)
		val rightBot = right.rhs as IdentifierRef
		assertEquals('z', rightBot.text)
	}
}
