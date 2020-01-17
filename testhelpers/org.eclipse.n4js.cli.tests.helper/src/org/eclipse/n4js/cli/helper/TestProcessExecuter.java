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
import org.eclipse.n4js.utils.io.ParallelReader;
import org.eclipse.n4js.utils.io.ParallelReader.CapturedOutput;

import com.google.common.base.Stopwatch;
import com.google.inject.Injector;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class TestProcessExecuter {
	final private boolean inheritIO;
	final private long timeout;
	final private TimeUnit timeoutUnit;
	final private TestProcessBuilder testProcessBuilder;

	/** Constructor */
	public TestProcessExecuter(Injector injector, boolean inheritIO, long timeout, TimeUnit unit) {
		this.inheritIO = inheritIO;
		this.timeout = timeout;
		this.timeoutUnit = unit;
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

	/** Runs n4jsc.jar in the given {@code workingDir} with the given environment additions and options. */
	public ProcessResult n4jscRun(Path workingDir, Map<String, String> environment, N4jscOptions options) {
		return joinProcess(() -> testProcessBuilder.n4jscRun(workingDir, environment, options));
	}

	/** Runs the given executable in the given {@code workingDir} with the given environment additions and options. */
	public ProcessResult run(Path workingDir, Map<String, String> environment, Path executable, String... options) {
		return joinProcess(() -> testProcessBuilder.run(workingDir, environment, executable, options));
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

			CapturedOutput output = ParallelReader.waitForAndCaptureOutput(process, inheritIO, timeout, timeoutUnit);
			result.exitCode = process.exitValue();
			result.stdOut = output.stdout;
			result.errOut = output.stderr;

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
}
