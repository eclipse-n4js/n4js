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

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.testing.HoverTestConfiguration;

import com.google.common.base.Strings;

/**
 * Abstract test class for hover protocol tests
 */
abstract public class AbstractHoverTest extends AbstractIdeTest<HoverTestConfiguration> {

	/** Call this method in a test */
	protected void test(HoverTestConfiguration htc) throws Exception {
		test(htc.getFilePath(), htc.getModel(), htc);
	}

	@Override
	protected void performTest(File root, Project project, HoverTestConfiguration htc)
			throws InterruptedException, ExecutionException {

		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		String completeFileUri = getFileUriFromModuleName(root, htc.getFilePath()).toString();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(completeFileUri));
		textDocumentPositionParams.setPosition(new Position(htc.getLine(), htc.getColumn()));
		CompletableFuture<Hover> hoverFuture = languageServer.hover(textDocumentPositionParams);

		Hover hover = hoverFuture.get();
		if (htc.getAssertHover() != null) {
			htc.getAssertHover().apply(hover);
		} else {
			String actualSignatureHelp = toString(hover);
			assertEquals(htc.getExpectedHover().trim(), actualSignatureHelp.trim());
		}
	}

	String toString(Hover hover) {
		String str = toString(hover.getRange()) + " " + toString(hover.getContents());
		return str;
	}

	String toString(Range range) {
		if (range == null) {
			return "[null]";
		}
		Position start = range.getStart();
		Position end = range.getEnd();
		String stringPosStr = start.getLine() + ":" + start.getCharacter();
		String endPosStr = end.getLine() + ":" + end.getCharacter();
		return "[" + stringPosStr + " - " + endPosStr + "]";
	}

	String toString(Either<List<Either<String, MarkedString>>, MarkupContent> contents) {
		if (contents.isLeft()) {
			List<Either<String, MarkedString>> markedStrings = contents.getLeft();
			String str = "";
			for (Either<String, MarkedString> ms : markedStrings) {
				if (!Strings.isNullOrEmpty(str)) {
					str += ", ";
				}
				if (ms.isLeft()) {
					str += ms.getLeft();
				} else {
					MarkedString markedStr = ms.getRight();
					str += "[" + markedStr.getLanguage() + "] " + markedStr.getValue();
				}
			}

			return str;

		} else {
			MarkupContent markupContent = contents.getRight();
			return "[" + markupContent.getKind() + "] " + markupContent.getValue();
		}
	}

}
