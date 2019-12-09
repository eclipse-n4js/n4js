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

import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests a project with two project dependencies A,B such that A depends on B.
 *
 * In this test, we have two external projects: nuka-carousel and react. nuka-carousel depends on react via a project
 * dependency, and file P1/src/X.n4jsx depends on both react and nuka-carousel via project dependencies.
 */
public class N4jscDependentProjectDependenciesTest extends AbstractCliCompileTest {
	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_project_dependencies", true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Test failure when compiling without target platform file. */
	@Test
	public void testSuccessfulCompilationWithInterdependentProjects() {
		ProcessResult yarnInstallResult = yarnInstall(workspace.toPath());
		assertEquals(yarnInstallResult.toString(), 0, yarnInstallResult.getExitCode());

		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 3, cliResult.getTranspiledFilesCount());
	}

}
