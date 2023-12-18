/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test various cases of changes between LSP server sessions to ensure the initial build of the later session correctly
 * reacts to those changes.
 */

public class InitialBuildTest extends AbstractIncrementalBuilderTest {

	private static Map<String, Map<String, String>> testData = Map.of(
			"ProviderProject", Map.of(
					"SomeModule", """
								export public class SomeClass {
								}
							"""),
			"ClientProject", Map.of(
					"ClientModule", """
								import {SomeClass} from "SomeModule";
								const x: SomeClass = null;
								x;
							""",
					CFG_DEPENDENCIES, """
								ProviderProject
							"""));

	private static Path temporaryFolder;

	@BeforeClass
	public static void createTemporaryFolder() throws IOException {
		temporaryFolder = Files.createTempDirectory("temp_InitialBuildTest_");
	}

	@AfterClass
	public static void deleteTemporaryFolder() throws IOException {
		if (temporaryFolder != null) {
			FileDeleter.delete(temporaryFolder);
		}
	}

	@Before
	public void cleanTemporaryFolder() throws IOException {
		FileUtils.cleanFolder(temporaryFolder);
	}

	@Test
	public void testFixFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(testData);
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClass", "SomeClassX"));
		startAndWaitForLspServer();
		assertIssues2(Pair.of("ClientModule", List.of(
				"(Error, [0:9 - 0:18], Import of SomeClass cannot be resolved.)",
				"(Error, [1:10 - 1:19], Couldn't resolve reference to Type 'SomeClass'.)")));

		shutdownLspServer();

		// test case: fix file between server sessions
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClassX", "SomeClass"));

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));
	}

	@Test
	public void testBreakFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(testData);
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();

		// test case: break file between server sessions
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClass", "SomeClassX"));

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(
				List.of("ClientModule"),
				Pair.of("ClientModule", List.of(
						"(Error, [0:9 - 0:18], Import of SomeClass cannot be resolved.)",
						"(Error, [1:10 - 1:19], Couldn't resolve reference to Type 'SomeClass'.)")));
	}

	@Test
	public void testAddRemoveFileBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		Path providerProjectStateFile = getProjectRoot("ProviderProject").toPath()
				.resolve(N4JSGlobals.N4JS_PROJECT_STATE);
		Path someModule = getFileURIFromModuleName("SomeModule").toFile().toPath();
		Path someModuleHidden = temporaryFolder.resolve(someModule.getFileName().toString());
		FileUtils.move(someModule, someModuleHidden);

		startAndWaitForLspServer();
		@SuppressWarnings("unchecked")
		Pair<String, List<String>>[] errorsWithSomeModuleMissing = new Pair[] {
				Pair.of("ClientModule", List.of(
						"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:10 - 1:19], Couldn't resolve reference to Type 'SomeClass'.)"))
		};
		assertIssues2(errorsWithSomeModuleMissing);

		shutdownLspServer();

		// sub-case #1: add file between server sessions (containing project does *not* contain a .n4js.projectstate
		// file)
		FileUtils.delete(providerProjectStateFile);
		FileUtils.move(someModuleHidden, someModule);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));

		shutdownLspServer();

		// sub-case #2: remove file between server sessions
		FileUtils.move(someModule, someModuleHidden);

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(List.of("ClientModule"), errorsWithSomeModuleMissing);

		shutdownLspServer();

		// sub-case #3: add file between server sessions (this time, containing project does contain an out-dated
		// '.n4js.projectstate' file!)
		assertTrue("project state file should exist but does not exist", Files.isRegularFile(providerProjectStateFile));
		FileUtils.move(someModuleHidden, someModule);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));
	}

	@Test
	public void testAddRemoveDependencyBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		Path providerProjectStateFile = getProjectRoot("ProviderProject").toPath()
				.resolve(N4JSGlobals.N4JS_PROJECT_STATE);
		Path clientProjectPackageJson = getProjectRoot("ClientProject").toPath().resolve(PACKAGE_JSON);
		changeFileOnDiskWithoutNotification(toFileURI(clientProjectPackageJson),
				Pair.of("\"ProviderProject\": \"\",", ""));

		startAndWaitForLspServer();
		@SuppressWarnings("unchecked")
		Pair<String, List<String>>[] errorsWithDependencyMissing = new Pair[] {
				Pair.of("ClientModule", List.of(
						"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:10 - 1:19], Couldn't resolve reference to Type 'SomeClass'.)")) };
		assertIssues2(errorsWithDependencyMissing);

		shutdownLspServer();

		// sub-case #1: add dependency between server sessions (target project does *not* contain a .n4js.projectstate
		// file)
		FileUtils.delete(providerProjectStateFile);
		changeFileOnDiskWithoutNotification(toFileURI(clientProjectPackageJson),
				Pair.of("\"n4js-runtime\": \"\"", "\"ProviderProject\": \"\", \"n4js-runtime\": \"\""));

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));

		shutdownLspServer();

		// sub-case #2: remove dependency between server sessions
		changeFileOnDiskWithoutNotification(toFileURI(clientProjectPackageJson),
				Pair.of("\"ProviderProject\": \"\", \"n4js-runtime\": \"\"", "\"n4js-runtime\": \"\""));

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(List.of("ClientModule"), errorsWithDependencyMissing);

		shutdownLspServer();

		// sub-case #3: add dependency between server sessions (this time, target project does contain an up-to-date
		// '.n4js.projectstate' file!)
		assertTrue("project state file should exist but does not exist", Files.isRegularFile(providerProjectStateFile));
		changeFileOnDiskWithoutNotification(toFileURI(clientProjectPackageJson),
				Pair.of("\"n4js-runtime\": \"\"", "\"ProviderProject\": \"\", \"n4js-runtime\": \"\""));

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));
	}

	@Test
	public void testAddRemoveProjectBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		Path providerProjectPath = getProjectRoot("ProviderProject").toPath();
		Path providerProjectPathHidden = temporaryFolder.resolve("ProviderProject");
		FileUtils.move(providerProjectPath, providerProjectPathHidden);

		startAndWaitForLspServer();
		@SuppressWarnings("unchecked")
		Pair<String, List<String>>[] errorsWithProviderProjectMissing = new Pair[] {
				Pair.of("ClientModule", List.of(
						"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:10 - 1:19], Couldn't resolve reference to Type 'SomeClass'.)")),
				Pair.of("ClientProject/" + PACKAGE_JSON, List.of(
						"(Error, [14:18 - 14:39], Project does not exist with project ID: ProviderProject.)")) };
		assertIssues2(errorsWithProviderProjectMissing);

		shutdownLspServer();

		// sub-case #1: add project between server sessions (project does *not* contain a .n4js.projectstate file)
		FileUtils.move(providerProjectPathHidden, providerProjectPath);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));

		shutdownLspServer();

		// sub-case #2: remove project between server sessions
		FileUtils.move(providerProjectPath, providerProjectPathHidden);

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(List.of("ClientModule"), errorsWithProviderProjectMissing);

		shutdownLspServer();

		// sub-case #3: add project between server sessions (this time, project does contain an up-to-date
		// '.n4js.projectstate' file!)
		FileUtils.move(providerProjectPathHidden, providerProjectPath);
		assertTrue("project state file does not exist",
				Files.isRegularFile(providerProjectPath.resolve(N4JSGlobals.N4JS_PROJECT_STATE)));

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(List.of("ClientModule"));
	}
}
