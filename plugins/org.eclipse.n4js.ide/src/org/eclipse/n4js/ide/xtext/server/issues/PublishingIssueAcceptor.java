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
package org.eclipse.n4js.ide.xtext.server.issues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.UriExtensions;

import com.google.common.collect.ComparisonChain;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings("deprecation")
@Singleton
public class PublishingIssueAcceptor implements IssueAcceptor {

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private LSPIssueToLSPDiagnosticConverter diagnosticIssueConverter;

	private LanguageClient client;

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
	public void publishDiagnostics(URI uri, List<? extends LSPIssue> issues) {
		if (client != null) {
			PublishDiagnosticsParams publishDiagnosticsParams = new PublishDiagnosticsParams();
			publishDiagnosticsParams.setUri(uriExtensions.toUriString(uri));
			List<Diagnostic> diags = toSortedDiagnostics(issues);
			publishDiagnosticsParams.setDiagnostics(diags);
			client.publishDiagnostics(publishDiagnosticsParams);
		}
	}

	@Override
	public void accept(URI uri, List<? extends LSPIssue> issues) {
		publishDiagnostics(uri, issues);
	}

	/**
	 * Convert the given issues to diagnostics. Does not return issues in files that are neither in the workspace nor
	 * currently opened in the editor. Does not return any issue with severity {@link Severity#IGNORE ignore}.
	 */
	protected List<Diagnostic> toSortedDiagnostics(List<? extends LSPIssue> issues) {
		if (issues.isEmpty()) {
			return Collections.emptyList();
		}

		List<Diagnostic> sortedDiags = new ArrayList<>();
		for (LSPIssue issue : issues) {
			if (issue.getSeverity() != Severity.IGNORE) {
				sortedDiags.add(diagnosticIssueConverter.toDiagnostic(issue));
			}
		}

		// Sort issues according to line and position
		final Comparator<Diagnostic> comparator = (Diagnostic d1, Diagnostic d2) -> {
			Position p1 = d1.getRange().getStart();
			Position p2 = d2.getRange().getStart();
			int result = ComparisonChain.start()
					.compare(p1.getLine(), p2.getLine())
					.compare(p2.getCharacter(), p2.getCharacter())
					.result();
			return result;
		};

		Collections.sort(sortedDiags, comparator);

		return sortedDiags;
	}

}
