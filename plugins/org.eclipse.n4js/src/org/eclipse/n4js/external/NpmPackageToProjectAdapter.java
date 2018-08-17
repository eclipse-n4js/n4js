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

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.xtext.util.Tuples.pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.util.Pair;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Adapts given npm package to n4js project form
 */
public class NpmPackageToProjectAdapter {

	private static Logger LOGGER = Logger.getLogger(NpmPackageToProjectAdapter.class);

	@Inject
	private NpmLogger logger;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private GitCloneSupplier gitCloneSupplier;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private SemverHelper semverHelper;

	/** Default filter for copying N4JSD project contents during adaptation */
	private final static Predicate<Path> COPY_N4JSD_PREDICATE = new Predicate<Path>() {
		private final static String SUFFIX = "." + N4JSGlobals.N4JSD_FILE_EXTENSION;

		@Override
		public boolean apply(Path path) {
			Path fileName = path.getFileName();
			return fileName != null && fileName.toString().endsWith(SUFFIX);
		}
	};

	/**
	 * Adapts npm packages in provided folder to the N4JS project structure. Only package folders which match requested
	 * packages are adapted. It is expected that npm flattens packages structures, therefore it is assumed that other
	 * folders are dependencies (also transitive) of the requested packages.
	 *
	 * Adaptation of a package P means:
	 * <ul>
	 * <li>Install corresponding type definitions package into the type definitions external library location</li>
	 * </ul>
	 *
	 * Returned set of N4JS project folders will not include those installed by the npm but without matching names in
	 * provided set of expected packages. Those packages are treated as transitive dependencies and are not returned to
	 * the caller.
	 *
	 * @param installedPackages
	 *            names of the expected packages
	 * @return pair of overall adaptation status and folders of successfully adapted npm packages
	 */
	public Pair<IStatus, Collection<File>> adaptPackages(Collection<String> installedPackages,
			Collection<String> uninstalledPackages) {
		final MultiStatus status = statusHelper.createMultiStatus("Status of adapting npm packages");
		final File nodeModulesFolder = locationProvider.getNodeModulesFolder();
		final Collection<String> installNames = newHashSet(installedPackages);
		final File[] npmPackageRoots = nodeModulesFolder
				.listFiles(packageName -> installNames.contains(packageName.getName()));
		final File n4jsdsFolder = getNpmsTypeDefinitionsFolder();

		// adapt installed
		for (File packageRoot : npmPackageRoots) {
			try {
				// add type definitions
				if (n4jsdsFolder != null) {
					addTypeDefinitions(packageRoot, n4jsdsFolder);
				}
			} catch (final Exception e) {
				String msg = "Unexpected error occurred while adapting '" + packageRoot.getName()
						+ "' npm package into N4JS format.";
				status.merge(statusHelper.createError(msg, e));
			}
		}

		// delete uninstalled type definitions
		final Collection<String> uninstallNames = newHashSet(uninstalledPackages);
		final File typeDefinitionsFolder = locationProvider.getTypeDefinitionsFolder();
		final File[] tdPackageRoots = typeDefinitionsFolder
				.listFiles(packageName -> {
					int index = packageName.getName().lastIndexOf("-n4jsd");
					if (index > 0) {
						String dirName = packageName.getName().substring(0, index);
						return uninstallNames.contains(dirName);
					} else {
						return false;
					}
				});
		for (File tdDir : tdPackageRoots) {
			FileUtils.deleteFileOrFolder(tdDir);
		}

		return pair(status, Arrays.asList(npmPackageRoots));
	}

	private static String NPM_DEFINITIONS_FOLDER_NAME = "npm";

	private File getNpmsTypeDefinitionsFolder() {
		return getNpmsTypeDefinitionsFolder(true);
	}

	/**
	 * Returns with the root folder of all available npm package definitions. Or returns with {@code null} if no
	 * definitions are available. Also performs an on demand {@code git pull}.
	 *
	 * @param performGitPull
	 *            {@code true} if a git pull has to be performed in the local clone.
	 *
	 * @return the root folder of all npm package definitions or {@code null} if missing.
	 */
	File getNpmsTypeDefinitionsFolder(final boolean performGitPull) {

		File repositoryLocation = new File(locationProvider.getTargetPlatformLocalGitRepositoryLocation());

		if (performGitPull && gitCloneSupplier.remoteRepoAvailable()) {
			// pull changes
			GitUtils.pull(repositoryLocation.toPath());
		}

		final File definitionsRoot = new File(repositoryLocation, NPM_DEFINITIONS_FOLDER_NAME);
		if (definitionsRoot.exists() && definitionsRoot.isDirectory()) {
			return definitionsRoot;
		} else {
			String msg = "Cannot locate local git repository clone for N4JS definition files: " + definitionsRoot + ".";
			LOGGER.error(msg);
			return null;
		}
	}

