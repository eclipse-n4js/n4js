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
package org.eclipse.n4js.cli.lsp;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;

/**
 * Customized to create a patched RemoteEndpoint
 */
public class PatchedLauncherBuilder<T> extends Launcher.Builder<T> {

	@Override
	protected RemoteEndpoint createRemoteEndpoint(MessageJsonHandler jsonHandler) {
		MessageConsumer outgoingMessageStream = new StreamMessageConsumer(output, jsonHandler);
		outgoingMessageStream = wrapMessageConsumer(outgoingMessageStream);
		Endpoint localEndpoint = ServiceEndpoints.toEndpoint(localServices);
		RemoteEndpoint remoteEndpoint;
		if (exceptionHandler == null) {
			remoteEndpoint = new PatchedRemoteEndpoint(outgoingMessageStream, localEndpoint);
		} else {
			remoteEndpoint = new PatchedRemoteEndpoint(outgoingMessageStream, localEndpoint, exceptionHandler);
		}
		jsonHandler.setMethodProvider(remoteEndpoint);
		return remoteEndpoint;
	}
}
