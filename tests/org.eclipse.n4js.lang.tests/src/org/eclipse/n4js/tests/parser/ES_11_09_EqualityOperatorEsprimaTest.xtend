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

import org.eclipse.n4js.n4JS.EqualityExpression
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.junit.Test
import org.eclipse.n4js.n4JS.IdentifierRef

class ES_11_09_EqualityOperatorEsprimaTest extends AbstractParserTest {

	@Test
	def void testEquals() {
		val program = 'x == y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val equality = statement.expression as EqualityExpression
		val op = equality.op
		assertEquals(EqualityOperator.EQ, op)

		val left = equality.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = equality.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testEqualsNot() {
		val program = 'x != y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val equality = statement.expression as EqualityExpression
		val op = equality.op
		assertEquals(EqualityOperator.NEQ, op)

		val left = equality.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = equality.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testSame() {
		val program = 'x === y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val equality = statement.expression as EqualityExpression
		val op = equality.op
		assertEquals(EqualityOperator.SAME, op)

		val left = equality.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = equality.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

	@Test
	def void testNotSame() {
		val program = 'x !== y'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val equality = statement.expression as EqualityExpression
		val op = equality.op
		assertEquals(EqualityOperator.NSAME, op)

		val left = equality.lhs as IdentifierRef
		assertEquals('x', left.text)
		val right = equality.rhs as IdentifierRef
		assertEquals('y', right.text)
	}

}
