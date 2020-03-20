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

	static final String CURSOR_SYMBOL = "<|>";

	/** Call this method in a test */
	protected void test(String modelWithCursor, String expectation) {
		int cursorIdx = modelWithCursor.indexOf(CURSOR_SYMBOL);
		if (cursorIdx < 0) {
			throw new IllegalArgumentException("Cursor symbol " + CURSOR_SYMBOL + " missing");
		}

		String model = modelWithCursor.replace(CURSOR_SYMBOL, "");
		String[] lines = model.substring(0, cursorIdx).replaceAll("\r", "").split("\n");
		int lineCountBeforeCursor = lines.length - 1;
		int columnBeforeCursor = lines[lineCountBeforeCursor].length();

		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel(model);
		tcc.setLine(lineCountBeforeCursor);
		tcc.setColumn(columnBeforeCursor);
		tcc.setExpectedCompletionItems(expectation);

		test(tcc.getFilePath(), tcc.getModel(), tcc);
	}

	@Override
	protected void performTest(Project project, String moduleName, TestCompletionConfiguration tcc)
			throws InterruptedException, ExecutionException {

		CompletionParams completionParams = new CompletionParams();
		Position pos = new Position(tcc.getLine(), tcc.getColumn());
		completionParams.setPosition(pos);

		// CompletionContext context = new CompletionContext();
		FileURI uri = getFileURIFromModuleName(tcc.getFilePath());
		// context.set(Lists.newArrayList(getDiagnostics(uri)));
		// completionParams.setContext(context);

		TextDocumentIdentifier textDocument = new TextDocumentIdentifier();
		textDocument.setUri(uri.toString());
		completionParams.setTextDocument(textDocument);

		CompletableFuture<Either<List<CompletionItem>, CompletionList>> future = languageServer
				.completion(completionParams);

		Either<List<CompletionItem>, CompletionList> result = future.get();
		List<CompletionItem> items = result.isLeft() ? result.getLeft() : result.getRight().getItems();

		// assert already sorted
		List<CompletionItem> sortedItems = ListExtensions.sortInplaceBy(items, CompletionItem::getSortText);
		Assert.assertEquals(items, sortedItems);

		if (tcc.getAssertCompletionList() != null) {
			tcc.getAssertCompletionList().apply(result.getRight());
		} else {
			String resultStr = Strings.toString(getStringLSP4J()::toString, items);
			assertEquals(tcc.getExpectedCompletionItems().trim(), resultStr.trim());
		}
	}

}
