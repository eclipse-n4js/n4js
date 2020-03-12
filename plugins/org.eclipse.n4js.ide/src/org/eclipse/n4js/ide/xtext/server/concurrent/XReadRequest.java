package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * A read request.
 */
public class XReadRequest<V> extends XAbstractRequest<V> {
	private final Function1<? super CancelIndicator, ? extends V> readOperation;

	/**
	 * The initializer future allows to track the running state of this request, e.g. if it was already started or not.
	 */
	private final CompletableFuture<Void> initializer;

	private final ExecutorService executor;

	/**
	 * Standard constructor.
	 */
	public XReadRequest(
			XRequestManager requestManager,
			String description,
			Function1<? super CancelIndicator, ? extends V> readOperation,
			ExecutorService executor) {
		super(requestManager, description);
		this.readOperation = readOperation;
		this.executor = executor;
		this.initializer = new CompletableFuture<>();
		this.initializer.thenRun(this::doRun);
	}

	@Override
	public void cancel() {
		super.cancel();
		if (initializer.cancel(true)) {
			cancelResult();
		}
	}

	@Override
	public void run() {
		initializer.complete(null);
	}

	/**
	 * The logic that is supposed to be executed when this request is run.
	 */
	private void doRun() {
		if (isDone()) {
			return;
		}
		this.executor.submit(() -> {
			try {
				if (isDone()) {
					return;
				}
				cancelIndicator.checkCanceled();
				V readResult = readOperation.apply(cancelIndicator);
				complete(readResult);
			} catch (Throwable t) {
				completeExceptionally(t);
			}
		});
	}

}
