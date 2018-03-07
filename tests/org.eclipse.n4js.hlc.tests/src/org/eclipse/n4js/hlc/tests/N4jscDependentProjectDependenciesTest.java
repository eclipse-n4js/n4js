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

import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.SuccessExitStatus;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests a project with two project dependencies A,B such that A depends on B.
 *
 * In this test, we have two external projects: nuka-carousel and react. nuka-carousel depends on react via a project
 * dependency, and file P1/src/X.n4jsx depends on both react and nuka-carousel via project dependencies. By GH-448, this
 * setup has errors when compiling X.n4jsx because the subtype check for NukaCarousel <: React.Component fails.
 *
 * See https://github.com/eclipse/n4js/issues/448.
 */
public class N4jscDependentProjectDependenciesTest extends BaseN4jscExternalTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_project_dependencies");
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test failure when compiling without target platform file.
	 *
	 * @throws ExitCodeException
	 *             propagated from compiler in case of issues
	 */
	@Test
	public void testSuccessfulCompilationWithInterdependentProjects() throws ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-bt", BuildType.allprojects.toString()
		};
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, status.code);
		assertTrue("install location was not created", getTargetPlatformInstallLocation().exists());
	}

}
