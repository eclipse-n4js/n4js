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
package org.eclipse.n4js.generator.headless;

import org.eclipse.xtext.validation.Issue;

/**
 * Accepts Xtext validation issues that occur during compilation of a resource.
 */
public interface IssueAcceptor {
	/**
	 * Accept an issue.
	 *
	 * @param issue
	 *            the issue to accept
	 */
	void accept(Issue issue);

	/**
	 * Accept all of the given issues.
	 *
	 * @param issues
	 *            the issues to accept
	 */
	default void acceptAll(Iterable<? extends Issue> issues) {
		issues.forEach(issue -> accept(issue));
	}
}
