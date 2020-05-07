/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.client.N4JSLanguageClient;
import org.eclipse.n4js.ide.client.N4JSLanguageClient.LogBuildProgressParams;

import com.google.inject.Singleton;

/**
 * Logging from the LSP server to the LSP client using LSP's {@link LanguageClient#logMessage(MessageParams)
 * window/logMessage} notification.
 */
@Singleton
public class LspLogger {

	private LanguageClient languageClient;

	/** Connect this logger to the given client. */
	public void connect(LanguageClient client) {
		this.languageClient = client;
	}

	/** Disconnect this logger from the given client. */
	public void disconnect() {
		this.languageClient = null;
	}

	/** */
	public void log(String messageString) {
		log(messageString, MessageType.Log);
	}

	/** */
	public void info(String messageString) {
		log(messageString, MessageType.Info);
	}

	/** */
	public void warning(String messageString) {
		log(messageString, MessageType.Warning);
	}

	/** */
	public void error(String messageString) {
		log(messageString, MessageType.Error);
	}

	/** */
	public void log(String messageString, MessageType type) {
		final LanguageClient lc = this.languageClient;
		if (lc == null) {
			return;
		}
		MessageParams message = new MessageParams();
		message.setMessage(messageString);
		message.setType(type);
		lc.logMessage(message);
	}

	/**
	 * <b>SMITH FUNCTIONALITY - only supported in N4JS extension for VS Code</b>
	 * <p>
	 * Will log a message indicating build progress to a special output channel in VS code.
	 *
	 * @param message
	 *            message to log. Use <code>'\n'</code> for line breaks. No implicit line break will be added after
	 *            printing the given string.
	 */
	public void logBuildProgress(String message) {
		final LanguageClient lc = this.languageClient;
		if (!(lc instanceof N4JSLanguageClient)) {
			return;
		}
		LogBuildProgressParams params = new LogBuildProgressParams(message);
		((N4JSLanguageClient) lc).logBuildProgress(params);
	}
}
