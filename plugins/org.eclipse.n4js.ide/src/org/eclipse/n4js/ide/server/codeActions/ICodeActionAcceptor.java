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

import java.util.List;

import org.eclipse.lsp4j.TextEdit;

/**
 * Minimal API for quickfixes to be send to the client as code actions.
 */
public interface ICodeActionAcceptor {

	/**
	 * Create a quickfix with the given string and the list of edits (will be retrieved on demand).
	 */
	void acceptQuickfixCodeAction(QuickfixContext context, String title, List<TextEdit> textEdits);

	/**
	 * Create a command with the given arguments as a result of applying a quickfix.
	 */
	void acceptQuickfixCommand(QuickfixContext context, String title, String commandID, Object... arguments);

}
