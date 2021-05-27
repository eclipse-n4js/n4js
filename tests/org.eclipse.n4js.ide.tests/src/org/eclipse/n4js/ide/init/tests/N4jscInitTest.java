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

import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscTestOptions.IMPLICIT_COMPILE;
import static org.eclipse.n4js.cli.N4jscTestOptions.INIT;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
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

	/** Create current working directory. */
	@Before
	public void setupWorkspace() throws IOException {
		cwd = new File(CWD_NAME);
		if (cwd.exists()) {
			FileUtils.delete(cwd);
		}
	}

	/** Delete current working directory. */
	@After
	public void deleteWorkspace() throws IOException {
		FileUtils.delete(cwd);
	}

	/** Basic init test. */
	@Test
	public void testYes() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		npmInstall(cwd.toPath());
		n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
	}

}
