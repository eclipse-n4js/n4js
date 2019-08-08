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
package org.eclipse.n4js.preferences;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.projectModel.locations.FileURI;

/**
 * Representation of a preference store for external libraries.
 */
public interface ExternalLibraryPreferenceStore extends Iterable<FileURI> {
	/** Code used in {@link #save(IProgressMonitor)} */
	public static final int STATUS_CODE_SAVED_CHANGES = 1;
	/** Code used in {@link #save(IProgressMonitor)} */
	public static final int STATUS_CODE_NO_CHANGES = 2;

	/**
	 * Returns with a collection of configured external library locations.
	 *
	 * @return a collection of configured external library locations.
	 */
	Collection<FileURI> getLocations();

	/**
	 * Returns with a collection of configured node_module locations.
	 *
	 * @return a collection of configured node_module locations.
	 */
	Collection<FileURI> getNodeModulesLocations();

	/**
	 * Adds a new external library configuration entry to the preferences. Has no effect if the location already exists.
	 * Clients must call {@link #save(IProgressMonitor)} to persist the changes.
	 *
	 * @param location
	 *            the external library location to add. Must not be {@code null}.
	 */
	void add(final FileURI location);

	/**
	 * Removes a external library configuration entry from the preferences. Has no effect if the location does not
	 * exist. Clients must call {@link #save(IProgressMonitor)} to persist the changes.
	 *
	 * @param location
	 *            the external library location to remove. Must not be {@code null}.
	 */
	void remove(final FileURI location);

	/**
	 * Moves the external library up in the ordered list. Has no effect if the location is already the first element, or
	 * the location does not exist.
	 *
	 * @param location
	 *            the location to move up.
	 */
	void moveUp(final FileURI location);

	/**
	 * Moves the external library down in the ordered list. Has no effect if the location is already the last element,
	 * or the location does not exist.
	 *
	 * @param location
	 *            the location to move down.
	 */
	void moveDown(final FileURI location);

	/**
	 * Resets the state of the configuration to the default state. Explicit {@link #save(IProgressMonitor)} should be
	 * invoked to make the changes persistent.
	 */
	void resetDefaults();

	/**
	 * Invalidates its internal volatile state and restores it with the persisted state.
	 */
	void invalidate();

	/**
	 * Makes the current state of the preferences persistent. Has no effect if the current state of the model equals
	 * with the state of the persisted one. In such cases this method returns with {@code OK} status without doing any
	 * further process.
	 *
	 * @param monitor
	 *            the monitor for the save progress. Optional, can be {@code null}, in such cases an
	 *            {@link NullProgressMonitor} will be used instead.
	 *
	 * @return a status representing the outcome of the save operation. If changes have been saved, the status code is
	 *         {@link #STATUS_CODE_SAVED_CHANGES}. If there were no changes, the status code is
	 *         {@link #STATUS_CODE_NO_CHANGES}.
	 */
	IStatus save(IProgressMonitor monitor);

	/**
	 * Adds the listener. Has no effect if the listener is either {@code null} or already registered one.
	 *
	 * @param listener
	 *            the listener to add. Can be {@code null}.
	 */
	void addListener(StoreUpdatedListener listener);

	/**
	 * Removes the listener. Has no effect if the listener is either {@code null} or it is a not registered one.
	 *
	 * @param listener
	 *            the listener to remove. Can be {@code null}.
	 */
	void removeListener(StoreUpdatedListener listener);

	/** Triggers synchronization of the stored node_modules folders with the ones that actually exist. */
	IStatus synchronizeNodeModulesFolders();

	/**
	 * Converts the given external library root location URIs into an iterable of existing external folder locations
	 * URIs.
	 *
	 * @param externalRootLocations
	 *            an iterable of external library root locations.
	 * @return an iterable of URIs pointing to the external project locations nested in the external root locations.
	 */
	public Iterable<FileURI> convertToProjectRootLocations(Iterable<FileURI> externalRootLocations);

	/**
	 * Returns with an iterator pointing to all existing external project root locations after checking that each
	 * project has an existing N4JS manifest file.
	 */
	@Override
	default Iterator<FileURI> iterator() {
		return convertToProjectRootLocations(getLocations()).iterator();
	}

	/**
	 * External library preference store update listener.
	 */
	static interface StoreUpdatedListener {

		/**
		 * Fired when the persisted state of the {@link ExternalLibraryPreferenceStore preference store} has been
		 * changed. The argument has the most recent persisted state.
		 *
		 * @param store
		 *            the updated store with the most recent persisted state.
		 * @param monitor
		 *            monitor for the notification process.
		 */
		void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor);

	}

}
