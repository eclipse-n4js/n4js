/**
 * Copyright (c) 2021 NumberFour AG.
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
import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.TracingMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

/**
 * Modifies {@link TracingMessageConsumer} to be more configurable during debugging sessions and to make the trace more
 * readable when not using the LSP Inspector (e.g. one LSP message per line, maximum length per message).
 */
@SuppressWarnings("javadoc")
public class PatchedTracingMessageConsumer implements MessageConsumer {

	/**
	 * If true, each LSP message will only produce a single line of logging output, to make sequences of messages easier
	 * to investigate.
	 */
	public static final boolean SINGLE_LINE = true;

	/**
	 * Maximum number of characters logged per LSP message to avoid cluttering log with extremely large messages.
	 */
	public static final int MESSAGE_MAX_LEN = 256;

	private final MessageConsumer delegate;
	private final Map<String, RequestInfo> requestsSent;
	private final Map<String, RequestInfo> requestsReceived;
	private final PrintWriter writer;

	public static final class RequestInfo {
		final String method;
		final long start;

		public RequestInfo(RequestMessage request) {
			this.method = request.getMethod();
			this.start = System.currentTimeMillis();
		}
	}

	public PatchedTracingMessageConsumer(MessageConsumer delegate, Map<String, RequestInfo> requestsSent,
			Map<String, RequestInfo> requestsReceived, PrintWriter writer) {
		this.delegate = delegate;
		this.requestsSent = requestsSent;
		this.requestsReceived = requestsReceived;
		this.writer = writer;
	}

	@Override
	public void consume(Message message) throws MessageIssueException, JsonRpcException {
		if (delegate instanceof StreamMessageConsumer) {
			logSent(message);
		} else if (delegate instanceof RemoteEndpoint) {
			logReceived(message);
		} else {
			doLog(String.format("Unknown type of delegate: %s", delegate.getClass().getName()));
		}
		delegate.consume(message);
	}

	protected void logSent(Message message) {
		if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			String method = requestMessage.getMethod();
			String id = requestMessage.getId();
			Object params = requestMessage.getParams();
			String paramsJson = toJSON(params);
			String format = "[Trace] SENDING  request      '%s' (ID %s) ---- Params: %s";
			doLog(String.format(format, method, id, paramsJson));
			requestsSent.put(id, new RequestInfo(requestMessage));
		} else if (message instanceof ResponseMessage) {
			ResponseMessage responseMessage = (ResponseMessage) message;
			String id = responseMessage.getId();
			PatchedTracingMessageConsumer.RequestInfo requestInfo = requestsReceived.remove(id);
			if (requestInfo == null) {
				doLog(String.format("Unmatched response message: %s", message));
				return;
			}
			String method = requestInfo.method;
			long latencyMillis = System.currentTimeMillis() - requestInfo.start;
			Object result = responseMessage.getResult();
			String resultJson = toJSON(result);
			String format = "[Trace] SENDING  response     '%s' (ID %s) - processing request took %sms ---- Result: %s";
			doLog(String.format(format, method, id, latencyMillis, resultJson));
		} else if (message instanceof NotificationMessage) {
			NotificationMessage notificationMessage = (NotificationMessage) message;
			String method = notificationMessage.getMethod();
			Object params = notificationMessage.getParams();
			String paramsJson = toJSON(params);
			String format = "[Trace] SENDING  notification '%s' ---- Params: %s";
			doLog(String.format(format, method, paramsJson));
		} else {
			doLog(String.format("Unknown message type: %s", message));
		}
	}

	protected void logReceived(Message message) {
		if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			String method = requestMessage.getMethod();
			String id = requestMessage.getId();
			Object params = requestMessage.getParams();
			String paramsJson = toJSON(params);
			String format = "[Trace] RECEIVED request      '%s' (ID %s) ---- Params: %s";
			doLog(String.format(format, method, id, paramsJson));
			requestsReceived.put(id, new RequestInfo(requestMessage));
		} else if (message instanceof ResponseMessage) {
			ResponseMessage responseMessage = (ResponseMessage) message;
			String id = responseMessage.getId();
			PatchedTracingMessageConsumer.RequestInfo requestInfo = requestsSent.remove(id);
			if (requestInfo == null) {
				doLog(String.format("Unmatched response message: %s", message));
				return;
			}
			String method = requestInfo.method;
			long latencyMillis = System.currentTimeMillis() - requestInfo.start;
			Object result = responseMessage.getResult();
			String resultJson = toJSON(result);
			String format = "[Trace] RECEIVED response     '%s' (ID %s) in %sms ---- Result: %s";
			doLog(String.format(format, method, id, latencyMillis, resultJson));
		} else if (message instanceof NotificationMessage) {
			NotificationMessage notificationMessage = (NotificationMessage) message;
			String method = notificationMessage.getMethod();
			Object params = notificationMessage.getParams();
			String paramsJson = toJSON(params);
			String format = "[Trace] RECEIVED notification '%s' ---- Params: %s";
			doLog(String.format(format, method, paramsJson));
		} else {
			doLog(String.format("Unknown message type: %s", message));
		}
	}

	protected String toJSON(Object obj) {
		String json = MessageJsonHandler.toString(obj);
		if (SINGLE_LINE) {
			json = json.replaceAll("\\s+", " ");
		}
		if (MESSAGE_MAX_LEN > 0 && json.length() > MESSAGE_MAX_LEN) {
			json = json.substring(0, MESSAGE_MAX_LEN) + " ...";
		}
		return json;
	}

	protected void doLog(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