	/**
	 * Add type definitions (N4JSDs) to the npm package. Types are added only if matching version is found.
	 *
	 * This method suppresses any potential issues as adding type definitions to some npm package does not affect
	 * overall npm usage. Still, errors are {@link #LOGGER logged} to help troubleshooting potential issues and returns
	 * with an {@link IStatus status} instance that represents the problem if any.
	 *
	 * @param packageRoot
	 *            npm package folder.
	 * @param definitionsFolder
	 *            root folder for npm type definitions.
	 *
	 * @return a status representing the outcome of performed the operation.
	 */
	IStatus addTypeDefinitions(File packageRoot, File definitionsFolder) {
		URI packageURI = URI.createFileURI(packageRoot.getAbsolutePath());
		Pair<String, Boolean> info = projectDescriptionLoader
				.loadVersionAndN4JSNatureFromProjectDescriptionAtLocation(packageURI);
		boolean hasN4JSNature = info.getSecond();
		String packageJsonVersion = info.getFirst();

		if (hasN4JSNature) {
			return statusHelper.OK();
		}

		String packageName = packageRoot.getName();
		File packageN4JSDsRoot = new File(definitionsFolder, packageName);
		if (!packageN4JSDsRoot.isDirectory()) {
			String message = "No type definitions found for '" + packageRoot + "' npm package at '" + packageN4JSDsRoot;
			message += "'" + (!packageN4JSDsRoot.isDirectory() ? " (which is not a directory)" : "") + ".";
			logger.logInfo(message);
			LOGGER.info(message);
			return statusHelper.OK();
		}

		VersionNumber packageVersion = semverHelper.parseVersionNumber(packageJsonVersion);
		if (packageVersion == null) {
			final String message = "Cannot read version from package.json of npm package '" + packageName + "'.";
			logger.logInfo(message);
			LOGGER.error(message);
			return statusHelper.createError(message);
		}
		String[] list = packageN4JSDsRoot.list();
		Set<VersionNumber> availableTDVersions = new TreeSet<>(SemverMatcher::compareLoose);
		for (int i = 0; i < list.length; i++) {
			String version = list[i];
			VersionNumber availableTypeDefinitionsVersion = semverHelper.parseVersionNumber(version);
			if (availableTypeDefinitionsVersion != null) {
				availableTDVersions.add(availableTypeDefinitionsVersion);
			}
		}

		VersionNumber closestMatchingVersion = SemverUtils.findClosestMatching(availableTDVersions, packageVersion);
		if (closestMatchingVersion == null) {
			String details = "";
			if (availableTDVersions.isEmpty()) {
				details = " Cannot find any type definitions for  '" + packageName + "'.";
			} else if (1 == availableTDVersions.size()) {
				final VersionNumber head = availableTDVersions.iterator().next();
				details = " Type definitions are available only in version : " + head + ".";
			} else {
				final String versions = Iterables.toString(availableTDVersions);
				details = " Type definitions are available only in versions : " + versions + ".";
			}
			String message = "Type definitions for '" + packageName + "' npm package in version " + packageVersion
					+ " are not available." + details;
			logger.logInfo(message);
			LOGGER.info(message);
			return statusHelper.OK();
		}

		File packageVersionedN4JSDProjectRoot = new File(packageN4JSDsRoot, closestMatchingVersion.toString());
		if (!(packageVersionedN4JSDProjectRoot.exists() && packageVersionedN4JSDProjectRoot.isDirectory())) {
			final String message = "Cannot find type definitions folder for npm package '" + packageName
					+ "' for version '" + closestMatchingVersion + "'.";
			logger.logInfo(message);
			LOGGER.error(message);
			return statusHelper.createError(message);
		}

		Path sourcePath = packageVersionedN4JSDProjectRoot.toPath();
		IStatus status = copyTypeDefinitionPackage(packageName, sourcePath);
		if (!status.isOK()) {
			return status;
		}

		return statusHelper.OK();
	}

	private IStatus copyTypeDefinitionPackage(String packageName, Path sourcePath) {
		File typeDefFolder = locationProvider.getTypeDefinitionsFolder();
		Path newTargetPath = typeDefFolder.toPath().resolve(packageName + "-n4jsd");
		IStatus status = copyN4JSDFiles(packageName, sourcePath, newTargetPath);
		if (!status.isOK()) {
			return status;
		}

		status = copyFile(packageName, sourcePath, newTargetPath, N4JSGlobals.PACKAGE_JSON);
		if (!status.isOK()) {
			return status;
		}

		return statusHelper.OK();
	}

	/** copy all n4jsd files */
	private IStatus copyN4JSDFiles(String packageName, Path sourcePath, Path targetPath) {
		try {
			FileCopier.copy(sourcePath, targetPath, COPY_N4JSD_PREDICATE);
			return statusHelper.OK();
		} catch (IOException e) {
			final String message = "Error while trying to update type definitions content for '" + packageName
					+ "' npm package.";
			logger.logInfo(message);
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}
	}

	/** copy package.json fragment */
	private IStatus copyFile(String packageName, Path sourcePath, Path targetPath, String file) {
		try {
			Path sourceFragmentPath = sourcePath.resolve(file);
			if (sourceFragmentPath.toFile().isFile()) {
				Files.copy(
						sourceFragmentPath,
						targetPath.resolve(file),
						StandardCopyOption.REPLACE_EXISTING);
				return statusHelper.OK();
			}

			final String message = "Error while trying to copy the file '" + file +
					"' for '" + packageName + "' npm package: File not found.";
			logger.logInfo(message);
			LOGGER.error(message);
			return statusHelper.createError(message);
		} catch (IOException e) {
			final String message = "Error while trying to copy the file '" + file +
					"' for '" + packageName + "' npm package.";
			logger.logInfo(message);
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}
	}

}
