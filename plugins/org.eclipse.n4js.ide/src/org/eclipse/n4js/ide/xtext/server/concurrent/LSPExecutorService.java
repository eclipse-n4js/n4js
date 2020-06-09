/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("javadoc")
@Singleton // ensures queue IDs are "global" within a single injector
public class LSPExecutorService {

	private static final Logger LOG = Logger.getLogger(LSPExecutorService.class);

	@Inject
	protected ExecutorService executorService;

	@Inject
	protected OperationCanceledManager operationCanceledManager;

	/** Global queue of all pending task across all queue IDs. */
	protected final List<QueuedTask<?>> pendingTasks = new ArrayList<>();
	/** Queue IDs with currently running tasks. */
	protected final Map<Object, QueuedTask<?>> runningTasks = new LinkedHashMap<>();

	protected final class QueuedTask<T> implements Runnable, XCancellable {
		protected final Object queueId;
		protected final String description;
		protected final Function<CancelIndicator, T> operation;
		protected final QueuedTaskFuture<T> result;
		protected boolean cancelled = false;

		protected QueuedTask(Object queueId, String description, Function<CancelIndicator, T> operation) {
			this.queueId = Objects.requireNonNull(queueId);
			this.description = Objects.requireNonNull(description);
			this.operation = Objects.requireNonNull(operation);
			this.result = new QueuedTaskFuture<>(this);
		}

		@Override
		public void run() {
			try {
				T actualResult = operation.apply(new CancelIndicator() {
					@Override
					public boolean isCanceled() {
						return cancelled;
					}
				});
				result.complete(actualResult);
			} catch (Throwable th) {
				if (isCancellation(th)) {
					result.doCancel();
				} else {
					result.completeExceptionally(th);
					LOG.error("error during queued task: ", th);
				}
			} finally {
				onDone(this);
			}
		}

		@Override
		public void cancel() {
			cancelled = true;
		}
	}

	public static class QueuedTaskFuture<T> extends CompletableFuture<T> {

		protected final QueuedTask<T> task;

		public QueuedTaskFuture(QueuedTask<T> task) {
			this.task = task;
		}

		/**
		 * Does not immediately cancel this future! Instead, only the corresponding queued task's cancel indicator is
		 * marked as cancelled, but it lies in the discretion of the task's implementation how to react to that. This
		 * future might even still complete normally if the task implementation chooses to ignore the cancellation
		 * status of the cancel indicator.
		 */
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			task.cancel();
			return isCancelled();
		}

		/** Actually cancels this future. Should only be invoked by {@link QueuedTask#run()}. */
		protected void doCancel() {
			super.cancel(false);
		}
	}

	public synchronized <T> QueuedTaskFuture<T> submit(String description,
			Function<CancelIndicator, T> task) {
		return submit(new Object(), description, task, false);
	}

	public synchronized <T> QueuedTaskFuture<T> submit(Object queueId, String description,
			Function<CancelIndicator, T> task) {
		return submit(queueId, description, task, false);
	}

	public synchronized <T> QueuedTaskFuture<T> submitAndCancelPrevious(Object queueId, String description,
			Function<CancelIndicator, T> task) {
		return submit(queueId, description, task, true);
	}

	protected synchronized <T> QueuedTaskFuture<T> submit(Object queueId, String description,
			Function<CancelIndicator, T> task, boolean cancelPrevious) {

		if (cancelPrevious) {
			cancelAll(queueId);
		}
		QueuedTask<T> callable = createQueuedTask(queueId, description, task);
		pendingTasks.add(callable);
		doSubmitPending();
		return callable.result;
	}

	protected synchronized void doSubmitPending() {
		QueuedTask<?> next;
		while ((next = pollNext()) != null) {
			doSubmit(next);
		}
	}

	protected synchronized void doSubmit(QueuedTask<?> task) {
		if (runningTasks.putIfAbsent(task.queueId, task) != null) {
			throw new IllegalStateException("executor inconsistency: queue ID already in progress: " + task.queueId);
		}
		executorService.submit(task); // will eventually invoke #onDone()
	}

	protected synchronized void onDone(QueuedTask<?> task) {
		if (runningTasks.remove(task.queueId) == null) {
			throw new IllegalStateException("executor inconsistency: queue ID not in progress: " + task.queueId);
		}
		doSubmitPending();
	}

	protected synchronized QueuedTask<?> pollNext() {
		Iterator<QueuedTask<?>> iter = pendingTasks.iterator();
		while (iter.hasNext()) {
			QueuedTask<?> curr = iter.next();
			boolean isBlocked = runningTasks.containsKey(curr.queueId);
			if (!isBlocked) {
				iter.remove();
				return curr;
			}
		}
		return null;
	}

	public synchronized void cancelAll() {
		Stream.concat(runningTasks.values().stream(), pendingTasks.stream())
				.forEach(t -> t.cancel());
	}

	public synchronized void cancelAll(Object queueId) {
		Stream.concat(runningTasks.values().stream(), pendingTasks.stream())
				.filter(t -> queueId.equals(t.queueId))
				.forEach(t -> t.cancel());
	}

	/**
	 * Blocks until all tasks complete that are running or pending at the time of invocation of this method OR are being
	 * submitted by other threads while this method is waiting. Thus, this method waits for this executor to idle.
	 */
	public /* NOT synchronized */ void join() {
		CompletableFuture<Void> allTasks;
		while ((allTasks = allTasksOrNull()) != null) {
			allTasks.join();
		}
	}

	/** Same as {@link #allTasks()}, but returns <code>null</code> iff there are no running or pending tasks. */
	public synchronized CompletableFuture<Void> allTasksOrNull() {
		if (runningTasks.isEmpty() && pendingTasks.isEmpty()) {
			return null;
		}
		return allTasks();
	}

	/**
	 * Returns a future that will complete when all tasks complete that are running or pending <em>at the time of
	 * invocation of this method</em>. Tasks submitted after this method returns will not be considered. Never returns
	 * <code>null</code>.
	 */
	public synchronized CompletableFuture<Void> allTasks() {
		CompletableFuture<?>[] allTasks = Stream.concat(runningTasks.values().stream(), pendingTasks.stream())
				.map(t -> t.result)
				.collect(Collectors.toList())
				.toArray(CompletableFuture[]::new);
		return CompletableFuture.allOf(allTasks);
	}

	public synchronized void shutdown() {
		MoreExecutors.shutdownAndAwaitTermination(executorService, 2500, TimeUnit.MILLISECONDS);
		cancelAll();
	}

	/** May be invoked from arbitrary threads. */
	protected /* NOT synchronized */ <T> QueuedTask<T> createQueuedTask(Object queueId, String description,
			Function<CancelIndicator, T> task) {

		return new QueuedTask<>(queueId, description, task);
	}

	/** May be invoked from arbitrary threads. */
	protected /* NOT synchronized */ boolean isCancellation(Throwable th) {
		return th instanceof CancellationException || operationCanceledManager.isOperationCanceledException(th);
	}
}
