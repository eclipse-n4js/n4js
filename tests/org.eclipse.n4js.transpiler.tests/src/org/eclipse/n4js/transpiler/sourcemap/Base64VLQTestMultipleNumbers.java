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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for Base64VLQ, multiple numbers in string.
 */
@RunWith(Parameterized.class)
public class Base64VLQTestMultipleNumbers {

	/** Only for beautifying the test fixture */
	private static int[] ints(int... decimals) {
		return decimals;
	}

	/** pairs of base64VLQ and numbers */
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "A", ints(0) },
				{ "DECODEME", ints(-1, 2, 1, 7, -1, 2, 6, 2) },
				{ "CuBwcO", ints(1, 23, 456, 7) },
				{ "AACKA", ints(0, 0, 1, 5, 0) },
				{ "IACIC", ints(4, 0, 1, 4, 1) },
				{ "MACTC", ints(6, 0, 1, -9, 1) },
				{ "IACpB", ints(4, 0, 1, -20) }

		});
	}

	final private int[] decimals;
	final private String base64VLQ;

	/** Setup parameterized test */
	public Base64VLQTestMultipleNumbers(String base64VLQ, int[] decimals) {
		this.base64VLQ = base64VLQ;
		this.decimals = decimals;
	}

	/**
	 * Test method for {@link org.eclipse.n4js.transpiler.sourcemap.Base64VLQ#encode(int[])}.
	 */
	@Test
	public void testEncode() {
		assertEquals(base64VLQ, Base64VLQ.encode(decimals));
	}

	/**
	 *
	 */
	@Test
	public void testDecode() {
		assertArrayEquals(decimals, Base64VLQ.decode(base64VLQ));
	}

	/**
	 * Test en/decoding
	 */
	@Test
	public void testRoundTrip() {
		assertEquals(base64VLQ, Base64VLQ.encode(Base64VLQ.decode(base64VLQ)));
	}

}
