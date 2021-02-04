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
package org.eclipse.n4js.ide.tests

import java.nio.file.Files
import java.util.concurrent.CompletableFuture
import org.eclipse.emf.common.util.URI
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentItem
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.xtext.ide.server.hover.IHoverService
import org.junit.Test

import static org.junit.Assert.*

/**
 * Ensures that the LSP server can cope with URIs that are not supported. Should simply ignore them,
 * without showing throwing exceptions, showing errors, etc.
 */
class UnsupportedURIsTest extends AbstractIdeTest {

	/** Simulates manually changing language mode of a plain-text file to "n4js". */
	@Test
	def void testChangeLanguageModeOfTextFile() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		val textFilePath = getProjectRoot().toPath.resolve(DEFAULT_SOURCE_FOLDER).resolve("Test.txt");
		Files.writeString(textFilePath, '''
			let s: string = 42;
		''');

		val uri = textFilePath.toFileURI;
		openFile(uri); // must not throw exceptions
		joinServerRequests();
		assertNoIssues(); // URI is ignored, so no issues are created
		assertNoErrorsInLogOrOutput();

		val response = hover(uri.toURI, 0, 10); // must not throw exceptions
		joinServerRequests();
		assertNoErrorsInLogOrOutput();
		assertFalse(response.cancelled);
		assertFalse(response.completedExceptionally); // unsupported URIs should be ignore (no errors should be shown to user)
		assertTrue(response.done);
		assertEquals(IHoverService.EMPTY_HOVER, response.get());

		changeOpenedFile(uri, "let" -> "// let"); // must not throw exceptions
		joinServerRequests();
		assertNoIssues();
		assertNoErrorsInLogOrOutput();

		saveOpenedFile(uri);
		closeFile(uri); // must not throw exceptions
		joinServerRequests();
		assertNoIssues();
		assertNoErrorsInLogOrOutput();
	}

	/** Simulates opening a new editor with CMD+N in VSCode and changing language mode to "n4js" before saving. */
	@Test
	def void testUntitledEditor() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		val uri = URI.createURI("untitled:Untitled-2");
		openFile(uri, "let s: string = 42;"); // must not throw exceptions
		joinServerRequests();
		assertNoIssues(); // URI is ignored, so no issues are created
		assertNoErrorsInLogOrOutput();

		val response = hover(uri, 0, 10); // must not throw exceptions
		joinServerRequests();
		assertNoErrorsInLogOrOutput();
		assertFalse(response.cancelled);
		assertFalse(response.completedExceptionally); // unsupported URIs should be ignore (no errors should be shown to user)
		assertTrue(response.done);
		assertEquals(IHoverService.EMPTY_HOVER, response.get());

		changeOpenedFile(uri, 2, "// "); // must not throw exceptions
		joinServerRequests();
		assertNoIssues();
		assertNoErrorsInLogOrOutput();

		closeFile(uri); // must not throw exceptions
		joinServerRequests();
		assertNoIssues();
		assertNoErrorsInLogOrOutput();
	}

	// because AbstractIdeTest#openFile(FileURI), etc. force us to use file URIs, we here need custom implementations:

	def private void openFile(URI uri, CharSequence content) {
		val textDocument = new TextDocumentItem(uri.toString(), languageInfo.getLanguageName(), 1, toUnixLineSeparator(content));
		languageServer.didOpen(new DidOpenTextDocumentParams(textDocument));
	}

	def private void changeOpenedFile(URI uri, int newVersion, CharSequence newText) {
		val docId = new VersionedTextDocumentIdentifier(uri.toString(), newVersion);
		val changes = #[ new TextDocumentContentChangeEvent(new Range(new Position(0, 0), new Position(0, 0)), 0, newText.toString) ];
		languageServer.didChange(new DidChangeTextDocumentParams(docId, changes));
	}

	def private void closeFile(URI uri) {
		languageServer.didClose(new DidCloseTextDocumentParams(new TextDocumentIdentifier(uri.toString())));
	}

	def private CompletableFuture<Hover> hover(URI uri, int line, int column) {
		return languageServer.hover(
			new TextDocumentPositionParams(
				new TextDocumentIdentifier(uri.toString()),
				new Position(line, column)));
	}
}
