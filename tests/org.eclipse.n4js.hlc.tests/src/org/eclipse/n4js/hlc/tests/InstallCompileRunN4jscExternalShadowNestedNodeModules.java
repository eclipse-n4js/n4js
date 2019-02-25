/**
 * Copyright (c) 2019 NumberFour AG.
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
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Testing the shadowing of npm packages.
 * <p>
 * Situation:
 * <ul>
 * <li>node_modules folder in yarn workspace root contains an npm package "dep".
 * <li>nested node_modules folder in project "P" contains an npm package called "dep".
 * </ul>
 * Expected behavior:
 * <ol>
 * <li>the npm package in the yarn workspace root should be compiled and linked to all projects in the workspace.
 * <li>the npm package in the nested node_modules folder should *NOT* be compiled.
 * </ol>
 * <p>
 * The above situation will occur if there are version conflicts between the package.json files of the member projects
 * in a yarn workspace. The expected behavior is in line with how the IDE behaves in the UI case.
 */
public class InstallCompileRunN4jscExternalShadowNestedNodeModules extends AbstractN4jscTest {

	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException, ExitCodeException {
		workspace = setupWorkspace("external_shadowNestedNodeModules", Predicates.alwaysFalse(),
				false); // false because test data already contains a fully configured yarn workspace!

		// Additional Preparation Step
		//
		// The test data contains a mocked npm package "dep" that would normally contain generated output code in output
		// folder "src-gen"; however, we do not want to include generated code in the test data (would make test very
		// fragile), so we compile this npm package here:
		new N4jscBase().doMain(
				"--buildType", BuildType.projects.toString(),
				workspace.getAbsolutePath() + "/node_modules/dep");
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
		final String fileToRun = packages + "/P/src/Main.n4js";

		final String[] args = {
				"--projectlocations", packages,
				"--buildType", BuildType.allprojects.toString(),
				"--runWith", "nodejs",
				"--run", fileToRun
		};
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput("Main.n4js in P" + System.lineSeparator()
				+ "#methodOld() in version 2.0.0" + System.lineSeparator()
				+ "#methodNew() in version 2.0.0",
				out);
	}
}
