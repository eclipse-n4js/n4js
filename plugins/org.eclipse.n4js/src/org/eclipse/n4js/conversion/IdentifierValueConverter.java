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
import org.eclipse.xtext.conversion.impl.IDValueConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.IssueCodes;

/**
 * A value converter that converts N4JSIdentifiers to string values.
 *
 * It is aware of unicode escape sequences.
 */
public class IdentifierValueConverter extends IDValueConverter {

	@Override
	protected String toEscapedString(String value) {
		return convertToJSIdentifier(value, false);
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			String result = convertFromJSIdentifier(string, node);
			return result;
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Mostly copied from {@link Strings#convertFromJavaString(String, boolean)}
	 */
	public static String convertFromJSIdentifier(String identifier, INode node) {
		char[] in = identifier.toCharArray();
		int off = 0;
		int errorOff = 0;
		int badOff = 0;
		int len = in.length;
		char aChar;
		char[] out = new char[len];
		int outLen = 0;
		boolean error = false;
		boolean badChar = false;
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
											errorOff = off;
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
											errorOff = off;
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
											errorOff = off;
											out[outLen++] = aChar;
											break loop;
										}
										break;
									case '}':
										if (hex == off + 1) {
											error = true;
											errorOff = off;
											out[outLen++] = aChar;
											break loop;
										} else {
											outLen += Character.toChars(mv, out, outLen);
											off = hex + 1;
											break loop;
										}
									default:
										error = true;
										errorOff = off;
										out[outLen++] = aChar;
										break loop;
									}
								}
								if (hex == len) {
									error = true;
									errorOff = off;
									out[outLen++] = aChar;
								}
								break;
							}
							error = true;
							errorOff = off;
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
							if (setChar(outLen, out, (char) value)) {
								outLen++;
							} else {
								badChar = true;
								badOff = off;
							}
							break;
						}
					}
					default: {
						error = true;
						errorOff = off;
						if (setChar(outLen, out, aChar)) {
							outLen++;
						} else {
							badChar = true;
							badOff = off;
						}
					}
					}
				} else {
					error = true;
					errorOff = off;
					if (setChar(outLen, out, aChar)) {
						outLen++;
					} else {
						badChar = true;
						badOff = off;
					}
				}
			} else if (aChar != '{' && aChar != '}') {
				if (setChar(outLen, out, aChar)) {
					outLen++;
				} else {
					badChar = true;
					badOff = off;
				}
			}
		}
		String result = new String(out, 0, outLen);
		if (error) {
			throw new N4JSValueConverterWithValueException(
					IssueCodes.getMessageForVCO_IDENT_ESCAP_SEQ(identifier, errorOff),
					IssueCodes.VCO_IDENT_ESCAP_SEQ,
					node, result, null);
		}
		if (badChar) {
			if (result.length() != 0)
				throw new N4JSValueConverterWithValueException(
						IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(result, identifier, badOff),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT,
						node, result, null);
			else
				throw new N4JSValueConverterException(IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR(
						identifier, badOff),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR, node,
						null);
		}
		return result;
	}

	private static boolean setChar(final int outLen, char[] out, char c) {
		if (outLen == 0) {
			if (!N4JSLanguageUtils.isValidIdentifierStart(c)) {
				return false;
			}
		} else {
			if (!N4JSLanguageUtils.isValidIdentifierPart(c)) {
				return false;
			}
		}
		out[outLen] = c;
		return true;
	}

	/**
	 * Mostly copied from {@link Strings#convertToJavaString(String, boolean)}
	 */
	public static String convertToJSIdentifier(String theString, boolean useUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '\b':
				outBuffer.append('\\');
				outBuffer.append('b');
				break;
			case '\u000B':
				outBuffer.append('\\');
				outBuffer.append('v');
				break;
			case '\u0000':
				outBuffer.append('\\');
				outBuffer.append('0');
				break;
			case '\'':
				outBuffer.append('\\');
				outBuffer.append('\'');
				break;
			case '"':
				outBuffer.append('\\');
				outBuffer.append('"');
				break;
			default:
				if (useUnicode && ((aChar < 0x0020) || (aChar > 0x007e))) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(Strings.toHex((aChar >> 12) & 0xF));
					outBuffer.append(Strings.toHex((aChar >> 8) & 0xF));
					outBuffer.append(Strings.toHex((aChar >> 4) & 0xF));
					outBuffer.append(Strings.toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}
}
