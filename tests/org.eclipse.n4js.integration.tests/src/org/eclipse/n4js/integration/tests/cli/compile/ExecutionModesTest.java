/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests.cli.compile;

import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Execute a small project that relies on functionality from "n4js-runtime" with both supported modes of execution:
 * <ul>
 * <li>node's native ESM support (i.e. "type": "module"),
 * <li>esm-library and "-r esm".
 * </ul>
 * The point is to make sure the "n4js-runtime" is compatible with both modes of execution, which relies on the special
 * bundling of the runtime code (see script {@code n4js-libs/packages/n4js-runtime/npm-bundle.sh} for details).
 * Therefore, this test can also be seen as a test for this bundling.
 */

public class ExecutionModesTest extends AbstractCliCompileTest {

	private static final String PROJECT_NAME = "execute-n4js-code";

	private static final String EXPECTED_OUTPUT = "Test of the implementation of built-in types:\n"
			+ "FQN of type A: SomeModule/A\n"
			+ "Test of dependency injection:\n"
			+ "Hello from B!";

	private Path wsp;
	private Path project;

	/** Ensure we get a helpful error message when running this test locally without first starting verdaccio. */
	@BeforeClass
	public static void assertVerdaccioIsRunning() {
		N4jsLibsAccess.assertVerdaccioIsRunning(6, TimeUnit.SECONDS);
	}

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		wsp = setupWorkspace(PROJECT_NAME, false).toPath();
		project = wsp.resolve(PROJECT_NAME);
	}

	@Test
	public void testExecuteWithNativeESM() throws IOException {
		// the proband is configured for native ESM, so all we have to do is copy the "n4js-runtime":
		N4CliHelper.copyN4jsLibsToLocation(
				project.resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		CliCompileResult cliResult = n4jsc(COMPILE(project.toFile()), SUCCESS);
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
		assertEquals(cliResult.toString(), 2, cliResult.getTranspiledFilesCount());

		Path fileMain = project.resolve("src-gen/Main.js");
		ProcessResult nodejsResult = nodejsRun(project, fileMain);
		assertEquals(nodejsResult.toString(), EXPECTED_OUTPUT, nodejsResult.getStdOut());
	}

	@Test
	@Ignore("currently no need to support '-r esm'")
	public void testExecuteWithEsmLibrary() throws IOException {
		// the proband is configured for native ESM, so we have to ...
		// A) patch the package.json:
		replaceInFile(project.resolve(N4JSGlobals.PACKAGE_JSON),
				// 1) remove "type":"module" from package.json
				Pair.of("\"type\": \"module\",", ""),
				// 2) add "esm" to dependencies
				Pair.of("\"dependencies\": {", "\"dependencies\": {\n\t\t\"esm\": \"3.2.25\","));
		// B) install library "esm":
		npmInstall(project);

		CliCompileResult cliResult = n4jsc(COMPILE(project.toFile()), SUCCESS);
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
		assertEquals(cliResult.toString(), 2, cliResult.getTranspiledFilesCount());

		Path fileMain = project.resolve("src-gen/Main.js");
		ProcessResult nodejsResult = nodejsRun(project, new String[] { "-r", "esm" }, fileMain);
		assertEquals(nodejsResult.toString(), EXPECTED_OUTPUT, nodejsResult.getStdOut());
	}

	@SafeVarargs
	private void replaceInFile(Path path, Pair<String, String>... replacements) throws IOException {
		String content = Files.readString(path);
		for (Pair<String, String> replacement : replacements) {
			Assert.assertTrue("file does not contain the string to be replaced: " + replacement.getKey(),
					content.contains(replacement.getKey()));
			content = content.replace(replacement.getKey(), replacement.getValue());
		}
		Files.writeString(path, content, StandardOpenOption.TRUNCATE_EXISTING);
	}
}
