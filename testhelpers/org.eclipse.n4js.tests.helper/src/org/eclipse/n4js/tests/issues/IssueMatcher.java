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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Joiner;

/**
 * Matches expectations against an instance of {@link Issue}. The expectations are specified using a simple builder
 * syntax.
 */
/**
 *
 */
public class IssueMatcher {
	private final List<IssuePropertyMatcher> propertyMatchers = new LinkedList<>();

	/**
	 * Adds an expectation for an issue's severity.
	 *
	 * @see Issue#getSeverity()
	 * @see Severity
	 *
	 * @param expectedSeverity
	 *            the expected severity
	 * @return this issue matcher
	 */
	public IssueMatcher severity(Severity expectedSeverity) {
		return addEqualsMatcher("severity", expectedSeverity, (Issue issue) -> issue.getSeverity());
	}

	/**
	 * Adds the expectation that an issue has severity {@link Severity#ERROR}.
	 *
	 * @see Issue#getSeverity()
	 * @see Severity
	 *
	 * @return this issue matcher
	 */
	public IssueMatcher error() {
		return severity(Severity.ERROR);
	}

	/**
	 * Adds the expectation that an issue has severity {@link Severity#WARNING}.
	 *
	 * @see Issue#getSeverity()
	 * @see Severity
	 *
	 * @return this issue matcher
	 */
	public IssueMatcher warning() {
		return severity(Severity.WARNING);
	}

	/**
	 * Adds the expectation that an issue has severity {@link Severity#INFO}.
	 *
	 * @see Issue#getSeverity()
	 * @see Severity
	 *
	 * @return this issue matcher
	 */
	public IssueMatcher info() {
		return severity(Severity.INFO);
	}

	/**
	 * Creates a builder for an issue's message.
	 *
	 * @see Issue#getMessage()
	 *
	 * @return an instance of {@link StringPropertyMatcher} that can be used to specify the actual expectation
	 */
	public StringPropertyMatcherBuilder message() {
		return new StringPropertyMatcherBuilder(this, "message", (Issue issue) -> issue.getMessage());
	}

	/**
	 * Adds an expectation for an issue's message.
	 *
	 * @see Issue#getMessage()
	 *
	 * @param expectedMessage
	 *            the expected message
	 * @return this issue matcher
	 */
	public IssueMatcher message(String expectedMessage) {
		return message().equals(expectedMessage);
	}

	/**
	 * Creates a builder for an issue's code.
	 *
	 * @see Issue#getCode()
	 *
	 * @return an instance of {@link StringPropertyMatcher} that can be used to specify the actual expectation
	 */
	public StringPropertyMatcherBuilder code() {
		return new StringPropertyMatcherBuilder(this, "code", (Issue issue) -> issue.getCode());
	}

	/**
	 * Adds an expectation for an issue's code.
	 *
	 * @see Issue#getCode()
	 *
	 * @param expectedCode
	 *            the expected code
	 * @return this issue matcher
	 */
	public IssueMatcher code(String expectedCode) {
		return code().equals(expectedCode);
	}

	/**
	 * Adds an expectation for an issue's type.
	 *
	 * @see Issue#getCode()
	 * @see CheckType
	 *
	 * @param expectedType
	 *            the expected type
	 * @return this issue matcher
	 */
	public IssueMatcher type(CheckType expectedType) {
		return addEqualsMatcher("type", expectedType, (Issue issue) -> issue.getType());
	}

	/**
	 * Creates a builder for an issue's URI.
	 *
	 * @see Issue#getUriToProblem()
	 *
	 * @return an instance of {@link URIPropertyMatcher} that can be used to specify the actual expectation
	 */
	public URIPropertyMatcherBuilder uri() {
		return new URIPropertyMatcherBuilder(this, "URI", (Issue issue) -> issue.getUriToProblem());
	}

	/**
	 * Adds an expectation for an issue's line number.
	 *
	 * @see Issue#getLineNumber()
	 *
	 * @param expectedLineNumber
	 *            the expected line number
	 * @return this issue matcher
	 */
	public IssueMatcher lineNumber(int expectedLineNumber) {
		return addEqualsMatcher("line number", expectedLineNumber, (Issue issue) -> issue.getLineNumber());
	}

	/**
	 * Adds an expectation for an issue's column.
	 *
	 * @see Issue#getColumn()
	 *
	 * @param expectedColumn
	 *            the expected column
	 * @return this issue matcher
	 */
	public IssueMatcher column(int expectedColumn) {
		return addEqualsMatcher("column", expectedColumn, (Issue issue) -> issue.getColumn());
	}

