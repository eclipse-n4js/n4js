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

import java.io.IOException
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.*
import static org.junit.Assert.assertNotEquals

/**
 * Tests index only flag which needs to rebuild the project state on a clean-build.
 */
class IncrementalBuilderIndexOnlyTest extends AbstractIncrementalBuilderTest {
	static final long FILE_TIME_MILLISECONDS = 8472000;

	@Test
	def void testIndexOnlyRebuild() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(#[
			NODE_MODULES + "LibProject" + SRC + "LibModule" -> 
				'''
					export public class LibClass1 {
						public libMethod() {}
					}
				''',
			"Main" ->
				'''
					import {LibClass} from "LibModule";
					
					new LibClass().libMethod();
				''',
			TestWorkspaceManager.DEPENDENCIES ->
				'''
					n4js-runtime,
					LibProject
				'''
		]);

		startAndWaitForLspServer();


		// 1: assert that issues are hidden
		assertIssuesInModules("LibModule" -> #[]);
		// 2: assert that error in node_modules actually exists
		assertIssuesInModules("Main" -> #[
			"(Error, [0:8 - 0:16], Import of LibClass cannot be resolved.)",
			"(Error, [2:4 - 2:12], Couldn't resolve reference to IdentifiableElement 'LibClass'.)"]);

		val libModule = getFileURIFromModuleName("LibModule");
		changeFileOnDiskWithoutNotification(libModule, "LibClass1" -> "LibClass");

		val libStateName = DEFAULT_PROJECT_NAME + "/" + N4JSGlobals.NODE_MODULES + "/LibProject/" + N4JSGlobals.N4JS_PROJECT_STATE;
		val prjStatePath = getFileURIFromModuleName(libStateName).toFileSystemPath();
		setFileCreationDate(prjStatePath, FILE_TIME_MILLISECONDS);

		cleanBuildAndWait();

		// 3: assert that node_modules have been actually rebuilt
		assertNoIssues();
		assertOutputFileExists("Main");
		val prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes).lastModifiedTime();
		// 4: assert that project state of node_modules project was updated
		assertNotEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
	}
}
