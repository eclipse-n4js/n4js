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
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
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
			TestWorkspaceManager.NODE_MODULES + "n4js-runtime" -> null,
			otherProjectName -> #[
				"folder/Other" -> '''
					export public class Other {
						public m() {}
					}
				''',
				TestWorkspaceManager.DEPENDENCIES -> "n4js-runtime"
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Other} from "folder/Other";
					new Other().m();
				''',
				TestWorkspaceManager.DEPENDENCIES -> '''
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
			TestWorkspaceManager.NODE_MODULES + "n4js-runtime" -> null,
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
				TestWorkspaceManager.DEPENDENCIES -> '''
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
		val tempFolder = createTempDir();

		val otherProjectFolderOld = getProjectRoot(projectName).toPath;
		val otherProjectFolderNew = tempFolder.resolve(projectName);
		Files.move(otherProjectFolderOld, otherProjectFolderNew);

		val nodeModulesFolder = rootFolder.resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(N4JSGlobals.NODE_MODULES);
		Files.createSymbolicLink(nodeModulesFolder.resolve(projectName), otherProjectFolderNew);
	}

	def private static Path createTempDir() throws IOException {
		val result = Files.createTempDirectory("_" + BuilderYarnWorkspaceTest.simpleName);
		result.toFile.deleteOnExit();
		return result;
	}
}
