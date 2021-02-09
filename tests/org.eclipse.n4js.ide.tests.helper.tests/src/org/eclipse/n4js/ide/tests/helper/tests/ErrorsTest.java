/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.tests;

import org.junit.Test;

/**
 *
 */
public class ErrorsTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/Errors");

		assertSingleTestResult("(test-0)", "Passed: noerrors~0: test-0 〔probands/Errors/Errors.n4js.xt〕");
		assertSingleTestResult("(test-1)", "Passed: errors~0: test-1 〔probands/Errors/Errors.n4js.xt〕");
		assertSingleTestResult("(test-2)",
				"Failed: errors~1: test-2 〔probands/Errors/Errors.n4js.xt〕. expected:<[]> but was:<[Couldn't resolve reference to IdentifiableElement 'b01'.]>");
		assertSingleTestResult("(test-3)", "Passed: errors~2: test-3 〔probands/Errors/Errors.n4js.xt〕");
		assertSingleTestResult("(test-4)",
				"Failed: errors~3: test-4 〔probands/Errors/Errors.n4js.xt〕. expected:<[]> but was:<[int is not a subtype of B.]>");
	}

}
