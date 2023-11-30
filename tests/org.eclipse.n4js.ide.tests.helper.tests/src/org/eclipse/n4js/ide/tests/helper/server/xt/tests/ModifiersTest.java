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
 * Tests for test method {@link XtIdeTest#definition(XtMethodData)}
 */
public class ModifiersTest extends AbstractXtParentRunnerTest {

	@Test
	public void test() throws Exception {
		run("probands/Modifiers");
		assertEventNames("testSuiteStarted - org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ "testSuiteStarted - FIXME.n4js.xt: probands/Modifiers\n"
				+ "testStarted\n"
				+ "testFailure\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testSuiteFinished - FIXME.n4js.xt: probands/Modifiers\n"
				+ "testSuiteStarted - IGNORE.n4js.xt: probands/Modifiers\n"
				+ "testIgnored\n"
				+ "testIgnored\n"
				+ "testSuiteFinished - IGNORE.n4js.xt: probands/Modifiers\n"
				+ "testSuiteFinished - org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup");
		assertResults(
				"Failed: definition~0: FIXME test-1 〔probands/Modifiers/FIXME.n4js.xt〕. Test fixed!\n"
						+ "Passed: definition~1: FIXME test-2 〔probands/Modifiers/FIXME.n4js.xt〕\n"
						+ "Ignored: definition~0: ! test-1 〔probands/Modifiers/IGNORE.n4js.xt〕\n"
						+ "Ignored: definition~1: ! test-2 〔probands/Modifiers/IGNORE.n4js.xt〕");
	}

}
