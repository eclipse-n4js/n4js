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

import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * Test if the workspace project does not have the default source folder '.'.
 */

public class WorkspaceNoDefaultSourceFolderPnpmTest extends AbstractIdeTest {

	@Inject
	WorkspaceAccess workspaceAccess;

	private static Map<String, Map<String, String>> testData = Map.of(
			"main-project", Map.of(
					"MainModule", """
							// empty
							""",
					CFG_DEPENDENCIES, """
								library-project-other-name
							"""),
			"library-project", Map.of(
					"LibraryModule", """
							// empty
							"""));

	@Test
	public void test() throws Exception {
		testWorkspaceManager.createTestOnDisk(testData);

		startAndWaitForLspServer();

		ProjectConfigSnapshot pc = concurrentIndex.getProjectConfig(TestWorkspaceManager.YARN_TEST_PROJECT);
		Assert.assertNotNull(pc);

		ImmutableSet<? extends SourceFolderSnapshot> sfs = pc.getSourceFolders();

		Assert.assertEquals(1, sfs.size());
		Assert.assertTrue(sfs.iterator().next() instanceof N4JSSourceFolderSnapshotForPackageJson);
	}

}
