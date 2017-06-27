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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.xtext.validation.Issue;

/**
 * Collects issues during a compilation. The collected issues can be accessed using the {@link #getCollectedIssues()}
 * method.
 */
public class IssueCollector implements IssueAcceptor {

	private final List<Issue> collectedIssues = new LinkedList<>();

	@Override
	public void accept(Issue issue) {
		collectedIssues.add(issue);
	}

	/**
	 * Returns the issues collected during compilation.
	 *
	 * @return a collection containing the collected issues
	 */
	public List<Issue> getCollectedIssues() {
		return collectedIssues;
	}
}
