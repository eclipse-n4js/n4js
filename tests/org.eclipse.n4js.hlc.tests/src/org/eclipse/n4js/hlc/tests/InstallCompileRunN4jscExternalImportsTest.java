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

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.runner.helper.NodejsResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs 'express'.
 */
public class InstallCompileRunN4jscExternalImportsTest extends AbstractCliCompileTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_imports", Predicates.alwaysTrue(), true);
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
	@Ignore // remove @Ignore when GH-887 is merged
	// continue here
	public void testCompileAndRunWithExternalDependencies() {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/external.project/src-gen/Main.js";

		N4jscOptions options = COMPILE(workspace);
		CliResult cliResult = main(options);
		assertEquals(cliResult.toString(), 69, cliResult.getTranspiledFilesCount());

		String expectedString = "react is not undefined true\n"
				+ "react-dom is not undefined true\n"
				+ "imports from libs are different true";

		NodejsResult nodejsResult = run(workspace.toPath(), Path.of(fileToRun));
		N4CliHelper.assertExpectedOutput(expectedString, nodejsResult.getStdOut());
	}

}
