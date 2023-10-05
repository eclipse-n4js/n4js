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
package org.eclipse.n4js.integration.tests.cli.compile;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for bug GH-2154.
 */
public class AT_GH_2154_HeadlessBuilderNotShowingAllIssues extends AbstractCliCompileTest {

	File workspace;
	File packagesFolder;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("GH-2154", true);
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);
		packagesFolder = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	
	@Test
	public void testAllIssuesShown() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace), N4jscExitCode.VALIDATION_ERRORS);

		String actualOutput = cliResult.getStdOut();
		actualOutput = actualOutput.replaceAll("Duration: .*s", "Duration: ----");
		actualOutput = actualOutput.replaceAll("done \\(.*s\\)", "done (----)");

		String expectedOutput = "n4jsc version 0.0.0.v19990101_0000\n"
				+ "Clean results - Deleted: 0, Duration: ----\n"
				+ "Initial build ...\n"
				+ "packages/TestProject/src/TestModule.n4js\n"
				+ "  WRN 15:5     The local variable Unused is never used\n"
				+ "  WRN 15:5     Variable names should start with lower case letter.\n"
				+ "  ERR 20:17    any is not a subtype of string.\n"
				+ "  WRN 20:17    Unnecessary cast from null to any\n"
				+ "... initial build done (----).\n"
				+ "Compile results - Generated: 0, Deleted: 0, Errors: 1, Warnings: 3, Duration: ----";

		assertEquals(cliResult.toString(), expectedOutput, actualOutput);
	}
}
