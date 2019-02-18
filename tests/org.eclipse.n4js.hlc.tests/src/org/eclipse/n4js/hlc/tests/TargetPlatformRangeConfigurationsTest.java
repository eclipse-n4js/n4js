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

import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs 'express' where target platform file specifies version range.
 */
@Ignore("See GH-940")
public class TargetPlatformRangeConfigurationsTest extends AbstractN4jscTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external", Predicates.alwaysTrue(), true);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for compiling project with external dependency specified within version range.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String packages = wsRoot + "/packages";
		final String fileToRun = packages + "/external.project/src/Main.n4js";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies",
				"--runWith", "nodejs",
				"--run", fileToRun,
				"--projectlocations", packages,
				"--buildType", BuildType.allprojects.toString()
		};
		final String out = runAndCaptureOutput(args);
		// note: express version 4.16.0 introduced more properties, so by checking for the following properties we can
		// assert that the version range ">=4.0.0 <4.16.0" specified above was taken into account during installation:
		N4CliHelper.assertExpectedOutput(
				"express properties: application, request, response, Route, Router, query, static", out);
		// express version 4.16.0 or 4.16.1 would give us:
		// "express properties: application, request, response, Route, Router, json, query, static, urlencoded"
	}
}
