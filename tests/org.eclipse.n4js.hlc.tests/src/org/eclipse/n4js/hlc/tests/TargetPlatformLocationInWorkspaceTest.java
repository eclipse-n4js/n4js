/**
 * Copyright (c) 2018 NumberFour AG.
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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * This test instructs N4JSC to put the target platform install location in the same directory that also contains the
 * projects to build.
 *
 * The primary thing this test is checking for, is that the npms in the node_modules folder are not recognized as
 * projects. Instead, the contained projects should be discovered and registered.
 */
public class TargetPlatformLocationInWorkspaceTest extends AbstractN4jscTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("tp-location-workspace", Predicates.alwaysTrue());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * See class doc for test description.
	 */
	@Test
	public void testTargetPlaformInstallLocationInWorkspace() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				"--projectlocations", wsRoot,
				"--buildType", BuildType.allprojects.toString(),
				"--targetPlatformInstallLocation", wsRoot + "/tp",
				"--systemLoader", COMMON_JS.getId(),
				"--installMissingDependencies"
		};

		try {
			// run n4jsc with the given arguments
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			// make sure the compiler invocation is successful
			e.printStackTrace();
			fail("Could not successfully execute N4JSC with target platform location projects directory. Check the console for a stacktrace.");
		}

	}
}
