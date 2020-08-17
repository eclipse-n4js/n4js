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
package org.eclipse.n4js.ide.tests.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.server.AbstractRenameTest.RenameTestConfiguration;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Abstract test class for rename protocol tests.
 */
abstract public class AbstractRenameTest extends AbstractStructuredIdeTest<RenameTestConfiguration> {

	public static class RenameTestConfiguration {
		final String sourceBefore;
		final List<Position> positions;
		final String newName;
		final String expectedSourceAfter;

		public RenameTestConfiguration(String sourceBefore, List<Position> positions, String newName,
				String sourceAfter) {
			this.sourceBefore = sourceBefore;
			this.positions = ImmutableList.copyOf(positions);
			this.newName = newName;
			this.expectedSourceAfter = sourceAfter;
		}

	}

	/** Call this method in a test */
	protected void testAtCursors(CharSequence sourceBefore, String newName, CharSequence sourceAfter) {
		List<ContentAndPosition> contentAndPositions = getContentAndPositions(sourceBefore.toString());
		String content = contentAndPositions.get(0).content;
		List<Position> positions = contentAndPositions.stream()
				.map(cap -> new Position(cap.line, cap.column))
				.collect(Collectors.toList());

		RenameTestConfiguration config = new RenameTestConfiguration(content, positions, newName,
				sourceAfter.toString());

		test(config.sourceBefore, config);
	}

	@Override
	protected void performTest(Project project, String moduleName, RenameTestConfiguration rtc)
			throws InterruptedException, ExecutionException, URISyntaxException {

		String completeFileUri = getFileURIFromModuleName(TestWorkspaceManager.DEFAULT_MODULE_NAME).toString();
		for (Position pos : rtc.positions) {
			performTestAtPosition(completeFileUri, rtc.sourceBefore, pos, rtc.newName, rtc.expectedSourceAfter);
			joinServerRequests();
		}
	}

	protected void performTestAtPosition(String fileURI, String sourceBefore, Position pos, String newName,
			String expectedSourceAfter) throws InterruptedException, ExecutionException {
		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(fileURI));
		textDocumentPositionParams.setPosition(pos);
		Either<Range, PrepareRenameResult> result1 = languageServer.prepareRename(textDocumentPositionParams).get();
		assertTrue("element cannot be renamed",
				result1 != null && (result1.getLeft() != null || result1.getRight() != null));

		RenameParams renameParams = new RenameParams();
		renameParams.setTextDocument(new TextDocumentIdentifier(fileURI));
		renameParams.setPosition(pos);
		renameParams.setNewName(newName);
		WorkspaceEdit workspaceEdit = languageServer.rename(renameParams).get();

		List<String> unexpectedURIs = workspaceEdit.getChanges().keySet().stream()
				.filter(uri -> !fileURI.equals(uri))
				.collect(Collectors.toList());
		assertTrue("rename led to workspace edits in unexpected URIs: " + Joiner.on(", ").join(unexpectedURIs),
				unexpectedURIs.isEmpty());

		List<TextEdit> edits = IterableExtensions.head(workspaceEdit.getChanges().values());
		String actualSourceAfter = applyTextEdits(sourceBefore, edits);
		assertEquals("rename led to incorrect source code changes", expectedSourceAfter, actualSourceAfter);
	}
}
