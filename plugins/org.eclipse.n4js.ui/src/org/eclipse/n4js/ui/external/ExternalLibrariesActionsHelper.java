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
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.projectModel.dependencies.ProjectDependenciesHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.utils.N4JSDataCollectors;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.inject.Inject;

/**
 * Bundles all maintenance actions of the library manager including the 'Big Button' action
 */
public class ExternalLibrariesActionsHelper {

	@Inject
	private StatusHelper statusHelper;
	@Inject
	private LibraryManager libManager;
	@Inject
	private ProjectDependenciesHelper dependenciesHelper;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private ExternalLibrariesReloadHelper externalLibrariesReloadHelper;
	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	/** Streamlined process of calculating and installing the dependencies without cleaning npm cache. */
	public void cleanAndInstallAllDependencies(SubMonitor monitor, MultiStatus multistatus) {
		cleanAndInstallAllDependencies(monitor, multistatus, false);
	}

	/** Streamlined process of calculating and installing the dependencies, npm cache cleaning forced by passed flag */
	public void cleanAndInstallAllDependencies(SubMonitor monitor, MultiStatus multistatus, boolean removeNpmCache) {
		try (Measurement m = N4JSDataCollectors.dcInstallHelper.getMeasurement("Install Missing Dependencies")) {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 10);

			// remove npm cache
			if (removeNpmCache) {
				maintenanceCleanNpmCache(multistatus, subMonitor.split(1));
			}

			// remove npms
			maintenanceDeleteNpms(multistatus);

			// install npms from target platform
			Map<String, NPMVersionRequirement> dependenciesToInstall = null;
			try (Measurement mm = N4JSDataCollectors.dcCollectMissingDeps
					.getMeasurement("Collect Missing Dependencies")) {

				dependenciesToInstall = dependenciesHelper.computeDependenciesOfWorkspace();
				dependenciesHelper.fixDependenciesToInstall(dependenciesToInstall);
			}

			// install dependencies and force external library workspace reload
			try (Measurement mm = N4JSDataCollectors.dcInstallMissingDeps
					.getMeasurement("Install missing dependencies")) {

				IStatus status = libManager.installNPMs(dependenciesToInstall, true, subMonitor.split(9));
				if (!status.isOK()) {
					multistatus.merge(status);
				}
			}
		}
	}

	/**
	 * Performs {@link LibraryManager#cleanCache(IProgressMonitor)}. If that operation fails, status is mergegd into
	 * provided status.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 */
	public void maintenanceCleanNpmCache(final MultiStatus multistatus, IProgressMonitor monitor) {
		IStatus status = libManager.cleanCache(monitor);
		if (!status.isOK()) {
			multistatus.merge(status);
		}
	}

	/**
	 * Actions to be taken if deleting npms is requested.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 */
	public void maintenanceDeleteNpms(final MultiStatus multistatus) {
		// get target platform folder (contains node_modules, package.json, etc.)
		File npmFolder = locationProvider.getTargetPlatformInstallFolder();

		// delete whole target platform folder
		if (npmFolder.exists()) {
			FileDeleter.delete(npmFolder, (IOException ioe) -> multistatus.merge(
					statusHelper.createError("Exception during deletion of the npm folder.", ioe)));
		}

		if (!npmFolder.exists()) {
			// recreate npm folder
			if (!locationProvider.repairNpmFolderState()) {
				multistatus.merge(statusHelper
						.createError("The target platform location folder was not recreated correctly."));
			}
		} else {
			// should never happen
			multistatus.merge(statusHelper.createError("Could not verify deletion of " + npmFolder.getAbsolutePath()));
		}
		// other actions like reinstall depends on this state
		externalLibraryWorkspace.updateState();
	}

	/**
	 * Updates all affected state after maintenance actions have been performed. In particular updates state of the
	 * workspace, persisted preferences state, and displayed preferences.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 */
	public void maintenanceUpateState(final MultiStatus multistatus, IProgressMonitor monitor) {
		externalLibraryWorkspace.updateState();
		try {
			// we are running batch operations, where we know for sure
			// type definitions were updated at the beginning,
			// hence skip refreshing type definitions
			externalLibrariesReloadHelper.reloadLibraries(false, monitor);
		} catch (Exception e) {
			multistatus.merge(
					statusHelper.createError("Error when reloading libraries after maintenance actions.", e));
		}
	}

}
