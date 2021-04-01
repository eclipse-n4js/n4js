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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.n4js.cli.lsp.PatchedTracingMessageConsumer.RequestInfo;

/**
 * Customized to create a patched RemoteEndpoint
 */
public class PatchedLauncherBuilder<T> extends Launcher.Builder<T> {

	// cannot use field #messageTracer of super class, because its type is 'MessageTracer' and that has a non-public
	// constructor and can therefore not be customized by sub-classing:
	private PatchedMessageTracer myMessageTracer = null;

	@Override
	public Builder<T> traceMessages(PrintWriter writer) {
		if (writer != null) {
			this.myMessageTracer = new PatchedMessageTracer(writer);
		}
		return this;
	}

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

	@Override
	protected MessageConsumer wrapMessageConsumer(MessageConsumer consumer) {
		if (myMessageTracer != null) {
			consumer = myMessageTracer.apply(consumer);
		}
		return super.wrapMessageConsumer(consumer);
	}

	// cannot extend org.eclipse.lsp4j.jsonrpc.MessageTracer due to non-public constructor :(
	private static class PatchedMessageTracer implements Function<MessageConsumer, MessageConsumer> {
		private final PrintWriter writer;
		private final Map<String, RequestInfo> sentRequests = new HashMap<>();
		private final Map<String, RequestInfo> receivedRequests = new HashMap<>();

		private PatchedMessageTracer(PrintWriter writer) {
			this.writer = Objects.requireNonNull(writer);
		}

		@Override
		public MessageConsumer apply(MessageConsumer messageConsumer) {
			return new PatchedTracingMessageConsumer(messageConsumer, sentRequests, receivedRequests, writer);
		}
	}
}
