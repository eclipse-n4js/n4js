/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests.cli.compile;

import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscTestOptions.SETVERSIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Tests for goal set-versions
 */
public class N4jscSetVersionsTest extends AbstractCliCompileTest {

	static final boolean DONT_CLEAN = false;

	File workspace;
	File proot;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("basic", true);
		N4CliHelper.copyN4jsLibsToLocation(workspace.toPath().resolve(N4JSGlobals.NODE_MODULES),
				N4JSGlobals.N4JS_RUNTIME, N4JSGlobals.MANGELHAFT_ASSERT);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Basic compile test. */
	@Test
	public void testSetVersions() throws Exception {
		N4jscTestOptions options = SETVERSIONS("1.2.3").setWorkingDirectory(proot.toPath()).verbose();
		CliCompileResult cliResult = n4jsc(options, SUCCESS);
		assertTrue(cliResult.getStdOut().contains(
				"Modified version string of dependencies to:\n"
						+ " - n4js-cli\n"
						+ " - n4js-mangelhaft-cli\n"
						+ " - n4js-runtime\n"
						+ " - n4js-runtime-ecma402\n"
						+ " - n4js-runtime-es2015\n"
						+ " - n4js-runtime-esnext\n"
						+ " - n4js-runtime-html5\n"
						+ " - n4js-runtime-v8\n"
						+ " - org.eclipse.n4js.mangelhaft\n"
						+ " - org.eclipse.n4js.mangelhaft.assert\n"
						+ " - org.eclipse.n4js.mangelhaft.assert.test\n"
						+ " - org.eclipse.n4js.mangelhaft.reporter.console\n"
						+ " - org.eclipse.n4js.mangelhaft.reporter.xunit\n"
						+ " - org.eclipse.n4js.mangelhaft.test\n"
						+ "in the following files:\n"
						+ " - .../P1/package.json\n"
						+ " - .../P2/package.json\n"
						+ " - .../P3/package.json\n"
						+ " - .../P4/package.json\n"
						+ " - .../PSingle/package.json\n"
						+ " - .../Test01/package.json\n"
						+ " - .../TestCleanPrj1/package.json\n"
						+ " - .../TestCleanPrj2/package.json"));

		File packagejson = new File(new File(proot, "P1"), N4JSGlobals.PACKAGE_JSON);
		Gson gson = new Gson();
		try (FileReader fr = new FileReader(packagejson)) {
			JsonObject object = gson.fromJson(fr, JsonObject.class);
			JsonObject dependencies = (JsonObject) object.get("dependencies");
			JsonPrimitive depN4jsRuntime = (JsonPrimitive) dependencies.get("n4js-runtime");
			assertEquals("1.2.3", depN4jsRuntime.getAsString());
		}

	}

}
