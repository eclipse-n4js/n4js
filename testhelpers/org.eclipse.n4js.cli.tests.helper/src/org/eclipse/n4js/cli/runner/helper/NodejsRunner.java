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
package org.eclipse.n4js.cli.runner.helper;

import static com.google.common.base.CharMatcher.breakingWhitespace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Concrete runner, i.e. runner implementation for node.js engine.
 */
public class NodejsRunner {

	/** Name of environment variable used by node to obtain list of search paths. */
	private static final String NODE_PATH = "NODE_PATH";

	/** Separator used in {@link #NODE_PATH}. */
	private static final String NODE_PATH_SEP = File.pathSeparator;

	/**
	 * Splitter used for parsing the {@link RunConfiguration#getEngineOptions() engine} and
	 * {@link RunConfiguration#getRunOptions() run options}.
	 */
	private static final Splitter OPTIONS_SPLITTER = Splitter.on(breakingWhitespace()).omitEmptyStrings().trimResults();

	final private NodeJsBinary nodeJsBinary;

	/** Constructor */
	public NodejsRunner(NodeJsBinary nodeJsBinary) {
		this.nodeJsBinary = nodeJsBinary;
	}

	/** @return a started nodejs process configured with the given configuration */
	public Process run(RunConfiguration runConfig) throws ExecutionException {
		final IStatus status = nodeJsBinary.validate();
		if (!status.isOK()) {
			Exceptions.sneakyThrow(new IllegalBinaryStateException(nodeJsBinary, status));
		}
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

		final String[] cmd = createCommand(runConfig);

		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.environment().putAll(env);
		pb.directory(workingDirectory.toFile());
		// pb.inheritIO(); // output is captured in NodejsExecuter

		try {
			return pb.start();
		} catch (IOException e) {
			throw new ExecutionException(e);
		}
	}

	private String[] createCommand(RunConfiguration runConfig) {
		final ArrayList<String> cmd = new ArrayList<>();

		// start command line with absolute path to node binary
		cmd.add(nodeJsBinary.getBinaryAbsolutePath());

		// activate esm
		cmd.add("-r");
		cmd.add("esm");

		// allow user flags
		final String engineOptions = runConfig.getEngineOptions();
		for (String engineOption : splitOptions(engineOptions)) {
			cmd.add(engineOption);
		}

		// the file to launch
		final Path fileToRun = runConfig.getFileToRun();
		if (fileToRun == null) {
			throw new IllegalArgumentException("run configuration does not specify a file to run");
		}
		cmd.add(fileToRun.toString());

		// more user flags
		final String runOptions = runConfig.getRunOptions();
		for (String runOption : splitOptions(runOptions)) {
			cmd.add(runOption);
		}

		return cmd.toArray(new String[0]);
	}

	private Iterable<String> splitOptions(String optionsString) {
		return optionsString != null ? OPTIONS_SPLITTER.split(optionsString.trim()) : Collections.emptyList();
	}

}
