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
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscOptions;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 * Class to call n4jsc and other other cli tools.
 */
public class CliTools {

	/** Default timeout for executions performed via the methods in this class. */
	public static final long DEFAULT_TIMEOUT_IN_MINUTES = 30;

	private static final String RELATIVE_PATH = "...";

	final private Map<String, String> environment = new HashMap<>();

	private boolean inheritIO = false;
	private boolean ignoreFailure = false;
	private long timeout = DEFAULT_TIMEOUT_IN_MINUTES;
	private TimeUnit timeoutUnit = TimeUnit.MINUTES;

	/**
	 * Exception thrown for failures of a {@link CliTools CLI tool}, unless {@link CliTools#setIgnoreFailure(boolean)}
	 * is set to <code>true</code>.
	 */
	public static class CliException extends RuntimeException {
		private final ProcessResult result;

		/** Creates an instance. */
		public CliException(String message, ProcessResult result) {
			this(message, result, null);
		}

		/** Creates an instance. */
		public CliException(String message, ProcessResult result, Throwable cause) {
			super(message, cause);
			this.result = result;
		}

		/**
		 * Returns the process result with the {@link ProcessResult#getExitCode() exit code} and
		 * {@link ProcessResult#getException() exception} (if any).
		 */
		public ProcessResult getResult() {
			return result;
		}
	}

	/**
	 * Calls n4jsc main method and updates the {@code cliResult}. Backend is deactivated.
	 * <p>
	 * NOTE: since this is performed synchronously and in-process, the timeout set via
	 * {@link #setTimeout(long, TimeUnit)} does not apply!
	 */
	public void callN4jscFrontendInprocess(String[] options, boolean removeUsage, CliCompileResult result) {
		InProcessExecuter inProcessExecuter = new InProcessExecuter(false, inheritIO);
		inProcessExecuter.n4jsc(new File("").getAbsoluteFile(), options, result);
		trimOutputs(result, removeUsage);
		checkForFailure("n4jsc (front-end only, in process)", result, ignoreFailure);
	}

