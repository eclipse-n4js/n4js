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
 * Matches against a specific issue property by calling the {@link Object#equals(Object)} method.
 */
public class IssuePropertyEqualsMatcher<T> extends IssuePropertyMatcherBase {
	private final T expectedValue;
	private final Function<Issue, T> getActualValue;

	/**
	 * Constructs a new matcher that matches an issue's property against the given expected value using the given
	 * function to obtain the actual property value from the issue.
	 *
	 * @param propertyName
	 *            the name of the property
	 * @param expectedValue
	 *            the expected value
	 * @param getActualValue
	 *            the function to obtain the actual property value
	 */
	public IssuePropertyEqualsMatcher(String propertyName, T expectedValue, Function<Issue, T> getActualValue) {
		super(propertyName);
		this.expectedValue = Objects.requireNonNull(expectedValue);
		this.getActualValue = Objects.requireNonNull(getActualValue);
	}

	@Override
	public boolean matches(Issue issue) {
		return expectedValue.equals(getActualValue.apply(issue));
	}

	@Override
	protected String explainMismatch(Issue issue) {
		return "Value '" + getActualValue.apply(issue) + "' is not equal to expected value'"
				+ expectedValue + "'";
	}

	@Override
	public String getDescription() {
		return "Property '" + getPropertyName() + "' equals '" + expectedValue + "'";
	}
}
