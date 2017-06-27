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
import org.eclipse.xtext.util.Strings;

import org.eclipse.n4js.validation.IssueCodes;

/**
 * A value converter that converts N4JSString literals to string values.
 *
 * It is aware of unicode escape sequences and octal escapes..
 */
public class N4JSStringValueConverter extends AbstractN4JSStringValueConverter {

	@Override
	protected String getRightDelimiter() {
		return "\"";
	}

	@Override
	protected String getLeftDelimiter() {
		return "\"";
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			if (string.length() == 1) {
				throw newN4JSValueConverterException(string.charAt(0), node, "");
			}
			char first = string.charAt(0);
			int lastIdx = string.length() - 1;
			if (string.charAt(lastIdx) == first) {
				if (string.length() >= 3) {
					if (string.charAt(lastIdx - 1) == '\\' && string.charAt(lastIdx - 2) != '\\') {
						String value = convertFromJSString(string.substring(1), node, false);
						throw newN4JSValueConverterException(first, node, value);
					}
				}
				return convertFromJSString(string.substring(1, lastIdx), node, true);
			} else {
				String value = convertFromJSString(string.substring(1), node, false);
				throw newN4JSValueConverterException(first, node, value);
			}
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Mostly copied from {@link Strings#convertToJavaString(String, boolean)}
	 */
	@Override
	protected void appendToJSString(char aChar, boolean useUnicode, StringBuilder result) {
		// Handle common case first, selecting largest block that
		// avoids the specials below
		if ((aChar > 61) && (aChar < 127)) {
			if (aChar == '\\') {
				result.append('\\');
				result.append('\\');
				return;
			}
			result.append(aChar);
			return;
		}
		switch (aChar) {
		case ' ':
			result.append(' ');
			break;
		case '\t':
			result.append('\\');
			result.append('t');
			break;
		case '\n':
			result.append('\\');
			result.append('n');
			break;
		case '\r':
			result.append('\\');
			result.append('r');
			break;
		case '\f':
			result.append('\\');
			result.append('f');
			break;
		case '\b':
			result.append('\\');
			result.append('b');
			break;
		case '\u000B':
			result.append('\\');
			result.append('v');
			break;
		case '\u0000':
			result.append('\\');
			result.append('0');
			break;
		case '\'':
			result.append('\\');
			result.append('\'');
			break;
		case '"':
			result.append('\\');
			result.append('"');
			break;
		default:
			if (useUnicode && ((aChar < 0x0020) || (aChar > 0x007e))) {
				result.append('\\');
				result.append('u');
				result.append(Strings.toHex((aChar >> 12) & 0xF));
				result.append(Strings.toHex((aChar >> 8) & 0xF));
				result.append(Strings.toHex((aChar >> 4) & 0xF));
				result.append(Strings.toHex(aChar & 0xF));
			} else {
				result.append(aChar);
			}
		}
	}

	/**
	 * Mostly copied from {@link Strings#convertFromJavaString(String, boolean)}
	 */
	@Override
	public String convertFromJSString(String jsString, INode node, boolean validate) {
		char[] in = jsString.toCharArray();
		int off = 0;
		int len = in.length;
		char aChar;
		char[] out = new char[len];
		int outLen = 0;
		boolean warn = false;
		boolean error = false;
		while (off < len) {
			aChar = in[off++];
			if (aChar == '\\') {
				if (off < len) {
					aChar = in[off++];
					switch (aChar) {
					case 'u': {
						// Read the xxxx
						int value = 0;
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
											out[outLen++] = aChar;
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
											out[outLen++] = aChar;
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
											out[outLen++] = aChar;
											break loop;
										}
										break;
									case '}':
										if (hex == off + 1) {
											error = true;
											out[outLen++] = aChar;
											break loop;
										} else {
											outLen += Character.toChars(mv, out, outLen);
											off = hex + 1;
											break loop;
										}
									default:
										error = true;
										out[outLen++] = aChar;
										break loop;
									}
								}
								if (hex == len) {
									error = true;
									out[outLen++] = aChar;
								}
								break;
							}
							error = true;
							out[outLen++] = aChar;
							break;
						} else {
							for (int i = 0; i < 4; i++) {
								aChar = in[off++];
								switch (aChar) {
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
									value = (value << 4) + aChar - '0';
									break;
								case 'a':
								case 'b':
								case 'c':
								case 'd':
								case 'e':
								case 'f':
									value = (value << 4) + 10 + aChar - 'a';
									break;
								case 'A':
								case 'B':
								case 'C':
								case 'D':
								case 'E':
								case 'F':
									value = (value << 4) + 10 + aChar - 'A';
									break;
								default:
									throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
								}
							}
							out[outLen++] = (char) value;
							break;
						}
					}
					case 'x': {
						// Read the xx
						int value = 0;
						if (!HexChars.isHexSequence(in, off, 2)) {
							out[outLen++] = aChar;
							error = true;
							break;
						} else {
							for (int i = 0; i < 2; i++) {
								aChar = in[off++];
								switch (aChar) {
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
									value = (value << 4) + aChar - '0';
									break;
								case 'a':
								case 'b':
								case 'c':
								case 'd':
								case 'e':
								case 'f':
									value = (value << 4) + 10 + aChar - 'a';
									break;
								case 'A':
								case 'B':
								case 'C':
								case 'D':
								case 'E':
								case 'F':
									value = (value << 4) + 10 + aChar - 'A';
									break;
								default:
									throw new IllegalArgumentException("Malformed \\x00 encoding.");
								}
							}
							out[outLen++] = (char) value;
							break;
						}
					}
					case 't': {
						aChar = '\t';
						out[outLen++] = aChar;
						break;
					}
					case 'r': {
						aChar = '\r';
						out[outLen++] = aChar;
						break;
					}
					case 'n': {
						aChar = '\n';
						out[outLen++] = aChar;
						break;
					}
					case 'f': {
						aChar = '\f';
						out[outLen++] = aChar;
						break;
					}
					case 'b': {
						aChar = '\b';
						out[outLen++] = aChar;
						break;
					}
					case 'v': {
						aChar = '\u000B';
						out[outLen++] = aChar;
						break;
					}
					case '"': {
						aChar = '"';
						out[outLen++] = aChar;
						break;
					}
					case '\'': {
						aChar = '\'';
						out[outLen++] = aChar;
						break;
					}
					case '1':
					case '2':
					case '3':
						warn = true; //$FALL-THROUGH$
					case '0': {
						if (off < len) {
							char next = in[off];
							char value = (char) (aChar - '0');
							if (next >= '0' && next <= '7') {
								value = (char) (value * 8 + (next - '0'));
								off++;
								if (off < len) {
									next = in[off];
									if (next >= '0' && next <= '7') {
										off++;
										value = (char) (value * 8 + (next - '0'));
									}
								}
								out[outLen++] = value;
								break;
							}
						}
						aChar = (char) (aChar - '0');
						out[outLen++] = aChar;
						break;
					}
					case '4':
					case '5':
					case '6':
					case '7': {
						warn = true;
						if (off < len) {
							char next = in[off];
							char value = (char) (aChar - '0');
							if (next >= '0' && next <= '7') {
								value = (char) (value * 8 + (next - '0'));
								off++;
								out[outLen++] = value;
								break;
							}
						}
						aChar = (char) (aChar - '0');
						out[outLen++] = aChar;
						break;
					}
					case '8':
					case '9': {
						warn = true;
						out[outLen++] = aChar;
						break;
					}
					case '\n':
					case '\u2028':
					case '\u2029':
						break;
					case '\r':
						if (off < len && in[off] == '\n') {
							off++;
						}
						break;
					default: {
						out[outLen++] = aChar;
					}
					}
				} else {
					error = true;
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

	/**
	 * Creates a new value converter exception.
	 */
	protected N4JSValueConverterWithValueException newN4JSValueConverterException(char c, INode node, String value) {
		if (c == '"')
			return new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_STRING_DOUBLE_QUOTE(),
					IssueCodes.VCO_STRING_DOUBLE_QUOTE, node, value, null);
		else
			return new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_STRING_QUOTE(),
					IssueCodes.VCO_STRING_QUOTE, node, value, null);
	}
}
