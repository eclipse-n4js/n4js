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
import org.eclipse.n4js.n4JS.StringLiteral
import org.junit.Test
import org.eclipse.n4js.n4JS.AdditiveExpression

class ES_07_08_4_StringLiteralErrorTest extends AbstractParserTest {

	@Test
	def void testEmpty_01() {
		'""'.parseSuccessfully
	}
	@Test
	def void testEmpty_02() {
		"''".parseSuccessfully
	}

	@Test
	def void testBogusEscape_01() {
		val script = "'\\123'".parseWithWarning
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("S", stringLiteral.value)
	}

	@Test
	def void testBogusEscape_02() {
		val script = "'\\0123'".parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("\n3", stringLiteral.value)
	}

	override parseSuccessfully(CharSequence js) {
		val script = super.parseSuccessfully(js)
		assertTrue(script.eResource.warnings.toString, script.eResource.warnings.empty)
		return script
	}

	def parseWithWarning(CharSequence js) {
		val script = js.parseUnrestricted
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertFalse(script.eResource.warnings.toString, script.eResource.warnings.empty)
		return script
	}

	@Test
	def void testUnclosed_01() {
		val script = "'".parseWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("", stringLiteral.value)
		assertEquals("'", stringLiteral.rawValue)
	}

	@Test
	def void testUnclosed_02() {
		val script = '"'.parseWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("", stringLiteral.value)
	}

	@Test
	def void testUnclosed_03() {
		val script = "'\\'".parseWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("'", stringLiteral.value)
	}

	@Test
	def void testUnclosed_04() {
		val script = '"\\"'.parseWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals('"', stringLiteral.value)
	}

	@Test
	def void testUnclosedWithASI() {
		val script = '''
			"abc
			1+1'''.parseWithError
		val first = script.scriptElements.head as ExpressionStatement
		val stringLiteral = first.expression as StringLiteral
		assertEquals('abc', stringLiteral.value)
		val last = script.scriptElements.last as ExpressionStatement
		val sum = last.expression as AdditiveExpression
		assertEquals("1+1", sum.text)
	}

	@Test
	def void testProperlyClosed_01() {
		val script = "'\\\\'".parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("\\", stringLiteral.value)
	}

	@Test
	def void testProperlyClosed_02() {
		val script = '"\\\\"'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals('\\', stringLiteral.value)
	}

	@Test
	def void testBadEscapement_01() {
		'''"Hello\112World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_02() {
		'''"Hello\212World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_03() {
		'''"Hello\312World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_04() {
		'''"Hello\412World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_05() {
		'''"Hello\512World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_06() {
		'''"Hello\612World"'''.parseWithWarning
	}

	@Test
	def void testBadEscapement_07() {
		'''"Hello\712World"'''.parseWithWarning
	}

}
