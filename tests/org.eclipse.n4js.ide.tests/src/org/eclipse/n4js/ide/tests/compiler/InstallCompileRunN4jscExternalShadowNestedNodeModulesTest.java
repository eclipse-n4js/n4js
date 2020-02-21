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
package org.eclipse.n4js.ide.tests.compiler;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.helper.ProcessResult;
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
public class InstallCompileRunN4jscExternalShadowNestedNodeModulesTest extends AbstractCliCompileTest {

	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_shadowNestedNodeModules", Predicates.alwaysFalse(),
				false); // false because test data already contains a fully configured yarn workspace!

		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 *
	 * TODO: use System.lineSeparator()
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() {
		final Path wsRoot = workspace.getAbsoluteFile().toPath();
		final Path project = wsRoot.resolve("packages").resolve("P");
		final Path fileToRun = project.resolve("src-gen").resolve("Main.js");

		N4jscOptions options = COMPILE(workspace);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		String expectedString = "Main.n4js in P\n";
		expectedString += "#methodOld() in version 2.0.0\n";
		expectedString += "#methodNew() in version 2.0.0";

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), fileToRun);
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}
}
