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

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RuntimeEnvironmentsHelper;
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.junit.Assert;
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
public class GH_986_ExternalCircularDependencyAndRuntimeLibrariesPluginUITest extends AbstractBuilderParticipantTest {

	// the probands folder
	private static final String PROBANDS = "probands";

	// the subfolder in the probands folder
	private static final String PROBANDS_SUBFOLDER = "GH_986";

	// the external libraries location in the probands folder
	private static final String EXT_LOC = "external-libraries";

	// the workspace projects location in the probands folder
	private static final String WS_LOC = "workspace";

	private static final N4JSProjectName CLIENT_DEPS_PROJECT_NAME = new N4JSProjectName("client-dependencies");
	private static final N4JSProjectName CLIENT_DEV_DEPS_PROJECT_NAME = new N4JSProjectName("client-devDependencies");

	private static final N4JSProjectName RUNTIME_ENVIRONMENT_NAME = new N4JSProjectName("n4js-es5");

	@Inject
	private RuntimeEnvironmentsHelper runtimeEnvironmentsHelper;

	@Inject
	private IN4JSCore n4jsCore;

	/** Compute compatible runtime environment of project that references a dependency cycle (via dependencies) */
	@Test
	public void testRunWithCycleInDependencyGraph() throws CoreException, IOException {
		// import project to workspace
		final File projectsRoot = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, WS_LOC));
		final IProject testProject = ProjectTestsUtils.importProject(projectsRoot, CLIENT_DEPS_PROJECT_NAME);

		ProjectTestsUtils.importProject(projectsRoot, RUNTIME_ENVIRONMENT_NAME);

		java.net.URI externalRootLocation = getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, EXT_LOC);
		ProjectTestsUtils.importDependencies(CLIENT_DEPS_PROJECT_NAME, externalRootLocation, libraryManager);

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

	/** Compute compatible runtime environment of project that references a dependency cycle (via devDependencies) */
	@Test
	public void testRunWithCycleInDevDependencyGraph() throws CoreException, IOException {
		// import project to workspace
		final File projectsRoot = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, WS_LOC));
		final IProject testProject = ProjectTestsUtils.importProject(projectsRoot, CLIENT_DEV_DEPS_PROJECT_NAME);

		ProjectTestsUtils.importProject(projectsRoot, RUNTIME_ENVIRONMENT_NAME);

		java.net.URI externalRootLocation = getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, EXT_LOC);
		ProjectTestsUtils.importDependencies(CLIENT_DEV_DEPS_PROJECT_NAME, externalRootLocation, libraryManager);

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
