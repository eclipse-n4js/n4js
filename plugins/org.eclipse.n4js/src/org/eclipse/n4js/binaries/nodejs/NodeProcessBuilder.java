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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.n4js.binaries.Binary;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.inject.Singleton;

/**
 * Factory for the processes interacting with Node.js, takes care of the OS specific data.
 */
@Singleton
public class NodeProcessBuilder {

	private static final String NPM_COMMAND_INSTALL = "install";
	private static final String NPM_COMMAND_UNINSTALL = "uninstall";
	private static final String NPM_OPTION_SAVE = "--save";
	private static final String NPM_OPTION_NO_SAVE = "--no-save";
	private static final String[] WIN_SHELL_COMAMNDS = { "cmd", "/c" };
	private static final String[] NIX_SHELL_COMAMNDS = { "sh", "-c" };

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	/**
	 * Prepares process builder for "npm install" command.
	 *
	 * @param invocationPath
	 *            location on which npm command should be invoked
	 * @param packageName
	 *            package to install (might be space separated list of names or <code>null</code> to issue a plain "npm
	 *            install" without package names.
	 * @param save
	 *            instructs npm to save installed packages in package.json (if available)
	 * @return configured, operating system aware process builder for "npm install" command
	 */
	public ProcessBuilder getNpmInstallProcessBuilder(File invocationPath, String packageName, boolean save) {
		ProcessBuilder pb = simpleCall(invocationPath, packageName, save, NPM_COMMAND_INSTALL);
		// for npm install we need to additionally update environment with npmrc config
		Binary npmrc = npmrcBinaryProvider.get();
		npmrc.updateEnvironment(pb.environment());
		return pb;
	}

	/**
	 * Prepares process builder for "npm uninstall" command.
	 *
	 * @param invocationPath
	 *            location on which npm command should be invoked
	 * @param packageName
	 *            package to uninstall (might be space separated list of names)
	 * @param save
	 *            instructs npm to save uninstalled packages in package.json (if available)
	 * @return configured, operating system aware process builder for "npm uninstall" command
	 */
	public ProcessBuilder getNpmUninstallProcessBuilder(File invocationPath, String packageName, boolean save) {
		return simpleCall(invocationPath, packageName, save, NPM_COMMAND_UNINSTALL);
	}

	/**
	 * Prepares process builder for "npm cache clean" command.
	 *
	 * @param invokationPath
	 *            location on which npm command should be invoked
	 * @return configured, operating system aware process builder for "npm cache clean" command
	 */
	public ProcessBuilder getNpmCacheCleanProcessBuilder(File invokationPath) {
		Builder<String> builder = ImmutableList.<String> builder();
		NpmBinary npmBinary = npmBinaryProvider.get();

		if (isWindows()) {
			builder.add(WIN_SHELL_COMAMNDS);
			builder.add(escapeBinaryPath(npmBinary.getBinaryAbsolutePath()), "cache", "clean", "--force");
		} else {
			builder.add(NIX_SHELL_COMAMNDS);
			builder.add(escapeBinaryPath(npmBinary.getBinaryAbsolutePath()) + " cache clean --force");
		}

		return create(builder.build(), npmBinary, invokationPath, false);
	}

	/**
	 * Prepares process builder for simple npm commands, e.g. "npm install" or "npm uninstall". Specific command should
	 * be passed without surrounding spaces or or quotes.
	 *
	 * @param invokationPath
	 *            location on which npm command should be invoked
	 * @param packageName
	 *            package passed as parameter to the command (might be space separated list of names). If packageName is
	 *            <code>null</code>, it is assumed to be the empty string.
	 * @param save
	 *            instructs npm to save command result to packages in package.json (if available)
	 * @param simpleCommand
	 *            command to execute
	 * @return configured, operating system aware process builder for given command
	 */
	private ProcessBuilder simpleCall(File invokationPath, String packageName, boolean save, String simpleCommand) {
		Builder<String> builder = ImmutableList.<String> builder();
		NpmBinary npmBinary = npmBinaryProvider.get();
		String saveCommand = save ? NPM_OPTION_SAVE : NPM_OPTION_NO_SAVE;
		String resolvedPackageName = (packageName == null) ? "" : packageName;

		if (isWindows()) {
			builder.add(WIN_SHELL_COMAMNDS);
			builder.add(escapeBinaryPath(npmBinary.getBinaryAbsolutePath()), simpleCommand, resolvedPackageName,
					saveCommand);
		} else {
			builder.add(NIX_SHELL_COMAMNDS);
			builder.add(
					escapeBinaryPath(npmBinary.getBinaryAbsolutePath()) + " " + simpleCommand + " "
							+ resolvedPackageName + " "
							+ saveCommand);
		}

		return create(builder.build(), npmBinary, invokationPath, false);
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
}
