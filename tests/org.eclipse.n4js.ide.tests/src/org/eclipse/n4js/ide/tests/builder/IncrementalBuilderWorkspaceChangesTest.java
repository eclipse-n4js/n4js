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

import static org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager.YARN_TEST_PROJECT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests incremental builds triggered by changes that lead to a different overall workspace configuration, e.g. projects
 * being added or removed, source folders being added or removed.
 */

public class IncrementalBuilderWorkspaceChangesTest extends AbstractIncrementalBuilderTest {

	private static Map<String, Map<String, String>> testCode_yarnWorkspaceWithTwoProjects = Map.of(
			"MainProject", Map.of(
					"Main", """
								import {OtherClass} from "Other";
								new OtherClass().m();
							""",
					CFG_DEPENDENCIES, """
								OtherProject
							"""),
			"OtherProject", Map.of(
					"Other", """
								export public class OtherClass {
									public m() {}
								}
							"""));

	private static Map<String, List<String>> expectedErrorsWhenOtherProjectIsMissing = Map.of(
			"Main", List.of(
					"(Error, [0:26 - 0:33], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
					"(Error, [1:5 - 1:15], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)"),
			"MainProject/" + PACKAGE_JSON, List.of(
					"(Error, [16:3 - 16:21], Project does not exist with project ID: OtherProject.)"));

