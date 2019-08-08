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

import com.google.inject.Inject;

/** */
public class LspLogger {

	@Inject
	N4JSLanguageServerImpl langServer;

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
		MessageParams message = new MessageParams();
		message.setMessage(messageString);
		message.setType(type);
		langServer.getLanguageClient().logMessage(message);
	}

}
