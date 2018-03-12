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

import static java.util.Collections.singletonMap;
import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs 'express' with N4JS definition file support.
 */
public class InstallCompileRunN4jscExternalWithDefinitionFilesTest extends BaseN4jscExternalTest {
	File workspace;

	private static final String PROJECT_NAME_N4JS = "project.using.external.from.n4js";
	private static final String PROJECT_NAME_N4JSX = "project.using.external.from.n4jsx";

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_with_n4jsd");
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	@Override
	protected Map<String, String> getNpmDependencies() {
		return singletonMap("express", "@4.15.3");
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testCompileAndRunWithExternalDependenciesAndDefinitionFiles() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String fileToRun = wsRoot + "/" + PROJECT_NAME_N4JS + "/src/Main.n4js";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"-rw", "nodejs",
				"-r", fileToRun,
				"--verbose",
				"--projectlocations", wsRoot,
				"-bt", BuildType.projects.toString(),
				wsRoot + "/" + PROJECT_NAME_N4JS
		};
		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(
				"express properties: init, defaultConfiguration, lazyrouter, handle, use, route, engine, param, set, path, enabled, disabled, enable, disable, acl, bind, checkout, connect, copy, delete, get, head, link, lock, m-search, merge, mkactivity, mkcalendar, mkcol, move, notify, options, patch, post, propfind, proppatch, purge, put, rebind, report, search, subscribe, trace, unbind, unlink, unlock, unsubscribe, all, del, render, listen",
				out);
	}

	/**
	 * Same test as above, but with two changes:
	 * <ol>
	 * <li>importing the external dependency from an N4JSX file (instead of an N4JS file),
	 * <li>using a target platform definition file and letting the 'n4jsc.jar' install the dependency.
	 * </ol>
	 */
	@Test
	public void testCompileAndRunWithExternalDependenciesAndDefinitionFilesFromN4JSX()
			throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String fileToRun = wsRoot + "/" + PROJECT_NAME_N4JSX + "/src/MainX.n4jsx";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--targetPlatformFile", wsRoot + "/targetplatform.n4tp",
				"--targetPlatformInstallLocation", wsRoot + "/targetPlatformInstallLocation",
				"-rw", "nodejs",
				"-r", fileToRun,
				"--verbose",
				"--projectlocations", wsRoot,
				"-bt", BuildType.projects.toString(),
				wsRoot + "/" + PROJECT_NAME_N4JSX
		};

		final String out = runAndCaptureOutput(args);
		N4CliHelper.assertExpectedOutput(
				"express properties: init, defaultConfiguration, lazyrouter, handle, use, route, engine, param, set, path, enabled, disabled, enable, disable, acl, bind, checkout, connect, copy, delete, get, head, link, lock, m-search, merge, mkactivity, mkcalendar, mkcol, move, notify, options, patch, post, propfind, proppatch, purge, put, rebind, report, search, subscribe, trace, unbind, unlink, unlock, unsubscribe, all, del, render, listen",
				out);
	}
}
