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

import org.junit.Test

/**
 * Tests incremental builds triggered by changes not inside source files but to the files themselves,
 * e.g. creating, deleting, or renaming source files.
 */
class IncrementalBuilderFileChangesTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testOpenNewlyCreatedFile() {
		testWorkspaceManager.createTestProjectOnDisk(#[
			"Main" -> '''
				export public class Cls {}
			'''
		]);
		startAndWaitForLspServer();
		assertNoIssues();

		createFileOnDiskWithoutNotification("Main", "A.n4js", '''
			import {Cls} from "Main"
			new Cls();
		''');
		openFile("A");
		joinServerRequests();

		// import in newly created file must be resolvable, even though the LSP builder was not
		// triggered yet through a didSave or didChangeWatchedFiles notification:
		assertNoIssues();
	}

	@Test
	def void testOpenRenamedFile() {
		testWorkspaceManager.createTestProjectOnDisk(#[
			"A" -> '''
				import {Cls} from "Main"
				new Cls();
			''',
			"Main" -> '''
				export public class Cls {}
			'''
		]);
		startAndWaitForLspServer();
		assertNoIssues();

		renameFileOnDiskWithoutNotification("A", "B.n4js");
		openFile("B");
		joinServerRequests();

		// import in renamed file must be resolvable, even though the LSP builder was not
		// triggered yet through a didSave or didChangeWatchedFiles notification:
		assertNoIssues();
	}
}
