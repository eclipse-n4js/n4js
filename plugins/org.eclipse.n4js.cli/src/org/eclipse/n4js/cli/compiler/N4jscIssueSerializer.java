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

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.n4js.ide.xtext.server.LSPBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Serializer for issues to be displayed on the user console. Uses 1-based positions.
 */
@Singleton
public class N4jscIssueSerializer {

	@Inject
	private LSPBuilder lspBuilder;

	/** @return user string for an issue */
	public String diagnostics(Diagnostic diagnostic) {
		String position = String.format("%d:%d",
				diagnostic.getRange().getStart().getLine() + 1,
				diagnostic.getRange().getStart().getCharacter() + 1);

		String s = String.format("  %s %-8s %s",
				getShortSeverity(diagnostic.getSeverity()),
				position,
				diagnostic.getMessage());

		return s;
	}

	private String getShortSeverity(DiagnosticSeverity severity) {
		switch (severity) {
		case Error:
			return "ERR";
		case Warning:
			return "WRN";
		case Information:
			return "INF";
		case Hint:
			return "HNT";
		}
		return "???";
	}

	/** @return user string for a file in the workspace */
	public String uri(String uri) {
		URI relativeUri = lspBuilder.makeWorkspaceRelative(URI.createURI(uri));
		return relativeUri.toFileString();
	}

}
