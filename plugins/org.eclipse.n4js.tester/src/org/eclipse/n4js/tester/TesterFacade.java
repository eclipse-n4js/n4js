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
 * Facade representation for the N4 tester core infrastructure.
 */
public interface TesterFacade {

	/**
	 * Prepares a test session with the given {@link TestTree test tree} argument.
	 *
	 * @param tree
	 *            the test tree for the test session.
	 * @return port number used
	 */
	int prepareTestSession(final TestTree tree);

	/**
	 * Shuts down the test framework, currently this only entails shutting down the HTTP server used for gathering test
	 * results. This method does not properly terminate running test sessions and should therefore only be called once
	 * all test sessions have finished.
	 * <p>
	 * Usually it is not required to invoke this method (terminating the JVM is enough), but sometimes this might be
	 * required, for example in JUnit tests to make sure that one test method cannot influence another.
	 */
	void shutdownFramework();
}
