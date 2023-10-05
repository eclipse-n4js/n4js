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
import org.junit.Test;

/**
 * Runs the test below top-level folder 'n4js-libs'.
 */
public class N4jsLibsTest {

	
	@Test
	public void testN4jsLibs() throws IOException {
		final Path n4jsRootPath = UtilN4.findN4jsRepoRootPath();
		final Path n4jsLibsRootPath = n4jsRootPath.resolve(N4JSGlobals.N4JS_LIBS_FOLDER_NAME);
		final Path testReportPath = n4jsLibsRootPath.resolve("build").resolve("report.xml");

		Files.deleteIfExists(testReportPath);

		CliTools cliTools = new CliTools();
		cliTools.setInheritIO(true);
		ProcessResult result = cliTools.yarnRun(n4jsLibsRootPath, "run", "test");

		assertEquals("non-zero exit code", 0, result.getExitCode());
		assertTrue("test report not found", Files.isRegularFile(testReportPath));
	}
}
