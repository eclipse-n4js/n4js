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
package org.eclipse.n4js.ide.xtext.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.n4js.ide.validation.N4JSIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

import com.google.common.base.Strings;

/**
 * Converter for Xtext {@link Issue}s and LSP {@link Diagnostic}s
 */
public class DiagnosticIssueConverter {

	/** Convert the given issue to a diagnostic. */
	// FIXME GH-1774 avoid using XWorkspaceManager for converting offset to/from line/column?
	public Diagnostic toDiagnostic(Issue issue, DocumentResourceProvider documentProvider) {
		Diagnostic result = new Diagnostic();

		result.setCode(issue.getCode());
		result.setMessage(Strings.nullToEmpty(issue.getMessage()));
		result.setSeverity(toSeverity(issue.getSeverity()));

		Position start = new Position(toZeroBasedInt(issue.getLineNumber()), toZeroBasedInt(issue.getColumn()));
		Position end = null;
		if (issue instanceof N4JSIssue) {
			N4JSIssue n4jsIssue = (N4JSIssue) issue;
			end = new Position(n4jsIssue.getLineNumberEnd() - 1, n4jsIssue.getColumnEnd() - 1);
		} else {
			URI uri = issue.getUriToProblem();
			Document doc = null;
			if (uri != null && documentProvider != null) {
				doc = documentProvider.getDocument(uri);
			}
			if (doc != null) {
				end = doc.getPosition(issue.getOffset() + issue.getLength());
			}
			if (end == null) {
				end = new Position(start.getLine(), start.getCharacter() + 1);
			}
		}

		result.setRange(new Range(start, end));
		return result;
	}

	// TODO: Remove this function when org.eclipse.xtext.validation.Issue.IssueImpl#lineNumber and #column are
	// initialized with '0'
	private int toZeroBasedInt(Integer oneBasedInteger) {
		return oneBasedInteger == null ? 0 : (oneBasedInteger - 1);
	}

	/**
	 * Convert the {@link Severity} to a lsp {@link DiagnosticSeverity}.
	 *
	 * Defaults to severity {@link DiagnosticSeverity#Hint}.
	 */
	protected DiagnosticSeverity toSeverity(Severity severity) {
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

	/**
	 * Convert the lsp {@link DiagnosticSeverity} to an Xtext {@link Severity}.
	 *
	 * Defaults to severity {@link Severity#IGNORE}.
	 */
	protected Severity toSeverity(DiagnosticSeverity severity) {
		switch (severity) {
		case Error:
			return Severity.ERROR;
		case Hint:
			return Severity.IGNORE;
		case Information:
			return Severity.INFO;
		case Warning:
			return Severity.WARNING;
		default:
			return Severity.IGNORE;
		}
	}

	/** Convert the given diagnostic to an issue. */
	public Issue toIssue(URI uri, Diagnostic diagnostic, DocumentResourceProvider documentProvider) {
		IssueImpl issue = new Issue.IssueImpl();

		issue.setSeverity(toSeverity(diagnostic.getSeverity()));

		Range range = diagnostic.getRange();
		Position sPos = range.getStart();
		Position ePos = range.getEnd();
		int offSetStart = 0;
		int offSetEnd = 0;
		if (documentProvider != null) {
			Document document = documentProvider.getDocument(URI.createURI(diagnostic.getSource()));
			offSetStart = document.getOffSet(new Position(sPos.getLine() + 1, sPos.getCharacter() + 1));
			offSetEnd = document.getOffSet(new Position(ePos.getLine() + 1, ePos.getCharacter() + 1));
		}

		issue.setLineNumber(sPos.getLine() + 1);
		issue.setColumn(sPos.getCharacter() + 1);
		issue.setOffset(offSetStart);
		issue.setLength(offSetEnd - offSetStart);

		issue.setUriToProblem(uri);
		issue.setCode(diagnostic.getCode());
		issue.setType(CheckType.FAST);
		issue.setMessage(diagnostic.getMessage());

		return issue;
	}

}
