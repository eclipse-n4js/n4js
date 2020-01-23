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

import java.util.Collections;
import java.util.List;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSCodeActionService implements ICodeActionService2 {

	@Inject
	N4JSQuickfixProvider quickfixProvider;

	@Override
	public List<Either<Command, CodeAction>> getCodeActions(Options options) {
		CodeActionAcceptor acceptor = new CodeActionAcceptor();

		List<Diagnostic> diagnostics = null;
		if (options.getCodeActionParams() != null && options.getCodeActionParams().getContext() != null) {
			diagnostics = options.getCodeActionParams().getContext().getDiagnostics();
		}
		if (diagnostics == null) {
			diagnostics = Collections.emptyList();
		}

		for (Diagnostic diag : diagnostics) {
			quickfixProvider.addQuickfix(diag.getCode(), options, acceptor);
		}

		return acceptor.getList();
	}

}
