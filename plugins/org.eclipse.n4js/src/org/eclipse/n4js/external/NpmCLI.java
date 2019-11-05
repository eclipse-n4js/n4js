/**
 * Copyright (c) 2019 NumberFour AG.
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.BinariesCommandFactory;
import org.eclipse.n4js.binaries.nodejs.NodeYarnProcessBuilder;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceModel;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.utils.ProcessExecutionCommandStatus;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class for installing npm dependencies into the external library.
 */
@Singleton
public class NpmCLI {

	@Inject
	private BinariesCommandFactory commandFactory;

	@Inject
	private ProcessExecutionCommandStatus executor;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private NpmLogger logger;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private NpmBinary npmBinary;

	@Inject
	private SemverHelper semverHelper;

	/** Simple validation if the package name is not null or empty */
	public boolean invalidPackageName(N4JSProjectName packageName) {
		return packageName == null || packageName.getRawName().trim().isEmpty();
	}

	/** Simple validation if the package version is not null or empty */
	public boolean invalidPackageVersion(String packageVersion) {
		return packageVersion == null || (!packageVersion.isEmpty() && !packageVersion.startsWith("@"));
	}

	/** Returns the version of the system's npm binary or <code>null</code> in case of error. */
	public VersionNumber getNpmVersion() {
		final ProcessResult result = commandFactory.checkBinaryVersionCommand(npmBinary).execute();
		if (result.isOK()) {
			final String output = result.getStdOut();
			return semverHelper.parseVersionNumber(output.trim());
		}
		return null;
	}

