/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * GH-1552: Experimental parallelization
 */
public class ParallelBuildManager<ID> {

	private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

	private final Collection<? extends ParallelJob<ID>> jobs;

	static abstract class ParallelJob<T> {

		abstract public void runJob();

		abstract public T getID();

		abstract public Collection<T> getDependencyIDs();

		@Override
		public String toString() {
			return String.valueOf(getID());
		}
	}

	ParallelBuildManager(Collection<? extends ParallelJob<ID>> jobs) {
		this.jobs = jobs;
	}

	/** Builds the given set of jobs in parallel */
	public void run() {
		Map<ID, ForkJoinTask<Void>> allTasks = new ConcurrentHashMap<>();
		class Impl extends RecursiveTask<Void> {

			private final ParallelJob<ID> src;

			Impl(ParallelJob<ID> src) {
				this.src = src;
			}

			@Override
			protected Void compute() {
				Collection<ID> dependencyIDs = src.getDependencyIDs();
				for (ID dependency : dependencyIDs) {
					ForkJoinTask<Void> predecessor = allTasks.get(dependency);
					if (predecessor != null) {
						predecessor.join();
					}
				}
				src.runJob();
				return null;
			}
		}

		forkJoinPool.invoke(new RecursiveTask<Void>() {
			@Override
			protected Void compute() {
				for (ParallelJob<ID> job : jobs) {
					Impl buildTask = new Impl(job);
					allTasks.put(job.getID(), buildTask);
					buildTask.fork();
				}
				for (ForkJoinTask<Void> buildTask : allTasks.values()) {
					buildTask.join();
				}
				return null;
			}
		});
	}

}
