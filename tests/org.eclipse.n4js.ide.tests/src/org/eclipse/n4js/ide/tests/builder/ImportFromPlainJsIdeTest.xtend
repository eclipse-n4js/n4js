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
package org.eclipse.n4js.ide.tests.builder

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

/**
 * Tests for importing from a plain JS project located in the node_modules folder,
 * with or without type definitions. Also covers:
 * <ul>
 * <li>main modules and project imports,
 * <li>default imports/exports,
 * <li>implicit dependency to the N4JSD definition project
 *     (see {@link #testPlainJS_withImplicitDependencyToTypeDefs_baseCase()}).
 * </ul>
 */
class ImportFromPlainJsIdeTest extends AbstractIdeTest {

	@Test
	def void testPlainJs_noTypeDefs_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".", // <- needed to have 'someModule.js' be placed in the project's root folder!
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1"
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff+ from "npm_package/someModule";
					stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff+ from "npm_package"; // should fail, because npm_package does not have a main module
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(
			"N4jsModuleBad" -> #[
				"(Error, [0:24 - 0:37], Cannot resolve project import: no matching module found.)"
			]
		);
	}

	@Test
	def void testPlainJs_noTypeDefs_withExplicitMainModule_includeJsExtension() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule.js"
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule1" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				''',
				"N4jsModule2" -> '''
					import * as stuff+ from "npm_package/someModule";
					stuff.foo();
				''',
				"N4jsModule3" -> '''
					import * as stuff+ from "npm_package";
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_noTypeDefs_withExplicitMainModule_omitJsExtension() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule"«/* omitting the ".js" file extension here! */»
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule1" -> '''
					import * as stuff+ from "someModule";
					stuff.foo();
				''',
				"N4jsModule2" -> '''
					import * as stuff+ from "npm_package/someModule";
					stuff.foo();
				''',
				"N4jsModule3" -> '''
					import * as stuff+ from "npm_package";
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_noTypeDefs_withImplicitMainModule() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"index.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1"
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule1" -> '''
					import * as stuff+ from "index";
					stuff.foo();
				''',
				"N4jsModule2" -> '''
					import * as stuff+ from "npm_package/index";
					stuff.foo();
				''',
				"N4jsModule3" -> '''
					import * as stuff+ from "npm_package";
					stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_withTypeDefs_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff from "someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff from "npm_package/someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff from "npm_package"; // should fail, because npm_package does not have a main module
					let s: string = stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(
			"N4jsModuleBad" -> #[
				"(Error, [0:23 - 0:36], Cannot resolve project import: no matching module found.)",
				"(Error, [1:22 - 1:25], Couldn't resolve reference to IdentifiableElement 'foo'.)"
			]
		);
	}

	@Test
	def void testPlainJs_withTypeDefs_withExplicitMainModule_definedInN4jsSection() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule.js"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff from "someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff from "npm_package/someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff from "npm_package";
					let s: string = stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_withTypeDefs_withExplicitMainModule_definedOnTopLevel_includeJsExtension() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule.js"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff from "someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff from "npm_package/someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff from "npm_package";
					let s: string = stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_withTypeDefs_withExplicitMainModule_definedOnTopLevel_omitJsExtension() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule"«/* omitting the ".js" file extension here! */»
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "@n4jsd/npm_package",
						"version": "0.0.1",
						"main": "someModule",«/* omitting the ".js" file extension here! */»
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff from "someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff from "npm_package/someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff from "npm_package";
					let s: string = stuff.foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void testPlainJs_withTypeDefs_defaultImportExport_withoutMainModule() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export default external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import foo from "someModule";
					let s: string = foo();
				''',
				"N4jsModuleGood2" -> '''
					import foo from "npm_package/someModule";
					let s: string = foo();
				''',
				"N4jsModuleBad" -> '''
					import foo from "npm_package"; // should fail, because npm_package does not have a main module
					let s: string = foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(
			"N4jsModuleBad" -> #[
				"(Error, [0:16 - 0:29], Cannot resolve project import: no matching module found.)",
				"(Error, [1:16 - 1:19], Couldn't resolve reference to IdentifiableElement 'foo'.)"
			]
		);
	}

	@Test
	def void testPlainJs_withTypeDefs_defaultImportExport_withExplicitMainModule() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule.js"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export default external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import foo from "someModule";
					let s: string = foo();
				''',
				"N4jsModuleGood2" -> '''
					import foo from "npm_package/someModule";
					let s: string = foo();
				''',
				"N4jsModuleBad" -> '''
					import foo from "npm_package";
					let s: string = foo();
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	/**
	 * This tests the case of an implicit dependency from an N4JS project to an N4JSD definition project,
	 * derived from a dependency from the N4JS project to a TypeScript definition project.
	 * <p>
	 * This is a use case common when working with N4JSD definition projects generated from TypeScript definitions.
	 */
	@Test
	def void testPlainJS_withImplicitDependencyToTypeDefs_baseCase() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".", // <- needed to have 'someModule.js' be placed in the project's root folder!
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "@types/npm_package" -> #[
				"someModule.d.ts" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "@types/npm_package",
						"version": "0.0.1"
					}
				'''
			],
			// this is the project generated by the 'n4jsd-generator' tool from the above TypeScript definition project:
			"@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModuleGood1" -> '''
					import * as stuff from "someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleGood2" -> '''
					import * as stuff from "npm_package/someModule";
					let s: string = stuff.foo();
				''',
				"N4jsModuleBad" -> '''
					import * as stuff from "npm_package"; // should fail, because npm_package does not have a main module
					let s: string = stuff.foo();
				''',
				// IMPORTANT: not explicitly defining a dependency to the generated "@n4jsd/npm_package"
				// is the main point of this test:
				CFG_DEPENDENCIES -> '''
					@types/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(
			"N4jsModuleBad" -> #[
				"(Error, [0:23 - 0:36], Cannot resolve project import: no matching module found.)",
				"(Error, [1:22 - 1:25], Couldn't resolve reference to IdentifiableElement 'foo'.)"
			]
		);
	}

	/**
	 * Same as {@link #testPlainJS_withImplicitDependencyToTypeDefs_baseCase()}, but ...
	 * <ol>
	 * <li>the plain JS module is the main module of its containing npm package, and
	 * <li>it exports something with a default export.
	 * </ol>
	 */
	@Test
	def void testPlainJS_withImplicitDependencyToTypeDefs_withMainModuleAndDefaultImportExport() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "npm_package" -> #[
				"someModule.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".", // <- needed to have 'someModule.js' be placed in the project's root folder!
				PACKAGE_JSON -> '''
					{
						"name": "npm_package",
						"version": "0.0.1",
						"main": "someModule.js"
					}
				'''
			],
			CFG_NODE_MODULES + "@types/npm_package" -> #[
				"someModule.d.ts" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "@types/npm_package",
						"version": "0.0.1"
					}
				'''
			],
			"@n4jsd/npm_package" -> #[
				"someModule.n4jsd" -> '''
					export default external public function foo(): string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
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
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule1" -> '''
					import foo from "someModule";
					let s: string = foo();
				''',
				"N4jsModule2" -> '''
					import foo from "npm_package/someModule";
					let s: string = foo();
				''',
				"N4jsModule3" -> '''
					import foo from "npm_package";
					let s: string = foo();
				''',
				CFG_DEPENDENCIES -> '''
					@types/npm_package,
					npm_package
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}
}
