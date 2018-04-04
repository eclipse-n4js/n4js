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

import static com.google.common.base.Preconditions.checkNotNull;

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
		return packageName == null || packageName.trim().isEmpty() || packageName.contains("@");
	}

	/** Simple validation if the package version is not null or empty */
	public boolean invalidPackageVersion(String packageVersion) {
		return packageVersion == null || (!packageVersion.isEmpty() && !packageVersion.startsWith("@"));
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

		int pckCount = packageNames.size();
		SubMonitor subMonitor = SubMonitor.convert(monitor, pckCount + 1);
		File installPath = new File(locationProvider.getTargetPlatformInstallLocation());

		int i = 0;
		for (String pckName : packageNames) {
			String jobType = install ? "Fetching '" : "Removing '";
			String msgHead = jobType + pckName;
			String msgTail = " [package " + i++ + " of " + pckCount + "]";

			// switch between install and uninstall
			IStatus packageProcessingStatus;
			if (install) {
				String packageVersion = Strings.nullToEmpty(versions.get(pckName));
				String msgVersion = packageVersion.isEmpty() ? "" : "in version " + packageVersion;
				subMonitor.setTaskName(msgHead + "' " + msgVersion + msgTail);
				packageProcessingStatus = install(pckName, packageVersion, installPath);
			} else {
				subMonitor.setTaskName(msgHead + "' " + msgTail);
				packageProcessingStatus = uninstall(pckName, installPath);
			}
			subMonitor.worked(1);

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

		String nameAndVersion = packageVersion.isEmpty() ? packageName : packageName + packageVersion;

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

	/**
	 * Cleans npm cache. Please note:
	 * <p>
	 * <i>"It should never be necessary to clear the cache for any reason other than reclaiming disk space"</i> (see
	 * <a href="https://docs.npmjs.com/cli/cache}">NPM Doc</a>)
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus cleanCacheInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		try {

			subMonitor.setTaskName("Cleaning npm cache");

			File targetInstallLocation = new File(locationProvider.getTargetPlatformInstallLocation());
			return clean(targetInstallLocation);

		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Cleans npm cache. Note that normally this has global side effects, i.e. it will delete npm cache for the user
	 * settings. While provided path does not have impact on effects of clean, it is used as working directory for
	 * invoking the command.
	 *
	 * @param cleanPath
	 *            to be uninstalled
	 */
	private IStatus clean(File cleanPath) {
		return executor.execute(
				() -> commandFactory.createCacheCleanCommand(cleanPath),
				"Error while cleaning npm cache.");
	}
}
