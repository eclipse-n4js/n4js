package org.eclipse.n4js.ui.external;

import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.builder.impl.ToBeBuilt;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

/**
 * A queue of package json files that need to be validated due to changes in node_modules folders.
 */
@SuppressWarnings("restriction")
@Singleton
public class OutdatedPackageJsonQueue {

	/**
	 * Encapsulates the changes that need to be performed after one or more projects have been removed / closed.
	 */
	public class Task {
		/**
		 * The names of the projects as encapsulated by this task.
		 */
		private final ImmutableSet<String> projectNames;
		/**
		 * The built data for this task.
		 */
		private final ToBeBuilt toBeBuilt;

		private final boolean forcedIndexSync;

		/**
		 * Constructor.
		 */
		protected Task(ImmutableSet<String> projectNames, ToBeBuilt toBeBuilt, boolean forcedIndexSync) {
			this.projectNames = projectNames;
			this.toBeBuilt = toBeBuilt;
			this.forcedIndexSync = forcedIndexSync;
		}

		/**
		 * Returns true if the is an empty task.
		 *
		 * @return true if empty.
		 */
		public boolean isEmpty() {
			return toBeBuilt.getToBeUpdated().isEmpty() && !forcedIndexSync;
		}

		/**
		 * Add the tasks again to the top of the queue.
		 */
		public void reschedule() {
			Set<URI> toBeUpdated = toBeBuilt.getToBeUpdated();
			if (toBeUpdated != null && !toBeUpdated.isEmpty()) {
				ToBeBuilt scheduleMe = new ToBeBuilt();
				scheduleMe.getToBeUpdated().addAll(toBeUpdated);
				insert(projectNames, scheduleMe, forcedIndexSync);
			}
		}

		/**
		 * Returns the project names.
		 */
		public ImmutableSet<String> getProjectNames() {
			return projectNames;
		}

		/**
		 * Returns the files that need to be build.
		 */
		public ToBeBuilt getToBeBuilt() {
			return toBeBuilt;
		}
	}

	/**
	 * Use a concurrent linked queue internally to allow concurrent read and add operations.
	 */
	private final Deque<Task> internalQueue = new ConcurrentLinkedDeque<>();

	/**
	 * Add the given projects to the end of the build queue.
	 *
	 * @param projectNames
	 *            the projects to be cleaned.
	 * @param toBeBuilt
	 *            their contents.
	 */
	protected void enqueue(Set<String> projectNames, ToBeBuilt toBeBuilt, boolean forcedIndexSync) {
		internalQueue.addLast(new Task(ImmutableSet.copyOf(projectNames), toBeBuilt, forcedIndexSync));
	}

	/**
	 * Add the given projects to the beginning of the build queue.
	 *
	 * @param projectNames
	 *            the projects to be cleaned.
	 * @param toBeBuilt
	 *            their contents.
	 */
	protected void insert(Set<String> projectNames, ToBeBuilt toBeBuilt, boolean forcedIndexSync) {
		internalQueue.addFirst(new Task(ImmutableSet.copyOf(projectNames), toBeBuilt, forcedIndexSync));
	}

	/**
	 * Return the next task that contains all the enqueued projects.
	 *
	 * @return the normalized task that has all the stuff that is to be done.
	 */
	public Task exhaust() {
		Set<String> projectNames = new LinkedHashSet<>();
		ToBeBuilt toBeBuilt = new ToBeBuilt();
		Set<URI> toBeUpdated = toBeBuilt.getToBeUpdated();
		boolean forcedIndexSync = false;
		Task next = internalQueue.poll();
		while (next != null) {
			Set<URI> nextToBeUpdated = next.toBeBuilt.getToBeUpdated();
			if (next.forcedIndexSync) {
				forcedIndexSync = true;
			}
			if (nextToBeUpdated != null && !nextToBeUpdated.isEmpty()) {
				projectNames.addAll(next.projectNames);
				toBeUpdated.addAll(nextToBeUpdated);
			}
			next = internalQueue.poll();
		}
		return new Task(ImmutableSet.copyOf(projectNames), toBeBuilt, forcedIndexSync);
	}

}