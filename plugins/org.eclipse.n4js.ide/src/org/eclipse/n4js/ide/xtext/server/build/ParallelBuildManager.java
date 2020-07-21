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
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * GH-1552: Experimental parallelization
 */
public class ParallelBuildManager {
	private final ExecutorService pool = Executors.newFixedThreadPool(3);
	private final Collection<? extends ParallelJob<?>> jobs;
	private final Map<Object, ParallelJob<?>> jobMap = new HashMap<>();
	private final Map<Object, Set<Object>> dependencyMap = new HashMap<>();
	private final Map<Object, Set<Object>> waitingProjectsMap = new HashMap<>();
	private final Set<ParallelJob<?>> startJobs = new HashSet<>();
	private final Set<Object> currentJobs = new HashSet<>();

	static abstract class ParallelJob<T> implements Runnable {
		protected ParallelBuildManager pbm;

		@Override
		public void run() {
			runJob();
			pbm.scheduleNext(this);
		}

		abstract public void runJob();

		abstract public T getID();

		abstract public Collection<T> getDependencyIDs();
	}

	ParallelBuildManager(Collection<? extends ParallelJob<?>> jobs) {
		this.jobs = jobs;
	}

	private void init() {
		for (ParallelJob<?> job : jobs) {
			job.pbm = this;
			Object jobID = job.getID();
			jobMap.put(jobID, job);
		}
		for (ParallelJob<?> job : jobs) {
			Object jobID = job.getID();

			Collection<Object> dependencies = new ArrayList<>(job.getDependencyIDs());
			dependencies.retainAll(jobMap.keySet()); // remove unavailable dependencies

			if (dependencies.isEmpty()) {
				startJobs.add(job);

			} else {
				dependencyMap.put(jobID, new HashSet<>(dependencies));

				for (Object dependency : dependencies) {
					waitingProjectsMap.computeIfAbsent(dependency, (ign) -> new HashSet<>()).add(jobID);
				}
			}
		}
	}

	private synchronized Set<Object> setFinishedGetNexts(Object jobID) {
		Set<Object> newReadyJobIDs = new HashSet<>();
		if (waitingProjectsMap.containsKey(jobID)) {
			Set<Object> waitingJobIDs = waitingProjectsMap.get(jobID);
			for (Object waitingJobID : waitingJobIDs) {
				if (dependencyMap.containsKey(waitingJobID)) {
					Set<Object> dependenciesOfJobID = dependencyMap.get(waitingJobID);
					dependenciesOfJobID.remove(jobID);

					if (dependenciesOfJobID.isEmpty()) {
						newReadyJobIDs.add(waitingJobID);
						dependencyMap.remove(waitingJobID);
					}
				}
			}
		}

		return newReadyJobIDs;
	}

	/** Builds the given set of jobs in parallel */
	public void run() {
		init();

		currentJobs.addAll(startJobs);
		for (ParallelJob<?> startJob : startJobs) {
			pool.execute(startJob);
		}

		try {
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void scheduleNext(ParallelJob<?> job) {
		Object jobID = job.getID();
		currentJobs.remove(job);

		Set<Object> newReadyJobIDs = setFinishedGetNexts(jobID);
		if (currentJobs.isEmpty() && newReadyJobIDs.isEmpty() && !dependencyMap.isEmpty()) {
			pool.shutdown();
			System.err.println("Did not finish all jobs");
		}
		for (Object newReadyJobID : newReadyJobIDs) {
			ParallelJob<?> readyJob = jobMap.get(newReadyJobID);
			currentJobs.add(readyJob);
			pool.execute(readyJob);
		}
		synchronized (pool) {
			pool.notifyAll();
			if (dependencyMap.isEmpty()) {
				pool.shutdown();
			}
		}

	}

}
