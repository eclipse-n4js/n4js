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
package org.eclipse.n4js.utils.git;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.toArray;
import static java.nio.file.Files.createDirectories;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.jgit.api.Git.cloneRepository;
import static org.eclipse.jgit.api.Git.open;
import static org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE;
import static org.eclipse.jgit.api.ResetCommand.ResetType.HARD;
import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.n4js.utils.collections.Arrays2.isEmpty;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.SubmoduleInitCommand;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;

/**
 * Contains a bunch of utility methods for performing Git operations.
 */
public abstract class GitUtils {

	/**
	 * Shared logger instance.
	 */
	private static final Logger LOGGER = getLogger(GitUtils.class);

	private static final String ORIGIN = DEFAULT_REMOTE_NAME;

	/** Callback that configures the SSH factory for the transport if possible, otherwise does nothing at all. */
	private static final TransportConfigCallback TRANSPORT_CALLBACK = transport -> {
		if (transport instanceof SshTransport) {
			((SshTransport) transport).setSshSessionFactory(new SshSessionFactory());
		}
	};

	/** @return true iff a connection can be established to the given URL */
	public static boolean netIsAvailable(final String remoteUrl) {
		try {
			final URL url = new URL(remoteUrl);
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Hard resets the {@code HEAD} of the reference in the locally cloned Git repository. If the repository does not
	 * exists yet at the given local clone path, then it also clones it, iff the {@code cloneIfMissing} argument is
	 * {@code true}.
	 *
	 * @param remoteUri
	 *            the URI of the remote repository. Could be omitted if the {@code cloneIfMissing} is {@code false}.
	 * @param localClonePath
	 *            the local path of the cloned repository.
	 * @param branch
	 *            the name of the branch to reset the {@code HEAD} pointer.
	 * @param cloneIfMissing
	 *            {@code true} if the repository has to be cloned in case if its absence.
	 * @param clean
	 *            if {@code true}, a Git clean will be executed after the reset, similar to running the command
	 *            {@code "git clean -dxff"}. Such an extensive clean will set the repository back to the state right
	 *            after freshly cloning it.
	 */
	public static void hardReset(final String remoteUri, final Path localClonePath, final String branch,
			final boolean cloneIfMissing, final boolean clean) {

		LOGGER.info("Performing hard reset... [Local repository: " + localClonePath + ", remote URI: " + remoteUri
				+ ", branch: " + branch + "]");

		checkNotNull(localClonePath, "localClonePath");
		if (cloneIfMissing) {
			checkNotNull(remoteUri, "remoteUri");
			clone(remoteUri, localClonePath, branch);
		}

		try (final Git git = open(localClonePath.toFile())) {
			final String currentBranch = git.getRepository().getBranch();
			if (!currentBranch.equals(branch)) {
				LOGGER.info("Current branch is: '" + currentBranch + "'.");
				LOGGER.info("Switching to desired '" + branch + "' branch...");
				git.pull().setProgressMonitor(createMonitor()).call();

				final boolean createLocalBranch = !hasLocalBranch(git, branch);
				LOGGER.info("Creating local branch '" + branch + "'? --> " + (createLocalBranch ? "yes" : "no"));
				git.checkout().setCreateBranch(createLocalBranch).setName(branch)
						.setStartPoint(R_REMOTES + "origin/" + branch).call();

				checkState(git.getRepository().getBranch().equals(branch),
						"Error when checking out '" + branch + "' branch.");
				LOGGER.info("Switched to '" + branch + "' branch.");
				git.pull().setProgressMonitor(createMonitor()).call();
			}

			LOGGER.info("Hard resetting local repository HEAD of the '" + branch + "' in '" + remoteUri + "'...");
			LOGGER.info("Local repository location: " + localClonePath + ".");

			final ResetCommand resetCommand = git.reset().setMode(HARD).setRef(HEAD);
			final Ref ref = resetCommand.call();
			LOGGER.info("Repository content has been successfully reset to '" + ref + "'.");

			if (clean) {
				LOGGER.info("Cleaning repository ...");
				final Collection<String> deletedFiles = git.clean()
						.setCleanDirectories(true).setIgnore(false).setForce(true).call();
				LOGGER.info("Cleaned up " + deletedFiles.size() + " files:\n" + Joiner.on(",\n").join(deletedFiles));
			}
		} catch (final RepositoryNotFoundException e) {
			if (cloneIfMissing) {
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			} else {
				final String message = "Git repository does not exist at " + localClonePath
						+ ". Git repository should be cloned manually.";
				throw new RuntimeException(message, e);
			}
		} catch (final Exception e) {
			LOGGER.error("Error when trying to hard reset to HEAD on '" + branch + "' branch in " + localClonePath
					+ " repository.");
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sugar for {@link #hardReset(String, Path, String, boolean, boolean)} with multiple remote git URIs and local
	 * paths.
	 *
	 * @param remoteUris
	 *            the URI of the remote repository. Could be omitted if the {@code cloneIfMissing} is {@code false}.
	 * @param localClonePaths
	 *            the local path of the cloned repository.
	 * @param branch
	 *            the name of the branch to reset the {@code HEAD} pointer.
	 * @param cloneIfMissing
	 *            {@code true} if the repository has to be cloned in case if its absence.
	 * @param clean
	 *            if {@code true}, a Git clean will be executed after the reset.
	 */
	public static void hardReset(final Iterable<String> remoteUris, final Iterable<Path> localClonePaths,
			final String branch, final boolean cloneIfMissing, final boolean clean) {

		checkNotNull(remoteUris, "remoteUris");
		checkNotNull(localClonePaths, "localClonePaths");
		checkArgument(size(remoteUris) == size(localClonePaths), "Remote URI - local clone path mismatch.");

		final Path[] paths = toArray(localClonePaths, Path.class);
		int i = 0;
		final CountDownLatch latch = new CountDownLatch(Iterables.size(remoteUris));
		final AtomicReference<Exception> resetExc = new AtomicReference<>();
		final Object mutex = new Object();
		for (final String remoteUri : remoteUris) {
			final int pathIndex = i++;
			new Thread(() -> {
				try {
					hardReset(remoteUri, paths[pathIndex], branch, cloneIfMissing, clean);
				} catch (final Exception e) {
					if (null == resetExc.get()) {
						synchronized (mutex) {
							if (null == resetExc.get()) {
								resetExc.set(e);
							}
						}
					}
				} finally {
					latch.countDown();
				}
			}, "Thread-Git-Hard-Reset-" + remoteUri).start();
		}
		try {
			latch.await(5L, TimeUnit.MINUTES);
			if (null != resetExc.get()) {
				Exceptions.sneakyThrow(resetExc.get());
			}
		} catch (final InterruptedException e) {
			throw new RuntimeException(
					"Timeouted while checking out remote Git repositories: " + Iterables.toString(remoteUris), e);
		}
	}

	/**
	 * Performs a git {@code pull} in a local git repository given as repository root path argument.
	 */
	public static void pull(final Path localClonePath) {
		pull(localClonePath, null);
	}

	/**
	 * Sugar for {@link #pull(Path)} with progress monitor support. Performs a git {@code pull} in a local git
	 * repository. If the {@code monitor} argument is optional, hence it can be {@code null}.
	 */
	public static void pull(final Path localClonePath, final IProgressMonitor monitor) {

		if (!isValidLocalClonePath(localClonePath)) {
			return;
		}

		@SuppressWarnings("restriction")
		final ProgressMonitor gitMonitor = null == monitor ? createMonitor()
				: new org.eclipse.egit.core.EclipseGitProgressTransformer(monitor);

		try (final Git git = open(localClonePath.toFile())) {
			git.pull().setProgressMonitor(gitMonitor).setTransportConfigCallback(TRANSPORT_CALLBACK).call();

		} catch (final GitAPIException e) {
			LOGGER.error("Error when trying to pull on repository  '" + localClonePath + ".");
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);

		} catch (final RepositoryNotFoundException e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);

		} catch (final IOException e) {
			LOGGER.warn("Git repository does not exists at " + localClonePath + ". Aborting git pull.");
			LOGGER.warn("Perform git clone first, then try to pull from remote.");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.error("Error when trying to open repository  '" + localClonePath + ".");
			}
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Obtain information about all submodules of the Git repository at the given path. Returns an empty map in case the
	 * repository does not include submodules. Throws exceptions in case of error.
	 */
	public static Map<String, SubmoduleStatus> getSubmodules(final Path localClonePath) {

		if (!isValidLocalClonePath(localClonePath)) {
			throw new IllegalArgumentException("invalid localClonePath: " + localClonePath);
		}

		try (final Git git = open(localClonePath.toFile())) {
			return git.submoduleStatus().call();
		} catch (Exception e) {
			LOGGER.error(e.getClass().getSimpleName()
					+ " while trying to obtain status of all submodules"
					+ " of repository '" + localClonePath
					+ "':" + e.getLocalizedMessage());
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize the submodules with the given repository-relative <code>submodulePaths</code> inside the Git
	 * repository at the given clone path. Throws exceptions in case of error.
	 *
	 * @param submodulePaths
	 *            repository-relative paths of the submodules to initialized; if empty, all submodules will be
	 *            initialized.
	 */
	public static void initSubmodules(final Path localClonePath, final Iterable<String> submodulePaths) {

		if (!isValidLocalClonePath(localClonePath)) {
			throw new IllegalArgumentException("invalid localClonePath: " + localClonePath);
		}

		try (final Git git = open(localClonePath.toFile())) {
			final SubmoduleInitCommand cmd = git.submoduleInit();
			for (String submodulePath : submodulePaths) {
				cmd.addPath(submodulePath);
			}
			cmd.call();
		} catch (Exception e) {
			LOGGER.error(e.getClass().getSimpleName()
					+ " while trying to initialize submodules " + Iterables.toString(submodulePaths)
					+ " of repository '" + localClonePath
					+ "':" + e.getLocalizedMessage());
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Update the submodules with the given repository-relative <code>submodulePaths</code> inside the Git repository at
	 * the given clone path. Throws exceptions in case of error.
	 *
	 * @param submodulePaths
	 *            repository-relative paths of the submodules to update; if empty, all submodules will be updated.
	 */
	public static void updateSubmodules(final Path localClonePath, final Iterable<String> submodulePaths,
			final IProgressMonitor monitor) {

		if (!isValidLocalClonePath(localClonePath)) {
			throw new IllegalArgumentException("invalid localClonePath: " + localClonePath);
		}

		@SuppressWarnings("restriction")
		final ProgressMonitor gitMonitor = null == monitor ? createMonitor()
				: new org.eclipse.egit.core.EclipseGitProgressTransformer(monitor);

		try (final Git git = open(localClonePath.toFile())) {
			final SubmoduleUpdateCommand cmd = git.submoduleUpdate();
			for (String submodulePath : submodulePaths) {
				cmd.addPath(submodulePath);
			}
			cmd.setProgressMonitor(gitMonitor);
			cmd.setTransportConfigCallback(TRANSPORT_CALLBACK);
			cmd.call();
		} catch (Exception e) {
			LOGGER.error(e.getClass().getSimpleName()
					+ " while trying to update submodules " + Iterables.toString(submodulePaths)
					+ " of repository '" + localClonePath
					+ "':" + e.getLocalizedMessage());
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	private static boolean isValidLocalClonePath(final Path localClonePath) {
		if (null == localClonePath) {
			LOGGER.warn("Local clone path should be specified for the git clone operation.");
			return false;
		}

		final File localCloneRoot = localClonePath.toFile();
		if (!localCloneRoot.exists()) {
			LOGGER.warn("Local git repository clone root does not exist: " + localCloneRoot + ".");
			return false;
		}

		if (!localCloneRoot.isDirectory()) {
			LOGGER.warn("Expecting a directory as the local git repository clone. Was a file: " + localCloneRoot + ".");
			return false;
		}

		return true;
	}

	/**
	 * Returns with the name of the master branch.
	 *
	 * @return the name of the master branch.
	 */
	public static String getMasterBranch() {
		return MASTER;
	}

	/**
	 * Clones a branch of a remote Git repository to the local file system.
	 *
	 * @param remoteUri
	 *            the remote Git repository URI as String.
	 * @param localClonePath
	 *            the local file system path.
	 * @param branch
	 *            the name of the branch to be cloned.
	 */
	private static void clone(final String remoteUri, final Path localClonePath, final String branch) {

		checkNotNull(remoteUri, "remoteUri");
		checkNotNull(localClonePath, "clonePath");
		checkNotNull(branch, "branch");

		final File destinationFolder = localClonePath.toFile();
		if (!destinationFolder.exists()) {
			try {
				createDirectories(localClonePath);
			} catch (final IOException e) {
				final String message = "Error while creating directory for local repository under " + localClonePath
						+ ".";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			}
			LOGGER.info("Creating folder for repository at '" + localClonePath + "'.");
		}
		checkState(destinationFolder.exists(),
				"Repository folder does not exist folder does not exist: " + destinationFolder.getAbsolutePath());

		final File[] existingFiles = destinationFolder.listFiles();
		if (!isEmpty(existingFiles)) {
			try (final Git git = open(localClonePath.toFile())) {
				LOGGER.info(
						"Repository already exists. Aborting clone phase. Files in " + destinationFolder + " are: "
								+ Joiner.on(',').join(existingFiles));
				final List<URIish> originUris = getOriginUris(git);
				if (!hasRemote(originUris, remoteUri)) {
					LOGGER.info(
							"Desired remote URI differs from the current one. Desired: '" + remoteUri
									+ "' origin URIs: '" + Joiner.on(',').join(originUris) + "'.");
					LOGGER.info("Cleaning up current git clone and running clone phase from scratch.");
					deleteRecursively(destinationFolder);
					clone(remoteUri, localClonePath, branch);
				} else {
					final String currentBranch = git.getRepository().getBranch();
					if (!currentBranch.equals(branch)) {
						LOGGER.info("Desired branch differs from the current one. Desired: '" + branch + "' current: '"
								+ currentBranch + "'.");
						git.pull().setProgressMonitor(createMonitor()).call();
						// check if the remote desired branch exists or not.
						final Ref remoteBranchRef = from(git.branchList().setListMode(REMOTE).call())
								.firstMatch(ref -> ref.getName().equals(R_REMOTES + ORIGIN + "/" + branch)).orNull();
						// since repository might be cloned via --depth 1 (aka shallow clone) we cannot just switch to
						// any remote branch those ones do not exist. and we cannot run 'pull --unshallow' either.
						// we have to delete the repository content and run a full clone from scratch.
						if (null == remoteBranchRef) {
							LOGGER.info("Cleaning up current git clone and running clone phase from scratch.");
							deleteRecursively(destinationFolder);
							clone(remoteUri, localClonePath, currentBranch);
						} else {
							git.pull().setProgressMonitor(createMonitor()).call();
							LOGGER.info("Pulled from upstream.");
						}
					}
				}
				return;
			} catch (final Exception e) {
				final String msg = "Error when performing git pull in " + localClonePath + " from " + remoteUri + ".";
				LOGGER.error(msg, e);
				throw new RuntimeException(
						"Error when performing git pull in " + localClonePath + " from " + remoteUri + ".", e);
			}
		}

		LOGGER.info("Cloning repository from '" + remoteUri + "'...");

		final CloneCommand cloneCommand = cloneRepository()
				.setURI(remoteUri)
				.setDirectory(destinationFolder)
				.setBranch(branch)
				.setProgressMonitor(createMonitor())
				.setTransportConfigCallback(TRANSPORT_CALLBACK);

		try (final Git git = cloneCommand.call()) {
			LOGGER.info(
					"Repository content has been successfully cloned to '" + git.getRepository().getDirectory() + "'.");
		} catch (final GitAPIException e) {
			final String message = "Error while cloning repository.";
			LOGGER.error(message, e);
			LOGGER.info("Trying to clean up local repository content: " + destinationFolder + ".");
			deleteRecursively(destinationFolder);
			LOGGER.info("Inconsistent checkout directory was successfully cleaned up.");
			throw new RuntimeException(message, e);
		}
	}

	private static boolean hasLocalBranch(Git git, final String branchName) throws GitAPIException {
		final Iterable<Ref> localBranchRefs = git.branchList().call();
		return from(localBranchRefs).anyMatch(ref -> ref.getName().endsWith(branchName));
	}

	/**
	 * Checks whether the given URIs contain the given URI.
	 *
	 * @param uris
	 *            the URIs
	 * @param uriStr
	 *            the URI to check
	 * @return <code>true</code> if the given repository's origin has the given URI and <code>false</code> otherwise
	 * @throws URISyntaxException
	 *             if the given URI is malformed
	 */
	private static boolean hasRemote(Iterable<URIish> uris, final String uriStr) throws URISyntaxException {
		final URIish uri = new URIish(uriStr);
		return from(uris).anyMatch((originUri) -> {
			return equals(originUri, uri);
		});
	}

	/**
	 * Compare the two given git remote URIs. This method is a reimplementation of {@link URIish#equals(Object)} with
	 * one difference. The scheme of the URIs is only considered if both URIs have a non-null and non-empty scheme part.
	 *
	 * @param lhs
	 *            the left hand side
	 * @param rhs
	 *            the right hand side
	 * @return <code>true</code> if the two URIs are to be considered equal and <code>false</code> otherwise
	 */
	private static boolean equals(URIish lhs, URIish rhs) {
		// We only consider the scheme if both URIs have one
		if (!StringUtils.isEmptyOrNull(lhs.getScheme()) && !StringUtils.isEmptyOrNull(rhs.getScheme())) {
			if (!Objects.equals(lhs.getScheme(), rhs.getScheme()))
				return false;
		}
		if (!equals(lhs.getUser(), rhs.getUser()))
			return false;
		if (!equals(lhs.getPass(), rhs.getPass()))
			return false;
		if (!equals(lhs.getHost(), rhs.getHost()))
			return false;
		if (lhs.getPort() != rhs.getPort())
			return false;
		if (!pathEquals(lhs.getPath(), rhs.getPath()))
			return false;
		return true;
	}

	/**
	 * A helper method for comparing strings. If both of the given strings are empty or <code>null</code>, they are
	 * considered equal.
	 *
	 * @param lhs
	 *            the left hand side
	 * @param rhs
	 *            the right hand side
	 * @return <code>true</code> if the two strings are to be considered equal and <code>false</code> otherwise
	 */
	private static boolean equals(String lhs, String rhs) {
		if (StringUtils.isEmptyOrNull(lhs) && StringUtils.isEmptyOrNull(rhs))
			return true;
		return Objects.equals(lhs, rhs);
	}

	private static boolean pathEquals(String lhs, String rhs) {
		if (StringUtils.isEmptyOrNull(lhs) && StringUtils.isEmptyOrNull(rhs))
			return true;

		// Skip leading slashes in both paths.
		int lhsIndex = 0;
		while (lhsIndex < lhs.length() && lhs.charAt(lhsIndex) == '/')
			++lhsIndex;

		int rhsIndex = 0;
		while (rhsIndex < rhs.length() && rhs.charAt(rhsIndex) == '/')
			++rhsIndex;

		String lhsRel = lhs.substring(lhsIndex);
		String rhsRel = rhs.substring(rhsIndex);
		return lhsRel.equals(rhsRel);
	}

	/**
	 * Returns all URIs of the given repository's origin remote.
	 *
	 * @param git
	 *            the git repository
	 * @return the list of URIs or an empty list if the given repository has no origin or if that origin has no URIs
	 * @throws GitAPIException
	 *             if an error occurs while accessing the given repository
	 */
	private static List<URIish> getOriginUris(Git git) throws GitAPIException {
		Optional<RemoteConfig> origin = getOriginRemote(git);
		if (origin.isPresent())
			return origin.get().getURIs();
		return Collections.emptyList();
	}

	/**
	 * Returns the origin of the given repository.
	 *
	 * @param git
	 *            the git repository
	 * @return the origin
	 * @throws GitAPIException
	 *             if an error occurs while accessing the given repository
	 */
	private static Optional<RemoteConfig> getOriginRemote(Git git) throws GitAPIException {
		List<RemoteConfig> remotes = git.remoteList().call();
		if (remotes.isEmpty())
			return Optional.absent();

		final String origin = getDefaultRemote();
		return from(remotes).firstMatch((remote) -> {
			return remote.getName().equals(origin);
		});
	}

	/**
	 * Returns the name of the default remote.
	 *
	 * @return the name of the default remote
	 */
	public static String getDefaultRemote() {
		return DEFAULT_REMOTE_NAME;
	}

	private static TextProgressMonitor createMonitor() {
		return new TextProgressMonitor(new OutputStreamWriter(System.out));
	}

	/**
	 * Recursively cleans up the resource given as a file. If the file represents a directory, this method will be
	 * called with each files contained by the given argument.
	 *
	 * @param file
	 *            the resource to delete.
	 */
	private static void deleteRecursively(final File file) {
		try {
			FileDeleter.delete(file.toPath());
		} catch (IOException e) {
			throw new RuntimeException("Error while recursively cleaning up content of " + file + ".");
		}
	}

}