	/**
	 * Adds an expectation for an issue's line number and column.
	 *
	 * @see Issue#getLineNumber()
	 * @see Issue#getColumn()
	 *
	 * @param expectedLineNumber
	 *            the expected line number
	 * @param expectedColumn
	 *            the expected column
	 * @return this issue matcher
	 */
	public IssueMatcher at(int expectedLineNumber, int expectedColumn) {
		return lineNumber(expectedLineNumber).column(expectedColumn);
	}

	/**
	 * Adds an expectation for an issue's URI, line number and column.
	 *
	 * @see Issue#getUriToProblem()
	 * @see Issue#getLineNumber()
	 * @see Issue#getColumn()
	 *
	 * @param expectedURISuffix
	 *            the expected suffix of the URI
	 * @param expectedLineNumber
	 *            the expected line number
	 * @param expectedColumn
	 *            the expected column
	 * @return this issue matcher
	 */
	public IssueMatcher at(URI expectedURISuffix, int expectedLineNumber, int expectedColumn) {
		return lineNumber(expectedLineNumber).column(expectedColumn).uri().endsWith(expectedURISuffix);
	}

	/**
	 * Adds an expectation for an issue's URI, line number and column.
	 *
	 * @see Issue#getUriToProblem()
	 * @see Issue#getLineNumber()
	 * @see Issue#getColumn()
	 *
	 * @param expectedURISuffix
	 *            the expected suffix of the URI
	 * @param expectedLineNumber
	 *            the expected line number
	 * @param expectedColumn
	 *            the expected column
	 * @return this issue matcher
	 */
	public IssueMatcher at(String expectedURISuffix, int expectedLineNumber, int expectedColumn) {
		return at(URIUtils.toFileUri(expectedURISuffix), expectedLineNumber, expectedColumn);
	}

	/**
	 * Adds an expectation for an issue's offset.
	 *
	 * @see Issue#getOffset()
	 *
	 * @param expectedOffset
	 *            the expected offset
	 * @return this issue matcher
	 */
	public IssueMatcher offset(int expectedOffset) {
		return addEqualsMatcher("offset", expectedOffset, (Issue issue) -> issue.getOffset());
	}

	/**
	 * Adds an expectation for an issue's length.
	 *
	 * @see Issue#getLength()
	 *
	 * @param expectedLength
	 *            the expected length
	 * @return this issue matcher
	 */
	public IssueMatcher length(int expectedLength) {
		return addEqualsMatcher("length", expectedLength, (Issue issue) -> issue.getLength());
	}

	/**
	 * Adds an expectation for whether an issue is a syntax error.
	 *
	 * @see Issue#isSyntaxError()
	 *
	 * @param syntaxError
	 *            the expected value
	 * @return this issue matcher
	 */
	public IssueMatcher syntaxError(boolean syntaxError) {
		return addEqualsMatcher("syntax error", syntaxError, (Issue issue) -> issue.isSyntaxError());
	}

	private <T> IssueMatcher addEqualsMatcher(String propertyName, T expectedValue, Function<Issue, T> getActualValue) {
		return addPropertyMatcher(new IssuePropertyEqualsMatcher<>(propertyName, expectedValue, getActualValue));
	}

	IssueMatcher addPropertyMatcher(IssuePropertyMatcher propertyMatcher) {
		propertyMatchers.add(Objects.requireNonNull(propertyMatcher));
		return this;
	}

	/**
	 * Matches this issue matcher against the given issue.
	 *
	 * @param issue
	 *            the issue to match against
	 * @return <code>true</code> if the expectations in this matcher match the given issue and <code>false</code>
	 *         otherwise
	 */
	public boolean matches(Issue issue) {
		Objects.requireNonNull(issue);
		for (IssuePropertyMatcher propertyMatcher : propertyMatchers) {
			if (!propertyMatcher.matches(issue))
				return false;
		}
		return true;
	}

	/**
	 * Returns a list of explanations for each mismatched property matcher for the given issue.
	 *
	 * @param issue
	 *            the issue to match against
	 * @return a list of explanations
	 */
	public List<String> explainMismatch(Issue issue) {
		Objects.requireNonNull(issue);

		List<String> result = new LinkedList<>();
		for (IssuePropertyMatcher propertyMatcher : propertyMatchers) {
			if (!propertyMatcher.matches(issue))
				result.add(propertyMatcher.getMessage(issue));
		}
		return result;
	}

	/**
	 * Returns a description of all property matchers in this issue matcher.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return Joiner.on(", ")
				.join(propertyMatchers.stream().map((IssuePropertyMatcher matcher) -> matcher.getDescription())
						.iterator());
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
