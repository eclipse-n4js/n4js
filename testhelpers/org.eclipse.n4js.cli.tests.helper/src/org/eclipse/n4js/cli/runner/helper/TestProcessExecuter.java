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

import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.YarnBinary;

import com.google.inject.Injector;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class TestProcessExecuter {
	final private TestProcessBuilder processBuilder;

	/** Constructor */
	public TestProcessExecuter(Injector injector) {
		NodeJsBinary nodeJsBinary = injector.getInstance(NodeJsBinary.class);
		NpmBinary npmBinary = injector.getInstance(NpmBinary.class);
		YarnBinary yarnBinary = injector.getInstance(YarnBinary.class);
		processBuilder = new TestProcessBuilder(nodeJsBinary, npmBinary, yarnBinary);
	}

	interface ProcessSupplier {
		Process get() throws ExecutionException;
	}

	/** Runs node with the given {@code runFile} in the given {@code workingDir} */
	public ProcessResult run(Path workingDir, Path runFile) {
		return joinProcess(() -> processBuilder.nodejsRun(workingDir, runFile));
	}

	/** Runs npm install in the given {@code workingDir} */
	public ProcessResult npmInstall(Path workingDir) {
		return joinProcess(() -> processBuilder.npmInstall(workingDir));
	}

	/** Runs yarn install in the given {@code workingDir} */
	public ProcessResult yarnInstall(Path workingDir) {
		return joinProcess(() -> processBuilder.yarnInstall(workingDir));
	}

	private ProcessResult joinProcess(ProcessSupplier ps) {
		ProcessResult result = new ProcessResult();
		try {
			Process process = ps.get();
			result.exitCode = process.waitFor();
			result.stdOut = getInputAsString(process.getInputStream());
			result.errOut = getInputAsString(process.getErrorStream());

		} catch (Exception e) {
			result.exception = e;
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
