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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.N4jscBase.BuildType;
import org.eclipse.n4js.hlc.base.N4jscBase.ExitCodeException;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs 'express' for different target platform configurations.
 */
public class TargetPlatformConfigurationsTest extends BaseN4jscExternalTest {

	@Override
	protected Map<String, String> getNpmDependencies() {
		return singletonMap("express", "@4.13.4");
	}

	// ===== normal failures

	/**
	 * Test failure when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoTargetPlatformFile() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + N4jscBase.EXITCODE_CONFIGURATION_ERROR);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_CONFIGURATION_ERROR, e.getExitCode());
		}
	}

	/**
	 * Test failure when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoInstallLocation() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + N4jscBase.EXITCODE_CONFIGURATION_ERROR);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_CONFIGURATION_ERROR, e.getExitCode());
		}
	}

	// test install location management

	/**
	 * Test creating install location.
	 */
	@Test
	public void testCompileCreatesInstallLocation() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		// force creating install location
		FileDeleter.delete(getTargetPlatformInstallLocation());

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(args);
		} catch (final ExitCodeException e) {
			assertTrue("install location was not created", getTargetPlatformInstallLocation().exists());
			assertEquals(N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}
	}

	/**
	 * Test cleaning install location.
	 */
	@Test
	public void testCompileCleanInstallLocation() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		// force creating install location
		File testFile = new File(getTargetPlatformInstallLocation(), "tst.txt");
		testFile.createNewFile();
		assertTrue("setup error, test file should exist yet at " + testFile.getAbsolutePath(), testFile.exists());

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(args);
		} catch (final ExitCodeException e) {
			assertFalse("install location was not cleaned, test file exists at " + testFile.getAbsolutePath(),
					testFile.exists());
			assertEquals(N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}
	}

	// ===== skip installation

	/**
	 * Test skip install when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoTargetPlatformFileWithSkipped() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + N4jscBase.EXITCODE_CONFIGURATION_ERROR);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
		}
	}

	/**
	 * Test skip install when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoInstallLocationWithSkipped() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + N4jscBase.EXITCODE_CONFIGURATION_ERROR);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
		}
	}

	/**
	 * Test skip install forced
	 */
	@Test
	public void testCompileForceSkippInstall() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(args);
			fail("Expecting exit code: " + N4jscBase.EXITCODE_CONFIGURATION_ERROR);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
		}
	}

	/**
	 * Test skip install combined with forced
	 */
	@Test
	public void testCompileSkippInstallAndForceSkipInstall() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		try {
			new N4jscBase().doMain(args);
		} catch (final ExitCodeException e) {
			assertEquals(N4jscBase.EXITCODE_COMPILE_ERROR, e.getExitCode());
		}
	}

	// combined compiler invocations
	/**
	 * Test compiling with external libraries installation, then invoke compilation without installing. We expect second
	 * invocation to re-use installed external libraries in first invocation.
	 */
	@Test
	public void testCompileWithInstallPlusCompileSkipInstall() throws IOException {
		System.out.println(name.getMethodName());
		setupWorkspace("external_with_n4jsd_tpt");
		final String wsRoot = TARGET + "/" + WSP;

		final String[] argsInstall = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(argsInstall);
		} catch (final ExitCodeException e) {
			assertTrue("install location was not created", getTargetPlatformInstallLocation().exists());
			assertEquals(N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}

		final String[] argsSkipInstall = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--targetPlatformSkipInstall",
				"--debug",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		try {
			new N4jscBase().doMain(argsSkipInstall);
		} catch (final ExitCodeException e) {
			assertTrue("install location still exists", getTargetPlatformInstallLocation().exists());
			assertEquals(N4jscBase.EXITCODE_SUCCESS, e.getExitCode());
		}

	}
}
