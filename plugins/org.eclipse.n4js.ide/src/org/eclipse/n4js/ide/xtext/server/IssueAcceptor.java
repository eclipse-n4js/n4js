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
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.validation.N4JSIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.ComparisonChain;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings("restriction")
@Singleton
public class IssueAcceptor {
	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private XWorkspaceManager workspaceManager;

	private LanguageClient client;

	/** Sets the client to communicate to */
	public void connect(LanguageClient languageClient) {
		this.client = languageClient;
	}

	/** Converts given issues to {@link Diagnostic}s and sends them to LSP client */
	public void publishDiagnostics(URI uri, Iterable<? extends Issue> issues) {
		PublishDiagnosticsParams publishDiagnosticsParams = new PublishDiagnosticsParams();
		publishDiagnosticsParams.setUri(uriExtensions.toUriString(uri));
		List<Diagnostic> diags = toDiagnostics(uri, issues);
		publishDiagnosticsParams.setDiagnostics(diags);
		client.publishDiagnostics(publishDiagnosticsParams);
	}

	/**
	 * Convert the given issues to diagnostics. Does not return issues in files that are neither in the workspace nor
	 * currently opened in the editor. Does not return any issue with severity {@link Severity#IGNORE ignore}.
	 */
	protected List<Diagnostic> toDiagnostics(URI uri, Iterable<? extends Issue> issues) {
		if (!workspaceManager.isDocumentOpen(uri)) {
			// Closed documents need to exist in the current workspace
			IProjectConfig projectConfig = workspaceManager.getWorkspaceConfig().findProjectContaining(uri);
			ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
			if (sourceFolder == null) {
				return Collections.emptyList();
			}
		}

		List<Diagnostic> sortedDiags = new ArrayList<>();
		for (Issue issue : issues) {
			if (issue.getSeverity() != Severity.IGNORE) {
				sortedDiags.add(toDiagnostic(issue));
			}
		}

		// Sort issues according to line and position
		final Comparator<Diagnostic> comparator = new Comparator<>() {
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

		Collections.sort(sortedDiags, comparator);

		return sortedDiags;
	}

	/**
	 * Convert the given issue to a diagnostic.
	 */
	protected Diagnostic toDiagnostic(Issue issue) {
		Diagnostic result = new Diagnostic();
		result.setCode(issue.getCode());
		result.setMessage(issue.getMessage());
		result.setSeverity(toDiagnosticSeverity(issue.getSeverity()));

		Position start = new Position(issue.getLineNumber(), issue.getColumn());
		Position end = null;
		if (issue instanceof N4JSIssue) {
			N4JSIssue n4jsIssue = (N4JSIssue) issue;
			end = new Position(n4jsIssue.getLineNumberEnd(), n4jsIssue.getColumnEnd());
		} else {
			URI uri = issue.getUriToProblem();
			Document doc = workspaceManager.getDocument(uri);
			doc.getPosition(issue.getOffset() + issue.getLength());
		}

		result.setRange(new Range(start, end));
		return result;
	}

	/**
	 * Convert the severity to a lsp {@link DiagnosticSeverity}.
	 *
	 * Defaults to severity {@link DiagnosticSeverity#Hint hint}.
	 */
	protected DiagnosticSeverity toDiagnosticSeverity(Severity severity) {
		switch (severity) {
		case ERROR:
			return DiagnosticSeverity.Error;
		case IGNORE:
			return DiagnosticSeverity.Hint;
		case INFO:
			return DiagnosticSeverity.Information;
		case WARNING:
			return DiagnosticSeverity.Warning;
		default:
			return DiagnosticSeverity.Hint;
		}
	}
}
