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
package org.eclipse.n4js;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 */
public abstract class AbstractJSLibTest {

	final class BlackListStatement extends Statement {
		private final Statement base;

		BlackListStatement(Statement base) {
			this.base = base;

		}

		@Override
		public void evaluate() throws Throwable {
			try {
				base.evaluate();
			} catch (AssertionError e) {
				// expected
				return;
			}
			String code = TestCodeProvider.getContentsFromFileEntry(config.entry, config.resourceName);
			assertEquals("BLACKLISTS OUT OF SYNC\nExpected test to fail but succeeded", code, config.entry.getName());
		}
	}

	/**
	 * The test configuration.
	 */
	protected final JSLibSingleTestConfig config;

	/**
	 * Creates test with given config.
	 *
	 * @param config
	 *            must not be null
	 */
	protected AbstractJSLibTest(JSLibSingleTestConfig config) {
		this.config = config;
		JSActivationUtil.enableJSSupport();
	}

	/**
	 * Executes test, if test is on blacklist, it is supposed to fail.
	 */
	@Rule
	public TestRule blackListHandler = new TestRule() {
		@Override
		public Statement apply(final Statement base, Description description) {
			if (config.isBlackList()) {
				return new BlackListStatement(base);
			} else {
				return base;
			}
		}
	};

}
