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

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodData;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.junit.Test;

/**
 * Tests for test method {@link XtIdeTest#definition(XtMethodData)}
 */
public class SuppressedIssuesTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/SuppressedIssues", N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS);
		assertEventNames("testRunStarted\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testRunFinished");
		assertResults("Passed: nowarnings~0:  〔probands/SuppressedIssues/SuppressedIssues.n4js.xt〕");
	}

}
