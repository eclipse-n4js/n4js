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
package org.eclipse.n4js.ide.server.codeActions;

import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.util.Ranges;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2.Options;

/**
 * Encapsulates the context that is made available to quickfix implementations.
 */
@SuppressWarnings("restriction")
public class QuickfixContext {
	/**
	 * The issue code that is about the be resolved.
	 */
	public final String issueCode;
	/**
	 * The code-action options.
	 */
	public final Options options;

	/**
	 * Standard constructor.
	 */
	public QuickfixContext(String issueCode, Options options) {
		this.issueCode = issueCode;
		this.options = options;
	}

	/**
	 * Return the current diagnostic or null, if it cannot be determined generically.
	 */
	public Diagnostic getDiagnostic() {
		CodeActionContext context = options.getCodeActionParams().getContext();
		for (Diagnostic d : context.getDiagnostics()) {
			if (issueCode.equals(d.getCode())) {
				if (Ranges.containsRange(d.getRange(), options.getCodeActionParams().getRange())) {
					return d;
				}
			}
		}
		return null;
	}
}