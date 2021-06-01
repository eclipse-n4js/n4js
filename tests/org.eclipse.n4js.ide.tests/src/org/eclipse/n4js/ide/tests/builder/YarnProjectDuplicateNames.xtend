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
package org.eclipse.n4js.ide.tests.builder

import java.io.File
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager
import org.eclipse.n4js.utils.Strings
import org.eclipse.n4js.utils.io.FileUtils
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot
import org.junit.Test

import static org.junit.Assert.*
import org.junit.Ignore

/**
 * Test two cases of projects with the same project name within a yarn setup
 */
class YarnProjectDuplicateNames extends AbstractIncrementalBuilderTest {

	private static val testData1 = #[
		TestWorkspaceManager.YARN_TEST_PROJECT -> #[
			"SomeModule" -> '''
				export public class SomeClass {
				}
			'''
		],
		"someProject2" -> #[ // to force the yarn setup
			"SomeModule2" -> '''
				export public class SomeClass2 {
				}
			'''
		]
	];

	@Ignore // GH-2143
	@Test
	def void projectEqualsWorkspaceName() throws Exception {
		testWorkspaceManager.createTestOnDisk(testData1);
		
		startAndWaitForLspServer();
		
		val workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();
		val projects = workspaceConfig.projects as Iterable<ProjectConfigSnapshot>;
		assertEquals(
			"yarn-test-project, yarn-test-project, someProject2, n4js-runtime",
			Strings.join(", ", [p|p.name], projects)
		);
	}


	private static val testData2 = #[
		"someProject" -> #[
			"SomeModule" -> '''
				export public class SomeClass {
				}
			'''
		],
		"someProject2" -> #[ // to force the yarn setup
			"SomeModule2" -> '''
				export public class SomeClass2 {
				}
			'''
		]
	];

	@Ignore // GH-2143
	@Test
	def void twoEqualProjectNames() throws Exception {
		testWorkspaceManager.createTestOnDisk(testData2);
		val someProject = new File(testWorkspaceManager.root, TestWorkspaceManager.YARN_TEST_PROJECT + "/packages/someProject");
		val packages2 = new File(testWorkspaceManager.root, TestWorkspaceManager.YARN_TEST_PROJECT + "/packages2");
		packages2.mkdir();
		FileUtils.copy(someProject, packages2);
		
		val pckjson = getFileURIFromModuleName(TestWorkspaceManager.YARN_TEST_PROJECT + "/package.json");
		changeFileOnDiskWithoutNotification(pckjson, "\"packages/*\"" -> "\"packages/*\", \"packages2/*\"");
		
		startAndWaitForLspServer();
		
		val workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();
		val projects = workspaceConfig.projects as Iterable<ProjectConfigSnapshot>;
		assertEquals(
			"yarn-test-project, someProject, someProject, someProject2, n4js-runtime", 
			Strings.join(", ", [p|p.name], projects)
		);
	}

}
