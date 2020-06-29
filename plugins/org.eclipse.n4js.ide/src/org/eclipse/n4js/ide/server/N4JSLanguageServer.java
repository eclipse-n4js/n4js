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
package org.eclipse.n4js.ide.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.openfiles.OpenFileContext;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

/**
 * Language server for N4JS.
 */
@Singleton
public class N4JSLanguageServer extends XLanguageServerImpl implements N4JSProtocolExtensions {

	@Override
	protected Optional<List<String>> getSupportedCodeActionKinds() {
		return Optional.of(Lists.newArrayList(
				CodeActionKind.QuickFix,
				CodeActionKind.Source,
				// the following specific kind must be listed for VSCode to work properly, even
				// though the LSP specification says it is sufficient to only list base kinds:
				CodeActionKind.SourceOrganizeImports));
	}

	@Override
	public CompletableFuture<String> documentContents(TextDocumentIdentifier param) {
		URI uri = getURI(param);
		return getOpenFilesManager().runInTemporaryFileContext(uri, "documentContents", false,
				(ofc, cancelIndicator) -> documentContents(ofc, cancelIndicator));
	}

	/**
	 * @param cancelIndicator
	 *            a cancel indicator
	 */
	private String documentContents(OpenFileContext ofc, CancelIndicator cancelIndicator) {
		XDocument doc = ofc.getDocument();
		return doc.getContents();
	}

}
