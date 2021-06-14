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
package org.eclipse.n4js.spec.tests;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.setup.workspace.WorkspaceDefaultsSetup;
import org.junit.runner.RunWith;

/**
 * TEMPORARY (for local testing)
 *
 * Either remove this test class or move the .d.ts Xt tests from "xt-tests/dts" to their own new top-level folder.
 */
@XpectSuiteClasses({
		XtIdeTest.class,
		WorkspaceDefaultsSetup.class
})
@RunWith(XtParentRunner.class)
public class TEMP_DtsXtTest {

	@XtFolder
	static String getFolder() {
		return "xt-tests/dts";
	}
}
