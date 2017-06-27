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
package org.eclipse.n4js.ui.typesearch;

import static org.eclipse.n4js.ui.typesearch.StringOperation.C_DIGIT;
import static org.eclipse.n4js.ui.typesearch.StringOperation.C_LOWER_LETTER;
import static org.eclipse.n4js.ui.typesearch.StringOperation.C_SPECIAL;
import static org.eclipse.n4js.ui.typesearch.StringOperation.C_UPPER_LETTER;
import static org.eclipse.n4js.ui.typesearch.StringOperation.EMPTY_REGIONS;
import static org.eclipse.n4js.ui.typesearch.StringOperation.MAX_OBVIOUS;
import static org.eclipse.n4js.ui.typesearch.StringOperation.OBVIOUS_IDENT_CHAR_NATURES;
import static org.eclipse.n4js.ui.typesearch.StringOperation.getPatternMatchingRegions;

/**
 */
/* default */abstract class SearchPatternHelper {

	public static final int R_EXACT_MATCH = 0;
	public static final int R_PREFIX_MATCH = 0x0001;
	public static final int R_PATTERN_MATCH = 0x0002;
	public static final int R_REGEXP_MATCH = 0x0004;
	public static final int R_CASE_SENSITIVE = 0x0008;
	public static final int R_ERASURE_MATCH = 0x0010;
	public static final int R_EQUIVALENT_MATCH = 0x0020;
	public static final int R_FULL_MATCH = 0x0040;
	public static final int R_CAMELCASE_MATCH = 0x0080;
	public static final int R_CAMELCASE_SAME_PART_COUNT_MATCH = 0x0100;

	/**
	 * Answers all the regions in a given name matching a given pattern using a specified match rule. </p>
	 * <p>
	 * Each of these regions is made of its starting index and its length in the given name. They are all concatenated
	 * in a single array of <code>int</code> which therefore always has an even length.
	 * </p>
	 * <p>
	 * All returned regions are disjointed from each other. That means that the end of a region is always different than
	 * the start of the following one.<br>
	 * For example, if two regions are returned:<br>
	 * <code>{ start1, length1, start2, length2 }</code><br>
	 * then <code>start1+length1</code> will always be smaller than <code>start2</code>.
	 * </p>
	 * <p>
	 * The possible comparison rules between the name and the pattern are:
	 * <ul>
	 * <li>{@link #R_EXACT_MATCH exact matching}</li>
	 * <li>{@link #R_PREFIX_MATCH prefix matching}</li>
	 * <li>{@link #R_PATTERN_MATCH pattern matching}</li>
	 * <li>{@link #R_CAMELCASE_MATCH camel case matching}</li>
	 * <li>{@link #R_CAMELCASE_SAME_PART_COUNT_MATCH camel case matching with same parts count}</li>
	 * </ul>
	 * Each of these rules may be combined with the {@link #R_CASE_SENSITIVE case sensitive flag} if the match
	 * comparison should respect the case.
	 *
	 * <pre>
	 * Examples:
	 * <ol><li>  pattern = "NPE"
	 *  name = NullPointerException / NoPermissionException
	 *  matchRule = {@link #R_CAMELCASE_MATCH}
	 *  result:  { 0, 1, 4, 1, 11, 1 } / { 0, 1, 2, 1, 12, 1 } </li>
	 * <li>  pattern = "NuPoEx"
	 *  name = NullPointerException
	 *  matchRule = {@link #R_CAMELCASE_MATCH}
	 *  result:  { 0, 2, 4, 2, 11, 2 }</li>
	 * <li>  pattern = "IPL3"
	 *  name = "IPerspectiveListener3"
	 *  matchRule = {@link #R_CAMELCASE_MATCH}
	 *  result:  { 0, 2, 12, 1, 20, 1 }</li>
	 * <li>  pattern = "HashME"
	 *  name = "HashMapEntry"
	 *  matchRule = {@link #R_CAMELCASE_MATCH}
	 *  result:  { 0, 5, 7, 1 }</li>
	 * <li>  pattern = "N???Po*Ex?eption"
	 *  name = NullPointerException
	 *  matchRule = {@link #R_PATTERN_MATCH} | {@link #R_CASE_SENSITIVE}
	 *  result:  { 0, 1, 4, 2, 11, 2, 14, 6 }</li>
	 * <li>  pattern = "Ha*M*ent*"
	 *  name = "HashMapEntry"
	 *  matchRule = {@link #R_PATTERN_MATCH}
	 *  result:  { 0, 2, 4, 1, 7, 3 }</li>
	 * </ol>
	 * </pre>
	 *
	 * @param pattern
	 *            the given pattern. If <code>null</code>, then an empty region (<code>new int[0]</code>) will be
	 *            returned showing that the name matches the pattern but no common character has been found.
	 * @param name
	 *            the given name
	 * @param matchRule
	 *            the rule to apply for the comparison.<br>
	 *            The following values are accepted:
	 *            <ul>
	 *            <li>{@link #R_EXACT_MATCH}</li> <li>{@link #R_PREFIX_MATCH}</li> <li>{@link #R_PATTERN_MATCH}</li>
	 *            <li>{@link #R_CAMELCASE_MATCH}</li> <li>{@link #R_CAMELCASE_SAME_PART_COUNT_MATCH}</li>
	 *            </ul>
	 *            <p>
	 *            Each of these valid values may be also combined with the {@link #R_CASE_SENSITIVE} flag.
	 *            </p>
	 *            Some examples:
	 *            <ul>
	 *            <li>{@link #R_EXACT_MATCH} | {@link #R_CASE_SENSITIVE}: if an exact case sensitive match is expected,</li>
	 *            <li>{@link #R_PREFIX_MATCH}: if a case insensitive prefix match is expected,</li>
	 *            <li>{@link #R_CAMELCASE_MATCH}: if a case insensitive camel case match is expected,</li>
	 *            <li>{@link #R_CAMELCASE_SAME_PART_COUNT_MATCH} | {@link #R_CASE_SENSITIVE}: if a case sensitive camel
	 *            case with same parts count match is expected,</li>
	 *            <li>etc.</li>
	 *            </ul>
	 * @return an array of <code>int</code> having two slots per returned regions (the first one is the region starting
	 *         index and the second one is the region length or <code>null</code> if the given name does not match the
	 *         given pattern).
	 *         <p>
	 *         The returned regions may be empty (<code>new int[0]</code>) if the pattern is <code>null</code> (whatever
	 *         the match rule is). The returned regions will also be empty if the pattern is only made of
	 *         <code>'?'</code> and/or <code>'*'</code> character(s) (e.g. <code>'*'</code>, <code>'?*'</code>,
	 *         <code>'???'</code>, etc.) when using a pattern match rule.
	 *         </p>
	 *
	 * @since 3.5
	 */
	public static final int[] getMatchingRegions(String pattern, String name, int matchRule) {
		if (name == null)
			return null;
		final int nameLength = name.length();
		if (pattern == null) {
			return new int[] { 0, nameLength };
		}
		final int patternLength = pattern.length();
		boolean countMatch = false;
		switch (matchRule) {
		case R_EXACT_MATCH:
			if (patternLength == nameLength && pattern.equalsIgnoreCase(name)) {
				return new int[] { 0, patternLength };
			}
			break;
		case R_EXACT_MATCH | R_CASE_SENSITIVE:
			if (patternLength == nameLength && pattern.equals(name)) {
				return new int[] { 0, patternLength };
			}
			break;
		case R_PREFIX_MATCH:
			if (patternLength <= nameLength && name.substring(0, patternLength).equalsIgnoreCase(pattern)) {
				return new int[] { 0, patternLength };
			}
			break;
		case R_PREFIX_MATCH | R_CASE_SENSITIVE:
			if (name.startsWith(pattern)) {
				return new int[] { 0, patternLength };
			}
			break;
		case R_CAMELCASE_SAME_PART_COUNT_MATCH:
			countMatch = true;
			//$FALL-THROUGH$
		case R_CAMELCASE_MATCH:
			if (patternLength <= nameLength) {
				int[] regions = getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0,
						nameLength, countMatch);
				if (regions != null)
					return regions;
				if (name.substring(0, patternLength).equalsIgnoreCase(pattern)) {
					return new int[] { 0, patternLength };
				}
			}
			break;
		case R_CAMELCASE_SAME_PART_COUNT_MATCH | R_CASE_SENSITIVE:
			countMatch = true;
			//$FALL-THROUGH$
		case R_CAMELCASE_MATCH | R_CASE_SENSITIVE:
			if (patternLength <= nameLength) {
				return getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0, nameLength,
						countMatch);
			}
			break;
		case R_PATTERN_MATCH:
			return getPatternMatchingRegions(pattern, 0, patternLength, name, 0, nameLength, false);
		case R_PATTERN_MATCH | R_CASE_SENSITIVE:
			return getPatternMatchingRegions(pattern, 0, patternLength, name, 0, nameLength, true);
		}
		return null;
	}

	/**
	 * Answers all the regions in a given name matching a given camel case pattern. </p>
	 * <p>
	 * Each of these regions is made of its starting index and its length in the given name. They are all concatenated
	 * in a single array of <code>int</code> which therefore always has an even length.
	 * </p>
	 * <p>
	 * Note that each region is disjointed from the following one.<br>
	 * E.g. if the regions are <code>{ start1, length1, start2, length2 }</code>, then <code>start1+length1</code> will
	 * always be smaller than <code>start2</code>.
	 * </p>
	 * <p>
	 *
	 * <pre>
	 * Examples:
	 * <ol><li>  pattern = "NPE"
	 *  name = NullPointerException / NoPermissionException
	 *  result:  { 0, 1, 4, 1, 11, 1 } / { 0, 1, 2, 1, 12, 1 } </li>
	 * <li>  pattern = "NuPoEx"
	 *  name = NullPointerException
	 *  result:  { 0, 2, 4, 2, 11, 2 }</li>
	 * <li>  pattern = "IPL3"
	 *  name = "IPerspectiveListener3"
	 *  result:  { 0, 2, 12, 1, 20, 1 }</li>
	 * <li>  pattern = "HashME"
	 *  name = "HashMapEntry"
	 *  result:  { 0, 5, 7, 1 }</li>
	 * </ol>
	 * </pre>
	 *
	 * </p>
	 *
	 * @param pattern
	 *            the given pattern
	 * @param patternStart
	 *            the start index of the pattern, inclusive
	 * @param patternEnd
	 *            the end index of the pattern, exclusive
	 * @param name
	 *            the given name
	 * @param nameStart
	 *            the start index of the name, inclusive
	 * @param nameEnd
	 *            the end index of the name, exclusive
	 * @param samePartCount
	 *            flag telling whether the pattern and the name should have the same count of parts or not.<br>
	 *            &nbsp;&nbsp;For example:
	 *            <ul>
	 *            <li>'HM' type string pattern will match 'HashMap' and 'HtmlMapper' types, but not 'HashMapEntry'</li>
	 *            <li>'HMap' type string pattern will still match previous 'HashMap' and 'HtmlMapper' types, but not
	 *            'HighMagnitude'</li>
	 *            </ul>
	 * @return an array of <code>int</code> having two slots per returned regions (first one is the starting index of
	 *         the region and the second one the length of the region).<br>
	 *         Note that it may be <code>null</code> if the given name does not match the pattern
	 * @since 3.5
	 */
	private static final int[] getCamelCaseMatchingRegions(String pattern, int patternStart, int patternEnd,
			String name, int nameStart, int nameEnd, boolean samePartCount) {

		/*
		 * !!!!!!!!!! WARNING !!!!!!!!!! The algorithm used in this method has been fully inspired from
		 * CharOperation#camelCaseMatch(char[], int, int, char[], int, int, boolean).
		 *
		 * So, if any change needs to be applied in the algorithm, do NOT forget to backport it in the CharOperation
		 * method!
		 */

		if (name == null)
			return null; // null name cannot match
		if (pattern == null) {
			// null pattern cannot match any region
			// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=264816
			return EMPTY_REGIONS;
		}
		if (patternEnd < 0)
			patternEnd = pattern.length();
		if (nameEnd < 0)
			nameEnd = name.length();

		if (patternEnd <= patternStart) {
			return nameEnd <= nameStart
					? new int[] { patternStart, patternEnd - patternStart }
			: null;
		}
		if (nameEnd <= nameStart)
			return null;
		// check first pattern char
		if (name.charAt(nameStart) != pattern.charAt(patternStart)) {
			// first char must strictly match (upper/lower)
			return null;
		}

		char patternChar, nameChar;
		int iPattern = patternStart;
		int iName = nameStart;

		// init segments
		int parts = 1;
		for (int i = patternStart + 1; i < patternEnd; i++) {
			final char ch = pattern.charAt(i);
			if (ch < MAX_OBVIOUS) {
				if ((OBVIOUS_IDENT_CHAR_NATURES[ch] & (C_UPPER_LETTER | C_DIGIT)) != 0) {
					parts++;
				}
			} else if (Character.isJavaIdentifierPart(ch) && (Character.isUpperCase(ch) || Character.isDigit(ch))) {
				parts++;
			}
		}
		int[] segments = null;
		int count = 0; // count

		// Main loop is on pattern characters
		int segmentStart = iName;
		while (true) {
			iPattern++;
			iName++;

			if (iPattern == patternEnd) { // we have exhausted pattern...
				// it's a match if the name can have additional parts (i.e. uppercase characters) or is also exhausted
				if (!samePartCount || iName == nameEnd) {
					if (segments == null) {
						segments = new int[2];
					}
					segments[count++] = segmentStart;
					segments[count++] = iName - segmentStart;
					if (count < segments.length) {
						System.arraycopy(segments, 0, segments = new int[count], 0, count);
					}
					return segments;
				}

				// otherwise it's a match only if the name has no more uppercase characters
				int segmentEnd = iName;
				while (true) {
					if (iName == nameEnd) {
						// we have exhausted the name, so it's a match
						if (segments == null) {
							segments = new int[2];
						}
						segments[count++] = segmentStart;
						segments[count++] = segmentEnd - segmentStart;
						if (count < segments.length) {
							System.arraycopy(segments, 0, segments = new int[count], 0, count);
						}
						return segments;
					}
					nameChar = name.charAt(iName);
					// test if the name character is uppercase
					if (nameChar < MAX_OBVIOUS) {
						if ((OBVIOUS_IDENT_CHAR_NATURES[nameChar] & C_UPPER_LETTER) != 0) {
							return null;
						}
					}
					else if (!Character.isJavaIdentifierPart(nameChar) || Character.isUpperCase(nameChar)) {
						return null;
					}
					iName++;
				}
			}

			if (iName == nameEnd) {
				// We have exhausted the name (and not the pattern), so it's not a match
				return null;
			}

			// For as long as we're exactly matching, bring it on (even if it's a lower case character)
			if ((patternChar = pattern.charAt(iPattern)) == name.charAt(iName)) {
				continue;
			}
			int segmentEnd = iName;

			// If characters are not equals, then it's not a match if patternChar is lowercase
			if (patternChar < MAX_OBVIOUS) {
				if ((OBVIOUS_IDENT_CHAR_NATURES[patternChar] & (C_UPPER_LETTER | C_DIGIT)) == 0) {
					return null;
				}
			} else if (Character.isJavaIdentifierPart(patternChar) && !Character.isUpperCase(patternChar)
					&& !Character.isDigit(patternChar)) {
				return null;
			}

			// patternChar is uppercase, so let's find the next uppercase in name
			while (true) {
				if (iName == nameEnd) {
					// We have exhausted name (and not pattern), so it's not a match
					return null;
				}

				nameChar = name.charAt(iName);
				if (nameChar < MAX_OBVIOUS) {
					int charNature = OBVIOUS_IDENT_CHAR_NATURES[nameChar];
					if ((charNature & (C_LOWER_LETTER | C_SPECIAL)) != 0) {
						// nameChar is lowercase
						iName++;
					} else if ((charNature & C_DIGIT) != 0) {
						// nameChar is digit => break if the digit is current pattern character otherwise consume it
						if (patternChar == nameChar)
							break;
						iName++;
						// nameChar is uppercase...
					} else if (patternChar != nameChar) {
						// .. and it does not match patternChar, so it's not a match
						return null;
					} else {
						// .. and it matched patternChar. Back to the big loop
						break;
					}
				}
				// Same tests for non-obvious characters
				else if (Character.isJavaIdentifierPart(nameChar) && !Character.isUpperCase(nameChar)) {
					iName++;
				} else if (Character.isDigit(nameChar)) {
					if (patternChar == nameChar)
						break;
					iName++;
				} else if (patternChar != nameChar) {
					return null;
				} else {
					break;
				}
			}
			// At this point, either name has been exhausted, or it is at an uppercase letter.
			// Since pattern is also at an uppercase letter
			if (segments == null) {
				segments = new int[parts * 2];
			}
			segments[count++] = segmentStart;
			segments[count++] = segmentEnd - segmentStart;
			segmentStart = iName;
		}
	}

}
