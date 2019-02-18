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
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.SuccessExitStatus;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for N4jsc, testing various situations in which compiler exits with errors.
 */
public class N4jscBasicTest extends AbstractN4jscTest {
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
	 * With "keep compiling" compiler does not throw compilation errors.
	 */
	@Test
	public void testMainArgsCompileAllKeepCompiling() throws ExitCodeException {

		String[] args = { "--projectlocations", proot.toString(), "--buildType", "allprojects", "--keepCompiling" };

		new N4jscBase().doMain(args);
		// Assert that at most 19 files are compiled. The actual number depends on the chosen algorithm for the build
		// order and on the order in which the project dependency graph is traversed. 19 is the maximum number of files
		// that can be transpiled without error.
		assertTrue(countFilesCompiledToES(proot.toString()) <= 19);
	}

	/**
	 * normal compile test
	 */
	@Test
	public void testMainArgsProjectRoot() throws ExitCodeException {

		// absolute filename
		String file1 = proot + "/" + "PSingle/src/a/A.n4js";

		String[] args = { "--projectlocations", proot.toString(), file1 };

		new N4jscBase().doMain(args);

	}

	/**
	 * Test successful exit, Exception is expected but with Error-Code 0
	 */
	@Test
	public void testMainHelp() throws ExitCodeException {
		String[] args = { "--help" };
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should have printed help and exited with success.", SuccessExitStatus.INSTANCE.code, status.code);
	}

	/**
	 * Test debug output before help. This test doesn't run a compile because of the "-help" option should end
	 * execution.
	 */
	@Test
	public void testMainDebugHelp() throws ExitCodeException {
		String[] args = { "--debug", "--help" };
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should have printed help and exited with success.", SuccessExitStatus.INSTANCE.code, status.code);
	}

	/**
	 * Test debug output after help.
	 */
	@Test
	public void testMainHelpDebug() throws ExitCodeException {
		String[] args = { "--help", "--debug" };
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should have printed help and exited with success.", SuccessExitStatus.INSTANCE.code, status.code);
	}

	/**
	 * Test debug output before help. This test doesn't run a compile because of the "-help" option should end
	 * execution.
	 */
	@Test
	public void testMainInvalidDataAfterHelp() throws ExitCodeException {
		String[] args = { "--help", "--preferences", "xxx", "--buildType", "allprojects", "more1", "more2", "more3" };
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should have printed help and exited with success.", SuccessExitStatus.INSTANCE.code, status.code);
	}

	/**
	 * Simple test of compiling a project and running a class from that compiled code with NODEJS.
	 */
	@Test
	public void testCompileP1_And_Run_A_WithNodeRunner() throws ExitCodeException {

		// Project
		String projectP1 = "P1";
		String pathToP1 = proot + "/" + projectP1;

		// absolute src filename
		String fileA = proot + "/" + projectP1 + "/src/A.n4js";

		String[] args = { "-pl", proot.toString(),
				"--buildType", "projects", pathToP1,
				"--runWith", "nodejs",
				"--run", fileA
		};
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, status.code);
	}

	/**
	 * Test that when build type is a single project, if clean flag is on, files in src-gen folder are removed but no
	 * files are compiled.
	 */
	@Test
	public void testCleanSingleProject() throws ExitCodeException {
		String project = "TestCleanPrj1";
		String pathToProject = proot + "/" + project;
		String[] args = { "--debug", "--clean", "--projectlocations", proot.toString(), "--buildType", "projects",
				pathToProject };
		new N4jscBase().doMain(args);

		assertEquals(1, countFilesCompiledToES(proot.toString())); // 1 = 0 in TestCleanPrj1 + 1 in TestCleanPrj2
	}

	/**
	 * Test that clean build is not triggered when --clean/-c is not used.
	 *
	 * @throws ExitCodeException
	 *             expected
	 */
	@Test
	public void testNoCleanSingleProject() throws ExitCodeException {
		String project = "TestCleanPrj1";
		String pathToProject = proot + "/" + project;
		String[] args = { "--projectlocations", proot.toString(), "--buildType", "projects", pathToProject };
		new N4jscBase().doMain(args);

		assertEquals(4, countFilesCompiledToES(proot.toString())); // 4 = 3 in TestCleanPrj1 + 1 in TestCleanPrj2
	}

	/**
	 * Test that clean removes files in src-gen folder but does not compile when compiling multiple projects.
	 */
	@Test
	public void testCleanMultipleProjects() throws ExitCodeException {
		String project1 = "TestCleanPrj1";
		String project2 = "TestCleanPrj2";
		String pathToProject1 = proot + "/" + project1;
		String pathToProject2 = proot + "/" + project2;
		String[] args = { "--clean", "--projectlocations", proot.toString(), "--buildType", "projects", pathToProject1,
				pathToProject2 };

		new N4jscBase().doMain(args);
		assertEquals(0, countFilesCompiledToES(proot.toString())); // 0 = 0 in TestCleanPrj1 + 0 in TestCleanPrj2
	}

	/**
	 * Test that clean removes files in src-gen folder but does not compile when compiling all projects in a given root.
	 */
	@Test
	public void testCleanAllProjects() throws ExitCodeException {
		String[] args = { "--clean", "--projectlocations", proot.toString(), "--buildType", "allprojects" };
		new N4jscBase().doMain(args);

		assertEquals(0, countFilesCompiledToES(proot.toString())); // 0 = 0 in all projects inside wsp/basic
	}

	/**
	 * Test that the compiler reports an error when the source files or projects contain invalid file or directory.
	 */
	@Test
	public void testInvalidSrcFiles() {
		// Project
		String pathToBLAH = "/BLAH";
		String[] args = { "-pl", proot.toString(), "--buildType", "projects", pathToBLAH };

		expectCompilerException(args, ErrorExitCode.EXITCODE_SRCFILES_INVALID);
	}
}
