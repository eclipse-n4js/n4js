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

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.utils.collections.Arrays2;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Representation of a preference store for external libraries.
 */
public interface ExternalLibraryPreferenceStore extends Iterable<URI> {

	/**
	 * Returns with a collection of configured external library locations.
	 *
	 * @return a collection of configured external library locations.
	 */
	Collection<URI> getLocations();

	/**
	 * Adds a new external library configuration entry to the preferences. Has no effect if the location already exists.
	 * Clients must call {@link #save(IProgressMonitor)} to persist the changes.
	 *
	 * @param location
	 *            the external library location to add. Must not be {@code null}.
	 */
	void add(final URI location);

	/**
	 * Removes a external library configuration entry from the preferences. Has no effect if the location does not
	 * exist. Clients must call {@link #save(IProgressMonitor)} to persist the changes.
	 *
	 * @param location
	 *            the external library location to remove. Must not be {@code null}.
	 */
	void remove(final URI location);

	/**
	 * Moves the external library up in the ordered list. Has no effect if the location is already the first element, or
	 * the location does not exist.
	 *
	 * @param location
	 *            the location to move up.
	 */
	void moveUp(final URI location);

	/**
	 * Moves the external library down in the ordered list. Has no effect if the location is already the last element,
	 * or the location does not exist.
	 *
	 * @param location
	 *            the location to move down.
	 */
	void moveDown(final URI location);

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
	 * @return returns a status representing the outcome of the save operation.
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

	/**
	 * Returns with an iterator pointing to all existing external project root locations after checking that each
	 * project has an existing N4JS manifest file.
	 */
	@Override
	default Iterator<URI> iterator() {
		return ExternalProjectLocationsProvider.INSTANCE.convertToProjectRootLocations(getLocations()).iterator();
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

	/**
	 * Provider that converts external library root locations into nested external project locations.
	 */
	static enum ExternalProjectLocationsProvider {

		/** Shared provider instance. */
		INSTANCE;

		private static final Logger LOGGER = Logger.getLogger(ExternalProjectLocationsProvider.class);

		/**
		 * Converts the given external library root location URIs into an iterable of existing external folder locations
		 * URIs.
		 *
		 * @param externalRootLocations
		 *            an iterable of external library root locations.
		 * @return an iterable of URIs pointing to the external project locations nested in the external root locations.
		 */
		public Iterable<URI> convertToProjectRootLocations(Iterable<URI> externalRootLocations) {
			return from(externalRootLocations).transformAndConcat(new Function<URI, Iterable<URI>>() {

				private final Predicate<File> existingFolders = file -> null != file && file.exists()
						&& file.isDirectory();

				private final Predicate<File> existingFiles = file -> null != file && file.exists() && file.isFile();

				private final Function<File, Iterable<File>> toSubFiles = new Function<File, Iterable<File>>() {

					@Override
					public Iterable<File> apply(final File folder) {

						if (null == folder || !folder.isDirectory()) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Not a directory: " + folder + ".");
							}
							return emptyList();
						}

						final File[] files = folder.listFiles();
						if (Arrays2.isEmpty(files)) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("No resources were found under: " + folder + ".");
							}
							return emptyList();
						}

						return asList(files);
					}
				};

				@Override
				public Iterable<URI> apply(final URI rootLocation) {
					final File rootFolder = new File(rootLocation);
					if (existingFolders.apply(rootFolder)) {
						return from(toSubFiles.apply(rootFolder))
								.filter(existingFolders)
								.transformAndConcat(toSubFiles)
								.filter(existingFiles)
								.filter(file -> N4MF_MANIFEST.equals(file.getName()))
								.transform(file -> file.getParentFile())
								.filter(existingFolders)
								.transform(file -> file.toURI());
					}
					return emptyList();
				}

			});
		}

	}

}
