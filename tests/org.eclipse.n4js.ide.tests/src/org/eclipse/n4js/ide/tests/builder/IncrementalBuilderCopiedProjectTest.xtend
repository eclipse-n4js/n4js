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
import java.io.IOException
import java.util.Map
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.utils.io.FileCopier
import org.junit.Test

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.*

/**
 */
class IncrementalBuilderCopiedProjectTest extends AbstractIncrementalBuilderTest {

	/**
	 * This test creates a copy of a built project (including .n4js.projectstate files).
	 * After copying, both of the projects should compile like the original project compiled before.
	 * 
	 * The contents of the test files (polyfills etc.) would create many warnings in case the
	 * .n4js.projectstate would reference absolute URIs.
	 */
	@Test
	def void testCreateDuplicateProject() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(
			CFG_NODE_MODULES + "lib" + CFG_SRC + "LibModule.n4jsd" -> '''
				@@Global
				@@ProvidedByRuntime
				
				@Polyfill export external public class Object extends Object {
				}
			''',
			"MyModule" -> '''
				_globalThis.MigrationContext;
			''',
			CFG_DEPENDENCIES -> '''
				lib
			'''
		);

		startAndWaitForLspServer();
		assertProjectBuildOrder("[lib, n4js-runtime, test-project]");
		assertIssues("MyModule" -> #["(Error, [0:0 - 0:11], Couldn't resolve reference to IdentifiableElement '_globalThis'.)"]);
		shutdownLspServer();
		

		val projectFolder = new File(getRoot(), DEFAULT_PROJECT_NAME);
		val projectFolder2 = new File(getRoot(), DEFAULT_PROJECT_NAME+"2");
		FileCopier.copy(projectFolder, projectFolder2);
		val packagejson2 = new FileURI(new File(getRoot(), DEFAULT_PROJECT_NAME+"2/package.json"));
		changeFileOnDiskWithoutNotification(packagejson2, "test-project" -> "test-project2");


		startAndWaitForLspServer();
		val myModule1 = new FileURI(new File(getRoot(), DEFAULT_PROJECT_NAME+"/src/MyModule.n4js"));
		val myModule2 = new FileURI(new File(getRoot(), DEFAULT_PROJECT_NAME+"2/src/MyModule.n4js"));
		assertProjectBuildOrder("[lib, n4js-runtime, test-project, test-project2]");
		assertIssues(Map.of(
			myModule1, #["(Error, [0:0 - 0:11], Couldn't resolve reference to IdentifiableElement '_globalThis'.)"],
			myModule2, #["(Error, [0:0 - 0:11], Couldn't resolve reference to IdentifiableElement '_globalThis'.)"]
		));
	}
	
}
