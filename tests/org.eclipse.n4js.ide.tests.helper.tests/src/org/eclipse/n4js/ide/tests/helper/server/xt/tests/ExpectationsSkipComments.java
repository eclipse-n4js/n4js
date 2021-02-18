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
 * Test for test method 'accessModifier'
 */
public class ExpectationsSkipComments extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/ExpectationsSkipComments");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest\n"
				+ " + ExpectationsSkipComments.n4js.xt: probands/ExpectationsSkipComments\n"
				+ " ++ accessModifier~0: test-1 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕(test-1)\n"
				+ " ++ definition~0: test-2 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕(test-2)\n"
				+ " ++ type~0: test-3 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕(test-3)");

		assertResult("(test-1)",
				"Passed: accessModifier~0: test-1 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕");
		assertResult("(test-2)",
				"Passed: definition~0: test-2 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕");
		assertResult("(test-3)",
				"Passed: type~0: test-3 〔probands/ExpectationsSkipComments/ExpectationsSkipComments.n4js.xt〕");
	}

}
