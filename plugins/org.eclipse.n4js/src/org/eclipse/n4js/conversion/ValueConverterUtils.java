/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.util.Strings
 *	in bundle org.eclipse.xtext.util
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2008, 2017 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.conversion;

import org.eclipse.xtext.util.Strings;

/**
 * Utility methods for value converters.
 */
public final class ValueConverterUtils {

	/**
	 * Converts a string into an escaped string by replacing all special characters with backslash escape sequences.
	 */
	public static String convertToEscapedString(String sourceStr, boolean useUnicode) {
		int len = sourceStr.length();
		StringBuilder result = new StringBuilder(len + 20);
		int off = 0;
		while (off < len) {
			char ch = sourceStr.charAt(off++);
			escapeAndAppendTo(ch, useUnicode, result);
		}
		return result.toString();
	}

	/**
	 * Mostly copied from method {@code org.eclipse.xtext.util.Strings#escapeAndAppendTo(char, boolean, StringBuilder)}
	 * (Xtext version 2.15.0).
	 */
	private static void escapeAndAppendTo(char c, boolean useUnicode, StringBuilder result) {
		String appendMe;
		switch (c) {
		case '\b':
			appendMe = "\\b";
			break;
		case '\t':
			appendMe = "\\t";
			break;
		case '\n':
			appendMe = "\\n";
			break;
		case '\f':
			appendMe = "\\f";
			break;
		case '\r':
			appendMe = "\\r";
			break;
		case '"':
			appendMe = "\\\"";
			break;
		case '\'':
			appendMe = "\\'";
			break;
		case '\\':
			appendMe = "\\\\";
			break;
		// ==== START CHANGES ====
		case '\u000B':
			appendMe = "\\v";
			break;
		case '\u0000':
			appendMe = "\\0";
			break;
		// ==== END CHANGES ====
		default:
			if (useUnicode && mustEncodeAsEscapeSequence(c)) {
				result.append("\\u");
				for (int i = 12; i >= 0; i -= 4) {
					result.append(Strings.toHex((c >> i) & 0xF));
				}
			} else {
				result.append(c);
			}
			return;
		}
		result.append(appendMe);
	}

	private static boolean mustEncodeAsEscapeSequence(char next) {
		return next < 0x0020 || next > 0x007e;
	}

	/**
	 * Converts a string that may contain backslash escape sequences into an unescaped string, by decoding all escape
	 * sequences.
	 *
	 * @param sourceStr
	 *            the source string to convert. May contain escape sequences introduced by a backslash.
	 * @param allowStringEscSeq
	 *            if <code>true</code>, then all escape sequences supported by N4JS string literals are allowed; if
	 *            <code>false</code>, then only {@code \\u} and {@code \\x} are allowed. The latter mode is useful for
	 *            decoding identifiers.
	 * @param keepBackSlashForUnknownEscSeq
	 *            if <code>true</code>, then the backslash will be copied over to the destination string in case of
	 *            unknown escape sequences.
	 * @param errorForUnknownEscSeq
	 *            if <code>true</code>, then an error will be reported for unknown escape sequences.
	 * @param validityChecker
	 *            if non-<code>null</code>, validity of each character added to the destination string (after decoding
	 *            escape sequences) will be checked with this predicate; otherwise no such validity checks will be
	 *            performed.
	 * @return the unescaped string in form of a {@link StringConverterResult}, possibly including error information.
	 */
	public static StringConverterResult convertFromEscapedString(String sourceStr, boolean allowStringEscSeq,
			boolean keepBackSlashForUnknownEscSeq, boolean errorForUnknownEscSeq,
			CharacterValidityChecker validityChecker) {
		int len = sourceStr.length();
		StringConverterResult result = new StringConverterResult(validityChecker, len);
		int off = 0;
		while (off < len) {
			char ch = sourceStr.charAt(off++);
			if (ch == '\\') {
				if (off < len) {
					off = unescape(sourceStr, off, allowStringEscSeq, keepBackSlashForUnknownEscSeq,
							errorForUnknownEscSeq, result);
				} else {
					// backslash at end of input string
					result.errorAt(off);
					if (keepBackSlashForUnknownEscSeq) {
						result.append('\\', off);
					}
				}
			} else if (ch == '\r') {
				// convert CR to LF:
				result.append('\n', off);
				// in a CR/LF sequence, ignore the LF directly following a CR:
				if (off < len && sourceStr.charAt(off) == '\n') {
					off++;
				}
			} else {
				result.append(ch, off);
			}
		}
		result.seal();
		return result;
	}

