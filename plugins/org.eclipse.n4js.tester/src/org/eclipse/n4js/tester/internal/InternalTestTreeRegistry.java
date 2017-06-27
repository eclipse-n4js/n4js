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
package org.eclipse.n4js.tester.internal;

import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestTree;

/**
 * Internal representation of a {@link TestTree test tree} registry.
 */
public interface InternalTestTreeRegistry extends TestTreeRegistry {

	/**
	 * Validates the content of the test tree associated with the session ID argument. Returns {@code true} if the
	 * content of the test tree is valid, otherwise returns with {@code false}.
	 * <p>
	 * A test tree is valid if each {@link TestCase test case} contained by the test tree has the corresponding
	 * {@link TestResult test result}.
	 *
	 * @param sessionId
	 *            the session ID which {@link TestTree test tree} should be validated.
	 */
	boolean validateTestTree(final String sessionId);

	/**
	 * Updates the content of a {@link TestTree test tree} associated with a test session, by appending the
	 * {@link TestResult test result} to one of the {@link TestCase test case} belongs to the test tree.
	 *
	 * @param sessionId
	 *            the unique ID of the session to identify the associated test tree.
	 * @param testId
	 *            the unique ID of the test case to update with the result argument.
	 * @param result
	 *            the result to update the test tree content with.
	 */
	void putTestResult(final String sessionId, final String testId, final TestResult result);

	/**
	 * Purges the {@link TestTree test tree} associated with the test session given with the session ID argument.
	 *
	 * @param sessionId
	 *            the unique ID of the test session.
	 */
	void purgeTestTree(final String sessionId);

	/**
	 * Purges the content of the entire registry.
	 */
	void purge();

}
