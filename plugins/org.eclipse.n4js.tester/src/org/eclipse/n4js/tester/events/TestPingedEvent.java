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

import static com.google.common.base.Optional.fromNullable;

import com.google.common.base.Optional;

/**
 * Representation of a test ping event.
 */
public class TestPingedEvent extends TestEvent {

	private final String testId;
	private final long timeout;
	private final Optional<String> comment;

	/**
	 * Creates a new test ping event with the given session ID, test ID and timeout arguments.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @param testId
	 *            the unique ID of the test.
	 * @param timeout
	 *            the timeout in milliseconds.
	 */
	public TestPingedEvent(final String sessionId, final String testId, final long timeout) {
		this(sessionId, testId, timeout, null);
	}

	/**
	 * Creates a new test ping event with the given arguments.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @param testId
	 *            the unique ID of the test.
	 * @param timeout
	 *            the timeout in milliseconds.
	 * @param comment
	 *            the comment for the ping. Optional, can be {@code null}.
	 */
	public TestPingedEvent(final String sessionId, final String testId, final long timeout, final String comment) {
		super(sessionId);
		this.testId = testId;
		this.timeout = timeout;
		this.comment = fromNullable(comment);
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
	 * Returns with the test timeout given in milliseconds.
	 *
	 * @return the timeout in milliseconds.
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Returns with the optional comment for the event.
	 *
	 * @return the comment.
	 */
	public Optional<String> getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return new StringBuilder(super.toString()).append("TID:").append(testId).append("|").toString();
	}

}
