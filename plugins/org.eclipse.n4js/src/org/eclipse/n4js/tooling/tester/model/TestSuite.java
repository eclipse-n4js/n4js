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
package org.eclipse.n4js.tooling.tester.model;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterators.concat;
import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.uniqueIndex;
import static java.util.Collections.unmodifiableList;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;

/**
 * Representation of a test suite.
 */
@JsonAutoDetect
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestSuite implements TestElement, Iterable<TestCase>, Comparable<TestSuite> {

	@JsonProperty
	private String name;
	private List<TestCase> testCases = newArrayList();
	private List<TestSuite> children = newArrayList();
	private Map<ID, TestCase> testCaseIndex = newHashMap();

	/* default */static TestSuite copyOf(final TestSuite suite) {
		final TestSuite copy = new TestSuite(suite.name);
		copy.testCases = newArrayList(transform(suite.testCases, input -> TestCase.copyOf(input)));
		copy.children = newArrayList(transform(suite.children, input -> copyOf(input)));
		copy.testCaseIndex = newHashMap(uniqueIndex(copy.testCases, testCase -> testCase.getId()));
		return copy;
	}

	/** Sole constructor. Used for serialization. */
	@SuppressWarnings("unused")
	private TestSuite() {
	}

	/**
	 * Creates a new test suit with the given name.
	 *
	 * @param name
	 *            the name of the test suit.
	 */
	public TestSuite(final String name) {
		this.name = name;
	}

	/**
	 * Returns with the wrapped test suits. Optional, can be {@code null}.
	 *
	 * @return the test suits.
	 */
	public List<TestCase> getTestCases() {
		return unmodifiableList(testCases);
	}

	/**
	 * Adds the given test case to the test suite.
	 *
	 * @param testCaseToAdd
	 *            the test case to be added. Cannot be {@code null}.
	 */
	public void add(final TestCase testCaseToAdd) {
		testCases.add(testCaseToAdd);
		testCaseIndex.put(testCaseToAdd.getId(), testCaseToAdd);
	}

	/**
	 * Adds the given test cases to the test suite.
	 *
	 * @param testCasesToAdd
	 *            the test cases to add to the current test suite.
	 */
	public void addAll(final Collection<? extends TestCase> testCasesToAdd) {
		this.testCases.addAll(testCasesToAdd);
		testCasesToAdd.forEach(t -> testCaseIndex.put(t.getId(), t));
	}

	/**
	 * Returns with the children test suites. If any. May return with an empty list of {@link TestSuite test suite}
	 * instances but never {@code null}.
	 *
	 * @return the child test suites for the test suite.
	 */
	public List<TestSuite> getChildren() {
		return children;
	}

	/**
	 * Sets the test cases.
	 *
	 * @param testCases
	 *            the test cases.
	 */
	public void setTestCases(final List<TestCase> testCases) {
		this.testCases = null == testCases ? newArrayList() : testCases;
		final Map<ID, TestCase> mapping = uniqueIndex(this.testCases, testCase -> testCase.getId());
		this.testCaseIndex = mapping.isEmpty() ? newHashMap() : newHashMap(mapping);
	}

	/**
	 * Returns with the name of the test suite.
	 *
	 * @return the name of the test suite.
	 */
	public String getName() {
		return name;
	}

	@Override
	public TestSuite clone() throws CloneNotSupportedException {
		return copyOf(this);
	}

	@Override
	public Iterator<TestCase> iterator() {
		return concat(testCases.iterator(),
				concat(transform(children.iterator(), new Function<TestSuite, Iterator<TestCase>>() {
					@Override
					public Iterator<TestCase> apply(final TestSuite testSuite) {
						return testSuite.iterator();
					}
				})));
	}

	@Override
	public Spliterator<TestCase> spliterator() {
		return Spliterators.spliterator(iterator(), Iterators.size(iterator()), 0);
	}

	@Override
	public String toString() {
		return toString(1);
	}

	private String toString(final int childIdentCount) {
		final StringBuilder sb = new StringBuilder();
		final int testCaseCount = getTestCases().size();
		sb.append(name);
		sb.append(" [").append(testCaseCount).append("]");
		if (isEmpty(children)) {
			return sb.toString();
		} else {
			for (final TestSuite suite : children) {
				sb.append("\n");
				for (int i = 0; i < childIdentCount; i++) {
					sb.append("\t");
				}
				sb.append(suite.toString(childIdentCount + 1));
			}
		}
		return sb.toString();

	}

	/**
	 * Returns with the test case identified with the unique test case ID argument. Or {@code null} if the test case
	 * cannot be found among the test cases with the given ID.
	 *
	 * @param testCaseId
	 *            the unique ID of the test case.
	 * @return the test case from the test suites (including the sub test suites as well) or {@code null} if no such
	 *         test case can be found.
	 */
	public TestCase getTestCase(final ID testCaseId) {
		return getTestCase(this, testCaseIndex.get(testCaseId), testCaseId);
	}

	private TestCase getTestCase(final TestSuite testSuite, TestCase testCase, final ID testCaseId) {
		if (null != testCase) {
			return testCase;
		}
		testCase = testSuite.testCaseIndex.get(testCaseId);
		if (null != testCase) {
			return testCase;
		}
		for (final TestSuite child : testSuite.children) {
			testCase = getTestCase(child, testCase, testCaseId);
			if (null != testCase) {
				return testCase;
			}
		}
		return testCase;
	}

	@Override
	public int compareTo(final TestSuite o) {
		return null == o ? -1 : nullToEmpty(name).compareTo(nullToEmpty(o.name));
	}

	/**
	 * Sorts the wrapped test cases by the test cases names based on alphabetical ordering.
	 */
	public void sort() {
		Collections.sort(testCases);
	}
}
