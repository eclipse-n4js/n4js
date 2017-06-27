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
package org.eclipse.n4js.hlc;

import static java.util.Collections.singletonMap;
import static org.eclipse.n4js.hlc.IncompleteApiImplementationTest.runCaptureOut;
import static org.eclipse.n4js.runner.SystemLoaderInfo.COMMON_JS;

import java.io.IOException;
import java.util.Map;

import org.eclipse.n4js.hlc.base.N4jscBase.BuildType;
import org.eclipse.n4js.hlc.base.N4jscBase.ExitCodeException;
import org.eclipse.n4js.hlc.helper.N4CliHelper;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs 'express'.
 */
public class InstallCompileRunN4jscExternalWithSingleProjectCompileTest extends BaseN4jscExternalTest {

	@Override
	protected Map<String, String> getNpmDependencies() {
		return singletonMap("express", "@4.13.4");
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testCompileAndRunWithExternalDependencies() throws IOException, ExitCodeException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_singleProjectOrFileCompile");
		final String wsRoot = TARGET + "/" + WSP;
		final String projectToCompile = wsRoot + "/external.project";
		final String fileToRun = projectToCompile + "/src/Main.n4js";

		final String[] args = {
				"--systemLoader", COMMON_JS.getId(),
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"-rw", "nodejs",
				"-r", fileToRun,
				"--debug",
				"--verbose",
				"-t", BuildType.projects.toString(),
				projectToCompile
		};
		final String out = runCaptureOut(args);
		N4CliHelper.assertExpectedOutput(
				"Application was created!", out);
	}

}
