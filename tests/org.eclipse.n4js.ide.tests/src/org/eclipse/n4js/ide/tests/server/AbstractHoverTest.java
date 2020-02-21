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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.testing.HoverTestConfiguration;

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
			String actualSignatureHelp = getStringLSP4J().toString(hover);
			assertEquals(htc.getExpectedHover().trim(), actualSignatureHelp.trim());
		}
	}

}
