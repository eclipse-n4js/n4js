/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Predicate;

/**
 */
// converted from MultiProjectPluginTest
public class MultiProjectIdeTest extends ConvertedIdeTest {

	private static final String PROJECT1_NAME = "multiProjectTest.first";
	private static final String PROJECT2_NAME = "multiProjectTest.second";

	IProject firstProjectUnderTest;
	IProject secondProjectUnderTest;
	IFolder src;
	IFolder src2;
	IFile projectDescriptionFile;
	IFile projectDescriptionFile2;
	Predicate<IMarker> errorMarkerPredicate = marker -> {
		Object value;
		try {
			value = marker.getAttribute(IMarker.SEVERITY);
			if (value instanceof Integer) {
				return IMarker.SEVERITY_ERROR == ((Integer) value).intValue();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	};

	@Test
	public void testFileInSrcWithMissingDepInOtherProject() throws Exception {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								import { D } from "D"
								class C extends D {}
								"""))),
				Pair.of(PROJECT2_NAME, Collections.emptyList()));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")));

		createFile(PROJECT2_NAME, "D", """
					export public class D {}
				""");
		joinServerRequests();
		// Same as above, errors are not resolved by just exporting class, it should be added as a dependency.
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")));

		addSecondProjectToDependencies();
		assertNoIssues();
	}

	// @Test
	// public void testTwoFilesSourceFolderRemovedFromProjectDescriptionFile() throws Exception {
	// val packageJsonOfFirstProject = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON);
	// assertMarkers("project description file (package.json) should have no errors before adding dependency",
	// packageJsonOfFirstProject, 0, errorMarkerPredicate);
	// addSecondProjectToDependencies
	// assertIssues("project description file (package.json) should have 1 error after adding dependency",
	// packageJsonOfFirstProject,
	// "line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules
	// folder. Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
	// val c = createTestFile(
	// src,
	// "C",
	// """
	// import { D } from "D"
	// class C extends D {}
	// """
	// );
	// createTestFile(src2, "D", "export public class D {}");
	// assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
	// removeDependency
	// assertIssues("file should have four errors", c,
	// "line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module
	// found.",
	// "line 2: Couldn't resolve reference to Type 'D'.");
	// }

	@Test
	public void testTwoFilesProjectNewlyCreated() throws Exception {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								import { D } from "D"
								class C extends D {}
								"""))),
				Pair.of(PROJECT2_NAME, Collections.emptyList()));

		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")));

		addProjectToDependencies("thirdProject");
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")),
				Pair.of("multiProjectTest.first/package.json", List.of(
						"(Error, [17:8 - 17:27], Project does not exist with project ID: thirdProject.)")));

		FileURI fileURI = toFileURI(getProjectLocation().toPath().resolve("thirdProject"));
		fileURI.appendSegment("src").toFile().mkdirs();
		Files.createSymbolicLink(
				getRoot().toPath().resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(N4JSGlobals.NODE_MODULES)
						.resolve("thirdProject"),
				fileURI.toPath());
		createFile(fileURI.appendSegment(N4JSGlobals.PACKAGE_JSON), """
				{
					"name": "thirdProject",
					"version": "0.0.1",
					"dependencies": {
						"n4js-runtime": "*"
					},
					"n4js": {
						"projectType": "library",
						"vendorId": "org.eclipse.n4js",
						"vendorName": "Eclipse N4JS Project",
						"output": "src-gen",
						"sources": {
							"source": [
								"src"
							]
						}
					}
				}
				""");
		joinServerRequests();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")));

		createFile(fileURI.appendSegments("src", "D.n4js"), "export public class D {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testProjectDescriptionFileRecreated() throws Exception {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								import { D } from "D"
								class C extends D {}
								"""),
						Pair.of(CFG_DEPENDENCIES, PROJECT2_NAME))),
				Pair.of(PROJECT2_NAME, List.of(
						Pair.of("D", """
								export public class D {}
								"""))));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI packageJsonFile = toFileURI(getPackageJsonFile(PROJECT2_NAME));
		String packageJsonContent = Files.readString(packageJsonFile.toPath());
		deleteFile(packageJsonFile);
		joinServerRequests();
		assertIssues2(
				Pair.of("multiProjectTest.first/package.json", List.of(
						"(Error, [16:3 - 16:32], Project does not exist with project ID: multiProjectTest.second.)")),
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)")));

