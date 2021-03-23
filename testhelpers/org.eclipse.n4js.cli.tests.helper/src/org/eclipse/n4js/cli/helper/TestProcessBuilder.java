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
package org.eclipse.n4js.cli.helper;

import static org.eclipse.n4js.utils.OSInfo.isWindows;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.utils.BinariesConstants;
import org.eclipse.n4js.cli.utils.BinariesLocatorHelper;
import org.eclipse.n4js.cli.utils.BinariesUtils;

import com.google.common.base.Optional;

/**
 * Concrete runner, i.e. runner implementation for node.js engine.
 */
public class TestProcessBuilder {
	Map<String, String> additionalEnvironmentVariables = new ConcurrentHashMap<>();

	private final BinariesLocatorHelper binariesLocatorHelper;

	/** Constructor */
	public TestProcessBuilder(BinariesLocatorHelper binariesLocatorHelper) {
		this.binariesLocatorHelper = binariesLocatorHelper;
	}

	/** @return a process: {@code node -r esm fileToRun} */
	public ProcessBuilder nodejsRun(Path workingDirectory, Map<String, String> environment, Path fileToRun,
			String[] options) {

		final String[] cmd = createCommandNodejsRun(fileToRun, environment, options);
		return createProcessBuilder(workingDirectory, cmd, environment);
	}

	/** @return a process: {@code npm install} */
	public ProcessBuilder npmRun(Path workingDirectory, Map<String, String> environment, String[] options) {
		final String[] cmd = createCommandNpmRun(environment, options);
		return createProcessBuilder(workingDirectory, cmd, environment);
	}

	/** @return a process: {@code yarn install} */
	public ProcessBuilder yarnRun(Path workingDirectory, Map<String, String> environment, String[] options) {
		final String[] cmd = createCommandYarnRun(environment, options);
		return createProcessBuilder(workingDirectory, cmd, environment);
	}

	/** @return a Java process: {@code java -jar n4jsc.jar OPTIONS} */
	public ProcessBuilder n4jscRun(Path workingDirectory, Map<String, String> environment, N4jscOptions options) {
		BinariesUtils.inheritNodeJsPathEnvVariable(environment); // necessary?
		final String[] cmd = createCommandN4jscRun(environment, options);
		return createProcessBuilder(workingDirectory, cmd, environment);
	}

	/** @return a process for running the given executable. */
	public ProcessBuilder run(Path workingDirectory, Map<String, String> environment, Path executable,
			String[] options) {
		final String[] cmd = createCommand(workingDirectory, environment, executable, options);
		return createProcessBuilder(workingDirectory, cmd, environment);
	}

	private String[] createCommandNodejsRun(Path fileToRun, Map<String, String> output_env, String[] options) {
		if (fileToRun == null) {
			throw new IllegalArgumentException("run configuration does not specify a file to run");
		}

		List<String> optionList = new ArrayList<>();
		optionList.add("-r");
		optionList.add("esm");
		optionList.add(fileToRun.toString());
		optionList.addAll(Arrays.asList(options));
		String[] cmdOptions = optionList.toArray(String[]::new);

		List<String> cmd = getCommands(output_env, binariesLocatorHelper.getNodeBinary(), cmdOptions);

		return cmd.toArray(new String[0]);
	}

	private String[] createCommandNpmRun(Map<String, String> output_env, String[] options) {
		List<String> cmd = getCommands(output_env, binariesLocatorHelper.getNpmBinary(), options);
		return cmd.toArray(new String[0]);
	}

	private String[] createCommandYarnRun(Map<String, String> output_env, String[] options) {
		List<String> cmd = getCommands(output_env, binariesLocatorHelper.getYarnBinary(), options);
		return cmd.toArray(new String[0]);
	}

	private String[] createCommandN4jscRun(Map<String, String> output_env, N4jscOptions options) {
		File n4jscAbsoluteFile = N4jscJarProvider.getAbsoluteRunnableN4jsc();
		String n4jscFileName = n4jscAbsoluteFile.toString();

		List<String> optionList = new ArrayList<>();
		optionList.add("-jar");
		optionList.add(n4jscFileName);
		optionList.addAll(options.toArgs());
		String[] cmdOptions = optionList.toArray(String[]::new);

		List<String> cmd = getCommands(output_env, binariesLocatorHelper.getJavaBinary(), cmdOptions);
		return cmd.toArray(new String[0]);
	}

	private String[] createCommand(Path workingDirectory, Map<String, String> output_env,
			Path executable, String[] options) {
		List<String> cmd = getCommands(Optional.of(workingDirectory), output_env, executable, options);
		return cmd.toArray(new String[0]);
	}

	private List<String> getCommands(Map<String, String> output_env, Path binaryPathAndName, String... options) {
		return getCommands(Optional.absent(), output_env, binaryPathAndName, options);
	}

	private List<String> getCommands(Optional<Path> workingDirectory, Map<String, String> output_env, Path executable,
			String... options) {

		Path executableAbsolute = workingDirectory.isPresent()
				? workingDirectory.get().resolve(executable)
				: executable.toAbsolutePath();

		String additionalPath = executableAbsolute.getParent().toString();
		output_env.put(BinariesUtils.PATH, additionalPath);

		ArrayList<String> cmd = new ArrayList<>();

		// start command line with absolute path to binary
		String npmPath = "\"" + executableAbsolute.toString() + "\"";

		if (isWindows()) {
			cmd.addAll(Arrays.asList(BinariesConstants.WIN_SHELL_COMAMNDS));
			cmd.add(npmPath);
			cmd.addAll(Arrays.asList(options));
		} else {
			cmd.addAll(Arrays.asList(BinariesConstants.NIX_SHELL_COMAMNDS));
			cmd.add(npmPath + " " + String.join(" ", options));
		}

		return cmd;
	}

	private ProcessBuilder createProcessBuilder(Path workingDirectory, String[] cmd, Map<String, String> env) {
		if (workingDirectory == null) {
			throw new IllegalArgumentException("run configuration does not specify a working directory");
		}

		env.putAll(additionalEnvironmentVariables);

		ProcessBuilder pb = new ProcessBuilder(cmd);
		Map<String, String> environment = pb.environment();
		BinariesUtils.mergeEnvironments(environment, env);

		pb.directory(workingDirectory.toFile());
		// pb.inheritIO(); // output is captured in NodejsExecuter

		return pb;
	}

}
