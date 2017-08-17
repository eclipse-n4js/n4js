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

import static org.eclipse.n4js.hlc.tests.IncompleteApiImplementationTest.runCaptureOut;
import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase.BuildType;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs several packages that are known to be problematic in terms of how they define
 * main module.
 */
public class InstallCompileRunN4jscExternalMainModuleTest extends BaseN4jscExternalTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("externalmm", Predicates.alwaysTrue());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	@Override
	protected Map<String, String> getNpmDependencies() {
		Map<String, String> deps = new HashMap<>();

		// main is "index.js"
		deps.put("express", "@4.13.4");

		// main is "lib", there is index.js in lib folder
		deps.put("jade", "@1.11.0");

		// main is "lodash.js"
		deps.put("lodash", "@4.6.0");

		// TODO karma is commented out due to bumping up to Node.js 6.x and the below described deprecation warnings:
		// (node) v8::ObjectTemplate::Set() with non-primitive values is deprecated
		// (node) and will stop working in the next major release.
		// // main is "./lib/index"
		// deps.put("karma", "@0.13.21");

		// main is "./lib/bar", but there is lib folder and lib.js file
		deps.put("bar", "@0.1.2");

		// main is "./lib/index.js"
		deps.put("pouchdb-find", "@0.10.3");

		// no main
		deps.put("body-parser", "@1.15.0");

		// broken main (defined in the package.json, but does not exist in the npm package)
		deps.put("next", "@1.1.1");

		return deps;
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();
		final String fileToRun = wsRoot + "/external.project.mm/src/Main.n4js";

		final String[] args = {
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--systemLoader", COMMON_JS.getId(),
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"-rw", "nodejs",
				"-r", fileToRun,
				"--verbose"
		};
		final String actual = runCaptureOut(args);
		StringBuilder expected = new StringBuilder()
				.append("\\(node:(\\d)+\\) DeprecationWarning: sys is deprecated\\. Use util instead\\.")
				.append("express imported").append("\n")
				.append("jade imported").append("\n")
				.append("lodash imported").append("\n")
				// TODO enable this when karma npm package is enabled again.
				// .append("karma imported").append("\n")
				.append("bar imported").append("\n") // Bar uses deprecated 'sys'
				.append("pouchdb-find imported").append("\n")
				.append("next imported").append("\n")
				.append("body-parser imported");

		Assert.assertTrue(
				"Actual output does not match with pattern.\nActual:\n" + actual + "\nExpected pattern:\n" + expected,
				actual.matches(expected.toString()));

	}

}
