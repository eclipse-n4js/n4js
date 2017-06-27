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

import java.io.StringReader;
import java.util.Iterator;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.conversion.ValueConverterWithValueException;
import org.eclipse.xtext.conversion.impl.AbstractValueConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.CharacterClassRange;
import org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.n4js.validation.IssueCodes;

/**
 * A value converter that validates regular expression literals and converts them to strings and vice versa.
 */
@Singleton
public class RegExLiteralConverter extends AbstractValueConverter<String> {

	/**
	 * The issue code for bogus regular expression literals.
	 */
	public static final String ISSUE_CODE = "RegExLiteralConverter.bogus.regex";

	/**
	 * A {@link ValueConverterWithValueException} that indicates an erroneous regular expression literal.
	 */
	public static class BogusRegExLiteralException extends ValueConverterWithValueException {

		private static final long serialVersionUID = 1L;

		/**
		 * Create a new exception for the given node with the recovered value.
		 */
		public BogusRegExLiteralException(String message, INode node, String value) {
			super(message, node, value, null);
		}

	}

	private IParser regexParser;
	private final IResourceServiceProvider.Registry serviceProviders;

	/**
	 * Creates a new literal converter that obtains an {@link IParser} for regular expressions from the given registry.
	 */
	@Inject
	public RegExLiteralConverter(IResourceServiceProvider.Registry serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	@Override
	public String toString(String value) {
		return value;
	}

	private IParser getRegexParser() {
		if (regexParser == null) {
			// no need for sync since we can also use a new regexParser if concurrent access happens by accident
			IResourceServiceProvider serviceProvider = serviceProviders.getResourceServiceProvider(URI
					.createURI("a.regex"));
			regexParser = serviceProvider.get(IParser.class);
		}
		return regexParser;
	}

	@Override
	public String toValue(String string, INode node) {
		String escaped = convertFromJS(string, node);
		IParseResult parseResult = getRegexParser().parse(new StringReader(escaped));
		Iterable<INode> syntaxErrors = parseResult.getSyntaxErrors();
		Iterator<INode> iterator = syntaxErrors.iterator();
		String issueMessage = null;
		boolean hasErrors = false;
		while (iterator.hasNext()) {
			INode syntaxError = iterator.next();
			String message = syntaxError.getSyntaxErrorMessage().getMessage();
			if (message.contains("'<EOF>' expecting '/'")) {
				hasErrors = true;
				break;
			}
			if (issueMessage == null)
				issueMessage = message;
		}
		if (hasErrors) {
			throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
					IssueCodes.VCO_REGEX_INVALID, node, string, null);
		}
		if (issueMessage != null) {
			throw new BogusRegExLiteralException(issueMessage, node, string);
		}
		RegularExpressionLiteral literal = (RegularExpressionLiteral) parseResult.getRootASTElement();
		if (literal.getFlags().getFlags().contains("u")) {
			TreeIterator<EObject> regExIterator = literal.eAllContents();
			while (regExIterator.hasNext()) {
				EObject next = regExIterator.next();
				if (next instanceof DecimalEscapeSequence) {
					throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
							IssueCodes.VCO_REGEX_INVALID, node, string, null);
				} else if (next instanceof ControlLetterEscapeSequence) {
					if (((ControlLetterEscapeSequence) next).getSequence().length() <= 2) {
						throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, string, null);
					}
				} else if (next instanceof CharacterClassRange) {
					CharacterClassRange casted = (CharacterClassRange) next;
					if (casted.getLeft() instanceof CharacterClassEscapeSequence
							|| casted.getRight() instanceof CharacterClassEscapeSequence) {
						throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, string, null);
					}
				}
			}
		}
		return string;
	}

	/**
	 * Mostly copied from {@link Strings#convertFromJavaString(String, boolean)}
	 */
	public static String convertFromJS(String literal, INode node) {
		char[] in = literal.toCharArray();
		int off = 0;
		int len = literal.length();
		char[] convtBuf = new char[len];
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;
		boolean error = false;
		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				if (off < end) {
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
					default: {
						out[outLen++] = '\\';
						out[outLen++] = aChar;
					}
					}
				} else {
					error = true;
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		String result = new String(out, 0, outLen);
		if (error) {
			throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_ILLEGAL_ESCAP(literal),
					IssueCodes.VCO_REGEX_ILLEGAL_ESCAP, node, result, null);
		}
		return result;
	}

}
