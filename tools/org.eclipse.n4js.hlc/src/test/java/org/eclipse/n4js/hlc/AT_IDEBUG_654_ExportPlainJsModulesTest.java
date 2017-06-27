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
package org.eclipse.n4js.hlc;

import static org.eclipse.n4js.hlc.IncompleteApiImplementationTest.runCaptureOut;

import java.io.IOException;

import org.eclipse.n4js.hlc.base.N4jscBase.ExitCodeException;
import org.eclipse.n4js.hlc.helper.N4CliHelper;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class AT_IDEBUG_654_ExportPlainJsModulesTest extends AbstractN4jscTest {

	static String WS_IDEBUG_654 = "IDEBUG-654";

	/**
	 * Setup workspace.
	 */
	@BeforeClass
	public static void setupWorkspace() throws IOException {
		setupWorkspace(WS_IDEBUG_654);
	}

	/***/
	@Test
	public void compileCheckModuleExportFromPlainJsFile_ExpectAvailable() throws ExitCodeException, IOException {
		System.out.println(logMethodname());

		final String wsRoot = TARGET + "/" + WSP;
		final String fileToRun = wsRoot + "/IDEBUG-654/src/Client.n4js";
		final String[] args = { "-pl", wsRoot,
				"-t", "allprojects",
				"-rw", "nodejs",
				"-r", fileToRun,
				"--debug",
				"-v"
		};

		// Run
		final String out = runCaptureOut(args);
		N4CliHelper.assertExpectedOutput("foo === 36: true, bar === 'bar': true", out);

	}

}
