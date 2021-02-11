/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.exclude;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 *
 */
// converted from ExcludePckJson_PluginUITest
public class ExcludePckJson_IdeTest extends ConvertedIdeTest {

	private static final String PROJECT_NAME = "ExcludePckJson";

	/***/
	@Before
	public void prepareWorkspace() {
		importProband(new File("probands", PROJECT_NAME));
	}

	/**
	 * Checks that only one package.json is contained in the index. This one package.json is the one from the project
	 * root folder.
	 */
	@Ignore("GH-2058")
	@Test
	public void checkIndex() {
		File project = getProjectRootForImportedProject(PROJECT_NAME);
		assertTrue("Test project is not a directory.", project.isDirectory());

		IResourceDescriptions index = concurrentIndex.getProjectIndex(PROJECT_NAME);
		assertNotNull("index of test project not found", index);

		List<String> jsonFilesInIndex = new ArrayList<>();
		for (IResourceDescription res : index.getAllResourceDescriptions()) {
			String resLocation = res.getURI().toString();

			if (resLocation.endsWith(".json")) {
				jsonFilesInIndex.add("Indexing found: " + resLocation);
			}
		}
		assertEquals("expected exactly one entry in index for a .json file, but got:\n    "
				+ Joiner.on("\n    ").join(jsonFilesInIndex), 1, jsonFilesInIndex.size());
	}

	/**
	 * Checks that there is exactly one error marker in the problems view. This single marker must be from the
	 * package.json of the project root folder.
	 */
	@Test
	public void checkProblemMarkers() {
		File project = getProjectRootForImportedProject(PROJECT_NAME);
		assertTrue("Test project is not a directory.", project.isDirectory());

		Set<FileURI> urisWithIssues = getIssues().keySet();
		assertEquals(1, urisWithIssues.size());
		assertTrue(urisWithIssues.iterator().next().toString()
				.endsWith("test-workspace/yarn-test-project/packages/ExcludePckJson/package.json"));
	}
}
