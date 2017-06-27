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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

/**
 * Class for representing a result (as an outcome) of a particular test.
 */
@JsonAutoDetect
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestResult implements Cloneable {

	@JsonProperty
	private TestStatus testStatus;
	private String expected;
	private String actual;
	private String message;
	private List<String> trace;
	private long elapsedTime;

	/* default */static TestResult copyOf(final TestResult result) {
		if (null == result) {
			return null;
		}
		final TestResult copy = new TestResult(result.testStatus);
		copy.expected = result.expected;
		copy.actual = result.actual;
		copy.message = result.message;
		copy.trace = null == result.trace ? newArrayList() : newArrayList(ImmutableList.copyOf(result.trace));
		copy.elapsedTime = result.elapsedTime;
		return copy;
	}

	/** Sole constructor. Used for serialization. */
	@SuppressWarnings("unused")
	private TestResult() {
	}

	/**
	 * Creates a test result instance with the status argument.
	 *
	 * @param testStatus
	 *            the test result status.
	 */
	public TestResult(final TestStatus testStatus) {
		this.testStatus = testStatus;
	}

	/**
	 * Returns with the expected result. Optional, can be {@code null}.
	 *
	 * @return the expected result.
	 */
	public String getExpected() {
		return expected;
	}

	/**
	 * Sets the expected value.
	 *
	 * @param expected
	 *            the new expected value.
	 */
	public void setExpected(final String expected) {
		this.expected = expected;
	}

	/**
	 * Gets the actual value. Optional, can be {@code null}.
	 *
	 * @return the actual value.
	 */
	public String getActual() {
		return actual;
	}

	/**
	 * Sets the actual value on the instance.
	 *
	 * @param actual
	 *            the new actual value.
	 */
	public void setActual(final String actual) {
		this.actual = actual;
	}

	/**
	 * Returns with the message for the result. Optional, can be {@code null}.
	 *
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message on the result.
	 *
	 * @param message
	 *            the new message.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Returns with the traces as a list of strings. Optional, can be {@code null}.
	 *
	 * @return the trace.
	 */
	public List<String> getTrace() {
		return trace;
	}

	/**
	 * Sets the traces.
	 *
	 * @param trace
	 *            the traces as list of strings.
	 */
	public void setTrace(final List<String> trace) {
		this.trace = trace;
	}

	/**
	 * Returns with the test status.
	 *
	 * @return the result status.
	 */
	public TestStatus getTestStatus() {
		return testStatus;
	}

	/**
	 * Returns with the elapsed time for the result.
	 *
	 * @return the elapsed time.
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Sets the elapsed time for the result.
	 *
	 * @param elapsedTime
	 *            the elapsed time.
	 */
	public void setElapsedTime(final long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Override
	public TestResult clone() throws CloneNotSupportedException {
		return copyOf(this);
	}

}
