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
package org.eclipse.n4js.ide.tests.buildorder

import org.junit.Test

/**
 * Test for build order.
 * These tests assure that dependencies of plain-js projects are not ignored when computing
 * the build order if they also are dependencies of non-plain-js projects (e.g. n4js projects)
 */
class BuildOrderToDtsTest extends AbstractBuildOrderTest {

	@Test
	def void testNmfDepsToPlainJS_A() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/node_modules/PlainJS1, " +  // PlainJS2 is ignored
				"yarn-test-project/packages/MyProject",
 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			CFG_NODE_MODULES + "PlainJS1" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							"PlainJS2": "*"
						}
					}
				'''
			],
			CFG_NODE_MODULES + "PlainJS2" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS2",
						"main": "index.js",
						"type": "module"
					}
				'''
			],
			
			"MyProject" -> #[
				// missing dependency to PlainJS2, hence it is ignored
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					PlainJS1
				'''
			]
		);
	}

	@Test
	def void testNmfDepsToPlainJS_Def() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/node_modules/PlainJS2, " +  // PlainJS2 not ignored
				"yarn-test-project/node_modules/PlainJS1, " + 
				"yarn-test-project/packages/MyProject",
 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			CFG_NODE_MODULES + "PlainJS1" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							"PlainJS2": "*"
						}
					}
				'''
			],
			CFG_NODE_MODULES + "PlainJS2" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS2",
						"main": "index.js",
						"type": "module"
					}
				'''
			],
			
			"MyProject" -> #[
				 // add dependency to PlainJS2
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					PlainJS1,
					PlainJS2
				'''
			]
		);
	}

	@Test
	def void testPackagesDepsToPlainJS_A() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/node_modules/PlainJS2, " +  // PlainJS2 not ignored
				"yarn-test-project/packages/PlainJS1, " + 
				"yarn-test-project/packages/MyProject",
 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			
			"MyProject" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					PlainJS1
				'''
			],
			"PlainJS1" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							"PlainJS2": "*"
						}
					}
				'''
			],
			// not ignored because in packages folder
			"PlainJS2" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS2",
						"main": "index.js",
						"type": "module"
					}
				'''
			]
		);
	}
	
	@Test
	def void testPackagesDepsToPlainJS_Def() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/packages/PlainJS2, " +  // PlainJS2 not ignored
				"yarn-test-project/packages/PlainJS1, " + 
				"yarn-test-project/packages/MyProject",
 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			
			"MyProject" -> #[
				 // add dependency to PlainJS2
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					PlainJS1,
					PlainJS2
				'''
			],
			"PlainJS1" -> #[
				"index.js" -> '''
					// dummy
				''',
				"index.d.ts" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							"PlainJS2": "*"
						}
					}
				'''
			],
			"PlainJS2" -> #[
				"index.js" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS2",
						"main": "index.js",
						"type": "module"
					}
				'''
			]
		);
	}
	
	/**
	 * actually this test is redundant to the ones before. However, it defines the usecase of
	 * interest since here, PlainJS1 has a type dependency and re-export of types in PlainJS2.
	 */
	@Test
	def void testNmfDepsToPlainJS_DefToDef() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/node_modules/PlainJS2, " +  // PlainJS2 not ignored
				"yarn-test-project/node_modules/PlainJS1, " + 
				"yarn-test-project/packages/MyProject",
 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			CFG_NODE_MODULES + "PlainJS1" -> #[
				"index.js" -> '''
					// dummy
				''',
				"index.d.ts" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							"PlainJS2": "*"
						}
					}
				'''
			],
			CFG_NODE_MODULES + "PlainJS2" -> #[
				"index.js" -> '''
					// dummy
				''',
				// interesting type information re-exported in PlainJS1
				"index.d.ts" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS2",
						"main": "index.js",
						"type": "module"
					}
				'''
			],
			
			"MyProject" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					PlainJS1,
					PlainJS2
				'''
			]
		);
	}
	
	
}
