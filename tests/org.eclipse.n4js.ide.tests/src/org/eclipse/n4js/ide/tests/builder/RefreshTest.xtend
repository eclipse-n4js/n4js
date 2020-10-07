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
import org.eclipse.n4js.ide.server.commands.N4JSCommandService
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.eclipse.n4js.utils.io.FileUtils
import org.junit.Test

/**
 * Tests for the "N4JS: Refresh" command.
 */
class RefreshTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testChangeFileInSingleProject() {
		testWorkspaceManager.createTestProjectOnDisk(
			"SomeModule" -> '''
				export public class SomeClass {
					public m() {}
				}
			''',
			"MainModule" -> '''
				import {SomeClass} from "SomeModule"
				new SomeClass().m();
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", "m()" -> "mx()");

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		val errorWithIncorrectMethodName = "MainModule" -> #[
			"(Error, [1:16 - 1:17], Couldn't resolve reference to IdentifiableElement 'm'.)"
		];
		assertIssues(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", "mx()" -> "m()");

		assertIssues(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeFileInWorkspaceProject() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"SomeProject" -> #[
				"SomeModule" -> '''
					export public class SomeClass {
						public m() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass} from "SomeModule"
					new SomeClass().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", "m()" -> "mx()");

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		val errorWithIncorrectMethodName = "MainModule" -> #[
			"(Error, [1:16 - 1:17], Couldn't resolve reference to IdentifiableElement 'm'.)"
		];
		assertIssues(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", "mx()" -> "m()");

		assertIssues(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeFileInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject" -> #[
				"SomeModule" -> '''
					export public class SomeClass {
						public m() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass} from "SomeModule"
					new SomeClass().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", "m()" -> "mx()");

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		val errorWithIncorrectMethodName = "MainModule" -> #[
			"(Error, [1:16 - 1:17], Couldn't resolve reference to IdentifiableElement 'm'.)"
		];
		assertIssues(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", "mx()" -> "m()");

		assertIssues(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testAddRemoveFileInNodeModulesProject() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject" -> #[
				// SomeModule omitted
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass} from "SomeModule"
					new SomeClass().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject
				'''
			]
		);
		startAndWaitForLspServer();
		val errorsWhenSomeModuleMissing = "MainModule" -> #[
			"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
			"(Error, [1:4 - 1:13], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)"
		];
		assertIssues(errorsWhenSomeModuleMissing);

		val someModule = getProjectRoot("SomeProject").toPath.resolve("src").resolve("SomeModule.n4js");
		Files.writeString(someModule, '''
			export public class SomeClass {
				public m() {}
			}
		''');

		assertIssues(errorsWhenSomeModuleMissing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		Files.delete(someModule);

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues(errorsWhenSomeModuleMissing);
	}

	/**
	 * The special aspect of this test is that we change a <code>package.json</code> file in a way
	 * that does *not* lead to a project being added/removed to/from the workspace.
	 */
	@Test
	def void testChangePackageJsonInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject" -> #[
				"SomeModule" -> '''
					export public class SomeClass {
						public m() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass} from "SomeModule"
					new SomeClass().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val packageJsonOfSomeProject = getPackageJsonFile("SomeProject").toFileURI;
		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject, '"src"' -> '"src_BAD"');

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues("MainModule" -> #[
			"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
			"(Error, [1:4 - 1:13], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)"
		]);

		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject, '"src_BAD"' -> '"src"');

		assertIssues("MainModule" -> #[
			"(Error, [0:24 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
			"(Error, [1:4 - 1:13], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)"
		]);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testAddRemoveDependencyInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject1" -> #[
				"SomeModule1" -> '''
					export public class SomeClass1 {
						public m() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject2" -> #[
				"SomeModule2" -> '''
					import {SomeClass1} from "SomeModule1"
					export public class SomeClass2 extends SomeClass1 {}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime" // missing dependency: SomeProject1
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass2} from "SomeModule2"
					new SomeClass2().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject2
				'''
			]
		);
		startAndWaitForLspServer();
		val errorWhenDependencyMissing = "MainModule" -> #[
			"(Error, [1:17 - 1:18], Couldn't resolve reference to IdentifiableElement 'm'.)"
		];
		assertIssues(errorWhenDependencyMissing);

		val packageJsonOfSomeProject2 = getPackageJsonFile("SomeProject2").toFileURI;
		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject2,
			'"n4js-runtime": "*"' -> '"n4js-runtime": "*", "SomeProject1": "*"'
		);

		assertIssues(errorWhenDependencyMissing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject2,
			'"n4js-runtime": "*", "SomeProject1": "*"' -> '"n4js-runtime": "*"'
		);

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues(errorWhenDependencyMissing);
	}

	@Test
	def void testAddRemoveProjectInNodeModulesFolder() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "SomeProject2" -> #[
				"SomeModule2" -> '''
					import {SomeClass1} from "SomeModule1"
					export public class SomeClass2 extends SomeClass1 {}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject1
				'''
			],
			"MainProject" -> #[
				"MainModule" -> '''
					import {SomeClass2} from "SomeModule2"
					new SomeClass2().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					SomeProject2
				'''
			]
		);
		startAndWaitForLspServer();
		val errorWhenSomeProject1Missing = "MainModule" -> #[
			"(Error, [1:17 - 1:18], Couldn't resolve reference to IdentifiableElement 'm'.)"
		];
		assertIssues(errorWhenSomeProject1Missing);

		createMissingProject(getProjectRoot("SomeProject2").toPath.parent);

		assertIssues(errorWhenSomeProject1Missing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		FileUtils.delete(getProjectRoot("SomeProject2").toPath.parent.resolve("SomeProject1"));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues(errorWhenSomeProject1Missing);
	}

	def private void createMissingProject(Path location) throws IOException {
		val someProject1Path = location.resolve("SomeProject1");
		Files.createDirectory(someProject1Path);
		Files.createDirectory(someProject1Path.resolve("src"));
		Files.writeString(someProject1Path.resolve("src").resolve("SomeModule1.n4js"), '''
			export public class SomeClass1 {
				public m() {}
			}
		''');
		Files.writeString(someProject1Path.resolve(N4JSGlobals.PACKAGE_JSON), '''
			{
				"name": "SomeProject1",
				"n4js": {
					"projectType": "library",
					"sources": {
						"source": [
							"src"
						]
					},
					"output": "src-gen"
				},
				"dependencies": {
					"n4js-runtime": "*"
				}
			}
		''');
	}
}