	/**
	 * @param off
	 *            should point to the control character, i.e. the character <em>following</em> the backslash.
	 * @return the updated offset.
	 */
	private static int unescape(String str, int off, boolean allowStringEscSeq, boolean keepBackSlashForUnknownEscSeq,
			boolean errorForUnknownEscSeq, StringConverterResult result) {

		int offNew;
		char ch = str.charAt(off);
		if (ch == 'u' || (allowStringEscSeq && ch == 'x')) {
			offNew = unescapeUnicodeSequence(str, off, result);
		} else if (allowStringEscSeq && ch >= '0' && ch <= '9') {
			offNew = unescapeOctalSequence(str, off, result);
		} else if (allowStringEscSeq) {
			offNew = unescapeSimpleControlChar(str, off, result);
		} else {
			offNew = off;
		}

		if (offNew == off) {
			// offset unchanged => unknown/unhandled control character
			offNew++; // consume 'ch'
			if (errorForUnknownEscSeq) {
				result.errorAt(off);
			}
			if (keepBackSlashForUnknownEscSeq) {
				result.append('\\', off);
			}
			result.append(ch, off);
		}
		return offNew;
	}

	/**
	 * @param off
	 *            should point to the control character, in this case either 'u' or 'x'.
	 * @return the updated offset.
	 */
	private static int unescapeUnicodeSequence(String str, int off, StringConverterResult result) {

		int len = str.length();
		boolean modeU = str.charAt(off) != 'x';

		// decode code point, i.e. 2 or 4 or arbitrary number of hex digits (depending on mode)
		int start = off + 1;
		int end, offNext;
		if (modeU && start < len && str.charAt(start) == '{') {
			// we have a sequence of the form "\\u{ddd}", allowing an arbitrary number of hex digits
			start++;
			end = str.indexOf('}', start);
			if (end - start <= 0) {
				// empty sequence (i.e. "\\u{}"), or closing '}' not found
				result.errorAt(off);
				return off;
			}
			offNext = end + 1; // +1 to skip the closing '}'
		} else {
			// we have a sequence with a fixed number of hex digits (2 or 4, depending on mode)
			end = start + (modeU ? 4 : 2);
			offNext = end;
		}
		int codePoint;
		try {
			codePoint = Integer.parseInt(str.substring(start, end), 16);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			result.errorAt(off);
			return off;
		}

		// append character
		if (!Character.isValidCodePoint(codePoint)) {
			result.errorAt(off);
			return off;
		} else {
			result.appendCodePoint(codePoint, off);
		}
		return offNext;
	}

	/**
	 * @param off
	 *            should point to the control character, in this case the first {@link #isOctalDigit(char) octal digit}
	 *            following the backslash.
	 * @return the updated offset.
	 */
	private static int unescapeOctalSequence(String str, int off, StringConverterResult result) {
		int len = str.length();

		int maxDigits = 2;
		char firstChar = str.charAt(off);
		if (firstChar != '0') {
			result.warningAt(off);
			if (firstChar >= '8' && firstChar <= '9') {
				return off;
			}
		}
		if (firstChar >= '0' && firstChar <= '3') {
			maxDigits++;
		}

		// decode code point, i.e. up to 'maxDigits' octal digits following the backslash
		int start = off;
		int end = off + 1;
		while (end < len && end - start < maxDigits && isOctalDigit(str.charAt(end))) {
			end++;
		}
		int codePoint;
		try {
			codePoint = Integer.parseInt(str.substring(start, end), 8);
		} catch (NumberFormatException e) {
			result.errorAt(off);
			return off;
		}

		// append character
		if (!Character.isValidCodePoint(codePoint)) {
			result.errorAt(off);
			return off;
		} else {
			result.appendCodePoint(codePoint, off);
		}

		return end;
	}

