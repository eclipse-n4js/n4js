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
package org.eclipse.n4js.ide.tests.helper.server.xt.tests;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodData;
import org.junit.Test;

/**
 * Test for test methods:
 * <ul>
 * <li/>{@link XtIdeTest#noerrors(XtMethodData)}
 * <li/>{@link XtIdeTest#errors(XtMethodData)}
 * </ul>
 */
public class ErrorsTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/Errors");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ " + Errors.n4js.xt: probands/Errors\n"
				+ " ++ noerrors~0: test-0 〔probands/Errors/Errors.n4js.xt〕(test-0)\n"
				+ " ++ errors~0: test-1 〔probands/Errors/Errors.n4js.xt〕(test-1)\n"
				+ " ++ errors~1: test-2 〔probands/Errors/Errors.n4js.xt〕(test-2)\n"
				+ " ++ errors~2: test-3 〔probands/Errors/Errors.n4js.xt〕(test-3)\n"
				+ " ++ errors~3: test-4 〔probands/Errors/Errors.n4js.xt〕(test-4)\n"
				+ " ++ errors~4: test-5 〔probands/Errors/Errors.n4js.xt〕(test-5)\n"
				+ " ++ noerrors~1: test-6 〔probands/Errors/Errors.n4js.xt〕(test-6)\n"
				+ " ++ errors~5: test-7 〔probands/Errors/Errors.n4js.xt〕(test-7)\n"
				+ " ++ errors~6: test-8 〔probands/Errors/Errors.n4js.xt〕(test-8)\n"
				+ " ++ errors~7: test-9 〔probands/Errors/Errors.n4js.xt〕(test-9)");

		assertResult("(test-0)", "Passed: noerrors~0: test-0 〔probands/Errors/Errors.n4js.xt〕");
		assertResult("(test-1)", "Passed: errors~0: test-1 〔probands/Errors/Errors.n4js.xt〕");
		assertResult("(test-2)",
				"Failed: errors~1: test-2 〔probands/Errors/Errors.n4js.xt〕. expected:<[]> but was:<[Couldn't resolve reference to IdentifiableElement 'b01'.]>");
		assertResult("(test-3)", "Passed: errors~2: test-3 〔probands/Errors/Errors.n4js.xt〕");
		assertResult("(test-4)",
				"Failed: errors~3: test-4 〔probands/Errors/Errors.n4js.xt〕. expected:<[]> but was:<[int is not a subtype of B.]>");
		assertResult("(test-5)",
				"Failed: errors~4: test-5 〔probands/Errors/Errors.n4js.xt〕. expected:<[]> but was:<[Couldn't resolve reference to IdentifiableElement 'b01'.]>");
		assertResult("(test-6)",
				"Failed: noerrors~1: test-6 〔probands/Errors/Errors.n4js.xt〕. Expected no errors, but found: ['int is not a subtype of B.' at '42']");
		assertResult("(test-7)",
				"Failed: errors~5: test-7 〔probands/Errors/Errors.n4js.xt〕. No error found at: ");
		assertResult("(test-8)",
				"Failed: errors~6: test-8 〔probands/Errors/Errors.n4js.xt〕. No error found at: B");
	}

}
