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
import org.eclipse.lsp4j.MessageType
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
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
		val fileA = getFileURIFromModuleName("A");
		assertNotNull("index should contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		val outputFileA = createSnapshotForOutputFile("A");

		openFile("Main");
		changeOpenedFile("Main", "meth(" -> "methx(");
		joinServerRequests();
		assertNoIssues();
		assertNotNull("index should contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		outputFileA.assertUnchanged();

		deleteFileOnDiskWithoutNotification("A");
		assertNotNull("index should contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		outputFileA.assertUnchanged();

		clearLogMessages();
		saveOpenedFile("Main");
		joinServerRequests();

		val logMsgs = getLogMessages();
		val logMsgsStr = getLogMessagesAsString();
		assertFalse(logMsgsStr, logMsgs.exists[type === MessageType.Error || type === MessageType.Warning]);
		assertFalse(logMsgsStr, logMsgs.exists[message.contains("FileNotFoundException")]);

		assertNull("index should no longer contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		outputFileA.assertNotExists();
	}

	@Test
	def void testFixFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProviderProject" -> #[
				"SomeModule" -> '''
					export public class SomeClassX {
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			],
			"ClientProject" -> #[
				"ClientModule" -> '''
					import {SomeClass} from "SomeModule";
					const x: SomeClass = null;
					x;
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					ProviderProject
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues("ClientModule" -> #[
			"(Error, [0:8 - 0:17], Import of SomeClass cannot be resolved.)",
			"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
		]);

		shutdownLspServer();
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClassX", "SomeClass"));


		startAndWaitForLspServer();
		// FIXME: GH-1860: should have no errors here already:
//		assertNoIssues();
		
		openFile("ClientModule");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testBreakFileBetweenServerSessions() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProviderProject" -> #[
				"SomeModule" -> '''
					export public class SomeClass {
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			],
			"ClientProject" -> #[
				"ClientModule" -> '''
					import {SomeClass} from "SomeModule";
					const x: SomeClass = null;
					x;
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					ProviderProject
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
		
		shutdownLspServer();
		changeFileOnDiskWithoutNotification("SomeModule", Pair.of("SomeClass", "SomeClassX"));


		startAndWaitForLspServer();
		// FIXME: GH-1860: should have errors here already:
//		assertIssues("ClientModule" -> #[
//			"(Error, [0:8 - 0:17], Import of SomeClass cannot be resolved.)",
//			"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
//		]);
		
		openFile("ClientModule");
		joinServerRequests();
		assertIssues("ClientModule" -> #[
			"(Error, [0:8 - 0:17], Import of SomeClass cannot be resolved.)",
			"(Error, [1:9 - 1:18], Couldn't resolve reference to Type 'SomeClass'.)"
		]);
	}

	@Test
	def void testDeletePlainJS() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"PlainJSModule.js" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val sourceFileURI = new File(new File(getProjectRoot(TestWorkspaceManager.DEFAULT_PROJECT_NAME), TestWorkspaceManager.DEFAULT_SOURCE_FOLDER), "PlainJSModule.js").toFileURI;
		val outputFile = new File(getOutputFolder(), "PlainJSModule.js");
		val outputFileURI = outputFile.toFileURI;

		assertTrue("output file should exist", outputFile.exists);
		assertContentOfFileOnDisk(outputFileURI, "console.log('hello');");

		deleteNonOpenedFile(sourceFileURI);
		joinServerRequests();

		assertFalse("output file should no longer exist", outputFile.exists);
	}
}
