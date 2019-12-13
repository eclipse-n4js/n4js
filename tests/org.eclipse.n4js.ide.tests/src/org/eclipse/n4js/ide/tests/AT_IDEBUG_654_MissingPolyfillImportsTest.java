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
package org.eclipse.n4js.ide.tests;

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
 * Test for checking the static polyfill imports in the generated modules.
 */
public class AT_IDEBUG_654_MissingPolyfillImportsTest extends AbstractCliCompileTest {

	File workspace;
	static String WS_IDEBUG_654_2 = "IDEBUG-654_2";

	/**
	 * Setup workspace.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(WS_IDEBUG_654_2, false, N4JSGlobals.N4JS_RUNTIME);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/***/
	@Test
	public void compileCheckPolyfillImports_ExpectExist() {

		Path projectDir = workspace.toPath().resolve(WS_IDEBUG_654_2);
		Path fileToRun = projectDir.resolve("src-gen/Main.js");

		N4jscOptions options = COMPILE(workspace);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 5, cliResult.getTranspiledFilesCount());

		ProcessResult nodejsResult = runNodejs(projectDir, fileToRun);
		assertEquals(nodejsResult.toString(),
				"functionFromModuleA\n" +
						"variableFromModuleB\n" +
						"variableFromModuleC\n" +
						"variableFromModuleC",
				nodejsResult.getStdOut());

	}

}
