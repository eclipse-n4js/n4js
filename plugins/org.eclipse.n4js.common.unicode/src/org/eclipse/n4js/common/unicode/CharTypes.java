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
package org.eclipse.n4js.common.unicode;

import static java.lang.Character.COMBINING_SPACING_MARK;
import static java.lang.Character.CONNECTOR_PUNCTUATION;
import static java.lang.Character.DECIMAL_DIGIT_NUMBER;
import static java.lang.Character.LETTER_NUMBER;
import static java.lang.Character.LOWERCASE_LETTER;
import static java.lang.Character.MODIFIER_LETTER;
import static java.lang.Character.NON_SPACING_MARK;
import static java.lang.Character.OTHER_LETTER;
import static java.lang.Character.SPACE_SEPARATOR;
import static java.lang.Character.TITLECASE_LETTER;
import static java.lang.Character.UPPERCASE_LETTER;

import org.eclipse.n4js.common.unicode.generator.UnicodeGrammarGenerator;

/**
 * Use com.ibm.icu to proper handle unicodes.
 *
 * The methods are used by the {@link UnicodeGrammarGenerator} and the value conversion routines in N4JS.
 */
public class CharTypes {

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a combining mark.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is either a {@link Character#NON_SPACING_MARK} or {@link Character#COMBINING_SPACING_MARK}
	 * </p>
	 */
	public static boolean isCombiningMark(int c) {
		int type = Character.getType(c);
		boolean result = type == NON_SPACING_MARK || type == COMBINING_SPACING_MARK;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a digit.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is a {@link Character#DECIMAL_DIGIT_NUMBER}
	 * </p>
	 */
	public static boolean isDigit(int c) {
		int type = Character.getType(c);
		boolean result = type == DECIMAL_DIGIT_NUMBER;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a connector punctuation.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is a {@link Character#CONNECTOR_PUNCTUATION}
	 * </p>
	 */
	public static boolean isConnectorPunctuation(int c) {
		int type = Character.getType(c);
		boolean result = type == CONNECTOR_PUNCTUATION;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a letter.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is either a {@link Character#UPPERCASE_LETTER}, {@link Character#LOWERCASE_LETTER},
	 * {@link Character#TITLECASE_LETTER}, {@link Character#MODIFIER_LETTER}, {@link Character#OTHER_LETTER} or
	 * {@link Character#LETTER_NUMBER}
	 * </p>
	 */
	public static boolean isLetter(int c) {
		int type = Character.getType(c);
		boolean result = type == UPPERCASE_LETTER || type == LOWERCASE_LETTER || type == TITLECASE_LETTER
				|| type == MODIFIER_LETTER || type == OTHER_LETTER || type == LETTER_NUMBER;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a space separator.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is a {@link Character#SPACE_SEPARATOR}
	 * </p>
	 */
	public static boolean isSpaceSeparator(int c) {
		int type = Character.getType(c);
		boolean result = type == SPACE_SEPARATOR;
		return result;
	}
}
