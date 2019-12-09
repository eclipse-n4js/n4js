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
package org.eclipse.n4js.ide.tests.jar;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.junit.Ignore;
import org.junit.Test;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractCliJarTest}!
 */
public class IncompleteApiImplementationJarTest extends AbstractCliJarTest {

	/***/
	public IncompleteApiImplementationJarTest() {
		super("IncompleteApiImplementationJarTest");
	}

	/**
	 * Compile & Run Tests for IDE-1510
	 *
	 * @throws Exception
	 *             in Error cases
	 */
	@Test
	@Ignore("GH-1291")
	public void testApiImplStub_CompileAndRunWithNodejsPlugin() throws Exception {

		Path workspace = Path.of(TARGET, WORKSPACE_FOLDER).toAbsolutePath();
		Path nodeModulesPath = Paths.get(workspace.toString(), N4JSGlobals.NODE_MODULES).toAbsolutePath();
		Path project = Path.of(workspace.toString(), N4CliHelper.PACKAGES, "one.x.impl").toAbsolutePath();

		N4CliHelper.copyN4jsLibsToLocation(nodeModulesPath, N4JSGlobals.N4JS_RUNTIME);

		CliCompileResult cliResult = n4jsc(COMPILE(project.toFile()));
		assertEquals(cliResult.toString(), 2, cliResult.getTranspiledFilesCount());

		Path fileA = project.resolve("src-gen/AT_IDE-1510_Missing_Method.js");

		ProcessResult nodejsResult = runNodejs(workspace, fileA);
		assertEquals(nodejsResult.toString(), 0, nodejsResult.getExitCode());
		assertTrue(nodejsResult.toString(), nodejsResult.getStdOut()
				.contains("Hello from Implementation one.x.impl::p.A.n4js !"));
		assertTrue(nodejsResult.toString(), nodejsResult.getStdOut()
				.contains("N4ApiNotImplementedError: API for method A.missing_n not implemented yet."));
		assertTrue(nodejsResult.toString(), nodejsResult.getStdOut()
				.contains("N4ApiNotImplementedError: API for method A.missing_someIA not implemented yet."));
	}
}
