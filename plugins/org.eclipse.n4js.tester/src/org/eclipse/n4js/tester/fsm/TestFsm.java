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
package org.eclipse.n4js.tester.fsm;

/**
 * Representation of a test FSM.
 */
public interface TestFsm {

	/**
	 * Associates and initializes the FSM with a test session given as the unique test session ID argument.
	 *
	 * @param sessionId
	 *            the unique ID of the test session.
	 * @return the FSM.
	 */
	TestFsm initializeSession(final String sessionId);

	/**
	 * Starts the previously initialized FSM associated with the given test session ID.
	 *
	 * @param sessionId
	 *            the unique ID of the test session that was started.
	 * @return the FSM.
	 */
	TestFsm startSession(final String sessionId);

	/**
	 * Gracefully ends a previously initialized FSM associated with the given test session ID.
	 *
	 * @param sessionId
	 *            the unique ID of the test session that was gracefully finished.
	 * @return the FSM.
	 */
	TestFsm endSession(final String sessionId);

	/**
	 * Pings the session associated with its unique session identifier argument.
	 *
	 * @param sessionId
	 *            the ID of the session.
	 * @param timeout
	 *            the requested timeout in milliseconds.
	 * @return the FSM instance.
	 */
	TestFsm pingSession(final String sessionId, final long timeout);

	/**
	 * Notifies the FSM about a test started transition.
	 *
	 * @param testId
	 *            the unique ID of the test that has started.
	 * @param timeout
	 *            the requested timeout in milliseconds.
	 * @return the state machine instance.
	 */
	TestFsm startTest(final String testId, final long timeout);

	/**
	 * Marks a test as gracefully finished in the underlying FSM.
	 *
	 * @param testId
	 *            the unique identifier of the finished test.
	 * @return the FSM.
	 */
	TestFsm endTest(final String testId);

	/**
	 * Pings the test given with the unique test identifier argument.
	 *
	 * @param testId
	 *            the ID of the running test that was pinged.
	 * @param timeout
	 *            the requested timeout in milliseconds.
	 * @return the finite state machine instance.
	 */
	TestFsm pingTest(final String testId, final long timeout);

}
