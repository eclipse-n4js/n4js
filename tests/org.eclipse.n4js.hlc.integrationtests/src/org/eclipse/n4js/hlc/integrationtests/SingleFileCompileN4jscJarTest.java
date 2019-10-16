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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.eclipse.n4js.cli.N4jscTestOptions.HELP;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.runner.helper.ProcessResult;
import org.junit.Test;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class SingleFileCompileN4jscJarTest extends AbstractCliJarTest {

	/***/
	public SingleFileCompileN4jscJarTest() {
		super("fixture");
	}

	/** Test help command */
	@Test
	public void testHelp() throws Exception {
		CliCompileResult cliResult = n4jsc(HELP());
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
	}

	/** Compile a single project. */
	@Test
	public void testSingleFileCompile() {
		File project = Path.of(TARGET, WORKSPACE_FOLDER, N4CliHelper.PACKAGES, "PSingle").toAbsolutePath().toFile();

		CliCompileResult cliResult = n4jsc(COMPILE(project));
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
	}

	/** Compile & Run whole project. */
	@Test
	public void testCompileAllAndRunWithNodejsPlugin() throws IOException {
		Path workspace = Path.of(TARGET, WORKSPACE_FOLDER).toAbsolutePath();
		Path nodeModulesPath = Paths.get(workspace.toString(), N4JSGlobals.NODE_MODULES).toAbsolutePath();
		Path project = Path.of(workspace.toString(), N4CliHelper.PACKAGES, "P1").toAbsolutePath();

		N4CliHelper.copyN4jsLibsToLocation(nodeModulesPath, N4JSGlobals.N4JS_RUNTIME);

		CliCompileResult cliResult = n4jsc(COMPILE(project.toFile()));
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());

		Path fileA = project.resolve("src-gen/A.js");

		ProcessResult nodejsResult = runNodejs(workspace, fileA);
		assertEquals(nodejsResult.toString(), 0, nodejsResult.getExitCode());
		assertEquals(nodejsResult.toString(), "Arrghtutut§", nodejsResult.getStdOut());
	}

}
