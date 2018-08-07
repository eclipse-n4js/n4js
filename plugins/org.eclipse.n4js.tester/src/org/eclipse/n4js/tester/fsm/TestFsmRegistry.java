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
 * Representation of an {@link TestFsm tester FSM} registry.
 */
public interface TestFsmRegistry {

	/**
	 * Creates a new FSM with the unique session ID argument and registers it into the cache.
	 *
	 * @param sessionId
	 *            the session ID for the FSM.
	 * @return return with the new FSM instance.
	 */
	TestFsm getTestFsm(final String sessionId);

	/**
	 * Returns with {@code true} if a tester session exists with the given unique session ID argument.
	 *
	 * @param sessionId
	 *            the unique identifier of the session.
	 * @return {@code true} if the test session exists, otherwise {@code false}.
	 */
	boolean isSessionExist(final String sessionId);

}
