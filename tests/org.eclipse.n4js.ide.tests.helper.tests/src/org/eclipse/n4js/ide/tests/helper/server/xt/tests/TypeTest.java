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

import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodData;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.junit.Test;

/**
 * Test for test methods:
 * <ul>
 * <li/>{@link XtIdeTest#type(XtMethodData)}
 * <li/>{@link XtIdeTest#typeArgs(XtMethodData)}
 * </ul>
 */
public class TypeTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/Type");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ " + Type.n4js.xt: probands/Type\n"
				+ " ++ type~0: test-1 〔probands/Type/Type.n4js.xt〕(test-1)\n"
				+ " ++ typeArgs~0: test-2 〔probands/Type/Type.n4js.xt〕(test-2)");

		assertResult("(test-1)", "Passed: type~0: test-1 〔probands/Type/Type.n4js.xt〕");
	}

}
