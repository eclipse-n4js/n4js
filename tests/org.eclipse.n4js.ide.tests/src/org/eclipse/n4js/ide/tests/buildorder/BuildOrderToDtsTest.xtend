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
 * Test for build order
 */
class BuildOrderToDtsTest extends AbstractBuildOrderTest {

	@Test
	def void testDepsToPlainJS_A() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
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
							PlainJS2": "*"
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
	
	@Test
	def void testDepsToPlainJS_Def() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
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
				"index.d.ts" -> '''
					// dummy
				''',
				"package.json" -> '''
					{
						"name": "PlainJS1",
						"main": "index.js",
						"type": "module",
						"dependencies": {
							PlainJS2": "*"
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
	
	@Test
	def void testDepsToPlainJS_DefToDef() {
		test("yarn-test-project, " + 
				"yarn-test-project/node_modules/n4js-runtime, " + 
				"yarn-test-project/packages/PlainJS2, " + 
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
			]
		);
	}
	
	
}