	private static boolean isOctalDigit(char ch) {
		return ch >= '0' && ch <= '7';
	}

	/**
	 * @param off
	 *            should point to the control character, i.e. the character <em>following</em> the backslash.
	 * @return the updated offset.
	 */
	private static int unescapeSimpleControlChar(String str, int off, StringConverterResult result) {
		char ch = str.charAt(off++);
		switch (ch) {
		case 't':
			result.append('\t', off);
			return off;
		case 'r':
			result.append('\r', off);
			return off;
		case 'n':
			result.append('\n', off);
			return off;
		case 'f':
			result.append('\f', off);
			return off;
		case 'b':
			result.append('\b', off);
			return off;
		case 'v':
			result.append('\u000B', off);
			return off;
		case '"':
			result.append('"', off);
			return off;
		case '\'':
			result.append('\'', off);
			return off;
		case '\r':
			// simply ignore this, but also ignore a LF that directly follows a CR:
			// (line continuation on Windows, i.e. a CR/LF sequence that is escaped with a '\')
			if (off < str.length() && str.charAt(off) == '\n') {
				off++;
			}
			return off;
		case '\n':
		case '\u2028':
		case '\u2029':
			// simply ignore these
			return off;
		default:
			// unknown control character
			return off - 1;
		}
	}

	/** Interface for checking the validity of a single character. */
	@FunctionalInterface
	public interface CharacterValidityChecker {
		/**
		 * Tells if the given character is valid.
		 *
		 * @param ch
		 *            the character to check.
		 * @param offset
		 *            the offset in the destination string (i.e. the converted value), not the encoded source string.
		 */
		boolean isValid(char ch, int offset);
	}

	/** Result of converting a string literal to its value. */
	public static class StringConverterResult {

		private CharacterValidityChecker validityChecker;
		private StringBuilder builder;

		private String value = null; // will be set by #seal()
		private int invalidCharOff = -1;
		private int warningOff = -1;
		private int errorOff = -1;

		private StringConverterResult(CharacterValidityChecker validityChecker, int capacity) {
			this.validityChecker = validityChecker;
			this.builder = new StringBuilder(capacity);
		}

		/**
		 * Returns the converted string. In case of invalid strings or errors, the returned string may be empty or may
		 * contain only a partial result.
		 */
		public String getValue() {
			return value;
		}

		/** Tells if an invalid character was encountered in the source string. */
		public boolean hasInvalidChar() {
			return invalidCharOff >= 0;
		}

		/** Tells if this result has a warning. */
		public boolean hasWarning() {
			return warningOff >= 0;
		}

		/** Tells if this result has an error. */
		public boolean hasError() {
			return errorOff >= 0;
		}

		/** Returns offset of first invalid character in source string or -1 if all characters were valid. */
		public int getInvalidCharOffset() {
			return invalidCharOff;
		}

		/** Returns offset of first warning in source string or -1 if no warning present. */
		public int getWarningOffset() {
			return warningOff;
		}

		/** Returns offset of first error in source string or -1 if no error present. */
		public int getErrorOffset() {
			return errorOff;
		}

		private void append(char ch, int srcOffset) {
			if (validityChecker == null || validityChecker.isValid(ch, builder.length())) {
				builder.append(ch);
			} else {
				invalidCharAt(srcOffset);
			}
		}

		private void appendCodePoint(int codePoint, int srcOffset) {
			if (validityChecker == null || (Character.isBmpCodePoint(codePoint)
					&& validityChecker.isValid((char) codePoint, builder.length()))) {
				builder.appendCodePoint(codePoint);
			} else {
				invalidCharAt(srcOffset);
			}
		}

		private void seal() {
			value = builder.toString();
			builder = null;
			validityChecker = null;
		}

		private void invalidCharAt(int offset) {
			if (invalidCharOff < 0) {
				invalidCharOff = offset;
			}
		}

		private void warningAt(int offset) {
			if (warningOff < 0) {
				warningOff = offset;
			}
		}

		private void errorAt(int offset) {
			if (errorOff < 0) {
				errorOff = offset;
			}
		}
	}

	private ValueConverterUtils() {
	}
}
