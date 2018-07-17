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

import java.util.Set;

import javax.inject.Provider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RuntimeEnvironmentsHelper;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.wizard.dependencies.InstallOptions;
import org.eclipse.n4js.ui.wizard.dependencies.RunnableInstallDependencies;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Installs {@code mocha} and {@code eslint} as project dependency, which will introduce a dependency cycle in the
 * dependency tree of the test project ({@code eslint} and {@code mocha} depend on each other via
 * {@code devDependencies}).
 *
 * This test asserts that despite this dependency cycle, the runner will correctly compute the set of compatible runtime
 * environment for the test project (for now this is the node.js runner only).
 */
public class GH_986_IntroduceExternalCircularDependencyAndRunTest extends AbstractBuilderParticipantTest {

	// the id of the test project
	private static final String CLIENT = "Client";
	// the module to be executed
	private static final String MODULE_TO_RUN = "A";

	@Inject
	private Provider<RunnableInstallDependencies> installDependenciesRunnable;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private RuntimeEnvironmentsHelper runtimeEnvironmentsHelper;

	@Inject
	private IN4JSCore n4jsCore;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		shippedCodeInitializeTestHelper.setupBuiltIns();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		// clear library manager to avoid confusing tests being executed after this test class
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of deleting NPM packages from library manager.");
		externals.maintenanceDeleteNpms(multistatus);

		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		super.tearDown();
	}

	/**
	 * See JavaDoc of class.
	 */
	@Test
	public void testRunWithCycleInDependencyTree() throws CoreException {
		final IProject project = createJSProject(CLIENT, "src", "src-gen",
				b -> b
						// test project of type 'library'
						.withType(ProjectType.LIBRARY)
						.withDependency("mocha", "*")
						.withDependency("eslint", "*"));

		configureProjectWithXtext(project);

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		// create hello world file
		createTestFile(project.getFolder("src"), MODULE_TO_RUN, "console.log(\"Hello World\");");

		// run installMissingDependencies
		RunnableInstallDependencies runnable = installDependenciesRunnable.get();
		runnable.setInstallOptions(new InstallOptions());
		runnable.run(new NullProgressMonitor());

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		final URI projectUri = URI.createPlatformResourceURI(CLIENT + "/package.json", true);

		final Optional<? extends IN4JSProject> projectOptional = n4jsCore
				.findProject(projectUri);

		Assert.assertTrue(
				"The IN4JSProject instance of the test project can be obtained via project URI " + projectUri.toString()
						+ ".",
				projectOptional.isPresent());

		final Set<RuntimeEnvironment> compatibleRuntimeEnvironments = runtimeEnvironmentsHelper
				.findCompatibleRuntimeEnvironments(projectOptional.get());

		if (compatibleRuntimeEnvironments.size() < 1) {
			Assert.fail("Expected the set of compatible runtime environments to be of size >= 1, but was "
					+ compatibleRuntimeEnvironments.size());
		}
	}
}
