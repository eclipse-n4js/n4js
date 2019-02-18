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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.io.Files;

/**
 * Tests for launching N4JS tests via the command line.
 */
public class N4jscTestersTest extends AbstractN4jscTest {
	File workspace;

	private final static String EXPECTED_TEST_CATALOG = "" +
			"[{\"origin\":\"DemoTest\",\"fqn\":\"src-gen/BarTest/OsInspectorTest2\",\"testMethods\":[\"testFail\"]}" +
			",{\"origin\":\"DemoTest\",\"fqn\":\"src-gen/BazTest/OsInspectorTest3\",\"testMethods\":[\"testIgnored\"]}"
			+
			",{\"origin\":\"DemoTest\",\"fqn\":\"src-gen/FooTest/OsInspectorTest\",\"testMethods\":[\"testPass\"]}" +
			",{\"origin\":\"DemoTest\",\"fqn\":\"src-gen/subfolder/SubFolderModule/SubFolderTest\",\"testMethods\":[\"testPass\"]}"
			+
			",{\"origin\":\"SysProjectA\",\"fqn\":\"src-gen/T/T\",\"testMethods\":[\"t\"]}" +
			",{\"origin\":\"TestProjectA\",\"fqn\":\"src-gen/A/A\",\"testMethods\":[\"a\"]}" +
			",{\"origin\":\"TestProjectA\",\"fqn\":\"src-gen/B/B\",\"testMethods\":[\"b1\",\"b2\"]}" +
			",{\"origin\":\"TestProjectB\",\"fqn\":\"src-gen/CSub1/CSub1\",\"testMethods\":[\"c1\",\"c2\"]}" +
			",{\"origin\":\"TestProjectB\",\"fqn\":\"src-gen/CSub2/CSub2\",\"testMethods\":[\"c1\",\"c2\",\"c3\"]}]";

	/**
	 * Prepare tests.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__TESTERS, Predicates.alwaysTrue(), true);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Simple test of compiling a project and execute tests from <u>a single test file</u>.
	 *
	 * @throws ExitCodeException
	 *             in error cases ( not expected )
	 */
	@Test
	public void testCompile_And_LaunchSinglePassingTestFile() throws ExitCodeException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		// absolute src filename
		String fileFooTest = pathToDemoTest + "/test/FooTest.n4js";

		String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", fileFooTest
		};

		new N4jscBase().doMain(args);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Simple test of compiling a project and execute tests from <u>a single test file</u>.
	 *
	 * @throws ExitCodeException
	 *             in error cases ( not expected )
	 */
	@Test
	public void testCompile_And_LaunchSingleIgnoredTestFile() throws ExitCodeException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		// absolute src filename
		String fileFooTest = pathToDemoTest + "/test/BazTest.n4js";

		String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", fileFooTest
		};

		new N4jscBase().doMain(args);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Simple test of compiling a project and execute tests from <u>a single test file</u>.
	 * <p>
	 * (negative test, it is expected to see error output)
	 */
	@Test
	public void testCompile_And_LaunchSingleFailingTestFile() {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		// absolute src filename
		String fileFooTest = pathToDemoTest + "/test/BarTest.n4js";

		String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", fileFooTest
		};

		expectCompilerException(args, ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Simple test of compiling a project and execute tests from <u>a single test file</u>.
	 * <p>
	 * (negative test, it is expected to see error output)
	 *
	 */
	@Test
	public void testCompile_And_RunTests_And_GenerateTestReport() throws FileNotFoundException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;
		String testReportRoot = pathToDemoTest + "/src-gen";

		String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"--testReportRoot", testReportRoot
		};

		expectCompilerException(args, ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR);

		File report = new File(testReportRoot + "/test-report.xml");
		assertTrue("Test report not found", report.exists());

		@SuppressWarnings("resource")
		String content = new Scanner(report).useDelimiter("\\Z").next();
		assertTrue(content.contains(
				"<testsuite name=\"src-gen/BarTest/OsInspectorTest2\" tests=\"1\" errors=\"0\" failures=\"1\" skipped=\"0\""));
		assertTrue(content.contains(
				"<error message=\"AssertionError: Invalid OS detected. (detected os :: fakeOsName not == fakeOsName )\">"));

		assertTrue(content.contains(
				"<testsuite name=\"src-gen/BazTest/OsInspectorTest3\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"1\""));

		assertTrue(content.contains(
				"<testsuite name=\"src-gen/FooTest/OsInspectorTest\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"0\""));

		assertTrue(content.contains(
				"\"src-gen/subfolder/SubFolderModule/SubFolderTest\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"0\""));
		assertTrue(content.contains(
				"<testcase name=\"testPass\" classname=\"src-gen/subfolder/SubFolderModule/SubFolderTest\""));
	}

	/**
	 * Simple test of compiling a project and execute tests from <u>whole test project</u>.
	 * <p>
	 * (negative test, it is expected to see error output)
	 */
	@Test
	public void testCompile_And_TestProject() {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		// Project
		String projectDemoTest = "DemoTest";
		String pathToDemoTest = proot + "/" + projectDemoTest;

		String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", pathToDemoTest
		};

		expectCompilerException(args, ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR);

		// TODO add proper assertion that test was actually executed properly!!!
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog (given as absolute path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_absolutePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();
		final File tempDir = Files.createTempDir();
		tempDir.deleteOnExit();
		final String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testCatalogFile", tempDir + "/test-catalog.json"
		};

		new N4jscBase().doMain(args);
		final File file = new File(tempDir + "/test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		assertEquals(EXPECTED_TEST_CATALOG, actual);
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog (given as relative path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_relativePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();
		final String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testCatalogFile", "test-catalog.json"
		};

		new N4jscBase().doMain(args);
		final File file = new File("test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		assertEquals(EXPECTED_TEST_CATALOG, actual);
	}

	/**
	 * Gathers all tests from the workspace and generates a test catalog into an existing file (given as relative path).
	 */
	@Test
	public void testCompileAllProjectsGenerateTestCatalog_existingRelativePath()
			throws ExitCodeException, FileNotFoundException, IOException {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		final File existingFile = new File("test-catalog.json");
		existingFile.createNewFile();
		existingFile.deleteOnExit();

		final String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testCatalogFile", "test-catalog.json"
		};

		new N4jscBase().doMain(args);
		final File file = new File("test-catalog.json");
		file.deleteOnExit();
		final String actual = new String(
				java.nio.file.Files.readAllBytes(Paths.get(file.toURI())));
		assertEquals(EXPECTED_TEST_CATALOG, actual);
	}

	/**
	 * Tests invalid file catalog location
	 */
	@Test
	public void testInvalidTestCatalogLocation() {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();
		final String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testCatalogFile", "some/fake/folder/test-catalog.json"
		};

		expectCompilerException(args, ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR);
	}

	/**
	 * Tests case when catalog location is a directory instead of a file.
	 */
	@Test
	public void testDirectoryTestCatalogLocation() {
		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();
		final File tempDir = Files.createTempDir();
		tempDir.deleteOnExit();
		final String[] args = { "--projectlocations", proot,
				"--buildType", "allprojects",
				"--testCatalogFile", tempDir.toString()
		};

		expectCompilerException(args, ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR);
	}

}
