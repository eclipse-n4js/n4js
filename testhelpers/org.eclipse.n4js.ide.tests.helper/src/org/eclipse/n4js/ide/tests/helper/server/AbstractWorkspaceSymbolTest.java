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

import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractWorkspaceSymbolTest.WorkspaceSymbolConfig;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;

/**
 * Abstract test class for definition protocol tests.
 */
@SuppressWarnings("deprecation")
abstract public class AbstractWorkspaceSymbolTest extends AbstractStructuredIdeTest<WorkspaceSymbolConfig> {

	/***/
	public static class WorkspaceSymbolConfig {
		WorkspaceSymbolParams params;
		String expectation;

		WorkspaceSymbolConfig(WorkspaceSymbolParams params, String expectation) {
			this.params = params;
			this.expectation = expectation;
		}
	}

	/** Call this method in a test */
	protected void test(String content, String expectation) throws Exception {
		test(content, new WorkspaceSymbolConfig(new WorkspaceSymbolParams(""), expectation));
	}

	@Override
	protected void performTest(Project project, String moduleName, WorkspaceSymbolConfig wsc)
			throws InterruptedException, ExecutionException, URISyntaxException {

		CompletableFuture<Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>>> result = languageServer
				.symbol(wsc.params);
		Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>> symbolsLists = result.get();
		List<? extends SymbolInformation> symbols = symbolsLists.getLeft();

		String actualSymbols = Strings.join(", ", s -> getStringLSP4J().toString(s), symbols);
		assertEquals(wsc.expectation, actualSymbols);
	}

}