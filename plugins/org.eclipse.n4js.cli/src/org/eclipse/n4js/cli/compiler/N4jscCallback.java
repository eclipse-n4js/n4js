/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.compiler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;

import com.google.common.collect.ComparisonChain;

/**
 *
 */
public class N4jscCallback implements LanguageClient {

	@Override
	public void telemetryEvent(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		Comparator<Diagnostic> comparator = new Comparator<>() {
			@Override
			public int compare(Diagnostic d1, Diagnostic d2) {
				Position p1 = d1.getRange().getStart();
				Position p2 = d2.getRange().getStart();
				int result = ComparisonChain.start()
						.compare(p1.getLine(), p2.getLine())
						.compare(p2.getCharacter(), p2.getCharacter())
						.result();
				return result;
			}
		};

		Collections.sort(issueList, comparator);
		N4jscConsole.println(IssueSerializer.uri(diagnostics.getUri()));
		for (Diagnostic diag : issueList) {
			N4jscConsole.println(IssueSerializer.diagnostics(diag));
		}
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		N4jscConsole.println(messageParams.getMessage());
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logMessage(MessageParams message) {
		N4jscConsole.println(message.getMessage());

	}

}
