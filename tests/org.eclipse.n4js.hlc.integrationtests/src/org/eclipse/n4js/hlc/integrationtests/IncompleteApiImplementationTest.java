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

import org.eclipse.n4js.cli.helper.AbstractCliJarTest;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class IncompleteApiImplementationTest extends AbstractCliJarTest {

	/***/
	public IncompleteApiImplementationTest() {
		super("IncompleteApiImplementationTest");
	}

	// /**
	// * Compile & Run whole project.
	// *
	// * @throws Exception
	// * in Error cases
	// */
	// @Test
	// public void testCompileAllAndRunWithNodejsPlugin() throws Exception {
	// logFile();
	//
	// Path nodeModulesPath = Paths.get(TARGET, WORKSPACE_FOLDER, N4JSGlobals.NODE_MODULES).toAbsolutePath();
	// N4CliHelper.copyN4jsLibsToLocation(nodeModulesPath, N4JSGlobals.N4JS_RUNTIME);
	//
	// // -rw run with
	// // -r run : file to run
	// Process p = createAndStartProcess(
	// "--buildType", "allprojects",
	// "--projectlocations", WORKSPACE_FOLDER + "/" + PACKAGES,
	// "--runWith", "nodejs",
	// "--run", WORKSPACE_FOLDER + "/" + PACKAGES + "/" + "P1/src/A.n4js");
	//
	// int exitCode = p.waitFor();
	//
	// assertEquals("successful termination", 0, exitCode);
	//
	// // check end of output.
	// N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT, "Arrghtutut§", outputLogFile);
	// }
	//
	// /**
	// * Compile & Run Tests for IDE-1510
	// *
	// * @throws Exception
	// * in Error cases
	// */
	// @Test
	// @Ignore("GH-1291")
	// public void testApiImplStub_CompileAndRunWithNodejsPlugin() throws Exception {
	// logFile();
	//
	// Process p = createAndStartProcess( // ----
	// "--projectlocations", WORKSPACE_FOLDER + "/" + "IDE-1510_Incomplete_API_Implementation", // ----
	// "--runWith", "nodejs", // ----
	// "--run", WORKSPACE_FOLDER + "/"
	// + "IDE-1510_Incomplete_API_Implementation/one.x.impl/src/AT_IDE-1510_Missing_Method.n4js", // ----
	// "--buildType", "allprojects", // ----
	// "wsp/IDE-1510_Incomplete_API_Implementation/one.api",
	// "wsp/IDE-1510_Incomplete_API_Implementation/one.x.impl");
	//
	// int exitCode = p.waitFor();
	//
	// assertEquals("successful termination", 0, exitCode);
	//
	// // check end of output.
	// N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
	// "Hello from Implementation one.x.impl::p.A.n4js !", outputLogFile);
	// N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
	// "N4ApiNotImplementedError: API for method A.missing_n not implemented yet.", outputLogFile);
	// N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
	// "N4ApiNotImplementedError: API for method A.missing_someIA not implemented yet.", outputLogFile);
	// }
	//
	// /**
	// * List available plugins.
	// *
	// * @throws Exception
	// * in Error cases
	// */
	// @Test
	// public void testListAllRunnersPlugins_expecting_NODEJS() throws Exception {
	// logFile();
	//
	// // -rw run with
	// // -lr list runners.
	// Process p = createAndStartProcess("--listRunners");
	//
	// int exitCode = p.waitFor();
	//
	// assertEquals("successful termination", 0, exitCode);
	//
	// N4CliHelper.assertContainsString("NODEJS", outputLogFile);
	// }
	//
	// /**
	// * Trying to run an uncompiled module: should result in a failure
	// *
	// * @throws IOException
	// * if process causes problems.
	// * @throws InterruptedException
	// * waiting for external process is interrupted.
	// */
	// @Test()
	// public void test_Run_Not_Compiled_A_WithNodeRunner() throws IOException, InterruptedException {
	// logFile();
	//
	// Path nodeModulesPath = Paths.get(TARGET, WORKSPACE_FOLDER, N4JSGlobals.NODE_MODULES).toAbsolutePath();
	// N4CliHelper.copyN4jsLibsToLocation(nodeModulesPath, N4JSGlobals.N4JS_RUNTIME);
	//
	// // Process is running from TARGET-Folder.
	// String proot = WORKSPACE_FOLDER + "/" + PACKAGES;
	//
	// // Project
	// String projectP1 = "P1";
	// String pathToP1 = proot + "/" + projectP1;
	//
	// // absolute src filename
	// String fileA = pathToP1 + "/src/A.n4js";
	//
	// Process p = createAndStartProcess("-pl", proot,
	// "--buildType", "dontcompile",
	// "--runWith", "nodejs",
	// "--run", fileA);
	//
	// int exitCode = p.waitFor();
	//
	// // check the expected exit code of 7:
	// assertEquals("Exit with wrong exitcode.", ErrorExitCode.EXITCODE_RUNNER_STOPPED_WITH_ERROR.getExitCodeValue(),
	// exitCode);
	// String out = N4CliHelper.readLogfile(outputLogFile);
	// String expectedErrorMessage = "Error: Cannot find module '/.*/target/wsp/packages/P1/src-gen/A'";
	// assertTrue(
	// "actual output did not contain pattern: " + expectedErrorMessage,
	// Pattern.compile(expectedErrorMessage).matcher(out).find());
	// }
}
