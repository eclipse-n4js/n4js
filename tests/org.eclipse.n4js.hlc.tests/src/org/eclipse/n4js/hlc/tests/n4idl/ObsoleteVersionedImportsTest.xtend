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
package org.eclipse.n4js.hlc.tests.n4idl

import com.google.common.base.Predicates
import java.io.File
import java.io.IOException
import org.eclipse.n4js.hlc.base.BuildType
import org.eclipse.n4js.hlc.base.ExitCodeException
import org.eclipse.n4js.hlc.tests.AbstractN4jscTest
import org.eclipse.n4js.test.helper.hlc.N4CliHelper
import org.eclipse.n4js.utils.io.FileDeleter
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS

/**
 * Compiles and runs a basic N4IDL project.
 *
 * Asserts that all used versions are imported correctly and that no obsolete versions are imported.
 */
public class ObsoleteVersionedImportsTest extends AbstractN4jscTest {
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

	/**
	 * Compiles the project and runs the given file.
	 *
	 * @param projectName
	 *            The project to run. This must be an existing project in the configure workspace (see
	 *            {@link #setupWorkspace()})
	 * @param fileToRunFQN
	 *            The fully qualified name of the file to run. Note that this file is assumed to be contained in the
	 *            source folder 'src/' of the project.
	 * @return The output of the runner
	 */
	private def String compileAndRun(String projectName, String fileToRunFQN) throws ExitCodeException, IOException {
		val wsRoot = workspace.getAbsolutePath().toString();
		val packages = wsRoot + "/packages";

		val fileToRun = packages + "/" + projectName + "/src-ext/" + fileToRunFQN;
		val projectToCompile = packages + "/" + projectName;

		val String[] args = #[
				"--systemLoader", COMMON_JS.getId(),
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--buildType", BuildType.projects.toString(),
				projectToCompile];
		
		return runAndCaptureOutput(args);
	}

	/**
	 * Compiles and runs the test project.
	 *
	 * Asserts the output to be equal to 'ObsoleteVersionedImports/expectations.log'.
	 */
	@Test
	public def void testNoObsoleteVersionsImported() throws IOException, ExitCodeException {
		val out = compileAndRun("ObsoleteVersionedImports", "run.js");
		val expectations = '''
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

		N4CliHelper.assertExpectedOutput(expectations, out);
	}
}
