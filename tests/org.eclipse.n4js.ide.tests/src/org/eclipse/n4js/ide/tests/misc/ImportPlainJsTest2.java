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
package org.eclipse.n4js.ide.tests.misc;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for importing from plain-JS modules.
 * <p>
 * This test class covers the base cases (including extensions ".cjs" and ".mjs"); special cases are covered in separate
 * test classes.
 */
@SuppressWarnings("javadoc")
public class ImportPlainJsTest2 extends AbstractIdeTest {

	@Test
	public void testInSourceFolder_extJS() {
		doTestInSourceFolder("js");
	}

	@Test
	public void testInSourceFolder_extCJS() {
		doTestInSourceFolder("cjs");
	}

	@Test
	public void testInSourceFolder_extMJS() {
		doTestInSourceFolder("mjs");
	}

	private void doTestInSourceFolder(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"N4jsProject", Map.of(
						"someModule." + fileExtension, """
								// content does not matter
								""",
						"N4jsModule", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testInExtFolder_extJS() {
		doTestInExternalFolder("js");
	}

	@Test
	public void testInExtFolder_extCJS() {
		doTestInExternalFolder("cjs");
	}

	@Test
	public void testInExtFolder_extMJS() {
		doTestInExternalFolder("mjs");
	}

	private void doTestInExternalFolder(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"N4jsProject", Map.of(
						"../src-ext/someModule." + fileExtension, """
								// content does not matter
								""",
						"N4jsModule", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""",
						PACKAGE_JSON, """
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
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testInNodeModules_extJS() {
		doTestInNodeModules("js");
	}

	@Test
	public void testInNodeModules_extCJS() {
		doTestInNodeModules("cjs");
	}

	@Test
	public void testInNodeModules_extMJS() {
		doTestInNodeModules("mjs");
	}

	private void doTestInNodeModules(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "somePackage", Map.of(
						"someModule." + fileExtension, """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".", // <- needed to have 'someModule.js' be placed in the project's root
												// folder!
						PACKAGE_JSON, """
								{
									"name": "somePackage",
									"version": "0.0.1"
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModule1", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""",
						"N4jsModule2", """
								import * as stuff+ from "somePackage/someModule";
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								somePackage
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testWithN4jsdFileSameProject_extJS() {
		doTestWithN4jsdFileSameProject("js");
	}

	@Test
	public void testWithN4jsdFileSameProject_extCJS() {
		doTestWithN4jsdFileSameProject("cjs");
	}

	@Test
	public void testWithN4jsdFileSameProject_extMJS() {
		doTestWithN4jsdFileSameProject("mjs");
	}

	private void doTestWithN4jsdFileSameProject(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"N4jsProject", Map.of(
						"../src-ext/someModule." + fileExtension, """
								// content does not matter
								""",
						"someModule.n4jsd", """
								export external function foo();
								""",
						"N4jsModule", """
								import * as stuff from "someModule";
								stuff.foo();
								""",
						PACKAGE_JSON, """
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
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(fileExtension, false);
	}

	@Test
	public void testWithN4jsdFileSeparateProjects_extJS() {
		doTestWithN4jsdFileSeparateProjects("js");
	}

	@Test
	public void testWithN4jsdFileSeparateProjects_extCJS() {
		doTestWithN4jsdFileSeparateProjects("cjs");
	}

	@Test
	public void testWithN4jsdFileSeparateProjects_extMJS() {
		doTestWithN4jsdFileSeparateProjects("mjs");
	}

	private void doTestWithN4jsdFileSeparateProjects(String fileExtension) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "somePackage", Map.of(
						"someModule." + fileExtension, """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".", // <- needed to have 'someModule.js' be placed in the project's root
												// folder!
						PACKAGE_JSON, """
								{
									"name": "somePackage",
									"version": "0.0.1"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/somePackage", Map.of(
						"someModule.n4jsd", """
								export external public function foo();
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
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
								"""),
				"N4jsProject", Map.of(
						"N4jsModule", """
								import * as stuff from "someModule";
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/somePackage,
								somePackage
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(fileExtension, true);
	}

	@Test
	public void testFileExtensionConflict_jsVsCjs() {
		doTestFileExtensionConflict(List.of("js", "cjs"), false, "cjs");
	}

	@Test
	public void testFileExtensionConflict_jsVsMjs() {
		doTestFileExtensionConflict(List.of("js", "mjs"), false, "mjs");
	}

	@Test
	public void testFileExtensionConflict_cjsVsMjs() {
		doTestFileExtensionConflict(List.of("cjs", "mjs"), false, "mjs");
	}

	@Test
	public void testFileExtensionConflict_withN4jsd_jsVsCjs() {
		doTestFileExtensionConflict(List.of("js", "cjs"), true, "cjs");
	}

	@Test
	public void testFileExtensionConflict_withN4jsd_jsVsMjs() {
		doTestFileExtensionConflict(List.of("js", "mjs"), true, "mjs");
	}

	@Test
	public void testFileExtensionConflict_withN4jsd_cjsVsMjs() {
		doTestFileExtensionConflict(List.of("cjs", "mjs"), true, "mjs");
	}

	private void doTestFileExtensionConflict(List<String> fileExtensionsOnDisk, boolean withN4jsd,
			String expectedFileExtensionInOutputCode) {
		Map<String, String> config = new HashMap<>();

		for (String fileExt : fileExtensionsOnDisk) {
			config.put("someModule." + fileExt, """
					// content does not matter
					""");
		}
		if (withN4jsd) {
			config.put("someModule.n4jsd", """
					export external function foo();
					""");
		}
		String withN4jsdStr = withN4jsd ? "" : "+";
		config.put("N4jsModule", """
				import * as stuff%s from "someModule";
				stuff.foo();
				""".formatted(withN4jsdStr));
		testWorkspaceManager.createTestOnDisk(Map.of("N4jsProject", config));
		startAndWaitForLspServer();
		assertNoIssues();

		assertPlainJsFileExtensionInOutputFile(expectedFileExtensionInOutputCode, false);
	}

	/**
	 * Ensure the generated file contains the correct file extension for the .[c|m]js file being imported, even though
	 * that file was hidden behind an .n4jsd file.
	 */
	private void assertPlainJsFileExtensionInOutputFile(String expectedFileExtension, boolean isProjectImport) {
		String outputCode;
		try {
			outputCode = Files.readString(getOutputFile("N4jsProject", "N4jsModule").toPath());
		} catch (Exception e) {
			throw new AssertionError("exception while reading generated output file", e);
		}
		String substringToSearch = " from '" + (isProjectImport ? "somePackage/" : "./") + "someModule."
				+ expectedFileExtension + "'";
		Assert.assertTrue(
				"output file of module N4jsModule.n4js did not contain expected substring \"" + substringToSearch
						+ "\":\n"
						+ "========\n"
						+ outputCode + "\n"
						+ "========",
				outputCode.contains(substringToSearch));
	}
}
