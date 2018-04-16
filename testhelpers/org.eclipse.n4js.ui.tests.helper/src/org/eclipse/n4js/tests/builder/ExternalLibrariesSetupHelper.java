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
package org.eclipse.n4js.tests.builder;

import static org.eclipse.n4js.external.TypeDefinitionGitLocationProvider.TypeDefinitionGitLocation.PUBLIC_DEFINITION_LOCATION;
import static org.eclipse.n4js.external.TypeDefinitionGitLocationProvider.TypeDefinitionGitLocation.TEST_DEFINITION_LOCATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider.TypeDefinitionGitLocationProviderImpl;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.inject.Inject;

/**
 * Use this test helper to set up and tear down the external libraries.
 */
public class ExternalLibrariesSetupHelper {

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private TypeDefinitionGitLocationProvider gitLocationProvider;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/** Sets up the known external library locations with the {@code node_modules} folder. */
	public void setupExternalLibraries(boolean initShippedCode, boolean useSandboxN4JSD) throws Exception {
		if (useSandboxN4JSD) {
			((TypeDefinitionGitLocationProviderImpl) gitLocationProvider).setGitLocation(TEST_DEFINITION_LOCATION);
		}

		final URI nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation();
		File nodeModuleLocationFile = new File(nodeModulesLocation);
		if (!nodeModuleLocationFile.exists()) {
			nodeModuleLocationFile.createNewFile();
		}
		assertTrue("Provided npm location should be available.", nodeModuleLocationFile.exists());

		if (initShippedCode) {
			shippedCodeInitializeTestHelper.setupBuiltIns();
		} else {
			externalLibraryPreferenceStore.add(nodeModulesLocation);
			final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
			assertTrue("Error while saving external library preference changes.", result.isOK());
		}

		ProjectTestsUtils.waitForAutoBuild();
	}

	/** Tears down the external libraries. */
	public void tearDownExternalLibraries(boolean tearDownShippedCode) throws Exception {
		((TypeDefinitionGitLocationProviderImpl) gitLocationProvider).setGitLocation(PUBLIC_DEFINITION_LOCATION);

		final URI nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation();
		externalLibraryPreferenceStore.remove(nodeModulesLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());

		if (tearDownShippedCode) {
			shippedCodeInitializeTestHelper.teardowneBuiltIns();
		}

		// cleanup leftovers in the file system
		File nodeModuleLocationFile = new File(nodeModulesLocation);
		assertTrue("Provided npm location does not exist.", nodeModuleLocationFile.exists());
		assertTrue("Provided npm location is not a folder.", nodeModuleLocationFile.isDirectory());
		FileDeleter.delete(nodeModuleLocationFile);
		assertFalse("Provided npm location should be deleted.", nodeModuleLocationFile.exists());

		ProjectTestsUtils.waitForAutoBuild();
	}

}
