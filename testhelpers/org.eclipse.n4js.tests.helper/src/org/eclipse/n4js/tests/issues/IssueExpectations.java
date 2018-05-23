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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.xtext.validation.Issue;

/**
 * Represents expectations against {@link Issue}s. The expectations can be inverted by calling the {@link #invert()}
 * method. Inverting expectations negates the result of <code>matches*</code> method calls. This is useful for
 * <i>FIXME</i> expectations that should not fail if the actual expectations are violated, but when they are matched.
 */
public class IssueExpectations {
	private final Collection<IssueMatcher> issueMatchers = new LinkedList<>();
	private boolean inverted;

	/**
	 * Creates a new, non-inverted instance.
	 */
	public IssueExpectations() {
		this.inverted = false;
	}

	/**
	 * Inverts this matcher, that is, inverts the result that is returned from the matching methods.
	 */
	public void invert() {
		this.inverted = !this.inverted;
	}

	/**
	 * Creates a new issue matcher and adds it to this matcher.
	 *
	 * @return the newly created issue matcher
	 */
	public IssueMatcher add() {
		IssueMatcher issueMatcher = new IssueMatcher();
		issueMatchers.add(issueMatcher);
		return issueMatcher;
	}

	/**
	 * Matches the expectations in the added issues matchers against the given issues.
	 *
	 * @param issues
	 *            the issues to match the expectations against
	 * @param messages
	 *            if this parameter is not <code>null</code>, this method will add an explanatory message for each
	 *            mismatch
	 * @return <code>true</code> if and only if every expectation was matched against an issue and every issue in the
	 *         given collection was matched by an expectation
	 */
	public boolean matchesExactly(Collection<Issue> issues, List<String> messages) {
		Collection<Issue> issueCopy = new LinkedList<>(issues);
		Collection<IssueMatcher> matcherCopy = new LinkedList<>(issueMatchers);

		performMatching(issueCopy, matcherCopy, messages);
		if (inverted) {
			if (issueCopy.isEmpty() && matcherCopy.isEmpty()) {
				if (issueMatchers.isEmpty() && messages != null) {
					messages.add("Expected issues, but got nothing");
				} else {
					explainIssues(issues, messages, inverted);
					explainExpectations(issueMatchers, messages, inverted);
				}
				return false;
			}
		} else {
			if (!issueCopy.isEmpty() || !matcherCopy.isEmpty()) {
				explainIssues(issueCopy, messages, inverted);
				explainExpectations(matcherCopy, messages, inverted);
				return false;
			}
		}
		return true;
	}

	/**
	 * Matches the expectations in the added issues matchers against the given issues.
	 *
	 * @param issues
	 *            the issues to match the expectations against
	 * @param messages
	 *            if this parameter is not <code>null</code>, this method will add an explanatory message for each
	 *            mismatch
	 * @return <code>true</code> if and only if every expectation was matched against an issue
	 */
	public boolean matchesAllExpectations(Collection<Issue> issues, List<String> messages) {
		Collection<Issue> issueCopy = new LinkedList<>(issues);
		Collection<IssueMatcher> matcherCopy = new LinkedList<>(issueMatchers);

		performMatching(issueCopy, matcherCopy, messages);
		if (inverted) {
			if (matcherCopy.isEmpty()) {
				explainExpectations(issueMatchers, messages, inverted);
				return false;
			}
		} else {
			if (!matcherCopy.isEmpty()) {
				explainExpectations(matcherCopy, messages, inverted);
				return false;
			}
		}
		return false;
	}

	/**
	 * Matches the expectations in the added issues matchers against the given issues.
	 *
	 * @param issues
	 *            the issues to match the expectations against
	 * @param messages
	 *            if this parameter is not <code>null</code>, this method will add an explanatory message for each
	 *            mismatch
	 * @return <code>true</code> if and only if every issue in the given collection was matched by an expectation
	 */
	public boolean matchesAllIssues(Collection<Issue> issues, List<String> messages) {
		Collection<Issue> issueCopy = new LinkedList<>(issues);
		Collection<IssueMatcher> matcherCopy = new LinkedList<>(issueMatchers);

		performMatching(issueCopy, matcherCopy, messages);
		if (inverted) {
			if (issueCopy.isEmpty()) {
				explainIssues(issues, messages, inverted);
				return false;
			}
		} else {
			if (!issueCopy.isEmpty()) {
				explainIssues(issueCopy, messages, inverted);
				return false;
			}
		}
		return true;
	}

	private void explainIssues(Collection<Issue> unmatchedIssues, List<String> messages, boolean expected) {
		if (messages != null) {
			for (Issue issue : unmatchedIssues) {
				messages.add((expected ? "Expected issue: " : "Unexpected issue: ") + issue);
			}
		}
	}

	private void explainExpectations(Collection<IssueMatcher> unmatchedMatchers, List<String> messages,
			boolean expected) {
		if (messages != null) {
			for (IssueMatcher matcher : unmatchedMatchers) {
				messages.add((expected ? "Unexpectedly matched" : "Unmatched ") + " expectation: "
						+ matcher.getDescription());
			}
		}
	}

	private void performMatching(Collection<Issue> issues, Collection<IssueMatcher> matchers, List<String> messages) {
		Iterator<Issue> issueIt = issues.iterator();
		while (issueIt.hasNext() && !matchers.isEmpty()) {
			Issue issue = issueIt.next();

			Iterator<IssueMatcher> matcherIt = matchers.iterator();
			while (matcherIt.hasNext()) {
				IssueMatcher matcher = matcherIt.next();
				if (matcher.matches(issue)) {
					issueIt.remove();
					matcherIt.remove();
					break;
				} else if (messages != null) {
					messages.addAll(matcher.explainMismatch(issue));
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		if (inverted)
			result.append("Issues expected NOT to match:\n");
		else
			result.append("Issues expected to match:\n");

		for (IssueMatcher matcher : issueMatchers)
			result.append("    ").append(matcher.toString()).append("\n");

		return result.toString();
	}
}
