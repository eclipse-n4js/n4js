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

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.junit.Test;

/**
 * IMPORTANT: All the tests in this class require that n4jsc.jar exist. Before executing this test, in the console,
 * change your current to the folder {@code git/n4js/tools/scripts/}. Then inside that folder, execute the
 * {@code mvn-cp-n4jsjar.sh}.
 * <p>
 * This script executes Maven locally to create n4jsc.jar and copy the n4jsc.jar to the folder
 * git/n4js/tests/org.eclipse.n4js.hlc.tests/target required by these tests.
 */
public class SingleFileCompileN4jscJarTest extends AbstractN4jscJarTest {

	/***/
	public SingleFileCompileN4jscJarTest() {
		super("fixture");
	}

	/**
	 * Test help command
	 */
	@Test
	public void testHelp() throws Exception {
		logFile();

		Process p = createAndStartProcess("--help");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);
	}

	/**
	 * Test simple help command with dangling argument (should be ignored).
	 */
	@Test
	public void testHelpWithDanglingArgument() throws Exception {
		logFile();

		Process p = createAndStartProcess("--help", "true");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);
	}

	/**
	 * Compile a single file.
	 *
	 * @throws Exception
	 *             not expected.
	 */
	@Test
	public void testSingleFileCompile() throws Exception {
		logFile();

		Process p = createAndStartProcess("--buildType", "singleFile", WSP + "/" + "PSingle/src/a/A.n4js");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);
	}

	/**
	 * Compile & Run whole project.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testCompileAllAndRunWithNodejsPlugin() throws Exception {
		logFile();

		// -rw run with
		// -r run : file to run
		Process p = createAndStartProcess("--buildType", "allprojects", "--projectlocations",
				WSP, "--runWith",
				"nodejs", "--run",
				WSP + "/" + "P1/src/A.n4js");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check end of output.
		N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT, "Arrghtutut§", outputLogFile);

	}

	/**
	 * Compile & Run Tests for IDE-1510
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testApiImplStub_CompileAndRunWithNodejsPlugin() throws Exception {
		logFile();

		Process p = createAndStartProcess( // ----
				"--projectlocations", WSP + "/" + "IDE-1510_Incomplete_API_Implementation", // ----
				"--runWith", "nodejs", // ----
				"--run", WSP + "/"
						+ "IDE-1510_Incomplete_API_Implementation/one.x.impl/src/AT_IDE-1510_Missing_Method.n4js", // ----
				"--buildType", "allprojects", // ----
				"wsp/IDE-1510_Incomplete_API_Implementation/one.api",
				"wsp/IDE-1510_Incomplete_API_Implementation/one.x.impl");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check end of output.
		N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
				"Hello from Implementation one.x.impl::p.A.n4js !", outputLogFile);
		N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
				"N4ApiNotImplementedError: API for method A.missing_n not implemented yet.", outputLogFile);
		N4CliHelper.assertEndOfOutputExpectedToContain(N4jscBase.MARKER_RUNNER_OUPTUT,
				"N4ApiNotImplementedError: API for method A.missing_someIA not implemented yet.", outputLogFile);
	}

	/**
	 * List available plugins.
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	public void testListAllRunnersPlugins_expecting_NODEJS() throws Exception {
		logFile();

		// -rw run with
		// -lr list runners.
		Process p = createAndStartProcess("--listRunners");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		N4CliHelper.assertContainsString("NODEJS", outputLogFile);
	}

	/**
	 * Trying to run an uncompiled module: should result in a failure
	 *
	 * @throws IOException
	 *             if process causes problems.
	 * @throws InterruptedException
	 *             waiting for external process is interrupted.
	 */
	@Test()
	public void test_Run_Not_Compiled_A_WithNodeRunner() throws IOException, InterruptedException {
		logFile();

		// Process is running from TARGET-Folder.
		String proot = WSP;

		// Project
		String projectP1 = "P1";
		String pathToP1 = proot + "/" + projectP1;

		// absolute src filename
		String fileA = pathToP1 + "/src/A.n4js";

		Process p = createAndStartProcess("-pl", proot,
				"--buildType", "dontcompile",
				"--runWith", "nodejs",
				"--run", fileA);

		int exitCode = p.waitFor();

		// check the expected exit code of 7:
		assertEquals("Exit with wrong exitcode.", ErrorExitCode.EXITCODE_RUNNER_STOPPED_WITH_ERROR.getExitCodeValue(),
				exitCode);

	}
}
