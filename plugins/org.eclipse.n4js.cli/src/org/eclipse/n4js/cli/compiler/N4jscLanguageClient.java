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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Overrides the lsp {@link LanguageClient} callback when used as a CLI utility
 */
@Singleton
public class N4jscLanguageClient implements LanguageClient {

	/***/
	@Inject
	protected IssueSerializer issueSerializer;

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

		N4jscConsole.println(issueSerializer.uri(diagnostics.getUri()));
		for (Diagnostic diag : issueList) {
			N4jscConsole.println(issueSerializer.diagnostics(diag));
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
