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

import org.junit.Test;

/**
 * Tests for Base64VLQ.byteToBase64 and Base64VLQ.base64ToByte
 */
public class Base64VLQCharsetTest {

	/**
	 * Test for Base64VLQ.byteToBase64 and Base64VLQ.base64ToByte
	 */
	@Test
	public void byteToCharAndBackAgain() {
		for (byte b = 0; b < 64; b++) {
			char c = Base64VLQ.byteToBase64(b);
			byte back = Base64VLQ.base64ToByte(c);
			assertEquals("Character '" + c + "' not converted correctly", b, back);
		}
	}

}
