/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscOptions;

import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 * Class to call n4jsc and other other cli tools.
 */
public class CliTools {
	private static final String RELATIVE_PATH = "...";

	final private Map<String, String> environment = new HashMap<>();
	private boolean isMirrorSystemOut = false;

	/** Calls n4jsc main method and updates the {@code cliResult}. Backend is deactivated. */
	public void callN4jscFrontendInprocess(String[] options, boolean removeUsage, CliCompileResult result) {
		InProcessExecuter inProcessExecuter = new InProcessExecuter(false, isMirrorSystemOut);
		inProcessExecuter.n4jsc(new File("").getAbsoluteFile(), options, result);
		trimOutputs(result, removeUsage);
	}

	/** Calls {@link N4jscMain#main(String[])} and updates the {@code cliResult}. */
	public void callN4jscInprocess(N4jscOptions options, boolean removeUsage, CliCompileResult result) {
		String[] args = options.toArgs().toArray(String[]::new);
		InProcessExecuter inProcessExecuter = new InProcessExecuter(true, isMirrorSystemOut);
		inProcessExecuter.n4jsc(options.getDirs().get(0), args, result);
		trimOutputs(result, removeUsage);
	}

	/** Runs n4jsc.jar in a separate process and updates the {@code cliResult}. Respects given environment variables. */
	public void callN4jscExprocess(N4jscOptions options, boolean removeUsage, CliCompileProcessResult cliResult) {
		List<File> srcFiles = options.getDirs();
		File fileArg = srcFiles.isEmpty() ? new File("").getAbsoluteFile() : srcFiles.get(0);
		ProcessResult n4jscResult = getExProcessExecuter().n4jscRun(fileArg.toPath(), environment, options);

		cliResult.workingDir = n4jscResult.getWorkingDir();
		cliResult.command = n4jscResult.getCommand();
		cliResult.exception = n4jscResult.getException();
		cliResult.exitCode = n4jscResult.getExitCode();
		cliResult.stdOut = n4jscResult.getStdOut();
		cliResult.errOut = n4jscResult.getErrOut();

		// save transpiled files
		cliResult.transpiledFiles = GeneratedJSFilesCounter.getTranspiledFiles(fileArg.toPath());

		trimOutputs(cliResult, removeUsage);
	}

	/**
	 * Sets the given name and value pair to the environment.
	 * <p>
	 * <b>Note:</b> Only active when used in
	 * {@link #callN4jscExprocess(N4jscOptions, boolean, CliCompileProcessResult) }
	 */
	public void setEnvironmentVariable(String name, String value) {
		this.environment.put(name, value);
	}

	/**
	 * Set configuration of n4jsc execution: Iff true, the output is mirrored during execution on System.out and
	 * System.err
	 */
	public void setIsMirrorSystemOut(boolean isMirrorSystemOut) {
		this.isMirrorSystemOut = isMirrorSystemOut;
	}

	/** see {@link TestProcessExecuter#runNodejs(Path, Map, Path, String[])} */
	public ProcessResult runNodejs(Path workingDir, Path runFile, String... options) {
		return getExProcessExecuter().runNodejs(workingDir, environment, runFile, options);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmInstall(Path workingDir, String... options) {
		String[] installOptions = Lists.asList("install", options).toArray(String[]::new);
		return getExProcessExecuter().npmRun(workingDir, environment, installOptions);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmList(Path workingDir, String... options) {
		String[] listOptions = Lists.asList("list", options).toArray(String[]::new);
		return getExProcessExecuter().npmRun(workingDir, environment, listOptions);
	}

	/** see {@link TestProcessExecuter#yarnRun(Path, Map, String[])} */
	public ProcessResult yarnInstall(Path workingDir, String... options) {
		String[] installOptions = Lists.asList("install", options).toArray(String[]::new);
		return getExProcessExecuter().yarnRun(workingDir, environment, installOptions);
	}

	private TestProcessExecuter getExProcessExecuter() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		TestProcessExecuter tpExecuter = new TestProcessExecuter(injector);
		return tpExecuter;
	}

	static void trimOutputs(ProcessResult result, boolean removeUsage) {
		result.stdOut = result.stdOut.replace(result.getWorkingDir(), RELATIVE_PATH);
		result.errOut = result.errOut.replace(result.getWorkingDir(), RELATIVE_PATH);

		if (removeUsage) {
			result.stdOut = result.stdOut.replace(N4jscOptions.USAGE, "");
			result.errOut = result.errOut.replace(N4jscOptions.USAGE, "");
		}

		result.stdOut = result.stdOut.trim();
		result.errOut = result.errOut.trim();
	}
}
