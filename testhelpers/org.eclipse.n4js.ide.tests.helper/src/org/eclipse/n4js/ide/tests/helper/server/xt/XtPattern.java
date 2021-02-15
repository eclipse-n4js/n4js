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

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.utils.Strings;

/**
 *
 */
public class XtPattern {

	static public class PatternBuilder {
		final static String WHITESPACE = "\\s+";
		String regex = WHITESPACE;
		Set<String> availableTextNames = new HashSet<>();
		Set<String> availableObjectNames = new HashSet<>();

		PatternBuilder keyword(String keywordName) {
			regex += keywordName + WHITESPACE;
			return this;
		}

		PatternBuilder textOpt(String argName, Object... validValues) {
			arg(argName, false, true, validValues);
			return this;
		}

		PatternBuilder textMan(String argName, Object... validValues) {
			arg(argName, false, false, validValues);
			return this;
		}

		PatternBuilder objOpt(String argName, Object... validValues) {
			arg(argName, true, true, validValues);
			return this;
		}

		PatternBuilder objMan(String argName, Object... validValues) {
			arg(argName, true, false, validValues);
			return this;
		}

		PatternBuilder arg(String argName, boolean isObject, boolean isOptional, Object... validValues) {
			String values = "[^']+";
			if (validValues != null && validValues.length > 0) {
				values = "[" + Strings.join("|", validValues) + "]";
			}
			regex += isOptional ? "(?:" : "";
			regex += argName + WHITESPACE;
			regex += "'(?<" + argName + ">" + values + ")'";
			regex += WHITESPACE;
			regex += isOptional ? ")?" : "";
			if (isObject) {
				availableObjectNames.add(argName);
			} else {
				availableTextNames.add(argName);
			}
			return this;
		}

		XtPattern build() {
			Pattern pattern = Pattern.compile(regex);
			return new XtPattern(pattern, this);
		}

		@Override
		public String toString() {
			return regex;
		}
	}

	static class Match {
		final Map<String, String> texts;
		final Map<String, IEObjectCoveringRegion> objectsWithOffset;
		final IEObjectCoveringRegion ocrReference;

		Match(Map<String, String> texts, Map<String, IEObjectCoveringRegion> objectsWithOffset,
				IEObjectCoveringRegion ocrReference) {

			this.texts = texts;
			this.objectsWithOffset = objectsWithOffset;
			this.ocrReference = ocrReference;
		}

		public String getText(String name) {
			assertTrue("Unknown name: " + name, texts.containsKey(name));
			return texts.get(name);
		}

		public IEObjectCoveringRegion getEObjectWithOffset(String name) {
			assertTrue("Unknown name: " + name, objectsWithOffset.containsKey(name));
			return objectsWithOffset.get(name);
		}
	}

	static public PatternBuilder builder() {
		return new PatternBuilder();
	}

	final Pattern pattern;
	final PatternBuilder builder;
	Matcher matcher;

	public XtPattern(Pattern pattern, PatternBuilder builder) {
		this.pattern = pattern;
		this.builder = builder;
	}

	public Match match(MethodData methodData, XtResourceEObjectAccessor resourceHandler) {
		String testMethod = methodData.getMethodNameWithArgs();
		int searchFromOffset = methodData.offset;
		matcher = pattern.matcher(testMethod);
		String msg = "The test method does not match the regex.\n";
		msg += "Test method: " + testMethod + "\n";
		msg += "Regex      : " + pattern.pattern();
		assertTrue(msg, matcher.find());

		Map<String, String> texts = new HashMap<>();
		Map<String, IEObjectCoveringRegion> objectsWithOffset = new HashMap<>();
		for (String name : builder.availableTextNames) {
			texts.put(name, matcher.group(name));
		}

		for (String name : builder.availableObjectNames) {
			String input = matcher.group(name);
			IEObjectCoveringRegion ocr = resourceHandler.getObjectCoveringRegion(searchFromOffset, input);
			objectsWithOffset.put(name, ocr);
		}
		IEObjectCoveringRegion ocrReference = resourceHandler.getObjectCoveringRegion(searchFromOffset, null);

		return new Match(texts, objectsWithOffset, ocrReference);
	}

}
