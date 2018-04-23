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

import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.N4_NPM_FOLDER_SUPPLIER;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.repairNpmFolderState;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.GitCloneSupplier;
import org.eclipse.n4js.ui.preferences.external.MaintenanceActionsButtonListener;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.inject.Inject;

/**
 * Similar to {@link MaintenanceActionsButtonListener} actions, but dedicated for different UI.
 */
public class ExternalLibrariesActionsHelper {

	@Inject
	private StatusHelper statusHelper;
	@Inject
	private LibraryManager npmManager;

	@Inject
	private GitCloneSupplier gitSupplier;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private ExternalLibrariesReloadHelper externalLibrariesReloadHelper;

	/**
	 * Performs {@link LibraryManager#cleanCache(IProgressMonitor)}. If that operation fails, status is mergegd
	 * into provided status.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 */
	public void maintenanceCleanNpmCache(final MultiStatus multistatus, IProgressMonitor monitor) {
		IStatus status = npmManager.cleanCache(monitor);
		if (!status.isOK()) {
			multistatus.merge(status);
		}
	}

	/**
	 * Actions to be taken if reseting type definitions is requested.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 */
	public void maintenanceResetTypeDefinitions(final MultiStatus multistatus) {
		// get folder
		File typeDefinitionsFolder = gitSupplier.get();

		if (typeDefinitionsFolder.exists()) {
			FileDeleter.delete(typeDefinitionsFolder, (IOException ioe) -> multistatus.merge(
					statusHelper.createError("Exception during deletion of the type definitions.", ioe)));
		}

		if (!typeDefinitionsFolder.exists()) {
			// recreate npm folder
			if (!gitSupplier.repairTypeDefinitions()) {
				multistatus.merge(
						statusHelper.createError("The type definitions folder was not recreated correctly."));
			}
		} else { // should never happen
			multistatus.merge(statusHelper
					.createError("Could not verify deletion of " + typeDefinitionsFolder.getAbsolutePath()));
		}
	}

	/**
	 * Actions to be taken if deleting npms is requested.
	 *
	 * @param multistatus
	 *            the status used accumulate issues
	 */
	public void maintenanceDeleteNpms(final MultiStatus multistatus) {
		// get folder
		File npmFolder = N4_NPM_FOLDER_SUPPLIER.get();

		if (npmFolder.exists()) {
			FileDeleter.delete(npmFolder, (IOException ioe) -> multistatus.merge(
					statusHelper.createError("Exception during deletion of the npm folder.", ioe)));
		}

		if (!npmFolder.exists()) {
			// recreate npm folder
			if (!repairNpmFolderState()) {
				multistatus.merge(statusHelper
						.createError("The npm folder was not recreated correctly."));
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
	 */
	public void installNoUpdate(final Map<String, String> versionedPackages, final MultiStatus multistatus,
			final IProgressMonitor monitor) {
		IStatus status = npmManager.installNPMs(versionedPackages, monitor);
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
