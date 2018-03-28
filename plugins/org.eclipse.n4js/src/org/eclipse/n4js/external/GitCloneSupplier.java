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
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.getOrCreateNestedFolder;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.log;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider.TypeDefinitionGitLocation;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Clones the remote Git repository with the N4JS definition files.
 */
@Singleton
public class GitCloneSupplier implements Supplier<File> {

	private static final Logger LOGGER = Logger.getLogger(GitCloneSupplier.class);

	private boolean successfullyCloned = false;

	private TypeDefinitionGitLocation currentGitLocation = null;

	@Inject
	private TypeDefinitionGitLocationProvider gitLocationProvider;

	@Inject
	private StatusHelper statusHelper;

	/**
	 * Supplies the local git repository root folder location. Ensures that the remote git repository is cloned in case
	 * of its absence. If the local git repository already exists, then it performs a hard reset on the {@code HEAD} of
	 * the {@code master} branch.
	 *
	 * @return returns with the file pointing to the local Git repository clone.
	 */
	@Override
	public synchronized File get() {
		final TypeDefinitionGitLocation gitLocation = gitLocationProvider.getGitLocation();
		checkNotNull(gitLocation, "Git location for type definitions was null.");
		if (!gitLocation.equals(currentGitLocation)) {
			currentGitLocation = gitLocation;
			successfullyCloned = false;
		}
		final String repositoryName = currentGitLocation.getRepositoryName();
		final String remoteURL = currentGitLocation.getRepositoryRemoteURL();
		final String remoteBranch = currentGitLocation.getRemoteBranch();
		if (!successfullyCloned) {
			final File gitRoot = getOrCreateNestedFolder(repositoryName);
			try {
				GitUtils.hardReset(remoteURL, gitRoot.toPath(), remoteBranch, true);
				GitUtils.pull(gitRoot.toPath());
				LOGGER.info("Local N4JS type definition files have been successfully prepared for npm support.");
				successfullyCloned = true;
				return gitRoot;
			} catch (final Exception e) {
				final String message = "Error occurred while preparing local git repository for N4JS type definition files.";
				LOGGER.error(message, e);
				log(statusHelper.createError(message, e));
				try {
					FileDeleter.delete(gitRoot.toPath()); // Clean up folder.
				} catch (final IOException e2) {
					final String cleanUpErrorMessage = "Error while cleaning up local git clone after failed clone.";
					LOGGER.error(cleanUpErrorMessage, e2);
					log(statusHelper.createError(cleanUpErrorMessage, e2));
				}
				successfullyCloned = false;
				return gitRoot;
			} finally {
				getOrCreateNestedFolder(repositoryName); // Make sure root is there even if clone failed.
			}
		}
		return getOrCreateNestedFolder(repositoryName);
	}

	/**
	 * Tries to clone type definitions again. If repository exists on the disk, only hard reset is performed.
	 *
	 * @return true if refreshed type definitions reside in the same location as before
	 */
	public synchronized boolean repairTypeDefinitions() {
		File oldRepo = get();
		this.successfullyCloned = false;
		File newRepo = get();
		return newRepo.getAbsolutePath().equals(oldRepo.getAbsolutePath());
	}

	/** @return true iff the last {@link #get()} or {@link #repairTypeDefinitions()} call was successful. */
	public synchronized boolean isSuccessfullyCloned() {
		return successfullyCloned;
	}

}
