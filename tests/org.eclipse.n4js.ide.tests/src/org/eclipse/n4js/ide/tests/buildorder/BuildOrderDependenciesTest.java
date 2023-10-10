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
package org.eclipse.n4js.ide.tests.buildorder;

import java.util.Map;

import org.junit.Test;

/**
 * Test for build order
 */

public class BuildOrderDependenciesTest extends AbstractBuildOrderTest {

	@Test
	public void testSingleDependency1() {
		testProject(
				"test-project/node_modules/n4js-runtime, " +
						"test-project/node_modules/D2, " +
						"test-project/node_modules/D1, " +
						"test-project",
				Map.of(
						CFG_DEPENDENCIES, "D1",
						CFG_NODE_MODULES + "n4js-runtime", "",
						CFG_NODE_MODULES + "D1" + CFG_SRC + "index", """
									const i = 1;
								""",
						CFG_NODE_MODULES + "D1" + CFG_DEPENDENCIES, "D2",
						CFG_NODE_MODULES + "D2" + CFG_SRC + "index", """
									const i = 2;
								""",
						CFG_NODE_MODULES + "D2" + CFG_NODE_MODULES + "n4js-runtime", ""

				));
	}

	@Test
	public void testSingleDependency2() {
		testProject(
				"test-project/node_modules/n4js-runtime, " +
						"test-project/node_modules/@scope/D2, " +
						"test-project/node_modules/D1, " +
						"test-project",
				Map.of(
						CFG_DEPENDENCIES, "D1",
						CFG_NODE_MODULES + "n4js-runtime", "",
						CFG_NODE_MODULES + "D1" + CFG_SRC + "index", """
									const i = 1;
								""",
						CFG_NODE_MODULES + "D1" + CFG_DEPENDENCIES, "@scope/D2",
						CFG_NODE_MODULES + "@scope/D2" + CFG_SRC + "index", """
									const i = 2;
								""",
						CFG_NODE_MODULES + "@scope/D2" + CFG_NODE_MODULES + "n4js-runtime", ""

				));
	}
}
