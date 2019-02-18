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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for error cases of the N4JSC calls. All tests are expected to throw {@link ExitCodeException} with
 * concrete {@link ErrorExitCode}.
 */
public class N4jscBasicErrorTest extends AbstractN4jscTest {
	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__BASIC, true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
		System.out.println("just for reference workspace base path is: " + workspace.getAbsolutePath().toString());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * normal compile all test, due to error in dependency will break the build with exitcode == 2
	 */
	@Test
	public void testCompilationFailsDueToMissingDependency() {
		String[] args = { "--projectlocations", proot.toString(), "--buildType", "allprojects" };

		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
		// Assert that at most 19 files are compiled. The actual number depends on the chosen algorithm for the build
		// order and on the order in which the project dependency graph is traversed. 19 is the maximum number of files
		// that can be transpiled without error.
		assertTrue(countFilesCompiledToES(proot.toString()) <= 19);
	}

	/**
	 * test missing parameter-operand for project locations, expecting Exception
	 */
	@Test
	public void testWronngArgumentsProjectRootMisssing() {
		String[] args = { "--projectlocations" };
		expectCompilerException(args, ErrorExitCode.EXITCODE_WRONG_CMDLINE_OPTIONS);
	}

	/**
	 * Trying to run an uncompiled module: should result in a failure
	 */
	@Test
	public void test_Run_Not_Compiled_A_WithNodeRunner() {

		// Project
		String projectP1 = "P1";
		String pathToP1 = proot + "/" + projectP1;

		// absolute src filename
		String fileA = pathToP1 + "/src/A.n4js";

		String[] args = { "--projectlocations", proot.toString(), "--runWith", "nodejs", "--run", fileA };

		expectCompilerException(args, ErrorExitCode.EXITCODE_RUNNER_STOPPED_WITH_ERROR);
	}

	/**
	 * Test if --clean/-c is on, -bt flag must be on as well.
	 */
	@Test
	public void testWhenCleanThenBuildType() {
		String[] args = { "--clean", "--projectlocations", proot.toString() };
		expectCompilerException(args, ErrorExitCode.EXITCODE_WRONG_CMDLINE_OPTIONS);
	}

	/**
	 * Test that --clean/-c can not be used with -bt singlefile.
	 */
	@Test
	public void testCleanAndTypeSingleFileNotAllowed() {
		String project = "TestCleanPrj1";
		String pathToFile = proot + "/" + project + "/src/C.n4js";
		String[] args = { "--clean", "--projectlocations", proot.toString(), "--buildType", "singlefile", pathToFile };
		expectCompilerException(args, ErrorExitCode.EXITCODE_WRONG_CMDLINE_OPTIONS);
	}

	/**
	 *
	 * Test that it is not allowed to use --clean/-c flag with -bt other than allprojects/dontcompile
	 */
	@Test
	public void testCleanProjectsWithoutProjectLocation() {
		String[] args = { "--clean", "--buildType", "projects" };
		expectCompilerException(args, ErrorExitCode.EXITCODE_WRONG_CMDLINE_OPTIONS);
		// freshly setup workspace contains compiled files in TestCleanPrj1 and TestCleanPrj2
		assertEquals(3, countFilesCompiledToES(proot.toString()));
	}
}
