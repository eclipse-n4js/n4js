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
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
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

	private final static String NODE_PATH_SEP = File.pathSeparator;

	/** Environment key */
	private static final String NODE_PATH = "NODE_PATH";

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

	@Inject
	private IN4JSCore n4jsCore;

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

		String[] cmds = new String[0];

		// FIXME GH-1282: clean up the following code!

		IN4JSProject containingProject = n4jsCore.findProject(runConfig.getUserSelection()).orNull();
		if (containingProject == null) {
			// FIXME error handling
		}
		Path workingDirectory = containingProject.getLocationPath();

		cmds = createCmds(runConfig);

		final Collection<String> paths = newLinkedHashSet();
		paths.addAll(newArrayList(Splitter.on(NODE_PATH_SEP).omitEmptyStrings().trimResults()
				.split(runConfig.getCustomEnginePath())));

		paths.addAll(runConfig.getAdditionalPaths());

		paths.add(workingDirectory.resolve(N4JSGlobals.NODE_MODULES).toAbsolutePath().toString());

		String nodePaths = Joiner.on(NODE_PATH_SEP).join(paths);

		Map<String, String> env = new LinkedHashMap<>();
		env.putAll(runConfig.getEnvironmentVariables());

		env.put(NODE_PATH, nodePaths);
		env = nodeJsBinary.updateEnvironment(env);

		return executor.exec(cmds, workingDirectory.toFile(), env);
	}

	private String[] createCmds(RunConfiguration runConfig) {

		final ArrayList<String> commands = new ArrayList<>();

		commands.add(nodeJsBinaryProvider.get().getBinaryAbsolutePath());

		// activate esm
		commands.add("-r");
		commands.add("esm");

		// allow user flags
		final String nodeOptions = runConfig.getEngineOptions();
		if (nodeOptions != null) {
			for (String nodeOption : Splitter.on(breakingWhitespace()).omitEmptyStrings().split(nodeOptions)) {
				commands.add(nodeOption);
			}
		}

		// the file to launch
		String userSelection = (String) runConfig.getExecutionData()
				.get(RunConfiguration.EXEC_DATA_KEY__USER_SELECTION);
		userSelection = userSelection.substring(userSelection.indexOf('/') + 1);
		commands.add(userSelection);

		return commands.toArray(new String[] {});
	}
}
