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

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;

/**
 * Provides information about the target platform install location and about the location of the actual target platform
 * file.
 * <p>
 * Keep folder computations in sync with {@link ExternalLibrariesActivator}!
 */
public interface TargetPlatformInstallLocationProvider {

	/**
	 * Shared logger.
	 */
	Logger LOGGER = Logger.getLogger(TargetPlatformInstallLocationProvider.class);

	/**
	 * Unique name of the {@code node_modules} folder that will be used to download npm packages.
	 */
	String NODE_MODULES_FOLDER = ExternalLibrariesActivator.NPM_CATEGORY;

	/**
	 * Unique name of the {@code node_modules} folder that will be used to download npm packages.
	 */
	String TYPE_DEFINITIONS_FOLDER = ExternalLibrariesActivator.TYPE_DEFINITIONS_CATEGORY;

	/**
	 * Returns with an {@link File} pointing to the target platform install location, called '.n4npm' folder.
	 *
	 * @return the {@link File} pointing to the target platform install location.
	 */
	File getTargetPlatformInstallFolder();

	/**
	 * Returns with an URI pointing to the target platform install location.
	 *
	 * @return the URI pointing to the target platform install location.
	 */
	default URI getTargetPlatformInstallURI() {
		File tpFolder = getTargetPlatformInstallFolder();
		return tpFolder == null ? null : tpFolder.toURI();
	}

	/**
	 * Returns with an URI pointing to the target platform file that has to be used to install any third party
	 * dependencies to the {@link #getTargetPlatformInstallURI() target platform install location}.
	 *
	 * @return the URI pointing to the actual target platform file.
	 */
	URI getTargetPlatformFileLocation();

	/**
	 * Returns with the unique name of the Git repository for storing the type definition files for the npm packages.
	 *
	 * @return the unique name of the Git repository.
	 */
	String getGitRepositoryName();

	/** @return the URI pointing to the {@code node_modules} folder which is used for installing npm packages. */
	default URI getNodeModulesURI() {
		return getURIInTargetPlatformLocation(NODE_MODULES_FOLDER);
	}

	/** @return the URI pointing to the {@code node_modules} folder which is used for installing npm packages. */
	default File getNodeModulesFolder() {
		return getFolderInTargetPlatformLocation(NODE_MODULES_FOLDER);
	}

	/** @return the URI pointing to the {@code type_definition} folder. */
	default URI getTypeDefinitionsURI() {
		return getURIInTargetPlatformLocation(TYPE_DEFINITIONS_FOLDER);
	}

	/** @return the URI pointing to the {@code type_definition} folder. */
	default File getTypeDefinitionsFolder() {
		return getFolderInTargetPlatformLocation(TYPE_DEFINITIONS_FOLDER);
	}

	/** @return the {@link URI} pointing to the given folder inside the target platform */
	default URI getURIInTargetPlatformLocation(String folderName) {
		return getFolderInTargetPlatformLocation(folderName).toURI();
	}

	/** @return the {@link File} pointing to the given folder inside the target platform */
	default File getFolderInTargetPlatformLocation(String folderName) {
		synchronized (folderName) {
			if (null == getTargetPlatformInstallFolder()) {
				final String message = "Target platform install location was not specified.";
				final NullPointerException exception = new NullPointerException(message);
				LOGGER.error(message, exception);
				exception.printStackTrace(); // This if for the HLC as it swallows the actual stack trace.
				throw exception;
			}

			final File installLocation = getTargetPlatformInstallFolder();
			checkState(installLocation.isDirectory(),
					"Cannot locate target platform install location: " + installLocation);

			final File folder = new File(installLocation, folderName);
			if (!folder.exists()) {
				checkState(folder.mkdir(), "Error while creating " + folderName + " folder for target platform.");
			}

			checkState(folder.isDirectory(), "Cannot locate " + folderName + " folder in target platform location.");

			try {
				return folder.getCanonicalFile();
			} catch (final IOException e) {
				throw new RuntimeException("Error while getting the canonical form of the " + folderName + " location.",
						e);
			}
		}
	}

	/**
	 * Returns with the URI pointing to the local git repository root location for storing and retrieving N4JS
	 * definition files for the npm installation process.
	 *
	 * @return the URI pointing to the local git repository location for the N4JSD files in the file system.
	 */
	default URI getTargetPlatformLocalGitRepositoryLocation() {
		if (null == getTargetPlatformInstallFolder()) {
			final String message = "Target platform install location was not specified.";
			final NullPointerException exception = new NullPointerException(message);
			LOGGER.error(message, exception);
			exception.printStackTrace(); // This if for the HLC as it swallows the actual stack trace.
			throw exception;
		}
		final File installLocation = getTargetPlatformInstallFolder();
		checkState(installLocation.isDirectory(), "Cannot locate target platform install location: " + installLocation);
		// The local git repository should be a sibling folder of the install location.
		File parentFile = installLocation.getParentFile();
		if (null == parentFile || !parentFile.exists() || !parentFile.isDirectory()) {
			LOGGER.warn("Cannot get parent folder of the target platform install location: " + parentFile + ".");
			LOGGER.warn("Falling back to install location: " + installLocation + ".");
			parentFile = installLocation;
		}
		final File gitRoot = new File(parentFile, getGitRepositoryName());
		if (!gitRoot.exists()) {
			checkState(gitRoot.mkdir(), "Error while creating local git repository folder for target platform.");
		}
		checkState(gitRoot.isDirectory(),
				"Cannot locate local git repository folder in target platform install location.");
		return gitRoot.toURI();
	}

	/**
	 * Recreates the folders node_modules and type_definitions.
	 *
	 * @return true if folder were recreated and exist
	 */
	default boolean repairNpmFolderState() {
		boolean success = true;
		File installLocation = getTargetPlatformInstallFolder();
		if (!installLocation.isDirectory()) {
			success &= installLocation.mkdir();
		}
		if (success) {
			File npmFile = getNodeModulesFolder();
			File tdFile = getTypeDefinitionsFolder();
			success &= npmFile != null && npmFile.isDirectory();
			success &= tdFile != null && tdFile.isDirectory();
		}
		return success;
	}
}
