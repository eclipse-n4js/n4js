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
package org.eclipse.n4js.xtext.ide.server.issues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class PublishingIssueAcceptor implements IssueAcceptor {

	/***/
	@Inject
	protected UriExtensions uriExtensions;
	/***/
	@Inject
	protected IssueToDiagnosticConverter diagnosticIssueConverter;
	/***/
	protected LanguageClient client;

	/** Sets the client to communicate to */
	public void connect(LanguageClient languageClient) {
		this.client = languageClient;
	}

	/**
	 * The issue acceptor is disconnected on a clients message to shutdown the connection.
	 */
	public void disconnect() {
		this.client = null;
	}

	/** Converts given issues to {@link Diagnostic}s and sends them to LSP client */
	@Override
	public void accept(URI uri, List<? extends Issue> issues) {
		if (client != null) {
			PublishDiagnosticsParams publishDiagnosticsParams = new PublishDiagnosticsParams();
			publishDiagnosticsParams.setUri(uriExtensions.toUriString(uri));
			List<Diagnostic> diags = toDiagnostics(issues);
			publishDiagnosticsParams.setDiagnostics(diags);
			client.publishDiagnostics(publishDiagnosticsParams);
		}
	}

	/**
	 * Convert the given issues to diagnostics. Does not return any issue with severity {@link Severity#IGNORE ignore}.
	 */
	protected List<Diagnostic> toDiagnostics(List<? extends Issue> issues) {
		if (issues.isEmpty()) {
			return Collections.emptyList();
		}

		List<Diagnostic> result = new ArrayList<>();
		for (Issue issue : issues) {
			if (issue.getSeverity() != Severity.IGNORE) {
				result.add(diagnosticIssueConverter.toDiagnostic(issue));
			}
		}
		return result;
	}

}
