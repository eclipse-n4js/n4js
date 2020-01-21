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
package org.eclipse.n4js.ide.tests.jar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The tests in this class test n4js-cli npm.
 */
public class CliJarWithVerdaccioJarTest extends AbstractCliJarTest {
	private static final int PORT = 4873;
	private static final String LOCALHOST = "localhost";
	private static final String PROJECT_NAME = "PSingleTestNpm";
	private static final String LOCAL_NPM_ADDRESS = "http://" + LOCALHOST + ":" + PORT;

	private static final Path PROJECT = Path.of(TARGET, WORKSPACE_FOLDER, PACKAGES, PROJECT_NAME);

	/***/
	public CliJarWithVerdaccioJarTest() {
		super("fixture");
	}

	/** Checks that verdaccio is running. Creates .npmrc file. */
	@Before
	public void setupNpmRegstry() throws IOException {
		// Before we make the effort to test anything, check if the npm local registry is available!
		assertTrue("Local npm registry is not found at " + LOCAL_NPM_ADDRESS, checkVerdaccio());

		// Create .npmrc file in the PSingleTestNpm folder
		File npmrcFile = new File(PROJECT.toString(), ".npmrc");
		if (npmrcFile.exists()) {
			npmrcFile.delete();
		}
		npmrcFile.createNewFile();
		PrintWriter out = new PrintWriter(npmrcFile);
		out.println("registry=" + LOCAL_NPM_ADDRESS);
		out.close();
	}

	/** Removes .npmrc file */
	@After
	public void tearDown() {
		// Remove .npmrc file in the PSingleTestNpm folder
		File npmrcFile = new File(PROJECT.toString() + File.separatorChar + ".npmrc");
		if (npmrcFile.exists()) {
			npmrcFile.delete();
		}
	}

	/** Test that n4js-cli@test is installed from local verdaccio and then call n4js-cli --help */
	@Test
	public void testN4JSCliHelp() {

		// Step 1: Call npm install in PSingleTestNpm folder
		npmInstall(PROJECT);

		// Step 2: List all installed packages and check version of n4js-runtime
		ProcessResult npmListResult1 = npmList(PROJECT);
		assertTrue(npmListResult1.toString(), npmListResult1.getStdOut().contains("n4js-runtime@0.0.1"));

		// Step 3: Install n4js-cli@latest in the project 'PSingleTestNpm'
		npmInstall(PROJECT, "n4js-cli@latest");

		// Step 4: List all installed packages and check version of n4js-cli
		ProcessResult npmListResult2 = npmList(PROJECT);
		assertEquals(npmListResult2.toString(), 0, npmListResult2.getExitCode());
		assertTrue(npmListResult2.toString(), npmListResult2.getStdOut().contains("n4js-cli@0.0.1"));

		// Step 5: Test that calling n4js-cli is OK
		Path runFile = Path.of(N4JSGlobals.NODE_MODULES, "n4js-cli", "bin", "n4jsc.js");
		ProcessResult nodeResult = runNodejs(PROJECT, runFile, "--help");
		assertTrue(nodeResult.toString(), nodeResult.getStdOut().contains("Usage"));
	}

	private static boolean checkVerdaccio() {
		try (Socket s = new Socket(LOCALHOST, PORT)) {
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

}
