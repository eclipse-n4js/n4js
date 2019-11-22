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
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Serializer for issues to be displayed on the user console
 */
@Singleton
public class IssueSerializer {

	@Inject
	XWorkspaceManager workspaceManager;

	/** @return user string for an issue */
	public String diagnostics(Diagnostic diagnostic) {
		String s = "   ";
		s += "[" + diagnostic.getSeverity() + "]";
		s += " (" + diagnostic.getRange().getStart().getLine();
		s += ":" + diagnostic.getRange().getStart().getCharacter();
		s += "): " + diagnostic.getMessage();
		return s;
	}

	/** @return user string for a file in the workspace */
	public String uri(String uri) {
		URI relativeUri = workspaceManager.makeWorkspaceRelative(URI.createURI(uri));
		return relativeUri.toFileString();
	}

}
