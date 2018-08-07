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
package org.eclipse.n4js.tests.externalPackages;

import static org.eclipse.n4js.tests.builder.BuilderUtil.countResourcesInIndex;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getAllResourceDescriptionsAsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RuntimeEnvironmentsHelper;
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.utils.StatusHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Tests that despite dependency cycles in the dependency graph of a workspace project, the set of required runtime
 * libraries can be computed successfully.
 *
 * More specifically, this means that some projects in the external library workspace declare a dependency cycle
 * (directly via "dependencies" or indirectly via "devDependencies").
 */
public class GH_986_ExternalCircularDependencyAndRuntimeLibrariesPluginTest extends AbstractBuilderParticipantTest {

	// the probands folder
	private static final String PROBANDS = "probands";

	// the subfolder in the probands folder
	private static final String PROBANDS_SUBFOLDER = "GH_986";

	// the external libraries location in the probands folder
	private static final String EXT_LOC = "external-libraries";

	// the workspace projects location in the probands folder
	private static final String WS_LOC = "workspace";

	private static final String CLIENT_DEPS_PROJECT_NAME = "client-dependencies";
	private static final String CLIENT_DEV_DEPS_PROJECT_NAME = "client-devDependencies";

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private RuntimeEnvironmentsHelper runtimeEnvironmentsHelper;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private IN4JSCore n4jsCore;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		assertEquals("Resources in index:\n" + getAllResourceDescriptionsAsString() + "\n", 0, countResourcesInIndex());

		shippedCodeInitializeTestHelper.setupBuiltIns();

		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertTrue("Expected empty workspace. Projects were in workspace: " + Arrays.toString(projects),
				0 == projects.length);
		final java.net.URI externalRootLocation = getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, EXT_LOC);
		externalLibraryPreferenceStore.add(externalRootLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
		waitForAutoBuild();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		// remove test-specific external libraries location
		final java.net.URI externalRootLocation = getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, EXT_LOC);
		externalLibraryPreferenceStore.remove(externalRootLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
		waitForAutoBuild();

		// clear library manager to avoid confusing tests being executed after this test class
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of deleting NPM packages from library manager.");
		externals.maintenanceDeleteNpms(multistatus);

		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		super.tearDown();
	}

	/**
	 * Compute compatible runtime environment of project that references a dependency cycle (via dependencies)
	 */
	@Test
	public void testRunWithCycleInDependencyGraph() throws CoreException {
		// import project to workspace
		final File projectsRoot = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, WS_LOC));
		final IProject testProject = ProjectTestsUtils.importProject(projectsRoot, CLIENT_DEPS_PROJECT_NAME);
		waitForAutoBuild();

		// obtain IN4JSProject representation of test project
		final URI projectUri = URI.createPlatformResourceURI(CLIENT_DEPS_PROJECT_NAME + "/package.json", true);
		final Optional<? extends IN4JSProject> projectOptional = n4jsCore.findProject(projectUri);

		// make assertions
		Assert.assertTrue(
				"The IN4JSProject instance of the test project can be obtained via project URI " + projectUri.toString()
						+ ".",
				projectOptional.isPresent());

		try {
			final Set<RuntimeEnvironment> compatibleRuntimeEnvironments = runtimeEnvironmentsHelper
					.findCompatibleRuntimeEnvironments(projectOptional.get());

			if (compatibleRuntimeEnvironments.size() < 1) {
				Assert.fail("Expected the set of compatible runtime environments to be of size >= 1, but was "
						+ compatibleRuntimeEnvironments.size());
			}
		} catch (DependencyCycleDetectedException e) {
			Assert.fail(
					"Computation of the set of compatible runtime environments should not throw a dependency-cycle-exception.");
		}

		// tear down
		testProject.delete(true, true, new NullProgressMonitor());
	}

	/**
	 * Compute compatible runtime environment of project that references a dependency cycle (via devDependencies)
	 */
	@Test
	public void testRunWithCycleInDevDependencyGraph() throws CoreException {
		// import project to workspace
		final File projectsRoot = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, WS_LOC));
		final IProject testProject = ProjectTestsUtils.importProject(projectsRoot, CLIENT_DEV_DEPS_PROJECT_NAME);
		waitForAutoBuild();

		// obtain IN4JSProject representation of test project
		final URI projectUri = URI.createPlatformResourceURI(CLIENT_DEV_DEPS_PROJECT_NAME + "/package.json", true);
		final Optional<? extends IN4JSProject> projectOptional = n4jsCore.findProject(projectUri);

		// make assertions
		Assert.assertTrue(
				"The IN4JSProject instance of the test project can be obtained via project URI " + projectUri.toString()
						+ ".",
				projectOptional.isPresent());

		try {
			final Set<RuntimeEnvironment> compatibleRuntimeEnvironments = runtimeEnvironmentsHelper
					.findCompatibleRuntimeEnvironments(projectOptional.get());

			if (compatibleRuntimeEnvironments.size() < 1) {
				Assert.fail("Expected the set of compatible runtime environments to be of size >= 1, but was "
						+ compatibleRuntimeEnvironments.size());
			}
		} catch (DependencyCycleDetectedException e) {
			Assert.fail(
					"Computation of the set of compatible runtime environments should not throw a dependency-cycle-exception.");
		}

		// tear down
		testProject.delete(true, true, new NullProgressMonitor());
	}
}
