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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.validation.IssueCodes;
import org.junit.Test;

/**
 * Tests for issue configurations in Xt setups.
 */
public class IssueConfigurationTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {

		// The test file IssuesConfiguration.n4js.xt is based on the assumption that certain issues are suppressed in
		// all tests by default. Make sure this test fails if the default suppression no longer meets this expectation:
		Set<String> suppressed = N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS;
		assertTrue(suppressed.contains(IssueCodes.DFG_NULL_DEREFERENCE));
		assertTrue(suppressed.contains(IssueCodes.CFG_LOCAL_VAR_UNUSED));
		assertFalse(suppressed.contains(IssueCodes.CLF_NAME_DOES_NOT_START_UPPERCASE));
		assertFalse(suppressed.contains(IssueCodes.CLF_NAME_DOES_NOT_START_LOWERCASE));

		run("probands/IssueConfiguration", suppressed);
		assertEventNames("testRunStarted\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testRunFinished");
		assertResults("Passed: nowarnings~0:  〔probands/IssueConfiguration/IssueConfiguration.n4js.xt〕\n"
				+ "Passed: warnings~0:  〔probands/IssueConfiguration/IssueConfiguration.n4js.xt〕\n"
				+ "Passed: warnings~1:  〔probands/IssueConfiguration/IssueConfiguration.n4js.xt〕\n"
				+ "Passed: nowarnings~1:  〔probands/IssueConfiguration/IssueConfiguration.n4js.xt〕");
	}

}
