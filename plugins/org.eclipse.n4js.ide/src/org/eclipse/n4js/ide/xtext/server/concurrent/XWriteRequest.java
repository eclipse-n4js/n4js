package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CompletableFuture;

import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

/**
 * A write request.
 */
public class XWriteRequest<U, V> extends XAbstractRequest<V> {
	private final Function0<? extends U> nonCancellable;

	private final Function2<? super CancelIndicator, ? super U, ? extends V> cancellable;

	private final CompletableFuture<Void> previous;

	/**
	 * Standard constructor.
	 */
	public XWriteRequest(Function0<? extends U> nonCancellable,
			Function2<? super CancelIndicator, ? super U, ? extends V> cancellable,
			CompletableFuture<Void> previous) {
		super();
		this.nonCancellable = nonCancellable;
		this.cancellable = cancellable;
		this.previous = previous;
	}

	@Override
	public void run() {
		try {
			this.previous.join();
		} catch (final Throwable _t) {
			// ignore
		}
		try {
			final U intermediateResult = this.nonCancellable.apply();
			cancelIndicator.checkCanceled();
			result.complete(this.cancellable.apply(cancelIndicator, intermediateResult));
		} catch (final Throwable t) {
			result.completeExceptionally(t);
		}
	}

}
