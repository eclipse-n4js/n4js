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
 * <li/>{@link XtIdeTest#linkedName(XtMethodData)}
 * <li/>{@link XtIdeTest#linkedPathname(XtMethodData)}
 * <li/>{@link XtIdeTest#linkedFragment(XtMethodData)}
 * </ul>
 */
public class LinkingMethodsTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/LinkingMethods");

		assertFiles("LinkedFragment.n4jsd.xt\n"
				+ "LinkedName.n4jsd.xt\n"
				+ "LinkedPathname.n4js.xt");
		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ " + LinkedFragment.n4jsd.xt: probands/LinkingMethods\n"
				+ " ++ linkedFragment~0: LF-1 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕(LF-1)\n"
				+ " ++ linkedFragment~1: LF-2 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕(LF-2)\n"
				+ " ++ linkedFragment~2: LF-3 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕(LF-3)\n"
				+ " ++ linkedFragment~3: LF-4 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕(LF-4)\n"
				+ " + LinkedName.n4jsd.xt: probands/LinkingMethods\n"
				+ " ++ linkedName~0: LN-1 〔probands/LinkingMethods/LinkedName.n4jsd.xt〕(LN-1)\n"
				+ " ++ linkedName~1: LN-2 〔probands/LinkingMethods/LinkedName.n4jsd.xt〕(LN-2)\n"
				+ " + LinkedPathname.n4js.xt: probands/LinkingMethods\n"
				+ " ++ linkedPathname~0: LP-1 〔probands/LinkingMethods/LinkedPathname.n4js.xt〕(LP-1)\n"
				+ " ++ linkedPathname~1: LP-2 〔probands/LinkingMethods/LinkedPathname.n4js.xt〕(LP-2)");

		assertResult("(LF-1)", "Passed: linkedFragment~0: LF-1 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕");
		assertResult("(LF-2)", "Passed: linkedFragment~1: LF-2 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕");
		assertResult("(LF-3)",
				"Failed: linkedFragment~2: LF-3 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕. expected:<[Wrong Expectation]> but was:<[n4scheme:/primitives.n4jsd#/1/@topLevelTypes.6]>");
		assertResult("(LF-4)", "Passed: linkedFragment~3: LF-4 〔probands/LinkingMethods/LinkedFragment.n4jsd.xt〕");
		assertResult("(LN-1)", "Passed: linkedName~0: LN-1 〔probands/LinkingMethods/LinkedName.n4jsd.xt〕");
		assertResult("(LN-2)",
				"Failed: linkedName~1: LN-2 〔probands/LinkingMethods/LinkedName.n4jsd.xt〕. expected:<[Wrong Expectation]> but was:<[LinkedName.Comparable]>");
		assertResult("(LP-1)", "Passed: linkedPathname~0: LP-1 〔probands/LinkingMethods/LinkedPathname.n4js.xt〕");
		assertResult("(LP-2)",
				"Failed: linkedPathname~1: LP-2 〔probands/LinkingMethods/LinkedPathname.n4js.xt〕. expected:<[Wrong Expectation]> but was:<[S/x]>");
	}

}
