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

import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;

/**
 * N4JS source actions for LSP.
 * <p>
 * Source actions are code actions that apply to an entire file (independently of the existence of errors or warnings in
 * the file) and have the LSP code action kind {@link CodeActionKind#Source Source} (or one of the specialized kinds).
 */
public class N4JSSourceActionProvider {

	/** A source action for cleaning up a file's imports. */
	@SourceAction
	public void organizeImports(CodeActionParams params, ICodeActionAcceptor acceptor) {
		acceptor.acceptSourceAction(
				"N4JS: Organize Imports", // note: due to the code action kind, VSCode will show a special label
				CodeActionKind.SourceOrganizeImports,
				N4JSCommandService.N4JS_ORGANIZE_IMPORTS,
				params.getTextDocument().getUri());
	}
}
