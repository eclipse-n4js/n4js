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
package org.eclipse.n4js.runner.nodejs;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.apache.log4j.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.IRunner;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.SystemLoaderInfo;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerDescriptorImpl;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Concrete runner, i.e. runner implementation for node.js engine.
 */
public class NodeRunner implements IRunner {
	private final static String NODE_PATH_SEP = File.pathSeparator;

	/** Environment key */
	private static final String NODE_PATH = "NODE_PATH";

	private static final org.apache.log4j.Logger LOGGER = getLogger(NodeRunner.class);

	/**
	 * Class for providing descriptors for the Node.js runner with the same information as defined in the plugin.xml
	 * (used only by N4jsc). Supports instance supplying with injection.
	 */
	public static final class NodeRunnerDescriptorProvider implements Provider<IRunnerDescriptor> {

		@Inject
		private Provider<NodeRunner> nodeRunnerProvider;

		@Override
		public IRunnerDescriptor get() {
			return new RunnerDescriptorImpl(ID, "Node.js Runner", RuntimeEnvironment.NODEJS, nodeRunnerProvider.get());
		}

	}

	/** ID of the Node.js runner as defined in the plugin.xml. */
	public static final String ID = "org.eclipse.n4js.runner.nodejs.NODEJS";

	/**
	 * Descriptor for the Node.js runner with the same information as defined in the plugin.xml (used only by N4jsc).
	 */
	public static final IRunnerDescriptor DESCRIPTOR = new RunnerDescriptorImpl(ID, "Node.js Runner",
			RuntimeEnvironment.NODEJS, new NodeRunner());

	@Inject
	private Provider<NodeEngineCommandBuilder> commandBuilderProvider;

	@Inject
	private Provider<NodeJsBinary> nodeJsBinaryProvider;

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

		final NodeJsBinary nodeJsBinary = nodeJsBinaryProvider.get();
		final IStatus status = nodeJsBinary.validate();
		if (!status.isOK()) {
			Exceptions.sneakyThrow(new IllegalBinaryStateException(nodeJsBinary, status));
		}

		Process process = null;
		String[] cmds = new String[0];
		try {
			NodeRunOptions runOptions = new NodeRunOptions();

			runOptions.setExecModule(runConfig.getExecModule());
			runOptions.setCoreProjectPaths(on(NODE_PATH_SEP).join(runConfig.getCoreProjectPaths()));
			runOptions.setEngineOptions(runConfig.getEngineOptions());
			runOptions.setCustomEnginePath(runConfig.getCustomEnginePath());
			runOptions.setExecutionData(runConfig.getExecutionDataAsJSON());
			runOptions.setSystemLoader(SystemLoaderInfo.fromString(runConfig.getSystemLoader()));

			final Collection<String> paths = newLinkedHashSet();
			// Add custom node paths
			paths.addAll(newArrayList(Splitter.on(NODE_PATH_SEP).omitEmptyStrings().trimResults()
					.split(runConfig.getCustomEnginePath())));

			runOptions.addInitModules(runConfig.getInitModules());
			if (runConfig.isUseDefaultBootstrap()) {
				paths.addAll(NodeEngineDefaultBootstrap.DEFAULT_BOOTSTRAP_PATHS);
			}

			NodeEngineCommandBuilder cb = commandBuilderProvider.get();
			cmds = cb.createCmds(runOptions);

			File workingDirectory = Files.createTempDirectory(null).toFile();

			paths.addAll(runConfig.getCoreProjectPaths());
			if (runConfig.getAdditionalPath() != null && !runConfig.getAdditionalPath().isEmpty())
				paths.add(runConfig.getAdditionalPath());

			String nodePaths = on(NODE_PATH_SEP).join(paths);

			Map<String, String> env = new LinkedHashMap<>();
			env.put(NODE_PATH, nodePaths);

			env = nodeJsBinary.updateEnvironment(env);

			process = executor.exec(cmds, workingDirectory, env);

		} catch (IOException | RuntimeException | ExecutionException e) {
			LOGGER.error(e);
		}
		return process;
	}
}
