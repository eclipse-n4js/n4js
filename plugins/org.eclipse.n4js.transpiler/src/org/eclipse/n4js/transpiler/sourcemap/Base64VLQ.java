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

/**
 * Encodes or decodes numbers to and from Base64VLQ as used in source code maps.
 *
 * Since we assume that all source code map related numbers fit into integers, the algorithm can only convert to and
 * from integers. Internally it is using long to avoid weird behavior in case of overflows. Note that the source maps
 * spec also restricts values to 32 bit quantities.
 *
 * <h3>VLQ format</h3> A Base64VLQ string strong may contain multiple numbers. The string is read from left to right,
 * each character is converted to a byte using the Base64 character set (A-Z, a-z, 0-9, +, /). The bits of a byte a
 * interpreted as follows:
 *
 * <ul>
 * <li>Bit 1: if first part of the number the sign, part of the number otherwise
 * <li>Bit 5-2: (part of) the number
 * <li>Bit 6: continuous bit
 * </ul>
 *
 * If the continuous bit is set, the succeeding lowing byte is interpreted as additional digits of the number. Otherwise
 * succeeding characters define more numbers.
 *
 * @see <a href="https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k/edit">Source Map
 *      Revision 3 Proposal</a>
 * @see <a href="https://en.wikipedia.org/wiki/Variable-length_quantity">Wikipedia: Variable-length quantity</a>
 * @see <a href=
 *      "https://blogs.msdn.microsoft.com/davidni/2016/03/14/source-maps-under-the-hood-vlq-base64-and-yoda/">MSDN Blog:
 *      Source Maps under the hood – VLQ, Base64 and Yoda</a>
 */
final public class Base64VLQ {

	private static final char[] BASE64_CHARSET = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};

	/**
	 * Returns the given (signed) integers as base64VLQ encoded string.
	 */
	public static String encode(int... decimals) {
		StringBuilder base64EncodedBytes = new StringBuilder();
		int numIndex = 0;
		while (numIndex < decimals.length) {
			long n = decimals[numIndex++];
			byte sign = (n < 0) ? (byte) 1 : 0;
			n = Math.abs(n);
			// first byte contains sign, thus we have only 4 bytes for information
			byte b = (byte) (((n & 0b1111) << 1) | sign);
			n >>= 4;
			while (n > 0) {
				b |= 0b100000; // set continuation bit
				base64EncodedBytes.append(byteToBase64(b));
				b = (byte) (n & 0b11111);
				n >>= 5;
			}
			base64EncodedBytes.append(byteToBase64(b));
		}
		return base64EncodedBytes.toString();

	}

	/**
	 * Returns the given base64VLQ as an array of integers. Throws IllegalArgumentException if input leads to problems.
	 */
	public static int[] decode(CharSequence s) {
		if (s == null || s.length() == 0)
			return new int[] {};

		final int stringLength = s.length();

		int numCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = s.charAt(i);
			byte b = base64ToByte(c);
			if ((b & 0b100000) == 0) {
				numCount++;
			}
		}

		int[] integers = new int[numCount];
		int charIndex = 0;
		int numIndex = 0;
		while (charIndex < stringLength) {
			char c = s.charAt(charIndex++);
			byte b = base64ToByte(c);
			boolean sign = (byte) (b & 1) > 0;
			long n = (b & 0b11110) >> 1;
			byte shift = 4; // first segment has 4 bits for number
			while ((b & 0b100000) > 0) {
				if (charIndex >= stringLength) {
					throw new IllegalArgumentException("Continuous bit set at last character of string");
				}
				c = s.charAt(charIndex++);
				b = base64ToByte(c);
				n += (b & 0b11111) << shift;
				shift += 5; // remaining segments have 5 bits for number
			}
			if (n < Integer.MIN_VALUE || n > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("Number exceeds integer range");
			}
			integers[numIndex++] = (int) (sign ? -n : n);
		}
		return integers;
	}

	static char byteToBase64(byte b) {
		return BASE64_CHARSET[b];
	}

	static byte base64ToByte(char c) {
		if (c >= 'A' && c <= 'Z') {
			return (byte) (c - 'A');
		}
		if (c >= 'a' && c <= 'z') {
			return (byte) (26 + c - 'a');
		}
		if (c >= '0' && c <= '9') {
			return (byte) (2 * 26 + c - '0');
		}
		if (c == '+') {
			return 2 * 26 + 10;
		}
		if (c == '/') {
			return 2 * 26 + 10 + 1;
		}
		throw new IllegalArgumentException("Character '" + c + "' is not not in the BASE64 charset.");
	}
}
