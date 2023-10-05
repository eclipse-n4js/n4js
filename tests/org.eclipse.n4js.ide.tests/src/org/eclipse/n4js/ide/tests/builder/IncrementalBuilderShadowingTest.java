/**
 * Copyright (c) 2021 NumberFour AG.
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

import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test for entering/leaving a "shadowing state" during a single <code>didChangeWatchedFiles</code> notification and
 * thus a single incremental build. Here, "shadowing state" means that the workspace contains a project in the
 * <code>packages</code> folder that shadows a project located in a <code>node_modules</code> folder.
 */

public class IncrementalBuilderShadowingTest extends AbstractIdeTest {

	@Test
	public void testCreateShadowingProject() throws Exception {
		doTestCreateShadowingProject(false);
	}

	@Test
	public void testCreateShadowingProject_whileModuleMainIsOpen() throws Exception {
		doTestCreateShadowingProject(true);
	}

	private void doTestCreateShadowingProject(boolean openModuleMain) throws Exception {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(Map.of(
				"ProjectMain", Map.of(
						"ModuleMain", """
									import {ClassOther} from "ModuleOther"
									new ClassOther().mInPackages();
									new ClassOther().mInNodeModules();
								""",
						CFG_DEPENDENCIES, """
									ProjectOther
								""")));

		Path nodeModulesFolder = getNodeModulesFolder().toPath();
		Path packagesFolder = getProjectLocation().toPath();

		createProjectOther(nodeModulesFolder, "InNodeModules");

		startAndWaitForLspServer();
		if (openModuleMain) {
			openFile("ModuleMain");
			joinServerRequests();
		}

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther");
		assertIssues2(
				Pair.of("ModuleMain", List.of(
						"(Error, [1:18 - 1:29], Couldn't resolve reference to IdentifiableElement 'mInPackages'.)")));

		createProjectOther(packagesFolder, "InPackages");
		// notify LSP server about project creation
		List<FileEvent> fileEvents = List.of(
				new FileEvent(toFileURI(packagesFolder.resolve("ProjectOther").resolve(PACKAGE_JSON)).toString(),
						FileChangeType.Created));
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
		joinServerRequests();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther",
				"yarn-test-project/packages/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/packages/ProjectOther");
		assertIssues2(
				Pair.of("ModuleMain", List.of(
						"(Error, [2:18 - 2:32], Couldn't resolve reference to IdentifiableElement 'mInNodeModules'.)")));
	}

	@Test
	public void testDeleteShadowingProject() throws Exception {
		doTestDeleteShadowingProject(false);
	}

	@Test
	public void testDeleteShadowingProject_whileModuleMainIsOpen() throws Exception {
		doTestDeleteShadowingProject(true);
	}

	private void doTestDeleteShadowingProject(boolean openModuleMain) throws Exception {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(Map.of(
				"ProjectMain", Map.of(
						"ModuleMain", """
									import {ClassOther} from "ModuleOther"
									new ClassOther().mInPackages();
									new ClassOther().mInNodeModules();
								""",
						CFG_DEPENDENCIES, """
									ProjectOther
								""")));

		Path nodeModulesFolder = getNodeModulesFolder().toPath();
		Path packagesFolder = getProjectLocation().toPath();

		createProjectOther(nodeModulesFolder, "InNodeModules");
		createProjectOther(packagesFolder, "InPackages");

		startAndWaitForLspServer();
		if (openModuleMain) {
			openFile("ModuleMain");
			joinServerRequests();
		}

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther",
				"yarn-test-project/packages/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/packages/ProjectOther");
		assertIssues2(
				Pair.of("ModuleMain", List.of(
						"(Error, [2:18 - 2:32], Couldn't resolve reference to IdentifiableElement 'mInNodeModules'.)")));

		deleteFolderNotContainingOpenFiles(toFileURI(packagesFolder.resolve("ProjectOther")), ".*"); // will notify
																										// server
		joinServerRequests();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther");
		assertIssues2(
				Pair.of("ModuleMain", List.of(
						"(Error, [1:18 - 1:29], Couldn't resolve reference to IdentifiableElement 'mInPackages'.)")));
	}

	private void createProjectOther(Path location, String methodNameSuffix) throws IOException {
		Path projectFolder = location.resolve("ProjectOther");
		Path srcFolder = projectFolder.resolve("src");
		Files.createDirectories(projectFolder);
		Files.createDirectory(srcFolder);
		Files.writeString(projectFolder.resolve(PACKAGE_JSON), """
					{
						"name": "ProjectOther",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"output": "src-gen",
							"sources": { "source": [ "src" ] }
						},
						"dependencies": {
							"n4js-runtime": "*"
						}
					}
				""");
		Files.writeString(srcFolder.resolve("ModuleOther.n4js"), """
					export public class ClassOther {
						public m%s() {}
					}
				""".formatted(methodNameSuffix));
	}
}
