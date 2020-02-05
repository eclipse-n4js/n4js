/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;

/**
 * Used the algorithm that is also used by the xtext.ui implementation of the CamelCase prefix matcher.
 */
public class CamelCasePrefixMatcher extends IPrefixMatcher.IgnoreCase {

	@Override
	public boolean isCandidateMatchingPrefix(String name, String prefix) {
		boolean result = super.isCandidateMatchingPrefix(name, prefix) ||
				prefix.length() < name.length() && camelCaseMatch(name, prefix);
		return result;
	}

	private static boolean camelCaseMatch(String name, String prefix) {
		return camelCaseMatch(prefix.toCharArray(), name.toCharArray());
	}

	private static boolean camelCaseMatch(char[] pattern, char[] name) {
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (name == null)
			return false; // null name cannot match

		return camelCaseMatch(pattern, 0, pattern.length, name, 0, name.length, false/* not the same count of parts */);
	}

	private static boolean camelCaseMatch(char[] pattern, int patternStart, int patternEnd, char[] name, int nameStart,
			int nameEnd, boolean samePartCount) {

		/*
		 * !!!!!!!!!! WARNING !!!!!!!!!! The algorithm implemented in this method has been heavily used in
		 * StringOperation#getCamelCaseMatchingRegions(String, int, int, String, int, int, boolean) method.
		 *
		 * So, if any change needs to be applied in the current algorithm, do NOT forget to also apply the same change
		 * in the StringOperation method!
		 */

		if (name == null)
			return false; // null name cannot match
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (patternEnd < 0)
			patternEnd = pattern.length;
		if (nameEnd < 0)
			nameEnd = name.length;

		if (patternEnd <= patternStart)
			return nameEnd <= nameStart;
		if (nameEnd <= nameStart)
			return false;
		// check first pattern char
		if (name[nameStart] != pattern[patternStart]) {
			// first char must strictly match (upper/lower)
			return false;
		}

		char patternChar, nameChar;
		int iPattern = patternStart;
		int iName = nameStart;

		// Main loop is on pattern characters
		while (true) {

			iPattern++;
			iName++;

			if (iPattern == patternEnd) { // we have exhausted pattern...
				// it's a match if the name can have additional parts (i.e. uppercase characters) or is also exhausted
				if (!samePartCount || iName == nameEnd)
					return true;

				// otherwise it's a match only if the name has no more uppercase characters
				while (true) {
					if (iName == nameEnd) {
						// we have exhausted the name, so it's a match
						return true;
					}
					nameChar = name[iName];
					// test if the name character is uppercase
					if (!Character.isJavaIdentifierPart(nameChar) || Character.isUpperCase(nameChar)) {
						return false;
					}
					iName++;
				}
			}

			if (iName == nameEnd) {
				// We have exhausted the name (and not the pattern), so it's not a match
				return false;
			}

			// For as long as we're exactly matching, bring it on (even if it's a lower case character)
			if ((patternChar = pattern[iPattern]) == name[iName]) {
				continue;
			}

			// If characters are not equals, then it's not a match if patternChar is lowercase
			if (Character.isJavaIdentifierPart(patternChar) && !Character.isUpperCase(patternChar)
					&& !Character.isDigit(patternChar)) {
				return false;
			}

			// patternChar is uppercase, so let's find the next uppercase in name
			while (true) {
				if (iName == nameEnd) {
					// We have exhausted name (and not pattern), so it's not a match
					return false;
				}

				nameChar = name[iName];
				if (Character.isJavaIdentifierPart(nameChar) && !Character.isUpperCase(nameChar)) {
					iName++;
				} else if (Character.isDigit(nameChar)) {
					if (patternChar == nameChar)
						break;
					iName++;
				} else if (Character.isJavaIdentifierPart(nameChar) && Character.isUpperCase(nameChar) && iName > 0
						&& Character.isUpperCase(name[iName - 1])) {
					if (patternChar == nameChar)
						break;
					iName++;
				} else if (patternChar != nameChar) {
					return false;
				} else {
					break;
				}
			}
			// At this point, either name has been exhausted, or it is at an uppercase letter.
			// Since pattern is also at an uppercase letter
		}
	}
}
