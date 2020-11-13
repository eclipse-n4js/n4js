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

import com.google.inject.Injector
import java.util.Collection
import java.util.List
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.utils.Strings
import org.eclipse.n4js.xtext.workspace.ProjectBuildOrderInfo
import org.eclipse.n4js.xtext.workspace.XWorkspaceConfigSnapshotProvider
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test for build order
 */
class BuildOrderTest extends AbstractIdeTest {

	private XWorkspaceConfigSnapshotProvider workspaceConfigProvider;
	private ProjectBuildOrderInfo.Provider projectBuildOrderInfoProvider;

	override Injector createInjector() {
		val Injector injector = super.createInjector();
		workspaceConfigProvider = injector.getInstance(XWorkspaceConfigSnapshotProvider);
		projectBuildOrderInfoProvider = injector.getInstance(ProjectBuildOrderInfo.Provider);
		return injector;
	}

	/**
	 * @param expectation
	 *            string of project names ordered in build order
	 * @param projectsModulesContents
	 *            test workspace
	 */
	@SafeVarargs
	 def final void test(String buildOrder, Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		test(buildOrder, emptyList, projectsModulesContents);
	}

	/**
	 * @param expectation
	 *            string of project names ordered in build order
	 * @param projectsModulesContents
	 *            test workspace
	 */
	@SafeVarargs
	 def final void test(String buildOrder, Collection<Collection<String>> cycles, Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		testWorkspaceManager.createTestOnDisk(projectsModulesContents);
		startAndWaitForLspServer();

		val workspaceConfig = workspaceConfigProvider.get();
		val projectBuildOrderInfo = projectBuildOrderInfoProvider.get(workspaceConfig);
		val boIterator = projectBuildOrderInfo.getIterator().visitAll();
		try {
			val String names = IteratorExtensions.join(boIterator, ", ", [it.name]);
			assertEquals(buildOrder, names);
		} catch (Exception exc) {
			throw new RuntimeException("Never happens since toString never throws an exception. Bogus xtext warning", exc)
		};
		assertEquals(cycles.size, projectBuildOrderInfo.projectCycles.size);
		val expectedCycles = cycles.map[Strings.join(", ", it)].toSet;
		for (cycle : projectBuildOrderInfo.projectCycles) {
			val detectedCycle = Strings.join(", ", cycle);
			assertTrue(expectedCycles.contains(detectedCycle));
		}
	}
	
	
	@Test
	def void testSingleDependency1() {
		test("yarn-test-project, n4js-runtime, P1", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies1() {
		test("yarn-test-project, n4js-runtime, P1, P2", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies2() {
		test("yarn-test-project, n4js-runtime, P1, P2", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies3() {
		test("yarn-test-project, n4js-runtime, P2, P1", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testCycle1() {
		test("yarn-test-project, n4js-runtime, P2, P1",
			#[#["P1", "P2"]],
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
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
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P1
				'''
			],
			"P3" -> #[
				"#DEPENDENCY" -> '''
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
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P3
				'''
			],
			"P3" -> #[
				"#DEPENDENCY" -> '''
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
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P1
				'''
			],
			"P3" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P4
				'''
			],
			"P4" -> #[
				"#DEPENDENCY" -> '''
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
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P3
				'''
			],
			"P3" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2,
					P4
				'''
			],
			"P4" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
}
