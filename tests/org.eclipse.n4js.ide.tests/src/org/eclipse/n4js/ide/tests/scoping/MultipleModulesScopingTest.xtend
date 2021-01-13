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
package org.eclipse.n4js.ide.tests.scoping

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

/**
 * Tests for building yarn workspaces, in particular certain corner and special cases
 * such as "yarn link".
 */
class MultipleModulesScopingTest extends AbstractIdeTest {

	@Test
	def void testTwoN4jsModulesWithSameName() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			"Project" -> #[
				"Client" -> '''
					import {C_P1} from "Module";
				''',
				CFG_DEPENDENCIES -> '''
					P1,
					P2
				'''
			],
			"P1" -> #[
				"Module" -> '''
					export public class C_P1 {
						b : string
					}
				'''
			],
			"P2" -> #[
				"Module" -> '''
					export public class C_P2 {
						b : string
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues("Client" -> #["(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: P1/Module, P2/Module.)"]);
	}

	@Test
	def void testN4jsdAndJsModulesWithSameNameInDifferentProjects() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			"Project" -> #[
				"Client" -> '''
					import {C_P1} from "Module";
				''',
				CFG_DEPENDENCIES -> '''
					P1,
					PLAINJS
				'''
			],
			"P1" -> #[
				"Module.n4jsd" -> '''
					export external public class C_P1 {
						b : string
					}
				'''
			],
			"PLAINJS" -> #[
				CFG_SOURCE_FOLDER -> ".",
				"Module.js" -> '''
					export const C = 2;
				''',
				"package.json" -> '''
					{
						"name": "PLAINJS"
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues("Client" -> #["(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: P1/Module, PLAINJS/Module.)"]);
	}

	@Test
	def void testN4jsAndJsModulesWithSameNameInSameProjectError() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			#[
				"Client" -> '''
					import * as M from "Module";
					M;
				''',
				"Module" -> '''
					export const C = 1;
				''',
				"/src-js/Module.js" -> '''
					export const C = 2;
				''',
				"package.json" -> '''
					{
					    "name": "test-project",
					    "n4js": {
					        "projectType": "library",
					        "output": "src-gen",
					        "sources": {
					            "source": [
					                "src"
					            ],
								"external": [
									"src-js"
								]
					        }
					    },
					    "dependencies": {
					        "n4js-runtime": ""
					    }
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues("Client" -> #["(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-project/Module, test-project/Module.)"]);
	}

	@Test
	def void testN4jsdAndJsModulesWithSameNameInSameProjectOK() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			#[
				"Client" -> '''
					import * as M from "Module";
					M;
				''',
				"Module.n4jsd" -> '''
					export external const C;
				''',
				"/src-js/Module.js" -> '''
					export const C = 2;
				''',
				"package.json" -> '''
					{
					    "name": "test-project",
					    "n4js": {
					        "projectType": "library",
					        "output": "src-gen",
					        "sources": {
					            "source": [
					                "src"
					            ],
								"external": [
									"src-js"
								]
					        }
					    },
					    "dependencies": {
					        "n4js-runtime": ""
					    }
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

}
