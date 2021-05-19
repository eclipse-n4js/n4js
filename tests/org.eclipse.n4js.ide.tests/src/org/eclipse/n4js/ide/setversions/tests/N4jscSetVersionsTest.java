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
package org.eclipse.n4js.ide.setversions.tests;

import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscTestOptions.SET_VERSIONS;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for goal set-versions
 */
public class N4jscSetVersionsTest extends AbstractCliCompileTest {

	static final boolean DONT_CLEAN = false;

	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("basic", true);
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME, N4JSGlobals.MANGELHAFT_ASSERT);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Basic compile test. */
	@Test
	public void testSetVersions() {
		CliCompileResult cliResult = n4jsc(SET_VERSIONS("1.2.3").setWorkingDirectory(proot.toPath()), SUCCESS);
		assertEquals("", cliResult.getStdOut());
	}

}
