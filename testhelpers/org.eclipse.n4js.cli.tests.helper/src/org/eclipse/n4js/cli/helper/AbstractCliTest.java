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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.security.Permission;
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.junit.Before;

import com.google.common.base.Stopwatch;

/**
 * Abstract test class to be used when testing N4JS CLI related things.
 */
abstract public class AbstractCliTest<ArgType> {
	final private SystemOutRedirecter systemOutRedirecter = new SystemOutRedirecter();

	/** Invokes the starting method of this test class */
	abstract public void doN4jsc(ArgType arg, CliResult cliResult) throws Exception;

	/** Reset the injector setup */
	@Before
	public void before() {
		N4jscTestFactory.resetInjector();
	}

	/** Sets up the System outputs and Security Manager */
	final public void setN4jscRedirections() {
		internalSetN4jscRedirections();
		N4jscTestFactory.set();
	}

	/** Sets up the System outputs and Security Manager */
	final public void setN4jscRedirectionsDeactivateBackend() {
		internalSetN4jscRedirections();
		N4jscTestFactory.setAndDeactivateBackend();
	}

	private void internalSetN4jscRedirections() {
		// systemOutRedirecter.setRedirections();
		System.setSecurityManager(new NoExitSecurityManager());
	}

	/** Restores everything. */
	final public void unsetN4jscRedirections() {
		// systemOutRedirecter.unsetRedirections();
		System.setSecurityManager(null);
		N4jscTestFactory.unset();
	}

	/** Convenience version of {@link #n4jsc(Object, int)} with exist code == 0 */
	protected CliResult n4jsc(ArgType args) {
		return n4jsc(args, 0);
	}

	/** Convenience version of {@link #n4jsc(Object, int, boolean)} with exist code == 0 and removeUsage == true */
	protected CliResult n4jsc(ArgType args, int exitCode) {
		return n4jsc(args, exitCode, true);
	}

	/**
	 * Calls main entry point of N4jsc with the given args. Checks that the given exit code equals the actual exit code
	 * of the invocation. Removes {@link N4jscOptions#USAGE} text if desired.
	 */
	protected CliResult n4jsc(ArgType args, int exitCode, boolean removeUsage) {
		CliResult cliResult = new CliResult();
		Stopwatch sw = Stopwatch.createStarted();

		try {
			setN4jscRedirections();
			doN4jsc(args, cliResult);

		} catch (SystemExitException e) {
			cliResult.exitCode = e.status;
			assertEquals(exitCode, e.status);
		} catch (Exception e) {
			cliResult.cause = e;
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

			unsetN4jscRedirections();
		}
		String curDirPath = new File("").getAbsolutePath();

		cliResult.stdOut = cliResult.stdOut.replace(curDirPath, "");
		cliResult.errOut = cliResult.errOut.replace(curDirPath, "");

		if (removeUsage) {
			cliResult.stdOut = cliResult.stdOut.replace(N4jscOptions.USAGE, "");
			cliResult.errOut = cliResult.errOut.replace(N4jscOptions.USAGE, "");
		}

		cliResult.stdOut = cliResult.stdOut.trim();
		cliResult.errOut = cliResult.errOut.trim();

		return cliResult;
	}

	static class SystemExitException extends SecurityException {
		public final int status;

		public SystemExitException(int status) {
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		private final SecurityManager securityManager;

		NoExitSecurityManager() {
			this.securityManager = System.getSecurityManager();
		}

		@Override
		public void checkPermission(Permission perm) {
			if (securityManager != null) {
				securityManager.checkPermission(perm);
			}
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
			if (securityManager != null) {
				securityManager.checkPermission(perm, context);
			}
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new SystemExitException(status);
		}

	}
}
