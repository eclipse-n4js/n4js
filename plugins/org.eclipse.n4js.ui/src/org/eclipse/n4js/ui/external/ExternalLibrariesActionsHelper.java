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
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.projectModel.dependencies.ProjectDependenciesHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
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
	private ProjectDependenciesHelper dependenciesHelper;
	@Inject
	private BinariesPreferenceStore preferenceStore;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private ExternalLibrariesReloadHelper externalLibrariesReloadHelper;
	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;
	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	/** Streamlined process of calculating and installing the dependencies without cleaning npm cache. */
	public void cleanAndInstallAllDependencies(SubMonitor monitor, MultiStatus multiStatus) {
		cleanAndInstallAllDependencies(Optional.absent(), false, monitor, multiStatus);
	}

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
	 * @param removeNpmCache
	 *            whether the npm cache should be cleared before installation of dependencies.
	 */
	public void cleanAndInstallAllDependencies(Optional<Path> npmrcLocation, boolean removeNpmCache,
			SubMonitor monitor, MultiStatus multiStatus) {
		try (Measurement m = N4JSDataCollectors.dcInstallHelper.getMeasurement("Install Missing Dependencies")) {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 10);

			// configure .npmrc
			if (npmrcLocation.isPresent()) {
				configureNpmrc(npmrcLocation.get(), multiStatus);
			}

			// remove npm cache
			if (removeNpmCache) {
				maintenanceCleanNpmCache(multiStatus, subMonitor.split(1));
			}

			// remove npms
			maintenanceDeleteNpms(multiStatus);

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
			preferenceStore.setPath(npmrcBinary, newLocation);
			IStatus save = preferenceStore.save();
			multiStatus.add(save);
		}
	}

	/**
	 * Performs {@link LibraryManager#cleanCache(IProgressMonitor)}. If that operation fails, status is merged into
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
