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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.N4jscBase.ExitCodeException;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for N4jsc, like checking command line options or simple compile.
 */
public class N4jscBasicTest extends AbstractN4jscTest {

	/**
	 * Prepare tests.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		setupWorkspace(TEST_DATA_SET__BASIC);
	}

	/**
	 * normal compile all test, due to error in dependency will break the build with exitcode == 2
	 */
	@Test(expected = ExitCodeException.class)
	public void testMainArgsCompileAll() throws ExitCodeException {
		System.out.println(logMethodname());
		System.out.println("just for reference base-path is: " + new File(".").getAbsolutePath());

		String proot = TARGET + "/" + WSP;

		String[] args = { "-pl", proot, "-t", "allprojects" };

		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			assertEquals("Wrong exit code", N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
			throw e;
		}
	}

	/**
	 * normal compile all test without flag "--keepCompiling"
	 */
	@Test
	public void testMainArgsCompileAllKeepCompiling() {
		System.out.println(logMethodname());
		System.out.println("just for reference base-path is: " + new File(".").getAbsolutePath());

		String proot = TARGET + "/" + WSP;

		String[] args = { "-pl", proot, "-t", "allprojects" };

		try {
			new N4jscBase().doMain(args);
			assertFalse("Line should not have been reached, ExitCodeException expected.", true);
		} catch (ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
		}
		// Assert that at most 13 files are compiled. The actual number depends on the chosen algorithm for the build
		// order and on the order in which the project dependency graph is traversed. 13 is the maximum number of files
		// that can be transpiled without error.
		assertTrue(countFilesCompiledToES(proot) <= 13);
	}

	/**
	 * normal compile test
	 */
	@Test
	public void testMainArgsProjectRoot() throws ExitCodeException {
		System.out.println(logMethodname());
		System.out.println("just for reference base-path is: " + new File(".").getAbsolutePath());

		String proot = TARGET + "/" + WSP;

		// absolute filename
		String file1 = proot + "/" + "PSingle/src/a/A.n4js";

		String[] args = { "-pl", proot, file1 };

		new N4jscBase().doMain(args);

	}

	/**
	 * test missing parameter-operand for projectroot, expecting Exception
	 */
	@Test(expected = N4jscBase.ExitCodeException.class)
	public void testMainArgsProjectRoot_broken() throws ExitCodeException {
		System.out.println(logMethodname());

		String[] args = { "-pl" };

		new N4jscBase().doMain(args);

	}

