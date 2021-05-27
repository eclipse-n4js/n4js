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

import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
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
		n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
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

	/** --answers test. */
	@Test
	public void optionsAnswers() throws Exception {
		String answers = "otherName";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"name\": \"otherName\",\n"));
	}

}
