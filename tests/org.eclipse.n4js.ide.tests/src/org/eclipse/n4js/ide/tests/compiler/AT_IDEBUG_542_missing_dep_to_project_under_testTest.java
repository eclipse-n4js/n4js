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
package org.eclipse.n4js.ide.tests.compiler;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class AT_IDEBUG_542_missing_dep_to_project_under_testTest extends AbstractCliCompileTest {

	File workspace;
	static String WSP_542 = "IDEBUG-542";

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(WSP_542, true,
				N4JSGlobals.N4JS_RUNTIME,
				N4JSGlobals.MANGELHAFT,
				new N4JSProjectName("n4js-runtime-es2015"));
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** The Problem was, that nothing was compiled. */
	@Test
	public void testCompileOfExtendedIterator_from_RuntimeLibrary() {

		File proot = new File(workspace, PACKAGES);

		N4jscOptions options = COMPILE(proot);

		CliCompileResult cliResult = n4jsc(options);

		// Make sure, we get here and have exactly two files compiled:
		assertEquals(cliResult.toString(), 0, cliResult.getTranspiledFilesCount(proot.toPath().resolve("APIx")));
		assertEquals(cliResult.toString(), 2, cliResult.getTranspiledFilesCount(proot.toPath().resolve("APIx-test")));
	}
}
