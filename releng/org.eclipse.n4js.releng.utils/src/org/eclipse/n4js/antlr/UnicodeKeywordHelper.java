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
package org.eclipse.n4js.antlr;

import static java.lang.Character.LETTER_NUMBER;
import static java.lang.Character.LOWERCASE_LETTER;
import static java.lang.Character.MODIFIER_LETTER;
import static java.lang.Character.OTHER_LETTER;
import static java.lang.Character.TITLECASE_LETTER;
import static java.lang.Character.UPPERCASE_LETTER;

import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenUtil;

import com.google.common.base.Strings;

/**
 * A keyword helper that produces lexer rules with support for unicode escapes. Used by Xtend 1 extension
 * UnicodeKeywordHelper.ext.
 */
@SuppressWarnings("restriction")
public class UnicodeKeywordHelper {

	/**
	 * Creates lexer rule for given keyword in order to handle JavaScript's unicode masks. E.g.:
	 *
	 * <pre>
	 * If :
	 * 	( 'i' | '\\' 'u' '0''0''6''9' )
	 * 	( 'f' | '\\' 'u' '0''0''6''6' );
	 * </pre>
	 *
	 * @param keyword
	 *            the keyword as string, e.g. 'if'
	 * @return the lexer body, e.g.
	 *
	 *         <pre>
	 * ( 'i' | '\\' 'u' '0''0''6''9' ) ( 'f' | '\\' 'u' '0''0''6''6' )
	 *         </pre>
	 */
	public static String toUnicodeKeyword(String keyword) {
		if (keyword.equals("async ")) {
			keyword = "async";
		}
		if (isIdentifier(keyword)) {
			StringBuilder result = new StringBuilder(keyword.length() * 30);
			for (char c : keyword.toCharArray()) {
				result.append("\n\t( '");
				result.append(c);
				result.append("' | '\\\\' 'u' ");
				String unicodeEscape = Strings.padStart(Integer.toHexString(c), 4, '0');
				for (char u : unicodeEscape.toCharArray()) {
					if ('0' <= u && u <= '9') {
						result.append("'");
						result.append(u);
						result.append("'");
					} else {
						result.append("( '");
						result.append(u);
						result.append("' | '");
						result.append(Character.toUpperCase(u));
						result.append("' )");
					}
				}
				result.append(" )");
			}
			return result.toString();
		}
		return "'" + AntlrGrammarGenUtil.toAntlrString(keyword) + "'";
	}

	private static boolean isIdentifier(String keyword) {
		boolean wasTrue = false;
		for (char c : keyword.toCharArray()) {
			if (isLetter(c)) {
				wasTrue = true;
			} else {
				if (wasTrue) {
					throw new IllegalArgumentException(keyword
							+ " starts with letter but is not alphanumeric. Surprise!");
				}
			}
		}
		return wasTrue;
	}

	private static boolean isLetter(int c) {
		int type = Character.getType(c);
		boolean result = type == UPPERCASE_LETTER
				|| type == LOWERCASE_LETTER
				|| type == TITLECASE_LETTER
				|| type == MODIFIER_LETTER
				|| type == OTHER_LETTER
				|| type == LETTER_NUMBER;
		return result;
	}
}
