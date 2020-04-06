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

import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.TextEdit;

/**
 * Minimal API for quickfixes and source actions to be sent to the client as code actions.
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

	/**
	 * Same as {@link #acceptSourceAction(String, String, String, Object...)}, using {@link CodeActionKind#Source} as
	 * the default code action kind for source actions.
	 */
	default void acceptSourceAction(String title, String commandId, Object... arguments) {
		acceptSourceAction(title, CodeActionKind.Source, commandId, arguments);
	}

	/**
	 * Create a source action with the given code action kind.
	 *
	 * @param kind
	 *            must be a source action kind, i.e. either the base kind {@link CodeActionKind#Source} or a
	 *            specialization thereof, e.g. {@link CodeActionKind#SourceOrganizeImports}.
	 */
	void acceptSourceAction(String title, String kind, String commandId, Object... arguments);
}
