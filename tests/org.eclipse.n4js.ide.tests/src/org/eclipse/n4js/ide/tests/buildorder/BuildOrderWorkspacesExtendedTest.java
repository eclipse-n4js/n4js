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
@SuppressWarnings("javadoc")
public class BuildOrderWorkspacesExtendedTest extends AbstractBuildOrderTest {

	@Test
	public void testWorkspacesObject() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P1, " +
				"yarn-test-project/packages/P2",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										"""),
						CFG_YARN_PROJECT, Map.of(
								PACKAGE_JSON, """
											{
												"private" : true,
												"workspaces" : {
													"packages" : ["packages/*"]
												}
											}
										""")));
	}

}
