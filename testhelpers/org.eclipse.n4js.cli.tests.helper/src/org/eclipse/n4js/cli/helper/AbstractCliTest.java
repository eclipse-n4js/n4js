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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.Permission;

import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscOptions;
import org.junit.After;
import org.junit.Before;

/**  */
abstract public class AbstractCliTest<ArgType> {

	private ByteArrayOutputStream baos;

	/** Invokes the starting method of this test class */
	abstract public void doMain(ArgType arg) throws Exception;

	/** Sets up the System outputs and Security Manager */
	@Before
	final public void before() throws UnsupportedEncodingException {
		baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true, "UTF-8");
		N4jscConsole.setPrintStream(ps);
		System.setSecurityManager(new NoExitSecurityManager());
	}

	/** Restores everything. */
	@After
	final public void after() throws IOException {
		baos.close();
		System.setSecurityManager(null); // restore original security manager
	}

	/** Convenience version of {@link #main(Object, int)} with exist code == 0 */
	protected String main(ArgType args) {
		return main(args, 0);
	}

	/** Convenience version of {@link #main(Object, int, boolean)} with exist code == 0 and removeUsage == true */
	protected String main(ArgType args, int exitCode) {
		return main(args, exitCode, true);
	}

	/**
	 * Calls main entry point of N4jsc with the given args. Checks that the given exit code equals the actual exit code
	 * of the invocation. Removes {@link N4jscOptions#USAGE} text if desired.
	 */
	protected String main(ArgType args, int exitCode, boolean removeUsage) {
		String consoleLog = "";
		try {
			baos.reset();
			doMain(args);
		} catch (SystemExitException e) {
			assertEquals(exitCode, e.status);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		consoleLog = getConsoleOutput();
		String curDirPath = new File("").getAbsolutePath();
		consoleLog = consoleLog.replace(curDirPath, "");

		if (removeUsage) {
			consoleLog = consoleLog.replace(N4jscOptions.USAGE, "");
		}
		return consoleLog.trim();
	}

	/** @return console output */
	protected String getConsoleOutput() {
		try {
			String output = baos.toString("UTF-8");
			return output;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
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
