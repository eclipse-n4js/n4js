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
package org.eclipse.n4js.binaries.nodejs;

import static org.eclipse.n4js.utils.OSInfo.isWindows;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.inject.Singleton;

/**
 * Factory for the processes interacting with node/yarn, taking care of the OS specific data. By default, this class
 * will use npm for installation and uninstallation of npm packages, unless method {@link #isYarnUsed(Path)} returns
 * <code>true</code>.
 */
@Singleton
public class NodeYarnProcessBuilder {

	private enum Operation {
		INSTALL, UNINSTALL
	}

	private static final String[] WIN_SHELL_COMAMNDS = { "cmd", "/c" };
	private static final String[] NIX_SHELL_COMAMNDS = { "sh", "-c" };

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private Provider<YarnBinary> yarnBinaryProvider;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * Prepares process builder for "cache clean" command, either using npm or yarn (depending on
	 * {@link #isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            location where the command should be invoked.
	 * @return configured, operating system aware process builder for "npm/yarn cache clean" command.
	 */
	public ProcessBuilder getNpmCacheCleanProcessBuilder(File invocationPath) {
		Builder<String> builder = ImmutableList.<String> builder();

		Binary binary = isYarnUsed(invocationPath.toPath()) ? yarnBinaryProvider.get()
				: npmBinaryProvider.get();

		Optional<String[]> cacheCleanCommand = binary.getCacheCleanCommand();
		if (!cacheCleanCommand.isPresent()) {
			throw new IllegalStateException("cache clean not supported by binary: " + binary.getId());
		}

		if (isWindows()) {
			builder.add(WIN_SHELL_COMAMNDS);
			builder.add(concat(escapeBinaryPath(binary.getBinaryAbsolutePath()),
					cacheCleanCommand.get()));
		} else {
			builder.add(NIX_SHELL_COMAMNDS);
			builder.add(escapeBinaryPath(binary.getBinaryAbsolutePath())
					+ " " + Joiner.on(" ").join(cacheCleanCommand.get()));
		}

		return create(builder.build(), binary, invocationPath, false);
	}

	/**
	 * Prepares process builder for building a command to install npm packages, either using npm or yarn (depending on
	 * {@link #isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            location where the command should be invoked.
	 * @param packageName
	 *            package to install (might be space-separated list of names or an empty string to issue a plain
	 *            "npm/yarn install" without package names).
	 * @param save
	 *            instructs npm/yarn to save installed packages in package.json. Note that yarn will always do this, so
	 *            this flag is ignored in case yarn is used.
	 * @return configured, operating system aware process builder for "npm/yarn install" command.
	 */
	public ProcessBuilder getInstallNpmPackageProcessBuilder(File invocationPath, String packageName, boolean save) {
		ProcessBuilder pb = installUninstallCall(invocationPath, Operation.INSTALL, packageName, save);
		// for installation we need to additionally update environment with npmrc config
		Binary npmrc = npmrcBinaryProvider.get();
		npmrc.updateEnvironment(pb.environment());
		return pb;
	}

	/**
	 * Prepares process builder for building a command to uninstall one or more npm packages, either using npm or yarn
	 * (depending on {@link #isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            location on which npm command should be invoked.
	 * @param packageName
	 *            package to uninstall (might be space-separated list of names).
	 * @param save
	 *            instructs npm/yarn to save installed packages in package.json. Note that yarn will always do this, so
	 *            this flag is ignored in case yarn is used.
	 * @return configured, operating system aware process builder for "npm/yarn uninstall" command
	 */
	public ProcessBuilder getUninstallNpmPackageProcessBuilder(File invocationPath, String packageName, boolean save) {
		return installUninstallCall(invocationPath, Operation.UNINSTALL, packageName, save);
	}

	private ProcessBuilder installUninstallCall(File invocationPath, Operation operation, String packageName,
			boolean save) {
		Objects.requireNonNull(packageName);

		Binary binary = isYarnUsed(invocationPath.toPath()) ? yarnBinaryProvider.get() : npmBinaryProvider.get();
		if (!binary.canInstallNpmPackages()) {
			throw new IllegalStateException("(un-)installing npm packages not supported by binary: " + binary.getId());
		}

		String simpleCommand = null;
		switch (operation) {
		case INSTALL:
			simpleCommand = binary.getNpmInstallCommand(packageName.trim().isEmpty());
			break;
		case UNINSTALL:
			simpleCommand = binary.getNpmUninstallCommand();
			break;
		}

		Optional<Pair<String, String>> saveOptions = binary.getNpmSaveOptions();
		String saveOption;
		if (saveOptions.isPresent()) {
			saveOption = save ? saveOptions.get().getKey() : saveOptions.get().getValue();
		} else {
			saveOption = ""; // the fact that save/no-save is unsupported by binary is ignored
		}

		Builder<String> builder = ImmutableList.<String> builder();
		if (isWindows()) {
			builder.add(WIN_SHELL_COMAMNDS);
			builder.add(escapeBinaryPath(binary.getBinaryAbsolutePath()), simpleCommand, packageName, saveOption);
		} else {
			builder.add(NIX_SHELL_COMAMNDS);
			builder.add(
					escapeBinaryPath(binary.getBinaryAbsolutePath()) + " " + simpleCommand + " " + packageName + " "
							+ saveOption);
		}

		return create(builder.build(), binary, invocationPath, false);
	}

	/**
	 * Tells if yarn will be used (instead of npm) for managing npm packages in the given project folder. For now, this
	 * method returns <code>true</code> if and only if {@link #isYarnRequired(Path) yarn is required}, but in the future
	 * this choice might be taken by the user, e.g. via a setting in the Eclipse preferences.
	 * <p>
	 * For now, the caller can assume that if this method returns <code>false</code>, npm will be used for managing npm
	 * packages, as there isn't a third tool supported, at the moment.
	 *
	 * @param projectFolder
	 *            path to an N4JS or plain Javascript project folder (which may or may not be contained in a yarn
	 *            workspace) or the workspace root of a yarn workspace.
	 * @return <code>true</code> if yarn is used for managing npm packages at the given location, <code>false</code> if
	 *         npm is used.
	 */
	public boolean isYarnUsed(Path projectFolder) {
		return isYarnRequired(projectFolder);
	}

	/**
	 * Tells if (un-)installation of npm packages at the given location requires the tool <code>yarn</code> instead of
	 * plain <code>npm</code>. This is the case if the path points to a project that is part of a yarn workspace or to
	 * the workspace root of a yarn workspace.
	 *
	 * @param projectFolder
	 *            path to an N4JS or plain Javascript project folder (which may or may not be contained in a yarn
	 *            workspace) or the workspace root of a yarn workspace.
	 */
	private boolean isYarnRequired(Path projectFolder) {
		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectFolder);
		return nodeModulesFolder != null && nodeModulesFolder.isYarnWorkspace;
	}

	/**
	 * Prepares process builder for process checking version of the provided binary.
	 *
	 * Note this process will not use binary providers, but provided binary directly. This is due to the fact, that this
	 * Implementation will be used to reconfigure binary providers themselves.
	 *
	 * @param binary
	 *            to be invoked
	 * @return configured process
	 */
	public ProcessBuilder createVersionCheckProcess(final Binary binary) {
		final Builder<String> builder = ImmutableList.<String> builder();
		if (isWindows()) {
			builder.add(WIN_SHELL_COMAMNDS);
		} else {
			builder.add(NIX_SHELL_COMAMNDS);
		}

		builder.add(escapeBinaryPath(binary.getBinaryAbsolutePath()) + " " + binary.getVersionArgument());

		return create(builder.build(), binary, null, false);
	}

	private ProcessBuilder create(List<String> commands, Binary binary, File workingDirectory,
			boolean redirectErrorStream) {
		final ProcessBuilder processBuilder = new ProcessBuilder(commands);
		processBuilder.redirectErrorStream(redirectErrorStream);
		binary.updateEnvironment(processBuilder.environment());
		if (workingDirectory != null)
			processBuilder.directory(workingDirectory);
		return processBuilder;
	}

	/**
	 * Escaping path for example <code>C:\Program Files\node.exe</code> will be <code>"C:\Program Files\node.exe"</code>
	 * .
	 */
	private String escapeBinaryPath(String path) {
		return "\"" + path + "\"";
	}

	private static String[] concat(String string, String... moreStrings) {
		String[] result = new String[moreStrings.length + 1];
		result[0] = string;
		System.arraycopy(moreStrings, 0, result, 1, moreStrings.length);
		return result;
	}
}
