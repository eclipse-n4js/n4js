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
package org.eclipse.n4js.ide.xtext.server.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.xtext.ide.server.UriExtensions;

import com.google.inject.Inject;

/**
 * Helper methods for dealing with parameter objects used in LSP notifications/requests, such as
 * {@link DidOpenTextDocumentParams}.
 */
public class ParamHelper {

	@Inject
	private UriExtensions uriExtensions;

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DidOpenTextDocumentParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DidChangeTextDocumentParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DidCloseTextDocumentParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DidSaveTextDocumentParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(CompletionParams params) {
		if (params == null) {
			return null;
		}
		URI fromIdentifier = getURI(params.getTextDocument());
		if (fromIdentifier != null) {
			return fromIdentifier;
		}
		@SuppressWarnings("deprecation")
		URI deprecatedURI = toURI(params.getUri());
		return deprecatedURI;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(ReferenceParams params) {
		return getURI((TextDocumentPositionParams) params);
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DocumentSymbolParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(CodeActionParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(CodeLensParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DocumentFormattingParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(DocumentOnTypeFormattingParams params) {
		return getURI((DocumentFormattingParams) params);
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(RenameParams params) {
		return params != null ? getURI(params.getTextDocument()) : null;
	}

	/** Extracts the URI from the given LSP parameter object. May return <code>null</code>. */
	public URI getURI(TextDocumentPositionParams params) {
		if (params == null) {
			return null;
		}
		URI fromIdentifier = getURI(params.getTextDocument());
		if (fromIdentifier != null) {
			return fromIdentifier;
		}
		@SuppressWarnings("deprecation")
		URI deprecatedURI = toURI(params.getUri());
		return deprecatedURI;
	}

	/** Extracts the URI from the given LSP text document item. May return <code>null</code>. */
	public URI getURI(TextDocumentItem params) {
		return params != null ? toURI(params.getUri()) : null;
	}

	/** Extracts the URI from the given LSP text document identifier. May return <code>null</code>. */
	public URI getURI(TextDocumentIdentifier id) {
		return id != null ? toURI(id.getUri()) : null;
	}

	/** Extracts the URI from the given LSP file event. May return <code>null</code>. */
	public URI getURI(FileEvent fileEvent) {
		return fileEvent != null ? toURI(fileEvent.getUri()) : null;
	}

	/** Converts the given string to a {@link URI}. Returns <code>null</code> iff <code>null</code> is passed in. */
	public URI toURI(String uriStr) {
		return uriStr != null ? uriExtensions.toUri(uriStr) : null;
	}
}
