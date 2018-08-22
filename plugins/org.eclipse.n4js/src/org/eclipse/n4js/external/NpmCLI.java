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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.utils.ProcessExecutionCommandStatus;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.Lists;
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

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private SemverHelper semverHelper;

	/** Simple validation if the package name is not null or empty */
	public boolean invalidPackageName(String packageName) {
		return packageName == null || packageName.trim().isEmpty();
	}

	/** Simple validation if the package version is not null or empty */
	public boolean invalidPackageVersion(String packageVersion) {
		return packageVersion == null || (!packageVersion.isEmpty() && !packageVersion.startsWith("@"));
	}

	/**
	 * Batch uninstall of npm packages based on provided names. Method does not return early, it will try to process all
	 * packages, even if there are errors during processing of a specific package. All encountered errors are logged and
	 * added as children to the returned multi status.
	 *
	 * @param monitor
	 *            used to track progress
	 * @param status
	 *            into which the status of this method call can be merged
	 * @param requestedChanges
	 *            collection of all npm packages to be uninstalled
	 * @return multi status with children for each issue during processing
	 */
	public Collection<LibraryChange> batchUninstall(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> requestedChanges) {

		return batchInstallUninstallInternal(monitor, status, requestedChanges, LibraryChangeType.Uninstall);
	}

	/**
	 * Batch install of npm packages based on provided names. Method does not return early, it will try to process all
	 * packages, even if there are errors during processing of a specific package. All encountered errors are logged and
	 * added as children to the returned multi status.
	 *
	 * @param monitor
	 *            used to track progress
	 * @param status
	 *            into which the status of this method call can be merged
	 * @param requestedChanges
	 *            collection of all npm packages to be installed
	 * @return multi status with children for each issue during processing
	 */
	public Collection<LibraryChange> batchInstall(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> requestedChanges) {

		return batchInstallUninstallInternal(monitor, status, requestedChanges, LibraryChangeType.Install);
	}

	private Collection<LibraryChange> batchInstallUninstallInternal(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> requestedChanges, LibraryChangeType addressedType) {

		int pckCount = requestedChanges.size();
		MultiStatus batchStatus = statusHelper.createMultiStatus(addressedType.name() + "ing npm packages.");
		SubMonitor subMonitor = SubMonitor.convert(monitor, pckCount + 1);

		Collection<LibraryChange> actualChanges = new LinkedHashSet<>();
		File installPath = new File(locationProvider.getTargetPlatformInstallURI());

		final String jobName = addressedType.name().toLowerCase();

		if (addressedType == LibraryChangeType.Install) {
			// for installation, we invoke npm only once for all packages
			final List<Pair<String, String>> packageNamesAndVersions = Lists.newArrayList();
			for (LibraryChange reqChg : requestedChanges) {
				if (reqChg.type == addressedType) {
					packageNamesAndVersions.add(
							Tuples.pair(reqChg.name, "@" + reqChg.version));
				}
			}
			IStatus installStatus = install(packageNamesAndVersions, installPath);
			if (installStatus == null || !installStatus.isOK()) {
				batchStatus.merge(installStatus);
			} else {
				String nodeModulesFolder = TargetPlatformInstallLocationProvider.NODE_MODULES_FOLDER;
				Path basePath = installPath.toPath().resolve(nodeModulesFolder);
				for (LibraryChange reqChg : requestedChanges) {
					if (reqChg.type == addressedType) {
						Path completePath = basePath.resolve(reqChg.name);
						String actualVersion = getActualVersion(batchStatus, reqChg, completePath);
						URI actualLocation = URI.createFileURI(completePath.toString());
						LibraryChange actualChange = new LibraryChange(LibraryChangeType.Added, actualLocation,
								reqChg.name, actualVersion);
						actualChanges.add(actualChange);
					}
				}
			}
		} else {
			// for all library change types other than installation, we invoke npm once per package
			// FIXME invoke npm only once also when uninstalling and updating
			int i = 0;
			for (LibraryChange reqChg : requestedChanges) {
				if (subMonitor.isCanceled())
					throw new OperationCanceledException("Operation <" + jobName + "> was canceled.");

				if (reqChg.type == addressedType) {
					String msgTail = " [package " + i++ + " of " + pckCount + "]";
					subMonitor.setTaskName(reqChg.toString() + msgTail);
					LibraryChange actChg = installUninstall(batchStatus, installPath, reqChg);
					if (actChg != null) {
						actualChanges.add(actChg);
					}
					subMonitor.worked(1);
					if (!batchStatus.isOK()) {
						break; // fail fast and do not let the user wait for the problem
					}
				}
			}
		}

		if (!batchStatus.isOK()) {
			logger.logInfo("Some packages could not be " + jobName + "ed due to errors, see log for details.");
			status.merge(batchStatus);
		}

		return actualChanges;
	}

	private LibraryChange installUninstall(MultiStatus batchStatus, File installPath, LibraryChange reqChg) {
		LibraryChange actualChange = null;
		IStatus packageProcessingStatus = null;

		String nodeModulesFolder = TargetPlatformInstallLocationProvider.NODE_MODULES_FOLDER;
		Path completePath = installPath.toPath().resolve(nodeModulesFolder).resolve(reqChg.name);
		LibraryChangeType actualChangeType = null;
		String actualVersion = "";
		if (reqChg.type == LibraryChangeType.Install) {
			packageProcessingStatus = install(
					Collections.singletonList(Tuples.pair(reqChg.name, "@" + reqChg.version)),
					installPath);

			if (packageProcessingStatus == null || !packageProcessingStatus.isOK()) {
				batchStatus.merge(packageProcessingStatus);
				return null;
			}

			actualChangeType = LibraryChangeType.Added;
			actualVersion = getActualVersion(batchStatus, reqChg, completePath);
		}
		if (reqChg.type == LibraryChangeType.Uninstall) {
			actualVersion = getActualVersion(batchStatus, reqChg, completePath);

			packageProcessingStatus = uninstall(reqChg.name, installPath);
			if (packageProcessingStatus == null || !packageProcessingStatus.isOK()) {
				batchStatus.merge(packageProcessingStatus);
				return null;
			}

			actualChangeType = LibraryChangeType.Removed;
		}

		if (packageProcessingStatus != null && packageProcessingStatus.isOK()) {
			URI actualLocation = URI.createFileURI(completePath.toString());
			actualChange = new LibraryChange(actualChangeType, actualLocation, reqChg.name, actualVersion);
		}

		return actualChange;
	}

	private String getActualVersion(MultiStatus batchStatus, LibraryChange reqChg, Path completePath) {
		URI location = URI.createFileURI(completePath.toString());
		String versionStr = projectDescriptionLoader.loadVersionAndN4JSNatureFromProjectDescriptionAtLocation(location)
				.getFirst();
		if (versionStr == null) {
			String msg = "Error reading package json when " + reqChg.toString();
			IStatus packJsonError = statusHelper.createError(msg);
			logger.logError(msg, new IllegalStateException(msg));
			batchStatus.merge(packJsonError);
			return "";
		}
		return versionStr;
	}

	/**
	 * Installs package with given name at the given path. Updates dependencies in the package.json of that location. If
	 * there is no package.json at that location npm errors will be logged to the error log. In that case npm usual
	 * still installs requested dependency (if possible).
	 *
	 * @param packageNamesAndVersions
	 *            to be installed
	 * @param installPath
	 *            path where package is supposed to be installed
	 */
	private IStatus install(List<Pair<String, String>> packageNamesAndVersions, File installPath) {
		List<String> packageNamesAndVersionsMerged = new ArrayList<>(packageNamesAndVersions.size());
		for (Pair<String, String> pair : packageNamesAndVersions) {
			String packageName = pair.getFirst();
			String packageVersion = pair.getSecond();

			// FIXME better error reporting (show all invalid names/versions, not just the first)
			if (invalidPackageName(packageName)) {
				return statusHelper.createError("Malformed npm package name: '" + packageName + "'.");
			}
			if (invalidPackageVersion(packageVersion)) {
				return statusHelper.createError("Malformed npm package version: '" + packageVersion + "'.");
			}

			// TODO IDE-3136 / GH-1011 workaround for missing support of GitHub version requirements
			NPMVersionRequirement packageVersionParsed = semverHelper.parse(packageVersion.substring(1));
			if (packageVersionParsed instanceof GitHubVersionRequirement) {
				// In case of a dependency like "JSONSelect@dbo/JSONSelect" (wherein "dbo/JSONSelect"
				// is a GitHub version requirement), we only report "JSONSelect@" to npm. For details
				// why this is necessary, see GH-1011.
				packageVersion = "@";
			}

			String nameAndVersion = packageVersion.isEmpty() ? packageName : packageName + packageVersion;
			packageNamesAndVersionsMerged.add(nameAndVersion);
		}

		return executor.execute(
				() -> commandFactory.createInstallPackageCommand(installPath, packageNamesAndVersionsMerged, true),
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

			File targetInstallLocation = new File(locationProvider.getTargetPlatformInstallURI());
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
