/**
 * Copyright (c) 2023 NumberFour AG.
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

import org.eclipse.lsp4j.CallHierarchyIncomingCall;
import org.eclipse.lsp4j.CallHierarchyIncomingCallsParams;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.CallHierarchyOutgoingCall;
import org.eclipse.lsp4j.CallHierarchyOutgoingCallsParams;
import org.eclipse.lsp4j.CallHierarchyPrepareParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.n4js.ide.tests.helper.server.AbstractCallHierarchyTest.CallHierarchyConfig;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;

/**
 * Abstract test class for type hierarchy protocol tests.
 */
abstract public class AbstractCallHierarchyTest extends AbstractStructuredIdeTest<CallHierarchyConfig> {

	
	public static class CallHierarchyConfig {
		final String expectation;
		final Position position;
		final boolean testIncomingCalls; // iff false: test outgoing calls

		CallHierarchyConfig(String expectation, Position position, boolean testIncomingCalls) {
			this.expectation = expectation;
			this.position = position;
			this.testIncomingCalls = testIncomingCalls;
		}

		CallHierarchyPrepareParams getParams(AbstractIdeTest ideTest) {
			String uriStr = ideTest.getFileURIFromModuleName(DEFAULT_MODULE_NAME).toString();
			CallHierarchyPrepareParams chpp = new CallHierarchyPrepareParams();
			chpp.setPosition(position);
			chpp.setTextDocument(new TextDocumentIdentifier(uriStr));
			return chpp;
		}
	}

	/** Call this method in a test */
	protected void testIncomingCallsAtCursor(String content, String expectation) throws Exception {
		ContentAndPosition contentAndPosition = getContentAndPosition(content);
		Position position = new Position(contentAndPosition.line, contentAndPosition.column);
		test(contentAndPosition.content, new CallHierarchyConfig(expectation, position, true));
	}

	/** Call this method in a test */
	protected void testOutgoingCallsAtCursor(String content, String expectation) throws Exception {
		ContentAndPosition contentAndPosition = getContentAndPosition(content);
		Position position = new Position(contentAndPosition.line, contentAndPosition.column);
		test(contentAndPosition.content, new CallHierarchyConfig(expectation, position, false));
	}

	@Override
	protected void performTest(Project project, String moduleName, CallHierarchyConfig thc)
			throws InterruptedException, ExecutionException, URISyntaxException {

		CallHierarchyPrepareParams thpp = thc.getParams(this);
		CompletableFuture<List<CallHierarchyItem>> result1 = languageServer.prepareCallHierarchy(thpp);
		List<CallHierarchyItem> items = result1.get();
		assertEquals(1, items.size());

		if (thc.testIncomingCalls) {
			CallHierarchyIncomingCallsParams chicp = new CallHierarchyIncomingCallsParams(items.get(0));
			CompletableFuture<List<CallHierarchyIncomingCall>> result2 = languageServer
					.callHierarchyIncomingCalls(chicp);
			List<CallHierarchyIncomingCall> incomingCalls = result2.get();

			String actualSymbols = Strings.join("\n", s -> getStringLSP4J().toString(s), incomingCalls);
			assertEquals(thc.expectation, actualSymbols);
		} else {
			CallHierarchyOutgoingCallsParams chocp = new CallHierarchyOutgoingCallsParams(items.get(0));
			CompletableFuture<List<CallHierarchyOutgoingCall>> result2 = languageServer
					.callHierarchyOutgoingCalls(chocp);
			List<CallHierarchyOutgoingCall> outgoingCalls = result2.get();

			String actualSymbols = Strings.join("\n", s -> getStringLSP4J().toString(s), outgoingCalls);
			assertEquals(thc.expectation, actualSymbols);
		}

	}

}