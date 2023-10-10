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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests incremental builds triggered by changes not inside source files but to the files themselves, e.g. creating,
 * deleting, or renaming source files.
 */

public class IncrementalBuilderFileChangesTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testOpenNewlyCreatedFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Main", """
							export public class Cls {}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		createFileOnDiskWithoutNotification("Main", "A.n4js", """
					import {Cls} from "Main"
					new Cls();
				""");
		openFile("A");
		joinServerRequests();

		// import in newly created file must be resolvable, even though the LSP builder was not
		// triggered yet through a didSave or didChangeWatchedFiles notification:
		assertNoIssues();
	}

	@Test
	public void testOpenRenamedFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							import {Cls} from "Main"
							new Cls();
						""",
				"Main", """
							export public class Cls {}
						"""));
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
	public void testBuildDeletedFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							import {Cls} from "Main"
							new Cls().meth();
						""",
				"Main", """
							export public class Cls {
								public meth() {}
							}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();
		FileURI fileA = getFileURIFromModuleName("A");
		assertNotNull("index should contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		FileSnapshot outputFileA = createSnapshotForOutputFile("A");

		openFile("Main");
		changeOpenedFile("Main", Pair.of("meth(", "methx("));
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

		List<MessageParams> logMsgs = getLogMessages();
		String logMsgsStr = getLogMessagesAsString();

		assertFalse(logMsgsStr, logMsgs.stream()
				.anyMatch((mp) -> mp.getType() == MessageType.Error || mp.getType() == MessageType.Warning));
		assertFalse(logMsgsStr, logMsgs.stream().anyMatch((mp) -> mp.getMessage().contains("FileNotFoundException")));

		assertNull("index should no longer contain an entry for A.n4js", getResourceDescriptionFromIndex(fileA));
		outputFileA.assertNotExists();
	}

	@Test
	public void testDeletePlainJS() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"PlainJSModule.js", """
							console.log('hello');
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI sourceFileURI = toFileURI(
				new File(new File(getProjectRoot(DEFAULT_PROJECT_NAME), DEFAULT_SOURCE_FOLDER), "PlainJSModule.js"));
		File outputFile = new File(getOutputFolder(), "PlainJSModule.js");
		FileURI outputFileURI = toFileURI(outputFile);

		assertTrue("output file should exist", outputFile.exists());
		assertContentOfFileOnDisk(outputFileURI, "console.log('hello');");

		deleteNonOpenedFile(sourceFileURI);
		joinServerRequests();

		assertFalse("output file should no longer exist", outputFile.exists());
	}
}
