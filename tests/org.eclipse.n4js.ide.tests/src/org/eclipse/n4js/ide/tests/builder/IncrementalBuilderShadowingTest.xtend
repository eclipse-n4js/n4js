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
package org.eclipse.n4js.ide.tests.builder

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.FileChangeType
import org.eclipse.lsp4j.FileEvent
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

/**
 * Test for entering/leaving a "shadowing state" during a single <code>didChangeWatchedFiles</code> notification
 * and thus a single incremental build. Here, "shadowing state" means that the workspace contains a project in the
 * <code>packages</code> folder that shadows a project located in a <code>node_modules</code> folder.
 */
class IncrementalBuilderShadowingTest extends AbstractIdeTest {

	@Test
	def void testCreateShadowingProject() throws Exception {
		doTestCreateShadowingProject(false);
	}

	@Test
	def void testCreateShadowingProject_whileModuleMainIsOpen() throws Exception {
		doTestCreateShadowingProject(true);
	}

	def private void doTestCreateShadowingProject(boolean openModuleMain) throws Exception {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(
			"ProjectMain" -> #[
				"ModuleMain" -> '''
					import {ClassOther} from "ModuleOther"
					new ClassOther().mInPackages();
					new ClassOther().mInNodeModules();
				''',
				CFG_DEPENDENCIES -> '''
					ProjectOther
				'''
			]
		);

		val nodeModulesFolder = getNodeModulesFolder().toPath;
		val packagesFolder = getProjectLocation().toPath;

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
			"yarn-test-project/packages/ProjectMain"
		);
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/node_modules/ProjectOther"
		);
		assertIssues2(
			"ModuleMain" -> #[
				"(Error, [1:17 - 1:28], Couldn't resolve reference to IdentifiableElement 'mInPackages'.)"
			]
		);

		createProjectOther(packagesFolder, "InPackages");
		// notify LSP server about project creation
		val fileEvents = #[
			new FileEvent(packagesFolder.resolve("ProjectOther").resolve(PACKAGE_JSON).toFileURI.toString, FileChangeType.Created)
		];
		val params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
		joinServerRequests();



		
		assertProjectsInWorkspace(
			"yarn-test-project",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/node_modules/ProjectOther",
			"yarn-test-project/packages/ProjectOther",
			"yarn-test-project/packages/ProjectMain"
		);
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/packages/ProjectOther"
		);
		assertIssues2(
			"ModuleMain" -> #[
				"(Error, [2:17 - 2:31], Couldn't resolve reference to IdentifiableElement 'mInNodeModules'.)"
			]
		);
	}

	@Test
	def void testDeleteShadowingProject() throws Exception {
		doTestDeleteShadowingProject(false);
	}

	@Test
	def void testDeleteShadowingProject_whileModuleMainIsOpen() throws Exception {
		doTestDeleteShadowingProject(true);
	}

	def private void doTestDeleteShadowingProject(boolean openModuleMain) throws Exception {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(
			"ProjectMain" -> #[
				"ModuleMain" -> '''
					import {ClassOther} from "ModuleOther"
					new ClassOther().mInPackages();
					new ClassOther().mInNodeModules();
				''',
				CFG_DEPENDENCIES -> '''
					ProjectOther
				'''
			]
		);

		val nodeModulesFolder = getNodeModulesFolder().toPath;
		val packagesFolder = getProjectLocation().toPath;

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
			"yarn-test-project/packages/ProjectMain"
		);
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/packages/ProjectOther"
		);
		assertIssues2(
			"ModuleMain" -> #[
				"(Error, [2:17 - 2:31], Couldn't resolve reference to IdentifiableElement 'mInNodeModules'.)"
			]
		);

		deleteFolderNotContainingOpenFiles(packagesFolder.resolve("ProjectOther").toFileURI, ".*"); // will notify server
		joinServerRequests();

		assertProjectsInWorkspace(
			"yarn-test-project",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/node_modules/ProjectOther",
			"yarn-test-project/packages/ProjectMain"
		);
		assertDependenciesOf("yarn-test-project/packages/ProjectMain",
			"yarn-test-project/node_modules/n4js-runtime",
			"yarn-test-project/node_modules/ProjectOther"
		);
		assertIssues2(
			"ModuleMain" -> #[
				"(Error, [1:17 - 1:28], Couldn't resolve reference to IdentifiableElement 'mInPackages'.)"
			]
		);
	}

	def private void createProjectOther(Path location, String methodNameSuffix) throws IOException {
		val projectFolder = location.resolve("ProjectOther");
		val srcFolder = projectFolder.resolve("src");
		Files.createDirectories(projectFolder);
		Files.createDirectory(srcFolder);
		Files.writeString(projectFolder.resolve(PACKAGE_JSON), '''
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
		''');
		Files.writeString(srcFolder.resolve("ModuleOther.n4js"), '''
			export public class ClassOther {
				public m«methodNameSuffix»() {}
			}
		''');
	}
}
