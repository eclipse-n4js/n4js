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
package org.eclipse.n4js.hlc.tests.n4idl;

import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.tests.BaseN4jscExternalTest;
import org.eclipse.n4js.hlc.tests.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Compiles and runs a basic N4IDL project.
 *
 * Asserts that all used versions are imported correctly and that no obsolete versions are imported.
 */
public class ObsoleteVersionedImportsTest extends BaseN4jscExternalTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("n4idl", Predicates.alwaysTrue());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
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
	private String compileAndRun(String projectName, String fileToRunFQN) throws ExitCodeException, IOException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String fileToRun = wsRoot + "/" + projectName + "/src/" + fileToRunFQN;
		final String projectToCompile = wsRoot + "/" + projectName;

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"-rw", "nodejs",
				"-r", fileToRun,
				"--verbose",
				"-t", BuildType.projects.toString(),
				projectToCompile,
				"-r",
				fileToRun
		};
		return runAndCaptureOutput(args);
	}

	/**
	 * Compiles and runs the test project.
	 *
	 * Assert the output to be equal to 'ObsoleteVersionedImports/expectations.log'.
	 */
	@Test
	public void testNoObsoleteVersionsImported() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String outputExpectationsFile = wsRoot + "/" + "ObsoleteVersionedImports" + "/expectations.log";

		final String out = compileAndRun("ObsoleteVersionedImports", "imports/ImplicitVersionImport_run.n4js");
		final String expectations = N4CliHelper.readLogfile(new File(outputExpectationsFile));

		N4CliHelper.assertExpectedOutput(expectations, out);
	}

}
