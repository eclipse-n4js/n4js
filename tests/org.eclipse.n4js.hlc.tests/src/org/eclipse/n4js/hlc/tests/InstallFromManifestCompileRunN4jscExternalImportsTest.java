/**
 * Copyright (c) 2018 NumberFour AG.
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

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Tells N4JSC to build few projects and run one of them. Projects being built have missing dependencies, so N4JSC is
 * instructed to discover missing dependencies and to install them before compilation, which is done with
 * {@code --installMissingDependencies} flag.
 */
public class InstallFromManifestCompileRunN4jscExternalImportsTest extends AbstractN4jscTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_project_install_dependencies", Predicates.alwaysTrue(), true);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/P3/src/f3.n4jsx";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--projectlocations", packages,
				"--buildType", BuildType.allprojects.toString()
		};
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(
				"P1\n" +
						"react is not undefined true\n" +
						"react-dom is not undefined true\n" +
						"imports from libs are different true\n" +
						"P2\n" +
						"React is not undefined true",
				out);
	}

	/**
	 * Similar to the {@link #testCompileAndRunWithExternalDependencies()} but instead of using
	 * {@link BuildType#allprojects} with common root, it is using {@link BuildType#projects} with concrete list of
	 * projects.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies2() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/P3/src/f3.n4jsx";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"-rw", "nodejs",
				"-r", fileToRun,
				"-bt", BuildType.projects.toString(),
				packages + "/P1",
				packages + "/P2",
				packages + "/P3"
		};
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(
				"P1\n" +
						"react is not undefined true\n" +
						"react-dom is not undefined true\n" +
						"imports from libs are different true\n" +
						"P2\n" +
						"React is not undefined true",
				out);
	}

}
