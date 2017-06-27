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

import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.COMBINING_SPACING_MARK;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.CONNECTOR_PUNCTUATION;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.DECIMAL_DIGIT_NUMBER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.LETTER_NUMBER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.LOWERCASE_LETTER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.MODIFIER_LETTER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.NON_SPACING_MARK;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.OTHER_LETTER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.SPACE_SEPARATOR;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.TITLECASE_LETTER;
import static com.ibm.icu.lang.UCharacterEnums.ECharacterCategory.UPPERCASE_LETTER;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UCharacterEnums.ECharacterCategory;

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
	 * That is, {@code c} is either a {@link ECharacterCategory#NON_SPACING_MARK} or
	 * {@link ECharacterCategory#COMBINING_SPACING_MARK}
	 * </p>
	 */
	public static boolean isCombiningMark(int c) {
		int type = UCharacter.getType(c);
		boolean result = type == NON_SPACING_MARK || type == COMBINING_SPACING_MARK;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a digit.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is a {@link ECharacterCategory#DECIMAL_DIGIT_NUMBER}
	 * </p>
	 */
	public static boolean isDigit(int c) {
		int type = UCharacter.getType(c);
		boolean result = type == DECIMAL_DIGIT_NUMBER;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a connector punctuation.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is a {@link ECharacterCategory#CONNECTOR_PUNCTUATION}
	 * </p>
	 */
	public static boolean isConnectorPunctuation(int c) {
		int type = UCharacter.getType(c);
		boolean result = type == CONNECTOR_PUNCTUATION;
		return result;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the character code {@code c} represents a letter.
	 * </p>
	 *
	 * <p>
	 * That is, {@code c} is either a {@link ECharacterCategory#UPPERCASE_LETTER},
	 * {@link ECharacterCategory#LOWERCASE_LETTER}, {@link ECharacterCategory#TITLECASE_LETTER},
	 * {@link ECharacterCategory#MODIFIER_LETTER}, {@link ECharacterCategory#OTHER_LETTER} or
	 * {@link ECharacterCategory#LETTER_NUMBER}
	 * </p>
	 */
	public static boolean isLetter(int c) {
		int type = UCharacter.getType(c);
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
	 * That is, {@code c} is a {@link ECharacterCategory#SPACE_SEPARATOR}
	 * </p>
	 */
	public static boolean isSpaceSeparator(int c) {
		int type = UCharacter.getType(c);
		boolean result = type == SPACE_SEPARATOR;
		return result;
	}
}
