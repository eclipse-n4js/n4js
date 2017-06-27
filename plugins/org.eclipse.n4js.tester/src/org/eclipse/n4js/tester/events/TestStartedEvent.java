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

import static com.google.common.collect.ImmutableMap.copyOf;
import static java.util.Collections.emptyMap;

import java.util.Map;

/**
 * Representation of a test started event.
 */
public class TestStartedEvent extends TestEvent {

	private final String testId;
	private final Map<String, String> properties;
	private final long timeout;

	/**
	 * Creates a new test ping event with the given session and test ID and the timeout arguments.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @param testId
	 *            the unique ID of the test.
	 * @param timeout
	 *            the timeout for the test given in milliseconds.
	 */
	public TestStartedEvent(final String sessionId, final String testId, final long timeout) {
		this(sessionId, testId, timeout, null);
	}

	/**
	 * Creates a new test ping event with the given session ID, test ID and an optional test properties arguments.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @param testId
	 *            the unique ID of the test.
	 * @param timeout
	 *            the timeout for the test given in milliseconds.
	 * @param properties
	 *            additional properties for the test start event. Optional, can be {@code null}.
	 */
	public TestStartedEvent(final String sessionId, final String testId, final long timeout,
			final Map<String, String> properties) {

		super(sessionId);
		this.testId = testId;
		this.timeout = timeout;
		this.properties = null == properties ? emptyMap() : copyOf(properties);
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
	 * Returns with a view of properties for the test start event. Never {@code null}.
	 *
	 * @return a map of properties for the event.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return new StringBuilder(super.toString()).append("TID:").append(testId).append("|").toString();
	}

}