	/**
	 * Test successful exit, Exception is expected but with Error-Code 0
	 */
	@Test
	public void testMainHelp() {
		System.out.println(logMethodname());
		String[] args = { "-h" };
		try {
			new N4jscBase().doMain(args);
			assertTrue("Should have printed help and exited before", false);
		} catch (ExitCodeException e) {
			assertEquals("Wrong exit code.", N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}
	}

	/**
	 * Test debug output before help. This test doesn't run a compile but because of the "-help" option should
	 */
	@Test
	public void testMainDebugHelp() {
		System.out.println(logMethodname());
		String[] args = { "-h", "--debug", "--preferences", "xxx", "-t", "allprojects" }; // , "more1", "more2", "more3"
		// };
		try {
			new N4jscBase().doMain(args);
			assertTrue("Should have printed help and exited before", false);
		} catch (ExitCodeException e) {
			assertEquals("Wrong exit code (not 0).", N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}
	}

	/**
	 * Simple test of compiling a project and running a class from that compiled code with NODEJS.
	 *
	 * @throws ExitCodeException
	 *             in error cases ( not expected )
	 */
	@Test
	public void testCompileP1_And_Run_A_WithNodeRunner() throws ExitCodeException {
		System.out.println(logMethodname());

		String proot = TARGET + "/" + WSP;

		// Project
		String projectP1 = "P1";
		String pathToP1 = proot + "/" + projectP1;

		// absolute src filename
		String fileA = proot + "/" + projectP1 + "/src/A.n4js";

		String[] args = { "-pl", proot,
				"-t", "projects", pathToP1,
				"-rw", "nodejs",
				"-r", fileA,
				"-v"
		};

		new N4jscBase().doMain(args);
	}

	/**
	 * Trying to run an uncompiled module: should result in a failure
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test(expected = ExitCodeException.class)
	public void test_Run_Not_Compiled_A_WithNodeRunner() throws ExitCodeException {
		System.out.println(logMethodname());

		String proot = TARGET + "/" + WSP;

		// Project
		String projectP1 = "P1";
		String pathToP1 = proot + "/" + projectP1;

		// absolute src filename
		String fileA = pathToP1 + "/src/A.n4js";

		String[] args = { "-pl", proot,

				"-rw", "nodejs",
				"-r", fileA,
				"-v"
		};

		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// check the expected exit code of 7:
			assertEquals("Exit with wrong exitcode.", N4jscBase.EXITCODE_RUNNER_STOPPED_WITH_ERROR, e.getExitCode());
			throw e;
		}
	}

	/**
	 * Test if --clean/-c is on, -t flag must be on as well.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test(expected = ExitCodeException.class)
	public void testWhenCleanThenBuildType() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String[] args = { "--clean", "-pl", proot };
		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			assertEquals("Exit with wrong exitcode", N4jscBase.EXITCODE_WRONG_CMDLINE_OPTIONS, e.getExitCode());
			throw e;
		}
	}

	/**
	 * Test that --clean/-c can not be used with -t singlefile.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test(expected = ExitCodeException.class)
	public void testCleanAndTypeSingleFileNotAllowed() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String project = "TestCleanPrj1";
		String pathToFile = proot + "/" + project + "/src/C.n4js";
		String[] args = { "--clean", "-pl", proot, "-t", "singlefile", pathToFile };
		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			assertEquals("Exit with wrong exitcode", N4jscBase.EXITCODE_WRONG_CMDLINE_OPTIONS, e.getExitCode());
			throw e;
		}
	}

	/**
	 * Test that when build type is a single project, if clean flag is on, files in src-gen folder are removed but no
	 * files are compiled.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test
	public void testCleanSingleProject() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String project = "TestCleanPrj1";
		String pathToProject = proot + "/" + project;
		String[] args = { "--clean", "-pl", proot, "-t", "projects", pathToProject };
		new N4jscBase().doMain(args);

		assertEquals(1, countFilesCompiledToES(proot)); // 1 = 0 in TestCleanPrj1 + 1 in TestCleanPrj2
	}

	/**
	 * Test that clean build is not triggered when --clean/-c is not used.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test
	public void testNoCleanSingleProject() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String project = "TestCleanPrj1";
		String pathToProject = proot + "/" + project;
		String[] args = { "-pl", proot, "-t", "projects", pathToProject };
		new N4jscBase().doMain(args);

		assertEquals(4, countFilesCompiledToES(proot)); // 4 = 3 in TestCleanPrj1 + 1 in TestCleanPrj2
	}

	/**
	 * Test that clean removes files in src-gen folder but does not compile when compiling multiple projects.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test
	public void testCleanMultipleProjects() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String project1 = "TestCleanPrj1";
		String project2 = "TestCleanPrj2";
		String pathToProject1 = proot + "/" + project1;
		String pathToProject2 = proot + "/" + project2;
		String[] args = { "-c", "-pl", proot, "-t", "projects", pathToProject1, pathToProject2 };

		new N4jscBase().doMain(args);
		assertEquals(0, countFilesCompiledToES(proot)); // 0 = 0 in TestCleanPrj1 + 0 in TestCleanPrj2
	}

	/**
	 *
	 * Test that it is not allowed to use --clean/-c flag with -t other than allprojects/dontcompile
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test(expected = ExitCodeException.class)
	public void testCleanProjectsWithoutProjectLocation() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String[] args = { "-c", "-t", "projects" };
		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			assertEquals("Exit with wrong exitcode", N4jscBase.EXITCODE_WRONG_CMDLINE_OPTIONS, e.getExitCode());
			throw e;
		}

		assertEquals(0, countFilesCompiledToES(proot)); // 0 = 0 in all projects inside wsp/basic
	}

	/**
	 * Test that clean removes files in src-gen folder but does not compile when compiling all projects in a given root.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test
	public void testCleanAllProjects() throws ExitCodeException {
		String proot = TARGET + "/" + WSP;
		String[] args = { "-c", "-pl", proot, "-t", "allprojects" };
		new N4jscBase().doMain(args);

		assertEquals(0, countFilesCompiledToES(proot)); // 0 = 0 in all projects inside wsp/basic
	}
}
