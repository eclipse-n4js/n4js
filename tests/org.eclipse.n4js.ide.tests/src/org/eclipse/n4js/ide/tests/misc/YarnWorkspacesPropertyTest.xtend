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
package org.eclipse.n4js.ide.tests.misc

import com.google.inject.Inject
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager
import org.eclipse.n4js.workspace.locations.FileURI
import org.eclipse.n4js.xtext.server.build.ConcurrentIndex
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test different legal forms of yarn's workspaces property.
 */
class YarnWorkspacesPropertyTest extends AbstractIdeTest {

	@Inject
	private ConcurrentIndex concurrentIndex;

	@Test
	def void testValueIsArray() {
		performTest('''
			"workspaces": [
				"packages/*"
			]
		''');
	}

	@Test
	def void testValueIsObject() {
		performTest('''
			"workspaces": {
				"packages": [
					"packages/*"
				],
				"nohoist": [
					"**/react-native",
					"**/react-native/**"
				]
			}
		''');
	}

	def private void performTest(CharSequence workspacesProperty) {
		testWorkspaceManager.createTestOnDisk(
			"ProjectA" -> #[
				"A" -> '''
					export public function foo(p: string) {}
				'''
			],
			"ProjectB" -> #[
				"B" -> '''
					import {foo} from "A"
					foo(42);
				''',
				CFG_DEPENDENCIES -> '''
					ProjectA
				'''
			]
		);
		changeFileOnDiskWithoutNotification(getRootPackageJsonURI(), '''
			{
				"name": "yarn-test-project",
				"version": "0.0.1",
				"private": true,
				«workspacesProperty»
			}
		''');
		startAndWaitForLspServer();

		// main assertion:
		val workspaceConfig = concurrentIndex.workspaceConfigSnapshot;
		assertEquals("there should be 4 projects in the workspace", 4, workspaceConfig.projects.size);
		assertNotNull("the yarn workspace project should have been discovered", workspaceConfig.findProjectByName(TestWorkspaceManager.YARN_TEST_PROJECT));
		assertNotNull("project 'n4js-runtime' should have been discovered", workspaceConfig.findProjectByName(N4JS_RUNTIME));
		assertNotNull("project 'ProjectA' should have been discovered", workspaceConfig.findProjectByName("ProjectA"));
		assertNotNull("project 'ProjectB' should have been discovered", workspaceConfig.findProjectByName("ProjectB"));

		// additional assertion to make sure every thing is working correctly:
		assertIssues(
			"B" -> #[
				"(Error, [1:4 - 1:6], int is not a subtype of string.)"
			]
		);
	}

	def private FileURI getRootPackageJsonURI() {
		return getRoot().toPath.resolve(TestWorkspaceManager.YARN_TEST_PROJECT).resolve(PACKAGE_JSON).toFileURI;
	}
}
