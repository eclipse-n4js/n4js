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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractCodeActionTest.N4JSTestCodeActionConfiguration;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.testing.AbstractLanguageServerTest.TestCodeActionConfiguration;

import com.google.common.collect.Lists;

/**
 * Abstract test class for <u>low-level</u> code action protocol tests.
 * <p>
 * For more high-level tests related to code actions, consider sub-classing one of
 * <ul>
 * <li>{@link AbstractOrganizeImportsTest}.
 * </ul>
 */
abstract public class AbstractCodeActionTest extends AbstractStructuredIdeTest<N4JSTestCodeActionConfiguration> {

	/** Call this method in a test */
	protected void test(N4JSTestCodeActionConfiguration tcac) throws Exception {
		test(tcac.getFilePath(), tcac.getModel(), tcac);
	}

	@Override
	protected void performTest(Project project, String moduleName, N4JSTestCodeActionConfiguration tcac)
			throws InterruptedException, ExecutionException {

		CodeActionParams codeActionParams = new CodeActionParams();
		Range range = new Range();
		Position posStart = new Position(tcac.getLine(), tcac.getColumn());
		Position posEnd = tcac.getEndLine() >= 0 && tcac.getEndColumn() >= 0
				? new Position(tcac.getEndLine(), tcac.getEndColumn())
				: posStart;
		range.setStart(posStart);
		range.setEnd(posEnd);
		codeActionParams.setRange(range);

		CodeActionContext context = new CodeActionContext();
		FileURI uri = getFileURIFromModuleName(moduleName);
		context.setDiagnostics(Lists.newArrayList(getIssues(uri)));
		codeActionParams.setContext(context);

		TextDocumentIdentifier textDocument = new TextDocumentIdentifier();
		textDocument.setUri(uri.toString());
		codeActionParams.setTextDocument(textDocument);

		CompletableFuture<List<Either<Command, CodeAction>>> future = languageServer.codeAction(codeActionParams);
		List<Either<Command, CodeAction>> result = future.get();
		if (tcac.getAssertCodeActions() != null) {
			tcac.getAssertCodeActions().apply(result);
		} else {
			String resultStr = result.stream()
					.map(cmdOrAction -> getStringLSP4J().toString3(cmdOrAction))
					.collect(Collectors.joining("\n-----\n"));
			assertEquals(tcac.getExpectedCodeActions().trim(), resultStr.trim());
		}
	}

	/** Like {@link TestCodeActionConfiguration}, but non-empty ranges can be defined. */
	public static class N4JSTestCodeActionConfiguration extends TestCodeActionConfiguration {
		private int endLine = -1;
		private int endColumn = -1;

		/**
		 * The line of the end of the code action request's {@link CodeActionParams#getRange() range} (zero based). If
		 * undefined, {@link #getLine() line} will be used.
		 */
		public int getEndLine() {
			return endLine;
		}

		/**
		 * The line of the end of the code action request's {@link CodeActionParams#getRange() range} (zero based). If
		 * undefined, {@link #getLine() line} will be used.
		 */
		public void setEndLine(int endLine) {
			this.endLine = endLine;
		}

		/**
		 * The column of the end of the code action request's {@link CodeActionParams#getRange() range} (zero based). If
		 * undefined, {@link #getColumn() column} will be used.
		 */
		public int getEndColumn() {
			return endColumn;
		}

		/**
		 * The column of the end of the code action request's {@link CodeActionParams#getRange() range} (zero based). If
		 * undefined, {@link #getColumn() column} will be used.
		 */
		public void setEndColumn(int endColumn) {
			this.endColumn = endColumn;
		}
	}
}
