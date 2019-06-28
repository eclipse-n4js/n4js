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
package org.eclipse.n4js.runner.chrome;

import static org.apache.log4j.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.IRunner;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerDescriptorImpl;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;

/**
 */
public class ChromeRunner implements IRunner {
	private final static String NODE_PATH_SEP = File.pathSeparator;

	private static final Logger LOGGER = getLogger(ChromeRunner.class);

	/** ID of the Chrome runner as defined in the plugin.xml. */
	public static final String ID = "org.eclipse.n4js.runner.chrome.CHROME";

	/** Descriptor for the Chrome runner with the same information as defined in the plugin.xml (used only by N4jsc). */
	public static final IRunnerDescriptor DESCRIPTOR = new RunnerDescriptorImpl(ID, "Chrome Runner",
			RuntimeEnvironment.CHROME, new ChromeRunner());

	@Override
	public RunConfiguration createConfiguration() {
		return new RunConfiguration();
	}

	@Override
	public void prepareConfiguration(RunConfiguration config) {
		// no special values to be prepared here
	}

	@Override
	public Process run(RunConfiguration runConfig, IExecutor executor) {

		Process process = null;
		String[] cmds = new String[0];
		try {
			ChromeRunnerRunOptions runOptions = new ChromeRunnerRunOptions();

			// runOptions.setExecModule(runConfig.getExecModule());
			runOptions.setCoreProjectPaths(runConfig.getCoreProjectPaths().keySet().stream()
					.map(Object::toString)
					.collect(Collectors.joining(NODE_PATH_SEP)));

			runOptions.setExecutionData(runConfig.getExecutionDataAsJSON());

			final List<Path> paths = new ArrayList<>();

			// runOptions.addInitModules(runConfig.getInitModules());
			// if (runConfig.isUseCustomBootstrap()) {
			// throw new UnsupportedOperationException("Using default bootstrap is not supported for Chrome.");
			// }

			NodeEngineCommandBuilder cb = new NodeEngineCommandBuilder();
			cmds = cb.createCmds(runOptions);

			File workingDirectory = Files.createTempDirectory(null)
					.toFile();

			paths.addAll(runConfig.getCoreProjectPaths().keySet());

			String nodePaths = paths.stream().map(Object::toString).collect(Collectors.joining(NODE_PATH_SEP));

			Map<String, String> env = new LinkedHashMap<>();
			env.put("NODE_PATH", nodePaths);

			// selenium-webdriver requires the chromedriver binary to be in the system path,
			// so we need to copy the host system path and add a temporary folder containing
			// the chromedriver binary
			final File chromedriver = getChromedriverBinary(true); // true -> copy to temporary folder
			final String newPATH = System.getenv("PATH") + File.pathSeparator
					+ chromedriver.getAbsoluteFile().getParent();
			env.put("PATH", newPATH);

			process = executor.exec(cmds, workingDirectory, env);

		} catch (IOException | RuntimeException | ExecutionException e) {
			LOGGER.error(e);
		}
		return process;
	}

	private File getChromedriverBinary(@SuppressWarnings("unused") boolean alwaysCopyToTemp) {
		// BinaryProvider.getChromedriverBinary(true); // true -> copy to temporary folder
		return null;
	}
}
