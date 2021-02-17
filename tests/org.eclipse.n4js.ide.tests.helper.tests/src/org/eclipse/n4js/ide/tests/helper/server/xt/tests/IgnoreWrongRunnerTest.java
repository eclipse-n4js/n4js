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
 * Test for .xt files that specify another Runner than the one that picked up the .xt file.
 */
public class IgnoreWrongRunnerTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/IgnoreWrongRunner");
		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest\n"
				+ " + IgnoreWrongRunner.n4js.xt: probands/IgnoreWrongRunner: Specified runner does not match current runner");
		assertEventNames("testIgnored");
	}

}
