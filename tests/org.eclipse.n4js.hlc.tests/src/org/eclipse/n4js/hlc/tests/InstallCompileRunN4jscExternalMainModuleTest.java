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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs several packages that are known to be problematic in terms of how they define
 * main module.
 */
public class InstallCompileRunN4jscExternalMainModuleTest extends AbstractCliCompileTest {

	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("externalmm", true, N4JSGlobals.N4JS_RUNTIME);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/external.project.mm/src-gen/Main.js";

		ProcessResult yarnInstallResult = yarnInstall(workspace.toPath());
		assertEquals(yarnInstallResult.toString(), 0, yarnInstallResult.getExitCode());

		N4jscOptions options = COMPILE(workspace);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		String expectedString = "express imported\n";
		expectedString += "jade imported\n";
		expectedString += "lodash imported\n";
		expectedString += "karma imported\n";
		expectedString += "bar imported\n";
		expectedString += "pouchdb-find imported\n";
		expectedString += "next imported\n";
		expectedString += "body-parser imported";

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRun));
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

}
