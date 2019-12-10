package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.ExecutorService;

import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * A read request.
 */
public class XReadRequest<V> extends XAbstractRequest<V> {
	private final Function1<? super CancelIndicator, ? extends V> cancellable;

	private final ExecutorService executor;

	/**
	 * Standard constructor.
	 */
	public XReadRequest(Function1<? super CancelIndicator, ? extends V> cancellable,
			ExecutorService executor) {
		this.cancellable = cancellable;
		this.executor = executor;
	}

	@Override
	public void run() {
		if (result.isCancelled()) {
			return;
		}
		this.executor.submit(() -> {
			try {
				cancelIndicator.checkCanceled();
				result.complete(this.cancellable.apply(cancelIndicator));
			} catch (final Throwable t) {
				result.completeExceptionally(t);
			}
		});
	}

}
