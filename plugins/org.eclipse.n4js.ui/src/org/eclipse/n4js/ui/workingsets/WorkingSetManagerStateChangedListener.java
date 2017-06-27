/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.n4js.utils.Diff;

/**
 * Listener that will be notified when the internal state of any working set manager has been modified.
 */
public interface WorkingSetManagerStateChangedListener {

	/**
	 * Triggered when any available working set manager state has been changed. These notifications are guaranteed to be
	 * received on the UI thread.
	 *
	 * @param event
	 *            the event about the change.
	 */
	void workingSetManagerStateChanged(final WorkingSetManagerChangeEvent event);

	/**
	 * Event that is fired when the internal state of the working set manager has changed.
	 */
	public static class WorkingSetManagerChangeEvent {

		private final Diff<WorkingSet> diff;
		private final String id;

		/**
		 * Creates a new working set manager state change event with the given difference argument.
		 *
		 * @param diff
		 *            the difference describing the old and the new state.
		 */
		public WorkingSetManagerChangeEvent(final String id, final Diff<WorkingSet> diff) {
			this.id = id;
			this.diff = checkNotNull(diff, "diff");
		}

		/**
		 * Returns with the difference for the change event.
		 *
		 * @return the difference.
		 */
		public Diff<WorkingSet> getDiff() {
			return diff;
		}

		/**
		 * Returns with the ID of the working set manager that has been modified.
		 *
		 * @return the unique identifier of the working set manager that has been modified.
		 */
		public String getId() {
			return id;
		}

	}

}
