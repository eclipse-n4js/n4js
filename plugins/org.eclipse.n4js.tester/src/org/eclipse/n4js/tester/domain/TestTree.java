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
package org.eclipse.n4js.tester.domain;

import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterators.concat;
import static com.google.common.collect.Iterators.size;
import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.valueOf;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

/**
 * Representation of a test tree.
 */
@JsonAutoDetect
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestTree implements TestElement, Iterable<TestCase> {

	private List<TestSuite> suites = newArrayList();
	@JsonProperty
	private ID sessionId;
	@JsonIgnore
	private String name;

	/* default */static TestTree copyOf(final TestTree tree) {
		return new TestTree(
				ID.copyOf(tree.sessionId),
				newArrayList(transform(tree.suites, ts -> TestSuite.copyOf(ts))),
				tree.name);
	}

	/** Sole constructor. Used for serialization. */
	@SuppressWarnings("unused")
	private TestTree() {
	}

	/**
	 * Creates a new test tree instance with the session ID and an empty array of {@link TestSuite test suite}s.
	 *
	 * @param sessionId
	 *            the unique session ID associated with the test tree.
	 */
	public TestTree(final ID sessionId) {
		this(sessionId, newArrayList());
	}

	/**
	 * Creates a new test tree instance with the session ID and the test suites argument.
	 *
	 * @param sessionId
	 *            the unique session ID associated with the test tree.
	 * @param suites
	 *            the test suites.
	 */
	public TestTree(final ID sessionId, final Collection<? extends TestSuite> suites) {
		this(sessionId, suites, valueOf(sessionId));
	}

	/**
	 * Creates a new test tree instance with the session ID and the test suites argument.
	 *
	 * @param sessionId
	 *            the unique session ID associated with the test tree.
	 * @param suites
	 *            the test suites.
	 * @param name
	 *            the human readable name of the test tree.
	 */
	public TestTree(final ID sessionId, final Collection<? extends TestSuite> suites, final String name) {
		this.name = name;
		this.suites = newArrayList(ImmutableList.copyOf(suites));
		this.sessionId = sessionId;
	}

	/**
	 * Returns with the test suites for the test tree.
	 *
	 * @return the test suites.
	 */
	public List<TestSuite> getSuites() {
		return suites;
	}

	/**
	 * Returns with the associated session's unique identifier.
	 *
	 * @return the ID of the associated session.
	 */
	public ID getSessionId() {
		return sessionId;
	}

	/**
	 * Returns with the test case associated with the given test case ID argument. Sugar for {@link #getTestCase(ID)}.
	 *
	 * @param testCaseId
	 *            the unique ID of the test case.
	 * @return the test case for the given test case ID or {@code null} if the test case cannot be found.
	 */
	public TestCase getTestCase(final String testCaseId) {
		return getTestCase(new ID(testCaseId));
	}

	/**
	 * Returns with the test case identified with the unique test case ID argument. Or {@code null} if the test case
	 * cannot be found with the given ID.
	 *
	 * @param testCaseId
	 *            the unique ID of the test case.
	 * @return the test case from the test tree or {@code null} if no such test case can be found.
	 */
	public TestCase getTestCase(final ID testCaseId) {
		for (final TestSuite suite : suites) {
			final TestCase testCase = suite.getTestCase(testCaseId);
			if (null != testCase) {
				return testCase;
			}
		}
		return null;
	}

	/**
	 * Returns with the human readable name of the test tree. In most cases it could return with the session ID.
	 *
	 * @return the human readable name of the test tree.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sorts the encapsulated test suites by their name in alphabetic order, then returns with the current (sorted)
	 * instance.
	 *
	 * @return the current sorted instance.
	 */
	public TestTree sort() {
		Collections.sort(suites);
		suites.forEach(s -> s.sort());
		return this;
	}

	@Override
	public TestTree clone() throws CloneNotSupportedException {
		return copyOf(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestTree other = (TestTree) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}

	@Override
	public Iterator<TestCase> iterator() {
		return concat(transform(suites.iterator(),
				new Function<TestSuite, Iterator<TestCase>>() {
					@Override
					public Iterator<TestCase> apply(final TestSuite suite) {
						return suite.iterator();
					}
				}));
	}

	@Override
	public Spliterator<TestCase> spliterator() {
		final int size = size(iterator());
		return Spliterators.spliterator(iterator(), size, 0);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Session ID: ").append(sessionId.toString());
		if (!isEmpty(suites)) {
			sb.append("\nSuites:");
			for (final TestSuite suite : suites) {
				sb.append("\n").append(suite);
			}
		}
		return sb.toString();
	}

}
