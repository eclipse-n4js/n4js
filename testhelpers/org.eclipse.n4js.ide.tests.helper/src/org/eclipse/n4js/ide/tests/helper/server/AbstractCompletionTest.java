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
package org.eclipse.n4js.ide.tests.helper.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.TestCompletionConfiguration;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;

/**
 * Abstract test class for code action protocol tests
 */
abstract public class AbstractCompletionTest extends AbstractStructuredIdeTest<TestCompletionConfiguration> {

	/**
	 * Executes the given module contents using the default workspace. Expects the given proposals to be equal to the
	 * results.
	 */
	protected void testAtCursor(String codeWithCursor, String expectedProposals) {
		testAtCursor(codeWithCursor, expectedProposals, null);
	}

	/**
	 * Executes the given module contents using the default workspace. Expects the given proposals to be among the
	 * results.
	 */
	protected void testAtCursorPartially(String codeWithCursor, String partialExpectedProposals) {
		testAtCursor(codeWithCursor, null, partialExpectedProposals);
	}

	/** Executes the given module contents using the default workspace. */
	protected void testAtCursor(String codeWithCursor, String expectedProposals, String partialExpectedProposals) {
		ContentAndPosition contentAndPosition = getContentAndPosition(codeWithCursor);
		TestCompletionConfiguration tcc = createTestCompletionConfiguration(contentAndPosition, expectedProposals,
				partialExpectedProposals);
		super.testInDefaultWorkspace(contentAndPosition.content, tcc);
	}

	/** @return {@link TestCompletionConfiguration} for a given code with cursor symbol */
	protected TestCompletionConfiguration createTestCompletionConfiguration(ContentAndPosition contentAndPosition,
			String expectedProposals, String partialExpectedProposals) {

		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel(contentAndPosition.content);
		tcc.setLine(contentAndPosition.line);
		tcc.setColumn(contentAndPosition.column);
		tcc.setExpectedCompletionItems(expectedProposals);
		if (partialExpectedProposals != null) {
			tcc.setAssertCompletionList(completionList -> {
				List<CompletionItem> items = completionList.getItems();
				String resultStr = Strings.join("\n", getStringLSP4J()::toString, items);
				assertTrue("Result:\n" + resultStr + "\ndoes not contain:\n" + partialExpectedProposals,
						resultStr.contains(partialExpectedProposals));
			});
		}

		return tcc;
	}

	@Override
	protected void performTest(Project project, String moduleName, TestCompletionConfiguration tcc)
			throws InterruptedException, ExecutionException {

		CompletionParams completionParams = new CompletionParams();
		Position pos = new Position(tcc.getLine(), tcc.getColumn());
		completionParams.setPosition(pos);

		FileURI uri = getFileURIFromModuleName(tcc.getFilePath());
		TextDocumentIdentifier textDocument = new TextDocumentIdentifier();
		textDocument.setUri(uri.toString());
		completionParams.setTextDocument(textDocument);

		CompletableFuture<Either<List<CompletionItem>, CompletionList>> future = injEnv.languageServer
				.completion(completionParams);

		Either<List<CompletionItem>, CompletionList> result = future.get();
		List<CompletionItem> items = result.isLeft() ? result.getLeft() : result.getRight().getItems();

		// assert already sorted
		List<CompletionItem> sortedItems = ListExtensions.sortInplaceBy(items, CompletionItem::getSortText);
		Assert.assertEquals(items, sortedItems);

		if (tcc.getAssertCompletionList() != null) {
			tcc.getAssertCompletionList().apply(result.getRight());
		} else {
			String resultStr = Strings.join("\n", getStringLSP4J()::toString, items);
			assertEquals(tcc.getExpectedCompletionItems().trim(), resultStr.trim());
		}
	}

}
