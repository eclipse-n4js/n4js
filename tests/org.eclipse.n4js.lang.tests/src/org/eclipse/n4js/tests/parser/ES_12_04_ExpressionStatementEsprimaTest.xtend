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
import org.junit.Test
import org.eclipse.n4js.n4JS.CommaExpression

class ES_12_04_ExpressionStatementEsprimaTest extends AbstractParserTest {

	@Test
	def void testIdentifierRef_01() {
		val script = 'x'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val x = statement.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testIdentifierRef_02() {
		val script = 'x, y'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val comma = statement.expression as CommaExpression
		assertEquals(2, comma.exprs.size)
		val x = comma.exprs.head as IdentifierRef
		assertEquals('x', x.text)
		val y = comma.exprs.last as IdentifierRef
		assertEquals('y', y.text)
	}

	@Test
	def void testEscapedIdentifierRef_01() {
		val script = 'var a; \\u0061'.parseSuccessfully
		val statement = script.scriptElements.last as ExpressionStatement
		val xRef = statement.expression as IdentifierRef
		assertEquals('\\u0061', xRef.text)
		val id = xRef.id
		assertEquals('a', id.name)
	}

	@Test
	def void testEscapedIdentifierRef_02() {
		val script = 'var aa; a\\u0061'.parseSuccessfully
		val statement = script.scriptElements.last as ExpressionStatement
		val xRef = statement.expression as IdentifierRef
		assertEquals('a\\u0061', xRef.text)
		val id = xRef.id
		assertEquals('aa', id.name)
	}

	@Test
	def void testEscapedIdentifierRef_03() {
		val script = 'var aa; \\u0061a '.parseSuccessfully
		val statement = script.scriptElements.last as ExpressionStatement
		val xRef = statement.expression as IdentifierRef
		assertEquals('\\u0061a', xRef.text)
		val id = xRef.id
		assertEquals('aa', id.name)
	}
}
