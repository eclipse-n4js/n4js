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
package org.eclipse.n4js.tests.project

import com.google.common.base.Predicate
import java.nio.file.Files
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.IProject
import org.eclipse.lsp4j.FileChangeType
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.utils.JsonUtils
import org.junit.Assert
import org.junit.Test

/**
 */
// converted from MultiProjectPluginTest
class MultiProjectIdeTest extends ConvertedIdeTest {

	private static final String PROJECT1_NAME = "multiProjectTest.first";
	private static final String PROJECT2_NAME = "multiProjectTest.second";

	IProject firstProjectUnderTest
	IProject secondProjectUnderTest
	IFolder src
	IFolder src2
	IFile projectDescriptionFile
	IFile projectDescriptionFile2
	Predicate<IMarker> errorMarkerPredicate = [
		val value = getAttribute(IMarker.SEVERITY);
		if (value instanceof Integer) {
			return IMarker.SEVERITY_ERROR === value.intValue;
		}
		return false;
	]

	@Test
	def void testFileInSrcWithMissingDepInOtherProject() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					import { D } from "D"
					class C extends D {}
				'''
			],
			PROJECT2_NAME -> #[]
		);
		startAndWaitForLspServer();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);

		createFile(PROJECT2_NAME, "D", '''
			export public class D {}
		''');
		joinServerRequests();
		// Same as above, errors are not resolved by just exporting class, it should be added as a dependency.
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);

		addSecondProjectToDependencies();
		assertNoIssues();
	}

//	@Test
//	def void testTwoFilesSourceFolderRemovedFromProjectDescriptionFile() throws Exception {
//		val packageJsonOfFirstProject = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON);
//		assertMarkers("project description file (package.json) should have no errors before adding dependency",
//			packageJsonOfFirstProject, 0, errorMarkerPredicate);
//		addSecondProjectToDependencies
//		assertIssues("project description file (package.json) should have 1 error after adding dependency",
//			packageJsonOfFirstProject,
//			"line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
//		val c = createTestFile(
//			src,
//			"C",
//			'''
//				import { D } from "D"
//				class C extends D {}
//			'''
//		);
//		createTestFile(src2, "D", "export public class D {}");
//		assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
//		removeDependency
//		assertIssues("file should have four errors", c,
//			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
//			"line 2: Couldn't resolve reference to Type 'D'.");
//	}
	
