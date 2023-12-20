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
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test two cases of projects with the same project name within a yarn setup
 */
@SuppressWarnings("unchecked")
public class YarnProjectDuplicateNames extends AbstractIncrementalBuilderTest {

	private static Map<String, Map<String, String>> testData1 = Map.of(
			TestWorkspaceManager.YARN_TEST_PROJECT, Map.of(
					"SomeModule", """
								export public class SomeClass {
								}
							"""),
			"someProject2", Map.of( // to force the yarn setup
					"SomeModule2", """
								export public class SomeClass2 {
								}
							"""));

	@Test
	public void projectEqualsWorkspaceName() throws Exception {
		testWorkspaceManager.createTestOnDisk(testData1);

		startAndWaitForLspServer();

		WorkspaceConfigSnapshot workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();
		Iterable<ProjectConfigSnapshot> projects = (Iterable<ProjectConfigSnapshot>) workspaceConfig.getProjects();
		assertEquals(
				"yarn-test-project, " +
						"yarn-test-project/packages/someProject2, " +
						"yarn-test-project/packages/yarn-test-project, " +
						"yarn-test-project/node_modules/n4js-runtime",
				Strings.join(", ", (p) -> p.getName(), projects));
	}

	private static Map<String, Map<String, String>> testData2 = Map.of(
			"someProject", Map.of(
					"SomeModule", """
								export public class SomeClass {
								}
							"""),
			"someProject2", Map.of( // to force the yarn setup
					"SomeModule2", """
								export public class SomeClass2 {
								}
							"""));

	@Ignore // GH-2143
	@Test
	public void twoEqualProjectNames() throws Exception {
		testWorkspaceManager.createTestOnDisk(testData2);
		File someProject = new File(testWorkspaceManager.getRoot(),
				TestWorkspaceManager.YARN_TEST_PROJECT + "/packages/someProject");
		File packages2 = new File(testWorkspaceManager.getRoot(),
				TestWorkspaceManager.YARN_TEST_PROJECT + "/packages2");
		packages2.mkdir();
		FileUtils.copy(someProject, packages2);

		FileURI pckjson = getFileURIFromModuleName(TestWorkspaceManager.YARN_TEST_PROJECT + "/package.json");
		changeFileOnDiskWithoutNotification(pckjson, Pair.of("\"packages/*\"", "\"packages/*\", \"packages2/*\""));

		startAndWaitForLspServer();

		WorkspaceConfigSnapshot workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();
		Iterable<ProjectConfigSnapshot> projects = (Iterable<ProjectConfigSnapshot>) workspaceConfig.getProjects();
		assertEquals(
				"yarn-test-project, someProject, someProject, someProject2, n4js-runtime",
				Strings.join(", ", (p) -> p.getName(), projects));
	}

}
