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
 * Test for build order. These tests assure that dependencies of plain-js projects are not ignored when computing the
 * build order if they also are dependencies of non-plain-js projects (e.g. n4js projects)
 */

public class BuildOrderToDtsTest extends AbstractBuildOrderTest {

	@Test
	public void testNmfDepsToPlainJS_A() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/node_modules/PlainJS1, " + // PlainJS2 is ignored
				"yarn-test-project/packages/MyProject",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						CFG_NODE_MODULES + "PlainJS1", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS1",
												"main": "index.js",
												"type": "module",
												"dependencies": {
													"PlainJS2": "*"
												}
											}
										"""),
						CFG_NODE_MODULES + "PlainJS2", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS2",
												"main": "index.js",
												"type": "module"
											}
										"""),

						"MyProject", Map.of(
								// missing dependency to PlainJS2, hence it is ignored
								CFG_DEPENDENCIES, """
											n4js-runtime,
											PlainJS1
										""")));
	}

	@Test
	public void testNmfDepsToPlainJS_Def() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/node_modules/PlainJS2, " + // PlainJS2 not ignored
				"yarn-test-project/node_modules/PlainJS1, " +
				"yarn-test-project/packages/MyProject",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						CFG_NODE_MODULES + "PlainJS1", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS1",
												"main": "index.js",
												"type": "module",
												"dependencies": {
													"PlainJS2": "*"
												}
											}
										"""),
						CFG_NODE_MODULES + "PlainJS2", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS2",
												"main": "index.js",
												"type": "module"
											}
										"""),

						"MyProject", Map.of(
								// add dependency to PlainJS2
								CFG_DEPENDENCIES, """
											n4js-runtime,
											PlainJS1,
											PlainJS2
										""")));
	}

	@Test
	public void testPackagesDepsToPlainJS_A() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				// "yarn-test-project/packages/PlainJS2, " + // PlainJS2 ignored
				"yarn-test-project/packages/PlainJS1, " +
				"yarn-test-project/packages/MyProject",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"MyProject", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											PlainJS1
										"""),
						"PlainJS1", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS1",
												"main": "index.js",
												"type": "module",
												"dependencies": {
													"PlainJS2": "*"
												}
											}
										"""),
						// ignored since there is no dependency to PlainJS2 from a non-PLAINJS project
						"PlainJS2", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS2",
												"main": "index.js",
												"type": "module"
											}
										""")));
	}

	@Test
	public void testPackagesDepsToPlainJS_Def() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/PlainJS2, " + // PlainJS2 not ignored
				"yarn-test-project/packages/PlainJS1, " +
				"yarn-test-project/packages/MyProject",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"MyProject", Map.of(
								// add dependency to PlainJS2
								CFG_DEPENDENCIES, """
											n4js-runtime,
											PlainJS1,
											PlainJS2
										"""),
						"PlainJS1", Map.of(
								"index.js", """
											// dummy
										""",
								"index.d.ts", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS1",
												"main": "index.js",
												"type": "module",
												"dependencies": {
													"PlainJS2": "*"
												}
											}
										"""),
						"PlainJS2", Map.of(
								"index.js", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS2",
												"main": "index.js",
												"type": "module"
											}
										""")));
	}

	/**
	 * actually this test is redundant to the ones before. However, it defines the usecase of interest since here,
	 * PlainJS1 has a type dependency and re-export of types in PlainJS2.
	 */
	@Test
	public void testNmfDepsToPlainJS_DefToDef() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/node_modules/PlainJS2, " + // PlainJS2 not ignored
				"yarn-test-project/node_modules/PlainJS1, " +
				"yarn-test-project/packages/MyProject",
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						CFG_NODE_MODULES + "PlainJS1", Map.of(
								"index.js", """
											// dummy
										""",
								"index.d.ts", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS1",
												"main": "index.js",
												"type": "module",
												"dependencies": {
													"PlainJS2": "*"
												}
											}
										"""),
						CFG_NODE_MODULES + "PlainJS2", Map.of(
								"index.js", """
											// dummy
										""",
								// interesting type information re-exported in PlainJS1
								"index.d.ts", """
											// dummy
										""",
								"package.json", """
											{
												"name": "PlainJS2",
												"main": "index.js",
												"type": "module"
											}
										"""),

						"MyProject", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											PlainJS1,
											PlainJS2
										""")));
	}

}
