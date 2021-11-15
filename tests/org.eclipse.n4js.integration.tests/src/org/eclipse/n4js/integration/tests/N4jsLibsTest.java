/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileCopier;
import org.junit.Test;

/**
 * Runs the test below top-level folder 'n4js-libs'.
 */
public class N4jsLibsTest {

	/***/
	@Test
	public void testN4jsLibs() throws IOException {
		final Path n4jsRootPath = UtilN4.findN4jsRepoRootPath();
		copyJarToN4jsCli(n4jsRootPath);

		final Path n4jsLibsRootPath = n4jsRootPath.resolve(N4JSGlobals.N4JS_LIBS_FOLDER_NAME);
		final Path testReportPath = n4jsLibsRootPath.resolve("build").resolve("report.xml");

		Files.deleteIfExists(testReportPath);

		CliTools cliTools = new CliTools();
		cliTools.setInheritIO(true);
		ProcessResult result = cliTools.yarnRun(n4jsLibsRootPath, "run", "test");

		assertEquals("non-zero exit code", 0, result.getExitCode());
		assertTrue("test report not found", Files.isRegularFile(testReportPath));
	}

	/**
	 * In order to be able to execute the tests in "n4js-cli", we have to copy the <code>n4jsc.jar</code> from folder
	 * "target" to "n4js-cli/bin".
	 * <p>
	 * The publishing script will do the same right before actually publishing the npm packages.
	 */
	private void copyJarToN4jsCli(Path n4jsRootPath) throws IOException {
		final Path n4jscJarPath = n4jsRootPath.resolve(N4JSGlobals.TARGET).resolve(N4JSGlobals.N4JSC_JAR);
		assertTrue(N4JSGlobals.N4JSC_JAR + " not found", Files.isRegularFile(n4jscJarPath));
		final Path n4jscJarInN4jsCliPath = n4jsRootPath.resolve(N4JSGlobals.N4JS_LIBS_SOURCES_PATH)
				.resolve(N4JSGlobals.N4JS_CLI.getRawName())
				.resolve("bin")
				.resolve(N4JSGlobals.N4JSC_JAR);
		Files.deleteIfExists(n4jscJarInN4jsCliPath);
		FileCopier.copy(n4jscJarPath, n4jscJarInN4jsCliPath);
	}
}
