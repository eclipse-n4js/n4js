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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;

import com.google.inject.Inject;

/**
 * Test helper for initializing shipped code from the external libraries. This is bypassing normal logic controlled by
 * the {@link ExternalLibrariesActivator#INCLUDES_BUILT_INS_SYSTEM_PROPERTY} that is not suitable for some tests setups.
 */
public class ShippedCodeInitializeTestHelper {

	@Inject
	private LibraryManager libraryManager;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/**
	 * Set up shipped projects in all {@link ExternalLibrariesActivator#EXTERNAL_LIBRARIES_SUPPLIER locations}.
	 * <p>
	 * <b>Attention:</b> Works only with PluginUI tests!
	 * <p>
	 * Setting this property is necessary since {@link ExternalLibraryPreferenceStore#resetDefaults()} will transitively
	 * call {@link ExternalLibrariesActivator#requiresInfrastructureForLibraryManager()}.
	 */
	synchronized public void setupBuiltIns() {
		ProjectTestsUtils.waitForAllJobs();
		System.setProperty(ExternalLibrariesActivator.INCLUDES_BUILT_INS_SYSTEM_PROPERTY, "true");

		externalLibraryPreferenceStore.synchronizeNodeModulesFolders();
		ProjectTestsUtils.waitForAllJobs();

		libraryManager.synchronizeNpms(new NullProgressMonitor());
		ProjectTestsUtils.waitForAllJobs();
	}

	/** Tear down shipped projects in all {@link ExternalLibrariesActivator#EXTERNAL_LIBRARIES_SUPPLIER locations}. */
	synchronized public void tearDownBuiltIns() {
		ProjectTestsUtils.waitForAllJobs();
		libraryManager.deleteAllNodeModulesFolders(new NullProgressMonitor());

		System.setProperty(ExternalLibrariesActivator.INCLUDES_BUILT_INS_SYSTEM_PROPERTY, "");

		externalLibraryPreferenceStore.synchronizeNodeModulesFolders();
		ProjectTestsUtils.waitForAllJobs();

		libraryManager.synchronizeNpms(new NullProgressMonitor());
		ProjectTestsUtils.waitForAllJobs();
	}

}
