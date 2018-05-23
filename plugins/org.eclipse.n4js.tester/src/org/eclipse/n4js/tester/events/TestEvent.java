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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of a test event.
 */
public abstract class TestEvent {

	private final String sessionId;

	/**
	 * Creates a new test event with the given test session ID argument.
	 *
	 * @param sessionId
	 *            the ID of the associated test.
	 */
	protected TestEvent(final String sessionId) {
		this.sessionId = checkNotNull(sessionId, "sessionId");
	}

	/**
	 * Returns with the unique identifier associated with the current test event.
	 *
	 * @return the ID of the associated session.
	 */
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("|SID:");
		sb.append(sessionId);
		sb.append("|");
		return sb.toString();
	}
}
