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

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractDefinitionTest;
import org.eclipse.n4js.ide.tests.helper.server.StringLSP4J;
import org.junit.Test;

/**
 * Test for navigation to definition.
 */
public class DefinitionTest extends AbstractDefinitionTest {

	/**
	 * Zero-based line number of getter "length" in type "String" in file "builtin_js.n4jsd".
	 */
	public static final int STRING_LENGTH_LINE = 110;

	/***/
	@Test
	public void testDefinition_01() throws Exception {
		testAtCursor(
				"var s: s<|>tring = ''; s.length;",
				"(n4scheme:/primitives.n4jsd, [0:0 - 12:0])");
	}

	/***/
	@Test
	public void testDefinition_02() throws Exception {
		testAtCursor(
				"var s: string = ''; s<|>.length;",
				"(test-project/src/MyModule.n4js, [0:4 - 0:5])");
	}

	/***/
	@Test
	public void testDefinition_03() throws Exception {
		testAtCursor(
				"var s: string = ''; s.le<|>ngth;",
				"(n4scheme:/builtin_js.n4jsd, [" + STRING_LENGTH_LINE + ":12 - " + STRING_LENGTH_LINE + ":18])");
	}

	/***/
	@Test
	public void testDefinition_destruct_decl() throws Exception {
		testAtCursor(
				"class C { fieldA : string}\n const {field<|>A} = new C();",
				"(test-project/src/MyModule.n4js, [0:10 - 0:16])");
	}

	/***/
	@Test
	public void testDefinition_destruct_assign() throws Exception {
		testAtCursor(
				"class C { fieldA : string}\nlet fieldA = undefined;\n({field<|>A} = new C());",
				"(test-project/src/MyModule.n4js, [0:10 - 0:16])\n"
						+ "(test-project/src/MyModule.n4js, [1:4 - 1:10])");
	}

	/***/
	@Test
	public void testDefinition_04() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Collections.emptyMap());
		startAndWaitForLspServer();

		DefinitionParams definitionParams = new DefinitionParams();
		definitionParams.setTextDocument(new TextDocumentIdentifier("n4scheme:/builtin_js.n4jsd"));
		// see position from test above
		definitionParams.setPosition(new Position(STRING_LENGTH_LINE, 12));
		CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definitionsFuture = languageServer
				.definition(definitionParams);
		Either<List<? extends Location>, List<? extends LocationLink>> definitions = definitionsFuture.get();

		File root = getRoot();
		String actualSignatureHelp = new StringLSP4J(root).toString4(definitions);
		assertEquals("(n4scheme:/builtin_js.n4jsd, [" + STRING_LENGTH_LINE + ":12 - " + STRING_LENGTH_LINE + ":18])",
				actualSignatureHelp.trim());
	}

}
