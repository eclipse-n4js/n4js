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
package org.eclipse.n4js.antlr.replacements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Utility class to translate search-and-replace patterns into regex and apply them. Both 'search' and 'replace' strings
 * can contain regular expressions, encapsulated in two hash characters ('##'). Everything outside is quoted. Working
 * with place-holders instead of using RegEx directly provides the advantage that larger 'search' and 'replace' strings
 * do not need to be RegEx-escaped manually.
 * <p>
 * Typical usage:
 * <ol>
 * <li>Named group with back reference: <code>....##(?<a1>\w+)##...##\k<a1>##...</code>, replace with
 * <code>...##${a1}##...</code></li>
 * </ol>
 * Note: Using single backslashes since pattern is loaded from file.
 */
public class PlaceholderReplacer {

	/**
	 * Separator in *.template files.
	 */
	private static final String SEPARATOR = "\n_________________________________REPLACE_WITH_________________________________\n";

	/**
	 * Syntax for place-holders as defined in *.template files.
	 */
	private final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("##([^#]+)##");

	/**
	 * Package visible for testing
	 */
	final Pattern searchPattern;
	/**
	 * Package visible for testing
	 */
	final String replacement;
	private final String source;

	/**
	 * Load 'search' and 'replace' strings from a file from this class' directory.
	 */
	public PlaceholderReplacer(String replacementName) throws IOException {
		String replacementBase = Replacements.class.getPackage().getName().replace(".", "/");
		this.source = replacementBase + "/" + replacementName;
		URL url = Replacements.class.getClassLoader().getResource(source);
		if (url == null) {
			throw new NullPointerException("cannot replacement :: " + source);
		}
		// normalize string to run on Windows and Mac/Unix
		String fileContents = Resources.toString(url, Charsets.UTF_8).replace("\r\n", "\n");
		String[] split = fileContents.split(SEPARATOR);
		String searchString = split[0];
		String replacementString = split[1];
		this.searchPattern = createPattern(searchString);
		this.replacement = createReplace(replacementString);
	}

	/**
	 * Only used in tests.
	 */
	PlaceholderReplacer(String searchString, String replacementString) {
		this.source = "(dummy)";
		this.searchPattern = createPattern(searchString);
		this.replacement = createReplace(replacementString);
	}

	private Pattern createPattern(String match) {
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(match);
		int lastOffset = 0;
		StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			result.append(Pattern.quote(match.substring(lastOffset, matcher.start())));
			String regEx = matcher.group(1);
			result.append(regEx);
			lastOffset = matcher.end();
		}
		result.append(Pattern.quote(match.substring(lastOffset)));
		return Pattern.compile(result.toString());
	}

	private String createReplace(String match) {
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(match);
		int lastOffset = 0;
		StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			result.append(Matcher.quoteReplacement(match.substring(lastOffset, matcher.start())));
			String regEx = matcher.group(1);
			result.append(regEx);
			lastOffset = matcher.end();
		}
		result.append(Matcher.quoteReplacement(match.substring(lastOffset)));
		return result.toString();
	}

	@Override
	public String toString() {
		return searchPattern + "\n" + SEPARATOR + "\n" + replacement;
	}

	/**
	 * Applies the replacement onto this string. Throws exceptions if the searchString ins being found not at all or
	 * more than once.
	 *
	 * @param original
	 *            The string on which the replacements should be applied
	 * @return The string onto which the replacements have been applied
	 */
	public String replaceExactlyOnce(String original) {
		Matcher matcher = searchPattern.matcher(original);
		if (!matcher.find()) {
			String msg = "Pattern not found!\nTemplate:" + source + "\nPattern:\n" + searchPattern;
			throw new IllegalStateException(msg);
		}
		StringBuffer sb = new StringBuffer();
		matcher.appendReplacement(sb, replacement);
		if (matcher.find()) {
			String msg = "Pattern found more than once!\nTemplate:" + source + "\nPattern:\n" + searchPattern;
			throw new IllegalStateException(msg);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
