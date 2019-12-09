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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * Basic tests for N4jsc, testing various situations in which compiler exits with errors.
 */
public class N4jscBasicTest extends AbstractCliCompileTest {

	static final boolean DONT_CLEAN = false;

	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("basic", true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Basic compile test. */
	@Test
	public void testMainArgsCompileAllKeepCompiling() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 16, cliResult.getTranspiledFilesCount());
	}

	/** Basic compile and run test. */
	@Test
	public void testCompileP1_And_Run_A_WithNodeRunner() throws Exception {
		// because we wanna execute stuff, we have to install the runtime:
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 16, cliResult.getTranspiledFilesCount());

		Path fileA = proot.toPath().resolve("P1/src-gen/A.js");

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), fileA);
		assertEquals(nodejsResult.toString(), "A", nodejsResult.getStdOut());
	}

	/** Test a second run of the compiler on the same sources */
	@Test
	public void testRunTwice() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());
		Path fileA = proot.toPath().resolve("P1/.n4js.projectstate");
		assertTrue(fileA.toFile().getAbsolutePath(), fileA.toFile().exists());

		CliCompileResult secondRun = n4jsc(compileOptions);
		assertEquals(secondRun.toString(), 0, secondRun.getTranspiledFilesCount());

		assertOutputEqual(firstRun, secondRun);
	}

	/** Test a second run of the compiler after a source file was removed */
	@Test
	public void testFileRemovedIfN4JSRemoved() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());

		File fileAn4js = proot.toPath().resolve("P1/src/A.n4js").toFile();
		assertTrue(fileAn4js.getAbsolutePath(), fileAn4js.isFile());
		assertTrue(fileAn4js.getAbsolutePath(), fileAn4js.exists());
		assertTrue(fileAn4js.getAbsolutePath(), fileAn4js.delete());
		assertFalse(fileAn4js.getAbsolutePath(), fileAn4js.exists());

		File fileAjs = proot.toPath().resolve("P1/src-gen/A.js").toFile();
		assertTrue(fileAjs.exists());
		File fileAmap = proot.toPath().resolve("P1/src-gen/A.map").toFile();
		assertTrue(fileAmap.exists());

		CliCompileResult secondRun = n4jsc(compileOptions);
		assertEquals(secondRun.toString(), 0, secondRun.getTranspiledFilesCount());
		assertEquals(secondRun.toString(), 2, secondRun.getDeletedFilesCount());

		assertFalse(fileAjs.exists());
		assertFalse(fileAmap.exists());
	}

	/** Test a second run of the compiler after a generated map file was removed */
	@Test
	public void testFileRegeneratedIfJSRemoved() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());

		File fileAjs = proot.toPath().resolve("P1/src-gen/A.js").toFile();
		assertTrue(fileAjs.getAbsolutePath(), fileAjs.isFile());
		assertTrue(fileAjs.getAbsolutePath(), fileAjs.exists());
		assertTrue(fileAjs.getAbsolutePath(), fileAjs.delete());
		assertFalse(fileAjs.getAbsolutePath(), fileAjs.exists());

		CliCompileResult secondRun = n4jsc(compileOptions);
		assertEquals(secondRun.toString(), 1, secondRun.getTranspiledFilesCount());
		assertEquals(secondRun.toString(), 0, secondRun.getDeletedFilesCount());

		assertTrue(fileAjs.exists());
		File fileAmap = proot.toPath().resolve("P1/src-gen/A.map").toFile();
		assertTrue(fileAmap.exists());

		assertOutputEqual(firstRun, secondRun);
	}

	/** Test a second run of the compiler after a generated js file was removed */
	@Test
	public void testFileRegeneratedIfMapRemoved() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());

		File fileAmap = proot.toPath().resolve("P1/src-gen/A.map").toFile();
		assertTrue(fileAmap.getAbsolutePath(), fileAmap.isFile());
		assertTrue(fileAmap.getAbsolutePath(), fileAmap.exists());
		assertTrue(fileAmap.getAbsolutePath(), fileAmap.delete());
		assertFalse(fileAmap.getAbsolutePath(), fileAmap.exists());

		CliCompileResult secondRun = n4jsc(compileOptions);
		assertEquals(secondRun.toString(), 1, secondRun.getTranspiledFilesCount());
		assertEquals(secondRun.toString(), 0, secondRun.getDeletedFilesCount());

		assertTrue(fileAmap.exists());
		File fileAjs = proot.toPath().resolve("P1/src-gen/A.js").toFile();
		assertTrue(fileAjs.exists());

		assertOutputEqual(firstRun, secondRun);
	}

	/** Test a second run of the compiler after a file with downstream files was removed */
	@Test
	public void testBuildDependentFilesIfFileRemoved() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());

		File fileCn4js = proot.toPath().resolve("P1/src/c/C.n4js").toFile();
		assertTrue(fileCn4js.getAbsolutePath(), fileCn4js.isFile());
		assertTrue(fileCn4js.getAbsolutePath(), fileCn4js.exists());
		assertTrue(fileCn4js.getAbsolutePath(), fileCn4js.delete());
		assertFalse(fileCn4js.getAbsolutePath(), fileCn4js.exists());

		CliCompileResult secondRun = n4jsc(compileOptions);
		assertEquals(secondRun.toString(), 0, secondRun.getTranspiledFilesCount());
		// C and Y are affected (js and map)
		assertEquals(secondRun.toString(), 4, secondRun.getDeletedFilesCount());

		// Y.n4js depends on C.n4js
		File fileYjs = proot.toPath().resolve("P1/src-gen/y/Y.js").toFile();
		assertFalse(fileYjs.exists());
	}

	/** Test a second run of the compiler after a file was added to fix compile problems */
	@Test
	public void testBuildDependentFilesIfFileReadded() throws Exception {
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME);

		N4jscTestOptions compileOptions = COMPILE(DONT_CLEAN, workspace);

		CliCompileResult firstRun = n4jsc(compileOptions);
		assertEquals(firstRun.toString(), 16, firstRun.getTranspiledFilesCount());

		Path fileCn4js = proot.toPath().resolve("P1/src/c/C.n4js");
		byte[] bytesC = Files.readAllBytes(fileCn4js);
		Files.delete(fileCn4js);

		n4jsc(compileOptions);

		// Y.n4js depends on C.n4js
		File fileCjs = proot.toPath().resolve("P1/src-gen/c/C.js").toFile();
		assertFalse(fileCjs.exists());
		File fileYjs = proot.toPath().resolve("P1/src-gen/y/Y.js").toFile();
		assertFalse(fileYjs.exists());

		Files.write(fileCn4js, bytesC);

		CliCompileResult thirdRun = n4jsc(compileOptions);

		assertTrue(fileCjs.exists());
		assertTrue(fileYjs.exists());

		assertOutputEqual(firstRun, thirdRun);
	}

	private void assertOutputEqual(CliCompileResult expected, CliCompileResult actual) {
		assertCollEquals(expected.getWrnFiles(), actual.getWrnFiles());
		assertCollEquals(expected.getWrnMsgs(), actual.getWrnMsgs());
		assertCollEquals(expected.getErrFiles(), actual.getErrFiles());
		assertCollEquals(expected.getErrMsgs(), actual.getErrMsgs());
	}

	private <T, C extends Collection<T>> void assertCollEquals(C expected, C actual) {
		final Joiner joiner = Joiner.on('\n');
		assertEquals(joiner.join(expected), joiner.join(actual));
	}

}
