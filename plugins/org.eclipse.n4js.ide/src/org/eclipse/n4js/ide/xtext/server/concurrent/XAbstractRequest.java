package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract base type for read and write requests.
 */
public abstract class XAbstractRequest<V> implements Runnable, XCancellable {
	/**
	 * The underyling future.
	 */
	protected final CompletableFuture<V> result;

	/**
	 * The current cancel indicator.
	 */
	protected final XRequestCancelIndicator cancelIndicator;

	/**
	 * The request manager that is handling this request.
	 */
	protected final XRequestManager requestManager;

	/**
	 * Standard constructor.
	 */
	protected XAbstractRequest(XRequestManager requestManager) {
		this.requestManager = requestManager;
		this.result = new CompletableFuture<>();
		this.cancelIndicator = new XRequestCancelIndicator(this.result);
	}

	@Override
	public void cancel() {
		this.cancelIndicator.cancel();
	}

	/**
	 * Return the underlying future.
	 */
	public CompletableFuture<V> get() {
		return this.result;
	}
}