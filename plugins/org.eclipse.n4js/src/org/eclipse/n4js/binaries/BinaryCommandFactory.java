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

import org.eclipse.n4js.binaries.nodejs.NodeProcessBuilder;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutionCommand;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Factory for commands used to interact with external binaries.
 */
@Singleton
public class BinaryCommandFactory {

	@Inject
	private ProcessExecutor processExecutor;

	@Inject
	private NodeProcessBuilder nodeProccessBuilder;

	/**
	 * Creates command that will execute external node process that will command npm to install given package.
	 *
	 * @param invocationPath
	 *            path where package is supposed to be installed
	 * @param packageName
	 *            name of the package to install
	 * @param saveDependency
	 *            flag if installed package should be saved in package.json of the install path
	 */
	public ProcessExecutionCommand createInstallPackageCommand(File invocationPath,
			String packageName, boolean saveDependency) {
		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "npm_install";

			@Override
			public ProcessResult execute() {
				ProcessBuilder processBuilder = nodeProccessBuilder.getNpmInstallProcessBuilder(invocationPath,
						packageName, saveDependency);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
	}

	/**
	 * Creates command that will execute external node process that will command npm to uninstall given package.
	 *
	 * @param invocationPath
	 *            path where package is supposed to be installed
	 * @param packageName
	 *            name of the package to uninstall
	 * @param saveDependency
	 *            flag if uninstalled package should be saved in package.json of the uninstall path
	 */
	public ProcessExecutionCommand createUninstallPackageCommand(File invocationPath,
			String packageName, boolean saveDependency) {
		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "npm_uninstall";

			@Override
			public ProcessResult execute() {
				ProcessBuilder processBuilder = nodeProccessBuilder.getNpmUninstallProcessBuilder(invocationPath,
						packageName, saveDependency);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
	}

	/**
	 * Creates command that will execute external node process that will clean npm cache.
	 *
	 * @param invocationPath
	 *            path in which cache clean will be invoked
	 */
	public ProcessExecutionCommand createCacheCleanCommand(File invocationPath) {
		return new ProcessExecutionCommand() {
			private static final String COMMAND_NAME = "npm_cache_clean";

			@Override
			public ProcessResult execute() {
				ProcessBuilder processBuilder = nodeProccessBuilder.getNpmCacheCleanProcessBuilder(invocationPath);
				return processExecutor.createAndExecute(processBuilder, COMMAND_NAME, OutputRedirection.REDIRECT);
			}
		};
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
