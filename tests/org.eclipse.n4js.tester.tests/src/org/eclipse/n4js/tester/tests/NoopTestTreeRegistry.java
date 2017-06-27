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
package org.eclipse.n4js.tester.tests;

import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;

/**
 * NOOP implementation for tests.
 */
public class NoopTestTreeRegistry implements InternalTestTreeRegistry {

	@Override
	public void registerTestTree(final TestTree tree) {
		// NOOP
	}

	@Override
	public TestTree getTestTree(final String sessionId) {
		return null;
	}

	@Override
	public boolean validateTestTree(final String sessionId) {
		return true;
	}

	@Override
	public void putTestResult(final String sessionId, final String testId, final TestResult result) {
		// NOOP
	}

	@Override
	public void purgeTestTree(final String sessionId) {
		// NOOP
	}

	@Override
	public void purge() {
		// NOOP
	}

}
