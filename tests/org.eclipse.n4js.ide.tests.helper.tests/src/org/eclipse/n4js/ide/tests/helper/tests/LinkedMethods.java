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

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.junit.Test;

/**
 * Test for test methods:
 * <ul>
 * <li/>{@link XtIdeTest#linkedName(MethodData)}
 * <li/>{@link XtIdeTest#linkedPathname(MethodData)}
 * <li/>{@link XtIdeTest#linkedFragment(MethodData)}
 * </ul>
 */
public class LinkedMethods extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/LinkedMethods");

		assertFiles("LinkedFragment.n4ts.xt\n"
				+ "LinkedName.n4ts.xt\n"
				+ "LinkedPathname.n4js.xt");
		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest\n"
				+ " + LinkedFragment.n4ts.xt: probands/LinkedMethods\n"
				+ " ++ linkedFragment~0: LF-1 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕(LF-1)\n"
				+ " ++ linkedFragment~1: LF-2 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕(LF-2)\n"
				+ " ++ linkedFragment~2: LF-3 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕(LF-3)\n"
				+ " ++ linkedFragment~3: LF-4 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕(LF-4)\n"
				+ " + LinkedName.n4ts.xt: probands/LinkedMethods\n"
				+ " ++ linkedName~0: LN-1 〔probands/LinkedMethods/LinkedName.n4ts.xt〕(LN-1)\n"
				+ " ++ linkedName~1: LN-2 〔probands/LinkedMethods/LinkedName.n4ts.xt〕(LN-2)\n"
				+ " + LinkedPathname.n4js.xt: probands/LinkedMethods\n"
				+ " ++ linkedPathname~0: LP-1 〔probands/LinkedMethods/LinkedPathname.n4js.xt〕(LP-1)\n"
				+ " ++ linkedPathname~1: LP-2 〔probands/LinkedMethods/LinkedPathname.n4js.xt〕(LP-2)");

		assertResult("(LF-1)", "Passed: linkedFragment~0: LF-1 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕");
		assertResult("(LF-2)", "Passed: linkedFragment~1: LF-2 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕");
		assertResult("(LF-3)",
				"Failed: linkedFragment~2: LF-3 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕. expected:<[Wrong Expectation]> but was:<[//@types.0]>");
		assertResult("(LF-4)", "Passed: linkedFragment~3: LF-4 〔probands/LinkedMethods/LinkedFragment.n4ts.xt〕");
		assertResult("(LN-1)", "Passed: linkedName~0: LN-1 〔probands/LinkedMethods/LinkedName.n4ts.xt〕");
		assertResult("(LN-2)",
				"Failed: linkedName~1: LN-2 〔probands/LinkedMethods/LinkedName.n4ts.xt〕. expected:<[Wrong Expectation]> but was:<[Comparable]>");
		assertResult("(LP-1)", "Passed: linkedPathname~0: LP-1 〔probands/LinkedMethods/LinkedPathname.n4js.xt〕");
		assertResult("(LP-2)",
				"Failed: linkedPathname~1: LP-2 〔probands/LinkedMethods/LinkedPathname.n4js.xt〕. expected:<[Wrong Expectation]> but was:<[S/x]>");
	}

}
