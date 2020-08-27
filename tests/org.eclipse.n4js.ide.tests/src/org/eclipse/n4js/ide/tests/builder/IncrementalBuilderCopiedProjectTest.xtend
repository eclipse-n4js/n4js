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
import java.util.Collections
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
				n4js-runtime,
				lib
			'''
		);

		startAndWaitForLspServer();
		assertIssues("MyModule" -> #["(Error, [0:0 - 0:11], Couldn't resolve reference to IdentifiableElement '_globalThis'.)"]);
		shutdownLspServer();
		

		val projectFolder = new File(getRoot(), DEFAULT_PROJECT_NAME);
		val projectFolder2 = new File(getRoot(), DEFAULT_PROJECT_NAME+"2");
		FileCopier.copy(projectFolder, projectFolder2);
		
		startAndWaitForLspServer();
		val myModule2 = new FileURI(new File(getRoot(), DEFAULT_PROJECT_NAME+"2/src/MyModule.n4js"));
		assertIssues(Collections.singletonMap(myModule2, #[
			"(Error, [0:0 - 0:11], Couldn't resolve reference to IdentifiableElement '_globalThis'.)"
		]));
	}
	
}
