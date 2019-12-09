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

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class AT_GHOLD_212_transpilecrashTest extends AbstractCliCompileTest {

	File workspace;
	static String WSP_212 = "GHOLD-212";

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(WSP_212, true);
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

		// Make sure, we get here and have exactly one file compiled:
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());
	}
}
