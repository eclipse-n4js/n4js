/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Ensure we are using the correct version of node during the tests.
 */
public class NodeVersionTest {

	
	@Test
	public void testNodeVersion() {
		Path cwd = new File("").toPath().toAbsolutePath();
		ProcessResult result = new CliTools().nodejsRun(cwd, Path.of("-v"));
		Assert.assertEquals("bad exit code", 0, result.getExitCode());
		String versionStr = result.getStdOut().trim();
		if (!N4JSGlobals.isCompatibleNodeVersion(versionStr)) {
			Assert.fail("tests are running with node version " + versionStr
					+ " but this version is not compatible to version v" + N4JSGlobals.NODE_VERSION + " defined in "
					+ N4JSGlobals.class.getSimpleName());
		}
	}
}
