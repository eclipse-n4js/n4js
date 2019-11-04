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
package org.eclipse.n4js.binaries;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.binaries.nodejs.NodeYarnProcessBuilder;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutionCommand;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Factory for commands used to interact with external binaries.
 */
@Singleton
public class BinariesCommandFactory {

	@Inject
	private ProcessExecutor processExecutor;

	@Inject
	private NodeYarnProcessBuilder nodeProccessBuilder;

	/**
	 * Creates a command that will execute an external process for installing all dependencies defined in the project's
	 * package.json file, either using npm or yarn (depending on {@link NodeYarnProcessBuilder#isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            path where package(s) are supposed to be installed
	 * @param saveDependency
	 *            flag if installed package should be saved in package.json of the install path (ignored by yarn).
	 */
	public ProcessExecutionCommand createInstallEverythingCommand(File invocationPath, boolean saveDependency) {
		return createInstallPackageCommand(invocationPath, Collections.emptyList(), saveDependency);
	}

	/**
	 * Like {@link #createInstallPackageCommand(File, List, boolean)}, but for installing a single package only.
	 */
	public ProcessExecutionCommand createInstallPackageCommand(File invocationPath,
			String packageName, boolean saveDependency) {
		return createInstallPackageCommand(invocationPath, Collections.singletonList(packageName), saveDependency);
	}

	/**
	 * Creates a command that will execute an external process for installing the given packages, either using npm or
	 * yarn (depending on {@link NodeYarnProcessBuilder#isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            path where the packages are supposed to be installed.
	 * @param packageNames
	 *            names of the packages to install (optionally including version requirements; may be an empty list to
	 *            issue a plain "npm install" without package names given).
	 * @param saveDependency
	 *            flag if installed package should be saved in package.json of the install path (ignored by yarn)
	 */
	public ProcessExecutionCommand createInstallPackageCommand(File invocationPath,
			List<String> packageNames, boolean saveDependency) {

		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "install_npm_package";

			@Override
			public ProcessResult execute() {
				String escapedPackageNames = !packageNames.isEmpty()
						? "\"" + Joiner.on("\" \"").join(packageNames) + "\""
						: ""; // empty string will lead to a plain "npm install" without package names given
				boolean actualSaveDependency = saveDependency && !packageNames.isEmpty();
				ProcessBuilder processBuilder = nodeProccessBuilder.getInstallNpmPackageProcessBuilder(invocationPath,
						escapedPackageNames, actualSaveDependency);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
	}

	/**
	 * Creates a command that will execute an external process for uninstalling the given packages, either using npm or
	 * yarn (depending on {@link NodeYarnProcessBuilder#isYarnUsed(Path)}).
	 *
	 * @param invocationPath
	 *            path where the packages are supposed to be uninstalled from.
	 * @param packageNames
	 *            names of the packages to uninstall.
	 * @param saveDependency
	 *            flag if uninstalled packages should be saved in package.json of the uninstall path (ignored by yarn).
	 */
	public ProcessExecutionCommand createUninstallPackageCommand(File invocationPath,
			List<N4JSProjectName> packageNames, boolean saveDependency) {

		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "uninstall_npm_package";

			@Override
			public ProcessResult execute() {
				String escapedPackageNames = "\"" + Joiner.on("\" \"").join(packageNames) + "\"";
				ProcessBuilder processBuilder = nodeProccessBuilder.getUninstallNpmPackageProcessBuilder(invocationPath,
						escapedPackageNames, saveDependency);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
	}

	/**
	 * Creates command that will execute external node process that will clean npm/yarn cache.
	 *
	 * @param invocationPath
	 *            path in which cache clean will be invoked
	 */
	public ProcessExecutionCommand createCacheCleanCommand(File invocationPath) {
		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "clean_npm_package_cache";

			@Override
			public ProcessResult execute() {
				ProcessBuilder processBuilder = nodeProccessBuilder.getNpmCacheCleanProcessBuilder(invocationPath);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
	}

	/** See {@link NodeYarnProcessBuilder#isYarnUsed(Path)}. */
	public boolean isYarnUsed(File invocationPath) {
		return nodeProccessBuilder.isYarnUsed(invocationPath.toPath());
	}

	/**
	 * Creates {@link ProcessExecutionCommand} responsible for binary version check
	 *
	 * @param binary
	 *            to be invoked
	 */
	public ProcessExecutionCommand checkBinaryVersionCommand(Binary binary) {
		return new ProcessExecutionCommand() {

			@Override
			public ProcessResult execute() {
				ProcessBuilder processBuilder = nodeProccessBuilder.createVersionCheckProcess(binary);
				return processExecutor.createAndExecute(processBuilder, binary.getLabel(), OutputRedirection.SUPPRESS);
			}
		};
	}
}
