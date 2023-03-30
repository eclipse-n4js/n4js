/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.misc;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests for importing from plain-JS modules.
 * <p>
 * This test class covers main modules and project imports. Also covers default imports/exports.
 */
@SuppressWarnings("javadoc")
public class ImportPlainJsMainModuleTest extends AbstractIdeTest {

	@Test
	public void testPlainJs_noTypeDefs_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".", // <- needed to have 'someModule.js' be placed in the project's root
												// folder!
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1"
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModuleGood1", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""",
						"N4jsModuleGood2", """
								import * as stuff+ from "npm_package/someModule";
								stuff.foo();
								""",
						"N4jsModuleBad",
						"""
								import * as stuff+ from "npm_package"; // should fail, because npm_package does not have a main module
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								npm_package
								""")));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("N4jsModuleBad", List.of(
						"(Error, [0:24 - 0:37], Cannot resolve project import: no matching module found.)")));
	}

	@Test
	public void testPlainJs_noTypeDefs_withExplicitMainModule_includeJsExtension() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule.js"
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModule1", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""",
						"N4jsModule2", """
								import * as stuff+ from "npm_package/someModule";
								stuff.foo();
								""",
						"N4jsModule3", """
								import * as stuff+ from "npm_package";
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_noTypeDefs_withExplicitMainModule_omitJsExtension() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",

						/* omitting the ".js" file extension here! */
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule"
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModule1", """
								import * as stuff+ from "someModule";
								stuff.foo();
								""",
						"N4jsModule2", """
								import * as stuff+ from "npm_package/someModule";
								stuff.foo();
								""",
						"N4jsModule3", """
								import * as stuff+ from "npm_package";
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_noTypeDefs_withImplicitMainModule() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"index.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1"
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModule1", """
								import * as stuff+ from "index";
								stuff.foo();
								""",
						"N4jsModule2", """
								import * as stuff+ from "npm_package/index";
								stuff.foo();
								""",
						"N4jsModule3", """
								import * as stuff+ from "npm_package";
								stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_withTypeDefs_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
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
						"N4jsModuleGood1", """
								import * as stuff from "someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleGood2", """
								import * as stuff from "npm_package/someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleBad",
						"""
								import * as stuff from "npm_package"; // should fail, because npm_package does not have a main module
								let s: string = stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("N4jsModuleBad", List.of(
						"(Error, [0:23 - 0:36], Cannot resolve project import: no matching module found.)",
						"(Error, [1:22 - 1:25], Couldn't resolve reference to IdentifiableElement 'foo'.)")));
	}

	@Test
	public void testPlainJs_withTypeDefs_withExplicitMainModule_definedInN4jsSection() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule.js"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
										"vendorId": "org.eclipse.n4js",
										"sources": {
											"source": [
												"."
											]
										},
										"mainModule": "someModule"
									}
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModuleGood1", """
								import * as stuff from "someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleGood2", """
								import * as stuff from "npm_package/someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleBad", """
								import * as stuff from "npm_package";
								let s: string = stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_withTypeDefs_withExplicitMainModule_definedOnTopLevel_includeJsExtension() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule.js"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"main": "someModule.js",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
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
						"N4jsModuleGood1", """
								import * as stuff from "someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleGood2", """
								import * as stuff from "npm_package/someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleBad", """
								import * as stuff from "npm_package";
								let s: string = stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_withTypeDefs_withExplicitMainModule_definedOnTopLevel_omitJsExtension() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",

						/* omitting the ".js" file extension here! */
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",

						/* omitting the ".js" file extension here! */
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"main": "someModule",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
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
						"N4jsModuleGood1", """
								import * as stuff from "someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleGood2", """
								import * as stuff from "npm_package/someModule";
								let s: string = stuff.foo();
								""",
						"N4jsModuleBad", """
								import * as stuff from "npm_package";
								let s: string = stuff.foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testPlainJs_withTypeDefs_defaultImportExport_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export default external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
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
						"N4jsModuleGood1", """
								import foo from "someModule";
								let s: string = foo();
								""",
						"N4jsModuleGood2", """
								import foo from "npm_package/someModule";
								let s: string = foo();
								""",
						"N4jsModuleBad",
						"""
								import foo from "npm_package"; // should fail, because npm_package does not have a main module
								let s: string = foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("N4jsModuleBad", List.of(
						"(Error, [0:16 - 0:29], Cannot resolve project import: no matching module found.)",
						"(Error, [1:16 - 1:19], Couldn't resolve reference to IdentifiableElement 'foo'.)")));
	}

	@Test
	public void testPlainJs_withTypeDefs_defaultImportExport_withExplicitMainModule() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "npm_package", Map.of(
						"someModule.js", """
								// content does not matter
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "npm_package",
									"version": "0.0.1",
									"main": "someModule.js"
								}
								"""),
				CFG_NODE_MODULES + "@n4jsd/npm_package", Map.of(
						"someModule.n4jsd", """
								export default external public function foo(): string;
								""",
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
								{
									"name": "@n4jsd/npm_package",
									"version": "0.0.1",
									"n4js": {
										"projectType": "definition",
										"definesPackage": "npm_package",
										"vendorId": "org.eclipse.n4js",
										"sources": {
											"source": [
												"."
											]
										},
										"mainModule": "someModule"
									}
								}
								"""),
				"N4jsProject", Map.of(
						"N4jsModuleGood1", """
								import foo from "someModule";
								let s: string = foo();
								""",
						"N4jsModuleGood2", """
								import foo from "npm_package/someModule";
								let s: string = foo();
								""",
						"N4jsModuleBad", """
								import foo from "npm_package";
								let s: string = foo();
								""",
						CFG_DEPENDENCIES, """
								@n4jsd/npm_package,
								npm_package
								""")));
		startAndWaitForLspServer();
		assertNoIssues();
	}
}
