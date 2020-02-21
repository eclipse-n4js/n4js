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
package org.eclipse.n4js.ide.tests.compiler;

import static org.eclipse.n4js.cli.N4jscTestOptions.CLEAN;
import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for N4jsc, testing various situations in which compiler exits with errors.
 */
public class N4jscCleanTest extends AbstractCliCompileTest {
	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("simple", true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Basic compile test. */
	@Test
	public void testCompile() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 4, cliResult.getTranspiledFilesCount());
	}

	/** Basic compile test. */
	@Test
	public void testCompileThenClean() {
		CliCompileResult compileResult = n4jsc(COMPILE(workspace));
		assertEquals(compileResult.toString(), 4, compileResult.getTranspiledFilesCount());

		CliCompileResult cleanResult = n4jsc(CLEAN(workspace));
		assertEquals(cleanResult.toString(), 8, cleanResult.getDeletedFilesCount());
	}

	/** Basic compile test. */
	@Test
	public void testCleanCompile() {
		CliCompileResult compileResult = n4jsc(COMPILE(workspace));
		assertEquals(compileResult.toString(), 4, compileResult.getTranspiledFilesCount());

		CliCompileResult cleanResult = n4jsc(COMPILE(workspace).clean());
		assertEquals(cleanResult.toString(), 8, cleanResult.getDeletedFilesCount());
	}

}
