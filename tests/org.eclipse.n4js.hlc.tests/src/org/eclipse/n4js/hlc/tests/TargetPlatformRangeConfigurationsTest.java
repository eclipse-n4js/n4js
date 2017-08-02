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
import static org.eclipse.n4js.hlc.tests.IncompleteApiImplementationTest.runCaptureOut;
import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.IOException;
import java.util.Map;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase.BuildType;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs 'express' where target platform file specifies version range.
 */
public class TargetPlatformRangeConfigurationsTest extends BaseN4jscExternalTest {

	@Override
	protected Map<String, String> getNpmDependencies() {
		return singletonMap("express", "@\">=4.0.0 <5.0.0\"");
	}

	/**
	 * Test for compiling project with external dependency specified within version range.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() throws IOException, ExitCodeException {
		System.out.println(name.getMethodName());
		setupWorkspace("external");
		final String wsRoot = TARGET + "/" + WSP;
		final String fileToRun = wsRoot + "/external.project/src/Main.n4js";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"-rw", "nodejs",
				"-r", fileToRun,
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		final String out = runCaptureOut(args);
		N4CliHelper.assertExpectedOutput(
				"express properties: application, request, response, Route, Router, query, static", out);
	}
}
