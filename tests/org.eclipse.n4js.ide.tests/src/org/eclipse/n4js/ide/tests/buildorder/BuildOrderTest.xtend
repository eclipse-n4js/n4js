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
class BuildOrderTest extends AbstractBuildOrderTest {

	@Test
	def void testSingleDependency1() {
		test("yarn-test-project, n4js-runtime, P1", 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies1() {
		test("yarn-test-project, n4js-runtime, P1, P2", 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies2() {
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
			]
		);
	}
	
	@Test
	def void testTwoDependencies3() {
		test("yarn-test-project, n4js-runtime, P2, P1", 
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testCycle1() {
		test("yarn-test-project, n4js-runtime, P2, P1",
			#[#["P1", "P2"]],
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
	@Test
	def void testCycle2() {
		test("yarn-test-project, n4js-runtime, P2, P1, P3",
			#[#["P1", "P2"]],
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			],
			"P3" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
	@Test
	def void testCycle3() {
		test("yarn-test-project, n4js-runtime, P3, P2, P1",
			#[#["P1", "P2", "P3"]],
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P3
				'''
			],
			"P3" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
	@Test
	def void testCycles1() {
		test("yarn-test-project, n4js-runtime, P2, P1, P4, P3",
			#[#["P1", "P2"], #["P3", "P4"]],
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			],
			"P3" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P4
				'''
			],
			"P4" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P3
				'''
			]
		);
	}
	
	@Test
	def void testCycles4000() {
		test("yarn-test-project, n4js-runtime, P4, P3, P2, P1",
			#[#["P1", "P2", "P3", "P4"], #["P2", "P3"]],
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P3
				'''
			],
			"P3" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P2,
					P4
				'''
			],
			"P4" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
}
