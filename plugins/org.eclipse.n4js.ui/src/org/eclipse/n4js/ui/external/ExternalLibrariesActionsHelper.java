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
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.ui.preferences.external.MaintenanceActionsButtonListener;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.inject.Inject;

/**
 * Similar to {@link MaintenanceActionsButtonListener} actions, but dedicated for different UI.
 */
public class ExternalLibrariesActionsHelper {
	static private final DataCollector dcInstallHelper = DataCollectors.INSTANCE
			.getOrCreateDataCollector("External Libraries Install Helper");
	private static final DataCollector dcCollectMissingDependencies = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Collect missing dependencies", "External Libraries Install Helper");
	private static final DataCollector dcInstallMissingDependencies = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Install missing dependencies", "External Libraries Install Helper");

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
		try (ClosableMeasurement m = dcInstallHelper.getClosableMeasurement("Install Missing Dependencies")) {
			SubMonitor subMonitor2 = monitor.split(1);

			// remove npm cache
			if (removeNpmCache) {
				maintenanceCleanNpmCache(multistatus, subMonitor2);
			}

			// remove npms
			maintenanceDeleteNpms(multistatus);

			// install npms from target platform
			Map<String, NPMVersionRequirement> dependenciesToInstall = null;
			try (ClosableMeasurement mm = dcCollectMissingDependencies
					.getClosableMeasurement("Collect Missing Dependencies")) {

				dependenciesToInstall = dependenciesHelper.computeDependenciesOfWorkspace();
				dependenciesHelper.fixDependenciesToInstall(dependenciesToInstall);
			}

			// install dependencies and force external library workspace reload
			try (ClosableMeasurement mm = dcInstallMissingDependencies
					.getClosableMeasurement("Install missing dependencies")) {

				SubMonitor subMonitor3 = monitor.split(45);
				installNoUpdate(dependenciesToInstall, true, multistatus, subMonitor3);
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
		} else {// should never happen
			multistatus
					.merge(statusHelper.createError("Could not verify deletion of " + npmFolder.getAbsolutePath()));
		}
		// other actions like reinstall depends on this state
		externalLibraryWorkspace.updateState();
	}

	/**
	 * Installs npm packages with provide names and versions. Note that in case package has no version it is expected
	 * that empty string is provided.
	 *
	 * Rebuild of externals is not triggered, hence caller needs to take care of that, e.g. by calling
	 * {@link #maintenanceUpateState}
	 *
	 * @param forceReloadAll
	 *            Specifies whether after the installation all external libraries in the external library workspace
	 *            should be reloaded and rebuilt (cf.
	 *            {@link LibraryManager#reloadAllExternalProjects(IProgressMonitor)}). If {@code false}, only the set of
	 *            packages that was created and/or updated by this install call will be scheduled for a reload.
	 */
	public void installNoUpdate(final Map<String, NPMVersionRequirement> versionedPackages,
			boolean forceReloadAll, final MultiStatus multistatus, final IProgressMonitor monitor) {
		IStatus status = libManager.installNPMs(versionedPackages, forceReloadAll, monitor);
		if (!status.isOK())
			multistatus.merge(status);
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
