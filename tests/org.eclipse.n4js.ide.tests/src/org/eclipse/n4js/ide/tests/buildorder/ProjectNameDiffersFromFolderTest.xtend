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
class ProjectNameDiffersFromFolderTest extends AbstractBuildOrderTest {

	@Test
	def void testSingleDependency1() {
		test("yarn-test-project, Not-P1", 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				PACKAGE_JSON -> '''
					{
						"name": "Not-P1",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"sources": {
								"source": [
									"src"
								]
							}
						}
					}
				'''
			]
		);
	}

}
