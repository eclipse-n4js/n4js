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
package org.eclipse.n4js.hlc.integrationtests;

import static org.eclipse.n4js.hlc.integrationtests.HlcTestingConstants.WORKSPACE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.junit.Test;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class TesterN4jscJarTest extends AbstractN4jscJarTest {

	/***/
	public TesterN4jscJarTest() {
		super("probands/testers", true);
	}

	/**
	 * Compile All and Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testCompileAllAndRunWithNodeWithOutput() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WORKSPACE_FOLDER + "/" + N4CliHelper.PACKAGES + "/" + projectDemoTest;

		Process p = createAndStartProcess(
				"--projectlocations", WORKSPACE_FOLDER + "/" + N4CliHelper.PACKAGES,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--verbose", // required, otherwise passed tests will not occur in output
				"--test", pathToDemoTest);

		int exitCode = p.waitFor();

		assertEquals(ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR.getExitCodeValue(), exitCode);

		String out = N4CliHelper.readLogfile(outputLogFile);
		assertTrue(out.contains(
				"ERROR: org.eclipse.n4js.hlc.base.ExitCodeException: There were test errors, see console logs and/or test report for details."));

		N4CliHelper.assertExpectedOutputContains(
				"|TID:src-gen/BarTest/OsInspectorTest2#testFail| => Failed", out);
		N4CliHelper.assertExpectedOutputContains(
				"|TID:src-gen/BazTest/OsInspectorTest3#testIgnored| => Ignored", out);

		N4CliHelper.assertExpectedOutputContains(
				"|TID:src-gen/FooTest/OsInspectorTest#testPass|", out);
		N4CliHelper.assertExpectedOutputNotContains(
				"|TID:src-gen/FooTest/OsInspectorTest#testPass| => Failed", out);
		N4CliHelper.assertExpectedOutputNotContains(
				"|TID:src-gen/FooTest/OsInspectorTest#testPass| => Ignored", out);
	}

	/**
	 * Compile All and Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testCompileAllAndRunWithNodeWithReport() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WORKSPACE_FOLDER + "/" + N4CliHelper.PACKAGES + "/" + projectDemoTest;
		String testReportRoot = pathToDemoTest + "/src-gen";

		final Process p = createAndStartProcess(
				"--projectlocations", WORKSPACE_FOLDER + "/" + N4CliHelper.PACKAGES,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"--testReportRoot", testReportRoot);

		final int exitCode = p.waitFor();

		assertEquals(ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR.getExitCodeValue(), exitCode);

		String out = N4CliHelper.readLogfile(outputLogFile);
		assertTrue(out.contains(
				"ERROR: org.eclipse.n4js.hlc.base.ExitCodeException: There were test errors, see console logs and/or test report for details."));

		File report = new File(HlcTestingConstants.TARGET + "/" + testReportRoot + "/test-report.xml");
		assertTrue("Test report not found", report.exists());

		@SuppressWarnings("resource")
		final String content = new Scanner(report).useDelimiter("\\Z").next();
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
}
