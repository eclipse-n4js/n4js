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

import org.eclipse.n4js.parser.conversion.IdentifierValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.ValueConverterWithValueException;
import org.junit.Assert;
import org.junit.Test;

/**
 */
@SuppressWarnings("javadoc")
public class IdentifierValueConverterTest extends Assert {

	public void assertConversion(String expected, String input) {
		String result = IdentifierValueConverter.convertFromN4JSIdentifier(input, null);
		assertEquals(expected, result);
	}

	public void assertError(String expected, String input) {
		try {
			IdentifierValueConverter.convertFromN4JSIdentifier(input, null);
			fail("expected exception");
		} catch (ValueConverterWithValueException e) {
			String result = (String) e.getValue();
			assertEquals(expected, result);
		}
	}

	public void assertErrorNoResult(String input) {
		try {
			IdentifierValueConverter.convertFromN4JSIdentifier(input, null);
			fail("expected exception");
		} catch (ValueConverterWithValueException e) {
			fail("expected plain value converter exception");
		} catch (ValueConverterException e) {
			// success
		}
	}

	@Test
	public void testSimple() {
		assertConversion("Hello", "Hello");
	}

	@Test
	public void testValidEscapeSequence() {
		assertConversion("aa", "a\\u0061");
	}

	@Test
	public void testInvalidEscapeSequence() {
		assertError("u006", "\\u006");
	}

	@Test
	public void testValidEscapeSequenceBraces() {
		assertConversion("aa", "a\\u{0061}");
	}

	@Test
	public void testValidEscapeSequenceBracesShort() {
		assertConversion("aa", "a\\u{61}");
	}

	@Test
	public void testInvalidEscapeSequenceBracesUnclosed() {
		assertError("u006", "\\u{006");
	}

	@Test
	public void testInvalidEscapeSequenceBracesNoChars() {
		assertError("u", "\\u{}");
	}

	@Test
	public void testInvalidEscapeSequenceBracesInvalidChar() {
		assertError("u110000", "\\u{110000}");
	}

	@Test
	public void testInvalidIdentifierStart() {
		assertErrorNoResult("\\u0030");
	}

	@Test
	public void testInvalidIdentifierPart() {
		assertError("a", "a\\u0020");
	}

	@Test
	public void testDots() {
		assertError("aa", "a\\.a");
	}

	@Test
	public void testUnknownEscapeSequence1() {
		assertError("aBc", "a\\Bc");
	}

	@Test
	public void testUnknownEscapeSequence2() {
		assertError("Abc", "\\Abc");
	}
}
