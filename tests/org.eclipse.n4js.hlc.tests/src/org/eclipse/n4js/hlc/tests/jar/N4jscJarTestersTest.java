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

import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.tests.N4CliHelper;
import org.junit.Ignore;
import org.junit.Test;

/**
 * IMPORTANT: All the tests in this class require that n4jsc.jar exist. Before executing this test, in the console,
 * change your current to the folder {@code git/n4js/tools/scripts/}. Then inside that folder, execute the
 * {@code mvn-cp-n4jsjar.sh}.
 * <p>
 * This script executes Maven locally to create n4jsc.jar and copy the n4jsc.jar to the folder
 * git/n4js/tests/org.eclipse.n4js.hlc.tests/target required by these tests.
 */
public class N4jscJarTestersTest extends AbstractN4jscJarTest {

	/***/
	public N4jscJarTestersTest() {
		super("probands/testers");
	}

	/**
	 * Compile All and Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	@Ignore("https://github.com/eclipse/n4js/issues/611")
	public void testCompileAllAndRunWithNodeWithOutput() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WSP + "/" + projectDemoTest;

		Process p = createAndStartProcess(
				"--systemLoader", COMMON_JS.getId(),
				"--projectlocations", WSP,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"--verbose");

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
	 * Compile All and Test project. Assert test output, test report, and proper exit code.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	@Ignore("https://github.com/NumberFour/n4js/issues/167")
	public void testCompileAllAndRunWithNodeWithReport() throws Exception {
		logFile();

		String projectDemoTest = "DemoTest";
		String pathToDemoTest = WSP + "/" + projectDemoTest;
		String testReportRoot = pathToDemoTest + "/src-gen";

		Process p = createAndStartProcess(
				"--systemLoader", COMMON_JS.getId(),
				"--projectlocations", WSP,
				"--buildType", "allprojects",
				"--testWith", "nodejs_mangelhaft",
				"--test", pathToDemoTest,
				"--testReportRoot", testReportRoot,
				"--verbose");

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