	/**
	 * Calls {@link N4jscMain#main(String[])} and updates the {@code cliResult}.
	 * <p>
	 * NOTE: since this is performed synchronously and in-process, the timeout set via
	 * {@link #setTimeout(long, TimeUnit)} does not apply!
	 */
	public void callN4jscInprocess(N4jscOptions options, boolean removeUsage, CliCompileResult result) {
		String[] args = options.toArgs().toArray(String[]::new);
		InProcessExecuter inProcessExecuter = new InProcessExecuter(true, inheritIO);
		inProcessExecuter.n4jsc(options.getDirs().get(0), args, result);
		trimOutputs(result, removeUsage);
		checkForFailure("n4jsc (with backend, in process)", result, ignoreFailure);
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
		checkForFailure("n4jsc (ex process)", cliResult, ignoreFailure);
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
	public void setInheritIO(boolean inheritIO) {
		this.inheritIO = inheritIO;
	}

	/**
	 * If set to <code>true</code>, the execution methods in this class will return normally in case of
	 * <ol>
	 * <li>exceptions being thrown during execution,
	 * <li>a non-zero exit code,
	 * <li>errors in the compiled N4JS source code (only applies to the n4jsc in-process methods, i.e.
	 * {@link #callN4jscFrontendInprocess(String[], boolean, CliCompileResult) #callN4jscFrontendInprocess()} and
	 * {@link #callN4jscInprocess(N4jscOptions, boolean, CliCompileResult) #callN4jscInprocess()}).
	 * </ol>
	 * The exception, exit code, and compile errors (if applicable) will then be available via the {@link ProcessResult}
	 * returned from the execution method.
	 * <p>
	 * The default is <code>false</code>, meaning that a {@link CliException} will be thrown by the execution method in
	 * the above three cases.
	 */
	public void setIgnoreFailure(boolean ignoreFailure) {
		this.ignoreFailure = ignoreFailure;
	}

	/**
	 * Set a custom timeout for executions performed via this class. Default timeout is
	 * {@value #DEFAULT_TIMEOUT_IN_MINUTES} minutes.
	 * <p>
	 * NOTE: this timeout does not apply to {@link #callN4jscInprocess(N4jscOptions, boolean, CliCompileResult)} and
	 * {@link #callN4jscFrontendInprocess(String[], boolean, CliCompileResult)}.
	 */
	public void setTimeout(long duration, TimeUnit unit) {
		this.timeout = duration;
		this.timeoutUnit = unit;
	}

	/** see {@link TestProcessExecuter#runNodejs(Path, Map, Path, String[])} */
	public ProcessResult runNodejs(Path workingDir, Path runFile, String... options) {
		return getExProcessExecuter().runNodejs(workingDir, environment, runFile, options);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmInstall(Path workingDir, String... options) {
		String[] installOptions = Lists.asList("install", options).toArray(String[]::new);
		return runNpm(workingDir, installOptions);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmList(Path workingDir, String... options) {
		String[] listOptions = Lists.asList("list", options).toArray(String[]::new);
		return runNpm(workingDir, listOptions);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult runNpm(Path workingDir, String... options) {
		return getExProcessExecuter().npmRun(workingDir, environment, options);
	}

	/** see {@link TestProcessExecuter#yarnRun(Path, Map, String[])} */
	public ProcessResult yarnInstall(Path workingDir, String... options) {
		String[] installOptions = Lists.asList("install", options).toArray(String[]::new);
		return runYarn(workingDir, installOptions);
	}

	/** see {@link TestProcessExecuter#yarnRun(Path, Map, String[])} */
	public ProcessResult runYarn(Path workingDir, String... options) {
		return getExProcessExecuter().yarnRun(workingDir, environment, options);
	}

	/** see {@link TestProcessExecuter#run(Path, Map, Path, String...)} */
	public ProcessResult run(Path workingDir, Path executable, String... options) {
		return getExProcessExecuter().run(workingDir, environment, executable, options);
	}

	private TestProcessExecuter getExProcessExecuter() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		TestProcessExecuter tpExecuter = new TestProcessExecuter(injector, inheritIO, ignoreFailure,
				timeout, timeoutUnit);
		return tpExecuter;
	}

	static void trimOutputs(ProcessResult result, boolean removeUsage) {
		String workingDirAbsolute = new File(result.getWorkingDir()).getAbsolutePath();

		result.stdOut = result.stdOut.replace(workingDirAbsolute, RELATIVE_PATH);
		result.errOut = result.errOut.replace(workingDirAbsolute, RELATIVE_PATH);

		if (removeUsage) {
			result.stdOut = result.stdOut.replace(N4jscOptions.USAGE, "");
			result.errOut = result.errOut.replace(N4jscOptions.USAGE, "");
		}

		result.stdOut = result.stdOut.trim();
		result.errOut = result.errOut.trim();
	}

	static void checkForFailure(String executableName, ProcessResult result, boolean ignoreFailure) {
		if (ignoreFailure) {
			return; // don't check in this case
		}
		if (result.getException() != null) {
			throw new CliException(
					"exception during " + executableName + System.lineSeparator() + result,
					result, result.getException());
		} else if (result.getExitCode() != 0) {
			throw new CliException(
					"non-zero exit code from " + executableName + ": " + result.getExitCode() + System.lineSeparator()
							+ result,
					result);
		}
		if (result instanceof CliCompileResult && !(result instanceof CliCompileProcessResult)) {
			CliCompileResult compileResult = (CliCompileResult) result;
			if (compileResult.getErrs() > 0) {
				throw new CliException("errors in compiled sources: "
						+ Joiner.on(System.lineSeparator()).join(compileResult.getErrMsgs()) + System.lineSeparator()
						+ result, result);
			}
		}
	}
}
