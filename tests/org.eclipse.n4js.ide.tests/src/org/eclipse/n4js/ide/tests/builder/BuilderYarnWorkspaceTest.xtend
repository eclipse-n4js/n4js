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
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.eclipse.n4js.utils.io.FileCopier
import org.eclipse.n4js.utils.io.FileDeleter
import org.eclipse.n4js.utils.io.FileUtils
import org.junit.Test

/**
 * Tests for building yarn workspaces, in particular certain corner and special cases
 * such as "yarn link".
 */
class BuilderYarnWorkspaceTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testYarnLinkSimple() throws Exception {
		val otherProjectName = "OtherProject";

		testWorkspaceManager.createTestOnDisk(
			otherProjectName -> #[
				"folder/Other" -> '''
					export public class Other {
						public m() {}
					}
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Other} from "folder/Other";
					new Other().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					OtherProject
				'''
			]
		);

		moveProjectToTempDirAndLinkInNodeModules(otherProjectName);

		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testYarnLinkWithRootSourceFolder() throws Exception {
		val otherProjectName = "OtherProject";

		testWorkspaceManager.createTestOnDisk(
			otherProjectName -> #[
				"folder/Other" -> '''
					export public class Other {
						public m() {}
					}
				''',
				TestWorkspaceManager.PACKAGE_JSON -> '''
					{
						"name": "«otherProjectName»",
						"version": "0.0.1",
						"dependencies": {
							"n4js-runtime": "*"
						},
						"n4js": {
							"projectType": "library",
							"mainModule": "src/folder/Other",
							"vendorId": "org.eclipse.n4js",
							"sources": {
								"source": [
									"."
								]
							}
						}
					}
				'''
			],
			"MainProject" -> #[
				"Main1" -> '''
					import {Other} from "src/folder/Other";
					new Other().m();
				''',
				"Main2" -> '''
					import {Other} from "OtherProject";
					new Other().m();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					OtherProject
				'''
			]
		);

		moveProjectToTempDirAndLinkInNodeModules(otherProjectName);

		startAndWaitForLspServer();
		assertNoIssues();
	}

	def private void moveProjectToTempDirAndLinkInNodeModules(String projectName) throws IOException {
		val rootFolder = getRoot().toPath;
		val tempFolder = FileUtils.createTempDirectory("_" + BuilderYarnWorkspaceTest.simpleName);

		val otherProjectFolderOld = getProjectRoot(projectName).toPath;
		val otherProjectFolderNew = tempFolder.resolve(projectName);
		FileCopier.copy(otherProjectFolderOld, otherProjectFolderNew); // warning: java.nio.file.Files#move() will fail if temporary folder is located on a different FileStore!
		FileDeleter.delete(otherProjectFolderOld);

		val nodeModulesFolder = rootFolder.resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(N4JSGlobals.NODE_MODULES);
		Files.createSymbolicLink(nodeModulesFolder.resolve(projectName), otherProjectFolderNew);
	}
}
