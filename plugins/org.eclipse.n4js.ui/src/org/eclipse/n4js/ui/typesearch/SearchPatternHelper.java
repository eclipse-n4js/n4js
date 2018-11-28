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
				int[] regions = StringOperation.getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0,
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
				return StringOperation.getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0, nameLength,
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

}
