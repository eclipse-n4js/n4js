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

import static com.google.common.base.CharMatcher.breakingWhitespace;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
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
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerDescriptorImpl;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Concrete runner, i.e. runner implementation for node.js engine.
 */
public class NodeRunner implements IRunner {

	/** Name of environemnt variable used by node to obtain list of search paths. */
	private static final String NODE_PATH = "NODE_PATH";

	/** Separator used in {@link #NODE_PATH}. */
	private static final String NODE_PATH_SEP = File.pathSeparator;

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
	private Provider<NodeJsBinary> nodeJsBinaryProvider;

	@Override
	public RunConfiguration createConfiguration() {
		return new RunConfiguration();
	}

	@Override
	public void prepareConfiguration(RunConfiguration config) {
		// no special preparations required
	}

	@Override
	public Process run(RunConfiguration runConfig, IExecutor executor) throws ExecutionException {

		final NodeJsBinary nodeJsBinary = nodeJsBinaryProvider.get();
		final IStatus status = nodeJsBinary.validate();
		if (!status.isOK()) {
			Exceptions.sneakyThrow(new IllegalBinaryStateException(nodeJsBinary, status));
		}

		final String[] cmd = createCommand(runConfig);

		final Path workingDirectory = runConfig.getWorkingDirectory();
		if (workingDirectory == null) {
			throw new IllegalArgumentException("run configuration does not specify a working directory");
		}

		Map<String, String> env = new LinkedHashMap<>();
		env.putAll(runConfig.getEnvironmentVariables());
		final Collection<String> additionalPaths = runConfig.getAdditionalPaths();
		if (additionalPaths != null && !additionalPaths.isEmpty()) {
			env.put(NODE_PATH, Joiner.on(NODE_PATH_SEP).join(additionalPaths));
		}
		env = nodeJsBinary.updateEnvironment(env);

		return executor.exec(cmd, workingDirectory.toFile(), env);
	}

	private String[] createCommand(RunConfiguration runConfig) {
		final ArrayList<String> cmd = new ArrayList<>();

		// start command line with absolute path to node binary
		cmd.add(nodeJsBinaryProvider.get().getBinaryAbsolutePath());

		// activate esm
		cmd.add("-r");
		cmd.add("esm");

		// allow user flags
		final String nodeOptions = runConfig.getEngineOptions();
		if (nodeOptions != null) {
			for (String nodeOption : Splitter.on(breakingWhitespace()).omitEmptyStrings().split(nodeOptions)) {
				cmd.add(nodeOption);
			}
		}

		// the file to launch
		final Path fileToRun = runConfig.getFileToRun();
		if (fileToRun == null) {
			throw new IllegalArgumentException("run configuration does not specify a file to run");
		}
		cmd.add(fileToRun.toString());

		return cmd.toArray(new String[0]);
	}
}
