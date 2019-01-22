/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.util;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.function.Consumer;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;

import com.google.inject.Inject;

/**
 * Test helper for initializing shipped code from the external libraries. This is bypassing normal logic controlled by
 * the {@link ExternalLibrariesActivator#INCLUDES_BUILT_INS_SYSTEM_PROPERTY} that is not suitable for some tests setups.
 */
public class ShippedCodeInitializeTestHelper {

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/**
	 * Set up shipped projects in all {@link ExternalLibrariesActivator#EXTERNAL_LIBRARIES_SUPPLIER locations}.
	 *
	 * Setting this property is necessary since {@link ExternalLibraryPreferenceStore#resetDefaults()} will transitively
	 * call {@link ExternalLibrariesActivator#requiresInfrastructureForLibraryManager()}.
	 */
	public void setupBuiltIns() {
		System.setProperty(ExternalLibrariesActivator.INCLUDES_BUILT_INS_SYSTEM_PROPERTY, "true");

		forAllLocations(externalLibraryPreferenceStore::add);
		assertTrue("Error while saving external library preference changes.",
				externalLibraryPreferenceStore.save(new NullProgressMonitor()).isOK());
	}

	/** Tear down shipped projects in all {@link ExternalLibrariesActivator#EXTERNAL_LIBRARIES_SUPPLIER locations}. */
	public void tearDownBuiltIns() {
		System.setProperty(ExternalLibrariesActivator.INCLUDES_BUILT_INS_SYSTEM_PROPERTY, "");

		forAllLocations(externalLibraryPreferenceStore::remove);
		assertTrue("Error while saving external library preference changes.",
				externalLibraryPreferenceStore.save(new NullProgressMonitor()).isOK());
	}

	private void forAllLocations(Consumer<URI> consume) {
		ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER.get().keySet().forEach(consume);
	}
}
