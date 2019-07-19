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
import static org.eclipse.n4js.N4JSGlobals.N4JS_RUNTIME;
import static org.eclipse.n4js.runner.nodejs.NodeRunner.ID;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the React external library.
 */
public class TestReactExternalLibraryPluginTest extends AbstractBuilderParticipantTest {

	private static final N4JSProjectName PACKAGE_REACT = new N4JSProjectName("react");
	private static final N4JSProjectName PACKAGE_N4JSD_REACT = new N4JSProjectName("@n4jsd/react");
	private static final String PROBANDS = "probands";

	private static final String WORKSPACE_LOC = "workspace";
	private static final N4JSProjectName PA = new N4JSProjectName("A");
	private static final N4JSProjectName PB = new N4JSProjectName("B");
	private static final String SRC_FOLDER = "src";

	private static final String CLIENT_MODULE = "src/A.n4jsx";

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private ProcessExecutor processExecutor;

	@Inject
	private LibraryManager libManager;

	/**
	 * Checks whether the platform is running or not.
	 */
	@BeforeClass
	public static void checkTestMode() {
		assertTrue("Expected running platform. Run the tests as JUnit Plug-in Tests.", Platform.isRunning());
	}

	/**
	 * Imports {@value #PA} project, checks for validation errors, installs {@code React npm} package, checks that
	 * errors are gone. Then verify that running React/JSX code is successful.
	 */
	@Test
	public void testInstallReactNpmThenRun() throws Exception {
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PA);
		waitForAutoBuild();

		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PA.toEclipseProjectName().getRawName());
		assertTrue(PA + " project is not accessible.", project.isAccessible());
		final IFile clientModule = project
				.getFile(getResourceName(SRC_FOLDER, PA + "." + N4JSGlobals.N4JSX_FILE_EXTENSION));
		assertTrue(clientModule + " client module is not accessible.", clientModule.isAccessible());

		final IFile projectDescriptionFile = project.getFile(getResourceName(IN4JSProject.PACKAGE_JSON));
		assertTrue(projectDescriptionFile + " client module is not accessible.", projectDescriptionFile.isAccessible());

		assertIssues(clientModule,
				"line 12: Cannot resolve import target :: resolving simple module import : found no matching modules",
				"line 14: Cannot resolve JSX implementation.",
				"line 15: Couldn't resolve reference to IdentifiableElement 'Component'.");
		assertIssues(projectDescriptionFile,
				"line 5: Project does not exist with project ID: n4js-runtime.",
				"line 6: Project does not exist with project ID: react.",
				"line 7: Project does not exist with project ID: @n4jsd/react.");

		libManager.installNPM(N4JS_RUNTIME, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		libManager.installNPM(PACKAGE_REACT, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		libManager.installNPM(PACKAGE_N4JSD_REACT, new PlatformResourceURI(project).toFileURI(),
				new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("Expected exactly zero errors in package.json.", projectDescriptionFile, 0);
		assertMarkers("Expected exactly no errors in client module.", clientModule, 0);

		final ProcessResult result = runClient();
		assertTrue("Unexpected output after running the client module: " + result.getStdOut(),
				result.getStdOut().contains("Symbol(react.element)"));

		libManager.uninstallNPM(PACKAGE_N4JSD_REACT, new NullProgressMonitor());
		libManager.uninstallNPM(PACKAGE_REACT, new NullProgressMonitor());
		libManager.uninstallNPM(N4JS_RUNTIME, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();
	}

	/**
	 * Test that the alias for react must be React.
	 */
	@Test
	public void testReactAlias() throws Exception {
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PB);
		waitForAutoBuild();

		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PB.toEclipseProjectName().getRawName());
		assertTrue(PB + " project is not accessible.", project.isAccessible());
		final IFile clientModule = project
				.getFile(getResourceName(SRC_FOLDER, PB + "." + N4JSGlobals.N4JSX_FILE_EXTENSION));
		assertTrue(clientModule + " client module B is not accessible.", clientModule.isAccessible());

		final IFile projectDescriptionFile = project.getFile(getResourceName(IN4JSProject.PACKAGE_JSON));
		assertTrue(projectDescriptionFile + " B module is not accessible.", projectDescriptionFile.isAccessible());

		libManager.installNPM(N4JS_RUNTIME, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		libManager.installNPM(PACKAGE_REACT, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		libManager.installNPM(PACKAGE_N4JSD_REACT, new PlatformResourceURI(project).toFileURI(),
				new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("Expected exactly zero errors in package.json.", projectDescriptionFile, 0);
		assertMarkers("Expected exactly 1 error in B module.", clientModule, 1);

		libManager.uninstallNPM(PACKAGE_N4JSD_REACT, new NullProgressMonitor());
		libManager.uninstallNPM(PACKAGE_REACT, new NullProgressMonitor());
		libManager.uninstallNPM(N4JS_RUNTIME, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();
	}

	private ProcessResult runClient() {
		final String pathToModuleToRun = getResourceName(PA.getRawName(), CLIENT_MODULE);
		final org.eclipse.emf.common.util.URI moduleToRun = createPlatformResourceURI(pathToModuleToRun, true);
		final RunConfiguration config = runnerFrontEnd.createConfiguration(ID, null, moduleToRun);
		final Process process;
		try {
			process = runnerFrontEndUI.runInUI(config);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception after invoking #runInUI().", e);
		}
		final ProcessResult result = processExecutor.execute(process, "", OutputRedirection.REDIRECT);
		assertTrue("Expected 0 error code for the process. Was: " + result.getExitCode() + "\nError message: "
				+ result.getStdErr(), result.isOK());
		return result;
	}
}
