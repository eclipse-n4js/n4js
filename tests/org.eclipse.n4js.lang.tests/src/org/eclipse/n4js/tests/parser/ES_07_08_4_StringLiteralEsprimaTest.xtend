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

class ES_07_08_4_StringLiteralEsprimaTest extends AbstractParserTest {

	@Test
	def void testHello() {
		val script = '"Hello"'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello", stringLiteral.value)
	}

	@Test
	def void testEscapeSequences() {
		val script = '''"\n\r\t\v\b\f\\\'\"\0"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("\u000A\u000D\u0009\u000B\u0008\u000C\u005C\u0027\u0022\u0000", stringLiteral.value)
		assertEquals('''"\n\r\t\v\b\f\\\'\"\0"'''.toString, stringLiteral.rawValue)
	}

	@Test
	def void testUnicodeEscapeSequence_01() {
		val script = '''"\u0061"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("a", stringLiteral.value)
	}

	@Test
	def void testUnicodeEscapeSequence_02() {
		val script = '''"\u{0061}"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("a", stringLiteral.value)
	}

	@Test
	def void testUnicodeEscapeSequence_03() {
		val script = '''"\u{61}"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("a", stringLiteral.value)
	}

	@Test
	def void testHexEscapeSequence() {
		val script = '''"\x61"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("a", stringLiteral.value)
	}

	@Test
	def void testIncompleteUnicodeEscapeSequence_01() {
		val script = '''"\u00"'''.parseESWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("u00", stringLiteral.value)
	}

	@Test
	def void testIncompleteUnicodeEscapeSequence_02() {
		val script = '''"\u{00"'''.parseESWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("u{00", stringLiteral.value)
	}

	@Test
	def void testIncompleteUnicodeEscapeSequence_03() {
		val script = '''"\u{}"'''.parseESWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("u{}", stringLiteral.value)
	}

	@Test
	def void testIncompleteHexEscapeSequence() {
		val script = '''"\xt"'''.parseESWithError
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("xt", stringLiteral.value)
	}

	@Test
	def void testLineBreak() {
		val script = '''"Hello\nworld"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\nworld", stringLiteral.value)
	}

	@Test
	def void testLineContinuation_01() {
		val script = '"Hello\\\nworld"'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Helloworld", stringLiteral.value)
	}

	@Test
	def void testLineContinuation_02() {
		val script = '"Hello\\\r\nworld"'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Helloworld", stringLiteral.value)
	}

	@Test
	def void testInvalidContinuation() {
		val script = '''"Hello\02World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u0002World", stringLiteral.value)
	}

	@Test
	def void testOctalSequence() {
		val script = '''"Hello\012World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u000AWorld", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_01() {
		val script = '''"Hello\112World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("HelloJWorld", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_02() {
		val script = '''"Hello\0112World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\t2World", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_03() {
		val script = '''"Hello\312World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u00CAWorld", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_04() {
		val script = '''"Hello\412World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello!2World", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_05() {
		val script = '''"Hello\812World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello812World", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_06() {
		val script = '''"Hello\712World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello92World", stringLiteral.value)
	}

	@Test
	def void testOctalEscape_07() {
		val script = '''"Hello\1World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u0001World", stringLiteral.value)
	}

	@Test
	def void testNULEscape() {
		val script = '''"Hello\0World"'''.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u0000World", stringLiteral.value)
	}


	// NOTE: strict mode is validated in the validation, not in the parser
	@Test
	def void testOctalEscapeStrictMode_01() {
		val script = ''''use strict';"Hello\112World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("HelloJWorld", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_02() {
		val script = ''''use strict';"Hello\0112World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\t2World", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_03() {
		val script = ''''use strict';"Hello\312World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u00CAWorld", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_04() {
		val script = ''''use strict';"Hello\412World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello!2World", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_05() {
		val script = ''''use strict';"Hello\812World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello812World", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_06() {
		val script = ''''use strict';"Hello\712World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello92World", stringLiteral.value)
	}

	@Test
	def void testOctalEscapeStrictMode_07() {
		val script = ''''use strict';"Hello\1World"'''.parseESWithError
		val statement = script.scriptElements.last as ExpressionStatement
		val stringLiteral = statement.expression as StringLiteral
		assertEquals("Hello\u0001World", stringLiteral.value)
	}

}
