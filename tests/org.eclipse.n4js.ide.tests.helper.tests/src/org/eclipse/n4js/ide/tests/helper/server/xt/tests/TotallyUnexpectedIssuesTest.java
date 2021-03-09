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

import org.junit.Test;

/**
 * Test for issues in a file that neither expects warnings nor errors
 */
public class TotallyUnexpectedIssuesTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/TotallyUnexpectedIssues");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ " + TotallyUnexpectedIssues.n4js.xt: probands/TotallyUnexpectedIssues\n"
				// the test method 'nothing' is a dummy. Without it this file would be ignored.
				+ " ++ nothing~0:  〔probands/TotallyUnexpectedIssues/TotallyUnexpectedIssues.n4js.xt〕(nothing~0)");

		assertResults(
				"Failed: TotallyUnexpectedIssues.n4js.xt: probands/TotallyUnexpectedIssues. Unexpected issue found: 'Couldn't resolve reference to IdentifiableElement 'B1'.' at 'B1'");
	}

}
