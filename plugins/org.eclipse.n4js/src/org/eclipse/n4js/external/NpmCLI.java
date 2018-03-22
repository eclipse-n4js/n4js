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
package org.eclipse.n4js.external;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.utils.ProcessExecutionCommandStatus;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class for installing npm dependencies into the external library.
 */
@Singleton
public class NpmCLI {

	@Inject
	private BinaryCommandFactory commandFactory;

	@Inject
	private ProcessExecutionCommandStatus executor;

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private NpmLogger logger;

	/** Simple validation if the package name is not null or empty */
	public boolean invalidPackageName(String packageName) {
		return packageName == null || packageName.trim().isEmpty();
	}

	/** Simple validation if the package version is not null or empty */
	public boolean invalidPackageVersion(String packageVersion) {
		return packageVersion == null;
	}

	/**
	 * Batch install / uninstall of npm packages based on provided names. Provided boolean flag switches between install
	 * and uninstall operations. Method does not return early, it will try to process all packages, even if there are
	 * errors during processing of a specific package. All encountered errors are logged and added as children to the
	 * returned multi status.
	 *
	 * @param monitor
	 *            used to track progress
	 * @param packageNames
	 *            names of the packages to install or uninstall
	 * @param versions
	 *            versions of the packages to install. ignored when uninstalling
	 * @param install
	 *            used to switch between install and uninstall operations
	 * @return multi status with children for each issue during processing
	 */
	public MultiStatus batchInstallUninstall(IProgressMonitor monitor, Collection<String> packageNames,
			Map<String, String> versions, boolean install) {

		String batchType = install ? "installing" : "uninstalling";
		MultiStatus batchStatus = statusHelper.createMultiStatus("Status of " + batchType + " npm packages.");

		int packagesCount = packageNames.size();
		SubMonitor subMonitor = SubMonitor.convert(monitor, packagesCount + 1);
		File installPath = new File(locationProvider.getTargetPlatformInstallLocation());

		int i = 0;
		for (String packageName : packageNames) {
			String jobType = install ? "Fetching '" : "Removing '";
			String msg = jobType + packageName + "' package... [package " + i++ + " of " + packagesCount + "]";
			subMonitor.setTaskName(msg);
			subMonitor.worked(1);

			// switch between install and uninstall
			IStatus packageProcessingStatus;
			if (install) {
				String packageVersion = Strings.nullToEmpty(versions.get(packageName));
				packageProcessingStatus = install(packageName, packageVersion, installPath);
			} else {
				packageProcessingStatus = uninstall(packageName, installPath);
			}

			if (!packageProcessingStatus.isOK()) {
				logger.logError(packageProcessingStatus);
				batchStatus.merge(packageProcessingStatus);
			}
		}

		return batchStatus;
	}

	/**
	 * Installs package with given name at the given path. Updates dependencies in the package.json of that location. If
	 * there is no package.json at that location npm errors will be logged to the error log. In that case npm usual
	 * still installs requested dependency (if possible).
	 *
	 * @param packageName
	 *            to be installed
	 * @param installPath
	 *            path where package is supposed to be installed
	 */
	private IStatus install(String packageName, String packageVersion, File installPath) {
		if (invalidPackageName(packageName)) {
			return statusHelper.createError("Malformed npm package name: '" + packageName + "'.");
		}
		if (invalidPackageVersion(packageVersion)) {
			return statusHelper.createError("Malformed npm package version: '" + packageVersion + "'.");
		}

		String nameAndVersion = packageVersion.isEmpty() ? packageName : packageName + "@" + packageVersion;

		return executor.execute(
				() -> commandFactory.createInstallPackageCommand(installPath, nameAndVersion, true),
				"Error while installing npm package.");
	}

	/**
	 * Uninstalls package under given name at the given path. Updates dependencies in the package.json of that location.
	 * If there is no package.json at that location npm errors will be logged to the error log. In that case npm usual
	 * still uninstalls requested dependency (if possible).
	 *
	 * @param packageName
	 *            to be uninstalled
	 * @param uninstallPath
	 *            path where package is supposed to be uninstalled
	 */
	private IStatus uninstall(String packageName, File uninstallPath) {
		if (invalidPackageName(packageName)) {
			return statusHelper.createError("Malformed npm package name: '" + packageName + "'.");
		}
		return executor.execute(
				() -> commandFactory.createUninstallPackageCommand(uninstallPath, packageName, true),
				"Error while uninstalling npm package.");
	}

}
