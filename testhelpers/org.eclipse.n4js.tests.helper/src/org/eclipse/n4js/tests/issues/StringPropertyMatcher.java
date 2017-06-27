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
package org.eclipse.n4js.tests.issues;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.xtext.validation.Issue;

/**
 * Matches an issue against an expectation for a string property. String expectations can test for prefix, suffix, and
 * equals in a case sensitive or case insensitive manner.
 */
public class StringPropertyMatcher extends IssuePropertyMatcherBase {
	/**
	 * Enumerates the match modes.
	 */
	public static enum Mode {
		/**
		 * Prefix matching mode.
		 */
		StartsWith,
		/**
		 * Suffix matching mode.
		 */
		EndsWith,
		/**
		 * Equals matching mode.
		 */
		Equals
	}

	private final Mode mode;
	private final boolean ignoreCase;
	private final String expectedPattern;
	private final Function<Issue, String> getActualValue;

	/**
	 * Creates a new string property matcher.
	 *
	 * @param propertyName
	 *            the name of the property
	 * @param mode
	 *            the matching mode
	 * @param ignoreCase
	 *            whether the matching is performed case insensitive or case sensitive
	 * @param expectedPattern
	 *            the expected pattern (the actual interpretation depends on the matching mode)
	 * @param getActualValue
	 *            a function to obtain the actual value to match against
	 */
	protected StringPropertyMatcher(String propertyName, Mode mode, Boolean ignoreCase, String expectedPattern,
			Function<Issue, String> getActualValue) {
		super(propertyName);
		this.mode = mode;
		this.ignoreCase = Objects.requireNonNull(ignoreCase);
		this.expectedPattern = safeGetValue(expectedPattern);
		this.getActualValue = Objects.requireNonNull(getActualValue);
	}

	@Override
	public boolean matches(Issue issue) {
		String actualValue = safeGetValue(getActualValue.apply(issue));
		switch (mode) {
		case StartsWith:
			return actualValue.startsWith(expectedPattern);
		case EndsWith:
			return actualValue.endsWith(expectedPattern);
		case Equals:
			return actualValue.equals(expectedPattern);
		}

		// This should never happen lest we extended the enum without adding a case above!
		throw new IllegalStateException("Unknown string property matching mode: " + mode);
	}

	private String safeGetValue(String value) {
		if (value == null)
			return null;
		return ignoreCase ? value.toLowerCase() : value;
	}

	@Override
	protected String explainMismatch(Issue issue) {
		String actualValue = safeGetValue(getActualValue.apply(issue));
		switch (mode) {
		case StartsWith:
			return "'" + expectedPattern + "' is not a prefix of value '" + actualValue + "'";
		case EndsWith:
			return "'" + expectedPattern + "' is not a suffix of value '" + actualValue + "'";
		case Equals:
			return "Value '" + actualValue + "' is not equal to expected value'"
					+ expectedPattern + "'";
		}

		// This should never happen lest we extended the enum without adding a case above!
		throw new IllegalStateException("Unknown string property matching mode: " + mode);
	}

	@Override
	public String getDescription() {
		switch (mode) {
		case StartsWith:
			return "'" + expectedPattern + "' is a prefix of property '" + getPropertyName() + "'";
		case EndsWith:
			return "'" + expectedPattern + "' is a suffix of property '" + getPropertyName() + "'";
		case Equals:
			return "Property '" + getPropertyName() + "' is equal to expected value'"
					+ expectedPattern + "'";
		}

		// This should never happen lest we extended the enum without adding a case above!
		throw new IllegalStateException("Unknown string property matching mode: " + mode);
	}
}
