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
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.junit.Assert;

import com.google.common.collect.Lists;

/**
 * Abstract test class for code action protocol tests
 */
abstract public class AbstractCompletionTest extends AbstractStructuredIdeTest<TestCompletionConfiguration> {

	static final String CURSOR_SYMBOL = "<|>";

	/** Default modules that export a number of classes, for use in the main module of organize imports tests. */
	protected List<Pair<String, String>> getDefaultTestModules() {
		return Lists.newArrayList();
	}

	/** Default workspace that export a number of classes, for use in the main module of organize imports tests. */
	protected List<Pair<String, List<Pair<String, String>>>> getDefaultTestYarnWorkspace() {
		return Lists.newArrayList();
	}

	/** Call this method in a test */
	protected void test(String codeWithCursor, String expectedProposals) {
		TestCompletionConfiguration tcc = createTestCompletionConfiguration(codeWithCursor, expectedProposals);
		String nameWithSelector = DEFAULT_MODULE_NAME + MODULE_SELECTOR;
		String testModuleContents = tcc.getModel();
		Pair<String, String> moduleContents = Pair.of(nameWithSelector, testModuleContents);

		boolean moduleAdded = false;
		if (!getDefaultTestYarnWorkspace().isEmpty()) {
			List<Pair<String, List<Pair<String, String>>>> workspace = getDefaultTestYarnWorkspace();
			for (Pair<String, List<Pair<String, String>>> project : workspace) {
				String projectName = project.getKey();
				if (projectName.endsWith(MODULE_SELECTOR)) {
					List<Pair<String, String>> modulesPlusMyModule = new ArrayList<>(project.getValue());
					modulesPlusMyModule.add(moduleContents);
					try {
						ReflectExtensions reflectExtensions = new ReflectExtensions();
						reflectExtensions.set(project, "k", projectName.substring(0, projectName.length() - 1));
						reflectExtensions.set(project, "v", modulesPlusMyModule);
						moduleAdded = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (!moduleAdded) {
				throw new IllegalStateException("No project selected. Use " + MODULE_SELECTOR);
			}

			testWS(workspace, tcc);
			return;

		} else {
			ArrayList<Pair<String, String>> allModules = Lists.newArrayList(moduleContents);
			allModules.addAll(getDefaultTestModules());
			test(allModules, tcc);
		}
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

		FileURI uri = getFileURIFromModuleName(tcc.getFilePath());
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
