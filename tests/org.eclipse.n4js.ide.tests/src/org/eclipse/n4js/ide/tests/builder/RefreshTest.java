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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests for the "N4JS: Refresh" command.
 */

public class RefreshTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testChangeFileInSingleProject() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"SomeModule", """
							export public class SomeClass {
								public m() {}
							}
						""",
				"MainModule", """
							import {SomeClass} from "SomeModule"
							new SomeClass().m();
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("m()", "mx()"));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		Pair<String, List<String>> errorWithIncorrectMethodName = Pair.of("MainModule", List.of(
				"(Error, [1:17 - 1:18], Couldn't resolve reference to IdentifiableElement 'm'.)"));
		assertIssues2(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("mx()", "m()"));

		assertIssues2(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeFileInWorkspaceProject() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"SomeProject", Map.of(
						"SomeModule", """
									export public class SomeClass {
										public m() {}
									}
								"""),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass} from "SomeModule"
									new SomeClass().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("m()", "mx()"));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		Pair<String, List<String>> errorWithIncorrectMethodName = Pair.of("MainModule", List.of(
				"(Error, [1:17 - 1:18], Couldn't resolve reference to IdentifiableElement 'm'.)"));
		assertIssues2(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("mx()", "m()"));

		assertIssues2(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeFileInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "SomeProject", Map.of(
						"SomeModule", """
									export public class SomeClass {
										public m() {}
									}
								"""),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass} from "SomeModule"
									new SomeClass().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("m()", "mx()"));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		Pair<String, List<String>> errorWithIncorrectMethodName = Pair.of("MainModule", List.of(
				"(Error, [1:17 - 1:18], Couldn't resolve reference to IdentifiableElement 'm'.)"));
		assertIssues2(errorWithIncorrectMethodName);

		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("mx()", "m()"));

		assertIssues2(errorWithIncorrectMethodName);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testAddRemoveFileInNodeModulesProject() throws IOException {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "SomeProject", Map.of(
				// SomeModule omitted
				),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass} from "SomeModule"
									new SomeClass().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject
								""")));
		startAndWaitForLspServer();
		Pair<String, List<String>> errorsWhenSomeModuleMissing = Pair.of("MainModule", List.of(
				"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:5 - 1:14], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)"));
		assertIssues2(errorsWhenSomeModuleMissing);

		Path someModule = getProjectRoot("SomeProject").toPath().resolve("src").resolve("SomeModule.n4js");
		Files.writeString(someModule, """
					export public class SomeClass {
						public m() {}
					}
				""");

		assertIssues2(errorsWhenSomeModuleMissing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		Files.delete(someModule);

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues2(errorsWhenSomeModuleMissing);
	}

	/**
	 * The special aspect of this test is that we change a <code>package.json</code> file in a way that does *not* lead
	 * to a project being added/removed to/from the workspace.
	 */
	@Test
	public void testChangePackageJsonInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "SomeProject", Map.of(
						"SomeModule", """
									export public class SomeClass {
										public m() {}
									}
								"""),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass} from "SomeModule"
									new SomeClass().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI packageJsonOfSomeProject = toFileURI(getPackageJsonFile("SomeProject"));
		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject, Pair.of("\"src\"", "\"src_BAD\""));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues2(Pair.of("MainModule", List.of(
				"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:5 - 1:14], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)")));

		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject, Pair.of("\"src_BAD\"", "\"src\""));

		assertIssues2(Pair.of("MainModule", List.of(
				"(Error, [0:25 - 0:37], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:5 - 1:14], Couldn't resolve reference to IdentifiableElement 'SomeClass'.)")));
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testAddRemoveDependencyInNodeModulesProject() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "SomeProject1", Map.of(
						"SomeModule1", """
									export public class SomeClass1 {
										public m() {}
									}
								"""),
				CFG_NODE_MODULES + "SomeProject2", Map.of(
						"SomeModule2", """
									import {SomeClass1} from "SomeModule1"
									export public class SomeClass2 extends SomeClass1 {}
								"""
				// missing dependency: SomeProject1
				),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass2} from "SomeModule2"
									new SomeClass2().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject2
								""")));
		startAndWaitForLspServer();
		Pair<String, List<String>> errorWhenDependencyMissing = Pair.of("MainModule", List.of(
				"(Error, [1:18 - 1:19], Couldn't resolve reference to IdentifiableElement 'm'.)"));
		assertIssues2(errorWhenDependencyMissing);

		FileURI packageJsonOfSomeProject2 = toFileURI(getPackageJsonFile("SomeProject2"));
		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject2,
				Pair.of("\"n4js-runtime\": \"\"", "\"n4js-runtime\": \"\", \"SomeProject1\": \"\""));

		assertIssues2(errorWhenDependencyMissing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		changeFileOnDiskWithoutNotification(packageJsonOfSomeProject2,
				Pair.of("\"n4js-runtime\": \"\", \"SomeProject1\": \"\"", "\"n4js-runtime\": \"\""));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues2(errorWhenDependencyMissing);
	}

	@Test
	public void testAddRemoveProjectInNodeModulesFolder() throws IOException {
		testWorkspaceManager.createTestOnDisk(Map.of(
				// missing project: SomeProject1
				CFG_NODE_MODULES + "SomeProject2", Map.of(
						"SomeModule2", """
									import {SomeClass1} from "SomeModule1"
									export public class SomeClass2 extends SomeClass1 {}
								""",
						CFG_DEPENDENCIES, """
									SomeProject1
								"""),
				"MainProject", Map.of(
						"MainModule", """
									import {SomeClass2} from "SomeModule2"
									new SomeClass2().m();
								""",
						CFG_DEPENDENCIES, """
									SomeProject2
								""")));
		startAndWaitForLspServer();
		Pair<String, List<String>> errorWhenSomeProject1Missing = Pair.of("MainModule", List.of(
				"(Error, [1:18 - 1:19], Couldn't resolve reference to IdentifiableElement 'm'.)"));
		assertIssues2(errorWhenSomeProject1Missing);

		createMissingProject(getProjectRoot("SomeProject2").toPath().getParent());

		assertIssues2(errorWhenSomeProject1Missing);
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertNoIssues();

		FileUtils.delete(getProjectRoot("SomeProject2").toPath().getParent().resolve("SomeProject1"));

		assertNoIssues();
		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();
		assertIssues2(errorWhenSomeProject1Missing);
	}

	private void createMissingProject(Path location) throws IOException {
		Path someProject1Path = location.resolve("SomeProject1");
		Files.createDirectory(someProject1Path);
		Files.createDirectory(someProject1Path.resolve("src"));
		Files.writeString(someProject1Path.resolve("src").resolve("SomeModule1.n4js"), """
					export public class SomeClass1 {
						public m() {}
					}
				""");
		Files.writeString(someProject1Path.resolve(PACKAGE_JSON), """
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
				""");
	}
}
