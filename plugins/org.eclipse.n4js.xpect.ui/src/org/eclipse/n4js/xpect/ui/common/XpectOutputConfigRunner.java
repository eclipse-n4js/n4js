/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.ui.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * N4JS runner dedicated to XpectOutput methods. Delegates to the {@link RunnerFrontEnd} to invoke n4js runners.
 */
public class XpectOutputConfigRunner {

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	private final static String NL = "\n";

	/**
	 * Executes provided configuration, that is assumed to come from XpectOutput test setup. Code is executed with
	 * {@code RunnerFrontEnd} facility. On demand, captured output is decorated with XpectOutput tokens to separate
	 * stdout from stderr.
	 */
	public String executeWithConfig(final RunConfiguration runConfig, boolean decorateStdStreams) {
		String executionResult;
		List<String> combinedOutput = new ArrayList<>();
		try {
			Process process = runnerFrontEnd.run(runConfig, executor());
			EngineOutput eo = captureOutput(process);

			combinedOutput.addAll(eo.getStdOut());
			if (!eo.getErrOut().isEmpty()) {
				if (decorateStdStreams) {
					combinedOutput.add("=== stderr ===");
				}
				combinedOutput.addAll(eo.getErrOut());
			}

			executionResult = Joiner.on(NL).join(combinedOutput);
		} catch (Exception e) {
			executionResult = e.getMessage();
			// print for troubleshooting purposes
			System.out.println("Error in N4js execution " + e);
			e.printStackTrace();
		}
		return executionResult;
	}

	/**
	 * This operation is blocking (waits for process to finish).
	 *
	 * @param process
	 *            the process to get the standard out and error from.
	 *
	 * @return output captured during process run
	 */
	private EngineOutput captureOutput(Process process) throws IOException {
		List<String> out = new ArrayList<>();
		List<String> err = new ArrayList<>();
		String outLine;
		String errLine;
		BufferedReader bri = null;
		BufferedReader bre = null;
		try {
			bri = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((outLine = bri.readLine()) != null) {
				out.add(outLine);
			}
			bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((errLine = bre.readLine()) != null) {
				err.add(errLine);
			}
		} catch (Exception e) {
			System.err.println("Exception in Engine.captureOutput: " + e.getMessage());
		} finally {
			if (bri != null) {
				bri.close();
			}
			if (bre != null) {
				bre.close();
			}
		}
		return new EngineOutput(out, err);
	}

	/**
	 * private Executor which is not redirecting out & err streams.
	 */
	private IExecutor executor() {
		return new IExecutor() {
			@Override
			public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> envp)
					throws ExecutionException {

				ProcessBuilder pb = new ProcessBuilder(cmdLine);
				pb.environment().putAll(envp);
				pb.directory(workingDirectory);

				try {
					return pb.start();
				} catch (IOException e) {
					throw new ExecutionException(e);
				}
			}
		};
	}
}
