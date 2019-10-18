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

import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.SystemExitRedirecter.SystemExitException;

import com.google.common.base.Stopwatch;

/**
 * Abstract test class to be used when testing N4JS CLI related things.
 */
public class InProcessExecuter<ArgType> {
	final private SystemOutRedirecter systemOutRedirecter = new SystemOutRedirecter();
	final private SystemExitRedirecter systemExitRedirecter = new SystemExitRedirecter();

	interface N4jscProcess<ArgType> {
		/** Invokes the starting method of this test class */
		abstract public void doN4jsc(ArgType arg, CliCompileResult cliResult) throws Exception;
	}

	final private boolean isBackendEnabled;

	InProcessExecuter(boolean isBackendEnabled) {
		this.isBackendEnabled = isBackendEnabled;
	}

	/**
	 * Calls main entry point of N4jsc with the given args. Checks that the given exit code equals the actual exit code
	 * of the invocation. Removes {@link N4jscOptions#USAGE} text if desired.
	 */
	protected CliCompileResult n4jsc(ArgType args, CliCompileResult cliResult, N4jscProcess<ArgType> process) {
		Stopwatch sw = Stopwatch.createStarted();

		try {
			setRedirections();
			cliResult.workingDir = new File("").getAbsolutePath().toString();
			process.doN4jsc(args, cliResult);
			cliResult.exitCode = 0;

		} catch (SystemExitException e) {
			cliResult.exitCode = e.status;

		} catch (Exception e) {
			cliResult.exception = e;
			if (cliResult.exitCode == ProcessResult.NO_EXIT_CODE) {
				cliResult.exitCode = -1;
			}
			e.printStackTrace();
			fail(e.getMessage());

		} finally {
			cliResult.duration = sw.stop().elapsed(TimeUnit.MILLISECONDS);
			cliResult.stdOut = systemOutRedirecter.getSystemOut();
			cliResult.errOut = systemOutRedirecter.getSystemErr();

			if (N4jscTestFactory.isInjectorCreated()) {
				N4jscTestLanguageClient callback = (N4jscTestLanguageClient) N4jscFactory.getLanguageClient();
				cliResult.errors = callback.errors;
				cliResult.warnings = callback.warnings;
			}

			unsetRedirections();
		}

		return cliResult;
	}

	void setRedirections() {
		if (isBackendEnabled) {
			N4jscTestFactory.set();
		} else {
			N4jscTestFactory.setAndDeactivateBackend();
		}
		systemOutRedirecter.set();
		systemExitRedirecter.set();
	}

	void unsetRedirections() {
		systemOutRedirecter.unset();
		systemExitRedirecter.unset();
		N4jscTestFactory.unset();
	}
}
