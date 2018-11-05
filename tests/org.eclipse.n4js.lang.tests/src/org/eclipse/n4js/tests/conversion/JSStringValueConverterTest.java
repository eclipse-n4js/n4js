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
package org.eclipse.n4js.tests.conversion;

import org.eclipse.n4js.conversion.AbstractN4JSStringValueConverter;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class JSStringValueConverterTest extends Assert {

	public void assertConversion(String expected, String input) {
		String result = AbstractN4JSStringValueConverter.convertFromN4JSString(input, null, false);
		assertEquals(expected, result);
	}

	@Test
	public void testSimple() {
		assertConversion("Hello", "Hello");
	}

	@Test
	public void testEscapeSequences() {
		assertConversion("\n\r\t\u000B\b\f\\\'\"\u0000", "\\n\\r\\t\\v\\b\\f\\\\\\'\\\"\\0");
	}

	@Test
	public void testUnicode() {
		assertConversion("a", "\\u0061");
		assertConversion("a", "\\u{0061}");
		assertConversion("a", "\\u{61}");
		assertConversion("\uD87E\uDC04", "\\u{2F804}");
	}

	@Test
	public void testHex() {
		assertConversion("a", "\\x61");
	}

	@Test
	public void testIncompleteUnicode() {
		assertConversion("u00", "\\u00");
		assertConversion("u00ZZ", "\\u00ZZ");
		assertConversion("u{00ZZ", "\\u{00ZZ");
		assertConversion("u{}", "\\u{}");
	}

	@Test
	public void testInvalidUnicode() {
		assertConversion("u{110000}", "\\u{110000}");
	}

	@Test
	public void testIncompleteHex() {
		assertConversion("xt", "\\xt");
		assertConversion("x0g", "\\x0g");
	}

	@Test
	public void testLineBreak() {
		assertConversion("Hello\r\nworld", "Hello\\r\\nworld");
		assertConversion("Hello\nworld", "Hello\\nworld");
	}

	@Test
	public void testLineContinuation() {
		assertConversion("Helloworld", "Hello\\\nworld");
		assertConversion("Helloworld", "Hello\\\r\nworld");
	}

	@Test
	public void testOctalDigitEscape() {
		assertConversion("Hello\u0002world", "Hello\\02world");
		assertConversion("Hello\nworld", "Hello\\012world");
		assertConversion("Hello\u00008world", "Hello\\08world");
		assertConversion("Hello\u0002world", "Hello\\002world");
		assertConversion("Hello\u00023world", "Hello\\0023world");
		assertConversion("Hello\n2World", "Hello\\0122World");
	}

	@Test
	public void testInvalidDigitEscape_01() {
		assertConversion("HelloRWorld", "Hello\\122World");
	}

	@Test
	public void testInvalidDigitEscape_02() {
		assertConversion("Hello\u00CAWorld", "Hello\\312World");
	}

	@Test
	public void testInvalidDigitEscape_03() {
		assertConversion("Hello!2World", "Hello\\412World");
	}

	@Test
	public void testInvalidDigitEscape_04() {
		assertConversion("Hello812World", "Hello\\812World");
	}

	@Test
	public void testInvalidDigitEscape_05() {
		assertConversion("Hello92World", "Hello\\712World");
	}

	@Test
	public void testInvalidDigitEscape_06() {
		assertConversion("Hello\u0001World", "Hello\\1World");
	}

	@Test
	public void testInvalidDigitEscape_07() {
		assertConversion("Hello8World", "Hello\\8World");
	}

	@Test
	public void testInvalidDigitEscape_08() {
		assertConversion("Hello 0World", "Hello\\400World");
	}

	@Test
	public void testInvalidDigitEscape_09() {
		assertConversion("Hello\u0007World", "Hello\\7World");
	}

	@Test
	public void testNULEscape() {
		assertConversion("Hello\u0000World", "Hello\\0World");
	}
}
