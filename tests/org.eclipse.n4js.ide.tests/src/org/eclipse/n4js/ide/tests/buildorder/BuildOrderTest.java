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

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for build order
 */

public class BuildOrderTest extends AbstractBuildOrderTest {

	@Test
	public void testSingleDependency1() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P1",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime
										""")));
	}

	@Test
	public void testTwoDependencies1() {
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
											n4js-runtime
										""")));
	}

	@Test
	public void testTwoDependencies2() {
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
										""")));
	}

	@Test
	public void testTwoDependencies3() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime
										""")));
	}

	@Test
	public void testCycle1() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1",

				List.of(List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2")),
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										""")));
	}

	@Test
	public void testCycle2() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1, " +
				"yarn-test-project/packages/P3",

				List.of(List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2")),
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										"""),
						"P3", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										""")));
	}

	@Test
	public void testCycle3() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P3, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1",

				List.of(List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2",
						"yarn-test-project/packages/P3")),
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P3
										"""),
						"P3", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										""")));
	}

	@Test
	public void testCycles1() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1, " +
				"yarn-test-project/packages/P4, " +
				"yarn-test-project/packages/P3",

				List.of(
						List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2"),
						List.of("yarn-test-project/packages/P3", "yarn-test-project/packages/P4")),
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										"""),
						"P3", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P4
										"""),
						"P4", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P3
										""")));
	}

	@Test
	public void testCycles4000() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P4, " +
				"yarn-test-project/packages/P3, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1",

				List.of(
						List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2",
								"yarn-test-project/packages/P3", "yarn-test-project/packages/P4"),
						List.of("yarn-test-project/packages/P2", "yarn-test-project/packages/P3")),
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P3
										"""),
						"P3", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2,
											P4
										"""),
						"P4", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										""")));
	}

}
