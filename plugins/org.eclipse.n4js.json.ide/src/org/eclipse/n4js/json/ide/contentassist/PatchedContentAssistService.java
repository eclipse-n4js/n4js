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
package org.eclipse.n4js.json.ide.contentassist;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.InsertTextFormat;
import org.eclipse.lsp4j.Position;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.contentassist.ContentAssistService;

import com.google.common.base.Objects;

/**
 * Fix the handling of snippets.
 */
public class PatchedContentAssistService extends ContentAssistService {

	@Override
	protected CompletionItem toCompletionItem(ContentAssistEntry entry, int caretOffset, Position caretPosition,
			Document document) {
		CompletionItem result = super.toCompletionItem(entry, caretOffset, caretPosition, document);
		if (entry.getKind().startsWith(ContentAssistEntry.KIND_SNIPPET + ":")) {
			result.setInsertTextFormat(InsertTextFormat.Snippet);
			entry.setKind(entry.getKind().substring(ContentAssistEntry.KIND_SNIPPET.length() + 1));
		} else if (Objects.equal(entry.getKind(), ContentAssistEntry.KIND_SNIPPET)) {
			result.setInsertTextFormat(InsertTextFormat.Snippet);
		}
		result.setKind(translateKind(entry));
		return result;
	}

}
