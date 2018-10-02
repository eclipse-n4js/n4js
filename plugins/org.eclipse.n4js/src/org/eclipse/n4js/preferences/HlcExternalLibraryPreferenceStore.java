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

import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.xtext.xbase.lib.StringExtensions.isNullOrEmpty;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.utils.StatusHelper;

/**
 * OSGi based implementation of a preference store for built-in libraries.
 * <p>
 * This class requires a running {@link Platform platform}.
 */
@Singleton
public class HlcExternalLibraryPreferenceStore extends ExternalLibraryPreferenceStoreImpl {

	private static final Logger LOGGER = Logger.getLogger(HlcExternalLibraryPreferenceStore.class);

	private static final String QUALIFIER = ExternalLibraryPreferenceStore.class.getName();
	private static final String CONFIGURATION_KEY = QUALIFIER + ".configuration";

	@Inject
	private StatusHelper statusHelper;

	@Override
	protected IStatus doSave(final ExternalLibraryPreferenceModel modelToSave) {
		final IEclipsePreferences node = InstanceScope.INSTANCE.getNode(QUALIFIER);
		node.put(CONFIGURATION_KEY, modelToSave.toJsonString());
		try {
			node.flush();
			return OK_STATUS;
		} catch (final BackingStoreException e) {
			final String message = "Unexpected error when trying to persist external library preferences.";
			LOGGER.error(message, e);
			return statusHelper.createError(message, e);
		}
	}

	@Override
	protected ExternalLibraryPreferenceModel doLoad() {
		final Preferences node = InstanceScope.INSTANCE.getNode(QUALIFIER);
		final String jsonString = node.get(CONFIGURATION_KEY, "");
		if (isNullOrEmpty(jsonString)) {
			return ExternalLibraryPreferenceModel.createDefaultForN4Product();
		}
		return ExternalLibraryPreferenceModel.createFromJson(jsonString);
	}

	@Override
	protected ExternalLibraryPreferenceModel getDefaults() {
		return ExternalLibraryPreferenceModel.createDefaultForN4Product();
	}

}
