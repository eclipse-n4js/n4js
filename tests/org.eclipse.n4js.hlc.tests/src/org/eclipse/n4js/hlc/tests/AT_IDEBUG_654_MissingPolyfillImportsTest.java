/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for checking the static polyfill imports in the generated modules.
 */
public class AT_IDEBUG_654_MissingPolyfillImportsTest extends AbstractN4jscTest {

	File workspace;
	static String WS_IDEBUG_654_2 = "IDEBUG-654_2";

	/**
	 * Setup workspace.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(WS_IDEBUG_654_2, false);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/***/
	@Test
	public void compileCheckPolyfillImports_ExpectExist() throws ExitCodeException, IOException {

		// pre compile
		final String wsRoot = workspace.getAbsolutePath().toString();
		// Compile
		final String[] args_precompile = { "--projectlocations", wsRoot, "--buildType", "allprojects" };
		new N4jscBase().doMain(args_precompile);

		// run without compile
		final String fileToRun = wsRoot + "/IDEBUG-654_2/src/Main.n4js";
		final String[] args = { "--projectlocations", wsRoot,
				"--buildType", "dontcompile",
				"--runWith", "nodejs",
				"--run", fileToRun
		};

		// Run
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(
				"functionFromModuleA\n" +
						"variableFromModuleB\n" +
						"variableFromModuleC\n" +
						"variableFromModuleC",
				out);

	}

}
