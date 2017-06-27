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
 * Event representing a test session failed unexpectedly.
 */
public class SessionFailedEvent extends TestEvent {

	private final Optional<String> comment;

	/**
	 * Creates a new event with the unique test session ID.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 */
	public SessionFailedEvent(final String sessionId) {
		this(sessionId, null);
	}

	/**
	 * Creates a new event with the unique test session ID and the optional comment.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @param comment
	 *            the optional comment for the failure.
	 */
	public SessionFailedEvent(final String sessionId, final String comment) {
		super(sessionId);
		this.comment = fromNullable(comment);
	}

	/**
	 * Returns with the optional comment describing the reason of the failure.
	 *
	 * @return the optional comment.
	 */
	public Optional<String> getComment() {
		return comment;
	}

}
