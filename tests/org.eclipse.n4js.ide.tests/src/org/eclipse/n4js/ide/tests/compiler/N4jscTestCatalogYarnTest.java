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
package org.eclipse.n4js.ide.tests.compiler;

import static org.eclipse.n4js.cli.N4jscExitCode.VALIDATION_ERRORS;
import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that a test catalog file is created for the yarn workspace project.
 */
public class N4jscTestCatalogYarnTest extends AbstractCliCompileTest {

	static final boolean DONT_CLEAN = false;

	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("basic", true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Compile and check if test catalog exists. */
	@Test
	public void testCompileAndAssertTestCatalog() throws Exception {
		// because we want to execute stuff, we have to install the runtime:
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		CliCompileResult cliResult = n4jsc(COMPILE(workspace), VALIDATION_ERRORS);
		assertEquals(cliResult.toString(), 16, cliResult.getTranspiledFilesCount());

		File testCatalogFileInWorkspace = new File(workspace, N4JSGlobals.TEST_CATALOG);
		assertFalse("Test catalog of workspace must not exist", testCatalogFileInWorkspace.isFile());

		String[] sourceProjectNames = { "P1", "P2", "P3", "P4", "PSingle" };
		String[] testProjectNames = { "Test01", "TestCleanPrj1", "TestCleanPrj2" };
		for (String projectName : sourceProjectNames) {
			File testCatalogFileInSubProject = new File(proot, projectName + File.separator + N4JSGlobals.TEST_CATALOG);
			assertFalse("Test catalog of '" + projectName + "' must not exist", testCatalogFileInSubProject.isFile());
		}
		for (String projectName : testProjectNames) {
			File testCatalogFileInSubProject = new File(proot, projectName + File.separator + N4JSGlobals.TEST_CATALOG);
			assertTrue("Test catalog of '" + projectName + "' must not exist", testCatalogFileInSubProject.isFile());
		}

	}

}
