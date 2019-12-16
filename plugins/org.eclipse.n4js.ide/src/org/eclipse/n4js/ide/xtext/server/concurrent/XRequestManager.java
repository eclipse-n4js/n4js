package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
@Singleton
public class XRequestManager {

	@Inject
	private ExecutorService parallel;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	private final ExecutorService queue = Executors.newSingleThreadExecutor(
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("XRequestManager-Queue-%d").build());

	private ArrayList<XAbstractRequest<?>> requests = new ArrayList<>();

	/**
	 * An orderly shutdown of this request manager.
	 */
	public void shutdown() {
		queue.shutdown();
		parallel.shutdown();
		cancel();
	}

	/**
	 * Run the given cancellable logic as a read request.
	 */
	public <V> CompletableFuture<V> runRead(Function1<? super CancelIndicator, ? extends V> cancellable) {
		return submit(new XReadRequest<>(this, cancellable, parallel));
	}

	/**
	 * Perform the given write and run the cancellable logic afterwards.
	 */
	public <U, V> CompletableFuture<V> runWrite(
			Function0<? extends U> nonCancellable,
			Function2<? super CancelIndicator, ? super U, ? extends V> cancellable) {
		return submit(new XWriteRequest<>(this, nonCancellable, cancellable, cancel()));
	}

	/**
	 * Submit the given request.
	 */
	protected <V> CompletableFuture<V> submit(XAbstractRequest<V> request) {
		requests.add(request);
		queue.submit(request);
		return request.get();
	}

	/**
	 * Cancel all requests in the queue.
	 */
	protected CompletableFuture<Void> cancel() {
		List<XAbstractRequest<?>> localRequests = requests;
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
