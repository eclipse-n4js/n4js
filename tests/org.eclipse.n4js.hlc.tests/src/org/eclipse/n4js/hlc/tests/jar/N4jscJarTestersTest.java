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
package org.eclipse.n4js.hlc.tests.jar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.tests.ExternalsUtiities;
import org.eclipse.n4js.hlc.tests.N4CliHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class N4jscJarTestersTest extends AbstractN4jscJarTest {

	private File targetPlatformInstallLocation;
	private File targetPlatformFile;

	/***/
	public N4jscJarTestersTest() {
		super("probands/testers");
	}

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	@Before
	public void beforeTest() throws IOException {
		ExternalsUtiities.setupExternals(targetPlatformInstallLocation, targetPlatformFile, description.getMethodName(),
				getNpmDependencies());
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	@After
	public void afterTest() {
		ExternalsUtiities.cleanupExternals(targetPlatformInstallLocation, targetPlatformFile);
	}

	/** Since N4JSC.jar does not provide built ins, we need to get them from npm. */
	// TODO https://github.com/NumberFour/n4js/issues/167
	protected Map<String, String> getNpmDependencies() {
		Map<String, String> deps = new HashMap<>();

		deps.put("eu.numberfour.mangelhaft", "@0.5.0");
		deps.put("eu.numberfour.mangelhaft.assert", "@0.5.0");

		return deps;
	}

	/**
	 * Compile All & Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testCompileAllAndRunWithNodeWithOutput() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WSP + "/" + projectDemoTest;

		Process p = createAndStartProcess(
				"-pl", WSP,
				"-t", "allprojects",
				"-tw", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"-v");

		int exitCode = p.waitFor();

		assertEquals(ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR.getExitCodeValue(), exitCode);

		String out = N4CliHelper.readLogfile(outputLogFile);

		N4CliHelper.assertExpectedOutputContains(
				"|TID:BarTest/OsInspectorTest2#testFail| => Failed", out);
		N4CliHelper.assertExpectedOutputContains(
				"|TID:BazTest/OsInspectorTest3#testIgnored| => Ignored", out);

		N4CliHelper.assertExpectedOutputContains(
				"|TID:FooTest/OsInspectorTest#testPass|", out);
		N4CliHelper.assertExpectedOutputNotContains(
				"|TID:FooTest/OsInspectorTest#testPass| => Failed", out);
		N4CliHelper.assertExpectedOutputNotContains(
				"|TID:FooTest/OsInspectorTest#testPass| => Ignored", out);
	}

	/**
	 * Compile All & Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testCompileAllAndRunWithNodeWithReport() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WSP + "/" + projectDemoTest;
		String testReportRoot = pathToDemoTest + "/src-gen";

		Process p = createAndStartProcess(
				"-pl", WSP,
				"-t", "allprojects",
				"-tw", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"--testReportRoot", testReportRoot,
				"-v");

		int exitCode = p.waitFor();

		assertEquals(ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR.getExitCodeValue(), exitCode);

		File report = new File(testReportRoot + "/test-report.xml");
		assertTrue("Test report not found", report.exists());

		@SuppressWarnings("resource")
		String content = new Scanner(report).useDelimiter("\\Z").next();
		assertTrue(content.contains(
				"<testsuite name=\"BarTest/OsInspectorTest2\" tests=\"1\" errors=\"0\" failures=\"1\" skipped=\"0\""));
		assertTrue(content.contains(
				"<error message=\"AssertionError: Invalid OS detected. (detected os :: fakeOsName not == fakeOsName )\">"));

		assertTrue(content.contains(
				"<testsuite name=\"BazTest/OsInspectorTest3\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"1\""));

		assertTrue(content.contains(
				"<testsuite name=\"FooTest/OsInspectorTest\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"0\""));

		assertTrue(content.contains(
				"\"subfolder/SubFolderModule/SubFolderTest\" tests=\"1\" errors=\"0\" failures=\"0\" skipped=\"0\""));
		assertTrue(content.contains(
				"<testcase name=\"testPass\" classname=\"subfolder/SubFolderModule/SubFolderTest\""));
	}
}
