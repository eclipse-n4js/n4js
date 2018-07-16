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
	 * Returns with an URI pointing to the target platform install location.
	 *
	 * @return the URI pointing to the target platform install location.
	 */
	URI getTargetPlatformInstallLocation();

	/**
	 * Returns with an URI pointing to the target platform file that has to be used to install any third party
	 * dependencies to the {@link #getTargetPlatformInstallLocation() target platform install location}.
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

	/**
	 * Returns with an URI pointing to the {@code node_modules} folder inside the target platform install location.
	 *
	 * @return the URI pointing the {@code node_modules} folder which is used for installing npm packages.
	 */
	default URI getTargetPlatformNodeModulesLocation() {
		if (null == getTargetPlatformInstallLocation()) {
			final String message = "Target platform install location was not specified.";
			final NullPointerException exception = new NullPointerException(message);
			LOGGER.error(message, exception);
			exception.printStackTrace(); // This if for the HLC as it swallows the actual stack trace.
			throw exception;
		}
		final File installLocation = new File(getTargetPlatformInstallLocation());
		checkState(installLocation.isDirectory(), "Cannot locate target platform install location: " + installLocation);
		final File nodeModules = new File(installLocation, NODE_MODULES_FOLDER);
		if (!nodeModules.exists()) {
			checkState(nodeModules.mkdir(), "Error while creating node_modules folder for target platform.");
		}
		checkState(nodeModules.isDirectory(), "Cannot locate node_modules folder in target platform install location.");
		try {
			return nodeModules.getCanonicalFile().toURI();
		} catch (final IOException e) {
			throw new RuntimeException("Error while getting the canonical form of the node_modules location.", e);
		}
	}

	/**
	 * Returns with the URI pointing to the local git repository root location for storing and retrieving N4JS
	 * definition files for the npm installation process.
	 *
	 * @return the URI pointing to the local git repository location for the N4JSD files in the file system.
	 */
	default URI getTargetPlatformLocalGitRepositoryLocation() {
		if (null == getTargetPlatformInstallLocation()) {
			final String message = "Target platform install location was not specified.";
			final NullPointerException exception = new NullPointerException(message);
			LOGGER.error(message, exception);
			exception.printStackTrace(); // This if for the HLC as it swallows the actual stack trace.
			throw exception;
		}
		final File installLocation = new File(getTargetPlatformInstallLocation());
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
}
