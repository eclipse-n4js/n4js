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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.utils.Strings;

/**
 *
 */
public class XtMethodPattern {

	static public class PatternBuilder {
		final static String WHITESPACE = "\\s+";
		String regex = "";
		Map<String, Boolean> availableTextNames = new HashMap<>();
		Map<String, Boolean> availableObjectNames = new HashMap<>();

		PatternBuilder keyword(String keywordName) {
			if (!regex.isBlank()) {
				regex += WHITESPACE;
			}
			regex += keywordName;
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
				values = Strings.join("|", validValues);
			}

			regex += isOptional ? "(?:" : "";
			if (!regex.isBlank()) {
				regex += WHITESPACE;
			}
			regex += argName + WHITESPACE;
			regex += "'(?<" + argName + ">" + values + ")'";
			regex += isOptional ? ")?" : "";
			if (isObject) {
				availableObjectNames.put(argName, isOptional);
			} else {
				availableTextNames.put(argName, isOptional);
			}
			return this;
		}

		XtMethodPattern build() {
			Pattern pattern = Pattern.compile(regex);
			return new XtMethodPattern(pattern, this);
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

	public XtMethodPattern(Pattern pattern, PatternBuilder builder) {
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
		for (String name : builder.availableTextNames.keySet()) {
			boolean isOptional = builder.availableTextNames.get(name);
			String input = matcher.group(name);
			assertTrue("Mandatory argument missing: " + name, isOptional || input != null);
			texts.put(name, input);
		}

		for (String name : builder.availableObjectNames.keySet()) {
			boolean isOptional = builder.availableObjectNames.get(name);
			String input = matcher.group(name);
			IEObjectCoveringRegion ocr = null;
			if (isOptional) {
				if (input != null) {
					ocr = resourceHandler.getObjectCoveringRegion(searchFromOffset, input);
					assertTrue("Element not found: " + name, ocr != null);
				}
			} else {
				assertTrue("Mandatory argument missing: " + name, input != null);
				ocr = resourceHandler.getObjectCoveringRegion(searchFromOffset, input);
				assertTrue("Element not found: " + name, ocr != null);
			}
			objectsWithOffset.put(name, ocr);
		}
		IEObjectCoveringRegion ocrReference = resourceHandler.getObjectCoveringRegion(searchFromOffset, null);

		return new Match(texts, objectsWithOffset, ocrReference);
	}

}
