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
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.Test;

/**
 * Tests for building yarn workspaces, in particular certain corner and special cases such as "yarn link".
 */

public class BuilderYarnWorkspaceTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testYarnLinkSimple() throws Exception {
		String otherProjectName = "OtherProject";

		testWorkspaceManager.createTestOnDisk(Map.of(
				otherProjectName, Map.of(
						"folder/Other", """
									export public class Other {
										public m() {}
									}
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Other} from "folder/Other";
									new Other().m();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								""")));

		moveProjectToTempDirAndLinkInNodeModules(otherProjectName);

		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testYarnLinkWithRootSourceFolder() throws Exception {
		String otherProjectName = "OtherProject";

		testWorkspaceManager.createTestOnDisk(Map.of(
				otherProjectName, Map.of(
						"folder/Other", """
									export public class Other {
										public m() {}
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "%s",
										"version": "0.0.1",
										"dependencies": {
											"n4js-runtime": "*"
										},
										"n4js": {
											"projectType": "library",
											"mainModule": "folder/Other",
											"vendorId": "org.eclipse.n4js",
											"output": "src-gen",
											"sources": {
												"source": [
													"src"
												]
											}
										}
									}
								""".formatted(otherProjectName)),
				"MainProject", Map.of(
						"Main1", """
									import {Other} from "folder/Other";
									new Other().m();
								""",
						"Main2", """
									import {Other} from "OtherProject";
									new Other().m();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								""")));

		moveProjectToTempDirAndLinkInNodeModules(otherProjectName);

		startAndWaitForLspServer();
		assertNoIssues();
	}

	private void moveProjectToTempDirAndLinkInNodeModules(String projectName) throws IOException {
		Path rootFolder = getRoot().toPath();
		Path tempFolder = FileUtils.createTempDirectory("_" + BuilderYarnWorkspaceTest.class.getSimpleName());
		Path nodeModulesFolder = rootFolder.resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(NODE_MODULES);
		Path symLink = nodeModulesFolder.resolve(projectName);

		Path otherProjectFolderOld = getProjectRoot(projectName).toPath();
		Path otherProjectFolderNew = tempFolder.resolve(projectName);
		FileDeleter.delete(symLink);
		// warning: java.nio.file.Files#move() will fail if temporary folder is located on a different FileStore!
		FileCopier.copy(otherProjectFolderOld, otherProjectFolderNew);
		FileDeleter.delete(otherProjectFolderOld);

		Files.createSymbolicLink(symLink, otherProjectFolderNew);
	}
}
