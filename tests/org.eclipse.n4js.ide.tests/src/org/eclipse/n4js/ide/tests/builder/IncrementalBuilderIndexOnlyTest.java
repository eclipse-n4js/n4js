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

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests index only flag which needs to rebuild the project state on a clean-build.
 */

public class IncrementalBuilderIndexOnlyTest extends AbstractIncrementalBuilderTest {
	static final long FILE_TIME_MILLISECONDS = 8472000;

	@Test
	public void testIndexOnlyRebuild() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				CFG_NODE_MODULES + "LibProject" + CFG_SRC + "LibModule",
				"""
							export public class LibClass1 {
								public libMethod() {}
							}
						""",
				"Main",
				"""
							import {LibClass} from "LibModule";

							new LibClass().libMethod();
						""",
				CFG_DEPENDENCIES,
				"""
							LibProject
						"""));

		startAndWaitForLspServer();

		// 1: assert that issues are hidden
		assertIssuesInModules(Pair.of("LibModule", List.of()));
		// 2: assert that error in node_modules actually exists
		assertIssuesInModules(Pair.of("Main", List.of(
				"(Error, [0:9 - 0:17], Import of LibClass cannot be resolved.)",
				"(Error, [2:5 - 2:13], Couldn't resolve reference to IdentifiableElement 'LibClass'.)")));

		FileURI libModule = getFileURIFromModuleName("LibModule");
		changeFileOnDiskWithoutNotification(libModule, Pair.of("LibClass1", "LibClass"));

		String libStateName = DEFAULT_PROJECT_NAME + "/" + NODE_MODULES + "/LibProject/"
				+ N4JSGlobals.N4JS_PROJECT_STATE;
		Path prjStatePath = getFileURIFromModuleName(libStateName).toFileSystemPath();
		setFileCreationDate(prjStatePath, FILE_TIME_MILLISECONDS);

		cleanBuildAndWait();

		// 3: assert that node_modules have been actually rebuilt
		assertNoIssues();
		assertOutputFileExists("Main");
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).lastModifiedTime();
		// 4: assert that project state of node_modules project was updated
		assertNotEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
	}
}
