/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test different legal forms of yarn's workspaces property.
 */
@SuppressWarnings("javadoc")
public class YarnWorkspacesPropertyTest extends AbstractIdeTest {

	@Test
	public void testValueIsArray() {
		performTest("""
				"workspaces": [
					"packages/*"
				]
				""");
	}

	@Test
	public void testValueIsObject() {
		performTest("""
				"workspaces": {
					"packages": [
						"packages/*"
					],
					"nohoist": [
						"**/react-native",
						"**/react-native/**"
					]
				}
				""");
	}

	private void performTest(CharSequence workspacesProperty) {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"A", """
								export public function foo(p: string) {}
								"""),
				"ProjectB", Map.of(
						"B", """
								import {foo} from "A"
								foo(42);
								""",
						CFG_DEPENDENCIES, """
								ProjectA
								""")));
		changeFileOnDiskWithoutNotification(getRootPackageJsonURI(), """
					{
						"name": "yarn-test-project",
						"version": "0.0.1",
						"private": true,
						%s
					}
				""".formatted(workspacesProperty));
		startAndWaitForLspServer();

		// main assertion:
		WorkspaceConfigSnapshot workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();
		assertEquals("there should be 4 projects in the workspace", 4, workspaceConfig.getProjects().size());
		assertNotNull("the yarn workspace project should have been discovered",
				workspaceConfig.findProjectByID(TestWorkspaceManager.YARN_TEST_PROJECT));
		assertNotNull("project 'n4js-runtime' should have been discovered",
				workspaceConfig.findProjectByID("yarn-test-project/node_modules/n4js-runtime"));
		assertNotNull("project 'ProjectA' should have been discovered",
				workspaceConfig.findProjectByID("yarn-test-project/packages/ProjectA"));
		assertNotNull("project 'ProjectB' should have been discovered",
				workspaceConfig.findProjectByID("yarn-test-project/packages/ProjectB"));

		// additional assertion to make sure every thing is working correctly:
		assertIssues2(
				Pair.of("B", List.of(
						"(Error, [1:4 - 1:6], 42 is not a subtype of string.)")));
	}

	private FileURI getRootPackageJsonURI() {
		return toFileURI(getRoot().toPath().resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(PACKAGE_JSON));
	}
}
