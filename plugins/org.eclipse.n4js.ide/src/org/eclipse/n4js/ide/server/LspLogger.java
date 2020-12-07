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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.server.util.ServerIncidentLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Logging from the LSP server to the LSP client using LSP's {@link LanguageClient#logMessage(MessageParams)
 * window/logMessage} notification.
 */
@Singleton
public class LspLogger {

	@Inject
	private ServerIncidentLogger serverIncidentLogger;

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
	public void error(String messageString, Throwable cause) {
		StringWriter w = new StringWriter();
		cause.printStackTrace(new PrintWriter(w));
		String msg = messageString + System.lineSeparator() + w.toString();
		log(msg, MessageType.Error);
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

		if (type == MessageType.Error) {
			serverIncidentLogger.report(messageString, true);
		}
	}

}