	/**
	 * Uninstalls an npm package. The path to the npm package (passed via {@code requestedChange.location}) must conform
	 * to either one of two the following scenarios:
	 *
	 * <pre>
	 * Scenario 1: The npm is a direct child of a {@code node_modules} folder
	 *
	 *  MyProject
	 *    package.json
	 *    node_modules
	 *       express    <- uninstall
	 * </pre>
	 *
	 * <pre>
	 * Scenario 2: The npm is a direct child of {@code @n4jsd} folder which in turn is a direct child of {@code node_modules}
	 *
	 *  MyProject
	 *    package.json
	 *    node_modules
	 *       &#64;n4jsd
	 *          express    <- uninstall
	 * </pre>
	 *
	 * @param monitor
	 *            progress monitor tracking the progress of the action
	 * @param status
	 *            status, into which the status of the method call can be merged
	 * @param requestedChange
	 *            change object containing the information (e.g. change type, path and version of npm package etc.)
	 *            about the npm to be uninstalled.
	 * @return collection of actual changes
	 * @throws IllegalArgumentException
	 *             if the requested change is not of type {@code LibraryChangeType.Uninstall}
	 * @throws IllegalStateException
	 *             if the the npm is neither direct child of {@code node_modules} nor {@code node_modules/@n4jsd}
	 */
	public Collection<LibraryChange> uninstall(IProgressMonitor monitor, MultiStatus status,
			LibraryChange requestedChange) {

		if (requestedChange.type != LibraryChangeType.Uninstall) {
			throw new IllegalArgumentException(
					"The expected change type is " + LibraryChangeType.Uninstall + " but is " + requestedChange.type);
		}

		String msg = "Uninstalling npm package '" + requestedChange.name + "'";
		MultiStatus resultStatus = statusHelper.createMultiStatus(msg);
		SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
		SafeURI<?> nodeModulesLocationURI = externalLibraryWorkspace
				.getRootLocationForResource(requestedChange.location);

		List<N4JSProjectName> packageNames = new ArrayList<>();
		if ((requestedChange.type == LibraryChangeType.Uninstall) &&
				ExternalLibraryPreferenceModel.isNodeModulesLocation(nodeModulesLocationURI)) {
			packageNames.add(requestedChange.name);
		}

		File nodeModulesLocation = nodeModulesLocationURI.getParent().toJavaIoFile();
		// Assume that the parent of node_modules folder is the root of the project which contains package.json
		// We call npm uninstall in this root folder
		IStatus installStatus = uninstall(packageNames, nodeModulesLocation);
		subMonitor.worked(1);

		Collection<LibraryChange> actualChanges = new LinkedList<>();
		if (installStatus == null || !installStatus.isOK()) {
			resultStatus.merge(installStatus);
		} else {
			File npmDirectory = requestedChange.location.toJavaIoFile();
			String actualVersion = getActualVersion(npmDirectory.toPath());
			if (actualVersion.isEmpty()) {
				actualChanges.add(new LibraryChange(LibraryChangeType.Removed, requestedChange.location,
						requestedChange.name, requestedChange.version));
			}
		}

		if (!resultStatus.isOK()) {
			logger.logInfo("Some packages could not be uninstalled due to errors, see log for details.");
			status.merge(resultStatus);
		}

		return actualChanges;
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
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return multi status with children for each issue during processing
	 */
	public Collection<LibraryChange> batchInstall(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> requestedChanges, FileURI target) {

		return batchInstallInternal(monitor, status, requestedChanges, target);
	}

	private Collection<LibraryChange> batchInstallInternal(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> requestedChanges, FileURI target) {

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.setTaskName("Installing npm packages.");

		// Convert platform URI to local (e.g. file) URI
		File installPath = target.toJavaIoFile();

		// for installation, we invoke npm only once for all packages
		final List<Pair<N4JSProjectName, String>> packageNamesAndVersions = Lists.newArrayList();
		for (LibraryChange reqChg : requestedChanges) {
			if (reqChg.type == LibraryChangeType.Install) {
				packageNamesAndVersions.add(Tuples.pair(reqChg.name, "@" + reqChg.version));
			}
		}

		IStatus installStatus = install(packageNamesAndVersions, installPath);
		subMonitor.worked(1);

		Set<LibraryChange> actualChanges;
		MultiStatus batchStatus = statusHelper.createMultiStatus("Installing npm packages.");
		if (installStatus == null || !installStatus.isOK()) {
			actualChanges = Collections.emptySet();
			batchStatus.merge(installStatus);
		} else {
			actualChanges = computeActualChanges(installPath.toPath(), requestedChanges, batchStatus);
		}

		if (!batchStatus.isOK()) {
			logger.logInfo("Some packages could not be installed due to errors, see log for details.");
			status.merge(batchStatus);
		}

		return actualChanges;
	}

	private Set<LibraryChange> computeActualChanges(Path installPath, Collection<LibraryChange> requestedChanges,
			MultiStatus mergeStatusHere) {
		Set<LibraryChange> result = new LinkedHashSet<>();
		Path nodeModulesFolder = installPath.resolve(N4JSGlobals.NODE_MODULES);
		for (LibraryChange reqChg : requestedChanges) {
			if (reqChg.type == LibraryChangeType.Install) {
				Path completePath = nodeModulesFolder.resolve(reqChg.name.getRawName());
				String actualVersion = getActualVersion(completePath);
				if (actualVersion.isEmpty()) {
					// unable to load version from package.json:
					// -> retry loading from node_modules folder located in yarn workspace root folder
					NodeModulesFolder nmf = nodeModulesDiscoveryHelper.getNodeModulesFolder(installPath);
					if (nmf.isYarnWorkspace) {
						Path nodeModulesFolderInYarnWorkspaceRoot = nmf.nodeModulesFolder.toPath();
						completePath = nodeModulesFolderInYarnWorkspaceRoot.resolve(reqChg.name.getRawName());
						actualVersion = getActualVersion(completePath);
					}
				}
				if (actualVersion.isEmpty()) {
					String msg = "Error reading package json when " + reqChg.toString();
					IStatus packJsonError = statusHelper.createError(msg);
					mergeStatusHere.merge(packJsonError);
				} else {
					FileURI actualLocation = new FileURI(URIUtils.toFileUri(completePath));
					LibraryChange actualChange = new LibraryChange(LibraryChangeType.Added, actualLocation,
							reqChg.name, actualVersion);
					result.add(actualChange);
				}
			}
		}
		return result;
	}

	private String getActualVersion(Path completePath) {
		URI location = URIUtils.toFileUri(completePath);
		String versionStr = projectDescriptionLoader
				.loadVersionAndN4JSNatureFromProjectDescriptionAtLocation(new FileURI(location))
				.getFirst();

		return Strings.nullToEmpty(versionStr);
	}

	/**
	 * Installs npm package with given name at the given path. Updates dependencies in the package.json of that
	 * location. If there is no package.json at that location npm errors will be logged to the error log. In that case
	 * npm/yarn usual still installs requested dependency (if possible).
	 *
	 * @param packageNamesAndVersions
	 *            to be installed
	 * @param installPath
	 *            path where package is supposed to be installed
	 */
	private IStatus install(List<Pair<N4JSProjectName, String>> packageNamesAndVersions, File installPath) {
		List<String> packageNamesAndVersionsMerged = new ArrayList<>(packageNamesAndVersions.size());
		for (Pair<N4JSProjectName, String> pair : packageNamesAndVersions) {
			N4JSProjectName packageName = pair.getFirst();
			String packageVersion = pair.getSecond();

			// FIXME better error reporting (show all invalid names/versions, not just the first)
			if (invalidPackageName(packageName)) {
				return statusHelper.createError("Malformed npm package name: '" + packageName + "'.");
			}
			if (invalidPackageVersion(packageVersion)) {
				return statusHelper.createError("Malformed npm package version: '" + packageVersion + "'.");
			}

			String nameAndVersion = packageVersion.isEmpty() ? packageName.getRawName()
					: packageName.getRawName() + packageVersion;
			packageNamesAndVersionsMerged.add(nameAndVersion);
		}

		IStatus status = executor.execute(
				() -> commandFactory.createInstallPackageCommand(installPath, packageNamesAndVersionsMerged, true),
				"Error while installing npm package.");

		// TODO IDE-3136 / GH-1011 workaround for a problem in node related to URL/GitHub version requirements
		// In case of a dependency like "JSONSelect@dbo/JSONSelect" (wherein "dbo/JSONSelect" is a GitHub version
		// requirement), the first installation works fine, but subsequent installations of additional npm packages may
		// uninstall(!) the earlier package that used a URL/GitHub version requirement. This is supposed to be fixed
		// in npm version 5.7.1. As a work-around we run a plain "npm install" after every installation of new packages,
		// which should re-install the package with a URL/GitHub version requirement.
		boolean isNpmUsed = !isYarnUsed(installPath);
		if (isNpmUsed) {
			VersionNumber currNpmVersion = getNpmVersion();
			VersionNumber fixedNpmVersion = SemverUtils.createVersionNumber(5, 7, 1);
			if (currNpmVersion != null && SemverMatcher.compareLoose(currNpmVersion, fixedNpmVersion) < 0) {
				IStatus workaroundStatus = executor.execute(
						() -> commandFactory.createInstallPackageCommand(installPath, Collections.emptyList(), false),
						"Error while running \"npm install\" after installing npm packages.");
				MultiStatus combinedStatus = statusHelper
						.createMultiStatus("Installing npm packages with additional \"npm install\" afterwards.");
				combinedStatus.merge(status);
				combinedStatus.merge(workaroundStatus);
				status = combinedStatus;
			}
		}

		return status;
	}

	/**
	 * Uninstalls package under given name at the given path. Updates dependencies in the package.json of that location.
	 * If there is no package.json at that location npm errors will be logged to the error log. In that case npm usual
	 * still uninstalls requested dependency (if possible).
	 *
	 * @param packageNames
	 *            package names to be uninstalled
	 * @param uninstallPath
	 *            the folder where the npm uninstall command will be executed
	 * @return status status describing the execution status of this method
	 */
	private IStatus uninstall(List<N4JSProjectName> packageNames, File uninstallPath) {
		for (N4JSProjectName packageName : packageNames) {
			if (invalidPackageName(packageName)) {
				return statusHelper.createError("Malformed npm package name: '" + packageName + "'.");
			}
		}

		return executor.execute(
				() -> commandFactory.createUninstallPackageCommand(uninstallPath, packageNames, true),
				"Error while uninstalling npm package.");
	}

	/**
	 * Runs a plain 'npm/yarn install' in a given folder.
	 */
	public IStatus runNpmYarnInstall(File invocationPath) {
		IStatus status = executor.execute(
				() -> commandFactory.createInstallEverythingCommand(invocationPath, true),
				"Error while installing npm package.");

		return status;
	}

	/** See {@link NodeYarnProcessBuilder#isYarnUsed(Path)}. */
	public boolean isYarnUsed(SafeURI<?> safeURI) {
		URI projectURI = safeURI.toURI();
		// convert platform URI to local (e.g. file) URI
		URI projectFileURI = projectURI.isFile() ? projectURI : CommonPlugin.asLocalURI(projectURI);
		File projectFile = new File(projectFileURI.toFileString()).getAbsoluteFile();
		return isYarnUsed(projectFile);
	}

	/** See {@link NodeYarnProcessBuilder#isYarnUsed(Path)}. */
	public boolean isYarnUsed(File invocationPath) {
		return commandFactory.isYarnUsed(invocationPath);
	}
}
