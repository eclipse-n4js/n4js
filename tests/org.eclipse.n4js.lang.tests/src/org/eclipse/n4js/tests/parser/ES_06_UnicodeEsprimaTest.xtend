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

import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import java.nio.charset.Charset
import org.junit.Test

class ES_06_UnicodeEsprimaTest extends AbstractParserTest {

	@Test
	def void testDefaultEncoding() {
		assertEquals('UTF-8', Charset.defaultCharset.displayName)
	}

	@Test
	def void testUnicodeIdentifier_01() {
		val script = '日本語 = []'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('日本語', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testUnicodeIdentifier_02() {
		val script = 'T\u203F = []'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('T\u203F', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testUnicodeIdentifier_03() {
		val script = 'T\u200C = []'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('T\u200C', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testUnicodeIdentifier_04() {
		val script = 'T\u200D = []'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('T\u200D', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testUnicodeIdentifier_05() {
		val script = '\u2163\u2161 = []'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('\u2163\u2161', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testUnicodeIdentifier_06() {
		val script = '\u2163\u2161\u200A=\u2009[]'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('\u2163\u2161', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}
}
