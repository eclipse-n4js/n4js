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
import static org.eclipse.n4js.external.libraries.PackageJson.PACKAGE_JSON;
import static org.eclipse.xtext.util.Tuples.pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.libraries.PackageJson;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.Version;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.io.FileCopier;
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
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private GitCloneSupplier gitCloneSupplier;

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
	 * <li>add n4jsd files from P's corresponding folder in the type definitions repository (if any),
	 * <li>if no {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} was added by the previous step (either
	 * because P has no folder in the type definitions repository or that folder does not contain a package.json
	 * fragment), then a {@link N4JSGlobals#PACKAGE_N4JS_JSON marker file} will be added to the package to denote that
	 * this package was part of the given 'namesOfPackagesToAdapt' (for later use by other code).
	 * </ul>
	 *
	 * Returned set of N4JS project folders will not include those installed by the npm but without matching names in
	 * provided set of expected packages. Those packages are treated as transitive dependencies and are not returned to
	 * the caller.
	 *
	 * @param namesOfPackagesToAdapt
	 *            names of the expected packages
	 * @return pair of overall adaptation status and folders of successfully adapted npm packages
	 */
	public Pair<IStatus, Collection<File>> adaptPackages(Collection<String> namesOfPackagesToAdapt) {
		final MultiStatus status = statusHelper.createMultiStatus("Status of adapting npm packages");
		final File nodeModulesFolder = new File(installLocationProvider.getTargetPlatformNodeModulesLocation());
		final Collection<String> names = newHashSet(namesOfPackagesToAdapt);
		final File[] packageRoots = nodeModulesFolder.listFiles(packageName -> names.contains(packageName.getName()));
		final File n4jsdsFolder = getNpmsTypeDefinitionsFolder();

		for (File packageRoot : packageRoots) {
			try {
				// add type definitions
				boolean havePackageJsonFragment = false;
				if (n4jsdsFolder != null) {
					addTypeDefinitions(packageRoot, n4jsdsFolder);
					havePackageJsonFragment = new File(packageRoot, N4JSGlobals.PACKAGE_FRAGMENT_JSON).isFile();
				}
				// create marker file to denote that his package was among "namesOfPackagesToAdapt"
				// (unless we already have a fragment, which can serve that purpose too;
				// compare with: ExternalProjectLocationsProvider#isExternalProjectDirectory(File))
				if (!havePackageJsonFragment) {
					File markerFile = new File(packageRoot, N4JSGlobals.PACKAGE_N4JS_JSON);
					Files.write(markerFile.toPath(), Collections.singletonList("{}")); // will overwrite existing file
				}
			} catch (final Exception e) {
				status.merge(
						statusHelper.createError("Unexpected error occurred while adapting '" + packageRoot.getName()
								+ "' npm package into N4JS format.", e));
			}
		}

		return pair(status, Arrays.asList(packageRoots));
	}

	/**
	 * Reads, parses and returns with the content of the {@code package.json} file as a POJO for the given npm package
	 * root location.
	 *
	 * @param packageRoot
	 *            the root location of the npm package.
	 *
	 * @return the POJO instance that represents the read, parsed content of the {@code package.json} file.
	 *
	 * @throws IOException
	 *             if {@code package.json} file does not exists, hence the content cannot be read.
	 */
	PackageJson getPackageJson(File packageRoot) throws IOException {

		final File packageJsonResource = new File(packageRoot, PACKAGE_JSON);
		if (!packageJsonResource.exists() || !packageJsonResource.isFile()) {
			throw new IOException("Cannot read package.json content for package '" + packageJsonResource.getName()
					+ "' at '" + packageJsonResource + "'.");
		}
		return PackageJson.readValue(packageJsonResource.toURI());
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

		File repositoryLocation = new File(installLocationProvider.getTargetPlatformLocalGitRepositoryLocation());

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
	IStatus addTypeDefinitions(File packageRoot, File definitionsFolder) throws IOException {

		String packageName = packageRoot.getName();
		File packageN4JSDsRoot = new File(definitionsFolder, packageName);
		if (!(packageN4JSDsRoot.exists() && packageN4JSDsRoot.isDirectory())) {
			LOGGER.info("No type definitions found for '" + packageRoot + "' npm package at '" + packageN4JSDsRoot + "'"
					+ (!packageN4JSDsRoot.isDirectory() ? " (which is not a directory)" : "") + ".");
			return statusHelper.OK();
		}

		PackageJson packageJson = getPackageJson(packageRoot);

		String packageJsonVersion = packageJson.version;
		Version packageVersion = Version.createFromString(packageJsonVersion);
		String[] list = packageN4JSDsRoot.list();
		Set<Version> availableTypeDefinitionsVersions = new HashSet<>();
		for (int i = 0; i < list.length; i++) {
			String version = list[i];
			Version availableTypeDefinitionsVersion = Version.createFromString(version);
			if (!Version.MISSING.equals(availableTypeDefinitionsVersion)) {
				availableTypeDefinitionsVersions.add(availableTypeDefinitionsVersion);
			}
		}

		Version closestMatchingVersion = Version.findClosestMatching(availableTypeDefinitionsVersions, packageVersion);
		if (Version.MISSING.equals(closestMatchingVersion)) {
			String details = "";
			if (availableTypeDefinitionsVersions.isEmpty()) {
				details = " Cannot find any type definitions for  '" + packageName + "'.";
			} else if (1 == availableTypeDefinitionsVersions.size()) {
				final Version head = availableTypeDefinitionsVersions.iterator().next();
				details = " Type definitions are available only in version : " + head + ".";
			} else {
				final String versions = Iterables.toString(availableTypeDefinitionsVersions);
				details = " Type definitions are available only in versions : " + versions + ".";
			}
			logger.logInfo("Type definitions for '" + packageName + "' npm package in version " + packageVersion
					+ " are not available." + details);
			return statusHelper.OK();
		}

		File packageVersionedN4JSDProjectRoot = new File(packageN4JSDsRoot, closestMatchingVersion.toString());
		if (!(packageVersionedN4JSDProjectRoot.exists() && packageVersionedN4JSDProjectRoot.isDirectory())) {
			final String message = "Cannot find type definitions folder for '" + packageName
					+ "' npm package for version '" + closestMatchingVersion + "'.";
			LOGGER.error(message);
			return statusHelper.createError(message);
		}

		Path sourcePath = packageVersionedN4JSDProjectRoot.toPath();
		Path targetPath = packageRoot.toPath();

		// copy all n4jsd files
		try {
			FileCopier.copy(sourcePath, targetPath, COPY_N4JSD_PREDICATE);
		} catch (IOException e) {
			final String message = "Error while trying to update type definitions content for '" + packageName
					+ "' npm package.";
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}

		// copy package.json fragment
		try {
			Files.copy(
					sourcePath.resolve(N4JSGlobals.PACKAGE_FRAGMENT_JSON),
					targetPath.resolve(N4JSGlobals.PACKAGE_FRAGMENT_JSON),
					StandardCopyOption.REPLACE_EXISTING);
			FileCopier.copy(sourcePath, targetPath, COPY_N4JSD_PREDICATE);
		} catch (IOException e) {
			final String message = "Error while trying to copy the package.json fragment '"
					+ N4JSGlobals.PACKAGE_FRAGMENT_JSON + "' for '" + packageName + "' npm package.";
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}

		return statusHelper.OK();
	}
}
