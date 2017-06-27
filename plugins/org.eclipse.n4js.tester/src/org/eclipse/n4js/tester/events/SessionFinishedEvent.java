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

/**
 * Event indicating that a test session has received the {@link SessionEndedEvent session ended event} and this is just
 * the justification of the successful session termination. This event should not be posted by any RESTful resources but
 * only from the underlying tester finite state machine which is responsible to verify the state transitions.
 */
public class SessionFinishedEvent extends TestEvent {

	/**
	 * Creates a new session finished event instance to verify the correctness of an ended test session.
	 *
	 * @param sessionId
	 *            the session ID.
	 */
	public SessionFinishedEvent(final String sessionId) {
		super(sessionId);
	}

}
