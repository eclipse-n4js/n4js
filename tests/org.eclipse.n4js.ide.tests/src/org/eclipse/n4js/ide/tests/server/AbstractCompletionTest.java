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

import java.util.ArrayList;
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
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Abstract test class for code action protocol tests
 */
abstract public class AbstractCompletionTest extends AbstractStructuredIdeTest<TestCompletionConfiguration> {

	static final String CURSOR_SYMBOL = "<|>";

	/** Some default modules that export a number of classes, for use in the main module of organize imports tests. */
	protected List<Pair<String, String>> getDefaultTestModules() {
		return Lists.newArrayList();
	}

	/** Call this method in a test */
	protected void test(String codeWithCursor, String expectedProposals) {
		TestCompletionConfiguration tcc = createTestCompletionConfiguration(codeWithCursor, expectedProposals);
		ArrayList<Pair<String, String>> defaultModule = Lists.newArrayList(Pair.of(MODULE_NAME, tcc.getModel()));
		Iterable<Pair<String, String>> modules = Iterables.concat(getDefaultTestModules(), defaultModule);
		test(modules, tcc);
	}

	/** @return {@link TestCompletionConfiguration} for a given code with cursor symbol */
	protected TestCompletionConfiguration createTestCompletionConfiguration(String codeWithCursor,
			String expectedProposals) {

		int cursorIdx = codeWithCursor.indexOf(CURSOR_SYMBOL);
		if (cursorIdx < 0) {
			throw new IllegalArgumentException("Cursor symbol " + CURSOR_SYMBOL + " missing");
		}

		String model = codeWithCursor.replace(CURSOR_SYMBOL, "");
		String[] lines = model.substring(0, cursorIdx).replaceAll("\r", "").split("\n");
		int lineCountBeforeCursor = lines.length - 1;
		int columnBeforeCursor = lines[lineCountBeforeCursor].length();

		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel(model);
		tcc.setLine(lineCountBeforeCursor);
		tcc.setColumn(columnBeforeCursor);
		tcc.setExpectedCompletionItems(expectedProposals);

		return tcc;
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
			String resultStr = Strings.join("\n", getStringLSP4J()::toString, items);
			assertEquals(tcc.getExpectedCompletionItems().trim(), resultStr.trim());
		}
	}

}
