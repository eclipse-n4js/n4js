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
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.junit.Test

class ES_11_04_UnaryOperatorsEsprimaTest extends AbstractParserTest {

	@Test
	def void testInc_01() {
		val script = '++x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testInc_02() {
		val script = '++eval'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('eval', x.text)
	}

	@Test
	def void testInc_03() {
		val script = '++arguments'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('arguments', x.text)
	}

	@Test
	def void testDec_01() {
		val script = '--x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.DEC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testDec_02() {
		val script = '--eval'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.DEC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('eval', x.text)
	}

	@Test
	def void testDec_03() {
		val script = '--arguments'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.DEC, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('arguments', x.text)
	}

	@Test
	def void testPos_01() {
		val script = '+x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.POS, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testNeg_01() {
		val script = '-x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.NEG, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testInv_01() {
		val script = '~x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INV, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testNot_01() {
		val script = '!x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.NOT, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testVoid_01() {
		val script = 'void x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.VOID, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testDelete_01() {
		val script = 'delete x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.DELETE, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testTypeof_01() {
		val script = 'typeof x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.TYPEOF, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testEscapeSequenceInTypeof_01() {
		val script = 'type\\u006ff x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.TYPEOF, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testEscapeSequenceInTypeof_02() {
		val script = 'type\\u006Ff x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.TYPEOF, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}
}
