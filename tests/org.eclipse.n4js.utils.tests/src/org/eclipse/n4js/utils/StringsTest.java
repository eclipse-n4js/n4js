/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for class {@link Strings}.
 */
public class StringsTest {

	
	@Test
	public void testEscapeNonPrintable() {
		assertEquals("", Strings.escapeNonPrintable(""));
		assertEquals("hello world", Strings.escapeNonPrintable("hello world"));
		assertEquals("hello\\tworld", Strings.escapeNonPrintable("hello\tworld"));
		assertEquals("hello\\tworld", Strings.escapeNonPrintable("hello\u0009world"));
		assertEquals("hello\\nworld", Strings.escapeNonPrintable("hello\nworld"));
		assertEquals("hello \\\"world\\\"", Strings.escapeNonPrintable("hello \"world\""));
		assertEquals("\\u0007", Strings.escapeNonPrintable("\u0007"));
	}
}
