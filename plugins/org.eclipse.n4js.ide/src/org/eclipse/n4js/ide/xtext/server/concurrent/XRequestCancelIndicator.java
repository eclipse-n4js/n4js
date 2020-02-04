package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.CancelChecker;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
public class XRequestCancelIndicator implements CancelIndicator, CancelChecker, XCancellable {
	private final CompletableFuture<?> requestFuture;

	/**
	 * Standard constructor
	 */
	public XRequestCancelIndicator(CompletableFuture<?> requestFuture) {
		this.requestFuture = requestFuture;
	}

	@Override
	public void cancel() {
		requestFuture.cancel(true);
	}

	@Override
	public boolean isCanceled() {
		return requestFuture.isCancelled();
	}

	@Override
	public void checkCanceled() {
		if (requestFuture.isCancelled()) {
			throw new CancellationException();
		}
	}

}
