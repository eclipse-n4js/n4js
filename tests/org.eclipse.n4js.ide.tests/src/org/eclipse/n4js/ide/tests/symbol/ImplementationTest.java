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
package org.eclipse.n4js.ide.tests.symbol;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.server.AbstractImplementationTest;
import org.eclipse.n4js.ide.tests.server.StringLSP4J;
import org.junit.Test;

/**
 * Test for navigation to implementation.
 */
public class ImplementationTest extends AbstractImplementationTest {

	/***/
	@Test
	public void testDefinition_01() throws Exception {
		testAtCursor(
				"var s: s<|>tring = ''; s.length;",
				"(test-project/src-gen/MyModule.js, [4:4 - 4:4])");
	}

	/***/
	@Test
	public void testDefinition_02() throws Exception {
		testAtCursor(
				"var s: string = ''; s<|>.length;",
				"(test-project/src-gen/MyModule.js, [5:0 - 5:0])");
	}

	/***/
	@Test
	public void testDefinition_03() throws Exception {
		testAtCursor(
				"var s: string = ''; s.le<|>ngth;",
				"(test-project/src-gen/MyModule.js, [5:0 - 5:0])");
	}

	/***/
	@Test
	public void testDefinition_04() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Collections.emptyMap());
		startAndWaitForLspServer();

		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier("n4scheme:/builtin_js.n4ts"));
		// see position from test above
		textDocumentPositionParams.setPosition(new Position(838, 15));
		CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definitionsFuture = languageServer
				.definition(textDocumentPositionParams);
		Either<List<? extends Location>, List<? extends LocationLink>> definitions = definitionsFuture.get();

		File root = getRoot();
		String actualSignatureHelp = new StringLSP4J(root).toString4(definitions);
		assertEquals("(n4scheme:/builtin_js.n4ts, [838:15 - 838:21])", actualSignatureHelp.trim());
	}

}
