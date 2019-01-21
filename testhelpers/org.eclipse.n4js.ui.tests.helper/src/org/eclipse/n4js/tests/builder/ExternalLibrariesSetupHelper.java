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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Use this test helper to set up and tear down the external libraries.
 */
public class ExternalLibrariesSetupHelper {

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/** Sets up the known external library locations with the {@code node_modules} folder. */
	public void setupExternalLibraries(boolean initShippedCode) throws Exception {
		URI nodeModulesLocation = getCleanModulesLocation();

		if (initShippedCode) {
			shippedCodeInitializeTestHelper.setupBuiltIns();
		} else {
			externalLibraryPreferenceStore.add(nodeModulesLocation);
			final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
			assertTrue("Error while saving external library preference changes.", result.isOK());
		}

		Injector n4jsInjector = N4JSActivator.getInstance().getInjector("org.eclipse.n4js.N4JS");
		LibraryManager libMan = n4jsInjector.getInstance(LibraryManager.class);
		libMan.registerAllExternalProjects(new NullProgressMonitor());

		ProjectTestsUtils.waitForAutoBuild();
	}

	private URI getCleanModulesLocation() {
		URI nodeModulesLocation = locationProvider.getNodeModulesURI();
		URI targetPlatformFileLocation = locationProvider.getTargetPlatformFileLocation();
		File nodeModulesDir = new File(nodeModulesLocation);
		File targetPlatformFile = new File(targetPlatformFileLocation);

		// When running plugin tests on Jenkins it can happen that the modules location is not clean
		// because it still contains packages that were installed by other plugin tests.
		FileUtils.deleteFileOrFolder(nodeModulesDir);
		FileUtils.deleteFileOrFolder(targetPlatformFile);
		locationProvider.repairNpmFolderState();

		assertTrue("Provided location should be available.", nodeModulesDir.isDirectory());
		assertTrue("Provided location should be available.", targetPlatformFile.isFile());
		return nodeModulesLocation;
	}

	/** Tears down the external libraries. */
	public void tearDownExternalLibraries(boolean tearDownShippedCode) throws Exception {

		final URI nodeModulesLocation = locationProvider.getNodeModulesURI();
		externalLibraryPreferenceStore.remove(nodeModulesLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());

		if (tearDownShippedCode) {
			shippedCodeInitializeTestHelper.tearDownBuiltIns();
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
