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

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests behaviour of N4JSC when user passes duplicate projects
 */
public class N4jscDuplicateProjectsTest extends AbstractN4jscTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("duplicates");
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Simple test of compiling a project with duplicated dependency.
	 */
	@Test
	public void testCompileDuplicateP1_And_Run_A_WithNodeRunner() {

		String proot = workspace.getAbsolutePath().toString();

		// Project
		String pathToP1_1 = proot + "/root1/P1";
		String pathToP1_3 = proot + "/root3/P1";
		String pathToClient = proot + "/root2/Client";

		// absolute src filename
		String fileToRun = proot + "/root2/Client/src/C.n4js";

		String[] args = {
				"--verbose",
				"--verbose",
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--debug",
				"--buildType", BuildType.projects.toString(),
				pathToClient,
				pathToP1_1,
				/* project with the same ID but in different location */
				pathToP1_3

		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}
}
