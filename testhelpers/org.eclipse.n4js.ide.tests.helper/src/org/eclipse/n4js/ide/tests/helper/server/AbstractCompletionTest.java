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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest.N4JSTestCompletionConfiguration;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.TestCompletionConfiguration;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;

/**
 * Abstract test class for code action protocol tests
 */
abstract public class AbstractCompletionTest extends AbstractStructuredIdeTest<N4JSTestCompletionConfiguration> {

	/**
	 * The string to add before an expected completion in
	 * {@link TestCompletionConfiguration#getExpectedCompletionItems() expectedCompletionItems} to mark the proposal to
	 * be applied.
	 */
	public static final String APPLY = "==>";

	public static class N4JSTestCompletionConfiguration extends TestCompletionConfiguration {

		private String expectedCodeAfterApply = null;

		public String getExpectedCodeAfterApply() {
			return this.expectedCodeAfterApply;
		}

		public void setExpectedCodeAfterApply(String codeAfterApply) {
			this.expectedCodeAfterApply = codeAfterApply;
		}
	}

	/**
	 * Executes the given module contents using the default workspace. Expects the given proposals to be equal to the
	 * results.
	 */
	protected void testAtCursor(String codeWithCursor, String expectedProposals) {
		doTestWithCursor(codeWithCursor, expectedProposals, null, null);
	}

	/**
	 * Executes the given module contents using the default workspace. Expects the given proposals to be among the
	 * results.
	 */
	protected void testAtCursorPartially(String codeWithCursor, String partialExpectedProposals) {
		doTestWithCursor(codeWithCursor, null, partialExpectedProposals, null);
	}

	protected void testAtCursorWithApply(String codeWithCursor, String expectedProposals,
			String expectedCodeAfterApply) {
		doTestWithCursor(codeWithCursor, expectedProposals, null, expectedCodeAfterApply);
	}

	private void doTestWithCursor(String codeWithCursor, String expectedProposals, String partialExpectedProposals,
			String expectedCodeAfterApply) {
		ContentAndPosition contentAndPosition = getContentAndPosition(codeWithCursor);
		N4JSTestCompletionConfiguration tcc = createTestCompletionConfiguration(contentAndPosition, expectedProposals,
				partialExpectedProposals, expectedCodeAfterApply);
		super.testInDefaultWorkspace(contentAndPosition.content, tcc);
	}

	/** @return {@link TestCompletionConfiguration} for a given code with cursor symbol */
	protected N4JSTestCompletionConfiguration createTestCompletionConfiguration(ContentAndPosition contentAndPosition,
			String expectedProposals, String partialExpectedProposals, String codeAfterApply) {

		N4JSTestCompletionConfiguration tcc = new N4JSTestCompletionConfiguration();
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
		tcc.setExpectedCodeAfterApply(codeAfterApply);

		return tcc;
	}

	@Override
	protected void performTest(Project project, String moduleName, N4JSTestCompletionConfiguration tcc)
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
			if (tcc.getExpectedCodeAfterApply() != null) {
				Assert.fail("assertCompletionList must not be combined with expectedCodeAfterApply");
			}

			tcc.getAssertCompletionList().apply(result.getRight());
		} else {
			String actualItemsStr = Strings.join("\n", getStringLSP4J()::toString, items);
			String expectedItemsStr = tcc.getExpectedCompletionItems();
			String expectedItemsStrNoApplyMarker = expectedItemsStr.replaceFirst(
					"(^|\\n)\\s*" + Pattern.quote(APPLY), "");

			assertEquals(expectedItemsStrNoApplyMarker.trim(), actualItemsStr.trim());

			String expectedCodeAfterApply = tcc.getExpectedCodeAfterApply();
			CompletionItem itemToBeApplied = getCompletionItemToBeApplied(sortedItems, tcc);
			if (expectedCodeAfterApply != null && itemToBeApplied != null) {
				String actualCodeAfterApply = applyCompletionItem(tcc.getModel(), pos, itemToBeApplied);
				String msg = "application of completion item did not yield correct result\n"
						+ "EXPECTED:\n"
						+ expectedCodeAfterApply + "\n"
						+ "ACTUAL:\n"
						+ actualCodeAfterApply;
				Assert.assertEquals(msg, expectedCodeAfterApply.trim(), actualCodeAfterApply.trim());
			} else if (expectedCodeAfterApply == null && itemToBeApplied != null) {
				Assert.fail("a completion item was marked for application with " + APPLY
						+ ", but no expected code after application was given");
			} else if (expectedCodeAfterApply != null && itemToBeApplied == null) {
				Assert.fail("expected code after application was given "
						+ "but no completion item was marked for application with " + APPLY);
			}
		}
	}

	private CompletionItem getCompletionItemToBeApplied(List<CompletionItem> items,
			N4JSTestCompletionConfiguration tcc) {
		String expectedItemsStr = tcc.getExpectedCompletionItems();
		Matcher m = Pattern.compile("(^|\\n)\\s*" + Pattern.quote(APPLY) + "([^\\n]+)($|\\n)")
				.matcher(expectedItemsStr.trim());
		boolean b = m.find();
		String itemToBeAppliedStr = b ? m.group(2).trim() : null;
		if (itemToBeAppliedStr == null) {
			return null;
		}
		CompletionItem itemToBeApplied = IterableExtensions.findFirst(items,
				i -> getStringLSP4J().toString(i).trim().startsWith(itemToBeAppliedStr));
		if (itemToBeApplied == null) {
			Assert.fail("the item that was marked for application with " + APPLY + " was not found");
		}
		return itemToBeApplied;
	}

	private String applyCompletionItem(String oldContent, Position pos, CompletionItem item) {
		String content = oldContent;

		TextEdit mainEdit = item.getTextEdit();
		if (mainEdit != null) {
			content = applyTextEdits(content, Collections.singleton(mainEdit));
		} else {
			String toInsert = item.getInsertText() != null ? item.getInsertText() : item.getLabel();
			if (toInsert != null) {
				XDocument doc = new XDocument(0, content.toString(), true, true);
				doc.applyChanges(Collections.singleton(new TextEdit(new Range(pos, pos), toInsert)));
				content = doc.getContents();
			}
		}

		List<TextEdit> additionalEdits = item.getAdditionalTextEdits();
		if (additionalEdits != null && !additionalEdits.isEmpty()) {
			content = applyTextEdits(content, additionalEdits);
		}

		return content;
	}
}
