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

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.net.URI;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.google.inject.Inject;

/**
 * Basic external library preference store implementation. This base class is independent from the actual
 * serialization/deserialization and persistence process.
 */
/* default */ abstract class ExternalLibraryPreferenceStoreImpl implements ExternalLibraryPreferenceStore {

	private final Collection<StoreUpdatedListener> listeners;

	private ExternalLibraryPreferenceModel model;

	/**
	 * Creates a new external library preference store.
	 */
	@Inject
	protected ExternalLibraryPreferenceStoreImpl() {
		listeners = newHashSet();
		model = getOrCreateModel();
	}

	@Override
	public Collection<URI> getLocations() {
		return getOrCreateModel().getExternalLibraryLocationsAsUris();
	}

	@Override
	public void add(final URI location) {
		getOrCreateModel().add(location);
	}

	@Override
	public void remove(final URI location) {
		getOrCreateModel().remove(location);
	}

	@Override
	public void moveUp(final URI location) {
		getOrCreateModel().moveUp(location);
	}

	@Override
	public void moveDown(final URI location) {
		getOrCreateModel().moveDown(location);
	}

	@Override
	public void resetDefaults() {
		model = getDefaults();
	}

	@Override
	public void invalidate() {
		model = doLoad();
	}

	@Override
	public final IStatus save(IProgressMonitor monitor) {

		if (getOrCreateModel().equals(doLoad())) {
			return OK_STATUS;
		}

		if (null == monitor) {
			monitor = new NullProgressMonitor();
		}
		final IStatus status = doSave(getOrCreateModel());
		if (null != status && status.isOK()) {
			notifyListeners(monitor);
		}
		return status;
	}

	@Override
	public void addListener(final StoreUpdatedListener listener) {
		if (null != listener) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(final StoreUpdatedListener listener) {
		if (null != listener) {
			listeners.remove(listener);
		}
	}

	/**
	 * Saves the given {@link ExternalLibraryPreferenceModel model} instance.
	 *
	 * @param modelToSave
	 *            the model instance to save.
	 * @return a status representing the outcome of the save operation.
	 */
	protected abstract IStatus doSave(ExternalLibraryPreferenceModel modelToSave);

	/**
	 * Returns with the {@link ExternalLibraryPreferenceModel model} instance created and initialized from the persisted
	 * state.
	 *
	 * @return the deserialized model instance.
	 */
	protected abstract ExternalLibraryPreferenceModel doLoad();

	/**
	 * Returns with the default {@link ExternalLibraryPreferenceModel model} instance.
	 *
	 * @return the model instance with the default values.
	 */
	protected ExternalLibraryPreferenceModel getDefaults() {
		return ExternalLibraryPreferenceModel.createDefault();
	}

	/**
	 * Notifies all registered listeners that the store's persistent state has been updated.
	 *
	 * @param monitor
	 *            monitor for the progress.
	 */
	protected void notifyListeners(final IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, listeners.size());
		for (final StoreUpdatedListener listener : listeners) {
			listener.storeUpdated(this, subMonitor.newChild(1));
		}
	}

	/**
	 * Returns with the backing model instance. Initializes it if it is not available yet.
	 *
	 * @return the model instance.
	 */
	protected final ExternalLibraryPreferenceModel getOrCreateModel() {
		if (null == model) {
			synchronized (ExternalLibraryPreferenceStoreImpl.class) {
				if (null == model) {
					model = doLoad();
				}
			}
		}
		return model;
	}

}
