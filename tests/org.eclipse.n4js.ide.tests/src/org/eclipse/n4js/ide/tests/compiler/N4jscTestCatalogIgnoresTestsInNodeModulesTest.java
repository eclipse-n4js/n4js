/**
 * Copyright (c) 2020 NumberFour AG.
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

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that the test catalog file does not contain tests of packages located inside the node_modules folder.
 */
public class N4jscTestCatalogIgnoresTestsInNodeModulesTest extends AbstractCliCompileTest {

	File workspace;
	File projectRoot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("testCatalogIgnoresTestsInNodeModules", false);
		projectRoot = new File(workspace, "P1").getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Compile and check if test catalog exists. */
	@Test
	public void testCompileAndAssertTestCatalog() throws Exception {

		File testCatalog = new File(projectRoot, N4JSGlobals.TEST_CATALOG);
		CliCompileResult cliResult = n4jsc(COMPILE(workspace), VALIDATION_ERRORS);
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		assertFalse("Superfluous test catalog in P1", testCatalog.isFile());
	}

}
