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
package org.eclipse.n4js.utils.process;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.inject.Singleton;

/**
 * Process executor that either executes provided {@link Process} or uses provided {@link ProcessBuilder} to create
 * process. Obtained process is executed and {@link ProcessResult result} is returned.
 */
@Singleton
public class ProcessExecutor {

	@Inject
	private OutputStreamPrinterThreadProvider printerThreadProvider;

	private static final long DEFAULT_PROCESS_TIMEOUT = 240L;
	private static final long DEFAULT_THREAD_TIMEOUT = 30_000L;
	private static final int ERROR_EXIT_CODE = -1;
	private static final Logger LOGGER = Logger.getLogger(ProcessExecutor.class);

	/**
	 * Convenience method delegates to {@link #execute(Process, String, OutputRedirection)} with <code>null</code> as
	 * name and {@link OutputRedirection#SUPPRESS} as redirect flag).
	 */
	public ProcessResult execute(final Process process) {
		return execute(process, null, OutputRedirection.SUPPRESS);
	}

	/**
	 * Waits until the process terminates, the returns with a result wrapping the exit code of the terminated process
	 * and the standard output and error output of the process.
	 *
	 * @param process
	 *            the process to execute
	 * @param processName
	 *            name used in debug messages, null empty string used
	 * @param redirect
	 *            indicates if captured output should be swallowed or not. If {@link OutputRedirection#SUPPRESS}, then
	 *            the captured process output will not be flushed to an output streams, in other words, it will not be
	 *            redirected.
	 * @return a new result object that represents the actual result of the created process execution
	 */
	public ProcessResult execute(final Process process, final String processName, final OutputRedirection redirect) {
		// prepare name to be used in log messages
		final String name = processName == null ? " " : " '" + processName + "' ";

		try {

			if (process == null) {
				LOGGER.error("Started process" + name + "was null");
				return new ProcessResult(ERROR_EXIT_CODE, "", "");
			}

			try (OutputStreamPrinterThread stdOutThread = printerThreadProvider.getPrinterThreadForStdOut(process,
					redirect);
					OutputStreamPrinterThread stdErrThread = printerThreadProvider.getPrinterThreadForStdErr(process,
							redirect)) {

				boolean finished = process.waitFor(DEFAULT_PROCESS_TIMEOUT, SECONDS);
				if (!finished) {
					LOGGER.error(
							"Process didn't finish after " + DEFAULT_PROCESS_TIMEOUT + " " + SECONDS);
					return new ProcessResult(ERROR_EXIT_CODE, "", "");
				}

				stdOutThread.join(DEFAULT_THREAD_TIMEOUT);// ms
				stdErrThread.join(DEFAULT_THREAD_TIMEOUT);// ms

				ProcessResult processResult = new ProcessResult(process.exitValue(), stdOutThread.toString(),
						stdErrThread.toString());

				if (LOGGER.isDebugEnabled() && (!finished || !processResult.isOK())) {
					final String processLog = processResult.toString();
					if (!StringExtensions.isNullOrEmpty(processLog)) {
						LOGGER.debug(processLog);
					}
				}

				return processResult;
			}

		} catch (final InterruptedException e) {
			LOGGER.error("Thread was interrupted while waiting for process" + name + "to end.", e);
			return new ProcessResult(ERROR_EXIT_CODE, "", writeStackeTrace(e));

		} finally {
			if (process != null && process.isAlive()) {
				// try to force close
				try {
					process.destroyForcibly().waitFor(DEFAULT_PROCESS_TIMEOUT, SECONDS);
				} catch (final InterruptedException e) {
					LOGGER.error("Error while trying to forcefully terminate" + name + "process.",
							e);
				}
				if (!process.isAlive()) {
					LOGGER.debug("Spawned" + name + "process was successfully terminated.");
				} else {
					// there is nothing else we can do about it
					LOGGER.error(
							"Cannot terminate" + name + "subprocess. Termination timeouted after "
									+ DEFAULT_PROCESS_TIMEOUT + " " + SECONDS + ".");
				}
			} else {
				LOGGER.debug("Spawned" + name + "process was successfully terminated.");
			}
		}
	}

	/**
	 * Convenience method delegates to {@link #createAndExecute(ProcessBuilder, String, OutputRedirection)} with
	 * <code>null</code> as name and {@link OutputRedirection#SUPPRESS} as redirect flag).
	 */
	public ProcessResult createAndExecute(final ProcessBuilder processBuilder) {
		return createAndExecute(processBuilder, null, OutputRedirection.SUPPRESS);
	}

	/**
	 * Convenience method for clients that want to execute process from prepared {@link ProcessBuilder}. Delegates to
	 * {@link #execute(Process, String, OutputRedirection)}.
	 *
	 * @param processBuilder
	 *            the {@link ProcessBuilder} used to create process
	 * @param name
	 *            name used in debug messages, null empty string used
	 * @param redirect
	 *            indicates if captured output should be redirected to this process
	 * @return a new result object that represents the actual result of the created process execution
	 */
	public ProcessResult createAndExecute(final ProcessBuilder processBuilder, String name,
			OutputRedirection redirect) {
		// prepare name to be used in log messages
		String pbName = name == null ? " " : " for process '" + name + "' ";

		if (processBuilder == null) {
			LOGGER.error("Provided process builder" + pbName + "was null");
			return new ProcessResult(ERROR_EXIT_CODE, "", "");
		}

		try {
			return execute(processBuilder.start(), name, redirect);
		} catch (IOException e) {
			LOGGER.error("Cannot start process from process builder" + pbName, e);
			return new ProcessResult(ERROR_EXIT_CODE, "", "");
		}
	}

	private String writeStackeTrace(final Throwable t) {
		final StringBuilder sb = new StringBuilder(t.getClass().getName());
		for (final StackTraceElement element : t.getStackTrace()) {
			sb.append("\tat ").append(element.getClassName())
					.append(".").append(element.getMethodName())
					.append("(").append(element.getFileName())
					.append(":").append(element.getLineNumber())
					.append(")");
		}
		return sb.toString();
	}

}
