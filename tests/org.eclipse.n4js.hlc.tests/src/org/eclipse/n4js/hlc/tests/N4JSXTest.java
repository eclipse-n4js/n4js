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
import java.util.Collection;

import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for N4jsc, like checking command line options or simple compile.
 */
public class N4JSXTest extends AbstractCliCompileTest {
	File workspace;
	File proot;
	private static final String TEST_DATA_SET__N4JSX = "n4jsx";

	/** Prepare tests. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__N4JSX, true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Compile an n4jsx workspace. */
	@Test
	public void testN4JSX() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace));

		Collection<String> fileNames = cliResult.getTranspiledFileNames();

		String expected = "packages/P1/src-gen/bar.js, ";
		expected += "packages/P2/src-gen/foo.js, ";
		expected += "packages/P2/src-gen/bar.js, ";
		expected += "packages/P2/src-gen/bar2.js, ";
		expected += "packages/P3/src-gen/bar.js, ";
		expected += "packages/P3/src-gen/foo.js";

		assertEquals(cliResult.toString(), 6, cliResult.getTranspiledFilesCount());
		assertEquals(cliResult.toString(), expected, String.join(", ", fileNames));
	}

}
