package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CompletableFuture;

import org.eclipse.xtext.ide.server.concurrent.Cancellable;
import org.eclipse.xtext.ide.server.concurrent.RequestCancelIndicator;

@SuppressWarnings("all")
public abstract class XAbstractRequest<V> implements Runnable, Cancellable {
	protected final CompletableFuture<V> result = new CompletableFuture<>();

	protected final RequestCancelIndicator cancelIndicator = new RequestCancelIndicator(this.result);

	@Override
	public void cancel() {
		this.cancelIndicator.cancel();
	}

	public CompletableFuture<V> get() {
		return this.result;
	}
}