		createFile(packageJsonFile, packageJsonContent);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testDuplicateModuleInOtherFolder() throws Exception {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								class C1 {}
								"""))),
				Pair.of(PROJECT2_NAME, List.of(
						Pair.of("C", """
								class C2 {}
								"""))));
		startAndWaitForLspServer();
		assertNoIssues();

		addSecondProjectToDependencies();
		assertNoIssues(); // no issues. 'Duplicate Module Issue' occurs only within same project

		removeDependency();
		assertNoIssues();
	}

	@Test
	public void testDuplicateN4JSDInOtherFolder() throws Exception {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								class C {}
								"""))),
				Pair.of(PROJECT2_NAME, List.of(
						Pair.of("C.n4jsd", """
								export external public class C {}
								"""))));
		startAndWaitForLspServer();
		assertNoIssues();

		addSecondProjectToDependencies();
		assertNoIssues(); // no issues. 'Duplicate Module Issue' occurs only within same project

		removeDependency();
		assertNoIssues();
	}

	@Test
	public void testChangeProjectTypeWithoutOpenedEditors() {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of(CFG_DEPENDENCIES, PROJECT2_NAME))),
				Pair.of(PROJECT2_NAME, Collections.emptyList()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile(toFileURI(getPackageJsonFile(PROJECT1_NAME)),
				Pair.of("\"projectType\": \"library\"",
						"\"projectType\": \"library\", \"requiredRuntimeLibraries\": [ \"" + PROJECT2_NAME + "\" ]"));
		joinServerRequests();
		assertIssues2(
				Pair.of(PROJECT1_NAME + "/package.json", List.of(
						"(Warning, [7:58 - 7:83], Project multiProjectTest.second of type library cannot be declared among the required runtime libraries.)")));

		changeNonOpenedFile(toFileURI(getPackageJsonFile(PROJECT2_NAME)),
				Pair.of("\"projectType\": \"library\"", "\"projectType\": \"runtimeLibrary\""));
		joinServerRequests();
		assertIssues2(
				Pair.of(PROJECT2_NAME + "/package.json", List.of(
						"(Warning, [16:3 - 16:21], Project n4js-runtime of type runtime environment cannot be declared among the dependencies or devDependencies.)")));

		changeNonOpenedFile(toFileURI(getPackageJsonFile(PROJECT2_NAME)),
				Pair.of("\"projectType\": \"runtimeLibrary\"", "\"projectType\": \"library\""));
		joinServerRequests();
		assertIssues2(
				Pair.of(PROJECT1_NAME + "/package.json", List.of(
						"(Warning, [7:58 - 7:83], Project multiProjectTest.second of type library cannot be declared among the required runtime libraries.)")));
	}

	@Test
	public void testDeleteExternalFolderValidateProjectDescriptionFileWithoutOpenedEditors() {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT1_NAME, List.of(
						Pair.of("C", """
								class C {}
								"""))));
		startAndWaitForLspServer();
		assertNoIssues();

		// add an external source folder
		changeNonOpenedFile(toFileURI(getPackageJsonFile(PROJECT1_NAME)),
				Pair.of("\"sources\": {", "\"sources\": { \"external\": [ \"ext\" ],"));
		joinServerRequests();
		assertIssues2(
				Pair.of(PROJECT1_NAME + "/package.json", List.of(
						"(Warning, [9:30 - 9:35], Source container path ext does not exist.)")));

		File extFolder = getProjectRoot(PROJECT1_NAME).toPath().resolve("ext").toFile();
		Assert.assertTrue("External folder 'ext' should be missing", !extFolder.exists());

		extFolder.mkdirs();
		sendDidChangeWatchedFiles(FileChangeType.Created, toFileURI(extFolder));
		joinServerRequests();
		Assert.assertTrue("External folder 'ext' should exist", extFolder.exists());
		// TODO GH-2059 should work without #cleanBuildAndWait()
		cleanBuildAndWait();
		assertNoIssues();

		extFolder.delete();
		sendDidChangeWatchedFiles(FileChangeType.Deleted, toFileURI(extFolder));
		joinServerRequests();
		Assert.assertTrue("External folder 'ext' should be missing", !extFolder.exists());
		// TODO GH-2059 should work without #cleanBuildAndWait()
		cleanBuildAndWait();
		assertIssues2(
				Pair.of(PROJECT1_NAME + "/package.json", List.of(
						"(Warning, [9:30 - 9:35], Source container path ext does not exist.)")));
	}

	private void addSecondProjectToDependencies() throws IOException {
		addProjectToDependencies(PROJECT2_NAME);
	}

	private void addProjectToDependencies(String projectName) throws IOException {
		FileURI fileURI = toFileURI(getPackageJsonFile(PROJECT1_NAME));
		PackageJsonUtils.addDependenciesToPackageJsonFile(fileURI.toPath(), Pair.of(projectName, "*"));
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests();
	}

	private void removeDependency() throws IOException {
		FileURI fileURI = toFileURI(getPackageJsonFile(PROJECT1_NAME));
		PackageJsonUtils.removeDependenciesFromPackageJsonFile(fileURI.toPath(), PROJECT2_NAME);
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests();
	}
}
