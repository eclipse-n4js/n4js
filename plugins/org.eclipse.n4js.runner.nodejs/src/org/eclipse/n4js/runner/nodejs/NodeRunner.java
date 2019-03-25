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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.IRunner;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFileBasedShippedCodeConfigurationHelper;
import org.eclipse.n4js.runner.SystemLoaderInfo;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerDescriptorImpl;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.utils.io.FileUtils;
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

	private static final Logger LOGGER = Logger.getLogger(NodeRunner.class);

	/** ID of the Node.js runner as defined in the plugin.xml. */
	public static final String ID = "org.eclipse.n4js.runner.nodejs.NODEJS";

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

	/**
	 * Descriptor for the Node.js runner with the same information as defined in the plugin.xml (used only by N4jsc).
	 */
	public static final IRunnerDescriptor DESCRIPTOR = new RunnerDescriptorImpl(ID, "Node.js Runner",
			RuntimeEnvironment.NODEJS, new NodeRunner());

	@Inject
	private Provider<NodeEngineCommandBuilder> commandBuilderProvider;

	@Inject
	private Provider<NodeJsBinary> nodeJsBinaryProvider;

	@Inject
	private RunnerFileBasedShippedCodeConfigurationHelper shippedCodeConfigurationHelper;

	@Inject
	private Provider<NodeRunOptions> nodeRunOptionsProvider;

	@Override
	public RunConfiguration createConfiguration() {
		return new RunConfiguration();
	}

	@Override
	public void prepareConfiguration(RunConfiguration config) {
		if (config.isUseCustomBootstrap()) {
			shippedCodeConfigurationHelper.configureFromFileSystem(config);
		}
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
			NodeRunOptions runOptions = createRunOptions(runConfig);

			Path workingDirectory = FileUtils.createTempDirectory("N4JSNodeRun");

			NodeEngineCommandBuilder cb = commandBuilderProvider.get();
			cmds = cb.createCmds(runOptions, workingDirectory);

			final Collection<String> paths = newLinkedHashSet();
			paths.addAll(newArrayList(Splitter.on(NODE_PATH_SEP).omitEmptyStrings().trimResults()
					.split(runConfig.getCustomEnginePath())));

			paths.addAll(runConfig.getAdditionalPaths());

			paths.add(workingDirectory.resolve(N4JSGlobals.NODE_MODULES).toAbsolutePath().toString());

			String nodePaths = on(NODE_PATH_SEP).join(paths);

			Map<String, String> env = new LinkedHashMap<>();
			env.putAll(runOptions.getEnvironmentVariables());

			env.put(NODE_PATH, nodePaths);
			env = nodeJsBinary.updateEnvironment(env);

			process = executor.exec(cmds, workingDirectory.toFile(), env);

		} catch (IOException | RuntimeException | ExecutionException e) {
			LOGGER.error(e);
		}
		return process;
	}

	/**
	 * Creates the {@link NodeRunOptions} based on the given {@link RunConfiguration}.
	 */
	protected NodeRunOptions createRunOptions(RunConfiguration runConfig) {
		NodeRunOptions runOptions = nodeRunOptionsProvider.get();

		runOptions.setExecModule(runConfig.getExecModule());
		runOptions.addInitModules(runConfig.getInitModules());
		runOptions.setCoreProjectPaths(runConfig.getCoreProjectPaths());
		runOptions.setEngineOptions(runConfig.getEngineOptions());
		runOptions.setEnvironmentVariables(runConfig.getEnvironmentVariables());
		runOptions.setCustomEnginePath(runConfig.getCustomEnginePath());
		runOptions.setExecutionData(runConfig.getExecutionDataAsJSON());
		runOptions.setSystemLoader(SystemLoaderInfo.fromString(runConfig.getSystemLoader()));
		return runOptions;
	}
}
