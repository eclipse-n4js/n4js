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
package org.eclipse.n4js.ide.init.tests;

import static org.eclipse.n4js.N4JSGlobals.PACKAGE_JSON;
import static org.eclipse.n4js.cli.N4jscExitCode.INIT_ERROR_WORKING_DIR;
import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscTestOptions.IMPLICIT_COMPILE;
import static org.eclipse.n4js.cli.N4jscTestOptions.INIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for goal init
 */
public class N4jscInitTest extends AbstractCliCompileTest {

	static final String CWD_NAME = "TestInit";

	File cwd;

	/** Set current working directory. */
	@Before
	public void setupWorkspace() throws IOException {
		cwd = new File(CWD_NAME);
		if (cwd.exists()) {
			FileUtils.delete(cwd);
		}
		cwd.mkdir();
	}

	/** Delete current working directory. */
	@After
	public void deleteWorkspace() throws IOException {
		FileUtils.delete(cwd);
	}

	/** Basic init test. */
	@Test
	public void optionYes() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		npmInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(0, result.getTranspiledFilesCount());
	}

	/** fail due to existing package.json file */
	@Test
	public void failDueToExistingPackagejson() throws Exception {
		File packjson = new File(cwd.getAbsoluteFile(), PACKAGE_JSON);
		packjson.createNewFile();

		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes();
		CliCompileResult result = n4jsc(options, INIT_ERROR_WORKING_DIR);

		assertEquals(
				"ERROR-410 (Error: Unsupported working directory):  Current working directory must not contain a package.json file.",
				result.getStdOut());
	}

	/** --scope test. */
	@Test
	public void optionsYesScope() throws Exception {
		File subfolder = new File(cwd, "scopedProject");
		subfolder.mkdir();
		N4jscTestOptions options = INIT().setWorkingDirectory(subfolder.toPath()).yes().scope();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "+ scopedProject\n"
				+ "  - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		String packagejsonContents = Files.readString(subfolder.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"name\": \"@TestInit/scopedProject\",\n"));
	}

	/** test hello world example */
	@Test
	public void helloWorld() throws Exception {
		String answers = "e";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		npmInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());
		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("."));
		assertEquals("Hello World", resultNodejs.getStdOut());
	}

	/** test hello world test example */
	@Test
	public void helloWorldTested() throws Exception {
		String answers = "t";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		npmInstall(cwd.toPath());

		ProcessResult resultBuild = npmRun(cwd.toPath(), "run", "build");
		assertTrue(resultBuild.getStdOut().contains("Transpiled: 4"));

		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("."));
		assertEquals("Hello World", resultNodejs.getStdOut());

		ProcessResult resultTest = npmRun(cwd.toPath(), "run", "test");
		assertTrue(resultTest.getStdOut().contains(
				"Testing completed: [32mSUCCESSES[39m: 1, [31mFAILURES[39m: 0, [31mERRORS[39m: 0, [36mSKIPPED[39m: 0"));
	}

	/** test another project name */
	@Test
	public void otherName() throws Exception {
		String answers = ",otherName";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		CliCompileResult result = n4jsc(options, SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"name\": \"otherName\",\n"));
	}

	/** test also create yarn */
	@Test
	public void yarn() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces(new File("packages"));
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + TestInit2\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		yarnInstall(cwd.toPath());
		n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
	}

	/** test yarn hello world */
	@Test
	public void yarnHelloWorld() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath())
				.workspaces(new File("packages"))
				.answers("e");
		n4jsc(options, SUCCESS);

		yarnInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());
		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("packages/TestInit2"));
		assertEquals("Hello World", resultNodejs.getStdOut());
	}

	/** test yarn hello world test */
	@Test
	public void yarnHelloWorldTested() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath())
				.workspaces(new File("packages"))
				.answers("t");
		n4jsc(options, SUCCESS);

		yarnInstall(cwd.toPath());

		ProcessResult resultBuild = yarnRun(cwd.toPath(), "run", "build");
		assertTrue(resultBuild.getStdOut().contains("Transpiled: 4"));

		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("packages/TestInit2"));
		assertEquals("Hello World", resultNodejs.getStdOut());

		ProcessResult resultTest = yarnRun(cwd.toPath(), "run", "test");
		assertTrue(resultTest.getStdOut().contains(
				"Testing completed: [32mSUCCESSES[39m: 1, [31mFAILURES[39m: 0, [31mERRORS[39m: 0, [36mSKIPPED[39m: 0"));
	}

}
