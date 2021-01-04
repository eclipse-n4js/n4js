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
import org.eclipse.n4js.xtext.workspace.XWorkspaceConfigSnapshotProvider
import org.junit.Test

import static org.junit.Assert.*
import org.eclipse.n4js.xtext.workspace.BuildOrderFactory

/**
 * Test for build order
 */
class BuildOrderSideLoadTest extends AbstractIdeTest {

	private XWorkspaceConfigSnapshotProvider workspaceConfigProvider;
	private BuildOrderFactory projectBuildOrderFactory;

	override Injector createInjector() {
		val Injector injector = super.createInjector();
		workspaceConfigProvider = injector.getInstance(XWorkspaceConfigSnapshotProvider);
		projectBuildOrderFactory = injector.getInstance(BuildOrderFactory);
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

		val workspaceConfig = workspaceConfigProvider.getWorkspaceConfigSnapshot();
		val projectBuildOrderInfo = projectBuildOrderFactory.createBuildOrderInfo(workspaceConfig);
		val boIterator = projectBuildOrderFactory.createBuildOrderIterator(workspaceConfig).visitAll();
		try {
			val String names = IteratorExtensions.join(boIterator, ", ", [it.name]);
			assertEquals(buildOrder, names);
		} catch (Exception exc) {
			throw new RuntimeException("Never happens since toString never throws an exception. Bogus xtext warning", exc)
		};
		assertEquals(cycles.size, projectBuildOrderInfo.getProjectCycles.size);
		val expectedCycles = cycles.map[Strings.join(", ", it)].toSet;
		for (cycle : projectBuildOrderInfo.getProjectCycles) {
			val detectedCycle = Strings.join(", ", cycle);
			assertTrue(expectedCycles.contains(detectedCycle));
		}
	}
	
	
	@Test
	def void testSingleDependency1() {
		// continue here: order does not seem right!
		test("yarn-test-project, n4js-runtime, @n4jsd/Lib1, Lib1, @n4jsd/Lib3, @n4jsd/Lib2, Lib2, P1",
			CFG_NODE_MODULES + "n4js-runtime" -> null,
			CFG_NODE_MODULES + "Lib1" -> #[
				"package.json" -> '''
					{
						"name": "Lib1"
					}
				'''
			],
			CFG_NODE_MODULES + "Lib2" -> #[
				"package.json" -> '''
					{
						"name": "Lib2",
						"dependencies": {
							"Lib3": "*"
						}
					}
				'''
			],
			CFG_NODE_MODULES + "Lib3" -> #[
				"package.json" -> '''
					{
						"name": "Lib3"
					}
				'''
			],
			CFG_WORKSPACES_FOLDER + "packages-gen/@n4jsd/Lib1" -> #[
				"package.json" -> '''
					{
						"name": "@n4jsd/Lib1",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "Lib1"
						}
					}
				'''
			],
			CFG_WORKSPACES_FOLDER + "packages-gen/@n4jsd/Lib2" -> #[
				"package.json" -> '''
					{
						"name": "@n4jsd/Lib2",
						"dependencies": {
							"@n4jsd/Lib3": "*"
						},
						"n4js": {
							"projectType": "definition",
							"definesPackage": "Lib2"
						}
					}
				'''
			],
			CFG_WORKSPACES_FOLDER + "packages-gen/@n4jsd/Lib3" -> #[
				"package.json" -> '''
					{
						"name": "@n4jsd/Lib3",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "Lib3"
						}
					}
				'''
			],
			"P1" -> #[
				CFG_DEPENDENCIES -> '''
					n4js-runtime,
					Lib1,
					Lib2
				'''
			]
		);
	}
	
}
