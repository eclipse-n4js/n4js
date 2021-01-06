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
class BuildOrderImplicitTypeDefinitionsActionTest extends AbstractBuildOrderTest {

	@Test
	def void testRemoveDepToPlainJS() {
		init(
			"P" -> #[
				CFG_DEPENDENCIES -> '''
					JS1
				'''
			],
			"JS1" -> #[
				"package.json" -> '''
					{
						"name": "JS1"
					}
				'''
			],
			"@n4jsd/DEF1" -> #[
				"package.json" -> '''
					{
						"name": "@n4jsd/DEF1",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "JS1"
						},
						"dependencies": {
							"n4js-runtime": ""
						}
					}
				'''
			]
		);
		
		assertBuildOrder("yarn-test-project, n4js-runtime, @n4jsd/DEF1, JS1, P");
		
		changeNonOpenedFile("P/package.json", '''"JS1"''' -> '''JS_UNAVAILABLE''');
		
		assertBuildOrder("yarn-test-project, n4js-runtime, @n4jsd/DEF1, JS1, P");
	}
	

	@Test
	def void testChangeDefinesProperty() {
		init(
			"P" -> #[
				CFG_DEPENDENCIES -> '''
					JS1
				'''
			],
			"JS1" -> #[
				"package.json" -> '''
					{
						"name": "JS1"
					}
				'''
			],
			"@n4jsd/DEF1" -> #[
				"package.json" -> '''
					{
						"name": "@n4jsd/DEF1",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "JS1"
						},
						"dependencies": {
							"n4js-runtime": ""
						}
					}
				'''
			]
		);
		
		assertBuildOrder("yarn-test-project, n4js-runtime, @n4jsd/DEF1, JS1, P");
		
		changeNonOpenedFile("@n4jsd/DEF1/package.json", '''"definesPackage": "JS1"''' -> '''"definesPackage": "JS_UNAVAILABLE"''');
		
		assertBuildOrder("yarn-test-project, n4js-runtime, @n4jsd/DEF1, JS1, P");
	}
//*/	
}
