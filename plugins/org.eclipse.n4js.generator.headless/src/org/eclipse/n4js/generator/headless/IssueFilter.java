/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator.headless;

import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.xtext.validation.Issue;

/**
 * Applies a given predicate and only passes the matching issues to a delegate.
 */
public class IssueFilter implements IssueAcceptor {

	private final IssueAcceptor delegate;

	private final Predicate<Issue> filter;

	/**
	 * Creates a new instance using the given predicate.
	 *
	 * @param delegate
	 *            the issue acceptor to pass the matched issues to, must not be <code>null</code>
	 * @param filter
	 *            the predicate to use, must not be <code>null</code>
	 */
	public IssueFilter(IssueAcceptor delegate, Predicate<Issue> filter) {
		this.delegate = Objects.requireNonNull(delegate);
		this.filter = Objects.requireNonNull(filter);
	}

	@Override
	public void accept(Issue issue) {
		if (filter.test(issue))
			delegate.accept(issue);
	}
}
