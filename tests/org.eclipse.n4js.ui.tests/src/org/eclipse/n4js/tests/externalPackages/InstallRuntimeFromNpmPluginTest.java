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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Provider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.wizard.dependencies.InstallOptions;
import org.eclipse.n4js.ui.wizard.dependencies.RunnableInstallDependencies;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * A simple plugin test that installs a specific version of shipped code projects (such as n4js-runtime-node) via npm.
 *
 * Asserts that a module can be executed using a runtime environment that was installed from npm.
 */
public class InstallRuntimeFromNpmPluginTest extends AbstractBuilderParticipantTest {

	// the id of the runner to launch
	private static final String NODE_RUNNER_ID = "org.eclipse.n4js.runner.nodejs.NODEJS";

	// the id of the test project
	private static final String CLIENT = "Client";
	// the module to be executed
	private static final String MODULE_TO_RUN = "A";
	// the uri of the module to be executed
	private static final URI MODULE_TO_RUN_URI = URI.createPlatformResourceURI(
			CLIENT + "/" + "src" + "/" + MODULE_TO_RUN + ".n4js", true);

	@Inject
	private Provider<RunnableInstallDependencies> installDependenciesRunnable;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

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
		externals.maintenanceDeleteNpms();

		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		super.tearDown();
	}

	/**
	 * See JavaDoc of class.
	 */
	@Test
	public void testInstallRuntimeFromNpmTest() throws CoreException, IOException {
		final IProject project = createJSProject(CLIENT, "src", "src-gen",
				b -> b
						// test project of type 'library'
						.withType(ProjectType.LIBRARY)
						// add dependency to node runtime environment in specific version
						.withDependency("n4js-runtime-node", "0.1.0"));

		configureProjectWithXtext(project);

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();
		assertNoIssues();

		// create hello world file
		createTestFile(project.getFolder("src"), MODULE_TO_RUN, "console.log(\"Hello World\");");

		// run installMissingDependencies
		RunnableInstallDependencies runnable = installDependenciesRunnable.get();
		runnable.setInstallOptions(new InstallOptions());
		runnable.run(new NullProgressMonitor());

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		// create module run configuration
		final RunConfiguration config = runnerFrontEnd.createConfiguration(NODE_RUNNER_ID,
				MODULE_TO_RUN_URI);

		// compute derived value (including execModule)
		runnerFrontEnd.computeDerivedValues(config);

		// execute node with the test module and assert output of execution
		final Process process = runnerFrontEnd.run(config, createTestExecutor());
		final String output = captureOutput(process);

		Assert.assertEquals("The process output matches the expectation.",
				"stdout:\n" + "Hello World\n" + "stderr:", output);
	}

	/**
	 * Returns a custom {@link IExecutor} that does not inherit the IO of the current process but rather allows to
	 * capture the output of the executed command via {@link Process#getInputStream()}.
	 */
	private IExecutor createTestExecutor() {
		return new IExecutor() {
			@Override
			public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> envp)
					throws ExecutionException {

				ProcessBuilder pb = new ProcessBuilder(cmdLine);
				pb.environment().putAll(envp);
				pb.directory(workingDirectory);

				try {
					return pb.start();
				} catch (IOException e) {
					throw new ExecutionException(e);
				}
			}
		};
	}

	/**
	 * Captures the output of the given {@code process} and returns it as a string.
	 *
	 * Returns a concatenated version of stdout and stderr output.
	 */
	private static String captureOutput(Process process) throws IOException {
		List<String> out = new ArrayList<>();
		String line;

		out.add("stdout:");

		try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			while ((line = outputReader.readLine()) != null) {
				out.add(line);
			}
		}

		out.add("stderr:");

		try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			while ((line = errorReader.readLine()) != null) {
				out.add(line);
			}
		}
		return out.stream().collect(Collectors.joining("\n"));
	}
}
