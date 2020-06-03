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
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton // ensures queue IDs are "global" within a single injector
public class LSPExecutorService {

	// FIXME support cancellation via the futures returned by #submit()!!!

	@Inject
	protected ExecutorService executorService;

	/** Global queue of all pending task across all queue IDs. */
	protected final List<QueuedTask<?>> pendingTasks = new ArrayList<>();
	/** Queue IDs with currently running tasks. */
	protected final Map<Object, QueuedTask<?>> runningTasks = new LinkedHashMap<>();

	protected final class QueuedTask<T> implements Runnable {
		final Object queueId;
		final Callable<T> callable;
		final CompletableFuture<T> result;

		public QueuedTask(Object queueId, Callable<T> callable) {
			this.queueId = Objects.requireNonNull(queueId);
			this.callable = Objects.requireNonNull(callable);
			this.result = new CompletableFuture<>();
		}

		@Override
		public void run() {
			try {
				T actualResult = callable.call();
				result.complete(actualResult);
			} catch (Exception e) {
				result.completeExceptionally(e);
			} finally {
				onDone(this);
			}
		}
	}

	public synchronized CompletableFuture<Void> submit(Runnable task) {
		return submit(new Object(), task);
	}

	public synchronized <T> CompletableFuture<T> submit(Callable<T> task) {
		return submit(new Object(), task);
	}

	public synchronized CompletableFuture<Void> submit(Object queueId, Runnable task) {
		return submit(queueId, task, null);
	}

	public synchronized <T> CompletableFuture<T> submit(Object queueId, Runnable task, T result) {
		return submit(queueId, () -> {
			task.run();
			return result;
		});
	}

	public synchronized <T> CompletableFuture<T> submit(Object queueId, Callable<T> task) {
		QueuedTask<T> callable = new QueuedTask<>(queueId, task);
		pendingTasks.add(callable);
		submitNext();
		return callable.result;
	}

	protected synchronized void submitNext() {
		QueuedTask<?> next = pollNext();
		submit(next);
	}

	protected synchronized void submit(QueuedTask<?> task) {
		if (runningTasks.putIfAbsent(task.queueId, task) != null) {
			throw new IllegalStateException("executor inconsistency: queue ID already in progress: " + task.queueId);
		}
		executorService.submit(task); // will eventually invoke #onDone()
	}

	protected synchronized void onDone(QueuedTask<?> task) {
		if (runningTasks.remove(task.queueId) == null) {
			throw new IllegalStateException("executor inconsistency: queue ID not in progress: " + task.queueId);
		}
		submitNext();
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
}
