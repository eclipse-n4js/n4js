package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Handles requests from the LSP client.
 * <p>
 * Synchronized API because other callers e.g. CommandService might also create worker threads which add requests.
 *
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
@Singleton
public class XRequestManager {

	private static final Logger LOG = Logger.getLogger(XRequestManager.class);

	@Inject
	private ExecutorService parallel;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	private final ExecutorService queue = Executors.newSingleThreadExecutor(
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("XRequestManager-Queue-%d").build());

	private List<XAbstractRequest<?>> requests = new ArrayList<>();

	/**
	 * An orderly shutdown of this request manager.
	 */
	synchronized public void shutdown() {
		queue.shutdown();
		parallel.shutdown();
		MoreExecutors.shutdownAndAwaitTermination(queue, 2500, TimeUnit.MILLISECONDS);
		MoreExecutors.shutdownAndAwaitTermination(parallel, 2500, TimeUnit.MILLISECONDS);
		cancel();
	}

	/**
	 * Run the given cancellable logic as a read request.
	 */
	synchronized public <V> CompletableFuture<V> runRead(String description,
			Function1<? super CancelIndicator, ? extends V> cancellable) {
		return submit(new XReadRequest<>(this, description, cancellable, parallel));
	}

	/**
	 * Perform the given write and run the cancellable logic afterwards.
	 */
	synchronized public <U, V> CompletableFuture<V> runWrite(
			String description,
			Function0<? extends U> nonCancellable,
			Function2<? super CancelIndicator, ? super U, ? extends V> cancellable) {
		return submit(new XWriteRequest<>(this, description, nonCancellable, cancellable, cancel()));
	}

	/**
	 * Allows to join all requests that have been submitted so far.
	 *
	 * @return a future that can be awaited
	 */
	synchronized public CompletableFuture<Void> allRequests() {
		CompletableFuture<?>[] cfs;
		List<XAbstractRequest<?>> localRequests = requests;
		cfs = new CompletableFuture<?>[localRequests.size()];
		for (int i = 0, max = localRequests.size(); i < max; i++) {
			XAbstractRequest<?> request = localRequests.get(i);
			cfs[i] = request.get();
		}
		return CompletableFuture.allOf(cfs);
	}

	/**
	 * Submit the given request.
	 */
	synchronized protected <V> CompletableFuture<V> submit(XAbstractRequest<V> request) {
		LOG.info("submit: " + request);
		requests.add(request);
		queue.submit(request);
		return request.get();
	}

	/**
	 * Cancel all requests in the queue.
	 */
	synchronized protected CompletableFuture<Void> cancel() {
		List<XAbstractRequest<?>> localRequests = requests;
		LOG.info("cancel: " + localRequests);
		requests = new ArrayList<>();
		CompletableFuture<?>[] cfs = new CompletableFuture<?>[localRequests.size()];
		for (int i = 0, max = localRequests.size(); i < max; i++) {
			XAbstractRequest<?> request = localRequests.get(i);
			request.cancel();
			cfs[i] = request.get();
		}
		return CompletableFuture.allOf(cfs);
	}

	/**
	 * Check if the given throwable is an indicator for a cancellation.
	 */
	protected boolean isCancelException(Throwable t) {
		if (t == null) {
			return false;
		}
		Throwable cause = t;
		if (t instanceof CompletionException) {
			cause = ((CompletionException) t).getCause();
		}
		if (cause instanceof CancellationException) {
			return true;
		}
		return operationCanceledManager.isOperationCanceledException(cause);
	}
}
