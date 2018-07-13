/**
 * Copyright (c) 2018 NumberFour AG.
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
import org.junit.Test;

/**
 * Uses the {@code n4jsc.jar} to compile and run all tests in a given project.
 *
 * The project contains passing as well as failing tests.
 */
public class CompileAndRunTestsWithFailuresTest extends AbstractN4jscJarTest {

	/** */
	public CompileAndRunTestsWithFailuresTest() {
		super("probands/GH-975-tester-failures", true);
	}

	/**
	 * Compiles and runs all tests in a simple N4JS test project.
	 */
	@Test
	public void testRunTestsWithFailures() throws IOException, InterruptedException {
		logFile();

		final String wsRoot = WORKSPACE_FOLDER;
		final String projectToTest = WORKSPACE_FOLDER + "/T";

		final Process p = createAndStartProcess(
				"--projectlocations", wsRoot,
				"--buildType", "allprojects",
				"--test", projectToTest);

		int exitCode = p.waitFor();

		assertEquals(ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR.getExitCodeValue(), exitCode);
	}

}
