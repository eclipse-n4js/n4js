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

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.AbstractLanguageServerTest.TestCodeActionConfiguration;

import com.google.common.collect.Lists;

/**
 * Abstract test class for code action protocol tests
 */
abstract public class AbstractCodeActionTest extends AbstractIdeTest<TestCodeActionConfiguration> {

	/** Call this method in a test */
	protected void test(TestCodeActionConfiguration tcac) throws Exception {
		test(tcac.getFilePath(), tcac.getModel(), tcac);
	}

	@Override
	protected void performTest(File root, Project project, TestCodeActionConfiguration tcac)
			throws InterruptedException, ExecutionException {

		CodeActionParams codeActionParams = new CodeActionParams();
		Range range = new Range();
		Position pos = new Position(tcac.getLine(), tcac.getColumn());
		range.setStart(pos);
		range.setEnd(pos);
		codeActionParams.setRange(range);

		CodeActionContext context = new CodeActionContext();
		FileURI uri = getFileUriFromModuleName(root, tcac.getFilePath());
		context.setDiagnostics(Lists.newArrayList(getDiagnostics(uri)));

		CompletableFuture<List<Either<Command, CodeAction>>> future = languageServer.codeAction(codeActionParams);
		List<Either<Command, CodeAction>> result = future.get();
		if (tcac.getAssertCodeActions() != null) {
			tcac.getAssertCodeActions().apply(result);
		} else {
			String resultStr = Strings.toString(getStringLSP4J()::toString3, result);
			assertEquals(tcac.getExpectedCodeActions().trim(), resultStr.trim());
		}
	}

}
