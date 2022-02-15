/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import java.nio.file.Files
import java.util.List
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Assert
import org.junit.Test

/**
 * Tests for importing from plain-JS modules.
 * <p>
 * This test class covers the base cases (including extensions ".cjs" and ".mjs");
 * special cases are covered in separate test classes.
 */
class ImportFromPlainJsIdeTest extends AbstractIdeTest {

	@Test
	def void testInSourceFolder_extJS() {
		doTestInSourceFolder("js");
	}

	@Test
	def void testInSourceFolder_extCJS() {
		doTestInSourceFolder("cjs");
	}

	@Test
	def void testInSourceFolder_extMJS() {
		doTestInSourceFolder("mjs");
	}

	def private void doTestInSourceFolder(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(
			"N4jsProject" -> #[
				"someModule." + fileExtension -> '''
					// content does not matter
				''',
				"N4jsModule" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testInExtFolder_extJS() {
		doTestInExternalFolder("js")
	}

	@Test
	def void testInExtFolder_extCJS() {
		doTestInExternalFolder("cjs")
	}

	@Test
	def void testInExtFolder_extMJS() {
		doTestInExternalFolder("mjs")
	}

	def private void doTestInExternalFolder(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(
			"N4jsProject" -> #[
				"../src-ext/someModule." + fileExtension -> '''
					// content does not matter
				''',
				"N4jsModule" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				''',
				PACKAGE_JSON -> '''
					{
						"name": "N4jsProject",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"vendorId": "org.eclipse.n4js",
							"sources": {
								"source": [
									"src"
								],
								"external": [
									"src-ext"
								]
							},
							"output": "src-gen"
						},
						"dependencies": {
							"n4js-runtime": "*"
						}
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testInNodeModules_extJS() {
		doTestInNodeModules("js");
	}

	@Test
	def void testInNodeModules_extCJS() {
		doTestInNodeModules("cjs");
	}

	@Test
	def void testInNodeModules_extMJS() {
		doTestInNodeModules("mjs");
	}

	def private void doTestInNodeModules(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "somePackage" -> #[
				"someModule." + fileExtension -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".", // <- needed to have 'someModule.js' be placed in the project's root folder!
				PACKAGE_JSON -> '''
					{
						"name": "somePackage",
						"version": "0.0.1"
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule1" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				''',
				"N4jsModule2" -> '''
					import * as stuff+ from "somePackage/someModule";
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					somePackage
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testWithN4jsdFileSameProject_extJS() {
		doTestWithN4jsdFileSameProject("js");
	}

	@Test
	def void testWithN4jsdFileSameProject_extCJS() {
		doTestWithN4jsdFileSameProject("cjs");
	}

	@Test
	def void testWithN4jsdFileSameProject_extMJS() {
		doTestWithN4jsdFileSameProject("mjs");
	}

	def private void doTestWithN4jsdFileSameProject(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(
			"N4jsProject" -> #[
				"../src-ext/someModule." + fileExtension -> '''
					// content does not matter
				''',
				"someModule.n4jsd" -> '''
					export external function foo();
				''',
				"N4jsModule" -> '''
					import * as stuff from "someModule";
					stuff.foo();
				''',
				PACKAGE_JSON -> '''
					{
						"name": "N4jsProject",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"vendorId": "org.eclipse.n4js",
							"sources": {
								"source": [
									"src"
								],
								"external": [
									"src-ext"
								]
							},
							"output": "src-gen"
						},
						"dependencies": {
							"n4js-runtime": "*"
						}
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(fileExtension, false);
	}

	@Test
	def void testWithN4jsdFileSeparateProjects_extJS() {
		doTestWithN4jsdFileSeparateProjects("js");
	}

	@Test
	def void testWithN4jsdFileSeparateProjects_extCJS() {
		doTestWithN4jsdFileSeparateProjects("cjs");
	}

	@Test
	def void testWithN4jsdFileSeparateProjects_extMJS() {
		doTestWithN4jsdFileSeparateProjects("mjs");
	}

	def private void doTestWithN4jsdFileSeparateProjects(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "somePackage" -> #[
				"someModule." + fileExtension -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".", // <- needed to have 'someModule.js' be placed in the project's root folder!
				PACKAGE_JSON -> '''
					{
						"name": "somePackage",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/somePackage" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo();
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "@n4jsd/somePackage",
						"version": "0.0.1",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "somePackage",
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
			"N4jsProject" -> #[
				"N4jsModule" -> '''
					import * as stuff from "someModule";
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/somePackage,
					somePackage
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(fileExtension, true);
	}

	@Test
	def void testFileExtensionConflict_jsVsCjs() {
		doTestFileExtensionConflict(#["js", "cjs"], false, "cjs");
	}

	@Test
	def void testFileExtensionConflict_jsVsMjs() {
		doTestFileExtensionConflict(#["js", "mjs"], false, "mjs");
	}

	@Test
	def void testFileExtensionConflict_cjsVsMjs() {
		doTestFileExtensionConflict(#["cjs", "mjs"], false, "mjs");
	}

	@Test
	def void testFileExtensionConflict_withN4jsd_jsVsCjs() {
		doTestFileExtensionConflict(#["js", "cjs"], true, "cjs");
	}

	@Test
	def void testFileExtensionConflict_withN4jsd_jsVsMjs() {
		doTestFileExtensionConflict(#["js", "mjs"], true, "mjs");
	}

	@Test
	def void testFileExtensionConflict_withN4jsd_cjsVsMjs() {
		doTestFileExtensionConflict(#["cjs", "mjs"], true, "mjs");
	}

	def private void doTestFileExtensionConflict(String[] fileExtensionsOnDisk, boolean withN4jsd, String expectedFileExtensionInOutputCode) {
		val List<Pair<String, ? extends CharSequence>> config = newArrayList();
		config += fileExtensionsOnDisk.map[fileExt|
			"someModule." + fileExt -> '''
				// content does not matter
			'''
		];
		if (withN4jsd) {
			config += "someModule.n4jsd" -> '''
				export external function foo();
			''';
		}
		config += "N4jsModule" -> '''
			import * as stuff«if (withN4jsd) "" else "+"» from "someModule";
			stuff.foo();
		''';
		testWorkspaceManager.createTestOnDisk(
			"N4jsProject" -> config
		);
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(expectedFileExtensionInOutputCode, false);
	}

	/**
	 * Ensure the generated file contains the correct file extension for the .[c|m]js file being imported,
	 * even though that file was hidden behind an .n4jsd file.
	 */
	def private void assertPlainJsFileExtensionInOutputFile(String expectedFileExtension, boolean isProjectImport) {
		var String outputCode;
		try {
			outputCode = Files.readString(getOutputFile("N4jsProject", "N4jsModule").toPath);
		} catch (Exception e) {
			throw new AssertionError("exception while reading generated output file", e);
		}
		val substringToSearch = " from '" + (if (isProjectImport) "somePackage/" else "./") + "someModule." + expectedFileExtension + "'";
		Assert.assertTrue("output file of module N4jsModule.n4js did not contain expected substring \"" + substringToSearch + "\":\n"
			+ "========\n"
			+ outputCode + "\n"
			+ "========",
			outputCode.contains(substringToSearch));
	}
}
