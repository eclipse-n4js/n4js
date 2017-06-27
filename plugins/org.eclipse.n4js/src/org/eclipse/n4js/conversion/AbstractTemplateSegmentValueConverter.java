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
package org.eclipse.n4js.conversion;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import org.eclipse.n4js.validation.IssueCodes;

/**
 */
public abstract class AbstractTemplateSegmentValueConverter extends AbstractN4JSStringValueConverter {

	@Override
	protected void appendToJSString(char aChar, boolean useUnicode, StringBuilder result) {
		result.append(aChar);
	}

	/**
	 * Asserts that the given value is a valid raw template string, e.g. it does not contain the '`' character or '${'
	 * sequence.
	 */
	@Override
	protected void assertValidValue(String value) {
		super.assertValidValue(value);
		for (int i = 1; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '`') {
				throw invalidRawTemplateString(value);
			} else if (c == '$') {
				if (i + 1 < value.length() && value.charAt(i + 1) == '{') {
					throw invalidRawTemplateString(value);
				}
			}
		}
	}

	/**
	 * Creates a new {@link ValueConverterException} because the given value is not a valid raw template string, e.g. it
	 * contains the '`' character or '${' sequence.
	 */
	private ValueConverterException invalidRawTemplateString(String value) {
		return new ValueConverterException("Cannot express the value '" + value + "' as a raw template string.",
				null, null);
	}

	/**
	 * See https://people.mozilla.org/~jorendorff/es6-draft.html#sec-static-semantics-tv-s-and-trv-s
	 */
	@Override
	public String convertFromJSString(String jsString, INode node, boolean validate) {
		char[] in = jsString.toCharArray();
		int off = 0;
		int len = jsString.length();
		char aChar;
		char[] out = new char[len];
		int outLen = 0;
		int end = off + len;
		boolean warn = false;
		boolean error = false;
		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				out[outLen++] = aChar;
				if (off < end) {
					aChar = in[off];
					switch (aChar) {
					case 'u': {
						out[outLen++] = aChar;
						off++;
						// Validate the escape sequence but write verbatim
						if (!HexChars.isHexSequence(in, off, 4)) {
							if (off < len && in[off] == '{') {
								int mv = 0;
								int hex = off + 1;
								loop: for (; hex < len; hex++) {
									char nextChar = in[hex];
									switch (nextChar) {
									case '0':
									case '1':
									case '2':
									case '3':
									case '4':
									case '5':
									case '6':
									case '7':
									case '8':
									case '9':
										mv = (mv << 4) + nextChar - '0';
										if (!Character.isValidCodePoint(mv)) {
											error = true;
											break loop;
										}
										break;
									case 'a':
									case 'b':
									case 'c':
									case 'd':
									case 'e':
									case 'f':
										mv = (mv << 4) + 10 + nextChar - 'a';
										if (!Character.isValidCodePoint(mv)) {
											error = true;
											break loop;
										}
										break;
									case 'A':
									case 'B':
									case 'C':
									case 'D':
									case 'E':
									case 'F':
										mv = (mv << 4) + 10 + nextChar - 'A';
										if (!Character.isValidCodePoint(mv)) {
											error = true;
											break loop;
										}
										break;
									case '}':
										if (hex == off + 1) {
											error = true;
										}
										break loop;
									default:
										error = true;
										break loop;
									}
								}
								if (hex == len) {
									error = true;
								}
								break;
							}
							error = true;
						}
						break;
					}
					case 'x': {
						out[outLen++] = aChar;
						off++;
						// Validate the escape sequence but write verbatim
						if (!HexChars.isHexSequence(in, off, 2)) {
							error = true;
						}
						break;
					}
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9': {
						out[outLen++] = aChar;
						off++;
						warn = true;
						break;
					}
					case '0':
						out[outLen++] = aChar;
						off++;
						warn = true;
						if (isOctal(in, off)) {
							error = true;
						}
						break;
					}
				}
			} else if (aChar == '\r') {
				out[outLen++] = '\n';
				if (off < end && in[off] == '\n') {
					off++;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		if (validate) {
			if (error) {
				throw new BadEscapementException(IssueCodes.getMessageForVCO_STRING_BAD_ESCAP_ERROR(),
						IssueCodes.VCO_STRING_BAD_ESCAP_ERROR, node, new String(out, 0, outLen), true);
			}
			if (warn) {
				throw new BadEscapementException(IssueCodes.getMessageForVCO_STRING_BAD_ESCAP_WARN(),
						IssueCodes.VCO_STRING_BAD_ESCAP_WARN, node, new String(out, 0, outLen), false);
			}
		}
		return new String(out, 0, outLen);
	}

	private static boolean isOctal(char[] in, int off) {
		// not enough chars available - return false
		if (off >= in.length)
			return false;
		return in[off] >= '0' && in[off] <= '7';
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			int len = string.length();
			int leftLen = getLeftDelimiter().length();
			if (len == leftLen) {
				throw newN4JSValueConverterException(node, "");
			}
			int lastIdx = len - getRightDelimiter().length();
			if (string.startsWith(getLeftDelimiter()) && string.endsWith(getRightDelimiter())) {
				int minLen = leftLen + getRightDelimiter().length();
				if (len > minLen) {
					if (string.charAt(lastIdx - 1) == '\\'
							&& (len == minLen + 1 || string.charAt(lastIdx - 2) != '\\')) {
						String value = convertFromJSString(string.substring(leftLen), node, false);
						throw newN4JSValueConverterException(node, value);
					}
				}
				return convertFromJSString(string.substring(leftLen, lastIdx), node, true);
			} else {
				String value = convertFromJSString(string.substring(leftLen), node, false);
				throw newN4JSValueConverterException(node, value);
			}
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Creates a new value converter exception.
	 */
	protected abstract N4JSValueConverterWithValueException newN4JSValueConverterException(INode node,
			String value);

}
