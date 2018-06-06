/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.external;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.eclipse.n4js.external.N4JSExternalProject;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

/**
 * Singleton that has information about the to-be-build external libraries.
 *
 * Is used by the eclipse builder to ensure externals are build before a real build is run and also by the
 * ExternalLibraryBuildJob.
 */
@Singleton
public class ExternalLibraryBuildQueue {

	/**
	 * Encapsulates the changes that need to be performed in the external workspace.
	 */
	public class Task {
		/**
		 * The projects that need to be build.
		 */
		public final Collection<N4JSExternalProject> toBuild;
		/**
		 * The projects that need to be cleaned.
		 */
		public final Collection<N4JSExternalProject> toClean;

		private Task(ImmutableSet<N4JSExternalProject> toBuild, ImmutableSet<N4JSExternalProject> toClean) {
			this.toBuild = toBuild;
			this.toClean = toClean;
		}

		/**
		 * Returns true if the is an empty task.
		 *
		 * @return true if empty.
		 */
		public boolean isEmpty() {
			return toBuild.isEmpty() && toClean.isEmpty();
		}

		/**
		 * Add the tasks again to the top of the queue.
		 */
		public void reschedule() {
			insert(toBuild, toClean);
		}
	}

	/*
	 * Use a concurrent linked queue internally to allow concurrent read and add operations.
	 */
	private final Deque<Task> internalQueue = new ConcurrentLinkedDeque<>();

	/**
	 * Add the given projects to the end of the build queue.
	 *
	 * @param toBuild
	 *            the projects to be build.
	 * @param toClean
	 *            the projects to be cleaned.
	 */
	public void enqueue(Collection<N4JSExternalProject> toBuild, Collection<N4JSExternalProject> toClean) {
		internalQueue.addLast(new Task(ImmutableSet.copyOf(toBuild), ImmutableSet.copyOf(toClean)));
	}

	/**
	 * Add the given projects to the beginning of the build queue.
	 *
	 * @param toBuild
	 *            the projects to be build.
	 * @param toClean
	 *            the projects to be cleaned.
	 */
	public void insert(Collection<N4JSExternalProject> toBuild, Collection<N4JSExternalProject> toClean) {
		internalQueue.addFirst(new Task(ImmutableSet.copyOf(toBuild), ImmutableSet.copyOf(toClean)));
	}

	/**
	 * Return the next task that contains all the enqueued projects. Last commands win, e.g. a subsequent clean
	 * overrules a previously added build.
	 *
	 * @return the normalized task that has all the stuff that is to be done.
	 */
	public Task exhaust() {
		Collection<N4JSExternalProject> toBuild = new LinkedHashSet<>();
		Collection<N4JSExternalProject> toClean = new LinkedHashSet<>();
		Task next = internalQueue.poll();
		while (next != null) {
			toBuild.removeAll(next.toClean);
			toClean.removeAll(next.toBuild);
			toBuild.addAll(next.toBuild);
			toClean.addAll(next.toClean);
			next = internalQueue.poll();
		}
		return new Task(ImmutableSet.copyOf(toBuild), ImmutableSet.copyOf(toClean));
	}

}
