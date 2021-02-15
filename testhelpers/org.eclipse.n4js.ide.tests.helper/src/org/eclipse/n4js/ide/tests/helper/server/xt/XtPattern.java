/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.utils.Strings;

/**
 *
 */
public class XtPattern {

	static public class PatternBuilder {
		final static String WHITESPACE = "\\s+";
		String regex = WHITESPACE;
		Set<String> availableArgNames = new HashSet<>();

		PatternBuilder keyword(String keywordName) {
			regex += keywordName + WHITESPACE;
			return this;
		}

		PatternBuilder argOpt(String argName, Object... validValues) {
			arg(argName, true, validValues);
			return this;
		}

		PatternBuilder argMan(String argName, Object... validValues) {
			arg(argName, false, validValues);
			return this;
		}

		PatternBuilder arg(String argName, boolean isOptional, Object... validValues) {
			String values = "[^']+";
			if (validValues != null && validValues.length > 0) {
				values = "[" + Strings.join("|", validValues) + "]";
			}
			regex += isOptional ? "(?:" : "";
			regex += argName + WHITESPACE;
			regex += "'(?<" + argName + ">" + values + ")'";
			regex += WHITESPACE;
			regex += isOptional ? ")?" : "";
			availableArgNames.add(argName);
			return this;
		}

		XtPattern build() {
			Pattern pattern = Pattern.compile(regex);
			return new XtPattern(pattern, availableArgNames);
		}

		@Override
		public String toString() {
			return regex;
		}
	}

	static public PatternBuilder builder() {
		return new PatternBuilder();
	}

	final Pattern pattern;
	final Set<String> availableArgNames;
	Matcher matcher;

	public XtPattern(Pattern pattern, Set<String> availableArgNames) {
		this.pattern = pattern;
		this.availableArgNames = availableArgNames;
	}

	public void match(String inputString) {
		matcher = pattern.matcher(inputString);
		String msg = "The test method does not match the regex.\n";
		msg += "Test method: " + inputString + "\n";
		msg += "Regex      : " + pattern.pattern();
		assertTrue(msg, matcher.find());
	}

	public String get(String groupName) {
		assertNotNull("Must call 'match' before calling 'get'", matcher);
		assertTrue("Unknown arg name: " + groupName, availableArgNames.contains(groupName));
		return matcher.group(groupName);
	}

}
