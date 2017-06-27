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

import org.eclipse.xtext.validation.Issue;

/**
 * Allows to delegate issues to two issue acceptors.
 */
public class IssueAcceptorTee implements IssueAcceptor {

	private final IssueAcceptor first;

	private final IssueAcceptor second;

	/**
	 * Creates a new instance that delegates to the given two issue acceptors.
	 *
	 * @param first
	 *            the first issue acceptor
	 * @param second
	 *            the second issue acceptor
	 */
	public IssueAcceptorTee(IssueAcceptor first, IssueAcceptor second) {
		this.first = Objects.requireNonNull(first);
		this.second = Objects.requireNonNull(second);
	}

	@Override
	public void accept(Issue issue) {
		first.accept(issue);
		second.accept(issue);
	}
}
