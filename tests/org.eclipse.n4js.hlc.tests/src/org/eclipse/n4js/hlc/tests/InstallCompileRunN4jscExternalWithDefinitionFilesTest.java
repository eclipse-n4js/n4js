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

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs 'express' with N4JS definition file support.
 */
public class InstallCompileRunN4jscExternalWithDefinitionFilesTest extends AbstractN4jscTest {
	File workspace;

	private static final String PROJECT_NAME_N4JS = "project.using.external.from.n4js";
	private static final String PROJECT_NAME_N4JSX = "project.using.external.from.n4jsx";

	private static final String EXPECTED = createExpectedOutput();

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_with_n4jsd", Predicates.alwaysTrue(), true);
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
	public void testCompileAndRunWithExternalDependenciesAndDefinitionFiles() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/" + PROJECT_NAME_N4JS + "/src/Main.n4js";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--projectlocations", packages,
				"--buildType", BuildType.projects.toString(),
				packages + "/" + PROJECT_NAME_N4JS,
				packages + "/n4js-runtime-node"
		};
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(EXPECTED, out);
	}

	/**
	 * Same test as above, but importing the external dependency from an N4JSX file (instead of an N4JS file).
	 */
	@Test
	public void testCompileAndRunWithExternalDependenciesAndDefinitionFilesFromN4JSX()
			throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/" + PROJECT_NAME_N4JSX + "/src/MainX.n4jsx";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--projectlocations", packages,
				"--buildType", BuildType.projects.toString(),
				packages + "/" + PROJECT_NAME_N4JSX,
				packages + "/n4js-runtime-node"
		};

		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(EXPECTED, out);
	}

	/** Keep in sync with the modules being executed ( {@code Main} and {@code MainX}). */
	private static String createExpectedOutput() {
		StringJoiner expected = new StringJoiner("\n ");
		expected.add("express");
		expected.add("has own prop init");
		expected.add("has own prop defaultConfiguration");
		expected.add("has own prop lazyrouter");
		expected.add("has own prop handle");
		expected.add("has own prop use");
		expected.add("has own prop route");
		expected.add("has own prop engine");
		expected.add("has own prop param");
		expected.add("has own prop listen");
		expected.add("has own prop render");
		return expected.toString();
	}
}
