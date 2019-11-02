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
package org.eclipse.n4js.hlc.tests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tells N4JSC to build few projects and run one of them. Projects being built have missing dependencies, so N4JSC is
 * instructed to discover missing dependencies and to install them before compilation, which is done with
 * {@code --installMissingDependencies} flag.
 */
public class InstallFromManifestCompileRunN4jscExternalImportsTest extends AbstractCliCompileTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_project_install_dependencies", true, N4JSGlobals.N4JS_RUNTIME);
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
		final String fileToRun = packages + "/P3/src-gen/f3.js";

		ProcessResult yarnInstallResult = yarnInstall(workspace.toPath());
		assertEquals(yarnInstallResult.toString(), 0, yarnInstallResult.getExitCode());

		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 3, cliResult.getTranspiledFilesCount());

		String expectedString = "P1\n";
		expectedString += "react is not undefined true\n";
		expectedString += "react-dom is not undefined true\n";
		expectedString += "imports from libs are different true\n";
		expectedString += "P2\n";
		expectedString += "React is not undefined true";

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRun));
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/**
	 * Similar to the {@link #testCompileAndRunWithExternalDependencies()} but instead of using
	 * {@link BuildType#allprojects} with common root, it is using {@link BuildType#projects} with concrete list of
	 * projects.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies2() {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/P3/src-gen/f3.js";

		ProcessResult yarnInstallResult = yarnInstall(workspace.toPath());
		assertEquals(yarnInstallResult.toString(), 0, yarnInstallResult.getExitCode());

		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 3, cliResult.getTranspiledFilesCount());

		String expectedString = "P1\n";
		expectedString += "react is not undefined true\n";
		expectedString += "react-dom is not undefined true\n";
		expectedString += "imports from libs are different true\n";
		expectedString += "P2\n";
		expectedString += "React is not undefined true";

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRun));
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

}
