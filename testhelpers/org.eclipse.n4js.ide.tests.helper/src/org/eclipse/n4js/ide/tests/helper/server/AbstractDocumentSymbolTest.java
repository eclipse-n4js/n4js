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

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractDocumentSymbolTest.DocumentSymbolConfig;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;

/**
 * Abstract test class for definition protocol tests.
 */
abstract public class AbstractDocumentSymbolTest extends AbstractStructuredIdeTest<DocumentSymbolConfig> {

	/***/
	public static class DocumentSymbolConfig {
		String expectation;

		DocumentSymbolConfig(String expectation) {
			this.expectation = expectation;
		}

		DocumentSymbolParams getParams(AbstractIdeTest ideTest) {
			String uriStr = ideTest.getFileURIFromModuleName(DEFAULT_MODULE_NAME).toString();
			TextDocumentIdentifier tdi = new TextDocumentIdentifier(uriStr);
			return new DocumentSymbolParams(tdi);
		}
	}

	/** Call this method in a test */
	protected void test(String content, String expectation) throws Exception {
		test(content, new DocumentSymbolConfig(expectation));
	}

	@Override
	protected void performTest(Project project, String moduleName, DocumentSymbolConfig dsc)
			throws InterruptedException, ExecutionException, URISyntaxException {

		CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> result = languageServer
				.documentSymbol(dsc.getParams(this));
		List<Either<SymbolInformation, DocumentSymbol>> symbols = result.get();

		String actualSymbols = Strings.join("\n", s -> getStringLSP4J().toString10(s), symbols);
		assertEquals(dsc.expectation, actualSymbols);
	}

}