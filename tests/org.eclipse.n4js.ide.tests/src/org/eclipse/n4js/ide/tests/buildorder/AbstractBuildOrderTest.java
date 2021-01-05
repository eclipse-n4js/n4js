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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.BuildOrderIterator;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Test for build order
 */
abstract class AbstractBuildOrderTest extends AbstractIdeTest {

	/**
	 * @param buildOrder
	 *            string of project names ordered in build order
	 * @param projectsModulesContents
	 *            test workspace
	 */
	@SafeVarargs
	final void test(String buildOrder, Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		test(buildOrder, Collections.emptyList(), projectsModulesContents);
	}

	/**
	 * @param buildOrder
	 *            string of project names ordered in build order
	 * @param projectsModulesContents
	 *            test workspace
	 */
	@SafeVarargs
	final void test(String buildOrder, Collection<Collection<String>> cycles,
			Pair<String, List<Pair<String, String>>>... projectsModulesContents) {

		init(projectsModulesContents);
		assertBuildOrder(buildOrder, cycles);

	}

	@SafeVarargs
	final void init(Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		testWorkspaceManager.createTestOnDisk(projectsModulesContents);
		startAndWaitForLspServer();
	}

	final void assertBuildOrder(String buildOrder, Collection<Collection<String>> cycles) {
		WorkspaceConfigSnapshot workspaceConfig = workspaceConfigProvider.getWorkspaceConfigSnapshot();
		BuildOrderInfo projectBuildOrderInfo = projectBuildOrderFactory.createBuildOrderInfo(workspaceConfig);
		BuildOrderIterator boIterator = projectBuildOrderFactory.createBuildOrderIterator(workspaceConfig).visitAll();
		try {
			String names = IteratorExtensions.join(boIterator, ", ", it -> it.getName());
			assertEquals(buildOrder, names);
		} catch (Exception exc) {
			throw new RuntimeException("Never happens since toString never throws an exception. Bogus xtext warning",
					exc);
		}
		assertEquals(cycles.size(), projectBuildOrderInfo.getProjectCycles().size());
		Set<String> expectedCycles = cycles.stream().map(it -> Strings.join(", ", it)).collect(Collectors.toSet());
		for (List<String> cycle : projectBuildOrderInfo.getProjectCycles()) {
			String detectedCycle = Strings.join(", ", cycle);
			assertTrue(expectedCycles.contains(detectedCycle));
		}
	}

}
