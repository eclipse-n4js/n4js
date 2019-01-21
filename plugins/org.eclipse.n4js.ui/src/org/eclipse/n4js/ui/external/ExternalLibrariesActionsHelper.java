/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.external;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.utils.N4JSDataCollectors;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Bundles all maintenance actions of the library manager including the 'Big Button' action
 */
public class ExternalLibrariesActionsHelper {
	@Inject
	private StatusHelper statusHelper;
	@Inject
	private LibraryManager libManager;
	@Inject
	private ExternalLibraryPreferenceStore extLibPreferenceStore;
	@Inject
	private BinariesPreferenceStore binPreferenceStore;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	/**
	 * Streamlined process of calculating and installing the dependencies, npm cache cleaning forced by passed flag.
	 * <p>
	 * <b>IMPORTANT:</b> If <code>npmrcLocation</code> is given (and only then), this method will change the default
	 * <code>.npmrc</code> location in the Eclipse preferences and this value will stay in effect after this method
	 * returns. Rationale of original implementor:<br>
	 * <cite>information about {@code .npmrc} is deep in the NodeProcessBuilder and by design it is not exposed. We
	 * could redesign that part and expose it, but it makes sense to assume user selected {@code .npmrc} file while
	 * setting up the workspace should be used for further dependencies setups (e.g. quick-fixes in manifests) in this
	 * workspace hence we save provided {@code .npmrc} file in the preferences.</cite>
	 *
	 * @param npmrcLocation
	 *            optional path to an <code>.npmrc</code> file to be used during installation of dependencies.
	 */
	public void cleanAndInstallAllDependencies(Optional<Path> npmrcLocation, SubMonitor monitor,
			MultiStatus multiStatus) {

		try (Measurement m = N4JSDataCollectors.dcInstallHelper.getMeasurement("Install Missing Dependencies")) {

			// configure .npmrc
			if (npmrcLocation.isPresent()) {
				configureNpmrc(npmrcLocation.get(), multiStatus);
			}

			// remove npms
			maintenanceDeleteNpms(multiStatus);

			// install dependencies and force external library workspace reload
			try (Measurement mm = N4JSDataCollectors.dcInstallMissingDeps
					.getMeasurement("Install missing dependencies")) {

				IStatus status = libManager.runNpmInstallOnAllProjects(monitor);
				if (!status.isOK()) {
					multiStatus.merge(status);
				}
			}
		}
	}

	/**
	 * Set default <code>.npmrc</code> location in the Eclipse preferences. This setting will stay in effect until it is
	 * changed via this method or via the Eclipse preferences dialog, etc.
	 *
	 * @param npmrcLocation
	 *            path pointing to a folder containing an <code>.npmrc</code> file.
	 */
	public void configureNpmrc(final Path npmrcLocation, final MultiStatus multiStatus) {
		URI newLocation = npmrcLocation.toFile().toURI();
		NpmrcBinary npmrcBinary = npmrcBinaryProvider.get();
		URI oldLocation = npmrcBinary.getUserConfiguredLocation();
		if (!newLocation.equals(oldLocation)) {
			binPreferenceStore.setPath(npmrcBinary, newLocation);
			IStatus save = binPreferenceStore.save();
			multiStatus.add(save);
		}
	}

	/**
	 * Actions to be taken if deleting npms is requested.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 */
	public void maintenanceDeleteNpms(final MultiStatus multistatus) {

		for (URI nodeModulesLoc : extLibPreferenceStore.getNodeModulesLocations()) {
			File nodeModulesFile = new File(nodeModulesLoc.getPath());
			// delete whole target platform folder
			if (nodeModulesFile.exists()) {
				FileDeleter.delete(nodeModulesFile, (IOException ioe) -> multistatus.merge(
						statusHelper.createError("Exception during deletion of the npm folder.", ioe)));
			}

			if (nodeModulesFile.exists()) {
				// should never happen
				multistatus.merge(statusHelper
						.createError("Could not verify deletion of " + nodeModulesFile.getAbsolutePath()));
			}
		}

		// other actions like reinstall depends on this state
		externalLibraryWorkspace.updateState();
	}

}
