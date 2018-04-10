/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4jsx.ui.tests;

import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;
import static org.eclipse.n4js.runner.nodejs.NodeRunner.ID;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the React external library.
 */
public class TestReactExternalLibraryPluginTest extends AbstractBuilderParticipantTest {

	private static final String PACKAGE_REACT = "react";
	private static final String PROBANDS = "probands";

	private static final String WORKSPACE_LOC = "workspace";
	private static final String PA = "A";
	private static final String PB = "B";
	private static final String SRC_FOLDER = "src";

	private static final String CLIENT_MODULE = "src/A.n4jsx";

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private ProcessExecutor processExecutor;

	@Inject
	private LibraryManager npmManager;

	/**
	 * Checks whether the platform is running or not.
	 */
	@BeforeClass
	public static void checkTestMode() {
		assertTrue("Expected running platform. Run the tests as JUnit Plug-in Tests.", Platform.isRunning());
	}

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	@Before
	public void setup() throws Exception {
		setupExternalLibraries(false, true);
	}

	/**
	 * Imports {@value #PA} project, checks for validation errors, installs {@code React npm} package, checks that
	 * errors are gone. Then verify that running React/JSX code is successful.
	 */
	// @Test
	public void testInstallReactNpmThenRun() throws Exception {
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PA);
		waitForAutoBuild();

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PA);
		assertTrue(PA + " project is not accessible.", project.isAccessible());
		final IFile clientModule = project
				.getFile(getResourceName(SRC_FOLDER, PA + "." + N4JSGlobals.N4JSX_FILE_EXTENSION));
		assertTrue(clientModule + " client module is not accessible.", clientModule.isAccessible());

		final IFile manifest = project.getFile(getResourceName(N4MF_MANIFEST));
		assertTrue(manifest + " client module is not accessible.", manifest.isAccessible());

		assertMarkers("Expected exactly 5 errors in client module.", clientModule, 5);
		assertMarkers("Expected exactly one error in manifest.", manifest, 1);

		npmManager.installNPM(PACKAGE_REACT, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("Expected exactly zero errors in manifest.", manifest, 0);
		assertMarkers("Expected exactly no errors in client module.", clientModule, 0);

		final ProcessResult result = runClient();
		assertTrue("Unexpected output after running the client module: " + result.getStdOut(),
				result.getStdOut().contains("Symbol(react.element)"));
	}

	/**
	 * Test that the alias for react must be React.
	 */
	@Test
	public void testReactAlias() throws Exception {
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PB);
		waitForAutoBuild();

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PB);
		assertTrue(PB + " project is not accessible.", project.isAccessible());
		final IFile clientModule = project
				.getFile(getResourceName(SRC_FOLDER, PB + "." + N4JSGlobals.N4JSX_FILE_EXTENSION));
		assertTrue(clientModule + " client module B is not accessible.", clientModule.isAccessible());

		final IFile manifest = project.getFile(getResourceName(N4MF_MANIFEST));
		assertTrue(manifest + " B module is not accessible.", manifest.isAccessible());

		npmManager.installNPM(PACKAGE_REACT, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("Expected exactly zero errors in manifest.", manifest, 0);
		assertMarkers("Expected exactly 1 error in B module.", clientModule, 1);
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index, cleanup file system leftovers.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		tearDownExternalLibraries(false);
	}

	private ProcessResult runClient() {
		final String pathToModuleToRun = getResourceName(PA, CLIENT_MODULE);
		final org.eclipse.emf.common.util.URI moduleToRun = createPlatformResourceURI(pathToModuleToRun, true);
		final RunConfiguration config = runnerFrontEnd.createConfiguration(ID, null, moduleToRun);
		final Process process = runnerFrontEndUI.runInUI(config);
		final ProcessResult result = processExecutor.execute(process, "", OutputRedirection.REDIRECT);
		assertTrue("Expected 0 error code for the process. Was: " + result.getExitCode() + "\nError message: "
				+ result.getStdErr(), result.isOK());
		return result;
	}
}
