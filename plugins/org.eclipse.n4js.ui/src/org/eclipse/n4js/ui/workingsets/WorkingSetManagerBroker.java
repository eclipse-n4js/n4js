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

import java.util.Collection;

import org.eclipse.n4js.utils.Diff;

/**
 * Representation of a broker for registered {@link WorkingSetManager working set manager} instances.
 */
public interface WorkingSetManagerBroker extends MementoAware {

	/**
	 * Returns with all the available working set managers.
	 *
	 * @return a view of all available {@link WorkingSetManager working set manager} instances.
	 */
	Collection<WorkingSetManager> getWorkingSetManagers();

	/**
	 * Sets the active working set manager.
	 *
	 * @param workingSetManager
	 *            the working set manager that has to be selected as the active one.
	 */
	void setActiveManager(final WorkingSetManager workingSetManager);

	/**
	 * Returns with {@code true} if the working set manager argument is the currently active one. Otherwise returns with
	 * {@code false}.
	 *
	 * @param workingSetManager
	 *            the working set manager to test whether it is the currently active one or not.
	 * @return {@code true} if the argument is the currently active one, otherwise {@code false}
	 */
	boolean isActiveManager(final WorkingSetManager workingSetManager);

	/**
	 * Returns with the active working set manager. Could return with {@code null} if no active working set manager is
	 * available.
	 *
	 * @return the active working set manager, or {@code null}, if not yet available.
	 */
	WorkingSetManager getActiveManager();

	/**
	 * Returns with {@code true} if working sets are configured to be shown as top level elements in the common
	 * navigator.
	 *
	 * @return {@code true} if working sets are the top level elements, otherwise returns with {@code false}.
	 */
	boolean isWorkingSetTopLevel();

	/**
	 * Sets the boolean flag whether working sets or projects have to be show as top level elements in the navigator. If
	 * {@code true}, then working sets are configured to be the top level elements, if {@code false}, then projects.
	 *
	 * @param b
	 *            the boolean flag whether working sets has to be top level elements or not. {@code true} if the working
	 *            sets should be the top level elements in the navigator.
	 */
	void setWorkingSetTopLevel(boolean b);

	/**
	 * Adds a top level element configuration change listener to the broker. Listeners will be notified when the
	 * configuration has been changed via {@link #setWorkingSetTopLevel(boolean)} method. Has no effect if the identical
	 * listener has been already added.
	 *
	 * @param listener
	 *            the listener to add. Should not be {@code null}.
	 */
	void addTopLevelElementChangedListener(TopLevelElementChangedListener listener);

	/**
	 * Removes a top level element configuration change listener from the broker. Has no effect if the listener argument
	 * was not added to this broker previously.
	 *
	 * @param listener
	 *            the listener to remove. Should not be {@code null}.
	 */
	void removeTopLevelElementChangedListener(TopLevelElementChangedListener listener);

	/**
	 * Adds a working set manager state change listener to the broker. Listeners will be notified when the internal
	 * state of any available working set manager has been modified. Has no effect if the identical listener has been
	 * already added.
	 *
	 * @param listener
	 *            the listener to add. Should not be {@code null}.
	 */
	void addWorkingSetManagerStateChangedListener(WorkingSetManagerStateChangedListener listener);

	/**
	 * Removes the working set manager state change listener the broker. Has no effect if the listener argument was not
	 * added to this broker previously.
	 *
	 * @param listener
	 *            the listener to remove. Should not be {@code null}.
	 */
	void removeWorkingSetManagerStateChangedListener(WorkingSetManagerStateChangedListener listener);

	/**
	 * Called when the state of a working set manager has been changed. Notifies all registered
	 * {@link WorkingSetManagerStateChangedListener listener} instances.
	 *
	 * @param id
	 *            the unique identifier of the working set manager that has been changed.
	 *
	 * @param diff
	 *            the difference describing the actual change.
	 *
	 */
	public void fireWorkingSetManagerUpdated(final String id, final Diff<WorkingSet> diff);

	/**
	 * Asynchronously refreshes the navigator content. If the navigator is currently not visible, then a refresh event
	 * will be queued and the navigator will be refreshed on the next available time. Queuing multiple delayed refresh
	 * event have no side effect.
	 */
	void refreshNavigator();

}
