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

import org.eclipse.lsp4j.Diagnostic;

/**
 *
 */
public class IssueSerializer {

	static public String diagnostics(Diagnostic diagnostic) {
		String s = "   ";
		s += "[" + diagnostic.getSeverity() + "]";
		s += " (" + diagnostic.getRange().getStart().getLine();
		s += ":" + diagnostic.getRange().getStart().getCharacter();
		s += "): " + diagnostic.getMessage();
		return s;
	}

	public static String uri(String uri) {
		String s = uri;
		return s;
	}

}
