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

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.Map
import org.eclipse.n4js.ide.server.commands.N4JSCommandService
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager
import org.eclipse.n4js.utils.io.FileDeleter
import org.junit.Test

/**
 * Tests that a rebuild (e.g. the "N4JS: Rebuild" command) finds a newly added npm package in the node_modules folder,
 * even if no didChangeWatchedFiles events were sent.
 */
class RebuildFindsNewNpmPackageTest extends AbstractIdeTest {

	/** Add/remove npm package in the local node_modules folder of a single N4JS project (i.e. no yarn workspace). */
	@Test
	def void testLocalNodeModulesFolder() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				import * as N+ from "LibModule"
				N.something;
			''',
			CFG_DEPENDENCIES -> '''
				lib
			'''
		);
		performTest(getProjectRoot(), getProjectRoot());
	}

	/** Add/remove npm package in the global node_modules folder of a yarn workspace. */
	@Test
	def void testGlobalNodeModulesFolder() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			"ProjectMain" -> #[
				"Main" -> '''
					import * as N+ from "LibModule"
					N.something;
				''',
				CFG_DEPENDENCIES -> '''
					lib
				'''
			],
			"ProjectOther" -> #[] // only required to obtain a yarn workspace
		);
		performTest(getProjectRoot(TestWorkspaceManager.YARN_TEST_PROJECT), getProjectRoot("ProjectMain"));
	}

	/**
	 * @param rootProjectFolder the project containing the node_modules folder where to add/remove the npm package.
	 * @param mainProjectFolder the project containing the test files.
	 */
	def private void performTest(File rootProjectFolder, File mainProjectFolder) throws IOException {
		startAndWaitForLspServer();

		val packageJsonFileURI = mainProjectFolder.toPath.resolve(PACKAGE_JSON).toFileURI;
		val errorsWhenNpmPackageMissing = Map.of(
			getFileURIFromModuleName("Main"), #[
				"(Error, [0:20 - 0:31], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			],
			packageJsonFileURI, #[
				"(Error, [15:3 - 15:13], Project does not exist with project ID: lib.)"
			]
		);
		assertIssues(errorsWhenNpmPackageMissing);

		// create the missing npm package (without notifications to the server)
		val libProjectFolder = createNpmPackageLib(rootProjectFolder);

		executeCommand(N4JSCommandService.N4JS_REBUILD);
		joinServerRequests();
		assertNoIssues();

		// remove npm package (without notifications to the server)
		FileDeleter.delete(libProjectFolder);

		executeCommand(N4JSCommandService.N4JS_REBUILD);
		joinServerRequests();
		assertIssues(errorsWhenNpmPackageMissing);
	}

	def private Path createNpmPackageLib(File parentProjectFolder) throws IOException {
		val libProjectFolder = parentProjectFolder.toPath.resolve(NODE_MODULES).resolve("lib");
		Files.createDirectory(libProjectFolder);
		Files.writeString(libProjectFolder.resolve(PACKAGE_JSON), '''
			{
				"name": "lib",
				"version": "0.0.1"
			}
		''');
		Files.writeString(libProjectFolder.resolve("LibModule.js"), '''
			// content does not matter
		''');
		return libProjectFolder;
	}
}
