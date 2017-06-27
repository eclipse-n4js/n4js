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
 * Representation of a test session ping event.
 */
public class SessionPingedEvent extends TestEvent {

	private final long timeout;
	private final Optional<String> comment;

	/**
	 * Creates a new session ping event instance with the given session ID and the timeout in milliseconds.
	 *
	 * @param sessionId
	 *            the unique ID of the test session
	 * @param timeout
	 *            the requested timeout for the test session in milliseconds.
	 */
	public SessionPingedEvent(final String sessionId, final long timeout) {
		this(sessionId, timeout, null);
	}

	/**
	 * Creates a new session ping event instance with the given session ID the timeout in milliseconds and an optional
	 * comment.
	 *
	 * @param sessionId
	 *            the unique ID of the test session
	 * @param timeout
	 *            the requested timeout for the test session in milliseconds.
	 * @param comment
	 *            the comment for the ping even. Optional, can be {@code null}.
	 */
	public SessionPingedEvent(final String sessionId, final long timeout, final String comment) {
		super(sessionId);
		this.timeout = timeout;
		this.comment = fromNullable(comment);
	}

	/**
	 * Returns with the session timeout in milliseconds.
	 *
	 * @return the timeout for the session.
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Returns with the optional comment for the ping session event.
	 *
	 * @return the comment for the event.
	 */
	public Optional<String> getComment() {
		return comment;
	}

}
