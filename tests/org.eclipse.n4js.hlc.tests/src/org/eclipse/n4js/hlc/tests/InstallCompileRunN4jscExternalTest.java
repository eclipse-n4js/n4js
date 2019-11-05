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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs 'express'.
 */
public class InstallCompileRunN4jscExternalTest extends AbstractCliCompileTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external", Predicates.alwaysFalse(), true);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for checking the npm support by downloading a third party package, importing and running it.
	 */
	@Test
	@Ignore // GH-1510
	public void testCompileAndRunWithExternalDependencies() {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/external.project/src-gen/Main.js";

		ProcessResult yarnInstallResult = yarnInstall(workspace.toPath());
		// error An unexpected error occurred: "could not find a copy of eslint to link in:
		// wsp/node_modules/is-promise/node_modules/listr-update-renderer/node_modules/ansi-regex/node_modules/xo/node_modules"
		assertEquals(yarnInstallResult.toString(), 0, yarnInstallResult.getExitCode());
		// assertTrue(yarnInstallResult.toString(),
		// yarnInstallResult.getErrOut().contains("could not find a copy of eslint to link in"));

		N4jscOptions options = COMPILE(workspace);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		String expectedString = "express properties: Route, Router, application, bodyParser, compress, ";
		expectedString += "cookieParser, cookieSession, csrf, default, directory, errorHandler, favicon, json, ";
		expectedString += "limit, logger, methodOverride, multipart, query, request, response, responseTime, ";
		expectedString += "session, static, staticCache, timeout, urlencoded, vhost";

		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRun));
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

}
