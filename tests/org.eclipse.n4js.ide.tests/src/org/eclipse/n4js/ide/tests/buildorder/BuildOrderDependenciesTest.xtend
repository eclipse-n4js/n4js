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
class BuildOrderDependenciesTest extends AbstractBuildOrderTest {

	@Test
	def void testSingleDependency1() {
		testProject(
					"test-project/node_modules/D2," +
					"test-project/node_modules/D1," +
					"test-project/node_modules/n4js-runtime, " +
					"test-project", 

			CFG_DEPENDENCIES -> "D1",
			CFG_NODE_MODULES + "n4js-runtime" -> "",
			CFG_NODE_MODULES + "D1" + CFG_SRC + "index" -> '''
					const i = 1;
				''',
			CFG_NODE_MODULES + "D1" + CFG_DEPENDENCIES -> "D2",
			CFG_NODE_MODULES + "D2" + CFG_SRC + "index" -> '''
					const i = 2;
				''',
			CFG_NODE_MODULES + "D2" + CFG_NODE_MODULES + "n4js-runtime" -> ""
			
		);
	}
}
