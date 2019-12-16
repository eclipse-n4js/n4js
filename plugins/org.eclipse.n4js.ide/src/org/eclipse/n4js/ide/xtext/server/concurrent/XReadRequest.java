package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * A read request.
 */
public class XReadRequest<V> extends XAbstractRequest<V> {
	private static final Logger LOG = Logger.getLogger(XReadRequest.class);

	private final Function1<? super CancelIndicator, ? extends V> cancellable;

	private final ExecutorService executor;

	/**
	 * Standard constructor.
	 */
	public XReadRequest(XRequestManager requestManager, Function1<? super CancelIndicator, ? extends V> cancellable,
			ExecutorService executor) {
		super(requestManager);
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
				result.complete(cancellable.apply(cancelIndicator));
			} catch (Throwable t) {
				if (!requestManager.isCancelException(t)) {
					LOG.error("Error during request: ", t);
				}
				result.completeExceptionally(t);
			}
		});
	}

}
