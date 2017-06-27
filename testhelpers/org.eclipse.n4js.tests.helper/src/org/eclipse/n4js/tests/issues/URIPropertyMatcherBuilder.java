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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.validation.Issue;

/**
 * A simple builder to create instances of {@link URIPropertyMatcher}.
 */
public class URIPropertyMatcherBuilder extends IssuePropertyMatcherBuilder {
	private final Function<Issue, URI> getActualValue;

	/**
	 * Creates a new builder that adds the newly created property matchers to the given issue matcher and that uses the
	 * given function to obtain the actual values from an issue.
	 *
	 * @param issueMatcher
	 *            the issue matcher to add the newly created property matchers to
	 * @param propertyName
	 *            the name of the property
	 * @param getActualValue
	 *            the function to obtain the actual value of a property from an issue
	 */
	public URIPropertyMatcherBuilder(IssueMatcher issueMatcher, String propertyName,
			Function<Issue, URI> getActualValue) {
		super(issueMatcher, propertyName);
		this.getActualValue = Objects.requireNonNull(getActualValue);
	}

	/**
	 * Returns a URI property matcher that matches a URI property against the given prefix.
	 *
	 * @param expectedPrefix
	 *            the prefix to match against
	 * @return the issue matcher
	 */
	public IssueMatcher startsWith(URI expectedPrefix) {
		return addPropertyMatcher(URIPropertyMatcher.Mode.StartsWith, expectedPrefix);
	}

	/**
	 * Returns a URI property matcher that matches a URI property against the given suffix.
	 *
	 * @param expectedSuffix
	 *            the suffix to match against
	 * @return the issue matcher
	 */
	public IssueMatcher endsWith(URI expectedSuffix) {
		return addPropertyMatcher(URIPropertyMatcher.Mode.EndsWith, expectedSuffix);
	}

	/**
	 * Returns a URI property matcher that matches a URI property against the given URI.
	 *
	 * @param expectedValue
	 *            the URI to match against
	 * @return the issue matcher
	 */
	public IssueMatcher equals(URI expectedValue) {
		return addPropertyMatcher(URIPropertyMatcher.Mode.Equals, expectedValue);
	}

	private IssueMatcher addPropertyMatcher(URIPropertyMatcher.Mode mode, URI expectedPattern) {
		return addPropertyMatcher(new URIPropertyMatcher(getPropertyName(), mode, expectedPattern, getActualValue));
	}
}