	@Test
	public void testCreateProject() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Main", """
							console.log('hello');
						"""));

		File packageJsonFile = getPackageJsonFile();
		FileURI packageJsonFileURI = new FileURI(packageJsonFile);
		String packageJsonContent = Files.readString(packageJsonFile.toPath());
		File outputFile = getOutputFile("Main");

		// before starting the LSP server, delete the package.json file
		assertTrue(packageJsonFile.delete());

		startAndWaitForLspServer();

		assertFalse("output file of module 'Main' should not exist", outputFile.exists());
		assertNoIssues();

		// recreate the package.json file
		packageJsonFile.createNewFile();
		openFile(packageJsonFileURI);
		joinServerRequests();

		changeOpenedFile(packageJsonFileURI, packageJsonContent);
		joinServerRequests();

		assertFalse("output file of module 'Main' should not exist", outputFile.exists()); // package.json not saved yet
		assertNoIssues();

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertTrue("output file of module 'Main' should exist", outputFile.exists()); // output file has appeared
		assertNoIssues();
	}

	@Test
	public void testCreateProjectByDiskChange() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Main", """
							console.log('hello');
						"""));

		File packageJsonFile = getPackageJsonFile();
		FileURI packageJsonFileURI = new FileURI(packageJsonFile);
		String packageJsonContent = Files.readString(packageJsonFile.toPath());
		File outputFile = getOutputFile("Main");

		// before starting the LSP server, delete the package.json file
		assertTrue(packageJsonFile.delete());

		startAndWaitForLspServer();

		assertFalse("output file of module 'Main' should not exist", outputFile.exists());
		assertNoIssues();

		// recreate the package.json file
		packageJsonFile.createNewFile();
		joinServerRequests();

		changeNonOpenedFile(packageJsonFileURI, packageJsonContent);
		joinServerRequests();

		assertTrue("output file of module 'Main' should exist", outputFile.exists()); // output file has appeared
		assertNoIssues();
	}

	@Test
	public void testCreateProject_inYarnWorkspace() throws IOException {
		testWorkspaceManager.createTestOnDisk(testCode_yarnWorkspaceWithTwoProjects);

		File packageJsonFile = getPackageJsonFile("OtherProject");
		FileURI packageJsonFileURI = new FileURI(packageJsonFile);
		String packageJsonContent = Files.readString(packageJsonFile.toPath());
		File outputFile = getOutputFile("OtherProject", "Other");

		// before starting the LSP server, delete the package.json file of OtherProject
		assertTrue(packageJsonFile.delete());

		startAndWaitForLspServer();

		assertFalse("output file of module 'Other' should not exist", outputFile.exists());

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing);

		// recreate the package.json file
		packageJsonFile.createNewFile();
		openFile(packageJsonFileURI);
		joinServerRequests();

		changeOpenedFile(packageJsonFileURI, packageJsonContent);
		joinServerRequests();

		// package.json not saved yet
		assertFalse("output file of module 'Other' should not exist", outputFile.exists());
		// package.json not saved yet, so still the original errors
		assertIssues2(expectedErrorsWhenOtherProjectIsMissing);

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertTrue("output file of module 'Other' should exist", outputFile.exists()); // output file has appeared
		assertNoIssues(); // now the original errors have gone away
	}

	@Test
	public void testDeleteProject_inYarnWorkspace_onlyPackageJson() {
		testWorkspaceManager.createTestOnDisk(testCode_yarnWorkspaceWithTwoProjects);

		startAndWaitForLspServer();
		assertNoIssues();

		File packageJsonFile = getPackageJsonFile("OtherProject");
		deleteNonOpenedFile(toFileURI(packageJsonFile));
		joinServerRequests();

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing);
	}

	@Test
	public void testDeleteProject_inYarnWorkspace_allFiles() {
		testWorkspaceManager.createTestOnDisk(testCode_yarnWorkspaceWithTwoProjects);

		startAndWaitForLspServer();
		assertNoIssues();

		FileURI otherProjectRoot = toFileURI(getProjectRoot("OtherProject"));
		// testing with more URIs in the 'didChangeWatchedFiles' notification than VSCode would send to assert
		// robustness of server
		deleteFolderNotContainingOpenFiles(otherProjectRoot, ".*");
		joinServerRequests();

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing);
	}

	@Test
	public void testChangePackageJson_changeProjectName() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"MainProject", Map.of(
						"Main", """
									class MainClass {};
								""")));
		startAndWaitForLspServer();

		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile("MainProject"));
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"name\": \"MainProject\"", "\"name\": \"RenamedProject\""));
		joinServerRequests();

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues2(Pair.of(
				"MainProject/package.json", List.of(
						"(Warning, [1:9 - 1:25], As a convention the package name \"RenamedProject\" should match the name of the project folder \"MainProject\" on the file system.)")));
	}

	@Test
	public void testChangePackageJson_changeProjectNameWithDepdendencyIn() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"MainProject", Map.of(
						"Main", """
									import {OtherClass} from "Other";
									new OtherClass().m();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								"""),
				"OtherProject", Map.of(
						"Other", """
									export public class OtherClass {
										public m() {}
									}
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile("MainProject"));
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"name\": \"MainProject\"", "\"name\": \"RenamedProject\""));
		joinServerRequests();

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues2(
				Pair.of("MainProject/package.json", List.of(
						"(Warning, [1:9 - 1:25], As a convention the package name \"RenamedProject\" should match the name of the project folder \"MainProject\" on the file system.)")));
	}

	@Test
	public void testChangePackageJson_changeProjectNameWithDepdendencyOut() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"MainProject", Map.of(
						"Main", """
									import {OtherClass} from "Other";
									new OtherClass().m();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								"""),
				"OtherProject", Map.of(
						"Other", """
									export public class OtherClass {
										public m() {}
									}
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		FileURI packageJsonOPFileURI = toFileURI(getPackageJsonFile("OtherProject"));
		openFile(packageJsonOPFileURI);
		changeOpenedFile(packageJsonOPFileURI,
				Pair.of("\"name\": \"OtherProject\"", "\"name\": \"RenamedProject\""));
		joinServerRequests();

		saveOpenedFile(packageJsonOPFileURI);
		joinServerRequests();

		assertIssues2(Map.of(
				"OtherProject/package.json", List.of(
						"    (Warning, [1:9 - 1:25], As a convention the package name \"RenamedProject\" should match the name of the project folder \"OtherProject\" on the file system.)"),
				"MainProject/package.json", List.of(
						"    (Error, [16:3 - 16:21], Project does not exist with project ID: OtherProject.)"),
				"Main.n4js", List.of(
						"    (Error, [0:26 - 0:33], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"    (Error, [1:5 - 1:15], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)")));

		FileURI packageJsonMPFileURI = toFileURI(getPackageJsonFile("MainProject"));
		openFile(packageJsonMPFileURI);
		changeOpenedFile(packageJsonMPFileURI,
				Pair.of("\"OtherProject\": \"\"", "\"RenamedProject\": \"\""));
		joinServerRequests();

		saveOpenedFile(packageJsonMPFileURI);
		joinServerRequests();

		assertIssues2(Map.of(
				"OtherProject/package.json", List.of(
						"(Warning, [1:9 - 1:25], As a convention the package name \"RenamedProject\" should match the name of the project folder \"OtherProject\" on the file system.)")));
	}

	@Test
	public void testChangePackageJson_addRemoveDependency_toN4JSProject() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"MainProject", Map.of(
						"Main", """
									import {OtherClass} from "Other";
									new OtherClass().m();
								"""
				// note: missing the dependency to OtherProject
				),
				"OtherProject", Map.of(
						"Other", """
									export public class OtherClass {
										public m() {}
									}
								""")));
		startAndWaitForLspServer();

		Map<String, List<String>> originalErrors = Map.of(
				"Main", List.of(
						"(Error, [0:26 - 0:33], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:5 - 1:15], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)"));

		doTestAddRemoveDependency(Pair.of("MainProject", "OtherProject"), true, originalErrors);
	}

	@Test
	public void testChangePackageJson_addRemoveDependency_toPlainjsProjectInNodeModules() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "PlainjsProject", Map.of(
						"PlainjsModule.js", """
									export public class OtherClass {
										public m() {}
									}
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
									{
										"name": "PlainjsProject",
										"version": "0.0.1"
									}
								"""),
				"MainProject", Map.of(
						"Main", """
									import * as N+ from "PlainjsModule";
									N.XYZ;
								"""
				// note: missing the dependency to PlainjsProject
				)));
		startAndWaitForLspServer();

		Map<String, List<String>> originalErrors = Map.of(
				"Main", List.of(
						"(Error, [0:21 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"));

		doTestAddRemoveDependency(Pair.of("MainProject", "PlainjsProject"), false, originalErrors);
	}

	@Test
	public void testChangePackageJson_addRemoveDependency_toPlainjsProjectInWorkspace() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"MainProject", Map.of(
						"Main", """
									import * as N+ from "PlainjsModule";
									N.XYZ;
								"""
				// note: missing the dependency to PlainjsProject
				),
				"PlainjsProject", Map.of(
						"PlainjsModule.js", """
									// content is irrelevant
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
									{
										"name": "PlainjsProject",
										"version": "0.0.1"
									}
								""")));
		startAndWaitForLspServer();

		Map<String, List<String>> originalErrors = Map.of(
				"Main", List.of(
						"(Error, [0:21 - 0:36], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"));

		doTestAddRemoveDependency(Pair.of("MainProject", "PlainjsProject"), false, originalErrors);
	}

	/**
	 * @param targetIsN4JSProject
	 *            tells whether the target project of the new dependency is an N4JS project (i.e. has a project type
	 *            other than PLAINJS).
	 */
	private void doTestAddRemoveDependency(Pair<String, String> dependency, boolean targetIsN4JSProject,
			Map<String, List<String>> originalErrors) {

		String sourceProjectName = dependency.getKey();
		String targetProjectName = dependency.getValue();

		assertIssues2(originalErrors);

		// add dependency from source project to target project (in package.json of source project)
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile(sourceProjectName));
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"n4js-runtime\": \"\"", "\"n4js-runtime\": \"\", \"" + targetProjectName + "\": \"\""));
		joinServerRequests();

		if (targetIsN4JSProject) {
			assertIssues2(originalErrors); // changes in package.json not saved yet, so still the original errors
		} else {
			// unfortunately we have an additional error in the open, non-saved package.json file when a dependency to a
			// plain-JS-project is added
			// (due to the optimization in ProjectDiscoveryHelper of hiding all unnecessary PLAINJS projects)
			int tpnLength = 29 + targetProjectName.length();
			Map<String, List<String>> errorsBeforeSaving = new HashMap<>(originalErrors);
			errorsBeforeSaving.put(sourceProjectName + "/" + PACKAGE_JSON, List.of(
					"(Error, [16:23 - 16:" + tpnLength + "], Project does not exist with project ID: "
							+ targetProjectName + ".)"));
			assertIssues2(errorsBeforeSaving); // changes in package.json not saved yet, so still the original errors +
												// 1 error in the unsaved package.json editor
		}

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertNoIssues(); // now the original errors have gone away

		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"n4js-runtime\": \"\", \"" + targetProjectName + "\": \"\"", "\"n4js-runtime\": \"\""));
		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues2(originalErrors); // back to original errors
	}

	@Test
	public void testChangePackageJson_addRemoveSourceFolder() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Main", """
							import {OtherClass} from "Other";
							new OtherClass().m();
						"""));
		startAndWaitForLspServer();

		List<String> originalErrors = List.of(
				"(Error, [0:26 - 0:33], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:5 - 1:15], Couldn't resolve reference to IdentifiableElement 'OtherClass'.)");
		assertIssues2(Pair.of("Main", originalErrors));

		// create a second source folder with a file "Other" fixing Main's error but introducing a new error
		File otherFile = new File(getProjectRoot(), "src2" + File.separator + "Other.n4js");
		otherFile.getParentFile().mkdirs();
		Files.writeString(otherFile.toPath(), """
					export public class OtherClass {
						public m() {}
					}
					let x: number = "bad";
				""");

		cleanBuildAndWait();
		assertIssues2(Pair.of("Main", originalErrors)); // new source folder not registered in package.json yet, so
														// still the original errors

		// register new source folder in package.json
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile());
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"src\"", "\"src\", \"src2\""));
		joinServerRequests();

		assertIssues2(Pair.of("Main", originalErrors)); // changes in package.json not saved yet, so still the original
														// errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		// now the original errors have gone away and a new error shows up
		assertIssues2(Pair.of("Other", List.of("(Error, [3:17 - 3:22], \"bad\" is not a subtype of number.)")));

		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"src\", \"src2\"", "\"src\""));
		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues2(Pair.of("Main", originalErrors)); // back to original errors
	}

	@Test
	public void testChangePackageJson_yarnWorkspacesProperty() throws IOException {
		testWorkspaceManager.createTestOnDisk(testCode_yarnWorkspaceWithTwoProjects);

		// before launching the LSP server, move "OtherProject" into a separate packages-folder
		File otherProjectOldLocation = getProjectRoot("OtherProject");
		File packagesFolder = otherProjectOldLocation.getParentFile();
		File packagesFolderNew = new File(packagesFolder.getParentFile(), "packagesNew");
		File otherProjectNewLocation = new File(packagesFolderNew, "OtherProject");
		otherProjectNewLocation.mkdirs();
		FileCopier.copy(otherProjectOldLocation, otherProjectNewLocation);
		FileDeleter.delete(otherProjectOldLocation);

		startAndWaitForLspServer();

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing);

		// register new packages folder in package.json in yarn workspace root folder
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile(YARN_TEST_PROJECT));
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"packages/*\"", "\"packages/*\", \"packagesNew/*\""));
		joinServerRequests();

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing); // changes in package.json not saved yet, so still the
																// original errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertNoIssues(); // now the original errors have gone away

		changeOpenedFile(packageJsonFileURI,
				Pair.of("\"packages/*\", \"packagesNew/*\"", "\"packages/*\""));
		joinServerRequests();

		assertNoIssues(); // changes in package.json not saved yet, so still no errors

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues2(expectedErrorsWhenOtherProjectIsMissing); // back to original errors
	}
}
