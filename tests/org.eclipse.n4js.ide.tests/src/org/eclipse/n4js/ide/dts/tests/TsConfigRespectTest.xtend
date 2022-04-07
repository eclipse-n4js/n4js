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
package org.eclipse.n4js.ide.dts.tests

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

// Tests:
//  - closure build
//    - direct/indirect/non-included
//    - properties: tsconfig#files, tsconfig#include, tsconfig#include+exclude, package.json#main, package.json#types
//    - import from client by project/direct import
//  - normal build

/**
 * 
 */
class TsConfigRespectTest extends AbstractIdeTest {

	@Test
	def void testNoTsconfig() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.d.ts" -> '''
					export class MyClass {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"version": "0.0.1",
						"main": "index.d.ts"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage";
					MyClass;
					
					export public class XYZ {}
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	def void testEmptyMainTypesFiles() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.d.ts" -> '''
					export class MyClass {
					}
				''',
				"tsconfig.json" -> '''
					{
					    "files": ["index.d.ts"]
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage/index"; // complete import
					MyClass;
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	def void testTsconfigFiles() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.d.ts" -> '''
					// empty
				''',
				"lib.d.ts" -> '''
					export class MyClass {
					}
				''',
				"tsconfig.json" -> '''
					{
					    "files": ["lib.d.ts"]
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage/lib"; // complete import
					MyClass;
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	def void testMissingTsconfigFiles() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.d.ts" -> '''
					// empty
				''',
				"lib.d.ts" -> '''
					export class MyClass {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"main": "index.d.ts",
						"version": "0.0.1"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage/lib"; // complete import
					MyClass;
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertIssuesInModules("client/src/module.n4js" -> #[
			"(Error, [0:24 - 0:39], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)",
			"(Error, [1:0 - 1:7], Couldn't resolve reference to IdentifiableElement 'MyClass'.)"
		]);

		shutdownLspServer();
	}

	@Test
	def void testSameMainAndTsconfigFiles() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.d.ts" -> '''
					export class MyClass {
					}
				''',
				"tsconfig.json" -> '''
					{
					    "files": ["index.d.ts"]
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"version": "0.0.1",
						"main": "index.d.ts"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage";
					MyClass;
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	def void testNonStandardMain() {
		 val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"module.d.ts" -> '''
					export class MyClass {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "@types/mypackage",
						"version": "0.0.1",
						"main": "module.d.ts"
					}
				'''
			],
			CFG_NODE_MODULES + "mypackage" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage";
					MyClass;
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

}