	@Test
	def void testTwoFilesProjectNewlyCreated() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					import { D } from "D"
					class C extends D {}
				'''
			],
			PROJECT2_NAME -> #[]
		);
		startAndWaitForLspServer();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);

		addProjectToDependencies("thirdProject");
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			],
			"multiProjectTest.first/package.json" -> #[
				"(Error, [16:4 - 16:23], Project does not exist with project ID: thirdProject.)"
			]
			
		);

		val fileURI = getProjectLocation().toPath.resolve("thirdProject").toFileURI;
		fileURI.appendSegment("src").toFile.mkdirs();
		Files.createSymbolicLink(
			getRoot().toPath.resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(N4JSGlobals.NODE_MODULES).resolve("thirdProject"),
			fileURI.toPath);
		createFile(fileURI.appendSegment(N4JSGlobals.PACKAGE_JSON), '''
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
		''');
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);

		createFile(fileURI.appendSegments("src", "D.n4js"), "export public class D {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testProjectDescriptionFileRecreated() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					import { D } from "D"
					class C extends D {}
				''',
				CFG_DEPENDENCIES -> PROJECT2_NAME
			],
			PROJECT2_NAME -> #[
				"D" -> '''
					export public class D {}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val packageJsonFile = getPackageJsonFile(PROJECT2_NAME).toFileURI;
		val packageJsonContent = Files.readString(packageJsonFile.toPath)
		deleteFile(packageJsonFile);
		joinServerRequests();
		assertIssues(
			"multiProjectTest.first/package.json" -> #[
				"(Error, [15:3 - 15:33], Project does not exist with project ID: multiProjectTest.second.)"
			],
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);

		createFile(packageJsonFile, packageJsonContent);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testDuplicateModuleInOtherFolder() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					class C1 {}
				'''
			],
			PROJECT2_NAME -> #[
				"C" -> '''
					class C2 {}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		addSecondProjectToDependencies();
		assertDuplicateModuleIssue(PROJECT1_NAME, "C.n4js", PROJECT2_NAME, "C.n4js");

		removeDependency();
		assertNoIssues();
	}

	@Test
	def void testDuplicateN4JSDInOtherFolder() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					class C {}
				'''
			],
			PROJECT2_NAME -> #[
				"C.n4jsd" -> '''
					export external public class C {}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		addSecondProjectToDependencies();
		assertDuplicateModuleIssue(PROJECT1_NAME, "C.n4js", PROJECT2_NAME, "C.n4jsd");

		removeDependency();
		assertNoIssues();
	}

	@Test
	def void testChangeProjectTypeWithoutOpenedEditors() {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				CFG_DEPENDENCIES -> PROJECT2_NAME
			],
			PROJECT2_NAME -> #[]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile(getPackageJsonFile(PROJECT1_NAME).toFileURI,
			'''"projectType": "library"''' -> '''"projectType": "library", "requiredRuntimeLibraries": [ "«PROJECT2_NAME»" ]''');
		joinServerRequests();
		assertIssues(
			PROJECT1_NAME + "/package.json" -> #[
				"(Warning, [6:58 - 6:83], Project multiProjectTest.second of type library cannot be declared among the required runtime libraries.)"
			]
		);

		changeNonOpenedFile(getPackageJsonFile(PROJECT2_NAME).toFileURI,
			'''"projectType": "library"''' -> '''"projectType": "runtimeLibrary"''');
		joinServerRequests();
		assertIssues(
			PROJECT2_NAME + "/package.json" -> #[
				"(Warning, [15:3 - 15:22], Project n4js-runtime of type runtime environment cannot be declared among the dependencies or devDependencies.)"
			]
		);

		changeNonOpenedFile(getPackageJsonFile(PROJECT2_NAME).toFileURI,
			'''"projectType": "runtimeLibrary"''' -> '''"projectType": "library"''');
		joinServerRequests();
		assertIssues(
			PROJECT1_NAME + "/package.json" -> #[
				"(Warning, [6:58 - 6:83], Project multiProjectTest.second of type library cannot be declared among the required runtime libraries.)"
			]
		);
	}

	@Test
	def void testDeleteExternalFolderValidateProjectDescriptionFileWithoutOpenedEditors() {
		testWorkspaceManager.createTestOnDisk(
			PROJECT1_NAME -> #[
				"C" -> '''
					class C {}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// add an external source folder
		changeNonOpenedFile(getPackageJsonFile(PROJECT1_NAME).toFileURI,
			'''"sources": {''' -> '''"sources": { "external": [ "ext" ],''');
		joinServerRequests();
		assertIssues(
			PROJECT1_NAME + "/package.json" -> #[
				"(Warning, [8:30 - 8:35], Source container path ext does not exist.)"
			]
		);

		val extFolder = getProjectRoot(PROJECT1_NAME).toPath.resolve("ext").toFile;
		Assert.assertTrue('External folder \'ext\' should be missing', !extFolder.exists);

		extFolder.mkdirs();
		sendDidChangeWatchedFiles(FileChangeType.Created, extFolder.toFileURI);
		joinServerRequests();
		Assert.assertTrue('External folder \'ext\' should exist', extFolder.exists);
// TODO GH-2059 should work without #cleanBuildAndWait()
cleanBuildAndWait();
		assertNoIssues();

		extFolder.delete();
		sendDidChangeWatchedFiles(FileChangeType.Deleted, extFolder.toFileURI);
		joinServerRequests();
		Assert.assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
// TODO GH-2059 should work without #cleanBuildAndWait()
cleanBuildAndWait();
		assertIssues(
			PROJECT1_NAME + "/package.json" -> #[
				"(Warning, [8:30 - 8:35], Source container path ext does not exist.)"
			]
		);
	}

	def private void addSecondProjectToDependencies() {
		addProjectToDependencies(PROJECT2_NAME);
	}

	def private void addProjectToDependencies(String projectName) {
		val fileURI = getPackageJsonFile(PROJECT1_NAME).toFileURI;
		JsonUtils.addDependenciesToPackageJsonFile(fileURI.toPath, projectName -> "*");
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests();
	}

	def private void removeDependency() {
		val fileURI = getPackageJsonFile(PROJECT1_NAME).toFileURI;
		JsonUtils.removeDependenciesFromPackageJsonFile(fileURI.toPath, PROJECT2_NAME);
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests();
	}

	def private void assertDuplicateModuleIssue(String projectName, String moduleNameWithExt, String duplicateProjectName, String duplicateModuleNameWithExt) {
		val issues = getIssues();
		Assert.assertEquals("exactly 1 issue expected", 1, issues.size);
		Assert.assertTrue("issue located in unexpected file", issues.keys.head.toString.endsWith("/" + projectName + "/src/" + moduleNameWithExt));
		val msg = issues.values.head.message;
		Assert.assertTrue("unexpected issue message: " + msg, msg.startsWith("A duplicate module C is also defined in ")
			&& msg.endsWith("/" + duplicateProjectName + "/src/" + duplicateModuleNameWithExt + "."));
	}
}
