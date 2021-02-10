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
public class WarningsTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/Warnings");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest\n"
				+ " + Warnings.n4js.xt: probands/Warnings\n"
				+ " ++ noerrors~0: test-00 〔probands/Warnings/Warnings.n4js.xt〕(test-00)\n"
				+ " ++ nowarnings~0: test-0 〔probands/Warnings/Warnings.n4js.xt〕(test-0)\n"
				+ " ++ warnings~0: test-1 〔probands/Warnings/Warnings.n4js.xt〕(test-1)\n"
				+ " ++ warnings~1: test-2 〔probands/Warnings/Warnings.n4js.xt〕(test-2)\n"
				+ " ++ warnings~2: test-3 〔probands/Warnings/Warnings.n4js.xt〕(test-3)\n"
				+ " ++ warnings~3: test-4 〔probands/Warnings/Warnings.n4js.xt〕(test-4)\n"
				+ " ++ warnings~4: test-5 〔probands/Warnings/Warnings.n4js.xt〕(test-5)\n"
				+ " ++ nowarnings~1: test-6 〔probands/Warnings/Warnings.n4js.xt〕(test-6)\n"
				+ " ++ warnings~5: test-7 〔probands/Warnings/Warnings.n4js.xt〕(test-7)\n"
				+ " ++ warnings~6: test-8 〔probands/Warnings/Warnings.n4js.xt〕(test-8)");

		assertResult("(test-00)", "Passed: noerrors~0: test-00 〔probands/Warnings/Warnings.n4js.xt〕");
		assertResult("(test-0)", "Passed: nowarnings~0: test-0 〔probands/Warnings/Warnings.n4js.xt〕");
		assertResult("(test-1)", "Passed: warnings~0: test-1 〔probands/Warnings/Warnings.n4js.xt〕");
		assertResult("(test-2)",
				"Failed: warnings~1: test-2 〔probands/Warnings/Warnings.n4js.xt〕. expected:<[]> but was:<[Unnecessary cast from null to B]>");
		assertResult("(test-3)", "Passed: warnings~2: test-3 〔probands/Warnings/Warnings.n4js.xt〕");
		assertResult("(test-4)",
				"Failed: warnings~3: test-4 〔probands/Warnings/Warnings.n4js.xt〕. expected:<[]> but was:<[Unnecessary cast from undefined to B]>");
		assertResult("(test-5)",
				"Failed: warnings~4: test-5 〔probands/Warnings/Warnings.n4js.xt〕. No warning found at: b01");
		assertResult("(test-6)",
				"Failed: nowarnings~1: test-6 〔probands/Warnings/Warnings.n4js.xt〕. Expected no warnings, but found: ['Unnecessary cast from null to B' at 'null as B']");
		assertResult("(test-7)",
				"Failed: warnings~5: test-7 〔probands/Warnings/Warnings.n4js.xt〕. Unexpected warning found at: 'Unnecessary cast from null to B' at 'null as B'");
		assertResult("(test-8)",
				"Failed: warnings~6: test-8 〔probands/Warnings/Warnings.n4js.xt〕. No warning found at: B");
	}

}
