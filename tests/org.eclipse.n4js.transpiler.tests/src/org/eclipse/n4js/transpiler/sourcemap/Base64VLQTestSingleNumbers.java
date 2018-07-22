/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for Base64VLQ
 */
@RunWith(Parameterized.class)
public class Base64VLQTestSingleNumbers {

	/** pairs of decimal and base64VLQ */
	@Parameters(name = "{index}: {0} - {1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				// basic (B = -0)
				{ 0, "A" }, { 1, "C" }, { -1, "D" },
				// continuous
				{ 32, "gC" }, { 33, "iC" }, { 1024, "ggC" }, { -1024, "hgC" },
				// larger numbers
				{ 1000, "w+B" },
				// max integer
				{ 2147483647, "+/////D" },
				// min integer
				{ -2147483648, "hgggggE" },
				{ -2147483647, "//////D" },
				{ 456, "wc" }
		});
	}

	final private int decimal;
	final private String base64VLQ;

	/** Setup parameterized test */
	public Base64VLQTestSingleNumbers(int decimal, String base64VLQ) {
		this.decimal = decimal;
		this.base64VLQ = base64VLQ;
	}

	/**
	 * Test method for {@link org.eclipse.n4js.transpiler.sourcemap.Base64VLQ#encode(int[])}.
	 */
	@Test
	public void testEncode() {
		assertEquals(base64VLQ, Base64VLQ.encode(decimal));
	}

	/**
	 * Test method for {@link org.eclipse.n4js.transpiler.sourcemap.Base64VLQ#decode(java.lang.String)}.
	 */
	@Test
	public void testDecode() {
		assertEquals(decimal, Base64VLQ.decode(base64VLQ)[0]);
	}

}
