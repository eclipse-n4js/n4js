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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replacement methods used by injectors to replace snippets in the generated grammar and lexer, and by fragments to
 * replace snippets in the generated java parser.
 */
public class Replacements {

	/**
	 * Returns the simple name of the top level class defined in the given grammar content.
	 */
	public static String getGrammarClass(String grammarContent) {
		Matcher m = Pattern.compile("public\\ class\\ ([a-zA-Z0-9_]+)\\ ").matcher(grammarContent);
		if (m.find()) {
			return m.group(1);
		}
		return "unknown grammar class";
	}

	/**
	 * Replaces all matches of the given pattern in the given input string. If no match was found, an
	 * {@link IllegalStateException} is raised.
	 *
	 * @see String#replaceAll(String, String)
	 */
	public static String replaceAll(String in, String pattern, String replacement) {
		String result = in.replaceAll(pattern, replacement);
		if (result.equals(in)) {
			Thread.dumpStack();
			throw new IllegalStateException("Did not find '" + pattern + "' in grammar");
		}
		return result;
	}

	/**
	 * Replaces all matches of the given pattern in the given input string. If no match was found, an
	 * {@link IllegalStateException} is raised.
	 *
	 * @see String#replaceFirst(String, String)
	 */
	public static String replaceFirst(String in, String pattern, String replacement) {
		String result = in.replaceFirst(pattern, replacement);
		if (result.equals(in)) {
			Thread.dumpStack();
			throw new IllegalStateException("Did not find '" + pattern + "' in grammar");
		}
		return result;
	}

	/**
	 * Replaces all matches of the given lookup in the given input string. If no match was found, an
	 * {@link IllegalStateException} is raised.
	 *
	 * @see String#replace(CharSequence, CharSequence)
	 */
	public static String replace(String in, String lookup, String replacement) {
		String result = in.replace(lookup, replacement);
		if (result.equals(in)) {
			final IllegalStateException ex = new IllegalStateException("Did not find '" + lookup + "' in grammar");
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	/**
	 * Applies a replacement, which is a string containing both the lookup (non-regex) and the replacement.These two
	 * strings are separated via
	 *
	 * <pre>
	 * _________________________________REPLACE_WITH_________________________________
	 * </pre>
	 *
	 * The replacement file has to be in the same folder as this class.
	 */
	public static String applyReplacement(String in, String replacementName) {
		try {
			PlaceholderReplacer replacer = new PlaceholderReplacer(replacementName);
			String result = replacer.replaceExactlyOnce(in);
			return result;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
