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

import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.utils.StatusHelper;

/**
 * OSGi backed preference store implementation for binaries and their corresponding file system locations.
 */
@Singleton
public class OsgiBinariesPreferenceStore extends BinariesPreferenceStore {

	private static final Logger LOGGER = Logger.getLogger(OsgiBinariesPreferenceStore.class);

	private static final String QUALIFIER = OsgiBinariesPreferenceStore.class.getName();

	@Inject
	private BinariesProvider binariesProvider;

	@Inject
	private StatusHelper statusHelper;

	@Override
	public IStatus save() {
		try {
			final IEclipsePreferences node = InstanceScope.INSTANCE.getNode(QUALIFIER);
			for (final Entry<Binary, URI> entry : getOrCreateState().entrySet()) {
				final URI path = entry.getValue();
				if (null != path) {
					final File file = new File(path);
					if (file.isDirectory()) {
						node.put(entry.getKey().getId(), file.getAbsolutePath());
					}
				} else {
					// Set to default.
					node.put(entry.getKey().getId(), "");
				}
			}
			node.flush();
			return OK_STATUS;
		} catch (final BackingStoreException e) {
			final String message = "Unexpected error when trying to persist binary preferences.";
			LOGGER.error(message, e);
			return statusHelper.createError(message, e);
		}
	}

	@Override
	protected Map<Binary, URI> init() {
		final Map<Binary, URI> initState = super.init();
		final Preferences node = InstanceScope.INSTANCE.getNode(QUALIFIER);
		for (final Binary binary : binariesProvider.getRegisteredBinaries()) {
			recursivePreferencesRead(initState, node, binary);
		}
		return initState;
	}

	/**
	 * Recursive read of the given preferences node based on the provided binary and its {@link Binary#getChildren()
	 * children}. Information read from preference node is stored in the provided state instance (instance is mutated).
	 *
	 * @param state
	 *            state instance to read to
	 * @param node
	 *            preferences node to read
	 * @param binary
	 *            the binary for which we read config
	 */
	private void recursivePreferencesRead(final Map<Binary, URI> state, final Preferences node,
			final Binary binary) {
		final String value = node.get(binary.getId(), "");
		if (!Strings.isNullOrEmpty(value)) {
			final File file = new File(value);
			if (file.isDirectory()) {
				state.put(binary, file.toURI());
			}
		}
		binary.getChildren().forEach(child -> recursivePreferencesRead(state, node, child));
	}

}
