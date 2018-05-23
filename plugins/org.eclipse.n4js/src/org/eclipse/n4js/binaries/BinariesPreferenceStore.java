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
package org.eclipse.n4js.binaries;

import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.inject.Singleton;

/**
 * In-memory preference store implementation for binaries and their corresponding file system locations.
 */
@Singleton
public class BinariesPreferenceStore {

	private Map<Binary, URI> state;

	/**
	 * Sets the location of the given binary argument.
	 *
	 * @param binary
	 *            the binary which path should be modified.
	 * @param path
	 *            the new path to the binary.
	 */
	public void setPath(Binary binary, URI path) {
		getOrCreateState().put(binary, path);
	}

	/**
	 * Returns with the path to the binary.
	 *
	 * @param binaries
	 *            the binary to get its path.
	 * @return the path to the binary.
	 */
	public URI getPath(Binary binaries) {
		return getOrCreateState().get(binaries);
	}

	/**
	 * Saves the state of the current store and returns with a status representing the outcome of the save operation.
	 * Does nothing by default but returns with {@link Status#OK_STATUS OK status}.
	 *
	 * @return returns with the outcome of the save operation.
	 */
	public IStatus save() {
		return OK_STATUS;
	}

	/**
	 * Initializes the state of the store. By default returns with an empty map.
	 *
	 * @return a map representing the initialized state of the current store.
	 */
	protected Map<Binary, URI> init() {
		return newHashMap();
	}

	/**
	 * Returns with the underlying preference store state. If the state is not initialized yet, initializes it.
	 *
	 * @return the state of the preference store.
	 */
	protected final Map<Binary, URI> getOrCreateState() {
		if (null == state) {
			synchronized (BinariesPreferenceStore.class) {
				if (null == state) {
					state = init();
				}
			}
		}
		return state;
	}
}
