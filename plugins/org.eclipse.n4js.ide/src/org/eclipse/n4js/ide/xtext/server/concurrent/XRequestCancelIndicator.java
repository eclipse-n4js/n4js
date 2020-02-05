package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.CancellationException;

import org.eclipse.lsp4j.jsonrpc.CancelChecker;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
public class XRequestCancelIndicator implements CancelIndicator, CancelChecker, XCancellable {
	private volatile boolean cancelled = false;
	private final XAbstractRequest<?> request;

	XRequestCancelIndicator(XAbstractRequest<?> request) {
		this.request = request;
	}

	@Override
	public void cancel() {
		request.cancel();
	}

	void doCancel() {
		this.cancelled = true;
	}

	@Override
	public boolean isCanceled() {
		return cancelled;
	}

	@Override
	public void checkCanceled() {
		if (cancelled) {
			throw new CancellationException();
		}
	}

}
