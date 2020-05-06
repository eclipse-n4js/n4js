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
package org.eclipse.n4js.ide.client;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ApplyWorkspaceEditResponse;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 * Amends the standard {@link LanguageClient} of LSP by some additional N4JS-specific methods.
 */
@SuppressWarnings("unused")
public interface N4JSLanguageClient extends LanguageClient {

	public static class N4JSCustomEventParams {
		@NonNull
		String kind;

		String argument;

		public N4JSCustomEventParams(String kind, String argument) {
			this.kind = Objects.requireNonNull(kind);
			this.argument = argument;
		}
	}

	/** Some custom event from the N4JS LSP server to the client. */
	@JsonNotification("n4js/customEvent")
	default CompletableFuture<ApplyWorkspaceEditResponse> customEvent(N4JSCustomEventParams params) {
		throw new UnsupportedOperationException();
	}
}
