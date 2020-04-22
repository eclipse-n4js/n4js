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
package org.eclipse.n4js.ide.tests.n4idl

import com.google.common.base.Predicates
import java.io.File
import java.io.IOException
import java.nio.file.Path
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest
import org.eclipse.n4js.utils.io.FileDeleter
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE
import static org.junit.Assert.assertEquals
import static org.eclipse.n4js.cli.N4jscExitCode.VALIDATION_ERRORS

/**
 * Compiles and runs a basic N4IDL project.
 *
 * Asserts that all used versions are imported correctly and that no obsolete versions are imported.
 */
public class ObsoleteVersionedImportsTest extends AbstractCliCompileTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public def void setupWorkspace() throws IOException {
		workspace = setupWorkspace("n4idl", Predicates.alwaysTrue(), true);
	}

	/** Delete workspace. */
	@After
	public def void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	// FIXME: GH-1392
	/**
	 * Compiles and runs the test project.
	 *
	 * Asserts the output to be equal to 'ObsoleteVersionedImports/expectations.log'.
	 */
	@Test
	public def void testNoObsoleteVersionsImported() throws IOException {
		val cliResult = n4jsc(COMPILE(workspace), VALIDATION_ERRORS);
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
		assertEquals(cliResult.toString(), 3, cliResult.getTranspiledFilesCount());
		
		val wsRoot = workspace.getAbsolutePath().toString();
		val fileToRun = wsRoot + "/packages/ObsoleteVersionedImports/src-gen/run.js";
		
		val expectedString = '''
		R#1: function R$1() {}
		R#2: undefined
		R#3: function R$3() {}
		PU#1: function P$1() {}
		PU#2: undefined
		AliasS#1: function S$1() {}
		AliasS#2: function S$2() {}
		AliasS#3: function S$3() {}
		T#1: function T$1() {}
		T#2: undefined
		Q#1: undefined''';

		val nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRun));
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}
}
