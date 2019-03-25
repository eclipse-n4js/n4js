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
package org.eclipse.n4js.tests.externalPackages;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.runner.nodejs.NodeRunner.ID;
import static org.eclipse.n4js.tests.builder.BuilderUtil.countResourcesInIndex;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getAllResourceDescriptionsAsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.internal.RaceDetectionHelper;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.repeat.RepeatedTestRule;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the external libraries.
 */
public class RunExternalLibrariesPluginTest extends AbstractBuilderParticipantTest {

	private static final String NL = "\n"; // node is not using system line separator

	private static final String PROBANDS = "probands";

	private static final String EXT_LOC = "IDE_1977_ExternalLibs";
	private static final String WORKSPACE_LOC = "IDE_1977_WorkspaceLibs";

	private static final String PA = "PA";
	private static final String PB = "PB";
	private static final String PC = "PC";
	private static final String PD = "PD";
	private static final String PX = "PX";
	private static final String CLIENT = "Client";

	private static final String CLIENT_MODULE = "src/Client.n4js";

	private static final Collection<String> LIB_PROJECT_IDS = ImmutableSet.<String> builder()
			.add(PA, PB, PC, PD, PX).build();

	private static final Collection<String> ALL_PROJECT_IDS = ImmutableSet.<String> builder()
			.addAll(LIB_PROJECT_IDS).add(CLIENT).build();

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private ProcessExecutor processExecutor;

	/**
	 * Checks whether the platform is running or not.
	 */
	@BeforeClass
	public static void checkTestMode() {
		assertTrue("Expected running platform. Run the tests as JUnit Plug-in Tests.", Platform.isRunning());
	}

