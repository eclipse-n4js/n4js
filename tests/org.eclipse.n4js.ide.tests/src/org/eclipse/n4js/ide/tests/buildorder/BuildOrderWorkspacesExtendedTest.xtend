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
class BuildOrderWorkspacesExtendedTest extends AbstractBuildOrderTest {

	@Test
	def void testWorkspacesObject() {
		test("yarn-test-project, n4js-runtime, P1, P2", 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			],
			CFG_YARN_PROJECT -> #[
				PACKAGE_JSON -> '''
					{
						"private" : true,
						"workspaces" : {
							"packages" : ["packages/*"]
						}
					}
				'''
			]
		);
	}

}
