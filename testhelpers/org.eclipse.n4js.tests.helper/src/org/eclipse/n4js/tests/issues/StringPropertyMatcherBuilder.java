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
 * A simple builder to create instances of {@link StringPropertyMatcher}.
 */
public class StringPropertyMatcherBuilder extends IssuePropertyMatcherBuilder {
	private final Function<Issue, String> getActualValue;

	/**
	 * Creates a new builder that adds the newly created string property matchers to the given instance of
	 * {@link IssueMatcher}.
	 *
	 * @param matcher
	 *            the issue matcher to add the newly created string property matchers to
	 * @param propertyName
	 *            the name of the property
	 * @param getActualValue
	 *            a function to obtain the actual value of a string property of an instance of {@link Issue}
	 */
	public StringPropertyMatcherBuilder(IssueMatcher matcher, String propertyName,
			Function<Issue, String> getActualValue) {
		super(matcher, propertyName);
		this.getActualValue = Objects.requireNonNull(getActualValue);
	}

	/**
	 * Returns a string property matcher that matches a string property against the given prefix in a case sensitive
	 * manner.
	 *
	 * @param expectedPrefix
	 *            the prefix to match against
	 * @return the issue matcher
	 */
	public IssueMatcher startsWith(String expectedPrefix) {
		return addPropertyMatcher(StringPropertyMatcher.Mode.StartsWith, false, expectedPrefix);
	}

	/**
	 * Returns a string property matcher that matches a string property against the given suffix in a case sensitive
	 * manner.
	 *
	 * @param expectedSuffix
	 *            the suffix to match against
	 * @return the issue matcher
	 */
	public IssueMatcher endsWith(String expectedSuffix) {
		return addPropertyMatcher(StringPropertyMatcher.Mode.EndsWith, false, expectedSuffix);
	}

	/**
	 * Returns a string property matcher that matches a string property against the given string in a case sensitive
	 * manner.
	 *
	 * @param expectedValue
	 *            the string to match against
	 * @return the issue matcher
	 */
	public IssueMatcher equals(String expectedValue) {
		return addPropertyMatcher(StringPropertyMatcher.Mode.Equals, false, expectedValue);
	}

	private IssueMatcher addPropertyMatcher(StringPropertyMatcher.Mode mode, boolean ignoreCase,
			String expectedPattern) {
		return addPropertyMatcher(
				new StringPropertyMatcher(getPropertyName(), mode, ignoreCase, expectedPattern, getActualValue));
	}
}
