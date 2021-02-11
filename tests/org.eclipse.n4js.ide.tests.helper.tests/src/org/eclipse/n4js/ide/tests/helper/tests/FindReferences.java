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
 * Test for test method {@link XtIdeTest#findReferences(MethodData)}
 */
public class FindReferences extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/FindReferences");

		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest\n"
				+ " + FindReferences.n4js.xt: probands/FindReferences\n"
				+ " ++ findReferences~0: test-1 〔probands/FindReferences/FindReferences.n4js.xt〕(test-1)\n"
				+ " ++ findReferences~1: test-2 〔probands/FindReferences/FindReferences.n4js.xt〕(test-2)");

		assertResult("(test-1)", "Passed: findReferences~0: test-1 〔probands/FindReferences/FindReferences.n4js.xt〕");
		assertResult("(test-2)", "Passed: findReferences~1: test-2 〔probands/FindReferences/FindReferences.n4js.xt〕");
	}

}
