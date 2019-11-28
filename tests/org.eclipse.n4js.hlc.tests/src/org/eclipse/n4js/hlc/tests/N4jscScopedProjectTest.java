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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test handling of projects using an npm scope in headless case.
 */
public class N4jscScopedProjectTest extends AbstractCliCompileTest {
	private File workspace;
	private File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__NPM_SCOPES, true, N4JSGlobals.N4JS_RUNTIME);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
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
	public void testNpmScopes() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 12, cliResult.getTranspiledFilesCount());

		String srcFolder = proot + "/XClient/src-gen/";
		String testFile1 = srcFolder + "ClientModule1.js";
		String testFile2 = srcFolder + "ClientModule2.js";

		String expectedString = "";
		expectedString += "Hello from A in @myScope/Lib!\n";
		expectedString += "Hello from B in Lib!\n";
		expectedString += "Hello from C in @myScope/Lib!\n";
		expectedString += "Hello from C in Lib!\n";
		expectedString += "Hello from D in @myScope/Lib!\n";
		expectedString += "Hello from D in Lib!";

		ProcessResult nodejsResult1 = runNodejs(workspace.toPath(), Path.of(testFile1));
		assertEquals(nodejsResult1.toString(), expectedString, nodejsResult1.getStdOut());

		ProcessResult nodejsResult2 = runNodejs(workspace.toPath(), Path.of(testFile2));
		assertEquals(nodejsResult2.toString(), expectedString, nodejsResult2.getStdOut());
	}

}
