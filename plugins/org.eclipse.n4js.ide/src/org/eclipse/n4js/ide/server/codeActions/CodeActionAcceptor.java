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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.server.codeActions.N4JSQuickfixProvider.QuickfixContext;

/**
 * Utility class to convert some changes to {@link CodeAction}s / {@link Command}s
 */
@SuppressWarnings("restriction")
public class CodeActionAcceptor {

	List<Either<Command, CodeAction>> codeActions = new ArrayList<>();

	/** Adds a quick-fix code action with the given title and command created of commandID and arguments */
	public void acceptQuickfixCommand(QuickfixContext context, String title, String commandID, Object... arguments) {
		acceptQuickfixCommand(context, title, commandID, Arrays.asList(arguments));
	}

	/** Adds a quick-fix code action with the given title and command created of commandID and arguments */
	public void acceptQuickfixCommand(QuickfixContext context, String title, String commandID, List<Object> arguments) {
		Command command = new Command();
		command.setTitle(title);
		command.setCommand(commandID);
		command.setArguments(arguments);
		acceptQuickfixCodeAction(context, title, command);
	}

	/** Adds a quick-fix code action with the given title and command */
	public void acceptQuickfixCodeAction(QuickfixContext context, String title, Command command) {
		acceptQuickfixCodeAction(context, title, null, command);
	}

	/** Adds a quick-fix code action with the given title and text edits */
	public void acceptQuickfixCodeAction(QuickfixContext context, String title, TextEdit... textEdits) {
		if (textEdits == null) {
			return;
		}

		acceptQuickfixCodeAction(context, title, Arrays.asList(textEdits));
	}

	/** Adds a quick-fix code action with the given title and text edits */
	public void acceptQuickfixCodeAction(QuickfixContext context, String title, List<TextEdit> textEdits) {
		if (textEdits == null || textEdits.isEmpty()) {
			return;
		}

		String uri = context.options.getCodeActionParams().getTextDocument().getUri();
		Map<String, List<TextEdit>> changes = new HashMap<>();
		changes.put(uri, textEdits);
		WorkspaceEdit edit = new WorkspaceEdit();
		edit.setChanges(changes);
		acceptQuickfixCodeAction(context, title, edit, null);
	}

	/** Adds a quick-fix code action with the given title and edit */
	public void acceptQuickfixCodeAction(QuickfixContext context, String title, WorkspaceEdit edit) {
		acceptQuickfixCodeAction(context, title, edit, null);
	}

	/** Adds a quick-fix code action with the given title, edit and command */
	public void acceptQuickfixCodeAction(QuickfixContext context, String title, WorkspaceEdit edit, Command command) {
		if (edit == null && command == null) {
			return;
		}
		CodeAction codeAction = new CodeAction();
		codeAction.setTitle(title);
		codeAction.setEdit(edit);
		codeAction.setCommand(command);
		codeAction.setKind(CodeActionKind.QuickFix);
		if (context.options != null && context.options.getCodeActionParams() != null) {
			CodeActionContext cac = context.options.getCodeActionParams().getContext();
			if (cac != null && cac.getDiagnostics() != null) {
				codeAction.setDiagnostics(cac.getDiagnostics());
			}
		}
		codeActions.add(Either.forRight(codeAction));
	}

	/** @return list of all changes */
	public List<Either<Command, CodeAction>> getList() {
		return codeActions;
	}

}
