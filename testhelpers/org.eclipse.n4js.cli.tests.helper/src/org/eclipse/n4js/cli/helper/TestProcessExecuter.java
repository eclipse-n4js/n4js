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

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.eclipse.n4js.binaries.nodejs.JavaBinary;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.YarnBinary;
import org.eclipse.n4js.cli.N4jscOptions;

import com.google.common.base.Stopwatch;
import com.google.inject.Injector;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class TestProcessExecuter {
	final private TestProcessBuilder testProcessBuilder;

	/** Constructor */
	public TestProcessExecuter(Injector injector) {
		NodeJsBinary nodeJsBinary = injector.getInstance(NodeJsBinary.class);
		NpmBinary npmBinary = injector.getInstance(NpmBinary.class);
		YarnBinary yarnBinary = injector.getInstance(YarnBinary.class);
		JavaBinary javaBinary = injector.getInstance(JavaBinary.class);
		testProcessBuilder = new TestProcessBuilder(nodeJsBinary, npmBinary, yarnBinary, javaBinary);
	}

	interface ProcessSupplier {
		Process get() throws ExecutionException;
	}

	/** Runs node with the given {@code runFile} in the given {@code workingDir} */
	public ProcessResult runNodejs(Path workingDir, Map<String, String> environment, Path runFile, String... options) {
		return joinProcess(() -> testProcessBuilder.nodejsRun(workingDir, environment, runFile, options));
	}

	/** Runs npm OPTIONS in the given {@code workingDir} */
	public ProcessResult npmRun(Path workingDir, Map<String, String> environment, String... options) {
		return joinProcess(() -> testProcessBuilder.npmRun(workingDir, environment, options));
	}

	/** Runs yarn OPTIONS in the given {@code workingDir} */
	public ProcessResult yarnRun(Path workingDir, Map<String, String> environment, String... options) {
		return joinProcess(() -> testProcessBuilder.yarnRun(workingDir, environment, options));
	}

	/** Runs n4jsc.jar in the given {@code workingDir} with the given environment additions and options */
	public ProcessResult n4jscRun(Path workingDir, Map<String, String> environment, N4jscOptions options) {
		return joinProcess(() -> testProcessBuilder.n4jscRun(workingDir, environment, options));
	}

	private ProcessResult joinProcess(Supplier<ProcessBuilder> pbs) {
		ProcessBuilder processBuilder = pbs.get();
		File workingDir = processBuilder.directory();

		ProcessResult result = new ProcessResult();
		result.command = String.join(" ", processBuilder.command());
		result.workingDir = workingDir.toString();

		Stopwatch sw = Stopwatch.createStarted();
		try {
			Process process = processBuilder.start();
			result.exitCode = process.waitFor();
			result.stdOut = getInputAsString(process.getInputStream());
			result.errOut = getInputAsString(process.getErrorStream());

		} catch (Exception e) {
			result.exception = e;
			if (result.exitCode == ProcessResult.NO_EXIT_CODE) {
				result.exitCode = -1;
			}

		} finally {
			result.duration = sw.stop().elapsed(TimeUnit.MILLISECONDS);

			CliTools.trimOutputs(result, false);
		}

		return result;
	}

	private String getInputAsString(InputStream is) {
		try (java.util.Scanner scanner = new java.util.Scanner(is)) {
			String string = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			return string.trim();
		}
	}

}
