package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * Abstract base type for read and write requests.
 */
public abstract class XAbstractRequest<V> implements Runnable, XCancellable {

	private static final Logger LOG = Logger.getLogger(XAbstractRequest.class);

	private class ResultFuture extends CompletableFuture<V> {
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			XAbstractRequest.this.cancel();
			return isCancelled();
		}

		void doCancel() {
			super.cancel(true);
		}
	}

	private static final AtomicInteger counter = new AtomicInteger(1);

	/**
	 * The underyling future.
	 */
	protected final ResultFuture result;

	/**
	 * The current cancel indicator.
	 */
	protected final XRequestCancelIndicator cancelIndicator;

	/**
	 * The request manager that is handling this request.
	 */
	protected final XRequestManager requestManager;

	/**
	 * A description that is used in log messages to better understand this request.
	 */
	protected final String description;

	/**
	 * Standard constructor.
	 */
	protected XAbstractRequest(XRequestManager requestManager, String description) {
		this.requestManager = requestManager;
		this.description = getClass().getSimpleName() + "[" + description + "#" + counter.getAndIncrement() + "]";

		this.result = new ResultFuture();
		this.cancelIndicator = new XRequestCancelIndicator(this);
	}

	@Override
	public String toString() {
		if (result.isCancelled()) {
			return "(cancelled)" + description;
		}
		if (result.isDone()) {
			return "(done)" + description;
		}
		return description;
	}

	void cancelResult() {
		result.doCancel();
	}

	boolean isDone() {
		return result.isDone();
	}

	void complete(V value) {
		result.complete(value);
	}

	void completeExceptionally(Throwable t) {
		if (!requestManager.isCancelException(t)) {
			LOG.error("Error during request: ", t);
			result.completeExceptionally(t);
		} else {
			cancelResult();
		}
	}

	@Override
	public void cancel() {
		cancelIndicator.doCancel();
	}

	/**
	 * Return the underlying future.
	 */
	public CompletableFuture<V> get() {
		return this.result;
	}
}