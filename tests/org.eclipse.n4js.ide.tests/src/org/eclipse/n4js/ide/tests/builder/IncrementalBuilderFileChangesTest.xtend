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

import org.eclipse.lsp4j.MessageType
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests incremental builds triggered by changes not inside source files but to the files themselves,
 * e.g. creating, deleting, or renaming source files.
 */
class IncrementalBuilderFileChangesTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testOpenNewlyCreatedFile() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				export public class Cls {}
			'''
		);
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
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				import {Cls} from "Main"
				new Cls();
			''',
			"Main" -> '''
				export public class Cls {}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		renameFileOnDiskWithoutNotification("A", "B.n4js");
		openFile("B");
		joinServerRequests();

		// import in renamed file must be resolvable, even though the LSP builder was not
		// triggered yet through a didSave or didChangeWatchedFiles notification:
		assertNoIssues();
	}

	@Test
	def void testBuildDeletedFile() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				import {Cls} from "Main"
				new Cls().meth();
			''',
			"Main" -> '''
				export public class Cls {
					public meth() {}
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main");
		changeOpenedFile("Main", "meth(" -> "methx(");
		joinServerRequests();
		assertNoIssues();

		deleteFileOnDiskWithoutNotification("A");

		clearLogMessages();
		saveOpenedFile("Main");
		joinServerRequests();
		val logMsgs = getLogMessages();
		val logMsgsStr = getLogMessagesAsString();
		assertFalse(logMsgsStr, logMsgs.exists[type === MessageType.Error || type === MessageType.Warning]);
		assertFalse(logMsgsStr, logMsgs.exists[message.contains("FileNotFoundException")]);
	}
}
