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
 * Event for representing the successful outcome of a test session execution.
 */
public class SessionEndedEvent extends TestEvent {

	/**
	 * Creates a new instance session ended event with the given session identifier.
	 *
	 * @param sessionId
	 *            the unique identifier of the test session.
	 */
	public SessionEndedEvent(final String sessionId) {
		super(sessionId);
	}

}
