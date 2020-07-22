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

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

/**
 * A protocol extension to drive special requests for n4js.
 */
@JsonSegment("n4js")
public interface N4JSProtocolExtensions {

	/**
	 * Return the content of a document with the given document URI.
	 */
	@JsonRequest
	CompletableFuture<String> documentContents(TextDocumentIdentifier documentUri);

}
