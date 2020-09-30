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
 * Tests that a test catalog file is created for each test project in the sidy-by-side situation.
 */
public class N4jscTestCatalogSideBySideTest extends AbstractCliCompileTest {

	static final boolean DONT_CLEAN = false;

	File workspace;
	String[] projectNames = { "P1", "P2", "P3", "P4", "PSingle", "Test01", "TestCleanPrj1", "TestCleanPrj2" };
	File[] projectRoots = new File[projectNames.length];

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("basic", false);
		for (int i = 0; i < projectNames.length; i++) {
			projectRoots[i] = new File(workspace, projectNames[i]).getAbsoluteFile();
		}
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Compile and check if test catalog exists. */
	@Test
	public void testCompileAndAssertTestCatalog() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		// this test is using projects that are intended for a yarn workspace (i.e. having dependencies between each
		// other) to test a side-by-side use case; this is why validation errors are expected in next line:
		CliCompileResult cliResult = n4jsc(COMPILE(workspace), VALIDATION_ERRORS);
		assertEquals(cliResult.toString(), 17, cliResult.getTranspiledFilesCount());

		for (int i = 0; i < projectNames.length; i++) {
			File projectRoot = projectRoots[i];
			File testCatalogFileInSubProject = new File(projectRoot, N4JSGlobals.TEST_CATALOG);
			String projectName = projectNames[i];
			if (projectName.contains("Test")) {
				assertTrue("Test catalog is missing in " + projectName, testCatalogFileInSubProject.isFile());
			} else {
				assertFalse("Superfluous test catalog in " + projectName, testCatalogFileInSubProject.isFile());
			}
		}
	}

}
