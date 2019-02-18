/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test handling of projects using an npm scope in headless case.
 */
public class N4jscScopedProjectTest extends AbstractN4jscTest {

	private static final String TEST_FILE_1 = "ClientModule1.n4js";
	private static final String TEST_FILE_2 = "ClientModule2.n4js";
	private static final String NL = System.lineSeparator();

	private File workspace;
	private File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__NPM_SCOPES, true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
		System.out.println("just for reference workspace base path is: " + workspace.getAbsolutePath().toString());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test npm scopes.
	 */
	@Test
	public void testNpmScopes() throws ExitCodeException, IOException {

		// (1) compile

		String[] args1 = {
				"--projectlocations", proot.toString(),
				"--buildType", "allprojects"
		};
		new N4jscBase().doMain(args1);

		// (2) run and check output

		File sourceFolder = new File(new File(proot, "XClient"), "src");
		File testFile1 = new File(sourceFolder, TEST_FILE_1);
		File testFile2 = new File(sourceFolder, TEST_FILE_2);

		String expectedOutput = "" +
				"Hello from A in @myScope/Lib!" + NL +
				"Hello from B in Lib!" + NL +
				"Hello from C in @myScope/Lib!" + NL +
				"Hello from C in Lib!" + NL +
				"Hello from D in @myScope/Lib!" + NL +
				"Hello from D in Lib!";

		runAndAssertOutput(testFile1, expectedOutput);
		runAndAssertOutput(testFile2, expectedOutput);
	}

	private String runAndAssertOutput(File fileToExecute, String expectedOutput) throws ExitCodeException, IOException {
		String[] args = {
				"--projectlocations", proot.toString(),
				"--buildType", "dontcompile",
				"--runWith", "nodejs",
				"--run", fileToExecute.getAbsolutePath()
		};
		String actualOutput = runAndCaptureOutput(args);
		assertEquals("incorrect output when running test file " + TEST_FILE_1,
				expectedOutput, actualOutput);
		return actualOutput;
	}
}
