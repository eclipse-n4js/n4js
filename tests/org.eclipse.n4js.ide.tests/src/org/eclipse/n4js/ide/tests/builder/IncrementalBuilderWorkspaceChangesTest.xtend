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
import java.util.Map
import org.eclipse.n4js.utils.io.FileCopier
import org.eclipse.n4js.utils.io.FileDeleter
import org.junit.Test

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.DEPENDENCIES
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.N4JS_RUNTIME
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.NODE_MODULES
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.YARN_TEST_PROJECT

/**
 * Tests incremental builds triggered by changes that lead to a different overall workspace configuration,
 * e.g. projects being added or removed, source folders being added or removed.
 */
class IncrementalBuilderWorkspaceChangesTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testChangePackageJson_addRemoveDependency() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {OtherClass} from "Other";
					new OtherClass().m();
				''',
				DEPENDENCIES -> N4JS_RUNTIME // note: missing the dependency to OtherProject
			],
			"OtherProject" -> #[
				"Other" -> '''
					export public class OtherClass {
						public m() {}
					}
				''',
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		val originalErrors = #[
			"(Error, [0:8 - 0:18], Couldn't resolve reference to TExportableElement 'OtherClass'.)",
			"(Error, [0:8 - 0:18], Import of OtherClass cannot be resolved.)",
			"(Error, [0:25 - 0:32], Cannot resolve import target :: resolving simple module import : found no matching modules)",
			"(Error, [1:4 - 1:14], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)"
		];
		assertIssues("Main" -> originalErrors);

		// add dependency from MainProject to OtherProject in package.json
		val packageJsonFileURI = getPackageJsonFile("MainProject").toFileURI;
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
			'"n4js-runtime": "*"' -> '"n4js-runtime": "*", "OtherProject": "*"'
		);
		joinServerRequests();

		assertIssues("Main" -> originalErrors); // changes in package.json not saved yet, so still the original errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertNoIssues(); // now the original errors have gone away

		changeOpenedFile(packageJsonFileURI,
			'"n4js-runtime": "*", "OtherProject": "*"' -> '"n4js-runtime": "*"'
		);
		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues("Main" -> originalErrors); // back to original errors
	}

	@Test
	def void testChangePackageJson_addRemoveSourceFolder() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				import {OtherClass} from "Other";
				new OtherClass().m();
			'''
		);
		startAndWaitForLspServer();

		val originalErrors = #[
			"(Error, [0:8 - 0:18], Couldn't resolve reference to TExportableElement 'OtherClass'.)",
			"(Error, [0:8 - 0:18], Import of OtherClass cannot be resolved.)",
			"(Error, [0:25 - 0:32], Cannot resolve import target :: resolving simple module import : found no matching modules)",
			"(Error, [1:4 - 1:14], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)"
		];
		assertIssues("Main" -> originalErrors);

		// create a second source folder with a file "Other" fixing Main's error but introducing a new error
		val otherFile = new File(getProjectRoot(), "src2" + File.separator + "Other.n4js");
		otherFile.parentFile.mkdirs;
		Files.writeString(otherFile.toPath, '''
			export public class OtherClass {
				public m() {}
			}
			let x: number = "bad";
		''');

		cleanBuildAndWait();
		assertIssues("Main" -> originalErrors); // new source folder not registered in package.json yet, so still the original errors

		// register new source folder in package.json
		val packageJsonFileURI = getPackageJsonFile().toFileURI;
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
			'"src"' -> '"src", "src2"'
		);
		joinServerRequests();

		assertIssues("Main" -> originalErrors); // changes in package.json not saved yet, so still the original errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		// now the original errors have gone away and a new error shows up
		assertIssues("Other" -> #[ "(Error, [3:16 - 3:21], string is not a subtype of number.)" ]);

		changeOpenedFile(packageJsonFileURI,
			'"src", "src2"' -> '"src"'
		);
		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues("Main" -> originalErrors); // back to original errors
	}

	@Test
	def void testChangePackageJson_yarnWorkspacesProperty() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {OtherClass} from "Other";
					new OtherClass().m();
				''',
				DEPENDENCIES -> '''
					«N4JS_RUNTIME»,
					OtherProject
				'''
			],
			"OtherProject" -> #[
				"Other" -> '''
					export public class OtherClass {
						public m() {}
					}
				''',
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);

		// before launching the LSP server, move "OtherProject" into a separate packages-folder
		val otherProjectOldLocation = getProjectRoot("OtherProject");
		val packagesFolder = otherProjectOldLocation.parentFile;
		val packagesFolderNew = new File(packagesFolder.parentFile, "packagesNew");
		val otherProjectNewLocation = new File(packagesFolderNew, "OtherProject");
		otherProjectNewLocation.mkdirs();
		FileCopier.copy(otherProjectOldLocation, otherProjectNewLocation);
		FileDeleter.delete(otherProjectOldLocation);

		startAndWaitForLspServer();

		val originalErrors = Map.of(
			getFileURIFromModuleName("Main"), #[
				"(Error, [0:8 - 0:18], Couldn't resolve reference to TExportableElement 'OtherClass'.)",
				"(Error, [0:8 - 0:18], Import of OtherClass cannot be resolved.)",
				"(Error, [0:25 - 0:32], Cannot resolve import target :: resolving simple module import : found no matching modules)",
				"(Error, [1:4 - 1:14], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)"
			],
			getPackageJsonFile("MainProject").toFileURI, #[
				"(Error, [16:3 - 16:22], Project does not exist with project ID: OtherProject.)"
			]
		);
		assertIssues(originalErrors);

		// register new packages folder in package.json in yarn workspace root folder
		val packageJsonFileURI = getPackageJsonFile(YARN_TEST_PROJECT).toFileURI;
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
			'"packages/*"' -> '"packages/*", "packagesNew/*"'
		);
		joinServerRequests();

		assertIssues(originalErrors); // changes in package.json not saved yet, so still the original errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertNoIssues(); // now the original errors have gone away

// TODO GH-1729 removal of workspace folders does not work yet
//		changeOpenedFile(packageJsonFileURI,
//			'"packages/*", "packagesNew/*"' -> '"packages/*"'
//		);
//		joinServerRequests();
//
//		assertNoIssues(); // changes in package.json not saved yet, so still no errors
//
//		saveOpenedFile(packageJsonFileURI);
//		joinServerRequests();
//
//		assertIssues(originalErrors); // back to original errors
	}
}
