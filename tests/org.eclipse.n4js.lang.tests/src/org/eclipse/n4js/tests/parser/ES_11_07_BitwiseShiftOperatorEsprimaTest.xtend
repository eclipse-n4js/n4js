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
import org.eclipse.n4js.n4JS.ShiftExpression
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ShiftOperator

class ES_11_07_BitwiseShiftOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testLeftShift() {
		val program = 'x << y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val shift = statement.expression as ShiftExpression
		val op = shift.op
		assertEquals(ShiftOperator.SHL, op)

		val left = shift.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = shift.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testRightShift() {
		val program = 'x >> y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val shift = statement.expression as ShiftExpression
		val op = shift.op
		assertEquals(ShiftOperator.SHR, op)

		val left = shift.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = shift.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testUnsignedRightShift() {
		val program = 'x >>> y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val shift = statement.expression as ShiftExpression
		val op = shift.op
		assertEquals(ShiftOperator.USHR, op)

		val left = shift.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = shift.rhs as IdentifierRef
		assertEquals('y', right.text)
	}
	
	@Test
	def void testLeftShiftWithTrailingIdentifierRef() {
		val program = '''
			a<<b
			c
		'''.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val shift = statement.expression as ShiftExpression
		val op = shift.op
		assertEquals(ShiftOperator.SHL, op)

		val left = shift.lhs as IdentifierRef
		assertEquals('a', left.text)
		val right = shift.rhs as IdentifierRef
		assertEquals('b', right.text)
	}

}
