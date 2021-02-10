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
 * Tests for test method 'definition'
 */
public class DefinitionTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/Definition");
		assertEventNames("[testRunStarted, testStarted, testFailure, testStarted, testFailure, testRunFinished]");
		assertResults(
				"Failed: definition~0: test 1 〔probands/Definition/Definition.n4js.xt〕. expected:<[wrong expectation]> but was:<[(test-project/src/Definition.n4js, [17:1 - 17:8])]>\n"
						+ "Failed: definition~1: test 2 〔probands/Definition/Definition.n4js.xt〕. expected:<[wrong expectation]> but was:<[(test-project/src/Definition.n4js, [17:1 - 17:8])]>");
	}

}
