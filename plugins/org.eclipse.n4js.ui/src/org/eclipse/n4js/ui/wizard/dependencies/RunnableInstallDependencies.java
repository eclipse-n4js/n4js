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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Runnable collector of the workspace projects setting files. Note single instance single use, not thread safe.
 */
public class RunnableInstallDependencies implements IRunnableWithProgress {
	private MultiStatus multistatus;
	private InstallOptions options;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private ExternalLibrariesActionsHelper librariesActionsHelper;

	@Inject
	private BinariesPreferenceStore preferenceStore;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	/** Logger that logs to the npm console which is visible by the user in his IDE. */
	@Inject
	private NpmLogger userLogger;

	/** needs to be called before {@link #run(IProgressMonitor)} */
	public void setInstallOptions(InstallOptions options) {
		this.options = options;
	}

	/** @return status of runnable */
	public synchronized IStatus getResultStatus() {
		if (multistatus == null)
			return statusHelper.createError(getClass().getName() + " was not called yet!");

		MultiStatus result = statusHelper.createMultiStatus("result");
		result.merge(multistatus);
		return result;
	}

	@Override
	synchronized public void run(IProgressMonitor pmonitor) {
		final SubMonitor monitor = SubMonitor.convert(pmonitor, 1);

		final boolean wasAutoBuilding = AutobuildUtils.get();

		AutobuildUtils.turnOff();

		multistatus = statusHelper
				.createMultiStatus("Status of setting up dependencies.");

		boolean userNpmrc = options.npmrc != null && options.npmrc.isEmpty() == false;
		if (userNpmrc) {
			File selectedNPMRC = getFileOrNull(options.npmrc);
			saveSettingsForNPM(multistatus, selectedNPMRC);
			if (!multistatus.isOK())
				return;
		}
		librariesActionsHelper.cleanAndInstallAllDependencies(monitor, multistatus, options.clearNpmCache);

		if (!multistatus.isOK())
			return;
		if (wasAutoBuilding)
			AutobuildUtils.turnOn();
	}

	/**
	 * information about {@code .npmrc} is deep in the NodeProcessBuilder and by design it is not exposed. We could
	 * redesign that part and expose it, but it makes sense to assume user selected {@code .npmrc} file while setting up
	 * the workspace should be used for further dependencies setups (e.g. quick-fixes in manifests) in this workspace
	 * hence we save provided {@code .npmrc} file in the preferences.
	 *
	 * @param status
	 *            used to accumulate operations result, if any
	 * @param selectedNPMRC
	 *            npmrc file to process
	 */
	private void saveSettingsForNPM(final MultiStatus status, File selectedNPMRC) {
		if (selectedNPMRC != null) {
			NpmrcBinary npmrcBinary = npmrcBinaryProvider.get();
			URI oldLocation = npmrcBinary.getUserConfiguredLocation();
			File npmrcFolder = selectedNPMRC.getParentFile();
			if (npmrcFolder != null) {
				URI newLocation = npmrcFolder.toURI();
				if (!newLocation.equals(oldLocation)) {
					userLogger.logInfo("dropping old npmrc : " + oldLocation);
					userLogger.logInfo("setting new npmrc : " + newLocation);
					preferenceStore.setPath(npmrcBinaryProvider.get(), newLocation);
					IStatus save = preferenceStore.save();
					status.add(save);
				}
			}
		}
	}

	/**
	 * Checks if file exists under path specified by the provided string.
	 *
	 * @return {@code File} instance or {@code null}
	 */
	private File getFileOrNull(String filePath) {
		if (!Strings.isNullOrEmpty(filePath)) {
			File fNPMRC = new File(filePath);
			if (fNPMRC.isFile())
				return fNPMRC;
		}
		return null;
	}

}
