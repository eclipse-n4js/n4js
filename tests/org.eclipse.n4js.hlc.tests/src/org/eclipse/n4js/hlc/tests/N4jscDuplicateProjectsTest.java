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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.SuccessExitStatus;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests behavior of N4JSC when user passes duplicate projects
 */
public class N4jscDuplicateProjectsTest extends AbstractN4jscTest {
	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("duplicates", true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Compiling a project with duplicated dependency should result in error.
	 */
	@Test
	public void testCompileDuplicateP1_DifferentProjects() {

		// Project
		String pathToP1_1 = proot + "/root1/P1";
		String pathToP1_3 = proot + "/root3/P1";
		String pathToClient = proot + "/root2/Client";

		String[] args = {
				"--buildType", BuildType.projects.toString(),
				pathToClient,
				pathToP1_1,
				/* project with the same ID but in different location */
				pathToP1_3

		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	/**
	 * Compiling a project with same dependency passed twice should not result in error.
	 */
	@Test
	public void testCompileDuplicateP1_SameProjectTwice() throws ExitCodeException {

		// Project
		String pathToP1_1 = proot + "/root1/P1";
		String pathToClient = proot + "/root2/Client";

		String[] args = {
				"--buildType", BuildType.projects.toString(),
				pathToClient,
				pathToP1_1,
				// let compiler "discover" P1 again
				"--projectlocations", proot + "/root1/"

		};
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, status.code);
	}
}
