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
import static org.junit.Assert.assertEquals
import org.eclipse.n4js.N4JSGlobals

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

	private static val testData = #[
			CFG_NODE_MODULES + "@types/mypackage" -> #[
				"index.d.ts" -> '''
					export class MyClass {
					}
				''',
				"v2/index.d.ts" -> '''
					export class MyClass2 {
					}
				''',
				"/tsconfig.json" -> '''
					{
					    "files": ["src/index.d.ts"]
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
				"index.js" -> '''
					export class MyClassJS {
					}
				''',
				"v2/index.js" -> '''
					export class MyClass2JS {
					}
				''',
				PACKAGE_JSON -> '''
					{
						"name": "mypackage",
						"version": "0.0.1",
						"main": "src/index.js"
					}
				'''
			],
			"client" -> #[
				"module" -> '''
					import { MyClass } from "mypackage";
				''',
				CFG_DEPENDENCIES -> '''
					mypackage, @types/mypackage
				'''
			]
		];


	@Test
	def void testCreateTSConfigFile() {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

}
