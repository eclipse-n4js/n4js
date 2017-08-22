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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

/**
 * Tests for launching N4JS tests via the command line.
 */
public class N4jscTestersTest extends AbstractN4jscTest {
	File workspace;

	private static Collection<String> REQUIRED_LIBS = ImmutableSet.<String> builder()
			.add(N4JSGlobals.MANGELHAFT)
			.add(N4JSGlobals.MANGELHAFT_ASSERT)
			.add("n4js-runtime-n4")
			.add("n4js-runtime-v8")
			.add("n4js-runtime-es2015")
			.add("n4js.lang")
			.build();

	/**
	 * Prepare tests.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__TESTERS, Predicates.in(REQUIRED_LIBS));
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Simple test of compiling a project and launching <u>a single test file</u>.
	 *
	 * @throws ExitCodeException
	 *             in error cases ( not expected )
	 */
	@Test
	public void testCompile_And_LaunchSingleTestFile() throws ExitCodeException {
		String proot = workspace.getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		// absolute src filename
		String fileFooTest = pathToDemoTest + "/test/FooTest.n4js";

		String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tw", "nodejs_mangelhaft",
				"--test", fileFooTest,
				"-v"
		};

		new N4jscBase().doMain(args);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Simple test of compiling a project and launching <u>a single test file</u>.
	 *
	 * @throws ExitCodeException
	 *             in error cases ( not expected )
	 */
	@Test
	public void testCompile_And_LaunchSingleTestFile2() throws ExitCodeException {
		String proot = workspace.getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		// absolute src filename
		String fileFooTest = pathToDemoTest + "/test/BarTest.n4js";

		String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tw", "nodejs_mangelhaft",
				"--test", fileFooTest,
				"-v"
		};

		new N4jscBase().doMain(args);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog (given as absolute path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_absolutePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = workspace.getAbsolutePath().toString();
		final File tempDir = Files.createTempDir();
		tempDir.deleteOnExit();
		final String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tc", tempDir + "/test-catalog.json",
				"-v"
		};

		new N4jscBase().doMain(args);
		final File file = new File(tempDir + "/test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		final String expected = "[{\"origin\":\"DemoTest\",\"fqn\":\"BarTest.OsInspectorTest2\",\"testMethods\":[\"testFail\"]},{\"origin\":\"DemoTest\",\"fqn\":\"FooTest.OsInspectorTest\",\"testMethods\":[\"testPass\"]},{\"origin\":\"SysProjectA\",\"fqn\":\"T.T\",\"testMethods\":[\"t\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"A.A\",\"testMethods\":[\"a\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"B.B\",\"testMethods\":[\"b1\",\"b2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub1.CSub1\",\"testMethods\":[\"c1\",\"c2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub2.CSub2\",\"testMethods\":[\"c1\",\"c2\",\"c3\"]}]";
		assertEquals(expected, actual);
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog (given as relative path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_relativePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = workspace.getAbsolutePath().toString();
		final String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tc", "test-catalog.json",
				"-v"
		};

		new N4jscBase().doMain(args);
		final File file = new File("test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		final String expected = "[{\"origin\":\"DemoTest\",\"fqn\":\"BarTest.OsInspectorTest2\",\"testMethods\":[\"testFail\"]},{\"origin\":\"DemoTest\",\"fqn\":\"FooTest.OsInspectorTest\",\"testMethods\":[\"testPass\"]},{\"origin\":\"SysProjectA\",\"fqn\":\"T.T\",\"testMethods\":[\"t\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"A.A\",\"testMethods\":[\"a\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"B.B\",\"testMethods\":[\"b1\",\"b2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub1.CSub1\",\"testMethods\":[\"c1\",\"c2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub2.CSub2\",\"testMethods\":[\"c1\",\"c2\",\"c3\"]}]";
		assertEquals(expected, actual);
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog into an existing file (given as relative path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_existingRelativePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = workspace.getAbsolutePath().toString();

		final File existingFile = new File("test-catalog.json");
		existingFile.createNewFile();
		existingFile.deleteOnExit();

		final String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tc", "test-catalog.json",
				"-v"
		};

		new N4jscBase().doMain(args);
		final File file = new File("test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		final String expected = "[{\"origin\":\"DemoTest\",\"fqn\":\"BarTest.OsInspectorTest2\",\"testMethods\":[\"testFail\"]},{\"origin\":\"DemoTest\",\"fqn\":\"FooTest.OsInspectorTest\",\"testMethods\":[\"testPass\"]},{\"origin\":\"SysProjectA\",\"fqn\":\"T.T\",\"testMethods\":[\"t\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"A.A\",\"testMethods\":[\"a\"]},{\"origin\":\"TestProjectA\",\"fqn\":\"B.B\",\"testMethods\":[\"b1\",\"b2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub1.CSub1\",\"testMethods\":[\"c1\",\"c2\"]},{\"origin\":\"TestProjectB\",\"fqn\":\"CSub2.CSub2\",\"testMethods\":[\"c1\",\"c2\",\"c3\"]}]";
		assertEquals(expected, actual);
	}

	/**
	 * Tests invalid file catalog location
	 */
	@Test
	public void testInvalidTestCatalogLocation() {
		String proot = workspace.getAbsolutePath().toString();
		final String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tc", "some/fake/folder/test-catalog.json",
				"-v"
		};

		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR.getExitCodeValue());
		} catch (final ExitCodeException e) {
			assertEquals(ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR.getExitCodeValue(), e.getExitCode());
		}
	}

	/**
	 * Tests case when catalog location is a directory instead of a file.
	 */
	@Test
	public void testDirectoryTestCatalogLocation() {
		String proot = workspace.getAbsolutePath().toString();
		final File tempDir = Files.createTempDir();
		tempDir.deleteOnExit();
		final String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-tc", tempDir.toString(),
				"-v"
		};

		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR.getExitCodeValue());
		} catch (final ExitCodeException e) {
			assertEquals(ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR.getExitCodeValue(), e.getExitCode());
		}
	}

	/*
	 * TODO more tests for launching testers from command-line
	 *
	 * Some ideas:
	 *
	 * - simple tests for launching a folder or project containing tests (instead of a file)
	 *
	 * - negative tests (e.g. launch folder that contains no tests, launch tests without compiling first, ...)
	 */
}
