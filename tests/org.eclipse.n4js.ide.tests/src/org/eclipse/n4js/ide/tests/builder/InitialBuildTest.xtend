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
package org.eclipse.n4js.ide.tests.builder

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.utils.io.FileDeleter
import org.eclipse.n4js.utils.io.FileUtils
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test various cases of changes between LSP server sessions to ensure the initial build of the later session
 * correctly reacts to those changes.
 */
class InitialBuildTest extends AbstractIncrementalBuilderTest {

	private static val testData = #[
		"ProviderProject" -> #[
			"SomeModule" -> '''
				export public class SomeClass {
				}
			'''
		],
		"ClientProject" -> #[
			"ClientModule" -> '''
				import {SomeClass} from "SomeModule";
				const x: SomeClass = null;
				x;
			''',
			CFG_DEPENDENCIES -> '''
				ProviderProject
			'''
		]
	];

	private static Path temporaryFolder;

	@BeforeClass
	def static void createTemporaryFolder() throws IOException {
		temporaryFolder = Files.createTempDirectory("temp_InitialBuildTest_");
	}

	@AfterClass
	def static void deleteTemporaryFolder() throws IOException {
		if (temporaryFolder !== null) {
			FileDeleter.delete(temporaryFolder);
		}
	}

	@Before
	def void cleanTemporaryFolder() throws IOException {
		FileUtils.cleanFolder(temporaryFolder);
	}

	@Test
	def void testFixFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(testData);
		changeFileOnDiskWithoutNotification("SomeModule", "SomeClass" -> "SomeClassX");
		startAndWaitForLspServer();
		assertIssues("ClientModule" -> #[
			"(Error, [0:8 - 0:17], Import of SomeClass cannot be resolved.)",
			"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
		]);

		shutdownLspServer();

		// test case: fix file between server sessions
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClassX", "SomeClass"));

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);
	}

	@Test
	def void testBreakFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(testData);
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();

		// test case: break file between server sessions
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClass", "SomeClassX"));

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(
			#["ClientModule"],
			"ClientModule" -> #[
				"(Error, [0:8 - 0:17], Import of SomeClass cannot be resolved.)",
				"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
			]
		);
	}

	@Test
	def void testAddRemoveFileBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		val providerProjectStateFile = getProjectRoot("ProviderProject").toPath.resolve(N4JSGlobals.N4JS_PROJECT_STATE);
		val someModule = getFileURIFromModuleName("SomeModule").toFile.toPath;
		val someModuleHidden = temporaryFolder.resolve(someModule.fileName.toString);
		FileUtils.move(someModule, someModuleHidden);

		startAndWaitForLspServer();
		val errorsWithSomeModuleMissing = #[
			"ClientModule" -> #[
				"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
			]
		];
		assertIssues(errorsWithSomeModuleMissing);

		shutdownLspServer();

		// sub-case #1: add file between server sessions (containing project does *not* contain a .n4js.projectstate file)
		FileUtils.delete(providerProjectStateFile);
		FileUtils.move(someModuleHidden, someModule);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);

		shutdownLspServer();

		// sub-case #2: remove file between server sessions
		FileUtils.move(someModule, someModuleHidden);

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(#["ClientModule"], errorsWithSomeModuleMissing);

		shutdownLspServer();

		// sub-case #3: add file between server sessions (this time, containing project does contain an out-dated '.n4js.projectstate' file!)
		assertTrue("project state file should exist but does not exist", Files.isRegularFile(providerProjectStateFile))
		FileUtils.move(someModuleHidden, someModule);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);
	}

	@Test
	def void testAddRemoveDependencyBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		val providerProjectStateFile = getProjectRoot("ProviderProject").toPath.resolve(N4JSGlobals.N4JS_PROJECT_STATE);
		val clientProjectPackageJson = getProjectRoot("ClientProject").toPath.resolve(PACKAGE_JSON);
		changeFileOnDiskWithoutNotification(clientProjectPackageJson.toFileURI,
			'"ProviderProject": "*",' -> ''
		);

		startAndWaitForLspServer();
		val errorsWithDependencyMissing = #[
			"ClientModule" -> #[
				"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
			]
		];
		assertIssues(errorsWithDependencyMissing);

		shutdownLspServer();

		// sub-case #1: add dependency between server sessions (target project does *not* contain a .n4js.projectstate file)
		FileUtils.delete(providerProjectStateFile);
		changeFileOnDiskWithoutNotification(clientProjectPackageJson.toFileURI,
			'"n4js-runtime": "*"' -> '"ProviderProject": "*", "n4js-runtime": "*"'
		);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);

		shutdownLspServer();

		// sub-case #2: remove dependency between server sessions
		changeFileOnDiskWithoutNotification(clientProjectPackageJson.toFileURI,
			'"ProviderProject": "*", "n4js-runtime": "*"' -> '"n4js-runtime": "*"'
		);

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(#["ClientModule"], errorsWithDependencyMissing);

		shutdownLspServer();

		// sub-case #3: add dependency between server sessions (this time, target project does contain an up-to-date '.n4js.projectstate' file!)
		assertTrue("project state file should exist but does not exist", Files.isRegularFile(providerProjectStateFile))
		changeFileOnDiskWithoutNotification(clientProjectPackageJson.toFileURI,
			'"n4js-runtime": "*"' -> '"ProviderProject": "*", "n4js-runtime": "*"'
		);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);
	}

	@Test
	def void testAddRemoveProjectBetweenServerSessions() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData);

		val providerProjectPath = getProjectRoot("ProviderProject").toPath;
		val providerProjectPathHidden = temporaryFolder.resolve("ProviderProject");
		FileUtils.move(providerProjectPath, providerProjectPathHidden);

		startAndWaitForLspServer();
		val errorsWithProviderProjectMissing = #[
			"ClientModule" -> #[
				"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
			],
			"ClientProject/" + PACKAGE_JSON -> #[
				"(Error, [15:3 - 15:25], Project does not exist with project ID: ProviderProject.)"
			]
		];
		assertIssues(errorsWithProviderProjectMissing);

		shutdownLspServer();

		// sub-case #1: add project between server sessions (project does *not* contain a .n4js.projectstate file)
		FileUtils.move(providerProjectPathHidden, providerProjectPath);

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);

		shutdownLspServer();

		// sub-case #2: remove project between server sessions
		FileUtils.move(providerProjectPath, providerProjectPathHidden);

		startAndWaitForLspServer();
		assertIssuesInBuilderAndEditors(#["ClientModule"], errorsWithProviderProjectMissing);

		shutdownLspServer();

		// sub-case #3: add project between server sessions (this time, project does contain an up-to-date '.n4js.projectstate' file!)
		FileUtils.move(providerProjectPathHidden, providerProjectPath);
		assertTrue("project state file does not exist", Files.isRegularFile(providerProjectPath.resolve(N4JSGlobals.N4JS_PROJECT_STATE)))

		startAndWaitForLspServer();
		assertNoIssuesInBuilderAndEditors(#["ClientModule"]);
	}
}
