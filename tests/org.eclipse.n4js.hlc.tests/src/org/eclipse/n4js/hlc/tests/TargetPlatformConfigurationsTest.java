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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.N4jscBase.BuildType;
import org.eclipse.n4js.hlc.base.SuccessExitStatus;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Downloads, installs, compiles and runs 'express' for different target platform configurations.
 */
public class TargetPlatformConfigurationsTest extends BaseN4jscExternalTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("external_with_n4jsd_tpt");
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	@Override
	protected Map<String, String> getNpmDependencies() {
		return singletonMap("express", "@4.13.4");
	}

	// ===== normal failures

	/**
	 * Test failure when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoTargetPlatformFile() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_CONFIGURATION_ERROR);
	}

	/**
	 * Test failure when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoInstallLocation() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_CONFIGURATION_ERROR);
	}

	// test install location management

	/**
	 * Test creating install location.
	 *
	 * @throws ExitCodeException
	 *             propagated from compiler in case of issues
	 */
	@Test
	public void testCompileCreatesInstallLocation() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		// force creating install location
		FileDeleter.delete(getTargetPlatformInstallLocation());

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, status.code);
		assertTrue("install location was not created", getTargetPlatformInstallLocation().exists());
	}

	/**
	 * Test cleaning install location.
	 *
	 * @throws ExitCodeException
	 *             propagated from compiler in case of issues
	 */
	@Test
	public void testCompileCleanInstallLocation() throws IOException, ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		// force creating install location
		File testFile = new File(getTargetPlatformInstallLocation(), "tst.txt");
		testFile.createNewFile();
		assertTrue("setup error, test file should exist yet at " + testFile.getAbsolutePath(), testFile.exists());

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, status.code);
		assertFalse("install location was not cleaned, test file exists at " + testFile.getAbsolutePath(),
				testFile.exists());
	}

	// ===== skip installation

	/**
	 * Test skip install when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoTargetPlatformFileWithSkipped() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	/**
	 * Test skip install when compiling without target platform file.
	 */
	@Test
	public void testCompileFailsIfNoInstallLocationWithSkipped() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	/**
	 * Test skip install forced
	 */
	@Test
	public void testCompileForceSkippInstall() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	/**
	 * Test skip install combined with forced
	 */
	@Test
	public void testCompileSkippInstallAndForceSkipInstall() {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				// "--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				// "--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString(),
				"--targetPlatformSkipInstall"
		};
		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	// combined compiler invocations
	/**
	 * Test compiling with external libraries installation, then invoke compilation without installing. We expect second
	 * invocation to re-use installed external libraries in first invocation.
	 *
	 * @throws ExitCodeException
	 *             propagated from compiler in case of issues
	 */
	@Test
	public void testCompileWithInstallPlusCompileSkipInstall() throws ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] argsInstall = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		new N4jscBase().doMain(argsInstall);
		SuccessExitStatus statusInstall = new N4jscBase().doMain(argsInstall);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, statusInstall.code);
		assertTrue("install location was not created", getTargetPlatformInstallLocation().exists());

		final String[] argsSkipInstall = {
				"--targetPlatformFile", getTargetPlatformFile().getAbsolutePath(),
				"--targetPlatformInstallLocation", getTargetPlatformInstallLocation().getAbsolutePath(),
				"--targetPlatformSkipInstall",
				"--verbose",
				"--projectlocations", wsRoot,
				"-t", BuildType.allprojects.toString()
		};
		SuccessExitStatus statusSkipInstall = new N4jscBase().doMain(argsSkipInstall);
		assertEquals("Should exit with success", SuccessExitStatus.INSTANCE.code, statusSkipInstall.code);
		assertTrue("install location still exists", getTargetPlatformInstallLocation().exists());

	}
}
