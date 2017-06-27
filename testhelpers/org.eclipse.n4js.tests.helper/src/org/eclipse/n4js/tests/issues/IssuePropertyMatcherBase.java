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

import org.eclipse.xtext.validation.Issue;

/**
 * Base class for issue property matches that need to refer to a property name for their error messages.
 */
public abstract class IssuePropertyMatcherBase implements IssuePropertyMatcher {
	private final String propertyName;

	/**
	 * Creates a new instance with the given name.
	 *
	 * @param propertyName
	 *            the name of the property
	 */
	protected IssuePropertyMatcherBase(String propertyName) {
		this.propertyName = Objects.requireNonNull(propertyName);
	}

	/**
	 * Returns the name of the property under examination.
	 *
	 * @return the property name
	 */
	protected String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getMessage(Issue issue) {
		return "Mismatched property '" + getPropertyName() + "': " + explainMismatch(issue);
	}

	/**
	 * Returns an explanation for why this matcher did not match the given issue.
	 *
	 * @param issue
	 *            the issue to be matched
	 *
	 * @return the explanation message
	 */
	protected abstract String explainMismatch(Issue issue);
}
