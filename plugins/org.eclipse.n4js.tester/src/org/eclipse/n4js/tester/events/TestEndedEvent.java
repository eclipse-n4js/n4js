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
package org.eclipse.n4js.tester.events;

import org.eclipse.n4js.tester.domain.TestResult;

/**
 * Representation of a test ended event.
 */
public class TestEndedEvent extends TestEvent {

	private final String testId;
	private final TestResult result;

	/**
	 * Creates a new event with the unique IDs of the session and the test and with the outcome of the test execution as
	 * the result.
	 *
	 * @param sessionId
	 *            the unique ID of the associated test session.
	 * @param testId
	 *            the unique ID of the finished test.
	 * @param result
	 *            the test result for the event.
	 */
	public TestEndedEvent(final String sessionId, final String testId, final TestResult result) {
		super(sessionId);
		this.testId = testId;
		this.result = result;
	}

	/**
	 * Returns with the unique ID of the test.
	 *
	 * @return the test ID.
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * Returns with the result as the outcome of a test execution.
	 *
	 * @return the test result for the event.
	 */
	public TestResult getResult() {
		return result;
	}

	@Override
	public String toString() {
		return new StringBuilder(super.toString()).append("TID:").append(testId).append("|").toString();
	}

}
