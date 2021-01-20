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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.CancelParams;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

/**
 * Patched RemoteEndpoint to make sure that we do not see out-of-order cancellation warnings.
 *
 * Cancellation that is issued by the server is to be treated differently from cancellation that is issued by the
 * client.
 */
public class PatchedRemoteEndpoint extends RemoteEndpoint {

	// Deliberately use the logger of the super class.
	private static final Logger LOG = Logger.getLogger(RemoteEndpoint.class);

	private final Endpoint localEndpoint;
	private final Function<Throwable, ResponseError> exceptionHandler;
	private final MessageConsumer out;
	private final Map<String, CompletableFuture<?>> receivedRequestMap;

	/**
	 * @param out
	 *            a consumer that transmits messages to the remote service
	 * @param localEndpoint
	 *            the local service implementation
	 * @param exceptionHandler
	 *            an exception handler that should never return null.
	 */
	@SuppressWarnings("unchecked")
	public PatchedRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint,
			Function<Throwable, ResponseError> exceptionHandler) {
		super(out, localEndpoint, exceptionHandler);
		this.localEndpoint = localEndpoint;
		this.exceptionHandler = exceptionHandler;
		this.out = out;
		Field field;
		try {
			field = RemoteEndpoint.class.getDeclaredField("receivedRequestMap");
			field.setAccessible(true);
			receivedRequestMap = (Map<String, CompletableFuture<?>>) field.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param out
	 *            a consumer that transmits messages to the remote service
	 * @param localEndpoint
	 *            the local service implementation
	 */
	public PatchedRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint) {
		this(out, localEndpoint, DEFAULT_EXCEPTION_HANDLER);
	}

	@Override
	protected boolean handleCancellation(NotificationMessage notificationMessage) {
		if (MessageJsonHandler.CANCEL_METHOD.getMethodName().equals(notificationMessage.getMethod())) {
			Object cancelParams = notificationMessage.getParams();
			if (cancelParams != null) {
				if (cancelParams instanceof CancelParams) {
					String id = ((CancelParams) cancelParams).getId();
					LOG.debug("Client cancels: " + id);
					CompletableFuture<?> future;
					synchronized (receivedRequestMap) {
						future = receivedRequestMap.remove(id);
					}
					if (future != null)
						future.cancel(true);
					else
						LOG.debug("Unmatched cancel notification for request id " + id);
					return true;
				} else {
					LOG.warn("Cancellation support is disabled, since the '"
							+ MessageJsonHandler.CANCEL_METHOD.getMethodName()
							+ "' method has been registered explicitly.");
				}
			} else {
				LOG.warn("Missing 'params' attribute of cancel notification.");
			}
		}
		return false;
	}

	@Override
	protected void handleNotification(NotificationMessage notificationMessage) {
		if (!handleCancellation(notificationMessage)) {
			// Forward the notification to the local endpoint
			LOG.debug("Client notifies: " + notificationMessage.getMethod());
			try {
				localEndpoint.notify(notificationMessage.getMethod(), notificationMessage.getParams());
			} catch (Exception exception) {
				LOG.debug("Notification threw an exception: " + notificationMessage, exception);
			}
		}
	}

	@Override
	protected void handleRequest(RequestMessage requestMessage) {
		CompletableFuture<?> future;
		final String messageId = requestMessage.getId();
		LOG.debug("Client requests: " + messageId + " / " + requestMessage.getMethod());
		try {
			// Forward the request to the local endpoint
			future = localEndpoint.request(requestMessage.getMethod(), requestMessage.getParams());
		} catch (Throwable throwable) {
			// The local endpoint has failed handling the request - reply with an error response
			ResponseError errorObject = exceptionHandler.apply(throwable);
			if (errorObject == null) {
				errorObject = fallbackResponseError("Internal error. Exception handler provided no error object",
						throwable);
			}
			out.consume(createErrorResponseMessage(requestMessage, errorObject));
			if (throwable instanceof Error)
				throw (Error) throwable;
			else
				return;
		}

		synchronized (receivedRequestMap) {
			receivedRequestMap.put(messageId, future);
		}
		future.thenAccept((result) -> {
			// Reply with the result object that was computed by the local endpoint
			out.consume(createResultResponseMessage(requestMessage, result));
			LOG.debug("Server response: " + messageId + " / " + requestMessage.getMethod());
		}).exceptionally((Throwable t) -> {
			// The local endpoint has failed computing a result - reply with an error response
			ResponseMessage responseMessage;
			if (isCancellation(t)) {
				String message = "The request (id: " + messageId + ", method: '" + requestMessage.getMethod()
						+ "') has been cancelled";
				ResponseError errorObject = new ResponseError(ResponseErrorCode.RequestCancelled, message, null);
				responseMessage = createErrorResponseMessage(requestMessage, errorObject);
				LOG.debug("Server cancels: " + messageId + " / " + requestMessage.getMethod());
			} else {
				ResponseError errorObject = exceptionHandler.apply(t);
				if (errorObject == null) {
					errorObject = fallbackResponseError("Internal error. Exception handler provided no error object",
							t);
				}
				responseMessage = createErrorResponseMessage(requestMessage, errorObject);
				LOG.error("Server errors: " + messageId + " / " + requestMessage.getMethod());
			}
			out.consume(responseMessage);
			return null;
		}).thenApply((obj) -> {
			synchronized (receivedRequestMap) {
				receivedRequestMap.remove(messageId);
			}
			return null;
		});
	}

	private static ResponseError fallbackResponseError(String header, Throwable throwable) {
		LOG.error(header + ": " + throwable.getMessage(), throwable);
		ResponseError error = new ResponseError();
		error.setMessage(header + ".");
		error.setCode(ResponseErrorCode.InternalError);
		ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
		PrintWriter stackTraceWriter = new PrintWriter(stackTrace);
		throwable.printStackTrace(stackTraceWriter);
		stackTraceWriter.flush();
		error.setData(stackTrace.toString());
		return error;
	}
}