	/**
	 * Loads (and indexes) all the required external libraries. Also imports all the workspace projects.
	 */
	@Before
	synchronized public void setupWorkspace() throws Exception {
		RaceDetectionHelper.log(">>> SETUP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		assertEquals("Resources in index:\n" + getAllResourceDescriptionsAsString() + "\n", 0, countResourcesInIndex());

		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertTrue("Expected empty workspace. Projects were in workspace: " + Arrays.toString(projects),
				0 == projects.length);

		for (final String projectName : ALL_PROJECT_IDS) {
			final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
			ProjectTestsUtils.importProject(projectsRoot, projectName);
		}

		URI externalRootLocation = getResourceUri(PROBANDS, EXT_LOC);
		ProjectTestsUtils.importDependencies(CLIENT, externalRootLocation, libraryManager);

		// This test is using an unsupported setup: Projects depend on each other without using a yarn workspace.
		// Hence, the following warnings are expected.
		// The unsupported setup is necessary because this test is testing shadowing which would not appear in a yarn
		// workspace like that.
		assertNoErrors();
		assertIssues(
				"line 5: Project depends on workspace project PA which is missing in the node_modules folder. " +
						"Either install project PA or introduce a yarn workspace of both of the projects.",
				"line 5: Project depends on workspace project PB which is missing in the node_modules folder. " +
						"Either install project PB or introduce a yarn workspace of both of the projects.",
				"line 5: Project depends on workspace project PC which is missing in the node_modules folder. " +
						"Either install project PC or introduce a yarn workspace of both of the projects.");
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index.
	 */
	@After
	synchronized public void tearDown2() throws Exception {
		waitForAutoBuild();
		RaceDetectionHelper.log(">>> TEARDOWN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	/***/
	@Test
	public void runClientWithAllOpenedWorkspaceProjects() throws CoreException {
		waitForAutoBuildCheckIndexRigid();
		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"Workspace A<init>" + NL +
				"Workspace B<init>" + NL +
				"Workspace C<init>" + NL +
				"Workspace D<init>" + NL,
				result.getStdOut());
		// @formatter:on
	}

	/***/
	@Ignore("random")
	@Test
	public void runClientWithAllClosedWorkspaceProjects() throws CoreException {

		for (final String libProjectName : LIB_PROJECT_IDS) {
			getProjectByName(libProjectName).close(new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"External A<init>" + NL +
				"External B<init>" + NL +
				"External C<init>" + NL +
				"External D<init>" + NL,
				result.getStdOut());
		// @formatter:on
	}

	/***/
	@Test
	public void runClientWithTwoClosedWorkspaceProjectsWithTransitiveDependency() throws CoreException {

		for (final String libProjectName : newArrayList(PB, PD)) {
			getProjectByName(libProjectName).close(new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"Workspace A<init>" + NL +
				"External B<init>" + NL +
				"Workspace C<init>" + NL +
				"External D<init>" + NL,
				result.getStdOut());
		// @formatter:on
	}

	/**
	 * Test rule to enable repeat tests.
	 */
	@Rule
	public RepeatedTestRule rule = new RepeatedTestRule();

	/***/
	@Test
	// @RepeatTest(times = 1000)
	public void runClientWithTwoClosedWorkspaceProjectsWithDirectDependency() throws CoreException {
		RaceDetectionHelper.log(">>> START >>>>>>>>>>>>>>>>>>>");

		for (final String libProjectName : newArrayList(PB, PC)) {
			RaceDetectionHelper.log("About to close " + libProjectName);
			getProjectByName(libProjectName).close(new NullProgressMonitor());
			RaceDetectionHelper.log("Did close " + libProjectName);
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"Workspace A<init>" + NL +
				"External B<init>" + NL +
				"External C<init>" + NL +
				"Workspace D<init>" + NL,
				result.getStdOut());
		RaceDetectionHelper.log(">>> END >>>>>>>>>>>>>>>>>>>");
		// @formatter:on
	}

	/***/
	@Test
	public void runClientWithTwoClosedWorkspaceProjectsThenReopenThem() throws CoreException {
		for (final String libProjectName : newArrayList(PB, PD)) {
			getProjectByName(libProjectName).close(new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult firstResult = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module with two closed projects: " + firstResult,
				"Workspace A<init>" + NL +
				"External B<init>" + NL +
				"Workspace C<init>" + NL +
				"External D<init>" + NL,
				firstResult.getStdOut());
		// @formatter:on

		for (final String libProjectName : newArrayList(PB, PD)) {
			getProjectByName(libProjectName).open(new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult secondResult = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module with all opened projects: " + secondResult,
				"Workspace A<init>" + NL +
				"Workspace B<init>" + NL +
				"Workspace C<init>" + NL +
				"Workspace D<init>" + NL,
				secondResult.getStdOut());
		// @formatter:on
	}

	/***/
	@Test
	public void runClientWithAllDeletedWorkspaceProjects() throws CoreException {

		for (final String libProjectName : LIB_PROJECT_IDS) {
			getProjectByName(libProjectName).delete(true, new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"External A<init>" + NL +
				"External B<init>" + NL +
				"External C<init>" + NL +
				"External D<init>" + NL,
				result.getStdOut());
		// @formatter:on
	}

	/***/
	@Ignore("random")
	@Test
	public void runClientWithTwoDeletedWorkspaceProjects() throws CoreException {

		for (final String libProjectName : newArrayList(PB, PD)) {
			getProjectByName(libProjectName).delete(true, new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult result = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module: " + result,
				"Workspace A<init>" + NL +
				"External B<init>" + NL +
				"Workspace C<init>" + NL +
				"External D<init>" + NL,
				result.getStdOut());
		// @formatter:on
	}

	/***/
	@Test
	public void runClientWithTwoDeletedWorkspaceProjectsThenReImportThem() throws Exception {

		for (final String libProjectName : newArrayList(PB, PD)) {
			getProjectByName(libProjectName).delete(true, new NullProgressMonitor());
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult firstResult = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module with two deleted projects: " + firstResult,
				"Workspace A<init>" + NL +
				"External B<init>" + NL +
				"Workspace C<init>" + NL +
				"External D<init>" + NL,
				firstResult.getStdOut());
		// @formatter:on

		for (final String libProjectName : newArrayList(PB, PD)) {
			final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
			ProjectTestsUtils.importProject(projectsRoot, libProjectName);
			waitForAutoBuildCheckIndexRigid();
		}

		syncExtAndBuild();

		final ProcessResult secondResult = runClient();
		// @formatter:off
		assertEquals("Unexpected output after running the client module with all opened projects: " + secondResult,
				"Workspace A<init>" + NL +
				"Workspace B<init>" + NL +
				"Workspace C<init>" + NL +
				"Workspace D<init>" + NL,
				secondResult.getStdOut());
		// @formatter:on
	}

	/**
	 * Besides waiting for auto-build to finish, performs a clean build and checks if the Xtext index content is still
	 * valid. This method can be used to ensure Xtext index content does not get messed up after a clean build either.
	 */
	private void waitForAutoBuildCheckIndexRigid() throws CoreException {
		waitForAutoBuild();
		cleanBuild();
		waitForAutoBuild();
	}

	private ProcessResult runClient() {
		final String pathToModuleToRun = getResourceName(CLIENT, CLIENT_MODULE);
		final org.eclipse.emf.common.util.URI moduleToRun = createPlatformResourceURI(pathToModuleToRun, true);
		final RunConfiguration config = runnerFrontEnd.createConfiguration(ID, null, moduleToRun);
		final Process process = runnerFrontEndUI.runInUI(config);
		final ProcessResult result = processExecutor.execute(process, "", OutputRedirection.REDIRECT);
		if (result.isOK())
			return result;
		throw new RuntimeException("Client exited with error.\n" + result);
	}

	private IProject getProjectByName(final String name) {
		final IProject project = from(asList(getWorkspace().getRoot().getProjects()))
				.firstMatch(p -> name.equals(p.getName())).orNull();
		assertNotNull("Cannot find project with name: " + name, project);
		return project;
	}

	@Override
	public void waitForAutoBuild(boolean assertValidityOfXtextIndex) {
		waitForNotReallyBuildButHousekeepingJobs();
		// simulate auto-build loop by synchronized and fast
		for (int i = 0; i < 10; i++) {
			IResourcesSetupUtil.waitForBuild();
		}
		if (assertValidityOfXtextIndex)
			assertXtextIndexIsValid();
	}

}
