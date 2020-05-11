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

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.server.LspLogger;

/**
 * Amends the standard {@link LanguageClient} of LSP by some additional N4JS-specific JSON-RPC methods.
 * <p>
 * Should be used only for internal "smith" features and temporary work-around features, because
 * <ol>
 * <li>such custom methods require special functionality on client side, which we do not want to implement/maintain for
 * all possible LSP clients,
 * <li>we do not have infrastructure for testing such custom, client-side functionality.
 * </ol>
 * Therefore, use of this interface should be kept to a minimum.
 */
@SuppressWarnings("unused")
public interface N4JSLanguageClient extends LanguageClient {

	/** Parameters for logging build progress. See {@link LspLogger#logBuildProgress(String)}. */
	public static class LogBuildProgressParams {

		/** Build progress message to log. See {@link LspLogger#logBuildProgress(String)}. */
		@NonNull
		public final String message;

		/** Creates an instance. */
		public LogBuildProgressParams(String message) {
			this.message = Objects.requireNonNull(message);
		}
	}

	/**
	 * <b>SMITH FUNCTIONALITY - only supported in N4JS extension for VS Code</b>
	 * <p>
	 * Will log a message indicating build progress to a special output channel in VS code.
	 *
	 * @see LspLogger#logBuildProgress(String)
	 */
	@JsonNotification("n4js/logBuildProgress")
	default void logBuildProgress(LogBuildProgressParams params) {
		throw new UnsupportedOperationException();
	}
}
