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
package org.eclipse.n4js.tester;

import org.eclipse.n4js.tester.domain.TestTree;

/**
 * Representation of a {@link TestTree test tree} registry.
 */
public interface TestTreeRegistry {

	/**
	 * Registers a {@link TestTree test tree} instance for a test session.
	 *
	 * @param tree
	 *            the test tree to register.
	 */
	void registerTestTree(final TestTree tree);

	/**
	 * Returns with the {@link TestTree test tree} instance associated with a test session given with the unique test
	 * session ID.
	 *
	 * @param sessionId
	 *            the unique ID of the session.
	 * @return the test tree for the session.
	 */
	TestTree getTestTree(final String sessionId);

}
