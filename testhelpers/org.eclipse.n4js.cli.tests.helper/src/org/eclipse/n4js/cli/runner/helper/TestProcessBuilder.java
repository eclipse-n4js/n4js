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

import static org.eclipse.n4js.utils.OSInfo.isWindows;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NodeYarnProcessBuilder;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.YarnBinary;
import org.eclipse.n4js.cli.N4jscOptions;

/**
 * Concrete runner, i.e. runner implementation for node.js engine.
 */
public class TestProcessBuilder {
	Map<String, String> additionalEnvironmentVariables = new ConcurrentHashMap<>();

	final private NodeJsBinary nodeJsBinary;
	final private NpmBinary npmBinary;
	final private YarnBinary yarnBinary;

	/** Constructor */
	public TestProcessBuilder(NodeJsBinary nodeJsBinary, NpmBinary npmBinary, YarnBinary yarnBinary) {
		this.nodeJsBinary = nodeJsBinary;
		this.npmBinary = npmBinary;
		this.yarnBinary = yarnBinary;
	}

	/** @return a started process: {@code node -r esm fileToRun} */
	public ProcessBuilder nodejsRun(Path workingDirectory, Path fileToRun) {
		final Map<String, String> env = new LinkedHashMap<>();
		final String[] cmd = createCommandNodejsRun(fileToRun, env);
		return createProcessBuilder(workingDirectory, cmd, env);
	}

	/** @return a started process: {@code npm install} */
	public ProcessBuilder npmInstall(Path workingDirectory) {
		final Map<String, String> env = new LinkedHashMap<>();
		final String[] cmd = createCommandNpmInstall(env);
		return createProcessBuilder(workingDirectory, cmd, env);
	}

	/** @return a started process: {@code yarn install} */
	public ProcessBuilder yarnInstall(Path workingDirectory) {
		final Map<String, String> env = new LinkedHashMap<>();
		final String[] cmd = createCommandYarnInstall(env);
		return createProcessBuilder(workingDirectory, cmd, env);
	}

	/** @return a started java Process {@code java -jar n4jsc.jar OPTIONS} */
	public ProcessBuilder n4jscRun(Path workingDirectory, N4jscOptions options) {
		final Map<String, String> env = new LinkedHashMap<>();
		Binary.inheritNodeJsPathEnvVariable(env); // necessary?
		final String[] cmd = createCommandN4jscRun(env, options);
		return createProcessBuilder(workingDirectory, cmd, env);
	}

	private String[] createCommandNodejsRun(Path fileToRun, Map<String, String> output_env) {
		if (fileToRun == null) {
			throw new IllegalArgumentException("run configuration does not specify a file to run");
		}

		List<String> cmd = getCommands(output_env, nodeJsBinary.getBinaryAbsolutePath(), //
				"-r", "esm", fileToRun.toString());

		return cmd.toArray(new String[0]);
	}

	private String[] createCommandNpmInstall(Map<String, String> output_env) {
		List<String> cmd = getCommands(output_env, npmBinary.getBinaryAbsolutePath(), "install");
		return cmd.toArray(new String[0]);
	}

	private String[] createCommandYarnInstall(Map<String, String> output_env) {
		List<String> cmd = getCommands(output_env, yarnBinary.getBinaryAbsolutePath(), "install");
		return cmd.toArray(new String[0]);
	}

	private String[] createCommandN4jscRun(Map<String, String> output_env, N4jscOptions options) {
		File n4jscAbsoluteFile = new File("target/n4jsc.jar").getAbsoluteFile();
		String n4jscFileName = n4jscAbsoluteFile.toString();

		ArrayList<String> args2 = new ArrayList<>();
		Collections.addAll(args2, "java", "-jar", n4jscFileName);
		args2.addAll(options.toArgs());
		String[] cmdOptions = args2.toArray(new String[args2.size()]);

		List<String> cmd = getCommands(output_env, n4jscFileName, cmdOptions);
		return cmd.toArray(new String[0]);
	}

	private List<String> getCommands(Map<String, String> output_env, String binaryFileName, String... options) {
		File binaryFile = new File(binaryFileName);

		String additionalPath = binaryFile.getParent();
		output_env.put(Binary.PATH, additionalPath);

		ArrayList<String> cmd = new ArrayList<>();

		// start command line with absolute path to binary
		String npmPath = "\"" + binaryFileName + "\"";

		if (isWindows()) {
			cmd.addAll(Arrays.asList(NodeYarnProcessBuilder.WIN_SHELL_COMAMNDS));
			cmd.add(npmPath);
			cmd.addAll(Arrays.asList(options));
		} else {
			cmd.addAll(Arrays.asList(NodeYarnProcessBuilder.NIX_SHELL_COMAMNDS));
			cmd.add(npmPath + " " + String.join(" ", options));
		}

		return cmd;
	}

	private ProcessBuilder createProcessBuilder(Path workingDirectory, String[] cmd, Map<String, String> env) {
		final IStatus status = nodeJsBinary.validate();
		if (!status.isOK()) {
			throw new IllegalStateException(status.getMessage());
		}
		if (workingDirectory == null) {
			throw new IllegalArgumentException("run configuration does not specify a working directory");
		}

		env.putAll(additionalEnvironmentVariables);

		ProcessBuilder pb = new ProcessBuilder(cmd);
		Map<String, String> environment = pb.environment();
		Binary.mergeEnvironments(environment, env);

		pb.directory(workingDirectory.toFile());
		// pb.inheritIO(); // output is captured in NodejsExecuter

		return pb;
	}

}
