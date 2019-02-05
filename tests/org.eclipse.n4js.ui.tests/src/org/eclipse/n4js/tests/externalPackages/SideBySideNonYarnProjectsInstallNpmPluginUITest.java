/**
 * Copyright (c) 2019 NumberFour AG.
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
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

/**
 *
 * 1) P1, P2 im Eclipse workspace; keine dependency zw P1, P2; P1 -> lodash; P2 -> immutable a) full build -> errors in
 * both projects b) run "npm install" in P1 -> errors in P1 gone c) run "npm install" in P2 -> all errors gone
 */
public class SideBySideNonYarnProjectsInstallNpmPluginUITest extends AbstractBuilderParticipantTest {

	// the id of the runner to launch
	private static final String NODE_RUNNER_ID = "org.eclipse.n4js.runner.nodejs.NODEJS";

	private static final String PROBANDS = "probands";
	private static final String DIFFERENT_NPM_SUBFOLDER = "InstallDifferentNpms";
	private static final String SAME_NPM_SUBFOLDER = "InstallSameNpm";
	private static final String DIFFERENT_NPMS_DEPENDENT_PROJECTS_SUBFOLDER = "InstallDifferentNpmsInDependentProjects";

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private ProjectDescriptionLoader prjDescLoader;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		setupShippedLibraries();
	}

	/**
	 * Install different npm packages in two independent projects.
	 *
	 * <pre>
	 * 1) P1, P2 im Eclipse workspace; keine dependency zw P1, P2; P1 -> lodash; P2 -> immutable
	 * 		a) full build -> errors in both projects
	 * 		b) run "npm install" in P1 -> errors in P1 gone
	 * 		c) run "npm install" in P2 -> all errors gone
	 * </pre>
	 */
	@Test
	public void installDifferentNpmInTwoIndependentProjects() throws CoreException {

		File prjDir = new File(getResourceUri(PROBANDS, DIFFERENT_NPM_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1, "line 15: Project does not exist with project ID: lodash.");
		assertIssues(pkgJsonP2, "line 15: Project does not exist with project ID: immutable.");

		org.eclipse.emf.common.util.URI prjP1URI = org.eclipse.emf.common.util.URI
				.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();
		assertIssues(pkgJsonP1); // No error in P1 anymore
		assertIssues(pkgJsonP2, "line 15: Project does not exist with project ID: immutable.");

		org.eclipse.emf.common.util.URI prjP2URI = org.eclipse.emf.common.util.URI
				.createFileURI(prjP2.getLocation().toString());
		String immutableVersion = getDependencyVersion(prjP2URI, "immutable");
		libraryManager.installNPM("immutable", immutableVersion, prjP2URI, new NullProgressMonitor());
		waitForAutoBuild();
		assertIssues(pkgJsonP1); // No errors in P1 anymore
		assertIssues(pkgJsonP2); // No errors in P2 anymore
	}

	/**
	 * Install the same npm package in two independent projects.
	 *
	 * <pre>
	 * 2) P1, P2 im Eclipse workspace; keine dependency zw P1, P2; P1 -> lodash; P2 -> lodash
	 * 	  a) full build -> errors in both projects
	 *	  b) run "npm install" in P1 -> all errors gone (TODO: Why?? Reconsider this!)
	 * </pre>
	 */
	@Test
	public void installSamepNpmInTwoIndependentProjects() throws CoreException {

		File prjDir = new File(getResourceUri(PROBANDS, SAME_NPM_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1, "line 15: Project does not exist with project ID: lodash.");
		assertIssues(pkgJsonP2, "line 15: Project does not exist with project ID: lodash.");

		org.eclipse.emf.common.util.URI prjP1URI = org.eclipse.emf.common.util.URI
				.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();
		assertIssues(pkgJsonP1); // No errors in P1 anymore
		assertIssues(pkgJsonP2); // No errors in P2 anymore
	}

	/**
	 * Install different npm packages. There is dependency between projects.
	 *
	 * <pre>
	 * 3) P1, P2 im Eclipse workspace; P1 -> P2; P1 -> lodash; P2 -> immutable like 1) above
	 * 	  but also test executing P1 with runner (reference to P2 must work at runtime)
	 * </pre>
	 */
	@Test
	public void installDifferentNpmsInTwoDependentProjects() throws CoreException, IOException {
		System.out.println("start");

		File prjDir = new File(getResourceUri(PROBANDS, DIFFERENT_NPMS_DEPENDENT_PROJECTS_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1, "line 15: Project does not exist with project ID: lodash.");
		assertIssues(pkgJsonP2, "line 15: Project does not exist with project ID: immutable.");

		org.eclipse.emf.common.util.URI prjP1URI = org.eclipse.emf.common.util.URI
				.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();
		assertIssues(pkgJsonP1); // No error in P1 anymore
		assertIssues(pkgJsonP2, "line 15: Project does not exist with project ID: immutable.");

		org.eclipse.emf.common.util.URI prjP2URI = org.eclipse.emf.common.util.URI
				.createFileURI(prjP2.getLocation().toString());
		String immutableVersion = getDependencyVersion(prjP2URI, "immutable");
		libraryManager.installNPM("immutable", immutableVersion, prjP2URI, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertIssues(pkgJsonP2); // No errors in P2 anymore
		assertIssues(pkgJsonP1); // No errors in P1 anymore

		// the uri of the module to be executed
		final URI moduleToRunURI = URI.createPlatformResourceURI("P1/src/A.n4js", true);
		final RunConfiguration config = runnerFrontEnd.createConfiguration(NODE_RUNNER_ID, moduleToRunURI);
		runnerFrontEnd.computeDerivedValues(config);

		final Process process = runnerFrontEnd.run(config, createTestExecutor());
		final String output = captureOutput(process);

		Assert.assertEquals("The process output matches the expectation.",
				"stdout:\n" + "Hello n4js!\n" + "stderr:", output);
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

	private String getDependencyVersion(org.eclipse.emf.common.util.URI prjURI, String dependencyName) {
		ProjectDescription prjDesc1 = prjDescLoader.loadProjectDescriptionAtLocation(prjURI);
		Optional<ProjectDependency> depPrj = prjDesc1.getProjectDependencies().stream()
				.filter(prjDep -> prjDep.getProjectName().equals(dependencyName))
				.findFirst();
		if (depPrj.isPresent()) {
			return depPrj.get().getVersionRequirementString();
		} else {
			return null;
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		tearDownShippedLibraries();
		super.tearDown();

	}
}
